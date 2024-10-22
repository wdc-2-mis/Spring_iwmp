package app.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.pfms.PfmsInvoiceData;
import app.dao.PfmsInvoiceDao;


@Repository("PfmsInvoiceDao")
public class PfmsInvoiceDaoImpl implements PfmsInvoiceDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${pfmsInvoiceStateData}")
	String pfmsInvoiceStateData;

	@Value("${pfmsInvoiceDistrictData}")
	String pfmsInvoiceDistrictData;
	
	@Value("${pfmsInvoiceInvoiceData}")
	String pfmsInvoiceInvoiceData;
	
	@Value("${pfmsInvoiceData}")
	String pfmsInvoiceData;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PfmsInvoiceData> getPfmsInvoiceStateData(Integer stateCode) {
		// TODO Auto-generated method stub
		List<PfmsInvoiceData> invoiceList=new ArrayList<PfmsInvoiceData>();
		String hql=pfmsInvoiceStateData;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			invoiceList = session.createQuery(hql).setInteger("stCode", stateCode).list();;
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
        return invoiceList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PfmsInvoiceData> getPfmsInvoiceData() {
		// TODO Auto-generated method stub
		List<PfmsInvoiceData> invoiceList=new ArrayList<PfmsInvoiceData>();
		String hql=pfmsInvoiceData;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			invoiceList = session.createQuery(hql).list();;
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
        return invoiceList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PfmsInvoiceData> getPfmsInvoiceDistrictData(Integer distCode) {
		// TODO Auto-generated method stub
		List<PfmsInvoiceData> invoiceList=new ArrayList<PfmsInvoiceData>();
		String hql=pfmsInvoiceDistrictData;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			invoiceList = session.createQuery(hql).setInteger("distCode", distCode).list();;
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
        return invoiceList;
	}

	@SuppressWarnings("unchecked")
	public List<PfmsInvoiceData> getPfmsInvoiceData(Integer invoiceNo) {
		// TODO Auto-generated method stub
		List<PfmsInvoiceData> invoiceList=new ArrayList<PfmsInvoiceData>();
		String hql=pfmsInvoiceInvoiceData;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			invoiceList = session.createQuery(hql).setInteger("invoiceNo", invoiceNo).list();;
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
        return invoiceList;
	}

}
