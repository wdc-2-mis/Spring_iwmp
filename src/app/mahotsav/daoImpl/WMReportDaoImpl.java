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
import app.mahotsav.dao.WMReportDao;
import app.mahotsav.model.WatershedMahotsavProjectLvlPhoto;
import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;

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
		List<WatershedMahotsavProjectLvlPhoto> list = new ArrayList<WatershedMahotsavProjectLvlPhoto>();
		List<String> imgList = new ArrayList<>();
		Query query = null;
		try {
			session.beginTransaction();
			if(imgType == "projectlvl") {
			 query = session.createQuery("SELECT p FROM WatershedMahotsavProjectLvlPhoto p JOIN p.watershedMahotsav l WHERE l.state.stCode = :stCode");
			}
			else {
			 query = session.createQuery("select p from MahotsavPrabhatPheriPhoto p join p.wmPrabhatPheri l where l.state.stCode = :stCode"); 
			}
			query.setInteger("stCode", stCode);
			list = query.list();
			for (WatershedMahotsavProjectLvlPhoto photo : list) 
			{
				//server
				//imgList.add(photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
				//System.out.println(" kdy= "+photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
				
				//local
				imgList.add(photo.getPhotoUrl().replaceAll(".*\\\\", ""));
				System.out.println(" kdy= "+photo.getPhotoUrl().replaceAll(".*\\\\", ""));
			}
			
			session.getTransaction().commit();
		}
		catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return imgList;
	}
	
	
	
}
