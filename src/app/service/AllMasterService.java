package app.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpMProjectPrepare;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MFpoCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.outcome.MShgCoreactivity;
import app.model.outcome.MTrainingSubject;

@Service("allMasterService")
public interface AllMasterService {

	String saveallmasterdata(String id, String actdesc, Boolean status, String loginId);
    List<MLivelihoodCoreactivity> getlivelihooddata();
    List<MProductivityCoreactivity> getproductiondata();
	List<MEpaCoreactivity> getepadata();
	List<MFpoCoreactivity> getfpodata();
	List<MTrainingSubject> gettrainingsubdata();
	List<MShgCoreactivity> getshgdata();
	List<MLivelihoodCoreactivity> findlivelihoodactdesc(Integer id);
	List<MProductivityCoreactivity> findproductionactdesc(Integer id);
	List<MEpaCoreactivity> findepaactdesc(Integer id);
	List<MFpoCoreactivity> findfpoactdesc(Integer id);
	List<MShgCoreactivity> findshgactdesc(Integer id);
	List<MTrainingSubject> findtrainingactdesc(Integer id);
	String updateallactivitymaster(int id, String modal, String actdesc, Boolean status, String loginId);
	Boolean deletemodalactivityid(int id, String modal);
	
	List<IwmpMProjectPrepare> getPreparednessData();
	String saveProjectPrepareData(Integer seqno, String pdesc, String srtname, String status1, String status2, HttpSession session);
	List<IwmpMProjectPrepare> findProjectPrepare(Integer id);
	String updateProjectPrepareData(Integer id, String modal, String prepareDesc, Boolean status, String shortDesc, 
			String selectedDesc1, String selectedDesc2, Integer sequence, HttpSession session);
}
