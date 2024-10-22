package app.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import app.bean.ProfileBean;
import app.bean.ProjectPrepareBean;
import app.dao.ProjectPrepareDao;
import app.model.IwmpMProject;
import app.model.master.IwmpMProjectPrepare;
import app.model.project.IwmpProjectPrepare;

@Repository("ProjectPrepareDao")
public class ProjectPrepareDaoImpl implements ProjectPrepareDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getProjectPrepare}")
	String getProjectPrepare;
	
	@Value("${getAddedPrepare}")
	String getAddedPrepare;
	
	@Override
	public List<IwmpMProjectPrepare> getprojectPreparedness() {
		
		List<IwmpMProjectPrepare> result=new ArrayList<IwmpMProjectPrepare>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql=null;
			Query query = null;
			hql = getProjectPrepare;
			session.beginTransaction();
			query = session.createQuery(hql);
			result =query.list();
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
			session.getTransaction().rollback();
			ex.printStackTrace();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}

	
	public Integer saveProjectPreparedness1(String ProjId, HttpSession session, String[] projectPreparelist,String yes) 
	{
		Session sessionF = sessionFactory.getCurrentSession();
		Transaction tx = sessionF.beginTransaction();
		String select[]= yes.split(",");
		IwmpMProject mProject;
		int p=0;
		try {
				
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				for(int i=0; i<projectPreparelist.length; i++) 
				{
					mProject = new IwmpMProject();
					IwmpMProjectPrepare mpp=new IwmpMProjectPrepare();
					IwmpProjectPrepare pp = new IwmpProjectPrepare();
					mProject.setProjectId(Integer.parseInt(ProjId));
					mpp.setProjectMprepareId(Integer.parseInt(projectPreparelist[i]));
					pp.setIwmpMProject(mProject);
					pp.setIwmpMProjectPrepare(mpp);
					if(select[i].equals("true")) 
					{
						pp.setActivityStatus(true);
					}
					if(select[i].equals("false")) 
					{
						pp.setActivityStatus(false);
					}
					
					pp.setCreatedBy(session.getAttribute("loginID").toString());
					pp.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
					pp.setRequestIp(ipAddr);
					sessionF.save(pp);
					p=1;
				}
					
		}
		catch (Exception e) 
		{
			p=0;
			tx.rollback();
			e.printStackTrace();
		}
		finally 
		{   
			tx.commit();
		} 
		return p;
	}

	@Override
	public List<ProjectPrepareBean> getAddPreparedness(Integer ProjId) {
		
		List<ProjectPrepareBean> result=new ArrayList<ProjectPrepareBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			String hql=null;
			SQLQuery query = null;
			
			//String hql=null;
			//Query query = null;
			hql = getAddedPrepare;
			session.beginTransaction();
			query = session.createSQLQuery(hql);
			query.setInteger("projid", ProjId);
			query.setResultTransformer(Transformers.aliasToBean(ProjectPrepareBean.class));
		//	query = session.createQuery(hql).setParameter(0, ProjId);
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
			session.getTransaction().commit();
		}
		return result;
	}


	@Override
	public Integer saveProjectPreparedness(String ProjId, HttpSession session, HashMap<Integer, String> map) {
		
		Session sessionF = sessionFactory.getCurrentSession();
		Transaction tx = sessionF.beginTransaction();
		IwmpMProject projectId;
		IwmpMProject mProject;
		int p=0;
		try {
				projectId = (IwmpMProject) sessionF.load(IwmpMProject.class, Integer.parseInt(ProjId));
				projectId.getProjectPrepares().clear();
			
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				Iterator entries = map.entrySet().iterator();
				while (entries.hasNext()) 
				{
				    Map.Entry entry = (Map.Entry) entries.next();
				    Integer prepareId = (Integer)entry.getKey();
				    String status = entry.getValue().toString();
				
				    mProject = new IwmpMProject();
					IwmpMProjectPrepare mpp=new IwmpMProjectPrepare();
					IwmpProjectPrepare pp = new IwmpProjectPrepare();
					mProject.setProjectId(Integer.parseInt(ProjId));
					mpp.setProjectMprepareId(prepareId);
					pp.setIwmpMProject(mProject);
					pp.setIwmpMProjectPrepare(mpp);
					pp.setActivityStatus(Boolean.valueOf(status));
					pp.setCreatedBy(session.getAttribute("loginID").toString());
					pp.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
					pp.setRequestIp(ipAddr);
					sessionF.save(pp);
					p=1;
				
				}
				sessionF.saveOrUpdate(projectId);
		}
		catch (Exception e) 
		{
			p=0;
			tx.rollback();
			e.printStackTrace();
		}
		finally 
		{   
			tx.commit();
		} 
		return p;
	}

}
