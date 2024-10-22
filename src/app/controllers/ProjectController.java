package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import app.model.IwmpMAreaType;
import app.model.IwmpMCsShare;
import app.model.IwmpMProject;
import app.service.AddProjectService;
import app.service.CommonService;
import app.service.DistrictMasterService;
import app.service.StateMasterService;

@Controller
public class ProjectController {
	HttpSession session;

	@Autowired(required = true)
	private AddProjectService projectService;
	@Autowired
	DistrictMasterService districtMasterService;

	@Autowired(required = true)
	private CommonService commonService;

	@Autowired(required = true)
	StateMasterService stateMasterService;
	// private List<IwmpMProject> projectList = new ArrayList<IwmpMProject>();

	private static final Logger logger = Logger.getLogger(ProjectController.class);

	public ProjectController() {
	//	System.out.println("ProjectController()");
	}

	@RequestMapping(value = "/addNewProject", method = RequestMethod.GET)
	public String newProject(Locale locale, Model model,HttpServletRequest request) {
		
//		ProjectState projectDetail = new ProjectState();
//		model.addAttribute("statelist", stateMasterService.getAllState());
//		model.addAttribute("projectDetail", projectDetail);
//		return "/project/AddProjectFrm";

//		ProjectState projectDetail = new ProjectState();
//		model.addAttribute("statelist", stateMasterService.getAllState());
//		model.addAttribute("projectDetail", projectDetail);
//		return "AddProjectFrm";

		session = request.getSession(true);
		String result=null;
		if(session!=null && session.getAttribute("loginID")!=null) {
			ProjectState projectDetail = new ProjectState();
			model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
			model.addAttribute("projectDetail", projectDetail);
			result="project/AddProjectFrm";
		}else {
			result="login";
		}		
		return result;

	}

