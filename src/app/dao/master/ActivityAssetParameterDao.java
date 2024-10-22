package app.dao.master;

import java.util.LinkedHashMap;

public interface ActivityAssetParameterDao {
	
	LinkedHashMap<Integer, String> getHead();
	LinkedHashMap<Integer, String>  getUnitMasters();
	String saveAssetParameter(Integer ahead, Integer aActivity, String parameterDesc, Integer aUnit, String loginid);

}
