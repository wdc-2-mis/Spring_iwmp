package app.service.outcome;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.RPCLivelihoodBean;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.LivelihoodEpaProd;
import app.model.outcome.ProductionDetail;

@Service("LivelihoodProductionEpaService")
public interface LivelihoodProductionEpaService {
	LinkedHashMap<Integer, String> getLivelihoodCoreActivity();

	LinkedHashMap<Integer, String> getProductionCoreActivity();

	LinkedHashMap<Integer, String> getEpaCoreActivity();

	public String addLivelihoodProductionEPA(LivelihoodEpaProd livelihoodProductionEpa);

	String deleteLivelihoodDetail(int livelihoodDetailId);

	String completeLivelihoodDetail(int livelihoodDetailId);

	String deleteProductionDetail(int ProductionDetailId);

	String completeProductionDetail(int ProductionDetailId);
	String deleteEpaDetail(int EpaDetailId);

	String completeEpaDetail(int EpaDetailId);
	List<RPCLivelihoodBean> getRptLivelihoodList(Integer stcode);

	List<RPCLivelihoodBean> getRptProductionList(Integer stcode);
	List<RPCLivelihoodBean> getRptEpaList(Integer stcode);
	List<LivelihoodDetail> getLivelihoodDetail(Integer regId);

	List<ProductionDetail> getProductionDetail(Integer regId);

	List<EpaDetail> getEpaDetail(Integer regId);

	List<LivelihoodDetail> getLivelihoodProjectDetail(Integer projectId);

	List<ProductionDetail> getProductionProjectDetail(Integer projectId);

	List<EpaDetail> getEpaProjectDetail(Integer projectId);

	LinkedHashMap<Integer, String> getCropList();
	List<RPCLivelihoodBean> getRptLivelihoodDistList(String stcd, String headtypeh);
	
	List<RPCLivelihoodBean> getLiveEPAProdDetailProjectWise(Integer distid, String headtypeh);
	
	List<EpaDetail> getEpaDetailProjectWise(Integer distid);
	List<LivelihoodDetail>  getLivelihoodDetailProjectWise(Integer distid);
	List<ProductionDetail> 	getProductionDetailProjectWise(Integer distid);

	List<RPCLivelihoodBean> getLiveEPAProdDetailAvgProjectWise(Integer distid, String headtypeh);

	List<RPCLivelihoodBean> getLiveEPAProdDetailSProjectWise(Integer distid, String headtypeh, Integer projcode);
	String completeAllEPALivelihoodProduction(Integer sentfrom,List<BigInteger> tempassetid, String scheme);

	List<RPCLivelihoodBean> findlivelidesc(Integer id);

	Boolean updatepostdata(Integer id, Integer livepost, String loginID);

	List<RPCLivelihoodBean> findproddesc(Integer id);

	Boolean updateprodpostdata(Integer id, Integer prodpost);
}
