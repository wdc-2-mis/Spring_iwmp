package app.dao.reports;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.AssetIdBean;
import app.bean.StateHeadActivityFinBean;

public interface AssetReportDao {
	LinkedHashMap<Integer, String> getSubActivity(Integer activityId);
	List<AssetIdBean> getAssetReport(Integer stCode,Integer distCode,Integer projId,Integer fyCode,Integer headCode,Integer activityCode,Integer subActivityCode, Integer monthid, String status);
	List<StateHeadActivityFinBean> getStwiseActTarAchWorks(Integer stCode,Integer fyCode,Integer headCode,Integer activityCode);
}
