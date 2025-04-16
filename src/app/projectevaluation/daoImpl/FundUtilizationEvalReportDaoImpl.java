package app.projectevaluation.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.ConvergenceWorksBean;
import app.dao.ConvergenceWorksDao;
import app.projectevaluation.bean.FundUtilizationEvalReportBean;
import app.projectevaluation.dao.FundUtilizationEvalReportDao;


@Repository("FundUtilizationEvalReportDao")
public class FundUtilizationEvalReportDaoImpl implements FundUtilizationEvalReportDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getMidTermFundUtilEvalReport}")
	String getMidTermFundUtilEvalReport;
	


	@Override
	public List<FundUtilizationEvalReportBean> getFundUtilizationEvalReport() {
		List<FundUtilizationEvalReportBean> getFundEVal = new ArrayList<>();
		String hql = getMidTermFundUtilEvalReport;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(FundUtilizationEvalReportBean.class));
			getFundEVal = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getFundEVal;
	}



}
