package app.serviceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.UserRoleMapBean;
import app.dao.UserProjectDao;
import app.model.UserReg;
import app.service.UserProjectService;

@Service("userProjectService")
public class UserProjectServiceImpl implements UserProjectService{

	@Autowired
	private UserProjectDao upDao;
	
	@Override
	//@Transactional  
	public Integer saveUserProject(UserRoleMapBean userProj, HttpSession session) 
	{
		return upDao.saveUserProject(userProj, session);
	}

	@Override
	public String showProject(int regId) {
		
		return upDao.showProject(regId);
	}

	

}
