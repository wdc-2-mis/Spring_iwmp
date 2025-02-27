package app.watershedyatra.service;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

@Service("WatershedYatraPIALevelService")
public interface WatershedYatraPIALevelService {
	
	LinkedHashMap<Integer,String> getBlockListpia(String userid);
	LinkedHashMap<String, Integer> getWatershedYatraAtPiaGPs(Integer blkCode, String userid);
	LinkedHashMap<String, Integer> getWatershedYatraAtPiaVillage(Integer gpCode, String userid);

}
