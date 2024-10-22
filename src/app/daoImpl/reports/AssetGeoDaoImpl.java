package app.daoImpl.reports;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.reports.AssetGeoRefBean;
import app.dao.reports.AssetGeoDao;
import app.model.AssetGeoReference;

@SuppressWarnings("deprecation")
@Repository("AssetGeoDao")
public class AssetGeoDaoImpl implements AssetGeoDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getAllGeoRefData}")
	String getAllGeoRefData;
	
	@Value("${getDistWiseGeoRefData}")
	String getDistWiseGeoRefData;
	
	@Value("${getProjWiseGeoRefData}")
	String getProjWiseGeoRefData;
	
	@Value("${getProjWiseGeoRefDetails}")
	String getProjWiseGeoRefDetails;
	
	@Value("${getGeoImages}")
	String getGeoImages;
	
	@SuppressWarnings({ "rawtypes","unchecked" })
	@Override
	public List<AssetGeoRefBean> getGeoRefData() {
		
		List<AssetGeoRefBean> list = new ArrayList<>();
		String hql = getAllGeoRefData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetGeoRefBean.class));
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<AssetGeoRefBean> getDistWiseGeoRefData(int stcode) {
		List<AssetGeoRefBean> list = new ArrayList<>();
		String hql = getDistWiseGeoRefData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetGeoRefBean.class));
			query.setInteger("stcode", stcode);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<AssetGeoRefBean> getProjWiseGeoRefData(int dcode) {
		List<AssetGeoRefBean> list = new ArrayList<>();
		String hql = getProjWiseGeoRefData;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetGeoRefBean.class));
			query.setInteger("dcode", dcode);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<AssetGeoRefBean> getProjWiseGeoRefDetails(int projid) {
		List<AssetGeoRefBean> list = new ArrayList<>();
		String hql = getProjWiseGeoRefDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetGeoRefBean.class));
			query.setInteger("projid", projid);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

	@Override
	public List<AssetGeoRefBean> getGeoImages(String wrkid, String stage) {
		List<AssetGeoRefBean> list = new ArrayList<>();
		String hql = getGeoImages;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AssetGeoRefBean.class));
			query.setString("wrkid", wrkid);
			query.setString("stage", stage);
			list = query.list();
			session.getTransaction().commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return list;
	}

}
