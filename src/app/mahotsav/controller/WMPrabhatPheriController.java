package app.mahotsav.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.Login;
import app.bean.ProfileBean;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.WMPrabhatPheriBean;
import app.mahotsav.service.WMPrabhatPheriService;
import app.service.ProfileService;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.service.WatershedYatraPIALevelService;
import app.watershedyatra.service.WatershedYatraService;

@Controller
public class WMPrabhatPheriController {

	HttpSession session;
	
    @Autowired
    ProfileService profileService;

    @Autowired
    WatershedYatraService ser;

    @Autowired
    WatershedYatraPIALevelService serp;
   
    @Autowired
    WMPrabhatPheriService wmService;

    @GetMapping("/getWMPrabhatPheri")
    public String getWMPrabhatPheri(HttpSession session, Map<String, Object> model) {

        if (session == null || session.getAttribute("loginID") == null) {
            return "login"; 
        }
        Integer regId = (Integer) session.getAttribute("regId");
        Integer stCode = (Integer) session.getAttribute("stateCode");
        String userType = (String) session.getAttribute("userType");

        profileService.getMapstate(regId, userType).stream().findFirst().ifPresent(bean -> {
            model.put("stateName", bean.getStatename());
            model.put("distName", bean.getDistrictname());
            model.put("stCode", bean.getStatecode() != null ? bean.getStatecode() : 0);
            model.put("distCode", bean.getDistrictcode() != null ? bean.getDistrictcode() : 0);
        });

        model.put("userType", userType);
        model.put("distList", ser.getDistrictList(stCode));
       // model.put("blkList", serp.getBlockListpia(regId.toString()));
        
        
        List<WMPrabhatPheriBean> dlist = wmService.getWatershedMahotsavDraftList(stCode);
        model.put("dataDList", dlist);
        model.put("dataDListSize", dlist != null ? dlist.size() : 0);
        
        List<WMPrabhatPheriBean> comlist = wmService.getWatershedMahotsavCompleteList(stCode);
        model.put("dataCList", comlist);
        model.put("dataCListSize", comlist != null ? comlist.size() : 0);
        return "mahotsav/mahotsavPrabhatPheri";
    }

    @RequestMapping(value = "/saveWMPrabhatPheri", method = RequestMethod.POST)
	public ModelAndView saveWMPrabhatPheri(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WMPrabhatPheriBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		List<String> imageNames = new ArrayList<>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/mahotsavPrabhatPheri");
				
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

				result = wmService.saveMahotsavPrabhatPheriDetails(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else if (result.equals("failexist")) {
					redirectAttributes.addFlashAttribute("result1", "Data not saved State Data already exist");
				} 
				else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved Successfully!");
				}
				return new ModelAndView("redirect:/getWMPrabhatPheri");
			} else {
				return new ModelAndView("redirect:/login");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
    @RequestMapping(value="/completeWMPrabhatPheri", method = RequestMethod.POST)
    @ResponseBody
    public String completeWMPrabhatPheri(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="ppid") List<Integer> ppid)
    {
    	ModelAndView mav = new ModelAndView();
    	String res="";
    	session = request.getSession(true);
    	if(session!=null && session.getAttribute("loginID")!=null) 
    	{
    		Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
    		String userType= session.getAttribute("userType").toString();
    		res=wmService.completeWMPrabhatPheri(ppid, session.getAttribute("loginID").toString());
    	
    	 
    	}else {
    		mav = new ModelAndView("login");
    		mav.addObject("login", new Login());
    	}
    	return res; 
    }
    
    // Get blocks by district code
    @GetMapping("/getBlocksByDistrict")
    @ResponseBody
    public List<Map<String, Object>> getBlocksByDistrict(@RequestParam("districtCode") Integer districtCode) {
        return wmService.getBlockListByDistrict(districtCode); // return list of blocks
    }

    // Get villages by block code
    @GetMapping("/getVillagesByBlock")
    @ResponseBody
    public List<Map<String, Object>> getVillagesByBlock(@RequestParam("blockCode") Integer blockCode) {
        return wmService.getVillageListByBlock(blockCode); // return list of villages
    }
    
    @RequestMapping(value = "/getWMPrabhatPheriVillage", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWMPrabhatPheriVillage(HttpServletRequest request, @RequestParam("bcode") int bCode) {
		
		return wmService.getWMPrabhatPheriVillage(bCode);
	}
    
   
    
    @RequestMapping(value="/deleteWMPrabhatPheri", method = RequestMethod.POST)
    @ResponseBody
    public String deleteWMPrabhatPheri(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="ppid") List<Integer> ppid)
    {
    	ModelAndView mav = new ModelAndView();
    	String res="";
    	session = request.getSession(true);
    	if(session!=null && session.getAttribute("loginID")!=null) 
    	{
    		Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
    		String userType= session.getAttribute("userType").toString();
    		res=wmService.deleteWMPrabhatPheri(ppid, session.getAttribute("loginID").toString());
    	
    	 
    	}else {
    		mav = new ModelAndView("login");
    		mav.addObject("login", new Login());
    	}
    	return res; 
    }
    
    @RequestMapping(value="/checkVillageWMP", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkVillageWMP(@RequestParam("vCode") Integer vCode) {
        return wmService.checkVillageWMP(vCode);
    }
    
    
    
    @RequestMapping(value = "/getWMPrabhatPheriEdit", method = RequestMethod.POST)
	public ModelAndView getWMPrabhatPheriEdit(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WMPrabhatPheriBean> editlist = new ArrayList<WMPrabhatPheriBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/EditMahotsavPrabhatPheri");
				String prabhatpheriId=request.getParameter("prabhatpheri_id");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("stateName",stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
				
				editlist=wmService.getWMPrabhatPheriEdit(Integer.parseInt(prabhatpheriId));
				
				mav.addObject("dataList",editlist);
				mav.addObject("dataListSize",editlist.size());
				
				
				

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
    @RequestMapping(value = "/updateWMPrabhatPheri", method = RequestMethod.POST)
	public ModelAndView updateWMPrabhatPheri(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WMPrabhatPheriBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		List<String> imageNames = new ArrayList<>();
		List<WMPrabhatPheriBean> dlist = new ArrayList<WMPrabhatPheriBean>();
		List<WMPrabhatPheriBean> comlist = new ArrayList<WMPrabhatPheriBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/EditMahotsavPrabhatPheri");

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

				result = wmService.updateWMPrabhatPheri(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Updated Successfully!");
				} 
				else {
					redirectAttributes.addFlashAttribute("result1", "Do not Update!");
				}
				return new ModelAndView("redirect:/getWMPrabhatPheri");
			} 
			else {
				return new ModelAndView("redirect:/login");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
    
    
}

