package app.mahotsav.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.dao.WMReportDao;
import app.projectevaluation.bean.ProjectEvaluationBean;

@Repository("WMReportDao")
public class WMReportDaoImpl implements WMReportDao {

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getWMInaugurationData}")
	String getWMInaugurationDetails;

	@Override
	public List<InaugurationMahotsavBean> getStateWMInaugurationReport() {
		List<InaugurationMahotsavBean> getWMInaugurationData = new ArrayList<InaugurationMahotsavBean>();
		String hql = getWMInaugurationDetails;
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(InaugurationMahotsavBean.class));
			getWMInaugurationData = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
			
		return getWMInaugurationData;
	}
	
	
}
