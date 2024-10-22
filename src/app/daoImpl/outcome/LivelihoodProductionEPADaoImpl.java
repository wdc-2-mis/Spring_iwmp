package app.daoImpl.outcome;

import java.math.BigInteger;
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

import app.bean.PhysicalActBean;
import app.bean.RPCLivelihoodBean;
import app.bean.menu.IwmpMMenu;
import app.dao.outcomes.LivelihoodProductionEpaDao;
import app.model.UserReg;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.LivelihoodEpaProd;
import app.model.outcome.MCroptype;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.outcome.ProductionDetail;

@Repository("LivelihoodProductionEpaDao")
public class LivelihoodProductionEPADaoImpl implements LivelihoodProductionEpaDao {
	@Autowired
	protected SessionFactory sessionFactory;

	@Value("${getLivelihoodCoreActivity}")
	String getLivelihoodCoreActivity;
	@Value("${getProductionCoreActivity}")
	String getProductionCoreActivity;
	@Value("${getEpaCoreActivity}")
	String getEpaCoreActivity;
	
	@Value("${getCropList}")
	String getCropList;
	
	@Value("${getReportLivelihood}")
	String getReportLivelihood;
	
	@Value("${getReportProduction}")
	String getReportProduction;
	
	@Value("${getReportEpa}")
	String getReportEpa;
	
	@Value("${getLivelihoodDetail}")
	String getlivelihood;
	
	@Value("${getProductionDetail}")
	String getProductionDetail;
	
	@Value("${getEpaDetail}")
	String getEpaDetail;
	

	@Value("${getReportLivelihoodDist}")
	String getReportLivelihoodDist;

	@Value("${getEpaProjectDetail}")
	String getEpaProjectDetail;
	@Value("${getReportProductionDist}")
	String getReportProductionDist;
	
	@Value("${getReportEpaDist}")
	String getReportEpaDist;
	
	@Value("${getReportLivelihoodProject}")
	String getReportLivelihoodProject;
	
	@Value("${getReportProductionProject}")
	String getReportProductionProject;
	
	@Value("${getReportEpaProject}")
	String getReportEpaProject;
	
	@Value("${getlivelihoodProjectDetail}")
	String getlivelihoodProjectDetail;
	
	@Value("${getProductionDProjectetail}")
	String getProductionDProjectetail;
	
	@Value("${getReportLivelihoodAvgProject}")
	String getReportLivelihoodAvgProject;
	
	@Value("${getReportLivelihoodAvgSProject}")
	String getReportLivelihoodAvgSProject;
	
	@Value("${getReportProductionAvgProject}")
	String getReportProductionAvgProject;
	
	@Value("${getReportProductionAvgSProject}")
	String getReportProductionAvgSProject;
	
	@Value("${completeEpa_detail}")
	String completeEpa_detail;
	@Value("${completeLivelihood_detail}")
	String completeLivelihood_detail;
	@Value("${completeProduction_detail}")
	String completeProduction_detail;
	
	@Value("${findlivepostdata}")
	String findlivepostdata;
	
	@Value("${updatepostdata}")
	String updatepostdata;
	
	@Value("${findproductdata}")
	String findproductdata;
	
