package app.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.SLNACoordinatorsBean;

@Service("SLNACoordinatorsService")
public interface SLNACoordinatorsService {
	

	public List<SLNACoordinatorsBean> getSLNACoordinatorsList();
	
	public String updateSLNACrdntrDetails(Integer stCode, String name, String email, BigDecimal mobile);

}
