package app.watershedyatra.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp; 
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.dao.RoutePlanVanTravelingDao;
import app.watershedyatra.model.RoutePlanVanTravel;

@Repository("RoutePlanVanTravelingDao")
public class RoutePlanVanTravelingDaoImpl implements RoutePlanVanTravelingDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Value("${getRoutePlanVanTravel}")
	String getRoutePlanVanTravel;
	
	@Value("${getRoutePlanVanTravelcomp}")
	String getRoutePlanVanTravelcomp;
	
	@Value("${getPIARoutePlanVanTravel}")
	String getPIARoutePlanVanTravel;
	
	@Value("${getPIARoutePlanVanTravelcomp}")
	String getPIARoutePlanVanTravelcomp;
	
	
	
	@Override
	public String saveRoutePlanVanTravelingLMS(Integer state, Integer district, Integer block, Integer grampan,
			Integer village, String location, String datetime, HttpSession session, Integer district1, Integer block1,
			Integer grampan1, Integer village1, String location1, String datetime1) {
		// TODO Auto-generated method stub
		Session sess = sessionFactory.getCurrentSession();
		String res="fail";
		Integer result=0;
		LocalDateTime localDateTime1=null;
		Timestamp timestamp2=null;
		try {
			
				sess.beginTransaction();
				List list = sess.createQuery("Select coalesce(max(flagwise),0)+1 from RoutePlanVanTravel").list();
			//	System.out.println(list.get(0));
				result=Integer.parseInt(list.get(0).toString());
			
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; 
				LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter); 
				Timestamp timestamp1 = Timestamp.valueOf(localDateTime); 
			//	System.out.println("Converted Timestamp: " + timestamp);
				
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				
				//sess.beginTransaction();
				
				RoutePlanVanTravel main =new RoutePlanVanTravel();
				IwmpState st =new IwmpState();
				IwmpDistrict dt =new IwmpDistrict();
				IwmpBlock bl =new IwmpBlock();
				IwmpGramPanchayat gp= new IwmpGramPanchayat();
				IwmpVillage v= new IwmpVillage();
				
				st.setStCode(state);
				main.setIwmpState(st);
				dt.setDcode(district);
				main.setIwmpDistrict(dt);
				bl.setBcode(block);
				main.setIwmpBlock(bl);
				gp.setGcode(grampan);
				main.setIwmpGramPanchayat(gp);
				v.setVcode(village);
				main.setIwmpVillage(v);
				main.setLocation1(location);
				main.setDate1(timestamp1);
				main.setFlagwise(result);
				main.setStatus('D');
				main.setCreatedBy(session.getAttribute("loginID").toString());
				main.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
				main.setRequestedIp(ipAddr);
				
				sess.save(main);
				
				if(!datetime1.equals("") && datetime1!=null) 
				{
					localDateTime1 = LocalDateTime.parse(datetime1, formatter); 
					timestamp2 = Timestamp.valueOf(localDateTime1); 
					
					RoutePlanVanTravel main1 =new RoutePlanVanTravel();
					IwmpState st1 =new IwmpState();
					IwmpDistrict dt1 =new IwmpDistrict();
					IwmpBlock bl1 =new IwmpBlock();
					IwmpGramPanchayat gp1= new IwmpGramPanchayat();
					IwmpVillage v1= new IwmpVillage();
					
					st1.setStCode(state);
					main1.setIwmpState(st1);
					dt1.setDcode(district1);
					main1.setIwmpDistrict(dt1);
					bl1.setBcode(block1);
					main1.setIwmpBlock(bl1);
					gp1.setGcode(grampan1);
					main1.setIwmpGramPanchayat(gp1);
					v1.setVcode(village1);
					main1.setIwmpVillage(v1);
					main1.setLocation2(location1);
					main1.setDate2(timestamp2);
					main1.setFlagwise(result);
					main1.setStatus('D');
					main1.setCreatedBy(session.getAttribute("loginID").toString());
					main1.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
					main1.setRequestedIp(ipAddr);
					
					sess.save(main1);
				}
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
	public List<NodalOfficerBean> getRoutePlanVanTraveling(Integer stcd) {
		// TODO Auto-generated method stub
		String getReport=getRoutePlanVanTravel;  // getRoutePlanVanTravelcomp
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			//  session.getTransaction().commit();
		  }
		return list;
	}


	@Override
	public String getExistingVillageCodes(Integer villageCode) {
		  Session session = sessionFactory.getCurrentSession();
		  int result;
		  String data="fail";
		  try {
		    session.beginTransaction();
			List list = session.createQuery("SELECT iwmpVillage.vcode FROM RoutePlanVanTravel where iwmpVillage.vcode=:villageCode").setInteger("villageCode", villageCode).list();
		//	result=Integer.parseInt(list.get(0).toString());
			if(!list.isEmpty())
				data="success";
		  } 
		  catch (HibernateException e) {
		    System.err.print("Hibernate error");
		    e.printStackTrace();
		    session.getTransaction().rollback();
		  } catch (Exception ex) {
		    session.getTransaction().rollback();
		    ex.printStackTrace();
		  }
		  finally {
			  session.getTransaction().commit();
		  }
		  return data;
	}


	@Override
	public String completeApproveRoutePlanforVanTraveling(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("update route_plan_van_travel SET status='C', updated_by=:updated, updated_date=:datee WHERE flagwise=:nrmpkid");
			 Date d= new Date();
			 
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query.setInteger("nrmpkid", assetid.get(i));
				 query.setString("updated", userid);
				 query.setDate("datee", d);
				 value=query.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
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
		finally {
			session.getTransaction().commit();
		}
		
		return str;
	}


	@Override
	public String deleteRoutePlanforVanTraveling(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("delete from route_plan_van_travel WHERE flagwise=:nrmpkid");
			 Date d= new Date();
			 
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query.setInteger("nrmpkid", assetid.get(i));
				 value=query.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
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
		finally {
			session.getTransaction().commit();
		}
		
		return str;
	}


	@Override
	public List<NodalOfficerBean> getRoutePlanVanTravelingComp(Integer stcd) {
		// TODO Auto-generated method stub
		String getReport=getRoutePlanVanTravelcomp; 
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			//  session.getTransaction().commit();
		  }
		return list;
	}


	@Override
	public List<NodalOfficerBean> getPIARoutePlanVanTraveling(Integer stcd, String loginID) {
		String getReport=getPIARoutePlanVanTravel;  // getRoutePlanVanTravelcomp
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setParameter("loginID", loginID);
				query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			//  session.getTransaction().commit();
		  }
		return list;
	}


	@Override
	public List<NodalOfficerBean> getPIARoutePlanVanTravelingComp(Integer stcd, String loginID) {
		String getReport=getPIARoutePlanVanTravelcomp; 
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("statecd",stcd); 
				query.setParameter("loginID", loginID);
				query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			//  session.getTransaction().commit();
		  }
		return list;
	}

}
