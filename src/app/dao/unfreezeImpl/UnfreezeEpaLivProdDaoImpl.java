package app.dao.unfreezeImpl;

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

import app.bean.UnfreezeEpaLivProdBean;
import app.dao.unfreeze.UnfreezeEpaLivProdDao;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.ProductionDetail;

@SuppressWarnings("deprecation")
@Repository("UnfreezeEpaLivProdDao")
public class UnfreezeEpaLivProdDaoImpl implements UnfreezeEpaLivProdDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getEpaDetails}") 
	String getEpaData;
	
	@Value("${getLivelihoodDetails}") 
	String getLivelihoodData;
	
	@Value("${getProductionDetails}") 
	String getProductionData;
	
	@Value("${getEpaWorkid}") 
	String getEpaWorkidData;
	
	@Value("${getLivelihoodWorkid}") 
	String getLivelihoodWorkidData;
	
	@Value("${getProductionWorkid}") 
	String getProductionWorkidData;
	
	@Override
	public List<UnfreezeEpaLivProdBean> getUnfreezeEpaLivProdData(Integer projid, String head) {
		
		Session session = sessionFactory.openSession();
		List<UnfreezeEpaLivProdBean> result = new ArrayList<UnfreezeEpaLivProdBean>();
		
		try {
			String Ehql=null;
			String Lhql=null;
			String Phql=null;
			SQLQuery query = null;
			Transaction tx = session.beginTransaction();
			Ehql=getEpaData;
			Lhql=getLivelihoodData;
			Phql=getProductionData;
			
			if(head.equals("l"))
			{
				query = session.createSQLQuery(Lhql);
			}
			else if(head.equals("p"))
			{
				query = session.createSQLQuery(Phql);
			}
			else if(head.equals("e"))
			{
				query = session.createSQLQuery(Ehql);
			}
			query.setInteger("pid", projid);
			query.setResultTransformer(Transformers.aliasToBean(UnfreezeEpaLivProdBean.class));
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
	public boolean unfreezeEpaLivProdData(String[] actCode, String head) 
	{
		Boolean res=false;
		Session session = sessionFactory.getCurrentSession();
		
		try 
		{
			session.beginTransaction();
			
			for(String code : actCode)
			{
				Integer id = Integer.parseInt(code);
				
				if(head.equals("e"))
				{
					EpaDetail e = session.load(EpaDetail.class, id);
					e.setStatus("D");
					session.update(e);
				}
				else if(head.equals("l"))
				{
					LivelihoodDetail l = session.load(LivelihoodDetail.class, id);
					l.setStatus("D");
					session.update(l);
				}
				else if(head.equals("p"))
				{
					ProductionDetail p = session.load(ProductionDetail.class, id);
					p.setStatus("D");
					session.update(p);
				}
			}
			session.getTransaction().commit();
			res=true;
		}
		catch(Exception ex) 
		{ 
			ex.printStackTrace();
			session.getTransaction().rollback();
		return false;
		}
		
		return res;
	}

	@Override
	public List<UnfreezeEpaLivProdBean> getUnfreezeWorkIdEpaLivProd(Integer projid, String head, Integer act) 
	{
		Session session = sessionFactory.openSession();
		List<UnfreezeEpaLivProdBean> result = new ArrayList<UnfreezeEpaLivProdBean>();
		
		try {
			String Ehql=null;
			String Lhql=null;
			String Phql=null;
			SQLQuery query = null;
			Transaction tx = session.beginTransaction();
			Ehql=getEpaWorkidData;
			Lhql=getLivelihoodWorkidData;
			Phql=getProductionWorkidData;
			
			if(head.equals("l"))
			{
				query = session.createSQLQuery(Lhql);
			}
			else if(head.equals("p"))
			{
				query = session.createSQLQuery(Phql);
			}
			else if(head.equals("e"))
			{
				query = session.createSQLQuery(Ehql);
			}
			query.setInteger("pid", projid);
			query.setInteger("aid", act);
			query.setResultTransformer(Transformers.aliasToBean(UnfreezeEpaLivProdBean.class));
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
	public boolean unfreezeWorkIdEpaLivProdData(String[] actCode, String head) 
	{
		Boolean res=false;
		Session session = sessionFactory.getCurrentSession();
		
		try 
		{
			session.beginTransaction();
			
			for(String code : actCode)
			{
				Integer id = Integer.parseInt(code);
				
				if(head.equals("e"))
				{
					SQLQuery sqlQuery = session.createSQLQuery("delete from wdcpmksy_project_asset_epa_status where assetid=:aid");
					sqlQuery.setInteger("aid",id);
					sqlQuery.executeUpdate();
				}
				else if(head.equals("l"))
				{
					SQLQuery sqlQuery = session.createSQLQuery("delete from wdcpmksy_project_asset_livelihood_status where assetid=:aid");
					sqlQuery.setInteger("aid",id);
					sqlQuery.executeUpdate();
				}
				else if(head.equals("p"))
				{
					SQLQuery sqlQuery = session.createSQLQuery("delete from wdcpmksy_project_asset_prod_status where assetid=:aid");
					sqlQuery.setInteger("aid",id);
					sqlQuery.executeUpdate();
				}
			}
			session.getTransaction().commit();
			res=true;
		}
		catch(Exception ex) 
		{ 
			ex.printStackTrace();
			session.getTransaction().rollback();
		return false;
		}
		
		return res;
	}
	
	
}

