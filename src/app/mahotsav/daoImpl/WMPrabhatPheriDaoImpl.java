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

	                 // Set correct lat/lng per image
	                 photo.setLatitude(latitudes.get(i));
	                 photo.setLongitute(longitudes.get(i));
	                 if (timestamps != null && timestamps.size() > i) {
	                     // parse string to Timestamp
//	                     photo.setPhoto_timestamp(Timestamp.valueOf(timestamps.get(i)));
	                     photo.setPhoto_timestamp(Timestamp.valueOf(userfileup.getPhotoTimestamp().get(i)));

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

}
