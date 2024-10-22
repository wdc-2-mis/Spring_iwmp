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

import app.PfmsStateDistProjExpndtrBean;
import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.dao.PfmsStateDistProjExpndtrDao;

@Repository
public class PfmsStateDistProjExpndtrDaoImpl implements PfmsStateDistProjExpndtrDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getStatewiseEpndtr}")
	String getStatewiseEpndtr;
	
	@Value("${getDistwiseEpndtr}")
	String getDistwiseEpndtr;
	
	@Value("${getProjwiseEpndtr}")
	String getProjwiseEpndtr;

	@Override
	public List<PfmsStateDistProjExpndtrBean> getStatewiseExpndtr() {
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<>();
		String hql=getStatewiseEpndtr;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(PfmsStateDistProjExpndtrBean.class));
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
	public List<PfmsStateDistProjExpndtrBean> getDistwiseExpndtr(Integer stCode) {
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<>();
		String hql=getDistwiseEpndtr;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(PfmsStateDistProjExpndtrBean.class));
			query.setInteger("stCode", stCode);
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
	public List<PfmsStateDistProjExpndtrBean> getProjwiseExpndtr(Integer dcode) {
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<>();
		String hql=getProjwiseEpndtr;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(PfmsStateDistProjExpndtrBean.class));
			query.setInteger("dcode", dcode);
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

}
