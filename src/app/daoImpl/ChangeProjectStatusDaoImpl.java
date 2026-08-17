package app.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.ConvergenceWorksBean;
import app.bean.ProjectDetailsBean;
import app.dao.ChangeProjectStatusDao;
import app.model.IwmpMProject;

@Repository
public class ChangeProjectStatusDaoImpl implements ChangeProjectStatusDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getProjectDetails}")
	String getProjectDetails;
	
	@Override
	public List<ProjectDetailsBean> getProjectDetails(Integer st_code, Integer dcode) {
		List<ProjectDetailsBean> list = new ArrayList<ProjectDetailsBean>();
		String hql = getProjectDetails;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger("stcode", st_code);
			query.setInteger("dcode", dcode);
			query.setResultTransformer(Transformers.aliasToBean(ProjectDetailsBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public void updateProjectStatus(List<Integer> projectIds, String username, String ipAddress) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
	    for (Integer projectId : projectIds) {
			try {
				IwmpMProject project = session.load(IwmpMProject.class, projectId);
				if (project != null) {
					project.setProjectStatus("C");
					project.setLastUpdatedBy(username);
					project.setLastUpdatedDate(new Date());
					project.setRequestIp(ipAddress);
					session.saveOrUpdate(project);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
			}
	    }
	    session.getTransaction().commit();
	}

}
