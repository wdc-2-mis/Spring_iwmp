package app.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.RevisedActPlanBean;
import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.bean.pfms.PfmsTranxMappedWithProjBean;
import app.dao.PfmsStwiseExpndtreDao;

@Repository("PfmsStwiseExpndtreDao")
public class PfmsStwiseExpndtreDaoImpl implements PfmsStwiseExpndtreDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getStwiseExpndtr}")
	String getStwiseExpndtr;
	
	@Value("${getDistwiseExpndtr}")
	String getDistwiseExpndtr;
	
	@Value("${getTranxMappedWithProjData}")
	String getTranxMappedWithProjData;
	
	@Override
	public List<PfmsStwiseExpndtreBean> getStwiseExpndtr() {
		Session session = sessionFactory.getCurrentSession();
		List<PfmsStwiseExpndtreBean> distExpList = new ArrayList<>();
		String sthql = getStwiseExpndtr;
		try {
			session.beginTransaction();
			distExpList = session.createSQLQuery(sthql).setResultTransformer(Transformers.aliasToBean(PfmsStwiseExpndtreBean.class)).list();
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return distExpList;
	}

	@Override
	public List<PfmsStwiseExpndtreBean> getDistwiseExpndtr(int stCode) {
		Session session = sessionFactory.getCurrentSession();
		List<PfmsStwiseExpndtreBean> distExpList = new ArrayList<>();
		String disthql = getDistwiseExpndtr;
		try {
			session.beginTransaction();
			distExpList = session.createSQLQuery(disthql).setInteger("stCode", stCode).setResultTransformer(Transformers.aliasToBean(PfmsStwiseExpndtreBean.class)).list();
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return distExpList;
	}

	@Override
	public List<PfmsTranxMappedWithProjBean> getTrnxMappedWithProjData(int distCode) {
		Session session = sessionFactory.getCurrentSession();
		List<PfmsTranxMappedWithProjBean> list = new ArrayList<>();
		String hql = getTranxMappedWithProjData;
		try {
			session.beginTransaction();
			list = session.createSQLQuery(hql).setInteger("distCode", distCode).setResultTransformer(Transformers.aliasToBean(PfmsTranxMappedWithProjBean.class)).list();
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

}
