/**
 * 
 */
package app.daoImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


import app.bean.IwmpRoleMenuMap;
import app.bean.ProjectSanctionedBean;
import app.dao.CommonDao;
import app.dao.IwmpProjectDao;
import app.model.IwmpAppRoleMap;
import app.model.IwmpDistrict;
import app.model.IwmpMCsShare;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;

/**
 * @author Spectra
 *
 */
@Repository("IwmpMProjectDao")
public class IwmpProjectDaoImpl implements IwmpProjectDao {

	 @Autowired
 SessionFactory sessionFactory;
	 
	 @Value("${getProjectSanctioned}")
	String getProjectSanctionedDetails;

	@Override
//	@Transactional(rollbackFor = {RuntimeException.class})
	public void addProject(IwmpMProject project) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {

			session.saveOrUpdate(project);
			System.out.print("project");
		} catch (Exception ex) {
			System.out.print("Err" + ex.getMessage());
			session.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			session.getTransaction().commit();
		}
	}

	@Override
	public int updateProjectList(List<IwmpMProject> projectList) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		int key = 0;
		Transaction tx = session.beginTransaction();
		try {

			if (null != projectList && projectList.size() > 0) {
				for (IwmpMProject project : projectList) {
					IwmpMProject tproject = (IwmpMProject) session.load(IwmpMProject.class, project.getProjectId());
					if (project.getUpdatestatus() == true) {
						tproject.setStatus('C');
						key+=1;
						session.saveOrUpdate(tproject);
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
		return key;
	}

	@Override
	public void addProjectList(List<IwmpMProject> projectList,IwmpMCsShare share,int stcode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		int key = 0;
		Transaction tx = session.beginTransaction();
		try {
			
			if (null != projectList && projectList.size() > 0) {
				for (IwmpMProject project : projectList) {

					Query query = session.createSQLQuery("select nextval('iwmp_m_project_proj_id_seq')");
					key = ((BigInteger) query.uniqueResult()).intValue();
					String dist,state;
				//	String.format("%08d", i);
					String tempPr="P" +String.format("%02d", stcode)+ String.format("%03d", project.getIwmpDistrict().getDcode())  + String.format("%06d", key);
					System.out.print("project" +Integer.toString(stcode, 2));
					project.setProjectCd(tempPr);
					
					IwmpDistrict temp = (IwmpDistrict) session.get(IwmpDistrict.class,
							project.getIwmpDistrict().getDcode());
					IwmpMFinYear tempy = (IwmpMFinYear) session.get(IwmpMFinYear.class,
							project.getIwmpMFinYear().getFinYrCd());
					project.setProjName(temp.getDistName() + "-WDC - " + project.getProjectSeqNo().toString() + " /"
							+ tempy.getFinYrDesc());
					project.setProjectId(key);
					project.setIwmpMCsShare(share);
					project.setStatus('D');
					session.saveOrUpdate(project);
				}
			}
			System.out.print("project");
		} catch (Exception ex) {
			System.out.print("Err" + ex.getMessage());
			Query query = session.createSQLQuery("select setval('iwmp_m_project_proj_id_seq',:seq, true);");
			query.setLong("seq", Long.parseLong(Integer.toString(key)));
			query.executeUpdate();
			tx.rollback();
			ex.printStackTrace();
		} finally {
			tx.commit();

		}
	}

	@Override
	public IwmpMProject getSequenceFinDistrict(int finYear, int dcode, int projectSeqNo) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		IwmpMProject dist = null;
		Query q = session.createQuery(
				"from  IwmpMProject as p where p.iwmpDistrict.dcode=:dcode "
				//+ "and p.iwmpMFinYear.finYrCd=:finYear "
				+ "and p.projectSeqNo=:projectSeqNo");
		q.setParameter("dcode", dcode);
		//q.setParameter("finYear", finYear);
		q.setParameter("projectSeqNo", projectSeqNo);
		if (!q.list().isEmpty())
			dist = (IwmpMProject) q.list().get(0);
		session.getTransaction().commit();
		return dist;
	}

	@Override
	public List<IwmpMProject> ListSanctionedProjectOpen(int stcode, int distcode, int finyear) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// from IwmpState where stCode=case 1 when 0 then stCode else 1 end
		@SuppressWarnings("unchecked")
		List<IwmpMProject> temp = session
				.createQuery("from IwmpMProject as p where p.status='C' and p.iwmpDistrict.iwmpState.stCode="
						+ "case :stcode when 0 then p.iwmpDistrict.iwmpState.stCode else :stcode end and p.iwmpDistrict.dcode=case :distcode when 0 then"
						+ " p.iwmpDistrict.dcode else :distcode end and p.projectSanctionYr.finYrCd=case :finyear when 0 then p.projectSanctionYr.finYrCd else :finyear end "
						+ " order by p.iwmpDistrict.iwmpState.stName,p.iwmpDistrict.distName ,p.projectSeqNo")
				.setParameter("stcode", stcode).setParameter("distcode", distcode).setParameter("finyear", finyear)
				.list();
		session.getTransaction().commit();

		return temp;
	}
	
	@Override
	public List<ProjectSanctionedBean> getProjectSanctioned(int finyear) {
		List<ProjectSanctionedBean> getProjectSanctioned = new ArrayList<>();
		String hql = getProjectSanctionedDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("finyear", finyear);
			query.setResultTransformer(Transformers.aliasToBean(ProjectSanctionedBean.class));
			getProjectSanctioned = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getProjectSanctioned;
	}

	@Override
	public List<IwmpMProject> ListSanctionedProject(int stcode, int distcode, int finyear, int period, int areatype,
			String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// from IwmpState where stCode=case 1 when 0 then stCode else 1 end
		@SuppressWarnings("unchecked")
		List<IwmpMProject> temp = session.createQuery("from IwmpMProject as p where p.iwmpDistrict.iwmpState.stCode="
				+ "case :stcode when 0 then p.iwmpDistrict.iwmpState.stCode else :stcode end and p.iwmpDistrict.dcode=case :distcode when 0 then"
				+ " p.iwmpDistrict.dcode else :distcode end and p.iwmpMFinYear.finYrCd=case :finyear when 0 then p.iwmpMFinYear.finYrCd else :finyear end "
				+ " and p.iwmpMProjectPrd.prdCode= case :iwmpMProjectPrd when 0 then p.iwmpMProjectPrd.prdCode else :iwmpMProjectPrd end "
				+ " and p.iwmpMAreaType.areaCd= case :iwmpMAreaType when 0 then p.iwmpMAreaType.areaCd else :iwmpMAreaType end "
				+ " and p.status= case :status when '0' then p.status else :status end "
				+ "order by p.iwmpDistrict.iwmpState.stName,p.iwmpDistrict.distName,p.projectSeqNo ").setParameter("stcode", stcode)
				.setParameter("distcode", distcode).setParameter("finyear", finyear)
				.setParameter("iwmpMProjectPrd", period).setParameter("iwmpMAreaType", areatype)
				.setParameter("status", status).list();

		session.getTransaction().commit();

		return temp;
	}
	@Override
	public List<IwmpMProject> ListSanctionedProjectStatus(int stcode, int distcode, int finyear, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// from IwmpState where stCode=case 1 when 0 then stCode else 1 end
		@SuppressWarnings("unchecked")
		List<IwmpMProject> temp = session.createQuery("from IwmpMProject as p where p.iwmpDistrict.iwmpState.stCode="
				+ "case :stcode when 0 then p.iwmpDistrict.iwmpState.stCode else :stcode end and p.iwmpDistrict.dcode=case :distcode when 0 then"
				+ " p.iwmpDistrict.dcode else :distcode end and p.iwmpMFinYear.finYrCd=case :finyear when 0 then p.iwmpMFinYear.finYrCd else :finyear end "
				//+ " and p.iwmpMProjectPrd.prdCode= case :iwmpMProjectPrd when 0 then p.iwmpMProjectPrd.prdCode else :iwmpMProjectPrd end "
			//	+ " and p.iwmpMAreaType.areaCd= case :iwmpMAreaType when 0 then p.iwmpMAreaType.areaCd else :iwmpMAreaType end "
				+ " and p.status= case :status when '0' then p.status else :status end "
				+ "order by p.iwmpDistrict.iwmpState.stName,p.iwmpDistrict.distName,p.projectSeqNo ").setParameter("stcode", stcode)
				.setParameter("distcode", distcode).setParameter("finyear", finyear)
			//	.setParameter("iwmpMProjectPrd", period).setParameter("iwmpMAreaType", areatype)
				.setParameter("status", status).list();

		session.getTransaction().commit();

		return temp;
	}
	
	@Override
	public List<IwmpMProject> ListSanctionedProjectStatus(int stcode, int distcode, int finyear, String status,String login) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		char c=status.charAt(0);
		session.beginTransaction();
		// from IwmpState where stCode=case 1 when 0 then stCode else 1 end
		@SuppressWarnings("unchecked")
		List<IwmpMProject> temp = session.createQuery("from IwmpMProject as p where p.iwmpDistrict.iwmpState.stCode="
				+ "case :stcode when 0 then p.iwmpDistrict.iwmpState.stCode else :stcode end and p.iwmpDistrict.dcode=case :distcode when 0 then"
				+ " p.iwmpDistrict.dcode else :distcode end and p.iwmpMFinYear.finYrCd=case :finyear when 0 then p.iwmpMFinYear.finYrCd else :finyear end "
				//+ " and p.iwmpMProjectPrd.prdCode= case :iwmpMProjectPrd when 0 then p.iwmpMProjectPrd.prdCode else :iwmpMProjectPrd end "
			//	+ " and p.iwmpMAreaType.areaCd= case :iwmpMAreaType when 0 then p.iwmpMAreaType.areaCd else :iwmpMAreaType end "
				+ " and p.status= case :status when '0' then p.status else :status end and p.iwmpDistrict.iwmpState.stCode in(select u.iwmpState.stCode from UserMap as u where u.userReg.userId=:login order by u.iwmpState.stName) "
				+ "order by p.iwmpDistrict.iwmpState.stName,p.iwmpDistrict.distName,p.projectSeqNo ").setParameter("stcode", stcode)
				.setParameter("distcode", distcode).setParameter("finyear", finyear)
				.setParameter("status", c).setParameter("login", login).list();

		session.getTransaction().commit();

		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMProject> ListSanctionedProjectNew(int stcode, int distcode, int finyear) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		// from IwmpState where stCode=case 1 when 0 then stCode else 1 end
		List<IwmpMProject> temp=null;
		try
		{
		 temp = session
				.createQuery("from IwmpMProject as p where p.status='D' and p.iwmpDistrict.iwmpState.stCode="
						+ "case :stcode when 0 then p.iwmpDistrict.iwmpState.stCode else :stcode end and p.iwmpDistrict.dcode=case :distcode when 0 then"
						+ " p.iwmpDistrict.dcode else :distcode end and p.iwmpMFinYear.finYrCd=case :finyear when 0 then p.iwmpMFinYear.finYrCd else :finyear end "
						+ " order by p.iwmpDistrict.iwmpState.stName,p.iwmpDistrict.distName,p.projectSeqNo")
				.setParameter("stcode", stcode).setParameter("distcode", distcode).setParameter("finyear", finyear)
				.list();

		session.getTransaction().commit();
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}

	@Override
	public int deleteProject(int projId) {
		
		Session session = sessionFactory.getCurrentSession();
		int key = 0;
		
		try {

			session.beginTransaction();
			IwmpMProject project = (IwmpMProject) session.load(IwmpMProject.class, projId);

			if (null != project) {
				session.delete(project);
			}
			session.getTransaction().commit();
			key=1;
			
		} catch (Exception ex) {
			System.out.print("Err" + ex.getMessage());

			session.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			//tx.commit();

		}
		return key;
		
	}

	@Override
	public IwmpMProject getProject(int projId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.getTransaction().begin();
		IwmpMProject temp = (IwmpMProject) session.get(IwmpMProject.class, projId);
		session.getTransaction().commit();
		return temp;
	}

	@Override
	public void updateProject(IwmpMProject project) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.getTransaction().begin();

		if (null != project) {
			session.update(project);
		}
		session.getTransaction().commit();
	}

	public List<IwmpMProject> ListSanctionedProjectStatus(String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		// from IwmpState where stCode=case 1 when 0 then stCode else 1 end
		@SuppressWarnings("unchecked")
		List<IwmpMProject> temp = session.createQuery("from IwmpMProject as p where p.status= case :status when 'C' then p.status else :status end "
				+ "order by p.iwmpDistrict.iwmpState.stName,p.iwmpDistrict.distName,p.projectSeqNo ")
				.setCharacter("status", status.charAt(0)).list();

		session.getTransaction().commit();

		return temp;
	}

}
