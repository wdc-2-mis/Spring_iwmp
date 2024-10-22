package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.ProjectLocationBean;
import app.model.master.IwmpVillage;

@Service("villageMasterService")
public interface VillageMasterService {
	LinkedHashMap<Integer,String> getVillageBlockWise(Integer bcode);
	List<ProjectLocationBean> getVillageByVillageCode(List<Integer> vcode);
	List<IwmpVillage> getVillageList(int stateCode,int districtCode,int blockCode,int gpCode);
	List<IwmpVillage> getActiveVillageList(int stateCode,int districtCode,int blockCode,int gpCode);
	void updateVillageList(List<IwmpVillage> village);
	Boolean updateVillageList(IwmpVillage village);
	IwmpVillage findVillageVcode(Integer vcode);
	List<IwmpVillage> getVillageList();
	LinkedHashMap<Integer,String> getVillageOfProject(Integer projId);
}
