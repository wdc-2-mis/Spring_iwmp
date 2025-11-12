package app.mahotsav.dao;

import app.mahotsav.model.WatershedMahotsavRegistration;

public interface AddWMSocialMediaDao {
	
	WatershedMahotsavRegistration findByUserRegNo(String regNo);

}
