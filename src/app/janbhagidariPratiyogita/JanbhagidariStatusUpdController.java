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

@Controller("janbhagidariStatusUpdController")
public class JanbhagidariStatusUpdController {

	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedYatraService ser;
	
	@Autowired
	JanbhagidariPratiyogitaService serk;
	
	@Autowired(required = true)
	public WSCommiteeService wscommitteeser;
	
	@RequestMapping(value = "/janbhagidariStatusUpdate", method = RequestMethod.GET)
	public ModelAndView janbhagidariStatusUpdate(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "projid", required = false) String projid) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("janbhagidariStatusUpdate");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer district = null;
				String checkdistrict = request.getParameter("district");
				
				if(checkdistrict !=null && !checkdistrict.trim().isEmpty())
				{
					district = Integer.parseInt(checkdistrict);
				}
					
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}
				if (userType.equals("SL")) {
				    if (projid != null && !projid.trim().isEmpty()) {
				        data = serk.getjanbhagidariNoStatus(stcd, district, Integer.parseInt(projid));
				        if (district != null) {
				            mav.addObject("projList", serk.getJanbhagidariPratiyogitaProject(district));
				            mav.addObject("district", district);
				        }
				    } else {
				        data = serk.getjanbhagidariNoStatus(stcd, district, null); 
				    }
				}
				
				else if(userType.equals("PI")){
					 if (projid != null && !projid.trim().isEmpty()) {
					 data = serk.getjanbhagidariPIANoStatusWithProj(projid);
					 }
					 else {
					data = serk.getjanbhagidariPIANoStatus(regId);
				}
					 mav.addObject("projList", wscommitteeser.getUserProjectList(regId));
				}
				mav.addObject("dataList", data);
				mav.addObject("dataListSize",data.size());
				mav.addObject("userType", userType);
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
				mav.addObject("selectedProjId", projid);
				mav.addObject("distCode",distCode);
				mav.addObject("distName", distName);
			}
		
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	return mav;
}
	
	@RequestMapping(value="/updateJanbhagidariCompletedDate", method = RequestMethod.POST)
	@ResponseBody
	public String updateJanbhagidariCompletedDate(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid,
			@RequestParam(value="compworkval") List<String> compworkval, @RequestParam(value="completedDate") List<String> completedDate)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		String createdBy = session.getAttribute("loginID").toString();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			res=serk.updateJanbhagidariCompDate(assetid, compworkval,completedDate, createdBy);
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
}
