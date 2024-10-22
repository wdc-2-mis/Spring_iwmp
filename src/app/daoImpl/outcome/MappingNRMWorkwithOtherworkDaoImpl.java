package app.daoImpl.outcome;

import java.math.BigInteger;
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

import app.bean.BaselineUpdateAchievementBean;
import app.bean.menu.MappingNRMWorkwithOtherworkBean;
import app.dao.outcomes.MappingNRMWorkwithOtherworkDao;
import app.model.IwmpProjectAssetStatus;
import app.model.WdcpmksyProjectAssetEPAStatus;
import app.model.WdcpmksyProjectAssetLivelihoodStatus;
import app.model.WdcpmksyProjectAssetProductionStatus;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.project.IwmpProjectLocation;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyMappingNRMWorkOtherWork;
import app.model.project.WdcpmksyPrdouctionWorkid;

@Repository("MappingNRMWorkwithOtherworkDao")
public class MappingNRMWorkwithOtherworkDaoImpl implements MappingNRMWorkwithOtherworkDao{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Value("${getMappingNRMWorkwithOtherwor}")
	String getMappingNRMWorkwithOtherwor; 
	
	@Value("${insertnrmworkotherwork}")
	String insertnrmworkotherwork;
	
	@Value("${getepanrmstatusmapp}")
	String getepanrmstatusmapp;
	
	@Value("${getlivelihnrmstatusmapp}")
	String getlivelihnrmstatusmapp;
	
	@Value("${getprodnrmstatusmapp}")
	String getprodnrmstatusmapp;

	@Value("${getCompletedNRMRelatedOtherWorkEPA}")
	String getCompletedNRMRelatedOtherWorkEPA;
	
	@Value("${getCompletedNRMRelatedOtherWorkLivelihood}")
	String getCompletedNRMRelatedOtherWorkLivelihood;
	
	@Value("${getCompletedNRMRelatedOtherWorkProduction}")
	String getCompletedNRMRelatedOtherWorkProduction;
	
	@Value("${getMappingNRMWorkwithOtherworid}")
	String getMappingNRMWorkwithOtherworid;
	
