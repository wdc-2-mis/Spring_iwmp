package app.service.unfreeze;

import java.util.List;

import app.model.Outcome2Data;

public interface UnfreezeAddParaOutcomeService {

	public List<Outcome2Data> getAddParaOutcomeData(Integer finYrCd, Integer projId);
	public String unfreezeAddParaOutcomeData(Integer id);
}
