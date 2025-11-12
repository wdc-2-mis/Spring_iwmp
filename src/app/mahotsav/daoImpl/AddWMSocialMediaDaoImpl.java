package app.mahotsav.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.mahotsav.dao.AddWMSocialMediaDao;
import app.mahotsav.model.WatershedMahotsavRegistration;

@Repository("AddWMSocialMediaDao")
public class AddWMSocialMediaDaoImpl implements  AddWMSocialMediaDao{
	

		@Autowired
		private SessionFactory sessionFactory;
	
	

		 @Override
		    public WatershedMahotsavRegistration findByUserRegNo(String regNo) {
		        Session session = sessionFactory.getCurrentSession();
		        WatershedMahotsavRegistration registration = null;
		        try {
		            session.beginTransaction();

		            registration = (WatershedMahotsavRegistration) session.createQuery(
		                    "FROM WatershedMahotsavRegistration r WHERE r.user_reg_no = :regNo")
		                    .setParameter("regNo", regNo)
		                    .uniqueResult();

		            session.getTransaction().commit();

		        } catch (Exception e) {
		            if (session.getTransaction().isActive()) {
		                session.getTransaction().rollback();
		            }
		            e.printStackTrace();
		        }
		        return registration;
		    }

}
