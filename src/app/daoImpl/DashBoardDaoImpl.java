package app.daoImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.AddOutcomeParaBean;
import app.bean.DolrSupportBean;
import app.bean.ProjectLocationBean;
import app.bean.TargetAchDashboardBean;
import app.bean.WatershedYatraDashboardChartBean;
import app.bean.WatrshdInagrtnPreYtraDashBean;
import app.bean.reports.DolrDashboardBean;
import app.dao.DashBoardDao;
import app.model.WdcpmksyMQuadIndicators;

@Repository("dashBoardDao")
public class DashBoardDaoImpl implements DashBoardDao{

	@Value("${projectList}")
	String allProjectQuery;
	
	@Value("${projectListshare}")
	String projectListshare;
	
	@Value("${lineprojectList}")
	String allProjectlineQuery;
	
	@Value("${blsCmpltdList}")
	String blsCmpltdList;
	
	@Value("${topTenBlsCmpltdList}")
	String topTenBlsCmpltdList;
	
	@Value("${topTenProjLocCmpltnPrcntgList}")
	String topTenProjLocCmpltnPrcntgList;
	
	@Value("${topTenStLandClassPrcntgList}")
	String topTenStLandClassPrcntgList;
	
	@Value("${compLandClassAchDetails}")
	String compLandClassAchDetails;
	
	@Value("${compIrriStatusDetails}")
	String compIrriStatusDetails;
	
	@Value("${compIrriStatusAchDetails}")
	String compIrriStatusAchDetails;
	
	@Value("${compNoOfCropDetails}")
	String compNoOfCropDetails;
	
	@Value("${compNoOfCropAchDetails}")
	String compNoOfCropAchDetails;
	
	@Value("${stwiseTotalExpndtrPrcntg}")
	String stwiseTotalExpndtrPrcntg;
	
	@Value("${topTenNonCompStArea}")
	String topTenNonCompStArea;
	
	@Value("${stwiseSancExpndtr}")
	String stwiseSancExpndtr;
	
	@Value("${getsoilmoistureData}")
	String getsoilmoistureData;
	
	@Value("${getafforestations}")
	String getafforestations;
	
	@Value("${getwaterrenovation}")
	String getwaterrenovation;
	
	@Value("${getprotectiveirrigation}")
	String getprotectiveirrigation;
	
	@Value("${getmanday}")
	String getmanday;
	
	@Value("${getfarmerbenefited}")
	String getfarmerbenefited;
	
	@Value("${getdegradedrainfed}")
	String getdegradedrainfed;
	
	@Value("${getsoilmoistureDistrictData}")
	String getsoilmoistureDistrictData;
	
	@Value("${getDistrictafforestationData}")
	String getDistrictafforestationData;
	
	@Value("${getDistrictwaterrenoData}")
	String getDistrictwaterrenoData;
	
	@Value("${getDistrictprotectiveirrData}")
	String getDistrictprotectiveirrData;
	
	@Value("${getDistrictmandaysData}")
	String getDistrictmandaysData;
	
	@Value("${getDistrictfarmerbenefData}")
	String getDistrictfarmerbenefData;
	
	@Value("${getDistrictdegradedrData}")
	String getDistrictdegradedrData;
	
	@Value("${gettarachievementdata}")
	String gettarachievementdata;
	
	@Value("${stateFinYearHeadDesc}")
	String stateFinYearHeadDesc;
	
	@Value("${stateFinYearAllHeadDesc}")
	String stateFinYearAllHeadDesc;
	
	@Value("${getmonthwiseAchdata}")
	String getmonthwiseAchdata;
	
	@Value("${getmonthwiseAchHordata}")
	String getmonthwiseAchHordata;
	
	@Value("${getmonthwiseAchSAMdata}")
	String getmonthwiseAchSAMdata;
	
	@Value("${getmonthwiseAchWHSNdata}")
	String getmonthwiseAchWHSNdata;
	
	@Value("${getmonthwiseAchRENOdata}")
	String getmonthwiseAchRENOdata;
	
	@Value("${getmonthwiseAchPINdata}")
	String getmonthwiseAchPINdata;
	
	@Value("${getmonthwiseAchPIRdata}")
	String getmonthwiseAchPIRdata;
	
	@Value("${getTarAndAchievdata}")
	String getTarAndAchievdata;
	
	@Value("${getTopStatesdata}")
	String getTopStatesdata;
	
	@Value("${getTopStatesdata2}")
	String getTopStatesdata2;
	
	@Value("${getTopStatesdata3}")
	String getTopStatesdata3;
	
	@Value("${getTopStatesdata4}")
	String getTopStatesdata4;
	
	@Value("${getTopStatesdata5}")
	String getTopStatesdata5;
	
