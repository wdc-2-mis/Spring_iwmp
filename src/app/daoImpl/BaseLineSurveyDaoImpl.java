package app.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.BaseLineSurveyBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.dao.BaseLineSurveyDao;
import app.model.BaseLineSurveyActivityDetails;
import app.model.BaseLineSurveyMain;
import app.model.IwmpMProject;
import app.model.UserMap;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.master.WdcpmksyMOutcome;
import app.model.master.WdcpmksyMOutcomeDetail;

@Repository("baseLineSurveyDao")
public class BaseLineSurveyDaoImpl implements BaseLineSurveyDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getIwmpMPhyActivity}")
	String getIwmpMPhyActivity;
	
	@Value("${getWdcpmksyMOutcome}")
	String getWdcpmksyMOutcome;
	
	@Value("${getWdcpmksyMOutcomeDetail}")
	String getWdcpmksyMOutcomeDetail;
	
	@Value("${getPhysicalHead}")
	String getPhysicalHead;
	
	@Value("${getSanctionedArea}")
	String getSanctionedArea;
	
	@Value("${getPreFilledBaseLineSurveyData}")
	String getPreFilledData;
	
	@Value("${deleteBaseLineSurveyActivityDetails}")
	String deleteBaseLineSurveyActivityDetails;
	
	@Value("${deleteBaseLineSurvey}")
	String deleteBaseLineSurvey;
	
	@Value("${completeBaseLineSurvey}")
	String completeBaseLineSurvey;
	
	@Value("${getProjectforBaseLineSurveySLNA}")
	String getProjectforSLNA;
	
	@Value("${getBaseLineSurveyDetailByDistCode}")
	String getBaseLineSurveyDetailByDistCode;
	
	@Value("${getBaseLineSurveyDetail}")
	String getBaseLineSurveyDetail;
	
	@Value("${getBaseLineSurveyDetailByStCodeDistCode}")
	String getBaseLineSurveyDetailByStCodeDistCode;
	
	@Value("${getBaseLineSurveyDetailByProjId}")
	String getBaseLineSurveyDetailByProjId;
	
	
	@Override
	public List<IwmpMPhyActivity> getPhysicalActivity() {
		// TODO Auto-generated method stub
		List<IwmpMPhyActivity> result = new ArrayList<IwmpMPhyActivity>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getIwmpMPhyActivity;
			ses.beginTransaction();
			//Integer code[]={1,2,3,5,7};
			query = ses.createQuery(hql);
			//query.setParameterList("headCode",code );
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}

	@Override
	public List<WdcpmksyMOutcome> getOutcomeHead() {
		// TODO Auto-generated method stub
		List<WdcpmksyMOutcome> result = new ArrayList<WdcpmksyMOutcome>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getWdcpmksyMOutcome;
			ses.beginTransaction();
			Integer code[]={1,2,3,4,5};
			query = ses.createQuery(hql);
			query.setParameterList("headCode",code );
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}

	@Override
	public List<WdcpmksyMOutcomeDetail> getOutcomeActivity() {
		// TODO Auto-generated method stub
		List<WdcpmksyMOutcomeDetail> result = new ArrayList<WdcpmksyMOutcomeDetail>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getWdcpmksyMOutcomeDetail;
			ses.beginTransaction();
			Integer code[]={1,2,3,4,5};
			query = ses.createQuery(hql);
			query.setParameterList("headCode",code );
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}

	@Override
	public List<IwmpMPhyHeads> getPhysicalHead() {
		// TODO Auto-generated method stub
		List<IwmpMPhyHeads> result = new ArrayList<IwmpMPhyHeads>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getPhysicalHead;
			ses.beginTransaction();
			//Integer code[]={1,2,3,5,7};
			query = ses.createQuery(hql);
			//query.setParameterList("headCode",code );
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}

	@Override
	public String saveDataAsDraft(BaseLineSurveyMain main,List<BaseLineSurveyActivityDetails> finalList) {
		// TODO Auto-generated method stub
		String result = "fail";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query1 = null;
		SQLQuery query2 = null;
		try {
			session.beginTransaction();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			String sql1 = deleteBaseLineSurveyActivityDetails;
			String sql2=deleteBaseLineSurvey;
			query1 = session.createSQLQuery(sql1);
			query2 = session.createSQLQuery(sql2);
			query1.setInteger("projId",main.getIwmpMProject().getProjectId());
			query2.setInteger("projId",main.getIwmpMProject().getProjectId());
			int a = query1.executeUpdate();
			int b = query2.executeUpdate();
			main.setRequestIp(ipAddr);
			main.setCreatedOn(new Date());
			if(a>=0 && b>=0){
			session.save(main);
			for(BaseLineSurveyActivityDetails blsActDet : finalList) {
				//blsActDet.setBaseLineSurveyMain(main);
				BaseLineSurveyActivityDetails actDetails = new BaseLineSurveyActivityDetails();
				actDetails=blsActDet;
				actDetails.setBaseLineSurveyMain(main);
				session.save(actDetails);
				//session.evict(actDetails);
				
			}
			session.getTransaction().commit();
			result="success";
			}
			
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			//session.getTransaction().commit();
		}
		return result;
	}

	@Override
	public String getSanctionedArea(Integer projId) {
		// TODO Auto-generated method stub
		String result = "0";
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getSanctionedArea;
			ses.beginTransaction();
			query = ses.createQuery(hql);
			query.setInteger("projId",projId);
			result =query.list().get(0).toString();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return result;
	}

	@Override
	public List<BaseLineSurveyBean> getPreFilledData(Integer projId) {
		// TODO Auto-generated method stub
		String result = "0";
		List<BaseLineSurveyBean> list = new ArrayList<BaseLineSurveyBean>();
		Session ses = sessionFactory.getCurrentSession();
		SQLQuery query = null;
		try {
			String hql = getPreFilledData;
			ses.beginTransaction();
			query = ses.createSQLQuery(hql);
			query.setInteger("projId",projId);
			query.setResultTransformer(Transformers.aliasToBean(BaseLineSurveyBean.class));
			list =query.list();
			
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			ses.getTransaction().rollback();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return list;
	}

	@Override
	public String completeBaseLineSurvey(Integer projId) {
		// TODO Auto-generated method stub
		String result = "fail";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = completeBaseLineSurvey;
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("projId",projId);
			
			int a = query.executeUpdate();
		if(a>0) {
			result="success";
			session.getTransaction().commit();
		}
			
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			//session.getTransaction().commit();
		}
		return result;
	}
	

	@Override
	public LinkedHashMap<Integer, String> getProjectforSLNA(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpMProject> rows = new ArrayList<IwmpMProject>();
		String hql=getProjectforSLNA;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId",registrationId);
			rows = query.list();
			  for(IwmpMProject row : rows){
				 // System.out.println("Size by regId: "+row.getProjectCd()+" regId "+regId);
				  map.put(row.getProjectId(), row.getProjName());
			  }
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return map;
	}

	@Override
	public List<BaseLineSurveyBean> getBaseLineSurveyDetailByDistCode(Integer distCode) {
		// TODO Auto-generated method stub
		List<BaseLineSurveyBean> rows = new ArrayList<BaseLineSurveyBean>();
		List<BaseLineSurveyMain> mainList = new ArrayList<BaseLineSurveyMain>();
		String hql=getBaseLineSurveyDetailByDistCode;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("distCode",distCode);
			//query.setResultTransformer(Transformers.aliasToBean(BaseLineSurveyBean.class));
			mainList = query.list();
			for(BaseLineSurveyMain main : mainList) {
				BaseLineSurveyBean bean = new BaseLineSurveyBean();
				bean.setDist_name(main.getIwmpMProject().getIwmpDistrict().getDistName());
				bean.setProj_name(main.getIwmpMProject().getProjName());
				bean.setProj_id(main.getIwmpMProject().getProjectId());
				bean.setSanctioned_area(main.getIwmpMProject().getAreaProposed());
				bean.setTotal_geo_area(main.getTotalGeoArea());
				bean.setArea_covering_type(main.getAreaCoveringType());
				if(main.getBaseLineSurveyActivityDetailses()!=null)
					for(BaseLineSurveyActivityDetails bad : main.getBaseLineSurveyActivityDetailses()) {
						/*
						 * if(bad.getWdcpmksyMOutcome()!=null &&
						 * bad.getWdcpmksyMOutcome().getOutcomeId()==1)
						 * bean.setTreatable_area(bad.getAreaOfActivity());
						 * if(bad.getWdcpmksyMOutcome()!=null &&
						 * bad.getWdcpmksyMOutcome().getOutcomeId()==2)
						 * bean.setTreatable_area((bean.getTreatable_area()==null?BigDecimal.ZERO:bean.
						 * getTreatable_area()).add(bad.getAreaOfActivity()));
						 */
						if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()<=3)
							bean.setTreatable_area((bean.getTreatable_area()==null?BigDecimal.ZERO:bean.getTreatable_area()).add(bad.getAreaOfActivity()));
						if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==4)
							bean.setArea_covered_under_diversified_crops((bean.getArea_covered_under_diversified_crops()==null?BigDecimal.ZERO:bean.getArea_covered_under_diversified_crops()).add(bad.getAreaOfActivity()));
						if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==5)
							bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bad.getAreaOfActivity()));
						if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==1)
							bean.setArea_brought_under_afforestation((bean.getArea_brought_under_afforestation()==null?BigDecimal.ZERO:bean.getArea_brought_under_afforestation()).add(bad.getAreaOfActivity()));
						if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==2)
							bean.setArea_brought_under_horticulture((bean.getArea_brought_under_horticulture()==null?BigDecimal.ZERO:bean.getArea_brought_under_horticulture()).add(bad.getAreaOfActivity()));
						if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==5)
							bean.setWater_harvesting_structure((bean.getWater_harvesting_structure()==null?BigDecimal.ZERO:bean.getWater_harvesting_structure()).add(bad.getAreaOfActivity()));
						if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==7)
							bean.setArea_brought_under_protective_irrigation((bean.getArea_brought_under_protective_irrigation()==null?BigDecimal.ZERO:bean.getArea_brought_under_protective_irrigation()).add(bad.getAreaOfActivity()));
						if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==3)
							bean.setArea_covered_with_soil_and_moisture_conservation((bean.getArea_covered_with_soil_and_moisture_conservation()==null?BigDecimal.ZERO:bean.getArea_covered_with_soil_and_moisture_conservation()).add(bad.getAreaOfActivity()));
						
					}
				bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(main.getTotalGrossCroppedArea().add(main.getTotalNetSownArea())));
				bean.setNo_of_household(main.getTotalSc()+main.getTotalSt()+main.getTotalOthers()+main.getNoOfLandlessHousehold()+main.getNoOfBplHousehold()+main.getSmallFarmerHousehold()+main.getMarginalFarmerHousehold()+main.getPersonDaysMigration());
				bean.setStname(main.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName());
				rows.add(bean);
			}
			session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return rows;
	}

	@Override
	public List<BaseLineSurveyBean> getBaseLineSurveyDetailByProjId(Integer projId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String result = "0";
				List<BaseLineSurveyBean> list = new ArrayList<BaseLineSurveyBean>();
				Session ses = sessionFactory.getCurrentSession();
				SQLQuery query = null;
				try {
					String hql = getBaseLineSurveyDetailByProjId;
					ses.beginTransaction();
					query = ses.createSQLQuery(hql);
					query.setInteger("projId",projId);
					query.setResultTransformer(Transformers.aliasToBean(BaseLineSurveyBean.class));
					list =query.list();
					
				} 
				catch (HibernateException e) 
				{
					System.err.print("Hibernate error" + e.getCause());
					System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
					e.printStackTrace();
					ses.getTransaction().rollback();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
					ses.getTransaction().rollback();
				} 
				finally {
					ses.getTransaction().commit();
				}
				return list;
	}

	@Override
	public LinkedHashMap<String,BaseLineSurveyBean> getBaseLineSurveyDetail() {
		// TODO Auto-generated method stub
		List<BaseLineSurveyBean> rows = new ArrayList<BaseLineSurveyBean>();
		List<BaseLineSurveyMain> mainList = new ArrayList<BaseLineSurveyMain>();
		LinkedHashMap<String,BaseLineSurveyBean> map = new LinkedHashMap<String,BaseLineSurveyBean>();
		
		String hql=getBaseLineSurveyDetail;
		Integer projectcount=0;
		LinkedHashMap<String,Integer> count =new LinkedHashMap<String,Integer>();
		List<String> stname= new ArrayList<String>();
		List<String> distname= new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			mainList = query.list();
			BaseLineSurveyBean bean = new BaseLineSurveyBean();
			for(BaseLineSurveyMain bsmain : mainList) {
				if(!map.containsKey(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName())) {
					bean = new BaseLineSurveyBean();
					projectcount=1;
					
					bean.setTotalproject(projectcount);
					bean.setDist_name(bsmain.getIwmpMProject().getIwmpDistrict().getDistName());
					bean.setDist_code(bsmain.getIwmpMProject().getIwmpDistrict().getDcode());
					bean.setProj_name(bsmain.getIwmpMProject().getProjName());
					bean.setProj_id(bsmain.getIwmpMProject().getProjectId());
					bean.setSanctioned_area((bean.getSanctioned_area()==null?BigDecimal.ZERO:bean.getSanctioned_area()).add(bsmain.getIwmpMProject().getAreaProposed()));
					bean.setTotal_geo_area((bean.getTotal_geo_area()==null?BigDecimal.ZERO:bean.getTotal_geo_area()).add(bsmain.getTotalGeoArea()));
					bean.setArea_covering_type((bean.getArea_covering_type()==null?bsmain.getAreaCoveringType():(bean.getArea_covering_type()+","+bsmain.getAreaCoveringType())));
					if(bsmain.getBaseLineSurveyActivityDetailses()!=null)
						for(BaseLineSurveyActivityDetails bad : bsmain.getBaseLineSurveyActivityDetailses()) {
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()<=3)
								bean.setTreatable_area((bean.getTreatable_area()==null?BigDecimal.ZERO:bean.getTreatable_area()).add(bad.getAreaOfActivity()));
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==4)
								bean.setArea_covered_under_diversified_crops((bean.getArea_covered_under_diversified_crops()==null?BigDecimal.ZERO:bean.getArea_covered_under_diversified_crops()).add(bad.getAreaOfActivity()));
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==5)
								bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==1)
								bean.setArea_brought_under_afforestation((bean.getArea_brought_under_afforestation()==null?BigDecimal.ZERO:bean.getArea_brought_under_afforestation()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==2)
								bean.setArea_brought_under_horticulture((bean.getArea_brought_under_horticulture()==null?BigDecimal.ZERO:bean.getArea_brought_under_horticulture()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==5)
								bean.setWater_harvesting_structure((bean.getWater_harvesting_structure()==null?BigDecimal.ZERO:bean.getWater_harvesting_structure()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==7)
								bean.setArea_brought_under_protective_irrigation((bean.getArea_brought_under_protective_irrigation()==null?BigDecimal.ZERO:bean.getArea_brought_under_protective_irrigation()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==3)
								bean.setArea_covered_with_soil_and_moisture_conservation((bean.getArea_covered_with_soil_and_moisture_conservation()==null?BigDecimal.ZERO:bean.getArea_covered_with_soil_and_moisture_conservation()).add(bad.getAreaOfActivity()));
							
						}
					bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bsmain.getTotalGrossCroppedArea().add(bsmain.getTotalNetSownArea())));
					bean.setNo_of_household(bean.getNo_of_household()==null?(bsmain.getTotalSc()+bsmain.getTotalSt()+bsmain.getTotalOthers()+bsmain.getTotalPopulation()+bsmain.getNoOfLandlessHousehold()+bsmain.getNoOfBplHousehold()+bsmain.getSmallFarmerHousehold()+bsmain.getMarginalFarmerHousehold()+bsmain.getPersonDaysMigration()):(bean.getNo_of_household()+bsmain.getTotalSc()+bsmain.getTotalSt()+bsmain.getTotalOthers()+bsmain.getTotalPopulation()+bsmain.getNoOfLandlessHousehold()+bsmain.getNoOfBplHousehold()+bsmain.getSmallFarmerHousehold()+bsmain.getMarginalFarmerHousehold()+bsmain.getPersonDaysMigration()));
					bean.setStname(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName());
					bean.setStcode(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStCode());
					
					rows.add(bean);
					count.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), projectcount);
					map.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), bean);
					
				}else {
					projectcount=count.get(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName())+1;
					count.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), projectcount);
					bean = map.get(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName());
					
					bean.setTotalproject(projectcount);
					bean.setDist_name(bsmain.getIwmpMProject().getIwmpDistrict().getDistName());
					bean.setDist_code(bsmain.getIwmpMProject().getIwmpDistrict().getDcode());
					bean.setProj_name(bsmain.getIwmpMProject().getProjName());
					bean.setProj_id(bsmain.getIwmpMProject().getProjectId());
					bean.setSanctioned_area((bean.getSanctioned_area()==null?BigDecimal.ZERO:bean.getSanctioned_area()).add(bsmain.getIwmpMProject().getAreaProposed()));
					bean.setTotal_geo_area((bean.getTotal_geo_area()==null?BigDecimal.ZERO:bean.getTotal_geo_area()).add(bsmain.getTotalGeoArea()));
					bean.setArea_covering_type((bean.getArea_covering_type()==null?bsmain.getAreaCoveringType():(bean.getArea_covering_type()+","+bsmain.getAreaCoveringType())));
					if(bsmain.getBaseLineSurveyActivityDetailses()!=null)
						for(BaseLineSurveyActivityDetails bad : bsmain.getBaseLineSurveyActivityDetailses()) {
							/*
							 * if(bad.getWdcpmksyMOutcome()!=null &&
							 * bad.getWdcpmksyMOutcome().getOutcomeId()==1)
							 * bean.setTreatable_area(bad.getAreaOfActivity());
							 * if(bad.getWdcpmksyMOutcome()!=null &&
							 * bad.getWdcpmksyMOutcome().getOutcomeId()==2)
							 * bean.setTreatable_area((bean.getTreatable_area()==null?BigDecimal.ZERO:bean.
							 * getTreatable_area()).add(bad.getAreaOfActivity()));
							 */
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()<=3)
								bean.setTreatable_area((bean.getTreatable_area()==null?BigDecimal.ZERO:bean.getTreatable_area()).add(bad.getAreaOfActivity()));
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==4)
								bean.setArea_covered_under_diversified_crops((bean.getArea_covered_under_diversified_crops()==null?BigDecimal.ZERO:bean.getArea_covered_under_diversified_crops()).add(bad.getAreaOfActivity()));
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==5)
								bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==1)
								bean.setArea_brought_under_afforestation((bean.getArea_brought_under_afforestation()==null?BigDecimal.ZERO:bean.getArea_brought_under_afforestation()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==2)
								bean.setArea_brought_under_horticulture((bean.getArea_brought_under_horticulture()==null?BigDecimal.ZERO:bean.getArea_brought_under_horticulture()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==5)
								bean.setWater_harvesting_structure((bean.getWater_harvesting_structure()==null?BigDecimal.ZERO:bean.getWater_harvesting_structure()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==7)
								bean.setArea_brought_under_protective_irrigation((bean.getArea_brought_under_protective_irrigation()==null?BigDecimal.ZERO:bean.getArea_brought_under_protective_irrigation()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==3)
								bean.setArea_covered_with_soil_and_moisture_conservation((bean.getArea_covered_with_soil_and_moisture_conservation()==null?BigDecimal.ZERO:bean.getArea_covered_with_soil_and_moisture_conservation()).add(bad.getAreaOfActivity()));
							
						}
					bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bsmain.getTotalGrossCroppedArea().add(bsmain.getTotalNetSownArea())));
					bean.setNo_of_household(bean.getNo_of_household()==null?(bsmain.getTotalSc()+bsmain.getTotalSt()+bsmain.getTotalOthers()+bsmain.getTotalPopulation()+bsmain.getNoOfLandlessHousehold()+bsmain.getNoOfBplHousehold()+bsmain.getSmallFarmerHousehold()+bsmain.getMarginalFarmerHousehold()+bsmain.getPersonDaysMigration()):(bean.getNo_of_household()+bsmain.getTotalSc()+bsmain.getTotalSt()+bsmain.getTotalOthers()+bsmain.getTotalPopulation()+bsmain.getNoOfLandlessHousehold()+bsmain.getNoOfBplHousehold()+bsmain.getSmallFarmerHousehold()+bsmain.getMarginalFarmerHousehold()+bsmain.getPersonDaysMigration()));
					bean.setStname(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName());
					bean.setStcode(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStCode());
					
					rows.add(bean);
					
					
					map.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), bean);
				}
				
				/*
				 * if(!count.containsKey(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState
				 * ().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName())) {
				 * projectcount=1;
				 * 
				 * bean = new BaseLineSurveyBean(); }else {
				 * projectcount=count.get(bsmain.getIwmpMProject().getIwmpDistrict().
				 * getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().
				 * getDistName())+1;
				 * count.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName
				 * ()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), projectcount);
				 * //bean = new BaseLineSurveyBean(); }
				 */
				
			}
			session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return map;
	}

	@Override
	public LinkedHashMap<String, BaseLineSurveyBean> getBaseLineSurveyDetailByStCodeDistCode(Integer stCode,
			Integer distCode) {
		// TODO Auto-generated method stub
		List<BaseLineSurveyBean> rows = new ArrayList<BaseLineSurveyBean>();
		List<BaseLineSurveyMain> mainList = new ArrayList<BaseLineSurveyMain>();
		LinkedHashMap<String,BaseLineSurveyBean> map = new LinkedHashMap<String,BaseLineSurveyBean>();
		
		String hql=getBaseLineSurveyDetailByStCodeDistCode;
		Integer projectcount=0;
		LinkedHashMap<String,Integer> count =new LinkedHashMap<String,Integer>();
		List<String> stname= new ArrayList<String>();
		List<String> distname= new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("stCode", stCode);
			query.setInteger("distCode", distCode);
			mainList = query.list();
			BaseLineSurveyBean bean = new BaseLineSurveyBean();
			for(BaseLineSurveyMain bsmain : mainList) {
				if(!map.containsKey(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName())) {
					bean = new BaseLineSurveyBean();
					projectcount=1;
					
					bean.setTotalproject(projectcount);
					bean.setDist_name(bsmain.getIwmpMProject().getIwmpDistrict().getDistName());
					bean.setDist_code(bsmain.getIwmpMProject().getIwmpDistrict().getDcode());
					bean.setProj_name(bsmain.getIwmpMProject().getProjName());
					bean.setProj_id(bsmain.getIwmpMProject().getProjectId());
					bean.setSanctioned_area((bean.getSanctioned_area()==null?BigDecimal.ZERO:bean.getSanctioned_area()).add(bsmain.getIwmpMProject().getAreaProposed()));
					bean.setTotal_geo_area((bean.getTotal_geo_area()==null?BigDecimal.ZERO:bean.getTotal_geo_area()).add(bsmain.getTotalGeoArea()));
					bean.setArea_covering_type((bean.getArea_covering_type()==null?bsmain.getAreaCoveringType():(bean.getArea_covering_type()+","+bsmain.getAreaCoveringType())));
					if(bsmain.getBaseLineSurveyActivityDetailses()!=null)
						for(BaseLineSurveyActivityDetails bad : bsmain.getBaseLineSurveyActivityDetailses()) {
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()<=3)
								bean.setTreatable_area((bean.getTreatable_area()==null?BigDecimal.ZERO:bean.getTreatable_area()).add(bad.getAreaOfActivity()));
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==4)
								bean.setArea_covered_under_diversified_crops((bean.getArea_covered_under_diversified_crops()==null?BigDecimal.ZERO:bean.getArea_covered_under_diversified_crops()).add(bad.getAreaOfActivity()));
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==5)
								bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==1)
								bean.setArea_brought_under_afforestation((bean.getArea_brought_under_afforestation()==null?BigDecimal.ZERO:bean.getArea_brought_under_afforestation()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==2)
								bean.setArea_brought_under_horticulture((bean.getArea_brought_under_horticulture()==null?BigDecimal.ZERO:bean.getArea_brought_under_horticulture()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==5)
								bean.setWater_harvesting_structure((bean.getWater_harvesting_structure()==null?BigDecimal.ZERO:bean.getWater_harvesting_structure()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==7)
								bean.setArea_brought_under_protective_irrigation((bean.getArea_brought_under_protective_irrigation()==null?BigDecimal.ZERO:bean.getArea_brought_under_protective_irrigation()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==3)
								bean.setArea_covered_with_soil_and_moisture_conservation((bean.getArea_covered_with_soil_and_moisture_conservation()==null?BigDecimal.ZERO:bean.getArea_covered_with_soil_and_moisture_conservation()).add(bad.getAreaOfActivity()));
							
						}
					bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bsmain.getTotalGrossCroppedArea().add(bsmain.getTotalNetSownArea())));
					bean.setNo_of_household(bean.getNo_of_household()==null?(bsmain.getTotalSc()+bsmain.getTotalSt()+bsmain.getTotalOthers()+bsmain.getNoOfLandlessHousehold()+bsmain.getNoOfBplHousehold()+bsmain.getSmallFarmerHousehold()+bsmain.getMarginalFarmerHousehold()+bsmain.getPersonDaysMigration()):(bean.getNo_of_household()+bsmain.getTotalSc()+bsmain.getTotalSt()+bsmain.getTotalOthers()+bsmain.getNoOfLandlessHousehold()+bsmain.getNoOfBplHousehold()+bsmain.getSmallFarmerHousehold()+bsmain.getMarginalFarmerHousehold()+bsmain.getPersonDaysMigration()));
					bean.setStname(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName());
					bean.setStcode(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStCode());
					
					rows.add(bean);
					count.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), projectcount);
					map.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), bean);
					
				}else {
					projectcount=count.get(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName())+1;
					count.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), projectcount);
					bean = map.get(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName());
					
					bean.setTotalproject(projectcount);
					bean.setDist_name(bsmain.getIwmpMProject().getIwmpDistrict().getDistName());
					bean.setDist_code(bsmain.getIwmpMProject().getIwmpDistrict().getDcode());
					bean.setProj_name(bsmain.getIwmpMProject().getProjName());
					bean.setProj_id(bsmain.getIwmpMProject().getProjectId());
					bean.setSanctioned_area((bean.getSanctioned_area()==null?BigDecimal.ZERO:bean.getSanctioned_area()).add(bsmain.getIwmpMProject().getAreaProposed()));
					bean.setTotal_geo_area((bean.getTotal_geo_area()==null?BigDecimal.ZERO:bean.getTotal_geo_area()).add(bsmain.getTotalGeoArea()));
					bean.setArea_covering_type((bean.getArea_covering_type()==null?bsmain.getAreaCoveringType():(bean.getArea_covering_type()+","+bsmain.getAreaCoveringType())));
					if(bsmain.getBaseLineSurveyActivityDetailses()!=null)
						for(BaseLineSurveyActivityDetails bad : bsmain.getBaseLineSurveyActivityDetailses()) {
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()<=3)
								bean.setTreatable_area((bean.getTreatable_area()==null?BigDecimal.ZERO:bean.getTreatable_area()).add(bad.getAreaOfActivity()));
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==4)
								bean.setArea_covered_under_diversified_crops((bean.getArea_covered_under_diversified_crops()==null?BigDecimal.ZERO:bean.getArea_covered_under_diversified_crops()).add(bad.getAreaOfActivity()));
							if(bad.getWdcpmksyMOutcome()!=null &&  bad.getWdcpmksyMOutcome().getOutcomeId()==5)
								bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==1)
								bean.setArea_brought_under_afforestation((bean.getArea_brought_under_afforestation()==null?BigDecimal.ZERO:bean.getArea_brought_under_afforestation()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==2)
								bean.setArea_brought_under_horticulture((bean.getArea_brought_under_horticulture()==null?BigDecimal.ZERO:bean.getArea_brought_under_horticulture()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==5)
								bean.setWater_harvesting_structure((bean.getWater_harvesting_structure()==null?BigDecimal.ZERO:bean.getWater_harvesting_structure()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==7)
								bean.setArea_brought_under_protective_irrigation((bean.getArea_brought_under_protective_irrigation()==null?BigDecimal.ZERO:bean.getArea_brought_under_protective_irrigation()).add(bad.getAreaOfActivity()));
							if(bad.getIwmpMPhyHeads()!=null &&  bad.getIwmpMPhyHeads().getHeadCode()==3)
								bean.setArea_covered_with_soil_and_moisture_conservation((bean.getArea_covered_with_soil_and_moisture_conservation()==null?BigDecimal.ZERO:bean.getArea_covered_with_soil_and_moisture_conservation()).add(bad.getAreaOfActivity()));
							
						}
					bean.setArea_brought_from_nil_single((bean.getArea_brought_from_nil_single()==null?BigDecimal.ZERO:bean.getArea_brought_from_nil_single()).add(bsmain.getTotalGrossCroppedArea().add(bsmain.getTotalNetSownArea())));
					bean.setNo_of_household(bean.getNo_of_household()==null?(bsmain.getTotalSc()+bsmain.getTotalSt()+bsmain.getTotalOthers()+bsmain.getTotalPopulation()+bsmain.getNoOfLandlessHousehold()+bsmain.getNoOfBplHousehold()+bsmain.getSmallFarmerHousehold()+bsmain.getMarginalFarmerHousehold()+bsmain.getPersonDaysMigration()):(bean.getNo_of_household()+bsmain.getTotalSc()+bsmain.getTotalSt()+bsmain.getTotalOthers()+bsmain.getTotalPopulation()+bsmain.getNoOfLandlessHousehold()+bsmain.getNoOfBplHousehold()+bsmain.getSmallFarmerHousehold()+bsmain.getMarginalFarmerHousehold()+bsmain.getPersonDaysMigration()));
					bean.setStname(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName());
					bean.setStcode(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStCode());
					
					rows.add(bean);
					
					
					map.put(bsmain.getIwmpMProject().getIwmpDistrict().getIwmpState().getStName()+bsmain.getIwmpMProject().getIwmpDistrict().getDistName(), bean);
				}
				
			}
			session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return map;
	}

}
