package app.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.AllActivityDao;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpMProjectPrepare;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MFpoCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.outcome.MShgCoreactivity;
import app.model.outcome.MTrainingSubject;
import app.service.AllMasterService;

@Service("allMasterService")
public class AllMasterServiceImpl implements AllMasterService{

	@Autowired
	AllActivityDao allactivitydao;
	
	@Override
	public String saveallmasterdata(String id, String actdesc, Boolean status, String loginId) {
		return allactivitydao.saveallmasterdata(id, actdesc, status, loginId);
	}

	@Override
	public List<MLivelihoodCoreactivity> getlivelihooddata() {
		return allactivitydao.getlivelihooddata();
	}

	@Override
	public List<MProductivityCoreactivity> getproductiondata() {
		return allactivitydao.getproductiondata();
	}

	@Override
	public List<MEpaCoreactivity> getepadata() {
		return allactivitydao.getepadata();
	}

	@Override
	public List<MFpoCoreactivity> getfpodata() {
		return allactivitydao.getfpodata();
	}

	@Override
	public List<MTrainingSubject> gettrainingsubdata() {
		return allactivitydao.gettrainingsubdata();
	}

	@Override
	public List<MShgCoreactivity> getshgdata() {
		return allactivitydao.getshgdata();
	}

	@Override
	public List<MLivelihoodCoreactivity> findlivelihoodactdesc(Integer id) {
		return allactivitydao.findlivelihoodactdesc(id);
	}

	@Override
	public List<MProductivityCoreactivity> findproductionactdesc(Integer id) {
		return allactivitydao.findproductionactdesc(id);
	}

	@Override
	public List<MEpaCoreactivity> findepaactdesc(Integer id) {
		return allactivitydao.findepaactdesc(id);
	}

	@Override
	public List<MFpoCoreactivity> findfpoactdesc(Integer id) {
		return allactivitydao.findfpoactdesc(id);
	}

	@Override
	public List<MShgCoreactivity> findshgactdesc(Integer id) {
		return allactivitydao.findshgactdesc(id);
	}

	@Override
	public List<MTrainingSubject> findtrainingactdesc(Integer id) {
		return allactivitydao.findtrainingactdesc(id);
	}

	@Override
	public String updateallactivitymaster(int id, String modal, String actdesc, Boolean status, String loginId) {
		return allactivitydao.updateallactivitymaster(id, modal, actdesc, status, loginId);
	}

	@Override
	public Boolean deletemodalactivityid(int id, String modal) {
		return allactivitydao.deletemodalactivityid(id, modal);
	}

	@Override
	public List<IwmpMProjectPrepare> getPreparednessData() {
		// TODO Auto-generated method stub
		return allactivitydao.getPreparednessData();
	}

	@Override
	public String saveProjectPrepareData(Integer seqno, String pdesc, String srtname, String status1, String status2,
			HttpSession session) {
		// TODO Auto-generated method stub
		return allactivitydao.saveProjectPrepareData(seqno, pdesc, srtname, status1, status2, session);
	}

	@Override
	public List<IwmpMProjectPrepare> findProjectPrepare(Integer id) {
		// TODO Auto-generated method stub
		return allactivitydao.findProjectPrepare( id);
	}

	@Override
	public String updateProjectPrepareData(Integer id, String modal, String prepareDesc, Boolean status,
			String shortDesc, String selectedDesc1, String selectedDesc2, Integer sequence, HttpSession session) {
		// TODO Auto-generated method stub
		return allactivitydao.updateProjectPrepareData(id, modal, prepareDesc, status, shortDesc, selectedDesc1, selectedDesc2, sequence, session);
	}

	

}
