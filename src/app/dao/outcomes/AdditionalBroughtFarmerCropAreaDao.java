package app.dao.outcomes;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;

public interface AdditionalBroughtFarmerCropAreaDao {
	
	LinkedHashMap<Integer,String> getFinYearonward22();
	List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropDraft(Integer projectId, Integer month, Integer year);
	List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropComplete(Integer projectId);
	String saveAdditionalBroughtFarmerCropArea(Integer projId, Integer month, Integer year, BigDecimal diversified, BigDecimal chnagesingle, BigDecimal farmer, BigDecimal changecorp, 
			Character status, String loginId, Integer additionalid, String atype, BigDecimal pulses, BigDecimal oilseeds);
	String completeAdditionalBroughtFarmerCropArea(Integer projId, Integer month, Integer year, Character status, String loginId, Integer additionalid);
	List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropAreaDraft(Integer project, String atline, Integer fyear, Integer month);
	List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtMonthComplt(Integer project, String atline, Integer fyear, Integer month);
	List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtYearComplt(Integer project, String atline, Integer fyear);
	LinkedHashMap<Integer, List<AdditionalBroughtFarmerCropAreaBean>> getstateWiseAdditionalAchievement(Integer stateCode, Integer finyr);
	List<AdditionalBroughtFarmerCropAreaBean> getnotyearlyPara(Integer dcode, String type, Integer finyr);
	
	LinkedHashMap<Integer,String> getFinYearAdditional(Integer project, String atline);
	//LinkedHashMap<String, Integer> getRemainYear(Integer project, String atline);
	int getmonthidtoclosed();
}
