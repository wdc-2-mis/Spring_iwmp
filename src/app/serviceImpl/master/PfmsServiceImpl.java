package app.serviceImpl.master;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.PfmsTransactionBean;
import app.dao.master.PfmsDao;
import app.service.master.PfmsService;

@Service("PfmsService")
public class PfmsServiceImpl implements PfmsService{
	
	@Autowired
	PfmsDao pfmsDao;

	@Override
	public List<PfmsTransactionBean> getPfmsTransaction(Integer mstrLvl, Integer projId) {
		// TODO Auto-generated method stub
		return pfmsDao.getPfmsTransaction(mstrLvl, projId);
	}

	@Override
	public String saveAsDraftPfmsTransaction(String[] eatmisdataId, Integer projId) {
		// TODO Auto-generated method stub
		return pfmsDao.saveAsDraftPfmsTransaction(eatmisdataId, projId);
	}

	@Override
	public String deletePfmsTransaction(Integer eatmisdataId) {
		// TODO Auto-generated method stub
		return pfmsDao.deletePfmsTransaction(eatmisdataId);
	}

	@Override
	public String completePfmsTransaction(String[] eatmisdataId) {
		// TODO Auto-generated method stub
		return pfmsDao.completePfmsTransaction(eatmisdataId);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectByStCode(Integer stCode) {
		// TODO Auto-generated method stub
		return pfmsDao.getProjectByStCode(stCode);
	}

	@Override
	public List<PfmsTransactionBean> getPfmsWorkTransaction(Integer regId, Integer projId, Integer finyear) {
		return pfmsDao.getPfmsWorkTransaction(regId, projId, finyear);
	}

	@Override
	public LinkedHashMap<Integer, String> getworkiddtl(int projId) {
		return pfmsDao.getworkiddtl(projId);
	}

	@Override
	public String saveAsDraftPfmsWorkId(List<String> workid, List<String> eatmisdataId, List<String> expenditure, List<Integer> totalworks, String createdby, HttpServletRequest request) {
		return pfmsDao.saveAsDraftPfmsWorkId(workid, eatmisdataId, expenditure, totalworks, createdby, request);
	}

	@Override
	public LinkedHashMap<Integer, String> getworkiddraftdtl(List<Integer> wicode) {
		return pfmsDao.getworkiddraftdtl(wicode);
	}

	@Override
	public String deletePfmsworkTransaction(Integer eatmisdataId) {
		return pfmsDao.deletePfmsworkTransaction(eatmisdataId);
	}

	@Override
	public String completePfmsWorkIdTransaction(String[] eatmisdataId) {
		return pfmsDao.completePfmsWorkIdTransaction(eatmisdataId);
	}

	@Override
	public List<PfmsTransactionBean> getworkidexpdtl(Integer id) {
		// TODO Auto-generated method stub
		return pfmsDao.getworkidexpdtl(id);
	}

	
	

	

}
