package app.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JOptionPane;

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
import app.bean.PhysicalHeaddataBean;
import app.dao.PhysicalActDao;
import app.model.IwmpState;
import app.model.IwmpUserProjectMap;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.SlnaStProfile;
import app.model.master.WdcpmksyMPhyOtherActivity;

@Repository("physicalActDao")
public class PhysicalActDaoImpl implements PhysicalActDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${phyActivityDetail}")
	String phyActivityDetail;
	
	@Value("${getheadcode}")
	String getheadcode;
	
	@Value("${getuomcode}")
	String getuomcode;
	
	@Value("${getseqno}")
	String getseqno;
	
	@Value("${getactivitydesc}")
	String getactivitydesc;
	
	@Value("${savephyAct}")
	String savephyAct;
	
	@Value("${findactvdesc}")
	String findactvdesc;
	
	@Value("${updatephyAct}")
	String updatephyAct;
	
	@Value("${deletephyAct}")
	String deletephyAct;
	
	@Value("${getphysubactivity}")
	String getphysubactivity;
	
	@Value("${getothersubcategory}")
	String getothersubcategory;
	
	@Value("${getphysubidactivity}")
	String getphysubidactivity;
	
	@Value("${getothersubcategorybyid}")
	String getothersubcategorybyid;
	
	@Value("${deletephysubAct}")
	String deletephysubAct;
	
	@Value("${deleteothrsubCat}")
	String deleteothrsubCat;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, List<PhysicalActBean>> getPhyActdata() {
	
			// TODO Auto-generated method stub
			LinkedHashMap<Integer, List<PhysicalActBean>> map = new LinkedHashMap<Integer, List<PhysicalActBean>>();
			Session session = sessionFactory.getCurrentSession();
			try {
				session.beginTransaction();
				SQLQuery query = null;
				String hql = phyActivityDetail;
				query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(PhysicalActBean.class));
				List<PhysicalActBean> list = query.list();
				for (PhysicalActBean row : list){
					//System.out.println("userId: "+row.getHeadcode());
				}
				List<PhysicalActBean> sublist = new ArrayList<PhysicalActBean>();
				if ((list != null) && (list.size() > 0)) {
					for (PhysicalActBean row : list){
						if (!map.containsKey(row.getActivitycode())) {
							sublist = new ArrayList<PhysicalActBean>();
							sublist.add(row);
							map.put(row.getActivitycode(), sublist);
						} else {
							sublist.add(row);
							map.put(row.getActivitycode(), sublist);
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
			}finally {
				
			}
			return map;
		}

	@Override
	public LinkedHashMap<Integer, String> getHeadCode() {		
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<PhysicalActBean> rows = new ArrayList<PhysicalActBean>();
		String hql=getheadcode;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActBean.class));
			
			rows = query.list();
			  for(PhysicalActBean row : rows){
				 map.put(row.getHeadcode(), row.getHeaddesc());
				 
			  }
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getUomCode() {
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<PhysicalActBean> rows = new ArrayList<PhysicalActBean>();
		String hql=getuomcode;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActBean.class));
			
			rows = query.list();
			  for(PhysicalActBean row : rows){
				 map.put(row.getUnitcode(), row.getUnitdesc());
				 
			  }
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return map;
	}

	
	@Override
	public Boolean savephyact(String adesc, int headcode, int uomcode, String status, BigDecimal seqno, int assets, String loginID) {
		Boolean res=false;
		Integer value=0;
		String savesql=savephyAct;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			
			
			 {
				
				 SQLQuery query = session.createSQLQuery(savesql);
				  Date d= new Date();
			        
				        query.setInteger("headcode", headcode);
				        query.setString("adesc", adesc);
				        query.setInteger("uomcode", uomcode);
			        	query.setString("status", status);
			        	query.setString("lastupdatedby", loginID);
			        	query.setDate("lastupdateddate", d);
			        	query.setString("requestid",ipadd);
					    query.setBigDecimal("seqno", seqno);
					    query.setInteger("assets" , assets);
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
		   ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

	@Override
	public List<PhysicalActBean> findactdesc(Integer id) {
List<PhysicalActBean> findactdesc=new ArrayList<PhysicalActBean>();
		
		String hql=findactvdesc;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("id", id);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
		
	}

	@Override
	public Boolean updateactdata(int id, String adesc, String status, int uomcode, BigDecimal seqno, int asset, String loginId) {
		Boolean res=false;
		Integer value=0;
		String savesql=updatephyAct;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			
			
			 {
				
				  
				 SQLQuery query = session.createSQLQuery(savesql);
				  Date d= new Date();
			        
				        query.setString("adesc", adesc);
				        query.setInteger("uomcode", uomcode);
			        	query.setString("status", status);
			        	query.setBigDecimal("seqno", seqno);
			        	query.setString("lastupdatedby", loginId);
			        	query.setDate("lastupdateddate", d);
			        	query.setString("ipadd",ipadd);
			        	query.setInteger("asset", asset);
			        	query.setInteger("id",id);
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
		    ex.printStackTrace();
		    session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

	@Override
	public Boolean deletephyhead(int id) {
		Boolean res=false;
		Integer value=0;
		String st = "could not delete record because this record exist on another table";
		String savesql=deletephyAct;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			
			
			
			 {
				SQLQuery query = session.createSQLQuery(savesql);
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
	public List<PhysicalActBean> getseqnum(int headcode) {
    List<PhysicalActBean> getseqnum=new ArrayList<PhysicalActBean>();
		
		String hql=getseqno;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("headcode", headcode);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActBean.class));
			getseqnum = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getseqnum;
	}

	@Override
	public LinkedHashMap<Integer, String> getactdesc(int headcode) {
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<PhysicalActBean> rows = new ArrayList<PhysicalActBean>();
		String hql=getactivitydesc;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setParameter("headcode", headcode);
			query.setResultTransformer(Transformers.aliasToBean(PhysicalActBean.class));
			
			rows = query.list();
			  for(PhysicalActBean row : rows){
				 map.put(row.getActivitycode(), row.getActdesc());
				 
			  }
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return map;
	}

	@Override
	public String savephysubact(int actdesc, String sbactdesc, Character status, BigDecimal seqno, String loginId, String userType, int stCode) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		IwmpMPhyActivity data = new IwmpMPhyActivity();
		data.setActivityCode(actdesc);
		IwmpMPhySubactivity savedata = new IwmpMPhySubactivity();
		WdcpmksyMPhyOtherActivity othrAct = new WdcpmksyMPhyOtherActivity();
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			if(userType.equals("ADMIN")) {
				savedata.setIwmpMPhyActivity(data);
				savedata.setSubActivityDesc(sbactdesc);
				savedata.setCreatedon(new Date());
				savedata.setStatus(status);
				savedata.setLastUpdatedBy(loginId);
				savedata.setLastUpdatedDate(new Date());
				savedata.setCreatedBy(loginId);
	            savedata.setRequestIp(ipAddr);
	            savedata.setSeqNo(seqno);
				session.save(savedata);
			}else if(userType.equals("SL")) {
				IwmpState st = new IwmpState();
				st.setStCode(stCode);
				othrAct.setIwmpMPhyActivity(data);
				othrAct.setIwmpState(st);
				othrAct.setOtherActivityDesc(sbactdesc);
				othrAct.setCreatedon(new Date());
				othrAct.setStatus(status);
				othrAct.setLastUpdatedBy(loginId);
				othrAct.setLastUpdatedDate(new Date());
				othrAct.setCreatedBy(loginId);
				othrAct.setRequestIp(ipAddr);
				othrAct.setSeqNo(seqno);
				session.save(othrAct);
			}
			
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
	public List<IwmpMPhySubactivity> getPhySubActdata() {
    List<IwmpMPhySubactivity> getsubactdata=new ArrayList<IwmpMPhySubactivity>();
		
		String hql=getphysubactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			getsubactdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getsubactdata;
	}

	@Override
	public List<IwmpMPhySubactivity> findsubactdesc(Integer id) {
    List<IwmpMPhySubactivity> getsubactdata=new ArrayList<IwmpMPhySubactivity>();
		
		String hql=getphysubidactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getsubactdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getsubactdata;
	}

	@Override
	public String updatephysubact(int id, String sbactdesc, Character status, BigDecimal seqno, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		IwmpMPhySubactivity savedata = new IwmpMPhySubactivity();
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			savedata = (IwmpMPhySubactivity) session.get(IwmpMPhySubactivity.class, id);
			savedata.setSubActivityDesc(sbactdesc);
			savedata.setStatus(status);
			savedata.setLastUpdatedBy(loginId);
			savedata.setLastUpdatedDate(new Date());
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
	public Boolean deletephysubhead(int id) {
		Boolean res=false;
		Integer value=0;
		String savesql=deletephysubAct;
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
	public List<WdcpmksyMPhyOtherActivity> getOtherSubCategorydata(int stCode) {
	    List<WdcpmksyMPhyOtherActivity> getOthersubCatdata=new ArrayList<WdcpmksyMPhyOtherActivity>();
		String hql=getothersubcategory;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setInteger("stCode", stCode);
			getOthersubCatdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getOthersubCatdata;
	}

	@Override
	public List<WdcpmksyMPhyOtherActivity> findothrsubcatdesc(Integer id) {
		List<WdcpmksyMPhyOtherActivity> getOthersubCatdata=new ArrayList<WdcpmksyMPhyOtherActivity>();
		String sthql = getothersubcategorybyid;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(sthql);
			query.setParameter("id", id);
			getOthersubCatdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getOthersubCatdata;
	}

	@Override
	public String updateothrsubcat(int id, String oscatdesc, Character status, BigDecimal seqno, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		WdcpmksyMPhyOtherActivity savedata = new WdcpmksyMPhyOtherActivity();
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			savedata = (WdcpmksyMPhyOtherActivity) session.get(WdcpmksyMPhyOtherActivity.class, id);
			savedata.setOtherActivityDesc(oscatdesc);
			savedata.setStatus(status);
			savedata.setLastUpdatedBy(loginId);
			savedata.setLastUpdatedDate(new Date());
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
	public Boolean deleteothersubcat(int id) {
		Boolean res=false;
		Integer value=0;
		String savesql=deleteothrsubCat;
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
			session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

	

}