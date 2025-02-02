package app.watershedyatra.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.dao.WatershedYatraDao;
import app.watershedyatra.model.NodalOfficer;
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
	

}
