package app.dao;


import java.util.LinkedHashMap;
import java.util.List;

import app.bean.FlexiFundMActivityBean;

public interface FlexiFundActivityDao {
    
    LinkedHashMap<Integer, List<FlexiFundMActivityBean>> getActivityData();
    
    Boolean saveActivity(String actname, String loginID);
    
    List<FlexiFundMActivityBean> editActivityData(int id);
    
    Boolean updateActivityData(int id, String actname, String loginID);
    
    Boolean deleteActivityData(int id);
    
    
}
