package app.watershedyatra.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.common.CommonFunctions;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.watershedyatra.dao.WatershedYatraPIALevelDao;

@Repository("WatershedYatraPIALevelDao")
public class WatershedYatraPIALevelDaoImpl implements WatershedYatraPIALevelDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonFunctions commonFunction;

	@Override
	public LinkedHashMap<Integer, String> getBlockListpia(String userid) {
		// TODO Auto-generated method stub
		List<IwmpVillage> villList = new ArrayList<IwmpVillage>();
		String hql="select village from IwmpVillage village where village.vcode in(select distinct iwmpVillage.vcode from IwmpProjectLocation where createdBy=:userid) ";
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("userid", userid);
			villList = query.list();
		
			for (IwmpVillage vill : villList) {
				map.put(vill.getIwmpGramPanchayat().getIwmpBlock().getBcode(), vill.getIwmpGramPanchayat().getIwmpBlock().getBlockName());
		
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
		finally {
			//session.getTransaction().commit();
		}
        return map;
	}

	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraAtPiaGPs(Integer blkCode, String userid) {
		
		List<IwmpVillage> villList = new ArrayList<IwmpVillage>();
		String hql="select village from IwmpVillage village where village.vcode in(select distinct iwmpVillage.vcode from IwmpProjectLocation where createdBy=:userid) ";
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("userid", userid);
			villList = query.list();
		
			for (IwmpVillage vill : villList) {
				map.put(vill.getIwmpGramPanchayat().getGramPanchayatName(), vill.getIwmpGramPanchayat().getGcode());
		
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
		finally {
			//session.getTransaction().commit();
		}
        return map;
	}

	@Override
	public LinkedHashMap<String, Integer> getWatershedYatraAtPiaVillage(Integer gpCode, String userid) {
		// TODO Auto-generated method stub
		List<IwmpVillage> villList = new ArrayList<IwmpVillage>();
		String hql="select village from IwmpVillage village where village.vcode in(select distinct iwmpVillage.vcode from IwmpProjectLocation where createdBy=:userid) and village.iwmpGramPanchayat.gcode=:gpCode order by village.villageName";
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("userid", userid);
			query.setInteger("gpCode", gpCode);
			villList = query.list();
		
			for (IwmpVillage vill : villList) {
				map.put(vill.getVillageName(), vill.getVcode());
		
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
		finally {
			//session.getTransaction().commit();
		}
        return map;
	}

}
