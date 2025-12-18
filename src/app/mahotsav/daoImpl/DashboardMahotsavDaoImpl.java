package app.mahotsav.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.WatrshdInagrtnPreYtraDashBean;
import app.mahotsav.bean.DashboardMahotsavBean;
import app.mahotsav.dao.DashboardMahotsavDao;
import app.projectevaluation.bean.ProjectEvaluationBean;

@Repository("DashboardMahotsavDao")
public class DashboardMahotsavDaoImpl implements DashboardMahotsavDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Value("${WatershedMahotsavDashboardSocialMedia}")
	String WatershedMahotsavDashboardSocialMedia;
	
	@Value("${WatershedMahotsavDashboardAll}")
	String WatershedMahotsavDashboardAll;
	
	@Value("${WatershedMahotsavDashboardIngurProj}")
	String WatershedMahotsavDashboardIngurProj;
	
	@Value("${MahotsavDashboardSocialMedia}")
	String MahotsavDashboardSocialMedia;
	
	@Value("${MahotsavDashboardParticipantsActivityList}")
	String MahotsavDashboardParticipantsList;
	
	@Value("${getStatemahotsavData}")
	String getStatemahotsavData;
	
	@Override
	public Map<String, List<DashboardMahotsavBean>> getMahotsavInagrtnYtraAtVillData() {
		
		
		String medhql=WatershedMahotsavDashboardSocialMedia;
		String allhql=WatershedMahotsavDashboardAll;
		String ingprohql=WatershedMahotsavDashboardIngurProj;
		
		Map<String, List<DashboardMahotsavBean>> map = new LinkedHashMap<String, List<DashboardMahotsavBean>>();
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = null;
		SQLQuery query1 = null;
		SQLQuery query2 = null;
		try {
			List<DashboardMahotsavBean> list = new ArrayList<>();
			session.beginTransaction();
			
				query = session.createSQLQuery(allhql);
				query.setResultTransformer(Transformers.aliasToBean(DashboardMahotsavBean.class));
				list = query.list();
				map.put("all",list);

				query1 = session.createSQLQuery(ingprohql);
				query1.setResultTransformer(Transformers.aliasToBean(DashboardMahotsavBean.class));
				list = query1.list();
				map.put("inpr",list);
			
			   query2 = session.createSQLQuery(medhql);
			   query2.setResultTransformer(Transformers.aliasToBean(DashboardMahotsavBean.class)); 
			   list = query2.list(); 
			   map.put("med",list);
			
			   session.getTransaction().commit();
			
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}


	@Override
	public List<DashboardMahotsavBean> getMahotsavSocialMedia() {
		
		
		String allhql=MahotsavDashboardSocialMedia;
		
		List<DashboardMahotsavBean> list = new ArrayList<DashboardMahotsavBean>();
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = null;
		
		try {
				session.beginTransaction();
				query = session.createSQLQuery(allhql);
				query.setResultTransformer(Transformers.aliasToBean(DashboardMahotsavBean.class));
				list = query.list();
				
			    session.getTransaction().commit();
			
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}


	@Override
	public List<DashboardMahotsavBean> getParticipantsListofMahotsav() {
		
		String allhql=MahotsavDashboardParticipantsList;
		
		List<DashboardMahotsavBean> list = new ArrayList<DashboardMahotsavBean>();
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = null;
		
		try {
				session.beginTransaction();
				query = session.createSQLQuery(allhql);
				query.setResultTransformer(Transformers.aliasToBean(DashboardMahotsavBean.class));
				list = query.list();
				
			    session.getTransaction().commit();
			
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}


	@Override
	public List<DashboardMahotsavBean> getStateWiseParticipants() {
		// TODO Auto-generated method stub
		List<DashboardMahotsavBean> getStatep = new ArrayList<DashboardMahotsavBean>();
		String hql = getStatemahotsavData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(DashboardMahotsavBean.class));
			getStatep = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
			
		return getStatep;
	}


}
