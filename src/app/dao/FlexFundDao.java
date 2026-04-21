package app.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import app.bean.FlexiFundMActivityBean;
import app.model.FlexiFundDetails;

public interface FlexFundDao {

	LinkedHashMap<Integer, String> getflexiactivity();

	LinkedHashMap<String, Integer> getFlexiFundGM(int pCode);

	boolean saveFlexiFundData(Integer projId, Integer gcode, List<Integer> activityList, List<String> detailsList, List<Double> estCostList,
			List<Double> costList, List<MultipartFile> photos, List<Integer> photoCountList, List<String> latitudeList, List<String> longitudeList,
			String status, List<String> remarksList, String createdBy, HttpServletRequest request);

	List<FlexiFundDetails> getFlexiFundByProjectAndGcode(Integer projId, Integer gcode);

	boolean updateFlexiFundData(List<Integer> rowIds, List<Integer> activityList, List<String> detailsList,
			List<Double> estCostList, List<Double> costList, List<MultipartFile> photos, List<Integer> photoRowIndex,
			String status, List<String> remarksList, String user);

	
	boolean deletePhotoById(Integer photoId);

	Map<String, Object> savePhoto(MultipartFile file, int flexiFundId, String lat, String lon, Integer projId, Integer gcode);

	List<FlexiFundMActivityBean> getStateWiseFlexiFundReport();

	boolean deleteFlexiFundRow(int id);

	

}
