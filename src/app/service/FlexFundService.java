package app.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.model.FlexiFundDetails;

@Service("FlexFundService")
public interface FlexFundService {

	LinkedHashMap<Integer,String> getflexiactivity();

	LinkedHashMap<String, Integer> getFlexiFundGM(int pCode);

	boolean saveFlexiFundData(Integer projId, Integer gcode, List<Integer> activityList, List<String> detailsList, List<Double> estCostList,
			List<Double> costList, List<MultipartFile> photos, List<Integer> photoCountList, List<String> latitudeList, List<String> longitudeList,
			String status, List<String> remarksList, String createdBy, HttpServletRequest request);

	List<FlexiFundDetails> getFlexiFundByProjectAndGcode(Integer projId, Integer gcode);

	boolean updateFlexiFundData(List<Integer> rowIds, List<Integer> activityList, List<String> detailsList,
			List<Double> estCostList, List<Double> costList, List<MultipartFile> photos, List<Integer> photoRowIndex,
			String status, List<String> remarksList, String user);

	boolean deletePhotoById(int photoId);

	Map<String, Object> savePhoto(MultipartFile file, int flexiFundId, String lat, String lon);
	
	
}
