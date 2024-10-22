package app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.FinYearBean;
import app.bean.LastActionLogBean;
import app.bean.Login;
import app.bean.ProjectPrepareBean;
import app.model.IwmpMFinYear;
import app.model.master.IwmpMProjectPrepare;
import app.service.FinYrServices;
import app.util.CommonUtility;

@Controller("FinancialYearMasterController")
public class FinancialYearMasterController {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	FinYrServices finYrServices;
	
	private Map<String, String> finYrList=null;
	
	@RequestMapping(value="/financialYear", method = RequestMethod.GET) 
	public ModelAndView getFinancialYear(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Boolean b=true;
		String regid=session.getAttribute("regId").toString();
	//	String ProjId=request.getParameter("projectId");
		List<FinYearBean> list=new  ArrayList<FinYearBean>();
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("user/financialYearMaster");
			mav.addObject("menu", menuController.getMenuUserId(request));
			list=finYrServices.getFinYrMaster();
			mav.addObject("finYearMasterList", list);
		//	if(!CommonUtility.isCurrentFinYearExist())
			b=finYrServices.isCurrentFinYearExist();
			if(!b) {
				finYrList=finYrServices.getNextFinYearList();
				mav.addObject("financialYear", finYrList);
			}	
			
			
			/*
			 * for(int i=0;i<ProjectPreparelist.size();i++) {
			 * System.out.println(ProjectPreparelist.get(i).getProjectPrepareId()); }
			 */
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	
	@RequestMapping(value="/saveFinancialYear", method = RequestMethod.POST) 
	public ModelAndView saveFinancialYear(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Boolean b=true;
		String regid=session.getAttribute("regId").toString();
		String finYear=request.getParameter("finYear");
		String finYearDesc=request.getParameter("finYearDes");
		String finYearstatus=request.getParameter("finYearstatus");
		String startFrom=request.getParameter("fromdate");
		String endTo=request.getParameter("enddate");
		int i=0;
		List<FinYearBean> list=new  ArrayList<FinYearBean>();
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("user/financialYearMaster");
			mav.addObject("menu", menuController.getMenuUserId(request));
			i=finYrServices.saveFinancialYear(finYearDesc, session, finYearstatus,startFrom,endTo);
			list=finYrServices.getFinYrMaster();
			mav.addObject("finYearMasterList", list);
			
			b=finYrServices.isCurrentFinYearExist();
			if(!b) {
				finYrList=finYrServices.getNextFinYearList();
				mav.addObject("financialYear", finYrList);
			}
			if(i>0) 
			{
				mav.addObject("MessagefinYear", "Financial year "+finYearDesc+" saved successfully.");
			}
			/*
			 * for(int i=0;i<ProjectPreparelist.size();i++) {
			 * System.out.println(ProjectPreparelist.get(i).getProjectPrepareId()); }
			 */
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	
	
	

}
