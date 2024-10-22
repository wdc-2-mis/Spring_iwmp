package app.daoImpl.master;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.master.ActivityAssetParameterDao;
import app.model.master.IwmpMPhyHeads;
import app.model.master.IwmpMUnit;

@Repository("ActivityAssetParameterDao")
public class ActivityAssetParameterDaoImpl implements ActivityAssetParameterDao{

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getHead}")
	String getHead;
	
	@Value("${getUnitMaster}")
	String getUnitMaster;
	
	@Value("${saveAssetParameter}")
	String saveAssetParameter;
	
	@Override
	public LinkedHashMap<Integer, String> getHead() {
		
		List<IwmpMPhyHeads> list = new ArrayList<IwmpMPhyHeads>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql=getHead;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			for(IwmpMPhyHeads head:list) {
				map.put(head.getHeadCode(), head.getHeadDesc());
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getUnitMasters() {
		
		List<IwmpMUnit> list = new ArrayList<IwmpMUnit>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql=getUnitMaster;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			for(IwmpMUnit head:list) 
			{
				map.put(head.getUnitCode(), head.getUnitDesc());
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public String saveAssetParameter(Integer ahead, Integer aActivity, String parameterDesc, Integer aUnit, String loginid) 
	{
		String res="";int a=0;
		String draftQuery=saveAssetParameter;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			
			SQLQuery sqlQuery =session.createSQLQuery(draftQuery);
			session.createSQLQuery(draftQuery);
			sqlQuery.setParameter("aActivity", aActivity);
			sqlQuery.setString("parameterDesc", parameterDesc);
			sqlQuery.setParameter("aUnit", aUnit);
			a=sqlQuery.executeUpdate();   //list().get(0).toString();
			
			if(a>0){ 
				
				res="success";
				session.getTransaction().commit();
			}
			else {
				session.getTransaction().rollback();
			}
			
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res="fail";
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
			res="fail";
		}
		return res;
	}

}
