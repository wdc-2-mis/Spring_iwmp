package app.daoImpl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.RoleMenuList;
import app.dao.CommonDao;
import app.model.IwmpLoginLog;
import app.model.IwmpMAreaType;
import app.model.IwmpMCsShare;
import app.model.IwmpMFinYear;
import app.model.IwmpMProjectPrd;
import app.model.IwmpUserActionLog;
import app.model.MQuarter;
import app.model.UserReg;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;

@Repository("commonDao")
public class commonDaoImpl implements CommonDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${blockListBydistCode}")
	String blockListBydistCode;

	@Value("${gpList}")
	String gpListByblockCode;

	@Value("${getInactiveGramPanchayatList}")
	String getInactiveGramPanchayatList;

	@Value("${getActiveGramPanchayatList}")
	String getActiveGramPanchayatList;

	@Value("${getActiveGramPanchayatListAll}")
	String getActiveGramPanchayatListAll;

	@Value("${getPublicReportOuter}")
	String getAllMenuRole;

	@Value("${getIwmpMPhyActivityAll}")
	String getIwmpMPhyActivityAll;

	@Value("${getphysubactivity}")
	String getphysubactivity;

	@Value("${getAllHindiMenuRole}")
	String getAllHindiMenuRole;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMFinYear> getAllFinancialYear() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMFinYear> temp = new ArrayList<IwmpMFinYear>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from IwmpMFinYear order by finYrCd").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMAreaType> getAllAreaType() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMAreaType> temp = new ArrayList<IwmpMAreaType>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from IwmpMAreaType order by areaDesc").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMProjectPrd> getAllProjectPrd() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMProjectPrd> temp = new ArrayList<IwmpMProjectPrd>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from IwmpMProjectPrd order by prdCode").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public List<IwmpMCsShare> getCentralStateShareDetail() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMCsShare> temp = new ArrayList<IwmpMCsShare>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from IwmpMCsShare order by createdBy desc").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public IwmpMAreaType getAreaTypeDetail(int areaCd) {
		// TODO Auto-generated method stub
		IwmpMAreaType temp = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			temp = (IwmpMAreaType) session.get(IwmpMAreaType.class, areaCd);
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public IwmpMCsShare getStateCentralShareDetail(Integer stCode) {
		// TODO Auto-generated method stub
		IwmpMCsShare temp = null;
		IwmpMCsShare share = new IwmpMCsShare();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			/*
			 * DetachedCriteria maxId = DetachedCriteria.forClass(IwmpMCsShare.class)
			 * .setProjection(Projections.max("stateCentralCd"));
			 * 
			 * temp = (IwmpMCsShare) session.createCriteria(IwmpMCsShare.class)
			 * .add(Property.forName("stateCentralCd").eq(maxId)).list().get(0);
			 */

			List<IwmpMCsShare> list = session.createQuery("from IwmpMCsShare where iwmpState.stCode=:stCode")
					.setInteger("stCode", stCode).list();

			for (IwmpMCsShare s : list) {
				share = s;
			}

			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print("err" + ex.getMessage());
			session.getTransaction().rollback();
		}
		return share;
	}

	@Override
	public Boolean checkFinancialyear(Date projectDate, Integer finyearid) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub IwmpMFinYear
		Boolean temp = false;
		try {
			Session session = sessionFactory.getCurrentSession();

			session.beginTransaction();

			List<IwmpMFinYear> list = session.createQuery("from IwmpMFinYear as f where  projectDate between f.  ")
					.list();
			session.getTransaction().commit();

		} catch (Exception ex) {
			System.out.print("err" + ex.getMessage());
		}
		return temp;
	}

	@Override
	public LinkedHashMap<Integer, String> getGramPanchayatByblockCode(Integer blockCode) {
		List<IwmpGramPanchayat> gpList = new ArrayList<IwmpGramPanchayat>();
		String hql = gpListByblockCode;
		LinkedHashMap<Integer, String> gpMap = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("block", blockCode);
			gpList = query.list();
			for (IwmpGramPanchayat gp : gpList) {
				gpMap.put(gp.getGcode(), gp.getGramPanchayatName());
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return gpMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpGramPanchayat> getInactiveGramPanchayatList(int stateCode, int districtCode, int blockCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		List<IwmpGramPanchayat> temp = null;
		try {

			temp = session.createQuery(getInactiveGramPanchayatList).setParameter("stcode", stateCode)
					.setParameter("distcode", districtCode).setParameter("blockCode", blockCode).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}

	@Override
	public void updateGramPanchayatList(List<IwmpGramPanchayat> gplist) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

			if (null != gplist && gplist.size() > 0) {
				for (IwmpGramPanchayat gp : gplist) {
					System.out.print("test" + gp.getUpdatestatus());
					if (gp.getUpdatestatus() == true) {

						IwmpGramPanchayat tproject = (IwmpGramPanchayat) session.load(IwmpGramPanchayat.class,
								gp.getGcode());
						System.out.println("true" + gp.getUpdatestatus());

						tproject.setGramPanchayatName(tproject.getGramPanchayatName() + " [ "
								+ tproject.getIwmpBlock().getBlockName() + " Block ]");

						tproject.setImportType("SLNA");
						tproject.setRequestIp(ipadd);
						tproject.setLastUpdatedDate(d);
						tproject.setActive(true);
						session.saveOrUpdate(tproject);
						System.out.print("done" + gp.getUpdatestatus());

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
	public List<IwmpGramPanchayat> getActiveGramPanchayatList(int stateCode, int districtCode, int blockCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		List<IwmpGramPanchayat> temp = null;
		try {

			temp = session.createQuery(getActiveGramPanchayatList).setParameter("stcode", stateCode)
					.setParameter("distcode", districtCode).setParameter("blockCode", blockCode).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}

	@Override
	public IwmpGramPanchayat findGramPanchaytGcode(int gCode) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		IwmpGramPanchayat gp = null;
		try {

			if (gCode > 0) {

				gp = (IwmpGramPanchayat) session.createQuery("from IwmpGramPanchayat where gcode=:gcode")
						.setParameter("gcode", gCode).uniqueResult();
			}
			session.getTransaction().commit();
			// System.out.print("project==" + village.getVillageName());
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
		return gp;
	}

	@Override
	public Boolean updateGramPanchayatList(IwmpGramPanchayat grampanchayat) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Boolean flag = false;
		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

			if (grampanchayat != null) {
				IwmpGramPanchayat gp = (IwmpGramPanchayat) session.load(IwmpGramPanchayat.class,
						grampanchayat.getGcode());

				gp.setGramPanchayatName(grampanchayat.getGramPanchayatName());
				gp.setIwmpBlock(grampanchayat.getIwmpBlock());

				gp.setImportType("SLNA");
				gp.setRequestIp(ipadd);
				gp.setLastUpdatedDate(d);
				// tproject.setActive(true);
				session.saveOrUpdate(gp);
				System.out.print("done" + grampanchayat.getUpdatestatus());

				flag = true;
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
	public Boolean authenticateUser(String url, HttpSession session, HttpServletRequest request) {
		boolean dataInsert = false;
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			Timestamp loginDt = new Timestamp(System.currentTimeMillis());
			InetAddress inetAddress = InetAddress.getLocalHost();
			UserReg reg = (UserReg) ses.load(UserReg.class, Integer.parseInt(session.getAttribute("regId").toString()));
			String ipadd = inetAddress.getHostAddress();
			String sessionId = session.getId();
			IwmpUserActionLog data = new IwmpUserActionLog();
			String userAgent = request.getHeader("user-agent");

			data.setRequestIp(ipadd);
			data.setUserReg(reg);
			data.setCreatedDate(loginDt);
			data.setSessionId(sessionId);
			data.setActionName(url);
			data.setUserAgent(userAgent);
			ses.save(data);
			dataInsert = true;
			ses.getTransaction().commit();
		} catch (Exception ex) {
			ses.getTransaction().rollback();
			System.out.println(ex.getMessage());
			ses.getTransaction().rollback();
		} finally {
			// ses.flush();
			// ses.close();
			if(ses.isOpen())
			{
				ses.close();
			}
		}
		return dataInsert;
	}

	@Override
	public List<RoleMenuList> getPublicReport() {
		// TODO Auto-generated method stub getAllMenuRole
		List<RoleMenuList> result = new ArrayList<RoleMenuList>();
		Session session = sessionFactory.openSession();
		try {
			String hql = null;
			SQLQuery query = null;

			@SuppressWarnings("unused")
			Transaction tx = session.beginTransaction();
			hql = getAllMenuRole;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(RoleMenuList.class));
			result = query.list();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		return result;
	}

	@Override
	public List<IwmpGramPanchayat> getActiveGramPanchayatList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		List<IwmpGramPanchayat> temp = null;
		try {

			temp = session.createQuery(getActiveGramPanchayatListAll).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}

	@Override
	public List<IwmpMPhyActivity> physicalActivityList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMPhyActivity> temp = new ArrayList<IwmpMPhyActivity>();
		try {
			session.beginTransaction();
			temp = session.createQuery(getIwmpMPhyActivityAll).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public List<IwmpMPhySubactivity> physicalSubActivityList() {
		// TODO Auto-generated method stub

		List<IwmpMPhySubactivity> getsubactdata = new ArrayList<IwmpMPhySubactivity>();
		String hql = getphysubactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			Query query = session.createQuery(hql);
			getsubactdata = query.list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return getsubactdata;

	}

	@Override
	public LinkedHashMap<Integer, String> getFinancialYear() {
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpMFinYear> rows = new ArrayList<IwmpMFinYear>();
		String hql="from IwmpMFinYear order by finYrCd";
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			rows = query.list();
			
			for(IwmpMFinYear quad : rows) {
				map.put(quad.getFinYrCd(), quad.getFinYrDesc());
			}
			
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return map;
	}

	@Override
	public List<MQuarter> getQuaterMaster() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<MQuarter> temp = new ArrayList<MQuarter>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from MQuarter order by quartCd").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public List<RoleMenuList> getPublicHindiReport() {
		List<RoleMenuList> result = new ArrayList<RoleMenuList>();
		Session session = sessionFactory.openSession();
		try {
			String hql = null;
			SQLQuery query = null;

			@SuppressWarnings("unused")
			Transaction tx = session.beginTransaction();
			hql = getAllHindiMenuRole;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(RoleMenuList.class));
			result = query.list();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.getTransaction().commit();
			/*
			 * session.flush(); session.close();
			 */
		}
		return result;
	}

}
