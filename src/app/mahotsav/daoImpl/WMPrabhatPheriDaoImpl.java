package app.mahotsav.daoImpl;

import java.io.File;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.Transaction;

import app.common.CommonFunctions;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.WMPrabhatPheriBean;
import app.mahotsav.dao.WMPrabhatPheriDao;
import app.mahotsav.model.MahotsavPrabhatPheri;
import app.mahotsav.model.MahotsavPrabhatPheriPhoto;
import app.mahotsav.model.WatershedMahotsavInauguaration;
import app.mahotsav.model.WatershedMahotsavInauguarationActPhoto;
import app.model.IwmpDistrict;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.model.MCulturalActivity;
import app.watershedyatra.model.WatershedYatVill;

@Repository("WMPrabhatPheriDao")
public class WMPrabhatPheriDaoImpl implements WMPrabhatPheriDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
	CommonFunctions commonFunction;
    
    @Value("${getWMdraftList}")
	String getWMdraftList;
	
	@Value("${getWMcompletedList}")
	String getWMcompletedList;
	
	@Value("${checkWMPrabhatPheriVillage}")
	String checkWMPrabhatPheriVillage;

	@Value("${getMahotsavPPeditList}")
	String getMahotsavPPeditList;
    @Override
    public List<Map<String, Object>> getBlockListByDistrict(Integer districtCode) {
        List<IwmpBlock> blockList = new ArrayList<>();
        String hql = "FROM IwmpBlock b WHERE b.distCode = :districtCode AND b.active = true ORDER BY b.blockName";
        List<Map<String, Object>> resultList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Query<IwmpBlock> query = session.createQuery(hql, IwmpBlock.class);
            query.setParameter("districtCode", districtCode);
            blockList = query.list();

            for (IwmpBlock blk : blockList) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("key", blk.getBlockCode());
                map.put("value", blk.getBlockName());
                resultList.add(map);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.getTransaction().commit();
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getVillageListByBlock(Integer blockCode) {
        List<IwmpVillage> villageList = new ArrayList<>();
        String hql = "FROM IwmpVillage v WHERE v.blockCode = :blockCode AND v.active = true ORDER BY v.villageName";
        List<Map<String, Object>> resultList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Query<IwmpVillage> query = session.createQuery(hql, IwmpVillage.class);
            query.setParameter("blockCode", blockCode);
            villageList = query.list();

            for (IwmpVillage v : villageList) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("key", v.getVcode());
                map.put("value", v.getVillageName());
                resultList.add(map);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.getTransaction().commit();
        }
        return resultList;
    }

	 @Override
	    public String savePrabhatPheri(Integer stCode, Integer distCode, Integer blockCode, Integer villageCode,
	                                   Date prabhatpheriDate, Integer maleParticipants, Integer femaleParticipants,
	                                   List<MultipartFile> photos, String requestIp, String createdBy) {

	        try {
	            Session session = sessionFactory.getCurrentSession();

	            // Create main MahotsavPrabhatPheri object
	            MahotsavPrabhatPheri prabhatPheri = new MahotsavPrabhatPheri();

	            // Load State, District, Block, Village objects
	            IwmpState state = session.get(IwmpState.class, stCode);
	            IwmpDistrict district = session.get(IwmpDistrict.class, distCode);
	            IwmpBlock block = session.get(IwmpBlock.class, blockCode);
	            IwmpVillage village = session.get(IwmpVillage.class, villageCode);

	            prabhatPheri.setIwmpState(state);
	            prabhatPheri.setIwmpDistrict(district);
	            prabhatPheri.setIwmpBlock(block);
	            prabhatPheri.setIwmpVillage(village);

	            prabhatPheri.setPrabhatpheriDate(prabhatpheriDate);
	            prabhatPheri.setMaleParticipants(maleParticipants);
	            prabhatPheri.setFemaleParticipants(femaleParticipants);
	            prabhatPheri.setCreatedOn(new Date());
	            prabhatPheri.setUpdatedOn(new Date());
	            prabhatPheri.setRequestIp(requestIp);
	            prabhatPheri.setCreatedBy(createdBy);
	            prabhatPheri.setStatus('D');

	            session.save(prabhatPheri);

	            // Save photos
	            if (photos != null && !photos.isEmpty()) {
	                String uploadDir = "D:/WatershedMahotsav"; // Change to your folder
	                File dir = new File(uploadDir);
	                if (!dir.exists()) dir.mkdirs();

	                for (MultipartFile file : photos) {
	                    if (!file.isEmpty()) {
	                        MahotsavPrabhatPheriPhoto photo = new MahotsavPrabhatPheriPhoto();
	                        photo.setWmPrabhatPheri(prabhatPheri);

	                        // Save file to server folder
	                        String fileName = new Date().getTime() + "_" + file.getOriginalFilename();
	                        File serverFile = new File(dir, fileName);
	                        try (OutputStream os = new FileOutputStream(serverFile)) {
	                            os.write(file.getBytes());
	                        }

	                        photo.setPhotoUrl(fileName); // You can prepend folder path if needed
	                        photo.setCreatedBy(createdBy);
	                        photo.setCreated_date(new Date());
	                        photo.setRequestedIp(requestIp);

	                        session.save(photo);
	                    }
	                }
	            }

	            return "Saved successfully";

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error while saving: " + e.getMessage();
	        }
	    }

	 @Override
	 public String saveMahotsavPrabhatPheriDetails(WMPrabhatPheriBean userfileup, HttpSession session) {
	     Session sess = sessionFactory.getCurrentSession();
	     String res = "fail";
	     int sequence = 0;

	     try {
	         sess.beginTransaction();

	         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	         Date prabhatpheriDate = formatter.parse(userfileup.getDate1());
	         String st_code = session.getAttribute("stateCode").toString();
	         String filePath = "D:\\WatershedMahotsav\\";

	         // Get sequence
	         Object seqObj = sess.createSQLQuery("SELECT value_id FROM watershed_mahotsav_prabhatpheri_sequence")
	                 .uniqueResult();
	         if (seqObj instanceof BigDecimal) sequence = ((BigDecimal) seqObj).intValue();
	         else if (seqObj instanceof BigInteger) sequence = ((BigInteger) seqObj).intValue();
	         else sequence = Integer.parseInt(seqObj.toString());

	         InetAddress inet = InetAddress.getLocalHost();
	         String ipAddr = inet.getHostAddress();

	         // ------------------ Save Main Data ------------------
	         MahotsavPrabhatPheri data = new MahotsavPrabhatPheri();
	         IwmpState s = new IwmpState();
	         s.setStCode(Integer.parseInt(st_code));

	         IwmpDistrict d = new IwmpDistrict();
	         d.setDcode(userfileup.getDistrict1());

	         IwmpBlock b = new IwmpBlock();
	         b.setBcode(userfileup.getBlock1());

	         IwmpVillage v = new IwmpVillage();
	         v.setVcode(userfileup.getVillage1());

	         data.setIwmpState(s);
	         data.setIwmpDistrict(d);
	         data.setIwmpBlock(b);
	         data.setIwmpVillage(v);
	         data.setCreatedBy(session.getAttribute("loginID").toString());
	         data.setCreatedOn(new Timestamp(new Date().getTime()));
	         data.setRequestIp(ipAddr);
	         data.setStatus('D');
	         data.setPrabhatpheriDate(prabhatpheriDate);
	         data.setMaleParticipants(userfileup.getMale_participants());
	         data.setFemaleParticipants(userfileup.getFemale_participants());

	         sess.save(data);

	         String code = st_code +"_"+ userfileup.getDistrict1()+"_" + userfileup.getBlock1()+"_" + data.getPrabhatpheriId();
	         // ------------------ Save Photos ------------------
	         
	         List<MultipartFile> photos = userfileup.getPhotos();
	         List<String> latitudes = userfileup.getLatitude();
	         List<String> longitudes = userfileup.getLongitute();
	         List<String> timestamps = userfileup.getPhotoTimestamp();

	         for (int i = 0; i < photos.size(); i++) {
	             MultipartFile image = photos.get(i);
	             if (!image.isEmpty()) {
	                 MahotsavPrabhatPheriPhoto photo = new MahotsavPrabhatPheriPhoto();
	                 photo.setWmPrabhatPheri(data);
	                 photo.setCreatedBy(session.getAttribute("loginID").toString());
	                 photo.setCreated_date(new Timestamp(new Date().getTime()));
	                 photo.setRequestedIp(ipAddr);

	                 // Latitude & Longitude
	                 photo.setLatitude((latitudes != null && latitudes.size() > i && !latitudes.get(i).trim().isEmpty()) 
	                                   ? latitudes.get(i) 
	                                   : null);

	                 photo.setLongitute((longitudes != null && longitudes.size() > i && !longitudes.get(i).trim().isEmpty()) 
	                                    ? longitudes.get(i) 
	                                    : null);

	                 // Photo timestamp
	                 if (timestamps != null && timestamps.size() > i && timestamps.get(i) != null && !timestamps.get(i).trim().isEmpty()) {
	                	    try {
	                	        String dateStr = timestamps.get(i); // e.g., "2025-11-21 09:15:30" or "2025:11:21 09:15:30"
	                	        // Replace ':' in date part if necessary
	                	        dateStr = dateStr.replaceFirst("(\\d{4}):(\\d{2}):(\\d{2})", "$1-$2-$3"); 
	                	        // Now dateStr = "2025-11-21 09:15:30"
	                	        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	                	        LocalDateTime dateTime = LocalDateTime.parse(dateStr, inputFormat);
	                	        photo.setPhoto_timestamp(Timestamp.valueOf(dateTime));
	                	    } catch (Exception e) {
	                	        photo.setPhoto_timestamp(null); // save null if parsing fails
	                	    }
	                	} else {
	                	    photo.setPhoto_timestamp(null);
	                	}


	                 // Upload the file
	                 commonFunction.uploadFilePrabhatPheriMahotsav(image, filePath, code, sequence);

	                 // Store URL
	                 photo.setPhotoUrl(filePath + "PP_" + code +"_"+ sequence + "_" + image.getOriginalFilename());

	                 sess.save(photo);
	                 sequence++;
	             }
	         }



	         // ------------------ Update Sequence Table ------------------
	         SQLQuery sqlQuery = sess.createSQLQuery(
	                 "UPDATE watershed_mahotsav_prabhatpheri_sequence SET value_id = :aut");
	         sqlQuery.setInteger("aut", sequence);
	         sqlQuery.executeUpdate();

	         sess.getTransaction().commit();
	         res = "success";

	     } catch (Exception ex) {
	         ex.printStackTrace();
	         sess.getTransaction().rollback();
	         res = "fail";
	     }

	     return res;
	 }

	@Override
	public List<WMPrabhatPheriBean> getWatershedMahotsavDraftList(Integer stCode) {
		String getReport=getWMdraftList;
		Session session = sessionFactory.getCurrentSession();
		List<WMPrabhatPheriBean> list = new ArrayList<WMPrabhatPheriBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("stcode",stCode); 
				query.setResultTransformer(Transformers.aliasToBean(WMPrabhatPheriBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
		}
		return list;
	}

	@Override
	public List<WMPrabhatPheriBean> getWatershedMahotsavCompleteList(Integer stCode) {
		String getReport=getWMcompletedList;
		Session session = sessionFactory.getCurrentSession();
		List<WMPrabhatPheriBean> list = new ArrayList<WMPrabhatPheriBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("stcode",stCode); 
				query.setResultTransformer(Transformers.aliasToBean(WMPrabhatPheriBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
		}
		return list;
	}

	@Override
	public String completeWMPrabhatPheri(List<Integer> ppid, String userid) {
		String str="fail";
		Integer value=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			 
			 session.beginTransaction();
			 InetAddress inetAddress = InetAddress.getLocalHost(); 
			 String ipadd=inetAddress.getHostAddress(); 
			 SQLQuery query = session.createSQLQuery("update watershed_mahotsav_prabhatpheri set status='C' where prabhatpheri_id=:ppid");
			 Date d= new Date();
			 
			 for(int i=0;i<ppid.size(); i++)
			 {
				 query.setInteger("ppid", ppid.get(i));
				 value=query.executeUpdate();
				 if(value>0) {
					 str="success";
				 }
				 else {
					session.getTransaction().rollback();
					str="fail";
				 }
			 }
		}
		catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex){
			
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.getTransaction().commit();
		}
		
		return str;
	}

	@Override
	public String deleteWMPrabhatPheri(List<Integer> ppid, String userid) {

	    String str = "fail";
	    Session session = sessionFactory.getCurrentSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();

	        // 1. Fetch parent records & photo paths
	        Query<MahotsavPrabhatPheri> query1 = session.createQuery(
	                "from MahotsavPrabhatPheri where prabhatpheriId IN (:ppidList)",
	                MahotsavPrabhatPheri.class);
	        query1.setParameterList("ppidList", ppid);

	        List<MahotsavPrabhatPheri> recordList = query1.list();
	        List<String> imgList = new ArrayList<>();

	        for (MahotsavPrabhatPheri mp : recordList) {
	            Set<MahotsavPrabhatPheriPhoto> photoSet = mp.getWmPrabhatPheriPhoto();

	            if (photoSet != null && !photoSet.isEmpty()) {
	                for (MahotsavPrabhatPheriPhoto photo : photoSet) {
	                    if (photo.getPhotoUrl() != null && !photo.getPhotoUrl().trim().isEmpty()) {
	                        imgList.add(photo.getPhotoUrl());
	                    }
	                }
	            }
	        }

	        // 2. Delete child photos table first (avoid FK issue)
	        SQLQuery deleteChildren = session.createSQLQuery(
	                "DELETE FROM watershed_mahotsav_prabhatpheri_act_photo WHERE prabhatpheri_id IN (:ppid)");
	        deleteChildren.setParameterList("ppid", ppid);
	        deleteChildren.executeUpdate();

	        // 3. Delete parent table record
	        SQLQuery deleteParent = session.createSQLQuery(
	                "DELETE FROM watershed_mahotsav_prabhatpheri WHERE prabhatpheri_id IN (:ppid)");
	        deleteParent.setParameterList("ppid", ppid);

	        int result = deleteParent.executeUpdate();
	        if (result > 0) {
	            str = "success";
	        } else {
	            tx.rollback();
	            return "fail";
	        }

	        // 4. Delete image files physically
	        for (String photoPath : imgList) {
	            File file = new File(photoPath);
	            if (file.exists()) {
	                if (file.delete()) {
	                    System.out.println("Deleted file: " + file.getAbsolutePath());
	                } else {
	                    System.out.println("Failed to delete file: " + file.getAbsolutePath());
	                }
	            } else {
	                System.out.println("File not found: " + photoPath);
	            }
	        }

	        tx.commit();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        if (tx != null) tx.rollback();
	        str = "fail";
	    }

	    return str;
	}

	@Override
	public boolean checkVillageWMP(Integer vCode) {
		Integer value = 0;
	    Boolean status = false; // Default to false in case no results found
	    
	    try (Session session = sessionFactory.openSession()) {
	        Transaction tx = session.beginTransaction();
	        
	        String sql = checkWMPrabhatPheriVillage;
	        SQLQuery query = session.createSQLQuery(sql);
	        query.setInteger("vCode", vCode);
	        value = ((Number) query.uniqueResult()).intValue();

	        if (value > 0) {
	            status = true;
	        }
	        
	        tx.commit();
	    } catch (Exception ex) {
	        ex.printStackTrace(); // Log exception for debugging
	    }

	    return status;
}

	@Override
	public LinkedHashMap<String, Integer> getWMPrabhatPheriVillage(Integer bCode) {
		// TODO Auto-generated method stub
		List<IwmpVillage> bldList=new ArrayList<IwmpVillage>();
	//	String hql=villageListWatershedyatra;
		LinkedHashMap<String, Integer> blkMap=new LinkedHashMap<String, Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from IwmpVillage where iwmpGramPanchayat.iwmpBlock.bcode=:gpscode order by villageName");
			query.setInteger("gpscode", bCode);
			bldList = query.list();
			
			for (IwmpVillage blk : bldList) {
				blkMap.put( blk.getVillageName(), blk.getVcode());
			//	System.out.println(district.getDcode()+" k "+district.getDistName());
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
        return blkMap;
	}

	@Override
	public List<WMPrabhatPheriBean> getWMPrabhatPheriEdit(Integer prabhatpheriId) {
		String getReport=getMahotsavPPeditList;
		Session session = sessionFactory.getCurrentSession();
		List<WMPrabhatPheriBean> list = new ArrayList<WMPrabhatPheriBean>();
		try {
				session.beginTransaction();
				Query query= session.createSQLQuery(getReport);
				query.setInteger("wcdcid",prabhatpheriId); 
				query.setResultTransformer(Transformers.aliasToBean(WMPrabhatPheriBean.class));
				list = query.list();
				session.getTransaction().commit();
		} 
		catch (HibernateException e) 
		{
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		} 
		catch(Exception ex)
		{
			session.getTransaction().rollback();
			ex.printStackTrace();
		}
		finally {
			//session.getTransaction().commit();
			//session.flush();
		//session.close();
		}
		return list;
		
	}

	@Override
	public String updateWMPrabhatPheri(WMPrabhatPheriBean userfileup, HttpSession session) {
	    Session sess = sessionFactory.getCurrentSession();
	    String res = "fail";
	    int sequence = 0;

	    try {
	        sess.beginTransaction();

	        // ------------------ PARSE DATE ------------------
	        Date prabhatpheriDate = null;
	        try {
	            if (userfileup.getDate1() != null && !userfileup.getDate1().trim().isEmpty()) {
	                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	                prabhatpheriDate = formatter.parse(userfileup.getDate1().trim());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        String st_code = session.getAttribute("stateCode").toString();
	        String filePath = "D:\\WatershedMahotsav\\";

	        // ------------------ GET EXISTING RECORD ------------------
	        MahotsavPrabhatPheri oldData = sess.get(MahotsavPrabhatPheri.class, userfileup.getPrabhatpheri_id());
	        if (oldData == null) return "fail";

	        // ------------------ IF NO NEW PHOTOS, ONLY UPDATE COUNTS ------------------
	        if (userfileup.getPhotos() == null || userfileup.getPhotos().size() <= 1) {
	            oldData.setMaleParticipants(userfileup.getMale_participants());
	            oldData.setFemaleParticipants(userfileup.getFemale_participants());
	            oldData.setUpdatedOn(new Date());
	            sess.update(oldData);
	            sess.getTransaction().commit();
	            return "success";
	        }

	        // ------------------ DELETE OLD PHOTOS ------------------
	        List<MahotsavPrabhatPheriPhoto> oldPhotoList = sess.createQuery(
	                        "from MahotsavPrabhatPheriPhoto where wmPrabhatPheri.prabhatpheriId = :ppid",
	                        MahotsavPrabhatPheriPhoto.class)
	                .setParameter("ppid", userfileup.getPrabhatpheri_id())
	                .list();

	        for (MahotsavPrabhatPheriPhoto p : oldPhotoList) {
	            if (p.getPhotoUrl() != null) {
	                File f = new File(p.getPhotoUrl());
	                if (f.exists()) f.delete();
	            }
	        }

	        sess.createSQLQuery("DELETE FROM watershed_mahotsav_prabhatpheri_act_photo WHERE prabhatpheri_id = :id")
	                .setParameter("id", userfileup.getPrabhatpheri_id())
	                .executeUpdate();
	        sess.createSQLQuery("DELETE FROM watershed_mahotsav_prabhatpheri WHERE prabhatpheri_id = :id")
	                .setParameter("id", userfileup.getPrabhatpheri_id())
	                .executeUpdate();

	        // ------------------ GET SEQUENCE ------------------
	        Object seqObj = sess.createSQLQuery("SELECT value_id FROM watershed_mahotsav_prabhatpheri_sequence").uniqueResult();
	        if (seqObj != null) {
	            if (seqObj instanceof BigDecimal) sequence = ((BigDecimal) seqObj).intValue();
	            else if (seqObj instanceof BigInteger) sequence = ((BigInteger) seqObj).intValue();
	            else sequence = Integer.parseInt(seqObj.toString());
	        }

	        InetAddress inet = InetAddress.getLocalHost();
	        String ipAddr = inet.getHostAddress();

	        // ------------------ INSERT FRESH MAIN RECORD ------------------
	        MahotsavPrabhatPheri data = new MahotsavPrabhatPheri();

	        IwmpState s = new IwmpState();
	         s.setStCode(Integer.parseInt(st_code));

	         IwmpDistrict d = new IwmpDistrict();
	         d.setDcode(userfileup.getDistrict1());

	         IwmpBlock b = new IwmpBlock();
	         b.setBcode(userfileup.getBlock1());

	         IwmpVillage v = new IwmpVillage();
	         v.setVcode(userfileup.getVillage1());

	         data.setIwmpState(s);
	         data.setIwmpDistrict(d);
	         data.setIwmpBlock(b);
	         data.setIwmpVillage(v);

	        data.setCreatedBy(session.getAttribute("loginID").toString());
	        data.setCreatedOn(new Timestamp(new Date().getTime()));
	        data.setRequestIp(ipAddr);
	        data.setStatus('D');
	        data.setPrabhatpheriDate(prabhatpheriDate);
	        data.setMaleParticipants(userfileup.getMale_participants());
	        data.setFemaleParticipants(userfileup.getFemale_participants());

	        sess.save(data);

	        String code = st_code + "_" +
	                      (userfileup.getDistrict1() != null ? userfileup.getDistrict1() : "0") + "_" +
	                      (userfileup.getBlock1() != null ? userfileup.getBlock1() : "0") + "_" +
	                      data.getPrabhatpheriId();

	        // ------------------ SAVE NEW PHOTOS ------------------
	        List<MultipartFile> photos = userfileup.getPhotos();
	        List<String> latitudes = userfileup.getLatitude();
	        List<String> longitudes = userfileup.getLongitute();
	        List<String> timestamps = userfileup.getPhotoTimestamp();

	        if (photos != null) {
	            for (int i = 0; i < photos.size(); i++) {
	                MultipartFile image = photos.get(i);
	                if (image != null && !image.isEmpty()) {
	                    MahotsavPrabhatPheriPhoto photo = new MahotsavPrabhatPheriPhoto();
	                    photo.setWmPrabhatPheri(data);
	                    photo.setCreatedBy(session.getAttribute("loginID").toString());
	                    photo.setCreated_date(new Timestamp(new Date().getTime()));
	                    photo.setRequestedIp(ipAddr);

	                    // Latitude
	                    photo.setLatitude((latitudes != null && latitudes.size() > i) ? latitudes.get(i) : null);
	                    // Longitude
	                    photo.setLongitute((longitudes != null && longitudes.size() > i) ? longitudes.get(i) : null);

	                    // Timestamp
	                    try {
	                        String dateStr = (timestamps != null && timestamps.size() > i) ? timestamps.get(i) : null;
	                        if(dateStr != null && !dateStr.isEmpty()) {
	                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	                            LocalDateTime dt = LocalDateTime.parse(dateStr, fmt);
	                            photo.setPhoto_timestamp(Timestamp.valueOf(dt));
	                        } else photo.setPhoto_timestamp(null);
	                    } catch (Exception e) {
	                        photo.setPhoto_timestamp(null);
	                    }

	                    // Upload file
	                    commonFunction.uploadFilePrabhatPheriMahotsav(image, filePath, code, sequence);

	                    // Save path
	                    photo.setPhotoUrl(filePath + "PP_" + code + "_" + sequence + "_" + image.getOriginalFilename());
	                    sess.save(photo);
	                    sequence++;
	                }
	            }
	        }

	        // ------------------ UPDATE SEQUENCE ------------------
	        sess.createSQLQuery("UPDATE watershed_mahotsav_prabhatpheri_sequence SET value_id = :seq")
	                .setParameter("seq", sequence)
	                .executeUpdate();

	        sess.getTransaction().commit();
	        res = "success";

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        sess.getTransaction().rollback();
	        res = "fail";
	    }

	    return res;
	}


}
