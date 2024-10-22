package app.service;

import java.util.List;

import app.bean.NRSCWorksBean;

public interface NRSCWorksService {

	List<NRSCWorksBean> getNRSCWorks();
	List<NRSCWorksBean> getNRSCDistWorks(Integer stcd);
	List<NRSCWorksBean> getNRSCProjWorks(Integer dcode);
}
