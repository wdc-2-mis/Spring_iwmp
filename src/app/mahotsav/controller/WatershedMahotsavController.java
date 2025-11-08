package app.mahotsav.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.service.WatershedMahotsavService;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;



@Controller("watershedMahotsavController")
public class WatershedMahotsavController {

	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	WatershedMahotsavService watershedMahotsavService;
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	private Map<Integer, String> stateList;
	
	private Map<String, String> districtList;
	
	private Map<String, String> blockList;
	
	private Map<String, String> villageList;
	
	@RequestMapping(value="/registerMahotsav", method = RequestMethod.GET)
	public ModelAndView registerMahotsav(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("mahotsav/mahotsavRegister");
		return mav;
	}
	
	
	@RequestMapping(value="/registerMahotsav", method = RequestMethod.POST)
	public ModelAndView otherMahotsav(HttpServletRequest request, HttpServletResponse response)
	{
		 ModelAndView mav = new ModelAndView("mahotsav/mahotsavOtherDtl");
         String name = request.getParameter("name");
         String phone = request.getParameter("phone");
         String email = request.getParameter("email");
         String address = request.getParameter("address");
         String userState= request.getParameter("state"); 
         String district= request.getParameter("district");
         String block= request.getParameter("block");
         String village= request.getParameter("village");
         String regNo= request.getParameter("regNo");
         
         stateList = stateMasterService.getAllState();
         mav.addObject("stateList", stateList);
		 mav.addObject("state", userState);
		 
         if(userState!=null && !userState.equals("") && !userState.equals("0")) {
 			districtList = ser.getDistrictList(Integer.parseInt(userState));
 			mav.addObject("districtList", districtList);}
 			mav.addObject("district", district);
 		
 		if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
			blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
			mav.addObject("blockList", blockList);}
			mav.addObject("blkd", block);
			
		if( block!=null && !block.equalsIgnoreCase("") && !block.equals("0")) {
			villageList = ser.getmahotsavvillageList(Integer.parseInt(block));
			mav.addObject("villageList", villageList);}
			mav.addObject("vlg", village);	
				
 		 mav.addObject("stateList", stateList);
         mav.addObject("name", name);
         mav.addObject("phone", phone);
         mav.addObject("email", email);
         mav.addObject("address", address);
         mav.addObject("regNo", regNo);


		return mav;
	}
	
	@RequestMapping(value="/submitRegistration", method = RequestMethod.POST)
	public ModelAndView submitFullRegistration(HttpServletRequest request) {
	    HttpSession session = request.getSession();

	    // Retrieve page 1 data
	    String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String state = request.getParameter("state");
        String district = request.getParameter("district");
        String block = request.getParameter("block");
        String village = request.getParameter("village");
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        String facebook = request.getParameter("facebook");
        String youtube = request.getParameter("youtube");
        String instagram = request.getParameter("instagram");
        String twitter = request.getParameter("twitter");
        String linkedin = request.getParameter("linkedin");
        String regNoParam = request.getParameter("regNo");
        
        String regNo = watershedMahotsavService.saveMahotsaveData(name, phone, email, address, Integer.parseInt(state), Integer.parseInt(district), Integer.parseInt(block), 
        		Integer.parseInt(village), longitude, latitude, facebook, youtube, instagram, twitter, linkedin, regNoParam, request);
	    
        ModelAndView mv = new ModelAndView("mahotsav/registrationSuccess");
        mv.addObject("regNo", regNo);
        mv.addObject("message", "Your registration number is " + regNo + ". Do you want to upload another video?");
        return mv;
	}
	
	@RequestMapping(value="/uploadAnotherVideo", method = RequestMethod.GET)
	public ModelAndView uploadAnotherVideo(HttpServletRequest request, HttpServletResponse response, @RequestParam("regNo") String regNo)
	{
		WatershedMahotsavRegistration registration = watershedMahotsavService.findByRegNo(regNo);
		if (registration == null) {
	        // fallback if regNo invalid
	        return new ModelAndView("redirect:/registerMahotsav");
	    }

	    ModelAndView mav = new ModelAndView("mahotsav/mahotsavOtherDtl");
	    mav.addObject("name", registration.getRegName());
	    mav.addObject("phone", registration.getPhno());
	    mav.addObject("email", registration.getEmail());
	    mav.addObject("address", registration.getAddress());
	    mav.addObject("stateList", stateMasterService.getAllState());
	    mav.addObject("regNo", regNo);  // optional to show in next JSP
	    return mav;
	}
}
	
