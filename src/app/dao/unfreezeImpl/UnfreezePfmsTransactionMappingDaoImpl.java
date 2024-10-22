package app.dao.unfreezeImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.bean.PfmsTransactionBean;
import app.dao.unfreeze.UnfreezePfmsTransactionMappingDao;
import app.model.IwmpMProject;
import app.model.master.PfmsEatmisdataDetail;

@Repository("UnfreezePfmsTransactionMappingDao")
public class UnfreezePfmsTransactionMappingDaoImpl implements UnfreezePfmsTransactionMappingDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<PfmsTransactionBean> getCompPfmsTranMapWithProj(Integer projId) {
		Session session = sessionFactory.getCurrentSession();
		List<PfmsEatmisdataDetail> list = new ArrayList<>();
		List<PfmsTransactionBean> tranxList = new ArrayList<>();
		
		Query query = null;
		try {
			session.beginTransaction();
			query = session.createQuery("from PfmsEatmisdataDetail pd where pd.iwmpMProject =:projId");
			query.setInteger("projId", projId);
			list = query.list();
			for (PfmsEatmisdataDetail eatMis : list) {
				PfmsTransactionBean tranx = new PfmsTransactionBean();
				tranx.setEatmisdataId(eatMis.getEatmisdataId());
				tranx.setTranxId(eatMis.getTransactionId());
				tranx.setDate(eatMis.getDate().toString());
				tranx.setComponentCode(eatMis.getComponentCode());
				tranx.setComponentName(eatMis.getComponentName());
				tranx.setInvoiceNo(eatMis.getInvoiceNo());
				tranx.setProjectId(eatMis.getIwmpMProject() == null ? null : eatMis.getIwmpMProject().getProjectId());
				tranx.setProjectName(eatMis.getProjectName());
				tranx.setTotalAmount(eatMis.getTotalAmount());
				tranx.setStatus(eatMis.getStatus() == null ? 'O' : eatMis.getStatus());
				tranxList.add(tranx);
			}
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return tranxList;
	}

	@Override
	public String updateAsDraftPfmsTransaction(String[] eatmisdataId, Integer projId) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			if (projId != null) {
				session.beginTransaction();
				for (int i = 0; i < eatmisdataId.length; i++) {
					PfmsEatmisdataDetail pfms = session.load(PfmsEatmisdataDetail.class,
							Integer.parseInt(eatmisdataId[i]));
					pfms.setWistatus('D');
					session.saveOrUpdate(pfms);
				}
				session.getTransaction().commit();
				res = "success";
			}
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}

	@Override
	public List<PfmsTransactionBean> getCompPfmsTranMapWithWorkId(Integer fyear, Integer projId) {
		Session session = sessionFactory.getCurrentSession();
		List<PfmsEatmisdataDetail> list = new ArrayList<>();
		List<PfmsTransactionBean> tranxList = new ArrayList<>();
		
		Query query = null;
		try {
			session.beginTransaction();
			query = session.createQuery("from PfmsEatmisdataDetail pd where pd.iwmpMProject =:projId and pd.financialYear =:fyear");
			query.setInteger("projId", projId);
			query.setInteger("fyear", fyear);
			list = query.list();
			for (PfmsEatmisdataDetail eatMis : list) {
				PfmsTransactionBean tranx = new PfmsTransactionBean();
				tranx.setEatmisdataId(eatMis.getEatmisdataId());
				tranx.setTranxId(eatMis.getTransactionId());
				tranx.setDate(eatMis.getDate().toString());
				tranx.setComponentCode(eatMis.getComponentCode());
				tranx.setComponentName(eatMis.getComponentName());
				tranx.setInvoiceNo(eatMis.getInvoiceNo());
				tranx.setProjectName(eatMis.getIwmpMProject().getProjName());
				tranx.setProjectId(eatMis.getIwmpMProject().getProjectId());
				tranx.setTotalAmount(eatMis.getTotalAmount());
				if(eatMis.getTotalworks()!=null)
				{
				tranx.setWicode(eatMis.getTotalworks());
				}
				tranx.setWistatus(eatMis.getWistatus()==null?'O':eatMis.getWistatus());
				tranxList.add(tranx);
			}
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return tranxList;
	}
	

}
