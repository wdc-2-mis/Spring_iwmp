package app.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import app.bean.PhysicalHeaddataBean;
import app.bean.ProjectLocationBean;
import app.model.master.IwmpMPhyHeads;

@Service("physicalheadservice")
public interface PhysicalHeadservice {
	LinkedHashMap<Integer,List<PhysicalHeaddataBean>> getPhyHeaddata();

	Boolean savephyhead(String headdesc, String status, BigDecimal seqno, Boolean bline, String loginId);

	Boolean deletephyhead(int id);
	
    List<PhysicalHeaddataBean> findphyhead(Integer id);

	Boolean updatephyhead(String headdesc, int id, String status, BigDecimal seqno, Boolean bline, String loginId);

	List<PhysicalHeaddataBean> getheadseqno();
	
	List<IwmpMPhyHeads> physicalHeadList();

	LinkedHashMap<Integer,String> getAllHeadList();

	LinkedHashMap<Integer,String> getAllPrayasHeadList();
	
}