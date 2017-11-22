
package com.telabase.ws.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.telabase.ds.dao.*;
import com.telabase.ds.entity.*;
import com.telabase.util.StringUtil;
import com.telabase.ws.model.DataList;
import com.telabase.ws.model.WSResponse;

@Controller
public class PatientController {

	private static final String BUCKET_NAME = "patient_profile_photo";
	private static Storage storage = null;

	private final static Logger logger = Logger.getLogger(PatientController.class.getName());

	@RequestMapping(value = "/api/patient/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Patient> list(HttpServletRequest request) {
		PatientDAO serve = new PatientDAO();
		return serve.list();
	}

	@RequestMapping(value = "/api/patient/datatable/list", method = RequestMethod.GET)
	@ResponseBody
	public DataList listDatatable(HttpServletRequest request) {

		String sStart = request.getParameter("start");
		String sLength = request.getParameter("length");
		List<Patient> rs = null;
		PatientDAO serve = new PatientDAO();
		DataList ds = new DataList();
		if(sStart!=null && sLength!=null){
			int start = Integer.parseInt(sStart);
			int length = Integer.parseInt(sLength);
				rs = serve.list(start,  start+length);
				ds.setRecordsFiltered(serve.count());
				ds.setRecordsTotal(rs.size());
			
		}else{
			ds.setRecordsTotal(serve.count());
		}
		ds.setData(rs);
		
		return ds;

	}

	@RequestMapping(value = "/api/patient/query", method = RequestMethod.GET)
	@ResponseBody
	public List<Patient> query(HttpServletRequest request) {
		PatientDAO serve = new PatientDAO();
		if (request.getParameter("keyword") == null)
			return null;
		return serve.searchByFirstname(request.getParameter("keyword"));
	}

	@RequestMapping(value = "/api/patient/securityCode/query", method = RequestMethod.GET)
	@ResponseBody
	public List<Patient> queryBySecurityCode(HttpServletRequest request) {
		PatientDAO serve = new PatientDAO();
		if (request.getParameter("code") == null)
			return null;
		return serve.searchBySecurityCode(request.getParameter("code"));
	}

