package app.service.reports;

import java.util.List;

import app.bean.AddOutcomeParaBean;

public interface MnthWiseMndyFrmrDtlService {

	List<AddOutcomeParaBean> getMnthWiseMndyFrmrDtlList(int stcode, int dcode, int projid, int yrcd);
}
