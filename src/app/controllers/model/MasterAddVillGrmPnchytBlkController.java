package app.controllers.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import app.bean.AddVillGrmPnchytBlkBean;
import app.bean.Login;
import app.model.master.IwmpVillage;
import app.service.AddVillGrmPnchytBlkService;
import app.service.CommonService;
import app.service.StateMasterService;

@Controller
public class MasterAddVillGrmPnchytBlkController {
	
	HttpSession session;
	
	@Autowired(required = true)
	StateMasterService stateMasterService;
	
	@Autowired
	AddVillGrmPnchytBlkService addVillGrmPnchytBlkSrvc;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping(value = "/addBlockGramPanchayatVillage", method = RequestMethod.GET)
	public ModelAndView addVillGramPnchytBlk(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("master/addVillGrmPnchytBlokMaster");
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getAllStateData", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getAllStateData(HttpServletRequest request, HttpServletResponse response){
		session = request.getSession(true);
		LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		ModelAndView mav = new ModelAndView();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				map = stateMasterService.getAllState();

			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return map;
		
	}
	
	
	@RequestMapping(value = "/getTableDatabylgdCode", method = RequestMethod.POST)
	@ResponseBody
	public List<AddVillGrmPnchytBlkBean> getTableDatabylgdCode(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("lgdCode") Integer lgdCode, @ModelAttribute("masterId") Integer masterId){
		session = request.getSession(true);
		List<AddVillGrmPnchytBlkBean> res = new ArrayList<>();
		ModelAndView mav = new ModelAndView();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = addVillGrmPnchytBlkSrvc.getTableDataBylgdCode(lgdCode,masterId);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
		
	}
	
	@RequestMapping(value = "/saveVillGrmBlkAsDraft", method = RequestMethod.POST)
	@ResponseBody
	public String saveVillGrmBlkAsDraft(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("masterId") Integer masterId, @ModelAttribute("state") Integer state,
			@ModelAttribute("district") Integer district, @ModelAttribute("block") Integer block,
			@ModelAttribute("grmPnchyt") Integer grmPnchyt, @ModelAttribute("lgdCode") Integer lgdCode, 
			@ModelAttribute("villGrmBlk") String villGrmBlk) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = addVillGrmPnchytBlkSrvc.saveVillGrmBlkAsDraft(masterId, state, district, block, grmPnchyt, lgdCode, villGrmBlk);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
		
	}
	
	@RequestMapping(value = "/getBlkByDistCode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getBlkbyDistCode(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("sCode") Integer sCode, @ModelAttribute("dCode") Integer dCode){
		session = request.getSession(true);
		LinkedHashMap<Integer, String> res = new LinkedHashMap<>();
		ModelAndView mav = new ModelAndView();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = addVillGrmPnchytBlkSrvc.getBlkByDistCode(sCode,dCode);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
		
	}
	
	@RequestMapping(value = "/getGpList", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getAllSubGpcategories(HttpServletRequest request,
			@RequestParam("blockCode") int blockCode) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		LinkedHashMap<String, Integer> remap = new LinkedHashMap<>();
		try {
			map = commonService.getGramPanchayatByblockCode((int) blockCode);
			for(Map.Entry<Integer, String> chk: map.entrySet()) {
				remap.put(chk.getValue(), chk.getKey());
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return remap;
		
	}

}
