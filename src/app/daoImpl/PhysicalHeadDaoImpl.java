package app.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


import app.bean.PhysicalHeaddataBean;
import app.dao.PhysicalHeadDao;
import app.model.IwmpState;
import app.model.master.IwmpMPhyActivityDashboard;
import app.model.master.IwmpMPhyHeads;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;

@Repository("physicalHeadDao")
public class PhysicalHeadDaoImpl implements PhysicalHeadDao {

	@Value("${phyHeadDetail}")
	String phyHeadDetail;

	@Value("${savephyHead}")
	String savephyHead;

	@Value("${deletephyHead}")
	String deletephyHead;

	@Value("${findphyshead}")
	String findphyshead;

	@Value("${updatephyHead}")
	String updatephyHead;

	@Value("${seqnosql}")
	String seqnosql;

	@Value("${getHead}")
	String getHead;
	
	@Value("${getepaactivity}")
	String getepaactivity;
	
	@Value("${getlivelihoodactivity}")
	String getlivelihoodactivity;
	
	@Value("${getproductionactivity}")
	String getproductionactivity;

	@Value("${allHeadQuery}")
	String allHeadQuery;
	
	@Value("${allHeadDQuery}")
	String allHeadDQuery;
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, List<PhysicalHeaddataBean>> getPhyHeaddata() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<PhysicalHeaddataBean>> map = new LinkedHashMap<Integer, List<PhysicalHeaddataBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = phyHeadDetail;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalHeaddataBean.class));
			// SQLQuery query = ses.createSQLQuery(sql);

			List<PhysicalHeaddataBean> list = query.list();
			// System.out.println("list size: "+list.size()+" : "+list);
			for (PhysicalHeaddataBean row : list) {
				// System.out.println("userId: "+row.getHeadcode());
			}
			List<PhysicalHeaddataBean> sublist = new ArrayList<PhysicalHeaddataBean>();
			if ((list != null) && (list.size() > 0)) {
				for (PhysicalHeaddataBean row : list) {
					if (!map.containsKey(row.getHeadcode())) {
						sublist = new ArrayList<PhysicalHeaddataBean>();
						sublist.add(row);
						map.put(row.getHeadcode(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getHeadcode(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			// e.printStackTrace();
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			// ex.printStackTrace();
		} finally {

		}
		return map;
	}

	@Override
	public Boolean savephyhead(String headdesc, String status, BigDecimal seqno, Boolean bline, String loginId) {
		// TODO Auto-generated method stub
		Boolean res = false;
		Integer value = 0;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String savesql = savephyHead;

		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();

			{

				SQLQuery query = session.createSQLQuery(savesql);
				Date d = new Date();

				query.setString("headdesc", headdesc);
				query.setString("status", status);
				query.setBoolean("bline", bline);
				query.setString("lastupdatedby", loginId);
				query.setDate("lastupdateddate", d);
				query.setString("requestid", ipadd);
				query.setString("requestid", ipadd);
				query.setBigDecimal("seqno", seqno);
				value = query.executeUpdate();
				if (value > 0) {
					res = true;
				} else {
					session.getTransaction().rollback();
					return false;
				}

			}
			if (res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();

		} catch (Exception ex) {
			// System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {

		}
		return res;
	}

	@Override
	public Boolean deletephyhead(int id) {
		Boolean res = false;
		Integer value = 0;
		String st = "could not delete record because this record exist on another table";
		String savesql = deletephyHead;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		try {

			{
				SQLQuery query = session.createSQLQuery(savesql);
				query.setInteger("id", id);

				value = query.executeUpdate();
				if (value > 0) {
					res = true;
				} else {
					session.getTransaction().rollback();
					return false;
				}

			}
			if (res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();

		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(null,st,"ALERT",JOptionPane.INFORMATION_MESSAGE);
			session.getTransaction().rollback();
		} finally {

		}
		return res;
	}

	@Override
	public List<PhysicalHeaddataBean> findphyhead(Integer id) {
		List<PhysicalHeaddataBean> findphyhead = new ArrayList<PhysicalHeaddataBean>();

		String hql = findphyshead;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {

			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("id", id);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalHeaddataBean.class));
			findphyhead = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().commit();
		} catch (Exception ex) {

			ex.printStackTrace();
			session.getTransaction().commit();
		}
		return findphyhead;
	}

	@Override
	public Boolean updatephyhead(String headdesc, int id, String status, BigDecimal seqno, Boolean bline, String loginId) {
		Boolean res = false;
		Integer value = 0;
		String savesql = updatephyHead;

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			{
				SQLQuery query = session.createSQLQuery(savesql);
				Date d = new Date();

				query.setString("headdesc", headdesc);
				query.setString("status", status);
				query.setBigDecimal("seqno", seqno);
				query.setBoolean("bline", bline);
				query.setString("lastupdatedby", loginId);
				query.setDate("lastupdateddate", d);
				query.setString("ipadd", ipadd);
				query.setInteger("id", id);
				value = query.executeUpdate();
				if (value > 0) {
					res = true;
				} else {
					session.getTransaction().rollback();
					return false;
				}

			}
			if (res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();

		} catch (Exception ex) {
			// System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {

		}
		return res;
	}

	@Override
	public List<PhysicalHeaddataBean> getheadseqno() {
		List<PhysicalHeaddataBean> getheadseqno = new ArrayList<PhysicalHeaddataBean>();

		String hql = seqnosql;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalHeaddataBean.class));
			getheadseqno = query.list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return getheadseqno;
	}

	@Override
	public List<IwmpMPhyHeads> physicalHeadList() {
		
		List<IwmpMPhyHeads> getheadseqno = new ArrayList<IwmpMPhyHeads>();

		String hql = getHead;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			Query query = session.createQuery(hql);
			getheadseqno = query.list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return getheadseqno;
	}

	@Override
	public List<MEpaCoreactivity> EntryPointActivityList() {
		
		List<MEpaCoreactivity> getheadseqno = new ArrayList<MEpaCoreactivity>();

		String hql = getepaactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			Query query = session.createQuery(hql);
			getheadseqno = query.list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return getheadseqno;
	}

	@Override
	public List<MLivelihoodCoreactivity> LivelihoodActivityList() {
		
		List<MLivelihoodCoreactivity> getheadseqno = new ArrayList<MLivelihoodCoreactivity>();

		String hql = getlivelihoodactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			Query query = session.createQuery(hql);
			getheadseqno = query.list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return getheadseqno;
	}

	@Override
	public List<MProductivityCoreactivity> ProductionActivityList() {
		// TODO Auto-generated method stub
		List<MProductivityCoreactivity> getheadseqno = new ArrayList<MProductivityCoreactivity>();

		String hql = getproductionactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			Query query = session.createQuery(hql);
			getheadseqno = query.list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return getheadseqno;
	}

	@Override
	public LinkedHashMap<Integer, String> getAllHeadList() {
		List<IwmpMPhyHeads> headList=new ArrayList<IwmpMPhyHeads>();
		String hql=allHeadQuery;
		LinkedHashMap<Integer, String> headMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				headList = query.list();
				for (IwmpMPhyHeads head : headList) 
				{
					headMap.put(head.getHeadCode(), head.getHeadDesc());
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
        return headMap;
	}

	@Override
	public LinkedHashMap<Integer, String> getAllPrayasHeadList() {
		LinkedHashMap<Integer, String> headDMap=new LinkedHashMap<Integer, String>();
	//	List<IwmpMPhyActivityDashboard> headDList=new ArrayList<IwmpMPhyActivityDashboard>();
		String hql=allHeadDQuery;
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery(hql);
				Iterator itr = query.list().iterator();
				while(itr.hasNext())
				{
					Object ob[] = (Object[])itr.next();
					headDMap.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
				}
				
				
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
			session.getTransaction().commit();
		}
        return headDMap;
	}

}
