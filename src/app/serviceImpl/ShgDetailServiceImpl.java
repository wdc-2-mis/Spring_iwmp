package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ShgDetailBean;
import app.dao.ShgDetailDao;
import app.service.ShgDetailService;

@Service("ShgDetailService")
public class ShgDetailServiceImpl implements ShgDetailService {

	@Autowired
	ShgDetailDao shgDao;
	
	
	@Override
	public List<ShgDetailBean> getListShgDetails(Integer projId, Integer grp) {

		return shgDao.getListShgDetails(projId, grp);
	}


	@Override
	public String saveListShgDetails(List<String> shg_detail_id,  List<String> account_detail, List<String> ifsc_code){
		
		return shgDao.saveListShgDetails(shg_detail_id, account_detail, ifsc_code );
	}


	@Override
	public String deleteSHGDetails(Integer shg_detail_id) {

		return shgDao.deleteSHGDetails(shg_detail_id);
	}


	@Override
	public String completeShgDetails(String[] shg_detail_id1) {

		return shgDao.completeShgDetails(shg_detail_id1);
	}


	@Override
	public List<ShgDetailBean> getShgAccount() {
		
		return shgDao.getShgAccount();
	}


	@Override
	public List<ShgDetailBean> distShgAccountReport(int stcode) {

		return shgDao.distShgAccountReport(stcode);
	}


	@Override
	public List<ShgDetailBean> projShgAccountReport(int dcode) {
		
		return shgDao.projShgAccountReport(dcode);
	}


	@Override
	public List<ShgDetailBean> getStatusSHG(Integer stcd) {
		return shgDao.getStatusSHG(stcd);
	}


	@Override
	public List<ShgDetailBean> getRemainingSHG(Integer dcode, String type) {
		return shgDao.getRemainingSHG(dcode,type);
	}
	
	

}

