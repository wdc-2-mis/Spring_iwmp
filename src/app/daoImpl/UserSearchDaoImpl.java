package app.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.MenuMap;
import app.bean.User;
import app.bean.UserBean;
import app.dao.UserSearchDao;
import app.model.UserMap;
import app.model.UserReg;

@Repository("userSearchDao")
public class UserSearchDaoImpl implements UserSearchDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getUserList}")
	String getUserList;
	
	@Value("${inActivateUser}")
	String inActivateUser;
	
	@Value("${getNextUserNo}")
	String getNextUserNo;
	
	@Value("${activateUser}")
	String activateUser;
	
	@Value("${deleteUser}")
	String deleteUser;
	
	@Value("${deleteUserMap}")
	String deleteUserMap;
	
	@Value("${getUserByRegIdForUserBean}")
	String getUserByRegIdForUserBean;

	@Override
	public LinkedHashMap<Integer, List<UserReg>> getUserList(UserReg userReg) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<UserReg>> map = new LinkedHashMap<Integer, List<UserReg>>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			String hql = getUserList + " userReg where ";
			Query query = null;
			String searchQuery = "";
			if (userReg.getUserId() != null && !userReg.getUserId().equals("")) {
				if (searchQuery.length() > 0) {
					searchQuery += " AND ";
				}
				searchQuery = searchQuery + " UPPER(userId) like UPPER(:userId)";
			}
			if (userReg.getUserName() != null && !userReg.getUserName().equals("")) {
				if (searchQuery.length() > 0) {
					searchQuery += " AND ";
				}
				searchQuery = searchQuery + " UPPER(userName) like UPPER(:userName)";
			}
			if (userReg.getDesignation() != null && !userReg.getDesignation().equals("")) {
				if (searchQuery.length() > 0) {
					searchQuery += " AND ";
				}
				searchQuery = searchQuery + " UPPER(designation) like UPPER(:designation)";
			}
			if (userReg.getDepartment() != null && !userReg.getDepartment().equals("")) {
				if (searchQuery.length() > 0) {
					searchQuery += " AND ";
				}
				searchQuery = searchQuery + " UPPER(department) like UPPER(:department)";
			}
			if (userReg.getEmail() != null && !userReg.getEmail().equals("")) {
				if (searchQuery.length() > 0) {
					searchQuery += " AND ";
				}
				searchQuery = searchQuery + " UPPER(email) like UPPER(:email)";
			}
			if (userReg.getUserType() != null && !userReg.getUserType().equals("")) {
				if (searchQuery.length() > 0) {
					searchQuery += " AND ";
				}
				searchQuery = searchQuery + " UPPER(userType) like UPPER(:userType)";
			}
			if (userReg.getStatus() != null && !userReg.getStatus().equals("")) {
				if (searchQuery.length() > 0) {
					searchQuery += " AND ";
				}
				searchQuery = searchQuery + " UPPER(status) like UPPER(:status)";
			}
			hql = hql + searchQuery;
			query = session.createQuery(hql);
			if (userReg.getUserId() != null && !userReg.getUserId().equals("")) {
				query.setString("userId", userReg.getUserId() + "%");
			}
			if (userReg.getUserName() != null && !userReg.getUserName().equals("")) {
				query.setString("userName", userReg.getUserName() + "%");
			}
			if (userReg.getDesignation() != null && !userReg.getDesignation().equals("")) {
				query.setString("designation", userReg.getDesignation() + "%");
			}
			if (userReg.getDepartment() != null && !userReg.getDepartment().equals("")) {
				query.setString("department", userReg.getDepartment() + "%");
			}
			if (userReg.getEmail() != null && !userReg.getEmail().equals("")) {
				query.setString("email", userReg.getEmail() + "%");
			}
			if (userReg.getUserType() != null && !userReg.getUserType().equals("")) {
				query.setString("userType", userReg.getUserType() + "%");
			}
			if (userReg.getStatus() != null && !userReg.getStatus().equals("")) {
				query.setString("status", userReg.getStatus() + "%");
			}
			List<UserReg> list = query.list();
			
			List<UserReg> sublist = new ArrayList<UserReg>();
			if ((list != null) && (list.size() > 0)) {
				for (UserReg row : list) {
					if (!map.containsKey(row.getRegId())) {
						sublist = new ArrayList<UserReg>();
						sublist.add(row);
						map.put(row.getRegId(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getRegId(), sublist);
					}
					
					
				}
			}
			for (Map.Entry<Integer,List<UserReg>> entry : map.entrySet())  {
	            System.out.println("Key = " + entry.getKey() + 
	                             ", Value = " + entry.getValue().get(0).getIwmpUserMaps().size()); 
	            for (UserMap temp : entry.getValue().get(0).getIwmpUserMaps()) {
	                System.out.println("Set Value: "+temp.getMapId());
	             }
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			System.err.print("Hibernate error" + e.getMessage());
			// e.printStackTrace();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			// ex.printStackTrace();
		}finally {
			//session.flush();
			//session.close();
		}

		return map;
	}

	@Override
	public Integer getNextUserNo() {
		Session session = sessionFactory.getCurrentSession();
		
		String sql=getNextUserNo;
		Integer count=0;
		try {
			session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		 count = Integer.parseInt(query.uniqueResult().toString());
		 session.getTransaction().commit();
		}catch(Exception ex) {
			session.getTransaction().rollback();
		}finally {
			//session.clear();
			//session.close();
		}
		return count;
	}

	@Override
	public boolean activateUser(UserReg userReg) {
		Session session = sessionFactory.getCurrentSession();
		String sql=activateUser;
		
		boolean result=false;
		
		try {
				 session.beginTransaction(); 
				 SQLQuery query = session.createSQLQuery(sql);
				 query.setString("userId",userReg.getUserId());
				 query.setString("encryptedPassword",userReg.getEncryptedPass());
				 query.setString("status",userReg.getStatus());
				 query.setString("authorizerId",userReg.getAuthorizerId());
				 query.setString("requestIp",userReg.getRequestIp());
				 query.setLong("regId",userReg.getRegId());
				 Integer res = query.executeUpdate();
				 
				 if(res>0 ) {
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
				}finally {
					//session.clear();
					//session.close();
				}
		return result;
	}
	
	@Override
	public boolean inActivateUser(UserReg userReg) {
		Session session = sessionFactory.getCurrentSession();
		String sql=inActivateUser;
		boolean result=false;
		
		try {
			session.beginTransaction();
			 SQLQuery query = session.createSQLQuery(sql);
			 query.setString("status",userReg.getStatus());
			 query.setString("updatedBy",userReg.getLastUpdatedBy());
			 query.setString("requestIp",userReg.getRequestIp());
			 query.setLong("regId",userReg.getRegId());
			 Integer res = query.executeUpdate();
			 if(res>0) {
				 session.getTransaction().commit();
					result=true; 
			 }else {
				 session.getTransaction().rollback();
					result=false;  
			 }
		}catch(Exception ex) {
			session.getTransaction().rollback();
			result = false;
		}finally {
		//	session.clear();
			//session.close();
		}
		return result;
	}
	
	@Override
	public boolean deleteUser(UserReg userReg) {
		Session session = sessionFactory.getCurrentSession();
		boolean result=true;
		
		String sql = deleteUser;
		String sql1 = deleteUserMap;
		try {
			session.beginTransaction();
			SQLQuery querym = session.createSQLQuery(sql1);
			querym.setInteger("regId",userReg.getRegId());
			Integer resm = querym.executeUpdate();
			
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("regId",userReg.getRegId());
			Integer res = query.executeUpdate();
			 if(res>0 && resm>0) {
				 session.getTransaction().commit();
					result=true; 
			 }else {
				 session.getTransaction().rollback();
					result=false;  
			 }
		}
		catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
			result = false;
		}
		finally {
			//session.clear();
			//session.close();
		}
		return result;
	}

	@Override
	public User getUserByRegIdForUserBean(Integer regId) {
		User user = new User();
		Session session = sessionFactory.getCurrentSession();
		String sql=getUserByRegIdForUserBean;
		try {
			session.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.setInteger("regId", regId);
		query.setResultTransformer(Transformers.aliasToBean(User.class));
		List<User> userList =  query.list();
		if(userList.size()>0) {
			for(User list : userList) {
				user=list;
			}
		}
		session.getTransaction().commit();
	}catch(Exception ex) {
		session.getTransaction().rollback();
		ex.getStackTrace();
	}finally {
//session.flush();
//session.close();
	}
		return user;
	}
}
