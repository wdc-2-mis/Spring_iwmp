package app.serviceImpl.outcomes;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.RPCLivelihoodBean;
import app.dao.DashBoardDao;
import app.dao.outcomes.LivelihoodProductionEpaDao;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.LivelihoodEpaProd;
import app.model.outcome.ProductionDetail;
import app.service.outcome.LivelihoodProductionEpaService;

@Service("LivelihoodProductionEpa")
public class LivelihoodProductionEpaServiceImpl implements LivelihoodProductionEpaService{
	@Autowired
	LivelihoodProductionEpaDao livelihoodDao;

	@Override
	public LinkedHashMap<Integer, String> getLivelihoodCoreActivity() {
		// TODO Auto-generated method stub
		return livelihoodDao.getLivelihoodCoreActivity();
	}

	@Override
	public LinkedHashMap<Integer, String> getProductionCoreActivity() {
		// TODO Auto-generated method stub
		return livelihoodDao.getProductionCoreActivity();
	}

	@Override
	public LinkedHashMap<Integer, String> getEpaCoreActivity() {
		// TODO Auto-generated method stub
		return livelihoodDao.getEpaCoreActivity();
	}

	@Override
	public String addLivelihoodProductionEPA(LivelihoodEpaProd livelihoodProductionEpa) {
		// TODO Auto-generated method stub
		return livelihoodDao.addLivelihoodProductionEPA(livelihoodProductionEpa);
		
	}

	@Override
	public LinkedHashMap<Integer, String> getCropList() {
		// TODO Auto-generated method stub
		return livelihoodDao.getCropList();
	}

	@Override
	public List<RPCLivelihoodBean> getRptLivelihoodList(Integer stcode) {
		// TODO Auto-generated method stub
		return livelihoodDao.getRptLivelihoodList(stcode);
	}

	@Override
	public List<RPCLivelihoodBean> getRptProductionList(Integer stcode) {
		// TODO Auto-generated method stub
		return livelihoodDao.getRptProductionList(stcode);
	}

	@Override
	public List<LivelihoodDetail> getLivelihoodDetail(Integer regId) {
		// TODO Auto-generated method stub
		return livelihoodDao.getLivelihoodDetail(regId);
	}

	@Override
	public List<ProductionDetail> getProductionDetail(Integer regId) {
		// TODO Auto-generated method stub
		return livelihoodDao.getProductionDetail(regId);
	}

	@Override
	public List<EpaDetail> getEpaDetail(Integer regId) {
		// TODO Auto-generated method stub
		return livelihoodDao.getEpaDetail(regId);
	}


	@Override
	public List<RPCLivelihoodBean> getRptLivelihoodDistList(String stcd, String headtypeh) {
		// TODO Auto-generated method stub
		return livelihoodDao.getRptLivelihoodDistList(stcd, headtypeh);
	}


	@Override
	public String deleteLivelihoodDetail(int livelihoodDetailId) {
		// TODO Auto-generated method stub
		return livelihoodDao.deleteLivelihoodDetail(livelihoodDetailId);
	}

	@Override
	public String completeLivelihoodDetail(int livelihoodDetailId) {
		// TODO Auto-generated method stub
		return livelihoodDao.completeLivelihoodDetail(livelihoodDetailId);
	}

	@Override
	public String deleteProductionDetail(int ProductionDetailId) {
		// TODO Auto-generated method stub
		return livelihoodDao.deleteProductionDetail(ProductionDetailId);
	}

	@Override
	public String completeProductionDetail(int ProductionDetailId) {
		// TODO Auto-generated method stub
		return livelihoodDao.completeProductionDetail(ProductionDetailId);
	}

	@Override
	public String deleteEpaDetail(int EpaDetailId) {
		// TODO Auto-generated method stub
		return livelihoodDao.deleteEpaDetail(EpaDetailId);
	}

	@Override
	public String completeEpaDetail(int EpaDetailId) {
		// TODO Auto-generated method stub
		return livelihoodDao.completeEpaDetail(EpaDetailId);
	}


	@Override
	public List<RPCLivelihoodBean> getRptEpaList(Integer stcode) {
		// TODO Auto-generated method stub
		return livelihoodDao.getRptEpaList(stcode);
	}

	@Override
	public List<EpaDetail> getEpaDetailProjectWise(Integer distid) {
		// TODO Auto-generated method stub
		return livelihoodDao.getEpaDetailProjectWise(distid);
	}

	@Override
	public List<LivelihoodDetail> getLivelihoodDetailProjectWise(Integer distid) {
		// TODO Auto-generated method stub
		return livelihoodDao.getLivelihoodDetailProjectWise( distid);
	}

	@Override
	public List<ProductionDetail> getProductionDetailProjectWise(Integer distid) {
		// TODO Auto-generated method stub
		return livelihoodDao.getProductionDetailProjectWise( distid);
	}



	@Override
	public List<LivelihoodDetail> getLivelihoodProjectDetail(Integer projectId) {
		// TODO Auto-generated method stub
		return livelihoodDao.getLivelihoodProjectDetail(projectId);
	}

	@Override
	public List<ProductionDetail> getProductionProjectDetail(Integer projectId) {
		// TODO Auto-generated method stub
		return livelihoodDao.getProductionProjectDetail(projectId);
	}

	@Override
	public List<EpaDetail> getEpaProjectDetail(Integer projectId) {
		// TODO Auto-generated method stub
		return livelihoodDao.getEpaProjectDetail(projectId);
	}

	@Override
	public List<RPCLivelihoodBean> getLiveEPAProdDetailProjectWise(Integer distid, String headtypeh) {
		// TODO Auto-generated method stub
		return livelihoodDao.getLiveEPAProdDetailProjectWise( distid,  headtypeh);
	}

	@Override
	public List<RPCLivelihoodBean> getLiveEPAProdDetailAvgProjectWise(Integer distid, String headtypeh) {
		// TODO Auto-generated method stub
		return livelihoodDao.getLiveEPAProdDetailAvgProjectWise(distid, headtypeh);
	}

	@Override
	public List<RPCLivelihoodBean> getLiveEPAProdDetailSProjectWise(Integer distid, String headtypeh,
			Integer projcode) {
		
		return livelihoodDao.getLiveEPAProdDetailSProjectWise(distid, headtypeh, projcode);
	}

	@Override
	public String completeAllEPALivelihoodProduction(Integer sentfrom, List<BigInteger> tempassetid, String scheme) {
		// TODO Auto-generated method stub
		return livelihoodDao.completeAllEPALivelihoodProduction(sentfrom,  tempassetid, scheme);
	}

	@Override
	public List<RPCLivelihoodBean> findlivelidesc(Integer id) {
		// TODO Auto-generated method stub
		return livelihoodDao.findlivelidesc(id);
	}

	@Override
	public Boolean updatepostdata(Integer id, Integer livepost, String loginID) {
	   return livelihoodDao.updatepostdata(id, livepost, loginID);
	}

	@Override
	public List<RPCLivelihoodBean> findproddesc(Integer id) {
		// TODO Auto-generated method stub
		return livelihoodDao.findproddesc(id);
	}

	@Override
	public Boolean updateprodpostdata(Integer id, Integer prodpost) {
		// TODO Auto-generated method stub
		return livelihoodDao.updateprodpostdata(id, prodpost);
	}


}
