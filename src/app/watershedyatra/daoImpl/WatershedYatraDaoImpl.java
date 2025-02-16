package app.watershedyatra.daoImpl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import app.watershedyatra.model.MCulturalActivity;
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.common.CommonFunctions;
import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.model.outcome.GroundwaterDetail;
import app.model.outcome.GroundwaterMain;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPrepBean;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.dao.WatershedYatraDao;
import app.watershedyatra.model.NodalOfficer;
import app.watershedyatra.model.PreYatraGramsabha;
import app.watershedyatra.model.PreYatraPrabhatpheri;
import app.watershedyatra.model.PreYatraPreparation;
import app.watershedyatra.model.WatershedYatVill;

@Repository("WatershedYatraDao")
public class WatershedYatraDaoImpl implements WatershedYatraDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Value("${districtListWatershedyatra}")
	String districtListWatershedyatra;
	
	@Value("${blockListWatershedyatra}")
	String blockListWatershedyatra;
	
	@Value("${grampanListWatershedyatra}")
	String grampanListWatershedyatra;
	
	@Value("${villageListWatershedyatra}")
	String villageListWatershedyatra;
	
	@Value("${getDraftListofNodalOfficer}")
	String getDraftListofNodalOfficer;
	
	
	@Value("${getCompleteListofNodalOfficer}")
	String getCompleteListofNodalOfficer;
	
	@Value("${cultListWatershedyatra}")
	String cultListWatershedyatra;
	
	@Value("${getWyatraDetails}")
	String getWyatraDetails;
	
	@Value("${getpreyatrarcd}")
	String getpreyatrarcd;
	
	@Value("${checkgrampanchayat}")
	String checkgrampanchayat;
	
	@Value("${checkVillagestatus}")
	String checkVillagestatus;
	
	@Override
	public LinkedHashMap<Integer, String> getDistrictList(int stcode) {
	
		List<IwmpDistrict> distList=new ArrayList<IwmpDistrict>();
		String hql=districtListWatershedyatra;
		LinkedHashMap<Integer, String> distMap=new LinkedHashMap<Integer, String>();
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				query.setInteger("stcode", stcode);
				distList = query.list();
				for (IwmpDistrict state : distList) 
				{
					distMap.put(state.getDcode(), state.getDistName());
				}
				//session.getTransaction().commit();
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
			session.getTransaction().commit();
		}
        return distMap;
	}

	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraBlock(Integer distcd) {
		// TODO Auto-generated method stub
		List<IwmpBlock> bldList=new ArrayList<IwmpBlock>();
		String hql=blockListWatershedyatra;
		LinkedHashMap<String, Integer> blkMap=new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("distcode", distcd);
			bldList = query.list();
			
			for (IwmpBlock blk : bldList) {
				blkMap.put( blk.getBlockName(), blk.getBcode());
			//	System.out.println(district.getDcode()+" k "+district.getDistName());
			}
			//session.getTransaction().commit();
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
			session.getTransaction().commit();
		}
        return blkMap;
	}

	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraGPs(Integer blkCode) {
		
		List<IwmpGramPanchayat> bldList=new ArrayList<IwmpGramPanchayat>();
		String hql=grampanListWatershedyatra;
		LinkedHashMap<String, Integer> blkMap=new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("blkcode", blkCode);
			bldList = query.list();
			
			for (IwmpGramPanchayat blk : bldList) {
				blkMap.put( blk.getGramPanchayatName(), blk.getGcode());
			//	System.out.println(district.getDcode()+" k "+district.getDistName());
			}
			//session.getTransaction().commit();
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
			session.getTransaction().commit();
		}
        return blkMap;
	}

	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraVillage(Integer gpCode) {
		// TODO Auto-generated method stub
		List<IwmpVillage> bldList=new ArrayList<IwmpVillage>();
		String hql=villageListWatershedyatra;
		LinkedHashMap<String, Integer> blkMap=new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("gpscode", gpCode);
			bldList = query.list();
			
			for (IwmpVillage blk : bldList) {
				blkMap.put( blk.getVillageName(), blk.getVcode());
			//	System.out.println(district.getDcode()+" k "+district.getDistName());
			}
			
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
			session.getTransaction().commit();
		}
        return blkMap;
	}

	@Override
	public String saveNodalOfficerLMS(String level, String state, Integer district, Integer block, String name,
			String designation, String mob, String email, HttpSession session) {
		// TODO Auto-generated method stub
		Session sess = sessionFactory.getCurrentSession();
		String res="fail";
		String result;
		try {
			
			sess.beginTransaction();
			SQLQuery chkdata=sess.createSQLQuery("select * from nodal_officer where UPPER(email)=UPPER(:emailid)");
			chkdata.setString("emailid", email);
			chkdata.list();
		
			if(chkdata.list().isEmpty()) 
			{
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				
				NodalOfficer main =new NodalOfficer();
				IwmpState st =new IwmpState();
				IwmpDistrict dt =new IwmpDistrict();
				IwmpBlock bl =new IwmpBlock();
				
				st.setStCode(Integer.parseInt(state));
				main.setIwmpState(st);
				
				if(district>0) {
				dt.setDcode(district);
				main.setIwmpDistrict(dt);
				}
				
				if(block>0) {
				bl.setBcode(block);
				main.setIwmpBlock(bl);
				}
				
				main.setLevel(level);
				main.setNodalName(name);
				main.setDesignation(designation);
				main.setMobile(mob);
				main.setEmail(email);
				main.setCreatedBy(session.getAttribute("loginID").toString());
				main.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
				main.setRequestedIp(ipAddr);
				main.setStatus('D');
				sess.save(main);
				sess.getTransaction().commit();
				res="success";
			}
			else {
				
				sess.getTransaction().commit();
				res="fail";
			
			}
			
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}
		finally {
			//sess.getTransaction().commit();
			//session.flush();
		//session.close();
		}
		
		return res;
	}

	@Override
	public List<NodalOfficerBean> getDraftListofNodalOfficer(Integer stcd) {
		// TODO Auto-generated method stub
		String getReport=getDraftListofNodalOfficer;
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
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
	public String completeApproveNodalOfficer(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("update nodal_officer set status='C', updated_by=:updated, updated_date=:datee where nodal_id=:nrmpkid");
			 Date d= new Date();
			 
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query.setInteger("nrmpkid", assetid.get(i));
				 query.setString("updated", userid);
				 query.setDate("datee", d);
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
	public String deleteApproveNodalOfficer(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("delete from nodal_officer where nodal_id=:nrmpkid");
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
	public String saveWatershedYatraVillageupload(WatershedYatraBean userfileup, HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		String res="fail";
		String upload="unUpload";
		try {
			
			
			
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; 
			LocalDateTime localDateTime = LocalDateTime.parse(userfileup.getDatetime(), formatter); 
			Timestamp timestamp1 = Timestamp.valueOf(localDateTime); 
			    Timestamp Gtimestamp1 = null;
			    Timestamp Gtimestamp2 = null;
			    Timestamp ptimestamp1 = null;
			    Timestamp ptimestamp2 = null;
			   
//			    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
//		        Date date = formatter1.parse(userfileup.getDate());
//		        Date date1 = formatter1.parse(userfileup.getDatetime());

		        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
		        
		       
	//		 String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/vanyatradoc/WatershedYatraVillage/";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/vanyatradoc/WatershedYatraVillage/";
			String filePath = "D:\\WatershedYatraVillage\\";
			
			MultipartFile[] mfile = {userfileup.getArExperiencephoto1(),userfileup.getArExperiencephoto2(),
					userfileup.getBhoomiCostphoto1(),userfileup.getBhoomiCostphoto2(),userfileup.getCulturalActivityphoto1(),
					userfileup.getCulturalActivityphoto2(),userfileup.getFilmYesphoto1(),userfileup.getFilmYesphoto2(),
					userfileup.getLocShramdaanpsphoto1(),userfileup.getLocShramdaanpsphoto2(),userfileup.getLokWorksphoto1(),
					userfileup.getLokWorksphoto2(),userfileup.getNoOfwatershedphoto1(),userfileup.getNoOfwatershedphoto2(),
					userfileup.getPlantationAreaphoto1(),userfileup.getPlantationAreaphoto2(),userfileup.getQuizParticipantsphoto1(),
					userfileup.getQuizParticipantsphoto2(),userfileup.getShapathYesphoto1(),userfileup.getShapathYesphoto2()
					};
			
			String[] fileDescriptions = {
				    "Ar1", "Ar2", "Bh1", "Bh2",
				    "Cul1", "Cul2", "Film1", "Film2",
				    "Loc1", "Loc2", "Lok1", "Lok2",
				    "wat1", "wat2", "Plan1", "Plan2",
				    "Quiz1", "Quiz2", "Shap1", "Shap2"
				};
			
		/*	for (MultipartFile file : mfile) {
				
				commonFunction.uploadFileforLMS(file, filePath, userfileup.getVillage());
			
			}  */
			
			for (int i = 0; i < mfile.length; i++) {
			    MultipartFile file = mfile[i];
			    String act = fileDescriptions[i];
			   
			    upload = commonFunction.uploadFileforwatershedYatra(file, filePath, userfileup.getBlock(), act);
			}
			
			
			
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				
				sess.beginTransaction();
				
				WatershedYatVill main =new WatershedYatVill();
				IwmpState st =new IwmpState();
				IwmpDistrict dt =new IwmpDistrict();
				IwmpBlock bl =new IwmpBlock();
				IwmpGramPanchayat gp =new IwmpGramPanchayat();
				IwmpVillage vil = new IwmpVillage();
				MCulturalActivity ca = new MCulturalActivity();
				
				if(upload.equals("upload")) {
				
				ca.setCulturalId(userfileup.getCulturalActivity());
				
				st.setStCode(Integer.parseInt(session.getAttribute("stateCode").toString()));
				main.setIwmpState(st);
				
				dt.setDcode(userfileup.getDistrict());
				main.setIwmpDistrict(dt);
				
				bl.setBcode(userfileup.getBlock());
				main.setIwmpBlock(bl);
				
				gp.setGcode(userfileup.getGrampan());
				main.setIwmpGramPanchayat(gp);
			
				vil.setVcode(userfileup.getVillage());
				main.setIwmpVillage(vil);
				
				main.setYatraDate1(timestamp1);
				main.setYatraLocation(userfileup.getLocation());
				main.setMaleParticipants(userfileup.getMaleParticipants());
				main.setFemaleParticipants(userfileup.getFemaleParticipants());
				main.setCentralMinister(userfileup.getCentralMinisters());
				main.setStateMinister(userfileup.getStateMinisters());
				main.setParliamentMembers(userfileup.getMembersOfParliament());
				main.setLegislativeAssemblyMembers(userfileup.getLegAssemblyMembers());
				main.setLegislativeCouncilMembers(userfileup.getLegCouncilMembers());
				main.setOtherPublicRepresentatives(userfileup.getPublicReps());
				main.setGovOfficials(userfileup.getGovOfficials());
				main.setNoOfArExperiencePeople(userfileup.getArExperience());
				main.setBhumiJalSanrakshan(userfileup.getShapathYes());
				main.setWatershedYatraFilm(userfileup.getFilmYes());
				main.setQuizParticipants(userfileup.getQuizParticipants());
				
				main.setmCulturalActivity(ca);
				main.setBhoomiPoojanNoOfWorks(userfileup.getBhoomiWorks());
				main.setBhoomiPoojanCostOfWorks(userfileup.getBhoomiCost());
				main.setLokarpanNoOfWorks(userfileup.getLokWorks());
				main.setLokarpanCostOfWorks(userfileup.getCostWorks());
				main.setShramdaanNoOfLocation(userfileup.getLocShramdaan());
				main.setShramdaanNoOfParticipatedPeople(userfileup.getLocShramdaanps());
				main.setManhour(userfileup.getManhour());
				main.setPlantationArea(userfileup.getPlantationArea());
				main.setNoOfAgroForsetry(userfileup.getNofagrohorti());
				main.setAwardDistribution(userfileup.getNoOfwatershed());
				

				main.setBhumiJalSanrakshanPath1(!userfileup.getShapathYesphoto1().isEmpty() ? filePath + "W"+"Shap1"+userfileup.getVillage()+"_"+userfileup.getShapathYesphoto1().getOriginalFilename() :  null);
				if (!userfileup.getShapathYesphoto1().isEmpty()) {
				main.setBhumi_jal_sanrakshan_path1_latitude(userfileup.getShapathYesphoto1_lat());
		        main.setBhumi_jal_sanrakshan_path1_longitute(userfileup.getShapathYesphoto1_lng());
		        main.setBhumi_jal_sanrakshan_path1_time(parseTimestamp(userfileup.getShapathYesphoto1_time()));
				}
				else {
					main.setBhumi_jal_sanrakshan_path1_latitude(null);
			        main.setBhumi_jal_sanrakshan_path1_longitute(null);
			        main.setBhumi_jal_sanrakshan_path1_time(null);
				}
				main.setBhumiJalSanrakshanPath2(!userfileup.getShapathYesphoto2().isEmpty() ? filePath + "W"+"Shap2"+userfileup.getVillage()+"_"+userfileup.getShapathYesphoto2().getOriginalFilename() :  null);
				if (!userfileup.getShapathYesphoto2().isEmpty()) {
				main.setBhumi_jal_sanrakshan_path2_latitude(userfileup.getShapathYesphoto2_lat());
		        main.setBhumi_jal_sanrakshan_path2_longitute(userfileup.getShapathYesphoto2_lng());
		        main.setBhumi_jal_sanrakshan_path2_time(parseTimestamp(userfileup.getShapathYesphoto2_time()));
				}
				else {
					main.setBhumi_jal_sanrakshan_path2_latitude(null);
			        main.setBhumi_jal_sanrakshan_path2_longitute(null);
			        main.setBhumi_jal_sanrakshan_path2_time(null);
				}
				main.setYatraFilmPath1(!userfileup.getFilmYesphoto1().isEmpty() ? filePath + "W"+"Film1"+userfileup.getVillage()+"_"+ userfileup.getFilmYesphoto1().getOriginalFilename() :  null);
				if (!userfileup.getFilmYesphoto1().isEmpty()) {
				main.setYatra_film_path1_latitude(userfileup.getFilmYesphoto1_lat());
		        main.setYatra_film_path1_longitute(userfileup.getFilmYesphoto1_lng());
		        main.setYatra_film_path1_time(parseTimestamp(userfileup.getFilmYesphoto1_time()));
				}
				else {
					main.setYatra_film_path1_latitude(null);
			        main.setYatra_film_path1_longitute(null);
			        main.setYatra_film_path1_time(null);
				}
				main.setYatraFilmPath2(!userfileup.getFilmYesphoto2().isEmpty() ? filePath + "W"+"Film2"+userfileup.getVillage()+"_"+userfileup.getFilmYesphoto2().getOriginalFilename() :  null);
				if (!userfileup.getFilmYesphoto2().isEmpty()) {
				main.setYatra_film_path2_latitude(userfileup.getFilmYesphoto2_lat());
		        main.setYatra_film_path2_longitute(userfileup.getFilmYesphoto2_lng());
		        main.setYatra_film_path2_time(parseTimestamp(userfileup.getFilmYesphoto2_time()));
				}
				else {
					main.setYatra_film_path2_latitude(null);
			        main.setYatra_film_path2_longitute(null);
			        main.setYatra_film_path2_time(null);
				}
		        
				main.setBhoomiPoojanPath1(!userfileup.getBhoomiCostphoto1().isEmpty() ? filePath + "W"+"Bh1"+userfileup.getVillage()+"_"+userfileup.getBhoomiCostphoto1().getOriginalFilename() :  null);
				if (!userfileup.getBhoomiCostphoto1().isEmpty()) {
				main.setBhoomi_poojan_path1_latitude(userfileup.getBhoomiCostphoto1_lat());
		        main.setBhoomi_poojan_path1_longitute(userfileup.getBhoomiCostphoto1_lng());
		        main.setBhoomi_poojan_path1_time(parseTimestamp(userfileup.getBhoomiCostphoto1_time()));
				}
				else {
					main.setBhoomi_poojan_path1_latitude(null);
			        main.setBhoomi_poojan_path1_longitute(null);
			        main.setBhoomi_poojan_path1_time(null);
				}
				main.setBhoomiPoojanPath2(!userfileup.getBhoomiCostphoto2().isEmpty() ? filePath + "W"+"Bh2"+userfileup.getVillage()+"_"+userfileup.getBhoomiCostphoto2().getOriginalFilename() :  null);
				if (!userfileup.getBhoomiCostphoto2().isEmpty()) {
				main.setBhoomi_poojan_path2_latitude(userfileup.getBhoomiCostphoto2_lat());
		        main.setBhoomi_poojan_path2_longitute(userfileup.getBhoomiCostphoto2_lng());
		        main.setBhoomi_poojan_path2_time(parseTimestamp(userfileup.getBhoomiCostphoto2_time()));
				}
				else {
					main.setBhoomi_poojan_path2_latitude(null);
			        main.setBhoomi_poojan_path2_longitute(null);
			        main.setBhoomi_poojan_path2_time(null);
				}
				main.setArExperiencePath1(!userfileup.getArExperiencephoto1().isEmpty() ? filePath + "W"+"Ar1"+userfileup.getVillage()+"_"+userfileup.getArExperiencephoto1().getOriginalFilename() :  null);
				if (!userfileup.getArExperiencephoto1().isEmpty()) {
				main.setAr_experience_path1_latitude(userfileup.getArExperiencephoto1_lat());
		        main.setAr_experience_path1_longitute(userfileup.getArExperiencephoto1_lng());
		        main.setAr_experience_path1_time(parseTimestamp(userfileup.getArExperiencephoto1_time()));
				}
				else {
					main.setAr_experience_path1_latitude(null);
			        main.setAr_experience_path1_longitute(null);
			        main.setAr_experience_path1_time(null);
				}
				main.setArExperiencePath2(!userfileup.getArExperiencephoto2().isEmpty() ? filePath + "W"+"Ar2"+userfileup.getVillage()+"_"+userfileup.getArExperiencephoto2().getOriginalFilename() :  null);
				if (!userfileup.getArExperiencephoto2().isEmpty()) {
				main.setAr_experience_path2_latitude(userfileup.getArExperiencephoto2_lat());
		        main.setAr_experience_path2_longitute(userfileup.getArExperiencephoto2_lng());
		        main.setAr_experience_path2_time(parseTimestamp(userfileup.getArExperiencephoto2_time()));
				}
				else {
					main.setAr_experience_path2_latitude(null);
			        main.setAr_experience_path2_longitute(null);
			        main.setAr_experience_path2_time(null);
				}
				main.setShramdaanPath1(!userfileup.getLocShramdaanpsphoto1().isEmpty() ? filePath + "W"+"Loc1"+userfileup.getVillage()+"_"+userfileup.getLocShramdaanpsphoto1().getOriginalFilename() :  null);
				if (!userfileup.getLocShramdaanpsphoto1().isEmpty()) {
				main.setShramdaan_path1_latitude(userfileup.getLocShramdaanpsphoto1_lat());
		        main.setShramdaan_path1_longitute(userfileup.getLocShramdaanpsphoto1_lng());
		        main.setShramdaan_path1_time(parseTimestamp(userfileup.getLocShramdaanpsphoto1_time()));
				}
				else {
					main.setShramdaan_path1_latitude(null);
			        main.setShramdaan_path1_longitute(null);
			        main.setShramdaan_path1_time(null);
				}
				main.setShramdaanPath2(!userfileup.getLocShramdaanpsphoto2().isEmpty() ? filePath + "W"+"Loc2"+userfileup.getVillage()+"_"+userfileup.getLocShramdaanpsphoto2().getOriginalFilename() :  null);
				if (!userfileup.getLocShramdaanpsphoto2().isEmpty()) {
				main.setShramdaan_path2_latitude(userfileup.getLocShramdaanpsphoto2_lat());
		        main.setShramdaan_path2_longitute(userfileup.getLocShramdaanpsphoto2_lng());
		        main.setShramdaan_path2_time(parseTimestamp(userfileup.getLocShramdaanpsphoto2_time()));
				}
				else {
					main.setShramdaan_path2_latitude(null);
			        main.setShramdaan_path2_longitute(null);
			        main.setShramdaan_path2_time(null);
				}
				main.setQuizParticipantsPath1(!userfileup.getQuizParticipantsphoto1().isEmpty() ? filePath + "W"+"Quiz1"+userfileup.getVillage()+"_"+userfileup.getQuizParticipantsphoto1().getOriginalFilename() :  null);
				if (!userfileup.getQuizParticipantsphoto1().isEmpty()) {
				main.setQuiz_participants_path1_latitude(userfileup.getQuizParticipantsphoto1_lat());
		        main.setQuiz_participants_path1_longitute(userfileup.getQuizParticipantsphoto1_lng());
		        main.setQuiz_participants_path1_time(parseTimestamp(userfileup.getQuizParticipantsphoto1_time()));
				}
				else {
					main.setQuiz_participants_path1_latitude(null);
			        main.setQuiz_participants_path1_longitute(null);
			        main.setQuiz_participants_path1_time(null);
				}
				main.setQuizParticipantsPath2(!userfileup.getQuizParticipantsphoto2().isEmpty() ? filePath +"W"+"Quiz2"+userfileup.getVillage()+"_"+ userfileup.getQuizParticipantsphoto2().getOriginalFilename() :  null);
				if (!userfileup.getQuizParticipantsphoto2().isEmpty()) {
				main.setQuiz_participants_path2_latitude(userfileup.getQuizParticipantsphoto2_lat());
		        main.setQuiz_participants_path2_longitute(userfileup.getQuizParticipantsphoto2_lng());
		        main.setQuiz_participants_path2_time(parseTimestamp(userfileup.getQuizParticipantsphoto2_time()));
				}
				else {
					main.setQuiz_participants_path2_latitude(null);
			        main.setQuiz_participants_path2_longitute(null);
			        main.setQuiz_participants_path2_time(null);
				}
				main.setLokarpanPath1(!userfileup.getLokWorksphoto1().isEmpty() ? filePath +"W"+"Lok1"+userfileup.getVillage()+"_"+ userfileup.getLokWorksphoto1().getOriginalFilename() :  null);
				if (!userfileup.getLokWorksphoto1().isEmpty()) {
				main.setLokarpan_path1_latitude(userfileup.getLokWorksphoto1_lat());
		        main.setLokarpan_path1_longitute(userfileup.getLokWorksphoto1_lng());
		        main.setLokarpan_path1_time(parseTimestamp(userfileup.getLokWorksphoto1_time()));
				}
				else {
					main.setLokarpan_path1_latitude(null);
			        main.setLokarpan_path1_longitute(null);
			        main.setLokarpan_path1_time(null);
				}
		        
				main.setLokarpanPath2(!userfileup.getLokWorksphoto2().isEmpty() ? filePath + "W"+"Lok2"+userfileup.getVillage()+"_"+userfileup.getLokWorksphoto2().getOriginalFilename() :  null);
				if (!userfileup.getLokWorksphoto2().isEmpty()) {
				main.setLokarpan_path2_latitude(userfileup.getLokWorksphoto2_lat());
		        main.setLokarpan_path2_longitute(userfileup.getLokWorksphoto2_lng());
		        main.setLokarpan_path2_time(parseTimestamp(userfileup.getLokWorksphoto2_time()));
				}
				else {
					main.setLokarpan_path2_latitude(null);
			        main.setLokarpan_path2_longitute(null);
			        main.setLokarpan_path2_time(null);
				}
				main.setPlantationPath1(!userfileup.getPlantationAreaphoto1().isEmpty() ? filePath + "W"+"Plan1"+userfileup.getVillage()+"_"+userfileup.getPlantationAreaphoto1().getOriginalFilename() :  null);
				if (!userfileup.getPlantationAreaphoto1().isEmpty()) {
				main.setPlantation_path1_latitude(userfileup.getPlantationAreaphoto1_lat());
		        main.setPlantation_path1_longitute(userfileup.getPlantationAreaphoto1_lng());
		        main.setPlantation_path1_time(parseTimestamp(userfileup.getPlantationAreaphoto1_time()));
				}
				else {
					main.setPlantation_path1_latitude(null);
			        main.setPlantation_path1_longitute(null);
			        main.setPlantation_path1_time(null);
				}
				main.setPlantationPath2(!userfileup.getPlantationAreaphoto2().isEmpty() ? filePath + "W"+"Plan2"+userfileup.getVillage()+"_"+userfileup.getPlantationAreaphoto2().getOriginalFilename() :  null);
				if (!userfileup.getPlantationAreaphoto2().isEmpty()) {
				main.setPlantation_path2_latitude(userfileup.getPlantationAreaphoto2_lat());
		        main.setPlantation_path2_longitute(userfileup.getPlantationAreaphoto2_lng());
		        main.setPlantation_path2_time(parseTimestamp(userfileup.getPlantationAreaphoto2_time()));
				}
				else {
					main.setPlantation_path2_latitude(null);
			        main.setPlantation_path2_longitute(null);
			        main.setPlantation_path2_time(null);
				}
				main.setAwardDistributionPath1(!userfileup.getNoOfwatershedphoto1().isEmpty() ? filePath +"W"+"wat1"+userfileup.getVillage()+"_"+ userfileup.getNoOfwatershedphoto1().getOriginalFilename() :  null);
				if (!userfileup.getNoOfwatershedphoto1().isEmpty()) {
				main.setAward_distribution_path1_latitude(userfileup.getNoOfwatershedphoto1_lat());
		        main.setAward_distribution_path1_longitute(userfileup.getNoOfwatershedphoto1_lng());
		        main.setAward_distribution_path1_time(parseTimestamp(userfileup.getNoOfwatershedphoto1_time()));
				}
				else {
					main.setAward_distribution_path1_latitude(null);
			        main.setAward_distribution_path1_longitute(null);
			        main.setAward_distribution_path1_time(null);
				}
				main.setAwardDistributionPath2(!userfileup.getNoOfwatershedphoto2().isEmpty() ? filePath + "W"+"wat2"+userfileup.getVillage()+"_"+userfileup.getNoOfwatershedphoto2().getOriginalFilename() :  null);
				if (!userfileup.getNoOfwatershedphoto2().isEmpty()) {
				main.setAward_distribution_path2_latitude(userfileup.getNoOfwatershedphoto2_lat());
		        main.setAward_distribution_path2_longitute(userfileup.getNoOfwatershedphoto2_lng());
		        main.setAward_distribution_path2_time(parseTimestamp(userfileup.getNoOfwatershedphoto2_time()));
				}
				else {
					main.setAward_distribution_path2_latitude(null);
			        main.setAward_distribution_path2_longitute(null);
			        main.setAward_distribution_path2_time(null);
				}
			
				main.setCulturalActivityPath1(!userfileup.getCulturalActivityphoto1().isEmpty() ? filePath + "W"+"Cul1"+userfileup.getVillage()+"_"+userfileup.getCulturalActivityphoto1().getOriginalFilename() : null);
				if (!userfileup.getCulturalActivityphoto1().isEmpty()) {
				main.setCultural_activity_path1_latitude(userfileup.getCulturalActivityphoto1_lat());
		        main.setCultural_activity_path1_longitute(userfileup.getCulturalActivityphoto1_lng());
		        main.setCultural_activity_path1_time(parseTimestamp(userfileup.getCulturalActivityphoto1_time()));
				}
				else {
					main.setCultural_activity_path1_latitude(null);
			        main.setCultural_activity_path1_longitute(null);
			        main.setCultural_activity_path1_time(null);
				}
				main.setCulturalActivityPath2(!userfileup.getCulturalActivityphoto2().isEmpty() ? filePath + "W"+"Cul2"+userfileup.getVillage()+"_"+userfileup.getCulturalActivityphoto2().getOriginalFilename() : null);
				if (!userfileup.getCulturalActivityphoto2().isEmpty()) {
				main.setCultural_activity_path2_latitude(userfileup.getCulturalActivityphoto2_lat());
		        main.setCultural_activity_path2_longitute(userfileup.getCulturalActivityphoto2_lng());
		        main.setCultural_activity_path2_time(parseTimestamp(userfileup.getCulturalActivityphoto2_time()));
				}
				else {
					main.setCultural_activity_path2_latitude(null);
			        main.setCultural_activity_path2_longitute(null);
			        main.setCultural_activity_path2_time(null);
				}
				
				main.setStatus("C");
				main.setCreatedBy(session.getAttribute("loginID").toString());
				main.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
				main.setRequestedIp(ipAddr);
//				
				sess.save(main);
				res="success";
				sess.getTransaction().commit();
				}
				
				else if(upload.equals("fileisempty")) {
					
					ca.setCulturalId(userfileup.getCulturalActivity());
					
					st.setStCode(Integer.parseInt(session.getAttribute("stateCode").toString()));
					main.setIwmpState(st);
					
					dt.setDcode(userfileup.getDistrict());
					main.setIwmpDistrict(dt);
					
					bl.setBcode(userfileup.getBlock());
					main.setIwmpBlock(bl);
					
					gp.setGcode(userfileup.getGrampan());
					main.setIwmpGramPanchayat(gp);
				
					vil.setVcode(userfileup.getVillage());
					main.setIwmpVillage(vil);
					
					main.setYatraDate1(timestamp1);
					main.setYatraLocation(userfileup.getLocation());
					main.setMaleParticipants(userfileup.getMaleParticipants());
					main.setFemaleParticipants(userfileup.getFemaleParticipants());
					main.setCentralMinister(userfileup.getCentralMinisters());
					main.setStateMinister(userfileup.getStateMinisters());
					main.setParliamentMembers(userfileup.getMembersOfParliament());
					main.setLegislativeAssemblyMembers(userfileup.getLegAssemblyMembers());
					main.setLegislativeCouncilMembers(userfileup.getLegCouncilMembers());
					main.setOtherPublicRepresentatives(userfileup.getPublicReps());
					main.setGovOfficials(userfileup.getGovOfficials());
					main.setNoOfArExperiencePeople(userfileup.getArExperience());
					main.setBhumiJalSanrakshan(userfileup.getShapathYes());
					main.setWatershedYatraFilm(userfileup.getFilmYes());
					main.setQuizParticipants(userfileup.getQuizParticipants());
					
					main.setmCulturalActivity(ca);
					main.setBhoomiPoojanNoOfWorks(userfileup.getBhoomiWorks());
					main.setBhoomiPoojanCostOfWorks(userfileup.getBhoomiCost());
					main.setLokarpanNoOfWorks(userfileup.getLokWorks());
					main.setLokarpanCostOfWorks(userfileup.getCostWorks());
					main.setShramdaanNoOfLocation(userfileup.getLocShramdaan());
					main.setShramdaanNoOfParticipatedPeople(userfileup.getLocShramdaanps());
					main.setManhour(userfileup.getManhour());
					main.setPlantationArea(userfileup.getPlantationArea());
					main.setNoOfAgroForsetry(userfileup.getNofagrohorti());
					main.setAwardDistribution(userfileup.getNoOfwatershed());
					

					main.setBhumiJalSanrakshanPath1(!userfileup.getShapathYesphoto1().isEmpty() ? filePath + "W"+"Shap1"+userfileup.getVillage()+"_"+userfileup.getShapathYesphoto1().getOriginalFilename() :  null);
					if (!userfileup.getShapathYesphoto1().isEmpty()) {
					main.setBhumi_jal_sanrakshan_path1_latitude(userfileup.getShapathYesphoto1_lat());
			        main.setBhumi_jal_sanrakshan_path1_longitute(userfileup.getShapathYesphoto1_lng());
			        main.setBhumi_jal_sanrakshan_path1_time(parseTimestamp(userfileup.getShapathYesphoto1_time()));
					}
					else {
						main.setBhumi_jal_sanrakshan_path1_latitude(null);
				        main.setBhumi_jal_sanrakshan_path1_longitute(null);
				        main.setBhumi_jal_sanrakshan_path1_time(null);
					}
					main.setBhumiJalSanrakshanPath2(!userfileup.getShapathYesphoto2().isEmpty() ? filePath + "W"+"Shap2"+userfileup.getVillage()+"_"+userfileup.getShapathYesphoto2().getOriginalFilename() :  null);
					if (!userfileup.getShapathYesphoto2().isEmpty()) {
					main.setBhumi_jal_sanrakshan_path2_latitude(userfileup.getShapathYesphoto2_lat());
			        main.setBhumi_jal_sanrakshan_path2_longitute(userfileup.getShapathYesphoto2_lng());
			        main.setBhumi_jal_sanrakshan_path2_time(parseTimestamp(userfileup.getShapathYesphoto2_time()));
					}
					else {
						main.setBhumi_jal_sanrakshan_path2_latitude(null);
				        main.setBhumi_jal_sanrakshan_path2_longitute(null);
				        main.setBhumi_jal_sanrakshan_path2_time(null);
					}
					main.setYatraFilmPath1(!userfileup.getFilmYesphoto1().isEmpty() ? filePath + "W"+"Film1"+userfileup.getVillage()+"_"+ userfileup.getFilmYesphoto1().getOriginalFilename() :  null);
					if (!userfileup.getFilmYesphoto1().isEmpty()) {
					main.setYatra_film_path1_latitude(userfileup.getFilmYesphoto1_lat());
			        main.setYatra_film_path1_longitute(userfileup.getFilmYesphoto1_lng());
			        main.setYatra_film_path1_time(parseTimestamp(userfileup.getFilmYesphoto1_time()));
					}
					else {
						main.setYatra_film_path1_latitude(null);
				        main.setYatra_film_path1_longitute(null);
				        main.setYatra_film_path1_time(null);
					}
					main.setYatraFilmPath2(!userfileup.getFilmYesphoto2().isEmpty() ? filePath + "W"+"Film2"+userfileup.getVillage()+"_"+userfileup.getFilmYesphoto2().getOriginalFilename() :  null);
					if (!userfileup.getFilmYesphoto2().isEmpty()) {
					main.setYatra_film_path2_latitude(userfileup.getFilmYesphoto2_lat());
			        main.setYatra_film_path2_longitute(userfileup.getFilmYesphoto2_lng());
			        main.setYatra_film_path2_time(parseTimestamp(userfileup.getFilmYesphoto2_time()));
					}
					else {
						main.setYatra_film_path2_latitude(null);
				        main.setYatra_film_path2_longitute(null);
				        main.setYatra_film_path2_time(null);
					}
			        
					main.setBhoomiPoojanPath1(!userfileup.getBhoomiCostphoto1().isEmpty() ? filePath + "W"+"Bh1"+userfileup.getVillage()+"_"+userfileup.getBhoomiCostphoto1().getOriginalFilename() :  null);
					if (!userfileup.getBhoomiCostphoto1().isEmpty()) {
					main.setBhoomi_poojan_path1_latitude(userfileup.getBhoomiCostphoto1_lat());
			        main.setBhoomi_poojan_path1_longitute(userfileup.getBhoomiCostphoto1_lng());
			        main.setBhoomi_poojan_path1_time(parseTimestamp(userfileup.getBhoomiCostphoto1_time()));
					}
					else {
						main.setBhoomi_poojan_path1_latitude(null);
				        main.setBhoomi_poojan_path1_longitute(null);
				        main.setBhoomi_poojan_path1_time(null);
					}
					main.setBhoomiPoojanPath2(!userfileup.getBhoomiCostphoto2().isEmpty() ? filePath + "W"+"Bh2"+userfileup.getVillage()+"_"+userfileup.getBhoomiCostphoto2().getOriginalFilename() :  null);
					if (!userfileup.getBhoomiCostphoto2().isEmpty()) {
					main.setBhoomi_poojan_path2_latitude(userfileup.getBhoomiCostphoto2_lat());
			        main.setBhoomi_poojan_path2_longitute(userfileup.getBhoomiCostphoto2_lng());
			        main.setBhoomi_poojan_path2_time(parseTimestamp(userfileup.getBhoomiCostphoto2_time()));
					}
					else {
						main.setBhoomi_poojan_path2_latitude(null);
				        main.setBhoomi_poojan_path2_longitute(null);
				        main.setBhoomi_poojan_path2_time(null);
					}
					main.setArExperiencePath1(!userfileup.getArExperiencephoto1().isEmpty() ? filePath + "W"+"Ar1"+userfileup.getVillage()+"_"+userfileup.getArExperiencephoto1().getOriginalFilename() :  null);
					if (!userfileup.getArExperiencephoto1().isEmpty()) {
					main.setAr_experience_path1_latitude(userfileup.getArExperiencephoto1_lat());
			        main.setAr_experience_path1_longitute(userfileup.getArExperiencephoto1_lng());
			        main.setAr_experience_path1_time(parseTimestamp(userfileup.getArExperiencephoto1_time()));
					}
					else {
						main.setAr_experience_path1_latitude(null);
				        main.setAr_experience_path1_longitute(null);
				        main.setAr_experience_path1_time(null);
					}
					main.setArExperiencePath2(!userfileup.getArExperiencephoto2().isEmpty() ? filePath + "W"+"Ar2"+userfileup.getVillage()+"_"+userfileup.getArExperiencephoto2().getOriginalFilename() :  null);
					if (!userfileup.getArExperiencephoto2().isEmpty()) {
					main.setAr_experience_path2_latitude(userfileup.getArExperiencephoto2_lat());
			        main.setAr_experience_path2_longitute(userfileup.getArExperiencephoto2_lng());
			        main.setAr_experience_path2_time(parseTimestamp(userfileup.getArExperiencephoto2_time()));
					}
					else {
						main.setAr_experience_path2_latitude(null);
				        main.setAr_experience_path2_longitute(null);
				        main.setAr_experience_path2_time(null);
					}
					main.setShramdaanPath1(!userfileup.getLocShramdaanpsphoto1().isEmpty() ? filePath + "W"+"Loc1"+userfileup.getVillage()+"_"+userfileup.getLocShramdaanpsphoto1().getOriginalFilename() :  null);
					if (!userfileup.getLocShramdaanpsphoto1().isEmpty()) {
					main.setShramdaan_path1_latitude(userfileup.getLocShramdaanpsphoto1_lat());
			        main.setShramdaan_path1_longitute(userfileup.getLocShramdaanpsphoto1_lng());
			        main.setShramdaan_path1_time(parseTimestamp(userfileup.getLocShramdaanpsphoto1_time()));
					}
					else {
						main.setShramdaan_path1_latitude(null);
				        main.setShramdaan_path1_longitute(null);
				        main.setShramdaan_path1_time(null);
					}
					main.setShramdaanPath2(!userfileup.getLocShramdaanpsphoto2().isEmpty() ? filePath + "W"+"Loc2"+userfileup.getVillage()+"_"+userfileup.getLocShramdaanpsphoto2().getOriginalFilename() :  null);
					if (!userfileup.getLocShramdaanpsphoto2().isEmpty()) {
					main.setShramdaan_path2_latitude(userfileup.getLocShramdaanpsphoto2_lat());
			        main.setShramdaan_path2_longitute(userfileup.getLocShramdaanpsphoto2_lng());
			        main.setShramdaan_path2_time(parseTimestamp(userfileup.getLocShramdaanpsphoto2_time()));
					}
					else {
						main.setShramdaan_path2_latitude(null);
				        main.setShramdaan_path2_longitute(null);
				        main.setShramdaan_path2_time(null);
					}
					main.setQuizParticipantsPath1(!userfileup.getQuizParticipantsphoto1().isEmpty() ? filePath + "W"+"Quiz1"+userfileup.getVillage()+"_"+userfileup.getQuizParticipantsphoto1().getOriginalFilename() :  null);
					if (!userfileup.getQuizParticipantsphoto1().isEmpty()) {
					main.setQuiz_participants_path1_latitude(userfileup.getQuizParticipantsphoto1_lat());
			        main.setQuiz_participants_path1_longitute(userfileup.getQuizParticipantsphoto1_lng());
			        main.setQuiz_participants_path1_time(parseTimestamp(userfileup.getQuizParticipantsphoto1_time()));
					}
					else {
						main.setQuiz_participants_path1_latitude(null);
				        main.setQuiz_participants_path1_longitute(null);
				        main.setQuiz_participants_path1_time(null);
					}
					main.setQuizParticipantsPath2(!userfileup.getQuizParticipantsphoto2().isEmpty() ? filePath +"W"+"Quiz2"+userfileup.getVillage()+"_"+ userfileup.getQuizParticipantsphoto2().getOriginalFilename() :  null);
					if (!userfileup.getQuizParticipantsphoto2().isEmpty()) {
					main.setQuiz_participants_path2_latitude(userfileup.getQuizParticipantsphoto2_lat());
			        main.setQuiz_participants_path2_longitute(userfileup.getQuizParticipantsphoto2_lng());
			        main.setQuiz_participants_path2_time(parseTimestamp(userfileup.getQuizParticipantsphoto2_time()));
					}
					else {
						main.setQuiz_participants_path2_latitude(null);
				        main.setQuiz_participants_path2_longitute(null);
				        main.setQuiz_participants_path2_time(null);
					}
					main.setLokarpanPath1(!userfileup.getLokWorksphoto1().isEmpty() ? filePath +"W"+"Lok1"+userfileup.getVillage()+"_"+ userfileup.getLokWorksphoto1().getOriginalFilename() :  null);
					if (!userfileup.getLokWorksphoto1().isEmpty()) {
					main.setLokarpan_path1_latitude(userfileup.getLokWorksphoto1_lat());
			        main.setLokarpan_path1_longitute(userfileup.getLokWorksphoto1_lng());
			        main.setLokarpan_path1_time(parseTimestamp(userfileup.getLokWorksphoto1_time()));
					}
					else {
						main.setLokarpan_path1_latitude(null);
				        main.setLokarpan_path1_longitute(null);
				        main.setLokarpan_path1_time(null);
					}
			        
					main.setLokarpanPath2(!userfileup.getLokWorksphoto2().isEmpty() ? filePath + "W"+"Lok2"+userfileup.getVillage()+"_"+userfileup.getLokWorksphoto2().getOriginalFilename() :  null);
					if (!userfileup.getLokWorksphoto2().isEmpty()) {
					main.setLokarpan_path2_latitude(userfileup.getLokWorksphoto2_lat());
			        main.setLokarpan_path2_longitute(userfileup.getLokWorksphoto2_lng());
			        main.setLokarpan_path2_time(parseTimestamp(userfileup.getLokWorksphoto2_time()));
					}
					else {
						main.setLokarpan_path2_latitude(null);
				        main.setLokarpan_path2_longitute(null);
				        main.setLokarpan_path2_time(null);
					}
					main.setPlantationPath1(!userfileup.getPlantationAreaphoto1().isEmpty() ? filePath + "W"+"Plan1"+userfileup.getVillage()+"_"+userfileup.getPlantationAreaphoto1().getOriginalFilename() :  null);
					if (!userfileup.getPlantationAreaphoto1().isEmpty()) {
					main.setPlantation_path1_latitude(userfileup.getPlantationAreaphoto1_lat());
			        main.setPlantation_path1_longitute(userfileup.getPlantationAreaphoto1_lng());
			        main.setPlantation_path1_time(parseTimestamp(userfileup.getPlantationAreaphoto1_time()));
					}
					else {
						main.setPlantation_path1_latitude(null);
				        main.setPlantation_path1_longitute(null);
				        main.setPlantation_path1_time(null);
					}
					main.setPlantationPath2(!userfileup.getPlantationAreaphoto2().isEmpty() ? filePath + "W"+"Plan2"+userfileup.getVillage()+"_"+userfileup.getPlantationAreaphoto2().getOriginalFilename() :  null);
					if (!userfileup.getPlantationAreaphoto2().isEmpty()) {
					main.setPlantation_path2_latitude(userfileup.getPlantationAreaphoto2_lat());
			        main.setPlantation_path2_longitute(userfileup.getPlantationAreaphoto2_lng());
			        main.setPlantation_path2_time(parseTimestamp(userfileup.getPlantationAreaphoto2_time()));
					}
					else {
						main.setPlantation_path2_latitude(null);
				        main.setPlantation_path2_longitute(null);
				        main.setPlantation_path2_time(null);
					}
					main.setAwardDistributionPath1(!userfileup.getNoOfwatershedphoto1().isEmpty() ? filePath +"W"+"wat1"+userfileup.getVillage()+"_"+ userfileup.getNoOfwatershedphoto1().getOriginalFilename() :  null);
					if (!userfileup.getNoOfwatershedphoto1().isEmpty()) {
					main.setAward_distribution_path1_latitude(userfileup.getNoOfwatershedphoto1_lat());
			        main.setAward_distribution_path1_longitute(userfileup.getNoOfwatershedphoto1_lng());
			        main.setAward_distribution_path1_time(parseTimestamp(userfileup.getNoOfwatershedphoto1_time()));
					}
					else {
						main.setAward_distribution_path1_latitude(null);
				        main.setAward_distribution_path1_longitute(null);
				        main.setAward_distribution_path1_time(null);
					}
					main.setAwardDistributionPath2(!userfileup.getNoOfwatershedphoto2().isEmpty() ? filePath + "W"+"wat2"+userfileup.getVillage()+"_"+userfileup.getNoOfwatershedphoto2().getOriginalFilename() :  null);
					if (!userfileup.getNoOfwatershedphoto2().isEmpty()) {
					main.setAward_distribution_path2_latitude(userfileup.getNoOfwatershedphoto2_lat());
			        main.setAward_distribution_path2_longitute(userfileup.getNoOfwatershedphoto2_lng());
			        main.setAward_distribution_path2_time(parseTimestamp(userfileup.getNoOfwatershedphoto2_time()));
					}
					else {
						main.setAward_distribution_path2_latitude(null);
				        main.setAward_distribution_path2_longitute(null);
				        main.setAward_distribution_path2_time(null);
					}
				
					main.setCulturalActivityPath1(!userfileup.getCulturalActivityphoto1().isEmpty() ? filePath + "W"+"Cul1"+userfileup.getVillage()+"_"+userfileup.getCulturalActivityphoto1().getOriginalFilename() : null);
					if (!userfileup.getCulturalActivityphoto1().isEmpty()) {
					main.setCultural_activity_path1_latitude(userfileup.getCulturalActivityphoto1_lat());
			        main.setCultural_activity_path1_longitute(userfileup.getCulturalActivityphoto1_lng());
			        main.setCultural_activity_path1_time(parseTimestamp(userfileup.getCulturalActivityphoto1_time()));
					}
					else {
						main.setCultural_activity_path1_latitude(null);
				        main.setCultural_activity_path1_longitute(null);
				        main.setCultural_activity_path1_time(null);
					}
					main.setCulturalActivityPath2(!userfileup.getCulturalActivityphoto2().isEmpty() ? filePath + "W"+"Cul2"+userfileup.getVillage()+"_"+userfileup.getCulturalActivityphoto2().getOriginalFilename() : null);
					if (!userfileup.getCulturalActivityphoto2().isEmpty()) {
					main.setCultural_activity_path2_latitude(userfileup.getCulturalActivityphoto2_lat());
			        main.setCultural_activity_path2_longitute(userfileup.getCulturalActivityphoto2_lng());
			        main.setCultural_activity_path2_time(parseTimestamp(userfileup.getCulturalActivityphoto2_time()));
					}
					else {
						main.setCultural_activity_path2_latitude(null);
				        main.setCultural_activity_path2_longitute(null);
				        main.setCultural_activity_path2_time(null);
					}
					
					main.setStatus("C");
					main.setCreatedBy(session.getAttribute("loginID").toString());
					main.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
					main.setRequestedIp(ipAddr);
//					
					sess.save(main);
					res="success";
					sess.getTransaction().commit();
					}
				else {
					
					res = "fail";
					
				}
				
					
				
				
				
				
				
				
				
				
				
		}
		catch(Exception ex) 
		{
			res = "fail";
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}
		finally {
			//session.flush();
		//session.close();
		}
		
		return res;
	}

	@Override
	public LinkedHashMap<Integer, String> getCultActivity() {
		
		List<MCulturalActivity> cultList=new ArrayList<MCulturalActivity>();
		String hql=cultListWatershedyatra;
		LinkedHashMap<Integer, String> cultMap=new LinkedHashMap<Integer, String>();
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
				session.beginTransaction();
				Query query = session.createQuery("from MCulturalActivity  order by culturalId");
				cultList = query.list();
				for (MCulturalActivity mcult : cultList) 
				{
					cultMap.put(mcult.getCulturalId(), mcult.getCulturalName());
				}
				//session.getTransaction().commit();
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
			session.getTransaction().commit();
			//session.flush();
		//session.close();
		}
        return cultMap;
	}

	@Override
	public List<NodalOfficerBean> getCompleteListofNodalOfficer(Integer stcd) {
		// TODO Auto-generated method stub
		String getReport=getCompleteListofNodalOfficer;
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
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
		//	session.getTransaction().commit();
			//session.flush();
		//session.close();
		}
		return list;
	}

	@Override
	public List<WatershedYatraBean> getWatershedYatraList(Integer stcd) {
		String getReport=getWyatraDetails;
		Session session = sessionFactory.getCurrentSession();
		List<WatershedYatraBean> list = new ArrayList<WatershedYatraBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(WatershedYatraBean.class));
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
	public String getExistingWatershedYatraVillageCodes(Integer villageCode) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.getCurrentSession();
		  int result;
		  String data="fail";
		  try {
		    session.beginTransaction();
			List list = session.createQuery("SELECT iwmpVillage.vcode FROM WatershedYatVill where iwmpVillage.vcode=:villageCode").setInteger("villageCode", villageCode).list();
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

	@Override
	public List<String> getImagesWatershedYatraId(Integer watershedYatraId) {
		Session session = sessionFactory.getCurrentSession();
		List<WatershedYatVill> list = new ArrayList<>();
		List<String> imgList = new ArrayList<>();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from WatershedYatVill where watershedYatraId = :watershedYatraId");
			query.setInteger("watershedYatraId", watershedYatraId);
//			query.setResultTransformer(Transformers.aliasToBean(WatershedYatraInauguaration.class));
			list = query.list();
			session.getTransaction().commit();
//			imgList.add(list.get(0).getWatershedYatraId().toString());
			
			//server
				if(list.get(0).getArExperiencePath1()!=null)
			imgList.add(list.get(0).getArExperiencePath1().substring(list.get(0).getArExperiencePath1().lastIndexOf("/")+1));
				
				if(list.get(0).getArExperiencePath2()!=null)
			imgList.add(list.get(0).getArExperiencePath2().substring(list.get(0).getArExperiencePath2().lastIndexOf("/")+1));
			
				if(list.get(0).getBhumiJalSanrakshanPath1()!=null)
				imgList.add(list.get(0).getBhumiJalSanrakshanPath1().substring(list.get(0).getBhumiJalSanrakshanPath1().lastIndexOf("/")+1));
			
				if(list.get(0).getBhumiJalSanrakshanPath2()!=null)
				imgList.add(list.get(0).getBhumiJalSanrakshanPath2().substring(list.get(0).getBhumiJalSanrakshanPath2().lastIndexOf("/")+1));
			
				if(list.get(0).getYatraFilmPath1()!=null)
				imgList.add(list.get(0).getYatraFilmPath1().substring(list.get(0).getYatraFilmPath1().lastIndexOf("/")+1));
			
				if(list.get(0).getYatraFilmPath2()!=null)
				imgList.add(list.get(0).getYatraFilmPath2().substring(list.get(0).getYatraFilmPath2().lastIndexOf("/")+1));
			
			if(list.get(0).getQuizParticipantsPath1()!=null)
			imgList.add(list.get(0).getQuizParticipantsPath1().substring(list.get(0).getQuizParticipantsPath1().lastIndexOf("/")+1));
			
			if(list.get(0).getQuizParticipantsPath2()!=null)
			imgList.add(list.get(0).getQuizParticipantsPath2().substring(list.get(0).getQuizParticipantsPath2().lastIndexOf("/")+1));
			
			if(list.get(0).getCulturalActivityPath1()!=null)
			imgList.add(list.get(0).getCulturalActivityPath1().substring(list.get(0).getCulturalActivityPath1().lastIndexOf("/")+1));
			
			if(list.get(0).getCulturalActivityPath2()!=null)
			imgList.add(list.get(0).getCulturalActivityPath2().substring(list.get(0).getCulturalActivityPath2().lastIndexOf("/")+1));
			
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
			
			//local
/*			
			if(list.get(0).getArExperiencePath1()!=null)
			imgList.add(list.get(0).getArExperiencePath1().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getArExperiencePath2()!=null)
			imgList.add(list.get(0).getArExperiencePath2().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getBhumiJalSanrakshanPath1()!=null)
			imgList.add(list.get(0).getBhumiJalSanrakshanPath1().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getBhumiJalSanrakshanPath2()!=null)
			imgList.add(list.get(0).getBhumiJalSanrakshanPath2().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getYatraFilmPath1()!=null)
			imgList.add(list.get(0).getYatraFilmPath1().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getYatraFilmPath2()!=null)
			imgList.add(list.get(0).getYatraFilmPath2().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getQuizParticipantsPath1()!=null)
			imgList.add(list.get(0).getQuizParticipantsPath1().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getQuizParticipantsPath2()!=null)
			imgList.add(list.get(0).getQuizParticipantsPath2().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getCulturalActivityPath1()!=null)
			imgList.add(list.get(0).getCulturalActivityPath1().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getCulturalActivityPath2()!=null)
			imgList.add(list.get(0).getCulturalActivityPath2().replaceAll(".*\\\\", ""));
			
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
*/			
		}catch(Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return imgList;
	}

	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	
	@Override
	public String savePreYatraPrep(PreYatraPrepBean preYatraPrep, HttpSession session, HttpServletRequest request) {
	    Session sess = sessionFactory.getCurrentSession();
	    String res = "fail";
	    Integer stateCode = Integer.parseInt(session.getAttribute("stateCode").toString());
	    Timestamp Gtimestamp1 = null;
	    Timestamp Gtimestamp2 = null;
	    Timestamp ptimestamp1 = null;
	    Timestamp ptimestamp2 = null;
	    
	    try {
	        sess.beginTransaction();

	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = formatter.parse(preYatraPrep.getDate());
	        Date date1 = formatter.parse(preYatraPrep.getDate1());

	        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
	        
	        if (!"Not Available".equals(preYatraPrep.getGramphoto1_time())) {
	        String dateTimeString1 = preYatraPrep.getGramphoto1_time(); 
	        LocalDateTime GlocalDateTime1 = LocalDateTime.parse(dateTimeString1, originalFormatter);
	        String formattedDate = GlocalDateTime1.format(datetimeFormatter);
	        LocalDateTime GlocalDateTime2 = LocalDateTime.parse(formattedDate, datetimeFormatter);
	         Gtimestamp1 = Timestamp.valueOf(GlocalDateTime2);
	        }
	        else {
	        	 Gtimestamp1 = null;
	        }
	        
	        if (!"Not Available".equals(preYatraPrep.getGramphoto2_time())) {
	        String dateTimeString2 = preYatraPrep.getGramphoto2_time(); 
	        LocalDateTime PlocalDateTime1 = LocalDateTime.parse(dateTimeString2, originalFormatter);
	        String PformattedDate = PlocalDateTime1.format(datetimeFormatter);
	        LocalDateTime PlocalDateTime2 = LocalDateTime.parse(PformattedDate, datetimeFormatter);
	        Gtimestamp2 = Timestamp.valueOf(PlocalDateTime2);
	        }
	        else {
	        	Gtimestamp2 = null;
	        }
	        PreYatraPreparation prep = new PreYatraPreparation();
	        IwmpState iwmpState = sess.get(IwmpState.class, stateCode);

	        Integer districtCode = preYatraPrep.getDistrict();
	        IwmpDistrict iwmpDistrict = sess.get(IwmpDistrict.class, districtCode);

	        Integer blockCode = preYatraPrep.getBlock();
	        IwmpBlock iwmpblock = sess.get(IwmpBlock.class, blockCode);

	        Integer gramCode = preYatraPrep.getGrampan();
	        IwmpGramPanchayat gp = sess.get(IwmpGramPanchayat.class, gramCode);

	        Integer villageCode = preYatraPrep.getVillage1();
	        IwmpVillage village = sess.get(IwmpVillage.class, villageCode);

	        //set prabhat pheri photos format
	        if (!"Not Available".equals(preYatraPrep.getPheriphoto1_time())) {
	        String dateTimeString = preYatraPrep.getPheriphoto1_time(); 
	        LocalDateTime GlocalDateTimep1 = LocalDateTime.parse(dateTimeString, originalFormatter);
	        String pformattedDate1 = GlocalDateTimep1.format(datetimeFormatter);
	        LocalDateTime GlocalDateTimep2 = LocalDateTime.parse(pformattedDate1, datetimeFormatter);
	        ptimestamp1 = Timestamp.valueOf(GlocalDateTimep2);
	        }
	        else {
	        	ptimestamp1 = null;
	        }
	        if (!"Not Available".equals(preYatraPrep.getPheriphoto2_time())) {
	        String dateTimeStringp2 = preYatraPrep.getPheriphoto2_time(); 
	        LocalDateTime localDateTimep1 = LocalDateTime.parse(dateTimeStringp2, originalFormatter);
	        String P2formattedDate = localDateTimep1.format(datetimeFormatter);
	        LocalDateTime P2localDateTime2 = LocalDateTime.parse(P2formattedDate, datetimeFormatter);
	        ptimestamp2 = Timestamp.valueOf(P2localDateTime2);
	        }
	        else {
	        	ptimestamp2 = null;
	        }
	        
	        
	        PreYatraPreparation prep2 = new PreYatraPreparation();
	        Integer districtCode2 = preYatraPrep.getDistrict1();
	        IwmpDistrict iwmpDistrict2 = sess.get(IwmpDistrict.class, districtCode2);

	        Integer blockCode2 = preYatraPrep.getBlock1();
	        IwmpBlock iwmpblock2 = sess.get(IwmpBlock.class, blockCode2);

	        Integer gramCode2 = preYatraPrep.getGrampan1();
	        IwmpGramPanchayat gp2 = sess.get(IwmpGramPanchayat.class, gramCode2);
   
	        // saved pre yatra preparation for gram sabha
	        prep.setIwmpState(iwmpState);
	        prep.setIwmpDistrict(iwmpDistrict);
	        prep.setIwmpBlock(iwmpblock);
	        prep.setIwmpGramPanchayat(gp);
	        prep.setStatus("C");
	        prep.setPreYatraType("gramSabha");
	        prep.setCreatedDate(new Date());
	        prep.setCreatedBy(session.getAttribute("loginID").toString());
	        prep.setRequestedIp(getClientIpAddr(request));
	        prep.setUpdatedBy(session.getAttribute("loginID").toString());
	        prep.setUpdatedDate(new Date());
	        sess.save(prep);

	        Integer prepId = prep.getPrepId(); 
	        
	        // Save Photo Files
	        boolean isDuplicatePhoto1 = isDuplicateGSPhoto1(preYatraPrep.getGramphoto1().getOriginalFilename(), preYatraPrep.getGramphoto1_lat(), preYatraPrep.getGramphoto1_lng());
	        String photo1Path = saveFile(preYatraPrep.getGramphoto1(), prepId, "GS_", "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/preyatraprep/", isDuplicatePhoto1);
	        
	        
	        
	        boolean isDuplicatePhoto2 = isDuplicateGSPhoto2(preYatraPrep.getGramphoto2().getOriginalFilename(), preYatraPrep.getGramphoto2_lat(), preYatraPrep.getGramphoto2_lng());
	        String photo2Path = saveFile(preYatraPrep.getGramphoto2(), prepId, "GS_", "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/preyatraprep/", isDuplicatePhoto2);
	        
	        PreYatraGramsabha gram = new PreYatraGramsabha();
	        gram.setPreYatraPreparation(prep);
	        gram.setGramsabhaDate(date);
	        gram.setGramsabhaPhoto1(photo1Path); 
	        gram.setGramsabhaPhoto1Latitude(preYatraPrep.getGramphoto1_lat());
	        gram.setGramsabhaPhoto1Longitude(preYatraPrep.getGramphoto1_lng());
	        gram.setGramsabhaPhoto1Time(Gtimestamp1);
	        gram.setGramsabhaPhoto2(photo2Path); 
	        gram.setGramsabhaPhoto2Latitude(preYatraPrep.getGramphoto2_lat());
	        gram.setGramsabhaPhoto2Longitude(preYatraPrep.getGramphoto2_lng());
	        gram.setGramsabhaPhoto2Time(Gtimestamp2);
	        gram.setCreatedBy(session.getAttribute("loginID").toString());
	        gram.setRequestedIp(getClientIpAddr(request));
	        gram.setCreatedDate(new Date());
            sess.save(gram);
            
         // saved pre yatra preparation for Prabhat Pheri
	        prep2.setIwmpState(iwmpState);
	        prep2.setIwmpDistrict(iwmpDistrict2);
	        prep2.setIwmpBlock(iwmpblock2);
	        prep2.setIwmpGramPanchayat(gp2);
	        prep2.setIwmpVillage(village);
	        prep2.setStatus("C");
	        prep2.setPreYatraType("prabhatPheri");
	        prep2.setCreatedDate(new Date());
	        prep2.setCreatedBy(session.getAttribute("loginID").toString());
	        prep2.setRequestedIp(getClientIpAddr(request));
	        prep2.setUpdatedBy(session.getAttribute("loginID").toString());
	        prep2.setUpdatedDate(new Date());
	        sess.save(prep2);
	        Integer prepId2 = prep.getPrepId();
	        boolean isDuplicatePhoto3 = isDuplicatePPPhoto1(preYatraPrep.getPheriphoto1().getOriginalFilename(), preYatraPrep.getPheriphoto1_lat(), preYatraPrep.getPheriphoto1_lng());
	        String photo3Path = saveFile(preYatraPrep.getPheriphoto1(), prepId2, "PP_", "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/preyatraprep/", isDuplicatePhoto3);
	        
	        boolean isDuplicatePhoto4 = isDuplicatePPPhoto2(preYatraPrep.getPheriphoto2().getOriginalFilename(), preYatraPrep.getPheriphoto2_lat(), preYatraPrep.getPheriphoto2_lng());
	        String photo4Path = saveFile(preYatraPrep.getPheriphoto2(), prepId2, "PP_", "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/preyatraprep/", isDuplicatePhoto4);
	        
	        PreYatraPrabhatpheri pheri = new PreYatraPrabhatpheri();
	        pheri.setPreYatraPreparation(prep2);
	        pheri.setPrabhatpheriDate(date1);
	        pheri.setPrabhatpheriPhoto1(photo3Path);
	        pheri.setPrabhatpheriPhoto1Latitude(preYatraPrep.getPheriphoto1_lat());
	        pheri.setPrabhatpheriPhoto1Longitude(preYatraPrep.getPheriphoto1_lng());
	        pheri.setPrabhatpheriPhoto1Time(ptimestamp1);
	        pheri.setPrabhatpheriPhoto2(photo4Path);
	        pheri.setPrabhatpheriPhoto2Latitude(preYatraPrep.getPheriphoto2_lat());
	        pheri.setPrabhatpheriPhoto2Longitude(preYatraPrep.getPheriphoto2_lng());
	        pheri.setPrabhatpheriPhoto2Time(ptimestamp2);
	        pheri.setCreatedDate(new Date());
	        pheri.setCreatedBy(session.getAttribute("loginID").toString());
	        pheri.setRequestedIp(getClientIpAddr(request));
	        sess.save(pheri);
	        sess.getTransaction().commit();
	        res = "success";
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	        sess.getTransaction().rollback();
	        ex.printStackTrace();
	        return "failure";
	    } finally {
	        // session.flush();
	        // session.close();
	    }
	   return res;
	}

	private boolean isDuplicateGSPhoto1(String photoName, String latitude, String longitude) {
	    Session session = sessionFactory.openSession();
	    boolean isDuplicate = false;
	    String hql = null;
	    Query query = null;
	    try {
	        if (!latitude.equals("Not Available")) {
	            hql = "FROM PreYatraGramsabha WHERE gramsabhaPhoto1 = :photoName AND gramsabhaPhoto1Latitude = :latitude AND gramsabhaPhoto1Longitude = :longitude";
	            query = session.createQuery(hql);
	            query.setParameter("photoName", photoName);
	            query.setParameter("latitude", latitude);
	            query.setParameter("longitude", longitude);
	        } else {
	            hql = "FROM PreYatraGramsabha WHERE gramsabhaPhoto1 = :photoName";
	            query = session.createQuery(hql);
	            query.setParameter("photoName", photoName);
	        }

	        List results = query.list(); // Get all results
	        isDuplicate = !results.isEmpty(); // Check if there are any results
	    } finally {
	        session.close();
	    }

	    return isDuplicate;
	}

	private boolean isDuplicateGSPhoto2(String photoName, String latitude, String longitude) {
	    Session session = sessionFactory.openSession();
	    boolean isDuplicate = false;
	    String hql = null;
	    Query query = null;
	    try {
	        if (!latitude.equals("Not Available")) {
	            hql = "FROM PreYatraGramsabha WHERE gramsabhaPhoto2 = :photoName AND gramsabhaPhoto2Latitude = :latitude AND gramsabhaPhoto2Longitude = :longitude";
	            query = session.createQuery(hql);
	            query.setParameter("photoName", photoName);
	            query.setParameter("latitude", latitude);
	            query.setParameter("longitude", longitude);
	        } else {
	            hql = "FROM PreYatraGramsabha WHERE gramsabhaPhoto2 = :photoName";
	            query = session.createQuery(hql);
	            query.setParameter("photoName", photoName);	
	        }

	        List results = query.list(); // Get all results
	        isDuplicate = !results.isEmpty(); // Check if there are any results
	    } finally {
	        session.close();
	    }

	    return isDuplicate;
	}

	private boolean isDuplicatePPPhoto1(String photoName, String latitude, String longitude) {
	    Session session = sessionFactory.openSession();
	    boolean isDuplicate = false;
	    String hql = null;
	    Query query = null;
	    try {
	        if (!latitude.equals("Not Available")) {
	            hql = "FROM PreYatraPrabhatpheri WHERE prabhatpheriPhoto1 = :photoName AND prabhatpheriPhoto1Latitude = :latitude AND prabhatpheriPhoto1Longitude = :longitude";
	            query = session.createQuery(hql);
	            query.setParameter("photoName", photoName);
	            query.setParameter("latitude", latitude);
	            query.setParameter("longitude", longitude);
	        } else {
	            hql = "FROM PreYatraPrabhatpheri WHERE prabhatpheriPhoto1 = :photoName";
	            query = session.createQuery(hql);
	            query.setParameter("photoName", photoName);
	        }

	        List results = query.list(); // Get all results
	        isDuplicate = !results.isEmpty(); // Check if there are any results
	    } finally {
	        session.close();
	    }

	    return isDuplicate;
	}

	private boolean isDuplicatePPPhoto2(String photoName, String latitude, String longitude) {
	    Session session = sessionFactory.openSession();
	    boolean isDuplicate = false;
	    String hql = null;
	    Query query = null;
	    try {
	        if (!latitude.equals("Not Available")) {
	        	hql = "FROM PreYatraPrabhatpheri WHERE prabhatpheriPhoto2 = :photoName AND prabhatpheriPhoto2Latitude = :latitude AND prabhatpheriPhoto2Longitude = :longitude";
	             query = session.createQuery(hql);
	            query.setParameter("photoName", photoName);
	            query.setParameter("latitude", latitude);
	            query.setParameter("longitude", longitude);
	        } else {
	            hql = "FROM PreYatraPrabhatpheri WHERE prabhatpheriPhoto2 = :photoName";
	            query = session.createQuery(hql);
	            query.setParameter("photoName", photoName);	
	        }

	        List results = query.list(); // Get all results
	        isDuplicate = !results.isEmpty(); // Check if there are any results
	    } finally {
	        session.close();
	    }

	    return isDuplicate;
	}
	private String saveFile(MultipartFile file, Integer id, String type, String directoryPath, boolean isDuplicate) {
	    if (file == null || file.isEmpty()) {
	        return null; // No file uploaded
	    }

	    String fileName = id+type+file.getOriginalFilename();
	    String filePath = directoryPath + fileName;

	    if (!isDuplicate) {
	        try {
	            // Ensure directory exists
	            File directory = new File(directoryPath);
	            if (!directory.exists()) {
	                directory.mkdirs();
	            }

	            // Save file to directory
	            Files.copy(file.getInputStream(), new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    return filePath; // Return file path for DB
	}

	@Override
	public List<PreYatraPreparationBean> getpreyatrasaveRecord(Integer stcd) {
		String getReport=getpreyatrarcd;
		Session session = sessionFactory.getCurrentSession();
		List<PreYatraPreparationBean> list = new ArrayList<PreYatraPreparationBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(PreYatraPreparationBean.class));
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
			//  session.getTransaction().commit();
		  }
		return list;
	}

	
	@Override
	public boolean checkgrampanchayat(Integer gramCode, String preyatraType) {
	    Integer value = 0;
	    Boolean status = false; // Default to false in case no results found
	    
	    try (Session session = sessionFactory.openSession()) {
	        Transaction tx = session.beginTransaction();
	        
	        String sql = checkgrampanchayat;
	        SQLQuery query = session.createSQLQuery(sql);
	        query.setInteger("gramCode", gramCode);
	        query.setParameter("preyatraType", preyatraType);
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
	public boolean checkVillageStatus(Integer vCode, String preyatraType) {
		 Integer value = 0;
		    Boolean status = false; // Default to false in case no results found
		    
		    try (Session session = sessionFactory.openSession()) {
		        Transaction tx = session.beginTransaction();
		        
		        String sql = checkVillagestatus;
		        SQLQuery query = session.createSQLQuery(sql);
		        query.setInteger("vCode", vCode);
		        query.setParameter("preyatraType", preyatraType);
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
}
