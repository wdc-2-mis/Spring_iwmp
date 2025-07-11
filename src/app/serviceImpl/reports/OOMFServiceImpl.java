package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.reports.OOMFCurrentStatusBean;
import app.dao.reports.OOMFDao;
import app.service.reports.OOMFService;

@Service("OOMFService")
public class OOMFServiceImpl implements OOMFService{
	
	@Autowired
	OOMFDao dao;

	@Override
	public List<OOMFCurrentStatusBean> getOOMFCurrentStatusReport() {
		
		return dao.getOOMFCurrentStatusReport();
	}

	@Override
	public String getOOMFFinYear() {
		
		return dao.getOOMFFinYear();
	}

	@Override
	public String getOOMFFinyearMonth() {
		
		return dao.getOOMFFinyearMonth();
	}

	@Override
	public List<OOMFCurrentStatusBean> getOOMFBeforePrayashData() {
		
		return dao.getOOMFBeforePrayashData();
	}
	

}
