package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.bean.RoleMenuList;
import app.model.IwmpMAreaType;
import app.model.IwmpMCsShare;
import app.model.IwmpMFinYear;
import app.model.IwmpMProjectPrd;
import app.model.MQuarter;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;

@Service("commonService")
public interface CommonService {
	
	 public List<IwmpMFinYear> getAllFinancialYear();
	 
	 public List<MQuarter> getQuaterMaster();
	 
	 public List<IwmpMAreaType> getAllAreaType();
	 
	 public List<IwmpMProjectPrd> getAllProjectPrd();
	 
	 public List<IwmpMCsShare> getCentralStateShareDetail();
	 
	 public IwmpMAreaType getAreaTypeDetail(int areaCd);
	 
	 public IwmpMCsShare getStateCentralShareDetail(Integer stCode);
	 
	 public LinkedHashMap<Integer,String> getGramPanchayatByblockCode(Integer blockCode);
	 
	 public List<IwmpGramPanchayat> getInactiveGramPanchayatList(int stateCode,int districtCode,int blockCode);
	 
	 public List<IwmpGramPanchayat> getActiveGramPanchayatList(int stateCode,int districtCode,int blockCode);
	 
	 public List<IwmpGramPanchayat> getActiveGramPanchayatList();
	 
	 void updateGramPanchayatList(List<IwmpGramPanchayat> village);
	 
	 public IwmpGramPanchayat findGramPanchaytGcode(int gCode);
	 
	 Boolean updateGramPanchayatList(IwmpGramPanchayat gp);
	 
	 Boolean authenticateUser(String url, HttpSession session, HttpServletRequest request);
	 
	 List<RoleMenuList> getPublicReport();	 
	 
	 public List<IwmpMPhyActivity> physicalActivityList();
	 
	 public List<IwmpMPhySubactivity> physicalSubActivityList();

	 LinkedHashMap<Integer,String> getFinancialYear();

	public List<RoleMenuList> getPublicHindiReport();
	

}

