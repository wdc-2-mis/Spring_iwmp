package app.CSV;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import app.dao.BlockMasterDao;
import app.dao.CommonDao;
import app.dao.DistrictMasterDao;
import app.dao.IwmpProjectDao;
import app.dao.PhysicalHeadDao;
import app.dao.StateMasterDao;
import app.dao.VillageMasterDao;
import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;

@Service
public class CSVService {
	@Autowired
	StateMasterDao stateMasterDao;
	
	@Autowired
	DistrictMasterDao districtMasterDao;
	
	@Autowired
	BlockMasterDao blockMasterDao;
	
	@Autowired
	VillageMasterDao villageMasterDao;
	
	@Autowired
	CommonDao commonDao;
	
	@Autowired
	PhysicalHeadDao physicalHeadDao;
	
	@Autowired
	IwmpProjectDao iwmpProjectDao;

	
	 public ByteArrayInputStream loadState() {
		    List<IwmpState> states = stateMasterDao.getStateList();
		    ByteArrayInputStream in = CSVHelper.stateCsv(states);
		    return in;
		  }
	 public ByteArrayInputStream loadDistrict() {
		    List<IwmpDistrict> districts = districtMasterDao.getDistrictList();
		    ByteArrayInputStream in = CSVHelper.districtCsv(districts);
		    return in;
		  }
	 public ByteArrayInputStream loadBlock() {
		    List<IwmpBlock> blocks = blockMasterDao.getActiveBlockList();
		    ByteArrayInputStream in = CSVHelper.blockCsv(blocks);
		    return in;
		  }
	 public ByteArrayInputStream loadGramPanchayat() {
		    List<IwmpGramPanchayat> gps = commonDao.getActiveGramPanchayatList();
		    ByteArrayInputStream in = CSVHelper.grampanchayatCsv(gps);
		    return in;
		  }
	 public ByteArrayInputStream loadVillage() {
		    List<IwmpVillage> villages = villageMasterDao.getVillageList();
		    ByteArrayInputStream in = CSVHelper.villageCsv(villages);
		    return in;
		  }
	 
	 public ByteArrayInputStream loadPhysicalHead() {
		    List<IwmpMPhyHeads> physicalhead = physicalHeadDao.physicalHeadList();
		    ByteArrayInputStream in = CSVHelper.physicalHeadCsv(physicalhead);
		    return in;
		  }
	 
	 public ByteArrayInputStream loadPhysicalActivity() {
		    List<IwmpMPhyActivity> physicalhead = commonDao.physicalActivityList();
		    ByteArrayInputStream in = CSVHelper.physicalHeadActivityCsv(physicalhead);
		    return in;
		  }
	 
	 public ByteArrayInputStream loadPhysicalSubActivity() {
		    List<IwmpMPhySubactivity> physicalhead = commonDao.physicalSubActivityList();
		    ByteArrayInputStream in = CSVHelper.physicalHeadSubActivityCsv(physicalhead);
		    return in;
		  }
	 
	 public ByteArrayInputStream loadFiancialyear() {
		    List<IwmpMFinYear> physicalhead = commonDao.getAllFinancialYear();
		    ByteArrayInputStream in = CSVHelper.financialYar(physicalhead);
		    return in;
		  }
	 
	 public ByteArrayInputStream loadSanctionedProject() {
		    List<IwmpMProject> physicalhead = iwmpProjectDao.ListSanctionedProjectStatus("C");
		    ByteArrayInputStream in = CSVHelper.sactionedProjectCsv(physicalhead);
		    return in; 
		  }

	 public ByteArrayInputStream loadEntryPointActivity() {
		    List<MEpaCoreactivity> physicalhead = physicalHeadDao.EntryPointActivityList();
		    ByteArrayInputStream in = CSVHelper.EntryPointActivityCsv(physicalhead);
		    return in;
		  }
	 
	 public ByteArrayInputStream loadLivelihoodActivity() {
		    List<MLivelihoodCoreactivity> physicalhead = physicalHeadDao.LivelihoodActivityList();
		    ByteArrayInputStream in = CSVHelper.LivelihoodActivityCsv(physicalhead);
		    return in;
		  }
	 
	 public ByteArrayInputStream loadProductionActivity() {
		    List<MProductivityCoreactivity> physicalhead = physicalHeadDao.ProductionActivityList();
		    ByteArrayInputStream in = CSVHelper.ProductionActivityCsv(physicalhead);
		    return in;
		  }
}
