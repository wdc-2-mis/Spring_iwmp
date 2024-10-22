package app.daoImpl;

import java.util.*;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.UserRoleMapBean;
import app.dao.UserProjectDao;
import app.model.IwmpMProject;
import app.model.IwmpUserProjectMap;
import app.model.UserReg;

@Repository("userProjectDao")
public class UserProjectDaoImpl implements UserProjectDao{
	
	@Value("${showproj}")
	String showproj;
	
	@Value("${activateUserApprole}")
	String activateUserApprole;
	
	@Value("${checkActiveUser}")
	String checkActiveUser;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer saveUserProject(UserRoleMapBean userProj, HttpSession session ) 
	{
		String sqlua=activateUserApprole;
		Session sessionF = sessionFactory.getCurrentSession();
		String distCode=userProj.getDistrict();
		Integer regid = Integer.valueOf(userProj.getUser());
		String [] projList=userProj.getProject().split(",");
		IwmpMProject iwmpMProject;
		Transaction tx = null;
		int p=0;
		try {
			tx = sessionF.beginTransaction();
			//Session session=sessionFactory.getCurrentSession();
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			UserReg userReg = (UserReg) sessionF.get(UserReg.class, Integer.valueOf(regid));
			
			SQLQuery queryc = sessionF.createSQLQuery(checkActiveUser);
			queryc.setString("uid", userReg.getUserId());
			Integer tranxCount = Integer.parseInt(queryc.list().get(0).toString());
			if (tranxCount ==0) 
			{
				SQLQuery query = sessionF.createSQLQuery(sqlua);
				query.setString("userId", userReg.getUserId());
				query.setString("authorizerId",session.getAttribute("loginid").toString());
				query.setString("requestIp",ipAddr);
				Integer res1 = query.executeUpdate();
			}
		
			for(String project:projList) 
			{
				IwmpUserProjectMap useproj= new IwmpUserProjectMap();
				iwmpMProject = new IwmpMProject();
				useproj.setUpdatedBy(session.getAttribute("loginid").toString());
				useproj.setIpAddress(ipAddr);
				useproj.setInsertDate(new Timestamp(new java.util.Date().getTime()));
				iwmpMProject.setProjectId(Integer.parseInt(project.trim()));
				useproj.setIwmpMProject(iwmpMProject);
				useproj.setUserReg(userReg);
				sessionF.save(useproj);
				p=1;
				//System.out.println("Project Mapped !");
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
			//sessionF.flush();
			//sessionF.close();
		} 
		return p;
	}

	@Override
	public String showProject(int regId) {
		Session session = sessionFactory.getCurrentSession();
		String counter = null; 
		
		String sql=showproj;
		Transaction tx=null;
		
		try {
			tx = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("regId", regId);
			List list = query.list();
			
			Iterator itr = list.iterator();
			while(itr.hasNext()) 
			{
				 counter = (String) itr.next();
			}
			tx.commit();
		//	session.getTransaction().commit();
		} 
		catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
		}
		return counter;
	}
	
	
	

}
