package app.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.AuditReportBean;
import app.dao.AuditDao;
import app.model.UserMap;
import app.model.UserReg;

@Repository("auditDao")
public class AuditDaoImpl implements AuditDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getOnlineUserList}")
	String getOnlineUserList;

	@Value("${CheckLoginuser}")
	String CheckLoginuser;
	HttpSession session;
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, List<AuditReportBean>> getOnlineUserList() {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, List<AuditReportBean>> map = new LinkedHashMap<Integer, List<AuditReportBean>>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = null;
			String hql = CheckLoginuser;
			query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.aliasToBean(AuditReportBean.class));
			//SQLQuery query = ses.createSQLQuery(sql);
			
			List<AuditReportBean> list = query.list();
			//System.out.println("list size: "+list.size()+" : "+list);
			for (AuditReportBean row : list){
				//System.out.println("userId: "+row.getUserid());
			}
			List<AuditReportBean> sublist = new ArrayList<AuditReportBean>();
			if ((list != null) && (list.size() > 0)) {
				for (AuditReportBean row : list){
					if (!map.containsKey(row.getRegId())) {
						sublist = new ArrayList<AuditReportBean>();
						sublist.add(row);
						map.put(row.getRegId(), sublist);
					} else {
						sublist.add(row);
						map.put(row.getRegId(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			session.getTransaction().rollback();
			// e.printStackTrace();
		} catch (Exception ex) {
			System.err.print("Error" + ex.getMessage() + ex.getCause());
			session.getTransaction().rollback();
			// ex.printStackTrace();
		}finally {
			
		}
		return map;
	}

	

}
