package app.daoImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.GroundWaterTableBean;
import app.dao.GroundWaterTableDao;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.outcome.GroundwaterDetail;
import app.model.outcome.GroundwaterMain;


@Repository("GroundWaterTableDao")
public class GroundWaterTableDaoImpl implements GroundWaterTableDao{

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getFinYear}")
	String getFinYear;
	
	@Value("${getGroundWaterTable}") 
	String getGroundWaterTable;
	
	@Value("${getGroundWaterTableAllProject}") 
	String getGroundWaterTableAllProject;
	
	@Value("${getGroundWaterTableAllDistrict}") 
	String getGroundWaterTableAllDistrict;
	
	@Value("${getGroundWaterTableAllState}") 
	String getGroundWaterTableAllState;
	
	@Value("${getWaterGroundDraftCom}") 
	String getWaterGroundDraftCom;
	
	@Value("${deleteGWTdraft}") 
	String deleteGWTdraft;
	
	@Value("${deleteGWTdraftmain}") 
	String deleteGWTdraftmain;
	
	@Value("${completeGWTdraft}") 
	String completeGWTdraft;
	
	@Value("${completeGWTdraftmain}") 
	String completeGWTdraftmain;
	
	@Value("${balselinCheck}") 
	String balselinCheck;
	
	@Value("${duringProjCheck}") 
	String duringProjCheck;
	
	@Value("${getWaterGroundDraftbasel}") 
	String getWaterGroundDraftbasel;
	
	@Value("${getWaterGroundDraftproject}") 
	String getWaterGroundDraftproject;
	
	@Value("${getWaterGroundDraftComBasel}") 
	String getWaterGroundDraftComBasel;
	
	@Override
	public LinkedHashMap<Integer, String> getFinYear() {
		String getyear=getFinYear;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(getyear);
		//	query.setInteger("pCode",pCode); 
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
	public String saveGroundWaterTable(String atline, Integer project, Integer fyear, BigDecimal preMonsoon, BigDecimal postMonsoon, HttpSession session, Integer gwtid,
			BigDecimal ph, Integer alkalinity, Integer hardness, Integer calcium, Integer chloride, BigDecimal nitrate, Integer dissolved, BigDecimal fluoride) {
		Session sess = sessionFactory.getCurrentSession();
		System.out.println("gwtid:" +gwtid);
		String res="fail";
		try {
			
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				
				sess.beginTransaction();
				if(!gwtid.equals(null) && gwtid > 0) 
				{
					GroundwaterMain gwm = (GroundwaterMain) sess.get(GroundwaterMain.class, gwtid);
					for(GroundwaterDetail gwd: gwm.getGroundwaterDetails()) 
					{
						  sess.delete(gwd);
					}
					sess.delete(gwm);
				}
				GroundwaterMain main =new GroundwaterMain();
				GroundwaterDetail detl= new GroundwaterDetail();
				IwmpMProject proj =new IwmpMProject();
				proj.setProjectId(project);
				if(!atline.equals("basel")) {
					IwmpMFinYear fin= new IwmpMFinYear();
					fin.setFinYrCd(fyear);
					main.setIwmpMFinYear(fin);
				}
				main.setIwmpMProject(proj);
				main.setLevelTime(atline);
				main.setCreatedBy(session.getAttribute("loginID").toString());
				main.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				main.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
				main.setRequestIp(ipAddr);
				main.setStatus('D');
				sess.save(main);
			//	for(int i=0; i<noOfTConduct;i++) {
				
					detl.setGroundwaterMain(main);
					detl.setDepthPostmonsoon(postMonsoon);
					detl.setDepthPremonsoon(preMonsoon);
					detl.setCreatedBy(session.getAttribute("loginID").toString());
					detl.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					detl.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
					detl.setRequestIp(ipAddr);
					detl.setStatus('D');
					detl.setPh(ph);   // ph, alkalinity, hardness, calcium, chloride, nitrate, dissolved, fluoride
					detl.setAlkalinity(alkalinity);
					detl.setHardness(hardness);
					detl.setCalcium(calcium);
					detl.setChloride(chloride);
					detl.setNitrate(nitrate);
					detl.setDissolvedSolid(dissolved);
					detl.setFluoride(fluoride);
					
					sess.save(detl);
					sess.evict(detl);

			//	}
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
	public List<GroundWaterTableBean> getGroundWaterTableReport(Integer state, Integer district, Integer project) {
		

		List<GroundWaterTableBean> result=new ArrayList<GroundWaterTableBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			 // query = session.createSQLQuery(hql);    getGroundWaterTable
			  if(project!=0) {
				hql=getGroundWaterTable;
				query = session.createSQLQuery(hql);
				query.setInteger("project", project);
			  }	
			  if(district!=0 && project==0) {
					hql=getGroundWaterTableAllProject;
					query = session.createSQLQuery(hql);
					query.setInteger("district", district);
			  }
			  if(district==0) {
					hql=getGroundWaterTableAllDistrict;
					query = session.createSQLQuery(hql);
					query.setInteger("statecd", state);
			  }
			  if(state==0) {
					hql=getGroundWaterTableAllState;
					query = session.createSQLQuery(hql);
			  }
			  query.setResultTransformer(Transformers.aliasToBean(GroundWaterTableBean.class));
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
			
		}
		return result;
	}



	@Override
	public List<GroundWaterTableBean> gwtDetaildataDC(Integer project) {
		
		List<GroundWaterTableBean> result=new ArrayList<GroundWaterTableBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			 // query = session.createSQLQuery(hql);    getGroundWaterTable
			  if(project!=0) {
				hql=getWaterGroundDraftCom;
				query = session.createSQLQuery(hql);
				query.setInteger("project", project);
			  }	
			  query.setResultTransformer(Transformers.aliasToBean(GroundWaterTableBean.class));
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
	public boolean deleteGWTdraft(Integer gwtid, Integer gwtdtlid) {
		
		Boolean res=false;
		Integer value=0;
		Integer value1=0;
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String deletesql=deleteGWTdraft;
		String deletesqlm=deleteGWTdraftmain;
		try {
				SQLQuery query = sessionf.createSQLQuery(deletesql);
			    query.setInteger("gwtdtl", gwtdtlid);
				value=query.executeUpdate();
				
				SQLQuery query1 = sessionf.createSQLQuery(deletesqlm);
			    query1.setInteger("gwtm", gwtid);
				value1=query1.executeUpdate();
				
				if(value>0 && value1>0) 
				{
					res=true;
				}
				else {
						sessionf.getTransaction().rollback();
						return false;
				}
				if(res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}



	@Override
	public boolean completeGWTDraftData(Integer gwtid, Integer gwtdtlid) {
		
		Boolean res=false;
		Integer value=0;
		Integer value1=0;
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String sql=completeGWTdraft;
		String sqlm=completeGWTdraftmain;
		try {
				SQLQuery query = sessionf.createSQLQuery(sql);
			    query.setInteger("gwtdtl", gwtdtlid);
			 //   query.setCharacter("status", 'C');
				value=query.executeUpdate();
				
				SQLQuery query1 = sessionf.createSQLQuery(sqlm);
			    query1.setInteger("gwtm", gwtid);
			  //  query.setCharacter("status", 'C');
				value1=query1.executeUpdate();
				
				if(value>0 && value1>0) 
				{
					res=true;
				}
				else {
						sessionf.getTransaction().rollback();
						return false;
				}
				if(res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}



	@Override
	public String balselinCheck(Integer project, String atline) {
		
		Session session = sessionFactory.openSession();
		List<GroundWaterTableBean> result=new ArrayList<GroundWaterTableBean>();
		String res="success";
		try {
			
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				if(project!=0 && atline.equals("basel")) 
				{
					hql=balselinCheck;
					query = session.createSQLQuery(hql);
					query.setInteger("project", project);
					query.setString("atline", atline);
				}	
				query.setResultTransformer(Transformers.aliasToBean(GroundWaterTableBean.class));
				result = query.list();
				if(result.size()==0)
				res="fail";
		}
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		
		return res;
	}



	@Override
	public String duringProjCheck(Integer project, String atline, Integer fyear) {
		
		Session session = sessionFactory.openSession();
		List<GroundWaterTableBean> result=new ArrayList<GroundWaterTableBean>();
		String res="success";
		try {
			
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				if(project!=0 && atline.equals("project") && fyear!=0) 
				{
					hql=duringProjCheck;
					query = session.createSQLQuery(hql);
					query.setInteger("project", project);
					query.setString("atline", atline);
					query.setInteger("fyear", fyear);
				}	
				query.setResultTransformer(Transformers.aliasToBean(GroundWaterTableBean.class));
				result = query.list();
				if(result.size() == 0)
				res="fail";
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
		}
		finally {
			session.getTransaction().commit();
		}
		
		return res;
	}



	@Override
	public List<GroundWaterTableBean> getGroundWaterTableDraft(Integer project, String atline, Integer fyear) {
		
		List<GroundWaterTableBean> result=new ArrayList<GroundWaterTableBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			 // query = session.createSQLQuery(hql);    getGroundWaterTable
			  if(atline.equals("basel")) {
				hql=getWaterGroundDraftbasel;
				query = session.createSQLQuery(hql);
				query.setInteger("project", project);
				query.setString("atline", atline);
			  }
			  if(atline.equals("project")) {
					hql=getWaterGroundDraftproject;
					query = session.createSQLQuery(hql);
					query.setInteger("project", project);
					query.setString("atline", atline);
					query.setInteger("fyear", fyear);
				  }
			  query.setResultTransformer(Transformers.aliasToBean(GroundWaterTableBean.class));
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
	public List<GroundWaterTableBean> gwtDetaildataDCBasel(Integer project) {
		// TODO Auto-generated method stub
		List<GroundWaterTableBean> result=new ArrayList<GroundWaterTableBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			 // query = session.createSQLQuery(hql);    getGroundWaterTable
			  if(project!=0) {
				hql=getWaterGroundDraftComBasel;
				query = session.createSQLQuery(hql);
				query.setInteger("project", project);
			  }	
			  query.setResultTransformer(Transformers.aliasToBean(GroundWaterTableBean.class));
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

}
