package app.daoImpl.reports;

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

import app.bean.WcdcPiaUserBean;
import app.dao.reports.WcdcPiaUserDao;

@Repository("WcdcPiaUserDao")
public class WcdcPiaUserDaoImpl implements WcdcPiaUserDao{
	
	@Autowired
	private SessionFactory sessionFactory;


	  @Value("${wcdcPiaUser1}") 
	  String wcdcPiaUser1;
	  
	  @Value("${wcdcPiaUser2}") 
	  String wcdcPiaUser2;

	@Override
	public List<WcdcPiaUserBean> getWcdcPiaUserList(String state, String district, String userType) {
		List<WcdcPiaUserBean> result=new ArrayList<WcdcPiaUserBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  if(userType.equals("WCDC"))
			  {
					hql =wcdcPiaUser1;
					query = session.createSQLQuery(hql);
					query.setInteger("state", Integer.parseInt(state));
			 }
			  else {
				  
				  hql=wcdcPiaUser2;
				  query = session.createSQLQuery(hql);
				  query.setInteger("state", Integer.parseInt(state));
				  query.setInteger("district", Integer.parseInt(district));
			  }
				query.setResultTransformer(Transformers.aliasToBean(WcdcPiaUserBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.getMessage();
			ex.getCause();
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			//session.flush(); session.close();
			 
		}
		return result;
	}

}
