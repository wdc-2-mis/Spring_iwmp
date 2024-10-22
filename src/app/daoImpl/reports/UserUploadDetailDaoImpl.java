package app.daoImpl.reports;

import java.math.BigInteger;
import java.net.InetAddress;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.WcdcPiaUserBean;
import app.bean.reports.UserFileUploadBean;
import app.bean.reports.UserUploadDetailsBean;
import app.dao.reports.UserUploadDetailDao;
import app.model.IwmpDistrict;
import app.model.master.IwmpUploadCategory;

@Repository("userUploadDetailDao")
public class UserUploadDetailDaoImpl implements UserUploadDetailDao{

	@Autowired
	private SessionFactory sessionFactory;

	  @Value("${getCategoryUploadforSLNA}") 
	  String getCategoryUploadforSLNA;
	
	  @Value("${ListofuploadedFileforSLNA}") 
	  String ListofuploadedFileforSLNA;
	  
	  @Value("${updmaxregid}") 
	  String updmaxregid;
	  
	  @Value("${userUploadSaveSLNA}")   
	  String userUploadSaveSLNA;
	  
	  @Value("${userUploadDeleteSLNA}")   
	  String userUploadDeleteSLNA;
	  
	  @Value("${userUploadUpdateSLNA}")   
	  String userUploadUpdateSLNA;
	  
	  @Value("${getCategoryUploadforDL}") 
	  String getCategoryUploadforDL;
	  
	  @Value("${ListofuploadedfileDL}") 
	  String ListofuploadedfileDL;
	  
	  @Value("${ListofUploadedDetailsSLNAReportAllState}") 
	  String ListofUploadedDetailsSLNAReportAllState;
	  
	  @Value("${ListofUploadedDetailsSLNAReport}") 
	  String ListofUploadedDetailsSLNAReport;
	  
	  @Value("${ListofuploadedFileforState}") 
	  String ListofuploadedFileforState;
	  
	  @Value("${doLRApprovalUploadingDocument}") 
	  String doLRApprovalUploadingDocument;
	
	@Override
	public List<IwmpUploadCategory> getUploadCategoryforSLNA() 
	{
		List<IwmpUploadCategory> result=new ArrayList<IwmpUploadCategory>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=getCategoryUploadforSLNA;
			result = ses.createQuery(hql).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}

