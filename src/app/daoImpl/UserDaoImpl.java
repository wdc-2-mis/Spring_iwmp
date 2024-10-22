package app.daoImpl;


import app.bean.Login;

import app.bean.User;

import app.dao.UserDao;
import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpState;

import app.model.UserMap;
import app.model.UserReg;

import java.util.ArrayList;
import java.util.List;
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

@Repository("userDao")
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;


	  @Value("${stateList}") 
	  String statelist;
	  
	  @Value("${projmasterlist}") 
	  String projmasterlist;
	  
	  @Value("${districtListByStateCode}") 
	  String distlist;
	  
	  @Value("${maxregid}") 
	  String maxregid;
	  
	  @Value("${addUserProject}") 
	  String addUserProject;
	  
	  @Value("${userIdforPIA}") 
	  String userIdforPIA;
	 
	  
	
	
	@SuppressWarnings("unchecked")
	public List<User> validateUser(Login login) {
		
		List<User> result=new ArrayList<User>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			String hql = "from UserReg where userId=:userId";
			result = session.createQuery(hql).setParameter("userId", login.getUser_id()).list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch(Exception ex){
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return result;
	   
	    }
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpState> getStateList(){
		List<IwmpState> result=new ArrayList<IwmpState>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
		String hql=statelist;
		result =ses.createQuery(hql).list();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex){
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpDistrict> getDistrictList(int stateCode){
		List<IwmpDistrict> result=new ArrayList<IwmpDistrict>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=distlist;
			result = ses.createQuery(hql).setParameter("stCode", stateCode).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveUserReg(UserReg userReg) {
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			//Session session=sessionFactory.getCurrentSession();
			ses.save(userReg);
		}
		catch (Exception e) 
		{
			ses.getTransaction().rollback();
			e.printStackTrace();
		}
		finally 
		{
			ses.getTransaction().commit();
		}  
	}
	
	public  Integer regId() 
	{
		Integer result=0;
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			String hql=maxregid;
			List list = ses.createQuery(hql).list();
		//	System.out.println(list.get(0));
			result=Integer.parseInt(list.get(0).toString());
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMProject> getUserProjectList(String stateCode, String dist) {
		List<IwmpMProject> result=new ArrayList<IwmpMProject>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			String hql=addUserProject;
			result = ses.createQuery(hql).setParameter("dist", Integer.valueOf(dist)).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserReg> getUseridList(String stateCode, String dist) {
		List<UserReg> result=new ArrayList<UserReg>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			String hql=userIdforPIA;
			result = ses.createQuery(hql).setParameter("distt", Integer.valueOf(dist)).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMProject> getProjectList(int stateCode, int distCode) {
		List<IwmpMProject> result=new ArrayList<IwmpMProject>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=projmasterlist;
			result = ses.createQuery(hql).setParameter("distCode", Integer.valueOf(distCode)).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}
	
	
}
