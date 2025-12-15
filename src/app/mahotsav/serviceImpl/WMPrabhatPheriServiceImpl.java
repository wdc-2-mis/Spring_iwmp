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
	public String saveMahotsavPrabhatPheriDetails(WMPrabhatPheriBean userfileup, HttpSession session) {
		return dao.saveMahotsavPrabhatPheriDetails(userfileup, session);
	}

	@Override
	public List<WMPrabhatPheriBean> getWatershedMahotsavDraftList(Integer regId) {
		return dao.getWatershedMahotsavDraftList(regId);
	}

	@Override
	public List<WMPrabhatPheriBean> getWatershedMahotsavCompleteList(Integer regId) {
		return dao.getWatershedMahotsavCompleteList(regId);
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
	public String updateWMPrabhatPheri(WMPrabhatPheriBean userfileup, HttpSession session) {
		return dao.updateWMPrabhatPheri(userfileup, session);
	}

	@Override
	public List<WMPrabhatPheriBean> getWMPrabhatPheriEdit(Integer prabhatpheriId) {
		return dao.getWMPrabhatPheriEdit(prabhatpheriId);
	}

	@Override
	public List<String> getImageMahotsavPrabhatPheriId(Integer ppId) {
		return dao.getImageMahotsavPrabhatPheriId(ppId);
	}


	@Override
	public LinkedHashMap<String, Integer> getWMPrabhatPheriVillage(Integer bCode, String userid) {
		return dao.getWMPrabhatPheriVillage(bCode, userid);
	}


}
