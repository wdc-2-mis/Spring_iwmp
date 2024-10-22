package app.daoImpl.reports;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.AssetIdBean;
import app.dao.reports.AssetReportDao;
import app.model.IwmpProjectAssetStatus;
import app.model.master.IwmpMPhySubactivity;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyProjectPhyAssetAchievement;

@Repository("AssetReportDao")
public class AssetReportDaoImpl implements AssetReportDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	 @Value("${getSubActivity}") 
	  String getSubActivity;
	 
	 @Value("${getAssetReport}") 
	  String getAssetReport;
	 
	 @Value("${getAssetReportWithSubActivity}") 
	  String getAssetReportWithSubActivity;
	 
	 @Value("${getAssetReportForclosed}") 
	  String getAssetReportForclosed;
	 
	 @Value("${getAssetReportCompleted}") 
	  String getAssetReportCompleted;
	 
	 @Value("${getAssetReportOngoing}") 
	  String getAssetReportOngoing;

	@Override
	public LinkedHashMap<Integer, String> getSubActivity(Integer activityId) {
		// TODO Auto-generated method stub
		String hql=getSubActivity;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			session.beginTransaction();
			Query query= session.createQuery(hql);
			query.setInteger("activityId",activityId); 
			Iterator itr = query.list().iterator();
			while(itr.hasNext())
			{
				Object ob[] = (Object[])itr.next();
				map.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
			}
			/*
			 * List<IwmpMPhySubactivity> rows = query.list(); for(IwmpMPhySubactivity row :
			 * rows){ map.put(row.getSubActivityCode(),row.getSubActivityDesc()); }
			 */
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
	public List<AssetIdBean> getAssetReport(Integer stCode, Integer distCode, Integer projId, Integer fyCode,
			Integer headCode, Integer activityCode, Integer subActivityCode, Integer monthid, String status) {
		// TODO Auto-generated method stub
		String hql="";
		Session session = sessionFactory.getCurrentSession();
		List<AssetIdBean> finallist = new ArrayList<AssetIdBean>();
		List<IwmpProjectPhysicalAsset> list = new ArrayList<IwmpProjectPhysicalAsset>();
		LinkedHashMap<String,BigDecimal> paramAchievementMap = new LinkedHashMap<String,BigDecimal>();
		try {
			session.beginTransaction();
			if(subActivityCode>0)
			{
				hql=getAssetReportWithSubActivity;
			}	
			else {
				if(status.equals("A"))
				hql=getAssetReport;
				if(status.equals("F"))
					hql=getAssetReportForclosed;
				if(status.equals("C"))
				hql=getAssetReportCompleted;
				if(status.equals("O"))
				hql=getAssetReportOngoing;
			}
			Query query= session.createQuery(hql);
			
			  query.setInteger("stCode",stCode);
			  query.setInteger("dCode",distCode);
			  query.setInteger("projId",projId);
			  query.setInteger("fyCode",fyCode);
			  query.setInteger("headCode",headCode);
			  query.setInteger("activityCode",activityCode);
			  if(subActivityCode>0)
			  query.setInteger("subActivityCode",subActivityCode);
			
			list = query.list();
			for(IwmpProjectPhysicalAsset asset : list) {
				BigDecimal assetachv = BigDecimal.ZERO;
				AssetIdBean bean = new AssetIdBean();
				bean.setAssetid(asset.getAssetid());
				bean.setActivitydesc(asset.getIwmpMPhyActivity().getActivityDesc());
				bean.setUnit(asset.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc());
				bean.setStname(asset.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getStName());
				bean.setDistname(asset.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getDistName());
				bean.setProjdesc(asset.getIwmpMProject().getProjName());
				bean.setFinyrdesc(asset.getIwmpMFinYear().getFinYrDesc());
				bean.setHeaddesc(asset.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
				bean.setActivitydesc(asset.getIwmpMPhyActivity().getActivityDesc());
				bean.setBname(asset.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(asset.getIwmpVillage().getVillageName());
				if(asset.getIwmpMPhySubactivity()!=null)
				bean.setSubactivitydesc(asset.getIwmpMPhySubactivity().getSubActivityDesc());
				bean.setStatuskd("Not Started");
				bean.setStatusdate("NA");
				for(WdcpmksyProjectPhyAssetAchievement ach : asset.getWdcpmksyProjectPhyAssetAchievements()) 
				{
					if(asset.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc().equals("nos")) {
						if(fyCode>0) {
							
							if(ach.getIwmpProjectPhysicalAsset().getAssetid()==asset.getAssetid() && ach.getIwmpMFinYear().getFinYrCd()==fyCode
									&& ach.getIwmpMMonth().getMonthId()==monthid)
								assetachv=assetachv.add(assetachv);
						}
						else {	if(ach.getIwmpProjectPhysicalAsset().getAssetid()==asset.getAssetid())
								assetachv=assetachv.add(assetachv);
						}
					}
					else {
						if(fyCode>0) {
							if(ach.getIwmpProjectPhysicalAsset().getAssetid()==asset.getAssetid() && ach.getIwmpMFinYear().getFinYrCd()==fyCode
									&& ach.getIwmpMMonth().getMonthId()==monthid)
								assetachv=assetachv.add(ach.getAchievement());
						}
						else {	if(ach.getIwmpProjectPhysicalAsset().getAssetid()==asset.getAssetid())
								assetachv=assetachv.add(ach.getAchievement());
						}
					}
				}
				bean.setAssetach(assetachv);
				for(IwmpProjectAssetStatus st : asset.getIwmpProjectAssetStatuses()) 
				{
					if(st.getIwmpProjectPhysicalAsset().getAssetid()==asset.getAssetid()) 
					{
						if(st.getStatus().equals('C')) {
							bean.setStatuskd("Completed");
							//System.out.println(st.getCompletiondate());
							SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
						    String output = outputFormat.format(st.getCompletiondate());
						    bean.setStatusdate(output);
						//	System.out.println(output+" kdy");
						}
						if(st.getStatus().equals('F')) {
							bean.setStatuskd("forClosed");
						}
						if(st.getStatus().equals('O')) {
							bean.setStatuskd("OnGoing");
						}	
					}
					else {
						bean.setStatuskd("Not Started");
					}	
				}
				
				
				finallist.add(bean);
				
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
		return finallist;
	}

}
