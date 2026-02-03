package app.mahotsav.daoImpl;

import java.util.ArrayList;
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

import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.bean.WMMediaReviewBean;
import app.mahotsav.dao.WMReportDao;
import app.mahotsav.model.MahotsavPrabhatPheriPhoto;
import app.mahotsav.model.WatershedMahotsavMediaMaster;
import app.mahotsav.model.WatershedMahotsavProjectLvlPhoto;
import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;
import app.projectevaluation.bean.ProjectEvaluationBean;

@Repository("WMReportDao")
public class WMReportDaoImpl implements WMReportDao {

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${districtListByStateCode}") 
	String distlist;
	
	@Value("${blocklistbydistcdwatershed}") 
	String blklist;
	
	@Value("${getMahotVillageList}") 
	String villlist;
	
	@Value("${getWMInaugurationData}")
	String getWMInaugurationDetails;

	@Value("${getProjLvlWMPrgDetails}")
	String getProjLvlWMPrgDetails;
	
	@Value("${getWMSocialMediaReportData}")
	String getWMSocialMediaReportDetails;
	
	@Value("${getDistWMProjLvlDetails}")
	String getDistWMProjLvlDetails;
	
	@Value("${getStWiseProjLvlPhoto}")
	String getStWiseProjLvlPhoto;
	
	@Value("${getStWiseProjLvlPPPhoto}")
	String getStWiseProjLvlPPPhoto;
	
	@Value("${getDistWiseProjLvlPhoto}")
	String getDistWiseProjLvlPhoto;
	
	@Value("${getDistWiseProjLvlPPPhoto}")
	String getDistWiseProjLvlPPPhoto;
	
	@Value("${getWMSocialMediaComDetails}")
	String getWMSocialMediaComDetails;
	
	@Value("${getTotNoOfScrnshtUploaded}")
	String getTotNoOfScrnshtUploaded;
	
