package app.service.unfreeze;

import java.util.List;

import app.model.WdcpmksyQuadTarget;

public interface UnfreezeOomFQuarterlyTarService {
	
	public List<WdcpmksyQuadTarget> getOomFQuarterlyTardata(Integer finYear, Integer projId);

	public String unfreezeOomfQuarterlyTarData(Integer finYear, Integer projId, Integer quarter);
}
