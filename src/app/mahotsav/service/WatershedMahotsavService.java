package app.mahotsav.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.model.WatershedMahotsavVideoDetails;

@Service("watershedMahotsavService")
public interface WatershedMahotsavService {

	String saveMahotsaveData(String name, String phone, String email, String address, int state, int district,
			int block, int village, String longitude, String latitude, String facebook, String youtube,
			String instagram, String twitter, String linkedin, String regNoParam, String mediaType, HttpServletRequest request);

	WatershedMahotsavRegistration findByRegNo(String regNo);

	boolean emailAlreadyExists(String email);

	boolean phoneAlreadyExists(String phone);

	boolean mediaAlreadyExists(String media);

	List<WatershedMahotsavVideoDetails> findAllMahotsaveVideo();

	
	

}
