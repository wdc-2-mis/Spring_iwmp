package app.service;

import java.util.List;

import app.PfmsStateDistProjExpndtrBean;

public interface PfmsStateDistProjExpndtrService {
	
	List<PfmsStateDistProjExpndtrBean> getStatewiseExpndtr();
	List<PfmsStateDistProjExpndtrBean> getDistwiseExpndtr(Integer stCode);
	List<PfmsStateDistProjExpndtrBean> getProjwiseExpndtr(Integer dcode);

}
