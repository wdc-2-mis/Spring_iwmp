package app.mahotsav.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;



@Controller("watershedMahotsavController")
public class WatershedMahotsavController {

	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	private Map<Integer, String> stateList;
	
	private Map<String, String> districtList;
	
	private Map<String, String> blockList;
	
	@RequestMapping(value="/registerMahotsav", method = RequestMethod.GET)
	public ModelAndView registerMahotsav(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("mahotsav/mahotsavRegister");
		return mav;
	}
	
	
	@RequestMapping(value="/otherMahotsav", method = RequestMethod.POST)
	public ModelAndView otherMahotsav(HttpServletRequest request, HttpServletResponse response)
	{
		 ModelAndView mav = new ModelAndView("mahotsav/mahotsavOtherDtl");
         String name = request.getParameter("name");
         String phone = request.getParameter("phone");
         String email = request.getParameter("email");
         String address = request.getParameter("userAddres");
         String userState= request.getParameter("state"); 
         String district= request.getParameter("district");
         String block= request.getParameter("block");
         
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
				
 		 mav.addObject("stateList", stateList);
         mav.addObject("name", name);
         mav.addObject("phone", phone);
         mav.addObject("email", email);
         mav.addObject("address", address);



		return mav;
	}
	
	@RequestMapping(value="/submitRegistration", method = RequestMethod.POST)
	public ModelAndView submitFullRegistration(HttpServletRequest request) {
	    HttpSession session = request.getSession();

	    // Retrieve page 1 data
	    String name = request.getParameter("name");
        System.out.println("name details:" +name);
	    
	return null;
	}
}
	
