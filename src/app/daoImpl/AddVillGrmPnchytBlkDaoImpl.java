package app.daoImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import app.bean.AddVillGrmPnchytBlkBean;
import app.bean.RevisedActPlanBean;
import app.dao.AddVillGrmPnchytBlkDao;
import app.model.IwmpDistrict;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;

@Repository("AddVillGrmPnchytBlkDao")
public class AddVillGrmPnchytBlkDaoImpl implements AddVillGrmPnchytBlkDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${chkAlreadyExistVillLgdCode}")
	String chkAlreadyExistVillLgdCode;
	
	@Value("${chkAlreadyExistGrmPnchytLgdCode}")
	String chkAlreadyExistGrmPnchytLgdCode;
	
	@Value("${chkAlreadyExistBlkLgdCode}")
	String chkAlreadyExistBlkLgdCode;
	
	@Value("${blockListBydistCode}")
	String blockListBydistCode;
	
	@Override
	public List<AddVillGrmPnchytBlkBean> getTableDataBylgdCode(Integer lgdCode, Integer masterId) {
		Session session = sessionFactory.getCurrentSession();
		List<AddVillGrmPnchytBlkBean> res = new ArrayList<>();
		List<IwmpVillage> villlist = new ArrayList<>();
		List<IwmpGramPanchayat> grmPnchytlist = new ArrayList<>();
		List<IwmpBlock> blklist = new ArrayList<>();
		String villHql = chkAlreadyExistVillLgdCode;
		String grmPnchytHql = chkAlreadyExistGrmPnchytLgdCode;
		String blkHql = chkAlreadyExistBlkLgdCode;
		try {
			session.beginTransaction();
			if(masterId==1) {
				villlist = session.createQuery(villHql).setParameter("lgdCode", lgdCode).list();
				for(IwmpVillage vill : villlist) {
					AddVillGrmPnchytBlkBean addVillGrmBlk = new AddVillGrmPnchytBlkBean();
					addVillGrmBlk.setStatename(vill.getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getStName());
					addVillGrmBlk.setStatelgdcode(Integer.parseInt(vill.getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getStateCodelgd()));
					addVillGrmBlk.setDistrictname(vill.getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getDistName());
					addVillGrmBlk.setDistrictlgdcode(vill.getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getDistrictCodelgd());
					addVillGrmBlk.setBlockname(vill.getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					addVillGrmBlk.setBlocklgdcode(vill.getIwmpGramPanchayat().getIwmpBlock().getBlockCodelgd());
					addVillGrmBlk.setGrampanchayatname(vill.getIwmpGramPanchayat().getGramPanchayatName());
					addVillGrmBlk.setGrampanchayatlgdcode(vill.getIwmpGramPanchayat().getGramPanchayatLgdCode());
					addVillGrmBlk.setVillagename(vill.getVillageName());
					addVillGrmBlk.setStatus(vill.getActive());
					res.add(addVillGrmBlk);
				}
			}
			if(masterId ==2) {
				grmPnchytlist = session.createQuery(grmPnchytHql).setParameter("lgdCode", lgdCode).list();
				for(IwmpGramPanchayat vill : grmPnchytlist) {
					AddVillGrmPnchytBlkBean addVillGrmBlk = new AddVillGrmPnchytBlkBean();
					addVillGrmBlk.setStatename(vill.getIwmpBlock().getIwmpDistrict().getIwmpState().getStName());
					addVillGrmBlk.setStatelgdcode(Integer.parseInt(vill.getIwmpBlock().getIwmpDistrict().getIwmpState().getStateCodelgd()));
					addVillGrmBlk.setDistrictname(vill.getIwmpBlock().getIwmpDistrict().getDistName());
					addVillGrmBlk.setDistrictlgdcode(vill.getIwmpBlock().getIwmpDistrict().getDistrictCodelgd());
					addVillGrmBlk.setBlockname(vill.getIwmpBlock().getBlockName());
					addVillGrmBlk.setBlocklgdcode(vill.getIwmpBlock().getBlockCodelgd());
					addVillGrmBlk.setGrampanchayatname(vill.getGramPanchayatName());
					addVillGrmBlk.setGrampanchayatlgdcode(vill.getGramPanchayatLgdCode());
					addVillGrmBlk.setStatus(vill.getActive());
					res.add(addVillGrmBlk);
				}
			}
			if(masterId ==3) {
				blklist = session.createQuery(blkHql).setParameter("lgdCode", lgdCode).list();
				for(IwmpBlock vill : blklist) {
					AddVillGrmPnchytBlkBean addVillGrmBlk = new AddVillGrmPnchytBlkBean();
					addVillGrmBlk.setStatename(vill.getIwmpDistrict().getIwmpState().getStName());
					addVillGrmBlk.setStatelgdcode(Integer.parseInt(vill.getIwmpDistrict().getIwmpState().getStateCodelgd()));
					addVillGrmBlk.setDistrictname(vill.getIwmpDistrict().getDistName());
					addVillGrmBlk.setDistrictlgdcode(vill.getIwmpDistrict().getDistrictCodelgd());
					addVillGrmBlk.setBlockname(vill.getBlockName());
					addVillGrmBlk.setBlocklgdcode(vill.getBlockCodelgd());
					addVillGrmBlk.setStatus(vill.getActive());
					res.add(addVillGrmBlk);
				}
			}
			session.getTransaction().commit();
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		return res;
	}

	@Override
	public String saveVillGrmBlkAsDraft(Integer masterId, Integer state, Integer district,Integer block, 
			Integer grmPnchyt, Integer lgdCode,	String villGrmBlk) {
		Session session = sessionFactory.getCurrentSession();
		String res = "fail";
		
		try {
			session.beginTransaction();
			if(masterId==1) {
				IwmpVillage vill = new IwmpVillage();
				IwmpGramPanchayat grmPanchyt = new IwmpGramPanchayat();
				vill.setStCode(state);
				vill.setDistCode(district);
				vill.setBlockCode(block);
				grmPanchyt.setGcode(grmPnchyt);
				vill.setIwmpGramPanchayat(grmPanchyt);
				vill.setVillageName(villGrmBlk);
				vill.setVillageLgdcode(lgdCode);
				vill.setActive(true);
				session.save(vill);
			}if(masterId==2) {
				IwmpGramPanchayat grmPanchyt = new IwmpGramPanchayat();
				IwmpBlock blk = new IwmpBlock();
				grmPanchyt.setStCode(state);
				grmPanchyt.setDistCode(district);
				blk.setBcode(block);
				grmPanchyt.setIwmpBlock(blk);
				grmPanchyt.setGramPanchayatName(villGrmBlk);
				grmPanchyt.setGramPanchayatLgdCode(lgdCode);
				grmPanchyt.setActive(true);
				session.save(grmPanchyt);
			}if(masterId==3) {
				IwmpBlock blk = new IwmpBlock();
				IwmpDistrict dist = new IwmpDistrict();
				blk.setStCode(state);
				blk.setStateCodelgd(state);
				blk.setDistCode(district);
				blk.setDistrictCodelgd(district);
				dist.setDcode(district);
				blk.setIwmpDistrict(dist);
				blk.setBlockName(villGrmBlk);
				blk.setBlockCode(lgdCode);
				blk.setBlockCodelgd(lgdCode);
				blk.setActive(true);
				session.save(blk);
			}
			session.getTransaction().commit();
			res = "success";
			
			
		}catch (HibernateException e) {
			System.err.print("Hibernate error");
			e.printStackTrace();
			session.getTransaction().rollback();
		}catch(Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return res;
	}
	
	@Override
	public LinkedHashMap<Integer, String> getBlkByDistCode(Integer stateCode,Integer districtCode) {
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

}
