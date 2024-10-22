package app.service;

import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import app.bean.UOMDataBean;

@Service("uomdataservice")
public interface UOMDataService {

	LinkedHashMap<Integer,List<UOMDataBean>> getUOMdata();

	List<UOMDataBean> getuomcode();

	Boolean savephyact(int uomcode, String uomdesc, String loginID);

	List<UOMDataBean> edituomdata(int id);

	Boolean updateuomdata(int id, String uomdesc, String loginID);

	Boolean deleteUOMdata(int id);
	

}
