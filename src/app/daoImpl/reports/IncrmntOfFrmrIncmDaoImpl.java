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

import app.bean.reports.IncrmntOfFrmrIncmBean;
import app.dao.reports.IncrmntOfFrmrIncmDao;

@Repository("IncrmntOfFrmrIncmDao")
public class IncrmntOfFrmrIncmDaoImpl implements IncrmntOfFrmrIncmDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getStWiseFrmrIncme}")
	String getStWiseFrmrIncme;
	
	@Value("${getDistWiseFrmrIncme}")
	String getDistWiseFrmrIncme;
	
	@Value("${getProjWiseFrmrIncme}")
	String getProjWiseFrmrIncme;
	
	@Value("${getStAreaPercentgage}")
	String getStAreaPercentgage;
	
	@Value("${getTarIncmArea}")
	String getTarIncmArea;
	
	@Override
	public List<IncrmntOfFrmrIncmBean> getStWiseFrmrIncmDetail(int stcode, int finyrcd) {
		
		List<IncrmntOfFrmrIncmBean> list = new ArrayList<>();
		finyrcd = finyrcd==0?finyrcd:finyrcd +1;
		String hql = getStWiseFrmrIncme;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(IncrmntOfFrmrIncmBean.class));
			query.setInteger("stcode", stcode);
			query.setInteger("finyrcd", finyrcd);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<IncrmntOfFrmrIncmBean> getStWiseAreaPercentage() {
		
		List<IncrmntOfFrmrIncmBean> list = new ArrayList<>();
		String hql = getStAreaPercentgage;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(IncrmntOfFrmrIncmBean.class));
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<IncrmntOfFrmrIncmBean> getTarIncmAreaDetails(int finyrcd) {
		List<IncrmntOfFrmrIncmBean> list = new ArrayList<>();
		String hql = getTarIncmArea;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(IncrmntOfFrmrIncmBean.class));
			query.setInteger("finyrcd", finyrcd);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<IncrmntOfFrmrIncmBean> getDistWiseFrmrIncmDetail(int stcode, int finyrcd) {
		
		List<IncrmntOfFrmrIncmBean> list = new ArrayList<>();
		finyrcd = finyrcd==0?finyrcd:finyrcd +1;
		String hql = getDistWiseFrmrIncme;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(IncrmntOfFrmrIncmBean.class));
			query.setInteger("stcode", stcode);
			query.setInteger("finyrcd", finyrcd);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<IncrmntOfFrmrIncmBean> getProjWiseFrmrIncmDetail(int dcode, int finyrcd) {
		List<IncrmntOfFrmrIncmBean> list = new ArrayList<>();
		finyrcd = finyrcd==0?finyrcd:finyrcd +1;
		String hql = getProjWiseFrmrIncme;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(IncrmntOfFrmrIncmBean.class));
			query.setInteger("dcode", dcode);
			query.setInteger("finyrcd", finyrcd);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

}
