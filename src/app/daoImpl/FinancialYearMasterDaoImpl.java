package app.daoImpl;

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

import app.bean.ProjectLocationBean;
import app.dao.FinancialYearMasterDao;
import app.model.IwmpMFinYear;

@Repository("FinancialYearMasterDao")
public class FinancialYearMasterDaoImpl implements FinancialYearMasterDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getAllFinaicialYear}")
	String getAllFinaicialYear;

	@Override
	public LinkedHashMap<Integer, String> getAllFinaicialYear() {
		// TODO Auto-generated method stub
		List<IwmpMFinYear> finYearList=new ArrayList<IwmpMFinYear>();
		LinkedHashMap<Integer, String> finYearMap = new LinkedHashMap<Integer, String>();
		String hql=getAllFinaicialYear;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			finYearList = query.list();
			session.getTransaction().commit();
			for(IwmpMFinYear yr:finYearList) {
				finYearMap.put(yr.getFinYrCd(), yr.getFinYrDesc());
			}
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
		}
        return finYearMap;
	}

}
