package app.service;

import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import app.model.IwmpState;


@Service("stateMasterService")
public interface StateMasterService {
	LinkedHashMap<Integer,String> getAllState();
	LinkedHashMap<Integer,String> getStateByStateCode(Integer stateCode);
	LinkedHashMap<Integer,String> getUserState(String loginid);
	List<IwmpState> getStateList ();
	LinkedHashMap<Integer,String> getStateByDistCode(Integer distCode);
	
}
