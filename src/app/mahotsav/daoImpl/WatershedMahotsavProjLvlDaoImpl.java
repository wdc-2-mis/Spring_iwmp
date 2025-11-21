package app.mahotsav.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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
			data.setMaleParticipants(userfileup.getMaleParticipants());
			data.setFemaleParticipants(userfileup.getFemaleParticipants());
			data.setCentralMinister(userfileup.getCentralMinisters());
			data.setStateMinister(userfileup.getStateMinisters());
			data.setParliamentMembers(userfileup.getMembersOfParliament());
			data.setLegislativeAssemblyMembers(userfileup.getLegAssemblyMembers());
			data.setLegislativeCouncilMembers(userfileup.getLegCouncilMembers());
			data.setOtherPublicRepresentatives(userfileup.getPublicReps());
			data.setGovOfficials(userfileup.getGovOfficials());
			data.setBhoomiPoojanNoOfWorks(userfileup.getNo_works_bhoomipoojan());
			data.setLokarpanNoOfWorks(userfileup.getNo_works_lokarpan());
			data.setShramdaanNoOfLocation(userfileup.getNo_location_shramdaan());
			data.setShramdaanNoOfParticipatedPeople(userfileup.getNo_people_shramdaan());
			data.setNoOfAgroForsetry(userfileup.getArea_plantation());
			sess.save(data);
			String code=st_code.toString()+"_"+data.getId();
			
			for(String lang : userfileup.getPhotos_bhoomipoojan_lat()) {
				System.out.println("yogesh = "+lang);
			}
			List<String> bhoomiLat = userfileup.getPhotos_bhoomipoojan_lat();
			List<String> bhoomiLng = userfileup.getPhotos_bhoomipoojan_lng();
			List<String> bhoomiTime = userfileup.getPhotos_bhoomipoojan_time();
			sequence= 1;
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
		            if (bhoomiTime != null && i < bhoomiTime.size()) photo.setPhototimestamp(Timestamp.valueOf(bhoomiTime.get(i)));

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
		            if (lokarpanTime != null && i < lokarpanTime.size()) photo.setPhototimestamp(Timestamp.valueOf(lokarpanTime.get(i)));
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
		            if (shramTime != null && i < shramTime.size()) photo.setPhototimestamp(Timestamp.valueOf(shramTime.get(i)));
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
		            if (forestTime != null && i < forestTime.size()) photo.setPhototimestamp(Timestamp.valueOf(forestTime.get(i)));
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
			Query query= session.createQuery(getWatershedMahotsavProjLvlData);
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
			Query query= session.createQuery(getComWatershedMahotsavProjLvlData);
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

}
