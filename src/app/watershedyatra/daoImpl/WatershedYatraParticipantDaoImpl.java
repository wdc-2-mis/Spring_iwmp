package app.watershedyatra.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.dao.WatershedYatraParticipantDao;


@Repository("WatershedYatraParticipantDao")
public class WatershedYatraParticipantDaoImpl implements WatershedYatraParticipantDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Value("${getWatershedYatraParticipantReport}") 
	String getWatershedYatraParticipantReport;
	
	@Value("${getWatershedYatraParticipantReportDateWise}") 
	String getWatershedYatraParticipantReportDateWise;
	
	@Value("${getWatershedYatraParticipantReportgrandtot}") 
	String getWatershedYatraParticipantReportgrandtot;
	
	@Value("${getWatershedYatraParticipantReportDateWisegrandtot}") 
	String getWatershedYatraParticipantReportDateWisegrandtot;

	@Override
	public List<NodalOfficerBean> getWatershedYatraParticipant(String userdate, String userdateto) {
		
		String getReport=getWatershedYatraParticipantReport;
		String getReport1=getWatershedYatraParticipantReportDateWise;
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		Date yadate =null;
		Date yadateto =null;
		try { //  male_participants+female_participants+central_minister+state_minister+parliament_members+legislative_assembly_members+legislative_council_members+other_public_representatives+gov_officials
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				session.beginTransaction();
				
				if(userdate !=null && !userdate.equals("")) 
					yadate = formatter.parse(userdate);
				
				if(userdateto!=null && !userdateto.equals("")) 
					yadateto = formatter.parse(userdateto);
			
				
				if( (userdate !=null && !userdate.equals("")) && (userdateto!=null && !userdateto.equals(""))) {
					Query query= session.createSQLQuery(getReport1);
					query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
					query.setParameter("userdate",yadate);
					query.setParameter("yadateto",yadateto);
					list = query.list();
				}
				else {
					Query query= session.createSQLQuery(getReport);
					query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
					list = query.list();
				}
				
				
				
				
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

	@Override
	public List<NodalOfficerBean> getWatershedYatraParticipantgrand(String userdate, String userdateto) {
		
		String getReport=getWatershedYatraParticipantReportgrandtot;
		String getReport1=getWatershedYatraParticipantReportDateWisegrandtot;
		Session session = sessionFactory.getCurrentSession();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		Date yadate =null;
		Date yadateto =null;
		try { //  male_participants+female_participants+central_minister+state_minister+parliament_members+legislative_assembly_members+legislative_council_members+other_public_representatives+gov_officials
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				session.beginTransaction();
				
				if(userdate !=null && !userdate.equals("")) 
					yadate = formatter.parse(userdate);
				
				if(userdateto!=null && !userdateto.equals("")) 
					yadateto = formatter.parse(userdateto);
			
				
				if( (userdate !=null && !userdate.equals("")) && (userdateto!=null && !userdateto.equals(""))) {
					Query query= session.createSQLQuery(getReport1);
					query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
					query.setParameter("userdate",yadate);
					query.setParameter("yadateto",yadateto);
					list = query.list();
				}
				else {
					Query query= session.createSQLQuery(getReport);
					query.setResultTransformer(Transformers.aliasToBean(NodalOfficerBean.class));
					list = query.list();
				}
				
				
				
				
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
