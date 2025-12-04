package app.mahotsav.dao;

import javax.servlet.http.HttpServletRequest;

import app.mahotsav.model.WatershedMahotsavRegistration;

public interface WatershedMahotsavDao {

	String saveMahotsaveData(String name, String phone, String email, String address, int state, int district,
			int block, int village, String longitude, String latitude, String facebook, String youtube,
			String instagram, String twitter, String linkedin, String regNoParam, String mediaType, HttpServletRequest request);

	WatershedMahotsavRegistration findByRegNo(String regNo);

	boolean emailAlreadyExists(String email);

	boolean phoneAlreadyExists(String phone);

	boolean mediaAlreadyExists(String media);
	

	
}
