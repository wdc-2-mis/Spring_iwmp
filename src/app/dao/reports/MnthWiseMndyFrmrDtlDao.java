package app.dao.reports;

import java.util.List;

import app.bean.AddOutcomeParaBean;

public interface MnthWiseMndyFrmrDtlDao {

	List<AddOutcomeParaBean> getMnthWiseMndyFrmrDtlList(int stcode, int dcode, int projid, int yrcd);
}
