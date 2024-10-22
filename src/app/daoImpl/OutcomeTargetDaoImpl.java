package app.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
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

import app.bean.OutcomeTargetBean;
import app.bean.PhysicalAchievementBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.dao.OutcomeTargetDao;
import app.model.UserReg;
import app.model.master.WdcpmksyMOutcome;
import app.model.project.IwmpProjectPhysicalPlan;
import app.model.project.IwmpProjectPhysicalTranx;
import app.model.project.WdcpmksyOutcomeTarget;
import app.model.project.WdcpmksyOutcomeTargetDetails;
import app.model.project.WdcpmksyOutcomeTargetTranx;
import app.model.project.WdcpmksyProjectPhysicalAchievementTranx;

@Repository("OutcomeTargetDao")
public class OutcomeTargetDaoImpl implements OutcomeTargetDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getOutcomeActivityList}")
	String outcomeActivityList;
	
	@Value("${saveAsDraftOutcomeTarget}")
	String saveAsDraftOutcomeTarget;
	
	@Value("${getUserToForwardForPhysicalAchievement}")
	String getUserToForward;
	
	@Value("${getUserDetailByRegId}")
	String getUserDetails;
	
	@Value("${forwardOutcomeTargetFromPIA}")
	String forwardOutcomeTargetFromPIA;
	
	@Value("${getForwardedOutcome}")
	String getForwardedOutcome;
	
	@Value("${viewOutcomeMovement}")
	String viewOutcomeMovement;
	
	@Value("${checkForAlreadyForwardedOutcome}")
	String checkForAlreadyForwardedOutcome;
	
	@Value("${getUserToForwardForOutcomeTarget}")
	String getUserToForwardForOutcomeTarget;
	
	@Value("${getUserToRejectForOutcomeTarget}")
	String getUserToReject;
	
	@Value("${getOutcomeDetails}")
	String getOutcomeDetails;

	@Value("${getOutcomeTargetMovementDetails}")
	String getOutcomeTargetMovementDetails;

	@Override
	public List<WdcpmksyMOutcome> getActivityDetail(Integer finYr) {
		// TODO Auto-generated method stub
		List<WdcpmksyMOutcome> result = new ArrayList<WdcpmksyMOutcome>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = outcomeActivityList;
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("finYr", finYr);
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			session.getTransaction().rollback();
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
	public String saveAsDraftOutcomeTarget(Integer pCode, Integer finYr, String outcomeid, String outcomedetailid,
			String target,String createdby) {
		// TODO Auto-generated method stub
		
		String res="fail";
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = saveAsDraftOutcomeTarget;
			InetAddress inet=InetAddress.getLocalHost();
			String ipaddress=inet.getHostAddress();
			session.beginTransaction();
			String[] outcomeidArray=outcomeid.split(",");
			String[] outcomedetailidArray=outcomedetailid.split(",");
			String[] targetArray=target.split(",");
			System.out.println(outcomeidArray.length+"#"+outcomedetailidArray.length+"#"+targetArray.length);
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projId", pCode);
			query.setInteger("finYr", finYr);
			query.setParameter("outcomeid", outcomeid);
			query.setParameter("outcomedetailid", outcomedetailid);
			query.setParameter("target", target);
			query.setString("createdby", createdby);
			query.setString("ipaddress", ipaddress);
			res = query.list().get(0).toString();
			if( res.equals("success")) {
				session.getTransaction().commit();
			}else {
				session.getTransaction().rollback();
			}
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
			//session.getTransaction().commit();
		}
		
		return res;
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId, String userType) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> userMap = new LinkedHashMap<Integer, String>();
		try {
			String sql = getUserToForward;
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(sql);
			query.setParameter("regId",	regId);
			query.setParameter("userType", userType);
			String list =query.list().get(0).toString();
			
			String hql2 = getUserDetails;
			 for(String i : list.split(",")) { 
				 Query query1 = session.createQuery(hql2);
				 query1.setInteger("regId", Integer.parseInt(i)); 
				 List<UserReg> user= query1.list(); 
				 for(UserReg u : user) { 
					 userMap.put(u.getRegId(), u.getUserId()); 
					 }
			  
			  }
			session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			//session.getTransaction().commit();
		}
		
		return userMap;
	}

	@Override
	public String forwardOutcomeTargetFromPIA(Integer pCode, Integer finYr, String outcomeid, String outcomedetailid,
			String target, Integer sentFrom, Integer sentTo, String createdBy) {
		// TODO Auto-generated method stub
		String res="fail";
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = forwardOutcomeTargetFromPIA;
			InetAddress inet=InetAddress.getLocalHost();
			String ipaddress=inet.getHostAddress();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projId", pCode);
			query.setInteger("finYr", finYr);
			query.setParameter("outcomeid", outcomeid);
			query.setParameter("outcomedetailid", outcomedetailid);
			query.setParameter("target", target);
			query.setInteger("sentFrom", sentFrom);
			query.setInteger("sentTo", sentTo);
			query.setString("createdby", createdBy);
			query.setString("ipaddress", ipaddress);
			res = query.list().get(0).toString();
			if( res.equals("success")) {
				session.getTransaction().commit();
			}else {
				session.getTransaction().rollback();
			}
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
			//session.getTransaction().commit();
		}
		
		return res;
	}

	@Override
	public List<WdcpmksyOutcomeTargetTranx> getForwardedOutcome(Integer pCode, Integer finYr) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<WdcpmksyOutcomeTargetTranx> outcomeList = new ArrayList<WdcpmksyOutcomeTargetTranx>();
		try {
			String sql = getForwardedOutcome;
			session.beginTransaction();
			Query query= session.createQuery(sql);
			query.setParameter("projId", pCode);
			query.setParameter("finYr", finYr);
			query.setMaxResults(1);
			outcomeList =query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			//session.getTransaction().commit();
		}
		
		return outcomeList;
	}

	@Override
	public List<OutcomeTargetBean> viewMovement(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		List<OutcomeTargetBean> list = new ArrayList<OutcomeTargetBean>();
		String hql=viewOutcomeMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regId",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(OutcomeTargetBean.class));
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
	public List<OutcomeTargetBean> checkForAlreadyForwardedOutcome(Integer targetid, Integer regId) {
		// TODO Auto-generated method stub
		List<OutcomeTargetBean> res=new ArrayList<OutcomeTargetBean>();
		List<WdcpmksyOutcomeTargetTranx> list = new ArrayList<WdcpmksyOutcomeTargetTranx>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = checkForAlreadyForwardedOutcome;
			session.beginTransaction();
			OutcomeTargetBean bean = new OutcomeTargetBean();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("targetid", targetid);
			query.setMaxResults(1);
			list =query.list();
			UserReg userReg = (UserReg) session.load(UserReg.class, regId);
			for(WdcpmksyOutcomeTargetTranx tranx : list) {
				bean.setProjectcd(tranx.getWdcpmksyOutcomeTarget().getIwmpMProject().getProjectId());
				bean.setProjectdesc(tranx.getWdcpmksyOutcomeTarget().getIwmpMProject().getProjName());
				bean.setFinyrcd(tranx.getWdcpmksyOutcomeTarget().getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(tranx.getWdcpmksyOutcomeTarget().getIwmpMFinYear().getFinYrDesc());
				bean.setStatus(tranx.getWdcpmksyOutcomeTarget().getStatus()=='C'?"Completed":"Pending");
				bean.setCurrentlyat(tranx.getIwmpUserRegBySentTo().getUserType().equals("SL")?"SLNA":tranx.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":tranx.getIwmpUserRegBySentTo().getUserType().equals("PI")?"PIA":"");
				bean.setRemarks(tranx.getRemarks());
				bean.setTargetid(tranx.getWdcpmksyOutcomeTarget().getOutcomeTargetId());
				bean.setAction(tranx.getAction());
				//bean.setShow(true);
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
	public String actionByWCDCSLNA(Integer sentTo, Integer sentFrom, Integer targetid, Character action, String remarks,
			String userType) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String res=null;
		try {
			session.getTransaction().begin();
			Date d = new Date();
			Timestamp time = new Timestamp(d.getTime());
			WdcpmksyOutcomeTargetTranx tranx = new WdcpmksyOutcomeTargetTranx();
			UserReg reg = new UserReg();
			reg.setRegId(sentFrom);
			tranx.setIwmpUserRegBySentFrom(reg);
			reg= new UserReg();
			reg.setRegId(sentTo);
			tranx.setIwmpUserRegBySentTo(reg);
			WdcpmksyOutcomeTarget wdcpmksyOutcomeTarget = (WdcpmksyOutcomeTarget) session.get(WdcpmksyOutcomeTarget.class, targetid);
			tranx.setWdcpmksyOutcomeTarget(wdcpmksyOutcomeTarget);
			if(action.toString().equals("F") && userType.equals("SL"))
			tranx.setAction('C');
			else
				tranx.setAction(action);
			tranx.setRemarks(remarks);
			tranx.setSentOn(time);
			session.save(tranx);
			if(action.toString().equals("C") && userType.equals("SL")) {
			wdcpmksyOutcomeTarget.setStatus('C');
			session.update(wdcpmksyOutcomeTarget);
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
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId, String userType, Character action,
			Integer targetid) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = "";
			if(action=='C' || action=='F')
			hql=getUserToForwardForOutcomeTarget;
			else if(action=='R')
			hql=getUserToReject;
			String hql2 = getUserDetails;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			
			if(action=='C' || action=='F') {
				query.setInteger("regId", regId);
				query.setString("userType", userType);
			}
			
			if(action=='R')
				query.setInteger("targetId", targetid);
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
	public List<OutcomeTargetBean> getOutcomeDetails(Integer targetid) {
		// TODO Auto-generated method stub
		List<OutcomeTargetBean> res=new ArrayList<OutcomeTargetBean>();
		List<WdcpmksyOutcomeTargetDetails> list = new ArrayList<WdcpmksyOutcomeTargetDetails>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getOutcomeDetails;
			session.beginTransaction();
			OutcomeTargetBean bean = new OutcomeTargetBean();
			Query query = session.createQuery(hql);
			query.setInteger("targetId", targetid);
			list =query.list();
			for(WdcpmksyOutcomeTargetDetails detail : list) {
				bean = new OutcomeTargetBean();
				bean.setProjectcd(detail.getWdcpmksyOutcomeTarget().getIwmpMProject().getProjectId());
				bean.setProjectdesc(detail.getWdcpmksyOutcomeTarget().getIwmpMProject().getProjName());
				bean.setFinyrcd(detail.getWdcpmksyOutcomeTarget().getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(detail.getWdcpmksyOutcomeTarget().getIwmpMFinYear().getFinYrDesc());
				bean.setStatus(detail.getWdcpmksyOutcomeTarget().getStatus()=='C'?"Completed":"Pending");
				bean.setOutcomehead(detail.getWdcpmksyMOutcome().getOutcomeDesc());
				if(detail.getWdcpmksyMOutcomeDetail()!=null)
				bean.setOutcomeactivity(detail.getWdcpmksyMOutcomeDetail().getOutcomeDetailDesc());
				bean.setTarget(detail.getTarget());
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
	public List<OutcomeTargetBean> getOutcomeTargetMovementDetails(Integer targetid) {
		// TODO Auto-generated method stub
		List<OutcomeTargetBean> res=new ArrayList<OutcomeTargetBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getOutcomeTargetMovementDetails;
			session.beginTransaction();
			OutcomeTargetBean bean = new OutcomeTargetBean();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("targetId", targetid);
			query.setResultTransformer(Transformers.aliasToBean(OutcomeTargetBean.class));
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
