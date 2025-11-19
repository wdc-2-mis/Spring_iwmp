package app.mahotsav.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;

@Service("WatershedMahotsavInaugurationService")
public interface WatershedMahotsavInaugurationService {
	
	public String saveMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session);

}
