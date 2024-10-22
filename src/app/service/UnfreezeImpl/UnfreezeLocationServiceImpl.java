package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.UnfreezeProjectLoactionBean;
import app.dao.unfreeze.UnfreezeLocationDao;
import app.service.unfreeze.UnfreezeLocationService;

@Service("UnfreezeLocationService")
public class UnfreezeLocationServiceImpl implements UnfreezeLocationService{

	@Autowired
	private UnfreezeLocationDao dao;
	
	@Override
	public List<UnfreezeProjectLoactionBean> getUnfreezeProjectLocation(Integer district, Integer project) {
		// TODO Auto-generated method stub
		return dao.getUnfreezeProjectLocation(district, project);
	}

	@Override
	public boolean deletePhysicalWorkIdTemp(Integer projid) {
		// TODO Auto-generated method stub
		return dao.deletePhysicalWorkIdTemp( projid);
	}

	@Override
	public boolean unfreezeBaselineSurveyPlotWise(Integer projid) {
		// TODO Auto-generated method stub
		return dao.unfreezeBaselineSurveyPlotWise(projid);
	}

	@Override
	public boolean unfreezeProjectLocatin(Integer projid) {
		// TODO Auto-generated method stub
		return dao.unfreezeProjectLocatin(projid);
	}

}
