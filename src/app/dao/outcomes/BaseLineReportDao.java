package app.dao.outcomes;

import java.util.List;

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;

public interface BaseLineReportDao {
	List<BaseLineOutcomeBean> getBlsReportStateLevel();
	List<BaseLineOutcomeBean> getBlsReportStateLevelForStCode(Integer stCode);
	List<BaseLineOutcomeBean> getBlsReportDistrictLevel(Integer stCode);
	List<BaseLineOutcomeBean> getBlsReportProjectLevel(Integer dCode);
	List<NewBaseLineSurveyBean> getBlsReportDetailLevel(Integer projectId);
}
