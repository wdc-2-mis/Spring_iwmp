package app.dao;

import javax.servlet.http.HttpSession;

import app.bean.UserRoleMapBean;

public interface UserProjectDao {
	
	Integer saveUserProject(UserRoleMapBean userProj, HttpSession session);
	String showProject(int regId);
}
