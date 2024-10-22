package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.reports.PMKSYBean;
import app.dao.reports.PMKSYDao;
import app.service.reports.PMKSYService;

@Service
public class PMKSYServiceImpl implements PMKSYService {
	
	@Autowired
	PMKSYDao pmksyDao;

	@Override
	public List<PMKSYBean> getBenifitedFarmersNo() {
		// TODO Auto-generated method stub
		return pmksyDao.getBenifitedFarmersNo();
	}

	@Override
	public List<PMKSYBean> getWtrHrvstngStrctrNo() {
		// TODO Auto-generated method stub
		return pmksyDao.getWtrHrvstngStrctrNo();
	}

	@Override
	public List<PMKSYBean> getDistWiseBenifitedFarmersNo(int stcode) {
		// TODO Auto-generated method stub
		return pmksyDao.getDistWiseBenifitedFarmersNo(stcode);
	}

	@Override
	public List<PMKSYBean> getProjWiseBenifitedFarmersNo(int dcode) {
		// TODO Auto-generated method stub
		return pmksyDao.getProjWiseBenifitedFarmersNo(dcode);
	}

	@Override
	public List<PMKSYBean> getStWiseWtrHrvstngIrriDetail() {
		// TODO Auto-generated method stub
		return pmksyDao.getStWiseWtrHrvstngIrriDetail();
	}

	@Override
	public List<PMKSYBean> getDistWiseWtrHrvstngIrriDetail(int stcode) {
		// TODO Auto-generated method stub
		return pmksyDao.getDistWiseWtrHrvstngIrriDetail(stcode);
	}

	@Override
	public List<PMKSYBean> getProjWiseWtrHrvstngIrriDetail(int dcode) {
		// TODO Auto-generated method stub
		return pmksyDao.getProjWiseWtrHrvstngIrriDetail(dcode);
	}

}
