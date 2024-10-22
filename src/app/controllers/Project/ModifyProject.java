package app.controllers.Project;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.context.request.WebRequest;

import app.bean.ProjectState;

import app.model.IwmpMProject;
import app.service.AddProjectService;
import app.service.CommonService;
import app.service.DistrictMasterService;
import app.service.StateMasterService;

@Controller("ModifyProject")
public class ModifyProject {
	HttpSession session;

	@Autowired(required = true)
	private AddProjectService projectService;
	@Autowired
	DistrictMasterService districtMasterService;

	@Autowired(required = true)
	private CommonService commonService;

	@Autowired(required = true)
	StateMasterService stateMasterService;

	private static final Logger logger = Logger.getLogger(ModifyProject.class);

	public ModifyProject() {

	}

	@RequestMapping(value = "/ProjectList", method = RequestMethod.GET)
	public String modifyProjectList(Locale locale, Model model) throws IOException {
		ProjectState projectDetail = new ProjectState();
		model.addAttribute("statelist", stateMasterService.getAllState());
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(0));
		model.addAttribute("projectDetail", projectDetail);
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		return ("project/ModifyProject");
	}

	@RequestMapping(value = "/ModifyListProject", method = RequestMethod.POST)
	public String ListProjectToModify(@ModelAttribute("projectDetail") ProjectState projectDetai, BindingResult result,
			Model model) {
		List<IwmpMProject> projectList = null;
		projectList = projectService.getListSanctionedProject(projectDetai.getStateCode(), projectDetai.getDistCode(),
				projectDetai.getFinCode(), projectDetai.getIwmpMProjectPrd(), projectDetai.getIwmpMAreaType(),
				projectDetai.getProjectStatus());

		model.addAttribute("statelist", stateMasterService.getAllState());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetai.getStateCode()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("projectList", projectList);
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		return "/project/ModifyProject";

	}

	
	
	@RequestMapping(value = "/modifyProject", params = "onmodify", method = RequestMethod.POST)
	public String modifyRecord(@ModelAttribute("projectDetail") ProjectState projectDetail, BindingResult result,
			Model model, WebRequest request) {
		IwmpMProject project = null;
		if (request.getParameter("projid") != null) {
			String id = request.getParameter("projid");
			project=projectService.getProject(Integer.parseInt(id));
		}
		model.addAttribute("projectDetail", project);
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		return "project/ModifyProjectDetail";
	}
	
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public String updateRecord(@ModelAttribute("projectDetail")  @Valid IwmpMProject projectDetail, BindingResult result,
			Model model, WebRequest request) {
		if (result.hasErrors()) {
			model.addAttribute("projectDetail", projectDetail);
			model.addAttribute("areaType", commonService.getAllAreaType());
			model.addAttribute("projectPrd", commonService.getAllProjectPrd());
			return "project/ModifyProjectDetail";
		}
		else
		{
			projectService.updateProject(projectDetail);
			ProjectState projectState = new ProjectState();
			model.addAttribute("statelist", stateMasterService.getAllState());
			model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(0));
			model.addAttribute("financialYear", commonService.getAllFinancialYear());
			model.addAttribute("projectDetail", projectState);
			model.addAttribute("areaType", commonService.getAllAreaType());
			model.addAttribute("projectPrd", commonService.getAllProjectPrd());
			model.addAttribute("msg", "Project is Updated Successfully");
			return "project/ModifyProject";
		}
	}
	
	@RequestMapping(value = "/deleteProject", params = "ondelete", method = RequestMethod.POST)
	public String deleteRecord(@ModelAttribute("projectDetail") ProjectState projectDetail, BindingResult result,
			Model model, WebRequest request) {
		if (request.getParameter("projid") != null) {
			String id = request.getParameter("projid");
			projectService.deleteProject(Integer.parseInt(id));
		}

		List<IwmpMProject> projectList = null;
		projectList = projectService.getListSanctionedProject(projectDetail.getStateCode(), projectDetail.getDistCode(),
				projectDetail.getFinCode(), projectDetail.getIwmpMProjectPrd(), projectDetail.getIwmpMAreaType(),
				projectDetail.getProjectStatus());

		model.addAttribute("statelist", stateMasterService.getAllState());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(projectDetail.getStateCode()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("projectList", projectList);
		model.addAttribute("areaType", commonService.getAllAreaType());
		model.addAttribute("projectPrd", commonService.getAllProjectPrd());
		model.addAttribute("msg", "Project is Deleted Successfully");
		return "project/ModifyProject";
	}
}
