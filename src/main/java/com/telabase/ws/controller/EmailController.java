package com.telabase.ws.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.telabase.ds.dao.EmCenterDAO;
import com.telabase.ds.dao.PatientDAO;
import com.telabase.ds.entity.EmCenter;
import com.telabase.ds.entity.Patient;
import com.telabase.security.entity.User;
import com.telabase.ws.model.WSResponse;

@Controller
public class EmailController {
	private static final String FROM_ADDRESS = "cnacha@gmail.com";
	
	@RequestMapping(value = "/api/patient/send", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse sendPatientEmail(HttpServletRequest request) {

		PatientDAO serve = new PatientDAO();
		Patient o = serve.findById(Long.parseLong(request.getParameter("id")));
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress(FROM_ADDRESS, "LifeGuard Admin"));
		  msg.addRecipient(Message.RecipientType.TO,
		                   new InternetAddress(request.getParameter("email"), "Customer"));
		  msg.setSubject("Your LifeGuard product has been activated");
		  StringBuffer mailContent = new StringBuffer();
		  mailContent.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\n" + 
		  		"    <!--[if gte mso 9]><xml>\n" + 
		  		"     <o:OfficeDocumentSettings>\n" + 
		  		"      <o:AllowPNG/>\n" + 
		  		"      <o:PixelsPerInch>96</o:PixelsPerInch>\n" + 
		  		"     </o:OfficeDocumentSettings>\n" + 
		  		"    </xml><![endif]-->\n" + 
		  		"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" + 
		  		"    <meta name=\"viewport\" content=\"width=device-width\">\n" + 
		  		"    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" + 
		  		"    <title></title>\n" + 
		  		"    <!--[if !mso]><!-- -->\n" + 
		  		"	<link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\">\n" + 
		  		"	<!--<![endif]-->\n" + 
		  		"    \n" + 
		  		"    <style type=\"text/css\" id=\"media-query\">\n" + 
		  		"      body {\n" + 
		  		"  margin: 0;\n" + 
		  		"  padding: 0; }\n" + 
		  		"\n" + 
		  		"table, tr, td {\n" + 
		  		"  vertical-align: top;\n" + 
		  		"  border-collapse: collapse; }\n" + 
		  		"\n" + 
		  		".ie-browser table, .mso-container table {\n" + 
		  		"  table-layout: fixed; }\n" + 
		  		"\n" + 
		  		"* {\n" + 
		  		"  line-height: inherit; }\n" + 
		  		"\n" + 
		  		"a[x-apple-data-detectors=true] {\n" + 
		  		"  color: inherit !important;\n" + 
		  		"  text-decoration: none !important; }\n" + 
		  		"\n" + 
		  		"[owa] .img-container div, [owa] .img-container button {\n" + 
		  		"  display: block !important; }\n" + 
		  		"\n" + 
		  		"[owa] .fullwidth button {\n" + 
		  		"  width: 100% !important; }\n" + 
		  		"\n" + 
		  		"[owa] .block-grid .col {\n" + 
		  		"  display: table-cell;\n" + 
		  		"  float: none !important;\n" + 
		  		"  vertical-align: top; }\n" + 
		  		"\n" + 
		  		".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {\n" + 
		  		"  width: 500px !important; }\n" + 
		  		"\n" + 
		  		".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {\n" + 
		  		"  line-height: 100%; }\n" + 
		  		"\n" + 
		  		".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {\n" + 
		  		"  width: 164px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {\n" + 
		  		"  width: 328px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {\n" + 
		  		"  width: 250px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {\n" + 
		  		"  width: 166px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {\n" + 
		  		"  width: 125px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {\n" + 
		  		"  width: 100px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {\n" + 
		  		"  width: 83px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {\n" + 
		  		"  width: 71px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {\n" + 
		  		"  width: 62px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {\n" + 
		  		"  width: 55px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {\n" + 
		  		"  width: 50px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {\n" + 
		  		"  width: 45px !important; }\n" + 
		  		"\n" + 
		  		".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {\n" + 
		  		"  width: 41px !important; }\n" + 
		  		"\n" + 
		  		"@media only screen and (min-width: 520px) {\n" + 
		  		"  .block-grid {\n" + 
		  		"    width: 500px !important; }\n" + 
		  		"  .block-grid .col {\n" + 
		  		"    vertical-align: top; }\n" + 
		  		"    .block-grid .col.num12 {\n" + 
		  		"      width: 500px !important; }\n" + 
		  		"  .block-grid.mixed-two-up .col.num4 {\n" + 
		  		"    width: 164px !important; }\n" + 
		  		"  .block-grid.mixed-two-up .col.num8 {\n" + 
		  		"    width: 328px !important; }\n" + 
		  		"  .block-grid.two-up .col {\n" + 
		  		"    width: 250px !important; }\n" + 
		  		"  .block-grid.three-up .col {\n" + 
		  		"    width: 166px !important; }\n" + 
		  		"  .block-grid.four-up .col {\n" + 
		  		"    width: 125px !important; }\n" + 
		  		"  .block-grid.five-up .col {\n" + 
		  		"    width: 100px !important; }\n" + 
		  		"  .block-grid.six-up .col {\n" + 
		  		"    width: 83px !important; }\n" + 
		  		"  .block-grid.seven-up .col {\n" + 
		  		"    width: 71px !important; }\n" + 
		  		"  .block-grid.eight-up .col {\n" + 
		  		"    width: 62px !important; }\n" + 
		  		"  .block-grid.nine-up .col {\n" + 
		  		"    width: 55px !important; }\n" + 
		  		"  .block-grid.ten-up .col {\n" + 
		  		"    width: 50px !important; }\n" + 
		  		"  .block-grid.eleven-up .col {\n" + 
		  		"    width: 45px !important; }\n" + 
		  		"  .block-grid.twelve-up .col {\n" + 
		  		"    width: 41px !important; } }\n" + 
		  		"\n" + 
		  		"@media (max-width: 520px) {\n" + 
		  		"  .block-grid, .col {\n" + 
		  		"    min-width: 320px !important;\n" + 
		  		"    max-width: 100% !important;\n" + 
		  		"    display: block !important; }\n" + 
		  		"  .block-grid {\n" + 
		  		"    width: calc(100% - 40px) !important; }\n" + 
		  		"  .col {\n" + 
		  		"    width: 100% !important; }\n" + 
		  		"    .col > div {\n" + 
		  		"      margin: 0 auto; }\n" + 
		  		"  img.fullwidth, img.fullwidthOnMobile {\n" + 
		  		"    max-width: 100% !important; }\n" + 
		  		"  .no-stack .col {\n" + 
		  		"    min-width: 0 !important;\n" + 
		  		"    display: table-cell !important; }\n" + 
		  		"  .no-stack.two-up .col {\n" + 
		  		"    width: 50% !important; }\n" + 
		  		"  .no-stack.mixed-two-up .col.num4 {\n" + 
		  		"    width: 33% !important; }\n" + 
		  		"  .no-stack.mixed-two-up .col.num8 {\n" + 
		  		"    width: 66% !important; }\n" + 
		  		"  .no-stack.three-up .col.num4 {\n" + 
		  		"    width: 33% !important; }\n" + 
		  		"  .no-stack.four-up .col.num3 {\n" + 
		  		"    width: 25% !important; } }\n" + 
		  		"\n" + 
		  		"    </style>\n" + 
		  		"</head>\n" + 
		  		"<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #FFFFFF\">\n" + 
		  		"  <style type=\"text/css\" id=\"media-query-bodytag\">\n" + 
		  		"    @media (max-width: 520px) {\n" + 
		  		"      .block-grid {\n" + 
		  		"        min-width: 320px!important;\n" + 
		  		"        max-width: 100%!important;\n" + 
		  		"        width: 100%!important;\n" + 
		  		"        display: block!important;\n" + 
		  		"      }\n" + 
		  		"\n" + 
		  		"      .col {\n" + 
		  		"        min-width: 320px!important;\n" + 
		  		"        max-width: 100%!important;\n" + 
		  		"        width: 100%!important;\n" + 
		  		"        display: block!important;\n" + 
		  		"      }\n" + 
		  		"\n" + 
		  		"        .col > div {\n" + 
		  		"          margin: 0 auto;\n" + 
		  		"        }\n" + 
		  		"\n" + 
		  		"      img.fullwidth {\n" + 
		  		"        max-width: 100%!important;\n" + 
		  		"      }\n" + 
		  		"			img.fullwidthOnMobile {\n" + 
		  		"        max-width: 100%!important;\n" + 
		  		"      }\n" + 
		  		"      .no-stack .col {\n" + 
		  		"				min-width: 0!important;\n" + 
		  		"				display: table-cell!important;\n" + 
		  		"			}\n" + 
		  		"			.no-stack.two-up .col {\n" + 
		  		"				width: 50%!important;\n" + 
		  		"			}\n" + 
		  		"			.no-stack.mixed-two-up .col.num4 {\n" + 
		  		"				width: 33%!important;\n" + 
		  		"			}\n" + 
		  		"			.no-stack.mixed-two-up .col.num8 {\n" + 
		  		"				width: 66%!important;\n" + 
		  		"			}\n" + 
		  		"			.no-stack.three-up .col.num4 {\n" + 
		  		"				width: 33%!important\n" + 
		  		"			}\n" + 
		  		"			.no-stack.four-up .col.num3 {\n" + 
		  		"				width: 25%!important\n" + 
		  		"			}\n" + 
		  		"    }\n" + 
		  		"  </style>\n" + 
		  		"  <!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" + 
		  		"  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" + 
		  		"  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #FFFFFF;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">\n" + 
		  		"	<tbody>\n" + 
		  		"	<tr style=\"vertical-align: top\">\n" + 
		  		"		<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" + 
		  		"    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #FFFFFF;\"><![endif]-->\n" + 
		  		"\n" + 
		  		"    <div style=\"background-color:transparent;\">\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\n" + 
		  		"\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth  fullwidth\" style=\"padding-right: 30px;  padding-left: 30px;\">\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px;\" align=\"center\"><![endif]-->\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div>  <img class=\"center  autowidth  fullwidth\" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/thanks.jpg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 440px\" width=\"440\">\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div><!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"</div>\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth \" style=\"padding-right: 0px;  padding-left: 0px;\">\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px;\" align=\"center\"><![endif]-->\n" + 
		  		"  <img class=\"center  autowidth \" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/icon.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 192px\" width=\"192\">\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"</div>\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 30px; padding-bottom: 15px;\"><![endif]-->\n" + 
		  		"<div style=\"font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;color:#555555; padding-right: 30px; padding-left: 30px; padding-top: 30px; padding-bottom: 15px;\">	\n" + 
		  		"	<div style=\"font-family:inherit;font-size:12px;line-height:14px;color:#555555;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 16px; line-height: 19px;\"><strong>ขอบคุณสำหรับการสนับสนุนสินค้าของเรา </strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 16px; line-height: 19px;\"><strong>ขณะนี้ระบบได้ทำการทะเบียนสินค้าให้คุณเป็นที่เรียบร้อยแล้ว </strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 16px; line-height: 19px;\"><strong>โดยมีรายละเอียดดังต่อไปนี้</strong></span></p></div>	\n" + 
		  		"</div>\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;\"><![endif]-->\n" + 
		  		"<div style=\"font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;line-height:120%;color:#49B4C6; padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;\">	\n" + 
		  		"	<div style=\"font-size:12px;line-height:14px;color:#49B4C6;font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 18px; line-height: 21px;\"><strong><span style=\"line-height: 21px; font-size: 18px;\">ข้อมูลการลงเบียนสินค้า</span></strong></span></p></div>	\n" + 
		  		"</div>\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" + 
		  		"<div style=\"color:#989898;line-height:200%;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\n" + 
		  		"	<div style=\"font-size:12px;line-height:24px;color:#989898;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 24px;text-align: center\"><span style=\"font-size: 16px; line-height: 32px;\"><strong>ข้อมูลผู้ใช้</strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 24px;text-align: center\">ชื่อ-นามสกุล<span style=\"font-size: 14px; line-height: 28px;\"> <strong>"+o.getFirstname()+" "+o.getLastname()+"</strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 24px;text-align: center\">อายุ <span style=\"font-size: 14px; line-height: 28px;\"><strong>"+o.getAge()+"</strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 24px;text-align: center\">โรคประจำตัว <span style=\"font-size: 14px; line-height: 28px;\"><strong>"+o.getDisease()+"</strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 24px;text-align: center\">เบอร์โทรศัพท์บนสายรัดข้อมูล <span style=\"font-size: 14px; line-height: 28px;\"><strong>"+o.getPhone()+"</strong></span></p></div>	\n" + 
		  		"</div>\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <div style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">\n" + 
		  		"  <!--[if (mso)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px;padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td><![endif]-->\n" + 
		  		"  <div align=\"center\"><div style=\"border-top: 1px solid #BBBBBB; width:100%; line-height:1px; height:1px; font-size:1px;\">&#160;</div></div>\n" + 
		  		"  <!--[if (mso)]></td></tr></table></td></tr></table><![endif]-->\n" + 
		  		"</div>\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" + 
		  		"<div style=\"color:#49B4C6;line-height:120%;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\n" + 
		  		"	<div style=\"font-size:12px;line-height:14px;color:#49B4C6;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\"><span style=\"font-size: 16px; line-height: 19px;\"><strong>รหัสลับ (Security Code)</strong></span></p></div>	\n" + 
		  		"</div>\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" + 
		  		"<div style=\"color:#555555;line-height:120%;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\n" + 
		  		"	<div style=\"font-size:12px;line-height:14px;color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\"><span style=\"font-size: 20px; line-height: 24px;\"><strong>"+o.getSecurityCode()+"</strong></span></p></div>	\n" + 
		  		"</div>\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" + 
		  		"<div style=\"color:#555555;line-height:120%;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\n" + 
		  		"	<div style=\"font-size:12px;line-height:14px;color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">ท่านสามารถใช้รหัสด้านบนในการเพิ่มคนไข้ในการดูแล</p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">เพื่อช่วยติดตามตำแหน่งและติดต่อ</p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">จากแอพพลิเคชั่น LifeCare</p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">ซึ่งสามารถค้นหาได้จาก Google Play และ App Store&#160;</p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">&#160;<br></p></div>	\n" + 
		  		"</div>\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                  \n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth \" style=\"padding-right: 0px;  padding-left: 0px;\">\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px;\" align=\"center\"><![endif]-->\n" + 
		  		"  <img class=\"center  autowidth \" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/appstores-640.jpg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 200px\" width=\"200\">\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\n" + 
		  		"</div>\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" + 
		  		"              </div>\n" + 
		  		"            </div>\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" + 
		  		"        </div>\n" + 
		  		"      </div>\n" + 
		  		"    </div>    <div style=\"background-color:#49B4C6;\">\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:#49B4C6;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\n" + 
		  		"\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                    <div style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\">\n" + 
		  		"  <!--[if (mso)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px;padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\"><table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td><![endif]-->\n" + 
		  		"  <div align=\"center\"><div style=\"border-top: 0px solid transparent; width:100%; line-height:0px; height:0px; font-size:0px;\">&#160;</div></div>\n" + 
		  		"  <!--[if (mso)]></td></tr></table></td></tr></table><![endif]-->\n" + 
		  		"</div>\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" + 
		  		"              </div>\n" + 
		  		"            </div>\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" + 
		  		"        </div>\n" + 
		  		"      </div>\n" + 
		  		"    </div>    <div style=\"background-color:#CCD7D9;\">\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #CCD7D9;\" class=\"block-grid \">\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#CCD7D9;\">\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:#CCD7D9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:#CCD7D9;\"><![endif]-->\n" + 
		  		"\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid #5ACEE1; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid #5ACEE1; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" + 
		  		"\n" + 
		  		"                  \n" + 
		  		"                    &#160;\n" + 
		  		"                  \n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" + 
		  		"              </div>\n" + 
		  		"            </div>\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" + 
		  		"        </div>\n" + 
		  		"      </div>\n" + 
		  		"    </div>   <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" + 
		  		"		</td>\n" + 
		  		"  </tr>\n" + 
		  		"  </tbody>\n" + 
		  		"  </table>\n" + 
		  		"  <!--[if (mso)|(IE)]></div><![endif]-->\n" + 
		  		"\n" + 
		  		"\n" + 
		  		"</body></html>");
		  
