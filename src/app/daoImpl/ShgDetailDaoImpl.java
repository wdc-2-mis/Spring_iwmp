package app.daoImpl;

import java.util.ArrayList;
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
import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.dao.ShgDetailDao;
import app.model.outcome.ShgDetail;

@Repository("ShgDetailDao")
public class ShgDetailDaoImpl implements ShgDetailDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getShgDetails}")
	String getShgDetails;
	
	@Value("${getShgAccountReport}")
	String getShgAccountReport;
	
	@Value("${getDistShgAccountReport}")
	String getDistShgAccountReport;
	
	@Value("${getProjShgAccountReport}")
	String getProjShgAccountReport;
	
	@Value("${getStatusSHG}")
	String getStatusSHGDetails;
	
	@Value("${getRemainingSHG}")
	String getRemainingSHGDetails;
	

	@Override
	public List<ShgDetailBean> getListShgDetails(Integer projId, Integer grp) {
	    List<ShgDetailBean> getshgDetail = new ArrayList<>();
	    String hql = getShgDetails;
	    String shg_type="";
	    
	    Session session = sessionFactory.getCurrentSession();
	    try {
	        session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(hql);
	        query.setInteger("projId", projId);
	        if(grp == 1)
	            query.setString("grp", "newSHG");
	        if(grp == 2)
	            query.setString("grp", "oldSHG");
	        
	        query.setResultTransformer(Transformers.aliasToBean(ShgDetailBean.class));
	        getshgDetail = query.list();
	        session.getTransaction().commit();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        session.getTransaction().rollback();
	    }
	    return getshgDetail;
	}

	@Override
	public String saveListShgDetails(List<String> shg_detail_id,  List<String> account_detail, List<String> ifsc_code) {

	    String res = "fail";
	    Session session = sessionFactory.getCurrentSession();
	    try {
	        session.beginTransaction();
	        for (int i = 0; i < shg_detail_id.size(); i++) {
	        	
	        	ShgDetail shg = session.load(ShgDetail.class, Integer.parseInt(shg_detail_id.get(i)));
	        	shg.setAccountDetail(account_detail.get(i));
	            shg.setIfsCode(ifsc_code.get(i));
	            shg.setStatus('D');
	            
	            session.saveOrUpdate(shg);
	        }

	        session.getTransaction().commit();
	        res = "success";

	    } catch (HibernateException e) {
	        System.err.println("Hibernate error");
	        e.printStackTrace();
	        session.getTransaction().rollback();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        session.getTransaction().rollback();
	    }

	    return res;
	}
	
	@Override
	public String deleteSHGDetails(Integer shg_detail_id) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			ShgDetail shg = session.load(ShgDetail.class, shg_detail_id);
			shg.setAccountDetail(null);
            shg.setIfsCode(null);
            shg.setStatus(null);
			session.saveOrUpdate(shg);
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
	public String completeShgDetails(String shg_detail_id1[]) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			for (int i = 0; i < shg_detail_id1.length; i++) {
				ShgDetail shg = session.load(ShgDetail.class, Integer.parseInt(shg_detail_id1[i]));
				shg.setStatus('C');
				session.saveOrUpdate(shg);
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
	public List<ShgDetailBean> getShgAccount() {
		 List<ShgDetailBean> getshgReport = new ArrayList<>();
		    String hql = getShgAccountReport;
		    String shg_type="";
		    
		    Session session = sessionFactory.getCurrentSession();
		    try {
		        session.beginTransaction();
		        SQLQuery query = session.createSQLQuery(hql);
		        query.setResultTransformer(Transformers.aliasToBean(ShgDetailBean.class));
		        getshgReport = query.list();
		        session.getTransaction().commit();
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        session.getTransaction().rollback();
		    }
		    return getshgReport;
		}

	@Override
	public List<ShgDetailBean> distShgAccountReport(int stcode) {
		Session session = sessionFactory.getCurrentSession();
		List<ShgDetailBean> distlist = new ArrayList<>();
		String disthql = getDistShgAccountReport;
		try {
			session.beginTransaction();
			distlist = session.createSQLQuery(disthql).setInteger("stcode", stcode).setResultTransformer(Transformers.aliasToBean(ShgDetailBean.class)).list();
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return distlist;
	}

	@Override
	public List<ShgDetailBean> projShgAccountReport(int dcode) {
		Session session = sessionFactory.getCurrentSession();
		List<ShgDetailBean> projlist = new ArrayList<>();
		String projhql = getProjShgAccountReport;
		try {
			session.beginTransaction();
			projlist = session.createSQLQuery(projhql).setInteger("dcode", dcode).setResultTransformer(Transformers.aliasToBean(ShgDetailBean.class)).list();
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
	public List<ShgDetailBean> getStatusSHG(Integer stcd) {
		List<ShgDetailBean> getStatusSHG = new ArrayList<>();
		String hql = getStatusSHGDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setResultTransformer(Transformers.aliasToBean(ShgDetailBean.class));
			getStatusSHG = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getStatusSHG;
	}

	@Override
	public List<ShgDetailBean> getRemainingSHG(Integer dcode, String type) {
		List<ShgDetailBean> getRemainingSHG = new ArrayList<>();
		String hql = getRemainingSHGDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("dcode", dcode);
			query.setString("type", type);
//			if(type == "1")
//	            query.setString("type", "newSHG");
//	        if(type == "2")
//	            query.setString("type", "oldSHG");
			query.setResultTransformer(Transformers.aliasToBean(ShgDetailBean.class));
			getRemainingSHG = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getRemainingSHG;
	}
	
	
}
	
	
