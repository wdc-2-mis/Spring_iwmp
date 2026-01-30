package app.punarutthan.daoImpl;

import java.io.File;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import app.common.CommonFunctions;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.model.WatershedMahotsavInauguaration;
import app.mahotsav.model.WatershedMahotsavInauguarationActPhoto;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;
import app.punarutthan.controller.WatershedPunarutthanBean;
import app.punarutthan.dao.WatershedPunarutthanDao;
import app.punarutthan.model.MStructure;
import app.punarutthan.model.Wdcpmksy1ProjectDetail;
import app.punarutthan.model.Wdcpmksy1PunarutthanPlan;
import app.punarutthan.model.Wdcpmksy1PunarutthanPlanImplementation;
import app.punarutthan.model.Wdcpmksy1PunarutthanPlanImplementationPhoto;
import app.punarutthan.model.Wdcpmksy1PunarutthanPlanPhoto;

@Repository("WatershedPunarutthanDao")
public class WatershedPunarutthanDaoImple implements WatershedPunarutthanDao{

	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Value("${getWatershedPunarutthanPlanDraft}")
	String getWatershedPunarutthanPlanDraft;
	
	@Value("${getWatershedPunarutthanPlanComplete}")
	String getWatershedPunarutthanPlanComplete;
	
	@Value("${getWatershedPunarutthanPlanId}")
	String getWatershedPunarutthanPlanId;
	
	@Value("${getPunarutthanDraftImplementation}")
	String getPunarutthanDraftImplementation;
	
	@Value("${getPunarutthanCompleteImplementation}")
	String getPunarutthanCompleteImplementation;
	
