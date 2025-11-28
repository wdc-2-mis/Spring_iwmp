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
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import app.common.CommonFunctions;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.dao.WatershedMahotsavInaugurationDao;
import app.mahotsav.model.MahotsavPrabhatPheriPhoto;
import app.mahotsav.model.WatershedMahotsavInauguaration;
import app.mahotsav.model.WatershedMahotsavInauguarationActPhoto;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.model.WatershedYatVill;
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
	
	@Value("${getMahotsavInaugurationEditList}")
	String getMahotsavInaugurationEditList;
	
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
			if(list.isEmpty()) 
			{
			
				String filePath="D:\\Inauguration\\";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/mahotsavdoc/Inauguration/";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/mahotsavdoc/Inauguration/";
			
		/*		
			    upload = commonFunction.uploadFileforLMS(file, filePath, userfileup.getBlock(), act);
			 */
			
			List list1 = sess.createSQLQuery("select value_id from watershed_mahotsav_inauguaration_sequence").list();
			sequence=Integer.parseInt(list1.get(0).toString());
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			WatershedMahotsavInauguaration data = new WatershedMahotsavInauguaration();
			WatershedMahotsavInauguarationActPhoto photo1= new WatershedMahotsavInauguarationActPhoto();
			
			
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
			
			  List<MultipartFile> photos = userfileup.getPhotos_bhoomipoojan();
		      List<String> latitudes = userfileup.getPhotos_bhoomipoojan_lat();
		      List<String> longitudes = userfileup.getPhotos_bhoomipoojan_lng();
		      List<String> timestamps = userfileup.getPhotos_bhoomipoojan_time();

		         for (int i = 0; i < photos.size(); i++) 
		         {
		             MultipartFile image = photos.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getBhoomipoojan());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(latitudes.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(latitudes.get(i));
						if(longitudes.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(longitudes.get(i));
						
		                if (timestamps.get(i).equalsIgnoreCase("0")) {
		                	 photo.setPhoto_timestamp(null);
		                }	 
		                else {	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(timestamps.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getBhoomipoojan().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getBhoomipoojan().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 sess.evict(photo);
		                 sequence++;
		             }
		         }
		         List<MultipartFile> photos_lokarpan=userfileup.getPhotos_lokarpan();
		         List<String> lokarpan_lat = userfileup.getPhotos_lokarpan_lat();
			     List<String> lokarpan_lng = userfileup.getPhotos_lokarpan_lng();
			     List<String> lokarpan_time = userfileup.getPhotos_lokarpan_time();
		         for (int i = 0; i < photos_lokarpan.size(); i++) 
		         {
		             MultipartFile image = photos_lokarpan.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getLokarpan());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(lokarpan_lat.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(lokarpan_lat.get(i));
						if(lokarpan_lng.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(lokarpan_lng.get(i));
						
		               if (lokarpan_time.get(i).equalsIgnoreCase("0")) {
		                	 photo.setPhoto_timestamp(null);
		               }	 
		               else {	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(lokarpan_time.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getLokarpan().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getLokarpan().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 sess.evict(photo);
		                 sequence++;
		             }
		         }
		         List<MultipartFile> photos_shramdaan=userfileup.getPhotos_shramdaan();
		         List<String> shramdaan_lat = userfileup.getPhotos_shramdaan_lat();
			     List<String> shramdaan_lng = userfileup.getPhotos_shramdaan_lng();
			     List<String> shramdaan_time = userfileup.getPhotos_shramdaan_time();
		         for (int i = 0; i < photos_shramdaan.size(); i++) 
		         {
		             MultipartFile image = photos_shramdaan.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getShramdaan());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(shramdaan_lat.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(shramdaan_lat.get(i));
						if(shramdaan_lng.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(shramdaan_lng.get(i));
						
		                if (shramdaan_time.get(i).equalsIgnoreCase("0")) { 
		                	 photo.setPhoto_timestamp(null);
		                }	 
		                else {	
		                	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(shramdaan_time.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getShramdaan().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getShramdaan().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 sess.evict(photo);
		                 sequence++;
		             }
		         }
		         List<MultipartFile> photos_forestry=userfileup.getPhotos_forestry();
		         List<String> forestry_lat = userfileup.getPhotos_forestry_lat();
			     List<String> forestry_lng = userfileup.getPhotos_forestry_lng();
			     List<String> forestry_time = userfileup.getPhotos_forestry_time();
		         for (int i = 0; i < photos_forestry.size(); i++) 
		         {
		             MultipartFile image = photos_forestry.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getForestry());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(forestry_lat.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(forestry_lat.get(i));
						if(forestry_lng.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(forestry_lng.get(i));
						
		                if (forestry_time.get(i).equalsIgnoreCase("0")) {
		                	 photo.setPhoto_timestamp(null);
		                }
		                else {	
		                	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(forestry_time.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                	
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getForestry().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getForestry().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 sess.evict(photo);
		                 sequence++;
		             }
		         }
		         List<MultipartFile> photos_janbhagidari=userfileup.getPhotos_janbhagidari();
		         List<String> janbhagidari_lat = userfileup.getPhotos_janbhagidari_lat();
			     List<String> janbhagidari_lng = userfileup.getPhotos_janbhagidari_lng();
			     List<String> janbhagidari_time = userfileup.getPhotos_janbhagidari_time();
		         for (int i = 0; i < photos_janbhagidari.size(); i++) 
		         {
		             MultipartFile image = photos_janbhagidari.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getAwarded());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(janbhagidari_lat.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(janbhagidari_lat.get(i));
						if(janbhagidari_lng.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(janbhagidari_lng.get(i));
						
		                if (janbhagidari_time.get(i).equalsIgnoreCase("0")) {
		                	 photo.setPhoto_timestamp(null);
		                }
		                else {	
		                	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(janbhagidari_time.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getAwarded().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getAwarded().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 sess.evict(photo);
		                 sequence++;
		             }
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
				//List<WatershedMahotsavInauguarationActPhoto> tempList = query1.list();
			    // list.addAll(tempList); 
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

	@Override
	public boolean checkMahotsavInaugurationExits(Integer stCode) {
		
		Integer value = 0;
	    Boolean status = false; // Default to false in case no results found
	    
	    try (Session session = sessionFactory.openSession()) {
	        Transaction tx = session.beginTransaction();
	        
	        SQLQuery query = session.createSQLQuery("select count(*) from watershed_mahotsav_inauguaration where st_code = :vCode");
	        query.setInteger("vCode", stCode);
	        value = ((Number) query.uniqueResult()).intValue();

	        if (value > 0) {
	            status = true;
	        }
	        
	        tx.commit();
	    } catch (Exception ex) {
	        ex.printStackTrace(); // Log exception for debugging
	    }

	    return status;
	}

	@Override
	public List<InaugurationMahotsavBean> getMahotsavInaugurationEditList(Integer inauguaration_id) {
		
		String getReport=getMahotsavInaugurationEditList;
		Session session = sessionFactory.getCurrentSession();
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("wcdcid",inauguaration_id); 
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
		finally {
			//session.getTransaction().commit();
			//session.flush();
		//session.close();
		}
		return list;
		
	}

	@Override
	public String updateMahotsavInaugurationDetails(InaugurationMahotsavBean userfileup, HttpSession session) {
		
		
		Session sess = sessionFactory.getCurrentSession();
		
		String res = "fail";
		int sequence=0;
		try {
			sess.beginTransaction();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			Date inaugurationDate = formatter.parse(userfileup.getDate());
			String st_code=session.getAttribute("stateCode").toString();
			
			List<MultipartFile> photos_bhoomipoojanu=userfileup.getPhotos_bhoomipoojan();
			List<MultipartFile> photos_lokarpanu=userfileup.getPhotos_lokarpan();
			List<MultipartFile> photos_shramdaanu=userfileup.getPhotos_shramdaan();
			List<MultipartFile> photos_forestryu=userfileup.getPhotos_forestry();
			List<MultipartFile> photos_janbhagidariu=userfileup.getPhotos_janbhagidari();
			
			System.out.println("photos_bhoomipoojanu="+photos_bhoomipoojanu.size());
			
			if(photos_bhoomipoojanu.size()==1 && photos_lokarpanu.size()==1 && photos_shramdaanu.size()==1 && photos_forestryu.size()==1 && photos_janbhagidariu.size()==1) 
			{
				
				WatershedMahotsavInauguaration data = (WatershedMahotsavInauguaration) sess.get(WatershedMahotsavInauguaration.class, userfileup.getInauguaration_id());
				
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
				data.setUpdatedOn(new java.util.Date());
				sess.update(data);
				res="success";
				sess.getTransaction().commit();

				
			}
			else {
			
				String filePath="D:\\Inauguration\\";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/mahotsavdoc/Inauguration/";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/mahotsavdoc/Inauguration/";
			
			List list1 = sess.createSQLQuery("select value_id from watershed_mahotsav_inauguaration_sequence").list();
			sequence=Integer.parseInt(list1.get(0).toString());
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			String code=st_code.toString()+userfileup.getDistrict().toString()+userfileup.getBlock().toString();
			//System.out.println("state="+code);
			
			WatershedMahotsavInauguaration data = (WatershedMahotsavInauguaration) sess.get(WatershedMahotsavInauguaration.class, userfileup.getInauguaration_id());
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
			data.setUpdatedOn(new java.util.Date());
			sess.update(data);
			
			  List<MultipartFile> photos = userfileup.getPhotos_bhoomipoojan();
		      List<String> latitudes = userfileup.getPhotos_bhoomipoojan_lat();
		      List<String> longitudes = userfileup.getPhotos_bhoomipoojan_lng();
		      List<String> timestamps = userfileup.getPhotos_bhoomipoojan_time();

		      if(photos.size()>1) {
		    	  List<String> imgList = new ArrayList<String>();
		    	  
		    	  List<WatershedMahotsavInauguarationActPhoto> listbhoomi = new ArrayList<WatershedMahotsavInauguarationActPhoto>();
		    	  Query querybhoomi = sess.createQuery("from WatershedMahotsavInauguarationActPhoto where watershedMahotsavInauguaration.inauguarationId = :inaugid and actId=:act");
				  querybhoomi.setInteger("inaugid", userfileup.getInauguaration_id());
				  querybhoomi.setInteger("act", userfileup.getBhoomipoojan());
				  listbhoomi = querybhoomi.list();
				 for (WatershedMahotsavInauguarationActPhoto photo : listbhoomi) {
					   
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
				 SQLQuery querybhoomip = sess.createSQLQuery("delete from watershed_mahotsav_inauguaration_act_photo where inauguaration_id=:nrmpkid and act_id=:actId");
				 querybhoomip.setInteger("nrmpkid", userfileup.getInauguaration_id());
				 querybhoomip.setInteger("actId", userfileup.getBhoomipoojan());
				 querybhoomip.executeUpdate();
		      
		         for (int i = 0; i < photos.size(); i++) 
		         {
		             MultipartFile image = photos.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getBhoomipoojan());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setUpdated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(latitudes.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(latitudes.get(i));
						if(longitudes.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(longitudes.get(i));
						
		                if (timestamps.get(i).equalsIgnoreCase("0")) {
		                	 photo.setPhoto_timestamp(null);
		                }	 
		                else {	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(timestamps.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getBhoomipoojan().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getBhoomipoojan().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 
		                 sequence++;
		             }
		         }
		      }
		         List<MultipartFile> photos_lokarpan=userfileup.getPhotos_lokarpan();
		         List<String> lokarpan_lat = userfileup.getPhotos_lokarpan_lat();
			     List<String> lokarpan_lng = userfileup.getPhotos_lokarpan_lng();
			     List<String> lokarpan_time = userfileup.getPhotos_lokarpan_time();
			     
			     if(photos_lokarpan.size()>1) {
			    	  List<String> imgList = new ArrayList<String>();
			    	  
			    	  List<WatershedMahotsavInauguarationActPhoto> listlokarpan = new ArrayList<WatershedMahotsavInauguarationActPhoto>();
			    	  Query querylokarpan = sess.createQuery("from WatershedMahotsavInauguarationActPhoto where watershedMahotsavInauguaration.inauguarationId = :inaugid and actId=:act");
					  querylokarpan.setInteger("inaugid", userfileup.getInauguaration_id());
					  querylokarpan.setInteger("act", userfileup.getLokarpan());
					  listlokarpan = querylokarpan.list();
					 for (WatershedMahotsavInauguarationActPhoto photo : listlokarpan) {
						   
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
					 SQLQuery querybhoomip = sess.createSQLQuery("delete from watershed_mahotsav_inauguaration_act_photo where inauguaration_id=:nrmpkid and act_id=:actId");
					 querybhoomip.setInteger("nrmpkid", userfileup.getInauguaration_id());
					 querybhoomip.setInteger("actId", userfileup.getLokarpan());
					 querybhoomip.executeUpdate();
			     
		         for (int i = 0; i < photos_lokarpan.size(); i++) 
		         {
		             MultipartFile image = photos_lokarpan.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getLokarpan());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setUpdated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(lokarpan_lat.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(lokarpan_lat.get(i));
						if(lokarpan_lng.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(lokarpan_lng.get(i));
						
		               if (lokarpan_time.get(i).equalsIgnoreCase("0")) {
		                	 photo.setPhoto_timestamp(null);
		               }	 
		               else {	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(lokarpan_time.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getLokarpan().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getLokarpan().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                
		                 sequence++;
		             }
		         }
			     }
		         List<MultipartFile> photos_shramdaan=userfileup.getPhotos_shramdaan();
		         List<String> shramdaan_lat = userfileup.getPhotos_shramdaan_lat();
			     List<String> shramdaan_lng = userfileup.getPhotos_shramdaan_lng();
			     List<String> shramdaan_time = userfileup.getPhotos_shramdaan_time();
			     
			     if(photos_shramdaan.size()>1) {
			    	  List<String> imgList = new ArrayList<String>();
			    	  
			    	  List<WatershedMahotsavInauguarationActPhoto> listshramdaan = new ArrayList<WatershedMahotsavInauguarationActPhoto>();
			    	  Query queryshramdaan = sess.createQuery("from WatershedMahotsavInauguarationActPhoto where watershedMahotsavInauguaration.inauguarationId = :inaugid and actId=:act");
					  queryshramdaan.setInteger("inaugid", userfileup.getInauguaration_id());
					  queryshramdaan.setInteger("act", userfileup.getShramdaan());
					  listshramdaan = queryshramdaan.list();
					 for (WatershedMahotsavInauguarationActPhoto photo : listshramdaan) {
						   
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
					 SQLQuery querybhoomip = sess.createSQLQuery("delete from watershed_mahotsav_inauguaration_act_photo where inauguaration_id=:nrmpkid and act_id=:actId");
					 querybhoomip.setInteger("nrmpkid", userfileup.getInauguaration_id());
					 querybhoomip.setInteger("actId", userfileup.getShramdaan());
					 querybhoomip.executeUpdate();
			     
		         for (int i = 0; i < photos_shramdaan.size(); i++) 
		         {
		             MultipartFile image = photos_shramdaan.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getShramdaan());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setUpdated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(shramdaan_lat.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(shramdaan_lat.get(i));
						if(shramdaan_lng.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(shramdaan_lng.get(i));
						
		                if (shramdaan_time.get(i).equalsIgnoreCase("0")) { 
		                	 photo.setPhoto_timestamp(null);
		                }	 
		                else {	
		                	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(shramdaan_time.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getShramdaan().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getShramdaan().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 sequence++;
		             }
		         }
			     }
		         List<MultipartFile> photos_forestry=userfileup.getPhotos_forestry();
		         List<String> forestry_lat = userfileup.getPhotos_forestry_lat();
			     List<String> forestry_lng = userfileup.getPhotos_forestry_lng();
			     List<String> forestry_time = userfileup.getPhotos_forestry_time();
			     
			     if(photos_forestry.size()>1) {
			    	  List<String> imgList = new ArrayList<String>();
			    	  
			    	  List<WatershedMahotsavInauguarationActPhoto> listforestry = new ArrayList<WatershedMahotsavInauguarationActPhoto>();
			    	  Query queryforestry = sess.createQuery("from WatershedMahotsavInauguarationActPhoto where watershedMahotsavInauguaration.inauguarationId = :inaugid and actId=:act");
					  queryforestry.setInteger("inaugid", userfileup.getInauguaration_id());
					  queryforestry.setInteger("act", userfileup.getForestry());
					  listforestry = queryforestry.list();
					 for (WatershedMahotsavInauguarationActPhoto photo : listforestry) {
						   
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
					 SQLQuery querybhoomip = sess.createSQLQuery("delete from watershed_mahotsav_inauguaration_act_photo where inauguaration_id=:nrmpkid and act_id=:actId");
					 querybhoomip.setInteger("nrmpkid", userfileup.getInauguaration_id());
					 querybhoomip.setInteger("actId", userfileup.getForestry());
					 querybhoomip.executeUpdate();
			     
		         for (int i = 0; i < photos_forestry.size(); i++) 
		         {
		             MultipartFile image = photos_forestry.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getForestry());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setUpdated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(forestry_lat.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(forestry_lat.get(i));
						if(forestry_lng.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(forestry_lng.get(i));
						
		                if (forestry_time.get(i).equalsIgnoreCase("0")) {
		                	 photo.setPhoto_timestamp(null);
		                }
		                else {	
		                	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(forestry_time.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                	
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getForestry().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getForestry().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                
		                 sequence++;
		             }
		         }
			     }
		         List<MultipartFile> photos_janbhagidari=userfileup.getPhotos_janbhagidari();
		         List<String> janbhagidari_lat = userfileup.getPhotos_janbhagidari_lat();
			     List<String> janbhagidari_lng = userfileup.getPhotos_janbhagidari_lng();
			     List<String> janbhagidari_time = userfileup.getPhotos_janbhagidari_time();
			     
			     if(photos_janbhagidari.size()>1) {
			    	  List<String> imgList = new ArrayList<String>();
			    	  
			    	  List<WatershedMahotsavInauguarationActPhoto> listjanbhagidari = new ArrayList<WatershedMahotsavInauguarationActPhoto>();
			    	  Query queryjanbhagidari = sess.createQuery("from WatershedMahotsavInauguarationActPhoto where watershedMahotsavInauguaration.inauguarationId = :inaugid and actId=:act");
					  queryjanbhagidari.setInteger("inaugid", userfileup.getInauguaration_id());
					  queryjanbhagidari.setInteger("act", userfileup.getAwarded());
					  listjanbhagidari = queryjanbhagidari.list();
					 for (WatershedMahotsavInauguarationActPhoto photo : listjanbhagidari) {
						   
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
					 SQLQuery querybhoomip = sess.createSQLQuery("delete from watershed_mahotsav_inauguaration_act_photo where inauguaration_id=:nrmpkid and act_id=:actId");
					 querybhoomip.setInteger("nrmpkid", userfileup.getInauguaration_id());
					 querybhoomip.setInteger("actId", userfileup.getAwarded());
					 querybhoomip.executeUpdate();
			     
					 
		         for (int i = 0; i < photos_janbhagidari.size(); i++) 
		         {
		             MultipartFile image = photos_janbhagidari.get(i);
		             if (!image.isEmpty()) {
		            	WatershedMahotsavInauguarationActPhoto photo = new WatershedMahotsavInauguarationActPhoto();
		                photo.setWatershedMahotsavInauguaration(data);
		                photo.setActId(userfileup.getAwarded());
				        photo.setCreatedBy(session.getAttribute("loginID").toString());
						photo.setCreated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setUpdated_date(new Timestamp(new java.util.Date().getTime()));
						photo.setRequestedIp(ipAddr);
						if(janbhagidari_lat.get(i).equalsIgnoreCase("0"))
							photo.setLatitude(null);
						else
							photo.setLatitude(janbhagidari_lat.get(i));
						if(janbhagidari_lng.get(i).equalsIgnoreCase("0"))
							photo.setLongitute(null);
						else
							photo.setLongitute(janbhagidari_lng.get(i));
						
		                if (janbhagidari_time.get(i).equalsIgnoreCase("0")) {
		                	 photo.setPhoto_timestamp(null);
		                }
		                else {	
		                	
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				            java.util.Date parsedDate = sdf.parse(janbhagidari_time.get(i));
				            Timestamp timestamp = new Timestamp(parsedDate.getTime());
		                    photo.setPhoto_timestamp(timestamp);
		                }
		                 // Upload the file
		                commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getAwarded().toString(), sequence);
		                 // Store URL
		                photo.setPhotoUrl(filePath+"I"+code+userfileup.getAwarded().toString()+sequence+"_"+image.getOriginalFilename());

		                 sess.save(photo);
		                 
		                 sequence++;
		             }
		         }
			     }
			SQLQuery sqlQuery = sess.createSQLQuery("UPDATE watershed_mahotsav_inauguaration_sequence SET value_id=:aut");
			sqlQuery.setInteger("aut", sequence);
			sqlQuery.executeUpdate();
			
			res = "success";
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
	public List<String> getImageMahotsavInaugurationId(Integer inaugId) {
		
		Session session = sessionFactory.getCurrentSession();
		List<WatershedMahotsavInauguarationActPhoto> list = new ArrayList<WatershedMahotsavInauguarationActPhoto>();
		List<String> imgList = new ArrayList<>();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from WatershedMahotsavInauguarationActPhoto where watershedMahotsavInauguaration.inauguarationId = :inaugrationId");
			query.setInteger("inaugrationId", inaugId);
			list = query.list();
			for (WatershedMahotsavInauguarationActPhoto photo : list) 
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
