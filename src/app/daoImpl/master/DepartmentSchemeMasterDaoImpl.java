package app.daoImpl.master;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.master.DepartmentSchemeMasterDao;
import app.model.IwmpState;
import app.model.MBlsOutType;
import app.model.MDepartmentScheme;

@Repository("DepartmentSchemeMasterDao")
public class DepartmentSchemeMasterDaoImpl implements DepartmentSchemeMasterDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getDeptSchemData}")
	String getDeptSchemData;
	
	@Value("${deleteDeptSchemData}")
	String deleteDeptSchemData;
	
	@Value("${finddeptschemmaster}")
	String finddeptschemmaster;
	
	@Value("${countshgdetailscheme}")
	String countshgdetailscheme;
	
	@Value("${countfpodetailscheme}")
	String countfpodetailscheme;
	
	
	@Override
	public List<MDepartmentScheme> getDeptSchemeMaster() {
		
			List<MDepartmentScheme> getds=new ArrayList<MDepartmentScheme>();
			String hql=getDeptSchemData;
			try {
					Session session = sessionFactory.getCurrentSession();
					session.beginTransaction();
					Query query = session.createQuery(hql);
					getds = query.list();
					session.getTransaction().commit();
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
			return getds;
	}

	@Override
	public String saveDeptSchemeMasterData(String desc, BigDecimal seqno, String type, HttpSession session) {
		// TODO Auto-generated method stub
		Session sessionf = sessionFactory.getCurrentSession();
		SQLQuery query = null;
		String res = "fail";
		Integer value=0;
		MDepartmentScheme savedata = new MDepartmentScheme();
		IwmpState st= new IwmpState();
		try {
				sessionf.getTransaction().begin();
				InetAddress inet = InetAddress.getLocalHost();
				String ipAddr = inet.getHostAddress();          
				savedata.setCreatedOn(new Date());
				savedata.setSchemeDescription(desc);
				savedata.setTypes(type);
				savedata.setSeqNo(seqno);
				savedata.setStatus(true);
				savedata.setCreatedBy(session.getAttribute("loginID").toString());
	            savedata.setRequestIp(ipAddr);
				sessionf.save(savedata);
				res = "success";
			
				sessionf.getTransaction().commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			res = "fail";
			sessionf.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public Boolean deleteDeptSchemeData(int id, String schemetype) {
		
		Boolean res=false;
		Integer value=0;
		Long count=0l;
		String savesql=deleteDeptSchemData;  
		String countscheme=null;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query query1;
		try {
			if(schemetype.equals("SHG"))
				countscheme=countshgdetailscheme;
			
			if(schemetype.equals("FPO"))
				countscheme=countfpodetailscheme;
			
			query1 = session.createQuery(countscheme);
			query1.setInteger("id", id);
			count =(Long)query1.uniqueResult();
			if(count==0) 
			{
				Query query = session.createQuery(savesql);
				query.setInteger("id", id);
			    value=query.executeUpdate();
			    if(value>0) 
				{
					res=true;
				}
			}
			else{
				session.getTransaction().rollback();
				return false;
			}
			  
			if(res)
				session.getTransaction().commit();
			

		}catch(Exception ex) {
		    ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}

	@Override
	public List<MDepartmentScheme> deptschememasterfind(Integer id) {
		// TODO Auto-generated method stub
		List<MDepartmentScheme> getdsdata=new ArrayList<MDepartmentScheme>();
		
		String hql=finddeptschemmaster;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getdsdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getdsdata;
	}

	@Override
	public String updateDeptSchemeData(int id, String desc, BigDecimal seqno, String status, HttpSession session) {
		// TODO Auto-generated method stub
		Session sessionf = sessionFactory.getCurrentSession();
		String res = "fail";
		MDepartmentScheme savedata = new MDepartmentScheme();
		try {
			sessionf.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			savedata = (MDepartmentScheme) sessionf.get(MDepartmentScheme.class, id);
			
			savedata.setSchemeDescription(desc);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
            savedata.setSeqNo(seqno);
            if(status.equals("true")) {
            savedata.setStatus(true);
            }
            if(status.equals("false")) {
            savedata.setStatus(false);
            }
			sessionf.update(savedata);
            sessionf.getTransaction().commit();
			res = "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			sessionf.getTransaction().rollback();
		}

		return res;
	}

}
