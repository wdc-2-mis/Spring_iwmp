package app.swachhata.daoImpl;

import java.io.File;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import app.common.CommonFunctions;
import app.mahotsav.bean.WatershedMahotsavProjectLevelBean;
import app.mahotsav.model.WatershedMahotsavInauguaration;
import app.mahotsav.model.WatershedMahotsavInauguarationActPhoto;
import app.mahotsav.model.WatershedMahotsavInauguarationActivityMaster;
import app.mahotsav.model.WatershedMahotsavProjectLevel;
import app.mahotsav.model.WatershedMahotsavProjectLvlPhoto;
import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;
import app.swachhata.controller.WatershedSwachhataBean;
import app.swachhata.dao.WatershedSwachhataDao;
import app.swachhata.model.WatershedSwachhataActivityMaster;
import app.swachhata.model.WatershedSwachhataProjectLevel;
import app.swachhata.model.WatershedSwachhataProjectLevelPhoto;

@Repository("WatershedSwachhataDao")
public class WatershedSwachhataDaoImpl implements WatershedSwachhataDao{

	
	@Autowired
	protected SessionFactory sessionFactory; 
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Value("${getWatershedSwachhataAtProj}")
	String getWatershedSwachhataAtProj;
	
	@Value("${getWatershedSwachhataDetailsRpt}")
	String getWatershedSwachhataDetailsRpt;
	
