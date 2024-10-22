package app.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
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

import app.bean.PhysicalActBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.dao.BaselineTypeDao;
import app.model.MBlsOutType;
import app.model.MBlsOutcome;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;

@Repository("baselineTypeDao")
public class BaselineTypeDaoImpl implements BaselineTypeDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getbaselinetype}")
	String getbaselinetype;
	
	@Value("${gettypeCode}")
	String gettypeCode;
	
	@Value("${getbaselinedata}")
	String getbaselinedata;
	
	@Value("${findbaselinetypedata}")
	String findbaselinetypedata;
	
	@Value("${deletebaselinetype}")
	String deletebaselinetype;
	
	@Value("${findbasedata}")
	String findbasedata;
	
	@Value("${deletebaseline}")
	String deletebaseline;
	
	@Override
	public String savebaselinetypedata(String typecode, String desc, BigDecimal seqno, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		MBlsOutType savedata = new MBlsOutType();
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			savedata.setTypeCode(typecode);
			savedata.setCreatedOn(new Date());
			savedata.setDescription(desc);
			savedata.setSeqNo(seqno);
			savedata.setUpdatedOn(new Date());
			savedata.setCreatedBy(loginId);
            savedata.setRequestIp(ipAddr);
            
			session.save(savedata);
            session.getTransaction().commit();
			res = "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public List<MBlsOutType> getbaselinetypedata() {
    List<MBlsOutType> getbltypedata=new ArrayList<MBlsOutType>();
		
		String hql=getbaselinetype;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			getbltypedata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getbltypedata;
	}
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, String> gettypeCode() {
		List<MBlsOutType> typecode = new ArrayList<MBlsOutType>();
		String hql=gettypeCode;
		LinkedHashMap<Integer, String> typeMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(MBlsOutType.class));
			typecode = query.list();
			  for(MBlsOutType row : typecode){
				  typeMap.put(row.getMBlsOutTypeIdPk(), row.getDescription());
				 
			  }
			 session.getTransaction().commit();
		}catch(HibernateException ex) {
		    ex.printStackTrace();
		    session.getTransaction().rollback();
		}catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return typeMap;
	}

	@Override
	public String savebaselinedata(Integer btype, String bdesc, BigDecimal seqno, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		MBlsOutcome savedata = new MBlsOutcome();
		MBlsOutType typeid = new MBlsOutType();
		typeid.setMBlsOutTypeIdPk(btype);
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			savedata.setMBlsOutType(typeid);
			savedata.setCreatedOn(new Date());
			savedata.setDescription(bdesc);
			savedata.setSeqNo(seqno);
			savedata.setUpdatedOn(new Date());
			savedata.setCreatedBy(loginId);
            savedata.setRequestIp(ipAddr);
            
			session.save(savedata);
            session.getTransaction().commit();
			res = "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public List<MBlsOutcome> getbaselinedata() {
     List<MBlsOutcome> getbldata=new ArrayList<MBlsOutcome>();
		
		String hql=getbaselinedata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			getbldata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getbldata;
	}

	@Override
	public List<MBlsOutType> findbaselinetypedesc(Integer id) {
    List<MBlsOutType> getbaselinetypedata=new ArrayList<MBlsOutType>();
		
		String hql=findbaselinetypedata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getbaselinetypedata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getbaselinetypedata;
	}

	@Override
	public String updatebaselinetypedata(int id, String typecode, String desc, BigDecimal seqno, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		MBlsOutType savedata = new MBlsOutType();
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			savedata = (MBlsOutType) session.get(MBlsOutType.class, id);
			
			savedata.setTypeCode(typecode);
			savedata.setDescription(desc);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
            savedata.setSeqNo(seqno);
			session.update(savedata);
            session.getTransaction().commit();
			res = "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public Boolean deletebaselinetype(int id) {
		Boolean res=false;
		Integer value=0;
		String savesql=deletebaselinetype;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
		 {
				Query query = session.createQuery(savesql);
				    	query.setInteger("id", id);
			            value=query.executeUpdate();
						if(value>0) {
							  res=true;
						}else {
							session.getTransaction().rollback();
							return false;
						}
				
			}
			if(res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();

		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			//JOptionPane.showMessageDialog(null,st,"ALERT",JOptionPane.INFORMATION_MESSAGE);
			session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

	@Override
	public List<MBlsOutcome> findbaselinedesc(Integer id) {
      List<MBlsOutcome> getbaselinedata=new ArrayList<MBlsOutcome>();
		
		String hql=findbasedata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getbaselinedata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getbaselinedata;
	}

	@Override
	public String updatebaselinedata(int id, int typedesc, String baslinedesc, BigDecimal seqno, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		MBlsOutType data = new MBlsOutType();
		data.setMBlsOutTypeIdPk(typedesc);
		MBlsOutcome savedata = new MBlsOutcome();
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			savedata = (MBlsOutcome) session.get(MBlsOutcome.class, id);
			savedata.setMBlsOutType(data);
			savedata.setDescription(baslinedesc);
			savedata.setSeqNo(seqno);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
            session.update(savedata);
            session.getTransaction().commit();
			res = "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public Boolean deletebaseline(int id) {
		Boolean res=false;
		Integer value=0;
		String savesql=deletebaseline;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
		 {
				Query query = session.createQuery(savesql);
				    	query.setInteger("id", id);
			            value=query.executeUpdate();
						if(value>0) {
							  res=true;
						}else {
							session.getTransaction().rollback();
							return false;
						}
				
			}
			if(res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();

		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			//JOptionPane.showMessageDialog(null,st,"ALERT",JOptionPane.INFORMATION_MESSAGE);
			session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

}
