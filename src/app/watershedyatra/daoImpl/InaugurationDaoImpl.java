package app.watershedyatra.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import app.common.CommonFunctions;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.dao.InaugurationDao;
import app.watershedyatra.model.WatershedYatraInauguaration;

@Repository("InaugurationDaoImpl")
public class InaugurationDaoImpl implements InaugurationDao {
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	public String saveInauguration(InaugurationBean userfileup, HttpSession session) {
		Session sess = sessionFactory.getCurrentSession();
		
		String res = "fail";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date inaugurationDate = formatter.parse(userfileup.getDate());
			
			String filePath="D:\\Inauguration\\";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/vanyatradoc/Inauguration";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TEST/vanyatradoc/Inauguration";
			
			MultipartFile[] mfile = {userfileup.getFlagOffPhoto1(), userfileup.getFlagOffPhoto2(), userfileup.getThemeSongPhoto1(), userfileup.getThemeSongPhoto2(),
					 	userfileup.getBhoomiPoojanPhoto1(), userfileup.getBhoomiPoojanPhoto2(), userfileup.getLokarpanPhoto1(), userfileup.getLokarpanPhoto2(),
					 	userfileup.getShramdaanPhoto1(), userfileup.getShramdaanPhoto2(), userfileup.getPlantationPhoto1(), userfileup.getPlantationPhoto2(), 
					 	userfileup.getAwardPhoto1(), userfileup.getAwardPhoto2() };
			
			for (MultipartFile file : mfile) {
			
				commonFunction.uploadFileforLMS(file, filePath);
			
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
			
			data.setIwmpState(s);
			data.setIwmpDistrict(d);
			data.setIwmpBlock(b);
			data.setCreatedBy(session.getAttribute("loginID").toString());
			data.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
			data.setRequestedIp(ipAddr);
			
			data.setInauguarationDate(inaugurationDate);
			data.setInauguarationLocation(userfileup.getLocation());
			data.setMaleParticipants(userfileup.getMaleParticipants());
			data.setFemaleParticipants(userfileup.getFemaleParticipants());
			data.setCentralMinister(userfileup.getCentralMinisters());
			data.setStateMinister(userfileup.getStateMinisters());
			data.setParliamentMembers(userfileup.getParliament());
			data.setLegislativeAssemblyMembers(userfileup.getAssemblyMembers());
			data.setLegislativeCouncilMembers(userfileup.getCouncilMembers());
			data.setOtherPublicRepresentatives(userfileup.getOthers());
			data.setGovOfficials(userfileup.getGovOfficials());
			data.setVanFlagOff(Boolean.valueOf(userfileup.getFlagOff()));
			data.setVanFlagPath1(filePath+userfileup.getFlagOffPhoto1().getOriginalFilename());
			data.setVanFlagPath2(filePath+userfileup.getFlagOffPhoto2().getOriginalFilename());
			data.setThemeSong(Boolean.valueOf(userfileup.getThemeSong()));
			data.setThemeSongPath1(filePath+userfileup.getThemeSongPhoto1().getOriginalFilename());
			data.setThemeSongPath2(filePath+userfileup.getThemeSongPhoto2().getOriginalFilename());
			data.setBhoomiPoojanNoOfWorks(userfileup.getNoWorksBhoomiPoojan());
			data.setBhoomiPoojanCostOfWorks(userfileup.getTotWorksBhoomiPoojan());
			data.setBhoomiPoojanPath1(filePath+userfileup.getBhoomiPoojanPhoto1().getOriginalFilename());
			data.setBhoomiPoojanPath2(filePath+userfileup.getBhoomiPoojanPhoto2().getOriginalFilename());
			data.setLokarpanNoOfWorks(userfileup.getNoWorksLokarpan());
			data.setLokarpanCostOfWorks(userfileup.getTotWorksLokarpan());
			data.setLokarpanPath1(filePath+userfileup.getLokarpanPhoto1().getOriginalFilename());
			data.setLokarpanPath2(filePath+userfileup.getLokarpanPhoto2().getOriginalFilename());
			data.setShramdaanNoOfLocation(userfileup.getNoLocationShramdaan());
			data.setShramdaanNoOfParticipatedPeople(userfileup.getCostPeopleShramdaan());
			data.setShramdaanPath1(filePath+userfileup.getShramdaanPhoto1().getOriginalFilename());
			data.setShramdaanPath2(filePath+userfileup.getShramdaanPhoto2().getOriginalFilename());
			data.setPlantationArea(userfileup.getAreaPlantation());
			data.setNoOfAgroForsetry(userfileup.getNoPlantation());
			data.setPlantationPath1(filePath+userfileup.getPlantationPhoto1().getOriginalFilename());
			data.setPlantationPath2(filePath+userfileup.getPlantationPhoto2().getOriginalFilename());
			data.setAwardDistribution(userfileup.getNoAwards());
			data.setAwardDistributionPath1(filePath+userfileup.getAwardPhoto1().getOriginalFilename());
			data.setAwardDistributionPath2(filePath+userfileup.getAwardPhoto2().getOriginalFilename());
			
			
			
			
			sess.save(data);
			sess.getTransaction().commit();
			res = "success";
		}
		catch (Exception ex) {
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}

	
	return res;
	}
	
	
	
}
