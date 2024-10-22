package app.daoImpl;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.ProjectLocationBean;
import app.bean.WcdcPiaUserBean;
import app.dao.ProjectLocationDao;
import app.model.IwmpUserProjectMap;

@Repository("projectLocationDao")
public class ProjectLocationDaoImpl implements ProjectLocationDao{
	
	 @Autowired
	SessionFactory sessionFactory;
	
	@Value("${getProjectByUser}")
	String getProjectByUser;

	@Value("${getVillagePrjforWC}")
	String getVillagePrjforWC;
	
	@Value("${checkWCLocationDraft}")
	String checkWCLocationDraft;
	
	@Value("${getVillagePrjforWC1}")
	String getVillagePrjforWC1;
	
	@Value("${getdraftPrjforWC}")
	String getdraftPrjforWC;
	
	@Value("${deldraftPrjforWC}")
	String deldraftPrjforWC;
	
	@Value("${saveProjectLocationAsDraft}")
	String saveProjectLocationAsDraft;
	
	@Value("${completeProjectLocation}")
	String completeProjectLocation;
	
	@Value("${getPreFilledProjectLocationData}")
	String getPreFilledProjectLocationData;
	
	@Value("${deletePreFilledProjectLocation}")
	String deletePreFilledProjectLocation;

	@Value("${getmpngprolist}")
	String getmpngprolist;

	@Value("${getWsCommitte}")
	String getWsCommitte;
	
	@Value("${saveWCLocationAsDraft}")
	String saveWCLocationAsDraft;
	
	@Value("${delWCLocationAsDraft}")
	String delWCLocationAsDraft;
	
	@Value("${checkWCLocationAsDraft}")
	String checkWCLocationAsDraft;
	
	@Value("${completeWCmap}")
	String completeWCmap;
	
	@Value("${checkWCStatus}")
	String checkWCStatus;
	
	@Value("${getfinalPrjforWC}")
	String getfinalPrjforWC;
	
	@Value("${prjctLocDetail}") 
	String prjctLocDetail;
	
	@Value("${deletewcaftercompleteLoc}") 
	String deletewcaftercompleteLoc;
	
	@Value("${checkProjectLocationExist}") 
	String checkProjectLocationExist;
	
	@Value("${getVillagePrjforWCExisting}")
	String getVillagePrjforWCExisting ;

	@Value("${getVillagePrjforWCExistingComma}")
	String getVillagePrjforWCExistingComma;
	
	@Value("${getLocationAndBaselineDraft}")
	String getLocationAndBaselineDraft;
	
