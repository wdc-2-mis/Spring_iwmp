package app.daoImpl.master;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

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

import app.bean.AssetIdBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.ProjectLocationBean;
import app.bean.reports.LivelihoodPrdouctionEPAWorkIdBean;

import app.dao.master.LivelihoodPrdouctionEPAWorkIdDao;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.IwmpProjectAssetStatus;
import app.model.UserReg;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.outcome.ProductionDetail;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.IwmpProjectPhysicalAssetTemp;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyPrdouctionWorkid;

@Repository("LivelihoodPrdouctionEPAWorkIdDao")
public class LivelihoodPrdouctionEPAWorkIdDaoImpl implements LivelihoodPrdouctionEPAWorkIdDao{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getEPADetailWorkId}")
	String getEPADetailWorkId;
	
	@Value("${getLivelihoodDetailWorkId}")
	String getLivelihoodDetailWorkId;
	
	@Value("${getProductionDetailWorkId}")
	String getProductionDetailWorkId;
	
	@Value("${getEPADetailWorkIdDraft}")
	String getEPADetailWorkIdDraft;
	
	@Value("${getLivelihoodDetailWorkIdDraft}")
	String getLivelihoodDetailWorkIdDraft;
	
	@Value("${getProductionDetailWorkIdDraft}")
	String getProductionDetailWorkIdDraft;
	
	@Value("${getAssetIdForEPACreation}")
	String getAssetIdForepaCreation;
	
	@Value("${getAssetIdForLivelihoodCreation}")
	String getAssetIdForLivelihoodCreation;
	
	@Value("${getAssetIdForProductionCreation}")
	String getAssetIdForProductionCreation;
	
	@Value("${deleteEPAAsset}")
	String deleteEPAAsset;
	
	@Value("${deleteLivelihoodAsset}")
	String deleteLivelihoodAsset;
	
	@Value("${deleteProductionAsset}")
	String deleteProductionAsset;
	
	@Value("${completeEPAAsset}")
	String completeEPAAsset;
	
	@Value("${completeLivelihoodAsset}")
	String completeLivelihoodAsset;
	
	@Value("${completeProductionAsset}")
	String completeProductionAsset;

	@Value("${getEPAWorkIdList}")
	String getEPAWorkIdList;
	
	@Value("${getLivelihoodWorkIdList}")
	String getLivelihoodWorkIdList;
	
	@Value("${getPrdouctionWorkIdList}")
	String getPrdouctionWorkIdList;
	
	@Value("${piafrowardEPAAsset}")
	String piafrowardEPAAsset;
	
	@Value("${piafrowardLivelihoodAsset}")
	String piafrowardLivelihoodAsset;
	
	@Value("${piafrowardProductionAsset}")
	String piafrowardProductionAsset;
	
	@Value("${getListOfAssetUserWiseEpa}")
	String getListOfAssetUserWiseEpa;
	
	@Value("${getPendngAssetAtWCDCProjectWiseEpa}")
	String getPendngAssetAtWCDCProjectWiseEpa;

	@Value("${getListOfAssetUserWiseLivelihood}")
	String getListOfAssetUserWiseLivelihood;
	
	@Value("${getPendngAssetAtWCDCProjectWiseLivelihood}")
	String getPendngAssetAtWCDCProjectWiseLivelihood;

	@Value("${getListOfAssetUserWiseProduction}")
	String getListOfAssetUserWiseProduction;
	
	@Value("${getPendngAssetAtWCDCProjectWiseProduction}")
	String getPendngAssetAtWCDCProjectWiseProduction;
	
	@Value("${wcdcCompleteEPA}")
	String wcdcCompleteEPA;
	
	@Value("${wcdcCompleteLivelihoodAsset}")
	String wcdcCompleteLivelihoodAsset;
	
	@Value("${wcdcCompleteProductionAsset}")
	String wcdcCompleteProductionAsset;
	
	@Value("${getCompletedAssetListEPAdp}")
	String getCompletedAssetListEPAdp;
	
	@Value("${getCompletedAssetListEPA}")
	String getCompletedAssetListEPA;
	
	@Value("${getCompletedAssetListLivelihooddp}")
	String getCompletedAssetListLivelihooddp;
	
	@Value("${getCompletedAssetListLivelihood}")
	String getCompletedAssetListLivelihood;
	
	@Value("${getCompletedAssetListPrdouctiondp}")
	String getCompletedAssetListPrdouctiondp;
	
	@Value("${getCompletedAssetListPrdouction}")
	String getCompletedAssetListPrdouction;
	
	@Value("${getforwardedAssetPIAEPA}")
	String getforwardedAssetPIAEPA;
	 
	@Value("${getforwardedAssetPIALivelihood}")
	String getforwardedAssetPIALivelihood;
	 
	@Value("${getforwardedAssetPIAPrdouction}")
	String getforwardedAssetPIAPrdouction;
	
	@Value("${getUserToForwardEPA}")
	String getUserToForwardEPA;
	
	@Value("${getEPAWorkIdDetails}")
	String getEPAWorkIdDetails;
	
	@Value("${getLivelihoodWorkidDetails}")
	String getLivelihoodWorkidDetails;
	
	@Value("${getProductionWorkidDetails}")
	String getProductionWorkidDetails;
	
	@Value("${getMEpaCoreactivityByproject}")
	String getMEpaCoreactivityByproject;
	
	@Value("${getMLiveCoreactivityByproject}")
	String getMLiveCoreactivityByproject;
	
	@Value("${getMProdCoreactivityByproject}")
	String getMProdCoreactivityByproject;
	
	@Override
	public List<EpaDetail> getEpaDetails(Integer projId, String scheme, Integer activity) {
		// TODO Auto-generated method stub
		List<EpaDetail> list=new ArrayList<EpaDetail>();
		String hql=getEPADetailWorkId;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setString("schm", scheme);
			query.setInteger("epaid", activity);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<LivelihoodDetail> getLivelihoodDetails(Integer projId, String scheme, Integer activity) {
		// TODO Auto-generated method stub
		List<LivelihoodDetail> list=new ArrayList<LivelihoodDetail>();
		String hql=getLivelihoodDetailWorkId;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setString("schm", scheme);
			query.setInteger("livid", activity);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<ProductionDetail> getProductionDetails(Integer projId, String scheme, Integer activity) {
		// TODO Auto-generated method stub
		List<ProductionDetail> list=new ArrayList<ProductionDetail>();
		String hql=getProductionDetailWorkId;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setString("schm", scheme);
			query.setInteger("prdid", activity);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyEpaWorkid> getEPAbyProjSchemeDraft(Integer projId, String finYr, Integer regId) {
		// TODO Auto-generated method stub
		List<WdcpmksyEpaWorkid> list=new ArrayList<WdcpmksyEpaWorkid>();
		String hql=getEPADetailWorkIdDraft;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setString("finYr", finYr);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}
	
	@Override
	public List<WdcpmksyLivelihoodWorkid> getLivelihoodbyProjSchemeDraft(Integer projId, String finYr, Integer regId) {
		// TODO Auto-generated method stub
		List<WdcpmksyLivelihoodWorkid> list=new ArrayList<WdcpmksyLivelihoodWorkid>();
		String hql=getLivelihoodDetailWorkIdDraft;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setString("finYr", finYr);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getProductionbyProjSchemeDraft(Integer projId, String finYr, Integer regId) {
		// TODO Auto-generated method stub
		List<WdcpmksyPrdouctionWorkid> list=new ArrayList<WdcpmksyPrdouctionWorkid>();
		String hql=getProductionDetailWorkIdDraft;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setString("finYr", finYr);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<AssetIdBean> getAssetIdForEPACreation(Integer activityCode) {
		// TODO Auto-generated method stub
		List<AssetIdBean> list=new ArrayList<AssetIdBean>();
		String hql=getAssetIdForepaCreation;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
			query.setInteger("activitycd", activityCode);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<AssetIdBean> getAssetIdForLivelihoodCreation(Integer activityCode) {
		// TODO Auto-generated method stub
		List<AssetIdBean> list=new ArrayList<AssetIdBean>();
		String hql=getAssetIdForLivelihoodCreation;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
			query.setInteger("activitycd", activityCode);
			list = query.list();
		
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<AssetIdBean> getAssetIdForProductionCreation(Integer activityCode) {
		// TODO Auto-generated method stub
		List<AssetIdBean> list=new ArrayList<AssetIdBean>();
		String hql=getAssetIdForProductionCreation;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
			query.setInteger("activitycd", activityCode);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public String saveEPALivelihoodProducttionAssetAsDraft(String finyr, Integer projcd, Integer[] aapid,
			Integer[] activity, Integer[] vcode, Integer loginid, String[] nearby, String[] areacov) {
		// TODO Auto-generated method stub
		String res="fail";
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Date d = new Date();
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String requestIp=inetAddress.getHostAddress();
				
				if(finyr.equals("epa")) 
				{
					WdcpmksyEpaWorkid asset = new WdcpmksyEpaWorkid();
					for(int i=0;i<activity.length;i++) 
					{
						asset = new WdcpmksyEpaWorkid();
						IwmpMProject proj = new IwmpMProject();
						proj.setProjectId(projcd);
						EpaDetail aap = new EpaDetail();
						aap.setEpaDetailId(aapid[i]);
						MEpaCoreactivity act = new MEpaCoreactivity();
						act.setEpaActivityId(activity[i]);
						IwmpVillage vill = new IwmpVillage();
						vill.setVcode(vcode[i]);
						UserReg userReg = (UserReg) session.get(UserReg.class, loginid);
						
						asset.setMEpaCoreactivity(act);
						asset.setIwmpMProject(proj);
						asset.setEpaDetail(aap);
						asset.setIwmpVillage(vill);
						asset.setStatus('D');
						asset.setCreatedon(d);
						asset.setCreatedby(userReg.getUserId());
						asset.setCurrentLevel('P');
						asset.setRequestIp(requestIp);
						asset.setNearby(nearby[i]);
						session.save(asset);
						
					}
				}
				
				if(finyr.equals("livelihood")) 
				{
					WdcpmksyLivelihoodWorkid asset = new WdcpmksyLivelihoodWorkid();
					for(int i=0;i<activity.length;i++) 
					{
						asset = new WdcpmksyLivelihoodWorkid();
						IwmpMProject proj = new IwmpMProject();
						proj.setProjectId(projcd);
						LivelihoodDetail aap = new LivelihoodDetail();
						aap.setLivelihoodDetailId(aapid[i]);
						MLivelihoodCoreactivity act = new MLivelihoodCoreactivity();
						act.setLivelihoodCoreactivityId(activity[i]);
						IwmpVillage vill = new IwmpVillage();
						vill.setVcode(vcode[i]);
						UserReg userReg = (UserReg) session.get(UserReg.class, loginid);
						
						asset.setMLivelihoodCoreactivity(act);
						asset.setIwmpMProject(proj);
						asset.setLivelihoodDetail(aap);
						asset.setIwmpVillage(vill);
						asset.setStatus('D');
						asset.setCurrentLevel('P');
						asset.setCreatedon(d);
						asset.setCreatedby(userReg.getUserId());
						asset.setRequestIp(requestIp);
						asset.setNearby(nearby[i]);
						session.save(asset);
						
					}
				}
				
				if(finyr.equals("production")) 
				{
					WdcpmksyPrdouctionWorkid asset = new WdcpmksyPrdouctionWorkid();
					for(int i=0;i<activity.length;i++) 
					{
						asset = new WdcpmksyPrdouctionWorkid();
						IwmpMProject proj = new IwmpMProject();
						proj.setProjectId(projcd);
						ProductionDetail aap = new ProductionDetail();
						aap.setProductionDetailId(aapid[i]);
						MProductivityCoreactivity act = new MProductivityCoreactivity();
						act.setProductivityCoreactivityId(activity[i]);
						IwmpVillage vill = new IwmpVillage();
						vill.setVcode(vcode[i]);
						UserReg userReg = (UserReg) session.get(UserReg.class, loginid);
						
						asset.setMProductivityCoreactivity(act);
						asset.setIwmpMProject(proj);
						asset.setProductionDetail(aap);
						asset.setIwmpVillage(vill);
						asset.setStatus('D');
						asset.setCurrentLevel('P');
						asset.setCreatedon(d);
						asset.setCreatedby(userReg.getUserId());
						asset.setRequestIp(requestIp);
						asset.setNearby(nearby[i]);
						if(areacov[i].equals("0"))
							asset.setAreaCovered(null);
						else
							asset.setAreaCovered(new BigDecimal(areacov[i]));
						session.save(asset);
					}
				}
				
				res="success";
			
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res="fail";
		} 
		catch(Exception ex){
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
	public String deleteAssetEPALivelihoodProducttion(Integer tempassetid, String finyr, Integer projcd) {
		
		String res="fail";
		String hql=deleteEPAAsset;
		String hqll=deleteLivelihoodAsset;
		String hqlp=deleteProductionAsset;
		int a=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			if(finyr.equals("epa")) 
			{
				Query query = session.createQuery(hql);
				query.setInteger("assetid", tempassetid);
				a = query.executeUpdate();
			}
			if(finyr.equals("livelihood")) 
			{
				Query query = session.createQuery(hqll);
				query.setInteger("assetid", tempassetid);
				a = query.executeUpdate();
			}
			if(finyr.equals("production")) 
			{
				Query query = session.createQuery(hqlp);
				query.setInteger("assetid", tempassetid);
				a = query.executeUpdate();
			}
			
			if(a>0) {
			
			res="success";
			}
			else {
				
				res="fail";
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
		finally {
			 session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public String completeAssetEPALivelihoodProducttion(List<BigInteger> tempassetid, String finyr, Integer projcd) {
		
		String res="fail";
		String hql=completeEPAAsset;
		String hqll=completeLivelihoodAsset;
		String hqlp=completeProductionAsset;
		int a=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			if(finyr.equals("epa")) 
			{
				Query query = session.createQuery(hql);
				query.setInteger("proj", projcd);
				a = query.executeUpdate();
			}
			if(finyr.equals("livelihood")) 
			{
				Query query = session.createQuery(hqll);
				query.setInteger("proj", projcd);
				a = query.executeUpdate();
			}
			if(finyr.equals("production")) 
			{
				Query query = session.createQuery(hqlp);
				query.setInteger("proj", projcd);
				a = query.executeUpdate();
			}
			
			if(a>0) {
			
				res="success";
			}
			else {
				
				res="fail";
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
		finally {
			 session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public List<LivelihoodPrdouctionEPAWorkIdBean> getLivelihoodPrdouctionEPAWorkIdList(Integer State, Integer district,
			Integer project, String scheme) {
		
		List<LivelihoodPrdouctionEPAWorkIdBean> result=new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				if(scheme.equals("epa"))
					hql =getEPAWorkIdList;
				
				if(scheme.equals("livelihood"))
					hql =getLivelihoodWorkIdList;
				
				if(scheme.equals("production"))
					hql =getPrdouctionWorkIdList;
				
				query = session.createSQLQuery(hql);
				query.setInteger("stcd",State);
				query.setInteger("dist", district);
				query.setInteger("proj", project);
				query.setResultTransformer(Transformers.aliasToBean(LivelihoodPrdouctionEPAWorkIdBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.getMessage();
			ex.getCause();
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.getTransaction().commit();
		}
		return result;
	}

	@Override
	public String forwardEPALivelihoodProducttionWCDC(List<BigInteger> tempassetid, Integer sentfrom, Integer sentto,
			HttpSession loginsess, String scheme, Integer projcd) {
		// TODO Auto-generated method stub
		String res="fail";
		String sql=null;
		Session session = sessionFactory.getCurrentSession();
		Date d = new Date();
		try {
			session.beginTransaction();
			if(scheme.equals("epa")) 
			{
				sql=piafrowardEPAAsset;
			}
			if(scheme.equals("livelihood")) 
			{
				sql=piafrowardLivelihoodAsset;
			}
			if(scheme.equals("production")) 
			{
				sql=piafrowardProductionAsset;
			}
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameterList("assetId", tempassetid);
			query.setInteger("sentto", sentto);
			query.setInteger("sentfrom", sentfrom);
			query.setString("updatedby", loginsess.getAttribute("loginID").toString());
			query.setTimestamp("updatedon", d);
			int a = query.executeUpdate();
			if(a>0) {
			
			res="success";
			}
			else {
				
				res="fail";
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
		finally {
			 session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public List<WdcpmksyEpaWorkid> getListOfAssetUserWiseEpa(Integer regId, String userType) {
		
		List<WdcpmksyEpaWorkid> list=new ArrayList<WdcpmksyEpaWorkid>();
		String hql=null;
		if(userType.equals("DI"))
		 hql=getListOfAssetUserWiseEpa;
		//if(userType.equals("SL"))
		//	 hql=getPendingListOfAssetSLNA;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyEpaWorkid> getPendngAssetAtWCDCProjectWiseEpa(Integer regId, String userType, Integer projId) {
		
		List<WdcpmksyEpaWorkid> list=new ArrayList<WdcpmksyEpaWorkid>();
		String hql="";
	//	if(userType.equals("SL"))
	//	hql=getPendingListOfAssetSLNAProjectWise;
		if(userType.equals("DI"))
			hql=getPendngAssetAtWCDCProjectWiseEpa;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("projId", projId);
		//	if(userType.equals("SL"))
		//	query.setInteger("dCode", dCode);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getListOfAssetUserWiseLivelihood(Integer regId, String userType) {
		
		List<WdcpmksyLivelihoodWorkid> list=new ArrayList<WdcpmksyLivelihoodWorkid>();
		String hql=null;
		if(userType.equals("DI"))
		 hql=getListOfAssetUserWiseLivelihood;
	//	if(userType.equals("SL"))
	//		 hql=getPendingListOfAssetSLNA;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getPendngAssetAtWCDCProjectWiseLivelihood(Integer regId, String userType,
			Integer projId) {
		
		List<WdcpmksyLivelihoodWorkid> list=new ArrayList<WdcpmksyLivelihoodWorkid>();
		String hql="";
	//	if(userType.equals("SL"))
	//	hql=getPendingListOfAssetSLNAProjectWise;
		if(userType.equals("DI"))
			hql=getPendngAssetAtWCDCProjectWiseLivelihood;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("projId", projId);
		//	if(userType.equals("SL"))
		//	query.setInteger("dCode", dCode);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getListOfAssetUserWiseProduction(Integer regId, String userType) {
		
		List<WdcpmksyPrdouctionWorkid> list=new ArrayList<WdcpmksyPrdouctionWorkid>();
		String hql=null;
		if(userType.equals("DI"))
		 hql=getListOfAssetUserWiseProduction;
	//	if(userType.equals("SL"))
	//		 hql=getPendingListOfAssetSLNA;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getPendngAssetAtWCDCProjectWiseProduction(Integer regId, String userType,
			Integer projId) {
		
		List<WdcpmksyPrdouctionWorkid> list=new ArrayList<WdcpmksyPrdouctionWorkid>();
		String hql="";
	//	if(userType.equals("SL"))
	//	hql=getPendingListOfAssetSLNAProjectWise;
		if(userType.equals("DI"))
			hql=getPendngAssetAtWCDCProjectWiseProduction;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("projId", projId);
		//	if(userType.equals("SL"))
		//	query.setInteger("dCode", dCode);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public String completeAsset(Integer sentfrom, List<BigInteger> tempassetid, String scheme) {
		
		String res="fail";
		String sql=null;
		Session session = sessionFactory.getCurrentSession();
		Date d = new Date();
		try {
			session.beginTransaction();
			if(scheme.equals("epa")) 
			{
				sql=wcdcCompleteEPA;
			}
			if(scheme.equals("livelihood")) 
			{
				sql=wcdcCompleteLivelihoodAsset;
			}
			if(scheme.equals("production")) 
			{
				sql=wcdcCompleteProductionAsset;
			}
			UserReg userReg = (UserReg) session.get(UserReg.class, sentfrom);
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameterList("assetId", tempassetid);
			query.setInteger("sentto", sentfrom);
			query.setInteger("sentfrom", sentfrom);
			query.setString("updatedby", userReg.getUserId());
			query.setTimestamp("updatedon", d);
			int a = query.executeUpdate();
			if(a>0) {
			res="success";
			}
			else {
				res="fail";
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
		finally {
			 session.getTransaction().commit();
		}
		return res;
	
	}

	@Override
	public String rejectAssetbyWCDC(Integer sentfrom, List<BigInteger> assetid, List<String> remarks, String scheme) {
		
		String res="";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress(); 
			Date senton = new Date();
			Integer i=0;
			if(scheme.equals("epa")) 
			{
				for(BigInteger id :assetid) {
				WdcpmksyEpaWorkid assetTemp = (WdcpmksyEpaWorkid) session.get(WdcpmksyEpaWorkid.class, id.intValue());
				UserReg iwmpUserRegBySentfrom = (UserReg) session.get(UserReg.class, sentfrom);
				assetTemp.setIwmpUserRegBySentTo(null);
				assetTemp.setIwmpUserRegBySentFrom(iwmpUserRegBySentfrom);
				assetTemp.setUpdatedby(iwmpUserRegBySentfrom.getUserId());
				assetTemp.setUpdatedon(senton);
				assetTemp.setRequestIp(requestIp);
				assetTemp.setActionForward('R');
				assetTemp.setCurrentLevel('P');
				assetTemp.setRemarks(remarks.get(i));
				
				session.update(assetTemp);
				i++;
			}
			res="successWCDC";
			}
			
			if(scheme.equals("livelihood")) 
			{
				for(BigInteger id :assetid) {
					WdcpmksyLivelihoodWorkid assetTemp = (WdcpmksyLivelihoodWorkid) session.get(WdcpmksyLivelihoodWorkid.class, id.intValue());
					UserReg iwmpUserRegBySentfrom = (UserReg) session.get(UserReg.class, sentfrom);
					assetTemp.setIwmpUserRegBySentTo(null);
					assetTemp.setIwmpUserRegBySentFrom(iwmpUserRegBySentfrom);
					assetTemp.setUpdatedby(iwmpUserRegBySentfrom.getUserId());
					assetTemp.setUpdatedon(senton);
					assetTemp.setRequestIp(requestIp);
					assetTemp.setActionForward('R');
					assetTemp.setCurrentLevel('P');
					assetTemp.setRemarks(remarks.get(i));
					
					session.update(assetTemp);
					i++;
				}
				res="successWCDC";
			
			}
			
			if(scheme.equals("production")) 
			{
				for(BigInteger id :assetid) {
					WdcpmksyPrdouctionWorkid assetTemp = (WdcpmksyPrdouctionWorkid) session.get(WdcpmksyPrdouctionWorkid.class, id.intValue());
					UserReg iwmpUserRegBySentfrom = (UserReg) session.get(UserReg.class, sentfrom);
					assetTemp.setIwmpUserRegBySentTo(null);
					assetTemp.setIwmpUserRegBySentFrom(iwmpUserRegBySentfrom);
					assetTemp.setUpdatedby(iwmpUserRegBySentfrom.getUserId());
					assetTemp.setUpdatedon(senton);
					assetTemp.setRequestIp(requestIp);
					assetTemp.setActionForward('R');
					assetTemp.setCurrentLevel('P');
					assetTemp.setRemarks(remarks.get(i));
					
					session.update(assetTemp);
					i++;
				}
				res="successWCDC";
			}
			
			
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res="failWCDC";
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
			res="failWCDC";
		}
		finally {
			 session.getTransaction().commit();
		}
		return res;
	}

	@Override
	public List<WdcpmksyEpaWorkid> getCompletedAssetListEPA(String userId, Integer projId, Integer regid) {
		
		List<WdcpmksyEpaWorkid> list=new ArrayList<WdcpmksyEpaWorkid>();
		String hql=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			if(projId.equals(0)) {
				hql=getCompletedAssetListEPAdp;
				list = session.createQuery(hql).setInteger("regId", regid).list();
			}
			else {
				hql=getCompletedAssetListEPA;
				list = session.createQuery(hql).setInteger("projId", projId).list();
			}
			

		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getCompletedAssetListLivelihood(String userId, Integer projId, Integer regid) {
		// TODO Auto-generated method stub
		List<WdcpmksyLivelihoodWorkid> list=new ArrayList<WdcpmksyLivelihoodWorkid>();
		String hql=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();

			if(projId.equals(0)) {
				hql=getCompletedAssetListLivelihooddp;
				list = session.createQuery(hql).setInteger("regId", regid).list();
			}
			else {
				hql=getCompletedAssetListLivelihood;
				list = session.createQuery(hql).setInteger("projId", projId).list();
			}

		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getCompletedAssetListProduction(String userId, Integer projId, Integer regid) {
		// TODO Auto-generated method stub
		List<WdcpmksyPrdouctionWorkid> list=new ArrayList<WdcpmksyPrdouctionWorkid>();
		String hql=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();

			if(projId.equals(0)) 
			{
				hql=getCompletedAssetListPrdouctiondp;
				list = session.createQuery(hql).setInteger("regId", regid).list();
			}
			else {
				hql=getCompletedAssetListPrdouction;
				list = session.createQuery(hql).setInteger("projId", projId).list();
			}
		  
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyEpaWorkid> getviewforwardedAssetEPA(Integer regId, String userType, String userid,
			Integer projcd) {
		
		Session session = sessionFactory.getCurrentSession();
		List<WdcpmksyEpaWorkid> list=new ArrayList<WdcpmksyEpaWorkid>();
		String hql="";
		//System.out.println(userType);
	//	if(userType.equals("DI"))
	//	hql=getforwardedAssetWCDC;
		if(userType.equals("PI"))
			hql=getforwardedAssetPIAEPA;
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("projId", projcd);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getviewforwardedAssetLivelihood(Integer regId, String userType, String userid,
			Integer projcd) {
		
		List<WdcpmksyLivelihoodWorkid> list=new ArrayList<WdcpmksyLivelihoodWorkid>();
		String hql="";
		//System.out.println(userType);
	//	if(userType.equals("DI"))
	//	hql=getforwardedAssetWCDC;
		if(userType.equals("PI"))
			hql=getforwardedAssetPIALivelihood;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("projId", projcd);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getviewforwardedAssetProduction(Integer regId, String userType, String userid,
			Integer projcd) {
		
		List<WdcpmksyPrdouctionWorkid> list=new ArrayList<WdcpmksyPrdouctionWorkid>();
		String hql="";
		//System.out.println(userType);
	//	if(userType.equals("DI"))
	//	hql=getforwardedAssetWCDC;
		if(userType.equals("PI"))
			hql=getforwardedAssetPIAPrdouction;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("projId", projcd);
			list = query.list();
			
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
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId) {
		// TODO Auto-generated method stub
		String sql=getUserToForwardEPA;
		Session session = sessionFactory.getCurrentSession();
		List<PhysicalActionPlanBean> list = new ArrayList<PhysicalActionPlanBean>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			SQLQuery query= session.createSQLQuery(sql);
			query.setParameter("regId",	regId);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActionPlanBean.class));
			list = query.list();
			for(PhysicalActionPlanBean bean : list) {
				map.put(bean.getRegid(), (bean.getDistname()+"("+bean.getUserid()+")"));
			}
			
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
		finally {
			 session.getTransaction().commit();
		}
		return map;
	}

	@Override
	public List<WdcpmksyEpaWorkid> getEPAWorkId(int epaActivityId, int projId) {
		List<WdcpmksyEpaWorkid> list = new ArrayList<WdcpmksyEpaWorkid>();
		String hql=getEPAWorkIdDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("epaActivityId", epaActivityId);
			query.setInteger("projId", projId);
			list = query.list();
			
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			 session.getTransaction().commit();
		
		}
		return list;
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getLivelihoodWorkid(int livelihoodCoreactivityId, int projId) {
		List<WdcpmksyLivelihoodWorkid> list = new ArrayList<WdcpmksyLivelihoodWorkid>();
		String hql=getLivelihoodWorkidDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("livelihoodActivityId", livelihoodCoreactivityId);
			query.setInteger("projId", projId);
			list = query.list();
			
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			 session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getProductionWorkId(int productivityCoreactivityId, int projId) {
		List<WdcpmksyPrdouctionWorkid> list = new ArrayList<WdcpmksyPrdouctionWorkid>();
		String hql=getProductionWorkidDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("productionActivityId", productivityCoreactivityId);
			query.setInteger("projId", projId);
			list = query.list();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
		
			session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public LinkedHashMap<Integer, String> getActivityEPALivelihoodProduction(Integer projId, String headcd) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String sql=null;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			if(headcd.equals("epa"))
				sql=getMEpaCoreactivityByproject;
			
			if(headcd.equals("livelihood"))
				sql=getMLiveCoreactivityByproject;
			
			if(headcd.equals("production"))
				sql=getMProdCoreactivityByproject;
			
			//Query query = session.createQuery(hql);
			SQLQuery query= session.createSQLQuery(sql);
			query.setInteger("projcd", projId);
			Iterator itr = query.list().iterator();
			
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
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

	

}