	@Override
	public LinkedHashMap<String, String> getProjectListMis(int distCodelgd) {
		
		List<Wdcpmksy1ProjectDetail> pdList = new ArrayList<Wdcpmksy1ProjectDetail>();
		String hql="select wpd from Wdcpmksy1ProjectDetail wpd where wpd.distLgd=:lgd and wpd.blockLgd is not null order by projName";
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("lgd", distCodelgd);
			pdList = query.list();
		
			for (Wdcpmksy1ProjectDetail pd : pdList) {
				map.put(pd.getProjectCd(), pd.getProjName());
		
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
		finally {
			//session.getTransaction().commit();
		}
        return map;
	}


	@Override
	public LinkedHashMap<String, Integer> getPunarutthanVillage(String pjcd) {
		
		
		String hql="select vcode, village_name from iwmp_village where gcode in(select gcode from iwmp_gram_panchayat where bcode in(select bcode from wdcpmksy1_project_detail where project_cd=:pjcd)) order by village_name";
		LinkedHashMap<String, Integer> villMap=new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(hql);
			query.setString("pjcd", pjcd);
			//query.list();
			 List<Object[]> results = query.list();

			    for (Object[] row : results) {
			        Integer vcode = ((Number) row[0]).intValue();   // first column
			        String villageName = (String) row[1];           // second column
			        villMap.put(villageName, vcode);
			    }

			
			//session.getTransaction().commit();
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
        return villMap;
	}


	@Override
	public LinkedHashMap<Integer, String> getStructureListMis() {

		List<MStructure> pdList = new ArrayList<MStructure>();
		String hql="select wpd from MStructure wpd order by structureDesc";
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			pdList = query.list();
		
			for (MStructure pd : pdList) {
				map.put(pd.getStructureId(), pd.getStructureDesc());
		
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
		finally {
			//session.getTransaction().commit();
		}
        return map;
	}


	@Override
	public String saveWatershedPunarutthanPlan(WatershedPunarutthanBean userfileup, HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		String res = "fail";
		String upload="unUpload";
		int sequence=0;
		try {
			sess.beginTransaction();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
		//	Date inaugurationDate = formatter.parse(userfileup.getDate());
			String st_code=session.getAttribute("stateCode").toString();
			
			List list = sess.createQuery("SELECT planId FROM Wdcpmksy1PunarutthanPlan where iwmpVillage.vcode=:villageCode").setInteger("villageCode", userfileup.getVillage1()).list();
			//result=Integer.parseInt(list.get(0).toString());
			if(list.isEmpty()) 
			{
			
				String filePath="D:\\punarutthan\\planing\\";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/punarutthan/planing/";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/punarutthan/planing/";
			
			List list1 = sess.createSQLQuery("select value_id from wdcpmksy1_punarutthan_sequence").list();
			sequence=Integer.parseInt(list1.get(0).toString());
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			Wdcpmksy1PunarutthanPlan data = new Wdcpmksy1PunarutthanPlan();
			Wdcpmksy1PunarutthanPlanPhoto photo1= new Wdcpmksy1PunarutthanPlanPhoto();
			
			
			IwmpState s= new IwmpState();
			s.setStCode(Integer.parseInt(session.getAttribute("stateCode").toString()));
			IwmpDistrict d= new IwmpDistrict();
			d.setDcode(userfileup.getDistrict1());
			IwmpVillage v= new IwmpVillage();
			v.setVcode(userfileup.getVillage1()); 
			MStructure st= new MStructure();
			st.setStructureId(userfileup.getStructure());
			
			data.setIwmpState(s);
			data.setIwmpDistrict(d);
			data.setIwmpVillage(v);
			data.setmStructure(st);
			data.setCreatedBy(session.getAttribute("loginID").toString());
			data.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
			data.setRequestIp(ipAddr);
			data.setStatus('D');
			data.setProjectCd(userfileup.getProject());
			data.setNoWork(userfileup.getWork());
			data.setWdf(userfileup.getWdf());
			data.setMgnrega(userfileup.getMgnrega());
			data.setOther(userfileup.getOther());
						
			sess.save(data);
			String code=st_code.toString()+userfileup.getDistrict1().toString()+userfileup.getVillage1().toString();
			
			  List<MultipartFile> photos = userfileup.getPhotos();
		      List<String> latitudes = userfileup.getLatitude();
		      List<String> longitudes = userfileup.getLongitute();
		      List<String> timestamps = userfileup.getPhotoTimestamp();

		         for (int i = 0; i < photos.size(); i++) 
		         {
		             MultipartFile image = photos.get(i);
		             if (!image.isEmpty()) {
		            	 Wdcpmksy1PunarutthanPlanPhoto photo = new Wdcpmksy1PunarutthanPlanPhoto();
		                photo.setWdcpmksy1PunarutthanPlan(data);
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(latitudes.get(i).equalsIgnoreCase(null))
							photo.setLatitude(null);
						else
							photo.setLatitude(latitudes.get(i));
						if(longitudes.get(i).equalsIgnoreCase(null))
							photo.setLongitute(null);
						else
							photo.setLongitute(longitudes.get(i));
						
		                if (timestamps.get(i).equalsIgnoreCase(null)) {
		                	 photo.setPhoto_timestamp(null);
		                }	 
		                else {	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(timestamps.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getProject(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getProject()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 sess.evict(photo);
		                 sequence++;
		             }
		         }
			SQLQuery sqlQuery = sess.createSQLQuery("UPDATE wdcpmksy1_punarutthan_sequence SET value_id=:aut");
			sqlQuery.setInteger("aut", sequence);
			sqlQuery.executeUpdate();
			
			res = "success";
			sess.getTransaction().commit();
			}
			else {
				res="failexist";
				sess.getTransaction().commit();
			}
		}
		catch (Exception ex) {
			res = "fail";
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}

	return res;
	}


	@Override
	public String getExistingPunarutthanPlanVillageCodes(Integer vCode) {

		Session session = sessionFactory.getCurrentSession();
		  int result;
		  String data="fail";
		  try {
		    session.beginTransaction();
			List list = session.createQuery("SELECT iwmpVillage.vcode FROM Wdcpmksy1PunarutthanPlan where iwmpVillage.vcode=:villageCode").setInteger("villageCode", vCode).list();
		//	result=Integer.parseInt(list.get(0).toString());
			if(!list.isEmpty())
				data="success";
		  } 
		  catch (HibernateException e) {
		    System.err.print("Hibernate error");
		    e.printStackTrace();
		    session.getTransaction().rollback();
		  } catch (Exception ex) {
		    session.getTransaction().rollback();
		    ex.printStackTrace();
		  }
		  finally {
			  session.getTransaction().commit();
		  }
		  return data;
	}


	@Override
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanDraft(String userid) {
		
		String getReport=getWatershedPunarutthanPlanDraft;
		Session session = sessionFactory.getCurrentSession();
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setString("usrid",userid); 
				query.setResultTransformer(Transformers.aliasToBean(WatershedPunarutthanBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}


	@Override
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanComplete(String userid) {
		
		String getReport=getWatershedPunarutthanPlanComplete;
		Session session = sessionFactory.getCurrentSession();
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setString("usrid",userid); 
				query.setResultTransformer(Transformers.aliasToBean(WatershedPunarutthanBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}


	@Override
	public String deletePunarutthanPlanDetails(List<Integer> assetid, String userid) {
		
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		List<String> imgList = new ArrayList<String>();
		List<Wdcpmksy1PunarutthanPlanPhoto> list = new ArrayList<Wdcpmksy1PunarutthanPlanPhoto>();
		
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 
			 Query query1 = session.createQuery("from Wdcpmksy1PunarutthanPlanPhoto where wdcpmksy1PunarutthanPlan.planId = :inaugid");
			 for(int i=0;i<assetid.size(); i++)
			 {
				query1.setInteger("inaugid", assetid.get(i));
				list = query1.list();
			 }
			 for (Wdcpmksy1PunarutthanPlanPhoto photo : list) {
				   
				 imgList.add(photo.getPhotoUrl());
			 }
			 for (String photo : imgList) 
			 {
		            if (photo != null && !photo.isEmpty()) 
		            {
		                File file = new File(photo);
		                if (file.exists()) 
		                {
		                    if (file.delete()) {
		                        System.out.println("Deleted file: " + file.getAbsolutePath());
		                    } else {
		                        System.out.println("Failed to delete file: " + file.getAbsolutePath());
		                    }
		                } 
		                else {
		                    System.out.println("File not found: " + file.getAbsolutePath());
		                }
		            }
		     }
			 
			 SQLQuery query = session.createSQLQuery("delete from wdcpmksy1_punarutthan_plan_photo where plan_id=:nrmpkid");
			 Date d= new Date();
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query.setInteger("nrmpkid", assetid.get(i));
				 value=query.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
			 }
			 SQLQuery query2 = session.createSQLQuery("delete from wdcpmksy1_punarutthan_plan where plan_id=:nrmpkid");
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query2.setInteger("nrmpkid", assetid.get(i));
				 value=query2.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
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
		
		return str;
	}


	@Override
	public String completePunarutthanPlanDetails(List<Integer> assetid, String userid) {
		
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("update wdcpmksy1_punarutthan_plan set status='C' where plan_id=:nrmpkid");
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query.setInteger("nrmpkid", assetid.get(i));
				 value=query.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
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
		
		return str;
	}


	@Override
	public List<WatershedPunarutthanBean> getWatershedPunarutthanPlanImpl(Integer plan_id) {
		
		String getReport=getWatershedPunarutthanPlanId;
		Session session = sessionFactory.getCurrentSession();
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("plnid",plan_id); 
				query.setResultTransformer(Transformers.aliasToBean(WatershedPunarutthanBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}


	@Override
	public String saveWatershedPunarutthanImplementation(WatershedPunarutthanBean userfileup, HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		String res = "fail";
		String upload="unUpload";
		int sequence=0;
		try {
			sess.beginTransaction();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
		//	Date inaugurationDate = formatter.parse(userfileup.getDate());
			String st_code=session.getAttribute("stateCode").toString();
			
		//	List list = sess.createQuery("SELECT planId FROM Wdcpmksy1PunarutthanPlan where iwmpVillage.vcode=:villageCode").setInteger("villageCode", userfileup.getVillage1()).list();
			//result=Integer.parseInt(list.get(0).toString());
			//if(list.isEmpty()) 
			//{
			
				String filePath="D:\\punarutthan\\Implementation\\";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/punarutthan/Implementation/";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/punarutthan/Implementation/";
			
			List list1 = sess.createSQLQuery("select value_id from wdcpmksy1_punarutthan_sequence").list();
			sequence=Integer.parseInt(list1.get(0).toString());
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			Wdcpmksy1PunarutthanPlanImplementation data = new Wdcpmksy1PunarutthanPlanImplementation();
			Wdcpmksy1PunarutthanPlanImplementationPhoto photo1= new Wdcpmksy1PunarutthanPlanImplementationPhoto();
			
			Wdcpmksy1PunarutthanPlan p= new Wdcpmksy1PunarutthanPlan();
			p.setPlanId(userfileup.getPlan_id());
			IwmpState s= new IwmpState();
			s.setStCode(Integer.parseInt(session.getAttribute("stateCode").toString()));
			IwmpDistrict d= new IwmpDistrict();
			d.setDcode(userfileup.getDistrict1());
			IwmpVillage v= new IwmpVillage();
			v.setVcode(userfileup.getVillage1()); 
			MStructure st= new MStructure();
			st.setStructureId(userfileup.getStructure());
			
			data.setWdcpmksy1PunarutthanPlan(p);
			data.setIwmpState(s);
			data.setIwmpDistrict(d);
			data.setIwmpVillage(v);
			data.setmStructure(st);
			data.setCreatedBy(session.getAttribute("loginID").toString());
			data.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
			data.setRequestIp(ipAddr);
			data.setStatus('D');
			data.setProjectCd(userfileup.getProject());
			data.setNoWork(userfileup.getWork());
			data.setWdf(userfileup.getWdf());
			data.setMgnrega(userfileup.getMgnrega());
			data.setOther(userfileup.getOther());
						
			sess.save(data);
			String code=st_code.toString()+userfileup.getDistrict1().toString()+userfileup.getVillage1().toString();
			
			  List<MultipartFile> photos = userfileup.getPhotos();
		      List<String> latitudes = userfileup.getLatitude();
		      List<String> longitudes = userfileup.getLongitute();
		      List<String> timestamps = userfileup.getPhotoTimestamp();

		         for (int i = 0; i < photos.size(); i++) 
		         {
		             MultipartFile image = photos.get(i);
		             if (!image.isEmpty()) {
		            	 Wdcpmksy1PunarutthanPlanImplementationPhoto photo = new Wdcpmksy1PunarutthanPlanImplementationPhoto();
		                photo.setWdcpmksy1PunarutthanPlanImplementation(data);
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(latitudes.get(i).equalsIgnoreCase(null))
							photo.setLatitude(null);
						else
							photo.setLatitude(latitudes.get(i));
						if(longitudes.get(i).equalsIgnoreCase(null))
							photo.setLongitute(null);
						else
							photo.setLongitute(longitudes.get(i));
						
		                if (timestamps.get(i).equalsIgnoreCase(null)) {
		                	 photo.setPhoto_timestamp(null);
		                }	 
		                else {	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(timestamps.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getProject(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getProject()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 sess.evict(photo);
		                 sequence++;
		             }
		         }
			SQLQuery sqlQuery = sess.createSQLQuery("UPDATE wdcpmksy1_punarutthan_sequence SET value_id=:aut");
			sqlQuery.setInteger("aut", sequence);
			sqlQuery.executeUpdate();
			
			res = "success";
			sess.getTransaction().commit();
		/*	}
			else {
				res="failexist";
				sess.getTransaction().commit();
			} */
		}
		catch (Exception ex) {
			res = "fail";
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}

	return res;
	}


	@Override
	public List<WatershedPunarutthanBean> getPunarutthanDraftImplementation(String userid) {
		
		String getReport=getPunarutthanDraftImplementation;
		Session session = sessionFactory.getCurrentSession();
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setString("usrid",userid); 
				query.setResultTransformer(Transformers.aliasToBean(WatershedPunarutthanBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}


	@Override
	public List<WatershedPunarutthanBean> getPunarutthanCompleteImplementation(String userid) {
		
		String getReport=getPunarutthanCompleteImplementation;
		Session session = sessionFactory.getCurrentSession();
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setString("usrid",userid); 
				query.setResultTransformer(Transformers.aliasToBean(WatershedPunarutthanBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}

}
