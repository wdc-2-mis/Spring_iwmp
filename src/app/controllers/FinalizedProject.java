package app.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import app.bean.ProjectState;
import app.model.IwmpMProject;
import app.service.AddProjectService;
import app.service.CommonService;
import app.service.DistrictMasterService;
import app.service.StateMasterService;

@Controller("FinalizedProject")
public class FinalizedProject {
	HttpSession session;

	@Autowired(required = true)
	private AddProjectService projectService;
	@Autowired
	DistrictMasterService districtMasterService;

	@Autowired(required = true)
	private CommonService commonService;

	@Autowired(required = true)
	StateMasterService stateMasterService;
	
	private static final Logger logger = Logger.getLogger(ProjectController.class);

	public FinalizedProject() {
	
	}
	@RequestMapping(value = "/projectStatusChange", method = RequestMethod.GET)
	public String finalizedProject(Locale locale, Model model,HttpServletRequest request) throws IOException {
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			ProjectState projectDetail = new ProjectState();
			List<IwmpMProject> projectList=null;
			projectDetail.setIwmpMProjectList(projectList);
			//model.addAttribute("statelist", stateMasterService.getAllState());
			model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
			model.addAttribute("financialYear", commonService.getAllFinancialYear());
			model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(0));
			model.addAttribute("projectDetail", projectDetail);	
			
			return("project/FinalizeProject");
		}else {
			return "login";
		}		
	//	return result;
		
		}

	@RequestMapping(value = "/sanctionedProject", params = "go", method = RequestMethod.POST)
	public String onGo(@ModelAttribute("projectDetail") ProjectState projectDetail,
			BindingResult result, Model model) {
		List<IwmpMProject> projectList=null;
		
//		projectList=projectService.getListSanctionedProjectStatus((int)projectDetail.getStateCode(),(int)projectDetail.getDistCode(),(int)projectDetail.getFinCode(),projectDetail.getStatus());
		projectList=projectService.getListSanctionedProjectStatus((int)projectDetail.getStateCode(),(int)projectDetail.getDistCode(),(int)projectDetail.getFinCode(),projectDetail.getStatus(),session.getAttribute("loginID").toString());
		projectDetail.setIwmpMProjectList(projectList);
		//model.addAttribute("statelist", stateMasterService.getAllState());
		model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetail.getStateCode()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("projectDetail", projectDetail);
		return "project/FinalizeProject";
	}
	@RequestMapping(value = "/sanctionedProject", params = "ondelete", method = RequestMethod.POST)
	public String OnDelete(@ModelAttribute("projectDetail") ProjectState projectDetail,
			BindingResult result,Model model, WebRequest request) {
		int delete=0;
		if (request.getParameter("projid") != null) {
			String id = request.getParameter("projid");
			delete=projectService.deleteProject(Integer.parseInt(id));
		}
	    
		List<IwmpMProject> projectList=null;
		projectList=projectService.getListSanctionedProjectNew((int)projectDetail.getStateCode(),(int)projectDetail.getDistCode(),(int)projectDetail.getFinCode());
		//model.addAttribute("statelist", stateMasterService.getAllState());
		model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode((int)projectDetail.getStateCode()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		projectDetail.setIwmpMProjectList(projectList);
		model.addAttribute("projectDetail", projectDetail);
		if(delete==0)
			model.addAttribute("msg", "Project can not Delete.");
		else
			model.addAttribute("msg", "Project is deleted Successfully");
		return "project/FinalizeProject";
	}
	@RequestMapping(value = "/sanctionedProject", method = RequestMethod.POST)
	public String OnFinalized(@ModelAttribute("projectDetail") ProjectState projectDetail,
			BindingResult result,Model model, WebRequest request) {
		//projectDetail = new ProjectState();
		int update=0;
		if(projectDetail.getIwmpMProjectList()!=null && projectDetail.getIwmpMProjectList().size()>0)
			update=projectService.updateProjectList(projectDetail.getIwmpMProjectList());
		projectDetail=new ProjectState();
		//model.addAttribute("statelist", stateMasterService.getAllState());
		model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(0));
		model.addAttribute("projectDetail", projectDetail);
		if(update==0)
			model.addAttribute("msg", "No project has been selected for Completion");
		else
			model.addAttribute("msg", "Project is Completed Successfully");
		return "project/FinalizeProject";
	}

}
