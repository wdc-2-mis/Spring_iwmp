package app.daoImpl;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import app.dao.BlockMasterDao;
import app.model.master.IwmpBlock;
import app.model.master.IwmpVillage;


@Repository("blockMasterDao")
public class BlockMasterDaoImpl implements BlockMasterDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${blockListBydistCode}")
	String blockListBydistCode;
	@Value("${blockListBydCode}")
	String blockListBydCode;
	@Value("${getInactiveBlockList}")
	String getInactiveBlockList;
	@Value("${getActiveBlockList}")
	String getActiveBlockList;
	
	
	@Value("${getActiveBlockListAll}")
	String getActiveBlockListAll;

	@Override
	public LinkedHashMap<Integer, String> getBlockByDistCode(Integer stateCode,Integer districtCode) {
		List<IwmpBlock> blockList=new ArrayList<IwmpBlock>();
		String hql=blockListBydistCode;
		LinkedHashMap<Integer, String> blockMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("stCode", stateCode);
			query.setLong("distCode", districtCode);
			blockList = query.list();
			for (IwmpBlock block : blockList) {
				blockMap.put(block.getBlockCode(), block.getBlockName());
			}
			session.getTransaction().commit();
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
        return blockMap;
	}
	@Override
	public LinkedHashMap<Integer, String> getBlockByDistCode(Integer districtCode) {
		List<IwmpBlock> blockList=new ArrayList<IwmpBlock>();
		String hql=blockListBydCode;
		LinkedHashMap<Integer, String> blockMap=new LinkedHashMap<Integer, String>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			//query.setLong("stCode", stateCode);
			query.setInteger("distCode", districtCode);
			blockList = query.list();
			for (IwmpBlock block : blockList) {
				blockMap.put(block.getBcode(), block.getBlockName());
			}
			session.getTransaction().commit();
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
        return blockMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpBlock> getInactiveBlockList(int stateCode, int districtCode, int blockCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		List<IwmpBlock> temp = null;
		try {


			temp = session.createQuery(getInactiveBlockList)
					.setParameter("stcode", stateCode).setParameter("distcode", districtCode)
					.setParameter("blockCode", blockCode).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			//System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}
	@Override
	public void updateBlockList(List<IwmpBlock> blockList) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

			if (null != blockList && blockList.size() > 0) {
				for (IwmpBlock gp : blockList) {
					//System.out.print("test" + gp.getUpdatestatus());
					if (gp.getUpdatestatus() == true) {

						IwmpBlock tproject = (IwmpBlock) session.load(IwmpBlock.class, gp.getBcode());
						//System.out.println("true" + gp.getUpdatestatus());
					
						tproject.setBlockName(tproject.getBlockName()+ " [ "
								+ tproject.getIwmpDistrict().getDistName() + " District ]");

						tproject.setImportType("SLNA");
						tproject.setRequestIp(ipadd);
						tproject.setLastUpdatedDate(d);
						tproject.setActive(true);
						session.saveOrUpdate(tproject);
						//System.out.print("done" + gp.getUpdatestatus());

					}
				}
			}
			//System.out.print("project");
		} catch (Exception ex) {
			//System.out.print("Err" + ex.getMessage());

			tx.rollback();
			ex.printStackTrace();
		} finally {
			tx.commit();

		}
		
	}
	@Override
	public List<IwmpBlock> getActiveBlockList(int stateCode, int districtCode, int blockCode) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();
		List<IwmpBlock> temp = null;
		try {
			temp = session.createQuery(getActiveBlockList)
					.setParameter("stcode", stateCode).setParameter("distcode", districtCode)
					.setParameter("blockCode", blockCode).list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			//System.out.print(ex.getMessage());
			session.getTransaction().rollback();
		}

		return temp;
	}
	@Override
	public IwmpBlock findBlockBcode(int blockCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		

		session.beginTransaction();
		IwmpBlock block =null;
		try {

			if (blockCode > 0) {

				block = (IwmpBlock) session.createQuery("from IwmpBlock where bcode=:bcode")
						.setParameter("bcode", blockCode).uniqueResult();
			}
			session.getTransaction().commit();
			//System.out.print("project==" + village.getVillageName());
		} catch (HibernateException e) {
			System.err.print("Hibernate error" + e.getMessage());
			session.getTransaction().rollback();
			e.printStackTrace();

		} catch (Exception e) {
			System.err.print(" error" + e.getMessage());
			session.getTransaction().rollback();
			e.printStackTrace();

		}

		finally {
		

		}
		return block;
	}
	@Override
	public Boolean updateBlock(IwmpBlock block) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Boolean flag=false;
		Transaction tx = session.beginTransaction();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipadd = inetAddress.getHostAddress();
			Date d = new Date();

				if (block!=null) {

					IwmpBlock tproject = (IwmpBlock) session.load(IwmpBlock.class, block.getBcode());
						
						tproject.setBlockName(block.getBlockName());
						tproject.setIwmpDistrict(block.getIwmpDistrict());
						tproject.setDistCode(block.getIwmpDistrict().getDcode());
						tproject.setDistrictCodelgd(block.getIwmpDistrict().getDcode());

						tproject.setImportType("SLNA");
						tproject.setRequestIp(ipadd);
						tproject.setLastUpdatedDate(d);
						//tproject.setActive(true);
						session.saveOrUpdate(tproject);
						//System.out.print("done" + block.getUpdatestatus());

						flag=true;
					}
			
		} catch (Exception ex) {
			//System.out.print("Err" + ex.getMessage());

			tx.rollback();
			ex.printStackTrace();
		} finally {
			tx.commit();

		}
		return flag;
	}
	@Override
	public List<IwmpBlock> getActiveBlockList() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				Session session = sessionFactory.getCurrentSession();

				session.beginTransaction();
				List<IwmpBlock> temp = null;
				try {
					temp = session.createQuery(getActiveBlockListAll).list();
					
					session.getTransaction().commit();
				} catch (Exception ex) {
					//System.out.print(ex.getMessage());
					session.getTransaction().rollback();
				}

				return temp;
	}
	@Override
	public LinkedHashMap<String,Integer> getBlockWithBlockLgdByDistCode(Integer districtCode) {
		List<IwmpBlock> blockList=new ArrayList<IwmpBlock>();
		String hql=blockListBydCode;
		LinkedHashMap<String,Integer> blockMap=new LinkedHashMap<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery(hql);
			//query.setLong("stCode", stateCode);
			query.setInteger("distCode", districtCode);
			blockList = query.list();
			for (IwmpBlock block : blockList) {
				blockMap.put( block.getBlockName()+" ("+block.getBlockCodelgd()+")", block.getBcode());
			}
			session.getTransaction().commit();
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
        return blockMap;
	
	}

		
}


