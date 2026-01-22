package app.mahotsav.daoImpl;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.common.CommonFunctions;
import app.mahotsav.bean.WMMediaViewsDetailsBean;
import app.mahotsav.bean.WatershedMahotsavBean;
import app.mahotsav.dao.WatershedMahotsavDao;
import app.mahotsav.model.WatershedMahotsavProjectLvlPhoto;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.model.WatershedMahotsavVideoDetails;
import app.mahotsav.model.WatershedMahotsavVideoViewDetails;
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
	
	@Value("${getWatershedMahotsavVideoDetails}")
	String getWatershedMahotsavVideoDetails;
	
	@Autowired
	CommonFunctions commonFunction;
	
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
		
		
		@Override
		public List<WatershedMahotsavBean> getWatershedMahotsavVideoDetails(String regno) {
			List<WatershedMahotsavBean> list = new ArrayList<WatershedMahotsavBean>();
			String hql = getWatershedMahotsavVideoDetails;
			Session session = sessionFactory.openSession();
			try {
				session.beginTransaction();
				Query query = session.createSQLQuery(hql);
				query.setParameter("regno", regno);
				query.setResultTransformer(Transformers.aliasToBean(WatershedMahotsavBean.class));
				list = query.list();
				session.getTransaction().commit();
			}catch(Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			}
			return list;
		}

		@Override
		public String saveWMMediaViewDetails(WMMediaViewsDetailsBean bean, HttpServletRequest request) {
			String result = "fail";
			String filePath="D:\\wmMediaViewsScreenshot\\";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/mahotsavdoc/wmMediaViewsScreenshot/";
			// String filePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/TESTING/mahotsavdoc/wmMediaViewsScreenshot/";
			
			Session session = sessionFactory.openSession();
			try {
				session.beginTransaction();
				WatershedMahotsavVideoDetails mvd =  session.load(WatershedMahotsavVideoDetails.class, bean.getVideoid());
				
				if(mvd != null) {
					WatershedMahotsavVideoViewDetails vvd =  session.get(WatershedMahotsavVideoViewDetails.class,mvd.getVideoDetailId());
					if(vvd != null) {
						if (vvd.getMediaViewUrl() != null && !vvd.getMediaViewUrl().isEmpty()) 
			            {
			                File file = new File(vvd.getMediaViewUrl());
			                if (file.exists()) 
			                {
			                    if (file.delete()) {
			                        System.out.println("Deleted file: " + file.getAbsolutePath());
			                    } else {
			                        System.out.println("Failed to delete file: " + file.getAbsolutePath());
			                    }
			                } 
			                else {
			                    System.out.println("File not found: " + file.getAbsolutePath());
			                }
			            }
						commonFunction.uploadFileMahotwavInauguration(bean.getPhotos_screenshot(), filePath, "", "", bean.getVideoid());
						vvd.setMediaViewUrl(filePath+"I"+""+""+bean.getVideoid()+"_"+bean.getPhotos_screenshot().getOriginalFilename());
						vvd.setNoOfLikes(bean.getNo_of_likes());
						vvd.setNoOfComments(bean.getNo_of_comments());
						vvd.setNoOfViews(bean.getNo_of_views());
						vvd.setNoOfShares(bean.getNo_of_shares());
						vvd.setUpdatedDate(LocalDate.now());
						vvd.setRequestedIp(getClientIpAddr(request));
						session.saveOrUpdate(vvd);
						session.getTransaction().commit();
						result = "updated";
					}else {
						vvd = new WatershedMahotsavVideoViewDetails();
						commonFunction.uploadFileMahotwavInauguration(bean.getPhotos_screenshot(), filePath, "", "", bean.getVideoid());
						vvd.setVideoDetailId(mvd.getVideoDetailId());
						vvd.setVideoDetails(mvd);
						vvd.setMediaViewUrl(filePath+"I"+""+""+bean.getVideoid()+"_"+bean.getPhotos_screenshot().getOriginalFilename());
						vvd.setNoOfLikes(bean.getNo_of_likes());
						vvd.setNoOfComments(bean.getNo_of_comments());
						vvd.setNoOfViews(bean.getNo_of_views());
						vvd.setNoOfShares(bean.getNo_of_shares());
						vvd.setStatus('D');
						vvd.setCreatedDate(LocalDate.now());
						vvd.setRequestedIp(getClientIpAddr(request));
						session.save(vvd);
						session.getTransaction().commit();
						result = "success";
					}
					
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				result = "fail";
			}
			return result;
		}

		@SuppressWarnings("deprecation")
		@Override
		public List<WMMediaViewsDetailsBean> getWMMediaViewsDetails(String regno, Integer videoid) {
			List<WMMediaViewsDetailsBean> list = new ArrayList<WMMediaViewsDetailsBean>();
			List<WatershedMahotsavVideoViewDetails> listDtl = new ArrayList<WatershedMahotsavVideoViewDetails>();
			Session session = sessionFactory.openSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery("from WatershedMahotsavVideoViewDetails where videoDetailId = :videoid");
				query.setParameter("videoid", videoid);
				listDtl = query.list();
				
				session.getTransaction().commit();
				if(listDtl.size()>0) {
					listDtl.forEach(s->{
						WMMediaViewsDetailsBean bean = new WMMediaViewsDetailsBean();
						bean.setRegno(regno);
						bean.setVideoid(videoid);
						bean.setNo_of_likes(s.getNoOfLikes());
						bean.setNo_of_comments(s.getNoOfComments());
						bean.setNo_of_views(s.getNoOfViews());
						bean.setNo_of_shares(s.getNoOfShares());
						bean.setStatus(s.getStatus());
						list.add(bean);
					});
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			}
			return list;
		}

		@Override
		public String comWMMediaViewDetails(String regno, Integer videoid) {
			String result = "fail";
			Session session = sessionFactory.openSession();
			try {
				session.beginTransaction();
				WatershedMahotsavVideoDetails mvd =  session.load(WatershedMahotsavVideoDetails.class, videoid);
				
				if(mvd != null) {
					WatershedMahotsavVideoViewDetails vvd =  session.get(WatershedMahotsavVideoViewDetails.class,mvd.getVideoDetailId());
					if(vvd != null) {
						vvd.setStatus('C');
						session.saveOrUpdate(vvd);
					}
					
				}
				session.getTransaction().commit();
				result = "success";
				
			}catch(Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				result = "fail";
			}
			return result;
		}

		@Override
		public List<String> getWMMediaScrnshtUrl(Integer videoid) {
			
			Session session = sessionFactory.getCurrentSession();
			List<WatershedMahotsavVideoViewDetails> list = new ArrayList<WatershedMahotsavVideoViewDetails>();
			List<String> imgList = new ArrayList<>();
			try {
				session.beginTransaction();
				Query query = session.createQuery("from WatershedMahotsavVideoViewDetails where videoDetailId = :id");
				query.setInteger("id", videoid);
				list = query.list();
				for (WatershedMahotsavVideoViewDetails photo : list) 
				{
					//server
					//imgList.add(photo.getMediaViewUrl().substring(photo.getMediaViewUrl().lastIndexOf("/")+1));
					//System.out.println(" kdy= "+photo.getMediaViewUrl().substring(photo.getMediaViewUrl().lastIndexOf("/")+1));
					
					//local
					imgList.add(photo.getMediaViewUrl().replaceAll(".*\\\\", ""));
//					System.out.println(" kdy= "+photo.getMediaViewUrl().replaceAll(".*\\\\", ""));
				}
				
				session.getTransaction().commit();
			}
			catch(Exception ex) {
				session.getTransaction().rollback();
				ex.printStackTrace();
			}
			return imgList;
		}

	
}
