package app.daoImpl.outcome;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

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

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;
import app.bean.PhysicalActBean;
import app.bean.VillGramPanBean;
import app.bean.reports.UserUploadDetailsBean;
import app.dao.outcomes.BaseLineOutcomeDao;
import app.model.BlsOutDetail;
import app.model.BlsOutDetailAchiev;
import app.model.BlsOutDetailCrop;
import app.model.BlsOutDetailCropAchiev;
import app.model.BlsOutDetailCropHis;
import app.model.BlsOutDetailHis;
import app.model.BlsOutMain;
import app.model.BlsOutMainAchiev;
import app.model.BlsOutMainHis;
import app.model.IwmpActiveUserHistory;
import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpUserProjectMap;
import app.model.MBlsOutType;
import app.model.MBlsOutcome;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;

@Repository("BaseLineOutcomeDao")
public class BaseLineOutcomeDaoImpl implements BaseLineOutcomeDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getTotSeasonCropCount}")
	String getTotSeasonCropCount;
	
	@Value("${totalareabase}")
	String totalareabase;
	
	@Value("${getbaselinecomdatachieve}")
	String getbaselinecomdatachieve;
	
	@Value("${getAllCropDataForBlsOutDetail}")
	String getAllCropDataForBlsOutDetail;

	@Value("${getProjectByRegIdForBaseLine}")
	String getProjectByRegId;

	@Value("${getPlotDataOfVillageforUpdate}")
	String getPlotDataOfVillageforUpdate;

	@Value("${getCropDataPlotProject}")
	String getCropDataPlotProject;

	@Value("${getProjectByRegIdForBaseLineUpdate}")
	String getProjectForBloUpdateByRegId;

	@Value("${getPlotForProjectVillage}")
	String getPlotForProjectVillage;

	@Value("${getOutComeMaster}")
	String getOutComeMaster;

	@Value("${getBaselineNewDraft}")
	String getBaselineNewDraft;

	@Value("${getbaselinefinaldata}")
	String getbaselinefinaldata;

	@Value("${NewBaselineMainDelete}")
	String NewBaselineMainDelete;

	@Value("${getbaselinecomdata}")
	String getbaselinecomdata;

	@Value("${NewBaselineDetailDelete}")
	String NewBaselineDetailDelete;

	@Value("${getcroptype}")
	String getcroptype;
	
	@Value("${getcroptypeupdate}")
	String getcroptypeupdate;


	@Value("${NewBaselineDetailtranDelete}")
	String NewBaselineDetailtranDelete;

	@Value("${NewBaselineComplete}")
	String NewBaselineComplete;

	@Value("${NewBaselineCompleteMainAch}")
	String NewBaselineCompleteMainAch;

	@Value("${NewBaselineCompleteDetailAch}")
	String NewBaselineCompleteDetailAch;

	@Value("${NewBaselineCompleteDetTransAch}")
	String NewBaselineCompleteDetTransAch;

	@Value("${NewBaselineCompleteachiev}")
	String NewBaselineCompleteachiev;

	@Value("${checktransbs}")
	String checktransbs;

	@Value("${checkdetalbs}")
	String checkdetalbs;

	@Value("${checkmainbs}")
	String checkmainbs;

	@Value("${getVillageOfProjectForBaseLine}")
	String getVillageOfProject;

	@Value("${getVillageOfProjectForUpdateBaseLine}")
	String getVillageOfProjectForUpdateBaseLine;

	@Value("${getPlotDataOfAVillage}")
	String getPlotDataOfAVillage;
	
	@Value("${getbaselinedraftdat}")
	String getbaselinedraftdat;

	@Value("${getVillageOfProjectMicro}")
	String getVillageOfProjectMicro;
	
	@Override
	public LinkedHashMap<Integer, String> getProjectByRegId(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		List<IwmpUserProjectMap> rows = new ArrayList<IwmpUserProjectMap>();
		String hql = getProjectByRegId;
		Session session = sessionFactory.getCurrentSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId", registrationId);
			
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
			}
			
			
			/*
			 * rows = query.list(); for (IwmpUserProjectMap row : rows) {
			 * System.out.println("Size by regId: " + row.getIwmpMProject().getProjectCd() +
			 * " regId " + regId); map.put(row.getIwmpMProject().getProjectId(),
			 * row.getIwmpMProject().getProjName()); }
			 */
			session.getTransaction().commit();
		} catch (Exception ex) {
			// System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {

		}
		return map;
	}

	@Override
	public List<MBlsOutcome> getOutComeMaster() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		List<MBlsOutcome> rows = new ArrayList<MBlsOutcome>();
		try {

			session.beginTransaction();
			String hql = getOutComeMaster;
			Query query = session.createQuery(hql);
			rows = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			// System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {

		}
		return rows;
	}

	@Override
	public String saveBLSasDraft(Integer projId, Integer vcode, String plotNo, BigDecimal projectArea,
			Integer irrigationStatus, Integer ownership, String ownerName, Integer landClassification, String landSubClassification, String noOfCrop,
			List<Integer> season, List<Integer> cropType, List<BigDecimal> cropArea, List<BigDecimal> cropProduction,
			List<BigDecimal> avgIncome, String forestLandType, String userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		//System.out.println("no of crop:" +noOfCrop);
		//System.out.println("no of forestLandType:" +forestLandType);
		String res = "fail";
		try {

			session.beginTransaction();
			BlsOutMain main = new BlsOutMain();
			BlsOutDetail detail = new BlsOutDetail();
			BlsOutDetailCrop tranx = new BlsOutDetailCrop();
			MBlsOutcome mbout = new MBlsOutcome();
			IwmpMProject iwmpMProject = new IwmpMProject();
			IwmpVillage iwmpVillage = new IwmpVillage();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();

			main.setArea(projectArea);
			main.setPlotNo("" + plotNo.toUpperCase());
			mbout = (MBlsOutcome) session.get(MBlsOutcome.class, irrigationStatus);
			main.setMBlsOutcomeByIrrigationStatusId(mbout);
			iwmpMProject = (IwmpMProject) session.get(IwmpMProject.class, projId);
			main.setIwmpMProject(iwmpMProject);
			iwmpVillage = (IwmpVillage) session.get(IwmpVillage.class, vcode);
			main.setIwmpVillage(iwmpVillage);
			main.setCreatedBy(userId);
			main.setCreatedOn(new Date());
			main.setRequestIp(ipAddr);
			main.setStatus('D');
			session.save(main);

			MBlsOutcome MBlsOutcomeByOwnershipId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeByClassificationLandId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeByForestLandTypeId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeByNoOfCropId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeByCropTypeId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeBySeasonId = new MBlsOutcome();

			detail.setBlsOutMain(main);
			MBlsOutcomeByOwnershipId = (MBlsOutcome) session.get(MBlsOutcome.class, ownership);
			detail.setMBlsOutcomeByOwnershipId(MBlsOutcomeByOwnershipId);
			detail.setOwnerName(ownerName);
			MBlsOutcomeByClassificationLandId = (MBlsOutcome) session.get(MBlsOutcome.class, landClassification);
			detail.setMBlsOutcomeByClassificationLandId(MBlsOutcomeByClassificationLandId);
			if (landClassification==8 && forestLandType != null && !forestLandType.equalsIgnoreCase(""))
				MBlsOutcomeByForestLandTypeId = (MBlsOutcome) session.get(MBlsOutcome.class, Integer.parseInt(forestLandType));
			detail.setMBlsOutcomeByForestLandTypeId(MBlsOutcomeByForestLandTypeId);
			if (noOfCrop != null && !noOfCrop.equalsIgnoreCase(""))
				MBlsOutcomeByNoOfCropId = (MBlsOutcome) session.get(MBlsOutcome.class, Integer.parseInt(noOfCrop));
			detail.setMBlsOutcomeByNoOfCropId(MBlsOutcomeByNoOfCropId);
			detail.setCreatedBy(userId);
			detail.setRequestIp(ipAddr);
			detail.setCreatedOn(new Date());
			detail.setLandSubClassification(landSubClassification);
			session.save(detail);
			session.evict(detail);

			for (int i = 0; i < cropType.size(); i++) {
				tranx.setBlsOutDetail(detail);
				tranx.setCreatedBy(userId);
				tranx.setCreatedOn(new Date());
				tranx.setCropArea(cropArea.get(i));
				tranx.setCropProduction(cropProduction.get(i));
				MBlsOutcomeByCropTypeId = (MBlsOutcome) session.get(MBlsOutcome.class, cropType.get(i));
				tranx.setMBlsOutcomeByCropTypeId(MBlsOutcomeByCropTypeId);
				if (season.size() > 0)
					MBlsOutcomeBySeasonId = (MBlsOutcome) session.get(MBlsOutcome.class, season.get(i));
				tranx.setMBlsOutcomeBySeasonId(MBlsOutcomeBySeasonId);
				tranx.setAvgIncome(avgIncome.get(i));
				tranx.setRequestIp(ipAddr);
				session.save(tranx);
				session.evict(tranx);
			}

			session.getTransaction().commit();
			res = "success";
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			res = "fail";
		}

		return res;
	}

	@Override
	public List<NewBaseLineSurveyBean> getBaselineNewDraft(Integer projectId) {

		List<NewBaseLineSurveyBean> result = new ArrayList<NewBaseLineSurveyBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql = null;
			SQLQuery query = null;
			@SuppressWarnings("unused")
			Transaction tx = session.beginTransaction();
			hql = getBaselineNewDraft;
			query = session.createSQLQuery(hql);
			query.setInteger("project", projectId);
			query.setResultTransformer(Transformers.aliasToBean(NewBaseLineSurveyBean.class));
			result = query.list();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {
			ex.getMessage();
			ex.getCause();
			ex.printStackTrace();
		} finally {
			session.getTransaction().commit();
			// session.flush(); session.close();
		}
		return result;
	}

	@Override
	public LinkedHashMap<Integer, List<NewBaseLineSurveyBean>> getbaselinefinaldata(Integer project) {
		LinkedHashMap<Integer, List<NewBaseLineSurveyBean>> map = new LinkedHashMap<Integer, List<NewBaseLineSurveyBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = getbaselinefinaldata;
			query = session.createSQLQuery(hql);
			query.setInteger("project", project);
			query.setResultTransformer(Transformers.aliasToBean(NewBaseLineSurveyBean.class));
			List<NewBaseLineSurveyBean> list = query.list();
			for (NewBaseLineSurveyBean row : list) {
				// System.out.println("userId: "+row.getHeadcode());
			}
			List<NewBaseLineSurveyBean> sublist = new ArrayList<NewBaseLineSurveyBean>();
			if ((list != null) && (list.size() > 0)) {
				for (NewBaseLineSurveyBean row : list) {
					if (!map.containsKey(row.getBls_out_detail_tranx_id_pk())) {
						sublist = new ArrayList<NewBaseLineSurveyBean>();
						sublist.add(row);
						map.put(row.getBls_out_detail_tranx_id_pk(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getBls_out_detail_tranx_id_pk(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			// e.printStackTrace();
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			// ex.printStackTrace();
		} finally {

		}
		return map;
	}

	public BlsOutDetailCropAchiev findbaselineedata(Integer id) {

		BlsOutDetailCropAchiev getbaselinecdata = new BlsOutDetailCropAchiev();


		 String hql=getbaselinecomdatachieve;

		// String hql=getbaselinecomdata;

		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			
			getbaselinecdata = (BlsOutDetailCropAchiev) session.createQuery(hql)
					.setParameter("id", id).uniqueResult();
			//	getbaselinecdata = (BlsOutDetailCropAchiev) session.load(BlsOutDetailCropAchiev.class, id);
//			Query query = session.createQuery(hql); 

			
	
			getbaselinecdata = (BlsOutDetailCropAchiev) session.createQuery(getbaselinecomdata)
					.setParameter("id", id).uniqueResult();
//			//getbaselinecdata = (BlsOutDetailCropAchiev) session.load(BlsOutDetailCropAchiev.class, id);
//			Query query = session.createQuery(hql); 

//		  query.setParameter("id", id);
//		  getbaselinecdata = (BlsOutDetailCropAchiev) query.getSingleResult();
//		  
			session.getTransaction().commit(); 
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return getbaselinecdata;

	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, String> getcroptypeCode() {
		List<MBlsOutcome> typecode = new ArrayList<MBlsOutcome>();
		String hql = getcroptype;
		LinkedHashMap<Integer, String> typeMap = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			// query.setResultTransformer(Transformers.aliasToBean(MBlsOutcome.class));
			typecode = query.list();

			for (MBlsOutcome row : typecode) {
				typeMap.put(row.getMBlsOutIdPk(), row.getDescription());

			}
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return typeMap;
	}

	@Override
	public boolean deleteBaselineNewDraft(Integer main, Integer detail, String detailtrans) {

		Boolean res = false;
		Integer value = 0;
		Integer value1 = 0;
		Session sessionf = sessionFactory.getCurrentSession();
		SQLQuery query;
		SQLQuery query1;
		SQLQuery query2;
		sessionf.beginTransaction();
		String deletemain = NewBaselineMainDelete;
		String deletedetail = NewBaselineDetailDelete;
		String deletedetailtrans = NewBaselineDetailtranDelete;
		String checktransbs1 = checktransbs;
		String checkdetalbs1 = checkdetalbs;
		//String checkmainbs1 = checkmainbs;
		try {

			query = sessionf.createSQLQuery(checktransbs1);
			query.setInteger("detail", detail);
			Integer tranxCount = Integer.parseInt(query.list().get(0).toString());
			if (tranxCount > 1) {
				query = sessionf.createSQLQuery(deletedetailtrans);
				query.setInteger("detailtrans", Integer.parseInt(detailtrans));
				value = query.executeUpdate();
			} else if (tranxCount == 1) {
				query = sessionf.createSQLQuery(deletedetailtrans);
				query.setInteger("detailtrans", Integer.parseInt(detailtrans));
				value = query.executeUpdate();

				query1 = sessionf.createSQLQuery(deletedetail);
				query1.setInteger("detail", detail);
				value1 = query1.executeUpdate();

				query2 = sessionf.createSQLQuery(deletemain);
				query2.setInteger("main", main);
				value1 = query2.executeUpdate();
			} else {

				query1 = sessionf.createSQLQuery(deletedetail);
				query1.setInteger("detail", detail);
				value1 = query1.executeUpdate();

				query2 = sessionf.createSQLQuery(deletemain);
				query2.setInteger("main", main);
				value1 = query2.executeUpdate();
			}

			/*
			 * if(!detailtrans.equals("")) { //int count = select count(*) from bls_out_crop
			 * where detail_id=deletedetail //if(count>0)
			 * 
			 * //else // rest code } else { query1 = sessionf.createSQLQuery(deletedetail);
			 * query1.setInteger("detail", detail); value1=query1.executeUpdate();
			 * 
			 * query2 = sessionf.createSQLQuery(deletemain); query2.setInteger("main",
			 * main); value1=query2.executeUpdate(); }
			 */

			if (value > 0 || value1 > 0) {
				res = true;
			} else {
				res = false;
				sessionf.getTransaction().rollback();
				return false;
			}
			if (res)
				sessionf.getTransaction().commit();
			else
				sessionf.getTransaction().rollback();
		} catch (Exception ex) {
			// System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		} finally {

		}
		return res;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean completeBaselineNewDraft(Integer project, List<String> villcode) {

		Boolean res = false;
		Integer value = 0;
		int abc = 0;
		int abc1 = 0;
		int abc2 = 0;
		int abc3 = 0;
		Session sessionf = sessionFactory.getCurrentSession();
		SQLQuery query;
		SQLQuery query1;
		SQLQuery query2;
		SQLQuery query3;
		SQLQuery query4;
		sessionf.beginTransaction();
		String comp = NewBaselineComplete;
		String mainAch = NewBaselineCompleteMainAch;
		String detAch = NewBaselineCompleteDetailAch;
		String detTranAch = NewBaselineCompleteDetTransAch;
		String mainachicomp = NewBaselineCompleteachiev;

		try { // 2439619472
			for (String list : villcode) {
				query1 = sessionf.createSQLQuery(mainAch);
				query1.setInteger("project", project);
				query1.setInteger("vill", Integer.parseInt(list));
				abc = query1.executeUpdate();

				query2 = sessionf.createSQLQuery(detAch);
				query2.setInteger("project", project);
				query2.setInteger("vill", Integer.parseInt(list));
				abc1 = query2.executeUpdate();

				query3 = sessionf.createSQLQuery(detTranAch);
				query3.setInteger("project", project);
				query3.setInteger("vill", Integer.parseInt(list));
				abc2 = query3.executeUpdate();

				query4 = sessionf.createSQLQuery(mainachicomp);
				query4.setInteger("project", project);
				query4.setInteger("vill", Integer.parseInt(list));
				abc3 = query4.executeUpdate();

				query = sessionf.createSQLQuery(comp);
				query.setInteger("project", project);
				query.setInteger("vill", Integer.parseInt(list));
				value = query.executeUpdate();
			}
			if (value > 0) {
				if ((abc > 0 && abc1 > 0 && abc3 > 0) || abc2 > 0) {
					res = true;
				} else {
					res = false;
				}
				if (res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
			}
		} catch (Exception ex) {
			// System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		} finally {

		}
		return res;
	}

	@Override
	public boolean updateBaselineCropAchiev(BlsOutDetailCropAchiev blsOutDetailCropAchiev) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Boolean flag = false;
		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

			if (blsOutDetailCropAchiev != null) {
				// Transformers.aliasToBean(
				BlsOutDetailCropAchiev tproject = (BlsOutDetailCropAchiev) session.load(BlsOutDetailCropAchiev.class,
						blsOutDetailCropAchiev.getBlsOutDetailCropIdPk());
				//System.out.println("true" + blsOutDetailCropAchiev.getCropArea());
				//System.out.println("gp" + tproject.getMBlsOutcomeByCropTypeId().getMBlsOutIdPk());
				BlsOutDetailCropHis history = new BlsOutDetailCropHis();
				history.setAvgIncome(tproject.getAvgIncome());
				history.setCreatedBy(tproject.getCreatedBy());
				history.setCreatedOn(tproject.getCreatedOn());
				history.setAvgIncome(tproject.getAvgIncome());
				history.setCropArea(tproject.getCropArea());
				history.setCropProduction(tproject.getCropProduction());
				history.setRequestIp(tproject.getRequestIp());
				history.setUpdatedOn(tproject.getUpdatedOn());
				history.setMBlsOutcomeByCropTypeId(tproject.getMBlsOutcomeByCropTypeId());
				history.setMBlsOutcomeBySeasonId(tproject.getMBlsOutcomeBySeasonId());
				BlsOutDetailHis dhis = new BlsOutDetailHis();
				// dhis.setBlsOutDetailIdPk(tproject.getBlsOutDetailTranxIdPk());
				dhis.setOwnerName(tproject.getBlsOutDetailAchiev().getOwnerName());
				dhis.setRequestIp(tproject.getRequestIp());
				dhis.setUpdatedOn(new java.util.Date());
				dhis.setCreatedOn(tproject.getCreatedOn());

				BlsOutMainHis m1 = new BlsOutMainHis();
				// m1.setBlsOutMainIdPk(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getBlsOutMainIdPk());

				m1.setCreatedBy(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getCreatedBy());
				m1.setCreatedOn(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getCreatedOn());
				m1.setUpdatedOn(new java.util.Date());
				m1.setArea(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getArea());
				m1.setIwmpMProject(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getIwmpMProject());
				m1.setIwmpVillage(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getIwmpVillage());
				m1.setMBlsOutcome(
						tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getMBlsOutcomeByIrrigationStatusId());
				m1.setPlotNo(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getPlotNo());
				m1.setRequestIp(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getRequestIp());
				m1.setStatus(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getStatus());
				session.save(m1);
				dhis.setBlsOutMainHis(m1);

				MBlsOutcome temp = new MBlsOutcome();
				temp.setMBlsOutIdPk(
						tproject.getBlsOutDetailAchiev().getMBlsOutcomeByClassificationLandId().getMBlsOutIdPk());
				dhis.setMBlsOutcomeByClassificationLandId(temp);
				if (tproject.getBlsOutDetailAchiev().getMBlsOutcomeByForestLandTypeId() != null) {
					temp = new MBlsOutcome();
					temp.setMBlsOutIdPk(
							tproject.getBlsOutDetailAchiev().getMBlsOutcomeByForestLandTypeId().getMBlsOutIdPk());
					dhis.setMBlsOutcomeByForestLandTypeId(temp);
				}

				temp = new MBlsOutcome();
				temp.setMBlsOutIdPk(tproject.getBlsOutDetailAchiev().getMBlsOutcomeByOwnershipId().getMBlsOutIdPk());
				dhis.setMBlsOutcomeByOwnershipId(temp);

				temp = new MBlsOutcome();
				temp.setMBlsOutIdPk(tproject.getBlsOutDetailAchiev().getMBlsOutcomeByNoOfCropId().getMBlsOutIdPk());
				dhis.setMBlsOutcomeByNoOfCropId(temp);

				dhis.setBlsOutMainHis(m1);

				session.save(dhis);
				history.setBlsOutDetailHis(dhis);

				if(blsOutDetailCropAchiev.getMBlsOutcomeBySeasonId()!=null)
				{
					MBlsOutcome season = new MBlsOutcome();
					season.setMBlsOutIdPk(blsOutDetailCropAchiev.getMBlsOutcomeBySeasonId().getMBlsOutIdPk());
					tproject.setMBlsOutcomeBySeasonId(season);
				}
				MBlsOutcome crop = new MBlsOutcome();
				crop.setMBlsOutIdPk(blsOutDetailCropAchiev.getMBlsOutcomeByCropTypeId().getMBlsOutIdPk());
				tproject.setMBlsOutcomeByCropTypeId(crop);

				tproject.setCropArea(blsOutDetailCropAchiev.getCropArea());
				tproject.setCropProduction(blsOutDetailCropAchiev.getCropProduction());

				tproject.setAvgIncome(blsOutDetailCropAchiev.getAvgIncome());

				tproject.setUpdatedOn(new java.util.Date());
				// tproject.setActive(true);
				session.update(tproject);

				session.save(history);
				// System.out.print("done" + blsOutDetailCropAchiev.get());

				flag = true;
			}

		} catch (Exception ex) {
			//System.out.print("Err" + ex.getMessage());
			session.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			tx.commit();

		}
		return flag;
	}

	@Override
	public HashMap<Integer, String> getVillageOfProject(Integer projId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// List<IwmpVillage> temp = null;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		String hql = getVillageOfProject;
		try {
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projId", projId);
			 query.setResultTransformer(Transformers.aliasToBean(VillGramPanBean.class)); 
			List<VillGramPanBean> temp = query.list();
			for (VillGramPanBean v : temp) {
				map.put(v.getVcode(), v.getVillagename()+" ["+v.getGrampanchayatname()+"]");
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			//System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewBaseLineSurveyBean> getPlotDataOfAVillage(Integer vcode, String plotno, Integer projid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// List<IwmpVillage> temp = null;
		List<BlsOutMain> res = new ArrayList<BlsOutMain>();
		List<NewBaseLineSurveyBean> list = new ArrayList<NewBaseLineSurveyBean>();
		String hql = getPlotDataOfAVillage;
		try {
			
		res =  session.createQuery(hql).setParameter("vcode", vcode).setParameter("plotno", plotno.toUpperCase()).setParameter("projid", projid).list();
					
			for (BlsOutMain m : res) {
				NewBaseLineSurveyBean bean = new NewBaseLineSurveyBean();
				bean.setArea(m.getArea());
				bean.setIrrigation_status_id(m.getMBlsOutcomeByIrrigationStatusId().getMBlsOutIdPk());
				for (BlsOutDetail det : m.getBlsOutDetails()) {
					bean.setOwnership_id(det.getMBlsOutcomeByOwnershipId().getMBlsOutIdPk());
					bean.setOwner_name(det.getOwnerName());
					bean.setClassification_land_id(det.getMBlsOutcomeByClassificationLandId().getMBlsOutIdPk());
					if (det.getMBlsOutcomeByForestLandTypeId() != null) {
						bean.setForest_land_type_id(det.getMBlsOutcomeByForestLandTypeId().getMBlsOutIdPk());
						bean.setNo_of_crop_id(0);
					}else {
						bean.setNo_of_crop_id(det.getMBlsOutcomeByNoOfCropId().getMBlsOutIdPk());
					}
				}
				list.add(bean);
				//System.out.println(list.size());
			}
			//System.out.println(res.get(0).getBlsOutMainIdPk());
			session.getTransaction().commit();
		} catch (Exception ex) {
		//	System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return list;
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectforBlsUpdateByRegId(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		List<IwmpUserProjectMap> rows = new ArrayList<IwmpUserProjectMap>();
		String hql = getProjectForBloUpdateByRegId;
		Session session = sessionFactory.getCurrentSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId", registrationId);
			rows = query.list();
			for (IwmpUserProjectMap row : rows) {
				//System.out.println("Size by regId: " + row.getIwmpMProject().getProjectCd() + " regId " + regId);
				map.put(row.getIwmpMProject().getProjectId(), row.getIwmpMProject().getProjName());
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			// System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {

		}
		return map;
	}

	@Override
	public LinkedHashMap<String, String> getPlotNoByProjectVillage(Integer projectId, Integer vcode) {
		// TODO Auto-generated method stub

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		List<BlsOutMain> rows = new ArrayList<BlsOutMain>();
		String hql = getPlotForProjectVillage;
		Session session = sessionFactory.getCurrentSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("projectId", projectId);
			query.setLong("vcode", vcode);
			rows = query.list();
			for (BlsOutMain row : rows) {
//				System.out.println("Size by project: " + row.getIwmpMProject().getProjectCd() + " vcode " + vcode);
				map.put(row.getPlotNo(), row.getPlotNo());
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			// System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {

		}
		return map;
	}

	@Override
	public HashMap<Integer, String> getVillageOfProjectforBlsUpdate(Integer projId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// List<IwmpVillage> temp = null;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		String hql = getVillageOfProjectForUpdateBaseLine;
		try {
			Query query = session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setResultTransformer(Transformers.aliasToBean(IwmpVillage.class));
			List<IwmpVillage> temp = query.list();
			for (IwmpVillage v : temp) {
				map.put(v.getVcode(), v.getVillageName());
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			//System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return map;
	}

	@Override
	public List<NewBaseLineSurveyBean> getCropDataOfPlotProject(Integer vcode, String plotno, Integer projId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// List<IwmpVillage> temp = null;
		List<BlsOutDetailCropAchiev> res = new ArrayList<BlsOutDetailCropAchiev>();
		List<NewBaseLineSurveyBean> list = new ArrayList<NewBaseLineSurveyBean>();
		String hql = getCropDataPlotProject;
		try {
			Query query = session.createQuery(hql);
			query.setInteger("projectId", projId);
			query.setInteger("vcode", vcode);
			query.setString("plotNo", plotno);
			// query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
			res = query.list();
			for (BlsOutDetailCropAchiev m : res) {
				NewBaseLineSurveyBean bean = new NewBaseLineSurveyBean();
				if (m.getMBlsOutcomeBySeasonId() != null) {
					bean.setSeason(m.getMBlsOutcomeBySeasonId().getDescription());
					bean.setSeason_id(m.getMBlsOutcomeBySeasonId().getMBlsOutIdPk());
				} else
					bean.setSeason("");
				bean.setCrop_type(m.getMBlsOutcomeByCropTypeId().getDescription());
				bean.setCrop_area(m.getCropArea());
				bean.setCrop_production(m.getCropProduction());
				bean.setAvg_income(m.getAvgIncome());
				bean.setBls_out_detail_tranx_id_pk(m.getBlsOutDetailCropIdPk());
				bean.setDel_status(m.getDelStatus());
				list.add(bean);
				//System.out.println("area--"+m.getCropArea());
			}

			session.getTransaction().commit();
		} catch (Exception ex) {
			//System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return list;
	}

	@Override
	public List<NewBaseLineSurveyBean> getPlotDataOfAVillageForUpdate(Integer vcode, String plotno) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// List<IwmpVillage> temp = null;
		List<BlsOutMainAchiev> res = new ArrayList<BlsOutMainAchiev>();
		List<NewBaseLineSurveyBean> list = new ArrayList<NewBaseLineSurveyBean>();
		String hql = getPlotDataOfVillageforUpdate;
		try {
			Query query = session.createQuery(hql);
			query.setInteger("vcode", vcode);
			query.setString("plotno", plotno);
			// query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
			res = query.list();
			for (BlsOutMainAchiev m : res) {
				NewBaseLineSurveyBean bean = new NewBaseLineSurveyBean();
				bean.setArea(m.getArea());
				bean.setIrrigation_status_id(m.getMBlsOutcomeByIrrigationStatusId().getMBlsOutIdPk());
				if(m.getMicro_irrigation()!=null) {
					bean.setMicro_irrigation(m.getMicro_irrigation());
				}
				for (BlsOutDetailAchiev det : m.getBlsOutDetailAchievs()) {
					bean.setBls_out_detail_id_pk(det.getBlsOutDetailIdPk());
					if (det.getMBlsOutcomeByOwnershipId() != null)
						bean.setOwnership_id(det.getMBlsOutcomeByOwnershipId().getMBlsOutIdPk());
					bean.setOwner_name(det.getOwnerName());
					if (det.getMBlsOutcomeByClassificationLandId() != null) {
						bean.setClassification_land_id(det.getMBlsOutcomeByClassificationLandId().getMBlsOutIdPk());
						bean.setLand_sub_classification(det.getLandSubClassification());
					}
					if (det.getMBlsOutcomeByNoOfCropId() != null)
						bean.setNo_of_crop_id(det.getMBlsOutcomeByNoOfCropId().getMBlsOutIdPk());
					if (det.getMBlsOutcomeByForestLandTypeId() != null)
						bean.setForest_land_type_id(det.getMBlsOutcomeByForestLandTypeId().getMBlsOutIdPk());
				}
				list.add(bean);
				//System.out.println(list.size());
			}
			//System.out.println(res.get(0).getBlsOutMainIdPk());
			session.getTransaction().commit();
		} catch (Exception ex) {
			//System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return list;
	}

	@Override
	public boolean deleteBaselineCropAchiev(Integer pkId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Boolean flag = false;
		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

			if (pkId != null) {
				// Transformers.aliasToBean(
				BlsOutDetailCropAchiev tproject = (BlsOutDetailCropAchiev) session.load(BlsOutDetailCropAchiev.class,
						pkId);
				String hql = getTotSeasonCropCount;
				Query query = session.createQuery(hql);
				query.setInteger("blsOutDetailIdPk", tproject.getBlsOutDetailAchiev().getBlsOutDetailIdPk());
				
				int tempseason=0;
				if(tproject.getMBlsOutcomeBySeasonId()!=null)
					tempseason=tproject.getMBlsOutcomeBySeasonId().getMBlsOutIdPk();
				
				query.setInteger("season", tempseason);
				
				Integer totcropinseason = Integer.parseInt(query.list().get(0).toString());
				
				//String noOfCrop=tproject.getBlsOutDetailAchiev().getMBlsOutcomeByNoOfCropId().getDescription();
				
				if(totcropinseason>1)
				{
				BlsOutDetailCropHis history = new BlsOutDetailCropHis();
				history.setAvgIncome(tproject.getAvgIncome());
				history.setCreatedBy(tproject.getCreatedBy());
				history.setCreatedOn(tproject.getCreatedOn());
				history.setAvgIncome(tproject.getAvgIncome());
				history.setCropArea(tproject.getCropArea());
				history.setCropProduction(tproject.getCropProduction());
				history.setRequestIp(tproject.getRequestIp());
				history.setUpdatedOn(new java.util.Date());
				history.setMBlsOutcomeByCropTypeId(tproject.getMBlsOutcomeByCropTypeId());
				history.setMBlsOutcomeBySeasonId(tproject.getMBlsOutcomeBySeasonId());
				BlsOutDetailHis dhis = new BlsOutDetailHis();
				// dhis.setBlsOutDetailIdPk(tproject.getBlsOutDetailTranxIdPk());
				dhis.setOwnerName(tproject.getBlsOutDetailAchiev().getOwnerName());
				dhis.setRequestIp(tproject.getRequestIp());
				dhis.setUpdatedOn(new java.util.Date());
				dhis.setCreatedOn(tproject.getCreatedOn());

				BlsOutMainHis m1 = new BlsOutMainHis();
				// m1.setBlsOutMainIdPk(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getBlsOutMainIdPk());

				m1.setCreatedBy(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getCreatedBy());
				m1.setCreatedOn(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getCreatedOn());
				m1.setUpdatedOn(new java.util.Date());
				m1.setArea(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getArea());
				m1.setIwmpMProject(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getIwmpMProject());
				m1.setIwmpVillage(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getIwmpVillage());
				m1.setMBlsOutcome(
						tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getMBlsOutcomeByIrrigationStatusId());
				m1.setPlotNo(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getPlotNo());
				m1.setRequestIp(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getRequestIp());
				m1.setStatus(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getStatus());
				session.save(m1);
				dhis.setBlsOutMainHis(m1);

				MBlsOutcome temp = new MBlsOutcome();
				temp.setMBlsOutIdPk(
						tproject.getBlsOutDetailAchiev().getMBlsOutcomeByClassificationLandId().getMBlsOutIdPk());
				dhis.setMBlsOutcomeByClassificationLandId(temp);
				if (tproject.getBlsOutDetailAchiev().getMBlsOutcomeByForestLandTypeId() != null) {
					temp = new MBlsOutcome();
					temp.setMBlsOutIdPk(
							tproject.getBlsOutDetailAchiev().getMBlsOutcomeByForestLandTypeId().getMBlsOutIdPk());
					dhis.setMBlsOutcomeByForestLandTypeId(temp);
				}

				temp = new MBlsOutcome();
				temp.setMBlsOutIdPk(tproject.getBlsOutDetailAchiev().getMBlsOutcomeByOwnershipId().getMBlsOutIdPk());
				dhis.setMBlsOutcomeByOwnershipId(temp);

				temp = new MBlsOutcome();
				temp.setMBlsOutIdPk(tproject.getBlsOutDetailAchiev().getMBlsOutcomeByNoOfCropId().getMBlsOutIdPk());
				dhis.setMBlsOutcomeByNoOfCropId(temp);

				dhis.setBlsOutMainHis(m1);

				session.save(dhis);
				history.setBlsOutDetailHis(dhis);

				session.save(history);
				session.delete(tproject);

				// System.out.print("done" + blsOutDetailCropAchiev.get());
				

				flag = true;
				}
			}

		} catch (Exception ex) {
			//System.out.print("Err" + ex.getMessage());
			session.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			tx.commit();

		}
		return flag;
	}

	@Override
	public String updateBLS(Integer projId, Integer vcode, String plotNo, BigDecimal projectArea,
			Integer irrigationStatus, Integer ownership, String ownerName, Integer landClassification,String landSubClassification, Integer noOfCrop,
			List<Integer> season, List<Integer> cropType, List<BigDecimal> cropArea, List<BigDecimal> cropProduction,
			List<BigDecimal> avgIncome, Integer forestLandType, String userId, Integer blsoutDetailId, BigDecimal microIrrigation) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		try {
//For Updating BLs Outmain and detail start
			session.beginTransaction();
			BlsOutMainAchiev main = new BlsOutMainAchiev();
			BlsOutDetailAchiev detail = new BlsOutDetailAchiev();
			System.out.println("check "+ noOfCrop);
			MBlsOutcome mbout = new MBlsOutcome();
			// IwmpMProject iwmpMProject = new IwmpMProject();
			// IwmpVillage iwmpVillage = new IwmpVillage();
			InetAddress inet = InetAddress.getLocalHost();
			detail = (BlsOutDetailAchiev) session.load(BlsOutDetailAchiev.class, blsoutDetailId);
			String ipAddr = inet.getHostAddress();
			main = detail.getBlsOutMainAchiev();
			main.setArea(projectArea);
			// main.setPlotNo("" + plotNo);
			mbout = (MBlsOutcome) session.get(MBlsOutcome.class, irrigationStatus);
			main.setMBlsOutcomeByIrrigationStatusId(mbout);
			main.setMicro_irrigation(microIrrigation);
			// iwmpMProject = (IwmpMProject) session.get(IwmpMProject.class, projId);
			// main.setIwmpMProject(iwmpMProject);
			// iwmpVillage = (IwmpVillage) session.get(IwmpVillage.class, vcode);
			// main.setIwmpVillage(iwmpVillage);
			// main.setCreatedBy(userId);
			main.setUpdatedOn(new Date());
			// main.setRequestIp(ipAddr);
			// main.setStatus('C');
			session.update(main);
			// session.evict(main);

			MBlsOutcome MBlsOutcomeByOwnershipId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeByClassificationLandId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeByForestLandTypeId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeByNoOfCropId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeByCropTypeId = new MBlsOutcome();
			MBlsOutcome MBlsOutcomeBySeasonId = new MBlsOutcome();

			// detail.setBlsOutMainAchiev(main);
			MBlsOutcomeByOwnershipId = (MBlsOutcome) session.get(MBlsOutcome.class, ownership);
			// MBlsOutcomeByOwnershipId.setMBlsOutIdPk(ownership);
			if (MBlsOutcomeByOwnershipId.getDescription().equals("Private"))
				detail.setOwnerName(null);
			detail.setMBlsOutcomeByOwnershipId(MBlsOutcomeByOwnershipId);
			detail.setOwnerName(ownerName);
			MBlsOutcomeByClassificationLandId = (MBlsOutcome) session.get(MBlsOutcome.class, landClassification);
			// MBlsOutcomeByClassificationLandId.setMBlsOutIdPk(landClassification);
			detail.setMBlsOutcomeByClassificationLandId(MBlsOutcomeByClassificationLandId);
			detail.setLandSubClassification(landSubClassification);
			if (forestLandType != null) {
				MBlsOutcomeByForestLandTypeId = (MBlsOutcome) session.get(MBlsOutcome.class, forestLandType);
				// MBlsOutcomeByForestLandTypeId.setMBlsOutIdPk(forestLandType);
				//detail.setMBlsOutcomeByNoOfCropId(null);
				detail.setMBlsOutcomeByForestLandTypeId(MBlsOutcomeByForestLandTypeId);

			}
			// detail.setMBlsOutcomeByForestLandTypeId(MBlsOutcomeByForestLandTypeId);
			if (noOfCrop != null) {
				MBlsOutcomeByNoOfCropId = (MBlsOutcome) session.get(MBlsOutcome.class, noOfCrop);
				// MBlsOutcomeByNoOfCropId.setMBlsOutIdPk(noOfCrop);
				detail.setMBlsOutcomeByNoOfCropId(MBlsOutcomeByNoOfCropId);
				//detail.setMBlsOutcomeByForestLandTypeId(null);
			}
			// detail.setMBlsOutcomeByNoOfCropId(MBlsOutcomeByNoOfCropId);
			// detail.setCreatedBy(userId);
			// detail.setRequestIp(ipAddr);
			detail.setUpdatedOn(new Date());
			session.update(detail);
			// session.evict(detail);
//----------------------End
			// Crop insert start------------------------------------
			for (int i = 0; i < cropType.size(); i++) {
				BlsOutDetailCropAchiev tranx = new BlsOutDetailCropAchiev();
				tranx.setBlsOutDetailAchiev(detail);
				tranx.setCreatedBy(userId);
				tranx.setCreatedOn(new Date());
				tranx.setCropArea(cropArea.get(i));
				tranx.setCropProduction(cropProduction.get(i));
				MBlsOutcomeByCropTypeId = (MBlsOutcome) session.get(MBlsOutcome.class, cropType.get(i));
				tranx.setMBlsOutcomeByCropTypeId(MBlsOutcomeByCropTypeId);
				if (season.size() > 0) {
					MBlsOutcomeBySeasonId = (MBlsOutcome) session.get(MBlsOutcome.class, season.get(i));
					tranx.setMBlsOutcomeBySeasonId(MBlsOutcomeBySeasonId);
				}
				tranx.setAvgIncome(avgIncome.get(i));
				tranx.setRequestIp(ipAddr);

				BlsOutDetailCrop crp = new BlsOutDetailCrop();
				BlsOutDetail bod = new BlsOutDetail();
				bod = (BlsOutDetail) session.get(BlsOutDetail.class,
						tranx.getBlsOutDetailAchiev().getBlsOutDetailIdPk());
				crp.setBlsOutDetail(bod);
				crp.setCreatedBy(userId);
				crp.setCreatedOn(new Date());
				crp.setUpdatedOn(new Date());
//				crp.setCropArea(cropArea.get(i));
//				crp.setCropProduction(cropProduction.get(i));
//				crp.setMBlsOutcomeByCropTypeId(MBlsOutcomeByCropTypeId);
//				if (MBlsOutcomeBySeasonId == null)
//					crp.setMBlsOutcomeBySeasonId(MBlsOutcomeBySeasonId);
//				crp.setAvgIncome(avgIncome.get(i));
				crp.setRequestIp(ipAddr);
//				Here 'P' stands for progress during project implementation.
//				crp.setStatus('P');
				session.save(crp);
				// session.evict(crp);
				tranx.setBlsOutDetailCropIdPk(crp.getBlsOutDetailTranxIdPk());
				

				session.save(tranx);
				session.delete(crp);
				// session.evict(tranx);

				// session.evict(tranx);
			}
/////////////////////////////////new Crop End--------------------------
			session.getTransaction().commit();
			res = "success";
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			res = "fail";
		}

		return res;
	}

	@Override
	public boolean deleteAllBaselineCropAchiev(Integer blsOutDetailId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Boolean flag = false;
		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

			if (blsOutDetailId != null) {
				// Transformers.aliasToBean(
				BlsOutDetailAchiev detail = (BlsOutDetailAchiev) session.load(BlsOutDetailAchiev.class, blsOutDetailId);

				BlsOutDetailHis dhis = new BlsOutDetailHis();
				// dhis.setBlsOutDetailIdPk(tproject.getBlsOutDetailTranxIdPk());
				dhis.setOwnerName(detail.getOwnerName());
				dhis.setLandSubClassification(detail.getLandSubClassification());
				dhis.setRequestIp(detail.getRequestIp());
				dhis.setUpdatedOn(new java.util.Date());
				dhis.setCreatedOn(detail.getCreatedOn());

				BlsOutMainHis m1 = new BlsOutMainHis();
				// m1.setBlsOutMainIdPk(tproject.getBlsOutDetailAchiev().getBlsOutMainAchiev().getBlsOutMainIdPk());

				m1.setCreatedBy(detail.getBlsOutMainAchiev().getCreatedBy());
				m1.setCreatedOn(detail.getBlsOutMainAchiev().getCreatedOn());
				m1.setUpdatedOn(new java.util.Date());
				m1.setArea(detail.getBlsOutMainAchiev().getArea());
				m1.setIwmpMProject(detail.getBlsOutMainAchiev().getIwmpMProject());
				m1.setIwmpVillage(detail.getBlsOutMainAchiev().getIwmpVillage());
				m1.setMBlsOutcome(detail.getBlsOutMainAchiev().getMBlsOutcomeByIrrigationStatusId());
				m1.setPlotNo(detail.getBlsOutMainAchiev().getPlotNo());
				m1.setRequestIp(detail.getBlsOutMainAchiev().getRequestIp());
				m1.setStatus(detail.getBlsOutMainAchiev().getStatus());
				session.save(m1);
				dhis.setBlsOutMainHis(m1);

				MBlsOutcome temp = new MBlsOutcome();
				if (detail.getMBlsOutcomeByClassificationLandId() != null) {
					temp.setMBlsOutIdPk(detail.getMBlsOutcomeByClassificationLandId().getMBlsOutIdPk());
					dhis.setMBlsOutcomeByClassificationLandId(temp);
				}
				if (detail.getMBlsOutcomeByForestLandTypeId() != null) {
					temp = new MBlsOutcome();
					temp.setMBlsOutIdPk(detail.getMBlsOutcomeByForestLandTypeId().getMBlsOutIdPk());
					dhis.setMBlsOutcomeByForestLandTypeId(temp);
				}

				temp = new MBlsOutcome();
				if (detail.getMBlsOutcomeByOwnershipId() != null) {
					temp.setMBlsOutIdPk(detail.getMBlsOutcomeByOwnershipId().getMBlsOutIdPk());
					dhis.setMBlsOutcomeByOwnershipId(temp);
				}

				if (detail.getMBlsOutcomeByNoOfCropId() != null) {
					temp = new MBlsOutcome();
					temp.setMBlsOutIdPk(detail.getMBlsOutcomeByNoOfCropId().getMBlsOutIdPk());
					dhis.setMBlsOutcomeByNoOfCropId(temp);
				}

				dhis.setBlsOutMainHis(m1);

				session.save(dhis);
				/* ------updtae Detail on the base of classification ,ownership and croptype */

				detail.setMBlsOutcomeByClassificationLandId(null);
				detail.setMBlsOutcomeByNoOfCropId(null);
				detail.setMBlsOutcomeByForestLandTypeId(null);
				detail.setOwnerName("");
				detail.setLandSubClassification("");
				detail.setMBlsOutcomeByOwnershipId(null);
				session.update(detail);
				/* -----------End */

				String hql = getAllCropDataForBlsOutDetail;
				Query query = session.createQuery(hql);
				query.setInteger("blsOutDetailIdPk", blsOutDetailId);

				// query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
				List<BlsOutDetailCropAchiev> res = new ArrayList<BlsOutDetailCropAchiev>();
				res = query.list();
				if (res != null) {
					for (BlsOutDetailCropAchiev crop : res) {

						BlsOutDetailCropHis history = new BlsOutDetailCropHis();
						history.setAvgIncome(crop.getAvgIncome());
						history.setCreatedBy(crop.getCreatedBy());
						history.setCreatedOn(crop.getCreatedOn());
						history.setAvgIncome(crop.getAvgIncome());
						history.setCropArea(crop.getCropArea());
						history.setCropProduction(crop.getCropProduction());
						history.setRequestIp(crop.getRequestIp());
						history.setUpdatedOn(new java.util.Date());
						history.setMBlsOutcomeByCropTypeId(crop.getMBlsOutcomeByCropTypeId());
						history.setMBlsOutcomeBySeasonId(crop.getMBlsOutcomeBySeasonId());
						history.setBlsOutDetailHis(dhis);
						history.setCreatedBy(crop.getCreatedBy());
						history.setCreatedOn(crop.getCreatedOn());
						session.save(history);
						session.delete(crop);
					}
					
				} else {
					flag=null;
				}
				tx.commit();
				flag = true;
			}

		} catch (Exception ex) {
			//System.out.print("Err" + ex.getMessage());
			session.getTransaction().rollback();
			ex.printStackTrace();
		} finally {

		}
		return flag;
	}

	@Override
	public LinkedHashMap<String, String> getcroptypeCode(Integer projectId, Integer villageCode,String plotNo, Integer season) {
		// TODO Auto-generated method stub
		List<MBlsOutcome> typecode = new ArrayList<MBlsOutcome>();
		String hql = getcroptypeupdate;
		LinkedHashMap<String, String> typeMap = new LinkedHashMap<String, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projId",projectId);
			query.setInteger("vcode",villageCode);
			query.setParameter("plotNo", plotNo);
			query.setInteger("season",season);
			// query.setResultTransformer(Transformers.aliasToBean(MBlsOutcome.class));
			typecode = query.list();

			for (MBlsOutcome row : typecode) {
				typeMap.put(Integer.toString(row.getMBlsOutIdPk()), row.getDescription());

			}
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return typeMap;
	}

	@Override
	public BigDecimal gettotalarea(Integer projId) {
		// TODO Auto-generated method stub
		BigDecimal res= BigDecimal.ZERO;
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			String hql=totalareabase;
			List list = ses.createQuery(hql).setParameter("proj", projId).list();
			res = (BigDecimal) list.get(0);
			//System.out.println("kdy "+res);
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return res;
	}

	@Override
	public BlsOutDetailCrop findbaselineDraftdata(Integer id) {


		BlsOutDetailCrop getbaselineddata = new BlsOutDetailCrop();

		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			
			getbaselineddata = (BlsOutDetailCrop) session.createQuery(getbaselinedraftdat).setParameter("id", id).uniqueResult();
//		  
			session.getTransaction().commit(); 
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return getbaselineddata;

	
	}

	@Override
	public String updateDraftWiseCropData(BlsOutDetailCrop bod) {
		String result = "fail";
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			session.update(bod);
			session.getTransaction().commit(); 
			result = "success";
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			result = "fail";
		} catch (Exception ex) {
			ex.printStackTrace();
			result = "fail";
		}
		return result;
	}

	@Override
	public List<NewBaseLineSurveyBean> getDraftCropDataOfPlotProject(Integer vcode, String plotno, Integer projId) {
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// List<IwmpVillage> temp = null;
		List<BlsOutDetailCrop> res = new ArrayList<BlsOutDetailCrop>();
		List<NewBaseLineSurveyBean> list = new ArrayList<NewBaseLineSurveyBean>();
		String hql = getCropDataPlotProject;
		try {
			Query query = session.createQuery(hql);
			query.setInteger("projectId", projId);
			query.setInteger("vcode", vcode);
			query.setString("plotNo", plotno);
			// query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
			res = query.list();
			for (BlsOutDetailCrop m : res) {
				NewBaseLineSurveyBean bean = new NewBaseLineSurveyBean();
				if (m.getMBlsOutcomeBySeasonId() != null) {
					bean.setSeason(m.getMBlsOutcomeBySeasonId().getDescription());
					bean.setSeason_id(m.getMBlsOutcomeBySeasonId().getMBlsOutIdPk());
				} else
					bean.setSeason("");
				bean.setCrop_type(m.getMBlsOutcomeByCropTypeId().getDescription());
				bean.setCrop_area(m.getCropArea());
				bean.setCrop_production(m.getCropProduction());
				bean.setAvg_income(m.getAvgIncome());
				bean.setBls_out_detail_tranx_id_pk(m.getBlsOutDetailTranxIdPk());
				list.add(bean);
			//	System.out.println("area--"+m.getCropArea());
			}

			session.getTransaction().commit();
		} catch (Exception ex) {
		//	System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return list;
	}

	@Override
	public HashMap<Integer, String> getVillageOfProjectMicro(Integer projId) {
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// List<IwmpVillage> temp = null;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		String hql = getVillageOfProjectMicro;
		try {
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projId", projId);
			 query.setResultTransformer(Transformers.aliasToBean(VillGramPanBean.class)); 
			List<VillGramPanBean> temp = query.list();
			for (VillGramPanBean v : temp) {
				map.put(v.getVcode(), v.getVillagename()+" ["+v.getGrampanchayatname()+"]");
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			//System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return map;
	}

	@Override
	public String changeCropDeleteStatus(Integer rowId) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		String status = "fail";
		String hql = getAllCropDataForBlsOutDetail;
		try {
		Query query = session.createQuery(hql);
		query.setInteger("blsOutDetailIdPk", rowId);

		// query.setResultTransformer(Transformers.aliasToBean(BaseLineOutcomeBean.class));
		List<BlsOutDetailCropAchiev> res = new ArrayList<BlsOutDetailCropAchiev>();
		res = query.list();
		if (res != null) {
			for (BlsOutDetailCropAchiev crop : res) {
				crop.setDelStatus('D');
				session.update(crop);
			}
		}
		tx.commit();
		status = "success";
		}catch(Exception ex) {
			session.getTransaction().rollback();
		}
		
		return status;
	}

}
