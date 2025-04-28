package app.janbhagidariPratiyogita;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ProfileBean;
import app.service.ProfileService;
import app.service.WSCommiteeService;
import app.watershedyatra.service.WatershedYatraService;

@Controller("janbhagidariPratiyogitaActController")
public class JanbhagidariPratiyogitaActController {

	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedYatraService ser;
	
	@Autowired
	JanbhagidariPratiyogitaService serk;
	
	@Autowired(required = true)
	public WSCommiteeService wscommitteeser;
	
	@RequestMapping(value = "/janbhagidariPratiyogitaActivity", method = RequestMethod.GET)
	public ModelAndView janbhagidariPratiyogitaAct(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("janbhagidariPratiyogitaActivity");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				String username = session.getAttribute("loginID").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
				List<JanbhagidariPratiyogitaBean> compdata = new ArrayList<JanbhagidariPratiyogitaBean>();
				String regid=session.getAttribute("regId").toString();
				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}
				mav.addObject("userType", userType);
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
				mav.addObject("WorkList", ser.getJanBWorkList());
				mav.addObject("projList", wscommitteeser.getUserProjectList(Integer.parseInt(regid)));
				
				mav.addObject("distName", distName);
				
				if(userType.equals("SL")){
				data=serk.getActivityDraftListDetails(stcd);
				}
				else if(userType.equals("PI")){
				data=serk.getActivityDraftListPIADetails(stcd, username);
				}
				
				mav.addObject("dataList",data);
				mav.addObject("dataListSize",data.size());
				mav.addObject("distCode",distCode);
				
				if(userType.equals("SL")){
				compdata=serk.getActivityCompleteListDetails(stcd);
				}
				else if(userType.equals("PI")){
					compdata=serk.getActivityCompleteListPIADetails(stcd, username);
				}
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
	
	@RequestMapping(value = "/saveJanbhagidariPratiyogitaActivity", method = RequestMethod.POST)
	@ResponseBody
	public String saveJanbhagidariPratiyogitaActivity(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="dcode") int dcode, 
			@RequestParam(value ="proj") int proj, @RequestParam(value="vill") String vill, 
			@RequestParam(value ="workList") String workList, @RequestParam(value ="estValueList") String estValueList,
			@RequestParam(value ="villagersList") String villagersList, @RequestParam(value ="ngosList") String ngosList,
			@RequestParam(value ="corporateList") String corporateList, @RequestParam(value ="compWorkList") String compWorkList, @RequestParam(value ="completedDateList") String completedDateList){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
//		Integer projId= Integer.parseInt(request.getParameter("project"));
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				String createdby = session.getAttribute("loginID").toString();
				res = serk.saveJanbhagidariPratiyogitaActivity(dcode, proj, vill, workList, estValueList, villagersList, ngosList, corporateList, compWorkList, completedDateList, session, request); 
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
	
	@RequestMapping(value="/deleteJanbhagidariActivity", method = RequestMethod.POST)
	@ResponseBody
	public String deleteJanbhagidariActivity(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			res=serk.deleteJanbhagidariActivity(assetid);
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/completeJanbhagidariActivity", method = RequestMethod.POST)
	@ResponseBody
	public String completeJanbhagidariActivity(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		String createdBy = session.getAttribute("loginID").toString();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			res=serk.completeJanbhagidariActivity(assetid, createdBy);
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/checkDuplicateWorkEntry", method = RequestMethod.POST)
	@ResponseBody
	public String checkDuplicateWorkEntry(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="villageId") Integer villageId, @RequestParam(value ="workId") Integer workId)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			res=serk.checkdupWorkEntry(villageId, workId);
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
}
