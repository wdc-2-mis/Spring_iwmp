package app.dao;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import app.bean.AuditReportBean;
import app.bean.PhysicalHeaddataBean;
import app.model.master.IwmpMPhyHeads;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;

public interface PhysicalHeadDao {
	LinkedHashMap<Integer,List<PhysicalHeaddataBean>> getPhyHeaddata();
	Boolean savephyhead(String headdesc, String status, BigDecimal seqno, Boolean bline, String loginId);
	Boolean deletephyhead(int id);
	List<PhysicalHeaddataBean> findphyhead(Integer id);
	Boolean updatephyhead(String headdesc, int id, String status, BigDecimal seqno, Boolean bline, String loginId);
	List<PhysicalHeaddataBean> getheadseqno();
	List<IwmpMPhyHeads> physicalHeadList();
	
	List<MEpaCoreactivity> EntryPointActivityList();
	List<MLivelihoodCoreactivity> LivelihoodActivityList();
	List<MProductivityCoreactivity> ProductionActivityList();
	LinkedHashMap<Integer, String> getAllHeadList();
	LinkedHashMap<Integer, String> getAllPrayasHeadList();
	
	
}
