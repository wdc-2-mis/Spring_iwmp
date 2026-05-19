package app.project.serviceORdao;

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

import app.bean.PhysicalAchievementBean;

@Repository("PhysicalAchievementWithoutPlanDao")
public class PhysicalAchievementWithoutPlanDaoImpl implements PhysicalAchievementWithoutPlanDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Value("${getFinancialYearForAchievementkdy}")
	String getFinancialYearForAchievementkdy;
	
	@Value("${getCurrentFinYrMonthIdkdy}")
	String getCurrentFinYrMonthIdkdy;
	
	@Value("${getActivityWithTargetkdy}")
	String getActivityWithTargetkdy;
	
	
	@Override
	public String getFinancialYearForAchievement(Integer pCode) {
		
		List<Integer> result = new ArrayList<Integer>();
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getFinancialYearForAchievementkdy;
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			//query.setInteger("pCode", pCode);
			result =query.list();
			for(Integer i : result) {
				if(res==null)
				res=Integer.toString(i);
				else
				res+=","+Integer.toString(i);
			}
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public List<String> getCurrentFinYrMonthIdkdy(Integer pCode, String finYr) {
		
		List<String> result =new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getCurrentFinYrMonthIdkdy;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
			query.setParameter("finYr", finYr);
			//query.setResultTransformer(Transformers.aliasToBean(PhysicalAchievementBean.class));
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}




	@Override
	public List<PhysicalAchievementBean> getActivityWithTargetkdy(Integer pCode, Integer finYr, Integer monthId) {
		
		List<PhysicalAchievementBean> result = new ArrayList<PhysicalAchievementBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getActivityWithTargetkdy;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
			query.setInteger("finYr", finYr);
			query.setInteger("monthId", monthId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalAchievementBean.class));
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}

}
