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

import app.dao.IndexCircleDataDao;

@Repository("indexCircleDataDao")
public class IndexCircleDataDaoImpl implements  IndexCircleDataDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${circle_figure}")
	String circle_figure;

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCircleData() {
		
		List<String> result=null;
		String hql = circle_figure;
		Session session = sessionFactory.getCurrentSession();
		try {
			  
			  session.beginTransaction();
			  SQLQuery query = session.createSQLQuery(hql);
			  List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  String[] strArray = Arrays.stream(row).toArray(String[]::new);
				  result = Arrays.asList(strArray);
			  }
			  session.getTransaction().commit();
			 
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally{
			
		}
		return result;
	}
	
	
}
