package app.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import app.bean.HeadActivityUnitBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.bean.ProjectLocationBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.dao.PhysicalActionPlanDao;
import app.model.IwmpMAreaType;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpUserProjectMap;
import app.model.UserReg;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.master.IwmpMUnit;
import app.model.project.IwmpApprovelLevel;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalPlan;
import app.model.project.IwmpProjectPhysicalTranx;
@Repository("PhysicalActionPlanDao")
public class PhysicalActionPlanDaoImpl implements PhysicalActionPlanDao{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getFinYearProjectWise}")
	String getFinYearProjectWise;
	
	@Value("${getFinYearExistPlan}")
	String getFinYearExistPlan;
	
	@Value("${getHead}")
	String getHead;
	
	@Value("${getActivity}")
	String getActivity;
	
	@Value("${getUnit}")
	String getUnit;
	
	@Value("${saveAsDraftPhysicalAnnualActionPlan}")
	String saveAsDraftPhysicalAnnualActionPlan;
	
	@Value("${deleteActivityFromAnnualActionPlan}")
	String deleteActivityFromAnnualActionPlan;
	
	@Value("${getListofPhysicalActionPlan}")
	String getListofPhysicalActionPlan;
	
	@Value("${senttoslna}")
	String senttoslna;
	
	@Value("${sentback}")
	String sentback;
	
	@Value("${sentbacktopia}")
	String sentbacktopia;
	
	@Value("${getPlanMovementDetails}")
	String getPlanMovementDetails;

	
	@Value("${checkAlreadyExistActivity}")
	String checkAlreadyExistActivity;
	
	@Value("${getUserToForward}")
	String getUserToForward;
	
	@Value("${annual_action_plan_pia_forward}")
	String pia_forward;
	
	@Value("${checkForAlreadyForwardedPlan}")
	String checkForAlreadyForwardedPlan;
	
	@Value("${checkForAlreadyForwardedPlanByPlanId}")
	String checkForAlreadyForwardedPlanByPlanId;
	
	@Value("${getProjectByUserForAAP}")
	String getProjectByUser;
	
	@Value("${getProjectByStateForAAP}")
	String getProjectByStateForAAP;
	
	@Value("${viewAAPMovement}")
	String viewAAPMovement;
	
	@Value("${viewAAPCMovement}")
	String viewAAPCMovement;
	
	@Value("${viewAAPMovementForDISLNA}")
	String viewAAPMovementForDISLNA;
	
	@Value("${getPlanDetails}")
	String getPlanDetails;
	
	@Value("${getRemarks}")
	String getRemarks;
	
	@Value("${getProjectByDcodeForPhyActPlanReport}")
	String getProjectPhysicalActionPlan;
	
	@Value("${getFromYearForPhyActPlanReport}")
	String getFromYear;
	
	@Value("${getToYearForPhyActPlanReport}")
	String getToYear;
	
	@Value("${getPhysicalActionPlanReport}")
	String getPhysicalActionPlanReport;
	
	@Value("${finPhyTrgtRpt2}")
	String finPhyTrgtRpt;
	
	@Value("${finPhyTrgtRptAllProj}")
	String finPhyTrgtRptAllProj;
	
	@Value("${finPhyTrgtRptAllDist}")
	String finPhyTrgtRptAllDist;
	
	@Value("${finPhyTrgtRptAllState}")
	String finPhyTrgtRptAllState;
	
	@Value("${getHeadActivity}")
	String getHeadActivity;
	
	@Value("${getdistProject}")
	String getdistProject;
	
	@Value("${updatePhysicalAnnualActionPlan}")
	String updatePhysicalAnnualActionPlan;
	
	@Value("${getplanidData}")
	String getplanidData;
	
	@Value("${viewARMovement}")
	String viewARMovement;
	

	@Value("${getHeadActivityDesc}")
	String getHeadActivityDesc;

	@Value("${getListofPhysicalActionAchiev}")
	String getListofPhysicalActionAchiev;
	

	@Override
	public LinkedHashMap<Integer, String> getFinYearProjectWise(Integer projId) {
		// TODO Auto-generated method stub
		List<IwmpMFinYear> yearList=new ArrayList<IwmpMFinYear>();
		LinkedHashMap<Integer, String> yearMap = new LinkedHashMap<Integer, String>();
		String hql=getFinYearProjectWise;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			yearList = query.list();
			session.getTransaction().commit();
			for(IwmpMFinYear yr:yearList) {
				yearMap.put(yr.getFinYrCd(), yr.getFinYrDesc());
			}
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return yearMap;
	}

	@Override
	public LinkedHashMap<Integer, String> getHead() {
		// TODO Auto-generated method stub
		List<IwmpMPhyHeads> list = new ArrayList<IwmpMPhyHeads>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql=getHead;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			for(IwmpMPhyHeads head:list) {
				map.put(head.getHeadCode(),head.getHeadDesc());
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getActivity(Integer headId) {
		// TODO Auto-generated method stub
	//	List<IwmpMPhyActivity> list = new ArrayList<IwmpMPhyActivity>();
		
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql=getActivity;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("headId", headId);
			
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
			}
			
			/*
			 * list = query.list(); for(IwmpMPhyActivity head:list) {
			 * map.put(head.getActivityCode(),head.getActivityDesc()); }
			 */
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			//session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getUnit(Integer activityId) {
		// TODO Auto-generated method stub
		List<IwmpMUnit> list = new ArrayList<IwmpMUnit>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql=getUnit;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("activityId", activityId);
			list = query.list();
			for(IwmpMUnit head:list) {
				map.put(head.getUnitCode(),head.getUnitDesc());
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public String saveAsDraft(String plan,String activity,Integer projectcd,Integer yearcd,String loginid) {
		// TODO Auto-generated method stub
		String res="";
		String draftQuery=saveAsDraftPhysicalAnnualActionPlan;
		String getAlreadyData = checkAlreadyExistActivity;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			Query query= session.createQuery(getAlreadyData);
			query.setParameter("projectcd",	projectcd); 
			query.setParameter("yearcd", yearcd); 
			List<String> list = query.list();
			List<String> lsstr= new ArrayList<String>();

			for(Object a: list){
			   lsstr.add(String.valueOf(a));
			}
			List<String> actList = Arrays.asList(activity.split(","));
			if(!Collections.disjoint(lsstr, actList)) {
				res="activity exist";
			}else {
				
			
			SQLQuery sqlQuery =session.createSQLQuery(draftQuery);
			session.createSQLQuery(draftQuery);
			sqlQuery.setString("plan", plan);
			sqlQuery.setString("activity", activity);
			sqlQuery.setParameter("projectcd", projectcd);
			sqlQuery.setParameter("yearcd", yearcd);
			sqlQuery.setCharacter("assetid", 'Y');
			sqlQuery.setString("createdby", loginid);
			sqlQuery.setString("ipaddress", ipadd);
			res = sqlQuery.list().get(0).toString();
			if( res.equals("success")) {
				session.getTransaction().commit();
			}else {
				session.getTransaction().rollback();
			}
			}
			/*
			 * if(a>0) { session.getTransaction().commit(); res="success"; }else {
			 * session.getTransaction().rollback(); res="fail"; }
			 */
			
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
	public List<PhysicalActionPlanBean> getListofPhysicalActionPlan(Integer projectcd, Integer yearcd) {
		// TODO Auto-generated method stub
		List<PhysicalActionPlanBean> list = new ArrayList<PhysicalActionPlanBean>();
		String hql=getListofPhysicalActionPlan;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projectcd", projectcd);
			query.setInteger("yearcd", yearcd);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
			list = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public String deleteActivityFromAnnualActionPlan(Integer aapid) {
		// TODO Auto-generated method stub
		String res="";
		String deleteQuery=deleteActivityFromAnnualActionPlan;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			Query query= session.createQuery(deleteQuery);
			query.setParameter("aapid",	aapid); 
		Integer a=query.executeUpdate();
			if(a>0) {
				session.getTransaction().commit();
				res="success";
			}else {
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
	public List<PhysicalActionPlanBean> getUserToForward(Integer regId) {
		// TODO Auto-generated method stub
		String sql=getUserToForward;
		Session session = sessionFactory.getCurrentSession();
		List<PhysicalActionPlanBean> list = new ArrayList<PhysicalActionPlanBean>();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			SQLQuery query= session.createSQLQuery(sql);
			query.setParameter("regId",	regId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
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
	public String forward(Integer sentto, Integer yearcd, Integer projectcd,Integer sentfrom,String remarks) {
		// TODO Auto-generated method stub
		String res="";
		String sql=pia_forward;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Timestamp time= Timestamp.from(Instant.now());
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			SQLQuery query= session.createSQLQuery(sql);
			query.setTimestamp("senton", time);
			query.setInteger("sentto",	sentto); 
			query.setInteger("yearcd", yearcd);
			query.setInteger("projectcd", projectcd);
			query.setInteger("sentfrom", sentfrom);
			query.setString("remarks", remarks);
			res = query.list().get(0).toString();
			if( res.equals("success")) {
				session.getTransaction().commit();
			}else {
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
	public List<IwmpProjectPhysicalTranx> checkForAlreadyForwardedPlan(Integer yearcd, Integer projectcd,Integer regId) {
		// TODO Auto-generated method stub
		String res="";
		String deleteQuery=checkForAlreadyForwardedPlan;
		Session session = sessionFactory.getCurrentSession();
		List<IwmpProjectPhysicalTranx> list = new ArrayList<IwmpProjectPhysicalTranx>();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			Query query= session.createQuery(deleteQuery);
			query.setParameter("yearcd",yearcd); 
			query.setInteger("projectcd", projectcd);
			//query.setInteger("regId", regId);
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
	public List<IwmpProjectPhysicalTranx> checkForAlreadyForwardedPlan(Integer planid,Integer regId) {
		// TODO Auto-generated method stub
		String res="";
		String hql=checkForAlreadyForwardedPlanByPlanId;
		Session session = sessionFactory.getCurrentSession();
		List<IwmpProjectPhysicalTranx> list = new ArrayList<IwmpProjectPhysicalTranx>();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			Query query= session.createQuery(hql);
			query.setInteger("planid",planid); 
			query.setInteger("regId", regId);
			
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
	public LinkedHashMap<Integer, String> getProjectByRegId(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpUserProjectMap> rows = new ArrayList<IwmpUserProjectMap>();
		String hql=getProjectByUser;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId",registrationId);
			rows = query.list();
			  for(IwmpUserProjectMap row : rows){
				  System.out.println("Size by regId: "+row.getIwmpMProject().getProjectCd()+" regId "+regId);
				  map.put(row.getIwmpMProject().getProjectId(), row.getIwmpMProject().getProjName());
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
	public List<PhysicalActionPlanTranxBean> viewMovement(Integer regId,String userType) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		List<PhysicalActionPlanTranxBean> list = new ArrayList<PhysicalActionPlanTranxBean>();
		PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=null;
		if(userType.equals("DI") || userType.equals("SL"))
			hql=viewAAPMovement;
			if(userType.equals("PI"))
				hql=viewAAPMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regid",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanTranxBean.class));
			list = query.list();
			
			
			 
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public List<PhysicalActionPlanTranxBean> getPlanMovementDetails(Integer planid) {
		// TODO Auto-generated method stub
		
		List<PhysicalActionPlanTranxBean> list = new ArrayList<PhysicalActionPlanTranxBean>();
		PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=getPlanMovementDetails;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("planid",planid);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanTranxBean.class));
			list = query.list();
			
			
			 
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}
	
	@Override
	public List<IwmpProjectPhysicalAap> getPlanDetails(Integer planid) {
		// TODO Auto-generated method stub
		List<IwmpProjectPhysicalAap> list = new ArrayList<IwmpProjectPhysicalAap>();
		String hql=getPlanDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("planid",planid);
			list = query.list();
			
			
			 
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public String forwardByWCDC(Integer sentto, Integer sentfrom, Integer planid, Character action, String remarks,String userType) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String res=null;
		try {
			session.getTransaction().begin();
			Timestamp time= Timestamp.from(Instant.now());  
			//Date d = new Date();
		//	Timestamp time = new Timestamp(d.getTime());
			IwmpProjectPhysicalTranx tranx = new IwmpProjectPhysicalTranx();
			UserReg reg = new UserReg();
			reg.setRegId(sentfrom);
			tranx.setIwmpUserRegBySentfrom(reg);
			reg= new UserReg();
			reg.setRegId(sentto);
			tranx.setIwmpUserRegBySentTo(reg);
			IwmpProjectPhysicalPlan plan = (IwmpProjectPhysicalPlan) session.get(IwmpProjectPhysicalPlan.class, planid);
			tranx.setIwmpProjectPhysicalPlan(plan);
			if(action.toString().equals("F") && userType.equals("SL"))
			tranx.setAction('C');
			else
				tranx.setAction(action);
			tranx.setRemarks(remarks);
			tranx.setSenton(time);
			session.save(tranx);
			if(action.toString().equals("F") && userType.equals("SL")) {
			plan.setStatus('C');
			session.update(plan);
			}
			res="success";
			session.getTransaction().commit();
		}catch(Exception ex) {
			res="fail";
			session.getTransaction().rollback();
		}finally {
			
		}
		return res;
	}

	@Override
	public Integer getSentToByRegId(Integer regId, Character action,Integer planid,String userType) {
		// TODO Auto-generated method stub
		String hql=null;
		Integer sentto =null;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query =null;
			if(userType.equals("DI")) 
			{
				if(action=='F') 
				{
					//forward to slna     https://7starhd.app/
					hql=senttoslna;
					query = session.createSQLQuery(hql);
					query.setLong("regId",regId);
				}
				else if(action=='R') 
				{
				//backward to pia for correction
					hql=sentbacktopia;
					query = session.createSQLQuery(hql);
					query.setLong("planId",planid);
					query.setLong("regId", regId);
				}
			}
			if(userType.equals("SL")) 
			{
				if(action=='F') 
				{
					//approve data
					/*
					 * hql=approveData; query = session.createSQLQuery(hql);
					 * query.setLong("planId",planid);
					 */
					 sentto=regId;
				}
				else if(action=='R') 
				{
					//backward to pia/wcdc for correction
					hql=sentback;
					query = session.createSQLQuery(hql);
					query.setLong("planId",planid);
					query.setLong("regId", regId);
				}
			}
			if(userType.equals("PI")) 
			{
				if(action=='F') 
				{
					//forward to slna
					hql=senttoslna;
					query = session.createSQLQuery(hql);
					query.setLong("regId",regId);
					
				}
				else if(action=='R') 
				{}
			}
			if(sentto!=regId && query.list().size()>0)
			sentto = Integer.parseInt(query.list().get(0).toString());
			session.getTransaction().commit();
		}
		catch(Exception ex) 
		{
		    System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {}
		return sentto;
	}

	@Override
	public List<IwmpProjectPhysicalTranx> getRemarks(Integer projectcd, Integer yearcd,Integer regId) {
		// TODO Auto-generated method stub
		String deleteQuery=getRemarks;
		Session session = sessionFactory.getCurrentSession();
		List<IwmpProjectPhysicalTranx> list = new ArrayList<IwmpProjectPhysicalTranx>();
		try {
				session.beginTransaction();
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress(); 
				Query query= session.createQuery(deleteQuery);
				query.setParameter("yearcd",yearcd); 
				query.setInteger("projectcd", projectcd);
				query.setInteger("regId", regId);
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
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
	public LinkedHashMap<Integer, String> getProjectPhysicalActionPlan(Integer dCode) {
		// TODO Auto-generated method stub
		String getProject=getProjectPhysicalActionPlan;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getProject);
			query.setInteger("dCode",dCode); 
			List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  map.put(Integer.parseInt(row[1].toString()),row[0].toString());
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
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getFromYearForPhysicalActionPlanReport(Integer pCode) {
		// TODO Auto-generated method stub
		String getProject=getFromYear;
		Session session = sessionFactory.openSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getProject);
			query.setInteger("pCode",pCode); 
			List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  map.put(Integer.parseInt(row[0].toString()),row[1].toString());
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
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getToYearForPhysicalActionPlanReport(Integer fromYear,Integer projId) {
		// TODO Auto-generated method stub
		//String getToYear=getToYear;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getToYear);
			query.setInteger("fromYear",fromYear); 
			query.setInteger("projId",projId); 
			List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  map.put(Integer.parseInt(row[0].toString()),row[1].toString());
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
		return map;
	}

	@Override
	public LinkedHashMap<String, List<PhysicalActionPlanBean>> getPhysicalActionPlanReport(Integer stCode,
			Integer distCode, Integer projId, Integer fromYear, Integer toYear) {
		// TODO Auto-generated method stub
		String getReport=getPhysicalActionPlanReport;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String, List<PhysicalActionPlanBean>> map = new LinkedHashMap<String, List<PhysicalActionPlanBean>>();
		List<PhysicalActionPlanBean> list = new ArrayList<PhysicalActionPlanBean>();
		List<PhysicalActionPlanBean> sublist = new ArrayList<PhysicalActionPlanBean>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getReport);
			//query.setInteger("stCode",stCode); 
			//query.setInteger("distCode",distCode); 
			query.setInteger("projId",projId); 
			query.setInteger("fromYear",fromYear); 
			query.setInteger("toYear",toYear); 
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
			list = query.list();
			 for(PhysicalActionPlanBean bean : list) {
				 if (!map.containsKey(bean.getYrdesc())) {
						sublist = new ArrayList<PhysicalActionPlanBean>();
						sublist.add(bean);
						map.put(bean.getYrdesc(), sublist);
					} else {
						sublist=(map.get(bean.getYrdesc()));
						sublist.add(bean);
						map.put(bean.getYrdesc(), sublist);
					}
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
		return map;
	}

	@Override
	public List<PhysicalActionPlanBean> getPhysicalActionReport(Integer stCode, Integer distCode, Integer projId,
			Integer fromYear, Integer toYear) {

		SQLQuery query=null;
		String getReport=finPhyTrgtRpt;
		String getReportAllp=finPhyTrgtRptAllProj;
		String getReportAlld=finPhyTrgtRptAllDist;
		String getReportAlls=finPhyTrgtRptAllState;
		
		Session session = sessionFactory.getCurrentSession();
		List<PhysicalActionPlanBean> list = new ArrayList<PhysicalActionPlanBean>();
		
		try {
			session.beginTransaction();
			if(!projId.equals(0)) {
			query= session.createSQLQuery(getReport);
			query.setInteger("projId",projId); 
			}
			if(projId.equals(0) && !distCode.equals(0)) {
				query= session.createSQLQuery(getReportAllp);
				query.setInteger("distCode",distCode); 
			}
			if(distCode.equals(0) && !stCode.equals(0)) {
				query= session.createSQLQuery(getReportAlld);
				query.setInteger("stCode",stCode); 
			}
			if(stCode.equals(0)) {
				query= session.createSQLQuery(getReportAlls);
			}
			query.setInteger("fromYear",fromYear); 
			query.setInteger("toYear",toYear); 
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
			list = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
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
	public LinkedHashMap<String, List<String>> viewHeadActivity() {
		// TODO Auto-generated method stub
		String getReport=getHeadActivity;
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMPhyActivity> list = new ArrayList<IwmpMPhyActivity>();
		List<String> lst = new ArrayList<String>();
		LinkedHashMap<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getReport);
			//query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
			list = query.list();
			for(IwmpMPhyActivity act : list) {
				lst = new ArrayList<String>();
			if (!map.containsKey(act.getIwmpMPhyHeads().getHeadDesc())) {
				lst.add(act.getActivityDesc());
				map.put(act.getIwmpMPhyHeads().getHeadDesc(), lst);
			} else {
				lst=map.get(act.getIwmpMPhyHeads().getHeadDesc());
				lst.add(act.getActivityDesc());
				map.put(act.getIwmpMPhyHeads().getHeadDesc(), lst);
			}
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
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getprojectfordistrict(Integer dCode, Integer stcode) {
		String getProject=getdistProject;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getProject);
			query.setInteger("dCode",dCode);
			query.setInteger("stcode",stcode);
			
			List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  map.put(Integer.parseInt(row[0].toString()),row[1].toString());
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
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectBystate(Integer stcode) {
		// TODO Auto-generated method stub
	//	Integer registrationId = regId;
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpProjectPhysicalPlan> rows = new ArrayList<IwmpProjectPhysicalPlan>();
		String hql=getProjectByStateForAAP;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				query.setLong("regId",stcode);
				rows = query.list();
				for(IwmpProjectPhysicalPlan row : rows)
				{
				//  System.out.println("Size by regId: "+row.getIwmpMProject().getProjectCd()+" regId "+regId);
					map.put(row.getIwmpMProject().getProjectId(), row.getIwmpMProject().getProjName());
				}
				session.getTransaction().commit();
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
		
		}
		return map;
	}

	@Override
	public String saveAsExisting(String plan, String activity, Integer projectcd, Integer yearcd, String loginid) {
		
		String res="";
		String draftQuery=saveAsDraftPhysicalAnnualActionPlan;
		String getAlreadyData = checkAlreadyExistActivity;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress(); 
				Query query= session.createQuery(getAlreadyData);
				query.setParameter("projectcd",	projectcd); 
				query.setParameter("yearcd", yearcd); 
				List<String> list = query.list();
				List<String> lsstr= new ArrayList<String>();

				for(Object a: list)
				{
					lsstr.add(String.valueOf(a));
				}
				List<String> actList = Arrays.asList(activity.split(","));
				if(!Collections.disjoint(lsstr, actList)) 
				{
					res="activity exist";
				}
				else {
			
					SQLQuery sqlQuery =session.createSQLQuery(draftQuery);
					session.createSQLQuery(draftQuery);
					sqlQuery.setString("plan", plan);
					sqlQuery.setString("activity", activity);
					sqlQuery.setParameter("projectcd", projectcd);
					sqlQuery.setParameter("yearcd", yearcd);
					sqlQuery.setCharacter("assetid", 'Y');
					sqlQuery.setString("createdby", loginid);
					sqlQuery.setString("ipaddress", ipadd);
					res = sqlQuery.list().get(0).toString();
					if( res.equals("success")) 
					{
							session.getTransaction().commit();
					}
					else {
							session.getTransaction().rollback();
					}
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
	public LinkedHashMap<Integer, String> getFinYearExistPlan(Integer projId) {
		
		List<IwmpMFinYear> yearList=new ArrayList<IwmpMFinYear>();
		LinkedHashMap<Integer, String> yearMap = new LinkedHashMap<Integer, String>();
		String hql=getFinYearExistPlan;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			yearList = query.list();
			session.getTransaction().commit();
			for(IwmpMFinYear yr:yearList) 
			{
				yearMap.put(yr.getFinYrCd(), yr.getFinYrDesc());
			}
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return yearMap;
	}

	@Override
	public String updateAsExisting(String plan, String activity, Integer projectcd, Integer yearcd, String loginid) {
		int ch=0, result;
		
		String res="";
		String update=updatePhysicalAnnualActionPlan;
		String getplaniddata = getplanidData;
		Session session = sessionFactory.getCurrentSession();
		BigDecimal plnact;
		try {
				session.beginTransaction();
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress(); 
				BigDecimal pln= new BigDecimal(plan);
				
				List list=session.createSQLQuery(getplaniddata).setInteger("projectcd", projectcd).setInteger("yearcd", yearcd).list();
				result=Integer.parseInt(list.get(0).toString());
				
				List list1=session.createSQLQuery("select qty_planned from iwmp_project_physical_aap where planid=:plan_id and phy_activity_code=:activity").setInteger("plan_id", result).setInteger("activity", Integer.parseInt(activity)).list();
				plnact=new BigDecimal(list1.get(0).toString());
				
				if(pln.compareTo(plnact)==1) 
				{
				
					SQLQuery sqlQuery =session.createSQLQuery(update);
					sqlQuery.setBigDecimal("plan", pln);
					sqlQuery.setInteger("activity", Integer.parseInt(activity));
					sqlQuery.setInteger("plan_id", result);
					sqlQuery.setString("created", loginid);
					ch=sqlQuery.executeUpdate();
					if(ch>=1) 
					{
						res="success";
						session.getTransaction().commit();
					}
					else{
						res="fail";
						session.getTransaction().rollback();
					}
				}
				else {
					session.getTransaction().rollback();
					res="less";
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
	public List<PhysicalActionPlanTranxBean> viewCMovement(Integer regId, String userType) {
		Integer registrationId = regId;
		List<PhysicalActionPlanTranxBean> list = new ArrayList<PhysicalActionPlanTranxBean>();
		PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=null;
		if(userType.equals("DI") || userType.equals("SL"))
			hql=viewAAPCMovement;
			if(userType.equals("PI"))
				hql=viewAAPCMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regid",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanTranxBean.class));
			list = query.list();
			
			
			 
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public List<PhysicalActionPlanTranxBean> viewARPlan(Integer regId, String userType) {
		Integer registrationId = regId;
		List<PhysicalActionPlanTranxBean> list = new ArrayList<PhysicalActionPlanTranxBean>();
		PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=null;
		if(userType.equals("DI") || userType.equals("SL"))
			hql=viewARMovement;
			if(userType.equals("PI"))
				hql=viewARMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regid",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanTranxBean.class));
			list = query.list();
			
			
			 
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public LinkedHashMap<Integer, String> getFinYearMonth() {
		
		List<IwmpMMonth> monthList=new ArrayList<IwmpMMonth>();
		LinkedHashMap<Integer, String> montMap = new LinkedHashMap<Integer, String>();
		//String hql=getFinYearProjectWise;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("select mont from IwmpMMonth mont order by mont.finmonthId");
			monthList = query.list();
			session.getTransaction().commit();
			for(IwmpMMonth yr:monthList) {
				montMap.put(yr.getMonthId(), yr.getMonthName());
			}
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return montMap;
	}


	@Override
	public List<PhysicalActionPlanBean> getListofHeadAndActivityDesc() {
		List<PhysicalActionPlanBean> list = new ArrayList<>();
		String hql = getHeadActivityDesc;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<PhysicalActionPlanBean> getListofPhysicalActionAchiev(Integer projectcd, Integer yearcd) {
		
		List<PhysicalActionPlanBean> list = new ArrayList<PhysicalActionPlanBean>();
		String hql=getListofPhysicalActionAchiev;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projectcd", projectcd);
			query.setInteger("yearcd", yearcd);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
			list = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}


	
}
