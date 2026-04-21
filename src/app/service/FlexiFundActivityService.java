package app.service;


import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.FlexiFundMActivityBean;



@Service("flexiFundActivityService")
public interface FlexiFundActivityService {
    
    LinkedHashMap<Integer, List<FlexiFundMActivityBean>> getActivityData();
    
    Boolean saveActivity(String actname, String loginID);
    
    List<FlexiFundMActivityBean> editActivityData(int id);
    
    Boolean updateActivityData(int id, String actname, String loginID);
    
    Boolean deleteActivityData(int id);
    
    
}
