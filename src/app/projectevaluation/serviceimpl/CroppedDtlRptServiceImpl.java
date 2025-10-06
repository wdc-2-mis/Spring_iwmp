package app.projectevaluation.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.projectevaluation.bean.CroppedDetailBean;
import app.projectevaluation.dao.CroppedDtlRptDao;
import app.projectevaluation.service.CroppedDtlRptService;

@Service
public class CroppedDtlRptServiceImpl implements CroppedDtlRptService{

	@Autowired
	CroppedDtlRptDao croppedDtlRptDao;

	@Override
	public List<CroppedDetailBean> getcroppedDtlAreaDtl() {
		return croppedDtlRptDao.getcroppedDtlAreaDtl();
	}

	@Override
	public List<CroppedDetailBean> getcroppedDtlAreaOthsDtl() {
		// TODO Auto-generated method stub
		return croppedDtlRptDao.getcroppedDtlAreaOthsDtl();
	}

	@Override
	public List<CroppedDetailBean> getDistwiseCropDtlArea(int stCode) {
		// TODO Auto-generated method stub
		return croppedDtlRptDao.getDistwiseCropDtlArea(stCode);
	}

	@Override
	public List<CroppedDetailBean> getDistwiseCropDtlOthArea(int stCode) {
		// TODO Auto-generated method stub
		return croppedDtlRptDao.getDistwiseCropDtlOthArea(stCode);
	}

	@Override
	public List<CroppedDetailBean> getProjwiseCropDtlArea(int dcode) {
		// TODO Auto-generated method stub
		return croppedDtlRptDao.getProjwiseCropDtlArea(dcode);
	}
	
	
}
