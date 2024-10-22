package app.dao;

import java.util.List;

import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.bean.pfms.PfmsTranxMappedWithProjBean;

public interface PfmsStwiseExpndtreDao {
	
	List<PfmsStwiseExpndtreBean> getStwiseExpndtr();
	List<PfmsStwiseExpndtreBean> getDistwiseExpndtr(int stCode);
	List<PfmsTranxMappedWithProjBean> getTrnxMappedWithProjData(int distCode);

}
