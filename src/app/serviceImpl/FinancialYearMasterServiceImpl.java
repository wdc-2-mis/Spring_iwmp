package app.serviceImpl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.FinancialYearMasterDao;
import app.service.FinancialYearMasterService;

@Service("FinancialYearMasterService")
public class FinancialYearMasterServiceImpl implements FinancialYearMasterService{
	
	@Autowired
	FinancialYearMasterDao dao;

	@Override
	public LinkedHashMap<Integer, String> getAllFinaicialYear() {
		// TODO Auto-generated method stub
		return dao.getAllFinaicialYear();
	}

}
