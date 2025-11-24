package app.mahotsav.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import app.mahotsav.bean.WMPrabhatPheriBean;
import app.watershedyatra.bean.WatershedYatraBean;
public interface WMPrabhatPheriService {

List<Map<String, Object>> getBlockListByDistrict(Integer districtCode);
    
    List<Map<String, Object>> getVillageListByBlock(Integer blockCode);

	String savePrabhatPheri(Integer stCode, Integer distCode, Integer blockCode, Integer villageCode,
			Date prabhatpheriDate, Integer maleParticipants, Integer femaleParticipants, List<MultipartFile> photos,
			String requestIp, String createdBy);

	String saveMahotsavPrabhatPheriDetails(WMPrabhatPheriBean userfileup, HttpSession session);

	List<WMPrabhatPheriBean> getWatershedMahotsavDraftList(Integer stCode);

	List<WMPrabhatPheriBean> getWatershedMahotsavCompleteList(Integer stCode);

	String completeWMPrabhatPheri(List<Integer> ppid, String userid);

	String deleteWMPrabhatPheri(List<Integer> ppid, String userid);

	boolean checkVillageWMP(Integer vCode);
	
	LinkedHashMap<String, Integer> getWMPrabhatPheriVillage(Integer bCode);

}
