package app.daoImpl.outcome;

import java.util.ArrayList;
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

import app.bean.AddOutcomeParaBean;
import app.bean.OtherActivityNameWithAssetBean;
import app.dao.outcomes.OtherActivityNameWithAssetDao;
import app.model.IwmpMFinYear;
import app.model.master.WdcpmksyMPhyOtherActivity;

@Repository("OtherActivityNameWithAssetDao")
public class OtherActivityNameWithAssetDaoImpl implements OtherActivityNameWithAssetDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${createAssetOtherActivityName}") 
	String createAssetOtherActivityName;
	
	@Value("${updateOtherActivityName}") 
	String updateOtherActivityName;
	
	@Value("${getOtherActivityNameid}") 
	String getOtherActivityNameid ;
	
	@Value("${getAssetOtherActivityName}") 
	String getAssetOtherActivityName;
	
	
	@Override
	public List<OtherActivityNameWithAssetBean> getcreateAssetOtherActivityName(String projectId, Integer stcode,
			String year) {
		// TODO Auto-generated method stub
		String getReport=createAssetOtherActivityName;
		Session session = sessionFactory.getCurrentSession();
		List<OtherActivityNameWithAssetBean> list = new ArrayList<OtherActivityNameWithAssetBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setInteger("proj", Integer.parseInt(projectId)); 
			query.setInteger("stcd", stcode);
			query.setInteger("fin", Integer.parseInt(year));
			query.setResultTransformer(Transformers.aliasToBean(OtherActivityNameWithAssetBean.class));
			list = query.list();
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
		return list;
	}


	@Override
	public String updateOtherActivityName(String projectId, Integer stcode, String year, String[] workid,
			String[] othername) {
		// TODO Auto-generated method stub
		String res = "";
		Session sessionf = sessionFactory.getCurrentSession();
		SQLQuery query;
		int value=0;
		String other = updateOtherActivityName;
		//String othid=getOtherActivityNameid;
		int id;
		try {
				
				sessionf.beginTransaction();
				
				String hql=getOtherActivityNameid;
				for(int i=0; i<workid.length; i++)
				{	
					//List list = sessionf.createSQLQuery(hql).setString("actdes", othername[i]).setInteger("stcd", stcode).list();
					//id=Integer.parseInt(list.get(0).toString());
					
					query = sessionf.createSQLQuery(other);
					query.setInteger("otherid", Integer.parseInt(othername[i]));
					query.setInteger("asset", Integer.parseInt(workid[i]));
					value = query.executeUpdate();
				}
				if (value > 0 ) {
					res = "success";
				} 
				else {
					res = "fail";
					sessionf.getTransaction().rollback();
				}
				if (res.equals("success"))
					sessionf.getTransaction().commit();
				else
					sessionf.getTransaction().rollback();
			} 
			catch (Exception ex) {
				// System.out.print(ex.getStackTrace()[0].getLineNumber());
				ex.printStackTrace();
				sessionf.getTransaction().rollback();
			} 
			finally {

			}
			return res;
	}


	@Override
	public List<OtherActivityNameWithAssetBean> getAssetOtherActivityName(String project, Integer stcode, String year) {
		// TODO Auto-generated method stub
		String getReport=getAssetOtherActivityName;
		Session session = sessionFactory.getCurrentSession();
		List<OtherActivityNameWithAssetBean> list = new ArrayList<OtherActivityNameWithAssetBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setInteger("proj", Integer.parseInt(project)); 
			query.setInteger("fin", Integer.parseInt(year));
			query.setResultTransformer(Transformers.aliasToBean(OtherActivityNameWithAssetBean.class));
			list = query.list();
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
		return list;
	}


	@Override
	public LinkedHashMap<Integer, String> getOtherNameMaster(Integer stcode) {
		// TODO Auto-generated method stub
		List<WdcpmksyMPhyOtherActivity> yearList=new ArrayList<WdcpmksyMPhyOtherActivity>();
	//	String hql=allFinyearQuery;
		LinkedHashMap<Integer, String> yearMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
				session.beginTransaction();
				Query query = session.createQuery("from WdcpmksyMPhyOtherActivity where iwmpState.stCode="+stcode+" order by otherActivityDesc");
				yearList = query.list();
				for (WdcpmksyMPhyOtherActivity year : yearList) 
				{
					yearMap.put(year.getOtherActivityCode(), year.getOtherActivityDesc());
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
        return yearMap;
	}

}
