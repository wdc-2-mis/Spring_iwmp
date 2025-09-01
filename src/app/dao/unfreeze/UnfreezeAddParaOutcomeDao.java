package app.dao.unfreeze;

import java.util.List;

import app.model.Outcome2Data;

public interface UnfreezeAddParaOutcomeDao {
	
	public List<Outcome2Data> getAddParaOutcomeData(Integer finYrCd, Integer projId);
	public String unfreezeAddParaOutcomeData(Integer id);

}
