package app.serviceImpl.master;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AddOutcomeParaBean;
import app.bean.PhysicalActBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.dao.master.OutcomeMasterDao;
import app.model.IwmpMMonth;
import app.model.master.WdcpmksyMOutcome;
import app.service.master.OutcomeMasterServices;

@Service("OutcomeMasterServices")
public class OutcomeMasterServiceImpl implements OutcomeMasterServices{

	@Autowired
	private OutcomeMasterDao dao;
	
	@Override
	public List<WdcpmksyOutcomeBean> getOutcomeMaster() {
		
		return dao.getOutcomeMaster();
	}

	@Override
	public List<IwmpMMonth> getAllMonths() {
		// TODO Auto-generated method stub
		return dao.getAllMonths();
	}

	@Override
	public Integer saveOutcomeMaster(WdcpmksyOutcomeBean outcome, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveOutcomeMaster(outcome, session);
	}

	@Override
	public List<WdcpmksyOutcomeBean> getoutcomeseqno() {
		// TODO Auto-generated method stub
		return dao.getoutcomeseqno();
	}

	@Override
	public Boolean deleteOutcomeMaster(int id) {
		// TODO Auto-generated method stub
		return dao.deleteOutcomeMaster(id);
	}

	@Override
	public LinkedHashMap<Integer, String> getfinyearCode() {
		// TODO Auto-generated method stub
		return dao.getfinyearCode();
	}

	@Override
	public LinkedHashMap<Integer, String> getmonthcode() {
		// TODO Auto-generated method stub
		return dao.getmonthcode();
	}

	@Override
	public List<WdcpmksyOutcomeBean> outcomedatafind(Integer id) {
		// TODO Auto-generated method stub
		return dao.outcomedatafind( id);
	}

	@Override
	public Boolean updateOutcomeMaster(Integer id, String outcomedesc, Character detail_exist1, Integer fin_yr_cd1,
			BigDecimal seqno, Integer month_id1, Boolean bls_required) {
		return dao.updateOutcomeMaster(id, outcomedesc, detail_exist1, fin_yr_cd1, seqno, month_id1, bls_required);
	}

	@Override
	public List<WdcpmksyOutcomeBean> getOutcomeDetailMaster() {
		// TODO Auto-generated method stub
		return dao.getOutcomeDetailMaster();
	}

	@Override
	public List<WdcpmksyMOutcome> getOutcomeYes() {
		// TODO Auto-generated method stub
		return dao.getOutcomeYes();
	}

	@Override
	public Integer saveOutcomeDetailMaster(WdcpmksyOutcomeBean outcome, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveOutcomeDetailMaster( outcome, session) ;
	}

	@Override
	public Boolean deleteOutcomeDetailMaster(int id, int idoutcomed) {
		// TODO Auto-generated method stub
		return dao.deleteOutcomeDetailMaster(id, idoutcomed);
	}

	@Override
	public LinkedHashMap<Integer, String> getOutcomeHeadcode() {
		// TODO Auto-generated method stub
		return dao.getOutcomeHeadcode();
	}

	@Override
	public List<WdcpmksyOutcomeBean> outcomedatadetailfind(Integer id) {
		// TODO Auto-generated method stub
		return dao.outcomedatadetailfind(id);
	}

	@Override

	public Boolean updateOutcomeDetailMaster(Integer id, String outcomedetdesc, Integer doutcomeidh, BigDecimal seqno) {
		// TODO Auto-generated method stub
		return dao.updateOutcomeDetailMaster(id, outcomedetdesc, doutcomeidh, seqno);
	}

	@Override
	public List<WdcpmksyOutcomeBean> getoutcomedseqno() {
		// TODO Auto-generated method stub
		return dao.getoutcomedseqno();
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getoutcomedesc() {
		return dao.getoutcomedesc();
	}

	
	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getOutcomeparadraft(Integer project, Integer month, Integer financial) {
		return dao.getOutcomeparadraft(project, month, financial);
	}

	@Override
	public String detOutcomedraftdata(Integer draftid) {
		return dao.detOutcomedraftdata(draftid);
	}

	@Override
	public String finalSaveOutcomeParadraftdata(Integer draftid) {
		return dao.finalSaveOutcomeParadraftdata(draftid);
	}

	@Override
	public List<AddOutcomeParaBean> getoutcomefinaldata(Integer projectId, Integer month, Integer year) {
		return dao.getoutcomefinaldata(projectId, month, year);
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getstateWiseAdditionalParameter(String fyear) {
		// TODO Auto-generated method stub
		return dao.getstateWiseAdditionalParameter(fyear);
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getDistWiseStatusAdditionalParameter(String stcode,
			String finyear) {
		// TODO Auto-generated method stub
		return dao.getDistWiseStatusAdditionalParameter(stcode, finyear);
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getPorjWiseStatusAdditionalParameter(String dcode,
			String finyear) {
		// TODO Auto-generated method stub
		return dao.getPorjWiseStatusAdditionalParameter(dcode, finyear);
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getoutcomeDegradedData(String project) {
		// TODO Auto-generated method stub
		return dao.getoutcomeDegradedData(project);
	}

	@Override
	public String draftOutcomeParam(Integer projId, Integer month, Integer year, BigDecimal degradedrainf,
			 String noofman, String sc, String st, String others, String female, String smallfarmer,
			String marginalfarmer, String landless, String bpl, Integer outcome2id, Character status, String loginId) {
		
		return dao.draftOutcomeParam(projId, month, year, degradedrainf, noofman, sc, st, others, female, smallfarmer, marginalfarmer, landless, bpl, outcome2id, status, loginId);
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getOutcomeparacomplete(Integer project, Integer month,
			Integer financial) {
	  return dao.getOutcomeparacomplete(project, month, financial);
	}

	

}
