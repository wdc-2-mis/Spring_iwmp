package app.daoImpl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.AllActivityDao;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpMProjectPrepare;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MFpoCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.outcome.MShgCoreactivity;
import app.model.outcome.MTrainingSubject;

@Repository("allActivityDao")
public class AllActivityDaoImpl implements AllActivityDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getlivelihoodactivity}")
	String getlivelihoodactivity;
	
	@Value("${getproductionactivity}")
	String getproductionactivity;
	
	@Value("${getepaactivity}")
	String getepaactivity;
	
	@Value("${getfpoactivity}")
	String getfpoactivity;
	
	@Value("${gettrainingsubactivity}")
	String gettrainingsubactivity;
	
	@Value("${getshgactivity}")
	String getshgactivity;
	
	@Value("${getlivelihoodidactivity}")
	String getlivelihoodidactivity;
	
	@Value("${getproductionidactivity}")
	String getproductionidactivity;
	
	@Value("${getepaidactivity}")
	String getepaidactivity;
	
	@Value("${getfpoidactivity}")
	String getfpoidactivity;
	
	@Value("${getshgidactivity}")
	String getshgidactivity;
	
	@Value("${gettrainingidactivity}")
	String gettrainingidactivity;
	
	@Value("${getPreparednessData}")
	String getPreparednessData;
	
	@Value("${countprojectprepareid}")
	String countprojectprepareid;
	
	@Value("${findProjectPrepare}")
	String findProjectPrepare;
	
	@Override
	public String saveallmasterdata(String id, String actdesc, Boolean status, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			
			if(id.equals("Livelihood"))
			{
				MLivelihoodCoreactivity savedata = new MLivelihoodCoreactivity();
				savedata.setLivelihoodCoreactivityDesc(actdesc);
				savedata.setCreatedOn(new Date());
				savedata.setRequestIp(ipAddr);
				savedata.setUpdatedOn(new Date());
				savedata.setIsActive(status);
				session.save(savedata);
				res = "success";
			}
			if(id.equals("Production"))
			{
				MProductivityCoreactivity savedata = new MProductivityCoreactivity();
				savedata.setProductivityCoreactivityDesc(actdesc);
				savedata.setCreatedOn(new Date());
				savedata.setRequestIp(ipAddr);
				savedata.setUpdatedOn(new Date());
				savedata.setIsActive(status);
				session.save(savedata);
				res = "success";
			}
			if(id.equals("EPA"))
			{
				MEpaCoreactivity savedata = new MEpaCoreactivity();
				savedata.setEpaDesc(actdesc);
				savedata.setCreatedOn(new Date());
				savedata.setRequestIp(ipAddr);
				savedata.setUpdatedOn(new Date());
				savedata.setIsActive(status);
				session.save(savedata);
				res = "success";
			}
			
			if(id.equals("FPO"))
			{
				MFpoCoreactivity savedata = new MFpoCoreactivity();
				savedata.setFpoCoreactivityDesc(actdesc);
				savedata.setCreatedOn(new Date());
				savedata.setRequestIp(ipAddr);
				savedata.setUpdatedOn(new Date());
				savedata.setIsActive(status);
				session.save(savedata);
				res = "success";
			}
			
			if(id.equals("SHG"))
			{
				MShgCoreactivity savedata = new MShgCoreactivity();
				savedata.setShgCoreactivityDesc(actdesc);
				savedata.setCreatedOn(new Date());
				savedata.setRequestIp(ipAddr);
				savedata.setUpdatedOn(new Date());
				savedata.setIsActive(status);
				session.save(savedata);
				res = "success";
			}
			if(id.equals("Training_Area"))
			{
				MTrainingSubject savedata = new MTrainingSubject();
				savedata.setTrainingSubDesc(actdesc);
				savedata.setCreatedOn(new Date());
				savedata.setRequestIp(ipAddr);
				savedata.setUpdatedOn(new Date());
				savedata.setIsActive(status);
				session.save(savedata);
				res = "success";
			}
			session.getTransaction().commit();
			
			
		}

		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public List<MLivelihoodCoreactivity> getlivelihooddata() {
    List<MLivelihoodCoreactivity> getlivelihooddata=new ArrayList<MLivelihoodCoreactivity>();
		
		String hql=getlivelihoodactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			getlivelihooddata = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getlivelihooddata;
	}

	@Override
	public List<MProductivityCoreactivity> getproductiondata() {
    List<MProductivityCoreactivity> getproductiondata=new ArrayList<MProductivityCoreactivity>();
		
		String hql=getproductionactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			getproductiondata = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getproductiondata;
	}

	@Override
	public List<MEpaCoreactivity> getepadata() {
    List<MEpaCoreactivity> getepadata=new ArrayList<MEpaCoreactivity>();
		
		String hql=getepaactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			getepadata = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getepadata;
	}

	@Override
	public List<MFpoCoreactivity> getfpodata() {
     List<MFpoCoreactivity> getfpodata=new ArrayList<MFpoCoreactivity>();
		
		String hql=getfpoactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			getfpodata = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getfpodata;
	}

	@Override
	public List<MTrainingSubject> gettrainingsubdata() {
    List<MTrainingSubject> gettrainingsubdata=new ArrayList<MTrainingSubject>();
		
		String hql=gettrainingsubactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			gettrainingsubdata = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return gettrainingsubdata;
	}

	@Override
	public List<MShgCoreactivity> getshgdata() {
    List<MShgCoreactivity> getshgdata=new ArrayList<MShgCoreactivity>();
		
		String hql=getshgactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			getshgdata = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getshgdata;
	}

	@Override
	public List<MLivelihoodCoreactivity> findlivelihoodactdesc(Integer id) {
    List<MLivelihoodCoreactivity> getlivelihooddata=new ArrayList<MLivelihoodCoreactivity>();
		
		String hql=getlivelihoodidactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getlivelihooddata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getlivelihooddata;
	}

	@Override
	public List<MProductivityCoreactivity> findproductionactdesc(Integer id) {
    List<MProductivityCoreactivity> getproductiondata=new ArrayList<MProductivityCoreactivity>();
		
		String hql=getproductionidactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getproductiondata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getproductiondata;
	}

	@Override
	public List<MEpaCoreactivity> findepaactdesc(Integer id) {
    List<MEpaCoreactivity> getepadata=new ArrayList<MEpaCoreactivity>();
		
		String hql=getepaidactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getepadata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getepadata;
	}

	@Override
	public List<MFpoCoreactivity> findfpoactdesc(Integer id) {
    List<MFpoCoreactivity> getfpodata=new ArrayList<MFpoCoreactivity>();
		
		String hql=getfpoidactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getfpodata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getfpodata;
	}

	@Override
	public List<MShgCoreactivity> findshgactdesc(Integer id) {
    List<MShgCoreactivity> getshgdata=new ArrayList<MShgCoreactivity>();
		
		String hql=getshgidactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			getshgdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getshgdata;
	}

	@Override
	public List<MTrainingSubject> findtrainingactdesc(Integer id) {
    List<MTrainingSubject> gettrainingdata=new ArrayList<MTrainingSubject>();
		
		String hql=gettrainingidactivity;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			gettrainingdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return gettrainingdata;
	}

	@Override
	public String updateallactivitymaster(int id, String modal, String actdesc, Boolean status, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			
			if(modal.equals("Livelihood"))
			{
			MLivelihoodCoreactivity savedata = new MLivelihoodCoreactivity();
			savedata = (MLivelihoodCoreactivity) session.get(MLivelihoodCoreactivity.class, id);
			savedata.setLivelihoodCoreactivityDesc(actdesc);
			savedata.setIsActive(status);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
			session.update(savedata);
			res = "success";
			}
			
			if(modal.equals("Production"))
			{
			MProductivityCoreactivity savedata = new MProductivityCoreactivity();
			savedata = (MProductivityCoreactivity) session.get(MProductivityCoreactivity.class, id);
			savedata.setProductivityCoreactivityDesc(actdesc);
			savedata.setIsActive(status);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
			session.update(savedata);
			res = "success";
			}
			
			if(modal.equals("EPA"))
			{
			MEpaCoreactivity savedata = new MEpaCoreactivity();
			savedata = (MEpaCoreactivity) session.get(MEpaCoreactivity.class, id);
			savedata.setEpaDesc(actdesc);
			savedata.setIsActive(status);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
			session.update(savedata);
			res = "success";
			}
			
			if(modal.equals("FPO"))
			{
			MFpoCoreactivity savedata = new MFpoCoreactivity();
			savedata = (MFpoCoreactivity) session.get(MFpoCoreactivity.class, id);
			savedata.setFpoCoreactivityDesc(actdesc);
			savedata.setIsActive(status);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
			session.update(savedata);
			res = "success";
			}
            
			if(modal.equals("SHG"))
			{
			MShgCoreactivity savedata = new MShgCoreactivity();
			savedata = (MShgCoreactivity) session.get(MShgCoreactivity.class, id);
			savedata.setShgCoreactivityDesc(actdesc);
			savedata.setIsActive(status);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
			session.update(savedata);
			res = "success";
			}
			
			if(modal.equals("Training"))
			{
			MTrainingSubject savedata = new MTrainingSubject();
			savedata = (MTrainingSubject) session.get(MTrainingSubject.class, id);
			savedata.setTrainingSubDesc(actdesc);
			savedata.setIsActive(status);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
			session.update(savedata);
			res = "success";
			}
			
			session.getTransaction().commit();
			
		}

		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public Boolean deletemodalactivityid(int id, String modal) {
		Session session = sessionFactory.getCurrentSession();
		Boolean res=false;
		Integer value=0;
		Long count=0l;
		String prepareid=countprojectprepareid;
		try {
			
			session.getTransaction().begin();
			if(modal.equals("Livelihood"))
			{
			MLivelihoodCoreactivity savedata = new MLivelihoodCoreactivity();
			savedata = (MLivelihoodCoreactivity) session.get(MLivelihoodCoreactivity.class, id);
			session.delete(savedata);
			res = true;
			}
			
			if(modal.equals("Production"))
			{
			MProductivityCoreactivity savedata = new MProductivityCoreactivity();
			savedata = (MProductivityCoreactivity) session.get(MProductivityCoreactivity.class, id);
			session.delete(savedata);
			res = true;
			}
			
			if(modal.equals("EPA"))
			{
			MEpaCoreactivity savedata = new MEpaCoreactivity();
			savedata = (MEpaCoreactivity) session.get(MEpaCoreactivity.class, id);
			session.delete(savedata);
			res = true;
			}
			
			if(modal.equals("FPO"))
			{
			MFpoCoreactivity savedata = new MFpoCoreactivity();
			savedata = (MFpoCoreactivity) session.get(MFpoCoreactivity.class, id);
			session.delete(savedata);
			res = true;
			}
            
			if(modal.equals("SHG"))
			{
			MShgCoreactivity savedata = new MShgCoreactivity();
			savedata = (MShgCoreactivity) session.get(MShgCoreactivity.class, id);
			session.delete(savedata);
			res = true;
			}
			
			if(modal.equals("Training"))
			{
			MTrainingSubject savedata = new MTrainingSubject();
			savedata = (MTrainingSubject) session.get(MTrainingSubject.class, id);
			session.delete(savedata);
			res = true;
			}
			
			if(modal.equals("Preparedness"))
			{
				Query query1 = session.createQuery(prepareid);
				query1.setInteger("id", id);
				count =(Long)query1.uniqueResult();
				if(count==0) 
				{
					IwmpMProjectPrepare savedata = new IwmpMProjectPrepare();
					savedata = (IwmpMProjectPrepare) session.get(IwmpMProjectPrepare.class, id);
					session.delete(savedata);
					res = true;
				}
				else {
					res = false;
				}
				
			}
			session.getTransaction().commit();
			
		}

		catch (Exception e) {
			e.printStackTrace();
			res = false;
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public List<IwmpMProjectPrepare> getPreparednessData() {
		// TODO Auto-generated method stub
		List<IwmpMProjectPrepare> getPreparedness=new ArrayList<IwmpMProjectPrepare>();
		
		String hql=getPreparednessData;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			getPreparedness = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getPreparedness;
	}

	@Override
	public String saveProjectPrepareData(Integer seqno, String pdesc, String srtname, String status1, String status2,
			HttpSession session) {
		// TODO Auto-generated method stub
		Session sessionf = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
				sessionf.getTransaction().begin();
				InetAddress inet = InetAddress.getLocalHost();
				String ipAddr = inet.getHostAddress();
				
				IwmpMProjectPrepare savedata = new IwmpMProjectPrepare();
				savedata.setPrepareDesc(pdesc);
				savedata.setShortDesc(srtname);
				savedata.setSelectedDesc1(status1);
				savedata.setSelectedDesc2(status2);
				savedata.setSequence(seqno);
				savedata.setCreatedOn(new Date());
				savedata.setRequestIp(ipAddr);
				savedata.setActive(true);
				savedata.setCreatedBy(session.getAttribute("loginID").toString());
				sessionf.save(savedata);
				res = "success";
			
				sessionf.getTransaction().commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			res = "fail";
			sessionf.getTransaction().rollback();
		}
		return res;
	}

	@Override
	public List<IwmpMProjectPrepare> findProjectPrepare(Integer id) {
		// TODO Auto-generated method stub
		List<IwmpMProjectPrepare> gettrainingdata=new ArrayList<IwmpMProjectPrepare>();
		
		String hql=findProjectPrepare;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			gettrainingdata = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return gettrainingdata;
	}

	@Override
	public String updateProjectPrepareData(Integer id, String modal, String prepareDesc, Boolean status,
			String shortDesc, String selectedDesc1, String selectedDesc2, Integer sequence, HttpSession session) {
		// TODO Auto-generated method stub
		Session sessionf = sessionFactory.getCurrentSession();
		String res = "fail";

		try {
			sessionf.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			
			IwmpMProjectPrepare savedata = new IwmpMProjectPrepare();
			savedata = (IwmpMProjectPrepare) sessionf.get(IwmpMProjectPrepare.class, id);
			
			savedata.setPrepareDesc(prepareDesc);
			savedata.setActive(status);
			savedata.setShortDesc(shortDesc);
			savedata.setSelectedDesc1(selectedDesc1);
			savedata.setSelectedDesc2(selectedDesc2);
			savedata.setSequence(sequence);
			savedata.setUpdatedOn(new Date());
			savedata.setRequestIp(ipAddr);
			sessionf.update(savedata);
			res = "success";
			
			sessionf.getTransaction().commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			sessionf.getTransaction().rollback();
		}

		return res;
	}

	

}
