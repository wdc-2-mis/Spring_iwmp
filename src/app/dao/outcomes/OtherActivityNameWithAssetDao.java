package app.dao.outcomes;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.OtherActivityNameWithAssetBean;

public interface OtherActivityNameWithAssetDao {

	List<OtherActivityNameWithAssetBean> getcreateAssetOtherActivityName(String projectId, Integer stcode, String year);
	String updateOtherActivityName(String projectId, Integer stcode, String year, String workid[], String othername[]);
	List<OtherActivityNameWithAssetBean> getAssetOtherActivityName(String project, Integer stcode, String year);
	LinkedHashMap<Integer,String> getOtherNameMaster( Integer stcode);
}
