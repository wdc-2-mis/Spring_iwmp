package app.watershedyatra.service;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;


@Service("WatershedYatraService")
public interface WatershedYatraService {
	
	LinkedHashMap<Integer,String> getDistrictList(int stcode);
	
	LinkedHashMap<String, Integer> getWatershedYatraBlock(Integer distcd);
	
	LinkedHashMap<String, Integer> getWatershedYatraGPs(Integer blkCode);
	
	LinkedHashMap<String, Integer> getWatershedYatraVillage(Integer gpCode);

}
