package app.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import app.bean.UserBean;
import app.bean.UserRoleMapBean;
import app.dao.UserDao;
import app.daoImpl.UserDaoImpl;
import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpState;
import app.model.IwmpUserProjectMap;
import app.model.UserMap;
import app.model.UserReg;

/**
 * @author kedar
 */
public class CommonUtility {
	
	@Autowired
	public UserDao dao;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UserMap userMap;
	
	//@Transactional  
	public UserReg createUserRegModel(UserBean userBean, HttpServletRequest request)
	{
		UserReg userReg = new UserReg();
		String [] projectList=null;
		Session session = sessionFactory.openSession();
		Set<UserMap> userMaps =new HashSet<UserMap>(0);
		String [] stateList=userBean.getUserState().split(",");
		Integer[] finalStateList = new Integer[stateList.length];
		int index = 0;
		for(int i = 0;i < stateList.length;i++)
		{
			try
		    {  	finalStateList[index] = Integer.parseInt(stateList[i]);
		        index++;
		    }
		    catch (NumberFormatException nfe)
		    {
		        //Do nothing or you could print error if you want
		    }
		}
		// Now there will be a number of 'invalid' elements 
		// at the end which will need to be trimmed
		finalStateList = Arrays.copyOf(finalStateList, index);
		
		if(userBean.getUserType().equalsIgnoreCase("PI"))
		{
		//	 projectList=userBean.getUserProject().split(",");
		}
		
		userReg.setRegId(dao.regId());
		userReg.setUserName(userBean.getUserName());
		userReg.setUserNgoid(userBean.getUserNgoid());
		userReg.setUserRegwith(userBean.getUserRegwith());
		userReg.setDesignation(userBean.getUserDesignation());
		userReg.setDepartment(userBean.getUserDepartment());
		userReg.setEmail(userBean.getUserEmailId());
		userReg.setPhoneNo(userBean.getUserPhoneNo());
		userReg.setMobileNo(userBean.getUserMobileNo());
		userReg.setFaxNo(userBean.getUserFaxNo());
		userReg.setUserType(userBean.getUserType());
		userReg.setStatus("NEW");
		userReg.setCurAddress(userBean.getUserAddres());
		userReg.setCreationDate(new Timestamp(new java.util.Date().getTime()));
		userReg.setRequestIp(request.getRemoteAddr());
		if(userBean.getUserType().equalsIgnoreCase("DL") || userBean.getUserType().equalsIgnoreCase("SL"))
		{	
			for(Integer state:finalStateList)
			{
				userMap=new UserMap();
				IwmpState iwmpState = (IwmpState) session.get(IwmpState.class, state);
				userMap.setCreatorDate(new Date(new java.util.Date().getTime()));
				userMap.setLastUpdatedDate(new Date(new java.util.Date().getTime()));
				userMap.setRequestIp(request.getRemoteAddr());
				userMap.setIwmpState(iwmpState);
				userMap.setUserReg(userReg);
				userMaps.add(userMap);
			}
		}	
		
		if(userBean.getUserType().equalsIgnoreCase("DI"))
		{	
			for(Integer state:finalStateList)
			{
				userMap=new UserMap();
				Integer distCode=new Integer(userBean.getUserDistrict());
				IwmpState iwmpState = (IwmpState) session.get(IwmpState.class, state);
				IwmpDistrict iwmpDistrict = (IwmpDistrict) session.get(IwmpDistrict.class, distCode);
				userMap.setCreatorDate(new Date(new java.util.Date().getTime()));
				userMap.setLastUpdatedDate(new Date(new java.util.Date().getTime()));
				userMap.setRequestIp(request.getRemoteAddr());
				userMap.setIwmpState(iwmpState);
				userMap.setIwmpDistrict(iwmpDistrict);
				userMap.setUserReg(userReg);
				userMaps.add(userMap);
			}
		}	
		
		if(userBean.getUserType().equalsIgnoreCase("PI") || userBean.getUserType().equalsIgnoreCase("NGO"))
		{	
			//for(String project:projectList)
			for(Integer state:finalStateList)
			{
				userMap=new UserMap();
				Integer distCode=new Integer(userBean.getUserDistrict());
			//	System.out.println("dCode: "+distCode);
				IwmpState iwmpState = (IwmpState) session.get(IwmpState.class, state);
				IwmpDistrict iwmpDistrict = (IwmpDistrict) session.get(IwmpDistrict.class, distCode);
			//	System.out.println("distCode: "+iwmpDistrict.getDistCode());
				userMap.setCreatorDate(new Date(new java.util.Date().getTime()));
				userMap.setLastUpdatedDate(new Date(new java.util.Date().getTime()));
				userMap.setRequestIp(request.getRemoteAddr());
				userMap.setIwmpState(iwmpState);
				userMap.setIwmpDistrict(iwmpDistrict);
				userMap.setUserReg(userReg);
				userMaps.add(userMap);
			}
		}	
		

		//userReg.setIwmpUserMaps((Set<UserMap>) userMaps);

		userReg.setIwmpUserMaps(userMaps);
		session.close();
		return userReg;
	}
	
