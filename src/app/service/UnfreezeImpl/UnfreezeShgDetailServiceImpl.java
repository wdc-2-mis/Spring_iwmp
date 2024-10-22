package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ShgDetailBean;
import app.dao.unfreeze.UnfreezeShgDetailDao;
import app.service.unfreeze.UnfreezeShgDetailService;


@Service("UnfreezeShgDetailService")
public class UnfreezeShgDetailServiceImpl implements UnfreezeShgDetailService{
	
	@Autowired
	UnfreezeShgDetailDao shgDao;
	
	
	@Override
	public List<ShgDetailBean> unfreezeListShgDetails(Integer project, String grp, String headType) {

		return shgDao.unfreezeListShgDetails(project, grp, headType);
	}


	@Override
	public boolean unfreezeShgDetailsData(String[] shg_id, String headType) {

		return shgDao.unfreezeShgDetailsData(shg_id, headType);
	}


	}
