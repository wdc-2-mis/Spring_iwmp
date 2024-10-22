package app.daoImpl.reports;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.ShgDetailBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.dao.reports.StateWiseCurrentStatusDao;

@Repository("StateWiseCurrentStatusDao")
public class StateWiseCurrentStatusDaoImpl implements StateWiseCurrentStatusDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${statewisecurrentstatus}")
	String statewisecurrentstatus;

	@Value("${statewisecurrentstatusOther}")
	String statewisecurrentstatusOther;
	
	@Value("${stateWiseStatusOtherActivity}")
	String stateWiseStatusOtherActivity;
	
	@Value("${stateWiseStatusBaselNetTreatArea}")
	String stateWiseStatusBaselNetTreatArea;
	
	@Value("${distWiseStatusBaselNetTreatArea}")
	String distWiseStatusBaselNetTreatArea;
	
	@Value("${districtwisecurrentstatusOther}")
	String districtwisecurrentstatusOther;
	
	@Value("${districtWiseStatusBaselNetTreatArea}")
	String districtWiseStatusBaselNetTreatArea;
	
	@Value("${districtWiseStatusOtherActivity}")
	String districtWiseStatusOtherActivity;
	
	@Value("${statewisecurrentstatusofplanachieve}")
	String statewisecurrentstatusofplanachieve;
	
	@Value("${districtWiseCurrentStatusPlan}")
	String districtWiseCurrentStatusPlan;
	
	@Value("${projWiseStatusBaselNetTreatArea}")
	String projWiseStatusBaselNetTreatArea;
	
	@Value("${projectwisecurrentstatusOther}")
	String projectwisecurrentstatusOther;
	
	@Value("${projWiseStatusBaseArea}")
	String projWiseStatusBaseArea;
	
	@Value("${projWiseStatusofOtherActivity}")
	String projWiseStatusofOtherActivity;
	
	@Value("${projWiseCurrentStatusPlanAchieve}")
	String projWiseCurrentStatusPlanAchieve;
	
	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatus() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = statewisecurrentstatus;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatusOther() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = statewisecurrentstatusOther;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getStateWiseStatusOtherActivity() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = stateWiseStatusOtherActivity;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getstateWiseStatusBaselNetTreatArea() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = stateWiseStatusBaselNetTreatArea;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistWiseStatusBaselNetTreatArea(int id) {
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getDistwiseDtl = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = distWiseStatusBaselNetTreatArea;
			query = session.createSQLQuery(hql);
			query.setInteger("stCode", id);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
				//	System.out.println("userId: "+row.getTotal_vill_basel());
		}
		List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
		if ((list != null) && (list.size() > 0)) {
			for (StateWiseCurrentStatusBean row : list){
				if (!getDistwiseDtl.containsKey(row.getDcode())) {
					sublist = new ArrayList<StateWiseCurrentStatusBean>();
					sublist.add(row);
					getDistwiseDtl.put(row.getDcode(), sublist);
				} else {
					sublist.add(row);
					getDistwiseDtl.put(row.getDcode(), sublist);
				}
			}
		}
		session.getTransaction().commit();
	
			
			
		}catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}
		return getDistwiseDtl;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getDistrictWiseCurrentStatusOther(int id) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = districtwisecurrentstatusOther;
			query = session.createSQLQuery(hql);
			query.setInteger("stCode", id);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistrictWiseStatusBaselNetTreatArea(int id) {
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = districtWiseStatusBaselNetTreatArea;
			query = session.createSQLQuery(hql);
			query.setInteger("stCode", id);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistrictWiseStatusOtherActivity(int id) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = districtWiseStatusOtherActivity;
			query = session.createSQLQuery(hql);
			query.setInteger("stCode", id);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatusPlanAchieve() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = statewisecurrentstatusofplanachieve;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}


	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistrictWiseCurrentStatusPlanAchieve(int id) {
		LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> map = new LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = districtWiseCurrentStatusPlan;
			query = session.createSQLQuery(hql);
			query.setInteger("stCode", id);
			query.setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class));
			List<StateWiseCurrentStatusBean> list = query.list();
			for (StateWiseCurrentStatusBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<StateWiseCurrentStatusBean> sublist = new ArrayList<StateWiseCurrentStatusBean>();
			if ((list != null) && (list.size() > 0)) {
				for (StateWiseCurrentStatusBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<StateWiseCurrentStatusBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	@Override
	public List<StateWiseCurrentStatusBean> projWiseStatusBaselNetTreatArea(int dcode) {
		Session session = sessionFactory.getCurrentSession();
		List<StateWiseCurrentStatusBean> projlist = new ArrayList<>();
		String projhql = projWiseStatusBaselNetTreatArea;
		try {
			session.beginTransaction();
			projlist = session.createSQLQuery(projhql).setInteger("dcode", dcode).setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class)).list();
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return projlist;
	}


	@Override
	public List<StateWiseCurrentStatusBean> projWiseCurrentStatusOther(int dcode) {
		Session session = sessionFactory.getCurrentSession();
		List<StateWiseCurrentStatusBean> projlistt = new ArrayList<>();
		String projhl = projectwisecurrentstatusOther;
		try {
			session.beginTransaction();
			projlistt = session.createSQLQuery(projhl).setInteger("dcode", dcode).setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class)).list();
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return projlistt;
	}


	@Override
	public List<StateWiseCurrentStatusBean> projWiseStatusBaselArea(int dcode) {
		Session session = sessionFactory.getCurrentSession();
		List<StateWiseCurrentStatusBean> projlist = new ArrayList<>();
		String proj = projWiseStatusBaseArea;
		try {
			session.beginTransaction();
			projlist = session.createSQLQuery(proj).setInteger("dcode", dcode).setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class)).list();
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return projlist;
	}


	@Override
	public List<StateWiseCurrentStatusBean> projWiseStatusofOtherActivity(int dcode) {
		Session session = sessionFactory.getCurrentSession();
		List<StateWiseCurrentStatusBean> projlist = new ArrayList<>();
		String proj = projWiseStatusofOtherActivity;
		try {
			session.beginTransaction();
			projlist = session.createSQLQuery(proj).setInteger("dcode", dcode).setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class)).list();
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return projlist;
	}


	@Override
	public List<StateWiseCurrentStatusBean> getprojWiseCurrentStatusPlanAchieve(int dcode) {
		Session session = sessionFactory.getCurrentSession();
		List<StateWiseCurrentStatusBean> prjlist = new ArrayList<>();
		String prj = projWiseCurrentStatusPlanAchieve;
		try {
			session.beginTransaction();
			prjlist = session.createSQLQuery(prj).setInteger("dcode", dcode).setResultTransformer(Transformers.aliasToBean(StateWiseCurrentStatusBean.class)).list();
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return prjlist;
	}
}