	@Value("${getLocationAndBaselineDistrictDraft}")
	String getLocationAndBaselineDistrictDraft;
	@Override
	public Boolean saveProjectLocationAsDraft(List<Integer> vCode, Integer pcode,String loginId) {
		// TODO Auto-generated method stub
		Boolean res=false;
		Integer value=0;
		Object[] ob = vCode.toArray();
		Integer vc[] = new Integer[vCode.size()];
		String savesql=saveProjectLocationAsDraft;
		String deletesql=deletePreFilledProjectLocation;
		String check=checkProjectLocationExist;
		Session session = sessionFactory.getCurrentSession();
		
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			for(int i =0; i<ob.length;i++) {
				vc[i]=Integer.parseInt(ob[i].toString());
			}
			session.beginTransaction();
			SQLQuery queryk = session.createSQLQuery(check);
			queryk.setInteger("pcode",pcode);
			Integer tranxCount = Integer.parseInt(queryk.list().get(0).toString());
		     if (tranxCount > 0) 
		     {
		    	 SQLQuery query1 = session.createSQLQuery(deletesql);
		    	 query1.setInteger("pcode",pcode);
		    	 Integer a=query1.executeUpdate();
		     }	 
			for(int i=0;i<vCode.size(); i++)
			{
				for(int j=i+1;j<vCode.size(); j++)
				if(vCode.get(i)==vCode.get(j)) {
				//	System.out.println(vCode.get(i)+" kdy "+vCode.get(j));
					vCode.remove(i);
				}
				
			}	
				 SQLQuery query = session.createSQLQuery(savesql);
				 Date d= new Date();
				 for(Integer row : vCode){ 
					 query.setInteger("vcode",row);
					 query.setInteger("pcode",pcode); 
					 query.setBoolean("status", false);
					 query.setString("createdby", loginId);
					 query.setDate("createddt", d);
					 query.setString("lastupdatedby", loginId);
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
	public Boolean completeProjectLocation(List<Integer> vCode, Integer pcode,String loginId) {
		// TODO Auto-generated method stub
		Boolean res=false;
		Integer value=0;
		Object[] ob = vCode.toArray();
		Integer vc[] = new Integer[vCode.size()];
		String savesql=completeProjectLocation;
	//	String deletesql=deletePreFilledProjectLocation;
		Session session = sessionFactory.getCurrentSession();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			for(int i =0; i<ob.length;i++) {
				vc[i]=Integer.parseInt(ob[i].toString());
			}
			
			session.beginTransaction();
			SQLQuery query1 = session.createSQLQuery(savesql);
			query1.setInteger("pcode",pcode);
			Integer i=query1.executeUpdate();
			
			if(i>0) {
				res=true;
				session.getTransaction().commit();
			}
			else{
				res=false;
				session.getTransaction().rollback();
			}

		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return res;
	}

	@Override
	public List<ProjectLocationBean> getPreFilledProjectLocationData(Integer pcode) {
		// TODO Auto-generated method stub
		List<ProjectLocationBean> villageList=new ArrayList<ProjectLocationBean>();
		
		String hql=getPreFilledProjectLocationData;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pcode", pcode);
			query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			villageList = query.list();
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
        return villageList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectLocationBean> getPiaAssignWc(Integer pcode) {
		List<ProjectLocationBean> getPiaAssignWc=new ArrayList<ProjectLocationBean>();
		
		String hql=getVillagePrjforWC;
		String checkdata = getVillagePrjforWC1;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			
			String data = session.createNativeQuery(checkWCLocationDraft).setParameter("pid", pcode).list().get(0).toString();
			Integer record = Integer.parseInt(data);	
			System.out.println("check record data:" +record);
			if(record>0)
		 	{
			   SQLQuery query1 = session.createSQLQuery(checkdata);
			   query1.setInteger("pcode", pcode);
			   query1.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			   getPiaAssignWc = query1.list();
		 	}
			else {
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pcode", pcode);
			query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			getPiaAssignWc = query.list();
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
        return getPiaAssignWc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String, List<ProjectLocationBean>> getmpngprolist(String user_id) {
    List<ProjectLocationBean> mpdprojname=new ArrayList<ProjectLocationBean>();
    LinkedHashMap<String, List<ProjectLocationBean>> map = new LinkedHashMap<String, List<ProjectLocationBean>>();
		String hql=getmpngprolist;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setString("user_id", user_id);
		
			query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			List<ProjectLocationBean> list = query.list();
			for (ProjectLocationBean row : list){
				System.out.println("value of mpd projname:" +row.getMpdprojname());
			}
			List<ProjectLocationBean> sublist = new ArrayList<ProjectLocationBean>();
			if ((list != null) && (list.size() > 0)) {
				for (ProjectLocationBean row : list){
					if (!map.containsKey(row.getMpdprojname())) {
						System.out.println("in:::::");
						sublist = new ArrayList<ProjectLocationBean>();
						sublist.add(row);
						map.put(row.getMpdprojname(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getMpdprojname(), sublist);
					}
				}
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
	public List<ProjectLocationBean> getWsCommittee(Integer pcode) {
		List<ProjectLocationBean> getWsCommittee=new ArrayList<ProjectLocationBean>();
		
		String hql=getWsCommitte;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pcode", pcode);
			
			query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			getWsCommittee = query.list();
			System.out.println("watershed comm:" +getWsCommittee);
			
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
        return getWsCommittee;
	}

	@Override
	public Boolean saveWCLocationAsDraft(String final1, String loginId) {
		// TODO Auto-generated method stub
				Boolean res=false;
				Integer value=0;
				SQLQuery query1;
				Integer chkvalue=0;
				String savesql=saveWCLocationAsDraft;
				String deleteWSsql=delWCLocationAsDraft;
				String checkdata=checkWCLocationAsDraft;
				Session session = sessionFactory.getCurrentSession();
				try {
					InetAddress inetAddress = InetAddress.getLocalHost(); 
					String ipadd=inetAddress.getHostAddress(); 
					
					
					session.beginTransaction();
					 
						 String parts[] = final1.split("#");
						 for(String part:parts)
							{
								
								String t[]=part.split(",");
							 	Date d= new Date();
							 	
							 	SQLQuery chkdata=session.createSQLQuery(checkdata);
							 	chkdata.setBigInteger("pid",BigInteger.valueOf(Integer.parseInt(t[1])));
							 	Integer record = Integer.parseInt(chkdata.list().get(0).toString());
							 	if(record>0)
							 	{
								   query1 = session.createSQLQuery(deleteWSsql);
								   query1.setBigInteger("pid",BigInteger.valueOf(Integer.parseInt(t[1])));
								   query1.executeUpdate();
							 	}
							 	
							    SQLQuery query = session.createSQLQuery(savesql);
								    query.setInteger("wcid",Integer.parseInt(t[0]));      
								    query.setBigInteger("pid",BigInteger.valueOf(Integer.parseInt(t[1])));
									query.setBoolean("status", false);
									query.setString("createdby", loginId);
									query.setDate("createddt", d);
									query.setString("lastupdatedby", loginId);
								    query.setDate("lastupdateddate", d);
									query.setString("requestid",ipadd);
									 value=query.executeUpdate();
								  
							}
					 
						 if(value>0) {
							  res=true;
						}else {
							session.getTransaction().rollback();
							return false;
						}
					 
					 if(res)
							session.getTransaction().commit();
						else
							session.getTransaction().rollback();
				}

				catch(Exception ex) {
				      //System.out.print(ex.getStackTrace()[0].getLineNumber());
					ex.printStackTrace();
					session.getTransaction().rollback();
				}finally {
				
				}

			
				return res;
	}

	@Override
	public List<ProjectLocationBean> getPiaAssigndraftWc(Integer pcode) {
List<ProjectLocationBean> getPiaAssigndraftWc=new ArrayList<ProjectLocationBean>();
		
		String hql=getdraftPrjforWC;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pcode", pcode);
			query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			getPiaAssigndraftWc = query.list();
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
        return getPiaAssigndraftWc;
	}

	@Override
	public Boolean detPiaAssigndraftWc(Integer pwccode) {
		
		String hql=deldraftPrjforWC;
		Integer value=0;
		Boolean res=false;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			{
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pwccode", pwccode);
			 value=query.executeUpdate();
			
			
		} 
		if(value>0) {
			  res=true;
		}else {
			session.getTransaction().rollback();
			return false;
		}
		 if(res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();
		}
		
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
        return res;
	}

	@Override
	public Boolean completeWCMapping(String projwcid, String loginId, Integer projid) {
		Boolean res=false;
		Integer value=0;
		String hql=completeWCmap;
	//	String hql1=deletewcaftercompleteLoc;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			session.beginTransaction();
			String parts[] = projwcid.split("#");
			for(String part:parts)
			{
				 Date d= new Date();
				 SQLQuery query = session.createSQLQuery(hql);
				 query.setString("lastupdatedby", loginId);
				 query.setDate("lastupdateddate", d);
				 query.setBoolean("status", true);
				 query.setBigInteger("pwcid",BigInteger.valueOf(Integer.parseInt(part)));
				 value=query.executeUpdate();
			}
			int k=0;
			List<Integer> wcid = new ArrayList<Integer>();
			for(String ar:parts) 
			{
				 wcid.add(Integer.parseInt(ar));
			}
			/*
			 * if(parts!=null && projid!=null && value!=0) { SQLQuery query1 =
			 * session.createSQLQuery(hql1); query1.setParameterList("wcid", wcid);
			 * query1.setInteger("projId", projid); k=query1.executeUpdate(); }
			 */
			if(value>0) {
				  res=true;
			}else {
				session.getTransaction().rollback();
				return false;
			}
		 
			if(res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();
	}
	catch(Exception ex) 
	{
		session.getTransaction().rollback();
		ex.printStackTrace();
	}
	finally {
	
	}

	return res;
}

	@Override
	public List<ProjectLocationBean> getcheckWCStatus(Integer projectId) {
		List<ProjectLocationBean> getcheckWCStatus=new ArrayList<ProjectLocationBean>();
		
		String hql=checkWCStatus;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("projectId", projectId);
			query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			getcheckWCStatus = query.list();
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
        return getcheckWCStatus;
	}

	@Override
	public List<ProjectLocationBean> getPiaAssignfinalWc(Integer pcode) 
	{
		List<ProjectLocationBean> getPiaAssignfinalWc=new ArrayList<ProjectLocationBean>();
		String hql=getfinalPrjforWC;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("pcode", pcode);
			query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			getPiaAssignfinalWc = query.list();
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
        return getPiaAssignfinalWc;
	}

	@Override
	public List<ProjectLocationBean> getProjectLocationList(Integer state, Integer district, Integer project) 
	{
		List<ProjectLocationBean> result=new ArrayList<ProjectLocationBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql =prjctLocDetail;
				query = session.createSQLQuery(hql);
				query.setInteger("state",state);
				query.setInteger("dist", district);
				query.setInteger("proj", project);
				query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
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
		}
		return result;
	}

	@Override
	public List<ProjectLocationBean> getLocationAndBaselineDraft(Integer st_code, String userType) {

		List<ProjectLocationBean> result=new ArrayList<ProjectLocationBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				if(userType.equals("DI")) {
					
					hql =getLocationAndBaselineDistrictDraft;
					query = session.createSQLQuery(hql);
					query.setInteger("distcd",st_code);
				}
				else {
					hql =getLocationAndBaselineDraft;
					query = session.createSQLQuery(hql);
					query.setInteger("states",st_code);
				}
				query.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
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
		}
		return result;
	}
}
