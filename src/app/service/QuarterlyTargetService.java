package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.AddOutcomeParaBean;
import app.model.WdcpmksyMQuadIndicators;
import app.model.WdcpmksyQuadTarget;

@Service("quarterlyTargetService")
public interface QuarterlyTargetService {

	LinkedHashMap<Integer,String> getQuarterList();
	LinkedHashMap<Integer,List<WdcpmksyMQuadIndicators>> getTargetData();
	List<WdcpmksyMQuadIndicators> getTargetList(int projId, int year);
	LinkedHashMap<Integer,List<WdcpmksyMQuadIndicators>> getAnnualTargetData(Integer project, Integer financial);
	Integer saveprojectpreparedness(int ProjId, Character status, int financial, String[] quarter1, String[] quarter2,
			String[] quarter3, String[] quarter4, String[] indicatorid, String q1s, String q2s, String q3s, String q4s, Integer stcode, String loginId);
	List<WdcpmksyMQuadIndicators> getindicatorsdata(Integer project, Integer financial);
	List<WdcpmksyMQuadIndicators> getquartSLNAdata(Integer projId, Integer stcode);
	List<WdcpmksyMQuadIndicators> getsingleslnadata(String achievementdtl);
	String SLNAApproveService(String quardDtl);
	String SLNAAllQuadApproveService(String quardDtl, String status);
	List<WdcpmksyMQuadIndicators> getSLNACompleteData(Integer stcode);
	List<WdcpmksyMQuadIndicators> getcompletedquaddata(String quarterdtl);
	
	

	
	
	
}