	@Value("${getTopStatesdata6}")
	String getTopStatesdata6;
	
	@Value("${getTopStatesdata7}")
	String getTopStatesdata7;
	
	@Value("${getClassBaseline}")
	String getClassBaseline;
	
	@Value("${getClassOnDate}")
	String getClassOnDate;
	
	@Value("${getIrriBaseline}")
	String getIrriBaseline;
	
	@Value("${getIrrionDate}")
	String getIrrionDate;
	
	@Value("${getOwnerBase}")
	String getOwnerBase;
	
	@Value("${getOwnerDate}")
	String getOwnerDate;
	
	@Value("${getNoofCropBaseData}")
	String getNoofCropBaseData;
	
	@Value("${getNoofCropDateData}")
	String getNoofCropDateData;
	
	@Value("${getBelowStatesdata1}")
	String getBelowStatesdata1;
	
	@Value("${getBelowStatesdata2}")
	String getBelowStatesdata2;
	
	@Value("${getBelowStatesdata3}")
	String getBelowStatesdata3;
	
	@Value("${getBelowStatesdata4}")
	String getBelowStatesdata4;
	
	@Value("${getBelowStatesdata5}")
	String getBelowStatesdata5;
	
	@Value("${getBelowStatesdata6}")
	String getBelowStatesdata6;
	
	@Value("${getBelowStatesdata7}")
	String getBelowStatesdata7;
	
	@Value("${getprogressivedata}")
	String getprogressivedata;
	
	@Value("${getstateprogressivedata}")
	String getstateprogressivedata;
	
	@Value("${getstateprogressivedbdata}")
	String getstateprogressivedbdata;
	
	@Value("${getdistrictprogressivedata}")
	String getdistrictprogressivedata;
	
	@Value("${getdistrictprogressivedbdata}")
	String getdistrictprogressivedbdata;
	
	@Value("${getstatewiseprogressivedata}")
	String getstatewiseprogressivedata;
	
	@Value("${getstatewiseprogressivedbdata}")
	String getstatewiseprogressivedbdata;
	
	@Value("${getdistprogressivedata}")
	String getdistprogressivedata;
	
	@Value("${getdistprogressivedbdata}")
	String getdistprogressivedbdata;
	
	@Value("${getprogressivenewrenodata}")
	String getprogressivenewrenodata;
	
	
	@Value("${getheadName}")
	String getheadName;
	
	@Value("${getdCode}")
	String getdCode;
	
	@Value("${getdName}")
	String getdName;
	
	@Value("${getSName}")
	String getSName;
	
	@Value("${getWatershedYatraInaugurationData}")
	String getWatershedYatraInaugurationData;
	
	@Value("${getWatershedYatraData}")
	String getWatershedYatraData;
	
	@Value("${getPreYatraData}")
	String getPreYatraData;
	
	@Value("${totplannedloc}")
	String totplannedloc;
	
