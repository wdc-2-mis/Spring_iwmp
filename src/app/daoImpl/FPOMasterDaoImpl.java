package app.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import app.bean.FPOReportBean;
import app.bean.TarAchProjOutcomeBean;
import app.bean.menu.IwmpMMenu;
import app.dao.FPOMasterDao;
import app.model.IwmpMProject;
import app.model.IwmpUserProjectMap;
import app.model.MDepartmentScheme;
import app.model.master.IwmpUserUploadDetails;
import app.model.outcome.FpoCoreaactivityDetail;
import app.model.outcome.FpoDetail;
import app.model.outcome.FpoMain;
import app.model.outcome.MFpoCoreactivity;

@Repository("fpoMasterDao")
public class FPOMasterDaoImpl implements FPOMasterDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getFPOCoreActivity}")
	String getFPOCoreActivity;
	
	@Value("${getFPOstatewise}")
	String getFPOstatewise;
	
	@Value("${getFPOdistwise}")
	String getFPOdistwise;
	
	@Value("${getFPOprojwise}")
	String getFPOprojwise;
	
	@Value("${getFPOAllprojwise}")
	String getFPOAllprojwise;
	
	@Value("${getFPODraftData}")
	String getFPODraftData;
	
	@Value("${deleteFPODraftData}")
	String deleteFPODraftData;
	
	@Value("${getFPOFinalData}")
	String getFPOFinalData;
	
	@Value("${getDeptOrg}")
	String getDeptOrg;
	
	@Override
	public LinkedHashMap<Integer, String> getCoreActivity() {
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<MFpoCoreactivity> rows = new ArrayList<MFpoCoreactivity>();
		String hql=getFPOCoreActivity;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			rows = query.list();
			  for(MFpoCoreactivity row : rows){
				  System.out.println("Size by regId1: "+row.getFpoCoreactivityId());
				  map.put(row.getFpoCoreactivityId(), row.getFpoCoreactivityDesc());
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
	public String savefpodata(Integer projId, String group, Integer no, List<String> fponame, List<Integer> deptorg, List<String> regno, List<String> regdt, List<Integer> noofmembers, List<String> fpoactivity,
			List<BigDecimal> fpoavg, List<Integer> nooffarm, String loginID) {
		Session session = sessionFactory.getCurrentSession();
		String status = "d";
		String res="fail";
		try {
			session.getTransaction().begin();
			FpoMain fpomain = new FpoMain();
			FpoDetail fpodetail = new FpoDetail();
			FpoCoreaactivityDetail fpocoredetail = new FpoCoreaactivityDetail();
			MFpoCoreactivity mcoreactivity = new MFpoCoreactivity();
			MDepartmentScheme mdeptscheme = new MDepartmentScheme();
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			
			
			IwmpMProject projectId = (IwmpMProject) session.get(IwmpMProject.class, projId);
			
			fpomain.setIwmpMProject(projectId);
			fpomain.setFpoType(group);
			fpomain.setTotalno(no);
			fpomain.setCreatedOn(new Date());
			fpomain.setRequestIp(ipAddr);
			fpomain.setCreatedBy(loginID);
			fpomain.setStatus(status);
			session.save(fpomain);
			
			for(int i=0;i<fponame.size();i++) {
				fpodetail.setFpoName(fponame.get(i));
				mdeptscheme.setDepartmentSchemeIdPk(deptorg.get(i));
				fpodetail.setMDepartmentScheme(mdeptscheme);
				fpodetail.setRegistrationNo(regno.get(i));
				Date parsedDate = formatter.parse(regdt.get(i));
				fpodetail.setRegistrationDate(parsedDate);
				fpodetail.setNoOfMembers(noofmembers.get(i));
				fpodetail.setNoOfFarmerAssociated(nooffarm.get(i));
				fpodetail.setTurnover(fpoavg.get(i));
				fpodetail.setCreatedOn(new Date());
				fpodetail.setRequestIp(ipAddr);
				fpodetail.setCreatedBy(loginID);
				fpodetail.setFpoMain(fpomain);
				//fpodetail.setStatus(status);
				session.save(fpodetail);
				session.evict(fpodetail);
				
				String activity[] = fpoactivity.get(i).split("#");
				for(String str : activity) {
					fpocoredetail = new FpoCoreaactivityDetail();

					mcoreactivity = (MFpoCoreactivity) session.get(MFpoCoreactivity.class, Integer.parseInt(str));
					fpocoredetail.setMFpoCoreactivity(mcoreactivity);
					fpocoredetail.setCreatedOn(new Date());
					fpocoredetail.setRequestIp(ipAddr);
					fpocoredetail.setCreatedBy(loginID);
					fpocoredetail.setFpoDetail(fpodetail);
					//fpocoredetail.setStatus(status);
					session.save(fpocoredetail);
					session.evict(fpocoredetail);
					
				}
			}
			
		
		session.getTransaction().commit();
		res="success";
		}catch (Exception e) {
			e.printStackTrace();
			res="fail";
		}
		return res;
	}

@Override
public List<FPOReportBean> getfpodatastatewise(Integer state) {
	String getReport=getFPOstatewise;
	Session session = sessionFactory.getCurrentSession();
	List<FPOReportBean> list = new ArrayList<FPOReportBean>();
	try {
		session.beginTransaction();
		Query query= session.createSQLQuery(getReport);
		query.setInteger("stCode",state); 
		query.setResultTransformer(Transformers.aliasToBean(FPOReportBean.class));
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
public List<FPOReportBean> getfpodatadistwise(Integer statecode) {
	String getReport=getFPOdistwise;
	Session session = sessionFactory.getCurrentSession();
	List<FPOReportBean> list = new ArrayList<FPOReportBean>();
	try {
		session.beginTransaction();
		Query query= session.createSQLQuery(getReport);
		query.setInteger("stCode",statecode); 
		query.setResultTransformer(Transformers.aliasToBean(FPOReportBean.class));
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

@SuppressWarnings("unchecked")
@Override
public List<FpoDetail> getfpodataprojwise(int dcode, String fpoType) {
	String getReport=getFPOprojwise;
	Session session = sessionFactory.getCurrentSession();
	List<FpoDetail> list = new ArrayList<FpoDetail>();
	try {
		session.beginTransaction();
		Query query= session.createQuery(getReport);
		query.setInteger("dcode",dcode); 
		query.setString("fpoType", fpoType);
		//query.setResultTransformer(Transformers.aliasToBean(FpoDetail.class));
		list = (List<FpoDetail> )query.list();
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
public List<FpoDetail> getfpodataallprojwise(int stcode, String fpoType) {
	String getReport=getFPOAllprojwise;
	Session session = sessionFactory.getCurrentSession();
	List<FpoDetail> list = new ArrayList<FpoDetail>();
	try {
		session.beginTransaction();
		Query query= session.createQuery(getReport);
		query.setInteger("stcode",stcode); 
		query.setString("fpoType", fpoType);
		//query.setResultTransformer(Transformers.aliasToBean(FpoDetail.class));
		list = (List<FpoDetail> )query.list();
		//System.out.println("himanshu" +query);
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
public List<FPOReportBean> getfpodraftdata(Integer projectId, String group) {
	String getReport=getFPODraftData;
	Session session = sessionFactory.getCurrentSession();
	List<FPOReportBean> list = new ArrayList<FPOReportBean>();
	try {
		session.beginTransaction();
		Query query= session.createSQLQuery(getReport);
		query.setInteger("projectId",projectId); 
		query.setString("group", group);
		query.setResultTransformer(Transformers.aliasToBean(FPOReportBean.class));
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
public String detFPOdraftdata(Integer fpoid) {
	try {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		FpoMain menu = (FpoMain) session.load(FpoMain.class, fpoid);
		

		if (null != menu) {
			session.delete(menu);
		}
		session.getTransaction().commit();
		return "success";

	} catch (Exception ex) {

		return "fail";
	}
	//return res;
}

@Override
public String finalSaveFPOdraftdata(Integer fpoid) {
	try {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		FpoMain menu = (FpoMain) session.load(FpoMain.class, fpoid);
		if (null != menu) {
			menu.setStatus("c");
			session.update(menu);
		}
		session.getTransaction().commit();
		return "success";

	} catch (Exception ex) {

		return "fail";
	}
}

@Override
public List<FPOReportBean> fpofinaldata(Integer projectId, String group) {
	String getReport=getFPOFinalData;
	Session session = sessionFactory.getCurrentSession();
	List<FPOReportBean> list = new ArrayList<FPOReportBean>();
	try {
		session.beginTransaction();
		Query query= session.createSQLQuery(getReport);
		query.setInteger("projectId",projectId); 
		query.setString("group", group);
		query.setResultTransformer(Transformers.aliasToBean(FPOReportBean.class));
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
public LinkedHashMap<Integer, String> getdeptorg(int stCode) {
	LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
	List<MDepartmentScheme> rows = new ArrayList<MDepartmentScheme>();
	String hql=getDeptOrg;
	Session session = sessionFactory.getCurrentSession();
	try {
		
		session.beginTransaction();
		Query query = session.createQuery(hql);
	//	query.setInteger("stCode", stCode);
		rows = query.list();
		  for(MDepartmentScheme row : rows){
			  map.put(row.getDepartmentSchemeIdPk(), row.getSchemeDescription());
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



}
