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
import java.util.Map;

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
import app.projectevaluation.bean.CroppedDetailsReportBean;
import app.projectevaluation.bean.ProductionDetailsBean;
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
import app.projectevaluation.model.WdcpmksyCroppedDetails3;
import app.projectevaluation.model.WdcpmksyEquityAspect;
import app.projectevaluation.model.WdcpmksyEcologicalPerspective;
import app.projectevaluation.model.WdcpmksyProductionDetails;



@Repository("projectEvaluationDAO")
public class ProjectEvaluationDAOImpl implements ProjectEvaluationDAO{


	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getPEData}")
	String getPEData;
	@Value("${getPlanWorkData}")
	String getPlanWorkData;
	
	@Value("${getGeoTaggingWorks}")
	String getGeoTaggingWorks;
	
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
	
	@Value("${fetchCompProjProfileData}")
	String fetchCompProjProfileData;

	@Value("${getProjectBlock}")
	String getProjectBlock;
	
	@Value("${getStateMidProjEvlData}")
	String getStateMidProjEvlDetails;
	
	@Value("${getDistMidProjEvlData}")
	String getDistMidProjEvlDetails;
	
	@Value("${getStateMidProjEvlCropData}")
	String getStateMidProjEvlCropData;
	
	@Value("${getDistMidProjEvlCropData}")
	String getDistMidProjEvlCropData;
	
	@Value("${getStateMidProjEvlWorkData}")
	String getStateMidProjEvlWorkData;
	
	@Value("${getDistMidProjEvlWorkData}")
	String getDistMidProjEvlWorkData;
	
	@Value("${getAverageAnnualIncome}")
	String getAverageAnnualIncome;
	
	@Value("${getCommunityBasedData}")
	String getCommunityBasedData;
	
	@Value("${getDistwiseAverageAnnualIncome}")
	String getDistwiseAverageAnnualIncome;
	
	@Value("${getDistwiseCommunityBasedData}")
	String getDistwiseCommunityBasedData;
	
	@Value("${getPreCropDetails}")
	String getPreCropDetails;
	
	@Value("${getMidCropDetails}")
	String getMidCropDetails;
	
	@Value("${getControlCropDetails}")
	String getControlCropDetails;
	
	@Value("${getDistWisePreCropDetails}")
	String getDistWisePreCropDetails;
	
	@Value("${getDistWiseMidCropDetails}")
	String getDistWiseMidCropDetails;
	
	@Value("${getDistWiseControlCropDetails}")
	String getDistWiseControlCropDetails;
	
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
	public String saveMandaysDetails(Integer profile_id, BigDecimal pre_farmer_income, BigDecimal mid_farmer_income,
			BigDecimal control_farmer_income, String remark_farmer_income, Integer farmer_benefited,
			Integer control_farmer_benefited, String remark_farmer_benefited, Integer mandays_generated,
			Integer control_mandays_generated, String remark_mandays_generated, BigDecimal pre_dug_well,
			BigDecimal mid_dug_well, BigDecimal control_dug_well, String remark_dug_well, BigDecimal pre_tube_well,
			BigDecimal mid_tube_well, BigDecimal control_tube_well, String remark_tube_well, Integer fromno,
			HttpSession session, Character area) {
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
				md.setPrefarmerIncome(pre_farmer_income);
				md.setMidfarmerIncome(mid_farmer_income);
				md.setControlfarmerincome(control_farmer_income);
				md.setRemarkFormerIncome(remark_farmer_income);
				md.setFarmerBenefited(farmer_benefited);
				md.setControl_farmer_benefited(control_farmer_benefited);
				md.setRemarkFormerBenefited(remark_farmer_benefited);
				md.setMandaysGenerated(mandays_generated);
				md.setControl_mandays_generated(control_mandays_generated);
				md.setRemarkmandaysGenerated(remark_mandays_generated);
				md.setPredugWell(pre_dug_well);
				md.setMiddugWell(mid_dug_well);
				md.setControl_dug_well(control_dug_well);
				md.setRemarkdugWell(remark_dug_well);
				md.setPretubeWell(pre_tube_well);
				md.setMidtubeWell(mid_tube_well);
				md.setControl_tube_well(control_tube_well);
				md.setRemarktubeWell(remark_tube_well);
				md.setCreatedBy(session.getAttribute("loginID").toString());
				md.setCreatedOn(new java.util.Date());
				md.setRequestIp(ipAddr);
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
			
				
				mdd.setPrefarmerIncome(pre_farmer_income);
				mdd.setMidfarmerIncome(mid_farmer_income);
				mdd.setControlfarmerincome(control_farmer_income);
				mdd.setRemarkFormerIncome(remark_farmer_income);
				mdd.setFarmerBenefited(farmer_benefited);
				mdd.setControl_farmer_benefited(control_farmer_benefited);
				mdd.setRemarkFormerBenefited(remark_farmer_benefited);
				mdd.setMandaysGenerated(mandays_generated);
				mdd.setControl_mandays_generated(control_mandays_generated);
				mdd.setRemarkmandaysGenerated(remark_mandays_generated);
				mdd.setPredugWell(pre_dug_well);
				mdd.setMiddugWell(mid_dug_well);
				mdd.setControl_dug_well(control_dug_well);
				mdd.setRemarkdugWell(remark_dug_well);
				mdd.setPretubeWell(pre_tube_well);
				mdd.setMidtubeWell(mid_tube_well);
				mdd.setControl_tube_well(control_tube_well);
				mdd.setRemarktubeWell(remark_tube_well);
				
				mdd.setUpdatedOn(new java.util.Date());
				mdd.setRequestIp(ipAddr);
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
	public List<ProjectEvaluationBean> getFundDetails(Integer pcode) {
	    List<ProjectEvaluationBean> fundDetails = new ArrayList<>();
	    Session session = sessionFactory.openSession();

	    try {
	        Transaction tx = session.beginTransaction();

	        String sql = "select central_share_amt as central, state_share_amt as state from iwmp_m_project where status='C' and proj_id=:pcode";

	        SQLQuery query = session.createSQLQuery(sql);
	        query.setInteger("pcode", pcode);
	        query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));

	        fundDetails = query.list();

	        tx.commit();
	    } catch (HibernateException e) {
	        System.err.println("Hibernate error");
	        e.printStackTrace();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        session.close();
	    }

	    return fundDetails;
	}
		
	@SuppressWarnings("deprecation")
	@Override
	public String saveFundUtilization(Integer projectProfileId, BigDecimal centralShare, String rmkCentralShare, BigDecimal stateShare, String rmkStatelShare, 
			BigDecimal totalFund, String rmkTotalFund, BigDecimal conPlannedFund, String rmkConPlannedFund, BigDecimal exCon, String rmkExCon, 
			BigDecimal wdc, String rmkWdc, HttpSession session, Integer fromno) {
		
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
						
			fundUtilization.setCentralShare(centralShare);
			fundUtilization.setCentralShareRemark(rmkCentralShare);
			fundUtilization.setStateShare(stateShare);
			fundUtilization.setStateShareRemark(rmkStatelShare);
			fundUtilization.setTotalFund(totalFund);
			fundUtilization.setTotalFundRemark(rmkTotalFund);
			fundUtilization.setTotalFundPlanned(conPlannedFund);
			fundUtilization.setTotalFundPlannedRemark(rmkConPlannedFund);
			fundUtilization.setTotalExpenditure(exCon);
			fundUtilization.setTotalExpenditureRemark(rmkExCon);
			fundUtilization.setTotalWdc(wdc);
			fundUtilization.setTotalWdcRemark(rmkWdc);
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
	        equityAspect.setControlWaterCommittee(null); // equityAspect.setControlWaterCommittee(cWatershedCom);
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
	public String saveOrUpdateCroppedDetails(HttpServletRequest request, HttpSession sess, Integer projProfId, BigDecimal prekharifCrop,BigDecimal prerabiCrop, BigDecimal prethirdCrop, BigDecimal precereals,BigDecimal prepulses, BigDecimal preoilSeed, BigDecimal premillets,
			BigDecimal preothers, BigDecimal prehorticulture,BigDecimal prenetSown, BigDecimal precropIntensity, BigDecimal midkharifCrop, BigDecimal midrabiCrop, BigDecimal midthirdCrop, BigDecimal midcereals,BigDecimal midpulses, BigDecimal midoilSeed, BigDecimal midmillets,
			BigDecimal midothers, BigDecimal midhorticulture,BigDecimal midnetSown, BigDecimal midcropIntensity, BigDecimal ckharifCrop, BigDecimal crabiCrop, BigDecimal cthirdCrop, BigDecimal ccereals, BigDecimal cpulses, BigDecimal coilSeed, BigDecimal cmillets, BigDecimal cothers, 
			BigDecimal chorticulture, BigDecimal cnetSown, BigDecimal ccropIntensity, String kharifCropremark, String rabiCropremark, String thirdCropremark, String cerealsremark, String pulsesremark, String oilSeedremark, String milletsremark, String othersremark, String horticultureremark, 
			String netSownremark, String cropIntensityremark, String othercrop) {
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
			crpDtl.setPregrossKharifCropArea(prekharifCrop);
			crpDtl.setPregrossRabiCropArea(prerabiCrop);
			crpDtl.setPregrossThirdCropArea(prethirdCrop);
			crpDtl.setPredifferentCropCereals(precereals);
			crpDtl.setPredifferentCropPulses(prepulses);
			crpDtl.setPredifferentCropOilSeed(preoilSeed);
			crpDtl.setPredifferentCropMillets(premillets);
			crpDtl.setPredifferentCropOther(preothers);
			crpDtl.setPrehorticultureArea(prehorticulture);
			crpDtl.setPrenetsownArea(prenetSown);
			crpDtl.setPrecroppingIntensity(precropIntensity);
			
			crpDtl.setMidgrossKharifCropArea(midkharifCrop);
			crpDtl.setMidgrossRabiCropArea(midrabiCrop);
			crpDtl.setMidgrossThirdCropArea(midthirdCrop);
			crpDtl.setMiddifferentCropCereals(midcereals);
			crpDtl.setMiddifferentCropPulses(midpulses);
			crpDtl.setMiddifferentCropOilSeed(midoilSeed);
			crpDtl.setMiddifferentCropMillets(midmillets);
			crpDtl.setMiddifferentCropOther(midothers);
			crpDtl.setMidhorticultureArea(midhorticulture);
			crpDtl.setMidnetsownArea(midnetSown);
			crpDtl.setMidcroppingIntensity(midcropIntensity);
			
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
			
			crpDtl.setKharifCropremark(kharifCropremark);
			crpDtl.setRabiCropremark(rabiCropremark);
			crpDtl.setThirdCropremark(thirdCropremark);
			crpDtl.setCerealsremark(cerealsremark);
			crpDtl.setPulsesremark(pulsesremark);
			crpDtl.setOilSeedremark(oilSeedremark);
			crpDtl.setMilletsremark(milletsremark);
			crpDtl.setOthersremark(othersremark);
			crpDtl.setHorticultureremark(horticultureremark);
			crpDtl.setNetSownremark(netSownremark);
			crpDtl.setCropIntensityremark(cropIntensityremark);
			crpDtl.setOthercrop(othercrop);
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
			Integer pia, String admiMechanism, String admiMechanismRemark, Character dprSlna, String dprSlnaRemark,
			Character allManpower, String allManpowerRemark, String wcdcRemark, String piaRemark, String wcRemark,
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
				
//				 Boolean dprSlnaBool = parseBoolean(dprSlna);
//			     Boolean allManpowerBool = parseBoolean(allManpower);
			        
			        ppv.setProjectProfileId(profile_id);
			        ieval.setWdcpmksyProjectProfileEvaluation(ppv);
			        ieval.setAdminMechanism(admiMechanism);
			        ieval.setAdminMechanismRemark(admiMechanismRemark);
			        ieval.setDprSlna(dprSlna);
			        ieval.setDprSlnaRemark(dprSlnaRemark);
			        ieval.setAllManpower(allManpower);
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
				
//				Boolean dprSlnaBool = parseBoolean(dprSlna);
//		        Boolean allManpowerBool = parseBoolean(allManpower);
//		        ieval.setWdcpmksyProjectProfileEvaluation(main);

                ieval1.setAdminMechanism(admiMechanism);
		        ieval1.setAdminMechanismRemark(admiMechanismRemark);
		        ieval1.setDprSlna(dprSlna);
		        ieval1.setDprSlnaRemark(dprSlnaRemark);
		        ieval1.setAllManpower(allManpower);
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
	public String saveOrUpdateCroppedDetails2(HttpServletRequest request, HttpSession sess, Integer profile_id, Integer projProfId,
			BigDecimal diversifiedcrops, BigDecimal niltosingle, BigDecimal sdcrop, Integer wHSConReju,
			BigDecimal soilandmoiscrops, BigDecimal degradedrainfed, BigDecimal cdiversifiedcrops,
			BigDecimal cniltosingle, BigDecimal csdcrop, Integer cWHSConReju, BigDecimal csoilandmoiscrops,
			BigDecimal cdegradedrainfed, String diversifiedcropsremark, String niltosingleremark, String sdcropremark,
			String WHSConRejuremark, String soilandmoiscropsremark, String degradedrainfedremark) {
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
			crpDtl.setProjectDiversifiedChange(diversifiedcrops);
			crpDtl.setProjectNillSingle(niltosingle);
			crpDtl.setProjectSingleDoublemore(sdcrop);
			crpDtl.setProjectWhsConstructedRejuvenated(wHSConReju);
			crpDtl.setProjectSoilMoisture(soilandmoiscrops);
			crpDtl.setProjectDegradedRainfed(degradedrainfed);
			crpDtl.setControlDiversifiedChange(cdiversifiedcrops);
			crpDtl.setControlNillSingle(cniltosingle);
			crpDtl.setControlSingleDoublemore(csdcrop);
			crpDtl.setControlWhsConstructedRejuvenated(cWHSConReju);
			crpDtl.setControlSoilMoisture(csoilandmoiscrops);
			crpDtl.setControlDegradedRainfed(cdegradedrainfed);
			crpDtl.setRemarkDiversifiedChange(diversifiedcropsremark);
			crpDtl.setRemarkNillSingle(niltosingleremark);
			crpDtl.setRemarkSingleDoublemore(sdcropremark);
			crpDtl.setRemarkWhsConstructedRejuvenated(WHSConRejuremark);
			crpDtl.setRemarkSoilMoisture(soilandmoiscropsremark);
			crpDtl.setRemarkDegradedRainfed(degradedrainfedremark);
			
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
		public String saveOrUpdateProductionDetails(HttpServletRequest request, HttpSession sess, Integer projProfId, BigDecimal preMilch, BigDecimal midMilch, 
				BigDecimal cMilch, String rmkMilch, BigDecimal preFodder, BigDecimal midFodder, BigDecimal cFodder, String rmkFodder, Integer preRuralUrban, 
				Integer midRuralUrban, Integer cRuralUrban, String rmkRuralUrban, Integer spring, Integer cSpring, String rmkSpring, Integer benefit, Integer cBenefit, 
				String rmkBenefit, Integer shg, Integer cShg, String rmkShg, Integer fpo, Integer cFpo, String rmkFpo, Integer ug, Integer cUg, String rmkUg, 
				Integer mShg, Integer cMshg, String rmkMshg, Integer mFpo, Integer cMfpo, String rmkMfpo, Integer mUg, Integer cMug, String rmkMug, 
				BigDecimal preTrunOverFpo, BigDecimal midTrunOverFpo, BigDecimal cTrunOverFpo, String rmkTrunOverFpo, BigDecimal preIncomeFpo, BigDecimal midIncomeFpo, 
				BigDecimal cIncomeFpo, String rmkIncomeFpo, BigDecimal preAnnualIncomeShg, BigDecimal midAnnualIncomeShg, 
				BigDecimal cAnnualIncomeShg, String rmkAnnualIncomeShg)
		{
			
			Session session = sessionFactory.getCurrentSession();
			List<WdcpmksyProductionDetails> list = new ArrayList<>();
			String res = "fail";
			try {
				String userId = sess.getAttribute("loginID").toString();
				InetAddress inet = InetAddress.getLocalHost();
				String ipAddr = inet.getHostAddress();
				session.beginTransaction();
				Query query = session.createQuery("from WdcpmksyProductionDetails where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
				query.setInteger("projProfId", projProfId);
				list = query.list();
				MEvaluationIndicator mEval = session.load(MEvaluationIndicator.class,8);
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
					prdDtl.setRequestIp(ipAddr);
//					prdDtl.setRequestIp(getClientIpAddr(request));
				}
				
				prdDtl.setPreMilchCattle(preMilch);
				prdDtl.setMidMilchCattle(midMilch);
				prdDtl.setControlMilchCattle(cMilch);
				prdDtl.setRemarkMilchCattle(rmkMilch);
				
				prdDtl.setPreFodderProduction(preFodder);
				prdDtl.setMidFodderProduction(midFodder);
				prdDtl.setControlFodderProduction(cFodder);
				prdDtl.setRemarkFodderProduction(rmkFodder);
				
				prdDtl.setPreRuralUrban(preRuralUrban);
				prdDtl.setMidRuralUrban(midRuralUrban);
				prdDtl.setControlRuralUrban(cRuralUrban);
				prdDtl.setRemarkRuralUrban(rmkRuralUrban);
				
				prdDtl.setSpringRejuvenated(spring);
				prdDtl.setControlSpringRejuvenated(cSpring);
				prdDtl.setRemarkSpringRejuvenated(rmkSpring);
				
				prdDtl.setPersonBenefitte(benefit);
				prdDtl.setControlPersonBenefitte(cBenefit);
				prdDtl.setRemarkPersonBenefitte(rmkBenefit);
				
				prdDtl.setCommunityBasedShg(shg);
				prdDtl.setControlCommunityBasedShg(cShg);
				prdDtl.setRemarkCommunityBasedShg(rmkShg);
				
				prdDtl.setCommunityBasedFpo(fpo);
				prdDtl.setControlCommunityBasedFpo(cFpo);
				prdDtl.setRemarkCommunityBasedFpo(rmkFpo);
				
				prdDtl.setCommunityBasedUg(ug);
				prdDtl.setControlCommunityBasedUg(cUg);
				prdDtl.setRemarkCommunityBasedUg(rmkUg);
				
				prdDtl.setMemberBasedShg(mShg);
				prdDtl.setControlMemberBasedShg(cMshg);
				prdDtl.setRemarkMemberBasedShg(rmkMshg);
				
				prdDtl.setMemberBasedFpo(mFpo);
				prdDtl.setControlMemberBasedFpo(cMfpo);
				prdDtl.setRemarkMemberBasedFpo(rmkMfpo);
				
				prdDtl.setMemberBasedUg(mUg);
				prdDtl.setControlMemberBasedUg(cMug);
				prdDtl.setRemarkMemberBasedUg(rmkMug);
				
				prdDtl.setPreTrunoverFpo(preTrunOverFpo);
				prdDtl.setMidTrunoverFpo(midTrunOverFpo);
				prdDtl.setControlTrunoverFpo(cTrunOverFpo);
				prdDtl.setRemarkTrunoverFpo(rmkTrunOverFpo);
				
				prdDtl.setPreIncomeFpo(preIncomeFpo);
				prdDtl.setMidIncomeFpo(midIncomeFpo);
				prdDtl.setControlIncomeFpo(cIncomeFpo);
				prdDtl.setRemarkIncomeFpo(rmkIncomeFpo);
				
				prdDtl.setPreAnnualIncomeShg(preAnnualIncomeShg);
				prdDtl.setMidAnnualIncomeShg(midAnnualIncomeShg);
				prdDtl.setControlAnnualIncomeShg(cAnnualIncomeShg);
				prdDtl.setRemarkAnnualIncomeShg(rmkAnnualIncomeShg);
				
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

		public String completeprojEvaldata(Integer projProfId, String summary, Character grade) {
			try {
				Session session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				WdcpmksyProjectProfileEvaluation menu = (WdcpmksyProjectProfileEvaluation) session.load(WdcpmksyProjectProfileEvaluation.class, projProfId);
				if (null != menu) {
					menu.setStatus('C');
					menu.setUpdatedOn(new Date());
					menu.setSummary(summary);
					menu.setGrade(grade);
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

		@Override
		public String updateProjProfileMonth(Integer projid, Integer monthid) {
		    Session session = sessionFactory.getCurrentSession();
		    String res = "fail";
		    try {
		        session.beginTransaction(); // ✅ Ensure transaction is started

		        Query<WdcpmksyProjectProfileEvaluation> query = session.createQuery(
		            "FROM WdcpmksyProjectProfileEvaluation w WHERE w.iwmpMProject.projectId = :projid",
		            WdcpmksyProjectProfileEvaluation.class
		        );
		        query.setParameter("projid", projid);
		        WdcpmksyProjectProfileEvaluation savedata = query.uniqueResult();

		        if (savedata != null) {
		            IwmpMMonth month = session.get(IwmpMMonth.class, monthid);
		            savedata.setIwmpMMonth(month);
		            session.update(savedata);
		            session.getTransaction().commit(); 
		            res = "success";
		        } else {
		            res = "not_found";
		            session.getTransaction().rollback(); 
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        res = "fail";
		        if (session.getTransaction().isActive()) {
		            session.getTransaction().rollback(); 
		        }
		    }
		    return res;
		}

		
		@Override
		public List<WdcpmksyCroppedDetails3> getCroppedDetails3(Integer projProfId) {
			List<WdcpmksyCroppedDetails3> list = new ArrayList<>();
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				
				Query query = session.createQuery("from WdcpmksyCroppedDetails3 where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
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
		public String saveOrUpdateCroppedDetails3(HttpServletRequest request, HttpSession sess, Integer projProfId,
				WdcpmksyCroppedDetails3 cropDetail3) {
			Session session = sessionFactory.getCurrentSession();
			List<WdcpmksyCroppedDetails3> list = new ArrayList<>();
			String res = "fail";
			try {
				String userId = sess.getAttribute("loginID").toString();
				
				session.beginTransaction();
				Query query = session.createQuery("from WdcpmksyCroppedDetails3 where wdcpmksyProjectProfileEvaluation.projectProfileId =:projProfId");
				query.setInteger("projProfId", projProfId);
				list = query.list();
				MEvaluationIndicator mEval = session.load(MEvaluationIndicator.class,6);
				WdcpmksyProjectProfileEvaluation projProEval = session.load(WdcpmksyProjectProfileEvaluation.class, projProfId);
				

				WdcpmksyCroppedDetails3 crpDtl;
				
				if(list.size()>0) {
					crpDtl = session.load(WdcpmksyCroppedDetails3.class,list.get(0).getCroppedDetails3Id());
					crpDtl.setUpdatedOn(new Date());
				}else {
					crpDtl = new WdcpmksyCroppedDetails3();
					projProEval.setmEvaluationIndicator(mEval);
					crpDtl.setWdcpmksyProjectProfileEvaluation(projProEval);
					crpDtl.setCreatedOn(new Date());
					crpDtl.setCreatedBy(userId);
					crpDtl.setRequestIp(getClientIpAddr(request));
				}
				crpDtl.setPrePlantationCover(cropDetail3.getPrePlantationCover());
				crpDtl.setPreRice(cropDetail3.getPreRice());
				crpDtl.setPreWheat(cropDetail3.getPreWheat());
				crpDtl.setPrePulses(cropDetail3.getPrePulses());
				crpDtl.setPreMillets(cropDetail3.getPreMillets());
				crpDtl.setPreOilSeed(cropDetail3.getPreOilSeed());
				crpDtl.setPreOther(cropDetail3.getPreOther());
				crpDtl.setPreCulturableWasteland(cropDetail3.getPreCulturableWasteland());
				crpDtl.setPreProtectiveIrrigation(cropDetail3.getPreProtectiveIrrigation());
				
				crpDtl.setMidPlantationCover(cropDetail3.getMidPlantationCover());
				crpDtl.setMidRice(cropDetail3.getMidRice());
				crpDtl.setMidWheat(cropDetail3.getMidWheat());
				crpDtl.setMidPulses(cropDetail3.getMidPulses());
				crpDtl.setMidMillets(cropDetail3.getMidMillets());
				crpDtl.setMidOilSeed(cropDetail3.getMidOilSeed());
				crpDtl.setMidOther(cropDetail3.getMidOther());
				crpDtl.setMidCulturableWasteland(cropDetail3.getMidCulturableWasteland());
				crpDtl.setMidProtectiveIrrigation(cropDetail3.getMidProtectiveIrrigation());
				
				crpDtl.setControlPlantationCover(cropDetail3.getControlPlantationCover());
				crpDtl.setControlRice(cropDetail3.getControlRice());
				crpDtl.setControlWheat(cropDetail3.getControlWheat());
				crpDtl.setControlPulses(cropDetail3.getControlPulses());
				crpDtl.setControlMillets(cropDetail3.getControlMillets());
				crpDtl.setControlOilSeed(cropDetail3.getControlOilSeed());
				crpDtl.setControlOther(cropDetail3.getControlOther());
				crpDtl.setControlCulturableWasteland(cropDetail3.getControlCulturableWasteland());
				crpDtl.setControlProtectiveIrrigation(cropDetail3.getControlProtectiveIrrigation());
				
				crpDtl.setRemarkPlantationCover(cropDetail3.getRemarkPlantationCover());
				crpDtl.setRemarkRice(cropDetail3.getRemarkRice());
				crpDtl.setRemarkWheat(cropDetail3.getRemarkWheat());
				crpDtl.setRemarkPulses(cropDetail3.getRemarkPulses());
				crpDtl.setRemarkMillets(cropDetail3.getRemarkMillets());
				crpDtl.setRemarkOilSeed(cropDetail3.getRemarkOilSeed());
				crpDtl.setRemarkOther(cropDetail3.getRemarkOther());
				crpDtl.setRemarkCulturableWasteland(cropDetail3.getRemarkCulturableWasteland());
				crpDtl.setRemarkProtectiveIrrigation(cropDetail3.getRemarkProtectiveIrrigation());
				
				crpDtl.setOthercrop(cropDetail3.getOthercrop());
				
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
		public LinkedHashMap<Integer, List<ProjectEvaluationBean>> fetchcompleteProjProfileData(Integer pcode) {
			LinkedHashMap<Integer, List<ProjectEvaluationBean>> map = new LinkedHashMap<Integer, List<ProjectEvaluationBean>>();
			String getRecort=fetchCompProjProfileData;
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
		public LinkedHashMap<Integer, List<ProjectEvaluationBean>> getPlanWorkData(Integer pcode) {
			
			LinkedHashMap<Integer, List<ProjectEvaluationBean>> map = new LinkedHashMap<Integer, List<ProjectEvaluationBean>>();
			String getRecort=getPlanWorkData;
			Session session = sessionFactory.getCurrentSession();
			List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
			try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getRecort);
//				query.setInteger("dcode",dcode); 
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
		public LinkedHashMap<Integer, List<ProjectEvaluationBean>> getGeoTaggingWorks(Integer pcode) {
			LinkedHashMap<Integer, List<ProjectEvaluationBean>> map = new LinkedHashMap<Integer, List<ProjectEvaluationBean>>();
			String getRecort=getGeoTaggingWorks;
			Session session = sessionFactory.getCurrentSession();
			List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
			try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getRecort);
//				query.setInteger("dcode",dcode); 
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
		public LinkedHashMap<Integer, String> getProjProfileBlock(Integer pcode) {
			String getProject=getProjectBlock;
			Session session = sessionFactory.getCurrentSession();
			LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
			try {
				session.beginTransaction();
				SQLQuery query= session.createSQLQuery(getProject);
				query.setInteger("pcode",pcode);
				
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
		public List<ProjectEvaluationBean> getStateMidProjEvoluation() {
			
			List<ProjectEvaluationBean> getStateMidProjEvoluation = new ArrayList<ProjectEvaluationBean>();
			String hql = getStateMidProjEvlDetails;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
				getStateMidProjEvoluation = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
				
			return getStateMidProjEvoluation;
		}
		
		@Override
		public List<ProjectEvaluationBean> getDistMidProjEvoluation(Integer stcd) {
			
			List<ProjectEvaluationBean> getDistMidProjEvoluation = new ArrayList<ProjectEvaluationBean>();
			String hql = getDistMidProjEvlDetails;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setInteger("stcd", stcd);
				query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
				getDistMidProjEvoluation = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
				
			return getDistMidProjEvoluation;
		}

		@Override
		public List<ProjectEvaluationBean> getStateMidProjEvlCropDetails() {
			
			List<ProjectEvaluationBean> getStateMidProjEvlCropDetails = new ArrayList<ProjectEvaluationBean>();
			String hql = getStateMidProjEvlCropData;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
				getStateMidProjEvlCropDetails = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
				
			return getStateMidProjEvlCropDetails;
		}
		
		@Override
		public List<ProjectEvaluationBean> getDistMidProjEvlCropDetails(Integer stcd) {
			
			List<ProjectEvaluationBean> getDistMidProjEvlCropDetails = new ArrayList<ProjectEvaluationBean>();
			String hql = getDistMidProjEvlCropData;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setInteger("stcd", stcd);
				query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
				getDistMidProjEvlCropDetails = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
				
			return getDistMidProjEvlCropDetails;
		}
		
		@Override
		public List<ProjectEvaluationBean> getStateMidProjEvlWorkDetails() {
			
			List<ProjectEvaluationBean> getStateMidProjEvlWorkDetails = new ArrayList<ProjectEvaluationBean>();
			String hql = getStateMidProjEvlWorkData;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
				getStateMidProjEvlWorkDetails = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
				
			return getStateMidProjEvlWorkDetails;
		}
		
		@Override
		public List<ProjectEvaluationBean> getDistMidProjEvlWorkDetails(Integer stcd) {
			
			List<ProjectEvaluationBean> getDistMidProjEvlWorkDetails = new ArrayList<ProjectEvaluationBean>();
			String hql = getDistMidProjEvlWorkData;
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setInteger("stcd", stcd);
				query.setResultTransformer(Transformers.aliasToBean(ProjectEvaluationBean.class));
				getDistMidProjEvlWorkDetails = query.list();
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
				
			return getDistMidProjEvlWorkDetails;
		}

		@Override
		public Map<String, List<CroppedDetailsReportBean>> getCroppedDetailsReportData() {
			Map<String, List<CroppedDetailsReportBean>> map = new LinkedHashMap<>();
			String prehql = getPreCropDetails;
			String midhql = getMidCropDetails;
			String controlhql = getControlCropDetails;
			List<CroppedDetailsReportBean> list = new ArrayList<>();
			
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(prehql);
				query.setResultTransformer(Transformers.aliasToBean(CroppedDetailsReportBean.class));
				list = query.list();
				map.put("pre", list);
				
				list = new ArrayList<>();
				query = session.createSQLQuery(midhql);
				query.setResultTransformer(Transformers.aliasToBean(CroppedDetailsReportBean.class));
				list = query.list();
				map.put("mid", list);
				
				list = new ArrayList<>();
				query = session.createSQLQuery(controlhql);
				query.setResultTransformer(Transformers.aliasToBean(CroppedDetailsReportBean.class));
				list = query.list();
				map.put("control", list);
				
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return map;
		}

		@Override
		public List<ProductionDetailsBean> getAverageAnnualIncome() {
			List<ProductionDetailsBean> list = new ArrayList<>();
			String hql = getAverageAnnualIncome;
			Session session = sessionFactory.getCurrentSession();
			
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(ProductionDetailsBean.class));
				list = query.list();
				
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			
			return list;
		}

		@Override
		public List<ProductionDetailsBean> getCommunityBasedData() {
			List<ProductionDetailsBean> list = new ArrayList<>();
			String hql = getCommunityBasedData;
			Session session = sessionFactory.getCurrentSession();
			
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(ProductionDetailsBean.class));
				list = query.list();
				
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			
			return list;
		}

		@Override
		public List<ProductionDetailsBean> getDistwiseAverageAnnualIncome(Integer stcode) {
			List<ProductionDetailsBean> list = new ArrayList<>();
			String hql = getDistwiseAverageAnnualIncome;
			Session session = sessionFactory.getCurrentSession();
			
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(ProductionDetailsBean.class));
				query.setInteger("stcode", stcode);
				list = query.list();
				
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			
			return list;
		}

		@Override
		public List<ProductionDetailsBean> getDistwiseCommunityBasedData(Integer stcode) {
			List<ProductionDetailsBean> list = new ArrayList<>();
			String hql = getDistwiseCommunityBasedData;
			Session session = sessionFactory.getCurrentSession();
			
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(ProductionDetailsBean.class));
				query.setInteger("stcode", stcode);
				list = query.list();
				
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			
			return list;
		}

		@Override
		public List<CroppedDetailsReportBean> getDistwiseCropDetailsReportData(Integer stcode, String type) {
			String prehql = getDistWisePreCropDetails;
			String midhql = getDistWiseMidCropDetails;
			String controlhql = getDistWiseControlCropDetails;
			List<CroppedDetailsReportBean> list = new ArrayList<>();
			
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query;
				if(type.equals("pre")){
					query = session.createSQLQuery(prehql);
					query.setResultTransformer(Transformers.aliasToBean(CroppedDetailsReportBean.class));
					query.setInteger("stcode", stcode);
					list = query.list();
				}
				
				else if(type.equals("mid")) {
				query = session.createSQLQuery(midhql);
				query.setResultTransformer(Transformers.aliasToBean(CroppedDetailsReportBean.class));
				query.setInteger("stcode", stcode);
				list = query.list();
				}
				else if(type.equals("control")){
				query = session.createSQLQuery(controlhql);
				query.setResultTransformer(Transformers.aliasToBean(CroppedDetailsReportBean.class));
				query.setInteger("stcode", stcode);
				list = query.list();
				}
				
				session.getTransaction().commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
			return list;
		}

}


