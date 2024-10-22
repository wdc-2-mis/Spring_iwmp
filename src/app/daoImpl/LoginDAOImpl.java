package app.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.hibernate.SharedSessionContract;
import app.bean.AuditReportBean;
import app.bean.Passwordhistory;
import app.bean.UserBean;
import app.dao.LoginDAO;
import app.model.IwmpLoginLog;
import app.model.UserReg;
import app.model.outcome.MLivelihoodCoreactivity;
import app.util.SessionUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.QueryProducer;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {

    @Autowired
	SessionFactory sessionFactory;

	HttpSession session;

	@Value("${LoginQry}")
	String LoginQry;
	
	@Value("${loginuser}")
	String loginuser;
	
	@Value("${CheckLoginuser}")
	String CheckLoginuser;
	
	@Value("${chkFailLoginCounter}")
	String chkFailLoginCounter;

	@Value("${userValidate}")
	String userValidate;

	@Value("${checkpasshistory}")
	String checkpasshistory;
	
	@Value("${updatepassword}")
	String updatepassword;
	
	@Value("${select.state.slna}")
	String stateslna;
	
	@Value("${select.state.pia.wcdc}")
	String statepiawcdc;
	
	@Value("${showDataPia}")
	String showDataPia;
	
	@Value("${showDataWcdc}")
	String showDataWcdc;
	
	@Value("${deleteloginlogdata}")
	String deleteloginlogdata;
	
	@Value("${chkloginuser}")
	String chkloginuser;
	
	@Value("${checkoldpass}")
	String checkoldpass;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserReg> checkUser(String user_id) {
		Session session = sessionFactory.getCurrentSession();
		boolean userFound = false;
		//Query using Hibernate Query Language
		List<UserReg> list=null;
		try {
		session.beginTransaction();
		list = session.createQuery("from UserReg where userId=:user_id").setParameter("user_id", user_id).list();
        if ((list != null) && (list.size() > 0)) {
			
			userFound= true;
		}
		session.getTransaction().commit();
		}
		
		catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			if(session.isOpen())
			{
				session.close();
			}
		}
       
		return list; 
	}
	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	@Override
	public boolean authenticateUser(String user_id, String password,String CSRFcodeSes, HttpServletRequest request) {
		
		Session ses = sessionFactory.getCurrentSession();
		session = request.getSession(false);
		String loginid = null;
		boolean userFound = false;
		String sql=LoginQry;
		try {
			ses.beginTransaction();
		List<Object[]> list = ses.createNativeQuery(sql).setParameter("userid", user_id).setParameter("salt", CSRFcodeSes).setParameter("pwd", password).list();

		for(Object[] obj:list)
		 {
			//Object[] obj = (Object[]) itr.next();
			loginid = user_id;
			session.setAttribute("loginID", loginid);
			SessionUtility.addSession(loginid,session);
			if(user_id.contains("PI"))
			{
				session.setAttribute("username",obj[2]);
				session.setAttribute("userType", obj[1]);
				session.setAttribute("regId", obj[9]);
				session.setAttribute("stateCode", obj[5]);
				session.setAttribute("roleName", obj[7]);
			}
			else {
				session.setAttribute("username", obj[0]);
				session.setAttribute("userType", obj[1]);
                session.setAttribute("stateCode", obj[5]);
                session.setAttribute("homePage", obj[6]);
                session.setAttribute("roleName", obj[7]);
                session.setAttribute("roleId", obj[8]);
                session.setAttribute("regId", obj[9]);
                session.setAttribute("stName", obj[10]);
                
			  } 
			//System.out.println("regId fetch" + session.getAttribute("regId"));
			userFound=true; 	
			ses.getTransaction().commit();
			
		}

		}catch(Exception ex) {
			ses.getTransaction().rollback();
			System.out.println(ex.getMessage());
			ses.getTransaction().rollback();
		}finally {
			if(ses.isOpen())
			{
				ses.getTransaction().commit();
				ses.close();
			}
		
		
		}
		return userFound;
	}


	
	
