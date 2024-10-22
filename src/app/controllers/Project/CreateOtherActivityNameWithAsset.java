package app.controllers.Project;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.model.IwmpMProject;
import app.service.FinYrServices;
import app.service.ProjectMasterService;
import app.service.outcome.OtherActivityNameWithAssetService;

@Controller("CreateOtherActivityNameWithAsset")
public class CreateOtherActivityNameWithAsset {
	
	HttpSession session=null;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	FinYrServices finYrServices;
	
	@Autowired(required = true)
	OtherActivityNameWithAssetService ser;
	
	@RequestMapping(value="/createAssetOtherActivityName", method = RequestMethod.GET)
	public ModelAndView createAssetOtherActivityName(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String project=request.getParameter("project");
		String year=request.getParameter("year");
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userId = session.getAttribute("loginID").toString();
			String userType = session.getAttribute("userType").toString();
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		    mav = new ModelAndView("project/createOtherActivityNameWithAsset");
		    if(project!=null && year!=null) {
		    	mav.addObject("dataList", ser.getcreateAssetOtherActivityName(project, stcode, year));
		    	mav.addObject("saveList", ser.getAssetOtherActivityName(project, stcode, year));
		    } 
			mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
			mav.addObject("yearList",finYrServices.getAllFinYear());
			mav.addObject("otherList",ser.getOtherNameMaster(stcode));
			mav.addObject("proj",project);
			mav.addObject("fyear",year); 
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/updateAssetOtherActivityName", method = RequestMethod.POST)
	public ModelAndView updateAssetOtherActivityName(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String project=request.getParameter("project");
		String year=request.getParameter("year");
		String workid[]= request.getParameterValues("workid");
		String othername[]= request.getParameterValues("othername");
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userId = session.getAttribute("loginID").toString();
			String userType = session.getAttribute("userType").toString();
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		    mav = new ModelAndView("project/createOtherActivityNameWithAsset");
		    
		    String update =ser.updateOtherActivityName(project, stcode, year, workid, othername);
		    if(project!=null && year!=null) {
		    	mav.addObject("saveList", ser.getAssetOtherActivityName(project, stcode, year));
		    } 
			mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
			mav.addObject("yearList", finYrServices.getAllFinYear());
			mav.addObject("proj", project);
			mav.addObject("fyear", year); 
			if(update.equals("success"))
			mav.addObject("messagerj", "Data Save Successfully."); 
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}

}
