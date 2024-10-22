package app.daoImpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.AddOutcomeParaBean;
import app.bean.reports.PhysicalActionAchievementBean;
import app.dao.reports.MnthWiseMndyFrmrDtlDao;



@Repository("MnthWiseMndyFrmrDtlDao")
public class MnthWiseMndyFrmrDtlDaoImpl implements MnthWiseMndyFrmrDtlDao {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getAllStatesMnthWiseMndyReport}")
	String getAllStatesMnthWiseMndyReport;
	
	@Value("${getAllDistrictMnthWiseMndyReport}")
	String getAllDistrictMnthWiseMndyReport;
	
	@Value("${getAllProjectMnthWiseMndyReport}")
	String getAllProjectMnthWiseMndyReport;
	
	@Value("${getProjectMnthWiseMndyReport}")
	String getProjectMnthWiseMndyReport;

	@Override
	public List<AddOutcomeParaBean> getMnthWiseMndyFrmrDtlList(int stcode, int dcode, int projid, int yrcd) {

		
		String getReport=null;
		Session session = sessionFactory.getCurrentSession();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		SQLQuery query;
		try {
			session.beginTransaction();   
			
			
			if(stcode==0 ) 
			{
				getReport=getAllStatesMnthWiseMndyReport;
				query= session.createSQLQuery(getReport);
				//query.setInteger("stcode",stCode); 
				query.setInteger("fromYear",yrcd); 
				query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
				list = query.list();
			}
			if(stcode!=0 && dcode==0) 
			{
				getReport=getAllDistrictMnthWiseMndyReport;
				query= session.createSQLQuery(getReport);
				query.setInteger("stcode",stcode); 
				query.setInteger("fromYear",yrcd); 
				query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
				list = query.list();
			}
			if(dcode!=0 && projid==0) 
			{
				getReport=getAllProjectMnthWiseMndyReport;
				query= session.createSQLQuery(getReport);
				query.setInteger("dcode",dcode); 
				query.setInteger("fromYear",yrcd); 
				query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
				list = query.list();
			}
			if(projid!=0) 
			{
				getReport=getProjectMnthWiseMndyReport;
				query= session.createSQLQuery(getReport);
				query.setInteger("projId",projid); 
				query.setInteger("fromYear",yrcd); 
				query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
				list = query.list();
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return list;
	
	}

}
