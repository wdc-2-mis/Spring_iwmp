package app.dao.unfreezeImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.ShgDetailBean;
import app.dao.unfreeze.UnfreezeShgDetailDao;
import app.model.outcome.ShgDetail;
import app.model.outcome.ShgMain;

@Repository("UnfreezeShgDetailDao")
public class UnfreezeShgDetailDaoImpl implements UnfreezeShgDetailDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${unfreezeShgDetails}")
	String unfreezeShgDetails;
	
	@Value("${unfreezeShgAccountDetails}")
	String unfreezeShgAccountDetails;

	@Override
	public List<ShgDetailBean> unfreezeListShgDetails(Integer project, String grp, String headType) {
		List<ShgDetailBean> getshgDetail = new ArrayList<>();
	    String hql = unfreezeShgDetails;
	    String hql2 = unfreezeShgAccountDetails;
	    String shg_type="";
	    if(headType.equals("ShgA"))
		{
	    Session session = sessionFactory.getCurrentSession();
	    try {
	        session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(hql2);
	        query.setInteger("project", project);
	       
	        if(grp.equals("newShg"))
	            query.setString("grp", "newSHG");
	        if(grp.equals("exShg"))
	            query.setString("grp", "oldSHG");
	        
	        query.setResultTransformer(Transformers.aliasToBean(ShgDetailBean.class));
	        getshgDetail = query.list();
	        session.getTransaction().commit();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        session.getTransaction().rollback();
	    }
		}
	    if(headType.equals("ShgD"))
		{
	    Session session = sessionFactory.getCurrentSession();
	    try {
	        session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(hql);
	        query.setInteger("project", project);
	       
	        if(grp.equals("newShg"))
	            query.setString("grp", "newSHG");
	        if(grp.equals("exShg"))
	            query.setString("grp", "oldSHG");
	        
	        query.setResultTransformer(Transformers.aliasToBean(ShgDetailBean.class));
	        getshgDetail = query.list();
	        session.getTransaction().commit();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        session.getTransaction().rollback();
	    }
		}
	    return getshgDetail;
	}

	@Override
	public boolean unfreezeShgDetailsData(String[] shg_id, String headType) {
			Boolean res=false;
			Session session = sessionFactory.getCurrentSession();
			
			try 
			{
				session.beginTransaction();
				
				for(String code : shg_id)
				{
					if(headType.equals("ShgA"))
					{
						ShgDetail sd = session.load(ShgDetail.class, Integer.parseInt(code));
						sd.setStatus('D');
						session.update(sd);
					}
					if(headType.equals("ShgD"))
					{
						
						ShgMain sm = session.load(ShgMain.class, Integer.parseInt(code));
						sm.setStatus('D');
						session.update(sm);
                    
                    Query query = session.createQuery("FROM ShgDetail WHERE shgMain = :sghid", ShgDetail.class);
                    query.setParameter("sghid", sm);
                    List<ShgDetail> shgDetails = query.getResultList();
                    
                    for (ShgDetail sd : shgDetails) {
                        sd.setStatus('D');
                        session.update(sd);
                    }
					}
				}
				session.getTransaction().commit();
				res=true;
			}
			catch(Exception ex) 
			{
				ex.printStackTrace();
				session.getTransaction().rollback();
			return false;
			}
			
			return res;
		}
}