	@Override
	public List<IwmpDistrict> getDistrictList(int stateCode) {
		
		List<IwmpDistrict> result=new ArrayList<IwmpDistrict>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=distlist;
			result = ses.createQuery(hql).setParameter("stCode", stateCode).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
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
	public List<IwmpBlock> getBlockList(int stateCode, int dist) {
		// TODO Auto-generated method stub
		List<IwmpBlock> result=new ArrayList<IwmpBlock>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=blklist;
			result = ses.createQuery(hql).setParameter("distcod", dist).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
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
	public List<IwmpVillage> getVillageList(int block) {
		List<IwmpVillage> result=new ArrayList<IwmpVillage>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=villlist;
			result = ses.createQuery(hql).setParameter("block", block).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
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
	public List<InaugurationMahotsavBean> getStateWMInaugurationReport() {
		List<InaugurationMahotsavBean> getWMInaugurationData = new ArrayList<InaugurationMahotsavBean>();
		String hql = getWMInaugurationDetails;
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(InaugurationMahotsavBean.class));
			getWMInaugurationData = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
			
		return getWMInaugurationData;
	}

	@Override
	public List<InaugurationMahotsavBean> getProjLvlWMPrgReport() {
		List<InaugurationMahotsavBean> getProjLvlWMPrgData = new ArrayList<InaugurationMahotsavBean>();
		String hql = getProjLvlWMPrgDetails;
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(InaugurationMahotsavBean.class));
			getProjLvlWMPrgData = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
			
		return getProjLvlWMPrgData;
	}

	@Override
	public List<SocialMediaReport> getWMSocialMediaReport(Integer stcd, Integer dcode, Integer bcode, Integer vcode) {
		List<SocialMediaReport> getWMSocialMediaReport = new ArrayList<>();
		String hql = getWMSocialMediaReportDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setInteger("dcode", dcode);
			query.setInteger("bcode", bcode);
			query.setInteger("vcode", vcode);
			query.setResultTransformer(Transformers.aliasToBean(SocialMediaReport.class));
			getWMSocialMediaReport = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getWMSocialMediaReport;
	}

	public List<String> getImageMahotsavProjAtStLVL(Integer stCode, String imgType) {
	    Session session = sessionFactory.getCurrentSession();
	    List<String> imgList = new ArrayList<>();
	    Query query = null;
        String hql = getStWiseProjLvlPhoto;
        String hql1 = getStWiseProjLvlPPPhoto;
	    
	    try {
	        session.beginTransaction();

	        if ("projectlvl".equals(imgType)) {
	            query = session.createQuery(hql);
	            query.setInteger("stCode", stCode);
	            List<WatershedMahotsavProjectLvlPhoto> list = query.list();

	            for (WatershedMahotsavProjectLvlPhoto photo : list) {
	              
	                //server
					imgList.add(photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
					//System.out.println(" kdy= "+photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
					
					//local
					//imgList.add(photo.getPhotoUrl().replaceAll(".*\\\\", ""));
					

	            }
	        } else {
	            query = session.createQuery(hql1);
	            query.setInteger("stCode", stCode);
	            List<MahotsavPrabhatPheriPhoto> list1 = query.list();

	            for (MahotsavPrabhatPheriPhoto photo : list1) {
	            	//server
					imgList.add(photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
					//System.out.println(" kdy= "+photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
					
					//local
					//imgList.add(photo.getPhotoUrl().replaceAll(".*\\\\", ""));
				
	            }
	        }

	        session.getTransaction().commit();
	    } catch (Exception ex) {
	        session.getTransaction().rollback();
	        ex.printStackTrace();
	    }

	    return imgList;
	}
	


	@Override
	public List<InaugurationMahotsavBean> getdistWMProjLvlProgRpt(int stcd) {
		List<InaugurationMahotsavBean> getDistWMProjLvlData = new ArrayList<InaugurationMahotsavBean>();
		String hql = getDistWMProjLvlDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setResultTransformer(Transformers.aliasToBean(InaugurationMahotsavBean.class));
			getDistWMProjLvlData = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
			
		return getDistWMProjLvlData;
	}

	@Override
	public List<String> getImageMahotsavProjAtDistLVL(Integer distCode, String imgType) {
		Session session = sessionFactory.getCurrentSession();
	    List<String> imgList = new ArrayList<>();
	    Query query = null;
	    String hql = getDistWiseProjLvlPhoto;
        String hql1 = getDistWiseProjLvlPPPhoto;	
	    
	    try {
	        session.beginTransaction();

	        if ("projectlvl".equals(imgType)) {
	            query = session.createQuery(hql);
	            query.setInteger("distCode", distCode);
	            List<WatershedMahotsavProjectLvlPhoto> list = query.list();

	            for (WatershedMahotsavProjectLvlPhoto photo : list) {
	              
	                //server
					imgList.add(photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
					//System.out.println(" kdy= "+photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
					
					//local
					//imgList.add(photo.getPhotoUrl().replaceAll(".*\\\\", ""));
					
	            }
	        } else {
	            query = session.createQuery(hql1);
	            query.setInteger("distCode", distCode);
	            List<MahotsavPrabhatPheriPhoto> list1 = query.list();

	            for (MahotsavPrabhatPheriPhoto photo : list1) {
	            	//server
					imgList.add(photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
					//System.out.println(" kdy= "+photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
					
					//local
				//	imgList.add(photo.getPhotoUrl().replaceAll(".*\\\\", ""));
				
	            }
	        }

	        session.getTransaction().commit();
	    } catch (Exception ex) {
	        session.getTransaction().rollback();
	        ex.printStackTrace();
	    }

	    return imgList;
	}

	@Override
	public List<WMMediaReviewBean> getWMSocialMediaComDetails(Integer stcode, Integer dcode, Integer platform,
			String status) {
		Session session = sessionFactory.getCurrentSession();
		WatershedMahotsavMediaMaster mediaMaster = new WatershedMahotsavMediaMaster();
		List<WMMediaReviewBean> list = new ArrayList<WMMediaReviewBean>();
		String hql = getWMSocialMediaComDetails;
		String plat = "";
		String stats = "";
		Query query = null;
		try {
			session.beginTransaction();
			if(platform !=0) {
				mediaMaster = session.get(WatershedMahotsavMediaMaster.class,platform);
				plat = mediaMaster.getMediaName();
			}
			if(!status.equals("All"))
				stats = status;
			
			query = session.createSQLQuery(hql);
			query.setInteger("stcode", stcode);
			query.setInteger("dcode", dcode);
			query.setParameter("platform", plat);
			query.setParameter("status", stats);
			query.setResultTransformer(Transformers.aliasToBean(WMMediaReviewBean.class));
			list = query.list();
			session.getTransaction().commit();
		}
		catch (Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<WMMediaReviewBean> getTotNoOfScrnshtUploaded() {
		Session session = sessionFactory.getCurrentSession();
		List<WMMediaReviewBean> list = new ArrayList<WMMediaReviewBean>();
		String hql = getTotNoOfScrnshtUploaded;
		Query query = null;
		try {
			session.beginTransaction();
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(WMMediaReviewBean.class));
			list = query.list();
			session.getTransaction().commit();
		}
		catch (Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}
	
	
	
}
