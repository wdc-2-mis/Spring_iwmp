package app.mahotsav.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.WMMediaViewsDetailsBean;
import app.mahotsav.bean.WatershedMahotsavBean;
import app.mahotsav.dao.WatershedMahotsavDao;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.model.WatershedMahotsavVideoDetails;
import app.mahotsav.service.WatershedMahotsavService;

@Service("watershedMahotsavService")
public class WatershedMahotsavServiceImpl implements WatershedMahotsavService{

	@Autowired
	WatershedMahotsavDao watershedMahotsavDao;

	@Override
	public String saveMahotsaveData(String name, String phone, String email, String address, int state, int district,
			int block, int village, String longitude, String latitude, String facebook, String youtube,
			String instagram, String twitter, String linkedin, String regNoParam, String mediaType, HttpServletRequest request) {
		
		return watershedMahotsavDao.saveMahotsaveData(name, phone, email, address, state, district, block, village, longitude, latitude, facebook, youtube, instagram, twitter, linkedin, regNoParam, mediaType, request);
	}

	@Override
	public WatershedMahotsavRegistration findByRegNo(String regNo) {
		
		return watershedMahotsavDao.findByRegNo(regNo);
	}

	@Override
	public boolean emailAlreadyExists(String email) {
		return watershedMahotsavDao.emailAlreadyExists(email);
	}

	@Override
	public boolean phoneAlreadyExists(String phone) {
		// TODO Auto-generated method stub
		return watershedMahotsavDao.phoneAlreadyExists(phone);
	}

	@Override
	public boolean mediaAlreadyExists(String media) {
		// TODO Auto-generated method stub
		return watershedMahotsavDao.mediaAlreadyExists(media);
	}

	@Override
	public List<WatershedMahotsavVideoDetails> findAllMahotsaveVideo() {
		// TODO Auto-generated method stub
		return watershedMahotsavDao.findAllMahotsaveVideo();
	}

	@Override
	public List<WatershedMahotsavBean> getWatershedMahotsavVideoDetails(String regno) {
		// TODO Auto-generated method stub
		return watershedMahotsavDao.getWatershedMahotsavVideoDetails(regno);
	}

	@Override
	public String saveWMMediaViewDetails(WMMediaViewsDetailsBean bean, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return watershedMahotsavDao.saveWMMediaViewDetails(bean, request);
	}

	@Override
	public List<WMMediaViewsDetailsBean> getWMMediaViewsDetails(String regno, Integer videoid) {
		// TODO Auto-generated method stub
		return watershedMahotsavDao.getWMMediaViewsDetails(regno, videoid);
	}

	@Override
	public String comWMMediaViewDetails(String regno, Integer videoid) {
		// TODO Auto-generated method stub
		return watershedMahotsavDao.comWMMediaViewDetails(regno, videoid);
	}

	@Override
	public List<String> getWMMediaScrnshtUrl(Integer videoid) {
		// TODO Auto-generated method stub
		return watershedMahotsavDao.getWMMediaScrnshtUrl(videoid);
	}
	
	

	
	
	
}
