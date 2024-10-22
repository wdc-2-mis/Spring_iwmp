package app.controllers.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import app.bean.Login;
import app.bean.reports.UserFileUploadBean;
import app.bean.reports.UserUploadDetailsBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.service.reports.UserUploadDetailServices;

@Controller("UploadDetailsforSLNA")
public class UploadDetailsforSLNA {

	HttpSession session;
	@Autowired(required = true)
	MenuController menuController;

	@Autowired(required = true)
	public UserUploadDetailServices uds;
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Value("${filePath}")
	String serverFilePath;

	private Map<String, String> categoryType;

	@RequestMapping(value = "/UserUploadDetailsSLNA", method = RequestMethod.GET)
	public ModelAndView UserUploadDetailsSLNA(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		String category_type = request.getParameter("category_type");
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();

		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("user/UserUploadDetailsforSLNA");
			mav.addObject("menu", menuController.getMenuUserId(request));

			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			list = uds.getListofuploadedFileforSLNA(Integer.parseInt(stcode));
			int i = 0;
			for (UserUploadDetailsBean bean : list) 
			{
				data = new String[10];
				i++;
				data[0] = String.valueOf(i);
				data[1] = bean.getUpload();
				data[2] = bean.getSubject();
				data[7] = data[2];
				data[3] = bean.getFile_name();
				data[4] = bean.getIs_new().toString();
				data[5] = bean.getFile_full_path();
				data[6] = bean.getPublish().toString();
				data[8] = bean.getId().toString();
				data[9] = bean.getUpload_category_id().toString();

				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());

			categoryType = uds.getUploadCategoryforSLNA();
			mav.addObject("categoryType", categoryType);
			mav.addObject("category", category_type);
		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}

	
	  @RequestMapping(value="/uploadFilesub", method= RequestMethod.POST)
	  public @ResponseBody String uploadFilekd(ModelMap model, HttpServletRequest request, @RequestParam("upload") MultipartFile mfile , 
			  RedirectAttributesModelMap mp)
	  throws Exception 
	  { 
		  session = request.getSession(false); 
		  int mid=0, max_id =0; 
		  String ext="", filePath="", file_name = "", concatinate = "."; 
		  if(session!= null && session.getAttribute("loginID") != null) 
		  { 
			  try { 
				  float size = mfile.getSize(); 
				  size = size / 1024;
				  if(size/1024 > 20) 
				  { 
					  return "File size should be less than 20 MB"; 
				  } 
				  byte[] bytes = mfile.getBytes(); 
				  String s1 = new String(bytes);
				  if (s1 != null && !s1.equals(""))
					  s1 = s1.substring(0, 2);

				  if (s1.startsWith("mz") || s1.startsWith("MZ") 
						|| s1.startsWith("7f454c46") || s1.startsWith("7F454C46")
						|| s1.startsWith("cafebabe") || s1.startsWith("CAFEBABE") 
						|| s1.startsWith("feedface") || s1.startsWith("FEEDFACE")) 
				  {
						return "Please select Only .doc,.docx,.ppt,.pptx,.jpg,.jpeg,.pdf,.xls,.xlsx, file for upload!";
				  }
				  filePath=serverFilePath; 
				  String fileName = mfile.getOriginalFilename(); 
				  if(mfile.isEmpty() || fileName.isEmpty()) 
				  { 
					  return "Please upload a valid file"; 
				  } 
				  File file = new File(filePath); 
				  if (!file.exists()) 
				  { 
					  file.mkdir(); 
				  } 
				  mid =fileName.lastIndexOf("."); 
				  ext = fileName.substring(mid + 1, fileName.length()); 
				  if ((ext.compareToIgnoreCase("") == 0) || (ext.compareToIgnoreCase("doc")== 0) 
						  || (ext.compareToIgnoreCase("docx") == 0)||(ext.compareToIgnoreCase("ppt") == 0) 
						  || (ext.compareToIgnoreCase("pptx") ==0)|| (ext.compareToIgnoreCase("jpg") == 0) 
						  ||(ext.compareToIgnoreCase("jpeg") == 0)|| (ext.compareToIgnoreCase("pdf") ==0) 
						  || (ext.compareToIgnoreCase("xls") == 0) ||(ext.compareToIgnoreCase("xlsx") == 0)) 
				  { 
					  file_name = "iwmp_file_" + "00" +max_id; 
					  file_name = file_name.concat(concatinate).concat(ext); 
					  if(!file_name.equals("")) 
					  { 
						  File fileToCreate = new File(filePath, file_name);
						  if (!fileToCreate.exists()) 
						  {
						/*    FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
							  fileOutStream.write(bytes); 
							  fileOutStream.flush(); 
							  fileOutStream.close();  */
							  BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToCreate)); 
							  outputStream.write(bytes);
							  outputStream.close(); 
						  } 
					  } 
				  }
			  } 
			  catch (IOException e) 
			  { 
				  return "Exception in uploadFileSub"; 
			  }
		  } 
		  return "uploaded successfully"; 
	}
	 
	@RequestMapping(value = "/saveUserUploadDetailSLNA", method = RequestMethod.POST)
	public ModelAndView saveUserUploadDetailSLNA(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("useruploadsl") UserFileUploadBean userfileup) throws Exception {
		session = request.getSession(true);
		String category_type = request.getParameter("category_type");
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String fname="";
		int max_id=0;
		boolean userUploadSaveSLNA=false;
		String res=null;
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("user/UserUploadDetailsforSLNA");
			mav.addObject("menu", menuController.getMenuUserId(request));
			max_id=uds.getMaxId();
			// MultipartFile mf=userfileup.getTheFile();
			fname = "WDC-PMKSY_file_" + "00" + max_id;

		//	String filePath = "E:\\CircularMessageAlert\\";

			String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/CircularMessageAlert/";
			//String filePath = "/usr/local/tomcat9/webapps/TESTING/CircularMessageAlert/";

		//String filePath = "/usr/local/tomcat9/webapps/PRD/CircularMessageAlert/";
		//	String filePath = "/usr/local/tomcat9/webapps/TESTING/CircularMessageAlert/";

			// String path=session.getServletContext().getRealPath("/");  
		     //   String filename=file.getOriginalFilename();  
			// String	filePath = path+"PRD/CircularMessageAlert/";
		//	String filePath = "/TESTING/CircularMessageAlert/";
		//	String res=commonFunction.uploadFile(userfileup.getTheFile(), filePath, fname);
			//System.out.println("filePath---"+filePath);
			if(!category_type.equals("9")) 
			{
				//if(userfileup.getTheFile()!=null)
				res=commonFunction.uploadFile(userfileup.getTheFile(), filePath, fname);
			}
		    if(category_type.equals("9"))
		    	res=commonFunction.uploadOnlyPDFFile(userfileup.getTheFile(), filePath, fname);
		    

			/*
			 * String filePath = "D:\\CircularMessageAlert\\"; String
			 * res=commonFunction.uploadFile(userfileup.getTheFile(), filePath, fname);
			 * 
			 * String filePath = serverFilePath; String
			 * res=commonFunction.uploadFile(userfileup.getTheFile(), filePath, fname);
			 */

			if(res.equals("success")) 
			{
				userUploadSaveSLNA= uds.userUploadSaveSLNA(userfileup, session, filePath, fname);
				if(userUploadSaveSLNA) 
				{
					mav.addObject("messageUpload", "File Uploaded Successfully");
				}
			}
			else
				mav.addObject("messageUpload", "Upload failed, "+res);
			//Upper code is done for file upload now do coding for save data in database
			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			list = uds.getListofuploadedFileforSLNA(Integer.parseInt(stcode));
			int i = 0;
			for (UserUploadDetailsBean bean : list) {
				data = new String[10];
				i++;
				data[0] = String.valueOf(i);
				data[1] = bean.getUpload();
				data[2] = bean.getSubject();
				data[7] = data[2];
				data[3] = bean.getFile_name();
				data[4] = bean.getIs_new().toString();
				data[5] = bean.getFile_full_path();
				data[6] = bean.getPublish().toString();
				data[8] = bean.getId().toString();
				data[9] = bean.getUpload_category_id().toString();

				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());

			categoryType = uds.getUploadCategoryforSLNA();
			mav.addObject("categoryType", categoryType);
			mav.addObject("category", category_type);

		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}

	@RequestMapping(value = "/deleteUserUploadDetailSLNA", method = RequestMethod.POST)
	public ModelAndView deleteUserUploadDetailSLNA(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("useruploadsl") UserFileUploadBean userfileup) throws Exception {
		session = request.getSession(true);
		String category_type = request.getParameter("category_type");
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String id = request.getParameter("id");
		String filename = request.getParameter("filename");
		boolean userUploadDeleteSLNA=false;
		File tempFile = null;
		
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("user/UserUploadDetailsforSLNA");
			mav.addObject("menu", menuController.getMenuUserId(request));
			String filePath = serverFilePath;
			userUploadDeleteSLNA= uds.userUploadDeleteSLNA(Integer.parseInt(id), filename, filePath);
			tempFile = new File(filePath + filename);
		//	tempFile = new File(servlet.getServletContext().getInitParameter("filesPath") + "Documents" + File.separator+ filename);  // server path
			boolean success = tempFile.delete();
			if(userUploadDeleteSLNA && success) 
					mav.addObject("messageUpload", "File Deleted Successfully");
			else
					mav.addObject("messageUpload", "File not Deleted!");
			//Upper code is done for file delete 
			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			list = uds.getListofuploadedFileforSLNA(Integer.parseInt(stcode));
			int i = 0;
			for (UserUploadDetailsBean bean : list) 
			{
				data = new String[10];
				i++;
				data[0] = String.valueOf(i);
				data[1] = bean.getUpload();
				data[2] = bean.getSubject();
				data[7] = data[2];
				data[3] = bean.getFile_name();
				data[4] = bean.getIs_new().toString();
				data[5] = bean.getFile_full_path();
				data[6] = bean.getPublish().toString();
				data[8] = bean.getId().toString();
				data[9] = bean.getUpload_category_id().toString();

				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());

			categoryType = uds.getUploadCategoryforSLNA();
			mav.addObject("categoryType", categoryType);
			mav.addObject("category", category_type);

		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value = "/updateUserUploadDetailSLNA", method = RequestMethod.POST)
	public ModelAndView updateUserUploadDetailSLNA(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("useruploadsl") UserFileUploadBean userfileup) throws Exception {
		session = request.getSession(true);
		
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String category_type = request.getParameter("category_type");
		String id = request.getParameter("id");
		String newsub = request.getParameter("newsub");
		String caption_id = request.getParameter("caption_id");
		String publish_id = request.getParameter("publish_id");
		String newcat_id = request.getParameter("newcat_id");
		String newcattxt = request.getParameter("newcattxt");
		String login=session.getAttribute("loginID").toString();
		boolean userUploadUpdateSLNA=false;
		
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("user/UserUploadDetailsforSLNA");
		//	mav.addObject("menu", menuController.getMenuUserId(request));
			
			userUploadUpdateSLNA= uds.userUploadUpdateSLNA(Integer.parseInt(id), newsub, caption_id, publish_id,
					Integer.parseInt(newcat_id), newcattxt, login);
			
			if(userUploadUpdateSLNA) 
					mav.addObject("messageUpload", "Data Update Successfully");
			else
					mav.addObject("messageUpload", "Not Updated!");
			//Upper code is done for update 
			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			list = uds.getListofuploadedFileforSLNA(Integer.parseInt(stcode));
			int i = 0;
			for (UserUploadDetailsBean bean : list) 
			{
				data = new String[10];
				i++;
				data[0] = String.valueOf(i);
				data[1] = bean.getUpload();
				data[2] = bean.getSubject();
				data[7] = data[2];
				data[3] = bean.getFile_name();
				data[4] = bean.getIs_new().toString();
				data[5] = bean.getFile_full_path();
				data[6] = bean.getPublish().toString();
				data[8] = bean.getId().toString();
				data[9] = bean.getUpload_category_id().toString();

				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());

			categoryType = uds.getUploadCategoryforSLNA();
			mav.addObject("categoryType", categoryType);
			mav.addObject("category", category_type);

		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
}
