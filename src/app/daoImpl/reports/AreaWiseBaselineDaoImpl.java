package app.daoImpl.reports;

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
import app.dao.reports.AreaWiseBaselineDao;

	@Repository("AreaWiseBaselineDao")
	@SuppressWarnings("deprecation")
	public class AreaWiseBaselineDaoImpl implements AreaWiseBaselineDao{

		@Autowired
		private SessionFactory sessionFactory;
		
		
		@Value("${getStatewiseAreaDetail}")
		String getStatewiseAreaDtl;
		
		@Value("${getStateWiseAreaDetails}")
		String getStateWiseAreaDetails;
		

		@Value("${getdistwiseAreaDetailO5}")
		String getdistwiseAreaDetailO5;

		@Value("${getDistWiseAreaDetails}")
		String getDistWiseArea;
		
		
		@SuppressWarnings("rawtypes")
		@Override
		public List<BaselineStateWiseAreaDetailBean> getStatewiseAreaDetail() {
			List<BaselineStateWiseAreaDetailBean> getStatewiseAreaDetail = new ArrayList<>();
			String hql= getStatewiseAreaDtl;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
				getStatewiseAreaDetail = query.list();
				session.getTransaction().commit();
			}catch(Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return getStatewiseAreaDetail;
		}
		
		@Override
		public List<BaselineStateWiseAreaDetailBean> getStateWiseAreaDetail2() {
			List<BaselineStateWiseAreaDetailBean> getStateWiseAreaDetail2 = new ArrayList<>();
			String hql= getStateWiseAreaDetails;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
				getStateWiseAreaDetail2 = query.list();
				session.getTransaction().commit();
			}catch(Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return getStateWiseAreaDetail2;
		}


		@Override
		public List<BaselineStateWiseAreaDetailBean> getDistAreaWiseblservey(int stcd, String stname) {
			// TODO Auto-generated method stub
			List<BaselineStateWiseAreaDetailBean> getStatewiseAreaDetail = new ArrayList<>();
			String hql= getdistwiseAreaDetailO5;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setInteger("stcode", stcd);
				query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
				getStatewiseAreaDetail = query.list();
				session.getTransaction().commit();
			}catch(Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return getStatewiseAreaDetail;
		}


		@Override
		public List<BaselineStateWiseAreaDetailBean> getDistWiseAreaDetails(Integer stcd) {
			List<BaselineStateWiseAreaDetailBean> getDistWiseAreaDetails = new ArrayList<>();
			String hql = getDistWiseArea;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setInteger("stcd", stcd);
				query.setResultTransformer(Transformers.aliasToBean(BaselineStateWiseAreaDetailBean.class));
				getDistWiseAreaDetails = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return getDistWiseAreaDetails;
		}

		
		
		
		
		
		
		
		
}
