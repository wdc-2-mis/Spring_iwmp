package app.serviceImpl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.FPOReportBean;
import app.dao.FPOMasterDao;
import app.model.outcome.FpoDetail;
import app.service.FPOMasterService;

@Service("fpoMasterService")
public class FPOMasterServiceImpl implements FPOMasterService{

	@Autowired(required = true)
	FPOMasterDao fpoMasterDao;
	
	@Override
	public LinkedHashMap<Integer, String> getCoreActivity() {
		return fpoMasterDao.getCoreActivity();
	}

	

	@Override
	public List<FPOReportBean> getfpodatastatewise(Integer state) {
		return fpoMasterDao.getfpodatastatewise(state);
	}

	@Override
	public List<FPOReportBean> getfpodatadistwise(Integer statecode){
		return fpoMasterDao.getfpodatadistwise(statecode);
	}
    
	@Override
	public List<FpoDetail> getfpodataprojwise(int dcode, String fpoType){
		return fpoMasterDao.getfpodataprojwise(dcode, fpoType);
	}

	@Override
	public List<FpoDetail> getfpodataallprojwise(int stcode, String fpoType) {
		return fpoMasterDao.getfpodataallprojwise(stcode, fpoType);
	}

	@Override
	public List<FPOReportBean> getfpodraftdata(Integer projectId, String group) {
		return fpoMasterDao.getfpodraftdata(projectId, group);
	}

	@Override
	public String detFPOdraftdata(Integer fpoid) {
		return fpoMasterDao.detFPOdraftdata(fpoid);
	}

	@Override
	public String finalSaveFPOdraftdata(Integer fpoid) {
		return fpoMasterDao.finalSaveFPOdraftdata(fpoid);
	}

	@Override
	public List<FPOReportBean> fpofinaldata(Integer projectId, String group) {
		return fpoMasterDao.fpofinaldata(projectId, group);
	}



	@Override
	public String savefpodata(Integer projId, String group, Integer no, List<String> fponame, List<Integer> deptorg, List<String> regno,
			List<String> regdt, List<Integer> noofmembers, List<String> fpoactivity, List<BigDecimal> fpoavg,
			List<Integer> nooffarm, String loginID) {
		return fpoMasterDao.savefpodata(projId, group, no, fponame, deptorg, regno, regdt, noofmembers, fpoactivity, fpoavg, nooffarm, loginID);
	}



	@Override
	public LinkedHashMap<Integer, String> getdeptorg(int stCode) {
		return fpoMasterDao.getdeptorg(stCode);
	}
}
