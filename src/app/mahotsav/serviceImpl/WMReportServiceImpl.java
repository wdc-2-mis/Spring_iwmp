package app.mahotsav.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.dao.WMReportDao;
import app.mahotsav.service.WMReportService;
import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;


@Service("WMReportService")
public class WMReportServiceImpl implements WMReportService{
	
	@Autowired
	WMReportDao WMDao;

	@Override
	public List<InaugurationMahotsavBean> getStateWMInaugurationReport() {
		return WMDao.getStateWMInaugurationReport();
	}

	@Override
	public List<InaugurationMahotsavBean> getProjLvlWMPrgReport() {
		// TODO Auto-generated method stub
		return WMDao.getProjLvlWMPrgReport();
	}

	@Override
	public List<SocialMediaReport> getWMSocailMediaReport(Integer stcd, Integer dcode, Integer bcode, Integer vcode) {
		return WMDao.getWMSocailMediaReport(stcd, dcode, bcode, vcode);
	}
	
	@Override
	public Map<String, String> getDistrictList(int stateCode) {
		// TODO Auto-generated method stub
		Map<String, String> districtList=new LinkedHashMap<String, String>();
		for (IwmpDistrict temp : WMDao.getDistrictList(stateCode)) {
//			districtList.put(temp.getDcode()+"", temp.getDistName());
			districtList.put(temp.getDistName(), temp.getDcode()+"");
		}
		return districtList ;
	}
	
	@Override
	public Map<String, String> getblockList(Integer userState, Integer district) {
		
		Map<String, String> blockList=new LinkedHashMap<String, String>();
		for(IwmpBlock temp: WMDao.getBlockList(userState, district)) {
//			blockList.put(temp.getBcode()+"", temp.getBlockName());
			blockList.put(temp.getBlockName(), temp.getBcode()+"");
		}
		return blockList;
	}
	
	@Override
	public Map<String, String> getmahotsavvillageList(int block) {
		Map<String, String> villageList=new LinkedHashMap<String, String>();
		for(IwmpVillage temp: WMDao.getVillageList(block)) {
//			villageList.put(temp.getVcode()+"", temp.getVillageName());
			villageList.put(temp.getVillageName(), temp.getVcode()+"");
		}
		return villageList;
	}
	

}
