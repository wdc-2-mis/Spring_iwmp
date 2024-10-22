package app.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import app.bean.ProfileBean;
import app.bean.UserBean;
import app.dao.ProfileDao;
import app.model.IwmpActiveUserHistory;
import app.model.IwmpState;
import app.model.UserMap;
import app.model.UserReg;
@Repository("profileDao")
public class ProfileDaoImpl implements ProfileDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	UserReg userReg;
	
	HttpSession session;
	
	@Value("${getProfile}")
	String getProfile;
	
	@Value("${getMapAdminst}")
	String getMapAdminst;
	
	@Value("${getMapState}")
	String getMapState;
	
	@Value("${getMapDistrict}")
	String getMapDistrict;
	
	@Value("${getMapProject}")
	String getMapProject;
	
	@Value("${deleteInsertDolrState}") 
	String deleteInsertDolrState;
	
	@Value("${getNewUser}") 
	String getNewUser;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserReg> getUserDetail(Integer regid) {

		List<UserReg> result=new ArrayList<UserReg>();
		Session ses = sessionFactory.getCurrentSession();
		try {
				String hql =getProfile;
				ses.beginTransaction();
				result = ses.createQuery(hql).setParameter("regid", regid).list();
				//query.setResultTransformer(Transformers.aliasToBean(UserReg.class));
				
			//	System.out.println("list: "+result.size()+ "regId: "+regid);
				
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error"+e.getCause());
			System.err.print("Hibernate error"+e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
		return result;
		
	}

	@Override
	public List<ProfileBean> getMapstate(Integer regid, String usertype) 
	{
		List<ProfileBean> result=new ArrayList<ProfileBean>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
				String hql=null;
				SQLQuery query = null;
				
				
				 
				session.beginTransaction();
				 // query = session.createSQLQuery(hql);
				if(usertype.equals("ADMIN") || usertype.equals("DL")) {
					hql =getMapAdminst;
					query = session.createSQLQuery(hql);
					query.setInteger("regid", regid);
					query.setResultTransformer(Transformers.aliasToBean(ProfileBean.class));
					result = query.list();
					
				}
				if(usertype.equals("SL")) {
					hql =getMapState;
					query = session.createSQLQuery(hql);
					query.setInteger("regid", regid);
					query.setResultTransformer(Transformers.aliasToBean(ProfileBean.class));
					result = query.list();
				}
				if(usertype.equals("DI")) {
					hql =getMapDistrict;
					query = session.createSQLQuery(hql);
					query.setInteger("regid", regid);
					query.setResultTransformer(Transformers.aliasToBean(ProfileBean.class));
					result = query.list();
					
				}
				if(usertype.equals("PI")) {
					hql =getMapProject;
					query = session.createSQLQuery(hql);
					query.setInteger("regid", regid);
					query.setResultTransformer(Transformers.aliasToBean(ProfileBean.class));
					result = query.list();
					
				}
			//	System.out.println("list: "+result.size()+ "regId: "+regid);
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			
		}
		return result;
	}


	@Override
	public Integer saveProfile(UserBean userReg) 
	{
		Integer i=0;
		Session session=sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
				UserReg uReg = (UserReg) session.get(UserReg.class, Integer.parseInt(userReg.getRegID()));
			//	UserReg uReg1 = (UserReg) session.get(UserReg.class, uReg.getUserId());
				uReg.setUserName(userReg.getUserName());
				uReg.setDesignation(userReg.getUserDesignation());
				uReg.setDepartment(userReg.getUserDepartment());
				uReg.setEmail(userReg.getUserEmailId());
				uReg.setMobileNo(userReg.getUserMobileNo());
				uReg.setPhoneNo(userReg.getUserPhoneNo());
				uReg.setFaxNo(userReg.getUserFaxNo());
				uReg.setCurAddress(userReg.getUserAddres());
				uReg.setUpdatorId(uReg.getUserId());
				uReg.setUpdationDate(new Timestamp(new java.util.Date().getTime()));
				uReg.setLastUpdatedBy(uReg.getUserId());
				uReg.setLastUpdatedDate(new Timestamp(new java.util.Date().getTime()));
				session.update(uReg);
				i=1;
			}
			catch (Exception e) 
			{
				i=0;
				e.printStackTrace();
				session.getTransaction().rollback();
			}
			finally {
				session.getTransaction().commit();
			}
		return i;
	}
	
	@Override
	public UserReg getUserDetailasUserReg(Integer regId) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			userReg = (UserReg) session.get(UserReg.class, regId);
			session.getTransaction().commit();
		}catch(Exception ex) {
			session.getTransaction().rollback();
		}
		return userReg;
	}
	
	@Override
	public Integer saveProfileHistory(Integer regid,String moveBy) {
		Integer i=0;
		Session session=sessionFactory.getCurrentSession();
		//Transaction tx = session.beginTransaction();
		try {
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				session.beginTransaction();
				UserReg uReg = (UserReg) session.get(UserReg.class, regid);
				IwmpActiveUserHistory IAUHistory=new IwmpActiveUserHistory();
				
				IAUHistory.setMoveOnDate(new Timestamp(new java.util.Date().getTime()));
				IAUHistory.setMoveBy(moveBy);
				IAUHistory.setMoveIp(ipAddr);
				IAUHistory.setUserName(uReg.getUserName());
				IAUHistory.setDesignation(uReg.getDesignation());
				IAUHistory.setDepartment(uReg.getDepartment());
				IAUHistory.setMobileNo(uReg.getMobileNo());
				IAUHistory.setPhoneNo(uReg.getPhoneNo());
				IAUHistory.setFaxNo(uReg.getFaxNo());
				IAUHistory.setEmail(uReg.getEmail());
				IAUHistory.setCurAddress(uReg.getCurAddress());
				IAUHistory.setUserId(uReg.getUserId());
				IAUHistory.setUserReg(uReg);
				
				session.save(IAUHistory);
				i=1;
		}
		catch (Exception e) 
		{
			i=0;
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			
		}
	return i;
	}

	@Override
	public Integer deleteInsertDolrState(Integer regid, String[] stateList) 
	{
		Integer i=0;
		Session session=sessionFactory.getCurrentSession();
		
		try {
				String sql=deleteInsertDolrState;
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				session.beginTransaction();
				IwmpState iwmpState;
				if(stateList!=null) 
				{
					SQLQuery query = session.createSQLQuery(sql);
					query.setInteger("regId",regid);
					query.executeUpdate();
					
					for(int k=0;k<stateList.length;k++) {
						UserMap uMap=new UserMap();
						iwmpState=new IwmpState();
						userReg=new UserReg();
						iwmpState.setStCode(Integer.parseInt(stateList[k]));
						userReg.setRegId(regid);
						uMap.setUserReg(userReg);
						uMap.setIwmpState(iwmpState);
						uMap.setRequestIp(ipAddr);
						uMap.setLastUpdatedDate(new Timestamp(new java.util.Date().getTime()));
						//uMap.setLastUpdatedBy(session.getAtt);
						
						session.save(uMap);
						i=1;
					}
				
					
				}
		}	
		catch (Exception e) 
		{
			i=0;
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			
		}		
		return i;
	}

	@Override
	public List<UserReg> getNewUserDetail(Integer regid) {
		List<UserReg> result=new ArrayList<UserReg>();
		Session ses = sessionFactory.getCurrentSession();
		try {
				String hql =getNewUser;
				ses.beginTransaction();
				Query query = ses.createQuery(hql).setParameter("regid", regid);
			//	query.setResultTransformer(Transformers.aliasToBean(UserReg.class));
				result = query.list();
			//	System.out.println("list: "+result.size()+ "regId: "+regid);
				
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error"+e.getCause());
			System.err.print("Hibernate error"+e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}
	
}
