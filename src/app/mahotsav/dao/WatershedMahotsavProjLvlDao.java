package app.mahotsav.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.mahotsav.bean.WatershedMahotsavProjectLevelBean;
import app.mahotsav.model.WatershedMahotsavProjectLevel;

public interface WatershedMahotsavProjLvlDao {
	
	public String saveMahotsavProjLvlDetails(WatershedMahotsavProjectLevelBean userfileup, HttpSession session);
	public List<WatershedMahotsavProjectLevelBean> getWatershedMahotsavAtProjLvl(Integer stcode, String loginId, Integer projid);
	public List<WatershedMahotsavProjectLevelBean> getComWatershedMahotsavAtProjLvl(Integer stcode, String loginId, Integer projid);
	public List<WatershedMahotsavProjectLevelBean> getBlksWiseWatershedMahotsavAtProjLvl(String loginId);
	String completeMahotsavProjLvlDetails(List<Integer> assetid, String userid);
	String deleteMahotsavProjLvlDetails(List<Integer> assetid, String userid);
	public List<WatershedMahotsavProjectLevelBean> getWatershedMahotsavProjLvlDtlForEdit(Integer id);
	public String updateMahotsavProjLvlDetails(WatershedMahotsavProjectLevelBean userfileup, HttpSession session);
	List<String> getImageMahotsavProjLvl(Integer id);
	public LinkedHashMap<Integer, String> getBlockbyProjId(Integer projid);

}
