package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.QuarterlyTargetDao;
import app.model.WdcpmksyMQuadIndicators;
import app.model.WdcpmksyQuadTarget;
import app.service.QuarterlyTargetService;

@Service("quarterlyTargetService")
public class QuarterlyTargetServiceImpl implements QuarterlyTargetService{

	@Autowired(required = true)
	QuarterlyTargetDao quarterlyTargetDao;

	@Override
	public LinkedHashMap<Integer, String> getQuarterList() {
		return quarterlyTargetDao.getQuarterList();
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getTargetList(int projId, int year) {
		// TODO Auto-generated method stub
		return quarterlyTargetDao.getTargetList(projId, year);
	}

	@Override
	public LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> getTargetData() {
		// TODO Auto-generated method stub
		return quarterlyTargetDao. getTargetData();
	}

	@Override
	public LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> getAnnualTargetData(Integer project,
			Integer financial) {
		// TODO Auto-generated method stub
		return quarterlyTargetDao.getAnnualTargetData(project,financial);
	}

	@Override
	public Integer saveprojectpreparedness(int ProjId, Character status, int financial, String[] quarter1, String[] quarter2,
			String[] quarter3, String[] quarter4, String[] indicatorid, String q1s, String q2s, String q3s, String q4s, Integer stcode, String loginId) {
		// TODO Auto-generated method stub
		return quarterlyTargetDao.saveprojectpreparedness(ProjId, status, financial, quarter1, quarter2,
			quarter3, quarter4, indicatorid, q1s, q2s, q3s, q4s, stcode, loginId);
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getindicatorsdata(Integer project, Integer financial) {
		// TODO Auto-generated method stub
		return quarterlyTargetDao.getindicatorsdata(project, financial);
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getquartSLNAdata(Integer projId, Integer stcode) {
		    return quarterlyTargetDao.getquartSLNAdata(projId, stcode);
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getsingleslnadata(String achievementdtl) {
		
		return quarterlyTargetDao.getsingleslnadata(achievementdtl);
	}

	@Override
	public String SLNAApproveService(String quardDtl) {
		return quarterlyTargetDao.SLNAApproveService(quardDtl);
	}

	@Override
	public String SLNAAllQuadApproveService(String quardDtl, String status) {
		
		return quarterlyTargetDao.SLNAAllQuadApproveService(quardDtl, status);
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getSLNACompleteData(Integer stcode) {
		
		return quarterlyTargetDao.getSLNACompleteData(stcode);
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getcompletedquaddata(String quarterdtl) {
		// TODO Auto-generated method stub
		return quarterlyTargetDao.getcompletedquaddata(quarterdtl);
	}
	
	
}
