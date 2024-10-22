package app.service;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

@Service("FinancialYearMasterService")
public interface FinancialYearMasterService {
	
	LinkedHashMap<Integer,String> getAllFinaicialYear();

}
