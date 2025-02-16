package app.watershedyatra.daoImpl;

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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import app.common.CommonFunctions;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.dao.InaugurationDao;
import app.watershedyatra.model.WatershedYatraInauguaration;

@Repository("InaugurationDaoImpl")
public class InaugurationDaoImpl implements InaugurationDao {
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getInaugurationDetails}")
	String getInaugurationDetails;
	
//	@Value("${getImageByInaugurationId}")
//	String getImageByInaugurationId;
	
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
	public String saveInauguration(InaugurationBean userfileup, HttpSession session) {
		Session sess = sessionFactory.getCurrentSession();
		
		String res = "fail";
		String upload="unUpload";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date inaugurationDate = formatter.parse(userfileup.getDate());
			
		String filePath="D:\\Inauguration\\";
//			 String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/vanyatradoc/Inauguration/";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/vanyatradoc/Inauguration/";
			
			MultipartFile[] mfile = {userfileup.getFlagoff_photo1(), userfileup.getFlagoff_photo2(), userfileup.getThemesong_photo1(), userfileup.getThemesong_photo2(),
					 	userfileup.getBhoomipoojan_photo1(), userfileup.getBhoomipoojan_photo2(), userfileup.getLokarpan_photo1(), userfileup.getLokarpan_photo2(),
					 	userfileup.getShramdaan_photo1(), userfileup.getShramdaan_photo2(), userfileup.getPlantation_photo1(), userfileup.getPlantation_photo2(), 
					 	userfileup.getAward_photo1(), userfileup.getAward_photo2(), userfileup.getDept_stalls_photo1(), userfileup.getDept_stalls_photo2(), 
					 	userfileup.getShg_fpo_stalls_photo1(), userfileup.getShg_fpo_stalls_photo2(), userfileup.getLakhpati_didi_photo1(), userfileup.getLakhpati_didi_photo2() };
			
			
			
			/*
			  for (MultipartFile file : mfile) {
			  
			  upload=commonFunction.uploadFileforLMS(file, filePath,
			  userfileup.getBlock());
			  
			  }
			 */
			
			String[] fileDescriptions = {
				    "Flag1", "Flag2", "Song1", "Song2",
				    "Bhoomi1", "Bhoomi2", "Lok1", "Lok2",
				    "Shram1", "Shram2", "Plan1", "Plan2",
				    "Award1", "Award2", "Dept1", "Dept2",
				    "SHG1", "SHG2", "Lakh1", "Lakh2"
				};
			
			for (int i = 0; i < mfile.length; i++) {
			    MultipartFile file = mfile[i];
			    String act = fileDescriptions[i];
			   
			    upload = commonFunction.uploadFileforLMS(file, filePath, userfileup.getBlock(), act);
			}
			
			
			
			
			sess.beginTransaction();
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			WatershedYatraInauguaration data = new WatershedYatraInauguaration();
			IwmpState s= new IwmpState();
			s.setStCode(Integer.parseInt(session.getAttribute("stateCode").toString()));
			IwmpDistrict d= new IwmpDistrict();
			d.setDcode(userfileup.getDistrict());
			IwmpBlock b= new IwmpBlock();
			b.setBcode(userfileup.getBlock()); 
			if(upload.equals("upload")) {
			data.setIwmpState(s);
			data.setIwmpDistrict(d);
			data.setIwmpBlock(b);
			data.setCreatedBy(session.getAttribute("loginID").toString());
			data.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
			data.setRequestedIp(ipAddr);
			data.setStatus("C");
			
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
			data.setVanFlagOff(Boolean.valueOf(userfileup.getFlagoff()));
//			data.setVanFlagPath1(filePath+userfileup.getFlagoff_photo1().getOriginalFilename());
//			data.setVanFlagPath2(filePath+userfileup.getFlagoff_photo2().getOriginalFilename());
			
			data.setVanFlagPath1(!userfileup.getFlagoff_photo1().isEmpty() ? filePath + "I"+"Flag1"+userfileup.getBlock()+"_"+userfileup.getFlagoff_photo1().getOriginalFilename() :  null);
			if(!userfileup.getFlagoff_photo1().isEmpty()) {
				data.setVan_flag_path1_latitude(userfileup.getFlagoff_photo1_lat());
				data.setVan_flag_path1_longitute(userfileup.getFlagoff_photo1_lng());
				data.setVan_flag_path1_time(parseTimestamp(userfileup.getFlagoff_photo1_time()));
			}
			else {
				data.setVan_flag_path1_latitude(null);
				data.setVan_flag_path1_longitute(null);
				data.setVan_flag_path1_time(null);
			}
			
			data.setVanFlagPath2(!userfileup.getFlagoff_photo2().isEmpty() ? filePath + "I"+"Flag2"+userfileup.getBlock()+"_"+userfileup.getFlagoff_photo2().getOriginalFilename() :  null);
			if (!userfileup.getFlagoff_photo2().isEmpty()) {
				data.setVan_flag_path2_latitude(userfileup.getFlagoff_photo2_lat());
				data.setVan_flag_path2_longitute(userfileup.getFlagoff_photo2_lng());
				data.setVan_flag_path2_time(parseTimestamp(userfileup.getFlagoff_photo2_time()));
			}
			else {
				data.setVan_flag_path2_latitude(null);
				data.setVan_flag_path2_longitute(null);
				data.setVan_flag_path2_time(null);
			}
			
			data.setThemeSong(Boolean.valueOf(userfileup.getThemesong()));
//			data.setThemeSongPath1(filePath+userfileup.getThemesong_photo1().getOriginalFilename());
//			data.setThemeSongPath2(filePath+userfileup.getThemesong_photo2().getOriginalFilename());
			
			data.setThemeSongPath1(!userfileup.getThemesong_photo1().isEmpty() ? filePath + "I"+"Song1"+userfileup.getBlock()+"_"+userfileup.getThemesong_photo1().getOriginalFilename() :  null);
			if (!userfileup.getThemesong_photo1().isEmpty()) {
				data.setTheme_song_path1_latitude(userfileup.getThemesong_photo1_lat());
				data.setTheme_song_path1_longitute(userfileup.getThemesong_photo1_lng());
				data.setTheme_song_path1_time(parseTimestamp(userfileup.getThemesong_photo1_time()));
			}
			else {
				data.setTheme_song_path1_latitude(null);
				data.setTheme_song_path1_longitute(null);
				data.setTheme_song_path1_time(null);
			}
			
			data.setThemeSongPath2(!userfileup.getThemesong_photo2().isEmpty() ? filePath + "I"+"Song2"+userfileup.getBlock()+"_"+userfileup.getThemesong_photo2().getOriginalFilename() :  null);
			if (!userfileup.getThemesong_photo2().isEmpty()) {
				data.setTheme_song_path2_latitude(userfileup.getThemesong_photo2_lat());
				data.setTheme_song_path2_longitute(userfileup.getThemesong_photo2_lng());
				data.setTheme_song_path2_time(parseTimestamp(userfileup.getThemesong_photo2_time()));
			}
			else {
				data.setTheme_song_path2_latitude(null);
				data.setTheme_song_path2_longitute(null);
				data.setTheme_song_path2_time(null);
			}
			data.setBhoomiPoojanNoOfWorks(userfileup.getNo_works_bhoomipoojan());
			data.setBhoomiPoojanCostOfWorks(userfileup.getTot_works_bhoomipoojan());
//			data.setBhoomiPoojanPath1(filePath+userfileup.getBhoomipoojan_photo1().getOriginalFilename());
//			data.setBhoomiPoojanPath2(filePath+userfileup.getBhoomipoojan_photo2().getOriginalFilename());
			
			data.setBhoomiPoojanPath1(!userfileup.getBhoomipoojan_photo1().isEmpty() ? filePath + "I"+"Bhoomi1"+userfileup.getBlock()+"_"+userfileup.getBhoomipoojan_photo1().getOriginalFilename() :  null);
			if (!userfileup.getBhoomipoojan_photo1().isEmpty()) {
				data.setBhoomi_poojan_path1_latitude(userfileup.getBhoomipoojan_photo1_lat());
				data.setBhoomi_poojan_path1_longitute(userfileup.getBhoomipoojan_photo1_lng());
				data.setBhoomi_poojan_path1_time(parseTimestamp(userfileup.getBhoomipoojan_photo1_time()));
			}
			else {
				data.setBhoomi_poojan_path1_latitude(null);
				data.setBhoomi_poojan_path1_longitute(null);
				data.setBhoomi_poojan_path1_time(null);
			}
			
			data.setBhoomiPoojanPath2(!userfileup.getBhoomipoojan_photo2().isEmpty() ? filePath +"I"+"Bhoomi2"+userfileup.getBlock()+"_"+ userfileup.getBhoomipoojan_photo2().getOriginalFilename() :  null);
			if (!userfileup.getBhoomipoojan_photo2().isEmpty()) {
				data.setBhoomi_poojan_path2_latitude(userfileup.getBhoomipoojan_photo2_lat());
				data.setBhoomi_poojan_path2_longitute(userfileup.getBhoomipoojan_photo2_lng());
				data.setBhoomi_poojan_path2_time(parseTimestamp(userfileup.getBhoomipoojan_photo2_time()));
			}
			else {
				data.setBhoomi_poojan_path2_latitude(null);
				data.setBhoomi_poojan_path2_longitute(null);
				data.setBhoomi_poojan_path2_time(null);
			}
//			
			data.setLokarpanNoOfWorks(userfileup.getNo_works_lokarpan());
			data.setLokarpanCostOfWorks(userfileup.getTot_works_lokarpan());
//			data.setLokarpanPath1(filePath+userfileup.getLokarpan_photo1().getOriginalFilename());
//			data.setLokarpanPath2(filePath+userfileup.getLokarpan_photo2().getOriginalFilename());
			
			data.setLokarpanPath1(!userfileup.getLokarpan_photo1().isEmpty() ? filePath + "I"+"Lok1"+userfileup.getBlock()+"_"+userfileup.getLokarpan_photo1().getOriginalFilename() :  null);
			if (!userfileup.getLokarpan_photo1().isEmpty()) {
				data.setLokarpan_path1_latitude(userfileup.getLokarpan_photo1_lat());
				data.setLokarpan_path1_longitute(userfileup.getLokarpan_photo1_lng());
				data.setLokarpan_path1_time(parseTimestamp(userfileup.getLokarpan_photo1_time()));
			}
			else {
				data.setLokarpan_path1_latitude(null);
				data.setLokarpan_path1_longitute(null);
				data.setLokarpan_path1_time(null);
			}
			
			data.setLokarpanPath2(!userfileup.getLokarpan_photo2().isEmpty() ? filePath + "I"+"Lok2"+userfileup.getBlock()+"_"+userfileup.getLokarpan_photo2().getOriginalFilename() :  null);
			if (!userfileup.getLokarpan_photo2().isEmpty()) {
				data.setLokarpan_path2_latitude(userfileup.getLokarpan_photo2_lat());
				data.setLokarpan_path2_longitute(userfileup.getLokarpan_photo2_lng());
				data.setLokarpan_path2_time(parseTimestamp(userfileup.getLokarpan_photo2_time()));
			}
			else {
				data.setLokarpan_path2_latitude(null);
				data.setLokarpan_path2_longitute(null);
				data.setLokarpan_path2_time(null);
			}
			
			data.setShramdaanNoOfLocation(userfileup.getNo_location_shramdaan());
			data.setShramdaanNoOfParticipatedPeople(userfileup.getNo_people_shramdaan());
			data.setManHr(userfileup.getMan());
//			data.setShramdaanPath1(filePath+userfileup.getShramdaan_photo1().getOriginalFilename());
//			data.setShramdaanPath2(filePath+userfileup.getShramdaan_photo2().getOriginalFilename());
			
			data.setShramdaanPath1(!userfileup.getShramdaan_photo1().isEmpty() ? filePath + "I"+"Shram1"+userfileup.getBlock()+"_"+userfileup.getShramdaan_photo1().getOriginalFilename() :  null);
			if (!userfileup.getShramdaan_photo1().isEmpty()) {
				data.setShramdaan_path1_latitude(userfileup.getShramdaan_photo1_lat());
				data.setShramdaan_path1_longitute(userfileup.getShramdaan_photo1_lng());
				data.setShramdaan_path1_time(parseTimestamp(userfileup.getShramdaan_photo1_time()));
			}
			else {
				data.setShramdaan_path1_latitude(null);
				data.setShramdaan_path1_longitute(null);
				data.setShramdaan_path1_time(null);
			}
			
			data.setShramdaanPath2(!userfileup.getShramdaan_photo2().isEmpty() ? filePath + "I"+"Shram2"+userfileup.getBlock()+"_"+userfileup.getShramdaan_photo2().getOriginalFilename() :  null);
			if (!userfileup.getShramdaan_photo2().isEmpty()) {
				data.setShramdaan_path2_latitude(userfileup.getShramdaan_photo2_lat());
				data.setShramdaan_path2_longitute(userfileup.getShramdaan_photo2_lng());
				data.setShramdaan_path2_time(parseTimestamp(userfileup.getShramdaan_photo2_time()));
			}
			else {
				data.setShramdaan_path2_latitude(null);
				data.setShramdaan_path2_longitute(null);
				data.setShramdaan_path2_time(null);
			}
			
			data.setPlantationArea(userfileup.getArea_plantation());
			data.setNoOfAgroForsetry(userfileup.getNo_plantation());
//			data.setPlantationPath1(filePath+userfileup.getPlantation_photo1().getOriginalFilename());
//			data.setPlantationPath2(filePath+userfileup.getPlantation_photo2().getOriginalFilename());
			
			data.setPlantationPath1(!userfileup.getPlantation_photo1().isEmpty() ? filePath + "I"+"Plan1"+userfileup.getBlock()+"_"+userfileup.getPlantation_photo1().getOriginalFilename() :  null);
			if (!userfileup.getPlantation_photo1().isEmpty()) {
				data.setPlantation_path1_latitude(userfileup.getPlantation_photo1_lat());
				data.setPlantation_path1_longitute(userfileup.getPlantation_photo1_lng());
				data.setPlantation_path1_time(parseTimestamp(userfileup.getPlantation_photo1_time()));
			}
			else {
				data.setPlantation_path1_latitude(null);
				data.setPlantation_path1_longitute(null);
				data.setPlantation_path1_time(null);
			}
			
			data.setPlantationPath2(!userfileup.getPlantation_photo2().isEmpty() ? filePath + "I"+"Plan2"+userfileup.getBlock()+"_"+userfileup.getPlantation_photo2().getOriginalFilename() :  null);
			if (!userfileup.getPlantation_photo2().isEmpty()) {
				data.setPlantation_path2_latitude(userfileup.getPlantation_photo2_lat());
				data.setPlantation_path2_longitute(userfileup.getPlantation_photo2_lng());
				data.setPlantation_path2_time(parseTimestamp(userfileup.getPlantation_photo2_time()));
			}
			else {
				data.setPlantation_path2_latitude(null);
				data.setPlantation_path2_longitute(null);
				data.setPlantation_path2_time(null);
			}
			
			data.setAwardDistribution(userfileup.getNo_awards());
//			data.setAwardDistributionPath1(filePath+userfileup.getAward_photo1().getOriginalFilename());
//			data.setAwardDistributionPath2(filePath+userfileup.getAward_photo2().getOriginalFilename());
			
			data.setAwardDistributionPath1(!userfileup.getAward_photo1().isEmpty() ? filePath + "I"+"Award1"+userfileup.getBlock()+"_"+userfileup.getAward_photo1().getOriginalFilename() :  null);
			if (!userfileup.getAward_photo1().isEmpty()) {
				data.setAward_distribution_path1_latitude(userfileup.getAward_photo1_lat());
				data.setAward_distribution_path1_longitute(userfileup.getAward_photo1_lng());
				data.setAward_distribution_path1_time(parseTimestamp(userfileup.getAward_photo1_time()));
			}
			else {
				data.setAward_distribution_path1_latitude(null);
				data.setAward_distribution_path1_longitute(null);
				data.setAward_distribution_path1_time(null);
			}
			
			data.setAwardDistributionPath2(!userfileup.getAward_photo2().isEmpty() ? filePath + "I"+"Award2"+userfileup.getBlock()+"_"+userfileup.getAward_photo2().getOriginalFilename() :  null);
			if (!userfileup.getAward_photo2().isEmpty()) {
				data.setAward_distribution_path2_latitude(userfileup.getAward_photo2_lat());
				data.setAward_distribution_path2_longitute(userfileup.getAward_photo2_lng());
				data.setAward_distribution_path2_time(parseTimestamp(userfileup.getAward_photo2_time()));
			}
			else {
				data.setAward_distribution_path2_latitude(null);
				data.setAward_distribution_path2_longitute(null);
				data.setAward_distribution_path2_time(null);
			}
			
			data.setNoDeptStalls(userfileup.getDept_stalls());
//			data.setDeptStallsPath1(filePath+userfileup.getDept_stalls_photo1().getOriginalFilename());
//			data.setDeptStallsPath2(filePath+userfileup.getDept_stalls_photo2().getOriginalFilename());
			
			data.setDeptStallsPath1(!userfileup.getDept_stalls_photo1().isEmpty() ? filePath + "I"+"Dept1"+userfileup.getBlock()+"_"+userfileup.getDept_stalls_photo1().getOriginalFilename() :  null);
			if (!userfileup.getDept_stalls_photo1().isEmpty()) {
				data.setDept_stalls_path1_latitude(userfileup.getDept_stalls_photo1_lat());
				data.setDept_stalls_path1_longitute(userfileup.getDept_stalls_photo1_lng());
				data.setDept_stalls_path1_time(parseTimestamp(userfileup.getDept_stalls_photo1_time()));
			}
			else {
				data.setDept_stalls_path1_latitude(null);
				data.setDept_stalls_path1_longitute(null);
				data.setDept_stalls_path1_time(null);
			}
			
			data.setDeptStallsPath2(!userfileup.getDept_stalls_photo2().isEmpty() ? filePath +"I"+"Dept2"+userfileup.getBlock()+"_"+ userfileup.getDept_stalls_photo2().getOriginalFilename() :  null);
			if (!userfileup.getDept_stalls_photo2().isEmpty()) {
				data.setDept_stalls_path2_latitude(userfileup.getDept_stalls_photo2_lat());
				data.setDept_stalls_path2_longitute(userfileup.getDept_stalls_photo2_lng());
				data.setDept_stalls_path2_time(parseTimestamp(userfileup.getDept_stalls_photo2_time()));
			}
			else {
				data.setDept_stalls_path2_latitude(null);
				data.setDept_stalls_path2_longitute(null);
				data.setDept_stalls_path2_time(null);
			}
			
			data.setNoShgFpo(userfileup.getShg_fpo_stalls());
//			data.setShgFpoPath1(filePath+userfileup.getShg_fpo_stalls_photo1().getOriginalFilename());
//			data.setShgFpoPath2(filePath+userfileup.getShg_fpo_stalls_photo2().getOriginalFilename());
			
			data.setShgFpoPath1(!userfileup.getShg_fpo_stalls_photo1().isEmpty() ? filePath + "I"+"SHG1"+userfileup.getBlock()+"_"+userfileup.getShg_fpo_stalls_photo1().getOriginalFilename() :  null);
			if (!userfileup.getShg_fpo_stalls_photo1().isEmpty()) {
				data.setShg_fpo_path1_latitude(userfileup.getShg_fpo_stalls_photo1_lat());
				data.setShg_fpo_path1_longitute(userfileup.getShg_fpo_stalls_photo1_lng());
				data.setShg_fpo_path1_time(parseTimestamp(userfileup.getShg_fpo_stalls_photo1_time()));
			}
			else {
				data.setShg_fpo_path1_latitude(null);
				data.setShg_fpo_path1_longitute(null);
				data.setShg_fpo_path1_time(null);
			}
			
			data.setShgFpoPath2(!userfileup.getShg_fpo_stalls_photo2().isEmpty() ? filePath + "I"+"SHG2"+userfileup.getBlock()+"_"+userfileup.getShg_fpo_stalls_photo2().getOriginalFilename() :  null);
			if (!userfileup.getShg_fpo_stalls_photo2().isEmpty()) {
				data.setShg_fpo_path2_latitude(userfileup.getShg_fpo_stalls_photo2_lat());
				data.setShg_fpo_path2_longitute(userfileup.getShg_fpo_stalls_photo2_lng());
				data.setShg_fpo_path2_time(parseTimestamp(userfileup.getShg_fpo_stalls_photo2_time()));
			}
			else {
				data.setShg_fpo_path2_latitude(null);
				data.setShg_fpo_path2_longitute(null);
				data.setShg_fpo_path2_time(null);
			}
			
			data.setNoLakhpatiDidi(userfileup.getNo_lakhpati_didi());
//			data.setLakhpatiDidiPath1(filePath+userfileup.getLakhpati_didi_photo1().getOriginalFilename());
//			data.setLakhpatiDidiPath2(filePath+userfileup.getLakhpati_didi_photo2().getOriginalFilename());
			
			data.setLakhpatiDidiPath1(!userfileup.getLakhpati_didi_photo1().isEmpty() ? filePath + "I"+"Lakh1"+userfileup.getBlock()+"_"+userfileup.getLakhpati_didi_photo1().getOriginalFilename() :  null);
			if (!userfileup.getLakhpati_didi_photo1().isEmpty()) {
				data.setLakhpati_didi_path1_latitude(userfileup.getLakhpati_didi_photo1_lat());
				data.setLakhpati_didi_path1_longitute(userfileup.getLakhpati_didi_photo1_lng());
				data.setLakhpati_didi_path1_time(parseTimestamp(userfileup.getLakhpati_didi_photo1_time()));
			}
			else {
				data.setLakhpati_didi_path1_latitude(null);
				data.setLakhpati_didi_path1_longitute(null);
				data.setLakhpati_didi_path1_time(null);
			}
			
			data.setLakhpatiDidiPath2(!userfileup.getLakhpati_didi_photo2().isEmpty() ? filePath +  "I"+"Lakh2"+userfileup.getBlock()+"_"+userfileup.getLakhpati_didi_photo2().getOriginalFilename() :  null);
			if (!userfileup.getLakhpati_didi_photo2().isEmpty()) {
				data.setLakhpati_didi_path2_latitude(userfileup.getLakhpati_didi_photo2_lat());
				data.setLakhpati_didi_path2_longitute(userfileup.getLakhpati_didi_photo2_lng());
				data.setLakhpati_didi_path2_time(parseTimestamp(userfileup.getLakhpati_didi_photo2_time()));
			}
			else {
				data.setLakhpati_didi_path2_latitude(null);
				data.setLakhpati_didi_path2_longitute(null);
				data.setLakhpati_didi_path2_time(null);
			}
			sess.save(data);
			res = "success";
			sess.getTransaction().commit();
			}
			else if(upload.equals("fileisempty")) {
					data.setIwmpState(s);
					data.setIwmpDistrict(d);
					data.setIwmpBlock(b);
					data.setCreatedBy(session.getAttribute("loginID").toString());
					data.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
					data.setRequestedIp(ipAddr);
					data.setStatus("C");
					
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
					data.setVanFlagOff(Boolean.valueOf(userfileup.getFlagoff()));
//					data.setVanFlagPath1(filePath+userfileup.getFlagoff_photo1().getOriginalFilename());
//					data.setVanFlagPath2(filePath+userfileup.getFlagoff_photo2().getOriginalFilename());
					
					data.setVanFlagPath1(!userfileup.getFlagoff_photo1().isEmpty() ? filePath + "I"+"Flag1"+userfileup.getBlock()+"_"+userfileup.getFlagoff_photo1().getOriginalFilename() :  null);
					if(!userfileup.getFlagoff_photo1().isEmpty()) {
						data.setVan_flag_path1_latitude(userfileup.getFlagoff_photo1_lat());
						data.setVan_flag_path1_longitute(userfileup.getFlagoff_photo1_lng());
						data.setVan_flag_path1_time(parseTimestamp(userfileup.getFlagoff_photo1_time()));
					}
					else {
						data.setVan_flag_path1_latitude(null);
						data.setVan_flag_path1_longitute(null);
						data.setVan_flag_path1_time(null);
					}
					
					data.setVanFlagPath2(!userfileup.getFlagoff_photo2().isEmpty() ? filePath + "I"+"Flag2"+userfileup.getBlock()+"_"+userfileup.getFlagoff_photo2().getOriginalFilename() :  null);
					if (!userfileup.getFlagoff_photo2().isEmpty()) {
						data.setVan_flag_path2_latitude(userfileup.getFlagoff_photo2_lat());
						data.setVan_flag_path2_longitute(userfileup.getFlagoff_photo2_lng());
						data.setVan_flag_path2_time(parseTimestamp(userfileup.getFlagoff_photo2_time()));
					}
					else {
						data.setVan_flag_path2_latitude(null);
						data.setVan_flag_path2_longitute(null);
						data.setVan_flag_path2_time(null);
					}
					
					data.setThemeSong(Boolean.valueOf(userfileup.getThemesong()));
//					data.setThemeSongPath1(filePath+userfileup.getThemesong_photo1().getOriginalFilename());
//					data.setThemeSongPath2(filePath+userfileup.getThemesong_photo2().getOriginalFilename());
					
					data.setThemeSongPath1(!userfileup.getThemesong_photo1().isEmpty() ? filePath + "I"+"Song1"+userfileup.getBlock()+"_"+userfileup.getThemesong_photo1().getOriginalFilename() :  null);
					if (!userfileup.getThemesong_photo1().isEmpty()) {
						data.setTheme_song_path1_latitude(userfileup.getThemesong_photo1_lat());
						data.setTheme_song_path1_longitute(userfileup.getThemesong_photo1_lng());
						data.setTheme_song_path1_time(parseTimestamp(userfileup.getThemesong_photo1_time()));
					}
					else {
						data.setTheme_song_path1_latitude(null);
						data.setTheme_song_path1_longitute(null);
						data.setTheme_song_path1_time(null);
					}
					
					data.setThemeSongPath2(!userfileup.getThemesong_photo2().isEmpty() ? filePath + "I"+"Song2"+userfileup.getBlock()+"_"+userfileup.getThemesong_photo2().getOriginalFilename() :  null);
					if (!userfileup.getThemesong_photo2().isEmpty()) {
						data.setTheme_song_path2_latitude(userfileup.getThemesong_photo2_lat());
						data.setTheme_song_path2_longitute(userfileup.getThemesong_photo2_lng());
						data.setTheme_song_path2_time(parseTimestamp(userfileup.getThemesong_photo2_time()));
					}
					else {
						data.setTheme_song_path2_latitude(null);
						data.setTheme_song_path2_longitute(null);
						data.setTheme_song_path2_time(null);
					}
					data.setBhoomiPoojanNoOfWorks(userfileup.getNo_works_bhoomipoojan());
					data.setBhoomiPoojanCostOfWorks(userfileup.getTot_works_bhoomipoojan());
//					data.setBhoomiPoojanPath1(filePath+userfileup.getBhoomipoojan_photo1().getOriginalFilename());
//					data.setBhoomiPoojanPath2(filePath+userfileup.getBhoomipoojan_photo2().getOriginalFilename());
					
					data.setBhoomiPoojanPath1(!userfileup.getBhoomipoojan_photo1().isEmpty() ? filePath + "I"+"Bhoomi1"+userfileup.getBlock()+"_"+userfileup.getBhoomipoojan_photo1().getOriginalFilename() :  null);
					if (!userfileup.getBhoomipoojan_photo1().isEmpty()) {
						data.setBhoomi_poojan_path1_latitude(userfileup.getBhoomipoojan_photo1_lat());
						data.setBhoomi_poojan_path1_longitute(userfileup.getBhoomipoojan_photo1_lng());
						data.setBhoomi_poojan_path1_time(parseTimestamp(userfileup.getBhoomipoojan_photo1_time()));
					}
					else {
						data.setBhoomi_poojan_path1_latitude(null);
						data.setBhoomi_poojan_path1_longitute(null);
						data.setBhoomi_poojan_path1_time(null);
					}
					
					data.setBhoomiPoojanPath2(!userfileup.getBhoomipoojan_photo2().isEmpty() ? filePath +"I"+"Bhoomi2"+userfileup.getBlock()+"_"+ userfileup.getBhoomipoojan_photo2().getOriginalFilename() :  null);
					if (!userfileup.getBhoomipoojan_photo2().isEmpty()) {
						data.setBhoomi_poojan_path2_latitude(userfileup.getBhoomipoojan_photo2_lat());
						data.setBhoomi_poojan_path2_longitute(userfileup.getBhoomipoojan_photo2_lng());
						data.setBhoomi_poojan_path2_time(parseTimestamp(userfileup.getBhoomipoojan_photo2_time()));
					}
					else {
						data.setBhoomi_poojan_path2_latitude(null);
						data.setBhoomi_poojan_path2_longitute(null);
						data.setBhoomi_poojan_path2_time(null);
					}
//					
					data.setLokarpanNoOfWorks(userfileup.getNo_works_lokarpan());
					data.setLokarpanCostOfWorks(userfileup.getTot_works_lokarpan());
//					data.setLokarpanPath1(filePath+userfileup.getLokarpan_photo1().getOriginalFilename());
//					data.setLokarpanPath2(filePath+userfileup.getLokarpan_photo2().getOriginalFilename());
					
					data.setLokarpanPath1(!userfileup.getLokarpan_photo1().isEmpty() ? filePath + "I"+"Lok1"+userfileup.getBlock()+"_"+userfileup.getLokarpan_photo1().getOriginalFilename() :  null);
					if (!userfileup.getLokarpan_photo1().isEmpty()) {
						data.setLokarpan_path1_latitude(userfileup.getLokarpan_photo1_lat());
						data.setLokarpan_path1_longitute(userfileup.getLokarpan_photo1_lng());
						data.setLokarpan_path1_time(parseTimestamp(userfileup.getLokarpan_photo1_time()));
					}
					else {
						data.setLokarpan_path1_latitude(null);
						data.setLokarpan_path1_longitute(null);
						data.setLokarpan_path1_time(null);
					}
					
					data.setLokarpanPath2(!userfileup.getLokarpan_photo2().isEmpty() ? filePath + "I"+"Lok2"+userfileup.getBlock()+"_"+userfileup.getLokarpan_photo2().getOriginalFilename() :  null);
					if (!userfileup.getLokarpan_photo2().isEmpty()) {
						data.setLokarpan_path2_latitude(userfileup.getLokarpan_photo2_lat());
						data.setLokarpan_path2_longitute(userfileup.getLokarpan_photo2_lng());
						data.setLokarpan_path2_time(parseTimestamp(userfileup.getLokarpan_photo2_time()));
					}
					else {
						data.setLokarpan_path2_latitude(null);
						data.setLokarpan_path2_longitute(null);
						data.setLokarpan_path2_time(null);
					}
					
					data.setShramdaanNoOfLocation(userfileup.getNo_location_shramdaan());
					data.setShramdaanNoOfParticipatedPeople(userfileup.getNo_people_shramdaan());
					data.setManHr(userfileup.getMan());
//					data.setShramdaanPath1(filePath+userfileup.getShramdaan_photo1().getOriginalFilename());
//					data.setShramdaanPath2(filePath+userfileup.getShramdaan_photo2().getOriginalFilename());
					
					data.setShramdaanPath1(!userfileup.getShramdaan_photo1().isEmpty() ? filePath + "I"+"Shram1"+userfileup.getBlock()+"_"+userfileup.getShramdaan_photo1().getOriginalFilename() :  null);
					if (!userfileup.getShramdaan_photo1().isEmpty()) {
						data.setShramdaan_path1_latitude(userfileup.getShramdaan_photo1_lat());
						data.setShramdaan_path1_longitute(userfileup.getShramdaan_photo1_lng());
						data.setShramdaan_path1_time(parseTimestamp(userfileup.getShramdaan_photo1_time()));
					}
					else {
						data.setShramdaan_path1_latitude(null);
						data.setShramdaan_path1_longitute(null);
						data.setShramdaan_path1_time(null);
					}
					
					data.setShramdaanPath2(!userfileup.getShramdaan_photo2().isEmpty() ? filePath + "I"+"Shram2"+userfileup.getBlock()+"_"+userfileup.getShramdaan_photo2().getOriginalFilename() :  null);
					if (!userfileup.getShramdaan_photo2().isEmpty()) {
						data.setShramdaan_path2_latitude(userfileup.getShramdaan_photo2_lat());
						data.setShramdaan_path2_longitute(userfileup.getShramdaan_photo2_lng());
						data.setShramdaan_path2_time(parseTimestamp(userfileup.getShramdaan_photo2_time()));
					}
					else {
						data.setShramdaan_path2_latitude(null);
						data.setShramdaan_path2_longitute(null);
						data.setShramdaan_path2_time(null);
					}
					
					data.setPlantationArea(userfileup.getArea_plantation());
					data.setNoOfAgroForsetry(userfileup.getNo_plantation());
//					data.setPlantationPath1(filePath+userfileup.getPlantation_photo1().getOriginalFilename());
//					data.setPlantationPath2(filePath+userfileup.getPlantation_photo2().getOriginalFilename());
					
					data.setPlantationPath1(!userfileup.getPlantation_photo1().isEmpty() ? filePath + "I"+"Plan1"+userfileup.getBlock()+"_"+userfileup.getPlantation_photo1().getOriginalFilename() :  null);
					if (!userfileup.getPlantation_photo1().isEmpty()) {
						data.setPlantation_path1_latitude(userfileup.getPlantation_photo1_lat());
						data.setPlantation_path1_longitute(userfileup.getPlantation_photo1_lng());
						data.setPlantation_path1_time(parseTimestamp(userfileup.getPlantation_photo1_time()));
					}
					else {
						data.setPlantation_path1_latitude(null);
						data.setPlantation_path1_longitute(null);
						data.setPlantation_path1_time(null);
					}
					
					data.setPlantationPath2(!userfileup.getPlantation_photo2().isEmpty() ? filePath + "I"+"Plan2"+userfileup.getBlock()+"_"+userfileup.getPlantation_photo2().getOriginalFilename() :  null);
					if (!userfileup.getPlantation_photo2().isEmpty()) {
						data.setPlantation_path2_latitude(userfileup.getPlantation_photo2_lat());
						data.setPlantation_path2_longitute(userfileup.getPlantation_photo2_lng());
						data.setPlantation_path2_time(parseTimestamp(userfileup.getPlantation_photo2_time()));
					}
					else {
						data.setPlantation_path2_latitude(null);
						data.setPlantation_path2_longitute(null);
						data.setPlantation_path2_time(null);
					}
					
					data.setAwardDistribution(userfileup.getNo_awards());
//					data.setAwardDistributionPath1(filePath+userfileup.getAward_photo1().getOriginalFilename());
//					data.setAwardDistributionPath2(filePath+userfileup.getAward_photo2().getOriginalFilename());
					
					data.setAwardDistributionPath1(!userfileup.getAward_photo1().isEmpty() ? filePath + "I"+"Award1"+userfileup.getBlock()+"_"+userfileup.getAward_photo1().getOriginalFilename() :  null);
					if (!userfileup.getAward_photo1().isEmpty()) {
						data.setAward_distribution_path1_latitude(userfileup.getAward_photo1_lat());
						data.setAward_distribution_path1_longitute(userfileup.getAward_photo1_lng());
						data.setAward_distribution_path1_time(parseTimestamp(userfileup.getAward_photo1_time()));
					}
					else {
						data.setAward_distribution_path1_latitude(null);
						data.setAward_distribution_path1_longitute(null);
						data.setAward_distribution_path1_time(null);
					}
					
					data.setAwardDistributionPath2(!userfileup.getAward_photo2().isEmpty() ? filePath + "I"+"Award2"+userfileup.getBlock()+"_"+userfileup.getAward_photo2().getOriginalFilename() :  null);
					if (!userfileup.getAward_photo2().isEmpty()) {
						data.setAward_distribution_path2_latitude(userfileup.getAward_photo2_lat());
						data.setAward_distribution_path2_longitute(userfileup.getAward_photo2_lng());
						data.setAward_distribution_path2_time(parseTimestamp(userfileup.getAward_photo2_time()));
					}
					else {
						data.setAward_distribution_path2_latitude(null);
						data.setAward_distribution_path2_longitute(null);
						data.setAward_distribution_path2_time(null);
					}
					
					data.setNoDeptStalls(userfileup.getDept_stalls());
//					data.setDeptStallsPath1(filePath+userfileup.getDept_stalls_photo1().getOriginalFilename());
//					data.setDeptStallsPath2(filePath+userfileup.getDept_stalls_photo2().getOriginalFilename());
					
					data.setDeptStallsPath1(!userfileup.getDept_stalls_photo1().isEmpty() ? filePath + "I"+"Dept1"+userfileup.getBlock()+"_"+userfileup.getDept_stalls_photo1().getOriginalFilename() :  null);
					if (!userfileup.getDept_stalls_photo1().isEmpty()) {
						data.setDept_stalls_path1_latitude(userfileup.getDept_stalls_photo1_lat());
						data.setDept_stalls_path1_longitute(userfileup.getDept_stalls_photo1_lng());
						data.setDept_stalls_path1_time(parseTimestamp(userfileup.getDept_stalls_photo1_time()));
					}
					else {
						data.setDept_stalls_path1_latitude(null);
						data.setDept_stalls_path1_longitute(null);
						data.setDept_stalls_path1_time(null);
					}
					
					data.setDeptStallsPath2(!userfileup.getDept_stalls_photo2().isEmpty() ? filePath +"I"+"Dept2"+userfileup.getBlock()+"_"+ userfileup.getDept_stalls_photo2().getOriginalFilename() :  null);
					if (!userfileup.getDept_stalls_photo2().isEmpty()) {
						data.setDept_stalls_path2_latitude(userfileup.getDept_stalls_photo2_lat());
						data.setDept_stalls_path2_longitute(userfileup.getDept_stalls_photo2_lng());
						data.setDept_stalls_path2_time(parseTimestamp(userfileup.getDept_stalls_photo2_time()));
					}
					else {
						data.setDept_stalls_path2_latitude(null);
						data.setDept_stalls_path2_longitute(null);
						data.setDept_stalls_path2_time(null);
					}
					
					data.setNoShgFpo(userfileup.getShg_fpo_stalls());
//					data.setShgFpoPath1(filePath+userfileup.getShg_fpo_stalls_photo1().getOriginalFilename());
//					data.setShgFpoPath2(filePath+userfileup.getShg_fpo_stalls_photo2().getOriginalFilename());
					
					data.setShgFpoPath1(!userfileup.getShg_fpo_stalls_photo1().isEmpty() ? filePath + "I"+"SHG1"+userfileup.getBlock()+"_"+userfileup.getShg_fpo_stalls_photo1().getOriginalFilename() :  null);
					if (!userfileup.getShg_fpo_stalls_photo1().isEmpty()) {
						data.setShg_fpo_path1_latitude(userfileup.getShg_fpo_stalls_photo1_lat());
						data.setShg_fpo_path1_longitute(userfileup.getShg_fpo_stalls_photo1_lng());
						data.setShg_fpo_path1_time(parseTimestamp(userfileup.getShg_fpo_stalls_photo1_time()));
					}
					else {
						data.setShg_fpo_path1_latitude(null);
						data.setShg_fpo_path1_longitute(null);
						data.setShg_fpo_path1_time(null);
					}
					
					data.setShgFpoPath2(!userfileup.getShg_fpo_stalls_photo2().isEmpty() ? filePath + "I"+"SHG2"+userfileup.getBlock()+"_"+userfileup.getShg_fpo_stalls_photo2().getOriginalFilename() :  null);
					if (!userfileup.getShg_fpo_stalls_photo2().isEmpty()) {
						data.setShg_fpo_path2_latitude(userfileup.getShg_fpo_stalls_photo2_lat());
						data.setShg_fpo_path2_longitute(userfileup.getShg_fpo_stalls_photo2_lng());
						data.setShg_fpo_path2_time(parseTimestamp(userfileup.getShg_fpo_stalls_photo2_time()));
					}
					else {
						data.setShg_fpo_path2_latitude(null);
						data.setShg_fpo_path2_longitute(null);
						data.setShg_fpo_path2_time(null);
					}
					
					data.setNoLakhpatiDidi(userfileup.getNo_lakhpati_didi());
//					data.setLakhpatiDidiPath1(filePath+userfileup.getLakhpati_didi_photo1().getOriginalFilename());
//					data.setLakhpatiDidiPath2(filePath+userfileup.getLakhpati_didi_photo2().getOriginalFilename());
					
					data.setLakhpatiDidiPath1(!userfileup.getLakhpati_didi_photo1().isEmpty() ? filePath + "I"+"Lakh1"+userfileup.getBlock()+"_"+userfileup.getLakhpati_didi_photo1().getOriginalFilename() :  null);
					if (!userfileup.getLakhpati_didi_photo1().isEmpty()) {
						data.setLakhpati_didi_path1_latitude(userfileup.getLakhpati_didi_photo1_lat());
						data.setLakhpati_didi_path1_longitute(userfileup.getLakhpati_didi_photo1_lng());
						data.setLakhpati_didi_path1_time(parseTimestamp(userfileup.getLakhpati_didi_photo1_time()));
					}
					else {
						data.setLakhpati_didi_path1_latitude(null);
						data.setLakhpati_didi_path1_longitute(null);
						data.setLakhpati_didi_path1_time(null);
					}
					
					data.setLakhpatiDidiPath2(!userfileup.getLakhpati_didi_photo2().isEmpty() ? filePath +  "I"+"Lakh2"+userfileup.getBlock()+"_"+userfileup.getLakhpati_didi_photo2().getOriginalFilename() :  null);
					if (!userfileup.getLakhpati_didi_photo2().isEmpty()) {
						data.setLakhpati_didi_path2_latitude(userfileup.getLakhpati_didi_photo2_lat());
						data.setLakhpati_didi_path2_longitute(userfileup.getLakhpati_didi_photo2_lng());
						data.setLakhpati_didi_path2_time(parseTimestamp(userfileup.getLakhpati_didi_photo2_time()));
					}
					else {
						data.setLakhpati_didi_path2_latitude(null);
						data.setLakhpati_didi_path2_longitute(null);
						data.setLakhpati_didi_path2_time(null);
					}
					sess.save(data);
					res = "success";
					sess.getTransaction().commit();
					}
			else {
				res = "fail";
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
	public List<InaugurationBean> getInaugurationDetails(Integer stcd) {
		
		String getReport=getInaugurationDetails;
		Session session = sessionFactory.getCurrentSession();
		List<InaugurationBean> list = new ArrayList<InaugurationBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(InaugurationBean.class));
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
	public List<String> getImagesByInaugurationId(int inaugurationId) {
//		String getImage=getImageByInaugurationId;
		Session session = sessionFactory.getCurrentSession();
		List<WatershedYatraInauguaration> list = new ArrayList<>();
		List<String> imgList = new ArrayList<>();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from WatershedYatraInauguaration where inauguarationId = :inaugid");
			query.setInteger("inaugid", inaugurationId);
//			query.setResultTransformer(Transformers.aliasToBean(WatershedYatraInauguaration.class));
			list = query.list();
			session.getTransaction().commit();
//			imgList.add(list.get(0).getInauguarationId().toString());
			
			//server
			

 			if(list.get(0).getVanFlagPath1()!=null)
				imgList.add(list.get(0).getVanFlagPath1().substring(list.get(0).getVanFlagPath1().lastIndexOf("/")+1));
			if(list.get(0).getVanFlagPath2()!=null)
				imgList.add(list.get(0).getVanFlagPath2().substring(list.get(0).getVanFlagPath2().lastIndexOf("/")+1));
			if(list.get(0).getThemeSongPath1()!=null)
				imgList.add(list.get(0).getThemeSongPath1().substring(list.get(0).getThemeSongPath1().lastIndexOf("/")+1));
			if(list.get(0).getThemeSongPath2()!=null)
				imgList.add(list.get(0).getThemeSongPath2().substring(list.get(0).getThemeSongPath2().lastIndexOf("/")+1));
			if(list.get(0).getBhoomiPoojanPath1()!=null)
				imgList.add(list.get(0).getBhoomiPoojanPath1().substring(list.get(0).getBhoomiPoojanPath1().lastIndexOf("/")+1));
			if(list.get(0).getBhoomiPoojanPath2()!=null)
				imgList.add(list.get(0).getBhoomiPoojanPath2().substring(list.get(0).getBhoomiPoojanPath2().lastIndexOf("/")+1));
			if(list.get(0).getLokarpanPath1()!=null)
				imgList.add(list.get(0).getLokarpanPath1().substring(list.get(0).getLokarpanPath1().lastIndexOf("/")+1));
			if(list.get(0).getLokarpanPath2()!=null)
				imgList.add(list.get(0).getLokarpanPath2().substring(list.get(0).getLokarpanPath2().lastIndexOf("/")+1));
			if(list.get(0).getShramdaanPath1()!=null)
				imgList.add(list.get(0).getShramdaanPath1().substring(list.get(0).getShramdaanPath1().lastIndexOf("/")+1));
			if(list.get(0).getShramdaanPath2()!=null)
				imgList.add(list.get(0).getShramdaanPath2().substring(list.get(0).getShramdaanPath2().lastIndexOf("/")+1));
			if(list.get(0).getPlantationPath1()!=null)
				imgList.add(list.get(0).getPlantationPath1().substring(list.get(0).getPlantationPath1().lastIndexOf("/")+1));
			if(list.get(0).getPlantationPath2()!=null)
				imgList.add(list.get(0).getPlantationPath2().substring(list.get(0).getPlantationPath2().lastIndexOf("/")+1));
			if(list.get(0).getAwardDistributionPath1()!=null)
				imgList.add(list.get(0).getAwardDistributionPath1().substring(list.get(0).getAwardDistributionPath1().lastIndexOf("/")+1));
			if(list.get(0).getAwardDistributionPath2()!=null)
				imgList.add(list.get(0).getAwardDistributionPath2().substring(list.get(0).getAwardDistributionPath2().lastIndexOf("/")+1));
			if(list.get(0).getDeptStallsPath1()!=null)
				imgList.add(list.get(0).getDeptStallsPath1().substring(list.get(0).getDeptStallsPath1().lastIndexOf("/")+1));
			if(list.get(0).getDeptStallsPath2()!=null)
				imgList.add(list.get(0).getDeptStallsPath2().substring(list.get(0).getDeptStallsPath2().lastIndexOf("/")+1));
			if(list.get(0).getShgFpoPath1()!=null)
				imgList.add(list.get(0).getShgFpoPath1().substring(list.get(0).getShgFpoPath1().lastIndexOf("/")+1));
			if(list.get(0).getShgFpoPath2()!=null)
				imgList.add(list.get(0).getShgFpoPath2().substring(list.get(0).getShgFpoPath2().lastIndexOf("/")+1));
			if(list.get(0).getLakhpatiDidiPath1()!=null)
				imgList.add(list.get(0).getLakhpatiDidiPath1().substring(list.get(0).getLakhpatiDidiPath1().lastIndexOf("/")+1));
			if(list.get(0).getLakhpatiDidiPath2()!=null)
				imgList.add(list.get(0).getLakhpatiDidiPath2().substring(list.get(0).getLakhpatiDidiPath2().lastIndexOf("/")+1));
			
			

			
			//local
/*			
			if(list.get(0).getVanFlagPath1()!=null)
				imgList.add(list.get(0).getVanFlagPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getVanFlagPath2()!=null)
				imgList.add(list.get(0).getVanFlagPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getThemeSongPath1()!=null)
				imgList.add(list.get(0).getThemeSongPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getThemeSongPath2()!=null)
				imgList.add(list.get(0).getThemeSongPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getBhoomiPoojanPath1()!=null)
				imgList.add(list.get(0).getBhoomiPoojanPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getBhoomiPoojanPath2()!=null)
				imgList.add(list.get(0).getBhoomiPoojanPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getLokarpanPath1()!=null)
				imgList.add(list.get(0).getLokarpanPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getLokarpanPath2()!=null)
				imgList.add(list.get(0).getLokarpanPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getShramdaanPath1()!=null)
				imgList.add(list.get(0).getShramdaanPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getShramdaanPath2()!=null)
				imgList.add(list.get(0).getShramdaanPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getPlantationPath1()!=null)
				imgList.add(list.get(0).getPlantationPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getPlantationPath2()!=null)
				imgList.add(list.get(0).getPlantationPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getAwardDistributionPath1()!=null)
				imgList.add(list.get(0).getAwardDistributionPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getAwardDistributionPath2()!=null)
				imgList.add(list.get(0).getAwardDistributionPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getDeptStallsPath1()!=null)
				imgList.add(list.get(0).getDeptStallsPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getDeptStallsPath2()!=null)
				imgList.add(list.get(0).getDeptStallsPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getShgFpoPath1()!=null)
				imgList.add(list.get(0).getShgFpoPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getShgFpoPath2()!=null)
				imgList.add(list.get(0).getShgFpoPath2().replaceAll(".*\\\\", ""));
			if(list.get(0).getLakhpatiDidiPath1()!=null)
				imgList.add(list.get(0).getLakhpatiDidiPath1().replaceAll(".*\\\\", ""));
			if(list.get(0).getLakhpatiDidiPath2()!=null)
				imgList.add(list.get(0).getLakhpatiDidiPath2().replaceAll(".*\\\\", ""));
*/			
			
		}catch(Exception ex) {
 			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return imgList;
	}

	@Override
	public String getExistingBlockInaguraCodes(Integer bCode) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.getCurrentSession();
		  int result;
		  String data="fail";
		  try {
		    session.beginTransaction();
			List list = session.createQuery("SELECT iwmpBlock.bcode FROM WatershedYatraInauguaration where iwmpBlock.bcode=:villageCode").setInteger("villageCode", bCode).list();
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
	
	
}
