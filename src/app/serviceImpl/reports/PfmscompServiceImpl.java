package app.serviceImpl.reports;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.PfmsComponentBean;
import app.bean.PfmsTransactionBean;
import app.service.reports.PfmscompDao;
import app.service.reports.PfmscompService;

@Service("PfmscompService")
public class PfmscompServiceImpl implements PfmscompService{
	
	@Autowired
	PfmscompDao compdao;

	
	@Override
	public LinkedHashMap<String, String> getAllComponent() {
		return compdao.getAllComponent();
	}


	@Override
	public List<PfmsComponentBean> getPfmsComponentReport(String stcode, String[] comp, String finyr) {
		return compdao.getPfmsComponentReport(stcode, comp, finyr);
	}


	@Override
	public List<PfmsComponentBean> getdistPfmsComponentReport(String stcode, String comp, String finyr) {
		return compdao.getdistPfmsComponentReport(stcode, comp, finyr);
	}


}
