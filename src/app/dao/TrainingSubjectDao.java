package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.bean.CapicityBuildingTrainingBean;

public interface TrainingSubjectDao {
	
	LinkedHashMap<Integer,String> getTrainingSubject();
	String saveCapicityBuilding(String level, Integer noOfTConduct, List<Integer> noOfperson, List<String> subject, List<String> startdate, 
	List<String> enddate, List<Integer> trainday, HttpSession session);
	List<CapicityBuildingTrainingBean> getcapicityBuildingReport();
	List<CapicityBuildingTrainingBean> getTrainingDetail12HOURS(Integer state);

}
