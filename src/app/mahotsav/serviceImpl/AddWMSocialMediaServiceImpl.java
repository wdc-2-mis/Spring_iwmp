package app.mahotsav.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.SocialMediaReport;
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

	@Override
	public List<SocialMediaReport> getSocialMediaReport(String stateCode, String districtCode) {
		return addWMSocialMediaDao.getSocialMediaReport(stateCode, districtCode);
	}

	@Override
	public List<SocialMediaReport> getWMSocialMediaReport() {
		return addWMSocialMediaDao.getWMSocialMediaReport();
	}

	@Override
	public List<SocialMediaReport> getDistWMSocialMediaReport(Integer stcd) {
		return addWMSocialMediaDao.getDistWMSocialMediaReport(stcd);
	}

	@Override
	public WatershedMahotsavRegistration findByUserRegNoAndEmail(String regNo, String email) {
		return addWMSocialMediaDao.findByUserRegNoAndEmail(regNo, email);
	}

}
