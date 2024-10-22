package app.controllers.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.reports.UserFileUploadBean;
import app.bean.reports.UserUploadDetailsBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.service.reports.UserUploadDetailServices;

@Controller("UserUplaodDetailsDL")
public class UserUplaodDetailsDL {
	
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

	@RequestMapping(value = "/userFileUplaodDetailsDL", method = RequestMethod.GET)
	public ModelAndView UserUploadDetailsSLNA(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		String category_type = request.getParameter("category_type");
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String newSubject = null;
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("user/userUplaodDetailsDL");
			mav.addObject("menu", menuController.getMenuUserId(request));

			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			list = uds.getListofuploadedFileforDL();
			int i = 0;
			for (UserUploadDetailsBean bean : list) 
			{
				data = new String[11];
				i++;
				data[0] = String.valueOf(i);// serial no
				data[1] = bean.getUpload();// category
				newSubject = bean.getSubject();// subject
				data[2] = newSubject != null ? newSubject.trim() : newSubject;
				data[7] = data[2];
				data[3] = bean.getFile_name();// file name
				data[4] = bean.getIs_new().toString();// is new
				data[5] = bean.getFile_full_path();// file path
				data[6] = bean.getPublish().toString();// publish
				data[8] = bean.getId().toString();// id
				data[9] = bean.getUpload_category_id().toString();// category upload
				data[10] = bean.getState_name();
				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());

			categoryType = uds.getUploadCategoryforDL();
			mav.addObject("categoryType", categoryType);
			mav.addObject("category", category_type);
		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}

	
	@RequestMapping(value = "/saveUserUploadDetailDL", method = RequestMethod.POST)
	public ModelAndView saveUserUploadDetailSLNA(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("useruploadsl") UserFileUploadBean userfileup) throws Exception {
		session = request.getSession(true);
		String category_type = request.getParameter("category_type");
		String vpath= request.getParameter("videopath");
		String lang = request.getParameter("language");
	//	String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String fname="";
		int max_id=0;
		boolean userUploadSave=false;
		String newSubject = null;
		String res="success";
		String filePath=null;
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("user/userUplaodDetailsDL");
			mav.addObject("menu", menuController.getMenuUserId(request));
			max_id=uds.getMaxId();
			// MultipartFile mf=userfileup.getTheFile();
			fname = "WDC-PMKSY_file_" + "00" + max_id;

			if(category_type.equals("10"))
				filePath=vpath;
			else
				filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/CircularMessageAlert";
			
			//filePath = "/usr/local/tomcat9/webapps/TESTING/CircularMessageAlert/";
			//filePath = "/TESTING/CircularMessageAlert/";
			//filePath="E:\\CircularMessageAlert\\";
			/*
			 * if(userfileup.getTheFile().getSize() > 0)
			 * res=commonFunction.uploadFile(userfileup.getTheFile(), filePath, fname);
			 */
		/*  "[^.]*\\.[^.]*\\.[^.]*"
		 * String filePath = serverFilePath; if(userfileup.getTheFile()!=null)
		 * res=commonFunction.uploadFile(userfileup.getTheFile(), filePath, fname);
		 */
			//System.out.println("filePath---"+filePath);
			
			if(!category_type.equals("9")) 
			{
				if(userfileup.getTheFile().getSize() > 0)
				res=commonFunction.uploadFile(userfileup.getTheFile(), filePath, fname);
			}
		    if(category_type.equals("9"))
		    	res=commonFunction.uploadOnlyPDFFile(userfileup.getTheFile(), filePath, fname);
		    
		    if(res.equals("success")) {
		    	userUploadSave= uds.userUploadSaveDL(userfileup, session, filePath, fname, lang);
				if(userUploadSave) 
				{
					mav.addObject("messageUpload", "Data saved Successfully");
				}
		    }
		    else {
		    	mav.addObject("messageUpload", "Upload failed, "+res);
		    }
			//Upper code is done for file upload now do coding for save data in database
			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			list = uds.getListofuploadedFileforDL();
			int i = 0;
			for (UserUploadDetailsBean bean : list) {
				data = new String[11];
				i++;
				data[0] = String.valueOf(i);// serial no
				data[1] = bean.getUpload();// category
				newSubject = bean.getSubject();// subject
				data[2] = newSubject != null ? newSubject.trim() : newSubject;
				data[7] = data[2];
				data[3] = bean.getFile_name();// file name
				data[4] = bean.getIs_new().toString();// is new
				data[5] = bean.getFile_full_path();// file path
				data[6] = bean.getPublish().toString();// publish
				data[8] = bean.getId().toString();// id
				data[9] = bean.getUpload_category_id().toString();// category upload
				data[10] = bean.getState_name();
				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());

			categoryType = uds.getUploadCategoryforDL();
			mav.addObject("categoryType", categoryType);
			mav.addObject("category", category_type);

		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value = "/deleteUserUploadDetailDL", method = RequestMethod.POST)
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
		String newSubject=null;
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("user/userUplaodDetailsDL");
			mav.addObject("menu", menuController.getMenuUserId(request));
			String filePath = serverFilePath;
			userUploadDeleteSLNA= uds.userUploadDeleteSLNA(Integer.parseInt(id), filename, filePath);
			tempFile = new File(filePath + filename);
		//	tempFile = new File(servlet.getServletContext().getInitParameter("filesPath") + "Documents" + File.separator+ filename);  // server path
			boolean success = tempFile.delete();
			if(userUploadDeleteSLNA || success) 
					mav.addObject("messageUpload", "Data/File Deleted Successfully");
			else
					mav.addObject("messageUpload", "Data/File not Deleted!");
			//Upper code is done for file delete 
			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			list = uds.getListofuploadedFileforDL();
			int i = 0;
			for (UserUploadDetailsBean bean : list) {
				data = new String[11];
				i++;
				data[0] = String.valueOf(i);// serial no
				data[1] = bean.getUpload();// category
				newSubject = bean.getSubject();// subject
				data[2] = newSubject != null ? newSubject.trim() : newSubject;
				data[7] = data[2];
				data[3] = bean.getFile_name();// file name
				data[4] = bean.getIs_new().toString();// is new
				data[5] = bean.getFile_full_path();// file path
				data[6] = bean.getPublish().toString();// publish
				data[8] = bean.getId().toString();// id
				data[9] = bean.getUpload_category_id().toString();// category upload
				data[10] = bean.getState_name();
				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());

