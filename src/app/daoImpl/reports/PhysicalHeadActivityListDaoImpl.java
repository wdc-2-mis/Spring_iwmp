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

import app.bean.GroundWaterTableBean;
import app.bean.reports.PhysicalHeadActivityListBean;
import app.dao.reports.PhysicalHeadActivityListDao;

@Repository("PhysicalHeadActivityListDao")
public class PhysicalHeadActivityListDaoImpl implements PhysicalHeadActivityListDao{

	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getPhysicalHeadActivityList}")
	String getPhysicalHeadActivityList;
	
	
	
	
	@Override
	public List<PhysicalHeadActivityListBean> getPhysicalHeadActivityList() {
		
		List<PhysicalHeadActivityListBean> result=new ArrayList<PhysicalHeadActivityListBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			
				hql=getPhysicalHeadActivityList;
				query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(PhysicalHeadActivityListBean.class));
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

}
