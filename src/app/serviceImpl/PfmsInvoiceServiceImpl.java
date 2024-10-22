package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.pfms.PfmsInvoiceData;
import app.dao.PfmsInvoiceDao;
import app.service.PfmsInvoiceService;

@Service("pfmsInvoiceService")
public class PfmsInvoiceServiceImpl  implements PfmsInvoiceService{
	
	@Autowired
	PfmsInvoiceDao pfmsInvoiceDao;

	@Override
	public List<PfmsInvoiceData> getPfmsInvoiceStateData(Integer stateCode) {
		// TODO Auto-generated method stub
		return pfmsInvoiceDao.getPfmsInvoiceStateData(stateCode);
	}

	@Override
	public List<PfmsInvoiceData> getPfmsInvoiceData() {
		// TODO Auto-generated method stub
		return pfmsInvoiceDao.getPfmsInvoiceData();
	}

	@Override
	public List<PfmsInvoiceData> getPfmsInvoiceDistrictData(Integer distCode) {
		// TODO Auto-generated method stub
		return pfmsInvoiceDao.getPfmsInvoiceDistrictData(distCode);
	}

}
