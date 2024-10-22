package app.daoImpl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.dao.HitCountDao;
import app.model.IwmpHitCount;

@Repository("HitCountDao")
public class HitCountDaoImpl implements HitCountDao{
	
	@Autowired
	SessionFactory session;
	
	@Value("${getHitCount}")
	String getHitCount;
	
	

	@Override
	public BigInteger getHitCount(String sessionId) {
		// TODO Auto-generated method stub
		BigInteger count=BigInteger.ZERO;
		Session ses = session.getCurrentSession();
		
		Query query = null;
		try {
			IwmpHitCount hitCount = new IwmpHitCount();
			String hql = getHitCount;
			Date date = new Date();  
			Boolean insert=false;
            Timestamp ts=new Timestamp(date.getTime());  
			ses.beginTransaction();
			query = ses.createQuery(hql);
			List<IwmpHitCount> list = query.list();
			if(list.size()>0)
			for(IwmpHitCount cc:list) {
				if(!cc.getSessionId().equals(sessionId))
				count =BigInteger.valueOf(Integer.parseInt(""+cc.getCount())+1);
				else
				count=BigInteger.valueOf(Integer.parseInt(""+cc.getCount()));
				if(count.compareTo(BigInteger.ONE)==1 || count.compareTo(BigInteger.ONE)==0) {
					ses.delete(cc);
					ses.getTransaction().commit();
				}else {
					count=count.add(BigInteger.ONE);
				}
			}
			else
				count=count.add(BigInteger.ONE);
			if(!ses.isOpen()) {
				ses = session.getCurrentSession();
				if(!ses.getTransaction().isActive()) {
					ses.beginTransaction();
			}
			}
			hitCount = new IwmpHitCount();
			
			hitCount.setCount(count);
			hitCount.setInserteddate(ts);
			hitCount.setSessionId(sessionId);
			ses.save(hitCount);
			ses.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error" + e.getCause());
			System.err.print("Hibernate error" + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();
			ses.getTransaction().rollback();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			ses.getTransaction().rollback();
		} 
		finally {
			
		}
		return count;
	}

}
