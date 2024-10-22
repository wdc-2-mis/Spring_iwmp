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

import app.bean.OOMFAchvDetailsBean;
import app.dao.OOMFAchvDetailsDao;

@Repository("OOMFAchvDetailsDao")
public class OOMFAchvDetailsDaoImpl implements OOMFAchvDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getOOMFAchv}")
	String getOOMFAddAchvDetails;

	@Override
	public List<OOMFAchvDetailsBean> getOOMFAchvDetails(Integer finyr) {
		List<OOMFAchvDetailsBean> getOOMFAchvDetails = new ArrayList<>();
		String hql = getOOMFAddAchvDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyr", finyr);
			query.setResultTransformer(Transformers.aliasToBean(OOMFAchvDetailsBean.class));
			getOOMFAchvDetails = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getOOMFAchvDetails;
	}
	
	
}
