package app.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import app.bean.VillGramPanBean;
import app.model.BlsOutMain;

public interface MicroIrrigationDao {

	HashMap<Integer, String> getPlotnoOfProject(Integer villageId);

	List<VillGramPanBean> getplotirriga(Integer plotnoId);

	Boolean saveMicroIrr(BigDecimal microI, Integer plotno, Character microstatus);

	Boolean getmicrostatus(Integer plotnoId);

}
