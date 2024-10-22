package app.daoImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
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

import app.bean.MenuMap;
import app.dao.ProjectMasterDao;
import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpUserProjectMap;
import app.model.UserReg;

@Repository("projectMasterDao")
public class ProjectMasterDaoImpl implements ProjectMasterDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getProjectByDCode}")
	String projectByDCode;
	
	@Value("${getProjectByprojectId}")
	String getProjectByprojectId;
	
	@Value("${getProjectByUser}")
	String getProjectByUser;
	
	@Value("${getProjectByUser}")
	String getprojectlocationexist;
	
	@Value("${getProjectByUserAndPlan}")     
	String getProjectByUserAndPlan;

	@Value("${districtListByStateCode}")
	String districtListByStateCode;
	
	@Value("${getProjectByprojectCode}")
	String getProjectByprojectCode;
	
	@Value("${getprojectNaCByDCode}")
	String projectNaCByDCode;
	
	@Value("${getProjBystCodedCode}")
	String getProjBystCodedCode;
	
	@Value("${getProjByStateCode}")
	String getProjByStateCode;
	
	@Override
	public LinkedHashMap<String, String> getProjectByDcode(Integer dCode) {
		// TODO Auto-generated method stub
		List<IwmpMProject> projectList=new ArrayList<IwmpMProject>();
		String hql=projectByDCode;
		LinkedHashMap<String, String> blockMap=new LinkedHashMap<String, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setInteger("dCode",dCode);
			projectList = query.list();
			for (IwmpMProject project : projectList) {
				blockMap.put(project.getProjectCd(), project.getProjName());
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
        return blockMap;
	}
	
	@Override
	public IwmpMProject getProjectByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		IwmpMProject project = new IwmpMProject();
		try {
			session.beginTransaction();
			project = (IwmpMProject) session.get(IwmpMProject.class, projectId);
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
        return project;
	}
	
	
	
		@Override 
		  public String getProjectByProjectCode(String projectCd) 
		  { // TODO Auto-generated method stub 
			  List<IwmpMProject> projectList=new ArrayList<IwmpMProject>(); 
			  Session session = sessionFactory.getCurrentSession(); 
			  String hql=getProjectByprojectCode;
		      String projectName = ""; 
		  
		  try {
		  session.beginTransaction();
		  
		  Query query = sessionFactory.getCurrentSession().createQuery(hql);
		  query.setString("projectCd",projectCd); 
		  projectName = query.list().get(0).toString();
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
		  return projectName; 
		  }
	 
	@Override
	public LinkedHashMap<Integer, String> getProjectByRegId(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpUserProjectMap> rows = new ArrayList<IwmpUserProjectMap>();
		//String hql=getProjectByUser;
		String hql=getprojectlocationexist;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId",registrationId);
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
			}
			
			
			
			/*
			 * rows = query.list(); for(IwmpUserProjectMap row : rows){
			 * System.out.println("Size by regId: "+row.getIwmpMProject().getProjectCd()
			 * +" regId "+regId); map.put(row.getIwmpMProject().getProjectId(),
			 * row.getIwmpMProject().getProjName()); }
			 */
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
	public LinkedHashMap<Integer, String> getDistrictByStcode(Integer stcode) {
		Integer stCode = stcode;
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpDistrict> rows = new ArrayList<IwmpDistrict>();
		String hql=districtListByStateCode;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("stCode",stCode);
			rows = query.list();
			  for(IwmpDistrict row : rows){
				
				  map.put(row.getDistCode(), row.getDistName());
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
	public LinkedHashMap<Integer, String> getProjNACByDcode(Integer dCode) {
		// TODO Auto-generated method stub
		List<IwmpMProject> projectList=new ArrayList<IwmpMProject>();
		String hql=projectNaCByDCode;
		LinkedHashMap<Integer, String> blockMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setInteger("dCode",dCode);
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				blockMap.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
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
        return blockMap;
	}

	@Override
	public LinkedHashMap<Integer, String> getProjBystCodedCode(Integer stCode, Integer dCode) {
		// TODO Auto-generated method stub
		String hql=getProjBystCodedCode;
		LinkedHashMap<Integer, String> projMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setInteger("stCode",stCode);
			query.setInteger("dCode",dCode);
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				projMap.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
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
        return projMap;
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectByRegIdPlan(Integer regId) {
		// TODO Auto-generated method stub
		Integer registrationId = regId;
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpUserProjectMap> rows = new ArrayList<IwmpUserProjectMap>();
		//String hql=getProjectByUser;
		String hql=getProjectByUserAndPlan;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId",registrationId);
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
			}
			
			
			
			/*
			 * rows = query.list(); for(IwmpUserProjectMap row : rows){
			 * System.out.println("Size by regId: "+row.getIwmpMProject().getProjectCd()
			 * +" regId "+regId); map.put(row.getIwmpMProject().getProjectId(),
			 * row.getIwmpMProject().getProjName()); }
			 */
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
	public LinkedHashMap<Integer, String> getProjByStateCode(Integer stCode) {
		// TODO Auto-generated method stub
		String hql=getProjByStateCode;
		LinkedHashMap<Integer, String> projMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setInteger("stCode",stCode);
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				projMap.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
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
        return projMap;
	}
	
	
}
