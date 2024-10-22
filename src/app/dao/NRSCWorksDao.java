package app.dao;

import java.util.List;

import app.bean.NRSCWorksBean;

public interface NRSCWorksDao {

	List<NRSCWorksBean> getNRSCWorks();
	List<NRSCWorksBean> getNRSCDistWorks(Integer stcd);
	List<NRSCWorksBean> getNRSCProjWorks(Integer dcode);
}
