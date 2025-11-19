package app.mahotsav.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import app.watershedyatra.model.WatershedYatraInauguaration;

@Repository("WatershedMahotsavInaugurationDao")
public class WatershedMahotsavInaugurationDaoImpl implements WatershedMahotsavInaugurationDao{
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired
	protected SessionFactory sessionFactory; 
	
//	@Value("${getInaugurationDetails}")
//	String getInaugurationDetails;
	
	
	
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
			
		//	List list = sess.createQuery("SELECT iwmpState.stCode FROM WatershedYatraInauguaration where iwmpState.stCode=:villageCode").setInteger("villageCode", Integer.parseInt(session.getAttribute("stateCode").toString())).list();
			//	result=Integer.parseInt(list.get(0).toString());
		//	if(list.isEmpty()) {
				
			
			
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
		catch (Exception ex) {
			res = "fail";
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}

	return res;
	}
	
	
	
	
	
	
	
	
	
	
	

}
