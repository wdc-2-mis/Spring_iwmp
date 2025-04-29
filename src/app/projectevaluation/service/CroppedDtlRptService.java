package app.projectevaluation.service;

import java.util.List;

import app.projectevaluation.bean.CroppedDetailBean;

public interface CroppedDtlRptService {

	List<CroppedDetailBean> getcroppedDtlAreaDtl();

	List<CroppedDetailBean> getcroppedDtlAreaOthsDtl();
	

}
