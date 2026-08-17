package app.daoImpl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
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

	    Session ses = session.getCurrentSession();

	    try {

	        if (!ses.getTransaction().isActive()) {
	            ses.beginTransaction();
	        }

	        LocalDate today =
	                LocalDate.now(ZoneId.of("Asia/Kolkata"));

	        java.sql.Date sqlToday =
	                java.sql.Date.valueOf(today);

	       
	        String visitorCheckSql =
	                "SELECT hit_count_id " +
	                "FROM iwmp_hit_visitor " +
	                "WHERE session_id = :sessionId " +
	                "AND visited_date = :today";

	        Query visitorCheckQuery =
	                ses.createSQLQuery(visitorCheckSql);

	        visitorCheckQuery.setParameter(
	                "sessionId",
	                sessionId
	        );

	        visitorCheckQuery.setParameter(
	                "today",
	                sqlToday
	        );

	        Object existingHitCountId =
	                visitorCheckQuery.uniqueResult();

	        if (existingHitCountId != null) {

	            String countSql =
	                    "SELECT count " +
	                    "FROM iwmp_hit_count " +
	                    "WHERE id = :id";

	            Query countQuery =
	                    ses.createSQLQuery(countSql);

	            countQuery.setParameter(
	                    "id",
	                    ((Number) existingHitCountId).intValue()
	            );

	            Number count =
	                    (Number) countQuery.uniqueResult();

	            ses.getTransaction().commit();

	            return BigInteger.valueOf(
	                    count.longValue()
	            );
	        }
            String dailySql =
	                "SELECT id " +
	                "FROM iwmp_hit_count " +
	                "WHERE hit_date = :today";

	        Query dailyQuery =
	                ses.createSQLQuery(dailySql);

	        dailyQuery.setParameter(
	                "today",
	                sqlToday
	        );

	        Number hitCountId =
	                (Number) dailyQuery.uniqueResult();

	        /*
	         * First visitor of the day.
	         */
	        if (hitCountId == null) {

	            String insertDailySql =
	                    "INSERT INTO iwmp_hit_count " +
	                    "(count, inserteddate, hit_date) " +
	                    "VALUES (0, CURRENT_TIMESTAMP, :hitDate) " +
	                    "ON CONFLICT (hit_date) DO NOTHING";

	            Query insertDailyQuery =
	                    ses.createSQLQuery(insertDailySql);

	            insertDailyQuery.setParameter(
	                    "hitDate",
	                    sqlToday
	            );

	            insertDailyQuery.executeUpdate();

	            String getDailyIdSql =
	                    "SELECT id " +
	                    "FROM iwmp_hit_count " +
	                    "WHERE hit_date = :today";

	            Query getDailyIdQuery =
	                    ses.createSQLQuery(getDailyIdSql);

	            getDailyIdQuery.setParameter(
	                    "today",
	                    sqlToday
	            );
	            
	            hitCountId =
	                    (Number) getDailyIdQuery.uniqueResult();
	        }

	
	        String visitorInsertSql =
	                "INSERT INTO iwmp_hit_visitor " +
	                "(hit_count_id, session_id, visited_date) " +
	                "VALUES (:hitCountId, :sessionId, :today) " +
	                "ON CONFLICT (session_id, visited_date) " +
	                "DO NOTHING";

	        Query visitorInsertQuery =
	                ses.createSQLQuery(visitorInsertSql);

	        visitorInsertQuery.setParameter(
	                "hitCountId",
	                hitCountId.intValue()
	        );

	        visitorInsertQuery.setParameter(
	                "sessionId",
	                sessionId
	        );

	        visitorInsertQuery.setParameter(
	                "today",
	                sqlToday
	        );

	        int inserted =
	                visitorInsertQuery.executeUpdate();

	        if (inserted > 0) {

	            String updateSql =
	                    "UPDATE iwmp_hit_count " +
	                    "SET count = count + 1 " +
	                    "WHERE id = :id " +
	                    "RETURNING count";

	            Query updateQuery =
	                    ses.createSQLQuery(updateSql);

	            updateQuery.setParameter(
	                    "id",
	                    hitCountId.intValue()
	            );

	            Number updatedCount =
	                    (Number) updateQuery.uniqueResult();

	            ses.getTransaction().commit();

	            return BigInteger.valueOf(
	                    updatedCount.longValue()
	            );
	        }

	         String finalCountSql =
	                "SELECT count " +
	                "FROM iwmp_hit_count " +
	                "WHERE id = :id";

	        Query finalCountQuery =
	                ses.createSQLQuery(finalCountSql);

	        finalCountQuery.setParameter(
	                "id",
	                hitCountId.intValue()
	        );

	        Number finalCount =
	                (Number) finalCountQuery.uniqueResult();

	        ses.getTransaction().commit();

	        return BigInteger.valueOf(
	                finalCount.longValue()
	        );

	    }
	    catch (Exception e) {

	        e.printStackTrace();

	        if (ses.getTransaction().isActive()) {
	            ses.getTransaction().rollback();
	        }

	        throw e;
	    }
	}

}
