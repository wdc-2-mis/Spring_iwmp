package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.RoleMenuList;
import app.dao.CommonDao;
import app.model.IwmpMAreaType;
import app.model.IwmpMCsShare;
import app.model.IwmpMFinYear;
import app.model.IwmpMProjectPrd;
import app.model.MQuarter;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;
import app.service.CommonService;

@Service
//@Transactional
public class CommonServiceImpl implements CommonService{
	@Autowired
    private CommonDao commonDao;

	@Override
	public List<IwmpMFinYear> getAllFinancialYear() {
		// TODO Auto-generated method stub
		return commonDao.getAllFinancialYear();
	}
	
	@Override
	public List<IwmpMAreaType> getAllAreaType() {
		// TODO Auto-generated method stub
		return commonDao.getAllAreaType();
	}

	@Override
	public List<IwmpMProjectPrd> getAllProjectPrd() {
		// TODO Auto-generated method stub
		return commonDao.getAllProjectPrd();
	}

	@Override
	public List<IwmpMCsShare> getCentralStateShareDetail() {
		// TODO Auto-generated method stub
		return commonDao.getCentralStateShareDetail();
	}

	@Override
	public IwmpMAreaType getAreaTypeDetail(int areaCd) {
		// TODO Auto-generated method stub
		return commonDao.getAreaTypeDetail(areaCd);
	}

	@Override
	public IwmpMCsShare getStateCentralShareDetail(Integer stCode) {
		// TODO Auto-generated method stub
		return commonDao.getStateCentralShareDetail(stCode);
	}

	@Override
	public List<IwmpGramPanchayat> getInactiveGramPanchayatList(int stateCode, int districtCode, int blockCode) {
		// TODO Auto-generated method stub
		return commonDao.getInactiveGramPanchayatList(stateCode, districtCode, blockCode);
	}

	@Override
	public LinkedHashMap<Integer, String> getGramPanchayatByblockCode(Integer blockCode) {
		// TODO Auto-generated method stub
		return commonDao.getGramPanchayatByblockCode(blockCode);
	}

	@Override
	public void updateGramPanchayatList(List<IwmpGramPanchayat> village) {
		// TODO Auto-generated method stub
		 commonDao.updateGramPanchayatList(village);
	}

	@Override
	public List<IwmpGramPanchayat> getActiveGramPanchayatList(int stateCode, int districtCode, int blockCode) {
		// TODO Auto-generated method stub
		return commonDao.getActiveGramPanchayatList(stateCode, districtCode, blockCode);
	}

	@Override
	public IwmpGramPanchayat findGramPanchaytGcode(int gCode) {
		// TODO Auto-generated method stub
		return commonDao.findGramPanchaytGcode(gCode);
	}

	@Override
	public Boolean updateGramPanchayatList(IwmpGramPanchayat gp) {
		// TODO Auto-generated method stub
		return commonDao.updateGramPanchayatList(gp);
		
	}

	@Override
	public Boolean authenticateUser(String url, HttpSession session, HttpServletRequest request)
	{
		return commonDao.authenticateUser(url, session, request);
	}
	@Override
	public List<RoleMenuList> getPublicReport(Integer roleId) {
		// TODO Auto-generated method stub
		return commonDao.getPublicReport(roleId);
	}

	@Override
	public List<IwmpGramPanchayat> getActiveGramPanchayatList() {
		// TODO Auto-generated method stub
		return commonDao.getActiveGramPanchayatList();
	}

	@Override
	public List<IwmpMPhyActivity> physicalActivityList() {
		// TODO Auto-generated method stub
		return commonDao.physicalActivityList();
	}

	@Override
	public List<IwmpMPhySubactivity> physicalSubActivityList() {
		// TODO Auto-generated method stub
		return commonDao.physicalSubActivityList();
	}

	@Override
	public LinkedHashMap<Integer, String> getFinancialYear() {
		// TODO Auto-generated method stub
		return commonDao.getFinancialYear();
	}



	@Override
	public List<MQuarter> getQuaterMaster() {
		// TODO Auto-generated method stub
		return commonDao.getQuaterMaster();
	}

	@Override
	public List<RoleMenuList> getPublicHindiReport() {
		return commonDao.getPublicHindiReport();
	}
}
