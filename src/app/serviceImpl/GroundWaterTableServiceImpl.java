package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.GroundWaterTableBean;
import app.dao.GroundWaterTableDao;
import app.service.GroundWaterTableService;

@Service("GroundWaterTableService")
public class GroundWaterTableServiceImpl implements GroundWaterTableService{

	
	@Autowired
	GroundWaterTableDao dao;
	
	@Override
	public LinkedHashMap<Integer, String> getFinYear() {
		
		return dao.getFinYear();
	}

	@Override
	public String saveGroundWaterTable(String atline, Integer project, Integer fyear, BigDecimal preMonsoon, BigDecimal postMonsoon, HttpSession session, Integer gwtid, 
			BigDecimal ph, Integer alkalinity, Integer hardness, Integer calcium, Integer chloride, BigDecimal nitrate, Integer dissolved, BigDecimal fluoride) 
	{
		return dao.saveGroundWaterTable(atline, project, fyear, preMonsoon, postMonsoon, session, gwtid, ph, alkalinity, hardness, calcium, chloride, nitrate, dissolved, fluoride);
	}

	@Override
	public List<GroundWaterTableBean> getGroundWaterTableReport(Integer state, Integer district, Integer project) {
		
		return dao.getGroundWaterTableReport(state, district, project);
	}

	@Override
	public List<GroundWaterTableBean> gwtDetaildataDC(Integer project) {
		
		return dao.gwtDetaildataDC(project);
	}

	@Override
	public boolean deleteGWTdraft(Integer gwtid, Integer gwtdtlid) {
		// TODO Auto-generated method stub
		return dao.deleteGWTdraft( gwtid, gwtdtlid);
	}

	@Override
	public boolean completeGWTDraftData(Integer gwtid, Integer gwtdtlid) {
		// TODO Auto-generated method stub
		return dao.completeGWTDraftData(gwtid, gwtdtlid);
	}

	@Override
	public String balselinCheck(Integer project, String atline) {
		// TODO Auto-generated method stub
		return dao.balselinCheck(project, atline);
	}

	@Override
	public String duringProjCheck(Integer project, String atline, Integer fyear) {
		// TODO Auto-generated method stub
		return dao.duringProjCheck(project, atline, fyear);
	}

	@Override
	public List<GroundWaterTableBean> getGroundWaterTableDraft(Integer project, String atline, Integer fyear) {
		// TODO Auto-generated method stub
		return dao.getGroundWaterTableDraft(project, atline, fyear);
	}

	@Override
	public List<GroundWaterTableBean> gwtDetaildataDCBasel(Integer project) {
		// TODO Auto-generated method stub
		return dao.gwtDetaildataDCBasel(project);
	}

}
