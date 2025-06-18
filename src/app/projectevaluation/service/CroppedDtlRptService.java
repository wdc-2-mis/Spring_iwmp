package app.projectevaluation.service;

import java.util.List;

import app.projectevaluation.bean.CroppedDetailBean;

public interface CroppedDtlRptService {

	List<CroppedDetailBean> getcroppedDtlAreaDtl();

	List<CroppedDetailBean> getcroppedDtlAreaOthsDtl();

	List<CroppedDetailBean> getDistwiseCropDtlArea(int stCode);

	List<CroppedDetailBean> getDistwiseCropDtlOthArea(int stCode);
	

}
