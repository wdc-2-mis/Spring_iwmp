package app.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.model.PfmsCgireceiptDetaildata;
import app.model.PfmsTreasuryreceiptDetaildata;
import app.PfmsTreasureBean;
import app.bean.FPOReportBean;
import app.dao.PfmsCgiReportDao;
import app.model.GetGoiReleaseToStateTreasury;
import app.model.IwmpMProject;

@Repository("PfmsCgiDao")
public class PfmsCgiReportDaoImpl implements PfmsCgiReportDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${getpfmstreasuredata}")
	String getpfmstreasuredata;
	
	@Value("${getexpandtreasure}")
	String getexpandtreasure;
	
	@Value("${getpfmstotaltreasuredata}")
	String getpfmstotaltreasuredata;
	
	@Value("${getGoiReleaseToStateTreasury}")
	String getGoiReleaseToStateTreasury;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PfmsCgireceiptDetaildata> Pfmscgidetaildata() {
		String hql = "from PfmsCgireceiptDetaildata";
		List<PfmsCgireceiptDetaildata> temp=new ArrayList<PfmsCgireceiptDetaildata>();
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			temp = session.createQuery(hql).list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return temp;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PfmsTreasureBean> Pfmstrereciptdata() {
		String hql = getpfmstreasuredata;
		List<PfmsTreasureBean> temp=new ArrayList<PfmsTreasureBean>();
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			temp = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(PfmsTreasureBean.class)).list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PfmsTreasuryreceiptDetaildata> expandtreasure(int statecode) {
		String hql = getexpandtreasure;
		List<PfmsTreasuryreceiptDetaildata> temp=new ArrayList<PfmsTreasuryreceiptDetaildata>();
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			temp = session.createQuery(hql).setParameter("statecode", statecode).list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return temp;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PfmsTreasureBean> totalPfmstrereciptdata(int statecode) {
		String hql = getpfmstotaltreasuredata;
		List<PfmsTreasureBean> temp=new ArrayList<PfmsTreasureBean>();
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			temp = session.createQuery(hql).setParameter("statecode", statecode).setResultTransformer(Transformers.aliasToBean(PfmsTreasureBean.class)).list();
			session.getTransaction().commit();
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return temp;
	}

	@Override
	public List<GetGoiReleaseToStateTreasury> getGoiReleaseToStateTreasury(int finyr) {
			String hql = getGoiReleaseToStateTreasury;
			List<GetGoiReleaseToStateTreasury> list = new ArrayList<GetGoiReleaseToStateTreasury>();
			try {
				Session session = sessionFactory.getCurrentSession();
				session.beginTransaction();
				Query query = session.createQuery(hql);
				query.setInteger("finyr", finyr);
				list = query.list();
				session.getTransaction().commit();
			} 
			catch (HibernateException e) {
				System.err.print("Hibernate error");
				e.printStackTrace();
			} 
			catch(Exception ex){
				
				ex.printStackTrace();
			}
			return list;
		}

	
}
