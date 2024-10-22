package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.FinYearBean;
import app.dao.FinYrDao;
import app.model.IwmpMFinYear;
import app.service.FinYrServices;

@Service("FinYrServices")
public class FinYrServicesImpl implements FinYrServices{
	
	
	
	@Autowired
	private FinYrDao finyrDao;

	@Override
	public List<FinYearBean> getFinYrMaster() {
		
		return finyrDao.getFinYrMaster();
	}

	@Override
	public boolean isCurrentFinYearExist() {

		return finyrDao.isCurrentFinYearExist();
	}

	@Override
	public Map<String, String> getNextFinYearList() {

		return finyrDao. getNextFinYearList();
	}

	@Override
	public Integer saveFinancialYear(String finYearDesc, HttpSession session, String finYearstatus, String startFrom,
			String endTo) {
		
		return finyrDao.saveFinancialYear(finYearDesc, session, finYearstatus,startFrom,endTo);
	}

	@Override
	public LinkedHashMap<Integer, String> getfinYearByFinCode(Integer toYear) {
		
		return finyrDao.getfinYearByFinCode(toYear);
	}

	@Override
	public LinkedHashMap<Integer, String> getAllFinYear() {
		// TODO Auto-generated method stub
		return finyrDao.getAllFinYear();
	}

}
