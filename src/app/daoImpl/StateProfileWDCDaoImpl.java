package app.daoImpl;

import java.math.BigDecimal;
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

import app.bean.DolrSupportBean;
import app.bean.FPOReportBean;
import app.bean.NetTretatbleAreaBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.StateProfileWDCBean;
import app.dao.StateProfileWDCDao;
import app.model.IwmpState;
import app.model.master.SlnaStProfile;

@Repository("stateProfileWDCDao")
public class StateProfileWDCDaoImpl implements StateProfileWDCDao {

	@Value("${getstateprofilewdc}")
	String getstateprofilewdc;

	@Autowired
	private SessionFactory sessionFactory;


	@Value("${getstateprofiledata}")
	String getstateprofiledata;
	
	@Value("${getTreatbleareadata}")
	String getTreatbleareadata;
	
	@Override
	public LinkedHashMap<Integer, List<StateProfileWDCBean>> getfpodatastatewise(Integer sCode) {
		// TODO Auto-generated method stub
		String getReport = getstateprofilewdc;
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<Integer, List<StateProfileWDCBean>> map = new LinkedHashMap<Integer, List<StateProfileWDCBean>>();
		List<StateProfileWDCBean> list = new ArrayList<StateProfileWDCBean>();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(getReport);
			query.setInteger("sCode", sCode);
			query.setResultTransformer(Transformers.aliasToBean(StateProfileWDCBean.class));
			list = query.list();
			map.put(1, list);
			// System.out.println("list:" +list);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		return map;
	}

	@Override
	public String savestateprofiledata(String status, Integer stateCode, Integer district, Integer blocks,
			Integer mircowatersheds, BigDecimal geoarea, BigDecimal untreatarea, BigDecimal iwmpProjects,
			BigDecimal waterShedP, BigDecimal assIrrigation, String loginID, BigDecimal areacoverwdciwmp) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		IwmpState state = new IwmpState();
		state.setStCode(stateCode);
		try {
			session.getTransaction().begin();
			InetAddress inet = InetAddress.getLocalHost();
			String ipAddr = inet.getHostAddress();
			//SlnaStProfile stprofile = new SlnaStProfile();
			SlnaStProfile stprofile = (SlnaStProfile) session.get(SlnaStProfile.class, state.getStCode());
			if (stprofile == null) {
				stprofile = new SlnaStProfile();
				stprofile.setIwmpState(state);
			}
			
			stprofile.setNoOfDist(district);
			stprofile.setNoOfBlock(blocks);
			stprofile.setTotGeoAreaHec(geoarea);
			stprofile.setAreaByPreiwmpProj(iwmpProjects);
			stprofile.setTotAreaAsurIrrig(assIrrigation);
			stprofile.setTotNoMicroWs(mircowatersheds);
			stprofile.setTotUntreatAreaHec(untreatarea);
			stprofile.setAreaByOtherWs(waterShedP);
			stprofile.setCreatedBy(loginID);
			stprofile.setCreatedDt(new Date());
			stprofile.setStatus(status);
			stprofile.setLastUpdatedBy(loginID);
			stprofile.setLastUpdatedDate(new Date());
			stprofile.setRequestIp(ipAddr);
			stprofile.setIwmpwdcpmksyarea(areacoverwdciwmp);
			session.saveOrUpdate(stprofile);

			session.getTransaction().commit();
			res = "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			res = "fail";
			session.getTransaction().rollback();
		}

		return res;
	}

	@Override
	public LinkedHashMap<Integer, List<SlnaStProfile>> getstateprofiledata(Integer stateCode) {
		LinkedHashMap<Integer, List<SlnaStProfile>> map = new LinkedHashMap<Integer, List<SlnaStProfile>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = null;
			String hql = getstateprofiledata;
			query = session.createQuery(hql);
			query.setParameter("stateCode", stateCode);
			//query.setResultTransformer(Transformers.aliasToBean(SlnaStProfile.class));
			List<SlnaStProfile> list = query.list();
			for (SlnaStProfile row : list){
				//System.out.println("userId: "+row.getHeadcode());
			}
			List<SlnaStProfile> sublist = new ArrayList<SlnaStProfile>();
			if ((list != null) && (list.size() > 0)) {
				for (SlnaStProfile row : list){
					if (!map.containsKey(row.getStateCd())) {
						sublist = new ArrayList<SlnaStProfile>();
						sublist.add(row);
						map.put(row.getStateCd(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getStateCd(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	@Override
	public LinkedHashMap<Integer, List<NetTretatbleAreaBean>> getnetTreatledata() {
		LinkedHashMap<Integer, List<NetTretatbleAreaBean>> map = new LinkedHashMap<Integer, List<NetTretatbleAreaBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = getTreatbleareadata;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(NetTretatbleAreaBean.class));
			List<NetTretatbleAreaBean> list = query.list();
			for (NetTretatbleAreaBean row : list){
				//System.out.println("userId: "+row.getHeadcode());
			}
			List<NetTretatbleAreaBean> sublist = new ArrayList<NetTretatbleAreaBean>();
			if ((list != null) && (list.size() > 0)) {
				for (NetTretatbleAreaBean row : list){
					if (!map.containsKey(row.getSt_code())) {
						sublist = new ArrayList<NetTretatbleAreaBean>();
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getSt_code(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

}