	@RequestMapping(value = "/api/patient/save", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse save(@RequestBody Patient o, HttpServletRequest request) {

		PatientDAO serve = new PatientDAO();
		try {
			serve.save(o);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}
	
		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}
	
	@RequestMapping(value = "/public/patient/photo/download", method = RequestMethod.GET)
	public void downloadPhoto(HttpServletRequest request, HttpServletResponse resp) {
		GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
			      .initialRetryDelayMillis(10)
			      .retryMaxAttempts(10)
			      .totalRetryPeriodMillis(15000)
			      .build());
		PatientDAO serve = new PatientDAO();
		Patient p = serve.findById(Long.parseLong(request.getParameter("id")));
		GcsFilename fileName = new GcsFilename(BUCKET_NAME, p.getPhotoFilePath());
		GcsInputChannel readChannel = gcsService.openPrefetchingReadChannel(fileName, 0, 2 * 1024 * 1024);
	    try {
			copy(Channels.newInputStream(readChannel), resp.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/api/patient/photo/upload", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse uploadPhoto(HttpServletRequest request) {
		GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
			      .initialRetryDelayMillis(10)
			      .retryMaxAttempts(10)
			      .totalRetryPeriodMillis(15000)
			      .build());
		GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
		GcsOutputChannel outputChannel;
		try {
			System.out.println(request.getParameter("id"));
			if(request.getParameter("id")!=null) {
				PatientDAO serve = new PatientDAO();
				 Patient p = serve.findById(Long.parseLong(request.getParameter("id")));
				 if(p.getPhotoFilePath() ==null) {
					 p.setPhotoFilePath(StringUtil.randomString(10)+".jpg");
					 serve.save(p);
				 }
				 
				GcsFilename fileName = new GcsFilename(BUCKET_NAME, p.getPhotoFilePath());//getFileName(request);
				 outputChannel = gcsService.createOrReplace(fileName, instance);
				 copy(request.getInputStream(), Channels.newOutputStream(outputChannel));
				 return new WSResponse(fileName.toString(), WSResponse.STATUS_SUCCESS);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return new WSResponse("Error Uploading", WSResponse.STATUS_FAIL);
		//
		// if(storage == null)
		// storage = StorageOptions.getDefaultInstance().getService();
		// PatientDAO serve = new PatientDAO();
		// try {
		// List<FileItem> items = new ServletFileUpload(new
		// DiskFileItemFactory()).parseRequest(request);
		// String id = null;
		// String fileName = null;
		// for (FileItem item : items) {
		// if(item.isFormField()) {
		// id = item.getString();
		// }else if (!item.isFormField()) {
		//
		// // Process form file field (input type="file").
		// String fieldName = item.getFieldName();
		//
		// fileName =
		// StringUtil.randomString(10)+"."+FilenameUtils.getExtension(fieldName);
		// System.out.println("uploading filename "+fileName);
		// InputStream fileContent = item.getInputStream();
		// // upload to storage bucket
		// // Modify access list to allow all users with link to read file
		// List<Acl> acls = new ArrayList<>();
		// acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
		// // the inputstream is closed by default, so we don't need to close it here
		// Blob blob = storage.create(BlobInfo.newBuilder(BUCKET_NAME,
		// fileName).setAcl(acls).build(),
		// fileContent);
		//
		//
		//
		// // return the public download link
		// return new WSResponse(blob.getMediaLink(), WSResponse.STATUS_SUCCESS);
		// }
		// }
		// // set filepath to patient
		// if(id!=null) {
		// Patient p = serve.findById(Long.parseLong(id));
		// p.setPhotoFilePath(fileName);
		// serve.save(p);
		// }
		// } catch (FileUploadException | IOException e) {
		// return new WSResponse("Error Uploading", WSResponse.STATUS_FAIL);
		// }
		// return new WSResponse("Error Uploading", WSResponse.STATUS_FAIL);
		//
	}

	@RequestMapping(value = "/api/patient/setloc", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse setloc(@RequestBody Patient o, HttpServletRequest request) {

		PatientDAO serve = new PatientDAO();
		try {
			Patient p = serve.findById(o.getId());
			p.setHomeLat(o.getHomeLat());
			p.setHomeLong(o.getHomeLong());
			p.setWarnDistance(o.getWarnDistance());
			serve.save(p);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}
		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}

	@RequestMapping(value = "/api/patient/sethome", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse sethome(@RequestBody Patient o, HttpServletRequest request) {

		PatientDAO serve = new PatientDAO();
		try {
			Patient p = serve.findById(o.getId());

			p.setHomeLat(o.getHomeLat());
			p.setHomeLong(o.getHomeLong());
			serve.save(p);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}
		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}

	@RequestMapping(value = "/api/patient/setwarndistance", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse setWarnDistance(@RequestBody Patient o, HttpServletRequest request) {

		PatientDAO serve = new PatientDAO();
		try {
			Patient p = serve.findById(o.getId());
			p.setWarnDistance(o.getWarnDistance());
			serve.save(p);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}
		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}

	@RequestMapping(value = "/api/patient/get", method = RequestMethod.GET)
	@ResponseBody
	public Patient findById(HttpServletRequest request) {

		PatientDAO serve = new PatientDAO();
		Patient o = serve.findById(Long.parseLong(request.getParameter("id")));
		return o;
	}
	

	@RequestMapping(value = "/api/patient/delete", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse delete(HttpServletRequest request) {

		PatientDAO serve = new PatientDAO();
		try {
			Patient o = new Patient();
			o.setId(Long.parseLong(request.getParameter("id")));
			serve.delete(o);

		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(null, WSResponse.STATUS_FAIL);
		}
		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}
	
	

	@RequestMapping(value = "/admin/patient/batch/save", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> saveAsBatch(@RequestBody Patient[] list, HttpServletRequest request) {
		List<WSResponse> resList = new ArrayList<WSResponse>();
		String result;
		for (Patient d : list) {

			resList.add(this.save(d, request));
		}

		return resList;
	}

	// Manual added code
	@RequestMapping(value = "/api/patient/caregiver/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Patient> listByCareGiver(HttpServletRequest request) {

		long id = Long.parseLong(request.getParameter("id"));
		CarePermitDAO dao = new CarePermitDAO();
		PatientDAO pDAO = new PatientDAO();
		List<CarePermit> rs = dao.findCarePermitByCareGiverId(id);
		List<Patient> pList = new ArrayList<Patient>();
		Patient patient;
		for (CarePermit permit : rs) {
			patient = pDAO.findById(permit.getPatientId());
			if (patient != null)
				pList.add(patient);
		}
		return pList;
	}


	/**
	 * Transfer the data from the inputStream to the outputStream. Then close both
	 * streams.
	 */
	private void copy(InputStream input, OutputStream output) throws IOException {
		try {
			byte[] buffer = new byte[2 * 1024 * 1024];
			int bytesRead = input.read(buffer);
			ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
			while (bytesRead != -1) {
				bOutput.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}
			Image image = ImagesServiceFactory.makeImage(bOutput.toByteArray());
			ImagesService imageService = ImagesServiceFactory.getImagesService();
			Transform resizeTransform = ImagesServiceFactory.makeResize(300, 300);
			Image resizedImage = imageService.applyTransform(resizeTransform, image);
			
			output.write(resizedImage.getImageData());	
		} finally {
			input.close();
			output.close();
		}
	}

}
