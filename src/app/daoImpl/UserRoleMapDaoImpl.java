package app.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.model.IwmpAppRoleMap;
import app.bean.MenuMap;
import app.bean.UserRoleMapBean;
import app.dao.UserRoleMapDao;
import app.model.IwmpMProject;
import app.model.IwmpUserProjectMap;

import app.model.UserReg;

@Repository("userRoleMapDao")
public class UserRoleMapDaoImpl implements UserRoleMapDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	IwmpMProject iwmpMProject;
	
	@Autowired(required = true)
	IwmpUserProjectMap iwmpUserProjectMap;
	
	@Autowired
	UserReg userReg;
	
	@Value("${getAllApplication}")
	String getAllApplication;
	
	@Value("${getProfile}")
	String getUserDetailByRegId;
	
	@Value("${getAllRole}")
	String getAllRole;
	
	@Value("${getUserListUnAssigned}")
	String getUserListUnAssigned;
	
	@Value("${getUserListAssigned}")
	String getUserListAssigned;
	
	@Value("${getUserListUnAssignedByDistrict}")
	String getUserListUnAssignedByDistrict;
	
	@Value("${getUserListAssignedByDistrict}")
	String getUserListAssignedByDistrict;
	
	@Value("${getUserListUnAssignedByState}")
	String getUserListUnAssignedByState;
	
	@Value("${getUserListAssignedByState}")
	String getUserListAssignedByState;
	
	@Value("${deleteUserProjectMap}")
	String deleteUserProjectMap;
	
	@Value("${getRoleAssignedForUser}")
	String getRoleAssignedForUser;
	
	@Value("${getRegIdByUserId}")
	String getRegIdByUserId;
	
	@Value("${getSpecificRoleList}")
	String getSpecificRoleList;
	
	@Value("${getProjectByDistrict}")
	String getProjectByDistrict;
	
	@Value("${getProjectByUser}")
	String getProjectByUser;
	
	@Value("${saveUserRoleMap}")
	String saveUserRoleMap;
	
	@Value("${updateUserRoleMap}")
	String updateUserRoleMap;
	
	@Value("${getSpecifyRole}")
	String getSpecifyRole;
	
	@Override
	public LinkedHashMap<Integer,String> getApplicationList() {

		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		String sql=getAllApplication;
		try {
			session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		List<Object[]> rows = query.list();
		String[] strArray=null;
		  for(Object[] row : rows){
			  strArray = Arrays.stream(row).toArray(String[]::new);
		  }
			if ((strArray != null) && (strArray.length> 0)) {
				 for(int i=0;i< strArray.length;i++){
						  map.put(Integer.parseInt(strArray[i]), strArray[++i]);
				  }
			}
			session.getTransaction().commit();
		}catch(Exception ex) {
			System.out.println("Error: "+ex.getCause());
			ex.getStackTrace();
			session.getTransaction().rollback();
		}finally {
			
		}
		return map; 
	}
	
	@Override
	public LinkedHashMap<Integer,String> getRoleList(HttpSession hsession) {

		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		String sql="";
		
		try {
			
			if(hsession.getAttribute("loginid").equals("USERADMIN")) 
			{
				sql=getSpecifyRole;
			}
			else
			sql=getAllRole;
			
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			List<Object[]> rows = query.list();
			String[] strArray=null;
			List<String[]> list = new ArrayList<String[]>();
			for(Object[] row : rows){
				strArray = Arrays.stream(row).toArray(String[]::new);
				map.put(Integer.parseInt(strArray[0]), strArray[1]);
			}
			/*
			 * if ((list != null) && (list.size()> 0)) { for(int i=0;i< list.size();i++){
			 * map.put(Integer.parseInt(strArray[i]), strArray[++i]); } }
			 */
			session.getTransaction().commit();
		}
		catch(Exception ex) {
			System.out.print("line number : ");
		      System.out.print(Thread.currentThread().getStackTrace()[1].getLineNumber());
			ex.getStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			
		}
		return map; 
	}

	@Override
	public LinkedHashMap<String, String> getUserListUnAssigned(String userType) {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		String sql=getUserListUnAssigned;
		try {
			session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userType",userType);
		List<Object[]> rows = query.list();
		String[] strArray=null;
		List<String[]> list = new ArrayList<String[]>();
		  for(Object[] row : rows){
			  strArray = Arrays.stream(row).toArray(String[]::new);
			  map.put(strArray[0], strArray[1]);
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			System.out.print("getUserListUnAssigned line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.getStackTrace();
			session.getTransaction().rollback();
		}finally {
			//session.flush();
		//session.close();
		}
		return map;
	}
	
	@Override
	public LinkedHashMap<String, String> getUserListAssigned(String userType) {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		String sql=getUserListAssigned;
		try {
			session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userType",userType);
		List<Object[]> rows = query.list();
		String[] strArray=null;
		List<String[]> list = new ArrayList<String[]>();
		  for(Object[] row : rows){
			  strArray = Arrays.stream(row).toArray(String[]::new);
			  map.put(strArray[0], strArray[1]);
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			System.out.print("getUserListAssigned line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.getStackTrace();
			session.getTransaction().rollback();
		}finally {
		//	session.flush();
		//session.close();
		}
		return map;
	}
	
	@Override
	public LinkedHashMap<String, String> getUserListUnAssignedByState(String userType,Integer stateCode) {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		String sql=getUserListUnAssignedByState;
		try {
			session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userType",userType);
		query.setInteger("stCode",stateCode);
		List<Object[]> rows = query.list();
		String[] strArray=null;
		List<String[]> list = new ArrayList<String[]>();
		  for(Object[] row : rows){
			  strArray = Arrays.stream(row).toArray(String[]::new);
			  map.put(strArray[0], strArray[1]);
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			//System.out.print("getUserListUnAssignedByState line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		//	session.flush();
	//	session.close();
		}
		return map;
	}
	
	@Override
	public LinkedHashMap<String, String> getUserListAssignedByState(String userType,Integer stateCode) {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		String sql=getUserListAssignedByState;
		try {
			session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userType",userType);
		query.setInteger("stCode",stateCode);
		List<Object[]> rows = query.list();
		String[] strArray=null;
		List<String[]> list = new ArrayList<String[]>();
		  for(Object[] row : rows){
			  strArray = Arrays.stream(row).toArray(String[]::new);
			  map.put(strArray[0], strArray[1]);
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			session.getTransaction().rollback();
			//System.out.print("getUserListUnAssignedByState line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
			//session.flush();
		//session.close();
		}
		return map;
	}

	@Override
	public boolean saveUserRoleMap(UserRoleMapBean userRoleMap,String[] projectArray) {
		Session session = sessionFactory.getCurrentSession();
		boolean result=false;
		String sql=saveUserRoleMap;
		Date d = new Date();
		boolean flag=true,saveUserProjectMap=true;;
		session.beginTransaction();
		int a=0;
		try {
		UserReg userReg = (UserReg) session.get(UserReg.class, Integer.parseInt(userRoleMap.getUser().toString()));
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userId",userReg.getUserId());
		query.setInteger("roleId", userRoleMap.getRole());
		query.setInteger("appId", userRoleMap.getApplication());
		query.setString("updatedBy", userRoleMap.getUpdateby());
		query.setString("requestIp", userRoleMap.getRequestip());
		a=query.executeUpdate();
		}catch(Exception ex) {
			flag=false;
			/*
			 * session.getTransaction().rollback(); System.out.print("line number : ");
			 * System.out.print(ex.getStackTrace()[0].getLineNumber());
			 * ex.printStackTrace();
			 */
		}finally {
			if(a>0 && flag) {
				try {
					if(projectArray!=null)
				for(int i=0;i<projectArray.length;i++) {
					iwmpUserProjectMap = new IwmpUserProjectMap();
					iwmpMProject = new IwmpMProject();
					iwmpMProject.setProjectId(Integer.parseInt(projectArray[i].toString()));
					iwmpUserProjectMap.setIwmpMProject(iwmpMProject);
					iwmpUserProjectMap.setInsertDate(d);
					iwmpUserProjectMap.setUpdatedBy(userRoleMap.getUpdateby());
					iwmpUserProjectMap.setIpAddress(userRoleMap.getRequestip());
					saveUserProjectMap=saveUserProjectMap(iwmpUserProjectMap,userRoleMap.getUser(),session.getTransaction());
					if(!saveUserProjectMap) {
						session.getTransaction().rollback();
						return false;
					}
						
				}
					result=true;
					session.getTransaction().commit();	
				}catch(Exception e) {
					result=false;
					session.getTransaction().rollback();
				}
				
			}
			else {
				result=false;
				session.getTransaction().rollback();
			}
			//session.flush();
		//session.close();
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateUserRoleMap(UserRoleMapBean userRoleMap,String[] projectArray) {
		Session session = sessionFactory.getCurrentSession();
		boolean result=false;
		String sql=updateUserRoleMap;
		Date d = new Date();
		boolean flag=true,saveUserProjectMap=true;
		session.beginTransaction();
		int a=0;
		
		try {
		UserReg userReg = (UserReg) session.get(UserReg.class, Integer.parseInt(userRoleMap.getUser()));
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setString("userId",userReg.getUserId());
		sqlQuery.setInteger("roleId", userRoleMap.getRole());
		sqlQuery.setString("updatedBy", userRoleMap.getUpdateby());
		sqlQuery.setString("requestIp", userRoleMap.getRequestip());
		a=sqlQuery.executeUpdate();
		
		}catch(Exception ex) {
			flag=false;
			/*
			 * session.getTransaction().rollback(); System.out.print("line number : ");
			 * System.out.print(ex.getStackTrace()[0].getLineNumber()); ex.getStackTrace();
			 */
		}finally {
			if(a>0 && flag) {
				try {
					if(projectArray!=null) {
						deleteUserProjectMap(Integer.parseInt(userRoleMap.getUser()),session.getTransaction());
				for(int i=0;i<projectArray.length;i++) {
					iwmpUserProjectMap = new IwmpUserProjectMap();
					iwmpMProject = new IwmpMProject();
					iwmpMProject.setProjectId(Integer.parseInt(projectArray[i].toString()));
					iwmpUserProjectMap.setIwmpMProject(iwmpMProject);
					iwmpUserProjectMap.setInsertDate(d);
					iwmpUserProjectMap.setUpdatedBy(userRoleMap.getUpdateby());
					iwmpUserProjectMap.setIpAddress(userRoleMap.getRequestip());
					saveUserProjectMap=saveUserProjectMap(iwmpUserProjectMap,userRoleMap.getUser(),session.getTransaction());
					if(!saveUserProjectMap) {
						session.getTransaction().rollback();
						return false;
					}
						
				}
					}
					result=true;
					session.getTransaction().commit();	
				}catch(Exception e) {
					result=false;
					session.getTransaction().rollback();
				}
				
			}
			else {
				result=false;
				session.getTransaction().rollback();
			}
			//session.flush();
		//session.close();
		}
		return result;
	}
	
	public boolean saveUserProjectMap(IwmpUserProjectMap iwmpUserProjectMap,String regId,Transaction tx) {
		boolean result=true;
		Session session = sessionFactory.getCurrentSession();
		//session.getTransaction().begin(); 
		try {
			UserReg userReg = (UserReg) session.get(UserReg.class, Integer.parseInt(regId));
			//userReg.setRegId(Long.parseLong(userRoleMap.getUser()));
			iwmpUserProjectMap.setUserReg(userReg);
		session.save(iwmpUserProjectMap);
		//session.getTransaction().commit();
		}catch(Exception ex) {
			result=false;
			tx.rollback();
		}finally {
			//session.flush();
			//session.close();
		}
		return result;
	}

	public void deleteUserProjectMap(Integer regId,Transaction tx) {
		Session session = sessionFactory.getCurrentSession();
		//session.getTransaction().begin(); 
		String sql=deleteUserProjectMap;
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setLong("regId",regId);
			query.executeUpdate();
			//tx.commit();
		}catch(Exception ex) {
			tx.rollback();
		}finally {
			//session.flush();
			//session.close();
		}
		
	}
	@Override
	public LinkedHashMap<String, String> getUserListUnAssignedByDistrict(String userType, Integer stateCode,
			Integer distCode) {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		String sql=getUserListUnAssignedByDistrict;
		try {
			session.beginTransaction(); 
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userType",userType);
		query.setInteger("stCode",stateCode);
		query.setInteger("distCode",distCode);
		List<Object[]> rows = query.list();
		String[] strArray=null;
		List<String[]> list = new ArrayList<String[]>();
		  for(Object[] row : rows){
			  strArray = Arrays.stream(row).toArray(String[]::new);
			  map.put(strArray[0], strArray[1]+"-"+strArray[2]);
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			//System.out.print("getUserListUnAssignedByState line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		//	session.flush();
		//session.close();
		}
		return map;
	}
	
	@Override
	public LinkedHashMap<String, String> getUserListAssignedByDistrict(String userType, Integer stateCode,
			Integer distCode) {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		String sql=getUserListAssignedByDistrict;
		try {
			session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("userType",userType);
		query.setInteger("stCode",stateCode);
		query.setInteger("distCode",distCode);
		List<Object[]> rows = query.list();
		String[] strArray=null;
		List<String[]> list = new ArrayList<String[]>();
		  for(Object[] row : rows){
			  strArray = Arrays.stream(row).toArray(String[]::new);
			  map.put(strArray[0], strArray[1]+"-"+strArray[2]);
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			//System.out.print("getUserListUnAssignedByState line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			//session.flush();
		//session.close();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, String> getProjectByDistrict(Integer stateCode, Integer distCode) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpMProject> rows = new ArrayList<IwmpMProject>();
		String hql=getProjectByDistrict;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setLong("stCode",Long.parseLong(Integer.toString(stateCode)));
		query.setLong("distCode",Long.parseLong(Integer.toString(distCode)));
		//query.setResultTransformer(Transformers.aliasToBean(IwmpMProject.class));
		rows = query.list();
		
		  for(IwmpMProject row : rows){
			  System.out.println("Size: "+row.getProjectCd()+" distCode "+distCode);
			  map.put(row.getProjectId(), row.getProjName());
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			session.getTransaction().rollback();
			//System.out.print("getUserListUnAssignedByState line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, String> getRoleAssignedForUser(String userId) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpAppRoleMap> rows = new ArrayList<IwmpAppRoleMap>();
		String hql=getRoleAssignedForUser;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString("userId",userId);
		//query.setResultTransformer(Transformers.aliasToBean(IwmpMProject.class));
		rows = query.list();
		
		  for(IwmpAppRoleMap row : rows){
			  map.put(row.getRoleId(), row.getRoleName());
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			session.getTransaction().rollback();
			//System.out.print("getUserListUnAssignedByState line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, String> getProjectByUser(Integer regId) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpUserProjectMap> rows = new ArrayList<IwmpUserProjectMap>();
		String hql=getProjectByUser;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setLong("regId",regId);
		//query.setResultTransformer(Transformers.aliasToBean(IwmpMProject.class));
		rows = query.list();
		
		  for(IwmpUserProjectMap row : rows){
			  System.out.println("Size by regId: "+row.getIwmpMProject().getProjectCd()+" regId "+regId);
			  map.put(row.getIwmpMProject().getProjectId(), row.getIwmpMProject().getProjName());
		  }
		  session.getTransaction().commit();
		}catch(Exception ex) {
			 session.getTransaction().rollback();
			//System.out.print("getUserListUnAssignedByState line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getRegId(String userId) {
		// TODO Auto-generated method stub
		Long regId=0L;
		String hql=getRegIdByUserId;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString("userId",userId);
		//query.setResultTransformer(Transformers.aliasToBean(IwmpMProject.class));
		List<UserReg> rows = query.list();
		
		/*
		 * for(UserReg row : rows){
		 * System.out.println("Size by regId: "+row.getIwmpMProject().getProjectCd()
		 * +" regId "+regId); map.put(row.getIwmpMProject().getProjectCd(),
		 * row.getIwmpMProject().getProjName()); }
		 */
		session.getTransaction().commit();
		}catch(Exception ex) {
			session.getTransaction().rollback();
			//System.out.print("getUserListUnAssignedByState line number : ");
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return null;
	}
	
	@Override
	public LinkedHashMap<Integer,String> getSpecificRoleList() {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpAppRoleMap> rows = new ArrayList<IwmpAppRoleMap>();
		String hql=getSpecificRoleList;
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			rows = query.list();
			
			  for(IwmpAppRoleMap row : rows){
				  map.put(row.getRoleId(), row.getRoleName());
			  }
			  session.getTransaction().commit();
		}catch(Exception ex) {
			System.out.print("line number : ");
		      System.out.print(Thread.currentThread().getStackTrace()[1].getLineNumber());
			ex.getStackTrace();
			session.getTransaction().rollback();
		}finally {
			//session.flush();
		//session.close();
		}
		return map; 
	}

}
