package app.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.VillageRecordBean;
import app.dao.VillageRecordDao;

@Repository("VillageRecordDao")
@SuppressWarnings("deprecation")
public class VillageRecordDaoImpl implements VillageRecordDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getVibrantVillageDetails}") 
	String getVibrantVillageDetails1;
	
	
	@SuppressWarnings("rawtypes")
	
	@Override
	public List<VillageRecordBean> getVibrantVillageDetails(Integer stcd, String status) {
		List<VillageRecordBean> getVibrantVillageDetails = new ArrayList<>();
		String hql = getVibrantVillageDetails1;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcd", stcd);
			query.setBoolean("status", Boolean.parseBoolean(status));
			query.setResultTransformer(Transformers.aliasToBean(VillageRecordBean.class));
			getVibrantVillageDetails = query.list();
			session.getTransaction().commit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return getVibrantVillageDetails;
	}

}
