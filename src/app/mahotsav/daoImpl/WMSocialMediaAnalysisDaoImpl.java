package app.mahotsav.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.dao.WMSocialMediaAnalysisDao;
import app.mahotsav.model.WatershedMahotsavMediaMaster;
import app.model.IwmpDistrict;

	
@Repository("WMSocialMediaAnalysisDao")
public class WMSocialMediaAnalysisDaoImpl implements WMSocialMediaAnalysisDao{
	
		@Autowired
		protected SessionFactory sessionFactory;
		
		@Value("${districtListByStateCode}") 
		String distlist;
		
		@Value("${getWmAnalysisReport}") 
		String getWmAnalysisReport;
		
		@Override
		public List<IwmpDistrict> getDistrictList(int stateCode) {
		
		List<IwmpDistrict> result=new ArrayList<IwmpDistrict>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=distlist;
			result = ses.createQuery(hql).setParameter("stCode", stateCode).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}

		
		@Override
		public List<WatershedMahotsavMediaMaster> getPlatformList() {
		    List<WatershedMahotsavMediaMaster> result = new ArrayList<>();
		    Session ses = sessionFactory.getCurrentSession();
		    try {
		        ses.beginTransaction();
		        String hql = "FROM WatershedMahotsavMediaMaster"; 
		        result = ses.createQuery(hql, WatershedMahotsavMediaMaster.class).list();
		    } catch (HibernateException e) {
		        System.err.println("Hibernate error in getPlatformList");
		        e.printStackTrace();
		        ses.getTransaction().rollback();
		    } catch (Exception ex) {
		        ses.getTransaction().rollback();
		        ex.printStackTrace();
		    } finally {
		        ses.getTransaction().commit();
		    }
		    return result;
		}

		public List<SocialMediaReport> getWMSocialMediaAnalysisReport(
		        Integer stcd, Integer dcode, Integer media, String orderBy) {

		    List<SocialMediaReport> list = new ArrayList<>();
		    Session session = sessionFactory.getCurrentSession();

		    try {
		        session.beginTransaction();

		        SQLQuery query = session.createSQLQuery(getWmAnalysisReport);
		        query.setInteger("stcd", stcd);
		        query.setInteger("dcode", dcode);
		        query.setInteger("media", media);
		        query.setString("orderBy", orderBy);

		        query.setResultTransformer(
		            Transformers.aliasToBean(SocialMediaReport.class));

		        list = query.list();
		        session.getTransaction().commit();

		    } catch (Exception e) {
		        session.getTransaction().rollback();
		        e.printStackTrace();
		    }

		    return list;
		}


}
