package app.watershedyatra.daoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.NRSCWorksBean;
import app.common.CommonFunctions;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.dao.VillageWatershedYatraReportDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

@Repository(" VillageWatershedYatraReportDao")
public class VillageWatershedYatraReportDaoImpl implements  VillageWatershedYatraReportDao {
	

		@Autowired
		private SessionFactory sessionFactory;
		
		@Autowired
		CommonFunctions commonFunction;
		
		
		@Value("${getWatershedYatraReport}")
		String getWatershedYatraReport;
		
		@Value("${getWatershedYatraReportfromdate}")
		String getWatershedYatraReportfromdate;
		
		@Value("${getWatershedYatraReporttodate}")
		String getWatershedYatraReporttodate;
		
		@Value("${getWorkStatusReport}")
		String getWorkStatusReport;
		
		@Value("${getWorkStatusReportActivity}")
		String getWorkStatusReportActivity;
		
		@Value("${getActivityNRMWorkReport}")
		String getActivityNRMWorkReport;
		
		@Value("${getActivityNRMWorkjalsakti}")
		String getActivityNRMWorkjalsakti;

		@Override
		public List<WatershedYatraBean> showWatershedYatraVillageReport(Integer State, Integer district, Integer block,
				Integer grampan, String userdate, String userdateto) {
				
				String getReport=getWatershedYatraReport;
				
				
				String getReport1=getWatershedYatraReportfromdate;
				
				
				String getReport2=getWatershedYatraReporttodate;
				
				Date yadate =null;
				Date yadateto =null;
				Query query;
				Query query1;
				Query query2;
				Session session = sessionFactory.getCurrentSession();
				List<WatershedYatraBean> list = new ArrayList<WatershedYatraBean>();
				try {
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					
					if(!userdate.equals("")) 
						yadate = formatter.parse(userdate);
					
					if(!userdateto.equals("")) 
						yadateto = formatter.parse(userdateto);
					
						session.beginTransaction();
						if(userdate.equals("") && userdateto.equals("")) {
							query= session.createSQLQuery(getReport);
							query.setInteger("statecd",State); 
							query.setInteger("distcd",district); 
							query.setInteger("blkcd",block); 
							query.setInteger("gpkcd",grampan); 
							query.setResultTransformer(Transformers.aliasToBean(WatershedYatraBean.class));
							list = query.list();
						}
						if( !userdate.equals("") && !userdateto.equals("")) {
							query= session.createSQLQuery(getReport2);
							query.setInteger("statecd",State); 
							query.setInteger("distcd",district); 
							query.setInteger("blkcd",block); 
							query.setInteger("gpkcd",grampan); 
							query.setParameter("userdate",yadate);
							query.setParameter("yadateto",yadateto);
							query.setResultTransformer(Transformers.aliasToBean(WatershedYatraBean.class));
							list = query.list();
						}
						if(!userdate.equals("")  &&  userdateto.equals("")) {
							query= session.createSQLQuery(getReport1);
							query.setInteger("statecd",State); 
							query.setInteger("distcd",district); 
							query.setInteger("blkcd",block); 
							query.setInteger("gpkcd",grampan); 
							query.setParameter("userdate",yadate);
							query.setResultTransformer(Transformers.aliasToBean(WatershedYatraBean.class));
							list = query.list();
						}
						session.getTransaction().commit();
				} 
				catch (HibernateException e) 
				{
					System.err.print("Hibernate error");
					e.printStackTrace();
					session.getTransaction().rollback();
				} 
				catch(Exception ex)
				{
					session.getTransaction().rollback();
					ex.printStackTrace();
				}
				return list;
			}


		@Override
		public List<NRSCWorksBean> getWorkStatusReport(Integer State, String userdate, String userdateto) {
			// TODO Auto-generated method stub
			
			String sql=getWorkStatusReport;
			String sqla=getWorkStatusReportActivity;
			Date yadate =null;
			Date yadateto =null;
			Query query;
			
			Session session = sessionFactory.getCurrentSession();
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			try {
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				
				if(!userdate.equals("")) 
					yadate = formatter.parse(userdate);
				
				if(!userdateto.equals("")) 
					yadateto = formatter.parse(userdateto);
				
					session.beginTransaction();
					
					if( !userdate.equals("") && !userdateto.equals("")) {
						
						if(State>0)
							query= session.createSQLQuery(sqla);
						else
							query= session.createSQLQuery(sql);
						
						query.setInteger("statecd",State); 
						query.setParameter("userdate",yadate);
						query.setParameter("yadateto",yadateto);
						query.setResultTransformer(Transformers.aliasToBean(NRSCWorksBean.class));
						list = query.list();
					}
					
					session.getTransaction().commit();
			} 
			catch (HibernateException e) 
			{
				System.err.print("Hibernate error");
				e.printStackTrace();
				session.getTransaction().rollback();
			} 
			catch(Exception ex)
			{
				session.getTransaction().rollback();
				ex.printStackTrace();
			}
			return list;
		}


		@Override
		public List<NRSCWorksBean> getActivityNRMWorkReportPost(Integer State, Integer district, Integer finyr, Integer activity) {
			// TODO Auto-generated method stub
			
			
			Date yadate =null;
			Date yadateto =null;
			Query query;
			String sqla=getActivityNRMWorkReport;
			Session session = sessionFactory.getCurrentSession();
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			try {
				
					session.beginTransaction();
					query= session.createSQLQuery(sqla);
					query.setInteger("stcd",State); 
					query.setInteger("distcd",district); 
					query.setInteger("finyr",finyr);
					query.setInteger("actcd",activity);
					query.setResultTransformer(Transformers.aliasToBean(NRSCWorksBean.class));
					list = query.list();
					
					session.getTransaction().commit();
			} 
			catch (HibernateException e) 
			{
				System.err.print("Hibernate error");
				e.printStackTrace();
				session.getTransaction().rollback();
			} 
			catch(Exception ex)
			{
				session.getTransaction().rollback();
				ex.printStackTrace();
			}
			return list;
		}


		@Override
		public List<NRSCWorksBean> getActivityNRMWorkJalSakati() {
			
			String sqla=getActivityNRMWorkjalsakti;
			Session session = sessionFactory.getCurrentSession();
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			Query query;
			
			try {
					session.beginTransaction();
					query= session.createSQLQuery(sqla);
					query.setResultTransformer(Transformers.aliasToBean(NRSCWorksBean.class));
					list = query.list();
					
					session.getTransaction().commit();
			} 
			catch (HibernateException e) 
			{
				System.err.print("Hibernate error");
				e.printStackTrace();
				session.getTransaction().rollback();
			} 
			catch(Exception ex)
			{
				session.getTransaction().rollback();
				ex.printStackTrace();
			}
			return list;
		}
		
		
}
