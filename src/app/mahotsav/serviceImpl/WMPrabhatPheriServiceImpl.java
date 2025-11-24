package app.mahotsav.serviceImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import app.mahotsav.bean.WMPrabhatPheriBean;
import app.mahotsav.dao.WMPrabhatPheriDao;
import app.mahotsav.service.WMPrabhatPheriService;
import app.watershedyatra.bean.WatershedYatraBean;

@Service("WMPrabhatPheriService")
public class WMPrabhatPheriServiceImpl implements WMPrabhatPheriService{
	
	
	@Autowired
	WMPrabhatPheriDao dao;

	@Override
	public List<Map<String, Object>> getBlockListByDistrict(Integer districtCode) {
		return dao.getBlockListByDistrict(districtCode);
	}

	@Override
	public List<Map<String, Object>> getVillageListByBlock(Integer blockCode) {
		return dao.getVillageListByBlock(blockCode);
	}

	@Override
	@Transactional
	public String savePrabhatPheri(Integer stCode, Integer distCode, Integer blockCode, Integer villageCode,
			Date prabhatpheriDate, Integer maleParticipants, Integer femaleParticipants, List<MultipartFile> photos,
			String requestIp, String createdBy) {
		return dao.savePrabhatPheri(stCode, distCode, blockCode, villageCode, prabhatpheriDate, maleParticipants, femaleParticipants, photos, requestIp, createdBy);
	}

	@Override
	public String saveMahotsavPrabhatPheriDetails(WMPrabhatPheriBean userfileup, HttpSession session) {
		return dao.saveMahotsavPrabhatPheriDetails(userfileup, session);
	}

	@Override
	public List<WMPrabhatPheriBean> getWatershedMahotsavDraftList(Integer stCode) {
		return dao.getWatershedMahotsavDraftList(stCode);
	}

	@Override
	public List<WMPrabhatPheriBean> getWatershedMahotsavCompleteList(Integer stCode) {
		return dao.getWatershedMahotsavCompleteList(stCode);
	}

	@Override
	public String completeWMPrabhatPheri(List<Integer> ppid, String userid) {
		return dao.completeWMPrabhatPheri(ppid, userid);
	}

	@Override
	public String deleteWMPrabhatPheri(List<Integer> ppid, String userid) {
		return dao.deleteWMPrabhatPheri(ppid, userid);
	}

	@Override
	public boolean checkVillageWMP(Integer vCode) {
		return dao.checkVillageWMP(vCode);
	}

	@Override
	public LinkedHashMap<String, Integer> getWMPrabhatPheriVillage(Integer bCode) {
		// TODO Auto-generated method stub
		return dao.getWMPrabhatPheriVillage(bCode);
	}


}
