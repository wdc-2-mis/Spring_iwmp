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

import app.service.BlockMasterService;
import app.service.DistrictMasterService;

@Controller("blockMasterController")
public class BlockMasterController {
	@Autowired
	BlockMasterService blockMasterService;
	
	// getBlockByDistCode will return block list by providing state code and district code.
	@RequestMapping(value="/getBlockByDistCode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getBlockByDistCode(HttpServletRequest request, @RequestParam(value ="sCode") Integer stateCode, @RequestParam(value ="dCode") Integer districtCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(blockMasterService.getBlockByDistCode(stateCode,districtCode));
		return map;
	}

}