	/*
	 * @Transactional public IwmpUserProjectMap createUserProjAssign(UserRoleMapBean
	 * userBean, HttpServletRequest request) { IwmpUserProjectMap userProjectMap =
	 * new IwmpUserProjectMap(); String [] projList=null; UserMap userMap=null;
	 * Session session = sessionFactory.openSession(); List<UserMap> userMaps =new
	 * ArrayList<UserMap>(); String [] stateList=userBean.getUserState().split(",");
	 * Long[] finalStateList = new Long[stateList.length]; int index = 0; for(int i
	 * = 0;i < stateList.length;i++) { try { finalStateList[index] =
	 * Long.parseLong(stateList[i]); index++; } catch (NumberFormatException nfe) {
	 * //Do nothing or you could print error if you want } } // Now there will be a
	 * number of 'invalid' elements // at the end which will need to be trimmed
	 * finalStateList = Arrays.copyOf(finalStateList, index);
	 * 
	 * if(userBean.getUserType().equalsIgnoreCase("PI")) { //
	 * projectList=userBean.getUserProject().split(","); }
	 * 
	 * userReg.setRegId(dao.regId()); userReg.setUserName(userBean.getUserName());
	 * userReg.setDesignation(userBean.getUserDesignation());
	 * userReg.setDepartment(userBean.getUserDepartment());
	 * userReg.setEmail(userBean.getUserEmailId());
	 * userReg.setPhoneNo(userBean.getUserPhoneNo());
	 * userReg.setMobileNo(userBean.getUserMobileNo());
	 * userReg.setFaxNo(userBean.getUserFaxNo());
	 * userReg.setUserType(userBean.getUserType()); userReg.setStatus("NEW");
	 * userReg.setCurAddress(userBean.getUserAddres()); userReg.setCreationDate(new
	 * Timestamp(new java.util.Date().getTime()));
	 * userReg.setRequestIp(request.getRemoteAddr());
	 * if(userBean.getUserType().equalsIgnoreCase("DL") ||
	 * userBean.getUserType().equalsIgnoreCase("SL")) { for(Long
	 * state:finalStateList) { userMap=new UserMap(); IwmpState iwmpState =
	 * (IwmpState) session.get(IwmpState.class, state); userMap.setCreatorDate(new
	 * Date(new java.util.Date().getTime())); userMap.setLastUpdatedDate(new
	 * Date(new java.util.Date().getTime()));
	 * userMap.setRequestIp(request.getRemoteAddr());
	 * userMap.setIwmpState(iwmpState); userMap.setUserReg(userReg);
	 * userMaps.add(userMap); } }
	 * 
	 * if(userBean.getUserType().equalsIgnoreCase("DI")) { for(Long
	 * state:finalStateList) { userMap=new UserMap(); Integer distCode=new
	 * Integer(userBean.getUserDistrict()); IwmpState iwmpState = (IwmpState)
	 * session.get(IwmpState.class, state); IwmpDistrict iwmpDistrict =
	 * (IwmpDistrict) session.get(IwmpDistrict.class, distCode);
	 * userMap.setCreatorDate(new Date(new java.util.Date().getTime()));
	 * userMap.setLastUpdatedDate(new Date(new java.util.Date().getTime()));
	 * userMap.setRequestIp(request.getRemoteAddr());
	 * userMap.setIwmpState(iwmpState); userMap.setIwmpDistrict(iwmpDistrict);
	 * userMap.setUserReg(userReg); userMaps.add(userMap); } }
	 * 
	 * if(userBean.getUserType().equalsIgnoreCase("PI")) { //for(String
	 * project:projectList) for(Long state:finalStateList) { userMap=new UserMap();
	 * Integer distCode=new Integer(userBean.getUserDistrict());
	 * System.out.println("dCode: "+distCode); IwmpState iwmpState = (IwmpState)
	 * session.get(IwmpState.class, state); IwmpDistrict iwmpDistrict =
	 * (IwmpDistrict) session.get(IwmpDistrict.class, distCode);
	 * System.out.println("distCode: "+iwmpDistrict.getDistCode());
	 * userMap.setCreatorDate(new Date(new java.util.Date().getTime()));
	 * userMap.setLastUpdatedDate(new Date(new java.util.Date().getTime()));
	 * userMap.setRequestIp(request.getRemoteAddr());
	 * userMap.setIwmpState(iwmpState); userMap.setIwmpDistrict(iwmpDistrict);
	 * userMap.setUserReg(userReg); userMaps.add(userMap); } }
	 * 
	 * userReg.setUserMaps(userMaps); return userReg; }
	 */
	

	 	
}
