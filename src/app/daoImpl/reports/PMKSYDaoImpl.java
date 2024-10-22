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

import app.bean.reports.PMKSYBean;
import app.dao.reports.PMKSYDao;

@Repository
public class PMKSYDaoImpl implements PMKSYDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getBenifitedFarmersNo}")
	String getBenifitedFarmersNo;
	
	@Value("${getWtrHrvstngStrctrNo}")
	String getWtrHrvstngStrctrNo;
	
	@Value("${getDistWiseBenifitedFarmersNo}")
	String getDistWiseBenifitedFarmersNo;
	
	@Value("${getProjWiseBenifitedFarmersNo}")
	String getProjWiseBenifitedFarmersNo;
	
	@Value("${getStWiseWtrHrvstngIrriDetail}")
	String getStWiseWtrHrvstngIrriDetail;
	
	@Value("${getDistWiseWtrIrriDetails}")
	String getDistWiseWtrIrriDetails;
	
	@Value("${getProjWiseWtrIrriDetails}")
	String getProjWiseWtrIrriDetails;

	@Override
	public List<PMKSYBean> getBenifitedFarmersNo() {
		List<PMKSYBean> list = new ArrayList<>();
		String hql= getBenifitedFarmersNo;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PMKSYBean.class));
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<PMKSYBean> getWtrHrvstngStrctrNo() {
		List<PMKSYBean> list = new ArrayList<>(); 
		String hql= getWtrHrvstngStrctrNo;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PMKSYBean.class));
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<PMKSYBean> getDistWiseBenifitedFarmersNo(int stcode) {
		List<PMKSYBean> list = new ArrayList<>();
		String hql= getDistWiseBenifitedFarmersNo;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PMKSYBean.class));
			query.setInteger("stcode", stcode);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<PMKSYBean> getProjWiseBenifitedFarmersNo(int dcode) {
		List<PMKSYBean> list = new ArrayList<>();
		String hql= getProjWiseBenifitedFarmersNo;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PMKSYBean.class));
			query.setInteger("dcode", dcode);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<PMKSYBean> getStWiseWtrHrvstngIrriDetail() {

		List<PMKSYBean> list = new ArrayList<>();
		String hql= getStWiseWtrHrvstngIrriDetail;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PMKSYBean.class));
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	
	}

	@Override
	public List<PMKSYBean> getDistWiseWtrHrvstngIrriDetail(int stcode) {
		List<PMKSYBean> list = new ArrayList<>();
		String hql= getDistWiseWtrIrriDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PMKSYBean.class));
			query.setInteger("stcode", stcode);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<PMKSYBean> getProjWiseWtrHrvstngIrriDetail(int dcode) {
		List<PMKSYBean> list = new ArrayList<>();
		String hql= getProjWiseWtrIrriDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PMKSYBean.class));
			query.setInteger("dcode", dcode);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

}
