package app.plan.update;

import java.math.BigDecimal;
import java.net.InetAddress;
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

import app.bean.PhysicalActionPlanBean;

@Repository("PhysicalActionPlanDecreaseDao")
public class PhysicalActionPlanDecreaseDaoImpl implements PhysicalActionPlanDecreaseDao{
	
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getListofPhysicalActionPlanWithAchiev}")
	String getListofPhysicalActionPlanWithAchiev;
	
	@Value("${updatePhysicalAnnualActionPlan}")
	String updatePhysicalAnnualActionPlan;
	
	@Value("${getplanidData}")
	String getplanidData;

	@Override
	public List<PhysicalActionPlanBean> getListofPhysicalActionPlanWithAchiev(Integer projectcd, Integer yearcd) {
		
		List<PhysicalActionPlanBean> list = new ArrayList<PhysicalActionPlanBean>();
		String hql=getListofPhysicalActionPlanWithAchiev;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(hql);
			query.setInteger("projectcd", projectcd);
			query.setInteger("yearcd", yearcd);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
			list = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
		
		
	}


	@Override
	public String updatePlanAcordingAchievement(String plan, String activity, Integer projectcd, Integer yearcd,
			String loginid) {
		
		int ch=0, result=0;
		
		String res="";
		String update=updatePhysicalAnnualActionPlan;
		String getplaniddata = getplanidData;
		Session session = sessionFactory.getCurrentSession();
		BigDecimal plnact;
		try {
				session.beginTransaction();
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress(); 
				BigDecimal pln= new BigDecimal(plan);
				
				List list=session.createSQLQuery(getplaniddata).setInteger("projectcd", projectcd).setInteger("yearcd", yearcd).list();
				result=Integer.parseInt(list.get(0).toString());
				
				List list1=session.createSQLQuery("select qty_planned from iwmp_project_physical_aap where planid=:plan_id and phy_activity_code=:activity").setInteger("plan_id", result).setInteger("activity", Integer.parseInt(activity)).list();
				plnact=new BigDecimal(list1.get(0).toString());
				
				if(pln.compareTo(plnact)<0) 
				{
				//System.out.println("from="+pln +" =tableplan="+plnact);
					SQLQuery sqlQuery =session.createSQLQuery(update);
					sqlQuery.setBigDecimal("plan", pln);
					sqlQuery.setInteger("activity", Integer.parseInt(activity));
					sqlQuery.setInteger("plan_id", result);
					sqlQuery.setString("created", loginid);
					ch=sqlQuery.executeUpdate();
					if(ch>=1) 
					{
						res="success";
						session.getTransaction().commit();
					}
					else{
						res="fail";
						session.getTransaction().rollback();
					}
				}
				else {
					session.getTransaction().rollback();
					res="less";
				}
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res="fail";
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
			res="fail";
		}
		return res;
	}
	
	

	
	
	
}
