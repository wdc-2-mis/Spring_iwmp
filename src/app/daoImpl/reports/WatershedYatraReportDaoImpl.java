package app.daoImpl.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.reports.WatershedYatraReportDao;
import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.bean.WatershedYatraBean;

@Repository("WatershedYatraReportDao")
public class WatershedYatraReportDaoImpl implements WatershedYatraReportDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${districtListByStateCode}") 
	String distlist;
	
	@Value("${blocklistbydistcdwatershed}") 
	String blklist;
	
	@Value("${gplistbyblkcdwatershed}") 
	String gpklist;
	
	@Value("${getRoutePlanVanTravelwatershed}") 
	String getRoutePlanVanTravelwatershed;
	
	@Value("${getNodalOfficerwatershedAllLevel}") 
	String getNodalOfficerwatershedAllLevel;
	
	@Value("${getNodalOfficerwatershedStateLevel}") 
	String getNodalOfficerwatershedStateLevel;
	
	@Value("${getNodalOfficerwatersheddistrictLevel}") 
	String getNodalOfficerwatersheddistrictLevel;
	
	@Value("${getNodalOfficerwatershedblockLevel}") 
	String getNodalOfficerwatershedblockLevel;
	
	@Value("${getNodalOfficerwatershedvillLevel}") 
	String getNodalOfficerwatershedvillLevel;
	
	@Value("${getInaugurationData}") 
	String getInaugurationData;
	
	@Value("${getPreYatraPrepReport}") 
	String getPreYatraPrepReportData;
	
	@Value("${getInaugurationDatatodate}") 
	String getInaugurationDatatodate;
	
	@Value("${getInaugurationDatafromdate}") 
	String getInaugurationDatafromdate;

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
	public List<IwmpBlock> getBlockList(int stateCode, int dist) {
		// TODO Auto-generated method stub
		List<IwmpBlock> result=new ArrayList<IwmpBlock>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=blklist;
			result = ses.createQuery(hql).setParameter("distcod", dist).list();
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
	public List<IwmpGramPanchayat> getGramPanchyatList(Integer block) {
		
		List<IwmpGramPanchayat> result=new ArrayList<IwmpGramPanchayat>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=gpklist;
			result = ses.createQuery(hql).setParameter("blkcd", block).list();
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
	public List<NodalOfficerBean> getRoutePlanReportData(Integer State, Integer district, Integer block,
			Integer grampan) {
		
		String getReport=getRoutePlanVanTravelwatershed;
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",State); 
				query.setInteger("distcd",district); 
				query.setInteger("blkcd",block); 
				query.setInteger("gpkcd",grampan); 
				query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
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
	public List<NodalOfficerBean> getNodalOfficerReportData(String lvl, Integer State, Integer district,
			Integer block) {
		
		String getReport=getNodalOfficerwatershedAllLevel;
		String getReport1=getNodalOfficerwatershedStateLevel;
		String getReport2=getNodalOfficerwatersheddistrictLevel;
		String getReport3=getNodalOfficerwatershedblockLevel;
		String getReport4=getNodalOfficerwatershedvillLevel;
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		Query query=null;
		try {
			
				session.beginTransaction();
				if(lvl.equals("a") && State==0) {
				query= session.createSQLQuery(getReport);
				query.setInteger("statecd",State); 
				}
				if(lvl.equals("a") && State>0) {
					query= session.createSQLQuery(getReport);
					query.setInteger("statecd",State); 
				}
				if(lvl.equals("state")) {
					query= session.createSQLQuery(getReport1);
					query.setInteger("statecd",State); 
				}
				if(lvl.equals("district")) {
					query= session.createSQLQuery(getReport2);
					query.setInteger("statecd",State); 
					query.setInteger("distcd",district); 
				}
				if(lvl.equals("block")) {
					query= session.createSQLQuery(getReport3);
					query.setInteger("statecd",State); 
					query.setInteger("distcd",district); 
					query.setInteger("blkcd",block);
				}
				if(lvl.equals("village")) {
					query= session.createSQLQuery(getReport4);
					query.setInteger("statecd",State); 
					query.setInteger("distcd",district); 
					query.setInteger("blkcd",block);
				}
				query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
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
	public List<InaugurationBean> getInaugurationReportData(Integer State, Integer district, Integer block, String userdate, String userdateto) {
		String getReport=getInaugurationData;
		String getReport2=getInaugurationDatatodate;
		String getReport1=getInaugurationDatafromdate;
		Session session = sessionFactory.getCurrentSession();
		List<InaugurationBean> list = new ArrayList<InaugurationBean>();
		try {
			Query query=null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date inaugurationDate = null;
			Date inaugurationDate1 = null;
			
			if(!userdate.equals("")) 
				inaugurationDate = formatter.parse(userdate);
			
			if(!userdateto.equals("")) 
				inaugurationDate1 = formatter.parse(userdateto);
			
				session.beginTransaction();
				
				if(userdate.equals("") && userdateto.equals("")) 
				{
					query= session.createSQLQuery(getReport);
					query.setInteger("statecd",State); 
					query.setInteger("distcd",district); 
					query.setInteger("blkcd",block);
					query.setResultTransformer(Transformers.aliasToBean(InaugurationBean.class));
					list = query.list();
				}
				if( !userdate.equals("") && !userdateto.equals("")) {
					query= session.createSQLQuery(getReport2);
					query.setInteger("statecd",State); 
					query.setInteger("distcd",district); 
					query.setInteger("blkcd",block);
					query.setDate("userdate",inaugurationDate);
					query.setDate("userdate1",inaugurationDate1);
					query.setResultTransformer(Transformers.aliasToBean(InaugurationBean.class));
					list = query.list();
				}
				if(!userdate.equals("")  &&  userdateto.equals("")) {
					query= session.createSQLQuery(getReport1);
					query.setInteger("statecd",State); 
					query.setInteger("distcd",district); 
					query.setInteger("blkcd",block);
					query.setDate("userdate",inaugurationDate);
					query.setResultTransformer(Transformers.aliasToBean(InaugurationBean.class));
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
	public List<PreYatraPreparationBean> getPreYatraPreparationReportData(Integer State, Integer district,
			Integer block, Integer grampan) 
	{
		String getReport=getPreYatraPrepReportData;
		Session session = sessionFactory.getCurrentSession();
		List<PreYatraPreparationBean> list = new ArrayList<PreYatraPreparationBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",State); 
				query.setInteger("distcd",district); 
				query.setInteger("blkcd",block); 
				query.setInteger("gpkcd",grampan); 
				query.setResultTransformer(Transformers.aliasToBean(PreYatraPreparationBean.class));
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
