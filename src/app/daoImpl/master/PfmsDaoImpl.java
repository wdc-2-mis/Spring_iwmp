package app.daoImpl.master;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

import app.bean.AuditReportBean;
import app.bean.BaseLineSurveyBean;
import app.bean.DisApprovalBean;
import app.bean.PfmsTransactionBean;
import app.bean.PhysicalActBean;
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.dao.master.PfmsDao;
import app.model.ConvergenceWorkDetail;
import app.model.IwmpActiveUserHistory;
import app.model.IwmpMProject;
import app.model.MDepartmentScheme;
import app.model.PFMSEatmisExpDetail;
import app.model.master.PfmsEatmisdataDetail;
import app.model.outcome.MShgCoreactivity;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;

@Repository("PfmsDao")
public class PfmsDaoImpl implements PfmsDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getPfmsTransactionStLvl}")
	String getPfmsTransactionStLvl;
	
	@Value("${getPfmsTransactionDistLvl}")
	String getPfmsTransactionDistLvl;
	
	@Value("${getPfmsTransactionBlkLvl}")
	String getPfmsTransactionBlkLvl;
	
	@Value("${getPfmsTransactionGpLvl}")
	String getPfmsTransactionGpLvl;
	
	@Value("${getPfmsTransactionVillLvl}")
	String getPfmsTransactionVillLvl;
	
	@Value("${getProjectsByStCode}")
	String getProjectsByStCode;
	
	@Value("${getWorkIdDtl}")
	String getWorkIdDtl;
	
	@Value("${getWorkIddraftDtl}")
	String getWorkIddraftDtl;
	
	@Value("${getPfmsTransactionWorkPiaLvl}")
	String getPfmsTransactionWorkPiaLvl;
	
	@Value("${getworkexpdetail}")
	String getworkexpdetail;
	
	/*
	 * @Value("${getPfmsWorkIdGpLvlD}") String getPfmsWorkIdGpLvlD;
	 */
	
	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}	
			
	@Override
	public List<PfmsTransactionBean> getPfmsTransaction(Integer mstrLvl, Integer projId) {
		Session session = sessionFactory.getCurrentSession();
		String hqlst = getPfmsTransactionStLvl;
		String hqldist = getPfmsTransactionDistLvl;
		String hqlblk = getPfmsTransactionBlkLvl;
		String hqlgp = getPfmsTransactionGpLvl;
		String hqlvill = getPfmsTransactionVillLvl;
		List<PfmsEatmisdataDetail> list = new ArrayList<>();
		List<PfmsTransactionBean> tranxList = new ArrayList<>();
		Query query = null;
		try {
			session.beginTransaction();
			if(mstrLvl==5) {
				query = session.createQuery(hqlst);
//				query.setString("Level", "STATE LEVEL");
				query.setInteger("projId", projId);
				list = query.list();
				for(PfmsEatmisdataDetail eatMis : list) {
					PfmsTransactionBean tranx = new PfmsTransactionBean();
					tranx.setEatmisdataId(eatMis.getEatmisdataId());
					tranx.setTranxId(eatMis.getTransactionId());
					tranx.setDate(eatMis.getDate().toString());
					tranx.setComponentCode(eatMis.getComponentCode());
					tranx.setComponentName(eatMis.getComponentName());
					tranx.setInvoiceNo(eatMis.getInvoiceNo());
					tranx.setProjectId(eatMis.getIwmpMProject()==null?null:eatMis.getIwmpMProject().getProjectId());
					tranx.setProjectName(eatMis.getProjectName());
					tranx.setTotalAmount(eatMis.getTotalAmount());
					tranx.setStatus(eatMis.getStatus()==null?'O':eatMis.getStatus());
					tranxList.add(tranx);
			}
			}else if(mstrLvl==4) {
				query = session.createQuery(hqldist);
//				query.setString("Level", "DISTRICT LEVEL");
				query.setInteger("projId", projId);
				list = query.list();
				for(PfmsEatmisdataDetail eatMis : list) {
					PfmsTransactionBean tranx = new PfmsTransactionBean();
					tranx.setEatmisdataId(eatMis.getEatmisdataId());
					tranx.setTranxId(eatMis.getTransactionId());
					tranx.setDate(eatMis.getDate().toString());
					tranx.setComponentCode(eatMis.getComponentCode());
					tranx.setComponentName(eatMis.getComponentName());
					tranx.setInvoiceNo(eatMis.getInvoiceNo());
					tranx.setProjectId(eatMis.getIwmpMProject()==null?null:eatMis.getIwmpMProject().getProjectId());
					tranx.setProjectName(eatMis.getProjectName());
					tranx.setTotalAmount(eatMis.getTotalAmount());
					tranx.setStatus(eatMis.getStatus()==null?'O':eatMis.getStatus());
					tranxList.add(tranx);
			}
			}else if(mstrLvl==3) {
				query = session.createQuery(hqlblk);
//				query.setString("Level", "BLOCK LEVEL");
				query.setInteger("projId", projId);
//				query.setResultTransformer(Transformers.aliasToBean(PfmsEatmisdataDetail.class));
				list = query.list();
				for(PfmsEatmisdataDetail eatMis : list) {
					PfmsTransactionBean tranx = new PfmsTransactionBean();
					tranx.setEatmisdataId(eatMis.getEatmisdataId());
					tranx.setTranxId(eatMis.getTransactionId());
					tranx.setDate(eatMis.getDate().toString());
					tranx.setComponentCode(eatMis.getComponentCode());
					tranx.setComponentName(eatMis.getComponentName());
					tranx.setInvoiceNo(eatMis.getInvoiceNo());
					tranx.setProjectId(eatMis.getIwmpMProject()==null?null:eatMis.getIwmpMProject().getProjectId());
					tranx.setProjectName(eatMis.getProjectName());
					tranx.setTotalAmount(eatMis.getTotalAmount());
					tranx.setStatus(eatMis.getStatus()==null?'O':eatMis.getStatus());
					tranxList.add(tranx);
			}
			}else if(mstrLvl == 2) {
				query = session.createQuery(hqlgp);
//				query.setString("Level", "PANCHAYAT LEVEL");
				query.setInteger("projId", projId);
//				query.setResultTransformer(Transformers.aliasToBean(PfmsEatmisdataDetail.class));
				list= query.list();
				for(PfmsEatmisdataDetail eatMis : list) {
					PfmsTransactionBean tranx = new PfmsTransactionBean();
					tranx.setEatmisdataId(eatMis.getEatmisdataId());
					tranx.setTranxId(eatMis.getTransactionId());
					tranx.setDate(eatMis.getDate().toString());
					tranx.setComponentCode(eatMis.getComponentCode());
					tranx.setComponentName(eatMis.getComponentName());
					tranx.setInvoiceNo(eatMis.getInvoiceNo());
					tranx.setProjectId(eatMis.getIwmpMProject()==null?null:eatMis.getIwmpMProject().getProjectId());
					tranx.setProjectName(eatMis.getProjectName());
					tranx.setTotalAmount(eatMis.getTotalAmount());
					tranx.setStatus(eatMis.getStatus()==null?'O':eatMis.getStatus());
					tranxList.add(tranx);
				}
			}else if(mstrLvl == 1) {
				query = session.createQuery(hqlvill);
//				query.setString("Level", "VILLAGE LEVEL");
				query.setInteger("projId", projId);
//				query.setResultTransformer(Transformers.aliasToBean(PfmsEatmisdataDetail.class));
				list= query.list();
				for(PfmsEatmisdataDetail eatMis : list) {
					PfmsTransactionBean tranx = new PfmsTransactionBean();
					tranx.setEatmisdataId(eatMis.getEatmisdataId());
					tranx.setTranxId(eatMis.getTransactionId());
					tranx.setDate(eatMis.getDate().toString());
					tranx.setComponentCode(eatMis.getComponentCode());
					tranx.setComponentName(eatMis.getComponentName());
					tranx.setInvoiceNo(eatMis.getInvoiceNo());
					tranx.setProjectId(eatMis.getIwmpMProject()==null?null:eatMis.getIwmpMProject().getProjectId());
					tranx.setProjectName(eatMis.getProjectName());
					tranx.setTotalAmount(eatMis.getTotalAmount());
					tranx.setStatus(eatMis.getStatus()==null?'O':eatMis.getStatus());
					tranxList.add(tranx);
				}
			}
			
			
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return tranxList;
	}

	@Override
	public String saveAsDraftPfmsTransaction(String eatmisdataId[], Integer projId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			if (projId != null) {
				session.beginTransaction();
				for (int i = 0; i < eatmisdataId.length; i++) {
					PfmsEatmisdataDetail pfms = session.load(PfmsEatmisdataDetail.class,
							Integer.parseInt(eatmisdataId[i]));
					IwmpMProject proj = new IwmpMProject();
					proj.setProjectId(projId);
					pfms.setIwmpMProject(proj);
					pfms.setProjectId(projId.toString());
					pfms.setProjectName(pfms.getIwmpMProject().getProjName());
					pfms.setStatus('D');
					session.saveOrUpdate(pfms);
				}

				session.getTransaction().commit();
				res = "success";

			}
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public String deletePfmsTransaction(Integer eatmisdataId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			PfmsEatmisdataDetail pfms = session.load(PfmsEatmisdataDetail.class, eatmisdataId);
//			IwmpMProject proj = new IwmpMProject();
//			proj.setProjectId(0);
			pfms.setIwmpMProject(null);
			pfms.setProjectId(null);
			pfms.setProjectName(null);
			pfms.setStatus(null);
			session.saveOrUpdate(pfms);
			
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public String completePfmsTransaction(String[] eatmisdataId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			for(int i =0; i<eatmisdataId.length;i++) {
				PfmsEatmisdataDetail pfms = session.load(PfmsEatmisdataDetail.class, Integer.parseInt(eatmisdataId[i]));
				pfms.setStatus('C');
				session.saveOrUpdate(pfms);
			}
			
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectByStCode(Integer stCode) {
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> getProjectList = new LinkedHashMap<>();
		List<IwmpMProject> list = new LinkedList<>();
		String hql = getProjectsByStCode;
		Query query = null;
		try {
			session.beginTransaction();
			query = session.createQuery(hql);
			query.setInteger("stCode", stCode);
			list = query.list();
			list.forEach(s->{
				getProjectList.put(s.getProjectId(), s.getProjName());
			});
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getProjectList;
	}

	@Override
	public List<PfmsTransactionBean> getPfmsWorkTransaction(Integer regId, Integer projId, Integer finyear) {
		Session session = sessionFactory.getCurrentSession();
		String hqlPia = getPfmsTransactionWorkPiaLvl;
		
		List<PfmsEatmisdataDetail> list = new ArrayList<>();
		List<PfmsTransactionBean> tranxList = new ArrayList<>();
		Query query = null;
		try {
			session.beginTransaction();
				query = session.createQuery(hqlPia);
				query.setInteger("projId", projId);
				query.setInteger("regId", regId);
				query.setInteger("finyear", finyear);
//				query.setString("Level", "STATE LEVEL");
				list = query.list();
				for(PfmsEatmisdataDetail eatMis : list) {
					PfmsTransactionBean tranx = new PfmsTransactionBean();
					tranx.setEatmisdataId(eatMis.getEatmisdataId());
					tranx.setTranxId(eatMis.getTransactionId());
					tranx.setDate(eatMis.getDate().toString());
					tranx.setComponentCode(eatMis.getComponentCode());
					tranx.setComponentName(eatMis.getComponentName());
					tranx.setInvoiceNo(eatMis.getInvoiceNo());
					tranx.setProjectName(eatMis.getIwmpMProject().getProjName());
					tranx.setProjectId(eatMis.getIwmpMProject().getProjectId());
					tranx.setTotalAmount(eatMis.getTotalAmount());
					if(eatMis.getTotalworks()!=null)
					{
					tranx.setWicode(eatMis.getTotalworks());
					}
					tranx.setWistatus(eatMis.getWistatus()==null?'O':eatMis.getWistatus());
					tranxList.add(tranx);
			}
			
			
			
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return tranxList;
	}

	
	
	@Override
	public LinkedHashMap<Integer, String> getworkiddtl(int projId) {
		List<PfmsTransactionBean> result = new ArrayList<PfmsTransactionBean>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getWorkIdDtl;
			ses.beginTransaction();
			query = ses.createSQLQuery(hql);
			query.setInteger("projId", projId);
			query.setResultTransformer(Transformers.aliasToBean(PfmsTransactionBean.class));
			result =query.list();
			for(PfmsTransactionBean act : result) {
				map.put(act.getAssetid(),act.getWorkdtl());
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
		} 
		finally {
			ses.getTransaction().commit();
		}
		return map;
		
	}

	@Override
	public String saveAsDraftPfmsWorkId(List<String> workid, List<String> eatmisdataId, List<String> expenditure, List<Integer> totalworks, String createdby, HttpServletRequest request) {
		String res="fail";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			PfmsEatmisdataDetail pfms = new PfmsEatmisdataDetail();
			PFMSEatmisExpDetail pfmsexp = new PFMSEatmisExpDetail();
			for(int i =0; i<eatmisdataId.size();i++) {
				pfms = session.load(PfmsEatmisdataDetail.class, Integer.parseInt(eatmisdataId.get(i)));
				pfms.setTotalworks(totalworks.get(i));
				pfms.setWistatus('D');
				session.saveOrUpdate(pfms);
				
			
				String workiddtl[] = workid.get(i).split("#");
			    String expdtl[] = expenditure.get(i).split("#");
			    for(int j = 0; j<workiddtl.length; j++)
			    {
			    	pfmsexp.setCreatedOn(new Date());
			    	pfmsexp.setRequestIp(getClientIpAddr(request));
			    	pfmsexp.setWicode(Integer.parseInt(workiddtl[j]));
			    	pfmsexp.setExpenditure(new BigDecimal(expdtl[j]));
			    	pfmsexp.setCreatedBy(createdby);
			    	pfmsexp.setPfmsEatmisdataDetail(pfms);
			    	session.save(pfmsexp);
				    session.evict(pfmsexp); 	
			    }
			}
			
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public LinkedHashMap<Integer, String> getworkiddraftdtl(List<Integer> wicode) {
		List<PfmsTransactionBean> result = new ArrayList<PfmsTransactionBean>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getWorkIddraftDtl;
			ses.beginTransaction();
			for(int i=0; i<wicode.size(); i++) {
			query = ses.createSQLQuery(hql);
			query.setInteger("wicode", wicode.get(i));
			query.setResultTransformer(Transformers.aliasToBean(PfmsTransactionBean.class));
			}
			result =query.list();
			for(PfmsTransactionBean act : result) {
				map.put(act.getAssetid(),act.getWorkdtl());
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
		} 
		finally {
			ses.getTransaction().commit();
		}
		return map;
	}

	public String deletePfmsworkTransaction(Integer eatmisdataId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			
			 SQLQuery query = session.createSQLQuery("delete from pfms_eatmisexpdata_detail where eatmisdata_id = :eatmisdataId");
		    query.setInteger("eatmisdataId", eatmisdataId);
			query.executeUpdate();
            
            
			PfmsEatmisdataDetail pfms = session.load(PfmsEatmisdataDetail.class, eatmisdataId);
//			IwmpMProject proj = new IwmpMProject();
//			proj.setProjectId(0);
			pfms.setTotalworks(null);
			pfms.setWistatus(null);
			session.saveOrUpdate(pfms);
			
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public String completePfmsWorkIdTransaction(String[] eatmisdataId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			for(int i =0; i<eatmisdataId.length;i++) {
				PfmsEatmisdataDetail pfms = session.load(PfmsEatmisdataDetail.class, Integer.parseInt(eatmisdataId[i]));
				pfms.setWistatus('C');
				session.saveOrUpdate(pfms);
			}
			
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public List<PfmsTransactionBean> getworkidexpdtl(Integer id) {
		List<PfmsTransactionBean> list=new ArrayList<PfmsTransactionBean>();
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql=getworkexpdetail;
			query = session.createSQLQuery(hql);
			query.setInteger("id", id);
			query.setResultTransformer(Transformers.aliasToBean(PfmsTransactionBean.class));
			list = query.list();
			session.getTransaction().commit();
		} 
		catch(HibernateException ex){
			System.err.print("Hibernate error");
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().commit();
		}
		return list;
	}

	
	
		
}
