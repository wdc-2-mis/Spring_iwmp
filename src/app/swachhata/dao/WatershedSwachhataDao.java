package app.swachhata.dao;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;

import app.swachhata.controller.WatershedSwachhataBean;

public interface WatershedSwachhataDao {
	
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block);
	public String saveWatershedSwachhataDetails(WatershedSwachhataBean userfileup, HttpSession session);

}
