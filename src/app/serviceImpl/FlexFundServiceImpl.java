package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.dao.FlexFundDao;
import app.model.FlexiFundDetails;
import app.service.FlexFundService;

@Service("FlexFundService")
public class FlexFundServiceImpl implements FlexFundService{

	@Autowired
	FlexFundDao dao;

	@Override
	public LinkedHashMap<Integer, String> getflexiactivity() {
		return dao.getflexiactivity();
	}

	@Override
	public LinkedHashMap<String, Integer> getFlexiFundGM(int pCode) {
		// TODO Auto-generated method stub
		return dao.getFlexiFundGM(pCode);
	}

	@Override
	public boolean saveFlexiFundData(Integer projId, Integer gcode, List<Integer> activityList,
			List<String> detailsList, List<Double> estCostList, List<Double> costList, List<MultipartFile> photos, List<Integer> photoCountList, List<String> latitudeList,
			List<String> longitudeList, String status, String createdBy, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.saveFlexiFundData(projId, gcode, activityList, detailsList, estCostList, costList, photos, photoCountList, latitudeList, longitudeList, status, createdBy, request);
	}

	@Override
	public List<FlexiFundDetails> getFlexiFundByProjectAndGcode(Integer projId, Integer gcode) {
		// TODO Auto-generated method stub
		return dao.getFlexiFundByProjectAndGcode(projId, gcode);
	}

	@Override
	public boolean updateFlexiFundData(List<Integer> rowIds, List<Integer> activityList, List<String> detailsList,
			List<Double> estCostList, List<Double> costList, List<MultipartFile> photos, List<Integer> photoRowIndex,
			String status, String user) {
		// TODO Auto-generated method stub
		return dao.updateFlexiFundData(rowIds, activityList, detailsList, estCostList, costList, photos, photoRowIndex, status, user);
	}
	
	
}