@Override
public boolean insertloginlog(List<UserReg> userList, String loginAtmpt, String  userSts, HttpServletRequest request) {
	
    boolean dataInsert = false;
    Session ses = sessionFactory.getCurrentSession();
    String login_sts = "failed";
    try {
    	session = request.getSession(false);
      ses.beginTransaction();
  	  //InetAddress inetAddress = InetAddress.getLocalHost(); 
	  //String ipadd=request.getRemoteAddr();
	  InetAddress inetAddress = InetAddress.getLocalHost(); 
	  String ipadd=inetAddress.getHostAddress(); 
	  String sessionId =session.getId(); 
	  String referrer = request.getHeader("referer"); 
	  String userAgent = request.getHeader("user-agent"); 
	  IwmpLoginLog data = new IwmpLoginLog(); 
  	  data.setIpAddress(getClientIpAddr(request));
  	  data.setUserSts(userSts);
  	  data.setLoginid(userList.get(0).getUserId());
  	  String username = userList.get(0).getUserId();
  	  data.setReferrer(referrer);
  	  data.setSessionId(sessionId);
  	  data.setUserAgent(userAgent);
  	  data.setLoginSts(loginAtmpt);
  	  data.setUserType("C");
  	  data.setUserReg(userList.get(0));
  	Timestamp loginDt = new Timestamp(System.currentTimeMillis());
  	data.setLoginDt(loginDt);
  	  ses.save(data);
		
  	  if(loginAtmpt.equals("success"))
		{
  		String sql=deleteloginlogdata;
  		SQLQuery query = ses.createSQLQuery(sql);
		query.setString("loginid",username);
		query.setString("login_sts", login_sts);
		query.executeUpdate();
		}
  	   dataInsert = true;
  	ses.getTransaction().commit();
	}catch(Exception ex) {
		System.out.println(ex.getMessage());
    	ses.getTransaction().rollback();
	}finally {
		//ses.flush();
		//ses.close();
		 if(ses.isOpen())
		 {
			 ses.close();
		 }
	}
   return dataInsert;
}


public Integer invalidattempt(String loginid)
{
	Session session = sessionFactory.getCurrentSession();
	Integer counter = 0; 
	
	boolean userFound = false;
	String sql=chkFailLoginCounter;
	try {
		session.beginTransaction(); 
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("loginid", loginid);
		List list = query.list();
	    System.out.println("value of list:"+list);
		counter = (Integer)list.get(0);
		
		session.getTransaction().commit();
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println(e.getMessage());
		session.getTransaction().rollback();
	}
	finally {
		if(session.isOpen())
		{
			session.close();
		}
	}
	return counter;

}

//change password
public boolean userValidate(String loginId, String userType, HttpServletRequest request)
{
Session ses = sessionFactory.getCurrentSession();
session = request.getSession(false);
boolean userFound = false;
String sql=userValidate;
try {
	ses.beginTransaction();
SQLQuery query = ses.createSQLQuery(sql);
query.setString("loginId", loginId);
query.setString("userType", userType);

List list = query.list();

Iterator itr = list.iterator();

while(itr.hasNext()) {
	
	Object[] obj = (Object[]) itr.next();
	
	 {
		 //System.out.println("inner value of changepassword:" +obj[1]);
		session.setAttribute("changepassword", obj[1]);
		session.setAttribute("firstlogin", obj[2]);
        
	  } 
	
	userFound=true; 	
}
ses.getTransaction().commit();
}catch(Exception ex) {
	ses.getTransaction().rollback();
	System.out.println(ex.getMessage());
	ses.getTransaction().rollback();
}finally {
	if(ses.isOpen())
	{
		ses.close();
	}
}
return userFound; 
}

public int checkpasshistory(String loginId, String encryptednewpwd)
{
	Session session = sessionFactory.getCurrentSession();
	Integer counter = 0; 
	
	boolean userFound = false;
	String sql=checkpasshistory;
	try {
		session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("loginId", loginId);
		query.setString("encryptednewpwd", encryptednewpwd);
		
		List list = query.list();
	    //System.out.println("value of list:"+list);
		counter = (Integer)list.get(0);
		System.out.println("counter value:" +counter);
		
		session.getTransaction().commit();
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println(e.getMessage());
		session.getTransaction().rollback();
	}
	return counter;

}

