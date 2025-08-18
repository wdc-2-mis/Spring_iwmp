package app.daoImpl;

import java.math.BigInteger;
import java.net.InetAddress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.bean.AssetIdBean;
import app.bean.PhysicalActBean;
import app.bean.PhysicalActionPlanBean;
import app.dao.CreateAssetIdDao;
import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;
import app.model.IwmpProjectAssetStatus;
import app.model.UserMap;
import app.model.UserReg;
import app.model.WdcpmksyProjectAssetEPAStatus;
import app.model.WdcpmksyProjectAssetLivelihoodStatus;
import app.model.WdcpmksyProjectAssetProductionStatus;
import app.model.master.IwmpBlock;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.project.IwmpProjectLocation;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.IwmpProjectPhysicalAssetTemp;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyPrdouctionWorkid;

@Repository("CreateAssetIdDao")
public class CreateAssetIdDaoImpl implements CreateAssetIdDao{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getActionPlanForAssetId}")
	String getActionPlanForAssetId;
	
	@Value("${getActionPlanForAssetIdTemp}")
	String getActionPlanForAssetIdTemp;
	
	@Value("${getActionPlanForAssetIdAsset}")
	String getActionPlanForAssetIdAsset;
	
	@Value("${getAssetIdForCreation}")
	String getAssetIdForCreation;
	
	@Value("${getBlockFromProjectLocation}")
	String getBlockFromProjectLocation;
	
	@Value("${getVillageFromProjectLocationBlockWise}")
	String getVillage;
	
	
	@Value("${getDraftPlanDetailsByProjYr}")
	String getDraftPlanDetails;
	
	@Value("${deleteAsset}")
	String deleteAsset;
	
	@Value("${forwardAssetPIA}")
	String forwardAssetPIA;
	
	@Value("${getUserToForward}")
	String getUserToForward;

	@Value("${getassetlist}")
	String getassetlist;
	
	@Value("${getassetlistkd}")
	String getassetlistkd;
	
	@Value("${getforwardedAssetWCDC}")
	String getforwardedAssetWCDC;
	
	@Value("${getforwardedAssetPIA}")
	String getforwardedAssetPIA;
	
	@Value("${getPendingListOfAssetWCDC}")
	String getPendingListOfAssetWCDC;
	
	@Value("${getPendingListOfAssetWCDCProjectWise}")
	String getPendingListOfAssetWCDCProjectWise;
	
	@Value("${getPendingListOfAssetSLNA}")
	String getPendingListOfAssetSLNA;
	
	@Value("${getSentToSLNAForAsset}")
	String getSentToSLNAForAsset;
	
	@Value("${deleteCompletedAssetFromTemp}")
	String deleteCompletedAssetFromTemp;
	
	@Value("${getRejectedAssetList}")
	String getRejectedAssetList;
	
	@Value("${getfinalassetlist}")
	String getfinalassetlist;
	
	@Value("${projectlistbyredid}")
	String projectlistbyredid;
	
	@Value("${getfinalassetdilist}")
	String getfinalassetdilist;
	
	@Value("${getProjectForPendingAsset}")
	String getProjectForPendingAsset;
	
	@Value("${getProjectForForwardedAsset}")
	String getProjectForForwardedAsset;
	
	@Value("${getProjectFromDistrictForPendingAsset}")
	String getProjectFromDistrictForPendingAsset;
	
	@Value("${getPendingListOfAssetSLNAProjectWise}")
	String getPendingListOfAssetSLNAProjectWise;
	
	@Value("${getforwardedAssetUserWiseForProject}")
	String getforwardedAssetUserWiseForProject;
	
	@Value("${getSubactivityByActivityCode}")
	String getSubactivityByActivityCode;
	
	@Value("${checkAssetStatus}")
	String checkAssetStatus;
	
	
	@Value("${checkAssetLivelihoodStatus}")
	String checkAssetLivelihoodStatus;
	
	@Value("${checkAssetProductionStatus}")
	String checkAssetProductionStatus;
	
	@Value("${checkAssetEPAStatus}")
	String checkAssetEPAStatus;
	
	@Value("${deleteAssetStatus}")
	String deleteAssetStatus;
	
	@Value("${deletelivelihooddata}")
	String deletelivelihooddata;
	
	@Value("${deleteproductiondata}")
	String deleteproductiondata;
	
	@Value("${deleteepadata}")
	String deleteepadata;
	
	@Value("${getfinalWorkWiselist}")
	String getfinalWorkWiselist;    
	
	@Value("${getfinalHeadWiselist}")
	String getfinalHeadWiselist;
	
	@Value("${getfinalProdHeadWiselist}")
	String getfinalProdHeadWiselist;
	
	@Value("${getfinalEPAHeadWiselist}")
	String getfinalEPAHeadWiselist;
	
	@Value("${getcompletelivelihoodasset}")
	String getcompletelivelihoodasset;
	
	@Value("${getcompleteassetdata}")
	String getcompleteassetdata;
	
	@Value("${getforclosedassetdata}")
	String getforclosedassetdata;
	
	@Value("${getcompleteproductionasset}")
	String getcompleteproductionasset;
	
	@Value("${getcompleteepaasset}")
	String getcompleteepaasset;

	@Value("${getforclosedlivelihoodasset}")
	String getforclosedlivelihoodasset;
	
	@Value("${getforclosedproductionasset}")
	String getforclosedproductionasset;
	
	@Value("${getforclosedepaasset}")
	String getforclosedepaasset;
	
	@Value("${getAllFinaicialYear}")
	String getAllFinaicialYear;
	
	@Value("${getMonthWiseDetailData}")
	String getMonthWiseDetailData;
	
	@Value("${getMEpaCoreactivity}")
	String getMEpaCoreactivity;
	
	@Value("${getMLiveCoreactivity}")
	String getMLiveCoreactivity;
	
	@Value("${getMProdCoreactivity}")
	String getMProdCoreactivity;
	
	@Value("${getNrmNotStartedData}")
	String getNrmNotStartedData;
	
	@Value("${getNrmData}")
	String getNrmData;
	
	@Value("${getLivelihoodNotStartedData}")
	String getLivelihoodNotStartedData;
	
	@Value("${getLivelihoodData}")
	String getLivelihoodData;
	
	@Value("${getProductionNotStartedData}")
	String getProductionNotStartedData;
	
	@Value("${getProductionData}")
	String getProductionData;
	
	@Value("${getEPANotStartedData}")
	String getEPANotStartedData;
	
	@Value("${getEPAData}")
	String getEPAData;
	
	@Value("${checkQueryStr}")
	String checkQueryStr;
	
	@Value("${secondQueryStr}")
	String secondQueryStr;
	
	@Value("${thirdQueryStr}")
	String thirdQueryStr;
	
	@Value("${checkLiveQueryStr}")
	String checkLiveQueryStr;
	
	@Value("${secondLivQueryStr}")
	String secondLivQueryStr;
	
	@Value("${thirdLivQueryStr}")
	String thirdLivQueryStr;
	
	@Value("${checkEPAQueryStr}")
	String checkEPAQueryStr;
	
	@Value("${secondEPAQueryStr}")
	String secondEPAQueryStr;
	
	@Value("${thirdEPAQueryStr}")
	String thirdEPAQueryStr;
	
	@Value("${checkProdQueryStr}")
	String checkProdQueryStr;
	
	@Value("${secondProdQueryStr}")
	String secondProdQueryStr;
	
	@Value("${thirdProdQueryStr}")
	String thirdProdQueryStr;
	
	@Override
	public List<IwmpProjectPhysicalAap> getActionPlan(Integer projId, Integer finYr) {
		// TODO Auto-generated method stub
		List<IwmpProjectPhysicalAap> list=new ArrayList<IwmpProjectPhysicalAap>();
		String hql=getActionPlanForAssetId;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setInteger("finYr", finYr);
			list = query.list();
			
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
	public List<AssetIdBean> getAssetIdForCreation(Integer activityCode) {
		// TODO Auto-generated method stub
		List<AssetIdBean> list=new ArrayList<AssetIdBean>();
		String hql=getAssetIdForCreation;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
			query.setInteger("activitycd", activityCode);
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
	public List<IwmpBlock> getBlockFromProjectLocation(Integer projId) {
		// TODO Auto-generated method stub
		List<IwmpProjectLocation> list=new ArrayList<IwmpProjectLocation>();
		List<IwmpBlock> listBlock=new ArrayList<IwmpBlock>();
		IwmpBlock block = new IwmpBlock();
		List<Integer> blockCodeList = new ArrayList<Integer>();
		String hql=getBlockFromProjectLocation;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			list = query.list();
			for(IwmpProjectLocation bean : list) {
				block = new IwmpBlock();
				
				block.setBcode(bean.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
				block.setBlockName(bean.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				if(!blockCodeList.contains(bean.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode())) {
					blockCodeList.add(bean.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
					listBlock.add(block);
				}
				
			}
			System.out.println("list"+listBlock.size());
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
		return listBlock;
	}

	@Override
	public LinkedHashMap<Integer, String> getVillageFromProjectLocationBlockWise(Integer projId, Integer bCode) {
		// TODO Auto-generated method stub
		List<IwmpProjectLocation> list=new ArrayList<IwmpProjectLocation>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql=getVillage;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setInteger("bCode", bCode);
			list = query.list();
			for(IwmpProjectLocation pl : list) {
				if (!map.containsKey(pl.getIwmpVillage().getVcode())) {
					map.put(pl.getIwmpVillage().getVcode(), pl.getIwmpVillage().getVillageName());
				} else {
					
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
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public String saveAssetAsDraft(Integer finyr, Integer projcd, Integer[] aapid, Integer[] activity,
			Integer[] vcode,Integer sentFrom,Integer[] subactivity, String[] landid) {
		// TODO Auto-generated method stub
		String res="fail";
		Session session = sessionFactory.getCurrentSession();
		try {
			
			Date d = new Date();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress();
			IwmpProjectPhysicalAssetTemp asset = new IwmpProjectPhysicalAssetTemp();
			for(int i=0;i<activity.length;i++) {
				session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				asset = new IwmpProjectPhysicalAssetTemp();
				IwmpMFinYear year = new IwmpMFinYear();
				year.setFinYrCd(finyr);
				IwmpMProject proj = new IwmpMProject();
				proj.setProjectId(projcd);
				IwmpProjectPhysicalAap aap = new IwmpProjectPhysicalAap();
				aap.setAapid(aapid[i]);
				IwmpMPhyActivity act = new IwmpMPhyActivity();
				act.setActivityCode(activity[i]);
				IwmpVillage vill = new IwmpVillage();
				vill.setVcode(vcode[i]);
				UserReg userReg = (UserReg) session.get(UserReg.class, sentFrom);
				asset.setIwmpMFinYear(year);
				asset.setIwmpMPhyActivity(act);
				asset.setIwmpMProject(proj);
				asset.setIwmpProjectPhysicalAap(aap);
				asset.setIwmpVillage(vill);
				asset.setStatus('D');
				try
				{
					asset.setNearby(landid[i]);
				}
				catch(Exception ex) {
					asset.setNearby(null);
				}
				
				asset.setCreatedon(d);
				asset.setCreatedby(userReg.getUserId());
				asset.setRequestIp(requestIp);
				asset.setCurrentLevel('P');
				IwmpMPhySubactivity sub = new IwmpMPhySubactivity();
				if(subactivity[i]>0) {
				sub.setSubActivityCode(subactivity[i]);
				asset.setIwmpMPhySubactivity(sub);
				}
				session.save(asset);
				session.getTransaction().commit();
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
		return res;
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getDraftPlanDetailsByProjYr(Integer projId, Integer finYr,Integer regId) {
		// TODO Auto-generated method stub
		List<IwmpProjectPhysicalAssetTemp> list=new ArrayList<IwmpProjectPhysicalAssetTemp>();
		String hql=getDraftPlanDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setInteger("finYr", finYr);
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
	public String deleteAsset(Integer tempassetid) {
		// TODO Auto-generated method stub
		String res="fail";
		String hql=deleteAsset;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("assetid", tempassetid);
			int a = query.executeUpdate();
			if(a>0) {
			session.getTransaction().commit();
			res="success";
			}
			else {
				session.getTransaction().rollback();
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
		return res;
	}
	
	@Override
	public String forwardAssetToWCDC(List<BigInteger> tempassetid,Integer sentfrom,Integer sentto,String createdby) {
		// TODO Auto-generated method stub
		String res="fail";
		String hql=forwardAssetPIA;
		Session session = sessionFactory.getCurrentSession();
		Date d = new Date();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameterList("assetId", tempassetid);
			query.setInteger("sentto", sentto);
			query.setInteger("sentfrom", sentfrom);
			query.setString("updatedby", createdby);
			query.setTimestamp("updatedon", d);
			int a = query.executeUpdate();
			if(a>0) {
			session.getTransaction().commit();
			res="success";
			}
			else {
				session.getTransaction().rollback();
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
		return res;
	}


	@Override
	public List<IwmpProjectPhysicalAsset> getassetList(String userId) {
		List<IwmpProjectPhysicalAsset> list=new ArrayList<IwmpProjectPhysicalAsset>();
		String hql=getassetlist;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();

			list = session.createQuery(hql).setParameter("userId", userId).list();
		    

			session.getTransaction().commit();

		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return list;
	}


	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId) {
		// TODO Auto-generated method stub
		String sql=getUserToForward;
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
	public List<IwmpProjectPhysicalAssetTemp> getforwardedAssetUserWise(Integer regId,String userType,String userid) {
		// TODO Auto-generated method stub
		List<IwmpProjectPhysicalAssetTemp> list=new ArrayList<IwmpProjectPhysicalAssetTemp>();
		String hql="";
		System.out.println(userType);
		if(userType.equals("DI"))
		hql=getforwardedAssetWCDC;
		if(userType.equals("PI"))
			hql=getforwardedAssetPIA;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setString("userid", userid);
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
	public List<IwmpProjectPhysicalAssetTemp> getListOfAssetUserWise(Integer regId,String userType) {
		// TODO Auto-generated method stub
		List<IwmpProjectPhysicalAssetTemp> list=new ArrayList<IwmpProjectPhysicalAssetTemp>();
		String hql=null;
		if(userType.equals("DI"))
		 hql=getPendingListOfAssetWCDC;
		if(userType.equals("SL"))
			 hql=getPendingListOfAssetSLNA;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
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
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}
	
	@Override
	public List<IwmpProjectPhysicalAssetTemp> getPendngAssetAtWCDCProjectWise(Integer regId,String userType,Integer projId,Integer dCode) {
		// TODO Auto-generated method stub
		List<IwmpProjectPhysicalAssetTemp> list=new ArrayList<IwmpProjectPhysicalAssetTemp>();
		String hql="";
		if(userType.equals("SL"))
		hql=getPendingListOfAssetSLNAProjectWise;
		if(userType.equals("DI"))
			hql=getPendingListOfAssetWCDCProjectWise;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			query.setInteger("projId", projId);
			if(userType.equals("SL"))
			query.setInteger("dCode", dCode);
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
	public String forwardAssettoSLNA(Integer sentfrom, List<BigInteger> assetid,Integer sentto) {
		// TODO Auto-generated method stub
		String res="";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress(); 
			Date senton = new Date();
			for(BigInteger id :assetid) {
				IwmpProjectPhysicalAssetTemp assetTemp = (IwmpProjectPhysicalAssetTemp) session.get(IwmpProjectPhysicalAssetTemp.class, id);
				UserReg iwmpUserRegBySentfrom = (UserReg) session.get(UserReg.class, sentfrom);
				UserReg iwmpUserRegBySentTo = (UserReg) session.get(UserReg.class, sentto);
				
				assetTemp.setUpdatedby(iwmpUserRegBySentfrom.getUserId());
				assetTemp.setUpdatedon(senton);
				assetTemp.setRequestIp(requestIp);
				assetTemp.setAction('F');
				assetTemp.setCurrentLevel('S');
				assetTemp.setIwmpUserRegBySentTo(iwmpUserRegBySentTo);
				assetTemp.setIwmpUserRegBySentFrom(iwmpUserRegBySentfrom);
				
				session.update(assetTemp);
			}
			res="successWCDC";
			
			session.getTransaction().commit();
			
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
		return res;
	}
	
	@Override
	public Integer getSentToSLNAForAsset(Integer sentfrom) {
		Integer sentTo=0;
		String hql=getSentToSLNAForAsset;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("regId", sentfrom);
			sentTo = Integer.parseInt(query.list().get(0).toString());
			
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
		return sentTo;
	}

	@Override
	public String rejectAssetbyWCDC(Integer sentfrom, List<BigInteger> assetid,List<String> remarks) {
		// TODO Auto-generated method stub
		String res="";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress(); 
			Date senton = new Date();
			Integer i=0;
			for(BigInteger id :assetid) {
				IwmpProjectPhysicalAssetTemp assetTemp = (IwmpProjectPhysicalAssetTemp) session.get(IwmpProjectPhysicalAssetTemp.class, id);
				UserReg iwmpUserRegBySentfrom = (UserReg) session.get(UserReg.class, sentfrom);
				assetTemp.setIwmpUserRegBySentTo(null);
				assetTemp.setIwmpUserRegBySentFrom(iwmpUserRegBySentfrom);
				assetTemp.setUpdatedby(iwmpUserRegBySentfrom.getUserId());
				assetTemp.setUpdatedon(senton);
				assetTemp.setRequestIp(requestIp);
				assetTemp.setAction('R');
				assetTemp.setCurrentLevel('P');
				assetTemp.setRemarks(remarks.get(i));
				
				session.update(assetTemp);
				i++;
				
				
			}
			res="successWCDC";
			
			session.getTransaction().commit();
			
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
		return res;
	}

	@Override
	public String completeAsset(Integer sentfrom, List<BigInteger> tempassetid, Integer sentto) {
		// TODO Auto-generated method stub
		String res="";
		String hql=deleteCompletedAssetFromTemp;
		Session session = sessionFactory.getCurrentSession();
		List<BigInteger> assetid = new ArrayList<BigInteger>();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress(); 
			Date senton = new Date();
			for(BigInteger id :tempassetid) {
				IwmpProjectPhysicalAssetTemp assetTemp = (IwmpProjectPhysicalAssetTemp) session.get(IwmpProjectPhysicalAssetTemp.class, id);
				UserReg iwmpUserRegBySentfrom = (UserReg) session.get(UserReg.class, sentfrom);
				IwmpProjectPhysicalAsset asset = new IwmpProjectPhysicalAsset();
				
				
				asset.setIwmpMFinYear(assetTemp.getIwmpMFinYear());
				asset.setIwmpMProject(assetTemp.getIwmpMProject());
				asset.setIwmpProjectPhysicalAap(assetTemp.getIwmpProjectPhysicalAap());
				asset.setIwmpMPhyActivity(assetTemp.getIwmpMPhyActivity());
				asset.setIwmpVillage(assetTemp.getIwmpVillage());
				asset.setStatus('C');
				asset.setCreatedby(iwmpUserRegBySentfrom.getUserId());
				asset.setNearby(assetTemp.getNearby());
				asset.setCreatedon(assetTemp.getCreatedon());
				asset.setUpdatedby(iwmpUserRegBySentfrom.getUserId());
				asset.setUpdatedon(senton);
				asset.setRequestIp(requestIp);
				asset.setIwmpMPhySubactivity(assetTemp.getIwmpMPhySubactivity());
				
				session.save(asset);
				if(!assetid.contains(assetTemp.getTempassetid())) {
					assetid.add(assetTemp.getTempassetid());
				}
				//session.delete(assetTemp);
				
			}
			//session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameterList("assetid", assetid);
			Integer a = query.executeUpdate();
			if(a>0) {
			res="successSLNA";
			session.getTransaction().commit();
			}else {
			res="failSLNA";
			session.getTransaction().rollback();
			}
			
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res="failSLNA";
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
			res="failSLNA";
		}
		return res;
	}
	
	@Override
	public String rejectAssetbySLNA(Integer sentfrom, List<BigInteger> assetid,List<String> remarks) {
		// TODO Auto-generated method stub
		String res="";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress(); 
			Date senton = new Date();
			Integer i=0;
			for(BigInteger id :assetid) {
				IwmpProjectPhysicalAssetTemp assetTemp = (IwmpProjectPhysicalAssetTemp) session.get(IwmpProjectPhysicalAssetTemp.class, id);
				UserReg iwmpUserRegBySentfrom = (UserReg) session.get(UserReg.class, sentfrom);
				assetTemp.setIwmpUserRegBySentTo(null);
				assetTemp.setIwmpUserRegBySentFrom(iwmpUserRegBySentfrom);
				assetTemp.setUpdatedby(iwmpUserRegBySentfrom.getUserId());
				assetTemp.setUpdatedon(senton);
				assetTemp.setRequestIp(requestIp);
				assetTemp.setAction('R');
				assetTemp.setCurrentLevel('P');
				assetTemp.setRemarks(remarks.get(i));
				session.update(assetTemp);
				i++;
				
				
				
			}
			res="successSLNA";
			
			session.getTransaction().commit();
			
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res="failSLNA";
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
			res="failSLNA";
		}
		return res;
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getRejectedAssetList(String userId) {
		// TODO Auto-generated method stub
		String hql=getRejectedAssetList;
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("loginid", userId);
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
	public List<IwmpProjectPhysicalAsset> getfinalassetList(Integer dCode, Integer pCode, String userId) {
		List<IwmpProjectPhysicalAsset> list=new ArrayList<IwmpProjectPhysicalAsset>();
		String hql=getfinalassetlist;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
				  query.setString("userId", userId);		
			      query.setInteger("pCode", pCode);
			      query.setInteger("dCode", dCode);
				  list = query.list();
			     session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return list;
		
	}

	@Override
	public LinkedHashMap<Integer, String> getListOfProjects(Integer regId) {
		
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpProjectPhysicalAsset> rows = new ArrayList<IwmpProjectPhysicalAsset>();
		String hql=projectlistbyredid;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId",regId);
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
			}
			
			
			/*
			 * rows = query.list(); for(IwmpProjectPhysicalAsset row : rows){
			 * 
			 * map.put(row.getIwmpMProject().getProjectId(),
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
	public List<IwmpProjectPhysicalAsset> getfinalassetdiList(Integer pCode, String userId) {
		List<IwmpProjectPhysicalAsset> list=new ArrayList<IwmpProjectPhysicalAsset>();
		String hql=getfinalassetdilist;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
				  query.setString("userId", userId);		
			      query.setInteger("pCode", pCode);
			     list = query.list();
			     session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		return list;
	}
	
	@Override
	public List<IwmpMProject> getProjectForPendingAsset(Integer regId) {
		// TODO Auto-generated method stub
		String hql=getProjectForPendingAsset;
		List<IwmpMProject> list = new ArrayList<IwmpMProject>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
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
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getProjectFromDistrictForPendingAsset(Integer dCode,Integer regId) {
		// TODO Auto-generated method stub
		String hql=getProjectFromDistrictForPendingAsset;
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("dCode", dCode);
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
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}
	
	@Override
	public List<IwmpMProject> getProjectForForwardedAsset(Integer regId,String userType,String userId) {
		// TODO Auto-generated method stub
		String hql=getProjectForForwardedAsset;
		List<IwmpMProject> list = new ArrayList<IwmpMProject>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			//query.setString("userType", userType);
			query.setString("userid", userId);
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
	public List<IwmpProjectPhysicalAssetTemp> getforwardedAssetUserWiseForProject(Integer regId, String userType,
			Integer projId,Integer dCode) {
		// TODO Auto-generated method stub
		String hql=getforwardedAssetUserWiseForProject;
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("regId", regId);
			//query.setString("userType", userType);
			query.setInteger("projId",projId);
			query.setInteger("dCode",dCode);
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
		finally {
			
		}
		return list;
	}

	@Override
	@Transactional
	public String saveAssetStatus(HttpServletRequest request,  String[] assetid,  String projid, String finYear, String finalAssetid) {
		String res="fail";
		Session session = sessionFactory.getCurrentSession(); 
		Long list;
		String checkdata=checkAssetStatus;
		String deletedata=deleteAssetStatus;
		Date d= new Date();
		int query1=0;
		try {
			session.beginTransaction(); 
			
			
			  list = (Long) session.createQuery(checkdata).
					  setParameter("projid",Integer.parseInt(projid)).
					  setParameter("finYear",Integer.parseInt(finYear)).uniqueResult(); 
			  if(list>0) 
			  { 
			    query1 = session.createSQLQuery(deletedata).setParameter("projid", Integer.parseInt(projid)).setParameter("finYear", Integer.parseInt(finYear)).executeUpdate(); 
			  }
			 
		 	
		 	
		 	String[] value = finalAssetid.split(",");	
			for(String i : value)
			{
				//session = sessionFactory.getCurrentSession();
				String status = ""; 
				String cdate  ="";
				String reason = "";
				String sdate ="";
				String convergence="";
				int assid=0;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				IwmpProjectAssetStatus asset = new IwmpProjectAssetStatus();
				if(request.getParameter("sdate"+i)!=null)
				{
					   sdate=request.getParameter("sdate"+i);
					  // assid = Integer.parseInt(request.getParameter(assetid[i]));
				}
				if(request.getParameter("status"+i)!=null)
			    {
				   status=request.getParameter("status"+i);
				   
			    }
				if(request.getParameter("cdate"+i)!=null)
				{
				   cdate=request.getParameter("cdate"+i);
				}
				if(request.getParameter("reason"+i)!=null)
				{
				   reason=request.getParameter("reason"+i);
				}
				if(request.getParameter("convergence"+i)!=null)
				{
				   convergence=request.getParameter("convergence"+i);
				}
			   
				  IwmpProjectPhysicalAsset phyass = new IwmpProjectPhysicalAsset();
				  phyass.setAssetid(new BigInteger(i));
				  IwmpMProject proj = new IwmpMProject();
				  proj.setProjectId(Integer.parseInt(projid));
				  asset.setIwmpProjectPhysicalAsset(phyass);
				  asset.setIwmpMProject(proj);
				  asset.setStartdate(simpleDateFormat.parse(sdate));
				  asset.setStatus(status.charAt(0));
					
				  if(cdate!=null && !cdate.equals(""))
				  asset.setCompletiondate(simpleDateFormat.parse(cdate));
				  else
				  asset.setCompletiondate(null);  
				  asset.setReason(reason);
				  asset.setConvergence(convergence.charAt(0));
				  asset.setLast_updated_date(d);
				  session.save(asset);
				  
			}
			session.getTransaction().commit();
		res="true";
		
	}
	catch(Exception ex){
			res="fail";
			ex.printStackTrace();
			//session.getTransaction().rollback();
	}
	finally {
			if(session.isOpen()) {
				session.close();
			}
	}
	return res;
	}

	@Override
	public String updateAssetStatus(HttpServletRequest request, String[] assetid,  String[] statusid) {
		String res="fail";
		Session session = sessionFactory.getCurrentSession();
		IwmpMProject proj = new IwmpMProject();
		//Transaction tx = null;
		try {
			//session.beginTransaction();
			IwmpProjectAssetStatus asset = new IwmpProjectAssetStatus();
			for(int i=0; i<assetid.length; i++)
			{
				session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				String status = ""; 
				String cdate  ="";
				String reason = "";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				asset = (IwmpProjectAssetStatus) session.get(IwmpProjectAssetStatus.class, Long.parseLong(statusid[i]));
				if(request.getParameter("status"+assetid[i])!=null)
				   {
					   status=request.getParameter("status"+assetid[i]);
					   
				   }
				   if(request.getParameter("cdate"+assetid[i])!=null)
				   {
					   cdate=request.getParameter("cdate"+assetid[i]);
				   }
				   if(request.getParameter("reason"+assetid[i])!=null)
				   {
					   reason=request.getParameter("reason"+assetid[i]);
				   }
				   
					/*
					 * proj.setProjectId(projid); asset.setIwmpMProject(proj);
					 */
				   asset.setStatus(status.charAt(0));
					
					  if(cdate!=null && !cdate.equals(""))
					  asset.setCompletiondate(simpleDateFormat.parse(cdate));
					  else
					  asset.setCompletiondate(null);  
					  asset.setReason(reason);
					  
					  session.saveOrUpdate(asset);
					  session.getTransaction().commit();
			}
			res="true";
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
			if(session.isOpen())
			{
				session.close();
			}
		}
		return res;
	}

	@Override
	
	public List<IwmpMPhySubactivity> getSubactivityByActivityCode(Integer activityCode) {
		// TODO Auto-generated method stub
		List<IwmpMPhySubactivity> subactivityList=new ArrayList<IwmpMPhySubactivity>();
		List<IwmpMPhySubactivity> finalList=new ArrayList<IwmpMPhySubactivity>();
		String hql=getSubactivityByActivityCode;
		//Transaction tx = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			 session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("activityCode", activityCode);
			//query.setResultTransformer(Transformers.aliasToBean(IwmpMPhyActivity.class));
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				IwmpMPhySubactivity sub = new IwmpMPhySubactivity();
				Object ob[] = (Object[])itr.next();
				sub.setSubActivityCode(Integer.parseInt(ob[0].toString()));
				sub.setSubActivityDesc(ob[1].toString());
				finalList.add(sub);
			}
		//	subactivityList = query.list();
		//	for(IwmpMPhySubactivity obj : subactivityList) {
		//		IwmpMPhySubactivity sub = new IwmpMPhySubactivity();
		//		sub.setSubActivityCode(obj.getSubActivityCode());
		//		sub.setSubActivityDesc(obj.getSubActivityDesc());
		//		finalList.add(sub);
		//	}
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
		finally {
			if(session.isOpen())
			{
				session.close();
			}
		}
		return finalList;
	}

	@Override
	public List<IwmpProjectPhysicalAssetTemp> getAssetTemp(Integer projId, Integer finYr, int aapid) {
		
		List<IwmpProjectPhysicalAssetTemp> list=new ArrayList<IwmpProjectPhysicalAssetTemp>();
		String hql=getActionPlanForAssetIdTemp;
		Session session = sessionFactory.getCurrentSession();
		//Transaction tx = null;
		try {
			 session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setInteger("finYr", finYr);
			query.setInteger("appid", aapid);
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
		finally {
			if(session.isOpen())
			{
				session.close();
			}
		}
		return list;
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getAssetAsset(Integer projId, Integer finYr, int aapid) {
		
		List<IwmpProjectPhysicalAsset> list=new ArrayList<IwmpProjectPhysicalAsset>();
		String hql=getActionPlanForAssetIdAsset;
		Session session = sessionFactory.getCurrentSession();
		//Transaction tx = null;
		try {
		    session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setInteger("finYr", finYr);
			query.setInteger("appid", aapid);
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
		finally {
			if(session.isOpen())
			{
				session.close();
			}
		}
		return list;
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getassetListkd(Integer projId) {
		
		List<IwmpProjectPhysicalAsset> list=new ArrayList<IwmpProjectPhysicalAsset>();
		String hql=getassetlistkd;
		Session session = sessionFactory.getCurrentSession();
		//Transaction tx = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projid", projId);
			list = query.list();

			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			if(session.isOpen())
			{
				session.close();
			}
		}
		return list;
	}

	@Override
	public List<IwmpProjectPhysicalAsset> getassetWorkWiseList(Integer pCode, String userId, Integer fYear) {
		List<IwmpProjectPhysicalAsset> list=new ArrayList<IwmpProjectPhysicalAsset>();
		String hql=getfinalWorkWiselist;
		
		Session session = this.sessionFactory.getCurrentSession();
		//Transaction tx = null;
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
				//  query.setString("userId", userId);		
			      query.setInteger("pCode", pCode);
			      query.setInteger("fYear", fYear);
				  list = query.list();
			     session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			
		}
		return list;
	}

	@Override
	public List<WdcpmksyLivelihoodWorkid> getassetHeadWiseList(Integer pCode, String userId, String hCode, Integer hActCode) {
		List<WdcpmksyLivelihoodWorkid> list=new ArrayList<WdcpmksyLivelihoodWorkid>();
		String hql=getfinalHeadWiselist;
		
		Session session = this.sessionFactory.getCurrentSession();
		//Transaction tx = null;
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
				  query.setString("userId", userId);		
			      query.setInteger("pCode", pCode);
			      query.setInteger("hActCode", hActCode);
					/* query.setString("hCode", hCode); */
				  list = query.list();
			     session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			
		}
		return list;
	}

	@Override
	public String saveAssetLivelihoodStatus(HttpServletRequest request, String[] assetid, String projid, String head,
			String finalAssetid) {
		String res="fail";
		Session session = sessionFactory.getCurrentSession(); 
		Long list;
		Date d = new Date();
		try {
			session.beginTransaction(); 
			list = (Long) session.createQuery(checkAssetLivelihoodStatus).setParameter("projid", Integer.parseInt(projid)).uniqueResult();
			if(list>0)
		 	{
		 	   int query1 = session.createSQLQuery(deletelivelihooddata).setParameter("projid", Integer.parseInt(projid)).executeUpdate();
			}
		 	
		 	
		 	String[] value = finalAssetid.split(",");	
			for(String i : value)
			{
				//session = sessionFactory.getCurrentSession();
			   String status = ""; 
				String cdate  ="";
				String reason = "";
				String sdate ="";
				int assid=0;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				WdcpmksyProjectAssetLivelihoodStatus asset = new WdcpmksyProjectAssetLivelihoodStatus();
				if(request.getParameter("sdate"+i)!=null)
				   {
					   sdate=request.getParameter("sdate"+i);
					  // assid = Integer.parseInt(request.getParameter(assetid[i]));
				   }
				if(request.getParameter("status"+i)!=null)
			   {
				   status=request.getParameter("status"+i);
				   
			   }
			   if(request.getParameter("cdate"+i)!=null)
			   {
				   cdate=request.getParameter("cdate"+i);
			   }
			   if(request.getParameter("reason"+i)!=null)
			   {
				   reason=request.getParameter("reason"+i);
			   }
			   
			   
			      WdcpmksyLivelihoodWorkid phyass = new WdcpmksyLivelihoodWorkid();
				  phyass.setAssetid(new Integer(i));
				  IwmpMProject proj = new IwmpMProject();
				  proj.setProjectId(Integer.parseInt(projid));
				  asset.setWdcpmksyLivelihoodWorkid(phyass);
				  asset.setIwmpMProject(proj);
				  asset.setStartdate(simpleDateFormat.parse(sdate));
				  asset.setStatus(status.charAt(0));
					
				  if(cdate!=null && !cdate.equals(""))
				  asset.setCompletiondate(simpleDateFormat.parse(cdate));
				  else
				  asset.setCompletiondate(null);  
				  asset.setLast_updated_date(d);
				  asset.setReason(reason);
				  //asset.setConvergence(convergence.charAt(0));
				  session.save(asset);
				  
			}
			session.getTransaction().commit();
		res="true";
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
		if(session.isOpen())
		{
			session.close();
		}
	}
	return res;
}

	@Override
	public List<WdcpmksyPrdouctionWorkid> getassetProdHeadWiseList(Integer pCode, String userId, Integer hActCode) {
		List<WdcpmksyPrdouctionWorkid> list=new ArrayList<WdcpmksyPrdouctionWorkid>();
		String hql=getfinalProdHeadWiselist;
		
		Session session = this.sessionFactory.getCurrentSession();
		//Transaction tx = null;
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
				  query.setString("userId", userId);		
			      query.setInteger("pCode", pCode);
			      query.setInteger("hActCode", hActCode);
					/* query.setString("hCode", hCode); */
				  list = query.list();
			     session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			
		}
		return list;
	}

	@Override
	public List<WdcpmksyEpaWorkid> getassetepaHeadWiseList(Integer pCode, String userId, Integer hActCode) {
		List<WdcpmksyEpaWorkid> list=new ArrayList<WdcpmksyEpaWorkid>();
		String hql=getfinalEPAHeadWiselist;
		
		Session session = this.sessionFactory.getCurrentSession();
		//Transaction tx = null;
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
				  query.setString("userId", userId);		
			      query.setInteger("pCode", pCode);
			      query.setInteger("hActCode", hActCode);	
			      /* query.setString("hCode", hCode); */
				  list = query.list();
			     session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			
		}
		return list;
	}

	@Override
	public String saveAssetProductionStatus(HttpServletRequest request, String[] assetid, String projid, String head, String finalAssetid) {
		String res="fail";
		Session session = sessionFactory.getCurrentSession(); 
		Long list;
		Date d = new Date();
		try {
			session.beginTransaction(); 
			list = (Long) session.createQuery(checkAssetProductionStatus).setParameter("projid", Integer.parseInt(projid)).uniqueResult();
			if(list>0)
		 	{
		 	   int query1 = session.createSQLQuery(deleteproductiondata).setParameter("projid", Integer.parseInt(projid)).executeUpdate();
			}
		 	
		 	
		 	String[] value = finalAssetid.split(",");	
			for(String i : value)
			{
				//session = sessionFactory.getCurrentSession();
			   String status = ""; 
				String cdate  ="";
				String reason = "";
				String sdate ="";
				int assid=0;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				WdcpmksyProjectAssetProductionStatus asset = new WdcpmksyProjectAssetProductionStatus();
				if(request.getParameter("sdate"+i)!=null)
				   {
					   sdate=request.getParameter("sdate"+i);
					  // assid = Integer.parseInt(request.getParameter(assetid[i]));
				   }
				if(request.getParameter("status"+i)!=null)
			   {
				   status=request.getParameter("status"+i);
				   
			   }
			   if(request.getParameter("cdate"+i)!=null)
			   {
				   cdate=request.getParameter("cdate"+i);
			   }
			   if(request.getParameter("reason"+i)!=null)
			   {
				   reason=request.getParameter("reason"+i);
			   }
			   
			   
			      WdcpmksyPrdouctionWorkid phyass = new WdcpmksyPrdouctionWorkid();
				  phyass.setAssetid(new Integer(i));
				  IwmpMProject proj = new IwmpMProject();
				  proj.setProjectId(Integer.parseInt(projid));
				  asset.setWdcpmksyPrdouctionWorkid(phyass);
				  asset.setIwmpMProject(proj);
				  asset.setStartdate(simpleDateFormat.parse(sdate));
				  asset.setStatus(status.charAt(0));
					
				  if(cdate!=null && !cdate.equals(""))
				  asset.setCompletiondate(simpleDateFormat.parse(cdate));
				  else
				  asset.setCompletiondate(null);  
				  asset.setReason(reason);
				  asset.setLast_updated_date(d);
				  //asset.setConvergence(convergence.charAt(0));
				  session.save(asset);
				  
			}
			session.getTransaction().commit();
		res="true";
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
		if(session.isOpen())
		{
			session.close();
		}
	}
	return res;
	}

	@Override
	public String saveAssetEPAStatus(HttpServletRequest request, String[] assetid, String projid, String head, String finalAssetid) {
		String res="fail";
		Session session = sessionFactory.getCurrentSession(); 
		Long list;
		Date d = new Date();
		try {
			session.beginTransaction(); 
			list = (Long) session.createQuery(checkAssetEPAStatus).setParameter("projid", Integer.parseInt(projid)).uniqueResult();
			if(list>0)
		 	{
		 	   int query1 = session.createSQLQuery(deleteepadata).setParameter("projid", Integer.parseInt(projid)).executeUpdate();
			}
		 	
		 	
		 	String[] value = finalAssetid.split(",");	
			for(String i : value)
			{
				//session = sessionFactory.getCurrentSession();
			   String status = ""; 
				String cdate  ="";
				String reason = "";
				String sdate ="";
				int assid=0;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				WdcpmksyProjectAssetEPAStatus asset = new WdcpmksyProjectAssetEPAStatus();
				if(request.getParameter("sdate"+i)!=null)
				   {
					   sdate=request.getParameter("sdate"+i);
					  // assid = Integer.parseInt(request.getParameter(assetid[i]));
				   }
				if(request.getParameter("status"+i)!=null)
			   {
				   status=request.getParameter("status"+i);
				   
			   }
			   if(request.getParameter("cdate"+i)!=null)
			   {
				   cdate=request.getParameter("cdate"+i);
			   }
			   if(request.getParameter("reason"+i)!=null)
			   {
				   reason=request.getParameter("reason"+i);
			   }
			   
			   
			      WdcpmksyEpaWorkid phyass = new WdcpmksyEpaWorkid();
				  phyass.setAssetid(new Integer(i));
				  IwmpMProject proj = new IwmpMProject();
				  proj.setProjectId(Integer.parseInt(projid));
				  asset.setWdcpmksyEpaWorkid(phyass);
				  asset.setIwmpMProject(proj);
				  asset.setStartdate(simpleDateFormat.parse(sdate));
				  asset.setStatus(status.charAt(0));
					
				  if(cdate!=null && !cdate.equals(""))
				  asset.setCompletiondate(simpleDateFormat.parse(cdate));
				  else
				  asset.setCompletiondate(null);  
				  asset.setReason(reason);
				  asset.setLast_updated_date(d);
				  //asset.setConvergence(convergence.charAt(0));
				  session.save(asset);
				  
			}
			session.getTransaction().commit();
		res="true";
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
		if(session.isOpen())
		{
			session.close();
		}
	}
	return res;
	}

	@Override
	public List<AssetIdBean> getassetheadcompletiondata(Integer pCode, String hCode, Integer headactivity) {
    List<AssetIdBean> getassetheadcdesc=new ArrayList<AssetIdBean>();
    SQLQuery query = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			if(hCode.equals("l"))
			{
			 query = session.createSQLQuery(getcompletelivelihoodasset);
			}
			if(hCode.equals("p"))
			{
			query = session.createSQLQuery(getcompleteproductionasset);	
			}
			if(hCode.equals("e"))
			{
			query = session.createSQLQuery(getcompleteepaasset);	
			}
			query.setInteger("pCode", pCode);
			query.setInteger("headactivity", headactivity);
			query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
			getassetheadcdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getassetheadcdesc;
	}

	@Override
	public List<AssetIdBean> getassetheadforcloseddata(Integer pCode, String hCode) {
		List<AssetIdBean> getassetheadfdesc=new ArrayList<AssetIdBean>();
	    SQLQuery query = null;
			try {
				Session session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				if(hCode.equals("l"))
				{
				 query = session.createSQLQuery(getforclosedlivelihoodasset);
				}
				if(hCode.equals("p"))
				{
				query = session.createSQLQuery(getforclosedproductionasset);	
				}
				if(hCode.equals("e"))
				{
				query = session.createSQLQuery(getforclosedepaasset);	
				}
				query.setInteger("pCode", pCode);
				query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
				getassetheadfdesc = query.list();
				session.getTransaction().commit();
			} 
			catch (HibernateException e) {
				System.err.print("Hibernate error");
				e.printStackTrace();
			} 
			catch(Exception ex){
				
				ex.printStackTrace();
			}
	        return getassetheadfdesc;
	}

	@Override
	public List<IwmpMFinYear> getAllFinancialYearDetails() {
		List<IwmpMFinYear> finYearList=new ArrayList<IwmpMFinYear>();
		String hql=getAllFinaicialYear;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			finYearList = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return finYearList;
	}

	@Override
	public List<IwmpMMonth> getAllMonthDetailData() {
		List<IwmpMMonth> list = new ArrayList<>();
		String hql = getMonthWiseDetailData;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public List<AssetIdBean> getassetcompletiondata(Integer pCode, Integer fCode) {
		List<AssetIdBean> getassetcdesc=new ArrayList<AssetIdBean>();
	    SQLQuery query = null;
			try {
				Session session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				
				 query = session.createSQLQuery(getcompleteassetdata);
				
				query.setInteger("pCode", pCode);
				query.setInteger("fCode", fCode);
				query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
				getassetcdesc = query.list();
				session.getTransaction().commit();
			} 
			catch (HibernateException e) {
				System.err.print("Hibernate error");
				e.printStackTrace();
			} 
			catch(Exception ex){
				
				ex.printStackTrace();
			}
	        return getassetcdesc;
	}

	@Override
	public List<AssetIdBean> getassetforcloseddata(Integer pCode, Integer fCode) {
		List<AssetIdBean> getassetfdesc=new ArrayList<AssetIdBean>();
	    SQLQuery query = null;
			try {
				Session session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				
				 query = session.createSQLQuery(getforclosedassetdata);
				
				query.setInteger("pCode", pCode);
				query.setInteger("fCode", fCode);
				query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
				getassetfdesc = query.list();
				session.getTransaction().commit();
			} 
			catch (HibernateException e) {
				System.err.print("Hibernate error");
				e.printStackTrace();
			} 
			catch(Exception ex){
				
				ex.printStackTrace();
			}
	        return getassetfdesc;
	}

	@Override
	public LinkedHashMap<Integer, String> getHeadActivitydesc(String headtype) {
		List<MEpaCoreactivity> list=new ArrayList<MEpaCoreactivity>();
		List<MLivelihoodCoreactivity> list1=new ArrayList<MLivelihoodCoreactivity>();
		List<MProductivityCoreactivity> list2=new ArrayList<MProductivityCoreactivity>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			if(headtype.equals("e")) 
			{
				Query query = session.createQuery(getMEpaCoreactivity); 
				list = query.list();
				for(MEpaCoreactivity pl : list) {
					if (!map.containsKey(pl.getEpaActivityId())) {
						map.put(pl.getEpaActivityId(), pl.getEpaDesc());
					} else {
						
					}
				}
			}
			if(headtype.equals("l")) 
			{
				Query query = session.createQuery(getMLiveCoreactivity); 
				list1 = query.list();
				for(MLivelihoodCoreactivity pl : list1) {
					if (!map.containsKey(pl.getLivelihoodCoreactivityId())) {
						map.put(pl.getLivelihoodCoreactivityId(), pl.getLivelihoodCoreactivityDesc());
					} else {
						
					}
				}
			}
			if(headtype.equals("p")) 
			{
				Query query = session.createQuery(getMProdCoreactivity); 
				list2 = query.list();
				for(MProductivityCoreactivity pl : list2) {
					if (!map.containsKey(pl.getProductivityCoreactivityId())) {
						map.put(pl.getProductivityCoreactivityId(), pl.getProductivityCoreactivityDesc());
					} else {
						
					}
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
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
}

	@Override
	public List<AssetIdBean> getListofWorkWiseStatus(Integer projid, Integer fyear, String hactivity, String wstatus) {
		List<AssetIdBean> getrecords=new ArrayList<AssetIdBean>();
	    SQLQuery query = null;
	    try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			if(hactivity.equals("N")) 
			{
				if(wstatus.equals("N"))
				{
					query = session.createSQLQuery(getNrmNotStartedData);
				}
				else {
					query = session.createSQLQuery(getNrmData);
					 }
				
			}
			if(hactivity.equals("E")) 
			{
				if(wstatus.equals("N"))
				{
					query = session.createSQLQuery(getEPANotStartedData);
				}
				else {
					query = session.createSQLQuery(getEPAData);
					 }
				
			}
			if(hactivity.equals("L")) 
			{
				if(wstatus.equals("N"))
				{
					query = session.createSQLQuery(getLivelihoodNotStartedData);
				}
				else {
					query = session.createSQLQuery(getLivelihoodData);
					 }
				
			}
			if(hactivity.equals("P")) 
			{
				if(wstatus.equals("N"))
				{
					query = session.createSQLQuery(getProductionNotStartedData);
				}
				else {
					query = session.createSQLQuery(getProductionData);
					 }
				
			}
			
			query.setInteger("projid", projid);
			if(hactivity.equals("N")) {
			query.setInteger("fyear", fyear);
			}
			if(!wstatus.equals("N")) {
			query.setString("wstatus", wstatus);
			}
			
			query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
			getrecords = query.list();
			session.getTransaction().commit();
	    }
	    
	    catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
		return getrecords;
	}

	@Override
	public List<AssetIdBean> getWorkWiseStatus(Integer workid, String activityid, Integer stcd) {
		List<AssetIdBean> getrecords=new ArrayList<AssetIdBean>();
	    SQLQuery query = null;
	    try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if(activityid.equals("N")) 
			{
				SQLQuery checkQuery  = session.createSQLQuery(checkQueryStr);
				checkQuery.setInteger("stcd", stcd);
	            checkQuery.setInteger("workid", workid);
	            
	            List<Object[]> existsResult = checkQuery.list();
	            if (!existsResult.isEmpty()) {
	            	SQLQuery secondQuery = session.createSQLQuery(secondQueryStr);
	            	secondQuery.setInteger("workid", workid);
	            	
	            	
	            	List<Object[]> secondResult = secondQuery.list();
	            	 if (secondResult.isEmpty()) {
	                     query = session.createSQLQuery(thirdQueryStr);
	                     query.setInteger("workid", workid);
	                 } else {
	                     query = secondQuery;
	                 }
	            }
			}
			if(activityid.equals("E")) 
			{
				SQLQuery checkQuery  = session.createSQLQuery(checkEPAQueryStr);
				checkQuery.setInteger("stcd", stcd);
	            checkQuery.setInteger("workid", workid);
	            
	            List<Object[]> existsResult = checkQuery.list();
	            if (!existsResult.isEmpty()) {
	            	SQLQuery secondQuery = session.createSQLQuery(secondEPAQueryStr);
	            	secondQuery.setInteger("workid", workid);
	            	
	            	
	            	List<Object[]> secondResult = secondQuery.list();
	            	 if (secondResult.isEmpty()) {
	                     query = session.createSQLQuery(thirdEPAQueryStr);
	                     query.setInteger("workid", workid);
	                 } else {
	                     query = secondQuery;
	                 }
	            }
			}
			if(activityid.equals("L")) 
			{
				SQLQuery checkQuery  = session.createSQLQuery(checkLiveQueryStr);
				checkQuery.setInteger("stcd", stcd);
	            checkQuery.setInteger("workid", workid);
	            
	            List<Object[]> existsResult = checkQuery.list();
	            if (!existsResult.isEmpty()) {
	            	SQLQuery secondQuery = session.createSQLQuery(secondLivQueryStr);
	            	secondQuery.setInteger("workid", workid);
	            	
	            	
	            	List<Object[]> secondResult = secondQuery.list();
	            	 if (secondResult.isEmpty()) {
	                     query = session.createSQLQuery(thirdLivQueryStr);
	                     query.setInteger("workid", workid);
	                 } else {
	                     query = secondQuery;
	                 }
	            }
			}
			if(activityid.equals("P")) 
			{
				SQLQuery checkQuery  = session.createSQLQuery(checkProdQueryStr);
				checkQuery.setInteger("stcd", stcd);
	            checkQuery.setInteger("workid", workid);
	            
	            List<Object[]> existsResult = checkQuery.list();
	            if (!existsResult.isEmpty()) {
	            	SQLQuery secondQuery = session.createSQLQuery(secondProdQueryStr);
	            	secondQuery.setInteger("workid", workid);
	            	
	            	
	            	List<Object[]> secondResult = secondQuery.list();
	            	 if (secondResult.isEmpty()) {
	                     query = session.createSQLQuery(thirdProdQueryStr);
	                     query.setInteger("workid", workid);
	                 } else {
	                     query = secondQuery;
	                 }
	            }
			}
			if (query != null) {
	            query.setResultTransformer(Transformers.aliasToBean(AssetIdBean.class));
	            getrecords = query.list();
	        }
	    }
	    
	    catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
		return getrecords;
	}
}
