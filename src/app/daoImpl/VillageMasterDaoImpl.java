package app.daoImpl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.MenuMap;
import app.bean.ProjectLocationBean;
import app.dao.VillageMasterDao;
import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.master.IwmpVillage;

@Repository("villageMasterDao")
public class VillageMasterDaoImpl implements VillageMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getVillageBlockWise}")
	String getVillageBlockWise;

	@Value("${getVillageByVillageCode}")
	String getVillageByVillageCode;

	@Value("${getInactiveVillageList}")
	String getInactiveVillageList;

	@Value("${getActiveVillageList}")
	String getActiveVillageList;

	@Value("${getActiveVillageListAll}")
	String getActiveVillageListAll;
	
	@Value("${getVillageOfProject}")
	String getVillageOfProject;

	@Override
	public LinkedHashMap<Integer, String> getVillageBlockWise(Integer bcode) {
		// TODO Auto-generated method stub
		List<IwmpVillage> villList = new ArrayList<IwmpVillage>();
		String hql = getVillageBlockWise;
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("block", bcode);
			villList = query.list();
		//	int a=villList.size();
			for (IwmpVillage vill : villList) {
				map.put(vill.getVcode(), vill.getVillageName() + " ( "+vill.getIwmpGramPanchayat().getGramPanchayatName()+" )");
			//	System.out.println( vill.getVcode()+" "+vill.getVillageName()+ " ("+vill.getIwmpGramPanchayat().getGramPanchayatName()+" )");
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectLocationBean> getVillageByVillageCode(List<Integer> vcode) {
		// TODO Auto-generated method stub
		List<String> villageList = new ArrayList<String>();
		List<ProjectLocationBean> villageList1 = new ArrayList<ProjectLocationBean>();
		Session session = sessionFactory.getCurrentSession();
		String hql = getVillageByVillageCode;
		try {

			session.beginTransaction();

			SQLQuery query = session.createSQLQuery(hql);
			query.setParameterList("vcode", vcode);
			query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			villageList1 = query.list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return villageList1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpVillage> getVillageList(int stateCode, int districtCode, int blockCode, int gpCode) {
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		List<IwmpVillage> temp = null;
		try {
			temp = session.createQuery(getInactiveVillageList).setParameter("stcode", stateCode)
					.setParameter("distcode", districtCode).setParameter("blockCode", blockCode)
					.setParameter("gcode", gpCode).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}

	@Override
	public void updateVillageList(List<IwmpVillage> villages) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

			if (null != villages && villages.size() > 0) {
				for (IwmpVillage village : villages) {
					System.out.print("test" + village.getUpdatestatus());
					if (village.getUpdatestatus() == true) {

						IwmpVillage tproject = (IwmpVillage) session.load(IwmpVillage.class, village.getVcode());
						System.out.println("true" + village.getUpdatestatus());
						System.out.println("gp" + tproject.getIwmpGramPanchayat().getGramPanchayatName());
						tproject.setVillageName(tproject.getVillageName() + "[ "
								+ tproject.getIwmpGramPanchayat().getGramPanchayatName() + " GP]");

						tproject.setImportType("SLNA");
						tproject.setRequestIp(ipadd);
						tproject.setLastUpdatedDate(d);
						tproject.setActive(true);
						session.saveOrUpdate(tproject);
						System.out.print("done" + village.getUpdatestatus());

					}
				}
			}
			System.out.print("project");
		} catch (Exception ex) {
			System.out.print("Err" + ex.getMessage());

			tx.rollback();
			ex.printStackTrace();
		} finally {
			tx.commit();

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpVillage> getActiveVillageList(int stateCode, int districtCode, int blockCode, int gpCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		List<IwmpVillage> temp = null;
		try {
			temp = session.createQuery(getActiveVillageList).setParameter("stcode", stateCode)
					.setParameter("distcode", districtCode).setParameter("blockCode", blockCode)
					.setParameter("gcode", gpCode).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}

	@Override
	public IwmpVillage findVillageVcode(Integer vcode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
	

		session.beginTransaction();
		IwmpVillage village =null;
		try {

			if (null != vcode && vcode > 0) {

				village = (IwmpVillage) session.createQuery("from IwmpVillage where vcode=:vcode")
						.setParameter("vcode", vcode).uniqueResult();
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
		return village;
	}

	@Override
	public Boolean updateVillageList(IwmpVillage village) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Boolean flag=false;
		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

				if (village!=null) {

						IwmpVillage tproject = (IwmpVillage) session.load(IwmpVillage.class, village.getVcode());
						System.out.println("true" + village.getUpdatestatus());
						System.out.println("gp" + tproject.getIwmpGramPanchayat().getGramPanchayatName());
						tproject.setVillageName(village.getVillageName());
						tproject.setIwmpGramPanchayat(village.getIwmpGramPanchayat());

						tproject.setImportType("SLNA");
						tproject.setRequestIp(ipadd);
						tproject.setLastUpdatedDate(d);
						//tproject.setActive(true);
						session.saveOrUpdate(tproject);
						System.out.print("done" + village.getUpdatestatus());

						flag=true;
					}
			
		} catch (Exception ex) {
			System.out.print("Err" + ex.getMessage());
			session.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			tx.commit();

		}
		return flag;
	}

	@Override
	public List<IwmpVillage> getVillageList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		List<IwmpVillage> temp = null;
		try {
			temp = session.createQuery(getActiveVillageListAll).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}

	@Override
	public LinkedHashMap<Integer, String> getVillageOfProject(Integer projId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		//List<IwmpVillage> temp = null;
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		String hql = getVillageOfProject;
		try {
			Query query =session.createQuery(hql);
			query.setInteger("projId", projId);
			query.setResultTransformer(Transformers.aliasToBean(IwmpVillage.class));
			List<IwmpVillage> temp = query.list();
			for(IwmpVillage v: temp) {
				map.put(v.getVcode(), v.getVillageName());
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return map;
	}

}
