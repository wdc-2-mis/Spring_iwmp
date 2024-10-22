/**
 * 
 */
package app.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.DistrictMasterDao;
import app.model.IwmpDistrict;
import app.model.IwmpMAreaType;
import app.model.master.IwmpGramPanchayat;

@Repository("districtMasterDao")
public class DistrictMasterDaoImpl implements DistrictMasterDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${districtListByStateCode}")
	String districtListByStateCode;
	
	@Value("${districtList}")
	String districtList;
	
	@Value("${districtListByDistCode}")
	String districtListByDistCode;

	@Override
	public LinkedHashMap<Integer, String> getDistrictByStateCode(Integer stateCode) {
		List<IwmpDistrict> districtList=new ArrayList<IwmpDistrict>();
		String hql=districtListByStateCode;
		LinkedHashMap<Integer, String> distMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("stCode", stateCode);
			districtList = query.list();
			
			for (IwmpDistrict district : districtList) {
				distMap.put(district.getDcode(), district.getDistName());
			//	System.out.println(district.getDcode()+" k "+district.getDistName());
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
        return distMap;
	}
	
	@Override
	public LinkedHashMap<Integer, String> getDistrictByDistCode(Integer distCode) {
		List<IwmpDistrict> districtList=new ArrayList<IwmpDistrict>();
		String hql=districtListByDistCode;
		LinkedHashMap<Integer, String> distMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("distCode", Long.parseLong(Integer.toString(distCode)));
			districtList = query.list();
			for (IwmpDistrict district : districtList) {
				distMap.put(district.getDistCode(), district.getDistName());
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
        return distMap;
	}
	
	@Override
	public LinkedHashMap<Integer, String> getDistrictByStateCodeWithDcode(Integer stateCode) {
		List<IwmpDistrict> districtList=new ArrayList<IwmpDistrict>();
		String hql=districtListByStateCode;
		LinkedHashMap<Integer, String> distMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("stCode", Long.parseLong(Integer.toString(stateCode)));
			districtList = query.list();
			for (IwmpDistrict district : districtList) {
				distMap.put(district.getDcode(), district.getDistName());
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
        return distMap;
	}

	@Override
	public IwmpDistrict findDistrictDcodecode(int dCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		

		session.beginTransaction();
		IwmpDistrict district =null;
		try {

			if ( dCode > 0) {

				district = (IwmpDistrict) session.createQuery("from IwmpDistrict where dcode=:dCode")
						.setParameter("dCode", dCode).uniqueResult();
			}
			session.getTransaction().commit();
			//System.out.print("project==" + village.getVillageName());
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			session.getTransaction().rollback();
			e.printStackTrace();

		} catch (Exception e) {
			System.err.print(" error" + e.getMessage());
			session.getTransaction().rollback();
			e.printStackTrace();

		}

		finally {
		

		}
		return district;
	}

	@Override
	public Boolean updateDistrict(IwmpDistrict dist) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Boolean flag=false;
		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

				if (dist!=null) {
					IwmpDistrict district = (IwmpDistrict) session.load(IwmpDistrict.class, dist.getDcode());
						
					district.setDistName(dist.getDistName());

					//district.set("SLNA");
					district.setRequestIp(ipadd);
					district.setLastUpdatedDate(d);
						//tproject.setActive(true);
						session.saveOrUpdate(district);
						System.out.print("done" + district.getCensusCodePortedData());

						flag=true;
					}
			
		} catch (Exception ex) {
			System.out.print("Err" + ex.getMessage());

			tx.rollback();
			ex.printStackTrace();
		} finally {
			tx.commit();

		}
		return flag;
	}

	@Override
	public List<IwmpDistrict> getDistrictListState(int stateCode) {
		// TODO Auto-generated method stub
		List<IwmpDistrict> districtList=new ArrayList<IwmpDistrict>();
		String hql=districtListByStateCode;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("stCode", stateCode);
			districtList = query.list();
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
        return districtList;
	}
	
	@Override
	public List<IwmpDistrict> getDistrictListDistCode(int distCode) {
		// TODO Auto-generated method stub
		List<IwmpDistrict> districtList=new ArrayList<IwmpDistrict>();
		String hql=districtListByDistCode;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("distCode", distCode);
			districtList = query.list();
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
        return districtList;
	}

	@Override
	public List<IwmpDistrict> getDistrictList() {
		// TODO Auto-generated method stub
		List<IwmpDistrict> districtList=new ArrayList<IwmpDistrict>();
		String hql=this.districtList;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			
			districtList = query.list();
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
        return districtList;
	}

	@Override
	public LinkedHashMap<String, Integer> getDistrictDataNew(Integer stateCode) {
		// TODO Auto-generated method stub
		List<IwmpDistrict> districtList=new ArrayList<IwmpDistrict>();
		String hql=districtListByStateCode;
		LinkedHashMap<String, Integer> distMap=new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("stCode", stateCode);
			districtList = query.list();
			
			for (IwmpDistrict district : districtList) {
				distMap.put( district.getDistName(), district.getDcode());
			//	System.out.println(district.getDcode()+" k "+district.getDistName());
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
        return distMap;
	}
	
	
}
