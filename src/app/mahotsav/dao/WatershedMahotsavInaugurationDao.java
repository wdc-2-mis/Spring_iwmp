package app.mahotsav.dao;

import javax.servlet.http.HttpSession;

import app.mahotsav.bean.InaugurationMahotsavBean;

public interface WatershedMahotsavInaugurationDao {
	
	public String saveMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session);

}
