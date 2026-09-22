package app.swachhata.service;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;

import app.swachhata.controller.WatershedSwachhataBean;

public interface WatershedSwachhataService {
	
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block);
	public String saveWatershedSwachhataDetails(WatershedSwachhataBean userfileup, HttpSession session);

}
