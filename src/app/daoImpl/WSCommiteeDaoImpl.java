package app.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

import app.bean.ProfileBean;
import app.dao.WSCommiteeDao;
import app.model.IwmpMProject;
import app.model.UserReg;
import app.model.master.IwmpMWc;

@Repository("wsCommiteeDao")
public class WSCommiteeDaoImpl implements WSCommiteeDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${wsComitList}") 
	String wsComitList;
	
	@Value("${userProjList}") 
	String userProjList;
	
	@Value("${getWCLocationAdded}") 
	String getWCLocationAdded;
	
	@Value("${getlistofApprove}") 
	String getlistofApprove;
	
	@Value("${checkWSCommittee}") 
	String checkWSCommittee;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMWc> getWatershedCommitteeList(Integer projid) {
		
		List<IwmpMWc> result=new ArrayList<IwmpMWc>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			String hql=wsComitList;
			result = ses.createQuery(hql).setParameter("proj", projid).list();
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
        return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMProject> getUserProjectList(int regid) {
		
		List<IwmpMProject> result=new ArrayList<IwmpMProject>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			String hql=userProjList;
			result = ses.createQuery(hql).setParameter("regid", regid).list();
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
        return result;
	}

	@Override
	public Integer saveWatershedCommittee(Integer ProjId, String wcname, HttpSession sss) {
		
		Session sessionF = sessionFactory.getCurrentSession();
		Transaction tx = sessionF.beginTransaction();
		int p=0;
		
		try {
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			IwmpMWc mwc=new IwmpMWc();
			IwmpMProject mProj = new IwmpMProject();
			
			mProj.setProjectId(ProjId);
			mwc.setIwmpMProject(mProj);
			mwc.setWcname(wcname);
			mwc.setCreatedDt(new Timestamp(new java.util.Date().getTime()));
			mwc.setCreatedBy(sss.getAttribute("loginID").toString());
			mwc.setRequestIp(ipAddr);
			sessionF.save(mwc);
			p=1;
			
		}
		catch (Exception e) 
		{
			sessionF.getTransaction().rollback();
			p=0;
			tx.rollback();
			e.printStackTrace();
		}
		finally 
		{   
			tx.commit();
			//sessionF.flush();
			//sessionF.close();
		} 
		return p;
	}

	@Override
	public Integer updateWatershedCommittee(IwmpMWc mwc, HttpSession hsession) {

		Integer i=0;
		Session session=sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
				IwmpMWc mwsc = (IwmpMWc) session.get(IwmpMWc.class, mwc.getWcId());
				mwsc.setIwmpMProject(mwc.getIwmpMProject());
				mwsc.setWcname(mwc.getWcname());
				mwsc.setLastUpdatedBy(hsession.getAttribute("loginID").toString());
				mwsc.setLastUpdatedDate(new Timestamp(new java.util.Date().getTime()));
				session.update(mwsc);
				
				i=1;
		}
		catch (Exception e) 
		{
			session.getTransaction().rollback();
			i=0;
			e.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
		}
	return i;
	}

	@Override
	public Integer deleteWatershedCommittee(String wc_code) {
		Integer i=0;
		Session session=sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			String str[] = wc_code.split(",");
				for(i=0; i<str.length; i++)
				{
					IwmpMWc wm=new IwmpMWc();
					wm.setWcId(Integer.parseInt(str[i]));
					session.delete(wm);
				}
				i=1;
		}
		catch (Exception e) 
		{
			session.getTransaction().rollback();
			i=0;
			e.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
		}
	return i;
	
	}

	@Override
	public List<IwmpMWc> getWCLocationAdded(int regid) {
		
		List<IwmpMWc> result = new ArrayList<IwmpMWc>();
		Session session = sessionFactory.getCurrentSession();
		try {
				String hql=null;
				SQLQuery query = null;
				session.getTransaction().begin();
				hql =getWCLocationAdded;
				query = session.createSQLQuery(hql);
				query.setInteger("regid", regid);
				query.setResultTransformer(Transformers.aliasToBean(IwmpMWc.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.getTransaction().commit();
			
		}
		
		
		return result;
	}

	@Override
	public List getlistofApprove() {
		List result = new ArrayList();
		Session session = sessionFactory.getCurrentSession();
		try {
				String hql=null;
				SQLQuery query = null;
				session.getTransaction().begin();
				hql =getlistofApprove;
				query = session.createSQLQuery(hql);
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.getTransaction().commit();
			
		}
		
		return result;
	}

	@Override
	public List checkWSCommittee(Integer ProjId) {
		
		List result = new ArrayList();
		Session session = sessionFactory.getCurrentSession();
		try {
				String hql=null;
				SQLQuery query = null;
				session.getTransaction().begin();
				hql =checkWSCommittee;
				query = session.createSQLQuery(hql);
				query.setInteger("ProjId", ProjId);
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.getTransaction().commit();
			
		}
		
		return result;
	}

}
