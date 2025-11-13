package app.daoImpl.reports;

import java.sql.Date;
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

import app.PfmsTreasureBean;
import app.TargetAchievementQuarterBean;
import app.bean.PrayasAchievementBean;
import app.bean.reports.QuarterlyTargetBean;
import app.dao.reports.TargetAchievementQuarterDao;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;

@Repository("TargetAchievementQuarterDao")
public class TargetAchievementQuarterDaoImpl implements TargetAchievementQuarterDao{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	@Value("${firstQuarterStartingYear}") 
	String firstQuarterStartingYear;
	
	/*
	 * @Value("${firstQuarterStartingNextYear}") String
	 * firstQuarterStartingNextYear;
	 */
	
	@Value("${secondQuarterStartingYear}") 
	String secondQuarterStartingYear;
	
	@Value("${thirdQuarterStartingYear}") 
	String thirdQuarterStartingYear;
	
	@Value("${fourQuarterStartingYear}") 
	String fourQuarterStartingYear;
	
	@Value("${allQuarterofYearData}") 
	String allQuarterofYearData;
	
	@Value("${firstQuarterofAboveYear}") 
	String firstQuarterofAboveYear;
	
	@Value("${secondQuarterofAboveYear}") 
	String secondQuarterofAboveYear;
	
	@Value("${thirdQuarterofAboveYear}") 
	String thirdQuarterofAboveYear;
	
	@Value("${fourQuarterofAboveYear}") 
	String fourQuarterofAboveYear;
	
	@Value("${allQuarterofAboveYearData}") 
	String allQuarterofAboveYearData;
	
	@Value("${firstQuarterDistWiseStartingYear}") 
	String firstQuarterDistWiseStartingYear;
	
	@Value("${allQuarterAllYearData}") 
	String allQuarterAllYearData ;
	
	
	@Value("${secondQuarterDistWiseStartingYear}") 
	String secondQuarterDistWiseStartingYear;
	
	@Value("${thirdQuarterDistWiseStartingYear}") 
	String thirdQuarterDistWiseStartingYear;
	
	@Value("${fourQuarterDistWiseStartingYear}") 
	String fourQuarterDistWiseStartingYear;
	
	@Value("${allQuarterDistWiseYearData}") 
	String allQuarterDistWiseYearData;
	
	@Value("${firstQuarterDistWiseAboveYear}") 
	String firstQuarterDistWiseAboveYear;
	
	@Value("${secondQuarterDistWiseAboveYear}") 
	String secondQuarterDistWiseAboveYear;
	
	@Value("${thirdQuarterDistWiseAboveYear}") 
	String thirdQuarterDistWiseAboveYear;
	
	@Value("${fourQuarterDistWiseAboveYear}") 
	String fourQuarterDistWiseAboveYear;
	
	@Value("${allQuarterDistWiseAboveYearData}") 
	String allQuarterDistWiseAboveYearData;
	
	@Value("${statefinyrWiseExpenditure}") 
	String statefinyrWiseExpenditure;
	
	@Value("${statewisepfmscomponent}") 
	String statewisepfmscomponent;
	
	@Value("${statewisepfmsNotcomponent}") 
	String statewisepfmsNotcomponent;
	
	@Value("${getstatemonthachdata}") 
	String getstatemonthachdata;
	
	@Value("${getdistmonthachdata}") 
	String getdistmonthachdata;
	
	@Value("${getprojmonthachdata}") 
	String getprojmonthachdata;
	
	@Value("${getquarttargetdata}") 
	String getquarttargetdata;
	
	@Value("${firstQuarterProjectWiseStartingYear}") 
	String firstQuarterProjectWiseStartingYear;
	
	@Value("${secondQuarterProjectWiseStartingYear}") 
	String secondQuarterProjectWiseStartingYear;
	
	@Value("${thirdQuarterProjectWiseStartingYear}") 
	String thirdQuarterProjectWiseStartingYear;
	
	@Value("${fourQuarterProjectWiseStartingYear}") 
	String fourQuarterProjectWiseStartingYear;
	
	@Value("${allQuarterProjectWiseYearData}") 
	String allQuarterProjectWiseYearData;
	
	@Value("${firstQuarterProjectWiseAboveYear}") 
	String firstQuarterProjectWiseAboveYear;
	
	@Value("${secondQuarterProjectWiseAboveYear}") 
	String secondQuarterProjectWiseAboveYear;
	
