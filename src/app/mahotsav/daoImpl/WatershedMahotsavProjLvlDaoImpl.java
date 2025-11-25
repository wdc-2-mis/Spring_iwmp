package app.mahotsav.daoImpl;

import java.io.File;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import app.mahotsav.bean.WMPrabhatPheriBean;
import app.mahotsav.bean.WatershedMahotsavProjectLevelBean;
import app.mahotsav.dao.WatershedMahotsavProjLvlDao;
import app.mahotsav.model.WatershedMahotsavInauguarationActPhoto;
import app.mahotsav.model.WatershedMahotsavInauguarationActivityMaster;
import app.mahotsav.model.WatershedMahotsavProjectLevel;
import app.mahotsav.model.WatershedMahotsavProjectLvlPhoto;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;

@Repository("WatershedMahotsavProjLvlDao")
public class WatershedMahotsavProjLvlDaoImpl implements WatershedMahotsavProjLvlDao {

	
	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired
	protected SessionFactory sessionFactory; 
	
	@Value("${getWatershedMahotsavProjLvlData}")
	String getWatershedMahotsavProjLvlData;
	
	@Value("${getComWatershedMahotsavProjLvlData}")
	String getComWatershedMahotsavProjLvlData;
	
	
	@Value("${getAllWatershedMahotsavProjLvlData}")
	String getAllWatershedMahotsavProjLvlData;
	
