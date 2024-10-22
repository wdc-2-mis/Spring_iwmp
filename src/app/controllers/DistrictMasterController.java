package app.controllers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.service.DistrictMasterService;

@Controller("districtMasterController")
public class DistrictMasterController {
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	// getDistrictByStateCode will return district list by providing statecode
	@RequestMapping(value="/getDistrictByStateCode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getDistrictByStateCode(HttpServletRequest request, @RequestParam(value ="id") Integer stateCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(districtMasterService.getDistrictByStateCode(stateCode));
		return map;
	}
	
	// getDistrictByDistCode will return district list by providing distcode
		@RequestMapping(value="/getDistrictByDistCode", method = RequestMethod.POST)
		@ResponseBody
		public LinkedHashMap<Integer,String> getDistrictByDistCode(HttpServletRequest request, @RequestParam(value ="id") Integer distCode)
		{
			LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
			map.putAll(districtMasterService.getDistrictByDistCode(distCode));
			return map;
		}
		
		
		// getDistrictByStateCode will return district list by providing statecode
		@RequestMapping(value="/getDistrictByStateCodeWithDcode", method = RequestMethod.POST)
		@ResponseBody
		public LinkedHashMap<Integer,String> getDistrictByStateCodeWithDcode(HttpServletRequest request, @RequestParam(value ="id") Integer stateCode)
		{
			LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
			map.put(0, "All");
			map.putAll(districtMasterService.getDistrictByStateCodeWithDcode(stateCode));
			return map;
		}

}
