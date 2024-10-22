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


import app.dao.StateMasterDao;
import app.model.IwmpDistrict;
import app.model.IwmpState;

@Repository("stateMasterDao")
public class StateMasterDaoImpl implements StateMasterDao{

	@Value("${stateList}")
	String allStateQuery;
	
	@Value("${stateListByStateCode}")
	String stateListByStateCodeQuery;
	
	@Value("${getUserState}")
	String getUserState;
	
	@Value("${districtListByDistCode}")
	String getStateByDistCode;
	
	@Autowired
	private SessionFactory sessionFactory;
	

	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, String> getAllState() {
		List<IwmpState> stateList=new ArrayList<IwmpState>();
		String hql=allStateQuery;
		LinkedHashMap<Integer, String> stateMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				stateList = query.list();
				for (IwmpState state : stateList) 
				{
					stateMap.put(state.getStCode(), state.getStName());
				}
				session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
        return stateMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, String> getStateByStateCode(Integer stateCode) {
		List<IwmpState> stateList=new ArrayList<IwmpState>();
		String hql=stateListByStateCodeQuery;
		LinkedHashMap<Integer, String> stateMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("stateCode", stateCode);
			stateList = query.list();
			for (IwmpState state : stateList) {
				stateMap.put(state.getStCode(), state.getStName());
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
        return stateMap;
	}

	@Override
	public LinkedHashMap<Integer, String> getUserState(String loginid) {
		// TODO Auto-generated method stub
		List<IwmpState> stateList=new ArrayList<IwmpState>();
		String hql=getUserState;
		LinkedHashMap<Integer, String> stateMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("login", loginid);
			stateList = query.list();
			for (IwmpState state : stateList) {
				stateMap.put(state.getStCode(), state.getStName());
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
        return stateMap;
	}

	
	public List<IwmpState> getStateList() {
		// TODO Auto-generated method stub
		List<IwmpState> stateList=new ArrayList<IwmpState>();
		String hql=allStateQuery;
		
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				stateList =(List<IwmpState>) query.list();
				
				session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
        return stateList;
	}

	@Override
	public LinkedHashMap<Integer, String> getStateByDistCode(Integer distCode) {
		// TODO Auto-generated method stub
		List<IwmpDistrict> stateList=new ArrayList<IwmpDistrict>();
		String hql=getStateByDistCode;
		LinkedHashMap<Integer, String> stateMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("distCode", distCode);
			stateList = query.list();
			for (IwmpDistrict state : stateList) {
				stateMap.put(state.getIwmpState().getStCode(), state.getIwmpState().getStName());
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
        return stateMap;
	}

}
