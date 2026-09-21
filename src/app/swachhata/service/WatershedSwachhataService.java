package app.swachhata.service;

import java.util.LinkedHashMap;

public interface WatershedSwachhataService {
	
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block);

}
