package app.dao;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import app.model.MBlsOutType;
import app.model.MBlsOutcome;

public interface BaselineTypeDao {

	String savebaselinetypedata(String typecode, String desc, BigDecimal seqno, String loginId);
    List<MBlsOutType> getbaselinetypedata();
	LinkedHashMap<Integer, String> gettypeCode();
	String savebaselinedata(Integer btype, String bdesc, BigDecimal seqno, String loginId);
	List<MBlsOutcome> getbaselinedata();
	List<MBlsOutType> findbaselinetypedesc(Integer id);
	String updatebaselinetypedata(int id, String typecode, String desc, BigDecimal seqno, String loginId);
	Boolean deletebaselinetype(int id);
	List<MBlsOutcome> findbaselinedesc(Integer id);
	String updatebaselinedata(int id, int typedesc, String baslinedesc, BigDecimal seqno, String loginId);
	Boolean deletebaseline(int id);

}
