package app.controllers.unfreeze;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.reports.UserUploadDetailsBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.reports.UserUploadDetailServices;

@Controller("ApprovalofUploadingDocumentDoLR")
public class ApprovalofUploadingDocumentDoLR {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;

	@Autowired(required = true)
	public UserUploadDetailServices uds;
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Value("${filePath}")
	String serverFilePath;

	private Map<String, String> categoryType;
	private Map<Integer, String> stateList;

	@RequestMapping(value = "/ApprovalUploadingDocumentDoLR", method = RequestMethod.GET)
	public ModelAndView ApprovalUploadingDocumentDoLR(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		String category_type = request.getParameter("category_type");
		String userState= request.getParameter("state");
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String newSubject = null;
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("unfreeze/approvalofUploadingDocumentDoLR");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);

			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			if(userState!=null && !userState.equals("") ) 
			{
				list = uds.getListofuploadedFileforState(Integer.parseInt(userState));
				int i = 0;
				for (UserUploadDetailsBean bean : list) 
				{
					data = new String[13];
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
					data[11] = bean.getStatus();
					data[12] = String.valueOf(bean.getId());
					dataList.add(data);
				}
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
	
	@RequestMapping(value = "/DoLRApprovalUploadingDocument", method = RequestMethod.POST)
	public ModelAndView DoLRApprovalUploadingDocument(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		String category_type = request.getParameter("category_type");
		String userState= request.getParameter("state");
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String newSubject = null;
		boolean WorkIdTemp=false;
		String upid[]= request.getParameterValues("upid");
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("unfreeze/approvalofUploadingDocumentDoLR");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			
			WorkIdTemp= uds.doLRApprovalUploadingDocument(upid);
			if(WorkIdTemp ) 
					mav.addObject("messageUpload", "Data Approved Successfully !");
			else
					mav.addObject("messageUpload", "Data not Approved!");
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
			if(userState!=null && !userState.equals("") ) 
			{
				list = uds.getListofuploadedFileforState(Integer.parseInt(userState));
				int i = 0;
				for (UserUploadDetailsBean bean : list) 
				{
					data = new String[13];
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
					data[11] = bean.getStatus();
					data[12] = String.valueOf(bean.getId());
					dataList.add(data);
				}
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