	@Value("${completedyatraloc}")
	String completedyatraloc;
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String,Integer> getAllProject() {
		List<DolrDashboardBean> projectList=new ArrayList<DolrDashboardBean>();
		String hql=allProjectQuery;
		LinkedHashMap<String,Integer> projectMap=new LinkedHashMap<String,Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				projectList =query.list();
				
				for (DolrDashboardBean project : projectList) 
				{
					projectMap.put(project.getStname(), project.getProjcount());
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
        return projectMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String,Integer> getAllProject(Integer central, Integer state) {
		List<DolrDashboardBean> projectList=new ArrayList<DolrDashboardBean>();
		String hql=projectListshare;
		LinkedHashMap<String,Integer> projectMap=new LinkedHashMap<String,Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				query.setInteger("central", central);
				query.setInteger("state", state);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				projectList =query.list();
				
				for (DolrDashboardBean project : projectList) 
				{
					projectMap.put(project.getStname(), project.getProjcount());
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
        return projectMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String,Integer> getAllProjectline() {
		List<DolrDashboardBean> projectList=new ArrayList<DolrDashboardBean>();
		String hql=allProjectlineQuery;
		LinkedHashMap<String,Integer> projectMap=new LinkedHashMap<String,Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				projectList =query.list();
				
				for (DolrDashboardBean project : projectList) 
				{
					projectMap.put(project.getStname(), project.getProjcount());
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
        return projectMap;
	}

	@Override
	public LinkedHashMap<String, BigDecimal> getTopTenStateBlsCompleted() {
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hql = blsCmpltdList;
		LinkedHashMap<String,BigDecimal> map=new LinkedHashMap<>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =query.list();
				
				for (DolrDashboardBean obj : compltdAreaList) 
				{
					map.put(obj.getStname(), obj.getPrcntge_area());
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
	public LinkedHashMap<String, BigDecimal> getTopTenProjLocCmpltnPrcntg() {
		List<DolrDashboardBean> compltdProjLocList=new ArrayList<DolrDashboardBean>();
		String hql = topTenProjLocCmpltnPrcntgList;
		LinkedHashMap<String,BigDecimal> map=new LinkedHashMap<>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdProjLocList =query.list();
				
				for (DolrDashboardBean obj : compltdProjLocList) 
				{
					map.put(obj.getStname(), obj.getProj_loc_prcntge());
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
	public List<DolrDashboardBean> getTopTenClassLandPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> topTenStLandClassprcntgList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenBlsCmpltdList;
		String hql = topTenStLandClassPrcntgList;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							topTenStLandClassprcntgList.add(obj);
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
		return topTenStLandClassprcntgList;
	}

	@Override
	public List<DolrDashboardBean> getCompClassLandAchPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compStLandClassAchList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenBlsCmpltdList;
		String hql = compLandClassAchDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							compStLandClassAchList.add(obj);
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
		return compStLandClassAchList;
	}

	@Override
	public List<DolrDashboardBean> getCompIrriStatusPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compIrriStatusList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenBlsCmpltdList;
		String hql = compIrriStatusDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							compIrriStatusList.add(obj);
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
		return compIrriStatusList;
	}

	@Override
	public List<DolrDashboardBean> getCompIrriStatusAchPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compIrriStatusAchList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenBlsCmpltdList;
		String hql = compIrriStatusAchDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							compIrriStatusAchList.add(obj);
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
		return compIrriStatusAchList;
	}

	@Override
	public List<DolrDashboardBean> getCompNoOfCropPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compNoOfCropList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenBlsCmpltdList;
		String hql = compNoOfCropDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							compNoOfCropList.add(obj);
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
		return compNoOfCropList;
	}

	@Override
	public List<DolrDashboardBean> getCompNoOfCropAchPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compNoOfCropAchList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenBlsCmpltdList;
		String hql = compNoOfCropAchDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							compNoOfCropAchList.add(obj);
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
		return compNoOfCropAchList;
	}

	@Override
	public List<DolrDashboardBean> getStwiseTotalExpPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		String hql = stwiseTotalExpndtrPrcntg;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				list = query.list();
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
		return list;
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompLandPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> topTenStLandClassprcntgList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenNonCompStArea;
		String hql = topTenStLandClassPrcntgList;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
//				for(DolrDashboardBean obj:list) {
//					for(DolrDashboardBean objArea:compltdAreaList) {
//						if(obj.getStname().equals(objArea.getStname())) {
//							topTenStLandClassprcntgList.add(obj);
//						}
//					}
//				}
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
		return list;
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompLandAchPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compStLandClassAchList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenNonCompStArea;
		String hql = compLandClassAchDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							compStLandClassAchList.add(obj);
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
		return compStLandClassAchList;
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompIrriPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compIrriStatusList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenNonCompStArea;
		String hql = compIrriStatusDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
//				for(DolrDashboardBean obj:list) {
//					for(DolrDashboardBean objArea:compltdAreaList) {
//						if(obj.getStname().equals(objArea.getStname())) {
//							compIrriStatusList.add(obj);
//						}
//					}
//				}
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
		return list;
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompIrriAchPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compIrriStatusAchList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenNonCompStArea;
		String hql = compIrriStatusAchDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							compIrriStatusAchList.add(obj);
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
		return compIrriStatusAchList;
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompCropPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compNoOfCropList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenNonCompStArea;
		String hql = compNoOfCropDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
//				for(DolrDashboardBean obj:list) {
//					for(DolrDashboardBean objArea:compltdAreaList) {
//						if(obj.getStname().equals(objArea.getStname())) {
//							compNoOfCropList.add(obj);
//						}
//					}
//				}
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
		return list;
	}

	@Override
	public List<DolrDashboardBean> getTopTenStNonCompCropAchPrcntg() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compNoOfCropAchList=new ArrayList<DolrDashboardBean>();
		List<DolrDashboardBean> compltdAreaList=new ArrayList<DolrDashboardBean>();
		String hqlArea = topTenNonCompStArea;
		String hql = compNoOfCropAchDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				SQLQuery queryArea = session.createSQLQuery(hqlArea);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				queryArea.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				compltdAreaList =queryArea.list();
				list = query.list();
				for(DolrDashboardBean obj:list) {
					for(DolrDashboardBean objArea:compltdAreaList) {
						if(obj.getStname().equals(objArea.getStname())) {
							compNoOfCropAchList.add(obj);
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
		return compNoOfCropAchList;
	}

	@Override
	public List<DolrDashboardBean> getStwiseSancExpndtrRecpt() {
		List<DolrDashboardBean> list=new ArrayList<DolrDashboardBean>();
		String hql = stwiseSancExpndtr;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
				list = query.list();
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
		return list;
	}

	@Override
	public List<DolrDashboardBean> getdashboardtarget(String id) {
		List<DolrDashboardBean> getdashboardtargetdata = new ArrayList<DolrDashboardBean>();
	
		/*
		 * Session session = sessionFactory.getCurrentSession(); String
		 * getdashboardwhsnewdata = getdashboardtarData; String getdashboardwhsreno =
		 * getdashboardwhsrenovate; String getdashboardaffors =
		 * getdashboardafforestation; String gethorti = gethorticulture; String
		 * getdegradedl = getdegradedland; String getrainf = getrainfed; String
		 * getnilsingle = getnilsingledata;
		 * 
		 * SQLQuery query = null; try { session.beginTransaction();
		 * if(id.equals("whsnew")) { query =
		 * session.createSQLQuery(getdashboardwhsnewdata); }
		 * if(id.equals("whsrenovate")) { query =
		 * session.createSQLQuery(getdashboardwhsreno); } if(id.equals("afforestation"))
		 * { query = session.createSQLQuery(getdashboardaffors); }
		 * if(id.equals("horticulture")) { query = session.createSQLQuery(gethorti); }
		 * if(id.equals("degradedland")) { query = session.createSQLQuery(getdegradedl);
		 * } if(id.equals("rainfed")) { query = session.createSQLQuery(getrainf); }
		 * if(id.equals("nilsingle")) { query = session.createSQLQuery(getnilsingle); }
		 * 
		 * 
		 * query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class))
		 * ; getdashboardtargetdata = query.list(); session.getTransaction().commit(); }
		 * catch (HibernateException e) { System.err.print("Hibernate error");
		 * e.printStackTrace(); session.getTransaction().rollback(); } catch (Exception
		 * ex) {
		 * 
		 * ex.printStackTrace(); session.getTransaction().rollback(); } return
		 * getdashboardtargetdata;
		 */
		return null;
	}

	@Override
	public List<DolrDashboardBean> getHomePagetarget(String id) {
		List<DolrDashboardBean> getdashboardtargetdata = new ArrayList<DolrDashboardBean>();
		
		Session session = sessionFactory.getCurrentSession();
		String getsoilmoisturedata = getsoilmoistureData;
		String getafforestation = getafforestations;
		String getwaterreno = getwaterrenovation;
		String getprotectiveirr = getprotectiveirrigation;
		String getmandays = getmanday;
		String getfarmerbenef = getfarmerbenefited;
		String getdegradedr = getdegradedrainfed;
		
		SQLQuery query = null;
		try {
			session.beginTransaction();
            if(id.equals("soilmoisture"))
            {
			query = session.createSQLQuery(getsoilmoisturedata);
            }
            if(id.equals("afforestation"))
            {
            query = session.createSQLQuery(getafforestation);
            }
            if(id.equals("waterreno"))
            {
            query = session.createSQLQuery(getwaterreno);
            }
            if(id.equals("protectiveirr"))
            {
            query = session.createSQLQuery(getprotectiveirr);
            }
            if(id.equals("mandays"))
            {
            query = session.createSQLQuery(getmandays);
            }
            if(id.equals("farmerbenef"))
            {
            query = session.createSQLQuery(getfarmerbenef);
            }
            if(id.equals("degradedr"))
            {
            query = session.createSQLQuery(getdegradedr);
            }
           
            
			query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
			getdashboardtargetdata = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getdashboardtargetdata;
	}

	@Override
	public List<DolrDashboardBean> getCircleDistricttarget(String id, String activity) {
		List<DolrDashboardBean> getCircleDistrictdata = new ArrayList<DolrDashboardBean>();
		Session session = sessionFactory.getCurrentSession();
		String getsoilmoistureDistrictdata = getsoilmoistureDistrictData;
		String getDistrictafforestation = getDistrictafforestationData;
		String getDistrictwaterreno = getDistrictwaterrenoData;
		String getDistrictprotectiveirr = getDistrictprotectiveirrData;
		String getDistrictmandays = getDistrictmandaysData;
		String getDistrictfarmerbenef = getDistrictfarmerbenefData;
		String getDistrictdegradedr = getDistrictdegradedrData;
		
		SQLQuery query = null;
		try {
			session.beginTransaction();
            if(activity.equals("soilmoisture"))
            {
			query = session.createSQLQuery(getsoilmoistureDistrictdata);
            query.setInteger("id", Integer.parseInt(id));
            }
            if(activity.equals("afforestation"))
            {
            query = session.createSQLQuery(getDistrictafforestation);
            query.setInteger("id", Integer.parseInt(id));
            }
            if(activity.equals("waterreno"))
            {
            query = session.createSQLQuery(getDistrictwaterreno);
            query.setInteger("id", Integer.parseInt(id));
            }
            if(activity.equals("protectiveirr"))
            {
            query = session.createSQLQuery(getDistrictprotectiveirr);
            query.setInteger("id", Integer.parseInt(id));
            }
            if(activity.equals("mandays"))
            {
            query = session.createSQLQuery(getDistrictmandays);
            query.setInteger("id", Integer.parseInt(id));
            }
            if(activity.equals("farmerbenef"))
            {
            query = session.createSQLQuery(getDistrictfarmerbenef);
            query.setInteger("id", Integer.parseInt(id));
            }
            if(activity.equals("degradedr"))
            {
            query = session.createSQLQuery(getDistrictdegradedr);
            query.setInteger("id", Integer.parseInt(id));
            }
            
            query.setResultTransformer(Transformers.aliasToBean(DolrDashboardBean.class));
            getCircleDistrictdata = query.list();
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getCircleDistrictdata;
	}

	@Override
	public LinkedHashMap<Integer, List<TargetAchDashboardBean>> gettarachcompdata() {
		LinkedHashMap<Integer, List<TargetAchDashboardBean>> map = new LinkedHashMap<Integer, List<TargetAchDashboardBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = gettarachievementdata;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			List<TargetAchDashboardBean> list = query.list();
			for (TargetAchDashboardBean row : list){
				//System.out.println("userId: "+row.getHeadcode());
			}
			List<TargetAchDashboardBean> sublist = new ArrayList<TargetAchDashboardBean>();
			if ((list != null) && (list.size() > 0)) {
				for (TargetAchDashboardBean row : list){
					if (!map.containsKey(row.getHeadcode())) {
						sublist = new ArrayList<TargetAchDashboardBean>();
						sublist.add(row);
						map.put(row.getHeadcode(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getHeadcode(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, List<TargetAchDashboardBean>> getheadDescFinYearState(String finyear, String state) {
		LinkedHashMap<Integer, List<TargetAchDashboardBean>> map = new LinkedHashMap<Integer, List<TargetAchDashboardBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = stateFinYearHeadDesc;
			String hqlAll =  stateFinYearAllHeadDesc;
			    if(Integer.parseInt(finyear) != 0 && Integer.parseInt(state) !=0)
			    {
			    query = session.createSQLQuery(hql);	
			    }else {
				query = session.createSQLQuery(hqlAll);
			    }
				query.setInteger("finyear", Integer.parseInt(finyear));
				query.setInteger("state", Integer.parseInt(state));
			
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			List<TargetAchDashboardBean> list = query.list();
			for (TargetAchDashboardBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<TargetAchDashboardBean> sublist = new ArrayList<TargetAchDashboardBean>();
			if ((list != null) && (list.size() > 0)) {
				for (TargetAchDashboardBean row : list){
					if (!map.containsKey(row.getStcode())) {
						sublist = new ArrayList<TargetAchDashboardBean>();
						sublist.add(row);
						map.put(row.getStcode(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getStcode(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch(Integer finYear, Integer state) {
List<TargetAchDashboardBean> findactdesc=new ArrayList<TargetAchDashboardBean>();
		
		String hql=getmonthwiseAchdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyear", finYear);
			query.setInteger("state", state);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
		
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch2(Integer finYear, Integer state) {
    List<TargetAchDashboardBean> findactdesc=new ArrayList<TargetAchDashboardBean>();
		
		String hql=getmonthwiseAchHordata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyear", finYear);
			query.setInteger("state", state);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch3(Integer finYear, Integer state) {
		List<TargetAchDashboardBean> findactdesc=new ArrayList<TargetAchDashboardBean>();
		String hql=getmonthwiseAchSAMdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyear", finYear);
			query.setInteger("state", state);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch4(Integer finYear, Integer state) {
		List<TargetAchDashboardBean> findactdesc=new ArrayList<TargetAchDashboardBean>();
		String hql=getmonthwiseAchWHSNdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyear", finYear);
			query.setInteger("state", state);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch5(Integer finYear, Integer state) {
		List<TargetAchDashboardBean> findactdesc=new ArrayList<TargetAchDashboardBean>();
		String hql=getmonthwiseAchRENOdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyear", finYear);
			query.setInteger("state", state);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch6(Integer finYear, Integer state) {
		List<TargetAchDashboardBean> findactdesc=new ArrayList<TargetAchDashboardBean>();
		String hql=getmonthwiseAchPINdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyear", finYear);
			query.setInteger("state", state);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
	}

	@Override
	public List<TargetAchDashboardBean> getMonthWiseAch7(Integer finYear, Integer state) {
		List<TargetAchDashboardBean> findactdesc=new ArrayList<TargetAchDashboardBean>();
		String hql=getmonthwiseAchPIRdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyear", finYear);
			query.setInteger("state", state);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
	}

	@Override
	public List<TargetAchDashboardBean> gettarachdata(Integer finYear, Integer state) {
		
		List<TargetAchDashboardBean> findactdesc=new ArrayList<TargetAchDashboardBean>();
		String hql=getTarAndAchievdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setInteger("state", state);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getTopStatesdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates2(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getTopStatesdata2;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates3(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getTopStatesdata3;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates4(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getTopStatesdata4;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates5(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getTopStatesdata5;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates6(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getTopStatesdata6;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getTopStates7(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getTopStatesdata7;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getClassificationBase() {
		List<TargetAchDashboardBean> findBaselineData=new ArrayList<TargetAchDashboardBean>();
		String hql=getClassBaseline;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findBaselineData = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findBaselineData;
	}

	@Override
	public List<TargetAchDashboardBean> getClassificationDate() {
		List<TargetAchDashboardBean> findAsOnDateData=new ArrayList<TargetAchDashboardBean>();
		String hql=getClassOnDate;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findAsOnDateData = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findAsOnDateData;
	}

	@Override
	public List<TargetAchDashboardBean> getIrrigationBase() {
		List<TargetAchDashboardBean> findIrrigationBase=new ArrayList<TargetAchDashboardBean>();
		String hql=getIrriBaseline;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findIrrigationBase = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findIrrigationBase;
	}

	@Override
	public List<TargetAchDashboardBean> getIrrigationonDate() {
		List<TargetAchDashboardBean> findIrrigationDate=new ArrayList<TargetAchDashboardBean>();
		String hql=getIrrionDate;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findIrrigationDate = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findIrrigationDate;
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getBelowStatesdata1;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates2(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getBelowStatesdata2;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates3(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getBelowStatesdata3;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates4(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getBelowStatesdata4;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates5(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getBelowStatesdata5;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates6(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getBelowStatesdata6;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getBelowStates7(Integer finYear) {
		List<TargetAchDashboardBean> findTopStates=new ArrayList<TargetAchDashboardBean>();
		String hql=getBelowStatesdata7;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finYear", finYear);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findTopStates = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findTopStates;
	}

	@Override
	public List<TargetAchDashboardBean> getProgressiveData(Integer headCode, Integer stCode) {
		List<TargetAchDashboardBean> findprogdetails=new ArrayList<TargetAchDashboardBean>();
		Integer headCode1 = 0;
		String hql=getprogressivedata;
		String hql2=getprogressivenewrenodata;
		SQLQuery query = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			if(headCode == 4)
			{
				query = session.createSQLQuery(hql2);
				query.setInteger("headCode1", 5);
				query.setInteger("headCode", headCode);
			    query.setInteger("stCode", stCode);
			}
			if(headCode == 6)
			{
				query = session.createSQLQuery(hql2);
				query.setInteger("headCode1", 7);
				query.setInteger("headCode", headCode);
			    query.setInteger("stCode", stCode);
			}
			else if(headCode !=4 && headCode !=6){
				 query = session.createSQLQuery(hql);
				 query.setInteger("headCode", headCode);
				 query.setInteger("stCode", stCode);
			}
				
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findprogdetails = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findprogdetails;
	}

	@Override
	public List<TargetAchDashboardBean> getstatewiseProgressive(Integer headCode) {
		List<TargetAchDashboardBean> findprogdetails=new ArrayList<TargetAchDashboardBean>();
		String hql=getstateprogressivedata;
		String hql1=getstateprogressivedbdata;
		SQLQuery query = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			if(headCode == 4)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("headCode", headCode);
				query.setInteger("headCode1", 5);
			}
			if(headCode == 6)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("headCode", headCode);
				query.setInteger("headCode1", 7);
			}
			else if(headCode != 4 && headCode !=6){
		    query = session.createSQLQuery(hql);
			query.setInteger("headCode", headCode);
			}
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findprogdetails = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findprogdetails;
	}

	@Override
	public List<TargetAchDashboardBean> getdistrictwiseProgressive(Integer headCode, Integer stCode) {
		List<TargetAchDashboardBean> findprogdetails=new ArrayList<TargetAchDashboardBean>();
		String hql=getdistrictprogressivedata;
		String hql1=getdistrictprogressivedbdata;
		SQLQuery query = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			if(headCode == 4)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("headCode", headCode);
				query.setInteger("headCode1", 5);
				query.setInteger("stCode", stCode);
			}
			if(headCode == 6)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("headCode", headCode);
				query.setInteger("headCode1", 7);
				query.setInteger("stCode", stCode);
			}
			else if(headCode != 4 && headCode !=6){
		    query = session.createSQLQuery(hql);
			query.setInteger("headCode", headCode);
			query.setInteger("stCode", stCode);
			}
		query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findprogdetails = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findprogdetails;
	}

	@Override
	public List<TargetAchDashboardBean> getDistrictProgressiveData(Integer headcode, Integer dcode) {
		List<TargetAchDashboardBean> finddistprogdetails=new ArrayList<TargetAchDashboardBean>();
		String hql=getdistprogressivedata;
		String hql1=getdistprogressivedbdata;
		SQLQuery query = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			if(headcode == 4)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("headCode", headcode);
				query.setInteger("headCode1", 5);
				query.setInteger("dCode", dcode);
			}
			if(headcode == 6)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("headCode", headcode);
				query.setInteger("headCode1", 7);
				query.setInteger("dCode", dcode);
			}
			else if(headcode != 4 && headcode !=6){
		    query = session.createSQLQuery(hql);
			query.setInteger("headCode", headcode);
			query.setInteger("dCode", dcode);
			}
			
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			finddistprogdetails = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return finddistprogdetails;
	}

	@Override
	public List<TargetAchDashboardBean> getheadname(Integer headCode) {
		List<TargetAchDashboardBean> findheadname=new ArrayList<TargetAchDashboardBean>();
		String hql=getheadName;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("headCode", headCode);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findheadname = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findheadname;
	}

	@Override
	public List<TargetAchDashboardBean> getdistrictfirstRecords(Integer headCode, Integer stCode) {
		List<TargetAchDashboardBean> getdata=new ArrayList<TargetAchDashboardBean>();
		String hql=getdCode;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query1 = session.createSQLQuery(hql);
		    query1.setInteger("stCode", stCode);
		    Integer dcode = Integer.parseInt(query1.list().get(0).toString());
		    System.out.println("dcode:" +dcode);
		    if(dcode > 0) {
		    SQLQuery query = session.createSQLQuery(getdistprogressivedata);
			query.setInteger("headCode", headCode);
			query.setInteger("dCode", dcode);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			getdata = query.list();
		    }
			session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
		
		return getdata;
	}

	@Override
	public List<TargetAchDashboardBean> getDistrictNameData(Integer dcode) {
		List<TargetAchDashboardBean> getdata=new ArrayList<TargetAchDashboardBean>();
		String hql=getdName;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
		    query.setInteger("dcode", dcode);
		    query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			getdata = query.list();
		    
			session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
		
		return getdata;
		    
	}

	@Override
	public List<TargetAchDashboardBean> getOwnershipBase() {
		List<TargetAchDashboardBean> findOwnershipBase=new ArrayList<TargetAchDashboardBean>();
		String hql=getOwnerBase;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findOwnershipBase = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findOwnershipBase;
	}

	@Override
	public List<TargetAchDashboardBean> getOwnershipDate() {
		List<TargetAchDashboardBean> findOwnershipDate=new ArrayList<TargetAchDashboardBean>();
		String hql=getOwnerDate;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findOwnershipDate = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findOwnershipDate;
	}

	@Override
	public List<TargetAchDashboardBean> getNoofCropBase() {
		List<TargetAchDashboardBean> findNoofCropBase=new ArrayList<TargetAchDashboardBean>();
		String hql=getNoofCropBaseData;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findNoofCropBase = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findNoofCropBase;
	}

	@Override
	public List<TargetAchDashboardBean> getNoofCropDate() {
		List<TargetAchDashboardBean> findNoofCropDate=new ArrayList<TargetAchDashboardBean>();
		String hql=getNoofCropDateData;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			findNoofCropDate = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findNoofCropDate;
	}

	@Override
	public List<TargetAchDashboardBean> getStateNameData(Integer scode) {
		List<TargetAchDashboardBean> getdata=new ArrayList<TargetAchDashboardBean>();
		String hql=getSName;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
		    query.setInteger("scode", scode);
		    query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			getdata = query.list();
		    
			session.getTransaction().commit();
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
		
		return getdata;
	}

	@Override
	public List<TargetAchDashboardBean> getStateProgressiveData(Integer headcode, Integer scode) {
		List<TargetAchDashboardBean> finddistprogdetails=new ArrayList<TargetAchDashboardBean>();
		String hql=getstatewiseprogressivedata;
		String hql1=getstatewiseprogressivedbdata;
		SQLQuery query = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			if(headcode == 4)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("headCode", headcode);
				query.setInteger("headCode1", 5);
				query.setInteger("scode", scode);
			}
			if(headcode == 6)
			{
				query = session.createSQLQuery(hql1);
				query.setInteger("headCode", headcode);
				query.setInteger("headCode1", 7);
				query.setInteger("scode", scode);
			}
			else if(headcode != 4 && headcode !=6){
		    query = session.createSQLQuery(hql);
			query.setInteger("headCode", headcode);
			query.setInteger("scode", scode);
			}
			
			query.setResultTransformer(Transformers.aliasToBean(TargetAchDashboardBean.class));
			finddistprogdetails = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return finddistprogdetails;
	}

	@Override
	public Map<String, List<WatrshdInagrtnPreYtraDashBean>> getWatrshdInagrtnPreYtraData() {
		String inghql=getWatershedYatraInaugurationData;
		String wtrhql=getWatershedYatraData;
		String prehql=getPreYatraData;
		Map<String, List<WatrshdInagrtnPreYtraDashBean>> map = new LinkedHashMap<String, List<WatrshdInagrtnPreYtraDashBean>>();
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = null;
		try {
			List<WatrshdInagrtnPreYtraDashBean> list = new ArrayList<>();
			session.beginTransaction();
			query = session.createSQLQuery(inghql);
			query.setResultTransformer(Transformers.aliasToBean(WatrshdInagrtnPreYtraDashBean.class));
			list = query.list();
			
			List<WatrshdInagrtnPreYtraDashBean> villist = new ArrayList<>();
			query = session.createSQLQuery(wtrhql);
			query.setResultTransformer(Transformers.aliasToBean(WatrshdInagrtnPreYtraDashBean.class));
			villist = query.list();
			
			WatrshdInagrtnPreYtraDashBean bean = new WatrshdInagrtnPreYtraDashBean();
			bean.setTotstates(villist.get(0).getTotstates());
			bean.setTotparticipants(list.get(0).getTotparticipants().add(villist.get(0).getTotparticipants()));
			bean.setTotarexperiencedpeople(villist.get(0).getTotarexperiencedpeople());
			bean.setTotquizparticipants(villist.get(0).getTotquizparticipants());
			bean.setTotyatraloc(villist.get(0).getTotyatraloc());
			bean.setTotbhumipujanworks(list.get(0).getTotbhumipujanworks().add(villist.get(0).getTotbhumipujanworks()));
			bean.setTotlokarpanworks(list.get(0).getTotlokarpanworks().add(villist.get(0).getTotlokarpanworks()));
			bean.setTotshramdannlocationno(list.get(0).getTotshramdannlocationno().add(villist.get(0).getTotshramdannlocationno()));
			bean.setTotawarddistribution(list.get(0).getTotawarddistribution().add(villist.get(0).getTotawarddistribution()));
			bean.setTotplantation(list.get(0).getTotplantation().add(villist.get(0).getTotplantation()));
			list = new ArrayList<>();
			list.add(bean);
			map.put("wtr",list);
			
			list = new ArrayList<>();
			query = session.createSQLQuery(prehql);
			query.setResultTransformer(Transformers.aliasToBean(WatrshdInagrtnPreYtraDashBean.class));
			list = query.list();
			map.put("pre",list);
			session.getTransaction().commit();
			
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public List<WatershedYatraDashboardChartBean> getWtrshdYtraChartData() {
		
		String plochql = totplannedloc;
		String clochql = completedyatraloc;
		List<WatershedYatraDashboardChartBean> list = new ArrayList<WatershedYatraDashboardChartBean>();
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = null;
		try {
			WatershedYatraDashboardChartBean bean = new WatershedYatraDashboardChartBean();
			session.beginTransaction();
			query = session.createSQLQuery(plochql);
			query.setResultTransformer(Transformers.aliasToBean(WatershedYatraDashboardChartBean.class));
			list = query.list();
			Integer val1 = 200;
//			bean.setTotplannedloc(list.get(0).getTotplannedloc().multiply(BigInteger.valueOf(val1)));
			bean.setTotplannedloc(list.get(0).getTotplannedloc());
			
			query = session.createSQLQuery(clochql);
			query.setResultTransformer(Transformers.aliasToBean(WatershedYatraDashboardChartBean.class));
			list = query.list();
			bean.setCompletedyatraloc(list.get(0).getCompletedyatraloc());
			Integer val = 10;
			bean.setTotplannedact(list.get(0).getCompletedyatraloc().multiply(BigInteger.valueOf(val)));
			bean.setTotcompletedact(list.get(0).getTotcompletedact());
			list = new ArrayList<>();
			list.add(bean);
			
			session.getTransaction().commit();
			
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	
}
