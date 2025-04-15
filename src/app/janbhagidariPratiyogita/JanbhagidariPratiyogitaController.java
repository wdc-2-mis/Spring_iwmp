package app.janbhagidariPratiyogita;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.Login;
import app.bean.ProfileBean;
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.service.ProfileService;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.service.WatershedYatraService;

@Controller("JanbhagidariPratiyogitaController")
public class JanbhagidariPratiyogitaController {
	
	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedYatraService ser;
	
	@Autowired
	JanbhagidariPratiyogitaService serk;
	
	
	@RequestMapping(value = "/janbhagidariPratiyogita", method = RequestMethod.GET)
	public ModelAndView janbhagidariPratiyogita(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("WatershedYatra/janbhagidariPratiyogita");

				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
				List<JanbhagidariPratiyogitaBean> compdata = new ArrayList<JanbhagidariPratiyogitaBean>();

				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}

				mav.addObject("userType", userType);
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
				
				
				data=serk.getDraftListDetails(stcd);
				mav.addObject("dataList",data);
				mav.addObject("dataListSize",data.size());
				
				
				compdata=serk.getCompleteListDetails(stcd);
				mav.addObject("compdataList",compdata);
				mav.addObject("compdataListSize",compdata.size());

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getJanbhagidariPratiyogitaProject", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getJanbhagidariPratiyogitaProject(HttpServletRequest request, @RequestParam("dCode") int dCode) {
		
		return serk.getJanbhagidariPratiyogitaProject(dCode);
	}
	
	@RequestMapping(value="/getJanbhagidariPratiyogitaProjectData", method = RequestMethod.POST)
	@ResponseBody
	public List<JanbhagidariPratiyogitaBean> getJanbhagidariPratiyogitaProjectData(HttpServletRequest request, HttpServletResponse response, 
			 @RequestParam(value ="project") Integer project)
	{
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		List<JanbhagidariPratiyogitaBean> list=new  ArrayList<JanbhagidariPratiyogitaBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			list=serk.getJanbhagidariPratiyogitaProjectData(project);
		}
		return list; 
	}
	
	@RequestMapping(value="/getGPofJanbhagidariPratiyogita", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getGPofJanbhagidariPratiyogita(HttpServletRequest request, HttpServletResponse response,
			 @RequestParam(value ="project") Integer project)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=serk.getGPofJanbhagidariPratiyogita(project);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return map; 
	}
	
	@RequestMapping(value="/getVILLofJanbhagidariPratiyogita", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getVILLofJanbhagidariPratiyogita(HttpServletRequest request, HttpServletResponse response,
			 @RequestParam(value ="gpid") List<Integer> gcode, @RequestParam(value ="project") Integer projectid)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map=serk.getVILLofJanbhagidariPratiyogita(gcode, projectid);
		return map; 
	}
	
	@RequestMapping(value = "/checkNGOName", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkNGOName(@RequestParam("ngoName") String ngoName, @RequestParam("projectId") int projectId) {
        return serk.isNGONameExists(ngoName, projectId);
    }
	
	@RequestMapping(value = "/saveJanbhagidariPratiyogita1", method = RequestMethod.POST)
	public ModelAndView saveJanbhagidariPratiyogita(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("janbhagidari") JanbhagidariPratiyogitaBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";

		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("WatershedYatra/janbhagidariPratiyogita");

				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;

				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}

				mav.addObject("userType", userType);
				// mav.addObject("distName",distName);
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));

				//result = serk.saveJanbhagidariPratiyogita(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved Successfully!");
				}
				return new ModelAndView("redirect:/janbhagidariPratiyogita");
			} else {
				return new ModelAndView("redirect:/login");


			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/deleteJanbhagidariPratiyogita", method = RequestMethod.POST)
	@ResponseBody
	public String deleteJanbhagidariPratiyogita(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=serk.deleteJanbhagidariPratiyogita(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/completeJanbhagidariPratiyogita", method = RequestMethod.POST)
	@ResponseBody
	public String completeJanbhagidariPratiyogita(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=serk.completeJanbhagidariPratiyogita(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value = "/saveJanbhagidariPratiyogita", method = RequestMethod.POST)
	@ResponseBody
	public String saveJanbhagidariPratiyogita(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="vill") List<String> vill, 
			@RequestParam(value ="ngoname") List<String> ngoname, @RequestParam(value ="dcode") int dcode, @RequestParam(value ="proj") int proj,  @RequestParam(value ="nogp") int nogp, @RequestParam(value ="novillage") int novillage,  @RequestParam(value ="projarea") String projarea,
			@RequestParam(value ="projoutlay") String projoutlay, @RequestParam(value ="funoutlay") int funoutlay, @RequestParam(value ="projexp") String projexp, @RequestParam(value ="expper") String expper, 
			@RequestParam(value ="swckgp") String swckgp){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
//		Integer projId= Integer.parseInt(request.getParameter("project"));
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				String createdby = session.getAttribute("loginID").toString();
				res = serk.saveJanbhagidariPratiyogita(vill, ngoname, dcode, proj,  nogp, 
						novillage, projarea, projoutlay, funoutlay, projexp, expper, swckgp, session); 
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value = "/getExistingProjectCodes", method = RequestMethod.POST)
	@ResponseBody
	public String getExistingProjectCodes( HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="pCode") Integer pCode ) {
	  return serk.getExistingProjectCodes(pCode);
	}
	
	@RequestMapping(value = "/getTotalNoofGP", method = RequestMethod.POST)
	@ResponseBody
	public Integer getTotalNoofGP( HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="dCode") Integer dCode) {
	  return serk.getTotalNoofGP(dCode);
	}
	
	@RequestMapping(value = "/getTotalNoofVill", method = RequestMethod.POST)
	@ResponseBody
	public Integer getTotalNoofVill( HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="dCode") Integer dCode) {
	  return serk.getTotalNoofVill(dCode);
	}

}
