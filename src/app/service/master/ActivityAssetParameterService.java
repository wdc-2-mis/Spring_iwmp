package app.service.master;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

@Service("ActivityAssetParameterService")
public interface ActivityAssetParameterService {
	
	LinkedHashMap<Integer, String> getHead();
	LinkedHashMap<Integer, String> getUnitMasters();
	String saveAssetParameter(Integer ahead, Integer aActivity, String parameterDesc, Integer aUnit, String loginid);

}
