package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.UOMDataBean;
import app.dao.UOMDao;
import app.service.UOMDataService;
@Service("uomdataservice")
public class UOMDataServiceImpl implements UOMDataService{

	
	@Autowired
	UOMDao uomdao;
	
	@Override
	public LinkedHashMap<Integer, List<UOMDataBean>> getUOMdata() {
		return uomdao.getUOMdata();
	}

	@Override
	public List<UOMDataBean> getuomcode() {
		// TODO Auto-generated method stub
		return uomdao.getuomcode();
	}

	@Override
	public Boolean savephyact(int uomcode, String uomdesc, String loginID) {
		// TODO Auto-generated method stub
		return uomdao.savephyact(uomcode, uomdesc, loginID);
	}

	@Override
	public List<UOMDataBean> edituomdata(int id) {
	     return uomdao.edituomdata(id);
	}

	@Override
	public Boolean updateuomdata(int id, String uomdesc, String loginID) {
		return uomdao.updateuomdata(id, uomdesc, loginID);
	}

	@Override
	public Boolean deleteUOMdata(int id) {
		return uomdao.deleteUOMdata(id);
	}

}
