package app.dao.unfreezeImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.UnfreezeProjectLoactionBean;
import app.dao.unfreeze.UnfreezeLocationDao;

@Repository("UnfreezeLocationDao")
public class UnfreezeLocationDaoImpl implements UnfreezeLocationDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${unfreezebaselineproject}") 
	String unfreezebaselineproject; 
	
	@Value("${unfreezebaselinedist}") 
	String unfreezebaselinedist;
	
	@Value("${deleteWorkIdTemp}") 
	String deleteWorkIdTemp;
	
	@Value("${unfreezebalseline}") 
	String unfreezebalseline;
	
	@Value("${unfreezeProjectLocatin}") 
	String unfreezeProjectLocatin;
	
	@Value("${unfreezeProjectLocatinwc}") 
	String unfreezeProjectLocatinwc;
	
	@Override
	public List<UnfreezeProjectLoactionBean> getUnfreezeProjectLocation(Integer district, Integer project) {
		
		List<UnfreezeProjectLoactionBean> result=new ArrayList<UnfreezeProjectLoactionBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				String hql1=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=unfreezebaselineproject;
				hql1=unfreezebaselinedist;
				if(project > 0 ) {
			    query = session.createSQLQuery(hql);
				query.setInteger("projcd", project);
				}
				if(project.equals(0) ) {
					 query = session.createSQLQuery(hql1);
					 query.setInteger("distcd", district);
				}
				query.setResultTransformer(Transformers.aliasToBean(UnfreezeProjectLoactionBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		return result;
	}


	@Override
	public boolean deletePhysicalWorkIdTemp(Integer projid) {
		
		Boolean res=false;
		Integer value=0;
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String deletesql=deleteWorkIdTemp;
		try {
				SQLQuery query = sessionf.createSQLQuery(deletesql);
			    query.setInteger("id", projid);
				value=query.executeUpdate();
				if(value>0) 
				{
					res=true;
				}
				else {
						sessionf.getTransaction().rollback();
						return false;
				}
				if(res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}


	@Override
	public boolean unfreezeBaselineSurveyPlotWise(Integer projid) {
		
		Boolean res=false;
		Integer value=0;
		String reskd="";
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String deletesql=unfreezebalseline ;
		try {
				SQLQuery query = sessionf.createSQLQuery(deletesql);
			    query.setInteger("projid", projid);
			    reskd = query.list().get(0).toString();
			    
				if( reskd.equals("success")) {
					res=true;
					sessionf.getTransaction().commit();
				}
				else {
					sessionf.getTransaction().rollback();
				}
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
			return false;
		}
		finally {
		
		}
		return res;
	}


	@Override
	public boolean unfreezeProjectLocatin(Integer projid) {
		
		Boolean res=false;
		Integer value=0;
		String reskd="";
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String deletesql=unfreezeProjectLocatinwc ;
		String unfreezesql=unfreezeProjectLocatin ;
		try {
				SQLQuery query = sessionf.createSQLQuery(deletesql);
			    query.setInteger("pid", projid);
			    query.executeUpdate();
			    
			    SQLQuery querykd = sessionf.createSQLQuery(unfreezesql);
			    querykd.setInteger("pid", projid);
			    value=querykd.executeUpdate();
				if(value>0) 
				{
					res=true;
				}
				else {
						sessionf.getTransaction().rollback();
						return false;
				}
				if(res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
			return false;
		}
		finally {
		
		}
		return res;
	}

}
