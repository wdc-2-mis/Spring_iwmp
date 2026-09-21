package app.swachhata.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.model.master.IwmpVillage;
import app.swachhata.dao.WatershedSwachhataDao;

@Repository("WatershedSwachhataDao")
public class WatershedSwachhataDaoImpl implements WatershedSwachhataDao{

	
	@Autowired
	protected SessionFactory sessionFactory; 
	
	
	
	
	@Override
	public LinkedHashMap<Integer, String> getVillagebyProjIdBlock(Integer projid, Integer block) {

		List<IwmpVillage> villList = new ArrayList<IwmpVillage>();
		String hql="select village from IwmpVillage village where village.vcode in(select distinct iwmpVillage.vcode from IwmpProjectLocation where iwmpMProject.projectId =:projid) and village.vcode in(select v.vcode from IwmpVillage v where v.iwmpGramPanchayat.iwmpBlock.bcode=:blk)";
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projid", projid);
			query.setInteger("blk", block);
			villList = query.list();
		
			for (IwmpVillage vill : villList) {
				map.put(vill.getVcode(), vill.getVillageName());
		
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
