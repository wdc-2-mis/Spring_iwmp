package app.daoImpl;

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
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.OutcomeAchievementBean;
import app.bean.OutcomeTargetBean;
import app.bean.PhysicalAchievementBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.dao.OutcomeAchievementDao;
import app.model.IwmpMProject;
import app.model.UserReg;
import app.model.project.WdcpmksyOutcomeAchDetails;
import app.model.project.WdcpmksyOutcomeAchTranx;
import app.model.project.WdcpmksyOutcomeAchievement;
import app.model.project.WdcpmksyOutcomeTarget;
import app.model.project.WdcpmksyOutcomeTargetDetails;
import app.model.project.WdcpmksyProjectPhysicalAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievementTranx;

@Repository("OutcomeAchievementDao")
public class OutcomeAchievementDaoImpl implements OutcomeAchievementDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getFinancialYearForOutcomeAchievement}")
	String getFinancialYearForAchievement;
	
	@Value("${getCurrentFinYrMonthIdForOutcomeAchievement}")
	String getCurrentFinYrMonthId;
	
	@Value("${getActivityWithTargetForOutcome}")
	String getActivityWithTarget;
	
	@Value("${getProjectByRegIdForOutcomeAchievement}")
	String getProjectByRegId;
	
	@Value("${getUserToForwardForPhysicalAchievement}")
	String getUserToForward;
	
	@Value("${getUserToRejectForOutcomeAchievement}")
	String getUserToReject;
	
	@Value("${getUserDetailByRegId}")
	String getUserDetails;
	
	@Value("${saveOutcomeAchievementAsDraft}")
	String saveAchievementAsDraft;
	
	@Value("${viewOutcomeAchievementMovement}")
	String viewAchievementMovement;
	
	@Value("${checkForAlreadyForwardedOutcomeAchievement}")
	String checkForAlreadyForwardedAchievement;
	
	@Value("${getOutcomeAchievementDetails}")
	String getOutcomeAchievementDetails;
	
	@Value("${getOutcomeAchievementMovementDetails}")
	String getOutcomeAchievementMovementDetails;

	@Override
	public String getFinancialYearForOutcomeAchievement(Integer pCode) {
		// TODO Auto-generated method stub
		List<Integer> result = new ArrayList<Integer>();
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getFinancialYearForAchievement;
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("pCode", pCode);
			result =query.list();
			for(Integer i : result) {
				if(res==null)
				res=Integer.toString(i);
				else
				res+=","+Integer.toString(i);
			}
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public List<String> getCurrentFinYrMonthId(Integer pCode, String finYr) {
		// TODO Auto-generated method stub
		List<String> result =new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getCurrentFinYrMonthId;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setParameter("pCode", pCode);
			query.setParameter("finYr", finYr);
			//query.setResultTransformer(Transformers.aliasToBean(PhysicalAchievementBean.class));
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
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}

	@Override
	public List<OutcomeAchievementBean> getActivityWithTarget(Integer pCode, Integer finYr, Integer monthId) {
		// TODO Auto-generated method stub
		List<OutcomeAchievementBean> result = new ArrayList<OutcomeAchievementBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getActivityWithTarget;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
			query.setInteger("finYr", finYr);
			query.setInteger("monthId", monthId);
			query.setResultTransformer(Transformers.aliasToBean(OutcomeAchievementBean.class));
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
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectByRegIdForOutcomeAchievement(Integer regId) {
		// TODO Auto-generated method stub
		List<WdcpmksyOutcomeTarget> result = new ArrayList<WdcpmksyOutcomeTarget>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getProjectByRegId;
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			result =query.list();
			for(WdcpmksyOutcomeTarget target : result) {
				map.put(target.getIwmpMProject().getProjectId(), target.getIwmpMProject().getProjName());
			}
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId, String userType, Character action,
			Integer achid) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = "";
			if(action=='C' || action=='F')
			hql=getUserToForward;
			else if(action=='R')
			hql=getUserToReject;
			String hql2 = getUserDetails;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("regId", regId);
			if(action=='C' || action=='F')
			query.setString("userType", userType);
			if(action=='R')
				query.setInteger("achId", achid);
			String list =query.list().get(0).toString();
			
			 for(String i : list.split(",")) { 
				 Query query1 = session.createQuery(hql2);
				 query1.setInteger("regId", Integer.parseInt(i)); 
				 List<UserReg> user= query1.list(); 
				 for(UserReg u : user) { 
					 result.put(u.getRegId(), u.getUserId()); 
					 }
			  
			  }
			 
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}

	@Override
	public String saveAchievementAsDraft(Integer pCode,String headCode, String activityCode, String ach, Integer finYr, Integer monthId,
			Integer sentFrom, Integer sentTo, String createdBy) {
		// TODO Auto-generated method stub
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress();
			String hql = saveAchievementAsDraft;
			session.beginTransaction();
			System.out.println("pCode "+pCode+" headCode "+headCode+" activityCode "+activityCode+" ach "+ach+" finYr"+finYr+" month"+monthId+" sentFrom"+sentFrom+" sentTo"+sentTo+" createdBy"+createdBy+" requestIp"+requestIp);
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
			query.setString("headCode", headCode);
			query.setString("activityCode", activityCode);
			query.setString("ach", ach);
			query.setInteger("finYr",finYr);
			query.setInteger("month", monthId);
			query.setInteger("sentFrom", sentFrom);
			query.setInteger("sentTo", sentTo);
			query.setString("createdBy", createdBy);
			query.setString("requestIp", requestIp);
			if(query.list().get(0).toString().equals("success"))
				res ="success";
			else
				res="fail";
			
			} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public List<OutcomeAchievementBean> viewMovement(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		List<OutcomeAchievementBean> list = new ArrayList<OutcomeAchievementBean>();
		String hql=viewAchievementMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regId",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(OutcomeAchievementBean.class));
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
	public List<OutcomeAchievementBean> checkForAlreadyForwardedOutcomeAchievement(Integer achId, Integer regId) {
		// TODO Auto-generated method stub
		List<OutcomeAchievementBean> res=new ArrayList<OutcomeAchievementBean>();
		List<WdcpmksyOutcomeAchTranx> list = new ArrayList<WdcpmksyOutcomeAchTranx>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = checkForAlreadyForwardedAchievement;
			session.beginTransaction();
			OutcomeAchievementBean bean = new OutcomeAchievementBean();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("achId", achId);
			query.setMaxResults(1);
			list =query.list();
			UserReg userReg = (UserReg) session.load(UserReg.class, regId);
			for(WdcpmksyOutcomeAchTranx tranx : list) {
				bean.setProjectcd(tranx.getWdcpmksyOutcomeAchievement().getIwmpMProject().getProjectId());
				bean.setProjectdesc(tranx.getWdcpmksyOutcomeAchievement().getIwmpMProject().getProjName());
				bean.setFinyrcd(tranx.getWdcpmksyOutcomeAchievement().getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(tranx.getWdcpmksyOutcomeAchievement().getIwmpMFinYear().getFinYrDesc());
				bean.setMonthid(tranx.getWdcpmksyOutcomeAchievement().getIwmpMMonth().getMonthId());
				bean.setMonthdesc(tranx.getWdcpmksyOutcomeAchievement().getIwmpMMonth().getMonthName());
				bean.setStatus(tranx.getWdcpmksyOutcomeAchievement().getStatus()=='C'?"Completed":"Pending");
				bean.setCurrentlyat(tranx.getIwmpUserRegBySentTo().getUserType().equals("SL")?"SLNA":tranx.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":tranx.getIwmpUserRegBySentTo().getUserType().equals("PI")?"PIA":"");
				bean.setRemarks(tranx.getRemarks());
				bean.setAchievementid(tranx.getWdcpmksyOutcomeAchievement().getAchievementId());
				bean.setAction(tranx.getAction());
				bean.setShow(true);
				bean.setUsertype(userReg.getUserType().equals("DI")?"WCDC":userReg.getUserType().equals("SL")?"SLNA":"PIA");
				//List<PhysicalAchievementBean> l=new ArrayList<PhysicalAchievementBean>();
				res.add(bean);
				//res.put(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMProject().getProjName(), l);
			}
			} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public String forwardOutcomeAchievementByWCDCSLNA(Integer sentTo, Integer sentFrom, Integer achid, Character action,
			String remarks, String userType) {
		// TODO Auto-generated method stub
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress();
			//String hql = forwardAchievement;
			WdcpmksyOutcomeAchTranx tranx = new WdcpmksyOutcomeAchTranx();
			session.beginTransaction();
			//System.out.println("pCode "+pCode+" activityCode "+activityCode+" ach "+ach+" finYr"+finYr+" month"+monthId+" sentFrom"+sentFrom+" sentTo"+sentTo+" createdBy"+createdBy+" requestIp"+requestIp);
			/*
			 * SQLQuery query = session.createSQLQuery(hql); query.setInteger("sentTo",
			 * sentTo); query.setInteger("sentFrom", sentFrom); query.setInteger("achid",
			 * achid); query.setCharacter("action",action); query.setString("remarks",
			 * remarks); query.setString("userType", userType);
			 */
			
			WdcpmksyOutcomeAchievement ach = (WdcpmksyOutcomeAchievement) session.load(WdcpmksyOutcomeAchievement.class, achid);
			UserReg userSentTo = new UserReg();
			userSentTo.setRegId(sentTo);
			UserReg userSentFrom = new UserReg();
			userSentFrom.setRegId(sentFrom);
			tranx.setIwmpUserRegBySentFrom(userSentFrom);
			tranx.setIwmpUserRegBySentTo(userSentTo);
			tranx.setAction(action);
			tranx.setRemarks(remarks);
			tranx.setWdcpmksyOutcomeAchievement(ach);
			tranx.setSentOn(new Date());
			if(action.equals('C'))
				ach.setStatus(action);
			
			session.save(tranx);
			session.update(ach);
			//if(query.list().get(0).toString().equals("success"))
				res ="success";
			//else
				//res="fail";
			
			} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			res="fail";
			session.getTransaction().rollback();
		} 
		catch (Exception ex) 
		{
			res="fail";
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public List<OutcomeAchievementBean> getOutcomeAchievementDetails(Integer achId) {
		// TODO Auto-generated method stub
		List<OutcomeAchievementBean> res=new ArrayList<OutcomeAchievementBean>();
		List<WdcpmksyOutcomeAchDetails> list = new ArrayList<WdcpmksyOutcomeAchDetails>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getOutcomeAchievementDetails;
			session.beginTransaction();
			OutcomeAchievementBean bean = new OutcomeAchievementBean();
			Query query = session.createQuery(hql);
			query.setInteger("achId", achId);
			list =query.list();
			for(WdcpmksyOutcomeAchDetails detail : list) {
				bean = new OutcomeAchievementBean();
				bean.setProjectcd(detail.getWdcpmksyOutcomeAchievement().getIwmpMProject().getProjectId());
				bean.setProjectdesc(detail.getWdcpmksyOutcomeAchievement().getIwmpMProject().getProjName());
				bean.setFinyrcd(detail.getWdcpmksyOutcomeAchievement().getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(detail.getWdcpmksyOutcomeAchievement().getIwmpMFinYear().getFinYrDesc());
				bean.setStatus(detail.getWdcpmksyOutcomeAchievement().getStatus()=='C'?"Completed":"Pending");
				bean.setHeaddesc(detail.getWdcpmksyMOutcome().getOutcomeDesc());
				if(detail.getWdcpmksyMOutcomeDetail()!=null)
				bean.setActivitydesc(detail.getWdcpmksyMOutcomeDetail().getOutcomeDetailDesc());
				bean.setAchievement(detail.getAchievement());
				bean.setMonthdesc(detail.getWdcpmksyOutcomeAchievement().getIwmpMMonth().getMonthName());
				res.add(bean);
			}
			} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public List<OutcomeAchievementBean> getOutcomeAchievementMovementDetails(Integer achId) {
		// TODO Auto-generated method stub
		List<OutcomeAchievementBean> res=new ArrayList<OutcomeAchievementBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getOutcomeAchievementMovementDetails;
			session.beginTransaction();
			OutcomeTargetBean bean = new OutcomeTargetBean();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("achId", achId);
			query.setResultTransformer(Transformers.aliasToBean(OutcomeAchievementBean.class));
			res =query.list();
			
			} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return res;
	}

}
