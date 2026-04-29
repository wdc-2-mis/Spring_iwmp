package app.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.bean.FlexiFundMActivityBean;
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
			List<String> longitudeList, String status, List<String> remarksList, String createdBy, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.saveFlexiFundData(projId, gcode, activityList, detailsList, estCostList, costList, photos, photoCountList, latitudeList, longitudeList, status, remarksList, createdBy, request);
	}

	@Override
	public List<FlexiFundDetails> getFlexiFundByProjectAndGcode(Integer projId, Integer gcode) {
		// TODO Auto-generated method stub
		return dao.getFlexiFundByProjectAndGcode(projId, gcode);
	}

	@Override
	public boolean updateFlexiFundData(List<Integer> rowIds, List<Integer> activityList, List<String> detailsList,
			List<Double> estCostList, List<Double> costList, List<MultipartFile> photos, List<Integer> photoRowIndex,
			String status, List<String> remarksList, String user) {
		// TODO Auto-generated method stub
		return dao.updateFlexiFundData(rowIds, activityList, detailsList, estCostList, costList, photos, photoRowIndex, status, remarksList, user);
	}

	@Override
	public boolean deletePhotoById(int photoId) {
		return dao.deletePhotoById(photoId);
		
	}

	@Override
	public Map<String, Object> savePhoto(MultipartFile file, int flexiFundId, String lat, String lon, Integer projId, Integer gcode) {
		// TODO Auto-generated method stub
		return dao.savePhoto(file, flexiFundId, lat, lon, projId, gcode);
	}
	 
	@Override
	public List<FlexiFundMActivityBean> getStateWiseFlexiFundReport() {
		return dao.getStateWiseFlexiFundReport();
	}

	@Override
	public boolean deleteFlexiFundRow(int id) {
		return dao.deleteFlexiFundRow(id);
		
	}

	@Override
	public List<Map<String, Object>> getCompleteFlexiFundData(int projid, int panchayat) {
		// TODO Auto-generated method stub
		return dao.getCompleteFlexiFundData(projid, panchayat);
	}

	@Override
	public boolean saveProgress(List<Integer> ffIds, List<BigDecimal> ffCosts, List<String> statusList,
			List<String> completionDates, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.saveProgress(ffIds, ffCosts, statusList, completionDates, request);
	}

	
}
