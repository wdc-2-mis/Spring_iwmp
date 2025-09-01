package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.unfreeze.UnfreezeAddParaOutcomeDao;
import app.model.Outcome2Data;
import app.service.unfreeze.UnfreezeAddParaOutcomeService;

@Service
public class UnfreezeAddParaOutcomeServiceImpl implements UnfreezeAddParaOutcomeService{

	@Autowired
	UnfreezeAddParaOutcomeDao dao;
	
	@Override
	public List<Outcome2Data> getAddParaOutcomeData(Integer finYrCd, Integer projId) {
		// TODO Auto-generated method stub
		return dao.getAddParaOutcomeData(finYrCd, projId);
	}

	@Override
	public String unfreezeAddParaOutcomeData(Integer id) {
		// TODO Auto-generated method stub
		return dao.unfreezeAddParaOutcomeData(id);
	}

}
