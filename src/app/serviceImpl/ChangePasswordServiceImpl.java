package app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.ChangePasswordDao;
import app.service.ChangePasswordService;

@Service("changePasswordService")
public class ChangePasswordServiceImpl implements ChangePasswordService{
	
	@Autowired(required = true)
	ChangePasswordDao changePasswordDao;

	@Override
	//@Transactional
	public boolean saveSecondPassword(Integer userRole,Integer stCode,Integer distCode,String project,String password) {
		// TODO Auto-generated method stub
		return changePasswordDao.saveSecondPassword(userRole, stCode, distCode, project, password);
	}

}
