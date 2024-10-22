package app.service.reports;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.AssetIdBean;

public interface AssetReportService {
	LinkedHashMap<Integer, String> getSubActivity(Integer activityId);
	List<AssetIdBean> getAssetReport(Integer stCode,Integer distCode,Integer projId,Integer fyCode,Integer headCode,Integer activityCode,Integer subActivityCode, Integer monthid, String status);

}
