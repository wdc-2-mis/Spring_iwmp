package app.service.outcome;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.OtherActivityNameWithAssetBean;

@Service("OtherActivityNameWithAssetService")
public interface OtherActivityNameWithAssetService {
	
	List<OtherActivityNameWithAssetBean> getcreateAssetOtherActivityName(String projectId, Integer stcode, String year);
	String updateOtherActivityName(String projectId, Integer stcode, String year, String workid[], String othername[]);
	List<OtherActivityNameWithAssetBean> getAssetOtherActivityName(String project, Integer stcode, String year);
	LinkedHashMap<Integer,String> getOtherNameMaster( Integer stcode);

}
