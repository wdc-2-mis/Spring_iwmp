package app.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.bean.UserRoleMapBean;

@Service("userProjectService")
public interface UserProjectService {
	
	Integer saveUserProject(UserRoleMapBean userProj , HttpSession session);
	public String showProject(int regId);
}
