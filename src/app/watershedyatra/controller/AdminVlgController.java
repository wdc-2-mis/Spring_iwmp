package app.watershedyatra.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.Login;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.UserService;
import app.watershedyatra.bean.AdminVlgBean;
import app.watershedyatra.model.WatershedYatVillDuplicate;
import app.watershedyatra.service.AdminVlgService;

@Controller("AdminVlgController")
public class AdminVlgController {

	HttpSession session;

	@Autowired
	AdminVlgService ser;

	@Autowired(required = true)
	MenuController menuController;

	@Autowired
	StateMasterService stateMasterService;

	@Autowired(required = true)
	public UserService userService;

	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> blockList;
	private Map<String, String> gpList;
	private Map<String, String> vlgList;

	@RequestMapping(value = "/getDupWatershedYatraVlg", method = RequestMethod.GET)
	public ModelAndView getDupWatershedYatraVlg(HttpServletRequest request, HttpServletResponse response) {
		
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();

		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String block = request.getParameter("block");
		String gp = request.getParameter("gp");
		
		List<AdminVlgBean> list = new ArrayList<AdminVlgBean>();

		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("WatershedYatra/watershedYatraAdminVillage");

				mav.addObject("menu", menuController.getMenuUserId(request));
				
				stateList = stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
				
				if (userState != null && !userState.equals("") && !userState.equals("0")) {
					districtList = userService.getDistrictList(Integer.parseInt(userState));
					mav.addObject("districtList", districtList);
				}
				mav.addObject("district", district);

				if (district != null && !district.equalsIgnoreCase("") && !district.equals("0")) {
					blockList = ser.getBlockList(Integer.parseInt(userState), Integer.parseInt(district));
					mav.addObject("blockList", blockList);
				}
				mav.addObject("block", block);

				if (block != null && !block.equalsIgnoreCase("") && !block.equals("0")) {
					gpList = ser.getGPList(Integer.parseInt(block));
					mav.addObject("gpList", gpList);
				}
				mav.addObject("gp", gp);
				
				if (gp != null && !gp.equalsIgnoreCase("") && !gp.equals("0")) {
					vlgList = ser.getVlgList(Integer.parseInt(gp));
					mav.addObject("vlgList", vlgList);
				}
				
				list=ser.getDupWatershedYatraVlgData();
				mav.addObject("dupWatershedYatraVlgDataList", list);
				mav.addObject("dupWatershedYatraVlgDataListSize", list.size());
				
			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/saveDupWatershedYatraVlgData", method = RequestMethod.POST)
	public ModelAndView saveDupWatershedYatraVlgData(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();

		String st_code = session.getAttribute("stateCode").toString();

		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String block = request.getParameter("block");
		String gp = request.getParameter("gp");
		String village= request.getParameter("village");
		String villageName= "";
		String res=null;


		List<AdminVlgBean> list = new ArrayList<AdminVlgBean>();

		if (session != null && session.getAttribute("loginID") != null) {

			mav = new ModelAndView("WatershedYatra/watershedYatraAdminVillage");


			mav.addObject("menu", menuController.getMenuUserId(request));
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			mav.addObject("stateName", stateList.get(Integer.parseInt(userState)));
			
			if (userState != null && !userState.equalsIgnoreCase(""))
				districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);
			mav.addObject("district", district);
			mav.addObject("districtName", districtList.get(district));

			if (district != null && !district.equalsIgnoreCase(""))
				blockList = ser.getBlockList(Integer.parseInt(userState), Integer.parseInt(district));
			mav.addObject("blockList", blockList);
			mav.addObject("block", block);
			mav.addObject("blockName", blockList.get(block));

			if (block != null && !block.equalsIgnoreCase(""))
				gpList = ser.getGPList(Integer.parseInt(block));
			mav.addObject("gpList", gpList);
			mav.addObject("gp", gp);
			mav.addObject("gpName", gpList.get(gp));
			
			if (gp != null && !gp.equalsIgnoreCase(""))
				vlgList = ser.getVlgList(Integer.parseInt(gp));
			mav.addObject("vlgList", vlgList);
			mav.addObject("village", village);
			mav.addObject("villageName", vlgList.get(village));
			
			villageName=vlgList.get(village);
			mav.addObject("villageName", villageName);
			
			res= ser.saveDupWatershedYatraVlgData(village, villageName, session);
			
			if (res.equals("success")) {
				redirectAttributes.addFlashAttribute("message", "Record saved successfully!");
	        } else {
	        	redirectAttributes.addFlashAttribute("message", "Record not saved successfully!");
	        }
			
			list=ser.getDupWatershedYatraVlgData();
			mav.addObject("dupWatershedYatraVlgDataList", list);
			mav.addObject("dupWatershedYatraVlgDataListSize", list.size());
			
			
			mav = new ModelAndView("redirect:/getDupWatershedYatraVlg");

		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value = "/deleteVlgData", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView deleteVlgData(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes, @RequestParam Integer watershed_id) {
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		String res="";

		if (session != null && session.getAttribute("loginID") != null) {
			
			res= ser.deleteVlgData(watershed_id);
			
			if (res.equals("success")) {
				redirectAttributes.addFlashAttribute("message", "Record deleted successfully!");
	        } else {
	        	redirectAttributes.addFlashAttribute("message", "Record not deleted!");
	        }
			mav = new ModelAndView("redirect:/getDupWatershedYatraVlg");

		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}

}
