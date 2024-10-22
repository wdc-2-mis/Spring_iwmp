package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.model.IwmpState;


public interface StateMasterDao {
	LinkedHashMap<Integer, String> getAllState();
	LinkedHashMap<Integer, String> getStateByStateCode(Integer stateCode);
	LinkedHashMap<Integer,String> getUserState(String loginid);
	List<IwmpState> getStateList ();
	LinkedHashMap<Integer,String> getStateByDistCode(Integer distCode);
}
