package app.daoImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.SLNACoordinatorsBean;
import app.dao.SLNACoordinatorsDao;

@Repository("SLNACoordinatorsDao")
public class SLNACoordinatorsDaoImpl implements SLNACoordinatorsDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getSLNACoordinatorsDetails}")
	String getSLNACoordinatorsDetails;
	
	@Value("${updateSLNACoordinatorsDetails}")
	String updateSLNACoordinatorsDetails;

	@Override
	public List<SLNACoordinatorsBean> getSLNACoordinatorsList() {
		List<SLNACoordinatorsBean> list = new ArrayList<>();
		Session session = sessionFactory.openSession();
		try {
			String hql = null;
			SQLQuery query = null;
			Transaction tx = session.beginTransaction();
			hql = getSLNACoordinatorsDetails;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(SLNACoordinatorsBean.class));
			list = query.list();
			
		}catch(HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public String updateSLNACrdntrDetails(Integer stCode, String name, String email, BigDecimal mobile) {
		Session session = sessionFactory.openSession();
		String res = "fail";
		int chk = 0;
		try {
			String hql = null;
			SQLQuery query = null;
			Transaction tx = session.beginTransaction();
			hql = updateSLNACoordinatorsDetails;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(SLNACoordinatorsBean.class));
			query.setString("name", name);
			query.setString("email", email);
			query.setBigDecimal("mobile", mobile);
			query.setInteger("stCode", stCode);
			chk = query.executeUpdate();
			res = chk>0?"success":"fail";
			session.getTransaction().commit();
		}catch(HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}
		return res;
	}
}
