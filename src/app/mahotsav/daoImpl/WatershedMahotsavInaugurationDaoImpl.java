package app.mahotsav.daoImpl;

import java.io.File;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
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
import app.mahotsav.dao.WatershedMahotsavInaugurationDao;
import app.mahotsav.model.WatershedMahotsavInauguaration;
import app.mahotsav.model.WatershedMahotsavInauguarationActPhoto;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.model.WatershedYatraInauguaration;

@Repository("WatershedMahotsavInaugurationDao")
public class WatershedMahotsavInaugurationDaoImpl implements WatershedMahotsavInaugurationDao{
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired
	protected SessionFactory sessionFactory; 
	
//	@Value("${getInaugurationDetails}")
//	String getInaugurationDetails;
	
	@Value("${getregisterInaugurationDraftDetails}")
	String getregisterInaugurationDraftDetails;
	
	@Value("${getregisterInaugurationcompleteDetails}")
	String getregisterInaugurationcompleteDetails;
	
	private Timestamp parseTimestamp(String dateTimeString) {
	    DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
	    DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    try {
	        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, originalFormatter);
	        return Timestamp.valueOf(localDateTime.format(targetFormatter));
	    } catch (DateTimeParseException e) {
	        // Handle parsing error
	        return null;
	    }
	}

	@Override
	public String saveMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session) {
		
		
		Session sess = sessionFactory.getCurrentSession();
		
		String res = "fail";
		String upload="unUpload";
		int sequence=0;
		try {
			sess.beginTransaction();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date inaugurationDate = formatter.parse(userfileup.getDate());
			String st_code=session.getAttribute("stateCode").toString();
			
			List list = sess.createQuery("SELECT iwmpState.stCode FROM WatershedMahotsavInauguaration where iwmpState.stCode=:villageCode").setInteger("villageCode", Integer.parseInt(session.getAttribute("stateCode").toString())).list();
			//result=Integer.parseInt(list.get(0).toString());
			if(list.isEmpty()) {
				
			
			
				String filePath="D:\\Inauguration\\";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/mahotsavdoc/Inauguration/";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/vanyatradoc/Inauguration/";
			
		/*		
			    upload = commonFunction.uploadFileforLMS(file, filePath, userfileup.getBlock(), act);
			 */
			
			List list1 = sess.createSQLQuery("select value_id from watershed_mahotsav_inauguaration_sequence").list();
			sequence=Integer.parseInt(list1.get(0).toString());
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			WatershedMahotsavInauguaration data = new WatershedMahotsavInauguaration();
			WatershedMahotsavInauguarationActPhoto photo= new WatershedMahotsavInauguarationActPhoto();
			
			
			IwmpState s= new IwmpState();
			s.setStCode(Integer.parseInt(session.getAttribute("stateCode").toString()));
			IwmpDistrict d= new IwmpDistrict();
			d.setDcode(userfileup.getDistrict());
			IwmpBlock b= new IwmpBlock();
			b.setBcode(userfileup.getBlock()); 
			
			data.setIwmpState(s);
			data.setIwmpDistrict(d);
			data.setIwmpBlock(b);
			
			data.setCreatedBy(session.getAttribute("loginID").toString());
			data.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
			data.setRequestIp(ipAddr);
			data.setStatus('D');
			
			data.setInauguarationDate(inaugurationDate);
			data.setInauguarationLocation(userfileup.getLocation());
			data.setMaleParticipants(userfileup.getMale_participants());
			data.setFemaleParticipants(userfileup.getFemale_participants());
			data.setCentralMinister(userfileup.getCentral_ministers());
			data.setStateMinister(userfileup.getState_ministers());
			data.setParliamentMembers(userfileup.getParliament());
			data.setLegislativeAssemblyMembers(userfileup.getAssembly_members());
			data.setLegislativeCouncilMembers(userfileup.getCouncil_members());
			data.setOtherPublicRepresentatives(userfileup.getOthers());
			data.setGovOfficials(userfileup.getGov_officials());
			data.setBhoomiPoojan(userfileup.getNo_works_bhoomipoojan());
			data.setLokarpan(userfileup.getNo_works_lokarpan());
			data.setShramdaanLocation(userfileup.getNo_location_shramdaan());
			data.setShramdaanParticipate(userfileup.getNo_people_shramdaan());
			data.setForestryHorticulture(userfileup.getArea_plantation());
			data.setAwardedJanbhagidari(userfileup.getNo_awards());
			sess.save(data);
			String code=st_code.toString()+userfileup.getDistrict().toString()+userfileup.getBlock().toString();
			//System.out.println("state="+code);
			
			
			
			for (MultipartFile image : userfileup.getPhotos_bhoomipoojan()) {
		        if (!image.isEmpty()) {
		        	photo.setWatershedMahotsavInauguaration(data);
		        	photo.setCreatedBy(session.getAttribute("loginID").toString());
					photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
					photo.setLatitude(null);
		        	commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getBhoomipoojan().toString(), sequence);
		        	photo.setActId(userfileup.getBhoomipoojan());
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getBhoomipoojan().toString()+sequence+"_"+image.getOriginalFilename());
		           // String fileName = image.getOriginalFilename();
		          //  System.out.println("kdy="+fileName);
		        	sequence=sequence+1;
		        }
		        sess.save(photo);
				sess.evict(photo);
		    }
			for (MultipartFile image1 : userfileup.getPhotos_lokarpan()) {
		        if (!image1.isEmpty()) {
		        	photo.setWatershedMahotsavInauguaration(data);
		        	photo.setCreatedBy(session.getAttribute("loginID").toString());
					photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileMahotwavInauguration(image1, filePath, code, userfileup.getLokarpan().toString(), sequence);
		        	photo.setActId(userfileup.getLokarpan());
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getLokarpan().toString()+sequence+"_"+image1.getOriginalFilename());
		         
		        	sequence=sequence+1;
		        }
		        sess.save(photo);
				sess.evict(photo);
		    }
			
			for (MultipartFile image2 : userfileup.getPhotos_shramdaan()) {
		        if (!image2.isEmpty()) {
		        	photo.setWatershedMahotsavInauguaration(data);
		        	photo.setCreatedBy(session.getAttribute("loginID").toString());
					photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileMahotwavInauguration(image2, filePath, code, userfileup.getShramdaan().toString(), sequence);
		        	photo.setActId(userfileup.getShramdaan());
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getShramdaan().toString()+sequence+"_"+image2.getOriginalFilename());
		          
		        	sequence=sequence+1;
		        }
		        sess.save(photo);
				sess.evict(photo);
		    }
			for (MultipartFile image3 : userfileup.getPhotos_forestry()) {
		        if (!image3.isEmpty()) {
		        	photo.setWatershedMahotsavInauguaration(data);
		        	photo.setCreatedBy(session.getAttribute("loginID").toString());
					photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileMahotwavInauguration(image3, filePath, code, userfileup.getForestry().toString(), sequence);
		        	photo.setActId(userfileup.getForestry());
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getForestry().toString()+sequence+"_"+image3.getOriginalFilename());
		           
		        	sequence=sequence+1;
		        }
		        sess.save(photo);
				sess.evict(photo);
		    }
			for (MultipartFile image4 : userfileup.getPhotos_janbhagidari()) {
		        if (!image4.isEmpty()) {
		        	photo.setWatershedMahotsavInauguaration(data);
		        	photo.setCreatedBy(session.getAttribute("loginID").toString());
					photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileMahotwavInauguration(image4, filePath, code, userfileup.getAwarded().toString(), sequence);
		        	photo.setActId(userfileup.getAwarded());
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getAwarded().toString()+sequence+"_"+image4.getOriginalFilename());
		         
		        	sequence=sequence+1;
		        }
		        sess.save(photo);
				sess.evict(photo);
		    }
			
			
			
			SQLQuery sqlQuery = sess.createSQLQuery("UPDATE watershed_mahotsav_inauguaration_sequence SET value_id=:aut");
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
	public List<InaugurationMahotsavBean> getregisterInaugurationDetails(Integer stcd) {
		
		String getReport=getregisterInaugurationDraftDetails;
		Session session = sessionFactory.getCurrentSession();
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(InaugurationMahotsavBean.class));
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
	public List<InaugurationMahotsavBean> getregisterInaugurationDetailsComp(Integer stcd) {
		
		String getReport=getregisterInaugurationcompleteDetails;
		Session session = sessionFactory.getCurrentSession();
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(InaugurationMahotsavBean.class));
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
	public String deleteMahotsavInaugurationDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		List<String> imgList = new ArrayList<String>();
		List<WatershedMahotsavInauguarationActPhoto> list = new ArrayList<WatershedMahotsavInauguarationActPhoto>();
		
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 
			// WatershedMahotsavInauguaration data = new WatershedMahotsavInauguaration();
			//WatershedMahotsavInauguarationActPhoto photo= new WatershedMahotsavInauguarationActPhoto();
			 Query query1 = session.createQuery("from WatershedMahotsavInauguarationActPhoto where watershedMahotsavInauguaration.inauguarationId = :inaugid");
			 for(int i=0;i<assetid.size(); i++)
			 {
				query1.setInteger("inaugid", assetid.get(i));
				list = query1.list();
			 }
			 for (WatershedMahotsavInauguarationActPhoto photo : list) {
				   
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
			 
			 SQLQuery query = session.createSQLQuery("delete from watershed_mahotsav_inauguaration_act_photo where inauguaration_id=:nrmpkid");
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
			 SQLQuery query2 = session.createSQLQuery("delete from watershed_mahotsav_inauguaration where inauguaration_id=:nrmpkid");
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
	public String completeMahotsavInaugurationDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("update watershed_mahotsav_inauguaration set status='C' where inauguaration_id=:nrmpkid");
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
	
	
	
	
	
	
	
	
	
	
	

}
