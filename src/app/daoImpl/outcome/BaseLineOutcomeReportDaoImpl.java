package app.daoImpl.outcome;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.dao.outcomes.BaseLineOutcomeReportDao;
import app.model.IwmpUserProjectMap;

@Repository("BaseLineOutcomeReportDao")
public class BaseLineOutcomeReportDaoImpl implements BaseLineOutcomeReportDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getBlsOutReportStateLevel}")
	String getBlsOutReportStateLevel;
	
	@Value("${getBlsOutReportStateLevelForStCode}")
	String getBlsOutReportStateLevelForStCode;
	
	@Value("${getBlsOutReportDistrictLevel}")
	String getBlsOutReportDistrictLevel;
	
	@Value("${getBlsOutReportProjectLevel}")
	String getBlsOutReportProjectLevel;
	
	@Value("${getBlsOutReportDetailLevel}")
	String getBlsOutReportDetailLevel;
	
	@Value("${getBlsReportDetailLevel}")
	String getBlsReportDetailLevel;

	@Override
	public List<BaseLineOutcomeBean> getBlsOutReportStateLevel() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, List<BaseLineOutcomeBean>> map = new LinkedHashMap<String, List<BaseLineOutcomeBean>>();
		List<BaseLineOutcomeBean> list = new ArrayList<BaseLineOutcomeBean>();
		String hql=getBlsOutReportStateLevel;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
			list = query.list();
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsOutReportDistrictLevel(Integer stCode) {
		// TODO Auto-generated method stub
		List<BaseLineOutcomeBean> list = new ArrayList<BaseLineOutcomeBean>();
		String hql=getBlsOutReportDistrictLevel;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stCode", stCode);
			query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
			list = query.list();
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsOutReportProjectLevel(Integer dCode) {
		// TODO Auto-generated method stub
		List<BaseLineOutcomeBean> list = new ArrayList<BaseLineOutcomeBean>();
		String hql=getBlsOutReportProjectLevel;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("dCode", dCode);
			query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
			list = query.list();
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public List<BaseLineOutcomeBean> getBlsOutReportStateLevelForStCode(Integer stCode) {
		// TODO Auto-generated method stub
		List<BaseLineOutcomeBean> list = new ArrayList<BaseLineOutcomeBean>();
		String hql=getBlsOutReportStateLevelForStCode;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stCode", stCode);
			query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
			list = query.list();
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public List<NewBaseLineSurveyBean> getBlsOutReportDetailLevel(Integer projectId) {
		// TODO Auto-generated method stub
		List<NewBaseLineSurveyBean> result=new ArrayList<NewBaseLineSurveyBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=getBlsOutReportDetailLevel;
				query = session.createSQLQuery(hql);
				query.setInteger("project", projectId);
				query.setResultTransformer(Transformers.aliasToBean(NewBaseLineSurveyBean.class));
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
		}
		finally {
			session.getTransaction().commit();
			//session.flush(); session.close();
		}
		return result;
	}

}
