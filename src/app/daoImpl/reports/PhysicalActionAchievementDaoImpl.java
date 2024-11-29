package app.daoImpl.reports;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.PhysicalActionPlanBean;
import app.bean.reports.ActivityWiseUptoPlanAchieveWorkBean;
import app.bean.reports.PhysicalActionAchievementBean;
import app.dao.reports.PhysicalActionAchievementDao;

@Repository("PhysicalActionAchievementDao")
public class PhysicalActionAchievementDaoImpl implements PhysicalActionAchievementDao{

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getProjectAchievementReport}")
	String getProjectAchievementReport;
	
	@Value("${getYearForPhysicalActionAchievementReport}")
	String getYearForPhysicalActionAchievementReport;
	
	@Value("${getYearForTarAchDashboard}")
	String getYearForTarAchDashboard;
	
	
	
	@Value("${getAllProjectAchievementReport}")
	String getAllProjectAchievementReport;
	
	@Value("${getAllDistrictAchievementReport}")
	String getAllDistrictAchievementReport;
	
	
	@Value("${getAllStatesAchievementReport}")
	String getAllStatesAchievementReport;
	
	@Value("${activtiyWiseUptoPlanAchievWork}")
	String activtiyWiseUptoPlanAchievWork;
	
	@Value("${activtiyWiseUptoPlanAchievWorkDist}")
	String activtiyWiseUptoPlanAchievWorkDist;
	
	@Value("${activtiyWiseUptoPlanAchievWorkProj}")
	String activtiyWiseUptoPlanAchievWorkProj;
	
	@Value("${activtiyWiseYrlyEpaWork}")
	String activtiyWiseYrlyEpaWork;
	
	@Value("${distWiseYrlyEpaActWork}")
	String distWiseYrlyEpaActWork;
	
	@Value("${projWiseYrlyEpaActWork}")
	String projWiseYrlyEpaActWork;
	
	@Value("${activtiyWiseYrlyLivWork}")
	String activtiyWiseYrlyLivWork;
	
	@Value("${distWiseYrlyLivActWork}")
	String distWiseYrlyLivActWork;
	
	@Value("${projWiseYrlyLivActWork}")
	String projWiseYrlyLivActWork;
	
	@Value("${activityWiseYrlyProdWork}")
	String activityWiseYrlyProdWork;
	
	@Value("${distWiseYrlyProdActWork}")
	String distWiseYrlyProdActWork;
	
	@Value("${projWiseYrlyProdActWork}")
	String projWiseYrlyProdActWork;
	
	@Override
	public LinkedHashMap<Integer, String> getYearForPhysicalActionAchievementReport(Integer pCode) {
		// TODO Auto-generated method stub
		String getProject=getYearForPhysicalActionAchievementReport;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getProject);
		//	query.setInteger("pCode",pCode); 
			List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  map.put(Integer.parseInt(row[0].toString()),row[1].toString());
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
		return map;
	}
	
	@Override
	public List<PhysicalActionAchievementBean> getPhysicalActionAchievementReport(Integer stCode, Integer distCode, Integer projId, Integer fromYear) {
		
		String getReport=null;
		Session session = sessionFactory.getCurrentSession();
		List<PhysicalActionAchievementBean> list = new ArrayList<PhysicalActionAchievementBean>();
		SQLQuery query;
		try {
			session.beginTransaction();   
			
			
			if(stCode==0 ) 
			{
				getReport=getAllStatesAchievementReport;
				query= session.createSQLQuery(getReport);
				//query.setInteger("stcode",stCode); 
				query.setInteger("fromYear",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActionAchievementBean.class));
				list = query.list();
			}
			if(stCode!=0 && distCode==0) 
			{
				getReport=getAllDistrictAchievementReport;
				query= session.createSQLQuery(getReport);
				query.setInteger("stcode",stCode); 
				query.setInteger("fromYear",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActionAchievementBean.class));
				list = query.list();
			}
			if(distCode!=0 && projId==0) 
			{
				getReport=getAllProjectAchievementReport;
				query= session.createSQLQuery(getReport);
				query.setInteger("dcode",distCode); 
				query.setInteger("fromYear",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActionAchievementBean.class));
				list = query.list();
			}
			if(projId!=0) 
			{
				getReport=getProjectAchievementReport;
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

	@Override
	public List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseUptoPlanAchievWorkReport(Integer stCode,
			Integer distCode, Integer projId, Integer fromYear) {
		
		String getReport=null;
		Session session = sessionFactory.getCurrentSession();
		List<ActivityWiseUptoPlanAchieveWorkBean> list = new ArrayList<ActivityWiseUptoPlanAchieveWorkBean>();
		SQLQuery query;
		try {
			session.beginTransaction();   
			
			
		/*	if(stCode==0 ) 
			{
				getReport=getAllStatesAchievementReport;
				query= session.createSQLQuery(getReport);
				//query.setInteger("stcode",stCode); 
				query.setInteger("fromYear",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActionAchievementBean.class));
				list = query.list();
			}  */
			if( distCode==0) 
			{
				getReport=activtiyWiseUptoPlanAchievWork;
				query= session.createSQLQuery(getReport);
				query.setInteger("stcd",stCode); 
				query.setInteger("finyr",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
				list = query.list();
			}
			if(distCode!=0 && projId==0) 
			{
				getReport=activtiyWiseUptoPlanAchievWorkDist;
				query= session.createSQLQuery(getReport);
				query.setInteger("distcd",distCode); 
				query.setInteger("finyr",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
				list = query.list();
			}
			if(projId!=0) 
			{
				getReport=activtiyWiseUptoPlanAchievWorkProj;
				query= session.createSQLQuery(getReport);
				query.setInteger("projid",projId); 
				query.setInteger("finyr",fromYear); 
				query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
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

	@Override
	public LinkedHashMap<Integer, String> getYearForAchDashboard() {
		String getYear=getYearForTarAchDashboard;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getYear);
			List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  map.put(Integer.parseInt(row[0].toString()),row[1].toString());
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
		return map;
	}

	@Override
	public List<ActivityWiseUptoPlanAchieveWorkBean> getActivityWiseEPALivProdWorkReport(Integer stCode,
			Integer distCode, Integer projId, Integer fromYear, String headType) {
		
		String getReport=null;
		Session session = sessionFactory.getCurrentSession();
		List<ActivityWiseUptoPlanAchieveWorkBean> list = new ArrayList<ActivityWiseUptoPlanAchieveWorkBean>();
		SQLQuery query;
		try {
			session.beginTransaction();   
			if (headType.equals("E")) {
				if (distCode == 0) {
					getReport = activtiyWiseYrlyEpaWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("stcd", stCode);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
				if (distCode != 0 && projId == 0) {
					getReport = distWiseYrlyEpaActWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("distcd", distCode);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
				if (projId != 0) {
					getReport = projWiseYrlyEpaActWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("projid", projId);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
			}
			if (headType.equals("L")) {
				if (distCode == 0) {
					getReport = activtiyWiseYrlyLivWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("stcd", stCode);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
				if (distCode != 0 && projId == 0) {
					getReport = distWiseYrlyLivActWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("distcd", distCode);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
				if (projId != 0) {
					getReport = projWiseYrlyLivActWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("projid", projId);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
			}if (headType.equals("P")) {
				if (distCode == 0) {
					getReport = activityWiseYrlyProdWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("stcd", stCode);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
				if (distCode != 0 && projId == 0) {
					getReport = distWiseYrlyProdActWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("distcd", distCode);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
				if (projId != 0) {
					getReport = projWiseYrlyProdActWork;
					query = session.createSQLQuery(getReport);
					query.setInteger("projid", projId);
					query.setInteger("finyr", fromYear);
					query.setResultTransformer(Transformers.aliasToBean(ActivityWiseUptoPlanAchieveWorkBean.class));
					list = query.list();
				}
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
