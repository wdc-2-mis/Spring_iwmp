package app.daoImpl;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.PhysicalActionPlanBean;
import app.bean.RevisedActPlanBean;
import app.dao.IwmpActPlanDao;
import app.model.IwmpActPlan;
import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.UserReg;

@Repository("IwmpActPlanDao")
public class IwmpActPlanDaoImpl implements IwmpActPlanDao{

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getAlreadyDiActPlanData}")
	String getAlreadyDiActPlanData;
	
//	@Override
//	public void addActPlan(List<IwmpActPlan> iwmpActPlan) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public String saveActPlan(Integer dcode, Integer projid, Integer finyear, Character dprstatus, String authname, Integer sentFrom) {

		String res="fail";
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			Date d = new Date();
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String requestIp=inetAddress.getHostAddress();
			Query query = session.createSQLQuery("select nextval('iwmp_act_plan_act_plan_id_seq')");
			@SuppressWarnings("deprecation")
			int key = ((BigInteger) query.uniqueResult()).intValue();
			IwmpActPlan iwmpActPlan = new IwmpActPlan();
			IwmpMFinYear year = new IwmpMFinYear();
			year.setFinYrCd(finyear);
			IwmpMProject proj = new IwmpMProject();
			proj.setProjectId(projid);
			IwmpDistrict dist = new IwmpDistrict();
			dist.setDcode(dcode);
			UserReg userReg = (UserReg) session.get(UserReg.class, sentFrom);
			
			iwmpActPlan.setActPlanId(key);
			iwmpActPlan.setIwmpDistrict(dist);
			iwmpActPlan.setIwmpMProject(proj);
			iwmpActPlan.setIwmpMFinYear(year);
			iwmpActPlan.setDprStatus(dprstatus);
			iwmpActPlan.setStatus('D');
			iwmpActPlan.setAuthName(authname);
			iwmpActPlan.setCreatedDt(d);
			iwmpActPlan.setCreatedBy(userReg.getUserId());
			iwmpActPlan.setRequestIp(requestIp);
			
			session.save(iwmpActPlan);
			session.getTransaction().commit();
			res="success";
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res="fail";
		} 
		catch(Exception ex){
			res="fail";
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return res;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<RevisedActPlanBean> getAlreadyDistActPlanData(Integer dCode) {
		
		List<RevisedActPlanBean> list = new LinkedList<>();
		String hql = getAlreadyDiActPlanData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(hql);
			query.setInteger("dCode", dCode);
			query.setResultTransformer(Transformers.aliasToBean(RevisedActPlanBean.class));
			list = query.list();
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public String getFinalStatus(Integer actPlanId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			IwmpActPlan actPlan = session.load(IwmpActPlan.class, actPlanId);
			if(actPlan!=null) {
				actPlan.setStatus('C');
				session.update(actPlan);
			}
			session.getTransaction().commit();
			return "success";
		}catch(Exception e) {
			return "fail";
		}
	}

	@Override
	public String deleteActPlan(Integer actPlanId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			IwmpActPlan actPlan = session.load(IwmpActPlan.class, actPlanId);
			if(actPlan!=null) {
				session.delete(actPlan);
			}
			session.getTransaction().commit();
			return "success";
		}catch(Exception e) {
			return "fail";
		}
	}

}
