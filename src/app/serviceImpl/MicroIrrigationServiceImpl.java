package app.serviceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.VillGramPanBean;
import app.dao.MicroIrrigationDao;
import app.model.BlsOutMain;
import app.service.MicroIrrigationService;

@Service("MicroIrrigationService")
public class MicroIrrigationServiceImpl implements MicroIrrigationService{

	@Autowired
	MicroIrrigationDao microIrrigationDao;
	
	@Override
	public HashMap<Integer, String> getPlotnoOfProject(Integer villageId) {
		return microIrrigationDao.getPlotnoOfProject(villageId);
	}

	public List<VillGramPanBean> getplotirriga(Integer plotnoId){
		return microIrrigationDao.getplotirriga(plotnoId);
		
	}

	@Override
	public Boolean saveMicroIrr(BigDecimal microI, Integer plotno, Character microstatus) {
	 return microIrrigationDao.saveMicroIrr(microI, plotno, microstatus);
	}

	@Override
	public Boolean getmicrostatus(Integer plotnoId) {
		return microIrrigationDao.getmicrostatus(plotnoId);
	}

}
