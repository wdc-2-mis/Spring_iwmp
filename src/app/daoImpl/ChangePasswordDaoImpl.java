package app.daoImpl;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.ChangePasswordDao;

@Repository("changePasswordDao")
public class ChangePasswordDaoImpl implements ChangePasswordDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${saveSecondPassword}")
	String saveSecondPassword;

	@Override
	public boolean saveSecondPassword(Integer userRole, Integer stCode, Integer distCode, String project,
			String password) {
		// TODO Auto-generated method stub
		String sql = saveSecondPassword;
		Session session = sessionFactory.getCurrentSession();
		boolean result = false;
		session.beginTransaction();
		int a = 0;
		/*
		 * if(userRole==2) { sql=sql; }if(userRole==8) { sql=sql; }if(userRole==3 ||
		 * userRole==9 || userRole==12 || userRole==15) { sql+=" and st_code=:stCode";
		 * }if(userRole==5) { sql+=" and st_code=:stCode and dCode=:distCode";
		 * }if(userRole==6) {
		 * 
		 * sql+ =" and stCode=:stCode and distCode=:distCode" ;
		 * 
		 * } sql +=")";
		 */
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setInteger("userType", userRole);
			sqlQuery.setString("pwd", password);
			sqlQuery.setInteger("stCode", stCode);
			sqlQuery.setInteger("distCode", distCode);
			sqlQuery.setInteger("projCode", Integer.parseInt(project));

			/*
			 * if ((userRole == 3 || userRole == 9 || userRole == 12 || userRole == 15) &&
			 * stCode > 0) { sqlQuery.setInteger("stCode", stCode); } if ((userRole == 5) &&
			 * stCode > 0) { sqlQuery.setInteger("stCode", stCode);
			 * sqlQuery.setInteger("distCode", distCode); } if (userRole == 6) {
			 * sqlQuery.setInteger("stCode", stCode); sqlQuery.setInteger("distCode",
			 * distCode); sqlQuery.setString("projCode", project);
			 * 
			 * }
			 */
			a = Integer.parseInt(sqlQuery.list().get(0).toString());
			if (a > 0) {
				result = true;
				session.getTransaction().commit();
			}else
				session.getTransaction().rollback();

		} catch (Exception ex) {
			//System.out.println(ex.getMessage());
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			
		}
		return result;
	}

}
