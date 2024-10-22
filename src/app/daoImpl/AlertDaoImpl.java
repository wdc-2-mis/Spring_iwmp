package app.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.AlertDao;
import app.model.master.IwmpUserUploadDetails;

@Repository("alertDao")
public class AlertDaoImpl implements AlertDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getAlert}")
	String query;
	
	@Value("${getlangAlert}")
	String newquery;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpUserUploadDetails> getAlert() {
		List<IwmpUserUploadDetails> result=new ArrayList<IwmpUserUploadDetails>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql =query;
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
					query.setResultTransformer(Transformers.aliasToBean(IwmpUserUploadDetails.class));
					 result = query.list();
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

	public List<IwmpUserUploadDetails> getnewAlert(String lang) {
		List<IwmpUserUploadDetails> result=new ArrayList<IwmpUserUploadDetails>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql =newquery;
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("lang", lang);
					query.setResultTransformer(Transformers.aliasToBean(IwmpUserUploadDetails.class));
					 result = query.list();
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

}
