package app.daoImpl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
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
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.RPCLivelihoodBean;
import app.bean.SelfHelpGroupBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.dao.SelfHelpGroupDao;
import app.model.IwmpMProject;
import app.model.MDepartmentScheme;
import app.model.UserMap;
import app.model.outcome.MShgCoreactivity;
import app.model.outcome.ShgCoreactivityDetail;
import app.model.outcome.ShgDetail;
import app.model.outcome.ShgMain;
import app.model.project.IwmpProjectPhysicalPlan;

@Repository("selfHelpGroupDao")
public class SelfHelpGroupDaoImpl implements SelfHelpGroupDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${getSHGCoreActivity}")
	String getSHGCoreActivity;
	
	@Value("${getSHGDraftData1}")
	String getSHGDraftData1;
	
	@Value("${getSHGDraftData2}")
	String getSHGDraftData2;
	
	@Value("${getSelfHelpCreatedExistList}")
	String getSelfHelpCreatedExistList;
	
	@Value("${getSelfHelpUserGroupList}")
	String getSelfHelpUserGroupList;
	
	@Value("${getSelfHelpCreatedExistListDist}")
	String getSelfHelpCreatedExistListDist;
	
	@Value("${getSelfHelpUserGroupListDist}")
	String getSelfHelpUserGroupListDist;
	
	@Value("${getSelfHelpCreatedExistListProject}")
	String getSelfHelpCreatedExistListProject;
	
	@Value("${getSelfHelpUserGroupListProject}")
	String getSelfHelpUserGroupListProject;
	
	@Value("${getSelfHelpCreatedNEWshg}")
	String getSelfHelpCreatedNEWshg;
	
	@Value("${getSelfHelpExistedOLDshg}")
	String getSelfHelpExistedOLDshg;
	
	@Value("${getSelfHelpCreateduserGroup}")
	String getSelfHelpCreateduserGroup;
	
	@Value("${getSHGDepartment}")
	String getSHGDepartment;

	@Override
	public LinkedHashMap<Integer, String> getSHGCoreActivity() {
		// TODO Auto-generated method stub
		List<MShgCoreactivity> result = new ArrayList<MShgCoreactivity>();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		try {
			String hql = getSHGCoreActivity;
			ses.beginTransaction();
			query = ses.createQuery(hql);
			result =query.list();
			for(MShgCoreactivity act : result) {
				map.put(act.getShgCoreactivityId(),act.getShgCoreactivityDesc());
			}
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return map;
	}

	@Override
	public String saveDraftSHGData(Integer projectCd, String group, Integer shgno, List<String> name, List<Integer> sc,
			List<Integer> st, List<Integer> others, List<Integer> women, List<String> activity, List<BigDecimal> turnover,
			List<BigDecimal> income, List<Integer> pmbima, List<String> fedrated,String updatedby,List<Integer> department,List<String> regdate, List<BigDecimal>revolve_amount, List<Boolean> threft_credit) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String res="fail";
		try {
		System.out.println("----------------->>>>"+fedrated+"---"+turnover+"---"+income);	
		session.getTransaction().begin();
		ShgMain main = new ShgMain();
		ShgDetail detail = new ShgDetail();
		ShgCoreactivityDetail coreactivity = new ShgCoreactivityDetail();
		MShgCoreactivity mcoreactivity = new MShgCoreactivity();
		MDepartmentScheme mdep = new MDepartmentScheme();
		InetAddress inet=InetAddress.getLocalHost();
		String ipAddr=inet.getHostAddress();
		
		IwmpMProject project = (IwmpMProject) session.get(IwmpMProject.class, projectCd);
		
		main.setIwmpMProject(project);
		main.setGroupType(group);
		main.setTotalno(shgno);
		main.setCreatedBy(updatedby);
		main.setCreatedOn(new Date());
		main.setRequestIp(ipAddr);
		main.setStatus('D');
		session.save(main);
		for(int i=0;i<name.size();i++) {
			detail = new ShgDetail();
			coreactivity = new ShgCoreactivityDetail();
			if(group.equalsIgnoreCase("newSHG")|| group.equalsIgnoreCase("oldSHG")) {
				if(threft_credit.get(i)) {
					if(income.get(i)!=null)
					detail.setAvgIncome(income.get(i));
					if(turnover.get(i)!=null)
					detail.setAvgTurnover(turnover.get(i));
					if(fedrated.get(i)!=null)
					detail.setFederated(fedrated.get(i).equalsIgnoreCase("y")?true:false);
					
				}
			
			Date regDate=new SimpleDateFormat("dd-MM-yyyy").parse(regdate.get(i));  
			detail.setRegDate(regDate);
			detail.setPmBimaYogana(pmbima.get(i));
			mdep.setDepartmentSchemeIdPk(department.get(i));
			detail.setMDepartmentScheme(mdep);
			detail.setRevolvingAmount(revolve_amount.get(i));
			detail.setThriftCredit(threft_credit.get(i));
			
			}
			
			
			detail.setName(name.get(i));
			detail.setOther(others.get(i));
			detail.setSc(sc.get(i));
			detail.setSt(st.get(i));
			detail.setWomen(women.get(i));
			detail.setShgMain(main);
			detail.setCreatedBy(updatedby);
			detail.setCreatedOn(new Date());
			detail.setRequestIp(ipAddr);
			session.save(detail);
			session.evict(detail);
				//String act[] = Arrays.asList(str.split("#"));
			if(group.equalsIgnoreCase("newSHG")|| group.equalsIgnoreCase("oldSHG")) {
				String act[] = activity.get(i).split("#");
				for(String s : act) {
					mcoreactivity = (MShgCoreactivity) session.get(MShgCoreactivity.class, Integer.parseInt(s));
					coreactivity.setMShgCoreactivity(mcoreactivity);
					coreactivity.setShgDetail(detail);
					coreactivity.setCreatedBy(updatedby);
					coreactivity.setCreatedOn(new Date());
					coreactivity.setRequestIp(ipAddr);
					session.save(coreactivity);
					session.evict(coreactivity);
				}
			}
				
		}
		session.getTransaction().commit();
		res="success";
		}catch(Exception e) {
			e.printStackTrace();
			res="fail";
		}
			
			
		return res;
	}

	@Override
	public LinkedHashMap<Integer,List<SelfHelpGroupBean>> getSHGDraftData(Integer projectCd, String group) {
		// TODO Auto-generated method stub
		List<ShgCoreactivityDetail> result = new ArrayList<ShgCoreactivityDetail>();
		List<ShgDetail> result2 = new ArrayList<ShgDetail>();
		Session ses = sessionFactory.getCurrentSession();
		Query query = null;
		String hql = null;
		LinkedHashMap<Integer,List<SelfHelpGroupBean>> map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		List<ShgCoreactivityDetail> list = new ArrayList<ShgCoreactivityDetail>();
		List<SelfHelpGroupBean> sublist = new ArrayList<SelfHelpGroupBean>();
		LinkedHashMap<Integer,String> coremap = new LinkedHashMap<Integer,String>();
		LinkedHashMap<Integer,List<SelfHelpGroupBean>> shgDetailsMap = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		String act=null;
		List<String> actDesc = new ArrayList<String>();
		SelfHelpGroupBean bean = new SelfHelpGroupBean();
		try {
			if(group.equalsIgnoreCase("group"))
			hql = getSHGDraftData2;
			else
			hql = getSHGDraftData1;	
			ses.beginTransaction();
			query = ses.createQuery(hql);
			query.setInteger("projId", projectCd);
			query.setString("groupType", group);
			if(group.equalsIgnoreCase("group"))
			result2 =query.list();
			else
			result =query.list();	
			
			if(group.equalsIgnoreCase("group")) {
				for(ShgDetail core : result2) {
					
					bean = new SelfHelpGroupBean();
					bean.setShg_id(core.getShgMain().getShgId());
					bean.setProject_id(core.getShgMain().getIwmpMProject().getProjectId());
					bean.setGrouptype(core.getShgMain().getGroupType());
					bean.setSgh_detail_id(core.getShgDetailId());
					bean.setName(core.getName());
					bean.setSc(core.getSc());
					bean.setSt(core.getSt());
					bean.setOthers(core.getOther());
					bean.setWomen(core.getWomen());
					bean.setTotal(core.getSc()+core.getSt()+core.getOther());
					bean.setFedrated(core.getFederated());
					/*
					 * bean.setDepartment_name(core.getMDepartmentScheme().getSchemeDescription());
					 * bean.setRegdate(core.getRegDate());
					 * bean.setRevolvingAmount(core.getRevolvingAmount());
					 * bean.setThriftCredit(core.getThriftCredit());
					 */
					
						sublist = new ArrayList<SelfHelpGroupBean>();
						sublist.add(bean);
						shgDetailsMap.put(core.getShgDetailId(), sublist);
					
				}
				for(ShgDetail core : result2) {
								
					if (!map.containsKey(core.getShgMain().getShgId()))
					{
						sublist = new ArrayList<SelfHelpGroupBean>();
						sublist=shgDetailsMap.get(core.getShgDetailId());
						shgDetailsMap.remove(core.getShgDetailId());
						map.put(core.getShgMain().getShgId(), sublist);
					}else {
						if (shgDetailsMap.containsKey(core.getShgDetailId())) {
							sublist=map.get(core.getShgMain().getShgId());
							List<SelfHelpGroupBean> l = shgDetailsMap.get(core.getShgDetailId());
							shgDetailsMap.remove(core.getShgDetailId());
							for(SelfHelpGroupBean li : l) { sublist.add(li); }
							map.put(core.getShgMain().getShgId(), sublist);
						}
						  
						 
					}
					
					
				}
			}else {
			for(ShgCoreactivityDetail core : result) {
				
				if (!coremap.containsKey(core.getShgDetail().getShgDetailId()))
				{
					act=core.getMShgCoreactivity().getShgCoreactivityDesc();
					coremap.put(core.getShgDetail().getShgDetailId(), act);
				}else {
					act=coremap.get(core.getShgDetail().getShgDetailId());
					act+=","+core.getMShgCoreactivity().getShgCoreactivityDesc();
					coremap.put(core.getShgDetail().getShgDetailId(), act);
				}
				
				
				bean = new SelfHelpGroupBean();
				bean.setShg_id(core.getShgDetail().getShgMain().getShgId());
				bean.setProject_id(core.getShgDetail().getShgMain().getIwmpMProject().getProjectId());
				bean.setGrouptype(core.getShgDetail().getShgMain().getGroupType());
				bean.setSgh_detail_id(core.getShgDetail().getShgDetailId());
				bean.setName(core.getShgDetail().getName());
				bean.setSc(core.getShgDetail().getSc());
				bean.setSt(core.getShgDetail().getSt());
				bean.setOthers(core.getShgDetail().getOther());
				bean.setWomen(core.getShgDetail().getWomen());
				bean.setTotal(core.getShgDetail().getSc()+core.getShgDetail().getSt()+core.getShgDetail().getOther());
				bean.setCoreactivitydetail_id(core.getShgCoreactivityDetailId());
				bean.setShg_coreactivity_desc(coremap.get(core.getShgDetail().getShgDetailId()));
				//bean.setShg_coreactivity_id(core.getMShgCoreactivity().getShgCoreactivityId());
				bean.setAvg_turnover(core.getShgDetail().getAvgTurnover());
				bean.setAvg_income(core.getShgDetail().getAvgIncome());
				bean.setPm_bima_yojna(core.getShgDetail().getPmBimaYogana());
				bean.setFedrated(core.getShgDetail().getFederated());
				if(core.getShgDetail()!=null && core.getShgDetail().getMDepartmentScheme()!=null) {
					bean.setDepartment_name(core.getShgDetail().getMDepartmentScheme().getSchemeDescription());
					bean.setRegdate(core.getShgDetail().getRegDate());
					bean.setRevolvingAmount(core.getShgDetail().getRevolvingAmount());
					bean.setThriftCredit(core.getShgDetail().getThriftCredit());
				}
				
				
					sublist = new ArrayList<SelfHelpGroupBean>();
					sublist.add(bean);
					shgDetailsMap.put(core.getShgDetail().getShgDetailId(), sublist);
				
			}
			for(ShgCoreactivityDetail core : result) {
							
				if (!map.containsKey(core.getShgDetail().getShgMain().getShgId()))
				{
					sublist = new ArrayList<SelfHelpGroupBean>();
					sublist=shgDetailsMap.get(core.getShgDetail().getShgDetailId());
					shgDetailsMap.remove(core.getShgDetail().getShgDetailId());
					map.put(core.getShgDetail().getShgMain().getShgId(), sublist);
				}else {
					if (shgDetailsMap.containsKey(core.getShgDetail().getShgDetailId())) {
						sublist=map.get(core.getShgDetail().getShgMain().getShgId());
						List<SelfHelpGroupBean> l = shgDetailsMap.get(core.getShgDetail().getShgDetailId());
						shgDetailsMap.remove(core.getShgDetail().getShgDetailId());
						for(SelfHelpGroupBean li : l) { sublist.add(li); }
						map.put(core.getShgDetail().getShgMain().getShgId(), sublist);
					}
					  
					 
				}
				
				
			}
			}
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally {
			ses.getTransaction().commit();
		}
		return map;
	}

	
	@Override
	public String deleteSHG(Integer shgid,String group) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String res="fail";
		try {
			
		session.getTransaction().begin();
		ShgDetail shgDetail = new ShgDetail();
		ShgCoreactivityDetail shgCoreactivityDetail = new ShgCoreactivityDetail();
		ShgMain shgMain = (ShgMain) session.get(ShgMain.class, shgid);
		if(shgMain.getShgDetails()!=null && !shgMain.getShgDetails().isEmpty())
		for(ShgDetail det : shgMain.getShgDetails()) {
			shgDetail = det;
			//if(!shgDetail.getShgCoreactivityDetails().isEmpty() && group.equals("group"))
			for(ShgCoreactivityDetail core : shgDetail.getShgCoreactivityDetails()) {
				shgCoreactivityDetail=core;
				session.delete(shgCoreactivityDetail);
			}
			session.delete(shgDetail);
			
		}
		session.delete(shgMain);
		
		
		session.getTransaction().commit();
		res="success";
		}catch(Exception e) {
			e.printStackTrace();
			res="fail";
		}
			
			
		return res;
	}

	@Override
	public String completeSHG(Integer shgid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String res="fail";
		try {
			
		session.getTransaction().begin();
		ShgMain shgMain = (ShgMain) session.get(ShgMain.class, shgid);
		shgMain.setStatus('C');
		session.update(shgMain);
		session.getTransaction().commit();
		res="success";
		}catch(Exception e) {
			e.printStackTrace();
			res="fail";
		}
			
			
		return res;
	}

	@Override
	public List<SelfHelpGroupReportBean> getSelfHelpCreatedExistList(Integer state) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpCreatedExistList;
			query = session.createSQLQuery(hql);
			query.setInteger("stcd", state);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public List<SelfHelpGroupReportBean> getSelfHelpUserGroupList(Integer state) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpUserGroupList;
			query = session.createSQLQuery(hql);
			query.setInteger("stcd", state);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public List<SelfHelpGroupReportBean> getSelfHelpGroupListDist(Integer stcd) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpCreatedExistListDist;
			query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public List<SelfHelpGroupReportBean> getSelfHelpUserGroupListDist(Integer stcd) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpUserGroupListDist;
			query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public List<SelfHelpGroupReportBean> getSelfHelpGroupListProject(Integer distid) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpCreatedExistListProject;
			query = session.createSQLQuery(hql);
			query.setInteger("distcd", distid);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public List<SelfHelpGroupReportBean> getSelfHelpUserGroupListProject(Integer distid) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpUserGroupListProject;
			query = session.createSQLQuery(hql);
			query.setInteger("distcd", distid);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public List<SelfHelpGroupReportBean> getSHGCreatedProjectDetail(Integer projectid) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpCreatedNEWshg;
			query = session.createSQLQuery(hql);
			query.setInteger("projcd", projectid);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public List<SelfHelpGroupReportBean> getSHGExistedProjectDetail(Integer projectid) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpExistedOLDshg;
			query = session.createSQLQuery(hql);
			query.setInteger("projcd", projectid);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public List<SelfHelpGroupReportBean> getSHGUserGroupProjectDetail(Integer projectid) {
		
		Session session = sessionFactory.getCurrentSession();
		List<SelfHelpGroupReportBean> list = new ArrayList<SelfHelpGroupReportBean>();
		try {
			String hql=null;
			SQLQuery query = null;
			session.beginTransaction();
			hql=getSelfHelpCreateduserGroup;
			query = session.createSQLQuery(hql);
			query.setInteger("projcd", projectid);
			query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
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
	public LinkedHashMap<Integer, String> getSHGDepartment(Integer stCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		List<MDepartmentScheme> list = new ArrayList<MDepartmentScheme>();
		try {
			session.beginTransaction();
			String hql=getSHGDepartment;
			Query query = session.createQuery(hql);
			//query.setInteger("stCode", stCode);
			//query.setResultTransformer(Transformers.aliasToBean(SelfHelpGroupReportBean.class));
			list = query.list();
			session.getTransaction().commit();
			for(MDepartmentScheme md : list) {
				map.put(md.getDepartmentSchemeIdPk(), md.getSchemeDescription());
			}
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

}
