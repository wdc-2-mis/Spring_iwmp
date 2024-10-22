package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AddVillGrmPnchytBlkBean;
import app.dao.AddVillGrmPnchytBlkDao;
import app.model.master.IwmpVillage;
import app.service.AddVillGrmPnchytBlkService;

@Service("AddVillGrmPnchytBlkService")
public class AddVillGrmPnchytBlkServiceImpl implements AddVillGrmPnchytBlkService{

	@Autowired
	AddVillGrmPnchytBlkDao addVillGrmPnchytBlkDao;
	
	@Override
	public List<AddVillGrmPnchytBlkBean> getTableDataBylgdCode(Integer lgdCode,Integer masterId) {
		// TODO Auto-generated method stub
		return addVillGrmPnchytBlkDao.getTableDataBylgdCode(lgdCode,masterId);
	}

	@Override
	public String saveVillGrmBlkAsDraft(Integer masterId, Integer state, Integer district, Integer block, Integer grmPnchyt, Integer lgdCode,
			String villGrmBlk) {
		// TODO Auto-generated method stub
		return addVillGrmPnchytBlkDao.saveVillGrmBlkAsDraft(masterId, state, district, block, grmPnchyt, lgdCode, villGrmBlk);
	}

	@Override
	public LinkedHashMap<Integer, String> getBlkByDistCode(Integer stateCode, Integer districtCode) {
		// TODO Auto-generated method stub
		return addVillGrmPnchytBlkDao.getBlkByDistCode(stateCode, districtCode);
	}

}
