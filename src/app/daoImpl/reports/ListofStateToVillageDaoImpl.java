package app.daoImpl.reports;

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

import app.bean.StateToVillageBean;
import app.dao.reports.ListofStateToVillageDao;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;

@Repository("ListofStateToVillageDao")
public class ListofStateToVillageDaoImpl implements ListofStateToVillageDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	 @Value("${blockList}") 
	  String blockList;
	  
	  @Value("${gpList}") 
	  String gpList;   

	  @Value("${showstatovillage}") 
	  String showstatovillage;
	  
	  @Value("${showstatovillagelgdTrue}") 
	  String showstatovillagelgdTrue;
	  
	  @Value("${showstatovillagelgdFalse}") 
	  String showstatovillagelgdFalse; 
	  
	  @Override
		public List<IwmpBlock> getBlockList(int stateCode, int distCode) {


			List<IwmpBlock> result=new ArrayList<IwmpBlock>();
			Session ses = sessionFactory.getCurrentSession();
			try {
				ses.beginTransaction();	
				String hql=blockList;
				result = ses.createQuery(hql).setParameter("distCode", distCode).list();
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
			finally {
				ses.getTransaction().commit();
			}
	        return result;
		}
		@Override
		public List<IwmpGramPanchayat> getGPList(int block) {
			List<IwmpGramPanchayat> result=new ArrayList<IwmpGramPanchayat>();
			Session ses = sessionFactory.getCurrentSession();
			try {
				ses.beginTransaction();	
				String hql=gpList;
				result = ses.createQuery(hql).setParameter("block", block).list();
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
			finally {
				ses.getTransaction().commit();
			}
	        return result;
		}
		@Override
		public List<StateToVillageBean> getListofStateToVill(int state, int district, int block, int gp, String unviewlgd, String userType) {
			
			List<StateToVillageBean> result=new ArrayList<StateToVillageBean>();
			Session session = sessionFactory.openSession();
			try {
				String hql=null;
				SQLQuery query = null;
				
				  @SuppressWarnings("unused")
				  Transaction tx = session.beginTransaction(); 
				 // query = session.createSQLQuery(hql);
				  if(userType.equals("ADMIN") || userType.equals("DL"))
				  {
					  if(unviewlgd.equals("true"))
					  {	  
					  	hql=showstatovillagelgdTrue;
					  }
					  if(unviewlgd.equals("false") )
					  {	
						  hql=showstatovillagelgdFalse;
					  }
					  if(unviewlgd.equals("0")) 
					  {
						hql =showstatovillage;
					  } 
				  }
				  else {
					  
					  hql=showstatovillagelgdTrue;
				  }
				    query = session.createSQLQuery(hql);
					query.setInteger("gramp", gp);
					query.setInteger("blcode", block);
					query.setInteger("dist", district);
					query.setInteger("stcd", state);
					
					query.setResultTransformer(Transformers.aliasToBean(StateToVillageBean.class));
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
