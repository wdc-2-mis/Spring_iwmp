package app.serviceImpl;


import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.FlexiFundMActivityBean;
import app.dao.FlexiFundActivityDao;
import app.service.FlexiFundActivityService;



@Service("flexiFundActivityService")
public class FlexiFundActivityServiceImpl implements FlexiFundActivityService {
    
    @Autowired
    FlexiFundActivityDao flexiFundActivityDao;
    
    @Override
    public LinkedHashMap<Integer, List<FlexiFundMActivityBean>> getActivityData() {
        return flexiFundActivityDao.getActivityData();
    }
    
    @Override
    public Boolean saveActivity(String actname, String loginID) {
        return flexiFundActivityDao.saveActivity(actname, loginID);
    }
    
    @Override
    public List<FlexiFundMActivityBean> editActivityData(int id) {
        return flexiFundActivityDao.editActivityData(id);
    }
    
    @Override
    public Boolean updateActivityData(int id, String actname, String loginID) {
        return flexiFundActivityDao.updateActivityData(id, actname, loginID);
    }
    
    @Override
    public Boolean deleteActivityData(int id) {
        return flexiFundActivityDao.deleteActivityData(id);
    }

}
