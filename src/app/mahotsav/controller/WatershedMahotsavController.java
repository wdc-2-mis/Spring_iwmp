package app.mahotsav.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.ProfileBean;
import app.mahotsav.bean.WMMediaViewsDetailsBean;
import app.mahotsav.bean.WatershedMahotsavBean;
import app.mahotsav.bean.WatershedMahotsavProjectLevelBean;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.model.WatershedMahotsavVideoDetails;
import app.mahotsav.service.WatershedMahotsavService;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;
import app.util.MediaTypeDetector;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.ServletContext;
import java.io.InputStream;



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
	
	@Autowired
    private ServletContext servletContext;

	
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
	
	@RequestMapping(value="/checkEmailExists", method = RequestMethod.POST)
	@ResponseBody
	public String checkEmailExists(@RequestParam("email") String email) {
	    boolean exists = watershedMahotsavService.emailAlreadyExists(email);
	    return exists ? "exists" : "not_exists";
	}
	
	@RequestMapping(value="/checkPhoneExists", method = RequestMethod.POST)
	@ResponseBody
	public String checkPhoneExists(@RequestParam("phone") String phone) {
	    boolean exists = watershedMahotsavService.phoneAlreadyExists(phone);
	    return exists ? "exists" : "not_exists";
	}
	
	@RequestMapping(value="/checkmediaExists", method = RequestMethod.POST)
	@ResponseBody
	public String checkfbExists(@RequestParam("media") String media) {
	    boolean exists = watershedMahotsavService.mediaAlreadyExists(media);
	    return exists ? "exists" : "not_exists";
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
        String mediaType = request.getParameter("urlType");
        
        
        ModelAndView mv = new ModelAndView("mahotsav/registrationSuccess");
        try {
            String regNo = watershedMahotsavService.saveMahotsaveData(name, phone, email, address,
                    Integer.parseInt(state), Integer.parseInt(district), Integer.parseInt(block),
                    Integer.parseInt(village), longitude, latitude, facebook, youtube, instagram, twitter,
                    linkedin, regNoParam, mediaType, request);

            if (regNo != null && !regNo.trim().isEmpty()) {
            mv.setViewName("mahotsav/registrationSuccess");
            mv.addObject("regNo", regNo);
            mv.addObject("message", "Your registration number is " + regNo + ". Do you want to upload another video or image?");
            }
            else {
            	 mv.setViewName("mahotsav/registrationSuccess");
            	 mv.addObject("errorMessage", "An error occurred during registration. Please try again later  or call 9990133538");
            }
        
        } catch (Exception e) {
            mv.addObject("errorMessage", "An error occurred during registration. Please try again later  or call 9990133538");
            e.printStackTrace();  // Log the stack trace for debugging purposes
        }

        return mv;
    }
	
	
	@RequestMapping(value="/uploadAnotherVideo", method = RequestMethod.GET)
	public ModelAndView uploadAnotherVideo(HttpServletRequest request, HttpServletResponse response, @RequestParam("regNo") String regNo)
	{
		WatershedMahotsavRegistration registration = watershedMahotsavService.findByRegNo(regNo);
		Integer stCode = 0;
		String stName = null;
		if (registration == null) {
	        return new ModelAndView("redirect:/registerMahotsav");
	    }

		for(WatershedMahotsavVideoDetails v : registration.getWatershedMahotsavVideoDetails()) {
			stCode = v.getIwmpState().getStCode();
			//stName = v.getIwmpState().getStName();
		}
	    
		ModelAndView mav = new ModelAndView("mahotsav/mahotsavOtherDtl");
		
		if(stCode!=null  && stCode != 0) {
 			districtList = ser.getDistrictList(stCode);
 			mav.addObject("districtList", districtList);}
		
	    mav.addObject("name", registration.getRegName());
	    mav.addObject("phone", registration.getPhno());
	    mav.addObject("email", registration.getEmail());
	    mav.addObject("address", registration.getAddress());
	    mav.addObject("stateList", stateMasterService.getAllState());
	    mav.addObject("regNo", regNo);  
	    mav.addObject("state", stCode);
	    
	    return mav;
	}
	
	@RequestMapping(value="/wdcpmksyActivityWork", method = RequestMethod.GET)
	public ModelAndView wdcpmksyActivityWork(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("mahotsav/wdcpmksyActivityWork");
		 stateList = stateMasterService.getAllState();
         mav.addObject("stateList", stateList);
         
		return mav;
	}
	
	
	
    @GetMapping("/wdcpmksyActivityWorkOpen")
    public ResponseEntity<InputStreamResource> openPdf(@RequestParam("state") String state ) {

        try {

          //  System.out.println("State code received: " + state);

            String pdfFileName = state+".pdf";

            String pdfPath = "/WEB-INF/workactivity/" + pdfFileName;

            InputStream pdfStream = servletContext.getResourceAsStream(pdfPath);

            if (pdfStream == null) {

                pdfStream = servletContext.getResourceAsStream("/WEB-INF/workactivity/"+state+".pdf");

                if (pdfStream == null) {

                    return ResponseEntity.notFound().build();

                }

            }

            return ResponseEntity.ok()

                    .contentType(MediaType.APPLICATION_PDF)

                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + pdfFileName)

                    .body(new InputStreamResource(pdfStream));


        } 
        catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError().build();

        }

    }

    @RequestMapping("/mediaReport")
    public String mediaReport(Model model) {

        List<WatershedMahotsavVideoDetails> list = watershedMahotsavService.findAllMahotsaveVideo();

        for (WatershedMahotsavVideoDetails item : list) {
            String type = MediaTypeDetector.detectMediaType(item.getMediaUrl()); // VIDEO / IMAGE / NA

            String mediaStr; // now String, not Character
            switch (type.toUpperCase()) {
                case "VIDEO":
                    mediaStr = "VB";  // Video → VB
                    break;
                case "IMAGE":
                    mediaStr = "PB";  // Image → PB
                    break;
                default:
                    mediaStr = "";    // NA → leave blank
            }

            item.setMedia_type(mediaStr); 
        }

        model.addAttribute("mediaList", list);
        return "mahotsav/mediaReport";
    }

    @RequestMapping(value="/viewWMMediaUrlDetails", method = RequestMethod.POST)
	public ModelAndView viewWMMediaUrlDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam("regno") String regNo)
	{
    	List<WatershedMahotsavBean> list = new ArrayList<WatershedMahotsavBean>();
		WatershedMahotsavRegistration registration = watershedMahotsavService.findByRegNo(regNo);
		if (registration == null) {
	        return new ModelAndView("redirect:/registerMahotsav");
	    }
		list = watershedMahotsavService.getWatershedMahotsavVideoDetails(regNo);
	    
		ModelAndView mav = new ModelAndView("mahotsav/viewWMSocialMediaDetails");
		
	    mav.addObject("WMList", list);
	    mav.addObject("regno", regNo);  
	    
	    return mav;
	}
    
    @SuppressWarnings("null")
	@RequestMapping(value="/addMediaViewDetails", method = RequestMethod.POST)
	public ModelAndView addMediaViewDetais(HttpServletRequest request, HttpServletResponse response)
	{
		String regNo = request.getParameter("regno");
		Integer videoid = Integer.parseInt(request.getParameter("videoid"));
		String mediatype = request.getParameter("mediatype");
		WatershedMahotsavRegistration registration = watershedMahotsavService.findByRegNo(regNo);
		if (registration == null) {
	        return new ModelAndView("redirect:/registerMahotsav");
	    }
		ModelAndView mav = new ModelAndView("mahotsav/addMediaViewDetails");
		List<WMMediaViewsDetailsBean> list = watershedMahotsavService.getWMMediaViewsDetails(regNo, videoid);
		if(list!=null && list.size()>0) {
			mav.addObject("nooflikes", list.get(0).getNo_of_likes());
			mav.addObject("noofsubs", list.get(0).getNo_of_subscriber());
			mav.addObject("noofview", list.get(0).getNo_of_views());
			mav.addObject("status", list.get(0).getStatus().toString());
			mav.addObject("file", watershedMahotsavService.getWMMediaScrnshtUrl(videoid).get(0));
			mav.addObject("viewsList", list);
			mav.addObject("viewsListSize", list.size());
		}
		mav.addObject("regno", regNo);
		mav.addObject("videoid", videoid);
		mav.addObject("mediatype", mediatype);

		return mav;
	}
    
    @RequestMapping(value = "/saveSocialMediaViewsDetails", method = RequestMethod.POST)
	public ModelAndView saveSocialMediaViewsDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadsl") WMMediaViewsDetailsBean userfileup)
			throws Exception {

		ModelAndView mav = new ModelAndView();
		String result = "fail";
		WatershedMahotsavRegistration registration = watershedMahotsavService.findByRegNo(userfileup.getRegno());
		if (registration == null) {
	        return new ModelAndView("redirect:/registerMahotsav");
	    }
		try {	
			result =  watershedMahotsavService.saveWMMediaViewDetails(userfileup, request);
				mav = new ModelAndView("mahotsav/addMediaViewDetails");
				List<WMMediaViewsDetailsBean> list = watershedMahotsavService.getWMMediaViewsDetails(userfileup.getRegno(), userfileup.getVideoid());
				if(list!=null && list.size()>0) {
					mav.addObject("nooflikes", list.get(0).getNo_of_likes());
					mav.addObject("noofsubs", list.get(0).getNo_of_subscriber());
					mav.addObject("noofview", list.get(0).getNo_of_views());
					mav.addObject("status", list.get(0).getStatus().toString());
					mav.addObject("file", watershedMahotsavService.getWMMediaScrnshtUrl(userfileup.getVideoid()).get(0));
					mav.addObject("viewsList", list);
					mav.addObject("viewsListSize", list.size());
				}
				mav.addObject("regno", userfileup.getRegno());
				mav.addObject("videoid", userfileup.getVideoid());
				mav.addObject("mediatype", userfileup.getMediatype());

				if (result.equals("success")) {
					mav.addObject("result", "Data saved Successfully");
				}else if (result.equals("updated")) {
					mav.addObject("result", "Data Updated Successfully");
				}
				else {
					mav.addObject("result", "Data not saved");
				} 
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
    
    
    @RequestMapping(value = "/comSocialMediaViewsDetails", method = RequestMethod.POST)
   	public ModelAndView comSocialMediaViewsDetails(HttpServletRequest request, HttpServletResponse response,
   			RedirectAttributes redirectAttributes)
   			throws Exception {

   		ModelAndView mav = new ModelAndView();
   		String regno = request.getParameter("regno");
   		Integer videoid = Integer.parseInt(request.getParameter("videoid"));
   		String mediatype = request.getParameter("mediatype");
   		String result = "fail";
   		WatershedMahotsavRegistration registration = watershedMahotsavService.findByRegNo(regno);
   		if (registration == null) {
   	        return new ModelAndView("redirect:/registerMahotsav");
   	    }
   		try {
   			result =  watershedMahotsavService.comWMMediaViewDetails(regno, videoid);
   				mav = new ModelAndView("mahotsav/addMediaViewDetails");
   				List<WMMediaViewsDetailsBean> list = watershedMahotsavService.getWMMediaViewsDetails(regno, videoid);
				if(list!=null && list.size()>0) {
					mav.addObject("nooflikes", list.get(0).getNo_of_likes());
					mav.addObject("noofsubs", list.get(0).getNo_of_subscriber());
					mav.addObject("noofview", list.get(0).getNo_of_views());
					mav.addObject("status", list.get(0).getStatus().toString());
					mav.addObject("file", watershedMahotsavService.getWMMediaScrnshtUrl(videoid).get(0));
					mav.addObject("viewsList", list);
					mav.addObject("viewsListSize", list.size());
				}
   				mav.addObject("regno", regno);
   				mav.addObject("videoid", videoid);
   				mav.addObject("mediatype", mediatype);
   				if (result.equals("success")) {
   					mav.addObject("result", "Data Completed Successfully");
   				} 
   				else {
   					mav.addObject("result1", "Data not Completed");
   				} 
   				
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		return mav;
   	}

	
}
	
