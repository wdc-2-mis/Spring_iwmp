package app.watershedyatra.dao;

import java.util.LinkedHashMap;

public interface WatershedYatraDao {
	
	LinkedHashMap<Integer,String> getDistrictList(int stcode);
	LinkedHashMap<String, Integer> getWatershedYatraBlock(Integer distcd);
	
	LinkedHashMap<String, Integer> getWatershedYatraGPs(Integer blkCode);
	LinkedHashMap<String, Integer> getWatershedYatraVillage(Integer gpCode);

}
