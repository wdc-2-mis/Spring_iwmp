package app.mahotsav.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.dao.AddWMSocialMediaDao;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.service.AddWMSocialMediaService;

@Service("AddWMSocialMediaService")
public class AddWMSocialMediaServiceImpl implements AddWMSocialMediaService {

	@Autowired
	AddWMSocialMediaDao addWMSocialMediaDao;
	
	@Override
	public WatershedMahotsavRegistration findByUserRegNo(String regNo) {
		return addWMSocialMediaDao.findByUserRegNo(regNo);
	}

}
