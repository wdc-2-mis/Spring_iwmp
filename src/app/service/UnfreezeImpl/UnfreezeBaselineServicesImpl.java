package app.service.UnfreezeImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.UnfreezeBaselineSurveyDataBean;
import app.dao.unfreeze.UnfreezeBaselineDao;
import app.model.BlsOutMain;
import app.service.unfreeze.UnfreezeBaselineServices;

@Service("UnfreezeBaselineServices")
public class UnfreezeBaselineServicesImpl implements UnfreezeBaselineServices{

	@Autowired(required = true)
	UnfreezeBaselineDao dao;
	
	
	@Override
	public Map<String, String> getBaselineVillageList(int project) {
		
		Map<String, String> villList=new LinkedHashMap<String, String>();
		for (BlsOutMain temp : dao.getBaselineVillageList(project)) 
		{
			villList.put(temp.getIwmpVillage().getVcode()+"", temp.getIwmpVillage().getVillageName());
		}
		return villList ;
	}


	@Override
	public List<UnfreezeBaselineSurveyDataBean> getUnfreezeBaselineSurveyData(Integer vill, Integer project) {
		// TODO Auto-generated method stub
		return dao.getUnfreezeBaselineSurveyData(vill, project);
	}


	@Override
	public boolean unfreezeBaselineSurveyDataComplete(String[] villcode) {
		// TODO Auto-generated method stub
		return dao.unfreezeBaselineSurveyDataComplete(villcode);
	}

}
