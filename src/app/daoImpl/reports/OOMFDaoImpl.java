package app.daoImpl.reports;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.reports.OOMFCurrentStatusBean;
import app.dao.reports.OOMFDao;

@Repository("OOMFDao")
public class OOMFDaoImpl implements OOMFDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getOOMFCurrentStatusReport}")
	String getOOMFCurrentStatusReport;
	
	@Value("${getOOMFBeforePrayashData}")
	String getOOMFBeforePrayashData;
	
	@Value("${getDistOOMFBeforePrayashData}")
	String getDistOOMFBeforePrayashData;
	
	@Value("${getProjOOMFBeforePrayashData}")
	String getProjOOMFBeforePrayashData;

	@Override
	public List<OOMFCurrentStatusBean> getOOMFCurrentStatusReport() {
	
		List<OOMFCurrentStatusBean> result=new ArrayList<OOMFCurrentStatusBean>();
		Session session = sessionFactory.openSession();
		try {
				int monaddi=0;
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				
				List list = session.createSQLQuery("select min(fin_yr_cd) from iwmp_m_fin_year where achiev_status is null").list();
				int finyr= Integer.parseInt(list.get(0).toString());
				
				int currentMonth = LocalDate.now().getMonthValue();
				if(currentMonth>=4 && currentMonth<=9)
					monaddi=9;
				else
					monaddi=3;
				
				List list1 = session.createSQLQuery("SELECT min(month_id) FROM iwmp_m_month WHERE((fin_month_id >= 1 AND fin_month_id <= CASE WHEN EXTRACT(MONTH FROM NOW()) >= 4 THEN EXTRACT(MONTH FROM NOW()) - 3 ELSE EXTRACT(MONTH FROM NOW()) + 9 END)) and achiev_status is null").list();
				int month= Integer.parseInt(list1.get(0).toString());
				
				hql=getOOMFCurrentStatusReport;
				query = session.createSQLQuery(hql);
				query.setInteger("yr", finyr);
				query.setInteger("mnth", month);
				query.setInteger("mon", monaddi);
				query.setResultTransformer(Transformers.aliasToBean(OOMFCurrentStatusBean.class));
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
	public String getOOMFFinYear() {
		Session session = sessionFactory.openSession();
		String finanyr=null;
		try {
				int monaddi=0;
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				
				List list = session.createSQLQuery("select min(fin_yr_cd) from iwmp_m_fin_year where achiev_status is null").list();
				int finyr= Integer.parseInt(list.get(0).toString());
				
				List list1 = session.createSQLQuery("select fin_yr_desc from iwmp_m_fin_year where fin_yr_cd="+finyr).list();
				finanyr= list1.get(0).toString();
				
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
		return finanyr;
	}

	@Override
	public String getOOMFFinyearMonth() {
		
		Session session = sessionFactory.openSession();
		String monthname=null;
		try {
				int monaddi=0;
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				
				List list = session.createSQLQuery("SELECT min(month_id) FROM iwmp_m_month WHERE((fin_month_id >= 1 AND fin_month_id <= CASE WHEN EXTRACT(MONTH FROM NOW()) >= 4 THEN EXTRACT(MONTH FROM NOW()) - 3 ELSE EXTRACT(MONTH FROM NOW()) + 9 END)) and achiev_status is null").list();
				int month= Integer.parseInt(list.get(0).toString());
				
				List list1 = session.createSQLQuery("select month_name from iwmp_m_month where month_id="+month).list();
				monthname= list1.get(0).toString();
				
				
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
		return monthname;
	}

	@Override
	public List<OOMFCurrentStatusBean> getOOMFBeforePrayashData() {
		
		List<OOMFCurrentStatusBean> result=new ArrayList<OOMFCurrentStatusBean>();
		Session session = sessionFactory.openSession();
		try {
				int monaddi=0;
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				
				List list = session.createSQLQuery("select min(fin_yr_cd) from iwmp_m_fin_year where achiev_status is null").list();
				int finyr= Integer.parseInt(list.get(0).toString());
				
				List list1 = session.createSQLQuery("SELECT min(month_id) FROM iwmp_m_month WHERE((fin_month_id >= 1 AND fin_month_id <= CASE WHEN EXTRACT(MONTH FROM NOW()) >= 4 THEN EXTRACT(MONTH FROM NOW()) - 3 ELSE EXTRACT(MONTH FROM NOW()) + 9 END)) and achiev_status is null").list();
				int month= Integer.parseInt(list1.get(0).toString());
				
				hql=getOOMFBeforePrayashData;
				query = session.createSQLQuery(hql);
				query.setInteger("yr", finyr);
				query.setInteger("mnth", month);
				query.setResultTransformer(Transformers.aliasToBean(OOMFCurrentStatusBean.class));
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
	public List<OOMFCurrentStatusBean> getDistOOMFBeforePrayashData(Integer stcd) {
		
		List<OOMFCurrentStatusBean> result=new ArrayList<OOMFCurrentStatusBean>();
		Session session = sessionFactory.openSession();
		try {
				int monaddi=0;
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				
				List list = session.createSQLQuery("select min(fin_yr_cd) from iwmp_m_fin_year where achiev_status is null").list();
				int finyr= Integer.parseInt(list.get(0).toString());
				
				List list1 = session.createSQLQuery("SELECT min(month_id) FROM iwmp_m_month WHERE((fin_month_id >= 1 AND fin_month_id <= CASE WHEN EXTRACT(MONTH FROM NOW()) >= 4 THEN EXTRACT(MONTH FROM NOW()) - 3 ELSE EXTRACT(MONTH FROM NOW()) + 9 END)) and achiev_status is null").list();
				int month= Integer.parseInt(list1.get(0).toString());
				
				hql=getDistOOMFBeforePrayashData;
				query = session.createSQLQuery(hql);
				query.setInteger("yr", finyr);
				query.setInteger("mnth", month);
				query.setInteger("stcd", stcd);
				query.setResultTransformer(Transformers.aliasToBean(OOMFCurrentStatusBean.class));
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
	public List<OOMFCurrentStatusBean> getProjOOMFBeforePrayashData(Integer dcode) {
		
		List<OOMFCurrentStatusBean> result=new ArrayList<OOMFCurrentStatusBean>();
		Session session = sessionFactory.openSession();
		try {
				int monaddi=0;
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				
				List list = session.createSQLQuery("select min(fin_yr_cd) from iwmp_m_fin_year where achiev_status is null").list();
				int finyr= Integer.parseInt(list.get(0).toString());
				
				List list1 = session.createSQLQuery("SELECT min(month_id) FROM iwmp_m_month WHERE((fin_month_id >= 1 AND fin_month_id <= CASE WHEN EXTRACT(MONTH FROM NOW()) >= 4 THEN EXTRACT(MONTH FROM NOW()) - 3 ELSE EXTRACT(MONTH FROM NOW()) + 9 END)) and achiev_status is null").list();
				int month= Integer.parseInt(list1.get(0).toString());
				
				hql=getProjOOMFBeforePrayashData;
				query = session.createSQLQuery(hql);
				query.setInteger("yr", finyr);
				query.setInteger("mnth", month);
				query.setInteger("dcode", dcode);
				query.setResultTransformer(Transformers.aliasToBean(OOMFCurrentStatusBean.class));
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
	

}
