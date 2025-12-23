package app.mahotsav.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import app.mahotsav.bean.WMMediaViewsDetailsBean;
import app.mahotsav.bean.WatershedMahotsavBean;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.model.WatershedMahotsavVideoDetails;

public interface WatershedMahotsavDao {

	String saveMahotsaveData(String name, String phone, String email, String address, int state, int district,
			int block, int village, String longitude, String latitude, String facebook, String youtube,
			String instagram, String twitter, String linkedin, String regNoParam, String mediaType, HttpServletRequest request);

	WatershedMahotsavRegistration findByRegNo(String regNo);

	boolean emailAlreadyExists(String email);

	boolean phoneAlreadyExists(String phone);

	boolean mediaAlreadyExists(String media);
	
	List<WatershedMahotsavVideoDetails> findAllMahotsaveVideo();
	
	List<WatershedMahotsavBean> getWatershedMahotsavVideoDetails(String regno);
	
	String saveWMMediaViewDetails(WMMediaViewsDetailsBean bean, HttpServletRequest request);
	
	List<WMMediaViewsDetailsBean> getWMMediaViewsDetails(String regno, Integer videoid);
	
	String comWMMediaViewDetails(String regno, Integer videoid);
	
	List<String> getWMMediaScrnshtUrl(Integer videoid);
	
}
