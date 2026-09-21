package app.swachhata.dao;

import java.util.LinkedHashMap;

public interface WatershedSwachhataDao {
	
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block);

}
