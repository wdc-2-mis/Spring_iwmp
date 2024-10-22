package app.daoImpl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
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

import app.bean.PhysicalActBean;
import app.bean.PhysicalHeaddataBean;
import app.bean.UOMDataBean;
import app.dao.UOMDao;

@Repository("uomDao")
public class UOMDaoImpl implements UOMDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getuomdata}")
	String getuomdata;
	
	@Value("${finduomcode}")
	String finduomcode;
	
	@Value("${saveuomdata}")
	String saveuomdata;
	
	@Value("${finduomeditdata}")
	String finduomeditdata;
	
	@Value("${updateUOM}")
	String updateUOM;
	
	@Value("${deleteuomdata}")
	String deleteuomdata;
	
	@Override
	public LinkedHashMap<Integer, List<UOMDataBean>> getUOMdata() {
		// TODO Auto-generated method stub
					LinkedHashMap<Integer, List<UOMDataBean>> map = new LinkedHashMap<Integer, List<UOMDataBean>>();
					Session session = sessionFactory.getCurrentSession();
					try {
						session.beginTransaction();
						SQLQuery query = null;
						String hql = getuomdata;
						query = session.createSQLQuery(hql);
						query.setResultTransformer(Transformers.aliasToBean(UOMDataBean.class));
						List<UOMDataBean> list = query.list();
						for (UOMDataBean row : list){
							//System.out.println("userId: "+row.getHeadcode());
						}
						List<UOMDataBean> sublist = new ArrayList<UOMDataBean>();
						if ((list != null) && (list.size() > 0)) {
							for (UOMDataBean row : list){
								if (!map.containsKey(row.getUnitcode())) {
									sublist = new ArrayList<UOMDataBean>();
									sublist.add(row);
									map.put(row.getUnitcode(), sublist);
								} else {
									sublist.add(row);
									map.put(row.getUnitcode(), sublist);
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
	public List<UOMDataBean> getuomcode() {
List<UOMDataBean> getuomcode=new ArrayList<UOMDataBean>();
		
		String hql=finduomcode;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(UOMDataBean.class));
			getuomcode = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getuomcode;
	}

	@Override
	public Boolean savephyact(int uomcode, String uomdesc, String loginID) {
		Boolean res=false;
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String savesql=saveuomdata;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			 {
				 SQLQuery query = session.createSQLQuery(savesql);
				 Date d= new Date();
			        	query.setInteger("unitcode", uomcode);
			        	query.setString("unitdesc", uomdesc);
			        	query.setString("lastupdatedby", loginID);
			        	query.setDate("lastupdateddate", d);
			        	query.setString("requestid",ipadd);
			        	
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
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

	@Override
	public List<UOMDataBean> edituomdata(int id) {
List<UOMDataBean> edituomdata=new ArrayList<UOMDataBean>();
		
		String hql=finduomeditdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("unitcode", id);
			query.setResultTransformer(Transformers.aliasToBean(UOMDataBean.class));
			edituomdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return edituomdata;
	}

	@Override
	public Boolean updateuomdata(int id, String uomdesc, String loginID) {
		Boolean res=false;
		Integer value=0;
		String savesql=updateUOM;
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			{
			 SQLQuery query = session.createSQLQuery(savesql);
				  Date d= new Date();
			        
				        query.setString("uomdesc", uomdesc);
				        query.setString("lastupdatedby", loginID);
			        	query.setDate("lastupdateddate", d);
			        	query.setString("ipadd",ipadd);
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
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

	@Override
	public Boolean deleteUOMdata(int id) {
		Boolean res=false;
		Integer value=0;
		String savesql=deleteuomdata;
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
			//JOptionPane.showMessageDialog(null,st,"ALERT",JOptionPane.INFORMATION_MESSAGE); 
			session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

}
