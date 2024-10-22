package app.daoImpl;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.AddOutcomeParaBean;
import app.bean.PhysicalHeaddataBean;
import app.dao.QuarterlyTargetDao;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.IwmpUserProjectMap;
import app.model.MQuarter;
import app.model.WdcpmksyMQuadIndicators;
import app.model.WdcpmksyQuadTarget;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpMProjectPrepare;
import app.model.master.SlnaStProfile;

@Repository("quarterlyTargetDao")
public class QuarterlyTargetDaoImpl implements QuarterlyTargetDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getMQuarter}")
	String getMQuarter;
	
	@Value("${getTargetdata}")
	String getTargetdata;
	
	@Value("${getTargetList}")
	String getTargetList;
	
	@Value("${getIndicatorData}")
	String getIndicatorData;
	
	@Value("${getTargetCompare}")
	String getTargetCompare;
	
	@Value("${getTargetdata1}")
	String getTargetdata1;
	
	@Value("${checkindicatos}")
	String checkindicatos;
	
	@Value("${getquarterlySLNAData}")
	String getquarterlySLNAData;
	
	@Value("${find1quarterlySLNAData}")
	String find1quarterlySLNAData;
	
	@Value("${find2quarterlySLNAData}")
	String find2quarterlySLNAData;
	
	@Value("${find3quarterlySLNAData}")
	String find3quarterlySLNAData;
	
	@Value("${find4quarterlySLNAData}")
	String find4quarterlySLNAData;
	
	@Value("${update1quarterlySLNAData}")
	String update1quarterlySLNAData;
	
	@Value("${update2quarterlySLNAData}")
	String update2quarterlySLNAData;
	
	@Value("${update3quarterlySLNAData}")
	String update3quarterlySLNAData;
	
	@Value("${update4quarterlySLNAData}")
	String update4quarterlySLNAData;
	
	@Value("${getSLNACompletedData}")
	String getSLNACompletedData;
	
	@Value("${getCompleteQuadData}")
	String getCompleteQuadData;
	
	
	@Override
	public LinkedHashMap<Integer, String> getQuarterList() {
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<MQuarter> rows = new ArrayList<MQuarter>();
		String hql=getMQuarter;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			rows = query.list();
			
			for(MQuarter quad : rows) {
				map.put(quad.getQuartCd(), quad.getQuartDesc());
			}
			
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return map;
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getTargetList(int projId, int year) {
		List<WdcpmksyMQuadIndicators> list = new ArrayList<WdcpmksyMQuadIndicators>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = getTargetdata;
			query = session.createSQLQuery(hql);
			query.setInteger("projId", projId);
			query.setInteger("year", year);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyMQuadIndicators.class));
			list = query.list();
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
		return list;
	}

	@Override
	public LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> getTargetData() {
		
		LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> map = new LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = getTargetList;
			query = session.createSQLQuery(hql);
			
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyMQuadIndicators.class));
			List<WdcpmksyMQuadIndicators> list = query.list();
			for (WdcpmksyMQuadIndicators row : list){
				//System.out.println("userId: "+row.getHeadcode());
			}
			List<WdcpmksyMQuadIndicators> sublist = new ArrayList<WdcpmksyMQuadIndicators>();
			if ((list != null) && (list.size() > 0)) {
				for (WdcpmksyMQuadIndicators row : list){
					if (!map.containsKey(row.getIndicators_id())) {
						sublist = new ArrayList<WdcpmksyMQuadIndicators>();
						sublist.add(row);
						map.put(row.getIndicators_id(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getIndicators_id(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			// e.printStackTrace();
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			// ex.printStackTrace();
		}finally {
			
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> getAnnualTargetData(Integer project,
			Integer financial) {
		LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> map = new LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			List<WdcpmksyMQuadIndicators> com= null;
			String compare = getTargetCompare;
			String hql = getTargetdata;
			String hql1  = getTargetdata1;
			
			com = session.createSQLQuery(compare).setInteger("projId", project).setInteger("year", financial).list();
			
			if (com.size() > 0)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("projId", project);
				query.setInteger("year", financial);
			}
			else {
			query = session.createSQLQuery(hql);
			query.setInteger("projId", project);
			query.setInteger("year", financial);
			}
			
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyMQuadIndicators.class));
			List<WdcpmksyMQuadIndicators> list = query.list();
			for (WdcpmksyMQuadIndicators row : list){
				//System.out.println("userId: "+row.getHeadcode());
			}
			List<WdcpmksyMQuadIndicators> sublist = new ArrayList<WdcpmksyMQuadIndicators>();
			if ((list != null) && (list.size() > 0)) {
				for (WdcpmksyMQuadIndicators row : list){
					if (!map.containsKey(row.getIndicators_id())) {
						sublist = new ArrayList<WdcpmksyMQuadIndicators>();
						sublist.add(row);
						map.put(row.getIndicators_id(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getIndicators_id(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			// e.printStackTrace();
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			// ex.printStackTrace();
		}finally {
			
		}
		return map;
	}

	@Override
	public Integer saveprojectpreparedness(int ProjId, Character status, int financial, String[] quarter1, String[] quarter2,
			String[] quarter3, String[] quarter4, String[] indicatorid, String q1s, String q2s, String q3s, String q4s, Integer stcode, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = checkindicatos;
		
		int p =0;
		try {
			session.beginTransaction();
			
			IwmpMProject proj = (IwmpMProject) session.get(IwmpMProject.class, ProjId);
			IwmpMFinYear finYear = (IwmpMFinYear) session.get(IwmpMFinYear.class, financial);
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
				for(int i =0; i<indicatorid.length; i++)
			{
				WdcpmksyQuadTarget target =(WdcpmksyQuadTarget) session.createQuery(hql).setParameter("ProjId", ProjId).setParameter("financial", financial).setParameter("indicatorid", Integer.parseInt(indicatorid[i])).uniqueResult(); 
				 if (target == null) 
				 { 
				  target=new WdcpmksyQuadTarget(); 
    			  target.setIwmpMProject(proj);
				  target.setIwmpMFinYear(finYear); 
				  }
				
					/*
					 * target.setIwmpMProject(proj); target.setIwmpMFinYear(finYear);
					 */
				target.setIndicatorId(Integer.parseInt(indicatorid[i]));
				if(!quarter1[i].isEmpty())
				{
					if(!q1s.equals("S")||status.equals('C')) {
                          if(!q1s.equals("C"))	
                          {
						target.setQ1status(status);
                          }
                          }
				}
				if(!quarter2[i].isEmpty())
				{
					if(!q2s.equals("S") ||status.equals('C')) {
						 if(!q2s.equals("C"))	
                         {
						target.setQ2status(status);
                         }
					}
				}
				if(!quarter3[i].isEmpty())
				{
					if(!q3s.equals("S") || status.equals('C')) {
						if(!q3s.equals("C")){
						target.setQ3status(status);
						}
					}
				}
				if(!quarter4[i].isEmpty())
				{
					if((!q4s.equals("S") || !q1s.equals("C")) || status.equals('C')) {
					target.setQ4status(status);
					}
				}
				target.setFirstQuad(Double.parseDouble(quarter1[i].isEmpty() ? "0" : quarter1[i]));
				target.setSecondQuad(Double.parseDouble(quarter2[i].isEmpty() ? "0" : quarter2[i]));
				target.setThirdQuad(Double.parseDouble(quarter3[i].isEmpty() ? "0" : quarter3[i]));
				target.setFourthQuad(Double.parseDouble(quarter4[i].isEmpty() ? "0" : quarter4[i]));
				target.setCreatedBy(loginId);
				target.setStcode(stcode);
				target.setStatus(status);
				target.setCreatedOn(new Date());
				target.setRequestIp(ipAddr);
				session.saveOrUpdate(target);
			   if(status == 'D')
			   {
				   p=1;
			   }
			   else
			   {
			   p=2;
			   }
			}
			session.getTransaction().commit();
		}
		
		catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			
		}
		return p;
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getindicatorsdata(Integer project, Integer financial) {
		List<WdcpmksyMQuadIndicators> list = new ArrayList<WdcpmksyMQuadIndicators>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = getIndicatorData;
			query = session.createSQLQuery(hql);
			query.setInteger("project", project);
			query.setInteger("financial", financial);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyMQuadIndicators.class));
			list = query.list();
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
		return list;
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getquartSLNAdata(Integer projId, Integer stcode) {
		List<WdcpmksyMQuadIndicators> getquartSLNAData = new ArrayList<WdcpmksyMQuadIndicators>();

		String hql = getquarterlySLNAData;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {

			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projId", projId);
			query.setInteger("stcode", stcode);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyMQuadIndicators.class));
			getquartSLNAData = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().commit();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().commit();
		}
		return getquartSLNAData;
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getsingleslnadata(String achievementdtl) {
		List<WdcpmksyMQuadIndicators> getsingleslnadata = new ArrayList<WdcpmksyMQuadIndicators>();
		String[] parts = achievementdtl.split("#");
		int projId = Integer.parseInt(parts[0]);
		int finCd = Integer.parseInt(parts[1]);
		String quarter = parts[2];
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String hql = find1quarterlySLNAData;
		String hql1 = find2quarterlySLNAData;
		String hql2 = find3quarterlySLNAData;
		String hql3 = find4quarterlySLNAData;
		SQLQuery query = null;
		try {

			
			if(quarter.equals("Quarter1"))
			{
				query = session.createSQLQuery(hql);
			}
			 if(quarter.equals("Quarter2"))
			{
				query = session.createSQLQuery(hql1);
			}
			 if(quarter.equals("Quarter3"))
			{
				query = session.createSQLQuery(hql2);
			}
			 if(quarter.equals("Quarter4"))
			{
				query = session.createSQLQuery(hql3);
			}
			 /*query = session.createSQLQuery(hql);*/
			query.setInteger("projId", projId);
			query.setInteger("finCd", finCd);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyMQuadIndicators.class));
			getsingleslnadata = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().commit();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().commit();
		}
		return getsingleslnadata;
	}

	@Override
	public String SLNAApproveService(String quardDtl) {
		String res="";
		int a=0;
		Session session = sessionFactory.getCurrentSession();
		String[] details =  quardDtl.split("#");
		String remarks = details[0];
		int projId = Integer.parseInt(details[1]);
		int finCd = Integer.parseInt(details[2]);
		String quarter = details[3];
		String status = details[4]; 
		session.beginTransaction();
		String updatequarter1 = update1quarterlySLNAData;
		String updatequarter2 = update2quarterlySLNAData;
		String updatequarter3 = update3quarterlySLNAData;
		String updatequarter4 = update4quarterlySLNAData;
    	
		SQLQuery query = null;
            try {

			
			if(quarter.equals("Quarter1"))
			{
				query = session.createSQLQuery(updatequarter1);
			}
			 if(quarter.equals("Quarter2"))
			{
				query = session.createSQLQuery(updatequarter2);
			}
			 if(quarter.equals("Quarter3"))
			{
				query = session.createSQLQuery(updatequarter3);
			}
			 if(quarter.equals("Quarter4"))
			{
				query = session.createSQLQuery(updatequarter4);
			}
			 /*query = session.createSQLQuery(hql);*/
			 query.setString("status", status);
			 query.setString("remarks", remarks);
			 query.setInteger("projId", projId);
			query.setInteger("finCd", finCd);
			a=query.executeUpdate();
			if(a>0)
			{
				res="success";
				session.getTransaction().commit();
			}
			else {
				session.getTransaction().rollback();
			}
			
}
            catch (HibernateException e) {
    			System.err.print("Hibernate error");
    			e.printStackTrace();
    			session.getTransaction().rollback();
    			res="fail";
    		} 
    		catch(Exception ex){
    			session.getTransaction().rollback();
    			ex.printStackTrace();
    			res="fail";
    		}
            return res;
	}

	@Override
	public String SLNAAllQuadApproveService(String quardDtl, String status) {
		String res="";
		int a=0;
		Session session = sessionFactory.getCurrentSession();
		String[] details = quardDtl.split(",");
		String updatequarter1 = update1quarterlySLNAData;
		String updatequarter2 = update2quarterlySLNAData;
		String updatequarter3 = update3quarterlySLNAData;
		String updatequarter4 = update4quarterlySLNAData;
		session.beginTransaction();
		SQLQuery query = null;
        try {
		for(String s:details) {
			String[] st = s.split("#");
			
			int projId = Integer.parseInt(st[0]);
			int finCd = Integer.parseInt(st[1]);
			String quarter = st[2];
			if(quarter.equals("Quarter1"))
			{
				query = session.createSQLQuery(updatequarter1);
			}
			 if(quarter.equals("Quarter2"))
			{
				query = session.createSQLQuery(updatequarter2);
			}
			 if(quarter.equals("Quarter3"))
			{
				query = session.createSQLQuery(updatequarter3);
			}
			 if(quarter.equals("Quarter4"))
			{
				query = session.createSQLQuery(updatequarter4);
			}
			 /*query = session.createSQLQuery(hql);*/
			 query.setString("status", status);
			 query.setString("remarks", "");
			 query.setInteger("projId", projId);
			query.setInteger("finCd", finCd);
			
			a=query.executeUpdate();
		}
        
		if(a>0)
		{
			res="success";
			session.getTransaction().commit();
		}
		else {
			session.getTransaction().rollback();
		}
		
}
        catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res="fail";
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
			res="fail";
		}
        return res;
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getSLNACompleteData(Integer stcode) {
		List<WdcpmksyMQuadIndicators> getquartSLNAData = new ArrayList<WdcpmksyMQuadIndicators>();

		String hql = getSLNACompletedData;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {

			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcode", stcode);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyMQuadIndicators.class));
			getquartSLNAData = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().commit();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().commit();
		}
		return getquartSLNAData;
	}

	@Override
	public List<WdcpmksyMQuadIndicators> getcompletedquaddata(String quarterdtl) {
		List<WdcpmksyMQuadIndicators> getcompletedquadsdata = new ArrayList<WdcpmksyMQuadIndicators>();
		String[] parts = quarterdtl.split("#");
		int projId = Integer.parseInt(parts[0]);
		int finCd = Integer.parseInt(parts[1]);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String hql = getCompleteQuadData;
		
		SQLQuery query = null;
		try {

			query = session.createSQLQuery(hql);
			query.setInteger("project", projId);
			query.setInteger("financial", finCd);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyMQuadIndicators.class));
			getcompletedquadsdata = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().commit();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().commit();
		}
		return getcompletedquadsdata;
	}

	

}


