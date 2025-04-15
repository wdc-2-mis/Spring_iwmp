package app.janbhagidariPratiyogita;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpMPhyHeads;
import app.model.master.IwmpVillage;
import app.watershedyatra.model.WatershedYatVill;
import app.watershedyatra.model.WatershedYatraInauguaration;

@Repository("JanbhagidariPratiyogitaDao")
public class JanbhagidariPratiyogitaDaoImpl implements JanbhagidariPratiyogitaDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${projlistListJanbhagidari}")
	String projlistListJanbhagidari;
	
	@Value("${getJanbhagidariPratiyogitaProjectData}")
	String getJanbhagidariPratiyogitaProjectData;
	
	@Value("${getJanbhagidariPratiyogitaDraftList}")
	String getJanbhagidariPratiyogitaDraftList;
	
	@Value("${getJanbhagidariPratiyogitaCompleteList}")
	String getJanbhagidariPratiyogitaCompleteList;
	
	@Override
	public LinkedHashMap<String, Integer> getJanbhagidariPratiyogitaProject(Integer distcd) {
		
		List<IwmpMProject> pjList=new ArrayList<IwmpMProject>();
		String hql=projlistListJanbhagidari;
		LinkedHashMap<String, Integer> pjMap=new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("distcode", distcd);
			pjList = query.list();
			
			for (IwmpMProject pj : pjList) {
				pjMap.put( pj.getProjName(), pj.getProjectId());
			//	System.out.println(district.getDcode()+" k "+district.getDistName());
			}
			//session.getTransaction().commit();
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
		finally {
			session.getTransaction().commit();
		}
        return pjMap;
	}

	@Override
	public List<JanbhagidariPratiyogitaBean> getJanbhagidariPratiyogitaProjectData(Integer project) {
		
		List<JanbhagidariPratiyogitaBean> result=new ArrayList<JanbhagidariPratiyogitaBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
			 // query = session.createSQLQuery(hql);    getGroundWaterTable
				hql=getJanbhagidariPratiyogitaProjectData;
				query = session.createSQLQuery(hql);
				query.setInteger("projectId", project);
				query.setResultTransformer(Transformers.aliasToBean(JanbhagidariPratiyogitaBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			 // session.flush(); session.close();
		}
		return result;
	}

	@Override
	public String saveJanbhagidariPratiyogita(List<String> vill, List<String> ngoname, int dcode, int proj,
	        int nogp, int novillage, String projarea, String projoutlay, int funoutlay,
	        String projexp, String expper, String swckgp, HttpSession session) {

	    Session sess = sessionFactory.getCurrentSession();
	    String res = "fail";
	    try {
	        sess.beginTransaction();

	        InetAddress inet = InetAddress.getLocalHost();
	        String ipAddr = inet.getHostAddress();

	        // Duplicate NGO Check
	        List<String> existingNGOs = sess.createQuery("SELECT UPPER(TRIM(ngo_name)) FROM JanbhagidariPratiyogitaNgoname WHERE janbhagidariPratiyogita.iwmpMProject.projectId = :prCode").setInteger("prCode", proj).list();

	        for (String existingNgo : existingNGOs) {
	            for (String inputNgo : ngoname) {
	                if (existingNgo.equalsIgnoreCase(inputNgo.trim())) {
	                    return "duplicate";
	                }
	            }
	        }

	        // Build main object
	        JanbhagidariPratiyogita data = new JanbhagidariPratiyogita();
	        IwmpState state = new IwmpState();
	        state.setStCode(Integer.parseInt(session.getAttribute("stateCode").toString()));
	        IwmpDistrict district = new IwmpDistrict();
	        district.setDcode(dcode);
	        IwmpMProject project = new IwmpMProject();
	        project.setProjectId(proj);

	        data.setIwmpState(state);
	        data.setIwmpDistrict(district);
	        data.setIwmpMProject(project);
	        data.setNo_gp(nogp);
	        data.setNo_village(novillage);
	        data.setProj_area(new BigDecimal(projarea));
	        data.setProj_outlay(new BigDecimal(projoutlay));
	        data.setFund_outlay(funoutlay);
	        data.setFund_expenditure(new BigDecimal(projexp));
	        data.setFund_per_exp(new BigDecimal(expper));
	        data.setCreatedBy(session.getAttribute("loginID").toString());
	        data.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	        data.setUpdatedBy(session.getAttribute("loginID").toString());
	        data.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	        data.setRequestedIp(ipAddr);
	        data.setStatus('D');

	        sess.save(data);

	        // Save SWCK GP Accounts
	        for (String gcode : swckgp.split(",")) {
	            IwmpGramPanchayat gp = new IwmpGramPanchayat();
	            gp.setGcode(Integer.parseInt(gcode.trim()));

	            JanbhagidariPratiyogitaSWCKAccount swck = new JanbhagidariPratiyogitaSWCKAccount();
	            swck.setJanbhagidariPratiyogita(data);
	            swck.setIwmpGramPanchayat(gp);
	            swck.setCreatedBy(session.getAttribute("loginID").toString());
	            swck.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	            swck.setUpdatedBy(session.getAttribute("loginID").toString());
	            swck.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	            swck.setRequestedIp(ipAddr);

	            sess.save(swck);
	        }

	        // Save NGO names and their villages
	        for (int i = 0; i < ngoname.size(); i++) {
	            JanbhagidariPratiyogitaNgoname ngon = new JanbhagidariPratiyogitaNgoname();
	            ngon.setJanbhagidariPratiyogita(data);
	            ngon.setNgo_name(ngoname.get(i).trim());
	            ngon.setCreatedBy(session.getAttribute("loginID").toString());
	            ngon.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	            ngon.setUpdatedBy(session.getAttribute("loginID").toString());
	            ngon.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	            ngon.setRequestedIp(ipAddr);

	            sess.save(ngon);

	            String[] villages = vill.get(i).split("#");
	            for (String vcode : villages) {
	                JanbhagidariPratiyogitaNgovillage ngovill = new JanbhagidariPratiyogitaNgovillage();
	                ngovill.setJanbhagidariPratiyogitaNgoname(ngon);

	                IwmpVillage village = new IwmpVillage();
	                village.setVcode(Integer.parseInt(vcode.trim()));
	                ngovill.setIwmpVillage(village);

	                ngovill.setCreatedBy(session.getAttribute("loginID").toString());
	                ngovill.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	                ngovill.setUpdatedBy(session.getAttribute("loginID").toString());
	                ngovill.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	                ngovill.setRequestedIp(ipAddr);

	                sess.save(ngovill);
	            }
	        }

	        sess.getTransaction().commit();
	        res = "success";

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (sess.getTransaction().isActive()) {
	            sess.getTransaction().rollback();
	        }
	        res = "fail";
	    }

	    return res;
	}

	@Override
	public List<JanbhagidariPratiyogitaBean> getDraftListDetails(Integer stcd) {
		
		List<JanbhagidariPratiyogitaBean> result=new ArrayList<JanbhagidariPratiyogitaBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=getJanbhagidariPratiyogitaDraftList;
				query = session.createSQLQuery(hql);
				query.setInteger("stcode", stcd);
				query.setResultTransformer(Transformers.aliasToBean(JanbhagidariPratiyogitaBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			 // session.flush(); session.close();
		}
		return result;
	}

	@Override
	public List<JanbhagidariPratiyogitaBean> getCompleteListDetails(Integer stcd) {
		
		List<JanbhagidariPratiyogitaBean> result=new ArrayList<JanbhagidariPratiyogitaBean>();
		Session session = sessionFactory.openSession();
		try {
				String hql=null;
				SQLQuery query = null;
			
				@SuppressWarnings("unused")
				Transaction tx = session.beginTransaction(); 
				hql=getJanbhagidariPratiyogitaCompleteList;
				query = session.createSQLQuery(hql);
				query.setInteger("stcode", stcd);
				query.setResultTransformer(Transformers.aliasToBean(JanbhagidariPratiyogitaBean.class));
				result = query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
			 // session.flush(); session.close();
		}
		return result;
	}

	@Override
	public String deleteJanbhagidariPratiyogita(List<Integer> assetid, String userid) {
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();

		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 
			 SQLQuery query1 = session.createSQLQuery("delete from janbhagidari_pratiyogita_ngovillage where ngoname_id in(select ngoname_id from janbhagidari_pratiyogita_ngoname where pratiyogita_id=:nrmpkid)");
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query1.setInteger("nrmpkid", assetid.get(i));
				 query1.executeUpdate();
			 }
			 SQLQuery query2 = session.createSQLQuery("delete from janbhagidari_pratiyogita_ngoname where pratiyogita_id=:nrmpkid");
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query2.setInteger("nrmpkid", assetid.get(i));
				 query2.executeUpdate();
			 }
			 SQLQuery query3 = session.createSQLQuery("delete from janbhagidari_pratiyogita_swck_account where pratiyogita_id=:nrmpkid");
			 for(int i=0;i<assetid.size(); i++)
			 {
				 query3.setInteger("nrmpkid", assetid.get(i));
				 query3.executeUpdate();
			 }
			 SQLQuery query = session.createSQLQuery("delete from janbhagidari_pratiyogita where pratiyogita_id=:nrmpkid");
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
	public String completeJanbhagidariPratiyogita(List<Integer> assetid, String userid) {
		
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();

		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 
			 SQLQuery query = session.createSQLQuery("update janbhagidari_pratiyogita set status='C' where pratiyogita_id=:nrmpkid");
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
	public LinkedHashMap<Integer, String> getGPofJanbhagidariPratiyogita(Integer project) {
		
	//	List<IwmpGramPanchayat> list = new ArrayList<IwmpGramPanchayat>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		List<IwmpVillage> list = new ArrayList<IwmpVillage>();
		String hql="select village from IwmpVillage village where village.vcode in(select distinct iwmpVillage.vcode from IwmpProjectLocation where iwmpMProject.projectId=:proj)";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("proj", project);
			list = query.list();
			for(IwmpVillage vill:list) {
				
				map.put(vill.getIwmpGramPanchayat().getGcode(), vill.getIwmpGramPanchayat().getGramPanchayatName());
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
	public LinkedHashMap<Integer, String> getVILLofJanbhagidariPratiyogita(List<Integer> gcode, Integer projectid) {
		
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		List<IwmpVillage> list = new ArrayList<IwmpVillage>();
		String hql="select village from IwmpVillage village where village.vcode in(select distinct iwmpVillage.vcode from IwmpProjectLocation where iwmpMProject.projectId=:proj) and village.iwmpGramPanchayat.gcode in(:gpCode) order by village.villageName";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("proj", projectid);
			query.setParameter("gpCode", gcode);
			list = query.list();
			for(IwmpVillage vill:list) {
				
				map.put(vill.getVcode(), vill.getVillageName());
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
	public String getExistingProjectCodes(Integer pCode) {
		
		 Session session = sessionFactory.getCurrentSession();
		  int result;
		  String data="fail";
		  try {
		    session.beginTransaction();
			List list = session.createQuery("SELECT iwmpMProject.projectId FROM JanbhagidariPratiyogita where iwmpMProject.projectId=:ProjCode").setInteger("ProjCode", pCode).list();
		//	result=Integer.parseInt(list.get(0).toString());
			if(!list.isEmpty())
				data="success";
		  } 
		  catch (HibernateException e) {
		    System.err.print("Hibernate error");
		    e.printStackTrace();
		    session.getTransaction().rollback();
		  } catch (Exception ex) {
		    session.getTransaction().rollback();
		    ex.printStackTrace();
		  }
		  finally {
			  session.getTransaction().commit();
		  }
		  return data;
	}

	@Override
	public Integer getTotalNoofGP(Integer dCode) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Integer data=0;
		try {
			session.beginTransaction();
			List<Integer> list = session.createSQLQuery("select cast(count(distinct gcode) as integer) from iwmp_gram_panchayat where bcode in(select bcode from iwmp_block where dcode=:distcode)").setInteger("distcode", dCode).list();
			data=Integer.parseInt(list.get(0).toString());
		} 
		catch (HibernateException e) {
		    System.err.print("Hibernate error");
		    e.printStackTrace();
		    session.getTransaction().rollback();
		} 
		catch (Exception ex) {
	    session.getTransaction().rollback();
	    ex.printStackTrace();
	  }
	  finally {
		  session.getTransaction().commit();
	  }
	  return data;
	}

	@Override
	public Integer getTotalNoofVill(Integer dCode) {
		
		Session session = sessionFactory.getCurrentSession();
			
			Integer data=0;
			try {
				session.beginTransaction();
				List<Integer> list = session.createSQLQuery("select cast(count(distinct vcode) as integer) from iwmp_village where gcode in (select gcode  from iwmp_gram_panchayat where bcode in(select bcode from iwmp_block where dcode=:distcode))").setInteger("distcode", dCode).list();
				data=Integer.parseInt(list.get(0).toString());
			} 
			catch (HibernateException e) {
			    System.err.print("Hibernate error");
			    e.printStackTrace();
			    session.getTransaction().rollback();
			} 
			catch (Exception ex) {
		    session.getTransaction().rollback();
		    ex.printStackTrace();
		  }
		  finally {
			  session.getTransaction().commit();
		  }
		  return data;
	}

	@Override
	public boolean isNGONameExists(String ngoName, int projectId) {
	    List<String> ngoNames = new ArrayList<>();

	    Transaction tx = null;

	    try (Session session = sessionFactory.openSession()) {
	        tx = session.beginTransaction(); // Start transaction

	        String hql = "SELECT UPPER(ngo_name) FROM JanbhagidariPratiyogitaNgoname where janbhagidariPratiyogita.iwmpMProject.projectId=:prCode";
	        Query<String> query = session.createQuery(hql, String.class);
	        query.setParameter("prCode", projectId);
	        ngoNames = query.list();

	        tx.commit(); 
	    } catch (Exception ex) {
	        if (tx != null) tx.rollback(); 
	        ex.printStackTrace(); 
	    }

	    return ngoNames.stream().anyMatch(existingName -> existingName.equalsIgnoreCase(ngoName));
	}


}
