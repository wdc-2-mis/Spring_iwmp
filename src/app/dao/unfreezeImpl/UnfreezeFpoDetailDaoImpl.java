package app.dao.unfreezeImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.FPOReportBean;
import app.dao.unfreeze.UnfreezeFpoDetailDao;
import app.model.outcome.EpaDetail;
import app.model.outcome.FpoMain;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.ProductionDetail;
import app.model.outcome.ShgDetail;
import app.model.outcome.ShgMain;

@Repository("UnfreezeFpoDetailDao")
public class UnfreezeFpoDetailDaoImpl implements UnfreezeFpoDetailDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${unfreezeFpoDetails}")
	String unfreezeFpoDetails;
	
	
	@Override
	public List<FPOReportBean> unfreezeFpoDetail(Integer projectId, String group) {
		String getReport=unfreezeFpoDetails;
		Session session = sessionFactory.getCurrentSession();
		List<FPOReportBean> list = new ArrayList<FPOReportBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setInteger("projectId",projectId);
		        query.setString("group", group);
			query.setResultTransformer(Transformers.aliasToBean(FPOReportBean.class));
			list = query.list();
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
		return list;
	}


	@Override
	public boolean unfreezeFpoDetailsData(String[] fpo_id, String group) {
		Boolean res=false;
		Session session = sessionFactory.getCurrentSession();
		
		try 
		{
			session.beginTransaction();
			
			for(String code : fpo_id)
			{
				Integer id = Integer.parseInt(code);
				
				
					FpoMain fm = session.load(FpoMain.class, id);
					fm.setStatus("D");
					session.update(fm);
				
			}
			session.getTransaction().commit();
			res=true;
		}
		catch(Exception ex) 
		{ 
			ex.printStackTrace();
			session.getTransaction().rollback();
		return false;
		}
		
		return res;
	}
	

}
