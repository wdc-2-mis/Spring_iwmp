package app.dao;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import app.bean.SelfHelpGroupBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.model.outcome.ShgCoreactivityDetail;

public interface SelfHelpGroupDao {
	LinkedHashMap<Integer,String> getSHGCoreActivity();
	String saveDraftSHGData(Integer projectCd,String group,Integer shgno,List<String> name,List<Integer> sc,List<Integer> st,List<Integer> others,List<Integer> women,List<String> activity,List<BigDecimal> turnover,List<BigDecimal> income,List<Integer> pmbima,List<String> fedrated,String updatedby,List<Integer> department,List<String> regdate, List<BigDecimal>revolve_amount, List<Boolean> threft_credit);
	LinkedHashMap<Integer,List<SelfHelpGroupBean>> getSHGDraftData(Integer projectCd,String group);
	String deleteSHG(Integer shgid,String group);
	String completeSHG(Integer shgid);
	
	List<SelfHelpGroupReportBean> getSelfHelpCreatedExistList(Integer state);
	List<SelfHelpGroupReportBean> getSelfHelpUserGroupList(Integer state);
	List<SelfHelpGroupReportBean> getSelfHelpGroupListDist(Integer stcd);
	List<SelfHelpGroupReportBean> getSelfHelpUserGroupListDist(Integer stcd);
	List<SelfHelpGroupReportBean> getSelfHelpGroupListProject(Integer distid);
	List<SelfHelpGroupReportBean> getSelfHelpUserGroupListProject(Integer distid);
	
	List<SelfHelpGroupReportBean> getSHGCreatedProjectDetail(Integer projectid);
	List<SelfHelpGroupReportBean> getSHGExistedProjectDetail(Integer projectid);
	List<SelfHelpGroupReportBean> getSHGUserGroupProjectDetail(Integer projectid);
	LinkedHashMap<Integer,String> getSHGDepartment(Integer stCode);

}
