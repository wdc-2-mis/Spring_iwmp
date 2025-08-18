package app.dao.unfreeze;

import java.util.List;

import app.model.WdcpmksyQuadTarget;

public interface UnfreezeOomFQuarterlyTarDao {
	
	public List<WdcpmksyQuadTarget> getOomFQuarterlyTardata(Integer finYear, Integer projId);
	
	public String unfreezeOomfQuarterlyTarData(Integer finYear, Integer projId, Integer quarter);

}
