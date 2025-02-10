package app.watershedyatra.daoImpl;

import java.io.File;
import java.io.IOException;
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
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; 
			LocalDateTime localDateTime = LocalDateTime.parse(userfileup.getDatetime(), formatter); 
			Timestamp timestamp1 = Timestamp.valueOf(localDateTime); 
			
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/vanyatradoc/WatershedYatraVillage/";
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
			
			for (MultipartFile file : mfile) {
				
				commonFunction.uploadFileforLMS(file, filePath);
			
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
//				if(userfileup.getCulturalActivity()==4) {
//				main.setCulturalActivityOther(userfileup.getOtherActivity());
				//System.out.println("other"+ userfileup.getOtherActivity());
//				}
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
				
				main.setArExperiencePath1(filePath+userfileup.getArExperiencephoto1().getOriginalFilename());
				main.setArExperiencePath2(filePath+userfileup.getArExperiencephoto2().getOriginalFilename());
				
				main.setBhumiJalSanrakshanPath1(!userfileup.getShapathYesphoto1().isEmpty() ? filePath + userfileup.getShapathYesphoto1().getOriginalFilename() :  null);
				main.setBhumiJalSanrakshanPath2(!userfileup.getShapathYesphoto2().isEmpty() ? filePath + userfileup.getShapathYesphoto2().getOriginalFilename() :  null);
				main.setYatraFilmPath1(!userfileup.getFilmYesphoto1().isEmpty() ? filePath + userfileup.getFilmYesphoto1().getOriginalFilename() :  null);
				main.setYatraFilmPath2(!userfileup.getFilmYesphoto2().isEmpty() ? filePath + userfileup.getFilmYesphoto2().getOriginalFilename() :  null);
				
				main.setShramdaanPath1(filePath+userfileup.getLocShramdaanpsphoto1().getOriginalFilename());
				main.setShramdaanPath2(filePath+userfileup.getLocShramdaanpsphoto2().getOriginalFilename());
//				main.setYatraFilmPath1(filePath+userfileup.getFilmYesphoto1().getOriginalFilename());
//				main.setYatraFilmPath2(filePath+userfileup.getFilmYesphoto2().getOriginalFilename());
				main.setQuizParticipantsPath1(filePath+userfileup.getQuizParticipantsphoto1().getOriginalFilename());
				main.setQuizParticipantsPath2(filePath+userfileup.getQuizParticipantsphoto2().getOriginalFilename());
				main.setCulturalActivityPath1(filePath+userfileup.getCulturalActivityphoto1().getOriginalFilename());
				main.setCulturalActivityPath2(filePath+userfileup.getCulturalActivityphoto2().getOriginalFilename());
				main.setBhoomiPoojanPath1(filePath+userfileup.getBhoomiCostphoto1().getOriginalFilename());
				main.setBhoomiPoojanPath2(filePath+userfileup.getBhoomiCostphoto2().getOriginalFilename());
				main.setLokarpanPath1(filePath+userfileup.getLokWorksphoto1().getOriginalFilename());
				main.setLokarpanPath2(filePath+userfileup.getLokWorksphoto2().getOriginalFilename());
				main.setPlantationPath1(filePath+userfileup.getPlantationAreaphoto1().getOriginalFilename());
				main.setPlantationPath2(filePath+userfileup.getPlantationAreaphoto2().getOriginalFilename());
				main.setAwardDistributionPath1(filePath+userfileup.getNoOfwatershedphoto1().getOriginalFilename());
				main.setAwardDistributionPath2(filePath+userfileup.getNoOfwatershedphoto2().getOriginalFilename());
//				main.setBhumiJalSanrakshanPath1(filePath+userfileup.getShapathYesphoto1().getOriginalFilename());
//				main.setBhumiJalSanrakshanPath2(filePath+userfileup.getShapathYesphoto2().getOriginalFilename());
				
				main.setStatus("C");
				main.setCreatedBy(session.getAttribute("loginID").toString());
				main.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
				main.setRequestedIp(ipAddr);
//				
				sess.save(main);
			
				sess.getTransaction().commit();
				res="success";
		
		}
		catch(Exception ex) 
		{
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
			imgList.add(list.get(0).getArExperiencePath1().substring(list.get(0).getArExperiencePath1().lastIndexOf("/")+1));
			imgList.add(list.get(0).getArExperiencePath2().substring(list.get(0).getArExperiencePath2().lastIndexOf("/")+1));
			if(list.get(0).getBhumiJalSanrakshanPath1()!=null)
				imgList.add(list.get(0).getBhumiJalSanrakshanPath1().substring(list.get(0).getBhumiJalSanrakshanPath1().lastIndexOf("/")+1));
			if(list.get(0).getBhumiJalSanrakshanPath2()!=null)
				imgList.add(list.get(0).getBhumiJalSanrakshanPath2().substring(list.get(0).getBhumiJalSanrakshanPath2().lastIndexOf("/")+1));
			if(list.get(0).getYatraFilmPath1()!=null)
				imgList.add(list.get(0).getYatraFilmPath1().substring(list.get(0).getYatraFilmPath1().lastIndexOf("/")+1));
			if(list.get(0).getYatraFilmPath2()!=null)
				imgList.add(list.get(0).getYatraFilmPath2().substring(list.get(0).getYatraFilmPath2().lastIndexOf("/")+1));
			
			imgList.add(list.get(0).getQuizParticipantsPath1().substring(list.get(0).getQuizParticipantsPath1().lastIndexOf("/")+1));
			imgList.add(list.get(0).getQuizParticipantsPath2().substring(list.get(0).getQuizParticipantsPath2().lastIndexOf("/")+1));
			imgList.add(list.get(0).getCulturalActivityPath1().substring(list.get(0).getCulturalActivityPath1().lastIndexOf("/")+1));
			imgList.add(list.get(0).getCulturalActivityPath2().substring(list.get(0).getCulturalActivityPath2().lastIndexOf("/")+1));
			imgList.add(list.get(0).getBhoomiPoojanPath1().substring(list.get(0).getBhoomiPoojanPath1().lastIndexOf("/")+1));
			imgList.add(list.get(0).getBhoomiPoojanPath2().substring(list.get(0).getBhoomiPoojanPath2().lastIndexOf("/")+1));
			
			
			imgList.add(list.get(0).getLokarpanPath1().substring(list.get(0).getLokarpanPath1().lastIndexOf("/")+1));
			imgList.add(list.get(0).getLokarpanPath2().substring(list.get(0).getLokarpanPath2().lastIndexOf("/")+1));
			imgList.add(list.get(0).getShramdaanPath1().substring(list.get(0).getShramdaanPath1().lastIndexOf("/")+1));
			imgList.add(list.get(0).getShramdaanPath2().substring(list.get(0).getShramdaanPath2().lastIndexOf("/")+1));
			imgList.add(list.get(0).getPlantationPath1().substring(list.get(0).getPlantationPath1().lastIndexOf("/")+1));
			imgList.add(list.get(0).getPlantationPath2().substring(list.get(0).getPlantationPath2().lastIndexOf("/")+1));
			imgList.add(list.get(0).getAwardDistributionPath1().substring(list.get(0).getAwardDistributionPath1().lastIndexOf("/")+1));
			imgList.add(list.get(0).getAwardDistributionPath2().substring(list.get(0).getAwardDistributionPath2().lastIndexOf("/")+1));
			
			//local
			imgList.add(list.get(0).getArExperiencePath1().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getArExperiencePath2().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getBhumiJalSanrakshanPath1()!=null)
			imgList.add(list.get(0).getBhumiJalSanrakshanPath1().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getBhumiJalSanrakshanPath2()!=null)
			imgList.add(list.get(0).getBhumiJalSanrakshanPath2().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getYatraFilmPath1()!=null)
			imgList.add(list.get(0).getYatraFilmPath1().replaceAll(".*\\\\", ""));
			
			if(list.get(0).getYatraFilmPath2()!=null)
			imgList.add(list.get(0).getYatraFilmPath2().replaceAll(".*\\\\", ""));
			
			imgList.add(list.get(0).getQuizParticipantsPath1().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getQuizParticipantsPath2().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getCulturalActivityPath1().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getCulturalActivityPath2().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getBhoomiPoojanPath1().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getBhoomiPoojanPath2().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getLokarpanPath1().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getLokarpanPath2().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getShramdaanPath1().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getShramdaanPath2().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getPlantationPath1().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getPlantationPath2().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getAwardDistributionPath1().replaceAll(".*\\\\", ""));
			imgList.add(list.get(0).getAwardDistributionPath2().replaceAll(".*\\\\", ""));
			
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

	        // Save Photo Files
	        boolean isDuplicatePhoto1 = isDuplicateGSPhoto1(preYatraPrep.getGramphoto1().getOriginalFilename(), preYatraPrep.getGramphoto1_lat(), preYatraPrep.getGramphoto1_lng());
	        String photo1Path = saveFile(preYatraPrep.getGramphoto1(), "D:\\preyatraprep/", isDuplicatePhoto1);
	        
	        
	        
	        boolean isDuplicatePhoto2 = isDuplicateGSPhoto2(preYatraPrep.getGramphoto2().getOriginalFilename(), preYatraPrep.getGramphoto2_lat(), preYatraPrep.getGramphoto2_lng());
	        String photo2Path = saveFile(preYatraPrep.getGramphoto2(), "D:\\preyatraprep/", isDuplicatePhoto2);
	        
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
            
	        boolean isDuplicatePhoto3 = isDuplicatePPPhoto1(preYatraPrep.getPheriphoto1().getOriginalFilename(), preYatraPrep.getPheriphoto1_lat(), preYatraPrep.getPheriphoto1_lng());
	        String photo3Path = saveFile(preYatraPrep.getPheriphoto1(), "D:\\preyatraprep/", isDuplicatePhoto3);
	        
	        boolean isDuplicatePhoto4 = isDuplicatePPPhoto2(preYatraPrep.getPheriphoto2().getOriginalFilename(), preYatraPrep.getPheriphoto2_lat(), preYatraPrep.getPheriphoto2_lng());
	        String photo4Path = saveFile(preYatraPrep.getPheriphoto2(), "D:\\preyatraprep/", isDuplicatePhoto4);
	        
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
	private String saveFile(MultipartFile file, String directoryPath, boolean isDuplicate) {
	    if (file == null || file.isEmpty()) {
	        return null; // No file uploaded
	    }

	    String fileName = file.getOriginalFilename();
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

}
