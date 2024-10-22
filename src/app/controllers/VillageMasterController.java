package app.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.bean.ProjectLocationBean;
import app.model.master.IwmpVillage;
import app.service.VillageMasterService;

@Controller("villageMasterController")
public class VillageMasterController {
	
	@Autowired
	VillageMasterService service;
	
	@RequestMapping(value="/getVillageBlockWise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getVillageBlockWise(HttpServletRequest request, @RequestParam(value ="bcode") Integer bcode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(service.getVillageBlockWise(bcode));
		return map;
	}
	
	@RequestMapping(value="/getVillageByVillageCode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<ProjectLocationBean>> getVillageByVillageCode(HttpServletRequest request, @RequestParam(value ="vcode") List<Integer> vcode)
	{
		LinkedHashMap<Integer,List<ProjectLocationBean>> map = new LinkedHashMap<Integer,List<ProjectLocationBean>>();
		List<ProjectLocationBean> list = new ArrayList<ProjectLocationBean>();
		try {
			list.addAll(service.getVillageByVillageCode(vcode));
			map.put(0, list);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
}
