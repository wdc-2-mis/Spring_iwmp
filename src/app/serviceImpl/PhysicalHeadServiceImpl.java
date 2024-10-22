package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AuditReportBean;
import app.bean.PhysicalHeaddataBean;
import app.bean.ProjectLocationBean;
import app.dao.AuditDao;
import app.dao.PhysicalHeadDao;
import app.model.master.IwmpMPhyHeads;
import app.service.PhysicalHeadservice;
@Service("physicalheadservice")
public class PhysicalHeadServiceImpl implements PhysicalHeadservice{

	
	@Autowired
	PhysicalHeadDao headDao;
	
	@Override
	
	public LinkedHashMap<Integer, List<PhysicalHeaddataBean>> getPhyHeaddata() {
	return headDao.getPhyHeaddata();
	}
	
	@Override
	public Boolean savephyhead(String headdesc, String status, BigDecimal seqno, Boolean bline, String loginId) {
		// TODO Auto-generated method stub
		return headDao.savephyhead(headdesc, status, seqno, bline, loginId);
	}

	@Override
	public Boolean deletephyhead(int id) {
		// TODO Auto-generated method stub
		return headDao.deletephyhead(id);
	}

	
	
	@Override
	public List<PhysicalHeaddataBean> findphyhead(Integer id) {
		return headDao.findphyhead(id);
	}
    
	@Override
	public Boolean updatephyhead(String headdesc, int id, String status,BigDecimal seqno, Boolean bline, String loginId) {
		return headDao.updatephyhead(headdesc, id, status, seqno, bline, loginId);
	}

	@Override
	public List<PhysicalHeaddataBean> getheadseqno() {
		return headDao.getheadseqno();
	}

	@Override
	public List<IwmpMPhyHeads> physicalHeadList() {
		// TODO Auto-generated method stub
		return headDao.physicalHeadList();
	}

	@Override
	public LinkedHashMap<Integer, String> getAllHeadList() {
		// TODO Auto-generated method stub
		return headDao.getAllHeadList();
	}

	@Override
	public LinkedHashMap<Integer, String> getAllPrayasHeadList() {
		// TODO Auto-generated method stub
		return headDao.getAllPrayasHeadList();
	}
	 
	
}