@Override
public boolean updatepassword(String encryptednewpwd, String firstLogin, String loginId, String userType) {
	Session session = sessionFactory.getCurrentSession();
	String sql=updatepassword;
	boolean result=false;
	Transaction tx = null; 
	try {
		session.beginTransaction();
	    SQLQuery query = session.createSQLQuery(sql);
	    query.setString("encryptednewpwd",encryptednewpwd);
	    query.setString("firstLogin",firstLogin);
	    query.setString("loginId",loginId);
	    query.setString("userType",userType);
	 
	 Integer res = query.executeUpdate();
	 if(res>0) {
		 session.getTransaction().commit();
			result=true; 
	 }else {
		 session.getTransaction().rollback();
			result=false;  
	 }
	//session.update(userReg);
	
	
	}catch(Exception ex) {
		session.getTransaction().rollback();
		result = false;
	}
	return result;
}

@Override
public boolean Insertpwdhistroy(String loginId, String encryptednewpwd, String userType,HttpSession session) {
	
	System.out.println("insert password history");
    boolean dataInsert = false;
    Session ses = sessionFactory.getCurrentSession();
    try {
    	ses.beginTransaction();
  	  InetAddress inetAddress = InetAddress.getLocalHost(); 
	  String ipadd=inetAddress.getHostAddress(); 
	  String sessionId =session.getId(); 
	  
	  
	  Passwordhistory data = new Passwordhistory(); 
	  data.setLoginid(loginId);
	  data.setEncrypted_pass(encryptednewpwd);
	  data.setIp_address(ipadd);
	  data.setUser_type(userType);
	  data.setLast_updated_by(loginId);
	  data.setSession_id(sessionId);
  	  ses.save(data);
  	  
  	  dataInsert = true;
  	ses.getTransaction().commit();
	}catch(Exception ex) {
		ses.getTransaction().rollback();
    	System.out.println(ex.getMessage());
	}finally {
		if(ses.isOpen())
		{
			ses.close();
		}
	}
   return dataInsert;
}

@Override
public int st_code1(int regid) {
	
	Session session = sessionFactory.getCurrentSession();
	Integer counter = 0; 
	
	boolean userFound = false;
	String sql=stateslna;
	try {
		session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setInteger("regid", regid);
		
		List list = query.list();
	    //System.out.println("value of list:"+list);
		counter = (Integer)list.get(0);
		session.getTransaction().commit();
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println(e.getMessage());
	}
	return counter;
}

@Override
public Object[] st_code2(String userId) {
	
	Session session = sessionFactory.getCurrentSession();
	Object[] counter = null; 
	
	
	String sql=statepiawcdc;
	try {
		session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userId", userId);
		
		List list = query.list();
		
		Iterator itr = list.iterator();
		while(itr.hasNext()) {
			 counter = (Object[]) itr.next();
			
		}
	    
		session.getTransaction().commit();
		
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println(e.getMessage());
	}finally {
		if(session.isOpen())
		{
			session.close();
		}
	}
	return counter;
}

@Override
public Object[] showDataPia(String userId) {
	
	Session session = sessionFactory.getCurrentSession();
	Object[] counter = null; 
	
	String sql=showDataPia;
	try {
		session.beginTransaction(); 
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userId", userId);
		
		List list = query.list();
		
		Iterator itr = list.iterator();
		while(itr.hasNext()) {
			 counter = (Object[]) itr.next();
			
		}
	    
		session.getTransaction().commit();
		
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println(e.getMessage());
	}finally {
		if(session.isOpen())
		{
			session.close();
		}
	}
	return counter;
}

@Override
public Object[] showDataWcdc(String userId) {
	
	Session session = sessionFactory.getCurrentSession();
	Object[] counter = null; 
	
	String sql=showDataWcdc;
	try {
		session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userId", userId);
		
		List list = query.list();
		
		Iterator itr = list.iterator();
		while(itr.hasNext()) {
			 counter = (Object[]) itr.next();
			
		}
	    
		session.getTransaction().commit();
		
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println(e.getMessage());
	}finally {
		if(session.isOpen())
		{
			session.close();
		}
	}
	return counter;
}


