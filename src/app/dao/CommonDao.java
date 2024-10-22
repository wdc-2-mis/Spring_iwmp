package app.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import app.bean.RoleMenuList;
import app.model.IwmpMAreaType;
import app.model.IwmpMCsShare;
import app.model.IwmpMFinYear;
import app.model.IwmpMProjectPrd;
import app.model.MQuarter;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;


public interface CommonDao {
	
	public List<IwmpMFinYear> getAllFinancialYear();
	
	public List<MQuarter> getQuaterMaster();

	public List<IwmpMAreaType> getAllAreaType();

	public IwmpMAreaType getAreaTypeDetail(int areaCd);
	
	public IwmpMCsShare getStateCentralShareDetail(Integer stCode);

	public List<IwmpMProjectPrd> getAllProjectPrd();

	public List<IwmpMCsShare> getCentralStateShareDetail();
	
	public Boolean checkFinancialyear(Date projectDate,Integer finyearid);
	
	LinkedHashMap<Integer, String> getGramPanchayatByblockCode(Integer districtCode);
	
	public List<IwmpGramPanchayat> getInactiveGramPanchayatList(int stateCode, int districtCode, int blockCode);
	
	public List<IwmpGramPanchayat> getActiveGramPanchayatList(int stateCode, int districtCode, int blockCode);
	
	public List<IwmpGramPanchayat> getActiveGramPanchayatList();
	
	public void updateGramPanchayatList(List<IwmpGramPanchayat> village);
	
	public IwmpGramPanchayat findGramPanchaytGcode(int gCode);
	
	Boolean updateGramPanchayatList(IwmpGramPanchayat gp);

	Boolean authenticateUser(String url, HttpSession session, HttpServletRequest request);
	List<RoleMenuList> getPublicReport();
	List<RoleMenuList> getPublicHindiReport();
	public List<IwmpMPhyActivity> physicalActivityList();
	
	public List<IwmpMPhySubactivity> physicalSubActivityList();

	public LinkedHashMap<Integer, String> getFinancialYear();
	
}
