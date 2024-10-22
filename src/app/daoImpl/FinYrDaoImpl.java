package app.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import app.bean.FinYearBean;
import app.bean.ProjectPrepareBean;
import app.dao.FinYrDao;
import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.master.IwmpMProjectPrepare;
import app.model.project.IwmpProjectPrepare;

@Repository("FinYrDao")
public class FinYrDaoImpl implements FinYrDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getFinyrMaster}")
	String getFinyrMaster;
	
	@Value("${finYearDetail}")
	String finYearDetail;
	
	@Value("${isCurrentFinYearExist}")
	String isCurrentFinYearExist;
	
	@Value("${nextFinYear}") 
	String nextFinYear;
	
	@Value("${maxfinyrid}") 
	String maxfinyrid;

	@Value("${finListByFinCode}") 
	String finListByFinCode;
	
	@Value("${getFinyrMaster}") 
	String allFinyearQuery;
	
	@Override
	public List<FinYearBean> getFinYrMaster() {
		
		List<FinYearBean> result=new ArrayList<FinYearBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql=null;
		//	Query query = null;
			SQLQuery query = null;
			hql=finYearDetail;
		//	hql = getFinyrMaster;
			session.getTransaction().begin();
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(FinYearBean.class));
		//	query = session.createQuery(hql);
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}
	
	

	@Override
	public boolean isCurrentFinYearExist() {
		
			Session session = sessionFactory.getCurrentSession();
			List result=new ArrayList();
	    	boolean flag=true;
	    	try
	    	{
	    		String hql=null;
				SQLQuery query = null;
				hql = isCurrentFinYearExist;
				session.getTransaction().begin();
				query = session.createSQLQuery(hql);
				result= query.list();
				
				for(int i=0;i<result.size();i++) 
				{
					result.get(i);
					if(result.get(i).toString().equalsIgnoreCase("0"))
					{
	    				flag=false;
	    			}
					//System.out.println(result.get(i)+"= kedar"); 
				}
	    	}
	    	catch(Exception ex)
	    	{
	    		session.getTransaction().rollback();
	    		ex.printStackTrace();
	    	}
	    	finally{
	    		session.getTransaction().commit();
	    	}
	  		return flag;
	  	 
	}

	@Override
	public Map<String, String> getNextFinYearList() 
	{
		Session session = sessionFactory.getCurrentSession();
		List<String> result=new ArrayList<String>();
		Map<String, String> mrs =new HashMap<String, String>();
		String fin;
		
    	try{
    		
    		String hql=null;
			SQLQuery query = null;
			hql = nextFinYear;
			session.getTransaction().begin();
			query = session.createSQLQuery(hql);
			List<Object[]> rows = query.list();
			for(Object[] row : rows)
			{
				 String[] strArray = Arrays.stream(row).toArray(String[]::new);
				 result = Arrays.asList(strArray);
			}
			fin=result.get(0).split("-")[0]+"-"+result.get(0).split("-")[1].substring(2);
			mrs.put(result.get(1), fin);
			/*	for(String str : result) 
				{ 
				  	System.out.println("Result: "+str);
				}  */
    	}
    	catch(Exception ex)
    	{
    		session.getTransaction().rollback();
    		ex.printStackTrace();
    	}
    	finally{
    		
    		session.getTransaction().commit();
    	}
		return mrs;
	}



	@Override
	public Integer saveFinancialYear(String finYearDesc, HttpSession session, String finYearstatus, String startFrom,
			String endTo) {
		
		Session sessionf = sessionFactory.openSession();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		int p=0;
		try {
				sessionf.getTransaction().begin();
				char ch = finYearstatus.charAt(0);
				java.util.Date startD=new java.util.Date();
		    	java.util.Date endD=new java.util.Date();
		    	
		    	startD=formatter.parse(startFrom);
		    	endD=formatter.parse(endTo);
		    	
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				   
				IwmpMFinYear fy = new IwmpMFinYear();
				
				fy.setActFlag(ch);
				fy.setCreatedBy(session.getAttribute("loginID").toString());
				fy.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
				fy.setLastUpdatedBy(session.getAttribute("loginID").toString());
				fy.setLastUpdatedDate(new Timestamp(new java.util.Date().getTime()));
				fy.setEndTo(endD);
				fy.setStartFrom(startD);
				fy.setFinYrCd(maxfinyrcd());
				fy.setFinYrDesc(finYearDesc);
				fy.setRequestIp(ipAddr);
				
				sessionf.save(fy);
				p=1;
				
		}
		catch(Exception ex)
    	{
			sessionf.getTransaction().rollback();
    		ex.printStackTrace();
    	}
    	finally{
    		
    		sessionf.getTransaction().commit();
    	}
		return p;
	}



	@Override
	public Integer maxfinyrcd() {
		Integer result=0;
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.getTransaction().begin();
			String hql=maxfinyrid;
			List list = ses.createQuery(hql).list();
		
			result=Integer.parseInt(list.get(0).toString());
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
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
	public LinkedHashMap<Integer, String> getfinYearByFinCode(Integer toYear) {
		List<IwmpMFinYear> finList=new ArrayList<IwmpMFinYear>();
		String hql=finListByFinCode;
		LinkedHashMap<Integer, String> finMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("toYear", Long.parseLong(Integer.toString(toYear)));
			finList = query.list();
			for (IwmpMFinYear finYear : finList) {
				finMap.put(finYear.getFinYrCd(), finYear.getFinYrDesc());
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
        return finMap;
	}



	@Override
	public LinkedHashMap<Integer, String> getAllFinYear() {
		// TODO Auto-generated method stub
		List<IwmpMFinYear> yearList=new ArrayList<IwmpMFinYear>();
		String hql=allFinyearQuery;
		LinkedHashMap<Integer, String> yearMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				yearList = query.list();
				for (IwmpMFinYear year : yearList) 
				{
					yearMap.put(year.getFinYrCd(), year.getFinYrDesc());
				}
				session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
        return yearMap;
	}

	

}
