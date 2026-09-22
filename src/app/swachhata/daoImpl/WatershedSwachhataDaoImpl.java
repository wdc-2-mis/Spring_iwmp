package app.swachhata.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import app.common.CommonFunctions;
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
		// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/mahotsavdoc/projectLevel/";
		// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/mahotsavdoc/projectLevel/";
			
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
			data.setSapling(userfileup.getNo_sapling());
			data.setLokarpan(userfileup.getNo_works_lokarpan());
			data.setShramdaan(userfileup.getNo_location_shramdaan());
			data.setCleanliness(userfileup.getNo_cleanliness());
			data.setAwareness(userfileup.getNo_awareness());
			
			sess.save(data);
			String code=st_code.toString()+userfileup.getVillage()+"_"+data.getSwachhataId();
			
//			for(String lang : userfileup.getPhotos_bhoomipoojan_lat()) {
//				System.out.println("yogesh = "+lang);
//			}
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

}
