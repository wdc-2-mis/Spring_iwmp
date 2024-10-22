package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.SelfHelpGroupBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.dao.SelfHelpGroupDao;
import app.model.outcome.ShgCoreactivityDetail;
import app.service.SelfHelpGroupService;

@Service("selfHelpGroupService")
public class SelfHelpGroupServiceImpl implements SelfHelpGroupService{
	
	@Autowired(required = true)
	SelfHelpGroupDao selfHelpGroupDao;

	@Override
	public LinkedHashMap<Integer, String> getSHGCoreActivity() {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSHGCoreActivity();
	}


	


	@Override
	public LinkedHashMap<Integer,List<SelfHelpGroupBean>> getSHGDraftData(Integer projectCd, String group) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSHGDraftData(projectCd, group);
	}


	@Override
	public String deleteSHG(Integer shgid,String group) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.deleteSHG(shgid,group);
	}


	@Override
	public String completeSHG(Integer shgid) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.completeSHG(shgid);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSelfHelpCreatedExistList(Integer state) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSelfHelpCreatedExistList( state);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSelfHelpUserGroupList(Integer state) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSelfHelpUserGroupList( state);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSelfHelpGroupListDist(Integer stcd) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSelfHelpGroupListDist( stcd);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSelfHelpUserGroupListDist(Integer stcd) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSelfHelpUserGroupListDist(stcd);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSelfHelpGroupListProject(Integer distid) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSelfHelpGroupListProject( distid);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSelfHelpUserGroupListProject(Integer distid) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSelfHelpUserGroupListProject( distid);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSHGCreatedProjectDetail(Integer projectid) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSHGCreatedProjectDetail(projectid);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSHGExistedProjectDetail(Integer projectid) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSHGExistedProjectDetail(projectid);
	}


	@Override
	public List<SelfHelpGroupReportBean> getSHGUserGroupProjectDetail(Integer projectid) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSHGUserGroupProjectDetail( projectid);
	}


	@Override
	public LinkedHashMap<Integer, String> getSHGDepartment(Integer stCode) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.getSHGDepartment(stCode);
	}

;



	@Override
	public String saveDraftSHGData(Integer projectCd, String group, Integer shgno, List<String> name, List<Integer> sc,
			List<Integer> st, List<Integer> others, List<Integer> women, List<String> activity,
			List<BigDecimal> turnover, List<BigDecimal> income, List<Integer> pmbima, List<String> fedrated,
			String updatedby, List<Integer> department, List<String> regdate, List<BigDecimal> revolve_amount,
			List<Boolean> threft_credit) {
		// TODO Auto-generated method stub
		return selfHelpGroupDao.saveDraftSHGData(projectCd, group, shgno, name, sc, st, others, women, activity, turnover, income, pmbima, fedrated, updatedby, department, regdate, revolve_amount, threft_credit);
	}

	
}
