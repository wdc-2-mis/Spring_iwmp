package app.watershedyatra.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
			
			MultipartFile[] mfile = {userfileup.getFlagoff_photo1(), userfileup.getFlagoff_photo2(), userfileup.getThemesong_photo1(), userfileup.getThemesong_photo2(),
					 	userfileup.getBhoomipoojan_photo1(), userfileup.getBhoomipoojan_photo2(), userfileup.getLokarpan_photo1(), userfileup.getLokarpan_photo2(),
					 	userfileup.getShramdaan_photo1(), userfileup.getShramdaan_photo2(), userfileup.getPlantation_photo1(), userfileup.getPlantation_photo2(), 
					 	userfileup.getAward_photo1(), userfileup.getAward_photo2() };
			
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
//			if(userfileup.getFlagoff_photo1()!=null)
			data.setVanFlagPath1(filePath+userfileup.getFlagoff_photo1().getOriginalFilename());
			
			data.setVanFlagPath2(filePath+userfileup.getFlagoff_photo2().getOriginalFilename());
			data.setThemeSong(Boolean.valueOf(userfileup.getThemesong()));
			data.setThemeSongPath1(filePath+userfileup.getThemesong_photo1().getOriginalFilename());
			data.setThemeSongPath2(filePath+userfileup.getThemesong_photo2().getOriginalFilename());
			data.setBhoomiPoojanNoOfWorks(userfileup.getNo_works_bhoomipoojan());
			data.setBhoomiPoojanCostOfWorks(userfileup.getTot_works_bhoomipoojan());
			data.setBhoomiPoojanPath1(filePath+userfileup.getBhoomipoojan_photo1().getOriginalFilename());
			data.setBhoomiPoojanPath2(filePath+userfileup.getBhoomipoojan_photo2().getOriginalFilename());
			data.setLokarpanNoOfWorks(userfileup.getNo_works_lokarpan());
			data.setLokarpanCostOfWorks(userfileup.getTot_works_lokarpan());
			data.setLokarpanPath1(filePath+userfileup.getLokarpan_photo1().getOriginalFilename());
			data.setLokarpanPath2(filePath+userfileup.getLokarpan_photo2().getOriginalFilename());
			data.setShramdaanNoOfLocation(userfileup.getNo_location_shramdaan());
			data.setShramdaanNoOfParticipatedPeople(userfileup.getNo_people_shramdaan());
			data.setManHr(userfileup.getMan());
			data.setShramdaanPath1(filePath+userfileup.getShramdaan_photo1().getOriginalFilename());
			data.setShramdaanPath2(filePath+userfileup.getShramdaan_photo2().getOriginalFilename());
			data.setPlantationArea(userfileup.getArea_plantation());
			data.setNoOfAgroForsetry(userfileup.getNo_plantation());
			data.setPlantationPath1(filePath+userfileup.getPlantation_photo1().getOriginalFilename());
			data.setPlantationPath2(filePath+userfileup.getPlantation_photo2().getOriginalFilename());
			data.setAwardDistribution(userfileup.getNo_awards());
			data.setAwardDistributionPath1(filePath+userfileup.getAward_photo1().getOriginalFilename());
			data.setAwardDistributionPath2(filePath+userfileup.getAward_photo2().getOriginalFilename());
			
			
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
	
	
}