	@Override
	public String saveMahotsavProjLvlDetails(WatershedMahotsavProjectLevelBean userfileup, HttpSession session) {
		Session sess = sessionFactory.getCurrentSession();
		
		String res = "fail";
		String upload="unUpload";
		int sequence=0;
		try {
			sess.beginTransaction();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date mahotsavDate = formatter.parse(userfileup.getDatetime());
			String st_code = session.getAttribute("stateCode").toString();
			String loginId = session.getAttribute("loginID").toString();
			

			String filePath="D:\\ProjectLevel\\";
		// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/mahotsavdoc/ProjectLevel/";
		// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/vanyatradoc/ProjectLevel/";
			
			WatershedMahotsavProjectLevel data = new WatershedMahotsavProjectLevel();
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			IwmpState s= new IwmpState();
			s.setStCode(Integer.parseInt(st_code));
			IwmpDistrict d= new IwmpDistrict();
			d.setDcode(userfileup.getDistrict());
			IwmpBlock b= new IwmpBlock();
			b.setBcode(userfileup.getBlock()); 
			
			data.setState(s);
			data.setDistrict(d);
			data.setBlock(b);
			
			data.setCreatedBy(loginId);
			data.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
			data.setRequestedIp(ipAddr);
			data.setStatus('D');
			
			data.setMahotsavDate(mahotsavDate);
			data.setMahotsavLocation(userfileup.getLocation());
			data.setMaleParticipants(userfileup.getMaleparticipants());
			data.setFemaleParticipants(userfileup.getFemaleparticipants());
			data.setCentralMinister(userfileup.getCentralministers());
			data.setStateMinister(userfileup.getStateministers());
			data.setParliamentMembers(userfileup.getMembersofparliament());
			data.setLegislativeAssemblyMembers(userfileup.getLegassemblymembers());
			data.setLegislativeCouncilMembers(userfileup.getLegcouncilmembers());
			data.setOtherPublicRepresentatives(userfileup.getPublicreps());
			data.setGovOfficials(userfileup.getGovofficials());
			data.setBhoomiPoojanNoOfWorks(userfileup.getNo_works_bhoomipoojan());
			data.setLokarpanNoOfWorks(userfileup.getNo_works_lokarpan());
			data.setShramdaanNoOfLocation(userfileup.getNo_location_shramdaan());
			data.setShramdaanNoOfParticipatedPeople(userfileup.getNo_people_shramdaan());
			data.setNoOfAgroForsetry(userfileup.getArea_plantation());
			sess.save(data);
			String code=st_code.toString()+"_"+data.getId();
			
//			for(String lang : userfileup.getPhotos_bhoomipoojan_lat()) {
//				System.out.println("yogesh = "+lang);
//			}
			List<String> bhoomiLat = userfileup.getPhotos_bhoomipoojan_lat();
			List<String> bhoomiLng = userfileup.getPhotos_bhoomipoojan_lng();
			List<String> bhoomiTime = userfileup.getPhotos_bhoomipoojan_time();
			sequence= 1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			for (MultipartFile image : userfileup.getPhotos_bhoomipoojan()) {
				WatershedMahotsavProjectLvlPhoto photo = new WatershedMahotsavProjectLvlPhoto();
		        if (!image.isEmpty()) {
		        	photo.setWatershedMahotsav(data);
		        	photo.setCreatedBy(loginId);
					photo.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getBhoomipoojan().toString(), sequence);
		        	WatershedMahotsavInauguarationActivityMaster actId = sess.get(WatershedMahotsavInauguarationActivityMaster.class, userfileup.getBhoomipoojan());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getBhoomipoojan().toString()+sequence+"_"+image.getOriginalFilename());
		        	// Set GPS/time metadata (aligned by index)
		        	int i = sequence -1;
		            if (bhoomiLat != null && i < bhoomiLat.size()) photo.setLatitude(bhoomiLat.get(i));
		            if (bhoomiLng != null && i < bhoomiLng.size()) photo.setLongitude(bhoomiLng.get(i));
		            if (bhoomiTime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhototimestamp(null);
		            }else {
		            	java.util.Date parsedDate = sdf.parse(bhoomiTime.get(i));
			            Timestamp timestamp = new Timestamp(parsedDate.getTime());
			            if (bhoomiTime != null && i < bhoomiTime.size()) photo.setPhototimestamp(timestamp);
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
				WatershedMahotsavProjectLvlPhoto photo = new WatershedMahotsavProjectLvlPhoto();
		        if (!image.isEmpty()) {
		        	photo.setWatershedMahotsav(data);
		        	photo.setCreatedBy(loginId);
					photo.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getLokarpan().toString(), sequence);
		        	WatershedMahotsavInauguarationActivityMaster actId = sess.get(WatershedMahotsavInauguarationActivityMaster.class, userfileup.getLokarpan());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getLokarpan().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (lokarpanLat != null && i < lokarpanLat.size()) photo.setLatitude(lokarpanLat.get(i));
		            if (lokarpanLng != null && i < lokarpanLng.size()) photo.setLongitude(lokarpanLng.get(i));
		            if (lokarpanTime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhototimestamp(null);
		            }else {
		            	java.util.Date parsedDate = sdf.parse(lokarpanTime.get(i));
			            Timestamp timestamp = new Timestamp(parsedDate.getTime());
			            if (lokarpanTime != null && i < lokarpanTime.size()) photo.setPhototimestamp(timestamp);
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
				WatershedMahotsavProjectLvlPhoto photo = new WatershedMahotsavProjectLvlPhoto();
		        if (!image.isEmpty()) {
		        	photo.setWatershedMahotsav(data);
		        	photo.setCreatedBy(loginId);
					photo.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getShramdaan().toString(), sequence);
		        	WatershedMahotsavInauguarationActivityMaster actId = sess.get(WatershedMahotsavInauguarationActivityMaster.class, userfileup.getShramdaan());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getShramdaan().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (shramLat != null && i < shramLat.size()) photo.setLatitude(shramLat.get(i));
		            if (shramLng != null && i < shramLng.size()) photo.setLongitude(shramLng.get(i));
		            if (shramTime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhototimestamp(null);
		            }else {
		            	java.util.Date parsedDate = sdf.parse(shramTime.get(i));
			            Timestamp timestamp = new Timestamp(parsedDate.getTime());
			            if (shramTime != null && i < shramTime.size()) photo.setPhototimestamp(timestamp);
		            }
		        	sequence = sequence+1;
		        	sess.save(photo);
		        }
			}
			sequence= 1;
			List<String> forestLat = userfileup.getPhotos_forestry_lat();
			List<String> forestLng = userfileup.getPhotos_forestry_lng();
			List<String> forestTime = userfileup.getPhotos_forestry_time();
			for (MultipartFile image : userfileup.getPhotos_forestry()) {
				WatershedMahotsavProjectLvlPhoto photo = new WatershedMahotsavProjectLvlPhoto();
		        if (!image.isEmpty()) {
		        	photo.setWatershedMahotsav(data);
		        	photo.setCreatedBy(loginId);
					photo.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
					photo.setRequestedIp(ipAddr);
		        	commonFunction.uploadFileMahotwavInauguration(image, filePath, code, userfileup.getForestry().toString(), sequence);
		        	WatershedMahotsavInauguarationActivityMaster actId = sess.get(WatershedMahotsavInauguarationActivityMaster.class, userfileup.getForestry());
		        	photo.setActivity(actId);
		        	photo.setPhotoUrl(filePath+"I"+code+userfileup.getForestry().toString()+sequence+"_"+image.getOriginalFilename());
		        	int i = sequence -1;
		            if (forestLat != null && i < forestLat.size()) photo.setLatitude(forestLat.get(i));
		            if (forestLng != null && i < forestLng.size()) photo.setLongitude(forestLng.get(i));
		            if (forestTime.get(i).equalsIgnoreCase("0")) {
		            	photo.setPhototimestamp(null);
		            }else {
		            	java.util.Date parsedDate = sdf.parse(forestTime.get(i));
			            Timestamp timestamp = new Timestamp(parsedDate.getTime());
			            if (forestTime != null && i < forestTime.size()) photo.setPhototimestamp(timestamp);
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
	public List<WatershedMahotsavProjectLevelBean> getWatershedMahotsavAtProjLvl(Integer stcode, String loginId) {
		List<WatershedMahotsavProjectLevelBean> list = new ArrayList<WatershedMahotsavProjectLevelBean>();
		String hql = getWatershedMahotsavProjLvlData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getWatershedMahotsavProjLvlData);
			query.setInteger("stcode",stcode);
//			query.setString("loginid", loginId);
			query.setResultTransformer(Transformers.aliasToBean(WatershedMahotsavProjectLevelBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public List<WatershedMahotsavProjectLevelBean> getComWatershedMahotsavAtProjLvl(Integer stcode, String loginId) {
		List<WatershedMahotsavProjectLevelBean> list = new ArrayList<WatershedMahotsavProjectLevelBean>();
		String hql = getComWatershedMahotsavProjLvlData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getComWatershedMahotsavProjLvlData);
			query.setInteger("stcode",stcode);
//			query.setString("loginid", loginId);
			query.setResultTransformer(Transformers.aliasToBean(WatershedMahotsavProjectLevelBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public String completeMahotsavProjLvlDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("update watershed_mahotsav_project_level set status='C' where watershed_mahotsav_id=:nrmpkid");
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
	public List<WatershedMahotsavProjectLevelBean> getBlksWiseWatershedMahotsavAtProjLvl(List<Integer> bcode,
			String loginId) {
		List<WatershedMahotsavProjectLevelBean> list = new ArrayList<WatershedMahotsavProjectLevelBean>();
		String hql = getAllWatershedMahotsavProjLvlData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(hql);
			query.setParameterList("bcode",bcode);
//			query.setString("loginid", loginId);
			query.setResultTransformer(Transformers.aliasToBean(WatershedMahotsavProjectLevelBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String deleteMahotsavProjLvlDetails(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		List<String> imgList = new ArrayList<String>();
		List<WatershedMahotsavProjectLvlPhoto> list = new ArrayList<WatershedMahotsavProjectLvlPhoto>();
		
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 
			// WatershedMahotsavInauguaration data = new WatershedMahotsavInauguaration();
			//WatershedMahotsavInauguarationActPhoto photo= new WatershedMahotsavInauguarationActPhoto();
			 @SuppressWarnings("rawtypes")
			 Query query1 = session.createQuery("from WatershedMahotsavProjectLvlPhoto where watershedMahotsav.id in (:id)");
			 query1.setParameterList("id", assetid);
			 list =query1.list();
			 for (WatershedMahotsavProjectLvlPhoto photo : list) {
				   
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
			 
			 SQLQuery query = session.createSQLQuery("delete from watershed_mahotsav_project_lvl_photo where watershed_mahotsav_id=:nrmpkid");
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
			 SQLQuery query2 = session.createSQLQuery("delete from watershed_mahotsav_project_level where watershed_mahotsav_id=:nrmpkid");
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

}