@SuppressWarnings("null")
@Override
public UserBean authenticatedUser(String loginid, String password, String cSRFcodeSes, HttpServletRequest request) {
	Session ses = sessionFactory.getCurrentSession();
	UserBean authenticatedUser = null;
	session = request.getSession(false);
	String sql=loginuser;
	try {
	Transaction tx = ses.beginTransaction(); 
	SQLQuery query = ses.createSQLQuery(sql);
	query.setString("userid", loginid);
	query.setString("salt", cSRFcodeSes);
	query.setString("pwd", password);
	query.setString("salt", cSRFcodeSes);
	query.setString("pwd", password);
	ScrollableResults scResults = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
	scResults.setRowNumber(1);
	while(scResults.next()) {
		 authenticatedUser=new UserBean();
		
		authenticatedUser.setRegID((String) scResults.get(0));
		authenticatedUser.setStatename((String) scResults.get(1));
		authenticatedUser.setDistName((String) scResults.get(2));
		authenticatedUser.setUserId((String) scResults.get(3));
		authenticatedUser.setUserName((String) scResults.get(4));
		authenticatedUser.setUserType((String) scResults.get(5));
		authenticatedUser.setUserDesignation((String) scResults.get(6));
		authenticatedUser.setProfileId((String) scResults.get(7));
		authenticatedUser.setUserDepartment((String) scResults.get(8));
		authenticatedUser.setUserAddres((String) scResults.get(9));
		authenticatedUser.setUserPhoneNo((String) scResults.get(11));
		authenticatedUser.setUserMobileNo((String) scResults.get(12));
		authenticatedUser.setUserFaxNo((String) scResults.get(13));
		authenticatedUser.setUserEmailId((String) scResults.get(15));
		authenticatedUser.setUpdatorId((String) scResults.get(21));
		authenticatedUser.setUpdationDate((String) scResults.get(22));
		authenticatedUser.setStatus((String) scResults.get(23));
		authenticatedUser.setUserpassword((String) scResults.get(25));
		authenticatedUser.setPiaName((String) scResults.get(26));
		authenticatedUser.setPiaType((String) scResults.get(27));
		authenticatedUser.setPiaHead((String) scResults.get(28));
		authenticatedUser.setFirstLogin((String) scResults.get(30));
		authenticatedUser.setPasswdModify((String) scResults.get(31));
		//authenticatedUserList.add(authenticatedUser);
		
		
	}
	ses.getTransaction().commit();
	}catch(Exception ex) {
		ses.getTransaction().rollback();
		System.out.println(ex.getMessage());
	}finally {
		if(ses.isOpen())
				{
			ses.close();
				}
	
	
	}
	
	return authenticatedUser;
}

@Override
public String checklogin(HttpServletRequest request) {
	Session session = sessionFactory.getCurrentSession();
	String counter = ""; 
	
	boolean userFound = false;
	String sql=chkloginuser;
	try {
		session.beginTransaction(); 
		SQLQuery query = session.createSQLQuery(sql);
		/*
		 * InetAddress inetAddress = InetAddress.getLocalHost(); String
		 * ipadd=inetAddress.getHostAddress();
		 */query.setString("ipadd", getClientIpAddr(request));
		List list = query.list();
	    System.out.println("value of list:"+list);
	    if(list.size()> 0)
		counter = list.get(0).toString();
		session.getTransaction().commit();
		
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println("error");
	}
	finally {
		
	}
	return counter;
}
@Override
public Boolean checkoldpassword(String loginId, String encryptedoldpwd) {
	Session session = sessionFactory.getCurrentSession();
	Boolean res=false;
	String sql=checkoldpass;
	try {
		session.beginTransaction(); 
		List list = session.createNativeQuery(sql).setParameter("loginId", loginId).setParameter("encryptedoldpwd", encryptedoldpwd).list(); 
		if(list.size()> 0)
		res = true;
		session.getTransaction().commit();
		
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println("error");
	}
	finally {
		
	}
	
	return res;
}
@Override
public String checkloginuser(HttpServletRequest request) {
	Session session = sessionFactory.getCurrentSession();
	String counter = ""; 
	
	boolean userFound = false;
	String sql=chkloginuser;
	try {
		session.beginTransaction(); 
		SQLQuery query = session.createSQLQuery(sql);
		/*
		 * InetAddress inetAddress = InetAddress.getLocalHost(); String
		 * ipadd=inetAddress.getHostAddress();
		 */
		query.setString("ipadd", getClientIpAddr(request));
		List list = query.list();
	    System.out.println("value of list:"+list);
	    if(list.size()> 0)
		counter = list.get(0).toString();
		session.getTransaction().commit();
		
	} catch (Exception e) {
		session.getTransaction().rollback();
		System.out.println("error");
	}
	finally {
		
	}
	return counter;
}



}

