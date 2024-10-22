package app.serviceImpl.reports;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AssetIdBean;
import app.dao.reports.AssetReportDao;
import app.service.reports.AssetReportService;

@Service("AssetReportService")
public class AssetReportServiceImpl implements AssetReportService{
	
	@Autowired
	AssetReportDao dao;

	@Override
	public LinkedHashMap<Integer, String> getSubActivity(Integer activityId) {
		// TODO Auto-generated method stub
		return dao.getSubActivity(activityId);
	}

	@Override
	public List<AssetIdBean> getAssetReport(Integer stCode, Integer distCode, Integer projId, Integer fyCode,
			Integer headCode, Integer activityCode, Integer subActivityCode, Integer monthid, String status) {
		// TODO Auto-generated method stub
		return dao.getAssetReport(stCode,distCode,projId,fyCode,headCode,activityCode,subActivityCode, monthid, status);
	}

}
