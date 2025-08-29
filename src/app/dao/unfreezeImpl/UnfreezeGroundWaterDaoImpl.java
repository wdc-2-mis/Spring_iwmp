package app.dao.unfreezeImpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Transaction;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.bean.GroundWaterTableBean;
import app.dao.unfreeze.UnfreezeGroundWaterDao;
import app.model.outcome.GroundwaterDetail;
import app.model.outcome.GroundwaterMain;
import app.model.outcome.ShgDetail;
import app.model.outcome.ShgMain;

@Repository
public class UnfreezeGroundWaterDaoImpl implements UnfreezeGroundWaterDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Value("${unfreezeGroundWater}")
    private String unfreezeGroundWater;

    @Value("${unfreezeBaselGroundWater}")
    private String unfreezeBaselGroundWater;

    @Override
    public List<GroundWaterTableBean> unfreezeListGW(Integer proj, String level, Integer dcode, Integer finyr) {
        List<GroundWaterTableBean> getGW = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            SQLQuery query;

            if ("project".equalsIgnoreCase(level)) {
                query = session.createSQLQuery(unfreezeGroundWater);
                query.setInteger("prj", proj);
                query.setInteger("dcode", dcode);
                query.setInteger("finyr", finyr);
            } else if ("basel".equalsIgnoreCase(level)) {
                query = session.createSQLQuery(unfreezeBaselGroundWater);
                query.setInteger("prj", proj);
                query.setInteger("dcode", dcode);
            } else {
                return getGW; // empty if wrong level
            }

            query.setResultTransformer(Transformers.aliasToBean(GroundWaterTableBean.class));
            getGW = query.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return getGW;
    }
    
    @Override
    public boolean unfreezeGWTData(String[] proj_id, String level, Integer dcode, Integer finyr) {
        boolean res = false;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            for (String code : proj_id) {
                if ("basel".equalsIgnoreCase(level)) 
                {
                	SQLQuery query = session.createSQLQuery("update groundwater_detail set status='D' where groundwater_id in(select groundwater_id from groundwater_main where proj_id=:pid and level_time='basel')");
    			    query.setInteger("pid", Integer.parseInt(code));
    			    query.executeUpdate();
    			    
    			    SQLQuery querykd = session.createSQLQuery("update groundwater_main set status='D' where proj_id=:pid and level_time='basel'");
    			    querykd.setInteger("pid", Integer.parseInt(code));
    			    querykd.executeUpdate();
                }
                if ("project".equalsIgnoreCase(level)) {
                	SQLQuery query = session.createSQLQuery("update groundwater_detail set status='D' where groundwater_id in(select groundwater_id from groundwater_main where proj_id=:pid and level_time='project' and fin_yr_cd=:finyr)");
    			    query.setInteger("pid", Integer.parseInt(code));
    			    query.setInteger("finyr", finyr);
    			    query.executeUpdate();
    			    
    			    SQLQuery querykd = session.createSQLQuery("update groundwater_main set status='D' where proj_id=:pid and level_time='project' and fin_yr_cd=:finyr");
    			    querykd.setInteger("pid", Integer.parseInt(code));
    			    querykd.setInteger("finyr", finyr);
    			    querykd.executeUpdate();
                }
            }

            tx.commit();
            res = true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return res;
    }

}
