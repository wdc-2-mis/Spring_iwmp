package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.model.WdcpmksyMQuadIndicators;
import app.model.WdcpmksyQuadTarget;

public interface QuarterlyTargetDao {

	LinkedHashMap<Integer, String> getQuarterList();

	List<WdcpmksyMQuadIndicators> getTargetList(int projId, int year);

	LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> getTargetData();

	LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> getAnnualTargetData(Integer project, Integer financial);

	
	Integer saveprojectpreparedness(int projId, Character status, int financial, String[] quarter1, String[] quarter2, String[] quarter3,
			String[] quarter4, String[] indicatorid, String q1s, String q2s, String q3s, String q4s, Integer stcode, String loginId);

	List<WdcpmksyMQuadIndicators> getindicatorsdata(Integer project, Integer financial);

	List<WdcpmksyMQuadIndicators> getquartSLNAdata(Integer projId, Integer stcode);

	List<WdcpmksyMQuadIndicators> getsingleslnadata(String achievementdtl);

	String SLNAApproveService(String quardDtl);

	String SLNAAllQuadApproveService(String quardDtl, String status);

	List<WdcpmksyMQuadIndicators> getSLNACompleteData(Integer stcode);

	List<WdcpmksyMQuadIndicators> getcompletedquaddata(String quarterdtl);

}
