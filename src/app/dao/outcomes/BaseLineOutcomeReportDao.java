package app.dao.outcomes;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;

public interface BaseLineOutcomeReportDao {
	List<BaseLineOutcomeBean> getBlsOutReportStateLevel();
	List<BaseLineOutcomeBean> getBlsOutReportStateLevelForStCode(Integer stCode);
	List<BaseLineOutcomeBean> getBlsOutReportDistrictLevel(Integer stCode);
	List<BaseLineOutcomeBean> getBlsOutReportProjectLevel(Integer dCode);
	List<NewBaseLineSurveyBean> getBlsOutReportDetailLevel(Integer projectId);
}
