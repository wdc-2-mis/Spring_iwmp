package app.daoImpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.reports.PhysicalActionAchievementBean;
import app.dao.reports.PhysicalTarAchDao;

	@Repository("PhysicalTarAchDao")
	public class PhysicalTarAchDaoImpl implements PhysicalTarAchDao{
	
	
		@Autowired
		protected SessionFactory sessionFactory;
		
		@Value("${getAllStateTarAchReport}")
		String getAllStateTarAchReport;
		
		@Value("${getAllDistrictTarAchReport}")
		String getAllDistrictTarAchReport;
		
		@Value("${getAllProjectTarAchReport}")
		String getAllProjectTarAchReport;
		
		@Value("${getProjectTarAchReport}")
		String getProjectTarAchReport;
	
	
	
	@Override
	public List<PhysicalActionAchievementBean> getphysicalTarAchReport(Integer stCode, Integer distCode, Integer projId, Integer fromYear) {
		
		String getReport=null;
		Session session = sessionFactory.getCurrentSession();
		List<PhysicalActionAchievementBean> list = new ArrayList<PhysicalActionAchievementBean>();
		SQLQuery query;
		try {
			session.beginTransaction();   
			
			
			if(stCode==0 ) 
			{
				getReport=getAllStateTarAchReport;
				query= session.createSQLQuery(getReport);
				//query.setInteger("stcode",stCode); 
				query.setInteger("fromYear",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActionAchievementBean.class));
				list = query.list();
			}
			if(stCode!=0 && distCode==0) 
			{
				getReport=getAllDistrictTarAchReport;
				query= session.createSQLQuery(getReport);
				query.setInteger("stcode",stCode); 
				query.setInteger("fromYear",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActionAchievementBean.class));
				list = query.list();
			}
			if(distCode!=0 && projId==0) 
			{
				getReport=getAllProjectTarAchReport;
				query= session.createSQLQuery(getReport);
				query.setInteger("dcode",distCode); 
				query.setInteger("fromYear",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActionAchievementBean.class));
				list = query.list();
			}
			if(projId!=0) 
			{
				getReport=getProjectTarAchReport;
				query= session.createSQLQuery(getReport);
				query.setInteger("projId",projId); 
				query.setInteger("fromYear",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActionAchievementBean.class));
				list = query.list();
			}
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


}
