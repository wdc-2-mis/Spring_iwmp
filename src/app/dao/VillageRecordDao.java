package app.dao;

import java.util.List;

import app.bean.VillageRecordBean;

public interface VillageRecordDao {
	
	List<VillageRecordBean> getVibrantVillageDetails(Integer stcd, String status);

}
