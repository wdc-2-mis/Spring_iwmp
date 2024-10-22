package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.CapicityBuildingTrainingBean;
import app.dao.TrainingSubjectDao;
import app.service.TrainingSubjectServices;

@Service("trainingSubjectServices")
public class TrainingSubjectServicesImpl implements TrainingSubjectServices{
	
	@Autowired(required = true)
	TrainingSubjectDao dao;
	

	@Override
	public LinkedHashMap<Integer, String> getTrainingSubject() {
		return dao.getTrainingSubject();
	}


	@Override
	public String saveCapicityBuilding(String level, Integer noOfTConduct, List<Integer> noOfperson,
			List<String> subject, List<String> startdate, List<String> enddate, List<Integer> trainday,
			HttpSession session) {
		return dao.saveCapicityBuilding(level,noOfTConduct,noOfperson,subject,startdate,enddate,trainday,session);
	}


	@Override
	public List<CapicityBuildingTrainingBean> getcapicityBuildingReport() {
		// TODO Auto-generated method stub
		return dao.getcapicityBuildingReport();
	}


	@Override
	public List<CapicityBuildingTrainingBean> getTrainingDetail12HOURS(Integer state) {
		// TODO Auto-generated method stub
		return dao.getTrainingDetail12HOURS(state);
	}
	

}
