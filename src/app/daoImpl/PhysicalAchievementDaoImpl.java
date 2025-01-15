package app.daoImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import app.bean.AssetIdBean;
import app.bean.LastActionLogBean;
import app.bean.PhysicalAchievementBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.model.WdcpmksyProjectAssetLivelihoodStatus;
import app.model.WdcpmksyProjectAssetProductionStatus;
import app.dao.PhysicalAchievementDao;
import app.model.IwmpActivityAssetAddonParameter;
import app.model.IwmpActivityAssetAddonParameterAchievement;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpProjectAssetStatus;
import app.model.UserMap;
import app.model.UserReg;
import app.model.WdcpmksyProjectAssetEPAStatus;
import app.model.master.IwmpBlock;
import app.model.master.IwmpMPhyActivity;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyProjectPhyAssetAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.model.project.WdcpmksyProjectPhysicalAchievementTranx;

@Repository("PhysicalAchievementDao")
public class PhysicalAchievementDaoImpl implements PhysicalAchievementDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getActivityWithTarget}")
	String getActivityWithTarget;
	
	@Value("${getAssetofActivity}")
	String getAssetofActivity;
	
	@Value("${getCurrentFinYrMonthId}")
	String getCurrentFinYrMonthId;
	
	@Value("${getFinancialYearForAchievement}")
	String getFinancialYearForAchievement;
	
	@Value("${getUserToForwardForPhysicalAchievement}")
	String getUserToForward;
	
	@Value("${getUserDetailByRegId}")
	String getUserDetails;
	
	@Value("${checkDistrictApprovalReuiredOrNot}")
	String checkDistrictApprovalReuiredOrNot;
	
	@Value("${saveAchievementAsDraft}")
	String saveAchievementAsDraft;
	
	@Value("${getAchievementData}")
	String getAchievementData;
	
	@Value("${checkForAlreadyForwardedAchievement}")
	String checkForAlreadyForwardedAchievement;
	
	@Value("${viewAchievementMovement}")
	String viewAchievementMovement;
	
	@Value("${getUserToRejectForAchievement}")
	String getUserToReject;
	
	@Value("${getProjectPhyAssetAchievement}")
	String getProjectPhyAssetAchievement;
	
	@Value("${getAchievementDetails}")
	String getAchievementDetails;
	
	@Value("${getPhysicalAssetStatus}")
	String getPhysicalAssetStatus;
	
	@Value("${getLivelihoodAssetStatus}")
	String getLivelihoodAssetStatus;
	
	@Value("${getProductionAssetStatus}")
	String getProductionAssetStatus;
	
	@Value("${getEPAAssetStatus}")
	String getEPAAssetStatus;
	
	@Value("${getPhysicalAssetAchievement}")
	String getPhysicalAssetAchievement;
	
	@Value("${getAchievementMovementDetails}")
	String getAchievementMovementDetails;
	
	@Value("${getUserToRejectSL}")
	String getUserToRejectSL;

	@Value("${chekcPlanComplete}")
	String chekcPlanComplete;
	
	@Value("${completeassetachievement}")
	String completeassetachievement;
	
	@Value("${viewAchievementNewMovement}")
	String viewAchievementNewMovement;
	
	@Value("${viewCompletedAchievement}")
	String viewCompletedAchievement;
	
	@Value("${getphyAchRecords}")
	String getphyAchRecords;
	
	@Value("${phyachquery1}")
	String phyachquery1;
	
	@Value("${phyachquery2}")
	String phyachquery2;
	
	@Value("${phyachquery3}")
	String phyachquery3;
	
	@Value("${phyachquery4}")
	String phyachquery4;
	
	@Override
	public List<PhysicalAchievementBean> getActivityWithTarget(Integer pCode,Integer finYr,Integer monthId) {
		// TODO Auto-generated method stub
		List<PhysicalAchievementBean> result = new ArrayList<PhysicalAchievementBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getActivityWithTarget;
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
			query.setInteger("finYr", finYr);
			query.setInteger("monthId", monthId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalAchievementBean.class));
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
	public List<IwmpProjectPhysicalAsset> getAssetofActivity(Integer pCode, Integer activityCode,Integer finYr, String stDate) {
		// TODO Auto-generated method stub
		List<IwmpProjectPhysicalAsset> result = new ArrayList<IwmpProjectPhysicalAsset>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			LocalDate date = LocalDate.parse(stDate, DateTimeFormatter.ISO_DATE);
			Date datek = java.sql.Date.valueOf(date);
			String hql = getAssetofActivity;
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("pCode", pCode);
			query.setInteger("activityCode", activityCode);
			query.setInteger("finYr", finYr);
			query.setDate("dated", datek);
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
	public List<String> getCurrentFinYrMonthId(Integer pCode,String finYr) {
		// TODO Auto-generated method stub
		List<String> result =new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getCurrentFinYrMonthId;
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
	public String getFinancialYearForPlan(Integer pCode) {
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
	public String saveAssetAchievement(List<String> assetAcheivement, Integer finYr, Integer monthId, String userid) {
		// TODO Auto-generated method stub
		String res="fail";
		Session session = null;
		
		String hql = getProjectPhyAssetAchievement;
		try {
			
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Date d= new Date();
			List<BigInteger> assetList = new ArrayList<BigInteger>();
			List<BigInteger> assetListDB = new ArrayList<BigInteger>();
			List<WdcpmksyProjectPhyAssetAchievement> assetAchList = new ArrayList<WdcpmksyProjectPhyAssetAchievement>();
			WdcpmksyProjectPhyAssetAchievement ach = new WdcpmksyProjectPhyAssetAchievement();
			for(String str:assetAcheivement) 
			{
				String[] arr1=str.split("_");
				assetList.add(new BigInteger(arr1[1].toString()));
			}
			Query query = session.createQuery(hql);
			query.setInteger("finYr", finYr);
			query.setInteger("monthId", monthId);
			query.setParameterList("assetId", assetList);
			assetAchList =query.list();
			if(!assetAchList.isEmpty()) 
			{
				for(WdcpmksyProjectPhyAssetAchievement asset : assetAchList) 
				{
					assetListDB.add(asset.getIwmpProjectPhysicalAsset().getAssetid());
				}
				for(BigInteger asset: assetList) 
				{
					if(!assetListDB.contains(asset)) 
					{
						for(String str:assetAcheivement) 
						{
							String[] arr1=str.split("_");
							if(asset.equals(new BigInteger(arr1[1].toString()))) 
							{
								IwmpProjectPhysicalAsset ast = new IwmpProjectPhysicalAsset();
								ast.setAssetid(new BigInteger(arr1[1].toString()));
								IwmpMMonth month = new IwmpMMonth();
								month.setMonthId(monthId);
								IwmpMFinYear yr = new IwmpMFinYear();
								yr.setFinYrCd(finYr);
							
								ach = new WdcpmksyProjectPhyAssetAchievement();
								ach.setIwmpProjectPhysicalAsset(ast);
								ach.setAchievement(new BigDecimal(arr1[2].toString()));
								ach.setIwmpMFinYear(yr);
								ach.setIwmpMMonth(month);
								ach.setCreatedon(d);
								ach.setCreatedby(userid);
								ach.setStatus('D');
								
								session.save(ach);
								res="success";	
							}
						}
					}
					else {
						
						for(WdcpmksyProjectPhyAssetAchievement achlist : assetAchList) 
						{
							for(String str:assetAcheivement) 
							{
								String[] arr1=str.split("_");
								if(achlist.getIwmpProjectPhysicalAsset().getAssetid().equals(new BigInteger(arr1[1].toString()))) 
								{
									achlist.setAchievement(new BigDecimal(arr1[2].toString()));
									achlist.setUpdatedby(userid);
									achlist.setUpdatedon(d);
								}
							}
							session.update(achlist);
							res="success";
						//session.getTransaction().commit();
						}
					}
				}
			}
			if(assetAchList.isEmpty()) 
			{
				for(String str:assetAcheivement) 
				{
					String[] arr1=str.split("_");
					IwmpProjectPhysicalAsset asset = new IwmpProjectPhysicalAsset();
					asset.setAssetid(new BigInteger(arr1[1].toString()));
					IwmpMMonth month = new IwmpMMonth();
					month.setMonthId(monthId);
					IwmpMFinYear yr = new IwmpMFinYear();
					yr.setFinYrCd(finYr);
					
					ach = new WdcpmksyProjectPhyAssetAchievement();
					ach.setIwmpProjectPhysicalAsset(asset);
					ach.setAchievement(new BigDecimal(arr1[2].toString()));
					ach.setIwmpMFinYear(yr);
					ach.setIwmpMMonth(month);
					ach.setCreatedon(d);
					ach.setCreatedby(userid);
					ach.setStatus('D');
					
					session.save(ach);
					res="success";
					//session.getTransaction().commit();
				}
			}
			/*
			 * else { for(WdcpmksyProjectPhyAssetAchievement achlist : assetAchList) {
			 * for(String str:assetAcheivement) { String[] arr1=str.split("_");
			 * if(achlist.getIwmpProjectPhysicalAsset().getAssetid().equals(new
			 * BigInteger(arr1[1].toString()))) { achlist.setAchievement(new
			 * BigDecimal(arr1[2].toString())); } } session.update(achlist); res="success";
			 * //session.getTransaction().commit(); } }
			 */
			
			session.getTransaction().commit();
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
			ex.printStackTrace();
			res="fail";
			session.getTransaction().rollback();
		} 
		finally {
			//session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId,String userType,Character action,Integer achid) {
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
	public String checkDistrictApprovalReuiredOrNot(Integer regId) {
		// TODO Auto-generated method stub
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = checkDistrictApprovalReuiredOrNot;
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			res =query.list().get(0).toString();
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
	public String saveAchievementAsDraft(Integer pCode, String activityCode, String ach, Integer finYr,
			Integer monthId, Integer sentFrom, Integer sentTo,String createdBy, boolean blsconf) {
		// TODO Auto-generated method stub
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress();
			String hql = saveAchievementAsDraft;
			session.beginTransaction();
			System.out.println("pCode "+pCode+" activityCode "+activityCode+" ach "+ach+" finYr"+finYr+" month"+monthId+" sentFrom"+sentFrom+" sentTo"+sentTo+" createdBy"+createdBy+" requestIp"+requestIp);
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pCode", pCode);
			query.setString("activityCode", activityCode);
			query.setString("ach", ach);
			query.setInteger("finYr",finYr);
			query.setInteger("month", monthId);
			query.setInteger("sentFrom", sentFrom);
			query.setInteger("sentTo", sentTo);
			query.setString("createdBy", createdBy);
			query.setString("requestIp", requestIp);
			query.setBoolean("bls", blsconf);
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
	public LinkedHashMap<String, List<PhysicalAchievementBean>> getAchievementData(Integer regId) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, List<PhysicalAchievementBean>> res=new LinkedHashMap<String, List<PhysicalAchievementBean>>();
		List<WdcpmksyProjectPhysicalAchievementTranx> list = new ArrayList<WdcpmksyProjectPhysicalAchievementTranx>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = getAchievementData;
			session.beginTransaction();
			PhysicalAchievementBean bean = new PhysicalAchievementBean();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			list =query.list();
			for(WdcpmksyProjectPhysicalAchievementTranx tranx : list) {
				bean.setProjectcd(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMProject().getProjectId());
				bean.setProjectdesc(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMProject().getProjName());
				bean.setFinyrcd(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMFinYear().getFinYrDesc());
				bean.setMonthid(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMMonth().getMonthId());
				bean.setMonthdesc(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMMonth().getMonthName());
				bean.setStatus(tranx.getAction()=='C'?"Completed":"Pending");
				bean.setCurrentlyat(tranx.getIwmpUserRegBySentTo().getUserType().equals("SL")?"SLNA":tranx.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":tranx.getIwmpUserRegBySentTo().getUserType().equals("PI")?"P":"");
				bean.setRemarks(tranx.getRemarks());
				bean.setAchievementid(tranx.getWdcpmksyProjectPhysicalAchievement().getAchievementId());
				List<PhysicalAchievementBean> l=new ArrayList<PhysicalAchievementBean>();
				l.add(bean);
				res.put(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMProject().getProjName(), l);
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
	public List<PhysicalAchievementBean> checkForAlreadyForwardedAchievement(Integer achId, Integer regId) {
		// TODO Auto-generated method stub
		List<PhysicalAchievementBean> res=new ArrayList<PhysicalAchievementBean>();
		List<WdcpmksyProjectPhysicalAchievementTranx> list = new ArrayList<WdcpmksyProjectPhysicalAchievementTranx>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = checkForAlreadyForwardedAchievement;
			session.beginTransaction();
			PhysicalAchievementBean bean = new PhysicalAchievementBean();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("achId", achId);
			query.setMaxResults(1);
			list =query.list();
			UserReg userReg = (UserReg) session.load(UserReg.class, regId);
			for(WdcpmksyProjectPhysicalAchievementTranx tranx : list) {
				bean.setProjectcd(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMProject().getProjectId());
				bean.setProjectdesc(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMProject().getProjName());
				bean.setFinyrcd(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMFinYear().getFinYrDesc());
				bean.setMonthid(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMMonth().getMonthId());
				bean.setMonthdesc(tranx.getWdcpmksyProjectPhysicalAchievement().getIwmpMMonth().getMonthName());
				bean.setStatus(tranx.getWdcpmksyProjectPhysicalAchievement().getStatus()=='C'?"Completed":"Pending");
				bean.setCurrentlyat(tranx.getIwmpUserRegBySentTo().getUserType().equals("SL")?"SLNA":tranx.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":tranx.getIwmpUserRegBySentTo().getUserType().equals("PI")?"PIA":"");
				bean.setRemarks(tranx.getRemarks());
				bean.setAchievementid(tranx.getWdcpmksyProjectPhysicalAchievement().getAchievementId());
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
	public String forwardAchievementByWCDC(Integer sentTo, Integer sentFrom, Integer achid, Character action,
			String remarks, String userType) {
		// TODO Auto-generated method stub
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress();
			session.beginTransaction();
			//String hql = forwardAchievement;
			String sql = completeassetachievement;
			WdcpmksyProjectPhysicalAchievementTranx tranx = new WdcpmksyProjectPhysicalAchievementTranx();
		//	session.beginTransaction();
			//System.out.println("pCode "+pCode+" activityCode "+activityCode+" ach "+ach+" finYr"+finYr+" month"+monthId+" sentFrom"+sentFrom+" sentTo"+sentTo+" createdBy"+createdBy+" requestIp"+requestIp);
			/*
			 * SQLQuery query = session.createSQLQuery(hql); query.setInteger("sentTo",
			 * sentTo); query.setInteger("sentFrom", sentFrom); query.setInteger("achid",
			 * achid); query.setCharacter("action",action); query.setString("remarks",
			 * remarks); query.setString("userType", userType);
			 */
			
			WdcpmksyProjectPhysicalAchievement ach = (WdcpmksyProjectPhysicalAchievement) session.load(WdcpmksyProjectPhysicalAchievement.class, achid);
			UserReg userSentTo = new UserReg();
			userSentTo.setRegId(sentTo);
			UserReg userSentFrom = new UserReg();
			userSentFrom.setRegId(sentFrom);
			tranx.setIwmpUserRegBySentfrom(userSentFrom);
			tranx.setIwmpUserRegBySentTo(userSentTo);
			tranx.setAction(action);
			tranx.setRemarks(remarks);
			tranx.setWdcpmksyProjectPhysicalAchievement(ach);
			tranx.setSenton(new Date());
			Integer year=ach.getIwmpMFinYear().getFinYrCd();
			Integer mon=ach.getIwmpMMonth().getMonthId();
		//	System.out.println(year+" kdy "+mon);
			if(action.equals('C'))
				ach.setStatus(action);
			
			session.save(tranx);
			session.update(ach);
			
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("finyr",year);
			query.setInteger("monid",mon); 
			query.executeUpdate();
			
			
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
	public List<PhysicalAchievementBean> viewMovement(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=viewAchievementMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regId",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalAchievementBean.class));
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
	public List<WdcpmksyProjectPhysicalAchievementDetails> getAchievementDetails(Integer achievementid) {
		// TODO Auto-generated method stub
		List<WdcpmksyProjectPhysicalAchievementDetails> list = new ArrayList<WdcpmksyProjectPhysicalAchievementDetails>();
		String hql=getAchievementDetails;
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
	public List<PhysicalAchievementBean> getAchievementMovementDetails(Integer achievementid) {
		// TODO Auto-generated method stub
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=getAchievementMovementDetails;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("achievementid",achievementid);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalAchievementBean.class));
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
	public String getMinumFinYear(Integer pCode) {
		
		List<String> result = new ArrayList<String>();
		String res=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = chekcPlanComplete;
			
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projcd", pCode);
			result =query.list();
			for(String i : result) {
				if(res==null)
				res=i;
				
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
	public List<PhysicalAchievementBean> viewAchievement(Integer regId) {
		Integer registrationId = regId;
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=viewAchievementNewMovement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regId",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalAchievementBean.class));
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
	public List<PhysicalAchievementBean> viewCompletedMovement(Integer regId) {
		Integer registrationId = regId;
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		PhysicalActionPlanTranxBean  tranxBean = new PhysicalActionPlanTranxBean();
		String hql=viewCompletedAchievement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong("regId",registrationId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalAchievementBean.class));
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
	public List<IwmpProjectAssetStatus> getProjectPhysicalAssetStatus(BigInteger assetid) {
		List<IwmpProjectAssetStatus> list = new ArrayList<IwmpProjectAssetStatus>();
		String hql=getPhysicalAssetStatus;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setBigInteger("assetid",assetid);
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
	public List<WdcpmksyProjectPhyAssetAchievement> getProjectPhysicalAssetAchievement(BigInteger assetid) {
		List<WdcpmksyProjectPhyAssetAchievement> list = new ArrayList<WdcpmksyProjectPhyAssetAchievement>();
		String hql=getPhysicalAssetAchievement;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setBigInteger("assetid",assetid);
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
	public List<WdcpmksyProjectAssetLivelihoodStatus> getProjectLivelihoodAssetStatus(int assetid) {
		List<WdcpmksyProjectAssetLivelihoodStatus> list = new ArrayList<WdcpmksyProjectAssetLivelihoodStatus>();
		String hql=getLivelihoodAssetStatus;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("assetid",assetid);
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
	public List<WdcpmksyProjectAssetProductionStatus> getProjectProductionAssetStatus(int assetid) {
		List<WdcpmksyProjectAssetProductionStatus> list = new ArrayList<WdcpmksyProjectAssetProductionStatus>();
		String hql=getProductionAssetStatus;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("assetid",assetid);
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
	public List<WdcpmksyProjectAssetEPAStatus> getProjectEPAAssetStatus(int assetid) {
		List<WdcpmksyProjectAssetEPAStatus> list = new ArrayList<WdcpmksyProjectAssetEPAStatus>();
		String hql=getEPAAssetStatus;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("assetid",assetid);
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
	public List<WdcpmksyProjectPhysicalAchievementDetails> showPhyAchRecords(Integer project, Integer fyear, Integer month) {
		List<WdcpmksyProjectPhysicalAchievementDetails> list = new ArrayList<WdcpmksyProjectPhysicalAchievementDetails>();
		String hql=getphyAchRecords;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("project",project);
			query.setParameter("fyear",fyear);
			query.setParameter("month",month);
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
	public boolean unfreezePhyAchData(Integer project, Integer month, Integer year, Integer achievementid) {
		Boolean res=false;
		Session session = sessionFactory.getCurrentSession();
		String hql=phyachquery1;
		try 
		{
			session.beginTransaction();
			SQLQuery query1 = session.createSQLQuery(hql);
			query1.setParameter("achievementid", achievementid);
			query1.executeUpdate();
			
			SQLQuery query2 = session.createSQLQuery(phyachquery2);
			query2.setParameter("achievementid", achievementid);
			query2.executeUpdate();
			
			SQLQuery query3 = session.createSQLQuery(phyachquery3);
			query3.setParameter("project", project);
			query3.setParameter("month", month);
			query3.setParameter("year", year);
			query3.executeUpdate();
			
			SQLQuery query4 = session.createSQLQuery(phyachquery4);
			query4.setParameter("achievementid", achievementid);
			query4.executeUpdate();
			
			 session.getTransaction().commit();
		        res = true;
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		return false;
		}
		
		return res;	
	}

}