	@Value("${updateProductionpostdata}")
	String updateProductionpostdata;
	
//	select livelihoodEpaProd.iwmpMProject.iwmpDistrict.iwmpState.stCode as stcode,livelihoodEpaProd.iwmpMProject.iwmpDistrict.iwmpState.stName as stname,sum(noActivities) as noactivity,
//	sum(sc) as sc, sum(st) as st, sum(other), sum(sc+st+other) as other,sum(women) as women
//	 from LivelihoodDetail as l  getlivelihood
//
//	group by  livelihoodEpaProd.iwmpMProject.iwmpDistrict.iwmpState.stCode ,livelihoodEpaProd.iwmpMProject.iwmpDistrict.iwmpState.stName 
	@Override
	public LinkedHashMap<Integer, String> getLivelihoodCoreActivity() {
		// TODO Auto-generated method stub
		List<MLivelihoodCoreactivity> list = new ArrayList<MLivelihoodCoreactivity>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql = getLivelihoodCoreActivity;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			for (MLivelihoodCoreactivity head : list) {
				map.put(head.getLivelihoodCoreactivityId(), head.getLivelihoodCoreactivityDesc());
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getProductionCoreActivity() {
		// TODO Auto-generated method stub
		List<MProductivityCoreactivity> list = new ArrayList<MProductivityCoreactivity>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql = getProductionCoreActivity;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			for (MProductivityCoreactivity head : list) {
				map.put(head.getProductivityCoreactivityId(), head.getProductivityCoreactivityDesc());
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate Production error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getEpaCoreActivity() {
		// TODO Auto-generated method stub
		List<MEpaCoreactivity> list = new ArrayList<MEpaCoreactivity>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql = getEpaCoreActivity;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			for (MEpaCoreactivity head : list) {
				map.put(head.getEpaActivityId(), head.getEpaDesc());
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate EPA error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	
	@Override
	public LinkedHashMap<Integer, String> getCropList() {
		// TODO Auto-generated method stub
		List<MCroptype> list = new ArrayList<MCroptype>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql = getCropList;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			for (MCroptype head : list) {
				map.put(head.getCropId(), head.getCropDesc());
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate EPA error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	
	@Override
	public String addLivelihoodProductionEPA(LivelihoodEpaProd livelihoodProductionEpa) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String msg="";
		
		try {
			session.beginTransaction();

			if (null != livelihoodProductionEpa) {
				session.save(livelihoodProductionEpa);
			}
			session.getTransaction().commit();
			msg="success";
		} catch (HibernateException e) {
			System.err.print("Hibernate EPA error");
			e.printStackTrace();
			session.getTransaction().rollback();
			msg=e.getMessage();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().rollback();
			msg=ex.getMessage();
		}
		return msg;
	}

	@Override
	public List<RPCLivelihoodBean> getRptLivelihoodList(Integer stcode) {
		Session session = sessionFactory.getCurrentSession();
		List<RPCLivelihoodBean> list = new ArrayList<RPCLivelihoodBean>();
		try {
			String hql=getReportLivelihood;
			session.beginTransaction();
			//Query query= session.createQuery(hql);
			
			SQLQuery query = null;
			query = session.createSQLQuery(hql);
			query.setInteger("stcode", stcode);
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
			list = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			//System.err.print("Hibernate error");
			e.printStackTrace();
			//session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	}
	@Override
	public List<RPCLivelihoodBean> getRptProductionList(Integer stcode) {
		Session session = sessionFactory.getCurrentSession();
		List<RPCLivelihoodBean> list = new ArrayList<RPCLivelihoodBean>();
		try {
			session.beginTransaction();
		//	Query query= session.createQuery(getReportProduction);
			SQLQuery query = null;
			String hql = getReportProduction;
			query = session.createSQLQuery(hql);
			query.setInteger("stcode", stcode);
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
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
	public List<LivelihoodDetail> getLivelihoodDetail(Integer regId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<LivelihoodDetail> list = new ArrayList<LivelihoodDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getlivelihood);
			query.setLong("regId",regId);
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
	public List<ProductionDetail> getProductionDetail(Integer regId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<ProductionDetail> list = new ArrayList<ProductionDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getProductionDetail);
			query.setLong("regId",regId);
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
	public List<EpaDetail> getEpaDetail(Integer regId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<EpaDetail> list = new ArrayList<EpaDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getEpaDetail);
			query.setLong("regId",regId);
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
	public List<RPCLivelihoodBean> getRptLivelihoodDistList(String stcd, String headtypeh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<RPCLivelihoodBean> list = new ArrayList<RPCLivelihoodBean>();
		//Query query=null;
		SQLQuery query = null;
		try {
			session.beginTransaction();
			if (headtypeh.equals("livelihood")) 
			{
				//query= session.createQuery(getReportLivelihoodDist);
				query = session.createSQLQuery(getReportLivelihoodDist);
			}
			if (headtypeh.equals("production")) 
			{
				//query= session.createQuery(getReportProductionDist);
				query = session.createSQLQuery(getReportProductionDist);
			}
			if (headtypeh.equals("epa")) 
			{
				//query= session.createQuery(getReportEpaDist);
				query = session.createSQLQuery(getReportEpaDist);
			}
			query.setInteger("stcd", Integer.parseInt(stcd));
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
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
	public String deleteLivelihoodDetail(int livelihoodDetailId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				try {
					Session session = sessionFactory.getCurrentSession();
					session.beginTransaction();
					LivelihoodDetail menu = (LivelihoodDetail) session.load(LivelihoodDetail.class, livelihoodDetailId);
					if (null != menu) {
					
						session.delete(menu);
					}
					session.getTransaction().commit();
					return "success";

				} catch (Exception ex) {

					return "fail";
				}
	}

	@Override
	public String completeLivelihoodDetail(int livelihoodDetailId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			LivelihoodDetail menu = (LivelihoodDetail) session.load(LivelihoodDetail.class, livelihoodDetailId);
			if (null != menu) {
				menu.setStatus("C");
				session.update(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}
	}

	@Override
	public String deleteProductionDetail(int ProductionDetailId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			ProductionDetail menu = (ProductionDetail) session.load(ProductionDetail.class, ProductionDetailId);
		if (null != menu) {
				
				session.delete(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}
	}

	@Override
	public String completeProductionDetail(int ProductionDetailId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			ProductionDetail menu = (ProductionDetail) session.load(ProductionDetail.class, ProductionDetailId);
			if (null != menu) {
				menu.setStatus("C");
				session.update(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}

	}

	@Override
	public String deleteEpaDetail(int EpaDetailId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			EpaDetail menu = (EpaDetail) session.load(EpaDetail.class, EpaDetailId);
			

			if (null != menu) {
				session.delete(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}

	}

	@Override
	public String completeEpaDetail(int EpaDetailId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			EpaDetail menu = (EpaDetail) session.load(EpaDetail.class, EpaDetailId);
		

			if (null != menu) {
				menu.setStatus("C");
				session.update(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}
	}


	@Override
	public List<RPCLivelihoodBean> getRptEpaList(Integer stcode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<RPCLivelihoodBean> list = new ArrayList<RPCLivelihoodBean>();
		try {
			session.beginTransaction();
		//	Query query= session.createQuery(getReportEpa);
			
			SQLQuery query = null;
			String hql = getReportEpa;
			query = session.createSQLQuery(hql);
			query.setInteger("stcode", stcode);
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
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
	public List<EpaDetail> getEpaDetailProjectWise(Integer distid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<EpaDetail> list = new ArrayList<EpaDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getReportEpaProject);
			query.setLong("distcd",distid);
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
	public List<LivelihoodDetail> getLivelihoodDetailProjectWise(Integer distid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<LivelihoodDetail> list = new ArrayList<LivelihoodDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getReportLivelihoodProject);
			query.setLong("distcd",distid);
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
	public List<ProductionDetail> getProductionDetailProjectWise(Integer distid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<ProductionDetail> list = new ArrayList<ProductionDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getReportProductionProject);
			query.setLong("distcd",distid);
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
	public List<LivelihoodDetail> getLivelihoodProjectDetail(Integer projectId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<LivelihoodDetail> list = new ArrayList<LivelihoodDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getlivelihoodProjectDetail);
			query.setLong("projectId",projectId);
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
	public List<ProductionDetail> getProductionProjectDetail(Integer projectId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<ProductionDetail> list = new ArrayList<ProductionDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getProductionDProjectetail);
			query.setLong("projectId",projectId);
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
	public List<EpaDetail> getEpaProjectDetail(Integer projectId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<EpaDetail> list = new ArrayList<EpaDetail>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(getEpaProjectDetail);
			query.setLong("projectId",projectId);
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
	public List<RPCLivelihoodBean> getLiveEPAProdDetailProjectWise(Integer distid, String headtypeh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<RPCLivelihoodBean> list = new ArrayList<RPCLivelihoodBean>();
		SQLQuery query = null;
		try {
			session.beginTransaction();
			if (headtypeh.equals("livelihood")) 
			{
				query = session.createSQLQuery(getReportLivelihoodProject);
			}
			if (headtypeh.equals("production")) 
			{
				query = session.createSQLQuery(getReportProductionProject);
			}
			if (headtypeh.equals("epa")) 
			{
				query = session.createSQLQuery(getReportEpaProject);
			}
			query.setInteger("distcd", distid);
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
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
	public List<RPCLivelihoodBean> getLiveEPAProdDetailAvgProjectWise(Integer distid, String headtypeh) {
		Session session = sessionFactory.getCurrentSession();
		List<RPCLivelihoodBean> list = new ArrayList<RPCLivelihoodBean>();
		SQLQuery query = null;
		try {
			session.beginTransaction();
			if (headtypeh.equals("livelihood")) 
			{
				query = session.createSQLQuery(getReportLivelihoodAvgProject);
			}
			
			  if (headtypeh.equals("production")) 
			  { 
				  query = session.createSQLQuery(getReportProductionAvgProject); 
			  } 
			  
			  
			 /* if
			 * (headtypeh.equals("epa")) { query =
			 * session.createSQLQuery(getReportEpaAvgProject); }
			 */
			query.setInteger("distcd", distid);
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
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
	public List<RPCLivelihoodBean> getLiveEPAProdDetailSProjectWise(Integer distid, String headtypeh,
			Integer projcode) {
		
		Session session = sessionFactory.getCurrentSession();
		List<RPCLivelihoodBean> list = new ArrayList<RPCLivelihoodBean>();
		SQLQuery query = null;
		try {
			session.beginTransaction();
			if (headtypeh.equals("livelihood")) 
			{
				query = session.createSQLQuery(getReportLivelihoodAvgSProject);
			}
			
			if (headtypeh.equals("production")) 
			{ 
				query = session.createSQLQuery(getReportProductionAvgSProject); 
			} 
			  
			 /* if
			 * (headtypeh.equals("epa")) { query =
			 * session.createSQLQuery(getReportEpaAvgProject); }
			 */
			query.setInteger("distcd", distid);
			query.setInteger("projcode", projcode);
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
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
	public String completeAllEPALivelihoodProduction(Integer sentfrom, List<BigInteger> tempassetid, String scheme) {
		// TODO Auto-generated method stub
		String res="fail";
		String sql=null;
		Session session = sessionFactory.getCurrentSession();
		Date d = new Date();
		try {
			session.beginTransaction();
			if(scheme.equals("epa")) 
			{
				sql=completeEpa_detail;
			}
			if(scheme.equals("livelihood")) 
			{
				sql=completeLivelihood_detail;
			}
			if(scheme.equals("production")) 
			{
				sql=completeProduction_detail;
			}
		
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameterList("assetId", tempassetid);
			
			int a = query.executeUpdate();
			if(a>0) {
			session.getTransaction().commit();
			res="success";
			}
			else {
				session.getTransaction().rollback();
				res="fail";
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
		return res;
	}

	@Override
	public List<RPCLivelihoodBean> findlivelidesc(Integer id) {
    List<RPCLivelihoodBean> findactdesc=new ArrayList<RPCLivelihoodBean>();
		
		String hql=findlivepostdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("id", id);
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
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
	public Boolean updatepostdata(Integer id, Integer livepost, String loginID) {
		Boolean res=false;
		Integer value=0;
		String savesql=updatepostdata;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			 {
					 SQLQuery query = session.createSQLQuery(savesql);
				  Date d= new Date();
			        
				      
			        	query.setDate("lastupdateddate", d);
			        	query.setInteger("livepost",livepost);
			        	query.setInteger("id",id);
					    
			        	value=query.executeUpdate();
						if(value>0) {
							  res=true;
						}else {
							session.getTransaction().rollback();
							return false;
						}
				
			}
			if(res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();

		}catch(Exception ex) {
		    ex.printStackTrace();
		    session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

	@Override
	public List<RPCLivelihoodBean> findproddesc(Integer id) {
    List<RPCLivelihoodBean> findactdesc=new ArrayList<RPCLivelihoodBean>();
		
		String hql=findproductdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("id", id);
			query.setResultTransformer(Transformers.aliasToBean(RPCLivelihoodBean.class));
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
	public Boolean updateprodpostdata(Integer id, Integer prodpost) {
		Boolean res=false;
		Integer value=0;
		String savesql=updateProductionpostdata;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			 {
					 SQLQuery query = session.createSQLQuery(savesql);
				  Date d= new Date();
			        
				      
			        	query.setDate("lastupdateddate", d);
			        	query.setInteger("prodpost",prodpost);
			        	query.setInteger("id",id);
					    
			        	value=query.executeUpdate();
						if(value>0) {
							  res=true;
						}else {
							session.getTransaction().rollback();
							return false;
						}
				
			}
			if(res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();

		}catch(Exception ex) {
		    ex.printStackTrace();
		    session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

}
