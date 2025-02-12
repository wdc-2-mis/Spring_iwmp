package app.watershedyatra.daoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
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
		

		@Override
		public List<WatershedYatraBean> showWatershedYatraVillageReport(Integer State, Integer district, Integer block,
				Integer grampan, String userdate) {
				
				String getReport=getWatershedYatraReport;
				Session session = sessionFactory.getCurrentSession();
				List<WatershedYatraBean> list = new ArrayList<WatershedYatraBean>();
				try {
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

					Date yadate = formatter.parse(userdate);
					
						session.beginTransaction();
						Query query= session.createSQLQuery(getReport);
						query.setInteger("statecd",State); 
						query.setInteger("distcd",district); 
						query.setInteger("blkcd",block); 
						query.setInteger("gpkcd",grampan); 
						query.setParameter("userdate",yadate);
						query.setResultTransformer(Transformers.aliasToBean(WatershedYatraBean.class));
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
