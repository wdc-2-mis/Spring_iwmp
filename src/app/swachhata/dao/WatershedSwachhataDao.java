package app.swachhata.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.swachhata.controller.WatershedSwachhataBean;

public interface WatershedSwachhataDao {
	
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block);
	public String saveWatershedSwachhataDetails(WatershedSwachhataBean userfileup, HttpSession session);
	public List<WatershedSwachhataBean> getWatershedSwachhataAtProj(String loginId);
	String completeWatershedSwachhataDetails(List<Integer> assetid, String userid);
	String deleteWatershedSwachhataDetails(List<Integer> assetid, String userid);
	public List<String> getImageSwachhataProjLvlId(Integer swachhataid);
	public List<WatershedSwachhataBean> getWatershedSwachhataidProjLvlEdit(Integer id);
	public String updateWatershedSwachhataDetails(WatershedSwachhataBean userfileup, HttpSession session);
	List<WatershedSwachhataBean> getProjectLevelSwachhataStateWise(Integer stCode);
	public List<String> getImageByStcode(Integer stcode);
	public List<WatershedSwachhataBean> getdistWSProjLvlProgRpt(int stCode);
	public List<String> getImageByDcode(Integer dcode);
	boolean checkWatershedSwachhataVillageExits(Integer vCode);

}
