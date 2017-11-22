package com.telabase.ws.controller;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class UploadImageServlet extends HttpServlet {
	private static final String BUCKET_NAME = "patient_profile_photo";
	private static Storage storage = null;

	@Override
	public void init() {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	@Override
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
	      ServletException {
		
		  try {
		        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		        for (FileItem item : items) {
		            if (!item.isFormField()) {
		               
		                // Process form file field (input type="file").
		                String fieldName = item.getFieldName();
		                String fileName = FilenameUtils.getName(item.getName());
		                InputStream fileContent = item.getInputStream();
		                // upload to storage bucket
		                // Modify access list to allow all users with link to read file
		        	    List<Acl> acls = new ArrayList<>();
		        	    acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
		        	    // the inputstream is closed by default, so we don't need to close it here
		        	    Blob blob =
		        	        storage.create(
		        	            BlobInfo.newBuilder(BUCKET_NAME, fileName).setAcl(acls).build(),
		        	            fileContent);

		        	    // return the public download link
		        	    response.getWriter().print(blob.getMediaLink());
		            }
		        }
		    } catch (FileUploadException e) {
		        throw new ServletException("Cannot parse multipart request.", e);
		    }
	   

	   
	  }

}


