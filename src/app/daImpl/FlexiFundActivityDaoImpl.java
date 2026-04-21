package app.daImpl;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
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

import app.bean.FlexiFundMActivityBean;
import app.dao.FlexiFundActivityDao;


@Repository("flexiFundActivityDao")
public class FlexiFundActivityDaoImpl implements FlexiFundActivityDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Value("${getactivitydata}")
    String getactivitydata;
    
    @Value("${saveactivitydata}")
    String saveactivitydata;
    
    @Value("${findactivityeditdata}")
    String findactivityeditdata;
    
    @Value("${updateActivity}")
    String updateActivity;
    
    @Value("${deleteactivitydata}")
    String deleteactivitydata;
    
    
    @Override
    public LinkedHashMap<Integer, List<FlexiFundMActivityBean>> getActivityData() {
        LinkedHashMap<Integer, List<FlexiFundMActivityBean>> map = new LinkedHashMap<Integer, List<FlexiFundMActivityBean>>();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            SQLQuery query = null;
            String hql = getactivitydata;
            query = session.createSQLQuery(hql);
            query.setResultTransformer(Transformers.aliasToBean(FlexiFundMActivityBean.class));
            List<FlexiFundMActivityBean> list = query.list();
            List<FlexiFundMActivityBean> sublist = new ArrayList<FlexiFundMActivityBean>();
            if ((list != null) && (list.size() > 0)) {
                for (FlexiFundMActivityBean row : list) {
                    if (!map.containsKey(row.getAct_id())) {
                        sublist = new ArrayList<FlexiFundMActivityBean>();
                        sublist.add(row);
                        map.put(row.getAct_id(), sublist);
                    } else {
                        sublist.add(row);
                        map.put(row.getAct_id(), sublist);
                    }
                }
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.print("Hibernate error" + e.getMessage());
        } catch (Exception ex) {
            System.err.print("Error" + ex.getMessage() + ex.getCause());
        }
        return map;
    }
    
    @Override
    public Boolean saveActivity(String actname, String loginID) {
        Boolean res = false;
        Integer value = 0;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String savesql = saveactivitydata;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipadd = inetAddress.getHostAddress();
            {
                SQLQuery query = session.createSQLQuery(savesql);
                Date d = new Date();
                query.setString("actname", actname);
                query.setString("lastupdatedby", loginID);
                query.setDate("lastupdateddate", d);
                query.setString("requestid", ipadd);
                
                value = query.executeUpdate();
                if (value > 0) {
                    res = true;
                } else {
                    session.getTransaction().rollback();
                    return false;
                }
            }
            if (res)
                session.getTransaction().commit();
            else
                session.getTransaction().rollback();
                
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        return res;
    }
    
    @Override
    public List<FlexiFundMActivityBean> editActivityData(int id) {
        List<FlexiFundMActivityBean> editActivityData = new ArrayList<FlexiFundMActivityBean>();
        
        String hql = findactivityeditdata;
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            
            SQLQuery query = session.createSQLQuery(hql);
            query.setInteger("act_id", id);
            query.setResultTransformer(Transformers.aliasToBean(FlexiFundMActivityBean.class));
            editActivityData = query.list();
            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.print("Hibernate error");
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return editActivityData;
    }
    
    @Override
    public Boolean updateActivityData(int id, String actname, String loginID) {
        Boolean res = false;
        Integer value = 0;
        String savesql = updateActivity;
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipadd = inetAddress.getHostAddress();
            {
                SQLQuery query = session.createSQLQuery(savesql);
                Date d = new Date();
                query.setString("actname", actname);
                query.setString("lastupdatedby", loginID);
                query.setDate("lastupdateddate", d);
                query.setString("ipadd", ipadd);
                query.setInteger("id", id);
                value = query.executeUpdate();
                if (value > 0) {
                    res = true;
                } else {
                    session.getTransaction().rollback();
                    return false;
                }
            }
            if (res)
                session.getTransaction().commit();
            else
                session.getTransaction().rollback();
                
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        return res;
    }
    
    @Override
    public Boolean deleteActivityData(int id) {
        Boolean res = false;
        Integer value = 0;
        String savesql = deleteactivitydata;
        String resetSequenceSQL = "SELECT setval('flexi_fund_activity_master_act_id_seq', COALESCE((SELECT MAX(act_id) FROM flexi_fund_activity_master), 0) + 1, false)";
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        try {
            SQLQuery deleteQuery = session.createSQLQuery(savesql);
            deleteQuery.setInteger("id", id);
            value = deleteQuery.executeUpdate();
            
            if(value > 0) {
                SQLQuery resetQuery = session.createSQLQuery(resetSequenceSQL);
                resetQuery.uniqueResult();
                res = true;
                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
                return false;
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
        return res;
    }

}
