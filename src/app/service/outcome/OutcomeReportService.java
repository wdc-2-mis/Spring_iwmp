package app.service.outcome;

import java.util.List;

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;

public interface OutcomeReportService {
	List<BaseLineOutcomeBean> getBlsReportStateLevel();
	List<BaseLineOutcomeBean> getBlsReportStateLevelForStCode(Integer stCode);
	List<BaseLineOutcomeBean> getBlsReportDistrictLevel(Integer stCode);
	List<BaseLineOutcomeBean> getBlsReportProjectLevel(Integer dCode);
	List<NewBaseLineSurveyBean> getBlsReportDetailLevel(Integer projectId);
}
