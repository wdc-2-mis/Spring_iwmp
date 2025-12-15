package app.mahotsav.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import app.mahotsav.bean.WMPrabhatPheriBean;

public interface WMPrabhatPheriDao {

	List<Map<String, Object>> getBlockListByDistrict(Integer districtCode);
    
    List<Map<String, Object>> getVillageListByBlock(Integer blockCode);
    
    String saveMahotsavPrabhatPheriDetails(WMPrabhatPheriBean userfileup, HttpSession session);

    List<WMPrabhatPheriBean> getWatershedMahotsavDraftList(Integer regId);

	List<WMPrabhatPheriBean> getWatershedMahotsavCompleteList(Integer regId);
	
	String completeWMPrabhatPheri(List<Integer> ppid, String userid);
	String deleteWMPrabhatPheri(List<Integer> ppid, String userid);
	boolean checkVillageWMP(Integer vCode);
	LinkedHashMap<String, Integer> getWMPrabhatPheriVillage(Integer bCode, String userid);
	String updateWMPrabhatPheri(WMPrabhatPheriBean userfileup, HttpSession session);
	List<WMPrabhatPheriBean> getWMPrabhatPheriEdit(Integer prabhatpheriId);

	List<String> getImageMahotsavPrabhatPheriId(Integer ppId);
	
	
}
