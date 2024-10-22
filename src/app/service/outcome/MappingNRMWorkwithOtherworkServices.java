package app.service.outcome;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import app.bean.menu.MappingNRMWorkwithOtherworkBean;
import app.model.IwmpProjectAssetStatus;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyMappingNRMWorkOtherWork;

public interface MappingNRMWorkwithOtherworkServices {
	
	List<IwmpProjectPhysicalAsset> getassetWorkWiseList(Integer pCode, String userId, Integer fYear, Integer head, Integer activity, Integer nrmwork);
	
	LinkedHashMap<Integer,String> getOtherHeadActivity(String headtype, Integer workid);
	
	LinkedHashMap<Integer,String> getOtherHeadActivityWork(String headtyp, Integer acttypes, Integer proj, Integer assetid);

	String checkNRMandOtherWorkMatch(String headtyp, Integer otherwork, Integer proj, Integer nrmwork);
	
	String saveNRMwithOtherAsset(List<BigInteger>  assetid, Integer projcd,String userid, List<String> headtype, List<Integer>  otherwork);
	
	List<WdcpmksyMappingNRMWorkOtherWork> getmappingNRMWorkDraft(Integer pCode);
	
	String deleteNRMwithOtherAsset(List<Integer>  assetid);
	
	String completeNRMwithOtherAsset(List<Integer>  assetid, String userid);
	
	List<MappingNRMWorkwithOtherworkBean> getCompletedNRMRelatedOtherWork(String scheme, Integer projcd);
	
	String checkNRMValidORNot(Integer nrmwork, Integer regid);
	
	List<WdcpmksyMappingNRMWorkOtherWork> getWorkIdWiseNRMWorkDraft(Integer nrmwork);
}
