package app.daoImpl.master;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.master.RemoveActiveUserProjectDao;
import app.model.IwmpMProject;

@Repository("removeActiveUserProjectDao")
public class RemoveActiveUserProjectDaoImpl implements RemoveActiveUserProjectDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Value("${removeProjectActiveUser}") 
	String removeProjectActiveUser;
	
	@Value("${deleteProjectforpiaUser}") 
	String deleteProjectforpiaUser;

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMProject> getUserProjectList(String regId) {
		List<IwmpMProject> result=new ArrayList<IwmpMProject>();
		Session ses = sessionFactory.getCurrentSession();
		try {
			ses.beginTransaction();
			String hql=removeProjectActiveUser;
			result = ses.createQuery(hql).setInteger("regid", Integer.parseInt(regId)).list();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			ses.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			ses.getTransaction().commit();
		}
        return result;
	}

	@Override
	public Integer userProjectRemove(String regId, String project) {
		
		Integer value=0;
		
		try {
			
			String deletemaster=deleteProjectforpiaUser;
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(deletemaster);
			query.setInteger("regid", Integer.parseInt(regId));
			query.setInteger("projcd", Integer.parseInt(project));
			value=query.executeUpdate();
			
			if(value>0) 
			{
				session.getTransaction().commit();
			}
			else {
					session.getTransaction().rollback();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}

}
