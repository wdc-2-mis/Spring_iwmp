package app.service;

import java.util.List;

import app.bean.VillageRecordBean;

public interface VillageRecordService {
	
	List<VillageRecordBean> getVibrantVillageDetails(Integer stcd, String status);

}
