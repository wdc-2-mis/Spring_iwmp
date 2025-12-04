package app.mahotsav.daoImpl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.mahotsav.dao.WatershedMahotsavDao;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.model.WatershedMahotsavVideoDetails;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;

@Repository("watershedMahotsavDao")
public class WatershedMahotsavDaoImpl implements WatershedMahotsavDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${getMahotsavUserDtl}")
	String getMahotsavUserDtl;
	
	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	@Override
	public String saveMahotsaveData(String name, String phone, String email, String address, int state, int district,
	        int block, int village, String longitude, String latitude, String facebook, String youtube,
	        String instagram, String twitter, String linkedin, String regNoParam, String mediaType, HttpServletRequest request) {

	    String regNo = "";
	    Session ses = sessionFactory.getCurrentSession();

	    try {
	        ses.beginTransaction();

	        WatershedMahotsavRegistration registration;

	        if (regNoParam != null && !regNoParam.trim().isEmpty()) {
	            registration = (WatershedMahotsavRegistration) ses
	                    .createQuery("FROM WatershedMahotsavRegistration WHERE user_reg_no = :regNo")
	                    .setParameter("regNo", regNoParam)
	                    .uniqueResult();

	            if (registration == null) {
	                registration = new WatershedMahotsavRegistration();
	                registration.setRegName(name);
	                registration.setPhno(phone);
	                registration.setEmail(email);
	                registration.setAddress(address);
	                registration.setCreatedDate(new Date());
	                registration.setRequestedIp(getClientIpAddr(request));

	                regNo = generateRegNo(state, district);
	                registration.setUser_reg_no(regNo);

	                ses.save(registration);
	            } else {
	                regNo = registration.getUser_reg_no();
	            }
	        } 
	        else {
	            registration = new WatershedMahotsavRegistration();
	            registration.setRegName(name);
	            registration.setPhno(phone);
	            registration.setEmail(email);
	            registration.setAddress(address);
	            registration.setCreatedDate(new Date());
	            registration.setRequestedIp(getClientIpAddr(request));

	            regNo = generateRegNo(state, district);
	            registration.setUser_reg_no(regNo);

	            ses.save(registration);
	        }

	        saveVideoIfPresent(ses, facebook, "Facebook", 1, state, district, block, village, longitude, latitude, registration, mediaType);
	        saveVideoIfPresent(ses, youtube, "YouTube", 2, state, district, block, village, longitude, latitude, registration, mediaType);
	        saveVideoIfPresent(ses, instagram, "Instagram", 3, state, district, block, village, longitude, latitude, registration, mediaType);
	        saveVideoIfPresent(ses, twitter, "Twitter", 4, state, district, block, village, longitude, latitude, registration, mediaType);
	        saveVideoIfPresent(ses, linkedin, "LinkedIn", 5, state, district, block, village, longitude, latitude, registration, mediaType);

	        ses.getTransaction().commit();

	    } catch (Exception e) {
	        if (ses.getTransaction().isActive()) {
	            ses.getTransaction().rollback();
	        }
	        e.printStackTrace();
	        regNo = null;
	    }

	    return regNo;
	}


	
	private String generateRegNo(int state, int district) {
        Random random = new Random();
        int randomNum = 10000 + random.nextInt(90000);
        return "WM" + state + district + randomNum;
    }
	
	private void saveVideoIfPresent(Session ses, String url, String platform, int mediaId, int state, int district,
            int block, int village, String longitude, String latitude,
            WatershedMahotsavRegistration registration, String mediaType) {
          if (url != null && !url.trim().isEmpty()) {

       if (!isValidPlatformUrl(platform, url)) {
        throw new IllegalArgumentException("Invalid URL for " + platform + ": " + url);
      }

       WatershedMahotsavVideoDetails video = new WatershedMahotsavVideoDetails();
       video.setMediaUrl(url);
       video.setLatitude(latitude);
       video.setLongitute(longitude);
       video.setCreatedDate(new Date());
       video.setStatus("PENDING");
       video.setMedia_type(mediaType);
       video.setRequestedIp(registration.getRequestedIp());
       video.setMahotsavReg(registration);

       video.setIwmpState(ses.get(IwmpState.class, state));
       video.setIwmpDistrict(ses.get(IwmpDistrict.class, district));
       video.setIwmpBlock(ses.get(IwmpBlock.class, block));
       video.setIwmpVillage(ses.get(IwmpVillage.class, village));

       ses.createNativeQuery(
           "UPDATE watershed_mahotsav_video_details SET media = :mediaId WHERE video_detail_id = :videoId")
           .setParameter("mediaId", mediaId)
           .setParameter("videoId", ses.save(video))
           .executeUpdate();
   }
}

         private boolean isValidPlatformUrl(String platform, String url) {
         url = url.toLowerCase();
         switch (platform.toLowerCase()) {
         case "facebook":
         return url.contains("facebook.com");
         case "youtube":
         return url.contains("youtube.com") || url.contains("youtu.be");
         case "instagram":
         return url.contains("instagram.com");
         case "twitter":
         return url.contains("x.com");
         case "linkedin":
         return url.contains("linkedin.com");
         default:
         return false;
     }
   }

         @Override
         public WatershedMahotsavRegistration findByRegNo(String regNo) {
             Session session = sessionFactory.getCurrentSession();
             WatershedMahotsavRegistration registration = null;

             try {
                 session.beginTransaction();

                 registration = (WatershedMahotsavRegistration) session
                         .createQuery(getMahotsavUserDtl)
                         .setParameter("regNo", regNo)
                         .uniqueResult();

                 session.getTransaction().commit();
             } catch (Exception e) {
                 if (session.getTransaction().isActive()) {
                     session.getTransaction().rollback();
                 }
                 e.printStackTrace();
             }

             return registration;
         }


         @Override
         public boolean emailAlreadyExists(String email) {
             boolean exists = false;
             Session session = sessionFactory.openSession();
             try {
                 String hql = "SELECT COUNT(r) FROM WatershedMahotsavRegistration r WHERE LOWER(r.email) = :email";
                 Long count = (Long) session.createQuery(hql)
                         .setParameter("email", email.toLowerCase())
                         .uniqueResult();
                 exists = (count != null && count > 0);
             } catch (Exception e) {
                 e.printStackTrace();
             } finally {
                 session.close();
             }
             return exists;
         }

		@Override
		public boolean phoneAlreadyExists(String phone) {
			boolean exists = false;
            Session session = sessionFactory.openSession();
            try {
                String hql = "SELECT COUNT(r) FROM WatershedMahotsavRegistration r  WHERE r.phno = :phone";
                Long count = (Long) session.createQuery(hql)
                        .setParameter("phone", phone)
                        .uniqueResult();
                exists = (count != null && count > 0);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
            return exists;
		}

		@Override
		public boolean mediaAlreadyExists(String media) {
			boolean exists = false;
            Session session = sessionFactory.openSession();
            try {
                String hql = "SELECT COUNT(r) FROM WatershedMahotsavVideoDetails r  WHERE r.mediaUrl = :media";
                Long count = (Long) session.createQuery(hql)
                        .setParameter("media", media)
                        .uniqueResult();
                exists = (count != null && count > 0);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
            return exists;
		}

		@Override 
		  public List<WatershedMahotsavVideoDetails> findAllMahotsaveVideo()
		  { 
			  Session session = sessionFactory.openSession(); 
			  return session.createQuery("FROM WatershedMahotsavVideoDetails", WatershedMahotsavVideoDetails.class ).getResultList(); 
		  }
		 

		/*
		 * @Override public List<WatershedMahotsavVideoDetails> findAllMahotsaveVideo()
		 * {
		 * 
		 * Session session = sessionFactory.getCurrentSession();
		 * session.beginTransaction(); List<WatershedMahotsavVideoDetails> list = new
		 * ArrayList<WatershedMahotsavVideoDetails>(); try { list = session
		 * .createQuery("FROM WatershedMahotsavVideoDetails where media_type is null",
		 * WatershedMahotsavVideoDetails.class) .list();
		 * 
		 * for (WatershedMahotsavVideoDetails item : list) {
		 * 
		 * String url = item.getMediaUrl(); String detectedType =
		 * MediaTypeDetector.detectMediaType(url); // VIDEO / IMAGE / NA
		 * 
		 * String dbMediaType = item.getMedia_type();
		 * 
		 * // Only update when NULL/Empty if (dbMediaType == null ||
		 * dbMediaType.trim().isEmpty()) {
		 * 
		 * if ("Video".equalsIgnoreCase(detectedType)) { item.setMedia_type("VB"); //
		 * Video → VB session.update(item);
		 * 
		 * } else if ("Image".equalsIgnoreCase(detectedType)) {
		 * item.setMedia_type("PB"); // Image → PB session.update(item);
		 * 
		 * }
		 * 
		 * } } session.getTransaction().commit(); }
		 * 
		 * catch (Exception e) { e.printStackTrace();
		 * session.getTransaction().rollback(); } return list; }
		 */


	
}
