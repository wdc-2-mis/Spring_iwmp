package app.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ProfileBean;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaService;
import app.model.FlexiFundDetails;
import app.model.FlexiFundPhoto;
import app.service.FlexFundService;
import app.service.ProfileService;
import app.watershedyatra.service.WatershedYatraService;
import java.io.OutputStream;

@Controller("flexiFundController")
public class FlexiFundController {

	
	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedYatraService ser;
	
	@Autowired
	FlexFundService service;
	
	@Autowired
	JanbhagidariPratiyogitaService serk;
	
	@RequestMapping(value = "/flexiFundAtSlna", method = RequestMethod.GET)
	public ModelAndView flexiFundAtSlna(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				
				mav = new ModelAndView("flexiFundAtSlna");
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
				mav.addObject("activity", service.getflexiactivity());
				mav.addObject("distName", distName);
			}
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}
			catch (Exception e) {
				e.printStackTrace();
			}
			return mav;
}

	@RequestMapping(value = "/getFlexFundProject", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getFlexFundProject(HttpServletRequest request, @RequestParam("dCode") int dCode) {
		return serk.getJanbhagidariPratiyogitaProject(dCode);
	}
	
	@RequestMapping(value = "/getFlexFundGramPanchayat", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getFlexFundGramPanchayat(HttpServletRequest request, @RequestParam("pCode") int pCode) {
		return service.getFlexiFundGM(pCode);
	}
	
	@RequestMapping(value = "/getFlexiFundData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getFlexiFundData(
	        @RequestParam Integer projid,
	        @RequestParam Integer panchayat) {

	    Map<String, Object> response = new HashMap<>();

	    List<FlexiFundDetails> detailsList =
	            service.getFlexiFundByProjectAndGcode(projid, panchayat);

	    if (detailsList == null || detailsList.isEmpty()) {
	        response.put("status", "NEW");
	        return response;
	    }

	    String status = detailsList.get(0).getStatus();

	    List<Map<String, Object>> dtoList = new ArrayList<>();

	    List<Map<String, Object>> draftList = new ArrayList<>();
	    List<Map<String, Object>> completeList = new ArrayList<>();
	    for (FlexiFundDetails d : detailsList) {

	        Map<String, Object> dto = new HashMap<>();

	        dto.put("actId", d.getActivity().getActId());
	        dto.put("actName", d.getActivity().getActName());
	        dto.put("workDesc", d.getWorkDesc());
	        dto.put("est_cost", d.getEst_cost());
	        dto.put("ffCost", d.getFfCost());
            dto.put("ffId", d.getFfId());
            dto.put("remark", d.getRemark());
	        List<Map<String, Object>> photos = new ArrayList<>();

	        if (d.getPhotos() != null) {
	            for (FlexiFundPhoto p : d.getPhotos()) {

	                Map<String, Object> photo = new HashMap<>();
	                photo.put("photoUrl", p.getPhotoUrl());
                    photo.put("photoId", p.getFfPhotoId());
	                photos.add(photo);
	            }
	        }

	        dto.put("photos", photos);

	        if ("C".equals(d.getStatus())) {
	            completeList.add(dto);
	        } else {
	            draftList.add(dto);
	        }
	    }


		/*
		 * response.put("status", status); response.put("data", dtoList);
		 */

	    response.put("draftData", draftList);
	    response.put("completeData", completeList);

	    if (draftList.isEmpty() && completeList.isEmpty()) {
	        response.put("status", "NEW");
	    } else {
	        response.put("status", "EXIST");
	    }
	    
	    return response;
	}
	
	
	
	@RequestMapping(value = "/deleteFlexiFundPhoto", method = RequestMethod.POST)
	@ResponseBody
	public String deletePhoto(@RequestParam("photoId") int photoId) {
		boolean result = service.deletePhotoById(photoId);
        return result ? "success" : "fail"; 
		}
	
	
	@RequestMapping(value = "/uploadFlexiFundPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadPhoto(
	        @RequestParam("photo") MultipartFile file,
	        @RequestParam("flexiFundId") int flexiFundId,
	        @RequestParam(value = "latitude", required = false) String lat,
	        @RequestParam(value = "longitude", required = false) String lon,
	        @RequestParam("projid") Integer projId,
	        @RequestParam("panchayat") Integer gcode) {

	    return service.savePhoto(file, flexiFundId, lat, lon, projId, gcode);
	}
	
	
	@RequestMapping(value = "/deleteFlexiFundRow", method = RequestMethod.POST)
	@ResponseBody
	public String deleteFlexiFundRow(@RequestParam("id") int id) {

		boolean isDeleted = service.deleteFlexiFundRow(id);

	    return isDeleted ? "success" : "fail";
	}
	
	
	@RequestMapping("/getImage")
	public void getImage(@RequestParam String name,
	                     HttpServletResponse response) throws IOException {

	    String filePath = "D:\\FlexiFund\\" + name;

	    File file = new File(filePath);

	    if (file.exists()) {

	        FileInputStream fis = new FileInputStream(file);
	        OutputStream os = response.getOutputStream();

	        response.setContentType("image/jpeg");

	        byte[] buffer = new byte[1024];
	        int len;

	        while ((len = fis.read(buffer)) != -1) {
	            os.write(buffer, 0, len);
	        }

	        fis.close();
	        os.close();
	    }
	}
	
	@RequestMapping(value = "/saveflexifund", method = RequestMethod.POST)
	@ResponseBody
	public String saveFlexiFund(HttpServletRequest request,
	        @RequestParam("activity[]") List<Integer> activityList,
	        @RequestParam("details[]") List<String> detailsList,
	        @RequestParam("totalest[]") List<Double> estCostList,
	        @RequestParam("cost[]") List<Double> costList,
	        @RequestParam(value = "photos[]", required = false) List<MultipartFile> photos,
	        @RequestParam(value = "photoRowIndex[]", required = false) List<Integer> photoCountList,
	        @RequestParam(value = "latitude[]", required = false) List<String> latitudeList,
	        @RequestParam(value = "longitude[]", required = false) List<String> longitudeList,
	        @RequestParam("projid") Integer projId,
	        @RequestParam("panchayat") Integer gcode,
	        @RequestParam("status") String status,
	        @RequestParam(value = "remark[]", required = false) List<String> remarksList) {

	    try {

	        if (photos == null) photos = new ArrayList<>();
	        if (photoCountList == null) photoCountList = new ArrayList<>();
	        if (latitudeList == null) latitudeList = new ArrayList<>();
	        if (longitudeList == null) longitudeList = new ArrayList<>();

	        String createdBy = request.getSession().getAttribute("loginID").toString();

	        boolean result = service.saveFlexiFundData(
	                projId, gcode,
	                activityList, detailsList, estCostList, costList,
	                photos, photoCountList,
	                latitudeList, longitudeList,
	                status, remarksList, createdBy, request
	        );

	        return result ? "success" : "fail";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "fail";
	    }
	}
	
	@RequestMapping(value = "/updateFlexiFund", method = RequestMethod.POST)
	@ResponseBody
	public String updateFlexiFund(
	        @RequestParam("rowIds[]") List<Integer> rowIds,
	        @RequestParam("activity[]") List<Integer> activityList,
	        @RequestParam("details[]") List<String> detailsList,
	        @RequestParam("totalest[]") List<Double> estCostList,
	        @RequestParam("cost[]") List<Double> costList,
	        @RequestParam(value = "photos[]", required = false) List<MultipartFile> photos,
	        @RequestParam(value = "photoRowIndex[]", required = false) List<Integer> photoRowIndex,
	        @RequestParam("status") String status,
	        @RequestParam(value = "remark[]", required = false) List<String> remarksList,
	        HttpServletRequest request) {

	    try {

	        String user = (String) request.getSession().getAttribute("userId");

	        boolean result = service.updateFlexiFundData(
	                rowIds,
	                activityList,
	                detailsList,
	                estCostList,
	                costList,
	                photos,
	                photoRowIndex,
	                status,
	                remarksList,
	                user
	        );

	        return result ? "success" : "fail";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "fail";
	    }
	}
}