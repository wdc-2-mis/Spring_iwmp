package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.BaselineTypeDao;
import app.model.MBlsOutType;
import app.model.MBlsOutcome;
import app.service.BaselineTypeService;

@Service("baselineTypeService")
public class BaselineTypeServiceImpl implements BaselineTypeService{

	@Autowired
	BaselineTypeDao baselineTypeDao;

	@Override
	public String savebaselinetypedata(String typecode, String desc, BigDecimal seqno, String loginId) {
		return baselineTypeDao.savebaselinetypedata(typecode, desc, seqno, loginId);
	}

	@Override
	public List<MBlsOutType> getbaselinetypedata() {
		return baselineTypeDao.getbaselinetypedata();
	}

	@Override
	public LinkedHashMap<Integer, String> gettypeCode() {
		return baselineTypeDao.gettypeCode();
	}

	@Override
	public String savebaselinedata(Integer btype, String bdesc, BigDecimal seqno, String loginId) {
		return baselineTypeDao.savebaselinedata(btype, bdesc, seqno, loginId);
	}

	@Override
	public List<MBlsOutcome> getbaselinedata() {
		return baselineTypeDao.getbaselinedata();
	}

	@Override
	public List<MBlsOutType> findbaselinetypedesc(Integer id) {
		return baselineTypeDao.findbaselinetypedesc(id);
	}

	@Override
	public String updatebaselinetypedata(int id, String typecode, String desc, BigDecimal seqno, String loginId) {
		return baselineTypeDao.updatebaselinetypedata(id, typecode, desc, seqno, loginId);
	}

	@Override
	public Boolean deletebaselinetype(int id) {
		return baselineTypeDao.deletebaselinetype(id);
	}

	@Override
	public List<MBlsOutcome> findbaselinedesc(Integer id) {
		return baselineTypeDao.findbaselinedesc(id);
	}
	@Override
	public String updatebaselinedata(int id, int typedesc, String baslinedesc, BigDecimal seqno, String loginId) {
		return baselineTypeDao.updatebaselinedata(id, typedesc, baslinedesc, seqno, loginId);
	}

	@Override
	public Boolean deletebaseline(int id) {
		return baselineTypeDao.deletebaseline(id);
	}
	
}
