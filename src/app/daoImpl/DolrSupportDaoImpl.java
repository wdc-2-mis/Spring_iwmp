package app.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.DolrSupportBean;
import app.bean.UOMDataBean;
import app.dao.DolrSupportDao;

@Repository("dolrsupportDao")
public class DolrSupportDaoImpl implements DolrSupportDao{
 
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getdolrsupportdata}")
	String getdolrsupportdata;
	
	@Override
	public LinkedHashMap<Integer, List<DolrSupportBean>> getsupportdata() {
		LinkedHashMap<Integer, List<DolrSupportBean>> map = new LinkedHashMap<Integer, List<DolrSupportBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = getdolrsupportdata;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(DolrSupportBean.class));
			List<DolrSupportBean> list = query.list();
			for (DolrSupportBean row : list){
				//System.out.println("userId: "+row.getHeadcode());
			}
			List<DolrSupportBean> sublist = new ArrayList<DolrSupportBean>();
			if ((list != null) && (list.size() > 0)) {
				for (DolrSupportBean row : list){
					if (!map.containsKey(row.getUserid())) {
						sublist = new ArrayList<DolrSupportBean>();
						sublist.add(row);
						map.put(row.getUserid(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getUserid(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			
		}finally {
			
		}
		return map;
	}

	
}
