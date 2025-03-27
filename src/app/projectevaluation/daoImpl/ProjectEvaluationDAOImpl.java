package app.projectevaluation.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;
import app.model.outcome.FpoMain;
import app.model.project.WdcpmksyBaselineupdateAchievementDetail;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.projectevaluation.dao.ProjectEvaluationDAO;


import app.projectevaluation.model.MEvaluationIndicator;
import app.projectevaluation.model.WdcpmksyExecutionPlannedWork;
import app.projectevaluation.model.WdcpmksyMandaysDetails;
import app.projectevaluation.model.WdcpmksyProjectProfileEvaluation;
import app.projectevaluation.model.WdcpmksyQualityShapeFiles;
import app.projectevaluation.model.WdcpmksyStatusGeotaggWork;
import app.projectevaluation.model.FundUtilization;
import app.projectevaluation.model.IndicatorEvaluation;
import app.projectevaluation.model.WdcpmksyCroppedDetails1;
import app.projectevaluation.model.WdcpmksyCroppedDetails2;
import app.projectevaluation.model.WdcpmksyEquityAspect;
import app.projectevaluation.model.WdcpmksyEcologicalPerspective;
import app.projectevaluation.model.WdcpmksyProductionDetails;



@Repository("projectEvaluationDAO")
public class ProjectEvaluationDAOImpl implements ProjectEvaluationDAO{


	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getPEData}")
	String getPEData;
	
	@Value("${getMandayDeatails}")
	String getMandayDeatails;
	
	@Value("${getmonthYear}")
	String getmonthYear;
	@Value("${checkprojProfile}")
	String checkprojProfile;
	
	@Value("${fetchProjProfileData}")
	String fetchProjProfileData;
	@Value("${getExecutionPlanWork}")
	String getExecutionPlanWork;
	
	@Value("${getProjectProfileId}")
	String getProjectProfileId;
	
    @Value("${getFundUtilization}")
	String getFundUtilization;
	
	@Value("${getIndicatorEvaluation}")
	String getIndicatorEvaluation;
	
	@Value("${getQualityShapeFile}")
	String getQualityShapeFile;
	
	@Value("${getStatusGeotagWork}")
	String getStatusGeotagWork;
	
	@Value("${getcurrentFinYear}")
	String getcurrentFinYear;
	
	@Value("${getprojMonth}")
	String getprojMonth;
	
	@Value("${getprojstatus}")
	String getprojstatus;
	
	@Value("${getEcoPerspective}")
	String getEcoPerspective;

	@Value("${projByDCode}")
	String projByDCode;
	
	@Value("${getprojevorptDetails}")
	String getprojevorptDetails;
	
	@Value("${getProjDetailsData}")
	String getProjDetailsData;
	
	
	@Override
	public LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojProfileData(Integer dcode, Integer pcode) {
		LinkedHashMap<Integer, List<ProjectEvaluationBean>> map = new LinkedHashMap<Integer, List<ProjectEvaluationBean>>();
		String getRecort=getPEData;
		Session session = sessionFactory.getCurrentSession();
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getRecort);
			query.setInteger("dcode",dcode); 
			query.setInteger("pcode", pcode);
			query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
			list = query.list();
			List<ProjectEvaluationBean> sublist = new ArrayList<ProjectEvaluationBean>();
			if ((list != null) && (list.size() > 0)) {
				for (ProjectEvaluationBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<ProjectEvaluationBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
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
		return map;
	}

	@Override
	public List<ProjectEvaluationBean> getMandayDeatails(Integer profileid) {
		
		List<ProjectEvaluationBean> result=new ArrayList<ProjectEvaluationBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getMandayDeatails;
			  query = session.createSQLQuery(hql);
			  query.setInteger("profileid", profileid);
			  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
			  result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		return result;
	}

	
	
	
	@Override
	public String saveMandaysDetails(Integer profile_id, BigDecimal culturable_wasteland,
			Integer whs_constructed_rejuvenated, BigDecimal soil_moisture, BigDecimal protective_irrigation,
			BigDecimal degraded_rainfed, BigDecimal farmer_income, Integer farmer_benefited, BigDecimal dug_well,
			Integer fromno, Integer mandays_generated, BigDecimal tube_well, HttpSession session, Character areatype, 
			BigDecimal conculturable, Integer conwhs, BigDecimal consoil, BigDecimal conprotective, BigDecimal condegraded_rainfed, 
			BigDecimal confarmer_income, Integer confarmer_benefited, Integer conmandays, BigDecimal condug_well, BigDecimal contube_well) {
		
		Session sess = sessionFactory.getCurrentSession();
		int a=0;
		String res="fail";
		try {
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			sess.beginTransaction();
			
			SQLQuery chkdata=sess.createSQLQuery("select mandays_detail_id from wdcpmksy_mandays_details where project_profile_id=:work");
			chkdata.setInteger("work", profile_id);
			chkdata.list();
		//	Integer vcode = Integer.parseInt(chkdata.list().get(0).toString());
			if(chkdata.list().isEmpty()) {
				
				WdcpmksyProjectProfileEvaluation ppv= new WdcpmksyProjectProfileEvaluation();
				WdcpmksyMandaysDetails md= new WdcpmksyMandaysDetails();
				
				ppv.setProjectProfileId(profile_id);
				md.setWdcpmksyProjectProfileEvaluation(ppv);
			//	md.setProjectControlled(areatype);
				md.setCulturableWasteland(culturable_wasteland);
				md.setWhsConstructedRejuvenated(whs_constructed_rejuvenated);
				md.setSoilMoisture(soil_moisture);
				md.setProtectiveIrrigation(protective_irrigation);
				md.setDegradedRainfed(degraded_rainfed);
				md.setFarmerIncome(farmer_income);
				md.setFarmerBenefited(farmer_benefited);
				md.setMandaysGenerated(mandays_generated);
				md.setDugWell(dug_well);
				md.setTubeWell(tube_well);
				md.setCreatedBy(session.getAttribute("loginID").toString());
				md.setCreatedOn(new java.util.Date());
				md.setRequestIp(ipAddr);
				
				md.setControl_culturable_wasteland(conculturable);
				md.setControl_whs_constructed_rejuvenated(conwhs);
				md.setControl_soil_moisture(consoil);
				md.setControl_protective_irrigation(conprotective);
				md.setControl_degraded_rainfed(condegraded_rainfed);
				md.setControl_farmer_income(confarmer_income);
				md.setControl_farmer_benefited(confarmer_benefited);
				md.setControl_mandays_generated(conmandays);
				md.setControl_dug_well(condug_well);
				md.setControl_tube_well(contube_well);
				sess.save(md);
				
				SQLQuery sqlQuery = sess.createSQLQuery("update wdcpmksy_project_profile_evaluation set evaluation_id=:evid where project_profile_id=:profile");
				sqlQuery.setInteger("evid",fromno);
				sqlQuery.setInteger("profile", profile_id);
				a=sqlQuery.executeUpdate();
				sess.getTransaction().commit();
				res="success";
			}
			else {
				
				Integer mandaysid = Integer.parseInt(chkdata.list().get(0).toString());
				
				WdcpmksyMandaysDetails mdd = sess.load(WdcpmksyMandaysDetails.class, mandaysid);
			
				//mdd.setProjectControlled(areatype);
				mdd.setCulturableWasteland(culturable_wasteland);
				mdd.setWhsConstructedRejuvenated(whs_constructed_rejuvenated);
				mdd.setSoilMoisture(soil_moisture);
				mdd.setProtectiveIrrigation(protective_irrigation);
				mdd.setDegradedRainfed(degraded_rainfed);
				mdd.setFarmerIncome(farmer_income);
				mdd.setFarmerBenefited(farmer_benefited);
				mdd.setMandaysGenerated(mandays_generated);
				mdd.setDugWell(dug_well);
				mdd.setTubeWell(tube_well);
				mdd.setUpdatedOn(new java.util.Date());
				mdd.setRequestIp(ipAddr);
				
				mdd.setControl_culturable_wasteland(conculturable);
				mdd.setControl_whs_constructed_rejuvenated(conwhs);
				mdd.setControl_soil_moisture(consoil);
				mdd.setControl_protective_irrigation(conprotective);
				mdd.setControl_degraded_rainfed(condegraded_rainfed);
				mdd.setControl_farmer_income(confarmer_income);
				mdd.setControl_farmer_benefited(confarmer_benefited);
				mdd.setControl_mandays_generated(conmandays);
				mdd.setControl_dug_well(condug_well);
				mdd.setControl_tube_well(contube_well);
				
				
				sess.saveOrUpdate(mdd);
				sess.getTransaction().commit();
				res="success";
			}
			
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}


	@Override
	public String saveExecutionPlanWork(Integer profile_id, Integer created_work, String created_work_remark,
			Integer completed_work, String completed_work_remark, Integer ongoing_work, String ongoing_work_remark,
			Integer fromno, HttpSession session) {

		
		
		Session sess = sessionFactory.getCurrentSession();
		int a=0;
		String res="fail";
		try {
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			sess.beginTransaction();
			
			SQLQuery chkdata=sess.createSQLQuery("select planned_work_id from wdcpmksy_execution_planned_work where project_profile_id=:work");
			chkdata.setInteger("work", profile_id);
			chkdata.list();
		//	Integer vcode = Integer.parseInt(chkdata.list().get(0).toString());
			if(chkdata.list().isEmpty()) {
				
				WdcpmksyProjectProfileEvaluation ppv= new WdcpmksyProjectProfileEvaluation();
				WdcpmksyExecutionPlannedWork epw= new WdcpmksyExecutionPlannedWork();
				

				ppv.setProjectProfileId(profile_id);
				epw.setWdcpmksyProjectProfileEvaluation(ppv);
				epw.setCreatedWork(created_work);
				epw.setCreatedWorkRemark(created_work_remark);
				epw.setCompletedWork(completed_work);
				epw.setCompletedWorkRemark(completed_work_remark);
				epw.setOngoingWork(ongoing_work);
				epw.setOngoingWorkRemark(ongoing_work_remark);
				epw.setCreatedBy(session.getAttribute("loginID").toString());
				epw.setCreatedOn(new java.util.Date());
				epw.setRequestIp(ipAddr);
				sess.save(epw);
				
				SQLQuery sqlQuery = sess.createSQLQuery("update wdcpmksy_project_profile_evaluation set evaluation_id=:evid where project_profile_id=:profile");
				sqlQuery.setInteger("evid",fromno);
				sqlQuery.setInteger("profile", profile_id);
				a=sqlQuery.executeUpdate();
				sess.getTransaction().commit();
				res="success";
			}
			else {
				
				Integer pworksid = Integer.parseInt(chkdata.list().get(0).toString());
				
				WdcpmksyExecutionPlannedWork epw1 = sess.load(WdcpmksyExecutionPlannedWork.class, pworksid);
				
				epw1.setCreatedWork(created_work);
				epw1.setCreatedWorkRemark(created_work_remark);
				epw1.setCompletedWork(completed_work);
				epw1.setCompletedWorkRemark(completed_work_remark);
				epw1.setOngoingWork(ongoing_work);
				epw1.setOngoingWorkRemark(ongoing_work_remark);
				epw1.setUpdatedOn(new java.util.Date());
				epw1.setRequestIp(ipAddr);
				sess.saveOrUpdate(epw1);
				sess.getTransaction().commit();
				res="success";
			}
			
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public String saveQualityShapeFile(Integer profile_id, BigDecimal shape_file_area, String shape_file_area_remark,
			BigDecimal variation_area, String variation_area_remark, Integer fromno, HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		int a=0;
		String res="fail";
		try {
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			sess.beginTransaction();
			
			
			SQLQuery chkdata=sess.createSQLQuery("select quality_shape_id from wdcpmksy_quality_shape_files where project_profile_id=:work");
			chkdata.setInteger("work", profile_id);
			chkdata.list();
		//	Integer vcode = Integer.parseInt(chkdata.list().get(0).toString());
			if(chkdata.list().isEmpty()) {
				
				WdcpmksyProjectProfileEvaluation ppv= new WdcpmksyProjectProfileEvaluation();
				WdcpmksyQualityShapeFiles epw= new WdcpmksyQualityShapeFiles();
				
				ppv.setProjectProfileId(profile_id);
				epw.setWdcpmksyProjectProfileEvaluation(ppv);
				epw.setShapeFileArea(shape_file_area);
				epw.setShapeFileAreaRemark(shape_file_area_remark);
				epw.setVariationArea(variation_area);
				epw.setVariationAreaRemark(variation_area_remark);
				epw.setCreatedBy(session.getAttribute("loginID").toString());
				epw.setCreatedOn(new java.util.Date());
				epw.setRequestIp(ipAddr);
				sess.save(epw);
				
				SQLQuery sqlQuery = sess.createSQLQuery("update wdcpmksy_project_profile_evaluation set evaluation_id=:evid where project_profile_id=:profile");
				sqlQuery.setInteger("evid",fromno);
				sqlQuery.setInteger("profile", profile_id);
				a=sqlQuery.executeUpdate();
				sess.getTransaction().commit();

				res="success";
			}
			else {
				
				Integer shapeid = Integer.parseInt(chkdata.list().get(0).toString());
				
				WdcpmksyQualityShapeFiles epw1 = sess.load(WdcpmksyQualityShapeFiles.class, shapeid);
				
				epw1.setShapeFileArea(shape_file_area);
				epw1.setShapeFileAreaRemark(shape_file_area_remark);
				epw1.setVariationArea(variation_area);
				epw1.setVariationAreaRemark(variation_area_remark);
				epw1.setUpdatedOn(new java.util.Date());
				epw1.setRequestIp(ipAddr);
				sess.saveOrUpdate(epw1);
				sess.getTransaction().commit();

				res="success";

			}
			
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
		
	}

	@Override
	public String saveGeoTagDetails(Integer profile_id, Integer geo_tagg_work, String geo_tagg_work_remark,
			Integer fromno, HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		int a=0;
		String res="fail";
		try {
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			sess.beginTransaction();
			
			
			SQLQuery chkdata=sess.createSQLQuery("select geotagg_work_id from wdcpmksy_status_geotagg_work where project_profile_id=:work");
			chkdata.setInteger("work", profile_id);
			chkdata.list();
		//	Integer vcode = Integer.parseInt(chkdata.list().get(0).toString());
			if(chkdata.list().isEmpty()) {
				
				WdcpmksyProjectProfileEvaluation ppv= new WdcpmksyProjectProfileEvaluation();
				WdcpmksyStatusGeotaggWork epw= new WdcpmksyStatusGeotaggWork();
				
				ppv.setProjectProfileId(profile_id);
				epw.setWdcpmksyProjectProfileEvaluation(ppv);
				epw.setGeoTaggWork(geo_tagg_work);
				epw.setGeoTaggWorkRemark(geo_tagg_work_remark);
				epw.setCreatedBy(session.getAttribute("loginID").toString());
				epw.setCreatedOn(new java.util.Date());
				epw.setRequestIp(ipAddr);
				sess.save(epw);
				
				SQLQuery sqlQuery = sess.createSQLQuery("update wdcpmksy_project_profile_evaluation set evaluation_id=:evid where project_profile_id=:profile");
				sqlQuery.setInteger("evid",fromno);
				sqlQuery.setInteger("profile", profile_id);
				a=sqlQuery.executeUpdate();
				sess.getTransaction().commit();
				res="success";
			}
			else {
				
				Integer geoTagid = Integer.parseInt(chkdata.list().get(0).toString());
				
				WdcpmksyStatusGeotaggWork epw1 = sess.load(WdcpmksyStatusGeotaggWork.class, geoTagid);
				
				epw1.setGeoTaggWork(geo_tagg_work);
				epw1.setGeoTaggWorkRemark(geo_tagg_work_remark);
				epw1.setUpdatedOn(new java.util.Date());
				epw1.setRequestIp(ipAddr);
				sess.saveOrUpdate(epw1);
				sess.getTransaction().commit();
				res="update";
			}
			
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}


	private Boolean parseBoolean(String value) {
	    if (value == null || value.isEmpty()) {
	        return null;
	    }
	    return Boolean.parseBoolean(value);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<ProjectEvaluationBean> getFundUtilization(Integer profileid) {
		
		List<ProjectEvaluationBean> result=new ArrayList<ProjectEvaluationBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getFundUtilization;
			  query = session.createSQLQuery(hql);
			  query.setInteger("profileid", profileid);
			  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
			  result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
		}
		return result;
	}	
		
	@SuppressWarnings("deprecation")
	@Override
	public String saveFundUtilization(Integer projectProfileId, BigDecimal preCentralShare, BigDecimal midCentralShare, String rmkCentralShare, 
			BigDecimal preStateShare, BigDecimal midStateShare, String rmkStatelShare, BigDecimal preTotalFund, BigDecimal midTotalFund, String rmkTotalFund, 
			BigDecimal preConPlannedFund, BigDecimal midConPlannedFund, String rmkConPlannedFund, BigDecimal preExCon, BigDecimal midExCon, String rmkExCon, HttpSession session, Integer fromno) {
		Session sess = sessionFactory.getCurrentSession();
		//System.out.println("projectProfileId:" + projectProfileId);
		String res = "fail";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			
			sess.beginTransaction();
			
			@SuppressWarnings("rawtypes")
			Query chkdata=sess.createQuery("from FundUtilization where wdcpmksyProjectProfileEvaluation.projectProfileId=:work");
			chkdata.setParameter("work", projectProfileId);

			@SuppressWarnings("unchecked")
			List<FundUtilization> resultList = chkdata.getResultList();
			
			MEvaluationIndicator ei = sess.load(MEvaluationIndicator.class, fromno);
			
			WdcpmksyProjectProfileEvaluation main = sess.load(WdcpmksyProjectProfileEvaluation.class, projectProfileId);
			if(main == null) {
				main = new WdcpmksyProjectProfileEvaluation();
			}

			
			//main.setProjectProfileId(projectProfileId);
			
			FundUtilization fundUtilization;
	        if (chkdata.getResultList().isEmpty()) {
	            // If no data exists, create a new FundUtilization object
	        	main.setmEvaluationIndicator(ei);
	            fundUtilization = new FundUtilization();
	            fundUtilization.setWdcpmksyProjectProfileEvaluation(main);
	            fundUtilization.setCreatedBy(session.getAttribute("loginID").toString());
	            fundUtilization.setCreatedOn(new java.util.Date());
	            res = "success";
//	            sess.update(main);
	        } else {
	            // If data exists, update the existing FundUtilization object
	            fundUtilization = resultList.get(0);
	            fundUtilization.setUpdatedOn(new java.util.Date());
	            res = "success";
	        }
						
			fundUtilization.setCentralSharePrestatus(preCentralShare);
			fundUtilization.setCentralShareMidstatus(midCentralShare);
			fundUtilization.setCentralShareRemark(rmkCentralShare);
			fundUtilization.setStateSharePrestatus(preStateShare);
			fundUtilization.setStateShareMidstatus(midStateShare);
			fundUtilization.setStateShareRemark(rmkStatelShare);
			fundUtilization.setTotalFundPrestatus(preTotalFund);
			fundUtilization.setTotalFundMidstatus(midTotalFund);
			fundUtilization.setTotalFundRemark(rmkTotalFund);
			fundUtilization.setTotalFundPlannedPrestatus(preConPlannedFund);
			fundUtilization.setTotalFundPlannedMidstatus(midConPlannedFund);
			fundUtilization.setTotalFundPlannedRemark(rmkConPlannedFund);
			fundUtilization.setTotalExpenditurePrestatus(preExCon);
			fundUtilization.setTotalExpenditureMidstatus(midExCon);
			fundUtilization.setTotalExpenditureRemark(rmkExCon);
			fundUtilization.setRequestIp(ipAddr);
			
			sess.saveOrUpdate(fundUtilization);		
//			sess.update(main);
			sess.getTransaction().commit();
	
		} catch (Exception ex) {
			ex.printStackTrace();
			sess.getTransaction().rollback();
		} 
		
		return res;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public List<WdcpmksyEquityAspect> getEquityAspect(Integer profileid) {
	    
	    Session session = sessionFactory.getCurrentSession();
	    Transaction tx = null;
	    
	    try {
	        tx = session.beginTransaction();
	        
	        String hql = "FROM WdcpmksyEquityAspect ea WHERE ea.wdcpmksyProjectProfileEvaluation.projectProfileId = :profileid";
	        Query<WdcpmksyEquityAspect> query = session.createQuery(hql, WdcpmksyEquityAspect.class);
	        query.setParameter("profileid", profileid);
	        List<WdcpmksyEquityAspect> result = query.getResultList();
	        
	        tx.commit();
	        return result;
	    } catch (HibernateException e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        throw e;
	    }
	}
	
	
	@SuppressWarnings("deprecation")
	public String saveEquityAspect(Integer projectProfileId, Boolean pWatershedCom, Boolean cWatershedCom, String rmkWatershedCom, Boolean pFpoShgVli, 
			Boolean cFpoShgVli, String rmkFpoShgVli, Boolean pLivelihood, Boolean cLivelihood, String rmkLivelihood, HttpSession session, Integer fromno) {
		Session sess = sessionFactory.getCurrentSession();
		//System.out.println("projectProfileId:" + projectProfileId);
		String res = "fail";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			
			sess.beginTransaction();
			
			@SuppressWarnings("rawtypes")
			Query chkdata=sess.createQuery("from WdcpmksyEquityAspect where wdcpmksyProjectProfileEvaluation.projectProfileId=:work");
			chkdata.setParameter("work", projectProfileId);

			@SuppressWarnings("unchecked")
			List<WdcpmksyEquityAspect> resultList = chkdata.getResultList();
			
			MEvaluationIndicator ei = sess.load(MEvaluationIndicator.class, fromno);
			
			WdcpmksyProjectProfileEvaluation main = sess.load(WdcpmksyProjectProfileEvaluation.class, projectProfileId);
			if(main == null) {
				main = new WdcpmksyProjectProfileEvaluation();
			}

			
			WdcpmksyEquityAspect equityAspect;
	        if (chkdata.getResultList().isEmpty()) {
	            // If no data exists, create a new WdcpmksyEquityAspect object
	        	main.setmEvaluationIndicator(ei);
	        	equityAspect = new WdcpmksyEquityAspect();
	        	equityAspect.setWdcpmksyProjectProfileEvaluation(main);
	        	equityAspect.setCreatedBy(session.getAttribute("loginID").toString());
	        	equityAspect.setCreatedOn(new java.util.Date());
	            res = "success";
//	            sess.update(main);
	        } else {
	            // If data exists, update the existing WdcpmksyEquityAspect object
	        	equityAspect = resultList.get(0);
	        	equityAspect.setUpdatedOn(new java.util.Date());
	            res = "success";
	        }
						
	        equityAspect.setWaterCommittee(pWatershedCom);
	        equityAspect.setControlWaterCommittee(cWatershedCom);
	        equityAspect.setWaterCommitteeRemark(rmkWatershedCom);
	        equityAspect.setFpoShgVli(pFpoShgVli);
	        equityAspect.setControlFpoShgVli(cFpoShgVli);
	        equityAspect.setFpoShgVliRemark(rmkFpoShgVli);
	        equityAspect.setLivelihoodOption(pLivelihood);
	        equityAspect.setControlLivelihoodOption(cLivelihood);
	        equityAspect.setLivelihoodOptionRemark(rmkLivelihood);
	        equityAspect.setRequestIp(ipAddr);
			
			sess.saveOrUpdate(equityAspect);		
			sess.getTransaction().commit();
	
		} catch (Exception ex) {
			ex.printStackTrace();
			sess.getTransaction().rollback();
		} 
		
		return res;
	}
	
	
	@Override
	public List<ProjectEvaluationBean> monthYear() {
		List<ProjectEvaluationBean>result = new ArrayList<ProjectEvaluationBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction();
			  hql = getmonthYear;
			  query = session.createSQLQuery(hql);
			  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
	          result = query.list();
		}
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		finally {
			session.getTransaction().commit();
			
		}
		return result;
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
	public String insertprojectProfile(Integer projid, Integer fcode, Integer mcode, Integer evaId,
	        BigDecimal sanctionedC, BigDecimal cShare, BigDecimal sShare, BigDecimal sancitonedP, Integer villageC,
	        Integer waterC, Integer membersWC, Integer householdsC, HttpSession session, HttpServletRequest request) {

	    Session sess = sessionFactory.getCurrentSession();
	    String res = "fail";
	    Transaction transaction = null;

	    try {
	        transaction = sess.beginTransaction();

	        // Check if the project profile already exists
	        SQLQuery chkdata = sess.createSQLQuery("SELECT project_profile_id FROM wdcpmksy_project_profile_evaluation WHERE proj_id = :projid AND status='D'");
	        chkdata.setInteger("projid", projid);
	        List<?> resultList = chkdata.list();

	        if (resultList.isEmpty()) {
	            // Creating a new project profile evaluation entry
	            WdcpmksyProjectProfileEvaluation eva = new WdcpmksyProjectProfileEvaluation();
	            IwmpMProject proj = new IwmpMProject();
	            IwmpMFinYear fin = new IwmpMFinYear();
	            IwmpMMonth month = new IwmpMMonth();
	            MEvaluationIndicator eval = sess.get(MEvaluationIndicator.class, evaId);  // Use 'get' instead of 'load' to avoid exceptions

	            proj.setProjectId(projid);
	            fin.setFinYrCd(fcode);
	            month.setMonthId(mcode);

	            eva.setIwmpMProject(proj);
	            eva.setIwmpMFinYear(fin);
	            eva.setIwmpMMonth(month);
	            eva.setmEvaluationIndicator(eval);

	            eva.setProjectCost(sanctionedC);
	            eva.setCentralShare(cShare);
	            eva.setStateShare(sShare);
	            eva.setProjectArea(sancitonedP);
	            eva.setVillageCovered(villageC);
	            eva.setWatershedCommittee(waterC);
	            eva.setMemberWatershedCommittee(membersWC);
	            eva.setHousehold(householdsC);
	            eva.setRequestIp(getClientIpAddr(request));
	            eva.setCreatedOn(new java.util.Date());
	            eva.setUpdatedOn(new java.util.Date());
	            eva.setCreatedBy(session.getAttribute("loginID").toString());
	            eva.setStatus('D');  // Set status as 'D'

	            sess.saveOrUpdate(eva);  // Use saveOrUpdate to save the new entity
	        } else {
	            // Updating an existing project profile evaluation entry
	            Integer projpid = Integer.parseInt(resultList.get(0).toString());
	            WdcpmksyProjectProfileEvaluation ieval1 = sess.load(WdcpmksyProjectProfileEvaluation.class, projpid);  // Use 'get'

	            ieval1.setProjectCost(sanctionedC);
	            ieval1.setCentralShare(cShare);
	            ieval1.setStateShare(sShare);
	            ieval1.setProjectArea(sancitonedP);
	            ieval1.setVillageCovered(villageC);
	            ieval1.setWatershedCommittee(waterC);
	            ieval1.setMemberWatershedCommittee(membersWC);
	            ieval1.setHousehold(householdsC);
	            ieval1.setRequestIp(getClientIpAddr(request));
	            ieval1.setUpdatedOn(new java.util.Date());

	            sess.saveOrUpdate(ieval1);  // Use saveOrUpdate for existing entity
	        }

	        transaction.commit();  // Commit transaction after success
	        res = "success";
	    } catch (HibernateException e) {
	        if (transaction != null) {
	            transaction.rollback();  // Rollback transaction on error
	        }
	    }

	    return res;
	}

	
	@Override
	public List<WdcpmksyCroppedDetails1> getCroppedDetails(Integer projProfId) {
		List<WdcpmksyCroppedDetails1> list = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from WdcpmksyCroppedDetails1 where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
			query.setInteger("projProfId", projProfId);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}
	

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public String saveOrUpdateCroppedDetails(HttpServletRequest request, HttpSession sess, Integer projProfId, BigDecimal kharifCrop,BigDecimal rabiCrop, BigDecimal thirdCrop, BigDecimal cereals,BigDecimal pulses, BigDecimal oilSeed, BigDecimal millets,
			BigDecimal others, BigDecimal horticulture,BigDecimal netSown,BigDecimal cropIntensity, BigDecimal diversifiedCrop, BigDecimal ckharifCrop, BigDecimal crabiCrop, BigDecimal cthirdCrop, BigDecimal ccereals, BigDecimal cpulses,
			BigDecimal coilSeed, BigDecimal cmillets, BigDecimal cothers, BigDecimal chorticulture, BigDecimal cnetSown,
			BigDecimal ccropIntensity, BigDecimal cdiversifiedCrop) {
		Session session = sessionFactory.getCurrentSession();
		List<WdcpmksyCroppedDetails1> list = new ArrayList<>();
		String res = "fail";
		try {
			String userId = sess.getAttribute("loginID").toString();
			
			session.beginTransaction();
			Query query = session.createQuery("from WdcpmksyCroppedDetails1 where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
			query.setInteger("projProfId", projProfId);
			list = query.list();
			MEvaluationIndicator mEval = session.load(MEvaluationIndicator.class,4);
			WdcpmksyProjectProfileEvaluation projProEval = session.load(WdcpmksyProjectProfileEvaluation.class, projProfId);
			

			WdcpmksyCroppedDetails1 crpDtl;
			
			if(list.size()>0) {
				crpDtl = session.load(WdcpmksyCroppedDetails1.class,list.get(0).getCroppedDetails1Id());
				crpDtl.setUpdatedOn(new Date());
			}else {
				crpDtl = new WdcpmksyCroppedDetails1();
				projProEval.setmEvaluationIndicator(mEval);
				crpDtl.setWdcpmksyProjectProfileEvaluation(projProEval);
				crpDtl.setCreatedOn(new Date());
				crpDtl.setCreatedBy(userId);
				crpDtl.setRequestIp(getClientIpAddr(request));
			}
			crpDtl.setGrossKharifCropArea(kharifCrop);
			crpDtl.setGrossRabiCropArea(rabiCrop);
			crpDtl.setGrossThirdCropArea(thirdCrop);
			crpDtl.setDifferentCropCereals(cereals);
			crpDtl.setDifferentCropPulses(pulses);
			crpDtl.setDifferentCropOilSeed(oilSeed);
			crpDtl.setDifferentCropMillets(millets);
			crpDtl.setDifferentCropOther(others);
			crpDtl.setHorticultureArea(horticulture);
			crpDtl.setNetsownArea(netSown);
			crpDtl.setCroppingIntensity(cropIntensity);
			crpDtl.setDiversifiedChange(diversifiedCrop);
			crpDtl.setControl_gross_kharif_crop_area(ckharifCrop);
			crpDtl.setControl_gross_rabi_crop_area(crabiCrop);
			crpDtl.setControl_gross_third_crop_area(cthirdCrop);
			crpDtl.setControl_different_crop_cereals(ccereals);
			crpDtl.setControl_different_crop_pulses(cpulses);
			crpDtl.setControl_different_crop_oil_seed(coilSeed);
			crpDtl.setControl_different_crop_millets(cmillets);
			crpDtl.setControl_different_crop_other(cothers);
			crpDtl.setControl_horticulture_area(chorticulture);
			crpDtl.setControl_netsown_area(cnetSown);
			crpDtl.setControl_cropping_intensity(ccropIntensity);
			crpDtl.setControl_diversified_change(cdiversifiedCrop);
			session.saveOrUpdate(crpDtl);
			session.getTransaction().commit();
			res = "success";
			
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			res = "fail";
		}
		return res;
	}

	@Override
	public Integer getProjectProfileId(Integer projId, Integer finYrId, Integer monthId) {
		Integer proId = null;
		List<WdcpmksyProjectProfileEvaluation> list = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		String hql = getProjectProfileId;
		try {
			session.beginTransaction();
			
			Query query = session.createQuery(getProjectProfileId);
			query.setInteger("projId", projId);
			query.setInteger("finYrCd", finYrId);
			query.setInteger("monthId", monthId);
			list = query.list();
			session.getTransaction().commit();
			proId = list.get(0).getProjectProfileId();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return proId;
	}

	@Override
	public String saveIndicatorEvaluationDetails(Integer profile_id, Integer fromno, Integer wcdc, Integer wc,
			Integer pia, String admiMechanism, String admiMechanismRemark, String dprSlna, String dprSlnaRemark,
			String allManpower, String allManpowerRemark, String wcdcRemark, String piaRemark, String wcRemark,
			HttpSession session) {
		Session sess = sessionFactory.getCurrentSession();
		int a=0;
		String res="fail";
		try {
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			sess.beginTransaction();
			
			SQLQuery chkdata=sess.createSQLQuery("select indicator_evaluation_id from wdcpmksy_indicator_evaluation where project_profile_id=:work");
			chkdata.setInteger("work", profile_id);
			chkdata.list();
			if(chkdata.list().isEmpty()) {
				
				WdcpmksyProjectProfileEvaluation ppv= new WdcpmksyProjectProfileEvaluation();
				IndicatorEvaluation ieval= new IndicatorEvaluation();
				
				 Boolean dprSlnaBool = parseBoolean(dprSlna);
			     Boolean allManpowerBool = parseBoolean(allManpower);
			        
			        ppv.setProjectProfileId(profile_id);
			        ieval.setWdcpmksyProjectProfileEvaluation(ppv);
			        ieval.setAdminMechanism(admiMechanism);
			        ieval.setAdminMechanismRemark(admiMechanismRemark);
			        ieval.setDprSlna(dprSlnaBool);
			        ieval.setDprSlnaRemark(dprSlnaRemark);
			        ieval.setAllManpower(allManpowerBool);
			        ieval.setAllManpowerRemark(allManpowerRemark);
			        
			        if(wcdc==0) {
			        	ieval.setWcdc(null);}
			        else {
			        ieval.setWcdc(wcdc);
			        }
			        ieval.setWcdcRemark(wcdcRemark);
			        
			        if(pia==0) {
			        	ieval.setPia(null);}
			        else {
			        ieval.setPia(pia);
			        }
			        ieval.setPiaRemark(piaRemark);
			        
			        if(wc==0) {
			        	ieval.setWc(null);}
			        else {
			        ieval.setWc(wc);
			        }
			        
			        ieval.setWcRemark(wcRemark);
			        ieval.setRequestIp(ipAddr);
			        ieval.setCreatedOn(new java.util.Date());
			        ieval.setUpdatedOn(new java.util.Date());
			        ieval.setCreatedBy(session.getAttribute("loginID").toString());
			        
				sess.save(ieval);
				
				SQLQuery sqlQuery = sess.createSQLQuery("update wdcpmksy_project_profile_evaluation set evaluation_id=:evid where project_profile_id=:profile");
				sqlQuery.setInteger("evid",fromno);
				sqlQuery.setInteger("profile", profile_id);
				a=sqlQuery.executeUpdate();
				sess.getTransaction().commit();
				res="success";
			}
			else {
				
				Integer pevlid = Integer.parseInt(chkdata.list().get(0).toString());
				
				IndicatorEvaluation ieval1 = sess.load(IndicatorEvaluation.class, pevlid);
				
				Boolean dprSlnaBool = parseBoolean(dprSlna);
		        Boolean allManpowerBool = parseBoolean(allManpower);
//		        ieval.setWdcpmksyProjectProfileEvaluation(main);

                ieval1.setAdminMechanism(admiMechanism);
		        ieval1.setAdminMechanismRemark(admiMechanismRemark);
		        ieval1.setDprSlna(dprSlnaBool);
		        ieval1.setDprSlnaRemark(dprSlnaRemark);
		        ieval1.setAllManpower(allManpowerBool);
		        ieval1.setAllManpowerRemark(allManpowerRemark);
		        if(wcdc==0) {
		        	ieval1.setWcdc(null);}
		        else {
		        	ieval1.setWcdc(wcdc);
		        }
		        ieval1.setWcdcRemark(wcdcRemark);
		        if(pia==0) {
		        	ieval1.setPia(null);}
		        else {
		        	ieval1.setPia(pia);
		        }
		        ieval1.setPiaRemark(piaRemark);
		        if(wc==0) {
		        	ieval1.setWc(null);}
		        else {
		        	ieval1.setWc(wc);
		        }
		        ieval1.setWcRemark(wcRemark);
		        ieval1.setRequestIp(ipAddr);
		        ieval1.setUpdatedOn(new java.util.Date());
		        ieval1.setCreatedBy(session.getAttribute("loginID").toString());
				sess.saveOrUpdate(ieval1);
				sess.getTransaction().commit();
				res="success";
			}
			
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
		
	}

	@Override
	public List<ProjectEvaluationBean> getExecutionPlanWork(Integer profile_id) {
		
		List<ProjectEvaluationBean> result=new ArrayList<ProjectEvaluationBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getExecutionPlanWork;
			  query = session.createSQLQuery(hql);
			  query.setInteger("profileid", profile_id);
			  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
			  result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		return result;
	}

	@Override
	public List<ProjectEvaluationBean> getIndicatorEvaluation(Integer profile_id) {
		List<ProjectEvaluationBean> result=new ArrayList<ProjectEvaluationBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getIndicatorEvaluation;
			  query = session.createSQLQuery(hql);
			  query.setInteger("profileid", profile_id);
			  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
			  result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		return result;
	}
	
	@Override
	public String checkProjectProfileStatus(String project) {
	    if (project == null || project.isEmpty()) {
	        throw new IllegalArgumentException("Project ID cannot be null or empty");
	    }

	    String status = null;
	    Session session = sessionFactory.openSession();

	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();  // Start transaction
	        String savesql = checkprojProfile;

	        SQLQuery query = session.createSQLQuery(savesql);
	        query.setParameter("projid", Integer.parseInt(project));

	        Object result = query.uniqueResult(); // Get result

	        if (result != null) {
	            status = result.toString(); // Convert result to string
	        }

	        tx.commit();  // Commit transaction
	    } catch (Exception ex) {
	        if (tx != null) {
	            tx.rollback();  // Rollback transaction in case of error
	        }
	        throw ex;
	    } finally {
	        session.close();  // Close the session
	    }

	    return status;
	}


	@Override
	public LinkedHashMap<Integer, List<ProjectEvaluationBean>> fetchprojProfileData(Integer pcode) {
		LinkedHashMap<Integer, List<ProjectEvaluationBean>> map = new LinkedHashMap<Integer, List<ProjectEvaluationBean>>();
		String getRecort=fetchProjProfileData;
		Session session = sessionFactory.getCurrentSession();
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getRecort);
			query.setInteger("pcode", pcode);
			query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
			list = query.list();
			List<ProjectEvaluationBean> sublist = new ArrayList<ProjectEvaluationBean>();
			if ((list != null) && (list.size() > 0)) {
				for (ProjectEvaluationBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<ProjectEvaluationBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
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
		return map;
	}

	@Override
	public List<ProjectEvaluationBean> getQualityShapeFile(Integer profile_id) {
		
		List<ProjectEvaluationBean> result=new ArrayList<ProjectEvaluationBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getQualityShapeFile;
			  query = session.createSQLQuery(hql);
			  query.setInteger("profileid", profile_id);
			  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
			  result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		return result;
	}

	@Override
	public List<ProjectEvaluationBean> getStatusGeotagWork(Integer profile_id) {
		
		List<ProjectEvaluationBean> result=new ArrayList<ProjectEvaluationBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getStatusGeotagWork;
			  query = session.createSQLQuery(hql);
			  query.setInteger("profileid", profile_id);
			  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
			  result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			//System.err.print("Error"+ex.getMessage()+ex.getCause());
			ex.printStackTrace();
		}finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		return result;
	}
	@Override
	public LinkedHashMap<Integer, String> getCurrentFinYear() {
	    LinkedHashMap<Integer, String> finYList = new LinkedHashMap<>();
	    String hql = getcurrentFinYear;
	    
	    Session session = sessionFactory.getCurrentSession();
	    try {
	        session.beginTransaction();
	        Query<IwmpMFinYear> query = session.createQuery(hql, IwmpMFinYear.class);
	        List<IwmpMFinYear> finList = query.list();
	        
	        for (IwmpMFinYear finyear : finList) {
	        	finYList.put(finyear.getFinYrCd(), finyear.getFinYrDesc());
	        }
	        
	        session.getTransaction().commit();
	    } 
	    catch (HibernateException e) {
	        System.err.println("Hibernate error");
	        e.printStackTrace();
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	    } 
	    catch(Exception ex) {
	        ex.printStackTrace();
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	    } 

	    return finYList;
	}

	@Override
	public LinkedHashMap<Integer, String> getmonthforproject() {
		String getMonth=getprojMonth;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getMonth);
			
			List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  map.put(Integer.parseInt(row[0].toString()),row[1].toString());
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
		return map;
	}

	@Override
	public List<ProjectEvaluationBean> getprojectstatus(int project, int month) {
		List<ProjectEvaluationBean>result = new ArrayList<ProjectEvaluationBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction();
			  hql = getprojstatus;
			  query = session.createSQLQuery(hql);
			  query.setInteger("projid", project);
			  query.setInteger("month", month);
			  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
	          result = query.list();
		}
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		finally {
			session.getTransaction().commit();
			
		}
		return result;
	}

	@Override
	public String saveOrUpdateCroppedDetails2(HttpServletRequest request, HttpSession sess, Integer projProfId, BigDecimal niltosingle,
			BigDecimal sdcrop, BigDecimal plantation, BigDecimal rice, BigDecimal wheat, BigDecimal pulses,
			BigDecimal millets, BigDecimal oilseed, BigDecimal others, BigDecimal cniltosingle, BigDecimal csdcrop,
			BigDecimal cplantation, BigDecimal crice, BigDecimal cwheat, BigDecimal cpulses, BigDecimal cmillets,
			BigDecimal coilseed, BigDecimal cothers) {
		Session session = sessionFactory.getCurrentSession();
		List<WdcpmksyCroppedDetails2> list = new ArrayList<>();
		String res = "fail";
		try {
			String userId = sess.getAttribute("loginID").toString();
			
			session.beginTransaction();
			Query query = session.createQuery("from WdcpmksyCroppedDetails2 where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
			query.setInteger("projProfId", projProfId);
			list = query.list();
			MEvaluationIndicator mEval = session.load(MEvaluationIndicator.class,5);
			WdcpmksyProjectProfileEvaluation projProEval = session.load(WdcpmksyProjectProfileEvaluation.class, projProfId);
			

			WdcpmksyCroppedDetails2 crpDtl;
			
			if(list.size()>0) {
				crpDtl = session.load(WdcpmksyCroppedDetails2.class,list.get(0).getCroppedDetails2Id());
				crpDtl.setUpdatedOn(new Date());
			}else {
				crpDtl = new WdcpmksyCroppedDetails2();
				projProEval.setmEvaluationIndicator(mEval);
				crpDtl.setWdcpmksyProjectProfileEvaluation(projProEval);
				crpDtl.setCreatedOn(new Date());
				crpDtl.setCreatedBy(userId);
				crpDtl.setRequestIp(getClientIpAddr(request));
			}
			crpDtl.setNillSingle(niltosingle);
			crpDtl.setSingelDoublemore(sdcrop);
			crpDtl.setPlantationCover(plantation);
			crpDtl.setWheat(wheat);
			crpDtl.setRice(rice);
			crpDtl.setPulses(pulses);
			crpDtl.setOil_seed(oilseed);
			crpDtl.setMillets(millets);
			crpDtl.setOther(others);
			crpDtl.setControl_nill_single(cniltosingle);
			crpDtl.setControl_singel_doublemore(csdcrop);
			crpDtl.setControl_plantation_cover(cplantation);
			crpDtl.setControl_wheat(cwheat);
			crpDtl.setControl_rice(crice);
			crpDtl.setControl_pulses(cpulses);
			crpDtl.setControl_oil_seed(coilseed);
			crpDtl.setControl_millets(cmillets);
			crpDtl.setControl_other(cothers);
			session.saveOrUpdate(crpDtl);
			session.getTransaction().commit();
			res = "success";
			
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			res = "fail";
		}
		return res;
	}

	@Override
	public List<WdcpmksyCroppedDetails2> getCroppedDetails2(Integer projProfId) {
		List<WdcpmksyCroppedDetails2> list = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from WdcpmksyCroppedDetails2 where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
			query.setInteger("projProfId", projProfId);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}


	@Override
	public String saveEcoperspectiveDetails(Integer profile_id, String naturalresource, String naturalresourceRemark,
			String norm, String normRemark, String antrlasset, String antrlassetRemark, String controlntlresource,
			String controlnorm, Integer fromno, String controlantrlasset, HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		int a=0;
		String res="fail";
		try {
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			sess.beginTransaction();
			
			SQLQuery chkdata=sess.createSQLQuery("select ecological_perspective_id from wdcpmksy_ecological_perspective where project_profile_id=:work");
			chkdata.setInteger("work", profile_id);
			chkdata.list();
			if(chkdata.list().isEmpty()) {
				
				WdcpmksyProjectProfileEvaluation ppv= new WdcpmksyProjectProfileEvaluation();
				WdcpmksyEcologicalPerspective eco= new WdcpmksyEcologicalPerspective();
				
				 Boolean naturalresourceBool = parseBoolean(naturalresource);
			     Boolean normBool = parseBoolean(norm);
			     Boolean antrlassetBool = parseBoolean(antrlasset);
			     Boolean controlantrlassetBool = parseBoolean(controlantrlasset);
			     Boolean controlntlresourceBool = parseBoolean(controlntlresource);
			     Boolean controlnormBool = parseBoolean(controlnorm);
			        
			        ppv.setProjectProfileId(profile_id);
			        eco.setWdcpmksyProjectProfileEvaluation(ppv);
			        eco.setNaturalResource(naturalresourceBool);
			        eco.setNaturalResourceRemark(naturalresourceRemark);
			        eco.setNormsRelating(normBool);
			        eco.setNormsRelatingRemark(normRemark);
			        eco.setAnturalAsset(antrlassetBool);
			        eco.setAnturalAssetRemark(antrlassetRemark);
			        eco.setControl_antural_asset(controlantrlassetBool);
			        eco.setControl_norms_relating(controlnormBool);
			        eco.setControl_natural_resource(controlntlresourceBool);
			        eco.setRequestIp(ipAddr);
			        eco.setCreatedOn(new java.util.Date());
			        eco.setUpdatedOn(new java.util.Date());
			        eco.setCreatedBy(session.getAttribute("loginID").toString());
			        
				sess.save(eco);
				
				SQLQuery sqlQuery = sess.createSQLQuery("update wdcpmksy_project_profile_evaluation set evaluation_id=:evid where project_profile_id=:profile");
				sqlQuery.setInteger("evid",fromno);
				sqlQuery.setInteger("profile", profile_id);
				a=sqlQuery.executeUpdate();
				sess.getTransaction().commit();
				res="success";
			}
			else {
				
				Integer pevlid = Integer.parseInt(chkdata.list().get(0).toString());
				
				WdcpmksyEcologicalPerspective eco1 = sess.load(WdcpmksyEcologicalPerspective.class, pevlid);
				
				 Boolean naturalresourceBool = parseBoolean(naturalresource);
			     Boolean normBool = parseBoolean(norm);
			     Boolean antrlassetBool = parseBoolean(antrlasset);
			     Boolean controlantrlassetBool = parseBoolean(controlantrlasset);
			     Boolean controlntlresourceBool = parseBoolean(controlntlresource);
			     Boolean controlnormBool = parseBoolean(controlnorm);
			     
				    eco1.setNaturalResource(naturalresourceBool);
			        eco1.setNaturalResourceRemark(naturalresourceRemark);
			        eco1.setNormsRelating(normBool);
			        eco1.setNormsRelatingRemark(normRemark);
			        eco1.setAnturalAsset(antrlassetBool);
			        eco1.setAnturalAssetRemark(antrlassetRemark);
			        eco1.setControl_antural_asset(controlantrlassetBool);
			        eco1.setControl_norms_relating(controlnormBool);
			        eco1.setControl_natural_resource(controlntlresourceBool);
			        eco1.setRequestIp(ipAddr);
			        eco1.setCreatedOn(new java.util.Date());
			        eco1.setUpdatedOn(new java.util.Date());
			        eco1.setCreatedBy(session.getAttribute("loginID").toString());
				sess.saveOrUpdate(eco1);
				sess.getTransaction().commit();
				res="success";
			}
			
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
		
	}


		@Override
		public List<ProjectEvaluationBean> getEcoPerspective(Integer profile_id) {
			List<ProjectEvaluationBean> result=new ArrayList<ProjectEvaluationBean>();
			Session session = sessionFactory.openSession();
			try {
				String hql=null;
				SQLQuery query = null;
				
				  @SuppressWarnings("unused")
				  Transaction tx = session.beginTransaction(); 
				  hql=getEcoPerspective;
				  query = session.createSQLQuery(hql);
				  query.setInteger("profileid", profile_id);
				  query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
				  result = query.list();
			} 
			catch (HibernateException e) 
			{
				System.err.print("Hibernate error");
				e.printStackTrace();
			} 
			catch(Exception ex)
			{
				ex.printStackTrace();
			}finally {
				session.getTransaction().commit();
			}
			return result;
		}
		
		
		@Override
		public List<WdcpmksyProductionDetails> getProductionDetails(Integer projProfId) {
			List<WdcpmksyProductionDetails> list = new ArrayList<>();
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				
				Query query = session.createQuery("from WdcpmksyProductionDetails where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
				query.setInteger("projProfId", projProfId);
				list = query.list();
				session.getTransaction().commit();
			}catch(Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return list;
		}

		@Override
		public String saveOrUpdateProductionDetails(HttpServletRequest request, HttpSession sess, Integer projProfId,
				BigDecimal milch, BigDecimal fodder, Integer ruralUrban, Integer spring, Integer benefit, Integer shg,
				Integer fpo, Integer ug, Integer mshg, Integer mfpo, Integer mug, BigDecimal trunoverFpo,
				BigDecimal incomeFpo, BigDecimal annualIncomeShg, BigDecimal cmilch, BigDecimal cfodder,
				Integer cruralUrban, Integer cspring, Integer cbenefit, Integer cshg, Integer cfpo, Integer cug,
				Integer cmshg, Integer cmfpo, Integer cmug, BigDecimal ctrunoverFpo, BigDecimal cincomeFpo,
				BigDecimal cannualIncomeShg) {
			Session session = sessionFactory.getCurrentSession();
			List<WdcpmksyProductionDetails> list = new ArrayList<>();
			String res = "fail";
			try {
				String userId = sess.getAttribute("loginID").toString();
				
				session.beginTransaction();
				Query query = session.createQuery("from WdcpmksyProductionDetails where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
				query.setInteger("projProfId", projProfId);
				list = query.list();
				MEvaluationIndicator mEval = session.load(MEvaluationIndicator.class,7);
				WdcpmksyProjectProfileEvaluation projProEval = session.load(WdcpmksyProjectProfileEvaluation.class, projProfId);
				

				WdcpmksyProductionDetails prdDtl;
				
				if(list.size()>0) {
					prdDtl = session.load(WdcpmksyProductionDetails.class,list.get(0).getProductionDetailId());
					prdDtl.setUpdatedOn(new Date());
				}else {
					prdDtl = new WdcpmksyProductionDetails();
					projProEval.setmEvaluationIndicator(mEval);
					prdDtl.setWdcpmksyProjectProfileEvaluation(projProEval);
					prdDtl.setCreatedOn(new Date());
					prdDtl.setCreatedBy(userId);
					prdDtl.setRequestIp(getClientIpAddr(request));
				}
				prdDtl.setMilchCattle(milch);
				prdDtl.setFodderProduction(fodder);
				prdDtl.setRuralUrban(ruralUrban);
				prdDtl.setSpringRejuvenated(spring);
				prdDtl.setPersonBenefitte(benefit);
				prdDtl.setCommunityBasedShg(shg);
				prdDtl.setCommunityBasedFpo(fpo);
				prdDtl.setCommunityBasedUg(ug);
				prdDtl.setMemberBasedShg(mshg);
				prdDtl.setMemberBasedFpo(mfpo);
				prdDtl.setMemberBasedUg(mug);
				prdDtl.setTrunoverFpo(trunoverFpo);
				prdDtl.setIncomeFpo(incomeFpo);
				prdDtl.setAnnualIncomeShg(annualIncomeShg);
				
				prdDtl.setControl_milch_cattle(cmilch);
				prdDtl.setControl_fodder_production(cfodder);
				prdDtl.setControl_rural_urban(cmug);
				prdDtl.setControl_spring_rejuvenated(cspring);
				prdDtl.setControl_person_benefitte(cbenefit);
				prdDtl.setControl_community_based_shg(cmshg);
				prdDtl.setControl_community_based_fpo(cmfpo);
				prdDtl.setControl_community_based_ug(cug);
				prdDtl.setControl_member_based_shg(cmshg);
				prdDtl.setControl_member_based_fpo(cmfpo);
				prdDtl.setControl_member_based_ug(cug);
				prdDtl.setControl_trunover_fpo(ctrunoverFpo);
				prdDtl.setControl_income_fpo(cincomeFpo);
				prdDtl.setControl_annual_income_shg(cincomeFpo);
				
				session.saveOrUpdate(prdDtl);
				session.getTransaction().commit();
				res = "success";
				
			}catch(Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
				res = "fail";
			}
			return res;
		}

		@Override
		public String completeprojEvaldata(Integer projProfId) {
			try {
				Session session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				WdcpmksyProjectProfileEvaluation menu = (WdcpmksyProjectProfileEvaluation) session.load(WdcpmksyProjectProfileEvaluation.class, projProfId);
				if (null != menu) {
					menu.setStatus('C');
					menu.setUpdatedOn(new Date());
					session.update(menu);
				}
				session.getTransaction().commit();
				return "success";

			} catch (Exception ex) {

				return "fail";
			}
		}

		@Override
		public LinkedHashMap<Integer, String> getProjByDCode(Integer dCode) {
			String hql=projByDCode;
			LinkedHashMap<Integer, String>projectList = new LinkedHashMap<Integer, String>();
			
			try (Session session = sessionFactory.getCurrentSession()){
				session.beginTransaction();
				Query<Object[]>query = session.createQuery(hql);
				query.setParameter("dCode", dCode);
				List<Object[]>list = query.list();
				list.stream().forEach(result -> projectList.put((Integer) result[0], (String) result[1]));
				session.getTransaction().commit();
				
				
			} catch (HibernateException e) {
		        System.err.print("Hibernate error");
		        e.printStackTrace();
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
			return projectList;
		}

		@Override
		public List<WdcpmksyProjectProfileEvaluation> getprojectevorptdata(Integer pCode) {
		    List<WdcpmksyProjectProfileEvaluation> list = new ArrayList<>();
		    String hql = getprojevorptDetails;
		    Session session = null;

		    try {
		        session = sessionFactory.getCurrentSession(); 
		        session.beginTransaction();

		        Query<WdcpmksyProjectProfileEvaluation> query = session.createQuery(hql, WdcpmksyProjectProfileEvaluation.class);
		        query.setParameter("pCode", pCode);

		        list = query.getResultList();
		        session.getTransaction().commit(); 
		    } catch (Exception ex) {
		        ex.printStackTrace();

		        if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
		            session.getTransaction().rollback();
		        }
		    }
		    return list;
		}

		
		@Override
		public ProjectEvaluationBean getProjectDetails(int projId) {
			 String hql = getProjDetailsData;
			Session session = null;
			ProjectEvaluationBean project = null;
			try {
				session = sessionFactory.getCurrentSession(); 
				session.beginTransaction();
		        Query<ProjectEvaluationBean> query = session.createSQLQuery(hql)
		        .setParameter("projId", projId)
                .setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));

		        project = (ProjectEvaluationBean) query.uniqueResult();
		        
		        session.getTransaction().commit(); 
			}
			catch (Exception e) {
				e.printStackTrace();

		        if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
		            session.getTransaction().rollback();
		        }
		    }

			return project;
		}




}


