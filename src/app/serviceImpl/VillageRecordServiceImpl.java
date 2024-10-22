package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.VillageRecordBean;
import app.dao.VillageRecordDao;
import app.service.VillageRecordService;

@Service
public class VillageRecordServiceImpl implements VillageRecordService{
	
	@Autowired
	VillageRecordDao villageRecordDao;

	@Override
	public List<VillageRecordBean> getVibrantVillageDetails(Integer stcd, String status) {
		return villageRecordDao.getVibrantVillageDetails(stcd,status);
	}

}
