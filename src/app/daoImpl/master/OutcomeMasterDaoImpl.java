package app.daoImpl.master;

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

import app.bean.AddOutcomeParaBean;
import app.bean.FPOReportBean;
import app.bean.PhysicalActBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.dao.master.OutcomeMasterDao;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;
import app.model.IwmpProjectAssetStatus;
import app.model.MDepartmentScheme;
import app.model.Outcome2Data;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpMProjectPrepare;
import app.model.master.WdcpmksyMOutcome;
import app.model.master.WdcpmksyMOutcomeDetail;
import app.model.outcome.FpoCoreaactivityDetail;
import app.model.outcome.FpoDetail;
import app.model.outcome.FpoMain;
import app.model.outcome.MFpoCoreactivity;
import app.model.project.IwmpProjectPrepare;
import app.model.project.WdcpmksyOutcomeAchDetails;
import app.model.project.WdcpmksyOutcomeAchievement;

@Repository("OutcomeMasterDao")
public class OutcomeMasterDaoImpl implements OutcomeMasterDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${outcomeDetail}")
	String outcomeDetail;
	
	@Value("${outcomeseqno}")
	String outcomeseqno;
	
	@Value("${outcomedseqno}")
	String outcomedseqno;
	
	@Value("${saveoutcomeDetail}")
	String saveoutcomeDetail;
	
	@Value("${deleteOutcomeMaster}")
	String deleteOutcomeMaster;
	
	@Value("${checkOutcomeMasterid}")
	String checkOutcomeMasterid;
	
	@Value("${deleteOutcomeMaster1}")
	String deleteOutcomeMaster1;
	
	@Value("${finyearmasteroutcome}") 
	String finyearmasteroutcome;
	
	@Value("${monthcodemasteroutcome}") 
	String monthcodemasteroutcome;
	
	@Value("${outcomedatafind}") 
	String outcomedatafind;
	
	@Value("${updateOutcomeMaster}") 
	String updateOutcomeMaster;
	
	@Value("${outcomedetailmaster}") 
	String outcomedetailmaster;
	
	@Value("${deleteOutcomeMaster2}") 
	String deleteOutcomeMaster2;
	
	@Value("${outcomedatadetailfind}") 
	String outcomedatadetailfind ;
	
	@Value("${updateOutcomeDetailMaster}") 
	String updateOutcomeDetailMaster;
	
	@Value("${getoutcomedata}") 
	String getoutcomedata;
	
	@Value("${getOutcomeDraftData}") 
	String getOutcomeDraftData;
	
	@Value("${getOutcomecompleteData}") 
	String getOutcomecompleteData;
	
	@Value("${getOutcomeFinalData}") 
	String getOutcomeFinalData;
	
	@Value("${stateWiseAdditionalParameter}") 
	String stateWiseAdditionalParameter;
	
	@Value("${stateWiseAdditionalParameterfinyr}") 
	String stateWiseAdditionalParameterfinyr;
	
	@Value("${districtWiseAdditionalParameter}") 
	String districtWiseAdditionalParameter;
	
	@Value("${districtWiseAdditionalParameterfinyr}") 
	String districtWiseAdditionalParameterfinyr;
	
	@Value("${projectWiseAdditionalParameter}") 
	String projectWiseAdditionalParameter;
	
	@Value("${projectWiseAdditionalParameterfinyr}") 
	String projectWiseAdditionalParameterfinyr;
	
	@Value("${getoutcomedegradedData}") 
	String getoutcomedegradedData;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WdcpmksyOutcomeBean> getOutcomeMaster() {

		List<WdcpmksyOutcomeBean> result=new ArrayList<WdcpmksyOutcomeBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql=null;
			SQLQuery query = null;
			hql=outcomeDetail;
			session.getTransaction().begin();
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}
	
	@Override
	public List<WdcpmksyOutcomeBean> getoutcomeseqno() {
		List<WdcpmksyOutcomeBean> getheadseqno=new ArrayList<WdcpmksyOutcomeBean>();
		
		String hql=outcomeseqno;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			getheadseqno = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getheadseqno;
	}
	
	@Override
	public List<WdcpmksyOutcomeBean> getoutcomedseqno() {
		List<WdcpmksyOutcomeBean> getheadseqno=new ArrayList<WdcpmksyOutcomeBean>();
		
		String hql=outcomedseqno;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			getheadseqno = query.list();
			
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return getheadseqno;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMMonth> getAllMonths() {
		Session session = sessionFactory.getCurrentSession();
		List<IwmpMMonth> temp = new ArrayList<IwmpMMonth>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from IwmpMMonth order by finmonthId").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public Integer saveOutcomeMaster(WdcpmksyOutcomeBean outcome, HttpSession session) {
		
		Session sessionF = sessionFactory.getCurrentSession();
		Transaction tx = sessionF.beginTransaction();
		String savesql=saveoutcomeDetail;
		int value=0;
		try {    
				 InetAddress inet=InetAddress.getLocalHost();
				 String ipAddr=inet.getHostAddress();
				 Date d= new Date();
				 SQLQuery query = sessionF.createSQLQuery(savesql);
			    // System.out.println(outcome.getOutcome_desc()+" "+outcome.getFin_yr_cd()+" "+outcome.getMonth_id()+" "+outcome.getSeq_no()+" "+outcome.getDetail_exist());   
			     query.setString("outcome_desc", outcome.getOutcome_desc());
			     if(outcome.getFin_yr_cd()!=null && !outcome.getFin_yr_cd().toString().equals("")) 
			     {
			    	 query.setInteger("fin_yr_cd", outcome.getFin_yr_cd());
			     }
			     else {
			    	 query.setParameter("fin_yr_cd", 0);
			     }
			     if(outcome.getMonth_id()!=null && !outcome.getMonth_id().toString().equals(""))
			     {	 
			    	 query.setInteger("month_id", outcome.getMonth_id());
			     }
			     else {
			    	 query.setParameter("month_id", 0);
			     }
			     query.setBigDecimal("seq_no", outcome.getSeq_no());
			     if(outcome.getDetail_exist()!=null && !outcome.getDetail_exist().toString().equals(""))
			     {
			     query.setCharacter("detail_exist", outcome.getDetail_exist());
			     }else
			    	 query.setString("detail_exist", null);
			     query.setString("created_by", session.getAttribute("loginID").toString());
			     query.setDate("created_on", d);
			     query.setString("updated_by", session.getAttribute("loginID").toString());
			     query.setDate("updated_on", d);
			     query.setString("request_ip",ipAddr);
			     query.setBoolean("bls_required", outcome.getBls_required());
			     value=query.executeUpdate();
				 
					
		}
		catch (Exception e) 
		{
			value=0;
			tx.rollback();
			e.printStackTrace();
		}
		finally 
		{   
			tx.commit();
		} 
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean deleteOutcomeMaster(int id) {
		
		Boolean res=false;
		Integer value=0;
		Integer value1=0;
		String checkid=checkOutcomeMasterid;
		String deletefirst=deleteOutcomeMaster1;
		String deletemaster=deleteOutcomeMaster;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<WdcpmksyOutcomeBean> getheadseqno=new ArrayList<WdcpmksyOutcomeBean>();
		try {
			
				SQLQuery queryc = session.createSQLQuery(checkid);
				queryc.setInteger("id", id);
				queryc.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
				getheadseqno = queryc.list();
				/*for(int i=0;i<getheadseqno.size();i++) 
				{
					 System.out.println(getheadseqno.get(i).getOutcome_id()); 
				}*/
				if(getheadseqno.size()>0)
				{
					res=false;
				}
				else {
					SQLQuery query1 = session.createSQLQuery(deletefirst);
					query1.setInteger("id", id);
					value1=query1.executeUpdate();
				
					SQLQuery query = session.createSQLQuery(deletemaster);
					query.setInteger("id", id);
					value=query.executeUpdate();
					if(value>0) 
					{
						res=true;
					}
					else {
							session.getTransaction().rollback();
							return false;
					}
					if(res)
						session.getTransaction().commit();
					else
						session.getTransaction().rollback();

				}
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}

	@Override
	public LinkedHashMap<Integer, String> getfinyearCode() {
		
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<WdcpmksyOutcomeBean> rows = new ArrayList<WdcpmksyOutcomeBean>();
		String hql=finyearmasteroutcome;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			rows = query.list();
			  for(WdcpmksyOutcomeBean row : rows){
				 map.put(row.getFin_yr_cd(), row.getFinyear());
				 
			  }
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, String> getmonthcode() {
		
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<WdcpmksyOutcomeBean> rows = new ArrayList<WdcpmksyOutcomeBean>();
		String hql=monthcodemasteroutcome;
		try {
			 Session session = sessionFactory.getCurrentSession();
			 session.beginTransaction();
			 SQLQuery query = session.createSQLQuery(hql);
			 query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			 rows = query.list();
			 for(WdcpmksyOutcomeBean row : rows)
			 {
				 map.put(row.getMonth_id(), row.getMonthname());
			 }
			 session.getTransaction().commit();
		}
		catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}
		finally {
		
		}
		return map;
	}

	@Override
	public List<WdcpmksyOutcomeBean> outcomedatafind(Integer id) {
		
		List<WdcpmksyOutcomeBean> findactdesc=new ArrayList<WdcpmksyOutcomeBean>();
		
		String hql=outcomedatafind;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("id", id);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
		
	}

	@Override
	public Boolean updateOutcomeMaster(Integer id, String outcomedesc, Character detail_exist1, Integer fin_yr_cd1,
			BigDecimal seqno, Integer month_id1, Boolean bls_required) {
		
		Boolean res=false;
		Integer value=0;
		String savesql=updateOutcomeMaster;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress(); 
				SQLQuery query = session.createSQLQuery(savesql);
				Date d= new Date();
				
				query.setString("outcome_desc", outcomedesc);
				
				if(fin_yr_cd1!=null && !fin_yr_cd1.toString().equals("")) 
			    {
					 query.setInteger("fin_yr_cd", fin_yr_cd1);
			    }
			    else {
			    	 query.setParameter("fin_yr_cd", 0);
			    }
				if(month_id1!=null && !month_id1.toString().equals(""))
				{	 
				    query.setInteger("month_id", month_id1);
				}
				else {
			    	 query.setParameter("month_id", 0);
			    }
				if(detail_exist1!=null && !detail_exist1.toString().equals(""))
			    {
					 query.setCharacter("detail_exist", detail_exist1);
			    }
				else
			    	 query.setString("detail_exist", null);
			    
			    query.setBigDecimal("seq_no", seqno);
			    query.setDate("updated_on", d);
			    query.setString("request_ip",ipadd);
			    query.setInteger("id",id);
			    query.setBoolean("bls_required", bls_required);
			    value=query.executeUpdate();
				if(value>0) 
				{
					res=true;
				}
				else {
						session.getTransaction().rollback();
						return false;
				}
				if(res)
					session.getTransaction().commit();
				else
					session.getTransaction().rollback();

		}
		catch(Exception ex) {
		    ex.printStackTrace();
		    session.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WdcpmksyOutcomeBean> getOutcomeDetailMaster() {
		
		List<WdcpmksyOutcomeBean> result=new ArrayList<WdcpmksyOutcomeBean>();
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql=null;
			SQLQuery query = null;
			hql=outcomedetailmaster;
			session.getTransaction().begin();
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			result =query.list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
		} 
		catch (Exception ex) 
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		} 
		finally {
			session.getTransaction().commit();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WdcpmksyMOutcome> getOutcomeYes() {
		Session session = sessionFactory.getCurrentSession();
		List<WdcpmksyMOutcome> temp = new ArrayList<WdcpmksyMOutcome>();
		try {
			session.beginTransaction();
			temp = session.createQuery("from WdcpmksyMOutcome where detailexist='Y' order by outcomeDesc").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
		return temp;
	}

	@Override
	public Integer saveOutcomeDetailMaster(WdcpmksyOutcomeBean outcome, HttpSession session) {
		
		Session sessionF = sessionFactory.getCurrentSession();
		Transaction tx = sessionF.beginTransaction();
		
		int p=0;
		try {
				
				InetAddress inet=InetAddress.getLocalHost();
				String ipAddr=inet.getHostAddress();
					
				WdcpmksyMOutcome mout=new WdcpmksyMOutcome();
				WdcpmksyMOutcomeDetail pp = new WdcpmksyMOutcomeDetail();
					
				mout.setOutcomeId(outcome.getOutcome_id());
				pp.setWdcpmksyMOutcome(mout);
				pp.setOutcomeDetailDesc(outcome.getOutcome_detail_desc());
				pp.setSeqNo(outcome.getDseq_no());
				pp.setCreatedBy(session.getAttribute("loginID").toString());
				pp.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
				pp.setRequestIp(ipAddr);
				sessionF.save(pp);
				p=1;
					
		}
		catch (Exception e) 
		{
			p=0;
			tx.rollback();
			e.printStackTrace();
		}
		finally 
		{   
			tx.commit();
		} 
		return p;
	}

	@Override
	public Boolean deleteOutcomeDetailMaster(int id, int idoutcomed) {
		// TODO Auto-generated method stub
		Boolean res=false;
		Integer value=0;
		Integer value1=0;
		String checkid=checkOutcomeMasterid;
		String deletefirst=deleteOutcomeMaster2;
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<WdcpmksyOutcomeBean> getheadseqno=new ArrayList<WdcpmksyOutcomeBean>();
		try {
			
				SQLQuery queryc = session.createSQLQuery(checkid);
				queryc.setInteger("id", id);
				queryc.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
				getheadseqno = queryc.list();
				/*for(int i=0;i<getheadseqno.size();i++) 
				{
					 System.out.println(getheadseqno.get(i).getOutcome_id()); 
				}*/
				if(getheadseqno.size()>0)
				{
					res=false;
				}
				else {
					SQLQuery query = session.createSQLQuery(deletefirst);
					query.setInteger("id", id);
					query.setInteger("outdtl", idoutcomed);
					value=query.executeUpdate();
				
					if(value>0) 
					{
						res=true;
					}
					else {
							session.getTransaction().rollback();
							return false;
					}
					if(res)
						session.getTransaction().commit();
					else
						session.getTransaction().rollback();

				}
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}

	@Override
	public LinkedHashMap<Integer, String> getOutcomeHeadcode() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<WdcpmksyOutcomeBean> rows = new ArrayList<WdcpmksyOutcomeBean>();
		//String hql=finyearmasteroutcome;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select outcome_id, outcome_desc from public.wdcpmksy_m_outcome where detail_exist='Y' order by outcome_desc");
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			rows = query.list();
			  for(WdcpmksyOutcomeBean row : rows){
				 map.put(row.getOutcome_id(), row.getOutcome_desc());
				 
			  }
			  
			  session.getTransaction().commit();
		}catch(Exception ex) {
		      //System.out.print(ex.getStackTrace()[0].getLineNumber());
			ex.printStackTrace();
		}finally {
		
		}
		return map;
	}

	@Override
	public List<WdcpmksyOutcomeBean> outcomedatadetailfind(Integer id) {
		// TODO Auto-generated method stub
		List<WdcpmksyOutcomeBean> findactdesc=new ArrayList<WdcpmksyOutcomeBean>();
		
		String hql=outcomedatadetailfind;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("id", id);
			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyOutcomeBean.class));
			findactdesc = query.list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return findactdesc;
	}

	@Override
	public Boolean updateOutcomeDetailMaster(Integer id, String outcomedetdesc, Integer doutcomeidh, BigDecimal seqno) {
	
		Boolean res=false;
		Integer value=0;
		String savesql=updateOutcomeDetailMaster;
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress(); 
				SQLQuery query = session.createSQLQuery(savesql);
				Date d= new Date();
				
			//    query.setInteger("outcome_id", doutcomeidh);
			    query.setString("outcome_detail_desc", outcomedetdesc);
			    query.setDate("created_on", d);
			    query.setString("request_ip", ipadd);
			    query.setInteger("id", id);
			    query.setBigDecimal("seq_no", seqno);
			    value=query.executeUpdate();
				if(value>0) 
				{
					res=true;
				}
				else {
						session.getTransaction().rollback();
						return false;
				}
				if(res)
					session.getTransaction().commit();
				else
					session.getTransaction().rollback();

		}
		catch(Exception ex) {
		    ex.printStackTrace();
		    session.getTransaction().rollback();
		}
		finally {
		
		}
		return res;
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getoutcomedesc() {
		LinkedHashMap<Integer, List<AddOutcomeParaBean>> map = new LinkedHashMap<Integer, List<AddOutcomeParaBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = getoutcomedata;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
			List<AddOutcomeParaBean> list = query.list();
			for (AddOutcomeParaBean row : list){
				//System.out.println("userId: "+row.getHeadcode());
			}
			List<AddOutcomeParaBean> sublist = new ArrayList<AddOutcomeParaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AddOutcomeParaBean row : list){
					if (!map.containsKey(row.getOutcome_id())) {
						sublist = new ArrayList<AddOutcomeParaBean>();
						sublist.add(row);
						map.put(row.getOutcome_id(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getOutcome_id(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			// e.printStackTrace();
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			// ex.printStackTrace();
		}finally {
			
		}
		return map;
	}

	
	@Override
	public String draftOutcomeParam(Integer projId, Integer month, Integer year, BigDecimal degradedrainf,
			 String noofman, String sc, String st, String others, String female, String smallfarmer,
			String marginalfarmer, String landless, String bpl, Integer outcome2id, Character status, String loginId) {
		Session session = sessionFactory.getCurrentSession();
		Outcome2Data wdcdata = new Outcome2Data();
		String res="fail";
		try {
			session.getTransaction().begin();
			
			if(outcome2id > 0)
			{
				wdcdata = session.get(Outcome2Data.class, outcome2id);
			}
			WdcpmksyMOutcome moutcome = new WdcpmksyMOutcome();
			WdcpmksyMOutcomeDetail moutcomedtl = new WdcpmksyMOutcomeDetail();
			
			IwmpMProject mproject = new IwmpMProject();
			IwmpMFinYear myear = new IwmpMFinYear();
			IwmpMMonth   mmonth = new IwmpMMonth();
			
			
			InetAddress inet=InetAddress.getLocalHost();
			String ipAddr=inet.getHostAddress();
			
			myear.setFinYrCd(year);
			wdcdata.setIwmpMFinYear(myear);
			mmonth.setMonthId(month);
			wdcdata.setIwmpMMonth(mmonth);
			mproject.setProjectId(projId);
			wdcdata.setIwmpMProject(mproject);
			wdcdata.setStatus(status);
			wdcdata.setCreatedBy(loginId);
			wdcdata.setCreatedOn(new Date());
			wdcdata.setRequestIp(ipAddr);
				       wdcdata.setDegraded_rainfed(degradedrainf);
				       wdcdata.setManDayGen(Long.parseLong(noofman));
				       wdcdata.setFarmerSc(Long.parseLong(sc));
				       wdcdata.setFarmerSt(Long.parseLong(st));
				       wdcdata.setOthers(Long.parseLong(others));
				       wdcdata.setFarmerFemale(Long.parseLong(female));
				       wdcdata.setFarmerSmall(Long.parseLong(smallfarmer));
				       wdcdata.setFarmerMirginal(Long.parseLong(marginalfarmer));
				       wdcdata.setFarmerLandless(Long.parseLong(landless));
				       wdcdata.setFarmerBpl(Long.parseLong(bpl));
			
			session.save(wdcdata);
			
		
		session.getTransaction().commit();
		res="success";
		}catch (Exception e) {
			e.printStackTrace();
			res="fail";
		}
		return res;
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getOutcomeparadraft(Integer project, Integer month, Integer financial) {
		LinkedHashMap<Integer, List<AddOutcomeParaBean>> map = new LinkedHashMap<Integer, List<AddOutcomeParaBean>>();
		String getReport=getOutcomeDraftData;
		Session session = sessionFactory.getCurrentSession();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setInteger("projectId",project); 
			query.setInteger("month", month);
			query.setInteger("year", financial);
			query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
			list = query.list();
			List<AddOutcomeParaBean> sublist = new ArrayList<AddOutcomeParaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AddOutcomeParaBean row : list){
					if (!map.containsKey(row.getOutcome_id())) {
						sublist = new ArrayList<AddOutcomeParaBean>();
						sublist.add(row);
						map.put(row.getOutcome_id(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getOutcome_id(), sublist);
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
	public String detOutcomedraftdata(Integer draftid) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Outcome2Data menu = (Outcome2Data) session.load(Outcome2Data.class, draftid);
			

			if (null != menu) {
				session.delete(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}
	}

	@Override
	public String finalSaveOutcomeParadraftdata(Integer draftid) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Outcome2Data menu = (Outcome2Data) session.load(Outcome2Data.class, draftid);
			if (null != menu) {
				menu.setStatus('c');
				session.update(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}
	}

	@Override
	public List<AddOutcomeParaBean> getoutcomefinaldata(Integer projectId, Integer month, Integer year) {
		String getReport=getOutcomeFinalData;
		Session session = sessionFactory.getCurrentSession();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setInteger("projectId",projectId); 
			query.setInteger("month", month);
			query.setInteger("year", year);
			query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
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
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getstateWiseAdditionalParameter(String fyear) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<AddOutcomeParaBean>> map = new LinkedHashMap<Integer, List<AddOutcomeParaBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = stateWiseAdditionalParameter;
			String hql1=stateWiseAdditionalParameterfinyr;
			if(fyear==null || fyear.equals("")) {
				query = session.createSQLQuery(hql);
			}
			else {
				query = session.createSQLQuery(hql1);
				query.setInteger("fin", Integer.parseInt(fyear));
			}
			query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
			List<AddOutcomeParaBean> list = query.list();
			for (AddOutcomeParaBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<AddOutcomeParaBean> sublist = new ArrayList<AddOutcomeParaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AddOutcomeParaBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<AddOutcomeParaBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getDistWiseStatusAdditionalParameter(String stcode,
			String finyear) {
		LinkedHashMap<Integer, List<AddOutcomeParaBean>> map = new LinkedHashMap<Integer, List<AddOutcomeParaBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = districtWiseAdditionalParameter;
			String hql1= districtWiseAdditionalParameterfinyr;
			if(finyear==null || finyear.equals("")) {
				query = session.createSQLQuery(hql);
			}
			else {
				query = session.createSQLQuery(hql1);
				query.setInteger("fin", Integer.parseInt(finyear));
			}
			query.setInteger("stcode", Integer.parseInt(stcode));
			query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
			List<AddOutcomeParaBean> list = query.list();
		//	for (AddOutcomeParaBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
		//	}
			List<AddOutcomeParaBean> sublist = new ArrayList<AddOutcomeParaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AddOutcomeParaBean row : list){
					if (!map.containsKey(row.getDcode())) {
						sublist = new ArrayList<AddOutcomeParaBean>();
						sublist.add(row);
						map.put(row.getDcode(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getDcode(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getPorjWiseStatusAdditionalParameter(String dcode,
			String finyear) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<AddOutcomeParaBean>> map = new LinkedHashMap<Integer, List<AddOutcomeParaBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = projectWiseAdditionalParameter;
			String hql1= projectWiseAdditionalParameterfinyr;
			if(finyear==null || finyear.equals("")) {
				query = session.createSQLQuery(hql);
			}
			else {
				query = session.createSQLQuery(hql1);
				query.setInteger("fin", Integer.parseInt(finyear));
			}
			query.setInteger("discode", Integer.parseInt(dcode));
			query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
			List<AddOutcomeParaBean> list = query.list();
			for (AddOutcomeParaBean row : list){
					//	System.out.println("userId: "+row.getTotal_vill_basel());
			}
			List<AddOutcomeParaBean> sublist = new ArrayList<AddOutcomeParaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AddOutcomeParaBean row : list){
					if (!map.containsKey(row.getProj_id())) {
						sublist = new ArrayList<AddOutcomeParaBean>();
						sublist.add(row);
						map.put(row.getProj_id(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getProj_id(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			
			System.err.print("Hibernate error" + e.getMessage());
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getoutcomeDegradedData(String project) {
		LinkedHashMap<Integer, List<AddOutcomeParaBean>> map = new LinkedHashMap<Integer, List<AddOutcomeParaBean>>();

		String getReport=getoutcomedegradedData;
		Session session = sessionFactory.getCurrentSession();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setInteger("projectId",Integer.parseInt(project)); 
			query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
			list = query.list();
			List<AddOutcomeParaBean> sublist = new ArrayList<AddOutcomeParaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AddOutcomeParaBean row : list){
					if (!map.containsKey(row.getOutcome_id())) {
						sublist = new ArrayList<AddOutcomeParaBean>();
						sublist.add(row);
						map.put(row.getOutcome_id(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getOutcome_id(), sublist);
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
	public LinkedHashMap<Integer, List<AddOutcomeParaBean>> getOutcomeparacomplete(Integer project, Integer month,
			Integer financial) {
		LinkedHashMap<Integer, List<AddOutcomeParaBean>> map = new LinkedHashMap<Integer, List<AddOutcomeParaBean>>();
		String getReport=getOutcomecompleteData;
		Session session = sessionFactory.getCurrentSession();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		try {
			session.beginTransaction();
			Query query= session.createSQLQuery(getReport);
			query.setInteger("projectId",project); 
			query.setInteger("month", month);
			query.setInteger("year", financial);
			query.setResultTransformer(Transformers.aliasToBean(AddOutcomeParaBean.class));
			list = query.list();
			List<AddOutcomeParaBean> sublist = new ArrayList<AddOutcomeParaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AddOutcomeParaBean row : list){
					if (!map.containsKey(row.getOutcome_id())) {
						sublist = new ArrayList<AddOutcomeParaBean>();
						sublist.add(row);
						map.put(row.getOutcome_id(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getOutcome_id(), sublist);
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
	
	
	

	
	

	

	

}