		  msg.setContent(mailContent.toString(),"text/html; charset=utf-8");

		  Transport.send(msg);
		} catch (Exception e) {
			return new WSResponse(e.getMessage(), WSResponse.STATUS_FAIL);
		}
		
		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}
	
	@RequestMapping(value = "/api/emcenter/send", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse sendEmCenterEmail(HttpServletRequest request) {

		EmCenterDAO serve = new EmCenterDAO();
		EmCenter o = serve.findById(Long.parseLong(request.getParameter("id")));
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress(FROM_ADDRESS, "LifeGuard Admin"));
		  msg.addRecipient(Message.RecipientType.TO,
		                   new InternetAddress(request.getParameter("email"), "Customer"));
		  msg.setSubject("Your Emergency Service Center has been registered, join our Lifeguard team");
		  StringBuffer mailContent = new StringBuffer();
		  mailContent.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\r\n" + 
		  		"    <!--[if gte mso 9]><xml>\r\n" + 
		  		"     <o:OfficeDocumentSettings>\r\n" + 
		  		"      <o:AllowPNG/>\r\n" + 
		  		"      <o:PixelsPerInch>96</o:PixelsPerInch>\r\n" + 
		  		"     </o:OfficeDocumentSettings>\r\n" + 
		  		"    </xml><![endif]-->\r\n" + 
		  		"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n" + 
		  		"    <meta name=\"viewport\" content=\"width=device-width\">\r\n" + 
		  		"    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n" + 
		  		"    <title></title>\r\n" + 
		  		"    <!--[if !mso]><!-- -->\r\n" + 
		  		"	<link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\">\r\n" + 
		  		"	<!--<![endif]-->\r\n" + 
		  		"    \r\n" + 
		  		"    <style type=\"text/css\" id=\"media-query\">\r\n" + 
		  		"      body {\r\n" + 
		  		"  margin: 0;\r\n" + 
		  		"  padding: 0; }\r\n" + 
		  		"\r\n" + 
		  		"table, tr, td {\r\n" + 
		  		"  vertical-align: top;\r\n" + 
		  		"  border-collapse: collapse; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser table, .mso-container table {\r\n" + 
		  		"  table-layout: fixed; }\r\n" + 
		  		"\r\n" + 
		  		"* {\r\n" + 
		  		"  line-height: inherit; }\r\n" + 
		  		"\r\n" + 
		  		"a[x-apple-data-detectors=true] {\r\n" + 
		  		"  color: inherit !important;\r\n" + 
		  		"  text-decoration: none !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .img-container div, [owa] .img-container button {\r\n" + 
		  		"  display: block !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .fullwidth button {\r\n" + 
		  		"  width: 100% !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .block-grid .col {\r\n" + 
		  		"  display: table-cell;\r\n" + 
		  		"  float: none !important;\r\n" + 
		  		"  vertical-align: top; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {\r\n" + 
		  		"  width: 500px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {\r\n" + 
		  		"  line-height: 100%; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {\r\n" + 
		  		"  width: 164px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {\r\n" + 
		  		"  width: 328px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {\r\n" + 
		  		"  width: 250px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {\r\n" + 
		  		"  width: 166px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {\r\n" + 
		  		"  width: 125px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {\r\n" + 
		  		"  width: 100px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {\r\n" + 
		  		"  width: 83px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {\r\n" + 
		  		"  width: 71px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {\r\n" + 
		  		"  width: 62px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {\r\n" + 
		  		"  width: 55px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {\r\n" + 
		  		"  width: 50px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {\r\n" + 
		  		"  width: 45px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {\r\n" + 
		  		"  width: 41px !important; }\r\n" + 
		  		"\r\n" + 
		  		"@media only screen and (min-width: 520px) {\r\n" + 
		  		"  .block-grid {\r\n" + 
		  		"    width: 500px !important; }\r\n" + 
		  		"  .block-grid .col {\r\n" + 
		  		"    vertical-align: top; }\r\n" + 
		  		"    .block-grid .col.num12 {\r\n" + 
		  		"      width: 500px !important; }\r\n" + 
		  		"  .block-grid.mixed-two-up .col.num4 {\r\n" + 
		  		"    width: 164px !important; }\r\n" + 
		  		"  .block-grid.mixed-two-up .col.num8 {\r\n" + 
		  		"    width: 328px !important; }\r\n" + 
		  		"  .block-grid.two-up .col {\r\n" + 
		  		"    width: 250px !important; }\r\n" + 
		  		"  .block-grid.three-up .col {\r\n" + 
		  		"    width: 166px !important; }\r\n" + 
		  		"  .block-grid.four-up .col {\r\n" + 
		  		"    width: 125px !important; }\r\n" + 
		  		"  .block-grid.five-up .col {\r\n" + 
		  		"    width: 100px !important; }\r\n" + 
		  		"  .block-grid.six-up .col {\r\n" + 
		  		"    width: 83px !important; }\r\n" + 
		  		"  .block-grid.seven-up .col {\r\n" + 
		  		"    width: 71px !important; }\r\n" + 
		  		"  .block-grid.eight-up .col {\r\n" + 
		  		"    width: 62px !important; }\r\n" + 
		  		"  .block-grid.nine-up .col {\r\n" + 
		  		"    width: 55px !important; }\r\n" + 
		  		"  .block-grid.ten-up .col {\r\n" + 
		  		"    width: 50px !important; }\r\n" + 
		  		"  .block-grid.eleven-up .col {\r\n" + 
		  		"    width: 45px !important; }\r\n" + 
		  		"  .block-grid.twelve-up .col {\r\n" + 
		  		"    width: 41px !important; } }\r\n" + 
		  		"\r\n" + 
		  		"@media (max-width: 520px) {\r\n" + 
		  		"  .block-grid, .col {\r\n" + 
		  		"    min-width: 320px !important;\r\n" + 
		  		"    max-width: 100% !important;\r\n" + 
		  		"    display: block !important; }\r\n" + 
		  		"  .block-grid {\r\n" + 
		  		"    width: calc(100% - 40px) !important; }\r\n" + 
		  		"  .col {\r\n" + 
		  		"    width: 100% !important; }\r\n" + 
		  		"    .col > div {\r\n" + 
		  		"      margin: 0 auto; }\r\n" + 
		  		"  img.fullwidth, img.fullwidthOnMobile {\r\n" + 
		  		"    max-width: 100% !important; }\r\n" + 
		  		"  .no-stack .col {\r\n" + 
		  		"    min-width: 0 !important;\r\n" + 
		  		"    display: table-cell !important; }\r\n" + 
		  		"  .no-stack.two-up .col {\r\n" + 
		  		"    width: 50% !important; }\r\n" + 
		  		"  .no-stack.mixed-two-up .col.num4 {\r\n" + 
		  		"    width: 33% !important; }\r\n" + 
		  		"  .no-stack.mixed-two-up .col.num8 {\r\n" + 
		  		"    width: 66% !important; }\r\n" + 
		  		"  .no-stack.three-up .col.num4 {\r\n" + 
		  		"    width: 33% !important; }\r\n" + 
		  		"  .no-stack.four-up .col.num3 {\r\n" + 
		  		"    width: 25% !important; } }\r\n" + 
		  		"\r\n" + 
		  		"    </style>\r\n" + 
		  		"</head>\r\n" + 
		  		"<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #FFFFFF\">\r\n" + 
		  		"  <style type=\"text/css\" id=\"media-query-bodytag\">\r\n" + 
		  		"    @media (max-width: 520px) {\r\n" + 
		  		"      .block-grid {\r\n" + 
		  		"        min-width: 320px!important;\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"        width: 100%!important;\r\n" + 
		  		"        display: block!important;\r\n" + 
		  		"      }\r\n" + 
		  		"\r\n" + 
		  		"      .col {\r\n" + 
		  		"        min-width: 320px!important;\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"        width: 100%!important;\r\n" + 
		  		"        display: block!important;\r\n" + 
		  		"      }\r\n" + 
		  		"\r\n" + 
		  		"        .col > div {\r\n" + 
		  		"          margin: 0 auto;\r\n" + 
		  		"        }\r\n" + 
		  		"\r\n" + 
		  		"      img.fullwidth {\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"      }\r\n" + 
		  		"			img.fullwidthOnMobile {\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"      }\r\n" + 
		  		"      .no-stack .col {\r\n" + 
		  		"				min-width: 0!important;\r\n" + 
		  		"				display: table-cell!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.two-up .col {\r\n" + 
		  		"				width: 50%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.mixed-two-up .col.num4 {\r\n" + 
		  		"				width: 33%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.mixed-two-up .col.num8 {\r\n" + 
		  		"				width: 66%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.three-up .col.num4 {\r\n" + 
		  		"				width: 33%!important\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.four-up .col.num3 {\r\n" + 
		  		"				width: 25%!important\r\n" + 
		  		"			}\r\n" + 
		  		"    }\r\n" + 
		  		"  </style>\r\n" + 
		  		"  <!--[if IE]><div class=\"ie-browser\"><![endif]-->\r\n" + 
		  		"  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n" + 
		  		"  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #FFFFFF;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
		  		"	<tbody>\r\n" + 
		  		"	<tr style=\"vertical-align: top\">\r\n" + 
		  		"		<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #FFFFFF;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"    <div style=\"background-color:transparent;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth  fullwidth\" style=\"padding-right: 0px;  padding-left: 0px;\">\r\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px;\" align=\"center\"><![endif]-->\r\n" + 
		  		"<div style=\"line-height:25px;font-size:1px\">&#160;</div>  <img class=\"center  autowidth  fullwidth\" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/joinus.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 500px\" width=\"500\">\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth \" style=\"padding-right: 30px;  padding-left: 30px;\">\r\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px;\" align=\"center\"><![endif]-->\r\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div>  <img class=\"center  autowidth \" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/icon.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 192px\" width=\"192\">\r\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div><!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 30px; padding-bottom: 15px;\"><![endif]-->\r\n" + 
		  		"<div style=\"font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;line-height:120%;color:#555555; padding-right: 30px; padding-left: 30px; padding-top: 30px; padding-bottom: 15px;\">	\r\n" + 
		  		"	<div style=\"font-family:Montserrat, 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;line-height:14px;font-size:12px;color:#555555;text-align:left;\"><p style=\"margin: 0;line-height: 14px;text-align: center;font-size: 12px\"><span style=\"font-size: 18px; line-height: 21px;\"><strong>ศูนย์บริการฉุกเฉินของคุณ</strong></span></p><p style=\"margin: 0;line-height: 14px;text-align: center;font-size: 12px\"><span style=\"font-size: 18px; line-height: 21px;\"><strong>ได้รับการลงทะเบียนในระบบแล้ว</strong></span></p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\r\n" + 
		  		"<div style=\"color:#555555;line-height:120%;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\r\n" + 
		  		"	<div style=\"font-size:12px;line-height:14px;color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">ชื่อ <span style=\"font-size: 16px; line-height: 19px;\"><strong>"+o.getName()+"</strong></span></p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">รายละเอียด <span style=\"font-size: 16px; line-height: 19px;\"><strong>"+o.getDescription()+"</strong></span></p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;\"><![endif]-->\r\n" + 
		  		"<div style=\"font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;line-height:120%;color:#5ACEE1; padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;\">	\r\n" + 
		  		"	<div style=\"line-height:14px;font-size:12px;color:#5ACEE1;font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 18px; line-height: 21px;\"><strong><span style=\"line-height: 21px; font-size: 18px;\">โปรดลงทะเบียนผู้ใช้งาน</span></strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 18px; line-height: 21px;\"><strong><span style=\"line-height: 21px; font-size: 18px;\">ผ่านแอพพลิเคชั่น Lifeguard&#160;</span></strong></span><span style=\"font-size: 18px; line-height: 21px;\"><strong>ด้วยรหัสศูนย์&#160;</strong></span><span style=\"font-size: 18px; line-height: 21px;\"></span></p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\r\n" + 
		  		"<div style=\"color:#555555;line-height:120%;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\r\n" + 
		  		"	<div style=\"font-size:12px;line-height:14px;color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 26px; line-height: 31px;\"><strong>"+o.getSecurityCode()+"</strong></span></p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth \" style=\"padding-right: 30px;  padding-left: 30px;\">\r\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px;\" align=\"center\"><![endif]-->\r\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div>  <img class=\"center  autowidth \" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/appstores-640.jpg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 200px\" width=\"200\">\r\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div><!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>    <div style=\"background-color:#5ACEE1;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:#5ACEE1;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    <div style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\">\r\n" + 
		  		"  <!--[if (mso)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px;padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\"><table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td><![endif]-->\r\n" + 
		  		"  <div align=\"center\"><div style=\"border-top: 0px solid transparent; width:100%; line-height:0px; height:0px; font-size:0px;\">&#160;</div></div>\r\n" + 
		  		"  <!--[if (mso)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>    <div style=\"background-color:#CCD7D9;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #CCD7D9;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#CCD7D9;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:#CCD7D9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:#CCD7D9;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid #5ACEE1; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid #5ACEE1; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    &#160;\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>   <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
		  		"		</td>\r\n" + 
		  		"  </tr>\r\n" + 
		  		"  </tbody>\r\n" + 
		  		"  </table>\r\n" + 
		  		"  <!--[if (mso)|(IE)]></div><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"\r\n" + 
		  		"</body></html>");
		  msg.setContent(mailContent.toString(),"text/html; charset=utf-8");

		  Transport.send(msg);
		} catch (Exception e) {
			return new WSResponse(e.getMessage(), WSResponse.STATUS_FAIL);
		}
		  
		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}

	public WSResponse sendUserRegistrationEmail(User user) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress(FROM_ADDRESS, "LifeGuard Admin"));
		  msg.addRecipient(Message.RecipientType.TO,
		                   new InternetAddress(user.getEmail(), "Customer"));
		  msg.setSubject("Your user account has been registered, Welcome to our Lifeguard family");
		  StringBuffer mailContent = new StringBuffer();
		  mailContent.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\r\n" + 
		  		"    <!--[if gte mso 9]><xml>\r\n" + 
		  		"     <o:OfficeDocumentSettings>\r\n" + 
		  		"      <o:AllowPNG/>\r\n" + 
		  		"      <o:PixelsPerInch>96</o:PixelsPerInch>\r\n" + 
		  		"     </o:OfficeDocumentSettings>\r\n" + 
		  		"    </xml><![endif]-->\r\n" + 
		  		"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n" + 
		  		"    <meta name=\"viewport\" content=\"width=device-width\">\r\n" + 
		  		"    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n" + 
		  		"    <title></title>\r\n" + 
		  		"    <!--[if !mso]><!-- -->\r\n" + 
		  		"	<link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\">\r\n" + 
		  		"	<!--<![endif]-->\r\n" + 
		  		"    \r\n" + 
		  		"    <style type=\"text/css\" id=\"media-query\">\r\n" + 
		  		"      body {\r\n" + 
		  		"  margin: 0;\r\n" + 
		  		"  padding: 0; }\r\n" + 
		  		"\r\n" + 
		  		"table, tr, td {\r\n" + 
		  		"  vertical-align: top;\r\n" + 
		  		"  border-collapse: collapse; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser table, .mso-container table {\r\n" + 
		  		"  table-layout: fixed; }\r\n" + 
		  		"\r\n" + 
		  		"* {\r\n" + 
		  		"  line-height: inherit; }\r\n" + 
		  		"\r\n" + 
		  		"a[x-apple-data-detectors=true] {\r\n" + 
		  		"  color: inherit !important;\r\n" + 
		  		"  text-decoration: none !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .img-container div, [owa] .img-container button {\r\n" + 
		  		"  display: block !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .fullwidth button {\r\n" + 
		  		"  width: 100% !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .block-grid .col {\r\n" + 
		  		"  display: table-cell;\r\n" + 
		  		"  float: none !important;\r\n" + 
		  		"  vertical-align: top; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {\r\n" + 
		  		"  width: 500px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {\r\n" + 
		  		"  line-height: 100%; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {\r\n" + 
		  		"  width: 164px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {\r\n" + 
		  		"  width: 328px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {\r\n" + 
		  		"  width: 250px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {\r\n" + 
		  		"  width: 166px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {\r\n" + 
		  		"  width: 125px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {\r\n" + 
		  		"  width: 100px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {\r\n" + 
		  		"  width: 83px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {\r\n" + 
		  		"  width: 71px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {\r\n" + 
		  		"  width: 62px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {\r\n" + 
		  		"  width: 55px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {\r\n" + 
		  		"  width: 50px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {\r\n" + 
		  		"  width: 45px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {\r\n" + 
		  		"  width: 41px !important; }\r\n" + 
		  		"\r\n" + 
		  		"@media only screen and (min-width: 520px) {\r\n" + 
		  		"  .block-grid {\r\n" + 
		  		"    width: 500px !important; }\r\n" + 
		  		"  .block-grid .col {\r\n" + 
		  		"    vertical-align: top; }\r\n" + 
		  		"    .block-grid .col.num12 {\r\n" + 
		  		"      width: 500px !important; }\r\n" + 
		  		"  .block-grid.mixed-two-up .col.num4 {\r\n" + 
		  		"    width: 164px !important; }\r\n" + 
		  		"  .block-grid.mixed-two-up .col.num8 {\r\n" + 
		  		"    width: 328px !important; }\r\n" + 
		  		"  .block-grid.two-up .col {\r\n" + 
		  		"    width: 250px !important; }\r\n" + 
		  		"  .block-grid.three-up .col {\r\n" + 
		  		"    width: 166px !important; }\r\n" + 
		  		"  .block-grid.four-up .col {\r\n" + 
		  		"    width: 125px !important; }\r\n" + 
		  		"  .block-grid.five-up .col {\r\n" + 
		  		"    width: 100px !important; }\r\n" + 
		  		"  .block-grid.six-up .col {\r\n" + 
		  		"    width: 83px !important; }\r\n" + 
		  		"  .block-grid.seven-up .col {\r\n" + 
		  		"    width: 71px !important; }\r\n" + 
		  		"  .block-grid.eight-up .col {\r\n" + 
		  		"    width: 62px !important; }\r\n" + 
		  		"  .block-grid.nine-up .col {\r\n" + 
		  		"    width: 55px !important; }\r\n" + 
		  		"  .block-grid.ten-up .col {\r\n" + 
		  		"    width: 50px !important; }\r\n" + 
		  		"  .block-grid.eleven-up .col {\r\n" + 
		  		"    width: 45px !important; }\r\n" + 
		  		"  .block-grid.twelve-up .col {\r\n" + 
		  		"    width: 41px !important; } }\r\n" + 
		  		"\r\n" + 
		  		"@media (max-width: 520px) {\r\n" + 
		  		"  .block-grid, .col {\r\n" + 
		  		"    min-width: 320px !important;\r\n" + 
		  		"    max-width: 100% !important;\r\n" + 
		  		"    display: block !important; }\r\n" + 
		  		"  .block-grid {\r\n" + 
		  		"    width: calc(100% - 40px) !important; }\r\n" + 
		  		"  .col {\r\n" + 
		  		"    width: 100% !important; }\r\n" + 
		  		"    .col > div {\r\n" + 
		  		"      margin: 0 auto; }\r\n" + 
		  		"  img.fullwidth, img.fullwidthOnMobile {\r\n" + 
		  		"    max-width: 100% !important; }\r\n" + 
		  		"  .no-stack .col {\r\n" + 
		  		"    min-width: 0 !important;\r\n" + 
		  		"    display: table-cell !important; }\r\n" + 
		  		"  .no-stack.two-up .col {\r\n" + 
		  		"    width: 50% !important; }\r\n" + 
		  		"  .no-stack.mixed-two-up .col.num4 {\r\n" + 
		  		"    width: 33% !important; }\r\n" + 
		  		"  .no-stack.mixed-two-up .col.num8 {\r\n" + 
		  		"    width: 66% !important; }\r\n" + 
		  		"  .no-stack.three-up .col.num4 {\r\n" + 
		  		"    width: 33% !important; }\r\n" + 
		  		"  .no-stack.four-up .col.num3 {\r\n" + 
		  		"    width: 25% !important; } }\r\n" + 
		  		"\r\n" + 
		  		"    </style>\r\n" + 
		  		"</head>\r\n" + 
		  		"<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #FFFFFF\">\r\n" + 
		  		"  <style type=\"text/css\" id=\"media-query-bodytag\">\r\n" + 
		  		"    @media (max-width: 520px) {\r\n" + 
		  		"      .block-grid {\r\n" + 
		  		"        min-width: 320px!important;\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"        width: 100%!important;\r\n" + 
		  		"        display: block!important;\r\n" + 
		  		"      }\r\n" + 
		  		"\r\n" + 
		  		"      .col {\r\n" + 
		  		"        min-width: 320px!important;\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"        width: 100%!important;\r\n" + 
		  		"        display: block!important;\r\n" + 
		  		"      }\r\n" + 
		  		"\r\n" + 
		  		"        .col > div {\r\n" + 
		  		"          margin: 0 auto;\r\n" + 
		  		"        }\r\n" + 
		  		"\r\n" + 
		  		"      img.fullwidth {\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"      }\r\n" + 
		  		"			img.fullwidthOnMobile {\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"      }\r\n" + 
		  		"      .no-stack .col {\r\n" + 
		  		"				min-width: 0!important;\r\n" + 
		  		"				display: table-cell!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.two-up .col {\r\n" + 
		  		"				width: 50%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.mixed-two-up .col.num4 {\r\n" + 
		  		"				width: 33%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.mixed-two-up .col.num8 {\r\n" + 
		  		"				width: 66%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.three-up .col.num4 {\r\n" + 
		  		"				width: 33%!important\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.four-up .col.num3 {\r\n" + 
		  		"				width: 25%!important\r\n" + 
		  		"			}\r\n" + 
		  		"    }\r\n" + 
		  		"  </style>\r\n" + 
		  		"  <!--[if IE]><div class=\"ie-browser\"><![endif]-->\r\n" + 
		  		"  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n" + 
		  		"  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #FFFFFF;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
		  		"	<tbody>\r\n" + 
		  		"	<tr style=\"vertical-align: top\">\r\n" + 
		  		"		<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #FFFFFF;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"    <div style=\"background-color:transparent;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth \" style=\"padding-right: 0px;  padding-left: 0px;\">\r\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px;\" align=\"center\"><![endif]-->\r\n" + 
		  		"<div style=\"line-height:25px;font-size:1px\">&#160;</div>  <img class=\"center  autowidth \" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/icon.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 192px\" width=\"192\">\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth  fullwidth\" style=\"padding-right: 30px;  padding-left: 30px;\">\r\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px;\" align=\"center\"><![endif]-->\r\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div>  <img class=\"center  autowidth  fullwidth\" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/welcome.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 440px\" width=\"440\">\r\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div><!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 30px; padding-bottom: 15px;\"><![endif]-->\r\n" + 
		  		"<div style=\"font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;line-height:120%;color:#555555; padding-right: 30px; padding-left: 30px; padding-top: 30px; padding-bottom: 15px;\">	\r\n" + 
		  		"	<div style=\"font-family:Montserrat, 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;font-size:12px;line-height:14px;color:#555555;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 18px; line-height: 21px;\"><strong>การลงทะเบียนใช้งานของคุณเสร็จสมบูรณ์</strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><br></p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;\"><![endif]-->\r\n" + 
		  		"<div style=\"font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;line-height:120%;color:#5ACEE1; padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;\">	\r\n" + 
		  		"	<div style=\"line-height:14px;font-size:12px;color:#5ACEE1;font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;text-align:left;\"><p style=\"margin: 0;line-height: 14px;text-align: center;font-size: 12px\"><span style=\"font-size: 18px; line-height: 21px;\"><b>กรุณาตรวจสอบข้อมูลผู้ใช้ด้านล่าง</b></span></p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 30px;\"><![endif]-->\r\n" + 
		  		"<div style=\"font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;color:#555555; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 30px;\">	\r\n" + 
		  		"	<div style=\"font-size:12px;line-height:14px;color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\"><strong>ชื่อ-นามสกุล</strong> : "+user.getFirstname() + " "+ user.getLastname()+"</p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">&#160;<strong>ชื่อผู้ใช้งาน</strong> (Username): "+user.getUsername()+"</p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\"><strong>รหัสผ่าน</strong> (Password):&#160; "+user.getPassword()+"</p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>    <div style=\"background-color:#5ACEE1;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:#5ACEE1;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    <div style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\">\r\n" + 
		  		"  <!--[if (mso)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px;padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\"><table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td><![endif]-->\r\n" + 
		  		"  <div align=\"center\"><div style=\"border-top: 0px solid transparent; width:100%; line-height:0px; height:0px; font-size:0px;\">&#160;</div></div>\r\n" + 
		  		"  <!--[if (mso)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>    <div style=\"background-color:#CCD7D9;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #CCD7D9;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#CCD7D9;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:#CCD7D9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:#CCD7D9;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid #5ACEE1; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid #5ACEE1; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    \r\n" + 
		  		"<div align=\"center\" style=\"padding-right: 25px; padding-left: 25px; padding-bottom: 25px;\">\r\n" + 
		  		"  <div style=\"line-height:25px;font-size:1px\">&#160;</div>\r\n" + 
		  		"  <div style=\"display: table; max-width:191px;\">\r\n" + 
		  		"  <!--[if (mso)|(IE)]><table width=\"141\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"border-collapse:collapse; padding-right: 25px; padding-left: 25px; padding-bottom: 25px;\"  align=\"center\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; mso-table-lspace: 0pt;mso-table-rspace: 0pt; width:141px;\"><tr><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;Margin-right: 10px\">\r\n" + 
		  		"      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"        <a href=\"https://www.facebook.com/\" title=\"Facebook\" target=\"_blank\">\r\n" + 
		  		"          <img src=\"https://storage.googleapis.com/web-email-resources/facebook@2x.png\" alt=\"Facebook\" title=\"Facebook\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
		  		"        </a>\r\n" + 
		  		"      <div style=\"line-height:5px;font-size:1px\">&#160;</div>\r\n" + 
		  		"      </td></tr>\r\n" + 
		  		"    </tbody></table>\r\n" + 
		  		"      <!--[if (mso)|(IE)]></td><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;Margin-right: 10px\">\r\n" + 
		  		"      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"        <a href=\"https://twitter.com/\" title=\"Twitter\" target=\"_blank\">\r\n" + 
		  		"          <img src=\"https://storage.googleapis.com/web-email-resources/twitter@2x.png\" alt=\"Twitter\" title=\"Twitter\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
		  		"        </a>\r\n" + 
		  		"      <div style=\"line-height:5px;font-size:1px\">&#160;</div>\r\n" + 
		  		"      </td></tr>\r\n" + 
		  		"    </tbody></table>\r\n" + 
		  		"      <!--[if (mso)|(IE)]></td><td width=\"32\" style=\"width:32px; padding-right: 0;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;Margin-right: 0\">\r\n" + 
		  		"      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"        <a href=\"https://plus.google.com/\" title=\"Google+\" target=\"_blank\">\r\n" + 
		  		"          <img src=\"https://storage.googleapis.com/web-email-resources/googleplus@2x.png\" alt=\"Google+\" title=\"Google+\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
		  		"        </a>\r\n" + 
		  		"      <div style=\"line-height:5px;font-size:1px\">&#160;</div>\r\n" + 
		  		"      </td></tr>\r\n" + 
		  		"    </tbody></table>\r\n" + 
		  		"    <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"  </div>\r\n" + 
		  		"</div>\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>   <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
		  		"		</td>\r\n" + 
		  		"  </tr>\r\n" + 
		  		"  </tbody>\r\n" + 
		  		"  </table>\r\n" + 
		  		"  <!--[if (mso)|(IE)]></div><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"\r\n" + 
		  		"</body></html>");
		  msg.setContent(mailContent.toString(),"text/html; charset=utf-8");

		  Transport.send(msg);
		} catch (Exception e) {
			return new WSResponse(e.getMessage(), WSResponse.STATUS_FAIL);
		}
		  
		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}
	
	public WSResponse sendResetPasswordEmail(User user) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress(FROM_ADDRESS, "LifeGuard Admin"));
		  msg.addRecipient(Message.RecipientType.TO,
		                   new InternetAddress(user.getEmail(), "Customer"));
		  msg.setSubject("Your password has been reset");
		  StringBuffer mailContent = new StringBuffer();
		  mailContent.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\r\n" + 
		  		"    <!--[if gte mso 9]><xml>\r\n" + 
		  		"     <o:OfficeDocumentSettings>\r\n" + 
		  		"      <o:AllowPNG/>\r\n" + 
		  		"      <o:PixelsPerInch>96</o:PixelsPerInch>\r\n" + 
		  		"     </o:OfficeDocumentSettings>\r\n" + 
		  		"    </xml><![endif]-->\r\n" + 
		  		"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n" + 
		  		"    <meta name=\"viewport\" content=\"width=device-width\">\r\n" + 
		  		"    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\r\n" + 
		  		"    <title></title>\r\n" + 
		  		"    <!--[if !mso]><!-- -->\r\n" + 
		  		"	<link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\">\r\n" + 
		  		"	<!--<![endif]-->\r\n" + 
		  		"    \r\n" + 
		  		"    <style type=\"text/css\" id=\"media-query\">\r\n" + 
		  		"      body {\r\n" + 
		  		"  margin: 0;\r\n" + 
		  		"  padding: 0; }\r\n" + 
		  		"\r\n" + 
		  		"table, tr, td {\r\n" + 
		  		"  vertical-align: top;\r\n" + 
		  		"  border-collapse: collapse; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser table, .mso-container table {\r\n" + 
		  		"  table-layout: fixed; }\r\n" + 
		  		"\r\n" + 
		  		"* {\r\n" + 
		  		"  line-height: inherit; }\r\n" + 
		  		"\r\n" + 
		  		"a[x-apple-data-detectors=true] {\r\n" + 
		  		"  color: inherit !important;\r\n" + 
		  		"  text-decoration: none !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .img-container div, [owa] .img-container button {\r\n" + 
		  		"  display: block !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .fullwidth button {\r\n" + 
		  		"  width: 100% !important; }\r\n" + 
		  		"\r\n" + 
		  		"[owa] .block-grid .col {\r\n" + 
		  		"  display: table-cell;\r\n" + 
		  		"  float: none !important;\r\n" + 
		  		"  vertical-align: top; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {\r\n" + 
		  		"  width: 500px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {\r\n" + 
		  		"  line-height: 100%; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {\r\n" + 
		  		"  width: 164px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {\r\n" + 
		  		"  width: 328px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {\r\n" + 
		  		"  width: 250px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {\r\n" + 
		  		"  width: 166px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {\r\n" + 
		  		"  width: 125px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {\r\n" + 
		  		"  width: 100px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {\r\n" + 
		  		"  width: 83px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {\r\n" + 
		  		"  width: 71px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {\r\n" + 
		  		"  width: 62px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {\r\n" + 
		  		"  width: 55px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {\r\n" + 
		  		"  width: 50px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {\r\n" + 
		  		"  width: 45px !important; }\r\n" + 
		  		"\r\n" + 
		  		".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {\r\n" + 
		  		"  width: 41px !important; }\r\n" + 
		  		"\r\n" + 
		  		"@media only screen and (min-width: 520px) {\r\n" + 
		  		"  .block-grid {\r\n" + 
		  		"    width: 500px !important; }\r\n" + 
		  		"  .block-grid .col {\r\n" + 
		  		"    vertical-align: top; }\r\n" + 
		  		"    .block-grid .col.num12 {\r\n" + 
		  		"      width: 500px !important; }\r\n" + 
		  		"  .block-grid.mixed-two-up .col.num4 {\r\n" + 
		  		"    width: 164px !important; }\r\n" + 
		  		"  .block-grid.mixed-two-up .col.num8 {\r\n" + 
		  		"    width: 328px !important; }\r\n" + 
		  		"  .block-grid.two-up .col {\r\n" + 
		  		"    width: 250px !important; }\r\n" + 
		  		"  .block-grid.three-up .col {\r\n" + 
		  		"    width: 166px !important; }\r\n" + 
		  		"  .block-grid.four-up .col {\r\n" + 
		  		"    width: 125px !important; }\r\n" + 
		  		"  .block-grid.five-up .col {\r\n" + 
		  		"    width: 100px !important; }\r\n" + 
		  		"  .block-grid.six-up .col {\r\n" + 
		  		"    width: 83px !important; }\r\n" + 
		  		"  .block-grid.seven-up .col {\r\n" + 
		  		"    width: 71px !important; }\r\n" + 
		  		"  .block-grid.eight-up .col {\r\n" + 
		  		"    width: 62px !important; }\r\n" + 
		  		"  .block-grid.nine-up .col {\r\n" + 
		  		"    width: 55px !important; }\r\n" + 
		  		"  .block-grid.ten-up .col {\r\n" + 
		  		"    width: 50px !important; }\r\n" + 
		  		"  .block-grid.eleven-up .col {\r\n" + 
		  		"    width: 45px !important; }\r\n" + 
		  		"  .block-grid.twelve-up .col {\r\n" + 
		  		"    width: 41px !important; } }\r\n" + 
		  		"\r\n" + 
		  		"@media (max-width: 520px) {\r\n" + 
		  		"  .block-grid, .col {\r\n" + 
		  		"    min-width: 320px !important;\r\n" + 
		  		"    max-width: 100% !important;\r\n" + 
		  		"    display: block !important; }\r\n" + 
		  		"  .block-grid {\r\n" + 
		  		"    width: calc(100% - 40px) !important; }\r\n" + 
		  		"  .col {\r\n" + 
		  		"    width: 100% !important; }\r\n" + 
		  		"    .col > div {\r\n" + 
		  		"      margin: 0 auto; }\r\n" + 
		  		"  img.fullwidth, img.fullwidthOnMobile {\r\n" + 
		  		"    max-width: 100% !important; }\r\n" + 
		  		"  .no-stack .col {\r\n" + 
		  		"    min-width: 0 !important;\r\n" + 
		  		"    display: table-cell !important; }\r\n" + 
		  		"  .no-stack.two-up .col {\r\n" + 
		  		"    width: 50% !important; }\r\n" + 
		  		"  .no-stack.mixed-two-up .col.num4 {\r\n" + 
		  		"    width: 33% !important; }\r\n" + 
		  		"  .no-stack.mixed-two-up .col.num8 {\r\n" + 
		  		"    width: 66% !important; }\r\n" + 
		  		"  .no-stack.three-up .col.num4 {\r\n" + 
		  		"    width: 33% !important; }\r\n" + 
		  		"  .no-stack.four-up .col.num3 {\r\n" + 
		  		"    width: 25% !important; } }\r\n" + 
		  		"\r\n" + 
		  		"    </style>\r\n" + 
		  		"</head>\r\n" + 
		  		"<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #FFFFFF\">\r\n" + 
		  		"  <style type=\"text/css\" id=\"media-query-bodytag\">\r\n" + 
		  		"    @media (max-width: 520px) {\r\n" + 
		  		"      .block-grid {\r\n" + 
		  		"        min-width: 320px!important;\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"        width: 100%!important;\r\n" + 
		  		"        display: block!important;\r\n" + 
		  		"      }\r\n" + 
		  		"\r\n" + 
		  		"      .col {\r\n" + 
		  		"        min-width: 320px!important;\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"        width: 100%!important;\r\n" + 
		  		"        display: block!important;\r\n" + 
		  		"      }\r\n" + 
		  		"\r\n" + 
		  		"        .col > div {\r\n" + 
		  		"          margin: 0 auto;\r\n" + 
		  		"        }\r\n" + 
		  		"\r\n" + 
		  		"      img.fullwidth {\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"      }\r\n" + 
		  		"			img.fullwidthOnMobile {\r\n" + 
		  		"        max-width: 100%!important;\r\n" + 
		  		"      }\r\n" + 
		  		"      .no-stack .col {\r\n" + 
		  		"				min-width: 0!important;\r\n" + 
		  		"				display: table-cell!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.two-up .col {\r\n" + 
		  		"				width: 50%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.mixed-two-up .col.num4 {\r\n" + 
		  		"				width: 33%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.mixed-two-up .col.num8 {\r\n" + 
		  		"				width: 66%!important;\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.three-up .col.num4 {\r\n" + 
		  		"				width: 33%!important\r\n" + 
		  		"			}\r\n" + 
		  		"			.no-stack.four-up .col.num3 {\r\n" + 
		  		"				width: 25%!important\r\n" + 
		  		"			}\r\n" + 
		  		"    }\r\n" + 
		  		"  </style>\r\n" + 
		  		"  <!--[if IE]><div class=\"ie-browser\"><![endif]-->\r\n" + 
		  		"  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n" + 
		  		"  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #FFFFFF;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
		  		"	<tbody>\r\n" + 
		  		"	<tr style=\"vertical-align: top\">\r\n" + 
		  		"		<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #FFFFFF;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"    <div style=\"background-color:transparent;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth \" style=\"padding-right: 0px;  padding-left: 0px;\">\r\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px;\" align=\"center\"><![endif]-->\r\n" + 
		  		"<div style=\"line-height:25px;font-size:1px\">&#160;</div>  <img class=\"center  autowidth \" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/icon.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 192px\" width=\"192\">\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <div align=\"center\" class=\"img-container center  autowidth  fullwidth\" style=\"padding-right: 30px;  padding-left: 30px;\">\r\n" + 
		  		"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px;\" align=\"center\"><![endif]-->\r\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div>  <img class=\"center  autowidth  fullwidth\" align=\"center\" border=\"0\" src=\"https://storage.googleapis.com/web-email-resources/thanks.jpg\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 440px\" width=\"440\">\r\n" + 
		  		"<div style=\"line-height:30px;font-size:1px\">&#160;</div><!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 30px; padding-bottom: 15px;\"><![endif]-->\r\n" + 
		  		"<div style=\"font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;line-height:120%;color:#555555; padding-right: 30px; padding-left: 30px; padding-top: 30px; padding-bottom: 15px;\">	\r\n" + 
		  		"	<div style=\"font-family:Montserrat, 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;font-size:12px;line-height:14px;color:#555555;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><span style=\"font-size: 18px; line-height: 21px;\"><strong>ระบบได้ทำการตั้งรหัสใหม่เสร็จสมบูรณ์</strong></span></p><p style=\"margin: 0;font-size: 12px;line-height: 14px;text-align: center\"><br></p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;\"><![endif]-->\r\n" + 
		  		"<div style=\"font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;line-height:120%;color:#5ACEE1; padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 5px;\">	\r\n" + 
		  		"	<div style=\"line-height:14px;font-size:12px;color:#5ACEE1;font-family:'Montserrat', 'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;text-align:left;\"><p style=\"margin: 0;line-height: 14px;text-align: center;font-size: 12px\"><span style=\"font-size: 18px; line-height: 21px;\"><b>กรุณาตรวจสอบข้อมูลผู้ใช้ด้านล่าง</b></span></p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                  \r\n" + 
		  		"                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 30px;\"><![endif]-->\r\n" + 
		  		"<div style=\"font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;color:#555555; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 30px;\">	\r\n" + 
		  		"	<div style=\"font-size:12px;line-height:14px;color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\"><strong>ชื่อ-นามสกุล</strong> : "+user.getFirstname() + " "+ user.getLastname()+"</p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\">&#160;<strong>ชื่อผู้ใช้งาน</strong> (Username): "+user.getUsername()+"</p><p style=\"margin: 0;font-size: 14px;line-height: 17px;text-align: center\"><strong>รหัสผ่าน</strong> (Password):&#160; "+user.getPassword()+"</p></div>	\r\n" + 
		  		"</div>\r\n" + 
		  		"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>    <div style=\"background-color:#5ACEE1;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:#5ACEE1;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    <div style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\">\r\n" + 
		  		"  <!--[if (mso)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px;padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\"><table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td><![endif]-->\r\n" + 
		  		"  <div align=\"center\"><div style=\"border-top: 0px solid transparent; width:100%; line-height:0px; height:0px; font-size:0px;\">&#160;</div></div>\r\n" + 
		  		"  <!--[if (mso)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"</div>\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>    <div style=\"background-color:#CCD7D9;\">\r\n" + 
		  		"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #CCD7D9;\" class=\"block-grid \">\r\n" + 
		  		"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#CCD7D9;\">\r\n" + 
		  		"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:#CCD7D9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:#CCD7D9;\"><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid #5ACEE1; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\r\n" + 
		  		"              <div style=\"background-color: transparent; width: 100% !important;\">\r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid #5ACEE1; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"                  \r\n" + 
		  		"                    \r\n" + 
		  		"<div align=\"center\" style=\"padding-right: 25px; padding-left: 25px; padding-bottom: 25px;\">\r\n" + 
		  		"  <div style=\"line-height:25px;font-size:1px\">&#160;</div>\r\n" + 
		  		"  <div style=\"display: table; max-width:191px;\">\r\n" + 
		  		"  <!--[if (mso)|(IE)]><table width=\"141\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"border-collapse:collapse; padding-right: 25px; padding-left: 25px; padding-bottom: 25px;\"  align=\"center\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; mso-table-lspace: 0pt;mso-table-rspace: 0pt; width:141px;\"><tr><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;Margin-right: 10px\">\r\n" + 
		  		"      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"        <a href=\"https://www.facebook.com/\" title=\"Facebook\" target=\"_blank\">\r\n" + 
		  		"          <img src=\"https://storage.googleapis.com/web-email-resources/facebook@2x.png\" alt=\"Facebook\" title=\"Facebook\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
		  		"        </a>\r\n" + 
		  		"      <div style=\"line-height:5px;font-size:1px\">&#160;</div>\r\n" + 
		  		"      </td></tr>\r\n" + 
		  		"    </tbody></table>\r\n" + 
		  		"      <!--[if (mso)|(IE)]></td><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;Margin-right: 10px\">\r\n" + 
		  		"      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"        <a href=\"https://twitter.com/\" title=\"Twitter\" target=\"_blank\">\r\n" + 
		  		"          <img src=\"https://storage.googleapis.com/web-email-resources/twitter@2x.png\" alt=\"Twitter\" title=\"Twitter\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
		  		"        </a>\r\n" + 
		  		"      <div style=\"line-height:5px;font-size:1px\">&#160;</div>\r\n" + 
		  		"      </td></tr>\r\n" + 
		  		"    </tbody></table>\r\n" + 
		  		"      <!--[if (mso)|(IE)]></td><td width=\"32\" style=\"width:32px; padding-right: 0;\" valign=\"top\"><![endif]-->\r\n" + 
		  		"    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;Margin-right: 0\">\r\n" + 
		  		"      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n" + 
		  		"        <a href=\"https://plus.google.com/\" title=\"Google+\" target=\"_blank\">\r\n" + 
		  		"          <img src=\"https://storage.googleapis.com/web-email-resources/googleplus@2x.png\" alt=\"Google+\" title=\"Google+\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\r\n" + 
		  		"        </a>\r\n" + 
		  		"      <div style=\"line-height:5px;font-size:1px\">&#160;</div>\r\n" + 
		  		"      </td></tr>\r\n" + 
		  		"    </tbody></table>\r\n" + 
		  		"    <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"  </div>\r\n" + 
		  		"</div>\r\n" + 
		  		"                  \r\n" + 
		  		"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\r\n" + 
		  		"              </div>\r\n" + 
		  		"            </div>\r\n" + 
		  		"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
		  		"        </div>\r\n" + 
		  		"      </div>\r\n" + 
		  		"    </div>   <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
		  		"		</td>\r\n" + 
		  		"  </tr>\r\n" + 
		  		"  </tbody>\r\n" + 
		  		"  </table>\r\n" + 
		  		"  <!--[if (mso)|(IE)]></div><![endif]-->\r\n" + 
		  		"\r\n" + 
		  		"\r\n" + 
		  		"</body></html>");
		  msg.setContent(mailContent.toString(),"text/html; charset=utf-8");

		  Transport.send(msg);
		} catch (Exception e) {
			return new WSResponse(e.getMessage(), WSResponse.STATUS_FAIL);
		}
		  
		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}
}