	@Override
	public List<IwmpProjectPhysicalAsset> getassetWorkWiseList(Integer pCode, String userId, Integer fYear, Integer head,
			Integer activity, Integer nrmwork) {
		
		List<IwmpProjectPhysicalAsset> list=new ArrayList<IwmpProjectPhysicalAsset>();
		String hql=null;
		
		Session session = sessionFactory.getCurrentSession();
		//Transaction tx = null;
		try {
			
			session.beginTransaction();
			
			if(nrmwork>0) {
				hql=getMappingNRMWorkwithOtherworid;
			}
			else {
				hql=getMappingNRMWorkwithOtherwor;
			}
			
			
			Query query = session.createQuery(hql);
			if(nrmwork>0) {
				query.setInteger("nrmwork", nrmwork);
			}
			else {
				  query.setInteger("activity", activity);		
			      query.setInteger("pCode", pCode);
			      query.setInteger("fYear", fYear);
			      query.setInteger("head", head);
			}
			list = query.list();
			
		    
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public LinkedHashMap<Integer, String> getOtherHeadActivity(String headtype, Integer workid) {
		
		List<MEpaCoreactivity> list=new ArrayList<MEpaCoreactivity>();
		List<MLivelihoodCoreactivity> list1=new ArrayList<MLivelihoodCoreactivity>();
		List<MProductivityCoreactivity> list2=new ArrayList<MProductivityCoreactivity>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			if(headtype.equals("e")) 
			{
				SQLQuery chkdata=session.createSQLQuery("select distinct vcode from iwmp_project_physical_asset where assetid=:work");
				chkdata.setInteger("work", workid);
				Integer vcode = Integer.parseInt(chkdata.list().get(0).toString());
				
			//	SQLQuery chkdataa=session.createSQLQuery("select distinct epa_activity_id from public.wdcpmksy_epa_workid where vcode=:vill");
			//	chkdataa.setInteger("vill", vcode);
			//	Integer act = Integer.parseInt(chkdataa.list().get(0).toString());
				
				Query query = session.createQuery("from MEpaCoreactivity where epaActivityId in(select MEpaCoreactivity.epaActivityId from WdcpmksyEpaWorkid where iwmpVillage.vcode=:vill) order by epaDesc"); 
				query.setInteger("vill", vcode);
				list = query.list();
				for(MEpaCoreactivity pl : list) {
					if (!map.containsKey(pl.getEpaActivityId())) {
						map.put(pl.getEpaActivityId(), pl.getEpaDesc());
					} else {
						
					}
				}
			}
			if(headtype.equals("l")) 
			{
				SQLQuery chkdata=session.createSQLQuery("select distinct vcode from iwmp_project_physical_asset where assetid=:work");
				chkdata.setInteger("work", workid);
				Integer vcode = Integer.parseInt(chkdata.list().get(0).toString());
				
				//SQLQuery chkdataa=session.createSQLQuery("select distinct livelihood_coreactivity_id from wdcpmksy_livelihood_workid where vcode=:vill");
			//	chkdataa.setInteger("vill", vcode);
			//	Integer act = Integer.parseInt(chkdataa.list().get(0).toString());
				
				Query query = session.createQuery("from MLivelihoodCoreactivity where livelihoodCoreactivityId in(select MLivelihoodCoreactivity.livelihoodCoreactivityId from WdcpmksyLivelihoodWorkid where iwmpVillage.vcode=:vill) order by livelihoodCoreactivityDesc");
				query.setInteger("vill", vcode);
				list1 = query.list();
				for(MLivelihoodCoreactivity pl : list1) {
					if (!map.containsKey(pl.getLivelihoodCoreactivityId())) {
						map.put(pl.getLivelihoodCoreactivityId(), pl.getLivelihoodCoreactivityDesc());
					} else {
						
					}
				}
			}
			if(headtype.equals("p")) 
			{
				SQLQuery chkdata=session.createSQLQuery("select distinct vcode from iwmp_project_physical_asset where assetid=:work");
				chkdata.setInteger("work", workid);
				Integer vcode = Integer.parseInt(chkdata.list().get(0).toString());
				
			//	SQLQuery chkdataa=session.createSQLQuery("select distinct productivity_coreactivity_id from public.wdcpmksy_prdouction_workid where vcode=:vill");
			//	chkdataa.setInteger("vill", vcode);
			//	Integer act = Integer.parseInt(chkdataa.list().get(0).toString());
				
				Query query = session.createQuery("from MProductivityCoreactivity where productivityCoreactivityId in(select MProductivityCoreactivity.productivityCoreactivityId from WdcpmksyPrdouctionWorkid where iwmpVillage.vcode=:vill) order by productivityCoreactivityDesc"); 
				query.setInteger("vill", vcode);
				list2 = query.list();
				for(MProductivityCoreactivity pl : list2) {
					if (!map.containsKey(pl.getProductivityCoreactivityId())) {
						map.put(pl.getProductivityCoreactivityId(), pl.getProductivityCoreactivityDesc());
					} else {
						
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
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getOtherHeadActivityWork(String headtyp, Integer acttypes, Integer proj, Integer assetid) {
		
		List<WdcpmksyEpaWorkid> list=new ArrayList<WdcpmksyEpaWorkid>();
		List<WdcpmksyLivelihoodWorkid> list1=new ArrayList<WdcpmksyLivelihoodWorkid>();
		List<WdcpmksyPrdouctionWorkid> list2=new ArrayList<WdcpmksyPrdouctionWorkid>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Integer vcode =0;
			
			if(proj==0) {
				
				SQLQuery chkdata=session.createSQLQuery("select proj_id from iwmp_project_physical_asset where assetid=:work");
				chkdata.setInteger("work", assetid);
				proj = Integer.parseInt(chkdata.list().get(0).toString());
				
			}
			SQLQuery chkdata=session.createSQLQuery("select vcode from iwmp_project_physical_asset where assetid=:work");
			chkdata.setInteger("work", assetid);
			vcode = Integer.parseInt(chkdata.list().get(0).toString());
			
			if(headtyp.equals("e")) 
			{
				Query query = session.createQuery(getepanrmstatusmapp);
				query.setInteger("pCode", proj);
				query.setInteger("activity", acttypes);
				query.setInteger("villag", vcode);
				list = query.list();
				for(WdcpmksyEpaWorkid pl : list) 
				{
					if (!map.containsKey(pl.getAssetid())) 
					{
						String distn=pl.getIwmpMProject().getIwmpDistrict().getDistName();
						String block=pl.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName();
						String vill=pl.getIwmpVillage().getVillageName();
						
						map.put(pl.getAssetid(), pl.getAssetid()+" ( "+distn+", "+block+", "+vill+" )");
					} 
					else {
						
					}
				}
			}
			if(headtyp.equals("l")) 
			{ 
			  //Query query = session.createQuery("from WdcpmksyProjectAssetLivelihoodStatus where iwmpMProject.projectId=(case :pCode when 0 then pa.iwmpMProject.projectId else :pCode end ) and wdcpmksyLivelihoodWorkid.MLivelihoodCoreactivity.livelihoodCoreactivityId=:activity and status='C'"); 
				Query query = session.createQuery(getlivelihnrmstatusmapp);
				query.setInteger("pCode", proj);
				query.setInteger("activity", acttypes);
				query.setInteger("villag", vcode);
				list1 = query.list();
				for(WdcpmksyLivelihoodWorkid pl : list1) {
					if (!map.containsKey(pl.getAssetid())) 
					{
						String distn=pl.getIwmpMProject().getIwmpDistrict().getDistName();
						String block=pl.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName();
						String vill=pl.getIwmpVillage().getVillageName();
						
						map.put(pl.getAssetid(), pl.getAssetid()+" ( "+distn+", "+block+", "+vill+" )");
					} 
					else {
						
					}
				}
			}
			if(headtyp.equals("p")) 
			{ 
				//Query query = session.createQuery("from WdcpmksyProjectAssetProductionStatus where iwmpMProject.projectId=(case :pCode when 0 then pa.iwmpMProject.projectId else :pCode end ) and wdcpmksyPrdouctionWorkid.MProductivityCoreactivity.productivityCoreactivityId=:activity and status='C'"); 
				Query query = session.createQuery(getprodnrmstatusmapp);
				query.setInteger("pCode", proj);
				query.setInteger("activity", acttypes);
				query.setInteger("villag", vcode);
				list2 = query.list();
				for(WdcpmksyPrdouctionWorkid pl : list2) {
					if (!map.containsKey(pl.getAssetid())) 
					{
						String distn=pl.getIwmpMProject().getIwmpDistrict().getDistName();
						String block=pl.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName();
						String vill=pl.getIwmpVillage().getVillageName();
						
						map.put(pl.getAssetid(), pl.getAssetid()+" ( "+distn+", "+block+", "+vill+" )");
					} 
					else {
						
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
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return map;
	}

	@Override
	public String checkNRMandOtherWorkMatch(String headtyp, Integer otherwork, Integer proj, Integer nrmwork) {
		String str="fail";
		
		Session session = sessionFactory.getCurrentSession();
		Integer nrmv=0;
		Integer prov=0;
		Integer livv=0;
		Integer epav=0;
		try {
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery("select vcode from iwmp_project_physical_asset  where assetid=:nrmwork");
			query.setInteger("nrmwork", nrmwork); 
			nrmv=Integer.parseInt(query.list().get(0).toString());
			
			if(headtyp.equals("p")) 
			{
				SQLQuery query1 = session.createSQLQuery("select vcode from wdcpmksy_prdouction_workid  where assetid=:otherwork");
				query1.setInteger("otherwork", otherwork); 
				prov=Integer.parseInt(query1.list().get(0).toString());
				
				if(nrmv.equals(prov))
					str="success";
				else
					str="fail";
				
			}
			
			if(headtyp.equals("l")) 
			{
				SQLQuery query1 = session.createSQLQuery("select vcode from wdcpmksy_livelihood_workid  where assetid=:otherwork");
				query1.setInteger("otherwork", otherwork); 
				livv=Integer.parseInt(query1.list().get(0).toString());
				
				if(nrmv.equals(livv))
					str="success";
				else
					str="fail";
				
			}
			if(headtyp.equals("e")) 
			{
				SQLQuery query1 = session.createSQLQuery("select vcode from wdcpmksy_epa_workid  where assetid=:otherwork");
				query1.setInteger("otherwork", otherwork); 
				epav=Integer.parseInt(query1.list().get(0).toString());
				
				if(nrmv.equals(epav))
					str="success";
				else
					str="fail";
				
			}
			
			
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return str;
	}

	@Override
	public String saveNRMwithOtherAsset(List<BigInteger> assetid, Integer projcd, String userid, List<String> headtype,
			List<Integer> otherwork) {
		String sql=null;
		String str="fail";
		Integer nrmv=0;
		Integer prov=0;
		Integer livv=0;
		Integer epav=0;
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 sql=insertnrmworkotherwork;
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 
			 if(projcd==0) {
				 for(int i=0;i<assetid.size(); i++)
				 {
					
					SQLQuery chkdata=session.createSQLQuery("select proj_id from iwmp_project_physical_asset where assetid=:work");
					chkdata.setInteger("work", assetid.get(i).intValue());
					projcd = Integer.parseInt(chkdata.list().get(0).toString());
					
				}
			 } 
			 
			 SQLQuery query = session.createSQLQuery(sql);
			 Date d= new Date();
			 
			 for(int i=0;i<assetid.size(); i++)
			 {
				// assetid.get(i);
				// headtype.get(i);
				// otherwork.get(i);
				 query.setInteger("nrm", assetid.get(i).intValue());
				 query.setInteger("other",otherwork.get(i));
				 query.setString("head", headtype.get(i));
				 query.setInteger("pcode",projcd); 
				 query.setBoolean("status", false);
				 query.setString("createdby", userid);
				 query.setDate("createddt", d);
				 query.setString("requestid",ipadd);
				 value=query.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
			 }
			 
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			str="fail";
			ex.printStackTrace();
		//	session.getTransaction().rollback();
			return str;
		}
		finally {
			session.getTransaction().commit();
		}
		
		return str;
	}

	@Override
	public List<WdcpmksyMappingNRMWorkOtherWork> getmappingNRMWorkDraft(Integer pCode) {
		
		List<WdcpmksyMappingNRMWorkOtherWork> list=new ArrayList<WdcpmksyMappingNRMWorkOtherWork>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery("from WdcpmksyMappingNRMWorkOtherWork pa where pa.iwmpMProject.projectId=:pCode and pa.status=false");
			    query.setInteger("pCode", pCode);
				list = query.list();
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		finally {
			session.getTransaction().commit();
		}
		return list;
	}

	@Override
	public String deleteNRMwithOtherAsset(List<Integer> assetid) {
		// TODO Auto-generated method stub
		String sql=null;
		String str="fail";
		Integer nrmv=0;
		Integer prov=0;
		Integer livv=0;
		Integer epav=0;
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 sql=insertnrmworkotherwork;
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("delete from wdcpmksy_mapping_nrmwork_otherwork where nrmwork_otherwork_id=:nrmpkid");
			 Date d= new Date();
			 
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query.setInteger("nrmpkid", assetid.get(i));
				 value=query.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
			 }
			 
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.getTransaction().commit();
		}
		
		return str;
	}

	@Override
	public String completeNRMwithOtherAsset(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		String sql=null;
		String str="fail";
		Integer nrmv=0;
		Integer prov=0;
		Integer livv=0;
		Integer epav=0;
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 sql=insertnrmworkotherwork;
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("update wdcpmksy_mapping_nrmwork_otherwork set status=true, last_updated_by=:updated, last_updated_date=:datee where nrmwork_otherwork_id=:nrmpkid");
			 Date d= new Date();
			 
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query.setInteger("nrmpkid", assetid.get(i));
				 query.setString("updated", userid);
				 query.setDate("datee", d);
				 value=query.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
			 }
			 
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.getTransaction().commit();
		}
		
		return str;
	}

	@Override
	public List<MappingNRMWorkwithOtherworkBean> getCompletedNRMRelatedOtherWork(String scheme, Integer projcd) {
		// TODO Auto-generated method stub
		
		List<MappingNRMWorkwithOtherworkBean> list = new ArrayList<MappingNRMWorkwithOtherworkBean>();
		MappingNRMWorkwithOtherworkBean  tranxBean = new MappingNRMWorkwithOtherworkBean();
		SQLQuery query =null;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			if(scheme.equals("e")) 
				query = session.createSQLQuery(getCompletedNRMRelatedOtherWorkEPA);
			
			if(scheme.equals("p")) 
				query = session.createSQLQuery(getCompletedNRMRelatedOtherWorkProduction);
			
			if(scheme.equals("l")) 
				query = session.createSQLQuery(getCompletedNRMRelatedOtherWorkLivelihood);
			
			query.setInteger("projid",projcd);
			query.setResultTransformer(Transformers.aliasToBean(MappingNRMWorkwithOtherworkBean.class));
			list = query.list();
			
			
			 
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
		
		}
		return list;
	}

	@Override
	public String checkNRMValidORNot(Integer nrmwork, Integer regid) {
		
		String str="fail";
		
		Session session = sessionFactory.getCurrentSession();
		Integer nrmv=0;
		Integer prov=0;
		Integer livv=0;
		Integer epav=0;
		try {
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery("select distinct reg_id from iwmp_user_project_map where proj_id in(select proj_id from iwmp_project_physical_asset  where assetid=:nrmwork)");
			query.setInteger("nrmwork", nrmwork); 
			if(query.list().isEmpty()) {
				str="fail";
			}
			else {
				nrmv=Integer.parseInt(query.list().get(0).toString());
				if(regid.equals(nrmv))
						str="success";
				else 
					str="fail";
			}
			
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			str="fail";
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.getTransaction().commit();
		}
		
		return str;
	}

	@Override
	public List<WdcpmksyMappingNRMWorkOtherWork> getWorkIdWiseNRMWorkDraft(Integer nrmwork) {
		
			List<WdcpmksyMappingNRMWorkOtherWork> list=new ArrayList<WdcpmksyMappingNRMWorkOtherWork>();
			Session session = sessionFactory.getCurrentSession();
			
			try {
					session.beginTransaction();
					Query query = session.createQuery("from WdcpmksyMappingNRMWorkOtherWork pa where pa.iwmpProjectPhysicalAsset.assetid=:nrmwork and pa.status=false");
				    query.setInteger("nrmwork", nrmwork);
					list = query.list();
			}
			catch (HibernateException e) {
				System.err.print("Hibernate error");
				e.printStackTrace();
				session.getTransaction().rollback();
			} 
			finally {
				session.getTransaction().commit();
			}
			return list;
	}

}
