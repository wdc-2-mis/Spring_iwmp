package app.mahotsav.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;


@Service("WatershedMahotsavInaugurationService")
public interface WatershedMahotsavInaugurationService {
	
	public String saveMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session);
	
	public List<InaugurationMahotsavBean> getregisterInaugurationDetails(Integer stcd);
	public List<InaugurationMahotsavBean> getregisterInaugurationDetailsComp(Integer stcd);
	
	String deleteMahotsavInaugurationDetails(List<Integer> assetid, String userid);
	
	String completeMahotsavInaugurationDetails(List<Integer> assetid, String userid);
	
	boolean checkMahotsavInaugurationExits(Integer stCode);
	
	public List<InaugurationMahotsavBean> getMahotsavInaugurationEditList(Integer inauguaration_id);
	
	public String updateMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session);
	
	List<String> getImageMahotsavInaugurationId(Integer inaugId);
	
	

}