	@Value("${getDistWatershedSwachhataDetailsRpt}")
	String getDistWatershedSwachhataDetailsRpt;
	
	
	@Override
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block) {

		List<IwmpVillage> villList = new ArrayList<IwmpVillage>();
		String hql="select village from IwmpVillage village where village.vcode in(select distinct iwmpVillage.vcode from IwmpProjectLocation where iwmpMProject.projectId =:projid) and village.vcode in(select v.vcode from IwmpVillage v where v.iwmpGramPanchayat.iwmpBlock.bcode=:blk)";
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projid", projid);
			query.setInteger("blk", block);
			villList = query.list();
		
			for (IwmpVillage vill : villList) {
				map.put(vill.getVcode(), vill.getVillageName());
		
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
	public String saveWatershedSwachhataDetails(WatershedSwachhataBean userfileup, HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		String res = "fail";
		String upload="unUpload";
		int sequence=0;
		try {
			sess.beginTransaction();
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; 
			LocalDateTime localDateTime = LocalDateTime.parse(userfileup.getDatetime(), formatter); 
			Timestamp mahotsavDate = Timestamp.valueOf(localDateTime); 

//			Date mahotsavDate = formatter.parse(userfileup.getDatetime());
			String st_code = session.getAttribute("stateCode").toString();
			String loginId = session.getAttribute("loginID").toString();
			

			String filePath="D:\\ProjectLevel\\";
		// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/swachhata/projectLevel/";
		// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/swachhata/projectLevel/";
			
			WatershedSwachhataProjectLevel data = new WatershedSwachhataProjectLevel();
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			IwmpState s= new IwmpState();
			s.setStCode(Integer.parseInt(st_code));
			IwmpDistrict d= new IwmpDistrict();
			d.setDcode(userfileup.getDistrict());
			IwmpMProject p = new IwmpMProject();
			p.setProjectId(userfileup.getProject());
			IwmpBlock b= new IwmpBlock();
			b.setBcode(userfileup.getBlock()); 
			IwmpVillage v=new IwmpVillage();
			v.setVcode(userfileup.getVillage());
			
			data.setState(s);
			data.setDistrict(d);
			data.setProject(p);
			data.setBlock(b);
			data.setVillage(v);
			
			data.setCreatedBy(loginId);
			data.setCreatedDate(LocalDate.now());
			data.setRequestedIp(ipAddr);
			data.setStatus('D');
			
			data.setSwachhataDate(localDateTime);
			data.setSwachhataLocation(userfileup.getLocation());
			
			data.setShg(userfileup.getShg());
			data.setUsg(userfileup.getUg());
			data.setFpo(userfileup.getFpo());
			data.setStudent(userfileup.getYouth());
			data.setOther(userfileup.getOther());
			data.setTotal(userfileup.getTotal());
			data.setSapling(userfileup.getNo_sapling());
			data.setLokarpan(userfileup.getNo_works_lokarpan());
			data.setShramdaan(userfileup.getNo_location_shramdaan());
			data.setCleanliness(userfileup.getNo_cleanliness());
			data.setAwareness(userfileup.getNo_awareness());
			
			sess.save(data);
			String code=st_code.toString()+userfileup.getVillage()+"_"+data.getSwachhataId();
			
			List<String> saplinglat = userfileup.getPhotos_sapling_lat();
			List<String> saplinglng = userfileup.getPhotos_sapling_lng();
			List<String> saplingtime = userfileup.getPhotos_sapling_time();
			sequence= 1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			for (MultipartFile image : userfileup.getPhotos_sapling()) {
				WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
		        if (!image.isEmpty()) {
		        	photo.setSwachhata(data);
		        	photo.setCreatedBy(loginId);
					photo.setCreatedDate(LocalDate.now());
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getSapling().toString(), sequence);
		        	WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getSapling());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getSapling().toString()+sequence+"_"+image.getOriginalFilename());
		        	// Set GPS/time metadata (aligned by index)
		        	int i = sequence -1;
		            if (saplinglat != null && i < saplinglat.size()) photo.setLatitude(saplinglat.get(i));
		            if (saplinglng != null && i < saplinglng.size()) photo.setLongitude(saplinglng.get(i));
		            if (saplingtime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhotoTimestamp(null);
		            }else {
		            	
		            	DateTimeFormatter formatter1 =DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
		            	LocalDateTime dateTime =LocalDateTime.parse(saplingtime.get(i), formatter1);
			            if (saplingtime != null && i < saplingtime.size()) 
			            	photo.setPhotoTimestamp(dateTime);
		            }
		            

		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
			}
			sequence= 1;
			List<String> lokarpanLat = userfileup.getPhotos_lokarpan_lat();
			List<String> lokarpanLng = userfileup.getPhotos_lokarpan_lng();
			List<String> lokarpanTime = userfileup.getPhotos_lokarpan_time();
			for (MultipartFile image : userfileup.getPhotos_lokarpan()) {
				WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
		        if (!image.isEmpty()) {
		        	photo.setSwachhata(data);
		        	photo.setCreatedBy(loginId);
		        	photo.setCreatedDate(LocalDate.now());
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getLokarpan().toString(), sequence);
		        	WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getLokarpan());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getLokarpan().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (lokarpanLat != null && i < lokarpanLat.size()) photo.setLatitude(lokarpanLat.get(i));
		            if (lokarpanLng != null && i < lokarpanLng.size()) photo.setLongitude(lokarpanLng.get(i));
		            if (lokarpanTime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhotoTimestamp(null);
		            }else {
		            	DateTimeFormatter formatter1 =DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
		            	LocalDateTime dateTime =LocalDateTime.parse(lokarpanTime.get(i), formatter1);
			            if (lokarpanTime != null && i < lokarpanTime.size()) 
			            	photo.setPhotoTimestamp(dateTime);
		            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
			}
			sequence= 1;
			List<String> shramLat = userfileup.getPhotos_shramdaan_lat();
			List<String> shramLng = userfileup.getPhotos_shramdaan_lng();
			List<String> shramTime = userfileup.getPhotos_shramdaan_time();
			for (MultipartFile image : userfileup.getPhotos_shramdaan()) {
				WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
		        if (!image.isEmpty()) {
		        	photo.setSwachhata(data);
		        	photo.setCreatedBy(loginId);
		        	photo.setCreatedDate(LocalDate.now());
					photo.setRequestedIp(ipAddr);
					commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getShramdaan().toString(), sequence);
					WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getShramdaan());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getShramdaan().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (shramLat != null && i < shramLat.size()) photo.setLatitude(shramLat.get(i));
		            if (shramLng != null && i < shramLng.size()) photo.setLongitude(shramLng.get(i));
		            if (shramTime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhotoTimestamp(null);
		            }else {
		            	
		            	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
			            LocalDateTime dateTime = LocalDateTime.parse(shramTime.get(i), formatter1);
			            if (shramTime != null && i < shramTime.size()) 
			            	photo.setPhotoTimestamp(dateTime);
		            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
			}
			sequence= 1;
			List<String> cleanlinesslat = userfileup.getPhotos_cleanliness_lat();
			List<String> cleanlinesslng = userfileup.getPhotos_cleanliness_lng();
			List<String> cleanlinesstime = userfileup.getPhotos_cleanliness_time();
			for (MultipartFile image : userfileup.getPhotos_cleanliness()) {
				WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
		        if (!image.isEmpty()) {
		        	photo.setSwachhata(data);
		        	photo.setCreatedBy(loginId);
		        	photo.setCreatedDate(LocalDate.now());
					photo.setRequestedIp(ipAddr);
					commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getCleanliness().toString(), sequence);
					WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getCleanliness());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getCleanliness().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (cleanlinesslat != null && i < cleanlinesslat.size()) photo.setLatitude(cleanlinesslat.get(i));
		            if (cleanlinesslng != null && i < cleanlinesslng.size()) photo.setLongitude(cleanlinesslng.get(i));
		            if (cleanlinesstime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhotoTimestamp(null);
		            }else {
		            	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
			            LocalDateTime dateTime = LocalDateTime.parse(cleanlinesstime.get(i), formatter1);
			            if (cleanlinesstime != null && i < cleanlinesstime.size()) 
			            	photo.setPhotoTimestamp(dateTime);
		            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
		        
			}
			
			sequence= 1;
			List<String> awarenesslat = userfileup.getPhotos_awareness_lat();
			List<String> awarenesslng = userfileup.getPhotos_awareness_lng();
			List<String> awarenesstime = userfileup.getPhotos_awareness_time();
			for (MultipartFile image : userfileup.getPhotos_awareness()) {
				WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
		        if (!image.isEmpty()) {
		        	photo.setSwachhata(data);
		        	photo.setCreatedBy(loginId);
		        	photo.setCreatedDate(LocalDate.now());
					photo.setRequestedIp(ipAddr);
					commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getAwareness().toString(), sequence);
					WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getAwareness());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getAwareness().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (awarenesslat != null && i < awarenesslat.size()) photo.setLatitude(awarenesslat.get(i));
		            if (awarenesslng != null && i < awarenesslng.size()) photo.setLongitude(awarenesslng.get(i));
		            if (awarenesstime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhotoTimestamp(null);
		            }else {
		            	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
			            LocalDateTime dateTime = LocalDateTime.parse(awarenesstime.get(i), formatter1);
			            if (awarenesstime != null && i < awarenesstime.size()) 
			            	photo.setPhotoTimestamp(dateTime);
		            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
		        
			}
			sess.flush();
			sess.clear();
			
			res = "success";
			sess.getTransaction().commit();

		}catch (Exception ex) {
			res = "fail";
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}
		return res;
	}

	@Override
	public List<WatershedSwachhataBean> getWatershedSwachhataAtProj(String loginId) {
		
		List<WatershedSwachhataBean> list = new ArrayList<WatershedSwachhataBean>();
		String hql = getWatershedSwachhataAtProj;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(hql);
			query.setInteger("loginid", Integer.parseInt(loginId));
			query.setResultTransformer(Transformers.aliasToBean(WatershedSwachhataBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}




	@Override
	public String completeWatershedSwachhataDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("update watershed_swachhata_project_level set status='C' where swachhata_id=:nrmpkid");
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
	public String deleteWatershedSwachhataDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		List<String> imgList = new ArrayList<String>();
		List<WatershedSwachhataProjectLevelPhoto> list = new ArrayList<WatershedSwachhataProjectLevelPhoto>();
		
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 
			 @SuppressWarnings("rawtypes")
			 Query query1 = session.createQuery("from WatershedSwachhataProjectLevelPhoto where swachhata.swachhataId = :swid");
			 for(int i=0;i<assetid.size(); i++)
			 {
				query1.setInteger("swid", assetid.get(i));
				list = query1.list();
			 }
			 for (WatershedSwachhataProjectLevelPhoto photo : list) {
				   
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
			 
			 SQLQuery query = session.createSQLQuery("delete from watershed_swachhata_project_level_photo where swachhata_id=:nrmpkid");
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
			 SQLQuery query2 = session.createSQLQuery("delete from watershed_swachhata_project_level where swachhata_id=:nrmpkid");
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
	public List<String> getImageSwachhataProjLvlId(Integer swachhataid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<WatershedSwachhataProjectLevelPhoto> list = new ArrayList<WatershedSwachhataProjectLevelPhoto>();
		List<String> imgList = new ArrayList<>();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from WatershedSwachhataProjectLevelPhoto where swachhata.swachhataId = :id");
			query.setInteger("id", swachhataid);
			list = query.list();
			for (WatershedSwachhataProjectLevelPhoto photo : list) 
			{
				//server
				imgList.add(photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
				//System.out.println(" kdy= "+photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
				
				//local
				//imgList.add(photo.getPhotoUrl().replaceAll(".*\\\\", ""));
//				System.out.println(" kdy= "+photo.getPhotoUrl().replaceAll(".*\\\\", ""));
			}
			
			session.getTransaction().commit();
		}
		catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return imgList;
	}




	@Override
	public List<WatershedSwachhataBean> getWatershedSwachhataidProjLvlEdit(Integer id) {
		// TODO Auto-generated method stub
		List<WatershedSwachhataBean> list = new ArrayList<WatershedSwachhataBean>();
		List<WatershedSwachhataProjectLevel> wlist = new ArrayList<WatershedSwachhataProjectLevel>();
//		List<WatershedMahotsavProjectLvlPhoto> plist = new ArrayList<WatershedMahotsavProjectLvlPhoto>();
		//String hql = getAllWatershedMahotsavProjLvlData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query= session.createQuery("from WatershedSwachhataProjectLevel where swachhataId = :id");
			query.setInteger("id",id);
			wlist = query.list();
			 
			 for(WatershedSwachhataProjectLevel lvl : wlist) {
				 WatershedSwachhataBean bean = new WatershedSwachhataBean();
				 bean.setSwachhata_id(id);
				 SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

				 bean.setDatetime(lvl.getSwachhataDate().toString());
				 bean.setSt_code(lvl.getState().getStCode());
				 bean.setStname(lvl.getState().getStName());
				 bean.setDistrict(lvl.getDistrict().getDcode());
				 bean.setDistname(lvl.getDistrict().getDistName());
				 bean.setProject(lvl.getProject().getProjectId());
				 bean.setProjname(lvl.getProject().getProjName());
				 bean.setBlock(lvl.getBlock().getBcode());
				 bean.setBlockname(lvl.getBlock().getBlockName());
				 bean.setVillage(lvl.getVillage().getVcode());
				 bean.setVillagename(lvl.getVillage().getVillageName());
				 bean.setLocation(lvl.getSwachhataLocation());
				 
				 bean.setShg(lvl.getShg());
				 bean.setUsg(lvl.getUsg());
				 bean.setFpo(lvl.getFpo());
				 bean.setYouth(lvl.getStudent());
				 bean.setOther(lvl.getOther());
				 bean.setTotal(lvl.getTotal());
				 bean.setNo_sapling(lvl.getSapling());
				 bean.setNo_works_lokarpan(lvl.getLokarpan());
				 bean.setNo_location_shramdaan(lvl.getShramdaan());
				 bean.setNo_cleanliness(lvl.getCleanliness());
				 bean.setNo_awareness(lvl.getAwareness());
				 
				list.add(bean); 
				 
			 }
			
			session.getTransaction().commit();
		} 
		catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}




	@Override
	public String updateWatershedSwachhataDetails(WatershedSwachhataBean userfileup, HttpSession session) {
		// TODO Auto-generated method stub
		Session sess = sessionFactory.getCurrentSession();
		
		String res = "fail";
		String upload="unUpload";
		int sequence=0;
		try {
			sess.beginTransaction();
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; 
			LocalDateTime localDateTime = LocalDateTime.parse(userfileup.getDatetime(), formatter); 
			Timestamp mahotsavDate = Timestamp.valueOf(localDateTime); 

//			Date mahotsavDate = formatter.parse(userfileup.getDatetime());
			String st_code = session.getAttribute("stateCode").toString();
			String loginId = session.getAttribute("loginID").toString();
			
			List<MultipartFile> photos_sapling = userfileup.getPhotos_sapling();
			List<MultipartFile> photos_lokarpan = userfileup.getPhotos_lokarpan();
			List<MultipartFile> photos_shramdaan = userfileup.getPhotos_shramdaan();
			List<MultipartFile> photos_cleanliness = userfileup.getPhotos_cleanliness();
			List<MultipartFile> photos_awareness = userfileup.getPhotos_awareness();
			
			
			if(photos_sapling.size()==1 && photos_lokarpan.size()==1 && photos_shramdaan.size()==1 && photos_cleanliness.size()==1 && photos_awareness.size()==1) 
			{
				
				WatershedSwachhataProjectLevel data = (WatershedSwachhataProjectLevel) sess.get(WatershedSwachhataProjectLevel.class, userfileup.getWaterid());
				
				data.setUpdatedBy(loginId);
				data.setUpdatedDate(LocalDate.now());
				
				data.setSwachhataDate(localDateTime);
				data.setSwachhataLocation(userfileup.getLocation());
				
				data.setShg(userfileup.getShg());
				data.setUsg(userfileup.getUg());
				data.setFpo(userfileup.getFpo());
				data.setStudent(userfileup.getYouth());
				data.setOther(userfileup.getOther());
				data.setTotal(userfileup.getTotal());
				data.setSapling(userfileup.getNo_sapling());
				data.setLokarpan(userfileup.getNo_works_lokarpan());
				data.setShramdaan(userfileup.getNo_location_shramdaan());
				data.setCleanliness(userfileup.getNo_cleanliness());
				data.setAwareness(userfileup.getNo_awareness());
				
				sess.update(data);
				res="success";
				sess.getTransaction().commit();

				
			}
			else {
			

			String filePath="D:\\ProjectLevel\\";
		// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/swachhata/projectLevel/";
		// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/swachhata/projectLevel/";
			
			WatershedSwachhataProjectLevel data = (WatershedSwachhataProjectLevel) sess.get(WatershedSwachhataProjectLevel.class, userfileup.getWaterid());
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			data.setUpdatedBy(loginId);
			data.setUpdatedDate(LocalDate.now());
			
			data.setSwachhataDate(localDateTime);
			data.setSwachhataLocation(userfileup.getLocation());
			data.setShg(userfileup.getShg());
			data.setUsg(userfileup.getUg());
			data.setFpo(userfileup.getFpo());
			data.setStudent(userfileup.getYouth());
			data.setOther(userfileup.getOther());
			data.setTotal(userfileup.getTotal());
			data.setSapling(userfileup.getNo_sapling());
			data.setLokarpan(userfileup.getNo_works_lokarpan());
			data.setShramdaan(userfileup.getNo_location_shramdaan());
			data.setCleanliness(userfileup.getNo_cleanliness());
			data.setAwareness(userfileup.getNo_awareness());
			
			sess.update(data);
			
			String code=st_code.toString()+userfileup.getVillage()+"_"+data.getSwachhataId();
			
			List<MultipartFile> photos = userfileup.getPhotos_sapling();
			List<String> saplinglat = userfileup.getPhotos_sapling_lat();
			List<String> saplinglng = userfileup.getPhotos_sapling_lng();
			List<String> saplingtime = userfileup.getPhotos_sapling_time();
			sequence= 1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			
			 if(photos.size()>1) {
		    	  List<String> imgList = new ArrayList<String>();
		    	  List<WatershedSwachhataProjectLevelPhoto> listSapling = new ArrayList<WatershedSwachhataProjectLevelPhoto>();
		    	  Query querySapling = sess.createQuery("from WatershedSwachhataProjectLevelPhoto where swachhata.swachhataId = :inaugid and actId=:act");
		    	  querySapling.setInteger("inaugid", userfileup.getWaterid());
		    	  querySapling.setInteger("act", userfileup.getSapling());
				  listSapling = querySapling.list();
				 for (WatershedSwachhataProjectLevelPhoto photo : listSapling) {
					   
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
				 SQLQuery querySaplingp = sess.createSQLQuery("delete from watershed_swachhata_project_level_photo where swachhata_id=:nrmpkid and act_id=:actId");
				 querySaplingp.setInteger("nrmpkid", userfileup.getWaterid());
				 querySaplingp.setInteger("actId", userfileup.getSapling());
				 querySaplingp.executeUpdate();
				 for (MultipartFile image : userfileup.getPhotos_sapling()) {
						WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
				        if (!image.isEmpty()) {
				        	photo.setSwachhata(data);
				        	photo.setCreatedBy(loginId);
							photo.setCreatedDate(LocalDate.now());
							photo.setRequestedIp(ipAddr);
				        	commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getSapling().toString(), sequence);
				        	WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getSapling());
				        	photo.setActivity(actId);
				        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getSapling().toString()+sequence+"_"+image.getOriginalFilename());
				        	// Set GPS/time metadata (aligned by index)
				        	int i = sequence -1;
				            if (saplinglat.get(i).equalsIgnoreCase("0")) 
				            	photo.setLatitude(null);
				            else
				            	photo.setLatitude(saplinglat.get(i));
				            
				            if (saplinglng.get(i).equalsIgnoreCase("0")) 
				            	photo.setLongitude(null);
				            else
				            	photo.setLongitude(saplinglng.get(i));
				            
				            if (saplingtime.get(i).equalsIgnoreCase("0")) {
				            	photo.setPhotoTimestamp(null);
				            }else {
				            	
				            	DateTimeFormatter formatter1 =DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
				            	LocalDateTime dateTime =LocalDateTime.parse(saplingtime.get(i), formatter1);
					            if (saplingtime != null && i < saplingtime.size()) 
					            	photo.setPhotoTimestamp(dateTime);
				            }
				        	sequence = sequence+1;
				        	sess.save(photo);
				        }
					} 
		      }
		
			sequence= 1;
			List<MultipartFile> photosl = userfileup.getPhotos_lokarpan();
			List<String> lokarpanLat = userfileup.getPhotos_lokarpan_lat();
			List<String> lokarpanLng = userfileup.getPhotos_lokarpan_lng();
			List<String> lokarpanTime = userfileup.getPhotos_lokarpan_time();
			if(photosl.size()>1) {
		    	  List<String> imgList = new ArrayList<String>();
		    	  List<WatershedSwachhataProjectLevelPhoto> listlokarpan = new ArrayList<WatershedSwachhataProjectLevelPhoto>();
		    	  Query querylokarpan = sess.createQuery("from WatershedSwachhataProjectLevelPhoto where swachhata.swachhataId = :inaugid and actId=:act");
		    	  querylokarpan.setInteger("inaugid", userfileup.getWaterid());
		    	  querylokarpan.setInteger("act", userfileup.getLokarpan());
				  listlokarpan = querylokarpan.list();
				 for (WatershedSwachhataProjectLevelPhoto photo : listlokarpan) {
					   
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
				 SQLQuery querylok = sess.createSQLQuery("delete from watershed_swachhata_project_level_photo where swachhata_id=:nrmpkid and act_id=:actId");
				 querylok.setInteger("nrmpkid", userfileup.getWaterid());
				 querylok.setInteger("actId", userfileup.getLokarpan());
				 querylok.executeUpdate();
				 for (MultipartFile image : userfileup.getPhotos_lokarpan()) {
					 WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
					 if (!image.isEmpty()) {
		        	photo.setSwachhata(data);
		        	photo.setCreatedBy(loginId);
		        	photo.setCreatedDate(LocalDate.now());
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getLokarpan().toString(), sequence);
		        	WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getLokarpan());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getLokarpan().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (lokarpanLat.get(i).equalsIgnoreCase("0")) 
		            	photo.setLatitude(null);
		            else
		            	photo.setLatitude(lokarpanLat.get(i));
		            
		            if (lokarpanLng.get(i).equalsIgnoreCase("0")) 
		            	photo.setLongitude(null);
		            else
		            	photo.setLongitude(lokarpanLng.get(i));
		            
		            if (lokarpanTime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhotoTimestamp(null);
		            }else {
		            	DateTimeFormatter formatter1 =DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
		            	LocalDateTime dateTime =LocalDateTime.parse(lokarpanTime.get(i), formatter1);
			            if (lokarpanTime != null && i < lokarpanTime.size()) 
			            	photo.setPhotoTimestamp(dateTime);
		            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
			}
			}
			sequence= 1;
			List<MultipartFile> photoss = userfileup.getPhotos_shramdaan();
			List<String> shramLat = userfileup.getPhotos_shramdaan_lat();
			List<String> shramLng = userfileup.getPhotos_shramdaan_lng();
			List<String> shramTime = userfileup.getPhotos_shramdaan_time();
			if(photoss.size()>1) {
		    	  List<String> imgList = new ArrayList<String>();
		    	  List<WatershedSwachhataProjectLevelPhoto> listshramdaan = new ArrayList<WatershedSwachhataProjectLevelPhoto>();
		    	  Query queryshramdaan = sess.createQuery("from WatershedSwachhataProjectLevelPhoto where swachhata.swachhataId = :inaugid and actId=:act");
		    	  queryshramdaan.setInteger("inaugid", userfileup.getWaterid());
		    	  queryshramdaan.setInteger("act", userfileup.getShramdaan());
				  listshramdaan = queryshramdaan.list();
				 for (WatershedSwachhataProjectLevelPhoto photo : listshramdaan) {
					   
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
				 SQLQuery queryshram = sess.createSQLQuery("delete from watershed_swachhata_project_level_photo where swachhata_id=:nrmpkid and act_id=:actId");
				 queryshram.setInteger("nrmpkid", userfileup.getWaterid());
				 queryshram.setInteger("actId", userfileup.getShramdaan());
				 queryshram.executeUpdate();
				 for (MultipartFile image : userfileup.getPhotos_shramdaan()) {
					 WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
					 if (!image.isEmpty()) {
			        	photo.setSwachhata(data);
			        	photo.setCreatedBy(loginId);
			        	photo.setCreatedDate(LocalDate.now());
						photo.setRequestedIp(ipAddr);
						commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getShramdaan().toString(), sequence);
						WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getShramdaan());
			        	photo.setActivity(actId);
			        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getShramdaan().toString()+sequence+"_"+image.getOriginalFilename());
			        	int i = sequence -1;
			            if (shramLat.get(i).equalsIgnoreCase("0")) 
			            	photo.setLatitude(null);
			            else
			            	photo.setLatitude(shramLat.get(i));
			            	
			            if (shramLng.get(i).equalsIgnoreCase("0")) 
			            	photo.setLongitude(null);
			            else
			            	photo.setLongitude(shramLng.get(i));
			            
			            if (shramTime.get(i).equalsIgnoreCase("0")) {
			            	photo.setPhotoTimestamp(null);
			            }
			            else {
			            	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
				            LocalDateTime dateTime = LocalDateTime.parse(shramTime.get(i), formatter1);
				            if (shramTime != null && i < shramTime.size()) 
				            	photo.setPhotoTimestamp(dateTime);
			            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
			}
			}
			sequence= 1;
			List<MultipartFile> photosc = userfileup.getPhotos_cleanliness();
			List<String> cleanlinesslat = userfileup.getPhotos_cleanliness_lat();
			List<String> cleanlinesslng = userfileup.getPhotos_cleanliness_lng();
			List<String> cleanlinesstime = userfileup.getPhotos_cleanliness_time();
			if(photosc.size()>1) {
		    	  List<String> imgList = new ArrayList<String>();
		    	  List<WatershedSwachhataProjectLevelPhoto> listcleanliness = new ArrayList<WatershedSwachhataProjectLevelPhoto>();
		    	  Query querycleanliness = sess.createQuery("from WatershedSwachhataProjectLevelPhoto where swachhata.swachhataId = :inaugid and actId=:act");
		    	  querycleanliness.setInteger("inaugid", userfileup.getWaterid());
		    	  querycleanliness.setInteger("act", userfileup.getCleanliness());
				  listcleanliness = querycleanliness.list();
				 for (WatershedSwachhataProjectLevelPhoto photo : listcleanliness) {
					   
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
				 SQLQuery querycleanl = sess.createSQLQuery("delete from watershed_swachhata_project_level_photo where swachhata_id=:nrmpkid and act_id=:actId");
				 querycleanl.setInteger("nrmpkid", userfileup.getWaterid());
				 querycleanl.setInteger("actId", userfileup.getCleanliness());
				 querycleanl.executeUpdate();
			for (MultipartFile image : userfileup.getPhotos_cleanliness()) {
				WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
		        if (!image.isEmpty()) {
		        	photo.setSwachhata(data);
		        	photo.setCreatedBy(loginId);
		        	photo.setCreatedDate(LocalDate.now());
					photo.setRequestedIp(ipAddr);
					commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getCleanliness().toString(), sequence);
					WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getCleanliness());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getCleanliness().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (cleanlinesslat.get(i).equalsIgnoreCase("0")) 
		            	photo.setLatitude(null);
		            else
		            	photo.setLatitude(cleanlinesslat.get(i));
		            
		            if (cleanlinesslng.get(i).equalsIgnoreCase("0")) 
		            	photo.setLongitude(null);
		            else
		            	photo.setLongitude(cleanlinesslng.get(i));
		            
		            if (cleanlinesstime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhotoTimestamp(null);
		            }else {
		            	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
			            LocalDateTime dateTime = LocalDateTime.parse(cleanlinesstime.get(i), formatter1);
			            if (cleanlinesstime != null && i < cleanlinesstime.size()) 
			            	photo.setPhotoTimestamp(dateTime);
		            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
		        
			}
			}
			sequence= 1;
			List<MultipartFile> photosa = userfileup.getPhotos_awareness();
			List<String> awarenesslat = userfileup.getPhotos_awareness_lat();
			List<String> awarenesslng = userfileup.getPhotos_awareness_lng();
			List<String> awarenesstime = userfileup.getPhotos_awareness_time();
			if(photosa.size()>1) {
		    	  List<String> imgList = new ArrayList<String>();
		    	  List<WatershedSwachhataProjectLevelPhoto> listawareness = new ArrayList<WatershedSwachhataProjectLevelPhoto>();
		    	  Query queryawareness = sess.createQuery("from WatershedSwachhataProjectLevelPhoto where swachhata.swachhataId = :inaugid and actId=:act");
		    	  queryawareness.setInteger("inaugid", userfileup.getWaterid());
		    	  queryawareness.setInteger("act", userfileup.getAwareness());
				  listawareness = queryawareness.list();
				 for (WatershedSwachhataProjectLevelPhoto photo : listawareness) {
					   
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
				 SQLQuery queryaware = sess.createSQLQuery("delete from watershed_swachhata_project_level_photo where swachhata_id=:nrmpkid and act_id=:actId");
				 queryaware.setInteger("nrmpkid", userfileup.getWaterid());
				 queryaware.setInteger("actId", userfileup.getAwareness());
				 queryaware.executeUpdate();
			for (MultipartFile image : userfileup.getPhotos_awareness()) {
				WatershedSwachhataProjectLevelPhoto photo = new WatershedSwachhataProjectLevelPhoto();
		        if (!image.isEmpty()) {
		        	photo.setSwachhata(data);
		        	photo.setCreatedBy(loginId);
		        	photo.setCreatedDate(LocalDate.now());
					photo.setRequestedIp(ipAddr);
					commonFunction.uploadFileSwachhataDetails(image, filePath, code, userfileup.getAwareness().toString(), sequence);
					WatershedSwachhataActivityMaster actId = sess.get(WatershedSwachhataActivityMaster.class, userfileup.getAwareness());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"Swachhata"+code+userfileup.getAwareness().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (awarenesslat.get(i).equalsIgnoreCase("0")) 
		            	photo.setLatitude(null);
		            else
		            	photo.setLatitude(awarenesslat.get(i));
		            
		            if (awarenesslng.get(i).equalsIgnoreCase("0")) 
		            	photo.setLongitude(null);
		            else
		            	photo.setLongitude(awarenesslng.get(i));
		            if (awarenesstime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhotoTimestamp(null);
		            }else {
		            	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
			            LocalDateTime dateTime = LocalDateTime.parse(awarenesstime.get(i), formatter1);
			            if (awarenesstime != null && i < awarenesstime.size()) 
			            	photo.setPhotoTimestamp(dateTime);
		            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
		        
			}
			}
			sess.flush();
			sess.clear();
			
			res = "success";
			sess.getTransaction().commit();
			}

		}catch (Exception ex) {
			res = "fail";
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}
		return res;
	}
	
	@Override
	public List<WatershedSwachhataBean> getProjectLevelSwachhataStateWise(Integer stCode) {
		String sql = getWatershedSwachhataDetailsRpt;
		List<WatershedSwachhataBean> list = new ArrayList<WatershedSwachhataBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("stCode", stCode);
			query.setResultTransformer(Transformers.aliasToBean(WatershedSwachhataBean.class));
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}




	@Override
	public List<String> getImageByStcode(Integer stcode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<WatershedSwachhataProjectLevelPhoto> list = new ArrayList<WatershedSwachhataProjectLevelPhoto>();
		List<String> imgList = new ArrayList<>();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from WatershedSwachhataProjectLevelPhoto where swachhata.state.stCode = :id");
			query.setInteger("id", stcode);
			list = query.list();
			for (WatershedSwachhataProjectLevelPhoto photo : list) 
			{
				//server
				imgList.add(photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
				//System.out.println(" kdy= "+photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/")+1));
				
				//local
				//imgList.add(photo.getPhotoUrl().replaceAll(".*\\\\", ""));
//				System.out.println(" kdy= "+photo.getPhotoUrl().replaceAll(".*\\\\", ""));
			}
			
			session.getTransaction().commit();
		}
		catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return imgList;
	}




	@Override
	public List<WatershedSwachhataBean> getdistWSProjLvlProgRpt(int stCode) {
		String sql = getDistWatershedSwachhataDetailsRpt;
		List<WatershedSwachhataBean> list = new ArrayList<WatershedSwachhataBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("stCode", stCode);
			query.setResultTransformer(Transformers.aliasToBean(WatershedSwachhataBean.class));
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}

}
