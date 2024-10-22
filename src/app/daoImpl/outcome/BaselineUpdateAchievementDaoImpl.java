package app.daoImpl.outcome;

import java.math.BigDecimal;
import java.net.InetAddress;
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

import app.bean.BaselineUpdateAchievementBean;
import app.bean.PhysicalAchievementBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.dao.outcomes.BaselineUpdateAchievementDao;
import app.model.IwmpUserProjectMap;
import app.model.UserReg;
import app.model.project.WdcpmksyBaselineupdateAchievement;
import app.model.project.WdcpmksyBaselineupdateAchievementDetail;
import app.model.project.WdcpmksyBaselineupdateAchievementTranx;
import app.model.project.WdcpmksyProjectPhysicalAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.model.project.WdcpmksyProjectPhysicalAchievementTranx;

@Repository("BaselineUpdateAchievementDao")
public class BaselineUpdateAchievementDaoImpl implements BaselineUpdateAchievementDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getProjectByUserAndBaseline}")
	String getProjectByUserAndBaseline;
	
	@Value("${getBaselineUpadateAchievement}")
	String getBaselineUpadateAchievement;
	
	@Value("${saveBaselineAchievementAsDraft}")
	String saveBaselineAchievementAsDraft;
	
	@Value("${viewBaselinAchievementNewMovement}")
	String viewBaselinAchievementNewMovement;
	
	@Value("${checkAlreadyForwardedBaselineAchievement}")
	String checkAlreadyForwardedBaselineAchievement;
	
	@Value("${getUserToForwardForPhysicalAchievement}")
	String getUserToForward;
	
	@Value("${getUserToRejectBaselineupdateAchievement}")
	String getUserToRejectBaselineupdateAchievement;
	
	@Value("${getUserDetailByRegId}")
	String getUserDetails;
	
	@Value("${viewBaselineAchievementMovement}")
	String viewBaselineAchievementMovement;
	
	@Value("${viewCompletedBaselineAchievement}")
	String viewCompletedBaselineAchievement;
	
	@Value("${getBaselineUpdateAchievementDetails}")
	String getBaselineUpdateAchievementDetails;
	
	@Value("${getBaselineupdateAchievementMovementDetails}")
	String getBaselineupdateAchievementMovementDetails;
	
	
	@Override
	public LinkedHashMap<Integer, String> getProjectByRegIdBaseline(Integer regId) {
		
		Integer registrationId = regId;
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
	//	List<IwmpUserProjectMap> rows = new ArrayList<IwmpUserProjectMap>();
		String hql=getProjectByUserAndBaseline;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId",registrationId);
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
			}
			/*
			 * rows = query.list(); for(IwmpUserProjectMap row : rows){
			 * System.out.println("Size by regId: "+row.getIwmpMProject().getProjectCd()
			 * +" regId "+regId); map.put(row.getIwmpMProject().getProjectId(),
			 * row.getIwmpMProject().getProjName()); }
			 */
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
	public String getFinancialYearForPlan(Integer pCode) {
		
		List<Integer> result = new ArrayList<Integer>();
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery("select finYrCd from IwmpMFinYear where achievStatus is null");
			//query.setInteger("pCode", pCode);
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
		
		List<String> result =new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = "select getYearAndMonthForBaselineupdateAchievement(:finYr,:pCode)";
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
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
	public List<BaselineUpdateAchievementBean> getActivityWithTarget(Integer pCode, Integer finYr, Integer monthId) {
		
		List<BaselineUpdateAchievementBean> result = new ArrayList<BaselineUpdateAchievementBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getBaselineUpadateAchievement;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
		//	query.setInteger("finYr", finYr);
		//	query.setInteger("monthId", monthId);
			query.setResultTransformer(Transformers.aliasToBean(BaselineUpdateAchievementBean.class));
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
	public String saveBaselineAchievementAsDraft(Integer pCode, Integer finYr, Integer monthId, Integer sentFrom,
			Integer sentTo, String createdBy, boolean blsconf, BigDecimal gross, BigDecimal grossachiv,
			BigDecimal total, BigDecimal totalachiv, BigDecimal cultivable, BigDecimal cultivableachiv,
			BigDecimal degraded, BigDecimal degradedachiv, BigDecimal rainfed, BigDecimal rainfedachiv,
			BigDecimal others, BigDecimal othersachiv, BigDecimal protective, BigDecimal protectiveachiv,
			BigDecimal assured, BigDecimal assuredachiv, BigDecimal noirri, BigDecimal noirriachiv, BigDecimal single,
			BigDecimal singleachiv, BigDecimal doublec, BigDecimal doubleachiv, BigDecimal multiple,
			BigDecimal multipleachiv, BigDecimal nocrop, BigDecimal nocropachiv) {

		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress();
			String hql = saveBaselineAchievementAsDraft;
			session.beginTransaction();
		//	System.out.println("pCode "+pCode+" activityCode "+activityCode+" ach "+ach+" finYr"+finYr+" month"+monthId+" sentFrom"+sentFrom+" sentTo"+sentTo+" createdBy"+createdBy+" requestIp"+requestIp);
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
			query.setInteger("finYr",finYr);
			query.setInteger("month", monthId);
			query.setInteger("sentFrom", sentFrom);
			query.setInteger("sentTo", sentTo);
			query.setString("createdBy", createdBy);
			query.setString("requestIp", requestIp);
			query.setBoolean("bls", blsconf);
			query.setBigDecimal("gross", gross);
			query.setBigDecimal("grossachiv", grossachiv);
			query.setBigDecimal("total", total);
			query.setBigDecimal("totalachiv", totalachiv);
			query.setBigDecimal("cultivable", cultivable);
			query.setBigDecimal("cultivableachiv", cultivableachiv);
			query.setBigDecimal("degraded", degraded);
			query.setBigDecimal("degradedachiv", degradedachiv);
			query.setBigDecimal("rainfedc", rainfed);
			query.setBigDecimal("rainfedachiv", rainfedachiv);
			query.setBigDecimal("otherc", others);
			query.setBigDecimal("othersachiv", othersachiv);
			query.setBigDecimal("protective", protective);
			query.setBigDecimal("protectiveachiv", protectiveachiv);
			query.setBigDecimal("assured", assured);
			query.setBigDecimal("assuredachiv", assuredachiv);
			query.setBigDecimal("noirri", noirri);
			query.setBigDecimal("noirriachiv", noirriachiv);
			query.setBigDecimal("single", single);
			query.setBigDecimal("singleachiv", singleachiv);
			query.setBigDecimal("doublec", doublec);
			query.setBigDecimal("doubleachiv", doubleachiv);
			query.setBigDecimal("multiple", multiple);
			query.setBigDecimal("multipleachiv", multipleachiv);
			query.setBigDecimal("nocrop", nocrop);
			query.setBigDecimal("nocropachiv", nocropachiv);
			
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
	public List<BaselineUpdateAchievementBean> viewAchievement(Integer regId) {
		
		Integer registrationId = regId;
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
	//	PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=viewBaselinAchievementNewMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regId",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(BaselineUpdateAchievementBean.class));
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
	public List<BaselineUpdateAchievementBean> checkAlreadyForwardedBaselinAchievement(Integer achId, Integer regId) {
		
		List<BaselineUpdateAchievementBean> res=new ArrayList<BaselineUpdateAchievementBean>();
		List<WdcpmksyBaselineupdateAchievementTranx> list = new ArrayList<WdcpmksyBaselineupdateAchievementTranx>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = checkAlreadyForwardedBaselineAchievement;
			session.beginTransaction();
			BaselineUpdateAchievementBean bean = new BaselineUpdateAchievementBean();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("achId", achId);
			query.setMaxResults(1);
			list =query.list();
			UserReg userReg = (UserReg) session.load(UserReg.class, regId);
			for(WdcpmksyBaselineupdateAchievementTranx tranx : list) {
				bean.setProjectcd(tranx.getWdcpmksyBaselineupdateAchievement().getIwmpMProject().getProjectId());
				bean.setProjectdesc(tranx.getWdcpmksyBaselineupdateAchievement().getIwmpMProject().getProjName());
				bean.setFinyrcd(tranx.getWdcpmksyBaselineupdateAchievement().getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(tranx.getWdcpmksyBaselineupdateAchievement().getIwmpMFinYear().getFinYrDesc());
				bean.setMonthid(tranx.getWdcpmksyBaselineupdateAchievement().getIwmpMMonth().getMonthId());
				bean.setMonthdesc(tranx.getWdcpmksyBaselineupdateAchievement().getIwmpMMonth().getMonthName());
				bean.setStatus(tranx.getWdcpmksyBaselineupdateAchievement().getStatus()=='C'?"Completed":"Pending");
				bean.setCurrentlyat(tranx.getIwmpUserRegBySentTo().getUserType().equals("SL")?"SLNA":tranx.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":tranx.getIwmpUserRegBySentTo().getUserType().equals("PI")?"PIA":"");
				bean.setRemarks(tranx.getRemarks());
				bean.setAchievementid(tranx.getWdcpmksyBaselineupdateAchievement().getAchievementId());
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
	public String forwardBaselineUpdateAchievementWCDC(Integer sentTo, Integer sentFrom, Integer achid,
			Character action, String remarks, String userType) {
		
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress();
			session.beginTransaction();
			
			WdcpmksyBaselineupdateAchievementTranx tranx = new WdcpmksyBaselineupdateAchievementTranx();
		
			WdcpmksyBaselineupdateAchievement ach = (WdcpmksyBaselineupdateAchievement) session.load(WdcpmksyBaselineupdateAchievement.class, achid);
			UserReg userSentTo = new UserReg();
			userSentTo.setRegId(sentTo);
			UserReg userSentFrom = new UserReg();
			userSentFrom.setRegId(sentFrom);
			tranx.setIwmpUserRegBySentfrom(userSentFrom);
			tranx.setIwmpUserRegBySentTo(userSentTo);
			tranx.setAction(action);
			tranx.setRemarks(remarks);
			tranx.setWdcpmksyBaselineupdateAchievement(ach);
			tranx.setSenton(new Date());
			Integer year=ach.getIwmpMFinYear().getFinYrCd();
			Integer mon=ach.getIwmpMMonth().getMonthId();
		//	System.out.println(year+" kdy "+mon);
			if(action.equals('C'))
				ach.setStatus(action);
			
			session.save(tranx);
			session.update(ach);
				res ="success";
			
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
	public LinkedHashMap<Integer, String> getUserToForwardkd(Integer regId, String userType, Character action,
			Integer achid) {
		
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = "";
			if(action=='C' || action=='F')
			hql=getUserToForward;
			else if(action=='R')
			hql=getUserToRejectBaselineupdateAchievement;
			String hql2 = getUserDetails;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			
			if(action=='C' || action=='F') {
			query.setString("userType", userType);
			query.setInteger("regId", regId);
			}if(action=='R')
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
	public List<BaselineUpdateAchievementBean> viewMovement(Integer regId) {
		
		Integer registrationId = regId;
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
		BaselineUpdateAchievementBean  tranxBean = new BaselineUpdateAchievementBean();
		String hql=viewBaselineAchievementMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regId",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(BaselineUpdateAchievementBean.class));
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
	public List<BaselineUpdateAchievementBean> viewCompletedBaselineAchievement(Integer regId) {
		
		Integer registrationId = regId;
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
		BaselineUpdateAchievementBean  tranxBean = new BaselineUpdateAchievementBean();
		String hql=viewCompletedBaselineAchievement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regId",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(BaselineUpdateAchievementBean.class));
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
	public List<WdcpmksyBaselineupdateAchievementDetail> getBaselineupdateAchievementDetails(Integer achievementid) {
		
		List<WdcpmksyBaselineupdateAchievementDetail> list = new ArrayList<WdcpmksyBaselineupdateAchievementDetail>();
		String hql=getBaselineUpdateAchievementDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("achievementid",achievementid);
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
	public List<BaselineUpdateAchievementBean> getBaselineupdateAchievementMovementDetails(Integer achievementid) {
		
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
		BaselineUpdateAchievementBean  tranxBean = new BaselineUpdateAchievementBean();
		String hql=getBaselineupdateAchievementMovementDetails;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("achievementid",achievementid);
			query.setResultTransformer(Transformers.aliasToBean(BaselineUpdateAchievementBean.class));
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

	

}
