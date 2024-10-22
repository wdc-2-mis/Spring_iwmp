package app.service.UnfreezeImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.UnfreezeWorkStatusDataBean;
import app.dao.unfreeze.UnfreezeWorkStatusDao;
import app.service.unfreeze.UnfreezeWorkStatusServices;

@Service("UnfreezeWorkStatusServices")
public class UnfreezeWorkStatusServicesImpl implements UnfreezeWorkStatusServices{

	@Autowired(required = true)
	UnfreezeWorkStatusDao dao;
	
	@Override
	public List<UnfreezeWorkStatusDataBean> getUnfreezeWorkStatusData(Integer finyr, Integer project, Integer district) {
		
		return dao.getUnfreezeWorkStatusData(finyr, project, district);
	}

	@Override
	public boolean unfreezeWorkStatusComplete(String[] workcode, String project, String finCode) {
		// TODO Auto-generated method stub
		return dao.unfreezeWorkStatusComplete(workcode, project, finCode);
	}

}
