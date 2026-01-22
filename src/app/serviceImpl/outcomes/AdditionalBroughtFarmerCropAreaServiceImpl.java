package app.serviceImpl.outcomes;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.dao.outcomes.AdditionalBroughtFarmerCropAreaDao;
import app.model.project.WdcpmksyAdditionalBroughtFarmerCrop;
import app.service.outcome.AdditionalBroughtFarmerCropAreaServices;

@Service("AdditionalBroughtFarmerCropAreaServices")
public class AdditionalBroughtFarmerCropAreaServiceImpl implements AdditionalBroughtFarmerCropAreaServices{

	@Autowired
	AdditionalBroughtFarmerCropAreaDao dao;
	
	
	@Override
	public LinkedHashMap<Integer, String> getFinYearonward22() {
		// TODO Auto-generated method stub
		return dao.getFinYearonward22();
	}


	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropDraft(Integer projectId, Integer month,
			Integer year) {
		// TODO Auto-generated method stub
		return dao.getAdditionalBroughtFarmerCropDraft(projectId, month, year);
	}


	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropComplete(Integer projectId) {
		// TODO Auto-generated method stub
		return dao.getAdditionalBroughtFarmerCropComplete(projectId);
	}


	@Override
	public String saveAdditionalBroughtFarmerCropArea(Integer projId, Integer month, Integer year,
			BigDecimal diversified, BigDecimal chnagesingle, BigDecimal farmer, BigDecimal changecorp, Character status,
			String loginId, Integer additionalid, String atype, BigDecimal pulses, BigDecimal oilseeds) {
		// TODO Auto-generated method stub
		return dao.saveAdditionalBroughtFarmerCropArea(projId, month, year, diversified, chnagesingle, farmer, changecorp, status, loginId, additionalid, atype, pulses, oilseeds);
	}


	@Override
	public String completeAdditionalBroughtFarmerCropArea(Integer projId, Integer month, Integer year, Character status,
			String loginId, Integer additionalid) {
		// TODO Auto-generated method stub
		return dao.completeAdditionalBroughtFarmerCropArea(projId, month, year, status, loginId, additionalid);
	}


	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtFarmerCropAreaDraft(Integer project,
			String atline, Integer fyear, Integer month) {
		// TODO Auto-generated method stub
		return dao.getAdditionalBroughtFarmerCropAreaDraft(project, atline, fyear, month);
	}


	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtMonthComplt(Integer project, String atline,
			Integer fyear, Integer month) {
		// TODO Auto-generated method stub
		return dao.getAdditionalBroughtMonthComplt(project, atline, fyear, month);
	}


	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtYearComplt(Integer project, String atline,
			Integer fyear) {
		// TODO Auto-generated method stub
		return dao.getAdditionalBroughtYearComplt(project, atline, fyear);
	}



	@Override
	public LinkedHashMap<Integer, String> getFinYearAdditional(Integer project, String atline) {
		// TODO Auto-generated method stub
		return dao.getFinYearAdditional(project, atline);
	}


	/*
	 * @Override public LinkedHashMap<String, Integer> getRemainYear(Integer
	 * project, String atline) { // TODO Auto-generated method stub return
	 * dao.getRemainYear(project, atline); }
	 */


	@Override
	public LinkedHashMap<Integer, List<AdditionalBroughtFarmerCropAreaBean>> getstateWiseAdditionalAchievement(
			Integer stateCode, Integer finyr) {
		// TODO Auto-generated method stub
		return dao.getstateWiseAdditionalAchievement(stateCode, finyr);
	}


	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getnotyearlyPara(Integer dcode, String type, Integer finyr) {
		// TODO Auto-generated method stub
		return dao.getnotyearlyPara(dcode, type, finyr);
	}


	@Override
	public int getmonthidtoclosed() {
		// TODO Auto-generated method stub
		return dao.getmonthidtoclosed();
	}


	@Override
	public String AdditionalBroughtFarmerSLNAforward(String loginID, Integer additionalid) {
		// TODO Auto-generated method stub
		return dao.AdditionalBroughtFarmerSLNAforward(loginID, additionalid);
	}


	@Override
	public List<Object[]> getAddBroughtProjFromDist(Integer dCode) {
		// TODO Auto-generated method stub
		return dao.getAddBroughtProjFromDist(dCode);
	}


	@Override
	public LinkedHashMap<Integer, String> getFarmerCropFinYear() {
		// TODO Auto-generated method stub
		return dao.getFarmerCropFinYear();
	}


	@Override
	public AdditionalBroughtFarmerCropAreaBean findByYear(String financialYear) {
		// TODO Auto-generated method stub
		return dao.findByYear(financialYear);
	}


	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getSLNAPendingAddFarmerCrop(Integer projectId, Integer district, Integer fyear) {
		return dao.getSLNAPendingAddFarmerCrop(projectId, district, fyear);
	}


	@Override
	public boolean updateAdditionalFarmerCropStatus(Integer id, String action, String remark, String userId) {
		// TODO Auto-generated method stub
		return dao.updateAdditionalFarmerCropStatus(id, action, remark, userId);
	}


	@Override
	public List<AdditionalBroughtFarmerCropAreaBean> getSLNACompleteAddBroughtFarmer(Integer stcode) {
		// TODO Auto-generated method stub
		return dao.getSLNACompleteAddBroughtFarmer(stcode);
	}


	


}
