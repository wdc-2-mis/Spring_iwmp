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

import app.service.ProjectMasterService;
@Controller("projectMasterController")
public class ProjectMasterController {
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	// getProjectByStCodeDistCode will return project list by providing state code and district code.
		@RequestMapping(value="/getProjectByDcode", method = RequestMethod.POST)
		@ResponseBody
		public LinkedHashMap<String,String> getProjectByDcode(HttpServletRequest request,@RequestParam(value ="dCode") Integer dCode)
		{
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("0", "All");
			map.putAll(projectMasterService.getProjectByDcode(dCode));
			return map;
		}
		
		
		
		// getProjectByStCodeDistCode will return project list by providing state code and district code.
				@RequestMapping(value="/getProjectByRegId", method = RequestMethod.POST)
				@ResponseBody
				public LinkedHashMap<Integer,String> getProjectByRegId(HttpServletRequest request,@RequestParam(value ="regId") Integer regId)
				{
					LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
					map.putAll(projectMasterService.getProjectByRegId(regId));
					return map;
				}

//get only project name and code using dcode 
				@RequestMapping(value="/getProjNACByDcode", method = RequestMethod.POST)
				@ResponseBody
				public LinkedHashMap<Integer,String> getProjNACByDcode(HttpServletRequest request,@RequestParam(value ="dCode") Integer dCode)
				{
					LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
					map.put(0, "--All--");
					map.putAll(projectMasterService.getProjNACByDcode(dCode));
					return map;
				}
				
				//get only project name and code using dcode 
				@RequestMapping(value="/getProjBystCodedCode", method = RequestMethod.POST)
				@ResponseBody
				public LinkedHashMap<Integer,String> getProjBystCodedCode(HttpServletRequest request,@RequestParam(value ="stCode") Integer stCode,@RequestParam(value ="dCode") Integer dCode)
				{
					LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
					map.put(0, "--All--");
					map.putAll(projectMasterService.getProjBystCodedCode(stCode,dCode));
					return map;
				}
}
