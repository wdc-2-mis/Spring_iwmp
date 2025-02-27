package app.watershedyatra.dao;

import java.util.LinkedHashMap;

public interface WatershedYatraPIALevelDao {
	
	LinkedHashMap<Integer,String> getBlockListpia(String userid);
	LinkedHashMap<String, Integer> getWatershedYatraAtPiaGPs(Integer blkCode, String userid);
	LinkedHashMap<String, Integer> getWatershedYatraAtPiaVillage(Integer gpCode, String userid);

}
