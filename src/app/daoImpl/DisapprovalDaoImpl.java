package app.daoImpl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.DisApprovalBean;
import app.dao.Disapprovaldao;
import app.model.IwmpState;
import app.model.UserReg;

@Repository("disapprovaldao")
public class DisapprovalDaoImpl implements Disapprovaldao{

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${showslnastatus}")
	String showslnastatus;
	
	@Value("${SetDistApprovalStatus}")
	String SetDistApprovalStatus;
	
	@Value("${stateList}")
	String allStateQuery;
	
	@Override
	public List<DisApprovalBean> checkstatus(Integer stcode) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<DisApprovalBean> list = null;
		DisApprovalBean counter;
		String sql = showslnastatus;
		try {
			SQLQuery query = session.createSQLQuery(sql);
			 query.setInteger("stcode", stcode);
			 list =  query.list();
			
			 System.out.println("value of list:" +list);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public Boolean updateslnadisapp(String val, Integer stcode) {
		Boolean res=false;
		Integer value=0;
		String savesql=SetDistApprovalStatus;
		
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			 {
				
				  
				 SQLQuery query = session.createSQLQuery(savesql);
				  Date d= new Date();
			        
				        query.setString("val", val);
				        query.setDate("d", d);
			        	query.setInteger("stcode",stcode);
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
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return res;
		
	}

	@Override
	public LinkedHashMap<Integer, String> getAllState() {
		List<IwmpState> stateList=new ArrayList<IwmpState>();
		String hql=allStateQuery;
		LinkedHashMap<Integer, String> stateMap=new LinkedHashMap<Integer, String>();
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			stateList = query.list();
		for (IwmpState state : stateList) {
			
			stateMap.put(state.getStCode(), state.getStName());
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
        return stateMap;
	}
	
	
}
