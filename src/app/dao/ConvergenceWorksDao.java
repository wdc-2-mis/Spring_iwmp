package app.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import app.bean.ConvergenceStatusBean;
import app.bean.ConvergenceWorksBean;
import app.model.ConvergenceWorkDetail;

public interface ConvergenceWorksDao {
	
	List<ConvergenceWorksBean> getListConvergenceWorkd(Integer projid);
	Map<Integer, String> getConvergenceSchemeDetails();
	String saveasdraftcnvrgdtl(List<String> workcode, List<String> cnvrgstatus, List<String> cvrgschmeid, List<String> expndwdc, List<String> expndcnvrgschm);
	List<ConvergenceWorkDetail> getConvergenceWorkDetail();
	String deleteCnvrgnceWorkCode(Integer workcode);
	String completeConvergenceWorkCode(String cnvrgnceId[]);	
	LinkedHashMap<Integer, String> getAllSchemes();
	List<ConvergenceWorksBean> getConvergenceWorks(Integer stcd, Integer dcode, Integer projid);
	List<ConvergenceWorksBean> getConvergedWorks();
	List<ConvergenceWorksBean> getConvergedDistWorks(Integer stcd);
	List<ConvergenceWorksBean> getConvergedProjWorks(Integer dcode);
	List<ConvergenceWorksBean> getConvergedProjDetailWorks(Integer projid);
	List<ConvergenceWorksBean> getConvergedExpndtr(Integer stcd, Integer dcode, Integer projid, Integer finyr, Integer sid);
	List<ConvergenceWorksBean> getEnteredConWorks();
	List<ConvergenceWorksBean> getConvergedStateExpndtr();
	List<ConvergenceWorksBean> getStatusConWorks(Integer stcd);
	List<ConvergenceWorksBean> getRemainingConWorks(Integer dcode);
	List<ConvergenceStatusBean> getConvergenceStatus(Integer projId, Integer head, Integer activity);
	String updateConvergenceStatus(List<String> workcode, List<String> cnvrgstatus);
	
}
