package app.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

import app.bean.PhysicalActBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.TarAchProjOutcomeBean;
import app.dao.TarAchProjOutcomeDao;
import app.model.UserReg;

@Repository("TarAchProjOutcomeDao")
public class TarAchProjOutcomeDaoImpl implements TarAchProjOutcomeDao{
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getFromYearForTarAchProjOutcome}")
	String getFromYear;
	
	@Value("${getToYearForTarAchProjOutcome}")
	String getToYear;
	
	@Value("${tarachprojrpt}")
	String tarachprojrpt;
	
	@Value("${getFYearDtl}")
	String getFYearDtl;
	
	@Value("${getYearDtl}")
	String getYearDtl;
	
	@Override
	public LinkedHashMap<Integer, String> getFromYearFortarAchProjOutcome(Integer pCode) {
		// TODO Auto-generated method stub
				String getProject=getFromYear;
				Session session = sessionFactory.getCurrentSession();
				LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
				try {
					session.beginTransaction();
					SQLQuery query= session.createSQLQuery(getProject);
					query.setInteger("pCode",pCode); 
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
	public LinkedHashMap<Integer, String> getToYearFortarAchProjOutcome(Integer fromYear, String projId) {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getToYear);
			query.setInteger("fromYear",fromYear); 
			//query.setString("projId",projId); 
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
	public List<TarAchProjOutcomeBean> getToYearForPhysicalActionPlanReport(Integer fromYear, Integer toYear, Integer project, Integer state, Integer district) {
		String getReport=tarachprojrpt;
		Session session = sessionFactory.getCurrentSession();
		List<TarAchProjOutcomeBean> list = new ArrayList<TarAchProjOutcomeBean>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getReport);
			query.setInteger("project", project);
			query.setInteger("stCode",state); 
			query.setInteger("distCode",district); 
			//query.setInteger("projId",project); 
			query.setInteger("fromYear",fromYear); 
			query.setInteger("toYear",toYear); 
			query.setResultTransformer(Transformers.aliasToBean(TarAchProjOutcomeBean.class));
			list = query.list();
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
	public List<TarAchProjOutcomeBean> getfYear() {
    List<TarAchProjOutcomeBean> getfYear=new ArrayList<TarAchProjOutcomeBean>();
		
		String hql=getFYearDtl;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TarAchProjOutcomeBean.class));
			getfYear = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getfYear;
	}

	@Override
	public List<TarAchProjOutcomeBean> getyList() {
List<TarAchProjOutcomeBean> getyList=new ArrayList<TarAchProjOutcomeBean>();
		
		String hql=getYearDtl;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TarAchProjOutcomeBean.class));
			getyList = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getyList;
	}

	
	
}
