package app.daoImpl.outcome;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.AddOutcomeParaBean;
import app.bean.GroundWaterTableBean;
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.dao.outcomes.AdditionalBroughtFarmerCropAreaDao;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;
import app.model.Outcome2Data;
import app.model.WdcpmksyMQuadIndicators;
import app.model.master.WdcpmksyMOutcome;
import app.model.master.WdcpmksyMOutcomeDetail;
import app.model.project.WdcpmksyAdditionalBroughtFarmerCrop;

@Repository("AdditionalBroughtFarmerCropAreaDao")
public class AdditionalBroughtFarmerCropAreaDaoImpl implements AdditionalBroughtFarmerCropAreaDao{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getFinYearonward22}")
	String getFinYearonward22;
	
	@Value("${getAdditionalBroughtFarmerCropDraft}")
	String getAdditionalBroughtFarmerCropDraft;
	
	@Value("${getAdditionalBroughtFarmerCropComplete}")
	String getAdditionalBroughtFarmerCropComplete;
	
	@Value("${getAdditionalBroughtfyDraft}")
	String getAdditionalBroughtfyDraft;
	
	@Value("${getAdditionalBroughtMonyDraft}")
	String getAdditionalBroughtMonyDraft;
	
	@Value("${getAdditionalBroughtMonthComplt}")
	String getAdditionalBroughtMonthComplt;
	
	@Value("${getAdditionalBroughtYearComplt}")
	String getAdditionalBroughtYearComplt;
	
	@Value("${getAdditionalfineyear}")
	String getAdditionalfineyear;
	
	@Value("${getAdditionalfineyearmont23}")
	String getAdditionalfineyearmont23;
	
	@Value("${getAdditionalfineyearmontgt23}")
	String getAdditionalfineyearmontgt23;

	@Value("${stateWiseAdditionalAchParameter}")
	String stateWiseAdditionalAchParameter;
	
	@Value("${getnotyearlyProj}")
	String getnotyearlyProj;
	
	@Value("${getnothalfyearlyProj}")
	String getnothalfyearlyProj;
	
	@Override
	public LinkedHashMap<Integer, String> getFinYearonward22() {
		
		List<IwmpMFinYear> yearList=new ArrayList<IwmpMFinYear>();
		LinkedHashMap<Integer, String> yearMap = new LinkedHashMap<Integer, String>();
		String hql=getFinYearonward22;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				yearList = query.list();
				session.getTransaction().commit();
				for(IwmpMFinYear yr:yearList) 
				{
					yearMap.put(yr.getFinYrCd(), yr.getFinYrDesc());
				}
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return yearMap;
	}

	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropDraft(Integer projectId, Integer month, Integer year) 
	{
		String getReport=getAdditionalBroughtFarmerCropDraft;
		Session session = sessionFactory.getCurrentSession();
		List<AdditionalBroughtFarmerCropAreaBean> list = new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setInteger("projectId",projectId); 
			query.setInteger("month", month);
			query.setInteger("year", year);
			query.setResultTransformer(Transformers.aliasToBean(AdditionalBroughtFarmerCropAreaBean.class));
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
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropComplete(Integer projectId) 
	{
		
		String getReport=getAdditionalBroughtFarmerCropComplete;
		Session session = sessionFactory.getCurrentSession();
		List<AdditionalBroughtFarmerCropAreaBean> list = new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("projectId",projectId); 
				query.setResultTransformer(Transformers.aliasToBean(AdditionalBroughtFarmerCropAreaBean.class));
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
	public String saveAdditionalBroughtFarmerCropArea(Integer projId, Integer month, Integer year,
			BigDecimal diversified, BigDecimal chnagesingle, BigDecimal farmer, BigDecimal changecorp, Character status,
			String loginId, Integer additionalid, String atype) 
	{
		
		Session session = sessionFactory.getCurrentSession();
		WdcpmksyAdditionalBroughtFarmerCrop wdcdata = new WdcpmksyAdditionalBroughtFarmerCrop();
		String res="fail";
		try {
				session.getTransaction().begin();
			
				if(atype.equals("fy")) 
				{
					SQLQuery query = session.createSQLQuery("select additional_brought_id from wdcpmksy_additional_brought_farmer_crop  where proj_id=:proj and fin_yr_cd=:finyr and achiev_type='Year-Wise' and status='C'");
					query.setInteger("proj", projId); 
					query.setInteger("finyr", year); 
					query.list();
				    if(query.list().isEmpty()) 
				    {
						 if(additionalid > 0) 
						 { 
							 wdcdata = session.get(WdcpmksyAdditionalBroughtFarmerCrop.class, additionalid); 
						 }
						 IwmpMProject mproject = new IwmpMProject();
						 IwmpMFinYear myear = new IwmpMFinYear();
						 IwmpMMonth   mmonth = new IwmpMMonth();
						
						 InetAddress inet=InetAddress.getLocalHost();
						 String ipAddr=inet.getHostAddress();
					
						 mproject.setProjectId(projId);
						 wdcdata.setIwmpMProject(mproject);
						 myear.setFinYrCd(year);
						 wdcdata.setIwmpMFinYear(myear);
						 wdcdata.setAchievType("Year-Wise");
						 wdcdata.setStatus(status);
						 wdcdata.setCreatedBy(loginId);
						 wdcdata.setCreatedOn(new Date());
						 wdcdata.setRequestIp(ipAddr);
						 wdcdata.setFarmerIncome(farmer);
						 wdcdata.setChangeCorp(changecorp);
				  
						 session.saveOrUpdate(wdcdata);
						 res="success";
				    } 
				    else { 
				    	 res="fail"; 
				    } 
			}
			
			if(atype.equals("m")) 
			{
				SQLQuery query = session.createSQLQuery("select additional_brought_id from wdcpmksy_additional_brought_farmer_crop  where proj_id=:proj and fin_yr_cd=:finyr and month_id=:mont and status='C'");
				query.setInteger("proj", projId); 
				query.setInteger("finyr", year); 
				query.setInteger("mont", month); 
				query.list();
			    if(query.list().isEmpty()) 
			    {
					 if(additionalid > 0) 
					 { 
						 wdcdata = session.get(WdcpmksyAdditionalBroughtFarmerCrop.class, additionalid); 
					 }
					 IwmpMProject mproject = new IwmpMProject();
					 IwmpMFinYear myear = new IwmpMFinYear();
					 IwmpMMonth   mmonth = new IwmpMMonth();
					
					 InetAddress inet=InetAddress.getLocalHost();
					 String ipAddr=inet.getHostAddress();
					
					 mproject.setProjectId(projId);
					 wdcdata.setIwmpMProject(mproject);
					 myear.setFinYrCd(year);
					 wdcdata.setIwmpMFinYear(myear);
					 wdcdata.setAchievType("Month-Wise");
					 mmonth.setMonthId(month);
					 wdcdata.setIwmpMMonth(mmonth);
					 wdcdata.setDiversified(diversified);
					 wdcdata.setChnagesingle(chnagesingle);
					 wdcdata.setStatus(status);
					 wdcdata.setCreatedBy(loginId);
					 wdcdata.setCreatedOn(new Date());
					 wdcdata.setRequestIp(ipAddr);
					
					 session.saveOrUpdate(wdcdata);
					 res="success";
				} 
			    else { 
			    	res="fail"; 
			    } 
			}
			session.getTransaction().commit();
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			res="fail";
		}
		return res;
	}

	@Override
	public String completeAdditionalBroughtFarmerCropArea(Integer projId, Integer month, Integer year, Character status,
			String loginId, Integer additionalid) {
		
		Session session = sessionFactory.getCurrentSession();
		WdcpmksyAdditionalBroughtFarmerCrop wdcdata = new WdcpmksyAdditionalBroughtFarmerCrop();
		String res="fail";
		try {
				session.getTransaction().begin();
				if(additionalid > 0) 
				{ 
					 wdcdata = session.get(WdcpmksyAdditionalBroughtFarmerCrop.class, additionalid); 
				}
				IwmpMProject mproject = new IwmpMProject();
				IwmpMFinYear myear = new IwmpMFinYear();
				IwmpMMonth   mmonth = new IwmpMMonth();
				
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				
				wdcdata.setStatus(status);
				wdcdata.setUpdatedBy(loginId);
				wdcdata.setUpdatedOn(new Date());
				wdcdata.setRequestIp(ipAddr);
				session.saveOrUpdate(wdcdata);
				
				session.getTransaction().commit();
				res="success";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			res="fail";
		}
		return res;
	}

	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropAreaDraft(Integer project,
			String atline, Integer fyear, Integer month) {
		// TODO Auto-generated method stub
		List<AdditionalBroughtFarmerCropAreaBean> result=new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
			 // query = session.createSQLQuery(hql);    getGroundWaterTable
				if(atline.equals("fy")) 
				{
					hql=getAdditionalBroughtfyDraft;
					query = session.createSQLQuery(hql);
					query.setInteger("projectId", project);
					query.setInteger("year", fyear);
				}
				if(atline.equals("m")) 
				{
					hql=getAdditionalBroughtMonyDraft;
					query = session.createSQLQuery(hql);
					query.setInteger("projectId", project);
					query.setInteger("year", fyear);
					query.setInteger("month", month);
				}
				query.setResultTransformer(Transformers.aliasToBean(AdditionalBroughtFarmerCropAreaBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			 // session.flush(); session.close();
		}
		return result;
	}

	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtMonthComplt(Integer project, String atline,
			Integer fyear, Integer month) 
	{
		
		List<AdditionalBroughtFarmerCropAreaBean> result=new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		Session session = sessionFactory.openSession();
		try {
			  String hql=null;
			  SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getAdditionalBroughtMonthComplt;
			  query = session.createSQLQuery(hql);
			  query.setInteger("projectId", project);
			  query.setInteger("year", fyear);
			  query.setInteger("month", month);
			  query.setResultTransformer(Transformers.aliasToBean(AdditionalBroughtFarmerCropAreaBean.class));
			  result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			 //session.flush(); session.close();
		}
		return result;
	}

	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtYearComplt(Integer project, String atline, Integer fyear) 
	{
		List<AdditionalBroughtFarmerCropAreaBean> result=new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		Session session = sessionFactory.openSession();
		try {
			  String hql=null;
			  SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getAdditionalBroughtYearComplt;
			  query = session.createSQLQuery(hql);
			  query.setInteger("projectId", project);
			  query.setInteger("year", fyear);
			  query.setResultTransformer(Transformers.aliasToBean(AdditionalBroughtFarmerCropAreaBean.class));
			  result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
		}
		return result;
	}


	@Override
	public LinkedHashMap<Integer, String> getFinYearAdditional(Integer project, String atline) {
		
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<WdcpmksyOutcomeBean> rows = new ArrayList<WdcpmksyOutcomeBean>();
		
		String hql=null;
		try {
			 Session session = sessionFactory.getCurrentSession();
			 session.beginTransaction();
			 
			 if(atline.equals("fy")) 
			 {
				 hql=getAdditionalfineyear;
				 SQLQuery query = session.createSQLQuery(hql);
				 query.setInteger("projid", project);
				 query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
				 rows = query.list();
				 for(WdcpmksyOutcomeBean row : rows)
				 {
					 map.put(row.getFin_yr_cd(), row.getFinyear());
				 }
			 }
			 else {
				 
				 
			//	 hql=getAdditionalfineyearmont23;	
				 SQLQuery query = session.createSQLQuery("select fin_yr_cd, fin_yr_desc as finyear from wdcpmksy_additional_m_fin_year where  achiev_status is null order by fin_yr_cd");
			//	 query.setInteger("projid", project);
				 query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
				 rows = query.list();
				 for(WdcpmksyOutcomeBean row : rows)
				 {
					 map.put(row.getFin_yr_cd(), row.getFinyear());
				 }
				 
				 
				 
				 
				/* 
				 SQLQuery query1 = session.createSQLQuery("select fin_yr_cd from wdcpmksy_additional_brought_farmer_crop where proj_id=:proj and achiev_type='Month-Wise' and status ='C' and fin_yr_cd in(22,23) and month_id=3");
				 query1.setInteger("proj", project); 
				 query1.list();
				 if(query1.list().size()!=2) 
				 {
					 hql=getAdditionalfineyearmont23;	
					 SQLQuery query = session.createSQLQuery(hql);
					 query.setInteger("projid", project);
					 query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
					 rows = query.list();
					 for(WdcpmksyOutcomeBean row : rows)
					 {
						 map.put(row.getFin_yr_cd(), row.getFinyear());
					 }
				 }
				 else {
					 
					 hql=getAdditionalfineyearmontgt23;
					 SQLQuery query = session.createSQLQuery(hql);
					 query.setInteger("projid", project);
					 query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
					 rows = query.list();
					 for(WdcpmksyOutcomeBean row : rows)
					 {
						 map.put(row.getFin_yr_cd(), row.getFinyear());
					 }
				 }  */
			 }
			 session.getTransaction().commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
		
		}
		return map;
	}

	

	/*
	 * @Override public LinkedHashMap<String, Integer> getRemainYear(Integer
	 * project, String atline) {
	 * 
	 * List<IwmpMFinYear> yrList=new ArrayList<IwmpMFinYear>(); //String
	 * hql=districtListByStateCode; LinkedHashMap<String, Integer> distMap=new
	 * LinkedHashMap<String, Integer>(); Session session =
	 * sessionFactory.getCurrentSession(); try { session.beginTransaction(); Query
	 * query = session.createQuery(""); query.setInteger("projid", project);
	 * query.setString("atline", atline); yrList = query.list();
	 * 
	 * for (IwmpMFinYear yr : yrList) { distMap.put( yr.getFinYrDesc(),
	 * yr.getFinYrCd());
	 * 
	 * } session.getTransaction().commit(); } catch (HibernateException e) {
	 * System.err.print("Hibernate error"); e.printStackTrace();
	 * session.getTransaction().rollback(); } catch(Exception ex){
	 * session.getTransaction().rollback(); ex.printStackTrace(); } return distMap;
	 * }
	 */

	@Override
	public LinkedHashMap<Integer, List<AdditionalBroughtFarmerCropAreaBean>> getstateWiseAdditionalAchievement(Integer stateCode, Integer finyr) {
		LinkedHashMap<Integer, List<AdditionalBroughtFarmerCropAreaBean>> map = new LinkedHashMap<Integer, List<AdditionalBroughtFarmerCropAreaBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = stateWiseAdditionalAchParameter;
			query = session.createSQLQuery(hql);
			query.setInteger("stateCode", stateCode);
			query.setInteger("finyr", finyr);
			query.setResultTransformer(Transformers.aliasToBean(AdditionalBroughtFarmerCropAreaBean.class));
			List<AdditionalBroughtFarmerCropAreaBean> list = query.list();
			for (AdditionalBroughtFarmerCropAreaBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<AdditionalBroughtFarmerCropAreaBean> sublist = new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AdditionalBroughtFarmerCropAreaBean row : list){
					if (!map.containsKey(row.getDcode())) {
						sublist = new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
						sublist.add(row);
						map.put(row.getDcode(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getDcode(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		}
         catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getnotyearlyPara(Integer dcode, String type, Integer finyr) {
		List<AdditionalBroughtFarmerCropAreaBean> getnotyearlydata = new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = getnotyearlyProj;
			String hql1 = getnothalfyearlyProj;
			if(type.equals("year")) {
			query = session.createSQLQuery(hql);
			}
			if(type.equals("halfyear")) {
			query = session.createSQLQuery(hql1);
			}
			query.setInteger("dcode", dcode);
			query.setInteger("finyr", finyr);
			
			
			query.setResultTransformer(Transformers.aliasToBean(AdditionalBroughtFarmerCropAreaBean.class));
			getnotyearlydata = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().commit();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().commit();
		}
		return getnotyearlydata;
	}


}
