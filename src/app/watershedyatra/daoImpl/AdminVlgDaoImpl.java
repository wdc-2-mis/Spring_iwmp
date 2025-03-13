package app.watershedyatra.daoImpl;

import java.util.ArrayList;
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

import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.watershedyatra.bean.AdminVlgBean;
import app.watershedyatra.dao.AdminVlgDao;
import app.watershedyatra.model.WatershedYatVill;
import app.watershedyatra.model.WatershedYatVillDuplicate;

@Repository("AdminVlgDao")
public class AdminVlgDaoImpl implements AdminVlgDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${blockList}")
	String blockList;
	  
	@Value("${gpList}")
	String gpList;
	  
	@Value("${vlgList}")
	String vlgList;
	  
	@Value("${getDupWatershedYatraVlgData}")
	String getDupWatershedYatraVlgDetails;
	
	
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
	public List<IwmpVillage> getVlgList(int gp) {
		List<IwmpVillage> result=new ArrayList<IwmpVillage>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=vlgList;
			result = ses.createQuery(hql).setParameter("gp", gp).list();
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
	public String saveDupWatershedYatraVlgData(String village, String villageName, HttpSession session) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		String res="fail";
		
		try {
			sess.beginTransaction();
			
			WatershedYatVillDuplicate data = new WatershedYatVillDuplicate();
			IwmpVillage vlg = new IwmpVillage();
			
			vlg.setVcode(Integer.parseInt(village));
			data.setIwmpVillage(vlg);
			data.setVillageName(villageName);
			
			sess.save(data);
			res="success";
			sess.getTransaction().commit();
			
		}
		catch(Exception ex) 
		{
			res = "fail";
			ex.printStackTrace();
			sess.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public List<AdminVlgBean> getDupWatershedYatraVlgData() {

		Session session = sessionFactory.getCurrentSession();
		
		String getReport=getDupWatershedYatraVlgDetails;
		
		List<AdminVlgBean> list = new ArrayList<AdminVlgBean>();
		
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setResultTransformer(Transformers.aliasToBean(AdminVlgBean.class));
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

	@Override
	public String deleteVlgData(Integer watershed_id) {
		
		String str="fail";
		Session session = sessionFactory.getCurrentSession();
		Integer value=0;
		
		try {
			 
			 session.beginTransaction();
			 SQLQuery query = session.createSQLQuery("delete from watershed_yatra_village_duplicate where watershed_id=:watershed_id");
			 query.setInteger("watershed_id", watershed_id);
			 
			 value=query.executeUpdate();
			 if(value>0) {
				 str="success";
			 }
			 else {
				session.getTransaction().rollback();
				str="fail";
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
	

}
