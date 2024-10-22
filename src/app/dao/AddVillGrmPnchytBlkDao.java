package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.AddVillGrmPnchytBlkBean;
import app.model.master.IwmpVillage;

public interface AddVillGrmPnchytBlkDao {
	List<AddVillGrmPnchytBlkBean> getTableDataBylgdCode(Integer lgdCode,Integer masterId);
	String saveVillGrmBlkAsDraft(Integer masterId, Integer state, Integer district, Integer block, Integer grmPnchyt, Integer lgdCode, String villGrmBlk);
	LinkedHashMap<Integer, String> getBlkByDistCode(Integer stateCode,Integer districtCode);
	
}