	@RequestMapping(value = "/saveProject", params = "add", method = RequestMethod.POST)
	public String addProjectDetails(@ModelAttribute("projectDetail") ProjectState projectDetail,
			BindingResult result, Model model) {

		List<IwmpMProject> projects = projectDetail.getIwmpMProjectList();
		if (projects == null) {
			projectDetail.getIwmpMProjectList().add(new IwmpMProject());
			// projectDetail.setIwmpMProjectList(projectList);
		} else {
			projectDetail.getIwmpMProjectList().add(new IwmpMProject());
		}

		model.addAttribute("projectDetail", projectDetail);
		model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetail.getStateCode()));
		return "project/AddProjectFrm";
	}

	@RequestMapping(value = "/onChange",method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal[] onChangeAreaTypeArea(HttpServletRequest request,@RequestParam("ap") BigDecimal areaProposed,@RequestParam("at") int areatype,@RequestParam("stCode") int stCode) {
		
		IwmpMAreaType tempArea = commonService
				.getAreaTypeDetail(areatype);
		BigDecimal[] val = null;
		IwmpMCsShare shareDetail = commonService.getStateCentralShareDetail(stCode);
		try {
			if (tempArea != null && areaProposed.floatValue() > 0) {
				BigDecimal bg = new BigDecimal("100000");
				BigDecimal perc = new BigDecimal("100");
				val = new BigDecimal[3];
				
				val[0]= areaProposed.multiply(tempArea.getAreaValue()).divide(bg).setScale(2, BigDecimal.ROUND_FLOOR);
				val[1]= val[0].multiply(shareDetail.getCentralShare()).divide(perc).setScale(2, BigDecimal.ROUND_FLOOR);
				val[2]= val[0].multiply(shareDetail.getStateShare()).divide(perc).setScale(2, BigDecimal.ROUND_FLOOR);
					
			}
		} catch (Exception ex) {
			// return "AddProjectFrm";
		}
		return val;
	}
	@RequestMapping(value = "/saveProject", params = "onchange", method = RequestMethod.POST)
	public String onChangeArea(@ModelAttribute("projectDetail") ProjectState projectDetail, BindingResult result,
			Model model, WebRequest request,@RequestParam("stateCode") int stateCode) {

		List<IwmpMProject> projects = projectDetail.getIwmpMProjectList();

		if (projectDetail.getIwmpMProjectList() != null) {
			if (request.getParameter("id") != null) {
				int id = Integer.parseInt(request.getParameter("id"));
				IwmpMAreaType areatype = commonService
						.getAreaTypeDetail(projects.get(id).getIwmpMAreaType().getAreaCd());
				IwmpMCsShare shareDetail = commonService.getStateCentralShareDetail(stateCode);
				try {
					if (areatype != null && projects.get(id).getAreaProposed().floatValue() > 0) {
						BigDecimal bg = new BigDecimal("100000");
						BigDecimal perc = new BigDecimal("100");
						projects.get(id).setProjectCost(
								projects.get(id).getAreaProposed().multiply(areatype.getAreaValue()).divide(bg));
						projects.get(id).setCentralShareAmt(
								projects.get(id).getProjectCost().multiply(shareDetail.getCentralShare()).divide(perc));
						projects.get(id).setStateShareAmt(
								projects.get(id).getProjectCost().multiply(shareDetail.getStateShare()).divide(perc));
					}
				} catch (Exception ex) {
					// return "AddProjectFrm";
				}
			}
		}
		model.addAttribute("projectDetail", projectDetail);
		model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetail.getStateCode()));
		return "project/AddProjectFrm";
	}

	@RequestMapping(value = "/saveProject", params = "ondate", method = RequestMethod.POST)
	public String onDateChange(@ModelAttribute("projectDetail") ProjectState projectDetail, BindingResult result,
			Model model, WebRequest request,@RequestParam("stateCode") int stateCode) {

		List<IwmpMProject> projects = projectDetail.getIwmpMProjectList();

		if (projectDetail.getIwmpMProjectList() != null) {
			if (request.getParameter("id") != null) {
				int id = Integer.parseInt(request.getParameter("id"));
				IwmpMAreaType areatype = commonService
						.getAreaTypeDetail(projects.get(id).getIwmpMAreaType().getAreaCd());
				IwmpMCsShare shareDetail = commonService.getStateCentralShareDetail(stateCode);
				try {
					if (areatype != null && projects.get(id).getAreaProposed().floatValue() > 0) {
						BigDecimal bg = new BigDecimal("100000");
						BigDecimal perc = new BigDecimal("100");
						projects.get(id).setProjectCost(
								projects.get(id).getAreaProposed().multiply(areatype.getAreaValue()).divide(bg));
						projects.get(id).setCentralShareAmt(
								projects.get(id).getProjectCost().multiply(shareDetail.getCentralShare()).divide(perc));
						projects.get(id).setStateShareAmt(
								projects.get(id).getProjectCost().multiply(shareDetail.getStateShare()).divide(perc));
					}
				} catch (Exception ex) {
					// return "AddProjectFrm";
				}
			}
		}
		model.addAttribute("projectDetail", projectDetail);
		model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetail.getStateCode()));
		return "project/AddProjectFrm";
	}


	@RequestMapping(value = "/checkSequence",method = RequestMethod.POST)
	@ResponseBody
	public String getAllSubcategories(HttpServletRequest request,@RequestParam("dcode") int districtCode,@RequestParam("seq") int sequence,
			@RequestParam("fcode") int finanialCode) {
		System.out.print("SSS");
		IwmpMProject temp = projectService.getSequenceFinDistrict(
				finanialCode, districtCode,	sequence);
		if (temp != null) {
			System.out.print("SSS IN");
			return "This Sequence Number is already Exist" ;
		}
		else
			return "";
	}

	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/saveProject", params = "ondelete", method = RequestMethod.POST)
	public String deleteRecord(@ModelAttribute("projectDetail") ProjectState projectDetail, BindingResult result,
			Model model, WebRequest request) {

		// List<IwmpMProject> projects = projectDetail.getIwmpMProjectList();

		if (projectDetail.getIwmpMProjectList() != null) {

			if (request.getParameter("id") != null) {
				String id = request.getParameter("id");
				projectDetail.getIwmpMProjectList().remove(Integer.parseInt(id));
			}
		}
		// projectDetail.setIwmpMProjectList(projects);
		model.addAttribute("projectDetail", projectDetail);
		model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetail.getStateCode()));
		
		return "project/AddProjectFrm";
	}

	@RequestMapping(value = "/saveProject", params = "go", method = RequestMethod.POST)
	public String addProjectGoDetails(@ModelAttribute("projectDetail") ProjectState projectDetail,
			BindingResult result, Model model,HttpServletRequest request) {
		session = request.getSession(true);
		if (projectDetail.getStateCode() > 0) {
			List<IwmpMProject> projects = projectDetail.getIwmpMProjectList();
			if (projects == null) {
				List<IwmpMProject> projectList = new ArrayList<IwmpMProject>();

				projectList.add(new IwmpMProject());
			//	projectList.add(new IwmpMProject());
				projectDetail.setIwmpMProjectList(projectList);
				// projectDetail.setIwmpMProjectList(projectList);
			}
		} else {
			model.addAttribute("error", "Please Select State");
		}
		model.addAttribute("projectDetail", projectDetail);
		if(session.getAttribute("loginID")!=null)
		model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetail.getStateCode()));
		return "project/AddProjectFrm";
	}

	// @RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	@RequestMapping(value = "/saveProject", params = "action2", method = RequestMethod.POST)
	public String saveProject(@ModelAttribute("projectDetail") @Valid ProjectState projectDetail, BindingResult result,
			Model model,@RequestParam("stateCode") int stateCode) {
		System.out.println("saveProject()");

	
		if (result.hasErrors()) {
			model.addAttribute("projectDetail", projectDetail);
			model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
			model.addAttribute("financialYear", commonService.getAllFinancialYear());
			model.addAttribute("areaType", commonService.getAllAreaType());
			model.addAttribute("projectPrd", commonService.getAllProjectPrd());
			model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetail.getStateCode()));
			return "project/AddProjectFrm";
		} else {
			List<IwmpMProject> projects = projectDetail.getIwmpMProjectList();
			projectService.addProjectList(projects,commonService.getStateCentralShareDetail(stateCode),projectDetail.getStateCode());
			model.addAttribute("msg", "Project is saved Successfully");
			projectDetail = new ProjectState();
			model.addAttribute("statelist", stateMasterService.getUserState(session.getAttribute("loginID").toString()));
			model.addAttribute("projectDetail", projectDetail);
			return "project/AddProjectFrm";
		}
	}

}
