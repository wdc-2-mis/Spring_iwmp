package app.watershedyatra.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp; 
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
	
	
	
	
	
	@Override
	public String saveRoutePlanVanTravelingLMS(Integer state, Integer district, Integer block, Integer grampan,
			Integer village, String location, String datetime, HttpSession session, Integer district1, Integer block1,
			Integer grampan1, Integer village1, String location1, String datetime1) {
		// TODO Auto-generated method stub
		Session sess = sessionFactory.getCurrentSession();
		String res="fail";
		try {
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; 
				LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter); 
				Timestamp timestamp1 = Timestamp.valueOf(localDateTime); 
			//	System.out.println("Converted Timestamp: " + timestamp);
				
				LocalDateTime localDateTime1 = LocalDateTime.parse(datetime1, formatter); 
				Timestamp timestamp2 = Timestamp.valueOf(localDateTime1); 
			
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				
				sess.beginTransaction();
				
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
				main.setStatus('C');
				main.setCreatedBy(session.getAttribute("loginID").toString());
				main.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
				main.setRequestedIp(ipAddr);
				
				sess.save(main);
				
				
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
				main1.setStatus('C');
				main1.setCreatedBy(session.getAttribute("loginID").toString());
				main1.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
				main1.setRequestedIp(ipAddr);
				
				sess.save(main1);
			
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
		String getReport=getRoutePlanVanTravel;
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
		return list;
	}

}
