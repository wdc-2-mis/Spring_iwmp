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
import app.dao.StoriesDao;
import app.model.master.IwmpUserUploadDetails;

@Repository("storiesDao")
public class StoriesDaoImpl implements StoriesDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getStories}")
	String query;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpUserUploadDetails> getStories() {
		List<IwmpUserUploadDetails> result=new ArrayList<IwmpUserUploadDetails>();
		Session session =sessionFactory.getCurrentSession();
		try {
			String hql = query;
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
					query.setResultTransformer(Transformers.aliasToBean(IwmpUserUploadDetails.class)).setMaxResults(5);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpUserUploadDetails> getAllStories(int pageid,int total) {
		List<IwmpUserUploadDetails> result=new ArrayList<IwmpUserUploadDetails>();
		Session session =sessionFactory.getCurrentSession();
		try {
			String hql = query;
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult((pageid - 1) * total);
			query.setMaxResults(total);
			query.setResultTransformer(Transformers.aliasToBean(IwmpUserUploadDetails.class));
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch(Exception ex){
			session.getTransaction().rollback();
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}
		
		return result;
	}

}
