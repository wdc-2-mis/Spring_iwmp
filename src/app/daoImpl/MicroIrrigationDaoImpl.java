package app.daoImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import app.bean.VillGramPanBean;
import app.dao.MicroIrrigationDao;
import app.model.BlsOutDetailAchiev;
import app.model.BlsOutMain;
import app.model.BlsOutMainAchiev;
import app.model.project.IwmpProjectPhysicalAsset;

@Repository("MicroIrrigationDao")
public class MicroIrrigationDaoImpl implements MicroIrrigationDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getplotsnoOfProject}")
	String getplotsnoOfProject;
	
	@Value("${getplotirrdata}")
	String getplotirrdata;
	
	@Value("${checkmicroIstatus}")
	String checkmicroIstatus;
	
	@Override
	public HashMap<Integer, String> getPlotnoOfProject(Integer villageId) {
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		String hql = getplotsnoOfProject;
		try {
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("villageId", villageId);
			 query.setResultTransformer(Transformers.aliasToBean(VillGramPanBean.class)); 
			List<VillGramPanBean> temp = query.list();
			for (VillGramPanBean v : temp) {
				map.put(v.getBls_out_main_id_pk(), v.getPlot_no());
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return map;
	}


	@Override
	public List<VillGramPanBean> getplotirriga(Integer plotnoId) {
    List<VillGramPanBean> irrgationdata=new ArrayList<VillGramPanBean>();
		String hql=getplotirrdata;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("plotnoId", plotnoId);
			query.setResultTransformer(Transformers.aliasToBean(VillGramPanBean.class));
			irrgationdata = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return irrgationdata;
	}


	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Boolean saveMicroIrr(BigDecimal microI, Integer plotno, Character microstatus) {
		Boolean res=false;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		try {
			BlsOutMain blsdata = (BlsOutMain)session.load(BlsOutMain.class, plotno);
			blsdata.setMicro_irrigation(microI);
			blsdata.setMicro_status(microstatus);
			session.update(blsdata);
			
			if(microstatus.equals('C') && microstatus == 'C')
			{
				BlsOutMainAchiev data = (BlsOutMainAchiev)session.load(BlsOutMainAchiev.class, plotno);
				data.setMicro_irrigation(microI);
				session.update(data);
			}
			
			session.getTransaction().commit();
			res=true;
			
		} catch (Exception e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
			res=false;
		}
		
		return res;
	}


	@Override
	public Boolean getmicrostatus(Integer plotnoId) {
		Boolean res=false;
		Integer value=0;
		String checkmicrostatus=checkmicroIstatus;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery query = session.createSQLQuery(checkmicrostatus);
			query.setInteger("plotnoId", plotnoId);
			List list=query.list();
			System.out.println("list size:" +list.size());
			if(list.size()>0) {
				  res=true;
			}else {
				session.getTransaction().rollback();
				return false;
			}
			if(res)
				session.getTransaction().commit();
			else
				session.getTransaction().rollback();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

}
