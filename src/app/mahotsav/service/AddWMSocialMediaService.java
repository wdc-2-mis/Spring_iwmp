package app.mahotsav.service;

import org.springframework.stereotype.Service;

import app.mahotsav.model.WatershedMahotsavRegistration;

@Service("AddWMSocialMediaService")
public interface AddWMSocialMediaService {


	WatershedMahotsavRegistration findByUserRegNo(String regNo);
}
