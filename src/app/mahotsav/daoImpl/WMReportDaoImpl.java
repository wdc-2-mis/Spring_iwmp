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
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.dao.WMReportDao;

@Repository("WMReportDao")
public class WMReportDaoImpl implements WMReportDao {

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getWMInaugurationData}")
	String getWMInaugurationDetails;

	@Value("${getProjLvlWMPrgDetails}")
	String getProjLvlWMPrgDetails;
	
	@Value("${getWMSocailMediaReportData}")
	String getWMSocailMediaReportDetails;
	
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

	@Override
	public List<InaugurationMahotsavBean> getProjLvlWMPrgReport() {
		List<InaugurationMahotsavBean> getProjLvlWMPrgData = new ArrayList<InaugurationMahotsavBean>();
		String hql = getProjLvlWMPrgDetails;
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(InaugurationMahotsavBean.class));
			getProjLvlWMPrgData = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
			
		return getProjLvlWMPrgData;
	}

	@Override
	public List<SocialMediaReport> getWMSocailMediaReport(Integer stcd, Integer dcode, Integer bcode, Integer vcode) {
		List<SocialMediaReport> getWMSocailMediaReport = new ArrayList<>();
		String hql = getWMSocailMediaReportDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setInteger("dcode", dcode);
			query.setInteger("bcode", bcode);
			query.setInteger("vcode", vcode);
			query.setResultTransformer(Transformers.aliasToBean(SocialMediaReport.class));
			getWMSocailMediaReport = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getWMSocailMediaReport;
	}
	
	
}
