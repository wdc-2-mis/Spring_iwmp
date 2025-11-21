package app.mahotsav.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import app.mahotsav.bean.InaugurationMahotsavBean;

public interface WatershedMahotsavInaugurationDao {
	
	public String saveMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session);
	public List<InaugurationMahotsavBean> getregisterInaugurationDetails(Integer stcd);

}