			categoryType = uds.getUploadCategoryforDL();
			mav.addObject("categoryType", categoryType);
			mav.addObject("category", category_type);

		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value = "/updateUserUploadDetailDL", method = RequestMethod.POST)
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
		String newSubject=null;
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("user/userUplaodDetailsDL");
		//	mav.addObject("menu", menuController.getMenuUserId(request));
			
			userUploadUpdateSLNA= uds.userUploadUpdateSLNA(Integer.parseInt(id), newsub, caption_id, publish_id,
					Integer.parseInt(newcat_id), newcattxt, login);
			
			if(userUploadUpdateSLNA) 
					mav.addObject("messageUpload", "Data Update Successfully");
			else
					mav.addObject("messageUpload", "Not Updated!");
			//Upper code is done for update 
			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			list = uds.getListofuploadedFileforDL();
			int i = 0;
			for (UserUploadDetailsBean bean : list) {
				data = new String[11];
				i++;
				data[0] = String.valueOf(i);// serial no
				data[1] = bean.getUpload();// category
				newSubject = bean.getSubject();// subject
				data[2] = newSubject != null ? newSubject.trim() : newSubject;
				data[7] = data[2];
				data[3] = bean.getFile_name();// file name
				data[4] = bean.getIs_new().toString();// is new
				data[5] = bean.getFile_full_path();// file path
				data[6] = bean.getPublish().toString();// publish
				data[8] = bean.getId().toString();// id
				data[9] = bean.getUpload_category_id().toString();// category upload
				data[10] = bean.getState_name();
				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());

			categoryType = uds.getUploadCategoryforDL();
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
