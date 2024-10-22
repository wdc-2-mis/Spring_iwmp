package app.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import app.bean.AuditReportBean;
import app.bean.UserBean;
import app.model.UserReg;

public interface LoginService {

	public List<UserReg> checkUser(String user_id);
	public boolean authenticateUser(String userId, String password, String CSRFcodeses, HttpServletRequest request);
	public boolean insertloginlog(List<UserReg> userList, String loginAtmpt, String userSts, HttpServletRequest request);
	public Integer invalidattempt(String loginid);
	
	//change password
	public boolean userValidate(String loginId, String userType, HttpServletRequest request);
	public int checkpasshistory(String loginId, String encryptednewpwd);
	public boolean updatepassword(String encryptednewpwd, String firstLogin, String loginId, String userType);
	public boolean Insertpwdhistroy(String loginId, String encryptednewpwd, String userType,HttpSession session);
	public UserBean authenticatedUser(String loginid, String password, String cSRFcodeSes,
			HttpServletRequest request);
	
	//password reset at slna
	public int st_code1(int regid);
	public Object[] st_code2(String userId);
	public Object[] showDataPia(String userId);
	public Object[] showDataWcdc(String userId);
	public String checklogin(HttpServletRequest request);
	public Boolean checkoldpassword(String loginId, String encryptedoldpwd);
	public String checkloginuser(HttpServletRequest request);
	
	
	
	}
