package app.daoImpl.master;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import app.bean.ProjectLocationBean;
import app.dao.master.AddExistingProjectLocationDao;
import app.model.IwmpUserProjectMap;
import app.model.master.IwmpVillage;

@Repository("AddExistingProjectLocationDao")
public class AddExistingProjectLocationDaoImpl implements AddExistingProjectLocationDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getprojectlocationexist}")
	String getprojectlocationexist;
	
	@Value("${getVillageBlockProjWise}")
	String getVillageBlockProjWise;
	
	@Value("${saveProjectLocationAsDraft}")
	String saveProjectLocationAsDraft;
	
	@Value("${getVillagePrjforWCExisting}")
	String getVillagePrjforWCExisting ;
	
	@Value("${saveWCLocationAsDraft}")
	String saveWCLocationAsDraft;
	
	@Value("${delWCLocationAsDraftExisting}")
	String delWCLocationAsDraftExisting;
	
	@Value("${checkWCLocationAsDraftExisting}")
	String checkWCLocationAsDraftExisting;
	
	@Value("${completeWCmapExisting}")
	String completeWCmapExisting;
	
	@Value("${getVillagePrjforWCExistingComma}")
	String getVillagePrjforWCExistingComma;
	
	@Value("${checkWCLocationDraft}")
	String checkWCLocationDraft;
	
	@Value("${getVillagePrjforWC}")
	String getVillagePrjforWC;
	
	@Value("${getVillagePrjforWCfirst}")
	String getVillagePrjforWCfirst;
	
	@Override
	public LinkedHashMap<Integer, String> getProjectLocationProject(Integer regId) {
		
		Integer registrationId = regId;
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<IwmpUserProjectMap> rows = new ArrayList<IwmpUserProjectMap>();
		String hql=getprojectlocationexist;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("regId",registrationId);
			
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
			}
			
			
			
			/*
			 * rows = query.list(); for(IwmpUserProjectMap row : rows){
			 * System.out.println("Size by regId: "+row.getIwmpMProject().getProjectCd()
			 * +" regId "+regId); map.put(row.getIwmpMProject().getProjectId(),
			 * row.getIwmpMProject().getProjName()); }
			 */
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getVillageBlockWise(Integer bcode, Integer project) {
		// TODO Auto-generated method stub
		List<IwmpVillage> villList = new ArrayList<IwmpVillage>();
		String hql = getVillageBlockProjWise;
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("block", bcode);
			//query.setInteger("proj", project);
			villList = query.list();
		//	int a=villList.size();
			for (IwmpVillage vill : villList) {
				map.put(vill.getVcode(), vill.getVillageName() + " ( "+vill.getIwmpGramPanchayat().getGramPanchayatName()+" )");
			//	System.out.println( vill.getVcode()+" "+vill.getVillageName()+ " ("+vill.getIwmpGramPanchayat().getGramPanchayatName()+" )");
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return map;
	}

	@Override
	public Boolean saveProjectLocationAsDraft(List<Integer> vCode, Integer pcode, String loginId) {
		// TODO Auto-generated method stub
		Boolean res=false;
		Integer value=0;
		Object[] ob = vCode.toArray();
		Integer vc[] = new Integer[vCode.size()];
		String savesql=saveProjectLocationAsDraft;
	//	String deletesql=deletePreFilledProjectLocation;
	//	String check=checkProjectLocationExist;
		Session session = sessionFactory.getCurrentSession();
		List<ProjectLocationBean> villageList=new ArrayList<ProjectLocationBean>();	
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			for(int i =0; i<ob.length;i++) {
				vc[i]=Integer.parseInt(ob[i].toString());
			}
			session.beginTransaction();
			/*
			 * SQLQuery queryk = session.createSQLQuery(check);
			 * queryk.setInteger("pcode",pcode); Integer tranxCount =
			 * Integer.parseInt(queryk.list().get(0).toString()); if (tranxCount > 0) {
			 * SQLQuery query1 = session.createSQLQuery(deletesql);
			 * query1.setInteger("pcode",pcode); Integer a=query1.executeUpdate(); }
			 */	 
			for(int i=0;i<vCode.size(); i++)
			{
				for(int j=i+1;j<vCode.size(); j++)
				if(vCode.get(i)==vCode.get(j)) {
				//	System.out.println(vCode.get(i)+" kdy "+vCode.get(j));
					vCode.remove(i);
				}
			}	
			List<Integer> village=new ArrayList<Integer>();
			SQLQuery query1 = session.createSQLQuery("select vcode from iwmp_project_location where proj_id=:pcode and status=false");
			query1.setInteger("pcode", pcode);
			query1.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
			villageList = query1.list();
			for(int k=0; k<villageList.size(); k++) {
				//System.out.println("kedar ="+villageList.get(k).getVcode());
				village.add(villageList.get(k).getVcode());
			}	
				
			
			for(int j=0;j<village.size(); j++)
			{
				for(int i=0;i<vCode.size(); i++) 
				{
					if(vCode.get(i).equals(village.get(j))) {
					//	System.out.println(vCode.get(i)+" kdy "+vCode.get(j));
						vCode.remove(i);
					}
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
	public List<ProjectLocationBean> getPiaAssignWc(Integer pcode) {
		
		List<ProjectLocationBean> getPiaAssignWc=new ArrayList<ProjectLocationBean>();
		//String hql=getVillagePrjforWC;
		String checkdataexist=checkWCLocationAsDraftExisting;
		String checkdata = getVillagePrjforWCExisting;
		String checkdatacomma = getVillagePrjforWCExistingComma;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			
			SQLQuery chkdata=session.createSQLQuery(checkdataexist);
			chkdata.setInteger("projid", pcode);
			Integer record = Integer.parseInt(chkdata.list().get(0).toString());
			if(record==0) {
				   SQLQuery query1 = session.createSQLQuery(checkdata);
				   query1.setInteger("pcode", pcode);
				   query1.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
				   getPiaAssignWc = query1.list();
			}	
			else {
				   SQLQuery query1 = session.createSQLQuery(checkdata);
				   query1.setInteger("pcode", pcode);
				   query1.setResultTransformer(Transformers.aliasToBean(ProjectLocationBean.class));
				   getPiaAssignWc = query1.list();
				
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

	@Override
	public Boolean saveWCLocationAsDraft(List<String> activity, List<Integer> plid, String loginId, Integer project) {
		// TODO Auto-generated method stub
		Boolean res=false;
		Integer value=0;
		Integer value1=0;
		SQLQuery query1;
		Integer chkvalue=0;
		String savesql=saveWCLocationAsDraft;
		String deleteWSsql=delWCLocationAsDraftExisting;
		String checkdata=checkWCLocationAsDraftExisting;
		Session session = sessionFactory.getCurrentSession();
		
		try {
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			session.beginTransaction();
			Date d= new Date();
			
			SQLQuery chkdata=session.createSQLQuery(checkdata);
			chkdata.setInteger("projid", project);
			Integer record = Integer.parseInt(chkdata.list().get(0).toString());
			
			if(record>0) 
			{ 
				query1 = session.createSQLQuery(deleteWSsql);
				query1.setInteger("projid", project);
				value1=query1.executeUpdate(); 
			 }
			
			if(value1>=0)
			for(int i=0;i<plid.size();i++) 
			{
				int k=plid.get(i);
				String act[] = activity.get(i).split("\\$");
				for(String s : act) {
				
					SQLQuery query = session.createSQLQuery(savesql);
				    query.setInteger("wcid",Integer.parseInt(s));      
				    query.setInteger("pid",k);
					query.setBoolean("status", false);
					query.setString("createdby", loginId);
					query.setDate("createddt", d);
					query.setString("lastupdatedby", loginId);
				    query.setDate("lastupdateddate", d);
					query.setString("requestid",ipadd);
					value=query.executeUpdate();
				}
			}
			
			
			/*
			 * 
			 * 
			 * String parts[] = final1.split("#"); for(String part:parts) {
			 * 
			 * String t[]=part.split(","); Date d= new Date();
			 * 
			 * SQLQuery chkdata=session.createSQLQuery(checkdata);
			 * chkdata.setBigInteger("pid",BigInteger.valueOf(Integer.parseInt(t[1])));
			 * Integer record = Integer.parseInt(chkdata.list().get(0).toString());
			 * if(record>0) { query1 = session.createSQLQuery(deleteWSsql);
			 * query1.setBigInteger("pid",BigInteger.valueOf(Integer.parseInt(t[1])));
			 * query1.executeUpdate(); }
			 * 
			 * SQLQuery query = session.createSQLQuery(savesql);
			 * query.setInteger("wcid",Integer.parseInt(t[0]));
			 * query.setBigInteger("pid",BigInteger.valueOf(Integer.parseInt(t[1])));
			 * query.setBoolean("status", false); query.setString("createdby", loginId);
			 * query.setDate("createddt", d); query.setString("lastupdatedby", loginId);
			 * query.setDate("lastupdateddate", d); query.setString("requestid",ipadd);
			 * value=query.executeUpdate();
			 * 
			 * }
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

		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}

	
		return res;
	}

	@Override
	public Boolean completeWCMappingExisting(String loginId, Integer projid) {
		// TODO Auto-generated method stub
		Boolean res=false;
		Integer value=0;
		String hql=completeWCmapExisting;
	//	String hql1=deletewcaftercompleteLoc;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost(); 
			String ipadd=inetAddress.getHostAddress(); 
			session.beginTransaction();
		//	String parts[] = projwcid.split("#");
		//	for(String part:parts)
		///	{
				 Date d= new Date();
				 SQLQuery query = session.createSQLQuery(hql);
				 query.setString("lastupdatedby", loginId);
				 query.setDate("lastupdateddate", d);
				 query.setBoolean("status", true);
				 query.setInteger("projId", projid);
				 value=query.executeUpdate();
		//	}
		//	int k=0;
		//	List<Integer> wcid = new ArrayList<Integer>();
		//	for(String ar:parts) 
		//	{
		//		 wcid.add(Integer.parseInt(ar));
		//	}
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
	public List<ProjectLocationBean> getPiaAssignWcFirst(Integer pcode) {
		// TODO Auto-generated method stub
List<ProjectLocationBean> getPiaAssignWc=new ArrayList<ProjectLocationBean>();
		
		String hql=getVillagePrjforWC;
		String checkdata = getVillagePrjforWCfirst;
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			
			String data = session.createNativeQuery(checkWCLocationDraft).setParameter("pid", pcode).list().get(0).toString();
			Integer record = Integer.parseInt(data);	
		//	System.out.println("check record data:" +record);
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

}
