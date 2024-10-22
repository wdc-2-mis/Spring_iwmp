package app.dao;

import java.math.BigDecimal;
import java.util.List;

import app.bean.SLNACoordinatorsBean;

public interface SLNACoordinatorsDao {


	public List<SLNACoordinatorsBean> getSLNACoordinatorsList();
	
	public String updateSLNACrdntrDetails(Integer stCode, String name, String email, BigDecimal mobile);
}
