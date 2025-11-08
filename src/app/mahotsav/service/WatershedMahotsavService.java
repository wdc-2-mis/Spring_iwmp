package app.mahotsav.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import app.mahotsav.model.WatershedMahotsavRegistration;

@Service("watershedMahotsavService")
public interface WatershedMahotsavService {

	String saveMahotsaveData(String name, String phone, String email, String address, int state, int district,
			int block, int village, String longitude, String latitude, String facebook, String youtube,
			String instagram, String twitter, String linkedin, String regNoParam, HttpServletRequest request);

	WatershedMahotsavRegistration findByRegNo(String regNo);

	
	

}
