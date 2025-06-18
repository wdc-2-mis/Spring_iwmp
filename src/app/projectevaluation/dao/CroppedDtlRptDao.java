package app.projectevaluation.dao;

import java.util.List;

import app.projectevaluation.bean.CroppedDetailBean;

public interface CroppedDtlRptDao {

	List<CroppedDetailBean> getcroppedDtlAreaDtl();

	List<CroppedDetailBean> getcroppedDtlAreaOthsDtl();

	List<CroppedDetailBean> getDistwiseCropDtlArea(int stCode);

}
