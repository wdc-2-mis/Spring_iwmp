package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.unfreeze.UnfreezeOomFQuarterlyTarDao;
import app.model.WdcpmksyQuadTarget;
import app.service.unfreeze.UnfreezeOomFQuarterlyTarService;

@Service
public class UnfreezeOomFQuarterlyTarServiceImpl implements UnfreezeOomFQuarterlyTarService{
	
	@Autowired
	UnfreezeOomFQuarterlyTarDao dao;

	@Override
	public String unfreezeOomfQuarterlyTarData(Integer finYear, Integer projId, Integer quarter) {
		// TODO Auto-generated method stub
		return dao.unfreezeOomfQuarterlyTarData(finYear, projId, quarter);
	}

	@Override
	public List<WdcpmksyQuadTarget> getOomFQuarterlyTardata(Integer finYear, Integer projId) {
		// TODO Auto-generated method stub
		return dao.getOomFQuarterlyTardata(finYear, projId);
	}

}
