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

import app.bean.UnfreezeBaselineSurveyDataBean;
import app.bean.UnfreezeProjectLoactionBean;
import app.dao.unfreeze.UnfreezeBaselineDao;
import app.model.BlsOutMain;

@Repository("UnfreezeBaselineDao")
public class UnfreezeBaselineDaoImpl implements UnfreezeBaselineDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Value("${villbaselinelist}") 
	String villbaselist;
	
	@Value("${unfreezebaselinevillage}") 
	String unfreezebaselinevillage;
	
	@Value("${unfreezebaselineprojvill}") 
	String unfreezebaselineprojvill;
	
	@Value("${unfreezebalselinecomplete}") 
	String unfreezebalselinecomplete;
	
	@Override
	public List<BlsOutMain> getBaselineVillageList(int project) {
		
		List<BlsOutMain> result=new ArrayList<BlsOutMain>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=villbaselist;
			result = ses.createQuery(hql).setParameter("proj", project).list();
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


	@Override
	public List<UnfreezeBaselineSurveyDataBean> getUnfreezeBaselineSurveyData(Integer vill, Integer project) {
		// TODO Auto-generated method stub
		List<UnfreezeBaselineSurveyDataBean> result=new ArrayList<UnfreezeBaselineSurveyDataBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				String hql1=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=unfreezebaselinevillage;
				hql1=unfreezebaselineprojvill;
				if(vill > 0 ) {
			    query = session.createSQLQuery(hql);
				query.setInteger("vill", vill);
				}
				if(vill.equals(0) ) {
					 query = session.createSQLQuery(hql1);
					 query.setInteger("proj", project);
				}
				query.setResultTransformer(Transformers.aliasToBean(UnfreezeBaselineSurveyDataBean.class));
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
		}
		return result;
	}


	@Override
	public boolean unfreezeBaselineSurveyDataComplete(String[] villcode) {
		
		Boolean res=false;
		Integer value=0;
		String reskd="";
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String deletesql=unfreezebalselinecomplete ;
		try {
				for(int i=0; i<villcode.length; i++)
				{
					SQLQuery query = sessionf.createSQLQuery(deletesql);
				    query.setInteger("vill", Integer.parseInt(villcode[i]));
				    reskd = query.list().get(0).toString();
				}
				if( reskd.equals("success")) {
					res=true;
					//sessionf.getTransaction().commit();
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
			sessionf.getTransaction().commit();
		}
		return res;
	}

}
