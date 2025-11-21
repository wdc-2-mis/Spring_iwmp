package app.mahotsav.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import app.mahotsav.bean.WatershedMahotsavProjectLevelBean;
import app.mahotsav.model.WatershedMahotsavProjectLevel;

public interface WatershedMahotsavProjLvlService {

	public String saveMahotsavProjLvlDetails(WatershedMahotsavProjectLevelBean userfileup, HttpSession session);
	public List<WatershedMahotsavProjectLevelBean> getWatershedMahotsavAtProjLvl(Integer stcode, String loginId);
	public List<WatershedMahotsavProjectLevelBean> getComWatershedMahotsavAtProjLvl(Integer stcode, String loginId);
}
