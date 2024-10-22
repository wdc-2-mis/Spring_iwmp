package app.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
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

import app.bean.GalleryBean;
import app.dao.GalleryDao;

@Repository("galleryDao")
public class GalleryDaoImpl implements GalleryDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${gallery_data}")
	String query;
	
	@Value("${gallery_data_index}")
	String indexQuery;
	
	@Value("${gallery_all_files}")
	String galleryAllFiles;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GalleryBean> getFiles(int stCode) {
		List<GalleryBean> result=new ArrayList<GalleryBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = query;
			session.beginTransaction();
			  SQLQuery query = session.createSQLQuery(hql);
			  query.setParameter("stCode", stCode);
			  query.setResultTransformer(Transformers.aliasToBean(GalleryBean.class));
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
		}finally {
			
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GalleryBean> getIndexFiles(int size) {
		List<GalleryBean> result=new ArrayList<GalleryBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = indexQuery+" limit "+size;
			session.beginTransaction();
			  SQLQuery query = session.createSQLQuery(hql);
			  query.setResultTransformer(Transformers.aliasToBean(GalleryBean.class));
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
		}finally {
			
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GalleryBean> getAllFiles() {
		List<GalleryBean> result=new ArrayList<GalleryBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = galleryAllFiles;
			session.beginTransaction();
			  SQLQuery query = session.createSQLQuery(hql);
			  query.setResultTransformer(Transformers.aliasToBean(GalleryBean.class));
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
		}finally {
			
		}
		
		return result;
	}
}
