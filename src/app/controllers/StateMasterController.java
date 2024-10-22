package app.controllers;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.service.StateMasterService;

@Controller("stateMasterController")
public class StateMasterController {
	
	@Autowired
	StateMasterService stateMasterService;
	
	// getAllState use to get all state list
	@RequestMapping(value="/getAllState", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getAllState(HttpServletRequest request)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(stateMasterService.getAllState());
		return map;
	}
	
	// getStateByStateCode use to get state list by statecode
	@RequestMapping(value="/getStateByStateCode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getStateByStateCode(HttpServletRequest request, @RequestParam(value ="id") Integer stateCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(stateMasterService.getStateByStateCode(stateCode));
		return map;
	}

}
