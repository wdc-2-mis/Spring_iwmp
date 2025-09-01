package app.dao.unfreezeImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.dao.unfreeze.UnfreezeAddParaOutcomeDao;
import app.model.Outcome2Data;

@Repository
public class UnfreezeAddParaOutcomeDaoImpl implements UnfreezeAddParaOutcomeDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Outcome2Data> getAddParaOutcomeData(Integer finYrCd, Integer projId) {
		List<Outcome2Data> list = new ArrayList<>();
		Session session = sessionFactory.openSession();
		try {
			Query query = null;
			session.beginTransaction();
			query = session.createQuery("from Outcome2Data where iwmpMFinYear.finYrCd = :finyear and iwmpMProject.projectId = :projid and status = 'C'");
			query.setInteger("finyear", finYrCd);
			query.setInteger("projid", projId);
//			query.setResultTransformer(Transformers.aliasToBean(WdcpmksyQuadTarget.class));
			list = query.list();
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public String unfreezeAddParaOutcomeData(Integer id) {
		String res = "";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Outcome2Data ot = session.load(Outcome2Data.class, id);
			ot.setStatus('D');
			session.saveOrUpdate(ot);
			
			session.getTransaction().commit();
			res = "success";
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			res = "fail";
		}
		return res;
	}

}
