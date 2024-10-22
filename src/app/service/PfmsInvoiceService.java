package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.pfms.PfmsInvoiceData;

@Service("PfmsInvoiceService")
public interface PfmsInvoiceService {
	List<PfmsInvoiceData> getPfmsInvoiceStateData(Integer stateCode);
	
	List<PfmsInvoiceData> getPfmsInvoiceData();
	
	List<PfmsInvoiceData> getPfmsInvoiceDistrictData(Integer distCode);

}
