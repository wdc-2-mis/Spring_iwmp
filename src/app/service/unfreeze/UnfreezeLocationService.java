package app.service.unfreeze;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.UnfreezeProjectLoactionBean;

@Service("UnfreezeLocationService")
public interface UnfreezeLocationService {
	
	List<UnfreezeProjectLoactionBean> getUnfreezeProjectLocation(Integer district, Integer project);
	boolean deletePhysicalWorkIdTemp(Integer projid);
	boolean unfreezeBaselineSurveyPlotWise(Integer projid);
	boolean unfreezeProjectLocatin(Integer projid);

}
