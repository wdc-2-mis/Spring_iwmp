package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.ProjectLocationBean;
import app.model.IwmpMProject;
import app.model.master.IwmpVillage;

public interface VillageMasterDao {
	LinkedHashMap<Integer,String> getVillageBlockWise(Integer bcode);
	List<ProjectLocationBean> getVillageByVillageCode(List<Integer> vcode);
	List<IwmpVillage> getVillageList(int stateCode,int districtCode,int blockCode,int gpCode);
	List<IwmpVillage> getVillageList();
	List<IwmpVillage> getActiveVillageList(int stateCode,int districtCode,int blockCode,int gpCode);
	public void updateVillageList(List<IwmpVillage> village);
	public Boolean updateVillageList(IwmpVillage village);
	IwmpVillage findVillageVcode(Integer vcode);
	LinkedHashMap<Integer,String> getVillageOfProject(Integer projId);
}
