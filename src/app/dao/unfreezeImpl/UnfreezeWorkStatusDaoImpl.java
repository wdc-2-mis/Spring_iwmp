package app.dao.unfreezeImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.UnfreezeWorkStatusDataBean;
import app.bean.UnfreezeBaselineSurveyDataBean;
import app.dao.unfreeze.UnfreezeWorkStatusDao;

@Repository("UnfreezeWorkStatusDao")
public class UnfreezeWorkStatusDaoImpl implements UnfreezeWorkStatusDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getUnfreezeWorkStatusData}") 
	String getUnfreezeWorkStatusData;
	
	@Value("${getUnfreezeWorkStatusDataAllYr}") 
	String getUnfreezeWorkStatusDataAllYr;
	
	@Value("${getUnfreezeWorkStatusDataAllproject}") 
	String getUnfreezeWorkStatusDataAllproject;
	
	
	
	

	@Override
	public List<UnfreezeWorkStatusDataBean> getUnfreezeWorkStatusData(Integer finyr, Integer project, Integer distcode) {
		
		List<UnfreezeWorkStatusDataBean> result=new ArrayList<UnfreezeWorkStatusDataBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				String hql1=null;
				String hql2=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=getUnfreezeWorkStatusData;
				hql1=getUnfreezeWorkStatusDataAllYr;
				hql2=getUnfreezeWorkStatusDataAllproject;
				if(finyr > 0 && project>0) {
				    query = session.createSQLQuery(hql);
					query.setInteger("finyr", finyr);
					query.setInteger("proj", project);
				}
				if(finyr.equals(0) && project>0) {
					 query = session.createSQLQuery(hql1);
					 query.setInteger("proj", project);
				}//distcode
				if(project.equals(0) ) {
					 query = session.createSQLQuery(hql2);
					 query.setInteger("distcode", distcode);
				}
				query.setResultTransformer(Transformers.aliasToBean(UnfreezeWorkStatusDataBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}finally {
			
			session.getTransaction().commit();
		}
		return result;
	}





	@Override
	public boolean unfreezeWorkStatusComplete(String[] workcode, String project, String finCode) {
		
		Boolean res=false;
		Integer value=0;
		Integer value1=0;
		String reskd=null;
		Session sessionf = sessionFactory.getCurrentSession();
		
		BigDecimal obj = new BigDecimal("0.0");
		//String deletesql=unfreezebalselinecomplete ;
		try {
			
				sessionf.beginTransaction();
				for(int i=0; i<workcode.length; i++)
				{
					SQLQuery query = sessionf.createSQLQuery("select coalesce(max(achievement), 0) as achievement from wdcpmksy_project_phy_asset_achievement  where asset_id=:workid");
				    query.setInteger("workid", Integer.parseInt(workcode[i]));
				    reskd = query.list().get(0).toString();
				 //   Integer record = Integer.parseInt(query.list().get(0).toString());
				   
				    double f = Double.parseDouble(reskd);
				 //   Long rat=Long.parseLong(reskd);        
				    BigDecimal b = BigDecimal.valueOf(f);
				//    System.out.println("kdy = "+b);
				   if(b.intValue()>0) {
					   
					   
				   }
				   else {
					   	SQLQuery query2 = sessionf.createSQLQuery("delete from wdcpmksy_project_phy_asset_achievement where status='D' and asset_id=:workid");
					   	query2.setInteger("workid", Integer.parseInt(workcode[i]));
						value1=query2.executeUpdate();
					   
					   	SQLQuery query1 = sessionf.createSQLQuery("delete from iwmp_project_asset_status where status='C' and  assetid=:workid");
					   	query1.setInteger("workid", Integer.parseInt(workcode[i]));
						value=query1.executeUpdate();
						if(value>0) 
						{
							res=true;
						}
						else {
								//sessionf.getTransaction().rollback();
								return false;
						}
						
				   }
			  
				}
				
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
			return false;
		}
		finally {
			
			sessionf.getTransaction().commit();	
			
			if(sessionf.isOpen()) {
				sessionf.close();
			}
		}
		return res;
	}

}
