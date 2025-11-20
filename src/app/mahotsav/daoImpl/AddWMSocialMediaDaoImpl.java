package app.mahotsav.daoImpl;

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

import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.dao.AddWMSocialMediaDao;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.projectevaluation.bean.FundUtilizationEvalReportBean;

@Repository("AddWMSocialMediaDao")
public class AddWMSocialMediaDaoImpl implements  AddWMSocialMediaDao{
	

		@Autowired
		private SessionFactory sessionFactory;
	
		@Value("${getSocialMediaReport}")
		String getSocialMediaReport;
		
		@Value("${getWMSocialMediaReport}")
		String getWMSocialMediaReport;

		@Value("${getDistWMSocialMediaReport}")
		String getDistWMSocialMediaReport;

		 @Override
		    public WatershedMahotsavRegistration findByUserRegNo(String regNo) {
		        Session session = sessionFactory.getCurrentSession();
		        WatershedMahotsavRegistration registration = null;
		        try {
		            session.beginTransaction();

		            registration = (WatershedMahotsavRegistration) session.createQuery(
		                    "FROM WatershedMahotsavRegistration r WHERE r.user_reg_no = :regNo")
		                    .setParameter("regNo", regNo)
		                    .uniqueResult();

		            session.getTransaction().commit();

		        } catch (Exception e) {
		            if (session.getTransaction().isActive()) {
		                session.getTransaction().rollback();
		            }
		            e.printStackTrace();
		        }
		        return registration;
		    }

		 @Override
		 @SuppressWarnings({ "deprecation", "unchecked" })
		 public List<SocialMediaReport> getSocialMediaReport(String stateCode, String districtCode) {

		     List<SocialMediaReport> result = new ArrayList<>();
		     Session session = sessionFactory.openSession();
		     Transaction tx = null;

		     try {
		         tx = session.beginTransaction();

		         SQLQuery query = session.createSQLQuery(getSocialMediaReport);

		         // Convert string to integer safely
		         Integer stCodeValue = 0;
		         if (stateCode != null && !stateCode.equals("") && !stateCode.equals("0")) {
		             stCodeValue = Integer.parseInt(stateCode);
		         }
		         query.setParameter("st_code", stCodeValue);

		         // If your query uses :district also, add this
		         if (districtCode != null && !districtCode.equals("") && !districtCode.equals("0")) {
		             query.setParameter("district_code", Integer.parseInt(districtCode));
		         }

		         // Map result to Bean class
		         query.setResultTransformer(Transformers.aliasToBean(SocialMediaReport.class));
		         result = query.list();

		         tx.commit();

		     } catch (HibernateException e) {
		         if (tx != null) tx.rollback();
		         System.err.println("Hibernate Exception: " + e.getMessage());
		         e.printStackTrace();
		     } catch (Exception ex) {
		         if (tx != null) tx.rollback();
		         System.err.println("General Exception: " + ex.getMessage());
		         ex.printStackTrace();
		     } finally {
		         session.close();
		     }

		     return result;
		 }


		@Override
		public List<SocialMediaReport> getWMSocialMediaReport() {
			List<SocialMediaReport> getWM = new ArrayList<>();
			String hql = getWMSocialMediaReport;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(SocialMediaReport.class));
				getWM = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return getWM;
		}

		@Override
		public List<SocialMediaReport> getDistWMSocialMediaReport(Integer stcd) {
			List<SocialMediaReport> listD = new ArrayList<>();
			String hql = getDistWMSocialMediaReport;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setInteger("stcd", stcd);
				query.setResultTransformer(Transformers.aliasToBean(SocialMediaReport.class));
				listD = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return listD;
		}
}
