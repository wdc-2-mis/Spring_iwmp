package app.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.PhysicalActBean;
import app.bean.PhysicalHeaddataBean;
import app.model.master.IwmpBlock;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.WdcpmksyMPhyOtherActivity;

@Service("physicalActservice")
public interface PhysicalActService {

	LinkedHashMap<Integer,List<PhysicalActBean>> getPhyActdata();
	LinkedHashMap<Integer,String> getHeadCode();
	LinkedHashMap<Integer, String> getUomCode();
	Boolean savephyact(String adesc, int headcode, int uomcode, String status, BigDecimal seqno, int assets, String loginID);
	List<PhysicalActBean> findactdesc(Integer id);
	Boolean updateactdata(int id, String adesc, String status, int uomcode, BigDecimal seqno, int asset, String loginId);
	Boolean deletephyhead(int id);
	List<PhysicalActBean> getseqnum(int headcode);
	LinkedHashMap<Integer, String> getactdesc(int headcode);
	String savephysubact(int actdesc, String sbactdesc, Character status, BigDecimal seqno, String loginId, String userType, int stCode);
	List<IwmpMPhySubactivity> getPhySubActdata();
	List<WdcpmksyMPhyOtherActivity> getOtherSubCategorydata(int stCode);
	List<IwmpMPhySubactivity> findsubactdesc(Integer id);
	List<WdcpmksyMPhyOtherActivity> findothrsubcatdesc(Integer id);
	String updatephysubact(int id, String sbactdesc, Character status, BigDecimal seqno, String string);
	String updateothrsubcat(int id, String oscatdesc, Character status, BigDecimal seqno, String loginId);
	Boolean deletephysubhead(int id);
	Boolean deleteothersubcat(int id);
	
}
