package app.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.NRSCWorksBean;
import app.dao.NRSCWorksDao;

@Repository("NRSCWorksDao")
public class NRSCWorksDaoImpl implements NRSCWorksDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getNRSCWorks}")
	String getNRSCWorksDetails;

	@Value("${getNRSCDistWorks}")
	String getNRSCDistWorksDetails;

	@Value("${getNRSCProjWorks}")
	String getNRSCProjWorksDetails;

	@SuppressWarnings("rawtypes")

	@Override
	public List<NRSCWorksBean> getNRSCWorks() {
		List<NRSCWorksBean> getNRSCWorks = new ArrayList<>();
		String hql = getNRSCWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(NRSCWorksBean.class));
			getNRSCWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}

		return getNRSCWorks;
	}

	@Override
	public List<NRSCWorksBean> getNRSCDistWorks(Integer stcd) {
		List<NRSCWorksBean> getNRSCDistWorks = new ArrayList<>();
		String hql = getNRSCDistWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setResultTransformer(Transformers.aliasToBean(NRSCWorksBean.class));
			getNRSCDistWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getNRSCDistWorks;
	}

	@Override
	public List<NRSCWorksBean> getNRSCProjWorks(Integer dcode) {
		List<NRSCWorksBean> getNRSCProjWorks = new ArrayList<>();
		String hql = getNRSCProjWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("dcode", dcode);
			query.setResultTransformer(Transformers.aliasToBean(NRSCWorksBean.class));
			getNRSCProjWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getNRSCProjWorks;
	}

}
