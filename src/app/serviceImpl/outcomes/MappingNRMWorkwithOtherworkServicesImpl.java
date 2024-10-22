package app.serviceImpl.outcomes;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.menu.MappingNRMWorkwithOtherworkBean;
import app.dao.outcomes.MappingNRMWorkwithOtherworkDao;
import app.model.IwmpProjectAssetStatus;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyMappingNRMWorkOtherWork;
import app.service.outcome.MappingNRMWorkwithOtherworkServices;

@Service("MappingNRMWorkwithOtherworkServices")
public class MappingNRMWorkwithOtherworkServicesImpl implements MappingNRMWorkwithOtherworkServices{

	@Autowired(required = true)
	MappingNRMWorkwithOtherworkDao dao;
	
	
	
	
	@Override
	public List<IwmpProjectPhysicalAsset> getassetWorkWiseList(Integer pCode, String userId, Integer fYear, Integer head,
			Integer activity, Integer nrmwork) {
		// TODO Auto-generated method stub
		return dao.getassetWorkWiseList(pCode, userId, fYear, head, activity, nrmwork);
	}




	@Override
	public LinkedHashMap<Integer, String> getOtherHeadActivity(String headtype, Integer workid) {
		// TODO Auto-generated method stub
		return dao.getOtherHeadActivity(headtype, workid);
	}




	@Override
	public LinkedHashMap<Integer, String> getOtherHeadActivityWork(String headtyp, Integer acttypes, Integer proj, Integer assetid) {
		// TODO Auto-generated method stub
		return dao.getOtherHeadActivityWork(headtyp, acttypes, proj, assetid);
	}




	@Override
	public String checkNRMandOtherWorkMatch(String headtyp, Integer otherwork, Integer proj, Integer nrmwork) {
		// TODO Auto-generated method stub
		return dao.checkNRMandOtherWorkMatch(headtyp, otherwork, proj, nrmwork);
	}




	@Override
	public String saveNRMwithOtherAsset(List<BigInteger> assetid, Integer projcd, String userid, List<String> headtype,
			List<Integer> otherwork) {
		// TODO Auto-generated method stub
		return dao.saveNRMwithOtherAsset(assetid, projcd, userid, headtype, otherwork);
	}




	@Override
	public List<WdcpmksyMappingNRMWorkOtherWork> getmappingNRMWorkDraft(Integer pCode) {
		// TODO Auto-generated method stub
		return dao.getmappingNRMWorkDraft(pCode);
	}




	@Override
	public String deleteNRMwithOtherAsset(List<Integer> assetid) {
		// TODO Auto-generated method stub
		return dao.deleteNRMwithOtherAsset(assetid);
	}




	@Override
	public String completeNRMwithOtherAsset(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completeNRMwithOtherAsset(assetid, userid);
	}




	@Override
	public List<MappingNRMWorkwithOtherworkBean> getCompletedNRMRelatedOtherWork(String scheme, Integer projcd) {
		// TODO Auto-generated method stub
		return dao.getCompletedNRMRelatedOtherWork( scheme, projcd);
	}




	@Override
	public String checkNRMValidORNot(Integer nrmwork, Integer regid) {
		// TODO Auto-generated method stub
		return dao.checkNRMValidORNot(nrmwork, regid);
	}




	@Override
	public List<WdcpmksyMappingNRMWorkOtherWork> getWorkIdWiseNRMWorkDraft(Integer nrmwork) {
		// TODO Auto-generated method stub
		return dao.getWorkIdWiseNRMWorkDraft(nrmwork);
	}

}