	@Value("${thirdQuarterProjectWiseAboveYear}") 
	String thirdQuarterProjectWiseAboveYear;
	
	@Value("${fourQuarterProjectWiseAboveYear}") 
	String fourQuarterProjectWiseAboveYear;
	
	@Value("${allQuarterProjectWiseAboveYearData}") 
	String allQuarterProjectWiseAboveYearData;
	
	@Override
	public List<IwmpMFinYear> getFinancialYearonward21() {
		
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMFinYear> temp = new ArrayList<IwmpMFinYear>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from IwmpMFinYear where finYrCd >21 order by finYrCd").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public List<TargetAchievementQuarterBean> getQuarterReport(Integer state, Integer year, Integer qtr) {
		// TODO Auto-generated method stub
		List<TargetAchievementQuarterBean> result=new ArrayList<TargetAchievementQuarterBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  if(year >24) {
				  if(qtr==1) 
				  	{
				  		String qtr1="20"+year+"-04-01";
						String qtr2="20"+year+"-07-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(qtr2);
						hql=firstQuarterofAboveYear;
						query = session.createSQLQuery(hql);
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
						query.setDate("quarter1", date2);
				  	}	
					if(qtr==2) {
						String qtr1= "20"+year+"-07-01";
						String qtr2= "20"+year+"-10-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(qtr2);
						hql=secondQuarterofAboveYear; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
						query.setDate("quarter2", date2);
					}
					if(qtr==3) {
						String qtr1= "20"+year+"-10-01";
						String qtr2= "20"+(year+1)+"-01-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(qtr2);
						hql=thirdQuarterofAboveYear; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
						query.setDate("quarter3", date2);
					}
					if(qtr==4) {
						String qtr1= "20"+(year+1)+"-01-01";
						String qtr2= "20"+(year+1)+"-04-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(qtr2);
						hql=fourQuarterofAboveYear; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
						query.setDate("quarter4", date2);
					}
					if(year>0 && qtr==5) {
						hql=allQuarterofAboveYearData; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
					}
			  }else {
				  if(qtr==1) 
				  	{   int i=4, j=5, k=6;
				  		String qtr1="20"+year+"-04-01";
						String qtr2="20"+year+"-07-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(qtr2);
						hql=firstQuarterStartingYear;
						query = session.createSQLQuery(hql);
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
					//	query.setDate("quarter1", date1);
						query.setDate("quarter2", date2);
					//	query.setInteger("im", i);
					//	query.setInteger("jm", j);
					//	query.setInteger("km", k);
				  	}	
					/*
					 * if(qtr==1 && year > 22) {
					 * 
					 * int i=4, j=5, k=6; String qtr1= "20"+year+"-04-01"; String qtr2=
					 * "20"+year+"-07-01"; Date date1=Date.valueOf(qtr1); Date
					 * date2=Date.valueOf(qtr2); hql=firstQuarterStartingNextYear; query
					 * =session.createSQLQuery(hql); // query.setInteger("stcd", state);
					 * query.setInteger("finyr", year); query.setDate("quarter1", date1);
					 * query.setDate("quarter2", date2); // query.setInteger("im", i); //
					 * query.setInteger("jm", j); // query.setInteger("km", k);
					 * 
					 * }
					 */
					if(qtr==2) {
						int i=7, j=8, k=9;
						String qtr1= "20"+year+"-07-01";
						String qtr2= "20"+year+"-10-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(qtr2);
						hql=secondQuarterStartingYear; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
					//	query.setDate("quarter1", date1);
						query.setDate("quarter2", date2);
					//	query.setInteger("im", i);
					//	query.setInteger("jm", j);
					//	query.setInteger("km", k);
					}
					if(qtr==3) {
						int i=10, j=11, k=12;
						String qtr1= "20"+year+"-10-01";
						String qtr2= "20"+(year+1)+"-01-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(qtr2);
						hql=thirdQuarterStartingYear; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
					//	query.setDate("quarter1", date1);
						query.setDate("quarter2", date2);
					//	query.setInteger("im", i);
					//	query.setInteger("jm", j);
					//	query.setInteger("km", k);
					}
					if(qtr==4) {
						int i=1, j=2, k=3;
						String qtr1= "20"+(year+1)+"-01-01";
						String qtr2= "20"+(year+1)+"-04-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(qtr2);
						hql=fourQuarterStartingYear; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
					//	query.setDate("quarter1", date1);
						query.setDate("quarter2", date2);
					//	query.setInteger("im", i);
					//	query.setInteger("jm", j);
					//	query.setInteger("km", k);
					}
					if(year>0 && qtr==5) {
						
						List list = session.createSQLQuery("select max(quarter) from insert_ommf_diversified_nilsingto_doubmulcrop where fin_yr_cd="+year).list();
						list.get(0).toString();
						
						String qtr1= "20"+year+"-04-01";
						//String qtr2= "20"+(year+1)+"-04-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(list.get(0).toString());
						hql=allQuarterofYearData; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
						query.setInteger("finyr", year);
						//query.setDate("quarter1", date1);
						//query.setDate("quarter2", date2);
					}
					if(year==0 && qtr==5) {
						
						List list = session.createSQLQuery("select max(quarter) from insert_ommf_diversified_nilsingto_doubmulcrop").list();
						list.get(0).toString();
						
						String qtr1= "2022-04-01";
						//String qtr2= "2024-01-01";
						Date date1=Date.valueOf(qtr1);
						Date date2=Date.valueOf(list.get(0).toString());
						hql=allQuarterAllYearData; 
						query =session.createSQLQuery(hql); 
						query.setInteger("stcd", state);
					//	query.setInteger("finyr", year);
					//	query.setDate("quarter1", date1);
					//	query.setDate("quarter2", date2);
						
					}
			  }
			  	
				
				query.setResultTransformer(Transformers.aliasToBean(TargetAchievementQuarterBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			
		}
		return result;
	}

	@Override
	public List<PfmsTreasureBean> getStateExpenditureReport(Integer year) {
		
		List<PfmsTreasureBean> result=new ArrayList<PfmsTreasureBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  String finyrpf="20"+year;
			  String finyr="20"+(year+1);
			  String financialyr="20"+year+"-"+finyr;
				hql=statefinyrWiseExpenditure;
				query = session.createSQLQuery(hql);
				query.setInteger("finyr", Integer.parseInt(finyr));
				query.setString("financialyr", financialyr);
				query.setResultTransformer(Transformers.aliasToBean(PfmsTreasureBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			session.getTransaction().rollback();
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			
		}
		return result;
	}

	@Override
	public List<PfmsTreasureBean> getStateWisePFMSComponent(Integer year) {
		
		List<PfmsTreasureBean> result=new ArrayList<PfmsTreasureBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  String finyr="20"+(year+1);
			 
				hql=statewisepfmscomponent;
				query = session.createSQLQuery(hql);
				query.setInteger("finyr", Integer.parseInt(finyr));
				query.setResultTransformer(Transformers.aliasToBean(PfmsTreasureBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			session.getTransaction().rollback();
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			
		}
		return result;
	}

	@Override
	public List<PfmsTreasureBean> getStateWisePFMSNotComponent(Integer year) {
		// TODO Auto-generated method stub
		List<PfmsTreasureBean> result=new ArrayList<PfmsTreasureBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  String finyr="20"+(year+1);
			 
				hql=statewisepfmsNotcomponent;
				query = session.createSQLQuery(hql);
				query.setInteger("finyr", Integer.parseInt(finyr));
				query.setResultTransformer(Transformers.aliasToBean(PfmsTreasureBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			session.getTransaction().rollback();
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			
		}
		return result;
	}

	@Override
	public List<PrayasAchievementBean> finddistmonthachdata(Integer stcode, Integer finCode, Integer month) {
		List<PrayasAchievementBean> result=new ArrayList<PrayasAchievementBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			       hql=getstatemonthachdata;
				   query = session.createSQLQuery(hql);
				   query.setInteger("stcode", stcode);
				   query.setInteger("finCode", finCode);
				   query.setInteger("month", month);
		           query.setResultTransformer(Transformers.aliasToBean(PrayasAchievementBean.class));
		           result = query.list();
} 
catch (HibernateException e) 
{
	System.err.print("Hibernate error");
	e.printStackTrace();
} 
finally {
	session.getTransaction().commit();
	
}
return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMFinYear> getYearonward22() {
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMFinYear> temp = new ArrayList<IwmpMFinYear>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from IwmpMFinYear where finYrCd > 21 order by finYrCd").list();
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public List<PrayasAchievementBean> finddistWisemonthachdata(Integer stCode, Integer finCode, Integer month) {
		List<PrayasAchievementBean> result=new ArrayList<PrayasAchievementBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			       hql=getdistmonthachdata;
				   query = session.createSQLQuery(hql);
				   query.setInteger("stCode", stCode);
				   query.setInteger("finCode", finCode);
				   query.setInteger("month", month);
		           query.setResultTransformer(Transformers.aliasToBean(PrayasAchievementBean.class));
		           result = query.list();
} 
catch (HibernateException e) 
{
	System.err.print("Hibernate error");
	e.printStackTrace();
} 
finally {
	session.getTransaction().commit();
	
}
return result;

}

	@Override
	public List<TargetAchievementQuarterBean> getDistWiseQuarterReport(Integer state, Integer year, Integer qtr) {
		// TODO Auto-generated method stub
		List<TargetAchievementQuarterBean> result=new ArrayList<TargetAchievementQuarterBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			 
			  	if(qtr==1) 
			  	{   
			  		int i=4, j=5, k=6;
			  		String qtr1="20"+year+"-04-01";
			  		String qtr2="20"+year+"-07-01";
			  		Date date1=Date.valueOf(qtr1);
			  		Date date2=Date.valueOf(qtr2);
			  		hql=year>24?firstQuarterDistWiseAboveYear:firstQuarterDistWiseStartingYear;
			  		query = session.createSQLQuery(hql);
			  		query.setInteger("stcd", state);
			  		query.setInteger("finyr", year);
			  	//	query.setDate("quarter1", date1);
			  		query.setDate("quarter2", date2);
			//		query.setInteger("im", i);
			//		query.setInteger("jm", j);
			//		query.setInteger("km", k);
			  	}	
				/*
				 * if(qtr==1 && year > 22) {
				 * 
				 * int i=4, j=5, k=6; String qtr1= "20"+year+"-04-01"; String qtr2=
				 * "20"+year+"-07-01"; Date date1=Date.valueOf(qtr1); Date
				 * date2=Date.valueOf(qtr2); hql=firstQuarterStartingNextYear; query
				 * =session.createSQLQuery(hql); // query.setInteger("stcd", state);
				 * query.setInteger("finyr", year); query.setDate("quarter1", date1);
				 * query.setDate("quarter2", date2); // query.setInteger("im", i); //
				 * query.setInteger("jm", j); // query.setInteger("km", k);
				 * 
				 * }
				 */
				if(qtr==2) 
				{
					int i=7, j=8, k=9;
					String qtr1= "20"+year+"-07-01";
					String qtr2= "20"+year+"-10-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(qtr2);
					hql=year>24?secondQuarterDistWiseAboveYear:secondQuarterDistWiseStartingYear; 
					query =session.createSQLQuery(hql); 
					query.setInteger("stcd", state);
					query.setInteger("finyr", year);
				//	query.setDate("quarter1", date1);
					query.setDate("quarter2", date2);
			//		query.setInteger("im", i);
			//		query.setInteger("jm", j);
			//		query.setInteger("km", k);
				}

				if(qtr==3) 
				{
					int i=10, j=11, k=12;
					String qtr1= "20"+year+"-10-01";
					String qtr2= "20"+(year+1)+"-01-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(qtr2);
					hql=year>24?thirdQuarterDistWiseAboveYear:thirdQuarterDistWiseStartingYear; 
					query =session.createSQLQuery(hql); 
					query.setInteger("stcd", state);
					query.setInteger("finyr", year);
				//	query.setDate("quarter1", date1);
					query.setDate("quarter2", date2);
			//		query.setInteger("im", i);
			//		query.setInteger("jm", j);
			//		query.setInteger("km", k);
				}

				if(qtr==4) 
				{
					int i=1, j=2, k=3;
					String qtr1= "20"+(year+1)+"-01-01";
					String qtr2= "20"+(year+1)+"-04-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(qtr2);
					hql=year>24?fourQuarterDistWiseAboveYear:fourQuarterDistWiseStartingYear; 
					query =session.createSQLQuery(hql); 
					query.setInteger("stcd", state);
					query.setInteger("finyr", year);
				//	query.setDate("quarter1", date1);
					query.setDate("quarter2", date2);
			//		query.setInteger("im", i);
			//		query.setInteger("jm", j);
			//		query.setInteger("km", k);allQuarterDistWiseYearData
				}
				
				if(year>0 && qtr==5) 
				{
					List list = session.createSQLQuery("select max(quarter) from insert_ommf_diversified_nilsingto_doubmulcrop_district where fin_yr_cd="+year).list();
					list.get(0).toString();
					
					String qtr1= "20"+year+"-04-01";
					//String qtr2= "20"+(year+1)+"-04-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(list.get(0).toString());
					hql=year>24?allQuarterDistWiseAboveYearData:allQuarterDistWiseYearData; 
					query =session.createSQLQuery(hql); 
					query.setInteger("stcd", state);
					query.setInteger("finyr", year);
					//query.setDate("quarter1", date1);
					//query.setDate("quarter2", date2);
				}
				
				if(year==0 && qtr==5) 
				{
					List list = session.createSQLQuery("select max(quarter) from insert_ommf_diversified_nilsingto_doubmulcrop_district").list();
					list.get(0).toString();
					
					String qtr1= "2022-04-01";
					//String qtr2= "2024-01-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(list.get(0).toString());
					hql=allQuarterDistWiseYearData; 
					query =session.createSQLQuery(hql); 
					query.setInteger("stcd", state);
					query.setInteger("finyr", year);
					//query.setDate("quarter1", date1);
					//query.setDate("quarter2", date2);
				}
				
				query.setResultTransformer(Transformers.aliasToBean(TargetAchievementQuarterBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			
		}
		return result;
	}

	@Override
	public Integer getnoofStateProj(Integer state) {
		// TODO Auto-generated method stub
		Integer result=0;
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			List list = ses.createQuery("Select count(dcode) from IwmpDistrict where iwmpState.stCode=:statec and distproj=true").setInteger("statec", state).list();
		//	System.out.println(list.get(0));
			result=Integer.parseInt(list.get(0).toString());
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
	public List<QuarterlyTargetBean> fetchquartargetrpt(Integer userState, Integer year) {
	    List<QuarterlyTargetBean> result = new ArrayList<>();
	    Transaction tx = null; 
	    try (Session session = sessionFactory.getCurrentSession()) {
	        tx = session.beginTransaction(); 

	        String hql = getquarttargetdata;
	       
	        SQLQuery query;
	             query = session.createSQLQuery(hql);
	            query.setInteger("state", userState);
		        query.setInteger("finyear", year);
	       
	        
	        query.setResultTransformer(Transformers.aliasToBean(QuarterlyTargetBean.class));

	        result = query.list();

	        tx.commit(); 
	    } catch (HibernateException e) {
	        if (tx != null) {
	            tx.rollback(); 
	        }
	        System.err.println("Hibernate error");
	        e.printStackTrace();
	    } catch (Exception ex) {
	        if (tx != null) {
	            tx.rollback(); // Rollback on any other exceptions
	        }
	        ex.printStackTrace();
	    }
	    return result;
	}

	@Override
	public List<TargetAchievementQuarterBean> getProjectWiseQuarterReport(Integer dist, Integer year, Integer qtr) {
		
		List<TargetAchievementQuarterBean> result=new ArrayList<TargetAchievementQuarterBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			 
			  	if(qtr==1) 
			  	{   
			  		int i=4, j=5, k=6;
			  		String qtr1="20"+year+"-04-01";
			  		String qtr2="20"+year+"-07-01";
			  		Date date1=Date.valueOf(qtr1);
			  		Date date2=Date.valueOf(qtr2);
			  		hql=year>24?firstQuarterProjectWiseAboveYear:firstQuarterProjectWiseStartingYear;
			  		query = session.createSQLQuery(hql);
			  		query.setInteger("distcd", dist);
			  		query.setInteger("finyr", year);
			  	//	query.setDate("quarter1", date1);
			  		query.setDate("quarter2", date2);
			//		query.setInteger("im", i);
			//		query.setInteger("jm", j);
			//		query.setInteger("km", k);
			  	}	
				/*
				 * if(qtr==1 && year > 22) {
				 * 
				 * int i=4, j=5, k=6; String qtr1= "20"+year+"-04-01"; String qtr2=
				 * "20"+year+"-07-01"; Date date1=Date.valueOf(qtr1); Date
				 * date2=Date.valueOf(qtr2); hql=firstQuarterStartingNextYear; query
				 * =session.createSQLQuery(hql); // query.setInteger("stcd", state);
				 * query.setInteger("finyr", year); query.setDate("quarter1", date1);
				 * query.setDate("quarter2", date2); // query.setInteger("im", i); //
				 * query.setInteger("jm", j); // query.setInteger("km", k);
				 * 
				 * }
				 */
				if(qtr==2) 
				{
					int i=7, j=8, k=9;
					String qtr1= "20"+year+"-07-01";
					String qtr2= "20"+year+"-10-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(qtr2);
					hql=year>24?secondQuarterProjectWiseAboveYear:secondQuarterProjectWiseStartingYear; 
					query =session.createSQLQuery(hql); 
					query.setInteger("distcd", dist);
					query.setInteger("finyr", year);
				//	query.setDate("quarter1", date1);
					query.setDate("quarter2", date2);
			//		query.setInteger("im", i);
			//		query.setInteger("jm", j);
			//		query.setInteger("km", k);
				}

				if(qtr==3) 
				{
					int i=10, j=11, k=12;
					String qtr1= "20"+year+"-10-01";
					String qtr2= "20"+(year+1)+"-01-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(qtr2);
					hql=year>24?thirdQuarterProjectWiseAboveYear:thirdQuarterProjectWiseStartingYear; 
					query =session.createSQLQuery(hql); 
					query.setInteger("distcd", dist);
					query.setInteger("finyr", year);
				//	query.setDate("quarter1", date1);
					query.setDate("quarter2", date2);
			//		query.setInteger("im", i);
			//		query.setInteger("jm", j);
			//		query.setInteger("km", k);
				}

				if(qtr==4) 
				{
					int i=1, j=2, k=3;
					String qtr1= "20"+(year+1)+"-01-01";
					String qtr2= "20"+(year+1)+"-04-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(qtr2);
					hql=year>24?fourQuarterProjectWiseAboveYear:fourQuarterProjectWiseStartingYear; 
					query =session.createSQLQuery(hql); 
					query.setInteger("distcd", dist);
					query.setInteger("finyr", year);
				//	query.setDate("quarter1", date1);
					query.setDate("quarter2", date2);
			//		query.setInteger("im", i);
			//		query.setInteger("jm", j);
			//		query.setInteger("km", k);allQuarterDistWiseYearData
				}
				
				if(year>0 && qtr==5) 
				{
					List list = session.createSQLQuery("select max(quarter) from insert_ommf_diversified_nilsingto_doubmulcrop_project where fin_yr_cd="+year).list();
					list.get(0).toString();
					
					String qtr1= "20"+year+"-04-01";
					//String qtr2= "20"+(year+1)+"-04-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(list.get(0).toString());
					hql=year>24?allQuarterProjectWiseAboveYearData:allQuarterProjectWiseYearData; 
					query =session.createSQLQuery(hql); 
					query.setInteger("distcd", dist);
					query.setInteger("finyr", year);
					//query.setDate("quarter1", date1);
					//query.setDate("quarter2", date2);
				}
				
				if(year==0 && qtr==5) 
				{
					List list = session.createSQLQuery("select max(quarter) from insert_ommf_diversified_nilsingto_doubmulcrop_project").list();
					list.get(0).toString();
					
					String qtr1= "2022-04-01";
					//String qtr2= "2024-01-01";
					Date date1=Date.valueOf(qtr1);
					Date date2=Date.valueOf(list.get(0).toString());
					hql=allQuarterProjectWiseYearData; 
					query =session.createSQLQuery(hql); 
					query.setInteger("distcd", dist);
					query.setInteger("finyr", year);
					//query.setDate("quarter1", date1);
					//query.setDate("quarter2", date2);
				}
				
				query.setResultTransformer(Transformers.aliasToBean(TargetAchievementQuarterBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			
		}
		return result;
	}

	@Override
	public Integer getnoofDistrictProj(Integer dcode) {
		
		Integer result=0;
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			List list = ses.createSQLQuery("Select count(proj_id) from iwmp_m_project where dcode=:dcodec ").setInteger("dcodec", dcode).list();
			System.out.println(list.get(0).toString());
			result=Integer.parseInt(list.get(0).toString());
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
	public List<PrayasAchievementBean> findprojWisemonthachdata(Integer dcode, Integer finCode, Integer month) {
		List<PrayasAchievementBean> result = new ArrayList<PrayasAchievementBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql = null;
			SQLQuery query = null;

			@SuppressWarnings("unused")
			Transaction tx = session.beginTransaction();
			hql = getprojmonthachdata;
			query = session.createSQLQuery(hql);
			query.setInteger("dcode", dcode);
			query.setInteger("finCode", finCode);
			query.setInteger("month", month);
			query.setResultTransformer(Transformers.aliasToBean(PrayasAchievementBean.class));
			result = query.list();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} finally {
			session.getTransaction().commit();

		}
		return result;

	}
	
	

}
