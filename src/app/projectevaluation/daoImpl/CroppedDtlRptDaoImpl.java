package app.projectevaluation.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.BaselineStateWiseAreaDetailBean;
import app.projectevaluation.bean.CroppedDetailBean;
import app.projectevaluation.bean.ProductionDetailsBean;
import app.projectevaluation.dao.CroppedDtlRptDao;

@Repository("CroppedDtlRptDao")
@SuppressWarnings("deprecation")
public class CroppedDtlRptDaoImpl implements CroppedDtlRptDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getStwisecroppedDtl}")
	String getStwisecroppedDtl;
	
	@Value("${getStwisecroppedOthDtl}")
	String getStwisecroppedOthDtl;
	
	@Value("${getDistwiseCroppedDtlArea}")
	String getDistwiseCroppedDtlArea;
	
	@Value("${getDistwiseCroppedDtlOtherArea}")
	String getDistwiseCroppedDtlOtherArea;
	
	@Override
	public List<CroppedDetailBean> getcroppedDtlAreaDtl() {
		List<CroppedDetailBean> getcroppedDetail = new ArrayList<>();
		String hql= getStwisecroppedDtl;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(CroppedDetailBean.class));
			getcroppedDetail = query.list();  
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getcroppedDetail;
	}

	@Override
	public List<CroppedDetailBean> getcroppedDtlAreaOthsDtl() {
		List<CroppedDetailBean> getcroppedOthDetail = new ArrayList<>();
		String hql= getStwisecroppedOthDtl;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(CroppedDetailBean.class));
			getcroppedOthDetail = query.list();  
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getcroppedOthDetail;
	}

	@Override
	public List<CroppedDetailBean> getDistwiseCropDtlArea(int stCode) {
		List<CroppedDetailBean> list = new ArrayList<>();
		String hql = getDistwiseCroppedDtlArea;
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(CroppedDetailBean.class));
			query.setInteger("stcode", stCode);
			list = query.list();
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<CroppedDetailBean> getDistwiseCropDtlOthArea(int stCode) {
		List<CroppedDetailBean> list = new ArrayList<>();
		String hql = getDistwiseCroppedDtlOtherArea;
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(CroppedDetailBean.class));
			query.setInteger("stcode", stCode);
			list = query.list();
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}


}