	@Override
	public List<UserUploadDetailsBean> getListofuploadedFileforSLNA(Integer stcode) 
	{
		List<UserUploadDetailsBean> result=new ArrayList<UserUploadDetailsBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=ListofuploadedFileforSLNA;
				query = session.createSQLQuery(hql);
				query.setInteger("stcd", stcode);
				query.setResultTransformer(Transformers.aliasToBean(UserUploadDetailsBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.getMessage();
			ex.getCause();
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			//session.flush(); session.close();
		}
		return result;
	}

	@Override
	public Integer getMaxId() {
		Integer result=0;
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			String hql=updmaxregid;
			List list = ses.createQuery(hql).list();
		//	System.out.println(list.get(0));
			result=Integer.parseInt(list.get(0).toString());
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}

	@Override
	public boolean userUploadSaveSLNA(UserFileUploadBean userfileup, HttpSession session, String filePath, String fname) {
		Boolean res=false;
		Integer value=0;
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String savesql=userUploadSaveSLNA;
		try {
				int mid=0;
				String ext="";
				String fileName = userfileup.getTheFile().getOriginalFilename();
				mid = fileName.lastIndexOf(".");
				ext = fileName.substring(mid + 1, fileName.length()); 
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress(); 
				Date d= new Date();
				SQLQuery query = sessionf.createSQLQuery(savesql);
			    query.setString("created_by", session.getAttribute("loginID").toString());
			    query.setDate("created_date", d);
			    query.setDate("date_of_publish", d);
			    query.setString("file_extension",ext);
			    query.setString("file_name",fname+"."+ext);
			    
			    if(userfileup.getNew_File()==null)
			    	query.setBoolean("is_new", false);
			    else
			    	query.setBoolean("is_new", true);
			    
			    if(userfileup.getPublish()==null)
			    	query.setBoolean("publish", false);
			    else
			    	query.setBoolean("publish", true);
			    
			    query.setString("path",filePath);
			    
			    query.setString("requested_ip",ipadd);
			    query.setInteger("st_code",Integer.parseInt(session.getAttribute("stateCode").toString()));
			    query.setString("subject",userfileup.getSubject()); 
			    query.setString("updated_by",session.getAttribute("loginID").toString());
			    query.setDate("updated_date", d);
			    query.setInteger("upload_category_id", Integer.parseInt(userfileup.getCategory_type()));
				value=query.executeUpdate();
				if(value>0) 
				{
					res=true;
				}
				else {
						sessionf.getTransaction().rollback();
						return false;
				}
				if(res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}

	@Override
	public boolean userUploadDeleteSLNA(Integer id, String filename, String filePath) 
	{
		Boolean res=false;
		Integer value=0;
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String deletesql=userUploadDeleteSLNA;
		try {
				SQLQuery query = sessionf.createSQLQuery(deletesql);
			    query.setBigInteger("id", BigInteger.valueOf(id));
				value=query.executeUpdate();
				if(value>0) 
				{
					res=true;
				}
				else {
						sessionf.getTransaction().rollback();
						return false;
				}
				if(res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}

	@Override
	public boolean userUploadUpdateSLNA(Integer id, String newsub, String caption_id, String publish_id,
			int newcat_id, String newcattxt, String login) 
	{
		Boolean res=false;
		Integer value=0;
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String updatesql=userUploadUpdateSLNA;
		try {
				SQLQuery query = sessionf.createSQLQuery(updatesql);
				query.setString("new_sub",newsub);
				
				if(caption_id.equals("false"))
					query.setBoolean("new_cap", false);
			    else
			    	query.setBoolean("new_cap", true);
			    if(publish_id.equals("false"))
			    	query.setBoolean("new_pub", false);
			    else
			    	query.setBoolean("new_pub", true);
			    
				query.setInteger("newcat_id",newcat_id);
				query.setString("loginid",login);
			    query.setInteger("id", id);
				value=query.executeUpdate();
				if(value>0) 
				{
					res=true;
				}
				else {
						sessionf.getTransaction().rollback();
						return false;
				}
				if(res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}

	@Override
	public List<IwmpUploadCategory> getUploadCategoryforDL() {
		List<IwmpUploadCategory> result=new ArrayList<IwmpUploadCategory>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();	
			String hql=getCategoryUploadforDL;
			result = ses.createQuery(hql).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}

	@Override
	public List<UserUploadDetailsBean> getListofuploadedFileforDL() {
		List<UserUploadDetailsBean> result=new ArrayList<UserUploadDetailsBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=ListofuploadedfileDL;
				query = session.createSQLQuery(hql);
				query.setResultTransformer(Transformers.aliasToBean(UserUploadDetailsBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.getMessage();
			ex.getCause();
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			//session.flush(); session.close();
		}
		return result;
	}

	@Override
	public boolean userUploadSaveDL(UserFileUploadBean userfileup, HttpSession session, String filePath, String fname, String lang) {
		Boolean res=false;
		Integer value=0;
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String savesql=userUploadSaveSLNA;
		try {
				int mid=0;
				String ext="";
				String fileName = userfileup.getTheFile().getOriginalFilename();
				mid = fileName.lastIndexOf(".");
				ext = fileName.substring(mid + 1, fileName.length()); 
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress(); 
				Date d= new Date();
				SQLQuery query = sessionf.createSQLQuery(savesql);
			    query.setString("created_by", session.getAttribute("loginID").toString());
			    query.setDate("created_date", d);
			    query.setDate("date_of_publish", d);
			    if((fileName!=null && !fileName.equals("")) || userfileup.getCategory_type().equals("10")) 
			    {
			    	query.setString("path",filePath);
			    	if(!userfileup.getCategory_type().equals("10")) {
				    query.setString("file_extension",ext);
				    query.setString("file_name",fname+"."+ext);
			    	}
			    }
			    else {
				    query.setString("path",null);
				    query.setString("file_extension",null);
				    query.setString("file_name",null);
			    } 
			    if(userfileup.getCategory_type().equals("10")) {
			    	 query.setString("file_extension",null);
					 query.setString("file_name",null);
			    }
			    if(userfileup.getNew_File()==null)
			    	query.setBoolean("is_new", false);
			    else
			    	query.setBoolean("is_new", true);
			    
			    if(userfileup.getPublish()==null)
			    	query.setBoolean("publish", false);
			    else
			    	query.setBoolean("publish", true);
			    
			    query.setString("requested_ip",ipadd);
			    query.setInteger("st_code",0);
			    query.setString("subject",userfileup.getSubject()); 
			    query.setString("updated_by",session.getAttribute("loginID").toString());
			    query.setDate("updated_date", d);
			    query.setString("lang", lang);
			    query.setInteger("upload_category_id", Integer.parseInt(userfileup.getCategory_type()));
				value=query.executeUpdate();
				if(value>0) 
				{
					res=true;
				}
				else {
						sessionf.getTransaction().rollback();
						return false;
				}
				if(res)
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}

	@Override
	public List<UserUploadDetailsBean> getListofUploadedDetailsSLNAReport(int stCode) {
		List<UserUploadDetailsBean> result=new ArrayList<UserUploadDetailsBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				if(stCode==0) {
					hql=ListofUploadedDetailsSLNAReportAllState;
					query = session.createSQLQuery(hql);
				}
				else {
					hql=ListofUploadedDetailsSLNAReport;
					query = session.createSQLQuery(hql);
					query.setInteger(0, stCode);
				}
				query.setResultTransformer(Transformers.aliasToBean(UserUploadDetailsBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.getMessage();
			ex.getCause();
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			//session.flush(); session.close();
		}
		return result;
	}

	@Override
	public List<UserUploadDetailsBean> getListofuploadedFileforState(Integer stcode) {
		
		List<UserUploadDetailsBean> result=new ArrayList<UserUploadDetailsBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=ListofuploadedFileforState;
				query = session.createSQLQuery(hql);
				query.setInteger("stcode", stcode);
				query.setResultTransformer(Transformers.aliasToBean(UserUploadDetailsBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.getMessage();
			ex.getCause();
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			//session.flush(); session.close();
		}
		return result;
	}

	@Override
	public boolean doLRApprovalUploadingDocument(String[] upid) {
		// TODO Auto-generated method stub
		Boolean res=false;
		Integer value=0;
		
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		String updatesql=doLRApprovalUploadingDocument ;
		try {
				for(int i=0; i<upid.length; i++)
				{
					SQLQuery query = sessionf.createSQLQuery(updatesql);
				    query.setInteger("upid", Integer.parseInt(upid[i]));
				    value=query.executeUpdate();
				}
				if( value> 0 ){
					res=true;
					//sessionf.getTransaction().commit();
				}
				else {
					sessionf.getTransaction().rollback();
				}
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			sessionf.getTransaction().rollback();
			return false;
		}
		finally {
			sessionf.getTransaction().commit();
		}
		return res;
	}

}
