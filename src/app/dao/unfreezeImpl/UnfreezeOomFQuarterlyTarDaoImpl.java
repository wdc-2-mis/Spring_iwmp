package app.dao.unfreezeImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.model.WdcpmksyQuadTarget;

@Repository
public class UnfreezeOomFQuarterlyTarDaoImpl implements app.dao.unfreeze.UnfreezeOomFQuarterlyTarDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "deprecation", "rawtypes" })
	@Override
	public String unfreezeOomfQuarterlyTarData(Integer finYear, Integer projId, Integer quarter) {
		String result =null;
		int res = 0;
		List<WdcpmksyQuadTarget> list = new ArrayList<>();
		Session session = sessionFactory.openSession();
		try {
			Query query = null;
			session.beginTransaction();
			query = session.createQuery("from WdcpmksyQuadTarget where iwmpMFinYear.finYrCd = :finyear and iwmpMProject.projectId = :projid");
			query.setInteger("finyear", finYear);
			query.setInteger("projid", projId);
//			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyQuadTarget.class));
			list = query.list();
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		for(WdcpmksyQuadTarget wdc : list) {
			String hql = null;
			if(Character.valueOf('C').equals(wdc.getQ1status()) && Character.valueOf('C').equals(wdc.getQ2status()) && Character.valueOf('C').equals(wdc.getQ3status()) && Character.valueOf('C').equals(wdc.getQ4status())) {
				hql = "update WdcpmksyQuadTarget set status = 'D', q1status = 'D', q2status = 'D', q3status = 'D', q4status = 'D' where iwmpMFinYear.finYrCd = :finyear and iwmpMProject.projectId = :projid";
			}
			Character q1;
			Character q2;
			Character q3;
			Character q4;
			if(wdc.getQ1status()==null) {
				q1 = null;
			}else {
				q1 = Character.valueOf('C').equals(wdc.getQ1status())?'D':wdc.getQ1status();
			}
			if(wdc.getQ2status()==null) {
				q2 = null;
			}else {
				q2 = Character.valueOf('C').equals(wdc.getQ2status())?'D':wdc.getQ2status();
			}
			if(wdc.getQ3status()==null) {
				q3 = null;
			}else {
				q3 = Character.valueOf('C').equals(wdc.getQ3status())?'D':wdc.getQ3status();
			}
			if(wdc.getQ4status()==null) {
				q4 = null;
			}else {
				q4 = Character.valueOf('C').equals(wdc.getQ4status())?'D':wdc.getQ4status();
			}
			
			if(quarter ==0) {
				try {
					Query query = null;
					session.beginTransaction();
					
					if(hql!=null) {
						query = session.createQuery(hql);
						query.setParameter("finyear", finYear);
						query.setParameter("projid", projId);
						res = query.executeUpdate();
					}
					else {
						query = session.createQuery(
								"update WdcpmksyQuadTarget wdc set wdc.q1status = :q1, wdc.q2status = :q2, wdc.q3status = :q3, wdc.q4status = :q4 where wdc.iwmpMFinYear.finYrCd = :finyear and wdc.iwmpMProject.projectId = :projid");
						query.setParameter("q1", q1);
						query.setParameter("q2", q2);
						query.setParameter("q3", q3);
						query.setParameter("q4", q4);
						query.setParameter("finyear", finYear);
						query.setParameter("projid", projId);
						res = query.executeUpdate();
					}
					session.getTransaction().commit();
					
				}catch (HibernateException e) {
					System.err.print("Hibernate error");
					e.printStackTrace();
					session.getTransaction().rollback();
				} 
			}else if(quarter==1) {
				try {
					Query query = null;
					session.beginTransaction();
					
					query = session.createQuery("update WdcpmksyQuadTarget wdc set wdc.q1status = :q1 where wdc.iwmpMFinYear.finYrCd = :finyear and wdc.iwmpMProject.projectId = :projid");
					query.setParameter("q1", q1);
					query.setParameter("finyear", finYear);
					query.setParameter("projid", projId);
					res = query.executeUpdate();
					session.getTransaction().commit();
				}catch (HibernateException e) {
					System.err.print("Hibernate error");
					e.printStackTrace();
					session.getTransaction().rollback();
				} 
			}else if(quarter==2) {
				try {
					Query query = null;
					session.beginTransaction();
					
					query = session.createQuery("update WdcpmksyQuadTarget wdc set wdc.q2status = :q2 where wdc.iwmpMFinYear.finYrCd = :finyear and wdc.iwmpMProject.projectId = :projid");
					query.setParameter("q2", q2);
					query.setParameter("finyear", finYear);
					query.setParameter("projid", projId);
					res = query.executeUpdate();
					session.getTransaction().commit();
				}catch (HibernateException e) {
					System.err.print("Hibernate error");
					e.printStackTrace();
					session.getTransaction().rollback();
				} 
			}else if(quarter==3) {
				try {
					Query query = null;
					session.beginTransaction();
					
					query = session.createQuery("update WdcpmksyQuadTarget wdc set wdc.q3status = :q3 where wdc.iwmpMFinYear.finYrCd = :finyear and wdc.iwmpMProject.projectId = :projid");
					query.setParameter("q3", q3);
					query.setParameter("finyear", finYear);
					query.setParameter("projid", projId);
					res = query.executeUpdate();
					session.getTransaction().commit();
				}catch (HibernateException e) {
					System.err.print("Hibernate error");
					e.printStackTrace();
					session.getTransaction().rollback();
				} 
			}else if(quarter==4) {
				try {
					Query query = null;
					session.beginTransaction();
					
					query = session.createQuery("update WdcpmksyQuadTarget wdc set wdc.q4status = :q4 where wdc.iwmpMFinYear.finYrCd = :finyear and wdc.iwmpMProject.projectId = :projid");
					query.setParameter("q4", q4);
					query.setParameter("finyear", finYear);
					query.setParameter("projid", projId);
					res = query.executeUpdate();
					session.getTransaction().commit();
				}catch (HibernateException e) {
					System.err.print("Hibernate error");
					e.printStackTrace();
					session.getTransaction().rollback();
				} 
			}
			if(res>0) {
				result="success";
			}
			else {
				result = "fail";
			}
		}
		
		return result;
	}

	@Override
	public List<WdcpmksyQuadTarget> getOomFQuarterlyTardata(Integer finYear, Integer projId) {
		List<WdcpmksyQuadTarget> list = new ArrayList<>();
		Session session = sessionFactory.openSession();
		try {
			Query query = null;
			session.beginTransaction();
			query = session.createQuery("from WdcpmksyQuadTarget where iwmpMFinYear.finYrCd = :finyear and iwmpMProject.projectId = :projid");
			query.setInteger("finyear", finYear);
			query.setInteger("projid", projId);
//			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyQuadTarget.class));
			list = query.list();
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

}
