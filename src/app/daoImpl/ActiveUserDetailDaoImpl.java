package app.daoImpl;

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

import app.bean.LastActionLogBean;
import app.bean.ProfileBean;
import app.dao.ActiveUserDetailDao;
import app.model.IwmpUserActionLog;
import app.model.IwmpUserProjectMap;
import app.model.UserMap;
import app.model.UserReg;

@Repository("activeUserDetailDao")
public class ActiveUserDetailDaoImpl implements ActiveUserDetailDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getActiveUserDetailall}")
	String getActiveUserDetailall;

	@Value("${getActiveUserDetailPi}")
	String getActiveUserDetailPi;
	
	@Value("${lastActivityByUser}")
	String lastActivityByUser;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMap> getUserMap(long state, String status) 
	{
		List<UserMap> result = new ArrayList<UserMap>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getActiveUserDetailall;
			ses.beginTransaction();
			result = ses.createQuery(hql).setParameter("state", Integer.parseInt(String.valueOf(state))).setParameter("status", status).list();
			//result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpUserProjectMap> getUserMap(long state, String utype, String status) 
	{
		List<IwmpUserProjectMap> result = new ArrayList<IwmpUserProjectMap>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getActiveUserDetailPi;
			ses.beginTransaction();
			//query = ses.createQuery(hql).setParameter(0, state);
			result = ses.createQuery(hql).setParameter("stCode", Integer.parseInt(String.valueOf(state))).setParameter("status", status).list();
			//result =query.list();
			for(IwmpUserProjectMap map : result) {
				map.getIwmpMProject().getIwmpDistrict().getDistName();
				
			}
			
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpUserActionLog> getUserLogMap(long state, String sl, String di) 
	{
		List<IwmpUserActionLog> result = new ArrayList<IwmpUserActionLog>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = lastActivityByUser;
			ses.beginTransaction();
			query = ses.createQuery(hql).setParameter(0, sl).setParameter(1, di).setParameter(2, state);
			result =query.list();
			
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}

	@Override
	public List<LastActionLogBean> getUserDetailLog(long state, String sl, String di) {
		
		List<LastActionLogBean> result=new ArrayList<LastActionLogBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			 // query = session.createSQLQuery(hql);
				hql =lastActivityByUser;
				query = session.createSQLQuery(hql);
				//query.setString(0, sl);
				//query.setString(1, di);
				query.setLong(0, state);
				query.setResultTransformer(Transformers.aliasToBean(LastActionLogBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
		}
		return result;
	}
	

}
