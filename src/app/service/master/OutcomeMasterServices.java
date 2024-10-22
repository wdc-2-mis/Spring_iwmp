package app.service.master;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import app.bean.AddOutcomeParaBean;
import app.bean.PhysicalActBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.model.IwmpMMonth;
import app.model.master.WdcpmksyMOutcome;

@Service("OutcomeMasterServices")
public interface OutcomeMasterServices {
	
	List<WdcpmksyOutcomeBean> getOutcomeMaster();
	List<WdcpmksyOutcomeBean> getOutcomeDetailMaster();
	//LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>> getOutcomeMaster();
	List<WdcpmksyOutcomeBean> getoutcomeseqno();
	List<WdcpmksyOutcomeBean> getoutcomedseqno();
	public List<IwmpMMonth> getAllMonths();
	public List<WdcpmksyMOutcome>  getOutcomeYes();
	Integer saveOutcomeMaster(WdcpmksyOutcomeBean outcome, HttpSession session);
	Boolean deleteOutcomeMaster(int id);
	Boolean deleteOutcomeDetailMaster(int id, int idoutcomed);
	LinkedHashMap<Integer,String> getfinyearCode();
	LinkedHashMap<Integer,String> getOutcomeHeadcode();
	LinkedHashMap<Integer,String> getmonthcode();
	List<WdcpmksyOutcomeBean> outcomedatafind(Integer id);
	List<WdcpmksyOutcomeBean> outcomedatadetailfind(Integer id);
	Boolean updateOutcomeMaster(Integer id, String outcomedesc, Character detail_exist1, Integer fin_yr_cd1, BigDecimal seqno, Integer month_id1, Boolean bls_required);
	Integer saveOutcomeDetailMaster(WdcpmksyOutcomeBean outcome, HttpSession session);
	Boolean updateOutcomeDetailMaster(Integer id, String outcomedetdesc, Integer doutcomeidh, BigDecimal seqno);
	LinkedHashMap<Integer, List<AddOutcomeParaBean>> getoutcomedesc();
	LinkedHashMap<Integer, List<AddOutcomeParaBean>> getOutcomeparadraft(Integer project, Integer month, Integer financial);
	String detOutcomedraftdata(Integer draftid);
	String finalSaveOutcomeParadraftdata(Integer draftid);
	List<AddOutcomeParaBean> getoutcomefinaldata(Integer projectId, Integer month, Integer year);
	
	LinkedHashMap<Integer,List<AddOutcomeParaBean>> getstateWiseAdditionalParameter(String finyear);
	LinkedHashMap<Integer,List<AddOutcomeParaBean>> getDistWiseStatusAdditionalParameter(String stcode, String finyear);
	LinkedHashMap<Integer,List<AddOutcomeParaBean>> getPorjWiseStatusAdditionalParameter(String dcode, String finyear);
	LinkedHashMap<Integer, List<AddOutcomeParaBean>> getoutcomeDegradedData(String project);
	String draftOutcomeParam(Integer projId, Integer month, Integer year, BigDecimal degradedrainf, String noofman, String sc, String st, String others, String female, String smallfarmer,
			String marginalfarmer, String landless, String bpl, Integer outcome2id, Character status, String loginId);
	LinkedHashMap<Integer, List<AddOutcomeParaBean>> getOutcomeparacomplete(Integer project, Integer month, Integer financial);
	

}
