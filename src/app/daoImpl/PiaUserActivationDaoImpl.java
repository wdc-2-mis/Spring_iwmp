package app.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.PiaUserActivationDao;
import app.model.UserMap;
import app.model.UserReg;

@Repository("piaUserActivationDao")
public class PiaUserActivationDaoImpl implements PiaUserActivationDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getUserList}")
	String getUserList;
	
	@Override
	public LinkedHashMap<Integer, List<UserReg>> getUserList(UserReg userReg,String stateCode) {
			// TODO Auto-generated method stub
			LinkedHashMap<Integer, List<UserReg>> map = new LinkedHashMap<Integer, List<UserReg>>();
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				String hql = getUserList + " userReg,UserMap userMap where ";
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
					searchQuery = searchQuery + " UPPER(userReg.userType) like UPPER(:userType)";
				}
				if (userReg.getStatus() != null && !userReg.getStatus().equals("")) {
					if (searchQuery.length() > 0) {
						searchQuery += " AND ";
					}
					searchQuery = searchQuery + " UPPER(status) like UPPER(:status)";
				}
				hql = hql + searchQuery + " and userReg.userType in('PI','DI') and userReg.regId=userMap.userReg.regId and userMap.iwmpState.stCode=:stCode";
				// modify hql = hql + searchQuery + " and userReg.userType ='PI' and userReg.regId=userMap.userReg.regId and userMap.iwmpState.stCode=:stCode";
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
				//System.out.println("stateCode: "+stateCode);
				query.setInteger("stCode", Integer.parseInt(stateCode));
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
				session.getTransaction().commit();
			} catch (HibernateException e) {
				System.err.print("Hibernate error" + e.getMessage());
				session.getTransaction().rollback();
				// e.printStackTrace();
			} catch (Exception ex) {
				System.err.print("Error" + ex.getMessage() + ex.getCause());
				session.getTransaction().rollback();
				// ex.printStackTrace();
			}finally {
				
			}

			return map;
		}

}
