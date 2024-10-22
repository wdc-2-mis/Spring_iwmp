package app.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import app.bean.FinYearBean;
import app.model.IwmpMFinYear;

public interface FinYrDao {
	
	List<FinYearBean> getFinYrMaster();
	boolean isCurrentFinYearExist();
	Map<String, String> getNextFinYearList();
	Integer saveFinancialYear(String finYearDesc, HttpSession session, String finYearstatus, String startFrom, String endTo);
	Integer maxfinyrcd();
	LinkedHashMap<Integer, String> getfinYearByFinCode(Integer toYear);
	LinkedHashMap<Integer,String> getAllFinYear();
}
