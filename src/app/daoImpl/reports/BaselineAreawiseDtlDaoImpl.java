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
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import app.bean.BaseLineOutcomeBean;
import app.bean.BaselineStateWiseAreaDetailBean;
import app.bean.BaselineStatewiseCropDetailBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.dao.reports.BaselineAreawiseDtlDao;

@Repository("BaselineAreawiseDtlDao")
@SuppressWarnings("deprecation")
public class BaselineAreawiseDtlDaoImpl implements BaselineAreawiseDtlDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Value("${getStwiseAreaDetail}")
	String getStwiseAreaDtl;
	
	@Value("${getStwiseAreaAchievDetail}")
	String getStwiseAreaAchievDtl;
	
	@Value("${getStwiseCropAreaDetail}")
	String getStwiseCrpAreaDtl;
	
	@Value("${getDistWiseCrpAreaDtl}")
	String getDistWiseCrpAreaDtl;
	
	@Value("${getProjWiseCropAreaDtl}")
	String getProjWiseCropAreaDtl;
	
	@Value("${getStwiseCropAreaOutcomeDetail}")
	String getStwiseCrpAreaOutDtl;
	
	@Value("${getDistwiseAreaDetail}")
	String getDistwiseAreaDetail;
	
	@Value("${getProjtwiseAreaDetail}")
	String getProjtwiseAreaDetail;
	
	@Value("${getDistwiseAreaAchieveDetail}")
	String getDistwiseAreaAchieveDetail;
	
	@Value("${getProjwiseAreaAchieveDetail}")
	String getProjwiseAreaAchieveDetail;
	
	@Value("${getDistrictwiseCropAreaOutcomeDetail}")
	String getDistrictwiseCropAreaOutcomeDetail;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<BaselineStateWiseAreaDetailBean> getStwiseAreaDetail() {
		List<BaselineStateWiseAreaDetailBean> getStwiseAreaDetail = new ArrayList<>();
		String hql= getStwiseAreaDtl;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
			getStwiseAreaDetail = query.list();  // :stcode   :distcode
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getStwiseAreaDetail;
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getStwiseAreaAchievDetail() {
		List<BaselineStateWiseAreaDetailBean> getStwiseAreaAchDetail = new ArrayList<>();
		String hql= getStwiseAreaAchievDtl;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
			getStwiseAreaAchDetail = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getStwiseAreaAchDetail;
	}

	@Override
	public List<BaselineStatewiseCropDetailBean> getStwiseCropTypeSurveyDetail() {
		List<BaselineStatewiseCropDetailBean> getStwiseCropAreaDetail = new ArrayList<>();
		String hql= getStwiseCrpAreaDtl;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(BaselineStatewiseCropDetailBean.class));
			getStwiseCropAreaDetail = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getStwiseCropAreaDetail;
	}

	@Override
	public List<BaselineStatewiseCropDetailBean> getStwiseCropTypeOutcomeDetail() {
		List<BaselineStatewiseCropDetailBean> getStwiseCropAreaoutDetail = new ArrayList<>();
		String hql= getStwiseCrpAreaOutDtl;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(BaselineStatewiseCropDetailBean.class));
			getStwiseCropAreaoutDetail = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getStwiseCropAreaoutDetail;
	}


	@Override
	public List<BaselineStatewiseCropDetailBean> getDistblsCrpareaSrvyDetail(int stCode) {
			// TODO Auto-generated method stub
			List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
			String hql=getDistWiseCrpAreaDtl;
			Session session = sessionFactory.getCurrentSession();
			try {
				
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setInteger("stCode", stCode);
				query.setResultTransformer(Transformers.aliasToBean(BaselineStatewiseCropDetailBean.class));
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
	public List<BaselineStatewiseCropDetailBean> getProjWiseblsCrpareaSrvyDetail(int dCode) {
			// TODO Auto-generated method stub
			List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
			String hql=getProjWiseCropAreaDtl;
			Session session = sessionFactory.getCurrentSession();
			try {
				
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setInteger("dCode", dCode);
				query.setResultTransformer(Transformers.aliasToBean(BaselineStatewiseCropDetailBean.class));
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
	public List<BaselineStateWiseAreaDetailBean> getDistWiseAreaDetail(int stcode) {
		
		List<BaselineStateWiseAreaDetailBean> getStwiseAreaDetail = new ArrayList<>();
		String hql= getDistwiseAreaDetail;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcode", stcode);
			query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
			getStwiseAreaDetail = query.list();  // :stcode   :distcode
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getStwiseAreaDetail;
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getProjWiseAreaDetail(int distcode) {
		// TODO Auto-generated method stub
		List<BaselineStateWiseAreaDetailBean> getStwiseAreaDetail = new ArrayList<>();
		String hql= getProjtwiseAreaDetail;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("distcode", distcode);
			query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
			getStwiseAreaDetail = query.list();  
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getStwiseAreaDetail;
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getDistwiseAreaAchieveDetail(int stcode) {
		
		List<BaselineStateWiseAreaDetailBean> getDistwiseAreaAchDetail = new ArrayList<>();
		String hql= getDistwiseAreaAchieveDetail;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcode", stcode);
			query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
			getDistwiseAreaAchDetail = query.list();  // :stcode   :distcode
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getDistwiseAreaAchDetail;
	}

	@Override
	public List<BaselineStateWiseAreaDetailBean> getProjwiseAreaAchieveDetail(int dcode) {
		
		List<BaselineStateWiseAreaDetailBean> projList = new ArrayList<>();
		String hql= getProjwiseAreaAchieveDetail;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("dcode", dcode);
			query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
			projList = query.list();  
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return projList;
	}

	@Override
	public List<BaselineStatewiseCropDetailBean> getDistwiseAreaCrpDetail(int stcode) {
		Session session = sessionFactory.getCurrentSession();
		List<BaselineStatewiseCropDetailBean> dlist = new ArrayList<>();
		String proj = getDistrictwiseCropAreaOutcomeDetail;
		try {
			session.beginTransaction();
			dlist = session.createSQLQuery(proj).setInteger("stcode", stcode).setResultTransformer(Transformers.aliasToBean(BaselineStatewiseCropDetailBean.class)).list();
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return dlist;
	}



}
