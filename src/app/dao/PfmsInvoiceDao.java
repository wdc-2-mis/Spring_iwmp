package app.dao;

import java.util.List;

import app.bean.pfms.PfmsInvoiceData;


public interface PfmsInvoiceDao {
	
	List<PfmsInvoiceData> getPfmsInvoiceStateData(Integer stateCode);
	
	List<PfmsInvoiceData> getPfmsInvoiceData();
	
	List<PfmsInvoiceData> getPfmsInvoiceDistrictData(Integer distCode);
	
	List<PfmsInvoiceData> getPfmsInvoiceData(Integer invoiceNo);

}
