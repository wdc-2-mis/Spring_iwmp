package app.swachhata.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.swachhata.controller.WatershedSwachhataBean;

public interface WatershedSwachhataService {
	
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block);
	public String saveWatershedSwachhataDetails(WatershedSwachhataBean userfileup, HttpSession session);
	public List<WatershedSwachhataBean> getWatershedSwachhataAtProj(String loginId);
	String completeWatershedSwachhataDetails(List<Integer> assetid, String userid);
	String deleteWatershedSwachhataDetails(List<Integer> assetid, String userid);
	public List<String> getImageSwachhataProjLvlId(Integer swachhataid);

}
