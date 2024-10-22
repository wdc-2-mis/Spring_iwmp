package app.daoImpl;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.TargetAchievementQuarterBean;
import app.bean.ConvergenceStatusBean;
import app.bean.ConvergenceWorksBean;
import app.dao.ConvergenceWorksDao;
import app.model.ConvergenceWorkDetail;
import app.model.IwmpProjectAssetStatus;
import app.model.master.IwmpMConvergenceScheme;
import app.model.master.PfmsEatmisdataDetail;
import app.model.project.IwmpProjectPhysicalAsset;

@Repository("ConvergenceWorksDao")
public class ConvergenceWorksDaoImpl implements ConvergenceWorksDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getListOfConvergenceWorks}")
	String getListOfConvergenceWorks;
	
	@Value("${getConvergenceWorks}")
	String getConvergenceWorksDetails;
	
	@Value("${getConvergedWorks}")
	String getConvergedWorksDetails;
	
	@Value("${getConvergedDistWorks}")
	String getConvergedDistWorksDetails;
	
	@Value("${getConvergedProjWorks}")
	String getConvergedProjWorksDetails;
	
	@Value("${getConvergedProjDetailWorks}")
	String getConvergedProjDetailWorksDetails;
	
	@Value("${getConvergedExpndtr}")
	String getConvergedExpDetails;
	
	@Value("${getAllSchemesList}")
	String getAllSchemeDetails;
	
	@Value("${getEnteredConWorks}")
	String getEnteredConWorksDetails;
	
	@Value("${getConvergedStateExpndtr}")
	String getConStateExpndtrDetails;
	
	@Value("${getStatusConWorks}")
	String getStatusConWorksDetails;
	
	@Value("${getRemainingConWorks}")
	String getRemainingConWorksDetails;
	
	@Value("${getConvergenceStatus}")
	String getConvergenceStatus;
	
	
	@Override
	public List<ConvergenceWorksBean> getListConvergenceWorkd(Integer projid) {
		List<ConvergenceWorksBean> getConvergenceWorks = new ArrayList<>();
		String hql = getListOfConvergenceWorks;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projid", projid);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getConvergenceWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getConvergenceWorks;
	}

	@Override
	public Map<Integer, String> getConvergenceSchemeDetails() {
		Map<Integer, String> schMap = new LinkedHashMap<>();
		List<IwmpMConvergenceScheme> getCnvrgncSchm = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from IwmpMConvergenceScheme");
			getCnvrgncSchm = query.list();
			for(IwmpMConvergenceScheme s: getCnvrgncSchm) {
				schMap.put(s.getSchemeId(), s.getSchemeName());
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return schMap;
	}

	@Override
	public String saveasdraftcnvrgdtl(List<String> workcode, List<String> cnvrgstatus, List<String> cvrgschmeid,
			List<String> expndwdc, List<String> expndcnvrgschm) {
		String res = "fail";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			for (int i = 0; i < workcode.size(); i++) {
				ConvergenceWorkDetail cnvrg = new ConvergenceWorkDetail();
				IwmpProjectPhysicalAsset asst = session.load(IwmpProjectPhysicalAsset.class,new BigInteger(workcode.get(i)));
				cnvrg.setIwmpProjectPhysicalAsset(asst);
				cnvrg.setCnvrgnceStatus(cnvrgstatus.get(i).charAt(0));
				IwmpMConvergenceScheme cSchm = session.load(IwmpMConvergenceScheme.class, Integer.parseInt(cvrgschmeid.get(i)));
				cnvrg.setIwmpmConvergenceScheme(cSchm);
				cnvrg.setExpndtrWdc2(new BigDecimal(expndwdc.get(i)));
				cnvrg.setExpndtrCnvrgdScheme(new BigDecimal(expndcnvrgschm.get(i)));
				cnvrg.setStatus('D');
				cnvrg.setCreatedOn(new Date());
				session.saveOrUpdate(cnvrg);
			}

			session.getTransaction().commit();
			res = "success";

		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public List<ConvergenceWorkDetail> getConvergenceWorkDetail() {
		List<ConvergenceWorkDetail> getCnvrgDetails = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from ConvergenceWorkDetail");
			getCnvrgDetails = query.list();
			
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getCnvrgDetails;
	}

	@Override
	public String deleteCnvrgnceWorkCode(Integer workcode) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			ConvergenceWorkDetail cnvrg = session.load(ConvergenceWorkDetail.class, workcode);
//			IwmpMProject proj = new IwmpMProject();
//			proj.setProjectId(0);
			session.delete(cnvrg);
			
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public String completeConvergenceWorkCode(String cnvrgnceId[]) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			for (int i = 0; i < cnvrgnceId.length; i++) {
				ConvergenceWorkDetail cnvrg = session.load(ConvergenceWorkDetail.class, Integer.parseInt(cnvrgnceId[i]));
				cnvrg.setStatus('C');
				cnvrg.setUpdatedOn(new Date());;
				session.saveOrUpdate(cnvrg);
			}
			
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}
	
	@Override
	public List<ConvergenceWorksBean> getConvergenceWorks(Integer stcd, Integer dcode, Integer projid) {
		List<ConvergenceWorksBean> getConvergenceWorks = new ArrayList<>();
		String hql = getConvergenceWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setInteger("dcode", dcode);
			query.setInteger("projid", projid);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getConvergenceWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getConvergenceWorks;
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedWorks() {
		List<ConvergenceWorksBean> getConvergedWorks = new ArrayList<>();
		String hql = getConvergedWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getConvergedWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getConvergedWorks;
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedDistWorks(Integer stcd) {
		List<ConvergenceWorksBean> getConvergedDistWorks = new ArrayList<>();
		String hql = getConvergedDistWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getConvergedDistWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getConvergedDistWorks;
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedProjWorks(Integer dcode) {
		List<ConvergenceWorksBean> getConvergedProjWorks = new ArrayList<>();
		String hql = getConvergedProjWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("dcode", dcode);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getConvergedProjWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getConvergedProjWorks;
	}
	
	@Override
	public List<ConvergenceWorksBean> getConvergedProjDetailWorks(Integer projid) {
		List<ConvergenceWorksBean> getConvergedProjDetailWorks = new ArrayList<>();
		String hql = getConvergedProjDetailWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projid", projid);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getConvergedProjDetailWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getConvergedProjDetailWorks;
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedExpndtr(Integer stcd, Integer dcode, Integer projid, Integer finyr, Integer sid) {
		List<ConvergenceWorksBean> getConvergedExpndtr = new ArrayList<>();
		String hql = getConvergedExpDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setInteger("dcode", dcode);
			query.setInteger("projid", projid);
			query.setInteger("finyr",finyr);
			query.setInteger("sid",sid);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getConvergedExpndtr = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getConvergedExpndtr;
	}
	
	@Override
	public LinkedHashMap<Integer, String> getAllSchemes() {
		List<ConvergenceWorksBean> getAllSchemes = new ArrayList<>();
		String hql = getAllSchemeDetails;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			SQLQuery query= session.createSQLQuery(hql);
		//	query.setInteger("pCode",pCode); 
			List<Object[]> rows = query.list();
			  for(Object[] row : rows){
				  map.put(Integer.parseInt(row[0].toString()),row[1].toString());
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
		return map;
	}

	@Override
	public List<ConvergenceWorksBean> getEnteredConWorks() {
		List<ConvergenceWorksBean> getEnteredConWorks = new ArrayList<>();
		String hql = getEnteredConWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getEnteredConWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getEnteredConWorks;
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedStateExpndtr() {
		List<ConvergenceWorksBean> getConvergedStateExpndtr = new ArrayList<>();
		String hql = getConStateExpndtrDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getConvergedStateExpndtr = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getConvergedStateExpndtr;
	}

	@Override
	public List<ConvergenceWorksBean> getStatusConWorks(Integer stcd) {
		List<ConvergenceWorksBean> getStatusConWorks = new ArrayList<>();
		String hql = getStatusConWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getStatusConWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getStatusConWorks;
	}

	@Override
	public List<ConvergenceWorksBean> getRemainingConWorks(Integer dcode) {
		List<ConvergenceWorksBean> getRemainingConWorks = new ArrayList<>();
		String hql = getRemainingConWorksDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("dcode", dcode);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceWorksBean.class));
			getRemainingConWorks = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getRemainingConWorks;
	}

	@Override
	public List<ConvergenceStatusBean> getConvergenceStatus(Integer projId, Integer head, Integer activity) {
		List<ConvergenceStatusBean> list = new ArrayList<>();
		String hql = getConvergenceStatus;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projId", projId);
			query.setInteger("head", head);
			query.setInteger("activity", activity);
			query.setResultTransformer(Transformers.aliasToBean(ConvergenceStatusBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public String updateConvergenceStatus(List<String> workcode, List<String> cnvrgstatus) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			for (int i = 0; i < workcode.size(); i++) {
				IwmpProjectAssetStatus cnvrg = session.load(IwmpProjectAssetStatus.class, Long.parseLong(workcode.get(i)));
				cnvrg.setConvergence(cnvrgstatus.get(i).charAt(0));
				cnvrg.setLast_updated_date(new Date());
				session.saveOrUpdate(cnvrg);
			}
			
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}
}
