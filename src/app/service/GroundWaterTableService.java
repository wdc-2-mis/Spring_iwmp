package app.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.bean.GroundWaterTableBean;

@Service("GroundWaterTableService")
public interface GroundWaterTableService {
	
	LinkedHashMap<Integer, String> getFinYear();
	String saveGroundWaterTable(String atline, Integer project, Integer fyear, BigDecimal preMonsoon, BigDecimal postMonsoon, HttpSession session, Integer gwtid,
			BigDecimal ph, Integer alkalinity, Integer hardness, Integer calcium, Integer chloride, BigDecimal nitrate, Integer dissolved, BigDecimal fluoride);
	
	List<GroundWaterTableBean> getGroundWaterTableReport(Integer state, Integer district,  Integer project);
	
	List<GroundWaterTableBean> gwtDetaildataDC(Integer project);
	
	List<GroundWaterTableBean> gwtDetaildataDCBasel(Integer project);
	
	boolean deleteGWTdraft(Integer gwtid, Integer gwtdtlid);
	
	boolean completeGWTDraftData(Integer gwtid, Integer gwtdtlid);
	
	String balselinCheck(Integer project, String atline);
	String duringProjCheck(Integer project, String atline, Integer fyear);
	
	List<GroundWaterTableBean> getGroundWaterTableDraft(Integer project, String atline, Integer fyear);

}
