package app.daoImpl.reports;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import app.bean.PfmsComponentBean;
import app.bean.PfmsTransactionBean;
import app.model.master.PfmscompBean;
import app.service.reports.PfmscompDao;

@Repository("PfmscompDao")
public class PfmscompDaoImpl implements PfmscompDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	 @Value("${componentList}") 
	  String componentList;
	 
	 @Value("${getPfmsComponentReport}") 
	  String getPfmsComponentReport;
	 
	 @Value("${getDistrictPfmsComponentReport}")
	 String getDistrictPfmsComponentReport;
	
	@Override
	public LinkedHashMap<String, String> getAllComponent() {
		
		List<PfmscompBean> list = new ArrayList<PfmscompBean>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String hql=componentList;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			for(PfmscompBean comp:list) {
				map.put(comp.getComponent_code(),comp.getComponent_name());
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
	public List<PfmsComponentBean> getPfmsComponentReport(String stcode, String[] comp, String finyr) {
		
		List<PfmsComponentBean> result=new ArrayList<PfmsComponentBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
				String cmpcd=null;
				for(int i=0;i<comp.length; i++) {
					
					if(cmpcd!=null)
					cmpcd=cmpcd+","+comp[i]; 
					else
						cmpcd=comp[i];
			

				}
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				String fy="20"+(Integer.parseInt(finyr));
				hql=getPfmsComponentReport;
				query = session.createSQLQuery(hql);
				query.setInteger("stcode", Integer.parseInt(stcode));
				query.setInteger("fromYear", Integer.parseInt(fy));
				query.setParameterList("cmpcode", cmpcd.split(","));
				query.setResultTransformer(Transformers.aliasToBean(PfmsComponentBean.class));
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


	@Override
	public List<PfmsComponentBean> getdistPfmsComponentReport(String stcode, String comp, String finyr) {
		List<PfmsComponentBean> result=new ArrayList<PfmsComponentBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
	
			@SuppressWarnings("unused")
			Transaction tx = session.beginTransaction(); 
			String fy="20"+(Integer.parseInt(finyr));
			hql=getDistrictPfmsComponentReport;
			query = session.createSQLQuery(hql);
			query.setInteger("stcode", Integer.parseInt(stcode));
			query.setInteger("fromYear", Integer.parseInt(finyr));
			query.setParameterList("cmpcode", comp.split(","));
			query.setResultTransformer(Transformers.aliasToBean(PfmsComponentBean.class));
			result = query.list();
	} 
	catch (HibernateException e) 
	{
		System.err.print("Hibernate error");
		e.printStackTrace();
	} 
	catch(Exception ex)
	{
//		//System.err.print("Error"+ex.getMessage()+ex.getCause());
		ex.printStackTrace();
	}finally {
		session.getTransaction().commit();
//		
	}
	return result;
}


	}
	
	
