package app.daoImpl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;

import app.bean.FlexiFundMActivityBean;
import app.dao.FlexFundDao;
import app.model.FlexFundActivityMaster;
import app.model.FlexiFundActivityExpProgress;
import app.model.FlexiFundDetails;
import app.model.FlexiFundPhoto;
import app.model.IwmpDistrict;
import app.model.IwmpMProject;
import app.model.UserMap;
import app.model.master.IwmpGramPanchayat;

@Repository("FlexFundDao")
public class FlexFundDaoImpl implements FlexFundDao{

	@Value("${flexiactivity}")
	String flexiactivity;
	
	@Value("${getFlexiFundGramPanchayat}")
	String getFlexiFundGramPanchayat;
	
	@Value("${flexiFundUtilizationStRpt}")
    String flexiFundUtilizationStRptData;
	
	@Value("${completeFlexiFund}")
    String completeFlexiFund;
	
	@Value("${progressFlexiFund}")
    String progressFlexiFund;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public LinkedHashMap<Integer, String> getflexiactivity() {
		List<FlexFundActivityMaster> fundList = new ArrayList<FlexFundActivityMaster>();
		String hql=flexiactivity;
		LinkedHashMap<Integer, String> flexiactivity=new LinkedHashMap<Integer, String>();
		
		Session session = sessionFactory.openSession();
		try {
			
			Transaction tx = session.beginTransaction();
				Query query = session.createQuery(hql);
				fundList = query.list();
				for (FlexFundActivityMaster list : fundList) 
				{
					flexiactivity.put(list.getActId(), list.getActName());
				}
				
		} 
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			session.getTransaction().commit();
		}
        return flexiactivity;
	}

	@Override
	public LinkedHashMap<String, Integer> getFlexiFundGM(int pCode) {
		List<IwmpGramPanchayat> gpList=new ArrayList<IwmpGramPanchayat>();
		String hql=getFlexiFundGramPanchayat;
		LinkedHashMap<String, Integer> gpMap=new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		try {
			 tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setInteger("projectId", pCode);
			gpList = query.list();
			
			for (IwmpGramPanchayat gp : gpList) {
				gpMap.put( gp.getGramPanchayatName(), gp.getGcode());
			}
			tx.commit();
		} 
		catch (HibernateException e) {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    } catch (Exception ex) {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }
	        ex.printStackTrace();
	    }
        return gpMap;
	}

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
	public boolean saveFlexiFundData(Integer projId, Integer gcode,
	        List<Integer> activityList,
	        List<String> detailsList,
	        List<Double> estCostList,
	        List<Double> costList,
	        List<MultipartFile> photos,
	        List<Integer> photoCountList,
	        List<String> latitudeList,
	        List<String> longitudeList,
	        String status,
	        List<String> remarksList,
	        String createdBy,
	        HttpServletRequest request) {

		Session sess = sessionFactory.getCurrentSession();
	    boolean res = false;

	    try {

	        sess.beginTransaction();

	        String filePath = "D:\\FlexiFund\\";
	        File dir = new File(filePath);
	        if (!dir.exists()) dir.mkdirs();

	        IwmpMProject project = sess.get(IwmpMProject.class, projId);
	        IwmpGramPanchayat panchayat = sess.get(IwmpGramPanchayat.class, gcode);

	        List<String> savedFileNames = new ArrayList<>();

	        for (MultipartFile file : photos) {

	            if (!file.isEmpty()) {

	                String originalName = file.getOriginalFilename().replaceAll("\\s+", "_");

	                String fileName = projId + "_" + gcode +  "_" + originalName;

	                File dest = new File(filePath + fileName);
	                file.transferTo(dest);

	                savedFileNames.add(fileName);
	            }
	        }

	        Map<Integer, List<Integer>> rowPhotoMap = new HashMap<>();

	        for (int i = 0; i < photoCountList.size(); i++) {

	            int rowId = photoCountList.get(i);

	            rowPhotoMap
	                .computeIfAbsent(rowId, k -> new ArrayList<>())
	                .add(i);
	        }

	        for (int i = 0; i < activityList.size(); i++) {

	            FlexiFundDetails details = new FlexiFundDetails();

	            details.setProject(project);
	            details.setGcode(panchayat);

	            FlexFundActivityMaster activity =
	                    sess.get(FlexFundActivityMaster.class, activityList.get(i));

	            details.setActivity(activity);
	            details.setWorkDesc(detailsList.get(i));
	            details.setEst_cost(estCostList.get(i));
	            details.setFfCost(costList.get(i));
	            details.setStatus(status);
	            String remark = "";
	            if (remarksList != null && remarksList.size() > i) {
	                remark = remarksList.get(i);
	            }
	            details.setRemark(remark);
	            details.setCreatedBy(createdBy);
	            details.setRequestedIp(getClientIpAddr(request));

	            sess.save(details);

	            List<Integer> photoIndexes = rowPhotoMap.get(i);

	            if (photoIndexes != null) {

	                for (Integer idx : photoIndexes) {

	                    FlexiFundPhoto photo = new FlexiFundPhoto();

	                    photo.setFlexiFundDetails(details);
	                    photo.setPhotoUrl(savedFileNames.get(idx));

	                    if (latitudeList != null && latitudeList.size() > idx) {
	                        photo.setLatitude(latitudeList.get(idx));
	                    }

	                    if (longitudeList != null && longitudeList.size() > idx) {
	                        photo.setLongitude(longitudeList.get(idx));
	                    }

	                    photo.setPhotoTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
	                    photo.setCreatedBy(createdBy);
	                    photo.setRequestedIp(getClientIpAddr(request));

	                    sess.save(photo);
	                }
	            }
	        }

	        sess.getTransaction().commit();
	        res = true;

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        sess.getTransaction().rollback();
	    }

	    return res;
	}

	@Override
	public List<FlexiFundDetails> getFlexiFundByProjectAndGcode(Integer projId, Integer gcode) {
	    List<FlexiFundDetails> result = new ArrayList<>();
	    Session ses = null;
	    Transaction tx = null;

	    try {
	        ses = sessionFactory.openSession();
	        tx = ses.beginTransaction();

	        String hql = "SELECT DISTINCT d FROM FlexiFundDetails d " +
	                     "LEFT JOIN FETCH d.photos " +
	                     "LEFT JOIN FETCH d.activity " + // Add this line
	                     "WHERE d.project.id = :projId AND d.gcode.id = :gcode";

	        result = ses.createQuery(hql, FlexiFundDetails.class)
	                    .setParameter("projId", projId)
	                    .setParameter("gcode", gcode)
	                    .getResultList();

	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    } finally {
	        if (ses != null) ses.close();
	    }
	    return result;
	}

	@Override
	public boolean updateFlexiFundData(
	        List<Integer> rowIds,
	        List<Integer> activityList,
	        List<String> detailsList,
	        List<Double> estCostList,
	        List<Double> costList,
	        List<MultipartFile> photos,
	        List<Integer> photoRowIndex,
	        String status,
	        List<String> remarksList,
	        String updatedBy) {

	    Session session = sessionFactory.getCurrentSession();
	    boolean flag = false;

	    try {

	        session.beginTransaction();

	        String filePath = "D:\\FlexiFund\\";
	        File dir = new File(filePath);
	        if (!dir.exists()) dir.mkdirs();

	        for (int i = 0; i < rowIds.size(); i++) {

	            Integer id = rowIds.get(i);

	            FlexiFundDetails entity = session.get(FlexiFundDetails.class, id);

	            if (entity != null) {

	            	FlexFundActivityMaster activity =
		                    session.get(FlexFundActivityMaster.class, activityList.get(i));
	            	
	                entity.setActivity(activity);
	                entity.setWorkDesc(detailsList.get(i));
	                entity.setEst_cost(estCostList.get(i));
	                entity.setFfCost(costList.get(i));
	                entity.setStatus(status);
	                String remark = "";
		            if (remarksList != null && remarksList.size() > i) {
		                remark = remarksList.get(i);
		            }
		            entity.setRemark(remark);
		            
	                entity.setUpdatedBy(updatedBy);
	                entity.setUpdatedDate(new Date());

	                session.update(entity);

	                if (photos != null && photoRowIndex != null) {

	                    for (int j = 0; j < photos.size(); j++) {

	                        if (photoRowIndex.get(j).equals(id)) {

	                            MultipartFile file = photos.get(j);
	                            
	                            if (!file.isEmpty()) {
	                            	String originalName = file.getOriginalFilename();
	                                String finalName;
	                               
	                                if (originalName.startsWith(entity.getProject() + "_" + entity.getGcode() + "_")) {
	                                    finalName = originalName;
	                                } else {
	                                    finalName = entity.getProject() + "_" +
	                                                entity.getGcode() + "_" +
	                                                originalName;
	                                }
	                                
	                                File dest = new File(filePath + finalName);
	                                file.transferTo(dest);

	                                FlexiFundPhoto photo = new FlexiFundPhoto();
	                                photo.setFlexiFundDetails(entity);
	                                photo.setPhotoUrl(finalName);

	                                session.save(photo);
	                            }
	                        }
	                    }
	                }
	                
	                
	            }
	        }

	        session.getTransaction().commit();
	        flag = true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        session.getTransaction().rollback();
	    }

	    return flag;
	}
	
	@Override
	public boolean deletePhotoById(Integer photoId) {

		Session session = sessionFactory.getCurrentSession();
	    boolean flag = false;

	    try {
	        session.beginTransaction();

	        String photoUrl = (String) session.createNativeQuery(
	                "SELECT photo_url FROM flexi_fund_photo WHERE ff_photo_id = :id")
	                .setParameter("id", photoId)
	                .uniqueResult();

	        if (photoUrl != null) {

	            String filePath = "D:\\FlexiFund\\" + photoUrl;

	            File file = new File(filePath);
	            if (file.exists()) {
	                file.delete();
	                System.out.println("File deleted: " + filePath);
	            } else {
	                System.out.println("File not found: " + filePath);
	            }

	            session.createNativeQuery(
	                    "DELETE FROM flexi_fund_photo WHERE ff_photo_id = :id")
	                    .setParameter("id", photoId)
	                    .executeUpdate();

	            flag = true;
	        }

	        session.getTransaction().commit();

	    } catch (Exception e) {
	        e.printStackTrace();
	        session.getTransaction().rollback();
	    }

	    return flag;
	}

	@Override
	public Map<String, Object> savePhoto(MultipartFile file, int flexiFundId, String lat, String lon, Integer projId, Integer gcode) {

		Map<String, Object> response = new HashMap<>();
	    Session session = sessionFactory.getCurrentSession();

	    try {
	        session.beginTransaction();

	        String uploadDir = "D:\\FlexiFund\\";
	        File dir = new File(uploadDir);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        String originalName = file.getOriginalFilename();

	        String uniqueName = projId + "_" + gcode + "_" + originalName;

	        File destination = new File(uploadDir + uniqueName);

	        file.transferTo(destination);

	        FlexiFundDetails flex = session.get(FlexiFundDetails.class, flexiFundId);

	        FlexiFundPhoto photo = new FlexiFundPhoto();
	        photo.setFlexiFundDetails(flex);
	        photo.setPhotoUrl(uniqueName);
	        photo.setLatitude(lat);
	        photo.setLongitude(lon);

	        Integer photoId = (Integer) session.save(photo);

	        session.getTransaction().commit();

	        response.put("photoId", photoId);
	        response.put("photoUrl", uniqueName);

	    } catch (Exception e) {
	        e.printStackTrace();
	        session.getTransaction().rollback();
	        response.put("error", "fail");
	    }

	    return response;
	}


	@Override
	public List<FlexiFundMActivityBean> getStateWiseFlexiFundReport() {
		List<FlexiFundMActivityBean> getStateWiseFlexiFundReport = new ArrayList<FlexiFundMActivityBean>();
        
        String hql = flexiFundUtilizationStRptData;
        Session session = sessionFactory.getCurrentSession();
       
        try {
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(hql);
            query.setResultTransformer(Transformers.aliasToBean(FlexiFundMActivityBean.class));
            getStateWiseFlexiFundReport = query.list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        return getStateWiseFlexiFundReport;
	}
	
	public boolean deleteFlexiFundRow(int id) {
	    Session session = sessionFactory.getCurrentSession();
	    Transaction tx = null; // Track transaction to commit/rollback

	    try {
	        tx = session.beginTransaction();

	        Query<FlexiFundPhoto> photoQuery = session.createQuery(
	                "FROM FlexiFundPhoto WHERE flexiFundDetails.id = :id", FlexiFundPhoto.class);
	        photoQuery.setParameter("id", id);
	        List<FlexiFundPhoto> photos = photoQuery.getResultList();

	        for (FlexiFundPhoto photo : photos) {
	            String filePath = "D:" + File.separator + "FlexiFund" + File.separator + photo.getPhotoUrl();
	            File file = new File(filePath);
	            if (file.exists()) {
	                file.delete();
	            }
	        }

	        session.createQuery("DELETE FROM FlexiFundPhoto WHERE flexiFundDetails.id = :id")
	               .setParameter("id", id)
	               .executeUpdate();

	        int mainDeleted = session.createQuery("DELETE FROM FlexiFundDetails WHERE ffId = :id")
	                                .setParameter("id", id)
	                                .executeUpdate();

	        tx.commit();
	        return mainDeleted > 0;

	    } catch (Exception e) {
	        if (tx != null) tx.rollback(); 
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public List<Map<String, Object>> getCompleteFlexiFundData(int projid, int panchayat) {

	    List<Map<String, Object>> resultList = new ArrayList<>();

	    Session session = sessionFactory.getCurrentSession();
	    Transaction tx = session.beginTransaction();

	    try {
	    	SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	
	    	String hql = completeFlexiFund;
	        List<FlexiFundDetails> list = session.createQuery(hql, FlexiFundDetails.class)
	                .setParameter("projid", projid)
	                .setParameter("panchayat", panchayat)
	                .getResultList();

	        for (FlexiFundDetails item : list) {

	            Map<String, Object> map = new HashMap<>();

	            map.put("ffId", item.getFfId());
	            map.put("actName", item.getActivity().getActName());
	            map.put("workDesc", item.getWorkDesc());
	            map.put("est_cost", item.getEst_cost());
	            map.put("remark", item.getRemark());
                Double ffcost = item.getFfCost();
                BigDecimal baseCost = BigDecimal.valueOf(ffcost);

	            String progressHql = progressFlexiFund;

	            List<FlexiFundActivityExpProgress> progressList =
	                    session.createQuery(progressHql, FlexiFundActivityExpProgress.class)
	                            .setParameter("ffId", item.getFfId())
	                            .getResultList();

	            if (!progressList.isEmpty()) {

	                BigDecimal totalProgressCost = BigDecimal.ZERO;
	                Date latestDate = null;
	                Date completionDate = null;

	                for (FlexiFundActivityExpProgress p : progressList) {

	                     if (p.getFfCost() != null) {
	                        totalProgressCost = totalProgressCost.add(p.getFfCost());
	                    }

	                    if (latestDate == null || p.getUpdatedDate().after(latestDate)) {
	                        latestDate = p.getUpdatedDate();
	                    }

	                    if (p.getCompletionDate() != null) {
	                        completionDate = p.getCompletionDate();
	                    }
	                }

	                map.put("ffCost", baseCost.add(totalProgressCost));

	               if (completionDate != null) {
	                    map.put("status", "C");
	                    map.put("completionDate", completionDate);
	                } else {
	                    map.put("status", "O");
	                    map.put("completionDate", null);
	                }

	                map.put("compDate", latestDate);
	                map.put("comp_Date", latestDate);

	            } else {

	                map.put("ffCost", item.getFfCost());
	                map.put("status", "O");
	                map.put("completionDate", null);

	                map.put("compDate",
	                    item.getUpdatedDate() != null
	                        ? displayFormat.format(item.getUpdatedDate())
	                        : "");

	                map.put("comp_Date", item.getUpdatedDate());
	            }
	            
	            String photoHql = "FROM FlexiFundPhoto p WHERE p.flexiFundDetails.ffId = :ffId";

	            List<FlexiFundPhoto> photos = session.createQuery(photoHql, FlexiFundPhoto.class)
	                    .setParameter("ffId", item.getFfId())
	                    .getResultList();

	            List<Map<String, Object>> photoList = new ArrayList<>();

	            for (FlexiFundPhoto p : photos) {
	                Map<String, Object> photoMap = new HashMap<>();
	                photoMap.put("photoId", p.getFfPhotoId());
	                photoMap.put("photoUrl", p.getPhotoUrl());
	                photoList.add(photoMap);
	            }

	            map.put("photos", photoList);

	            resultList.add(map);
	        }

	        tx.commit();

	    } catch (Exception e) {
	        tx.rollback();
	        e.printStackTrace();
	    }

	    return resultList;
	}

	@Override
	public boolean saveProgress(List<Integer> ffIds,
	                           List<BigDecimal> ffCosts,
	                           List<String> statusList,
	                           List<String> completionDates,
	                           HttpServletRequest request) {

	    Session session = sessionFactory.getCurrentSession();
	    Transaction tx = null;
	    String createdBy = request.getSession().getAttribute("loginID").toString();
	    try {
	        tx = session.beginTransaction();

	        for (int i = 0; i < ffIds.size(); i++) {

	            Integer ffId = ffIds.get(i);
	            BigDecimal newCost = ffCosts.get(i);
	            String status = statusList.get(i);
	            
	            String compDateStr = "";
	            if (completionDates != null && completionDates.size() > i) {
	                compDateStr = completionDates.get(i);
	            }

	            FlexiFundDetails flex = session.get(FlexiFundDetails.class, ffId);

	            FlexiFundActivityExpProgress progress = new FlexiFundActivityExpProgress();

	            progress.setFlexiFundDetails(flex);
	            progress.setFfCost(newCost);

	            if ("C".equals(status) && compDateStr != null && !compDateStr.isEmpty()) {

	                Date compDate = new SimpleDateFormat("yyyy-MM-dd").parse(compDateStr);
	                progress.setCompletionDate(compDate);
	                flex.setExpStatus("C");

	            } else {
	                progress.setCompletionDate(null);
	            }

	            progress.setUpdatedDate(new Date());
	            progress.setRequestedIp(getClientIpAddr(request));
	            progress.setCreatedBy(createdBy);
	            progress.setUpdatedBy(createdBy);

	            session.save(progress);
	        }

	        tx.commit();
	        return true;

	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	        return false;
	    }
	}
}
