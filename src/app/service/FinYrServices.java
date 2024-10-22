package app.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.bean.FinYearBean;
import app.model.IwmpMFinYear;

@Service("FinYrServices")
public interface FinYrServices {
	
	List<FinYearBean> getFinYrMaster();
	boolean isCurrentFinYearExist();
	Map<String, String> getNextFinYearList();
	Integer saveFinancialYear(String finYearDesc, HttpSession session, String finYearstatus, String startFrom, String endTo);
	LinkedHashMap<Integer, String> getfinYearByFinCode(Integer toYear);
	LinkedHashMap<Integer,String> getAllFinYear();
}
