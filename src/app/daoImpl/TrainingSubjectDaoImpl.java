package app.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
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

import app.bean.CapicityBuildingTrainingBean;
import app.bean.GroundWaterTableBean;
import app.dao.TrainingSubjectDao;
import app.model.outcome.MTrainingSubject;
import app.model.outcome.TrainingDetail;
import app.model.outcome.TrainingMain;
import app.model.outcome.TrainingSubjectDetail;

@Repository("trainingSubjectDao")
public class TrainingSubjectDaoImpl implements TrainingSubjectDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getTrainingSubject}")
	String getTrainingSubject;
	
	@Value("${getCapicityBuildingReport}")
	String getCapicityBuildingReport;
	
	@Value("${getTrainingDetail12HOURS}")
	String getTrainingDetail12HOURS;
	
	

	@Override
	public LinkedHashMap<Integer, String> getTrainingSubject() {
		
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		String sql=getTrainingSubject;
		try {
		/*	session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			List<Object[]> rows = query.list();
			String[] strArray=null;
			for(Object[] row : rows){
			  strArray = Arrays.stream(row).toArray(String[]::new);
			  map.put(Integer.parseInt(strArray[0]), strArray[1]);
			}
			session.getTransaction().commit();  */
			
			
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(sql);
			List<Object[]> rows1 = query.list();
			for(Object[] row : rows1){
				  map.put(Integer.parseInt(row[0].toString()),row[1].toString());
			}
			session.getTransaction().commit();
			
			
		}
		catch(Exception ex) 
		{
			System.out.print("getTraining subject line number : ");
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			//session.flush();
		//session.close();
		}
		return map;
	}



	@Override
	public String saveCapicityBuilding(String level, Integer noOfTConduct, List<Integer> noOfperson,
			List<String> subject, List<String> startdate, List<String> enddate, List<Integer> trainday,
			HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		String res="fail";
		try {
			
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		    	//Calendar date = Calendar.getInstance();
		    	
		    	java.util.Date startD=new java.util.Date();
		    	java.util.Date endD=new java.util.Date();
		    	
				
				sess.beginTransaction();
				TrainingMain main =new TrainingMain();
				TrainingDetail detl= new TrainingDetail();
				TrainingSubjectDetail subdtl =new TrainingSubjectDetail();
				MTrainingSubject msub= new MTrainingSubject();
				
				main.setTrainingLevel(level);
				main.setTotalno(noOfTConduct);
				main.setState_cd(Integer.parseInt(session.getAttribute("stateCode").toString()));
				main.setCreatedBy(session.getAttribute("loginID").toString());
				main.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				main.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
				main.setRequestIp(ipAddr);
				sess.save(main);
				for(int i=0; i<noOfTConduct;i++) {
					startD=formatter.parse(enddate.get(i));
					detl.setEndDate(startD);
					detl.setPersonNo(noOfperson.get(i));
					endD=formatter.parse(startdate.get(i));
					detl.setStartDate(endD);
					detl.setTrainingDays(trainday.get(i));
					detl.setTrainingMain(main);
					detl.setCreatedBy(session.getAttribute("loginID").toString());
					detl.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					detl.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
					detl.setRequestIp(ipAddr);
					sess.save(detl);
					sess.evict(detl);
					String sub[] = subject.get(i).split("#");
					for(String s : sub) {
						msub = (MTrainingSubject) sess.get(MTrainingSubject.class, Integer.parseInt(s));
						subdtl.setMTrainingSubject(msub);
						subdtl.setTrainingDetail(detl);
						subdtl.setCreatedBy(session.getAttribute("loginID").toString());
						subdtl.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
						subdtl.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
						subdtl.setRequestIp(ipAddr);
						sess.save(subdtl);
						sess.evict(subdtl);
					}

				}
				
				sess.getTransaction().commit();
				res="success";
		
		}
		catch(Exception ex) 
		{
			System.out.print("getTraining subject line number : ");
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
	public List<CapicityBuildingTrainingBean> getcapicityBuildingReport() {
		
		List<CapicityBuildingTrainingBean> result=new ArrayList<CapicityBuildingTrainingBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getCapicityBuildingReport;
			  query = session.createSQLQuery(hql);
			  query.setResultTransformer(Transformers.aliasToBean(CapicityBuildingTrainingBean.class));
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
	public List<CapicityBuildingTrainingBean> getTrainingDetail12HOURS(Integer state) {
		
		List<CapicityBuildingTrainingBean> result=new ArrayList<CapicityBuildingTrainingBean>();
		Session session = sessionFactory.openSession();
		try {
			String hql=null;
			SQLQuery query = null;
			
			  @SuppressWarnings("unused")
			  Transaction tx = session.beginTransaction(); 
			  hql=getTrainingDetail12HOURS;
			  query = session.createSQLQuery(hql);
			  query.setInteger("stcode", state);
			  query.setResultTransformer(Transformers.aliasToBean(CapicityBuildingTrainingBean.class));
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
