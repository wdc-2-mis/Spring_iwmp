package app.dao.unfreeze;

import java.util.List;

import app.bean.GroundWaterTableBean;


public interface UnfreezeGroundWaterDao {
	
	List<GroundWaterTableBean> unfreezeListGW(Integer proj, String level, Integer dcode, Integer finyr);
	boolean unfreezeGWTData(String[] proj_id, String level, Integer dcode, Integer finyr);
	

}
