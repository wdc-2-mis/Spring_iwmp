package app.dao;

import java.util.List;

import app.PfmsStateDistProjExpndtrBean;

public interface PfmsStateDistProjExpndtrDao {
	
	List<PfmsStateDistProjExpndtrBean> getStatewiseExpndtr();
	List<PfmsStateDistProjExpndtrBean> getDistwiseExpndtr(Integer stCode);
	List<PfmsStateDistProjExpndtrBean> getProjwiseExpndtr(Integer dcode);
}
