package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.PhysicalActBean;
import app.bean.PhysicalHeaddataBean;
import app.dao.PhysicalActDao;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.WdcpmksyMPhyOtherActivity;
import app.service.PhysicalActService;

@Service("physicalActservice")
public class PhysicalActServiceImpl implements PhysicalActService{

	@Autowired
	PhysicalActDao phyactDao;
	
    @Override
	public LinkedHashMap<Integer, List<PhysicalActBean>> getPhyActdata() {
	return phyactDao.getPhyActdata();
	}
    

    @Override
	public LinkedHashMap<Integer, String> getHeadCode() {
		// TODO Auto-generated method stub
		return phyactDao.getHeadCode();
	}


	@Override
	public LinkedHashMap<Integer, String> getUomCode() {
		// TODO Auto-generated method stub
		return phyactDao.getUomCode();
	}


	@Override
	public Boolean savephyact(String adesc, int headcode, int uomcode, String status, BigDecimal seqno, int assets, String loginID) {
		// TODO Auto-generated method stub
		return phyactDao.savephyact(adesc, headcode, uomcode,status,seqno, assets, loginID);
	}

	@Override
	public List<PhysicalActBean> findactdesc(Integer id){
		return phyactDao.findactdesc(id);
		
	}
	public Boolean updateactdata(int id, String adesc, String status, int uomcode, BigDecimal seqno, int asset, String loginId) {
	return phyactDao.updateactdata(id, adesc, status, uomcode,seqno, asset, loginId);
	}

	public Boolean deletephyhead(int id) {
		return phyactDao.deletephyhead(id);
	}


	@Override
	public List<PhysicalActBean> getseqnum(int headcode){
		return phyactDao.getseqnum(headcode);
		
	}


	@Override
	public LinkedHashMap<Integer, String> getactdesc(int headcode) {
		return phyactDao.getactdesc(headcode);
	}


	@Override
	public String savephysubact(int actdesc, String sbactdesc, Character status, BigDecimal seqno, String loginId, String userType, int stCode) {
		return phyactDao.savephysubact(actdesc, sbactdesc, status, seqno, loginId, userType, stCode);
	}


	@Override
	public List<IwmpMPhySubactivity> getPhySubActdata() {
	     return phyactDao.getPhySubActdata();
	}


	@Override
	public List<IwmpMPhySubactivity> findsubactdesc(Integer id) {
		return phyactDao.findsubactdesc(id);
	}


	@Override
	public String updatephysubact(int id, String sbactdesc, Character status, BigDecimal seqno, String loginId) {
		return phyactDao.updatephysubact(id, sbactdesc, status, seqno, loginId);
	}


	@Override
	public Boolean deletephysubhead(int id) {
		return phyactDao.deletephysubhead(id);
	}


	@Override
	public List<WdcpmksyMPhyOtherActivity> getOtherSubCategorydata(int stCode) {
		// TODO Auto-generated method stub
		return phyactDao.getOtherSubCategorydata(stCode);
	}


	@Override
	public List<WdcpmksyMPhyOtherActivity> findothrsubcatdesc(Integer id) {
		// TODO Auto-generated method stub
		return phyactDao.findothrsubcatdesc(id);
	}


	@Override
	public String updateothrsubcat(int id, String oscatdesc, Character status, BigDecimal seqno, String loginId) {
		// TODO Auto-generated method stub
		return phyactDao.updateothrsubcat(id, oscatdesc, status, seqno, loginId);
	}


	@Override
	public Boolean deleteothersubcat(int id) {
		// TODO Auto-generated method stub
		return phyactDao.deleteothersubcat(id);
	}


	
}
