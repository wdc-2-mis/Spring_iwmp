package app.serviceImpl;

import app.bean.AuditReportBean;
import app.bean.UserBean;
import app.dao.LoginDAO;
import app.model.UserReg;
import app.service.LoginService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	@Autowired
	 private LoginDAO loginDAO;

	   public void setLoginDAO(LoginDAO loginDAO) {
             this.loginDAO = loginDAO;
      }
     
      public List<UserReg> checkUser(String userId){
      return loginDAO.checkUser(userId);

      }

      public boolean authenticateUser(String userId, String password, String CSRFcodeses, HttpServletRequest request){
          return loginDAO.authenticateUser(userId, password, CSRFcodeses, request);
      }
         
        
          public boolean insertloginlog(List<UserReg> userList, String loginAtmpt, String  userSts, HttpServletRequest request){
              return loginDAO.insertloginlog(userList, loginAtmpt, userSts, request);
}   
		public Integer invalidattempt(String loginid) {
			return loginDAO.invalidattempt(loginid);
		}
      
		public boolean userValidate(String loginId, String userType, HttpServletRequest request){
		return loginDAO.userValidate(loginId, userType, request);
		}
		public int checkpasshistory(String loginId, String encryptednewpwd) {
			return loginDAO.checkpasshistory(loginId, encryptednewpwd);
		}
		public boolean updatepassword(String encryptednewpwd, String firstLogin, String loginId, String userType)
		{
			return loginDAO.updatepassword(encryptednewpwd, firstLogin, loginId, userType);
		}
		public boolean Insertpwdhistroy(String loginId, String encryptednewpwd, String userType,HttpSession session)
		{
			return loginDAO.Insertpwdhistroy(loginId, encryptednewpwd, userType,session);
		}

		public UserBean authenticatedUser(String loginid, String password, String cSRFcodeSes,
				HttpServletRequest request)
		{
			return loginDAO.authenticatedUser(loginid, password, cSRFcodeSes, request);
		}
//for slna password reset
		public int st_code1(int regid) {
			return loginDAO.st_code1(regid);
		}
		public Object[] st_code2(String userId) {
			return loginDAO.st_code2(userId);
		}
		public Object[] showDataPia(String userId) {
			return loginDAO.showDataPia(userId);
		}
		public Object[] showDataWcdc(String userId) {
			return loginDAO.showDataWcdc(userId);
		}

		@Override
		public String checklogin(HttpServletRequest request) {
			return loginDAO.checklogin(request);
		}

		@Override
		public Boolean checkoldpassword(String loginId, String encryptedoldpwd) {
			return loginDAO.checkoldpassword(loginId, encryptedoldpwd);
		}

		@Override
		public String checkloginuser(HttpServletRequest request) {
			return loginDAO.checkloginuser(request);
		}
}