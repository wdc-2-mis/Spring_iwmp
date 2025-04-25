package app.janbhagidariPratiyogita;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface JanbhagidariPratiyogitaDao {
	
	LinkedHashMap<String, Integer> getJanbhagidariPratiyogitaProject(Integer distcd);
	
	List<JanbhagidariPratiyogitaBean> getJanbhagidariPratiyogitaProjectData(Integer project);
	
	public String saveJanbhagidariPratiyogita(List<String> vill, List<String> ngoname, int dcode, int proj,  int nogp, 
			int novillage, String projarea, String projoutlay, String funoutlay, String projexp, String expper, String bank, HttpSession session);
	
	List<JanbhagidariPratiyogitaBean> getDraftListDetails(Integer stcd);
	
	List<JanbhagidariPratiyogitaBean> getCompleteListDetails(Integer stcd);
	
	String deleteJanbhagidariPratiyogita(List<Integer>  assetid, String userid);
	
	String completeJanbhagidariPratiyogita(List<Integer>  assetid, String userid);
	
	LinkedHashMap<Integer, String> getGPofJanbhagidariPratiyogita(Integer project);
	
	LinkedHashMap<Integer, String> getVILLofJanbhagidariPratiyogita(List<Integer> gcode, Integer projectid);
	
	String getExistingProjectCodes(Integer pCode);
	
	Integer getTotalNoofGP(Integer dCode);
	
	Integer getTotalNoofVill(Integer dCode);

	boolean isNGONameExists(String ngoName, int projectId);
	
	List<JanbhagidariPratiyogitaBean> getListJanbhagidariPratiyogitaDetails();
	
	List<JanbhagidariPratiyogitaBean> janbhagidariPratiyogitaALLReport(String State, String district, String project);
	
	List<JanbhagidariPratiyogitaBean> getListofNGONameWithGPandVillage(Integer project);
	
	List<JanbhagidariPratiyogitaBean> getListofswckGPand(Integer projid);

	List<JanbhagidariPratiyogitaBean> getDraftListPIADetails(Integer stcd, String username);

	List<JanbhagidariPratiyogitaBean> getCompleteListPIADetails(Integer stcd, String username);

	List<JanbhagidariPratiyogitaBean> getjanbhagidariPratiyogitaStatusReport();
	
	List<JanbhagidariPratiyogitaBean> getjanbhagidariPratiyogitaDistStatusReport(int id);

	String saveJanbhagidariPratiyogitaActivity(int dcode, int proj, List<String> vill, List<String> workList,
			List<String> estValueList, List<String> villagersList, List<String> ngosList, List<String> corporateList,
			List<String> compWorkList, List<String> completedDateList, HttpSession session, HttpServletRequest request);

	List<JanbhagidariPratiyogitaBean> getActivityDraftListDetails(Integer stcd);

	List<JanbhagidariPratiyogitaBean> getActivityDraftListPIADetails(Integer stcd, String username);

	String deleteJanbhagidariActivity(List<Integer> assetid);

	String completeJanbhagidariActivity(List<Integer> assetid, String createdBy);

	List<JanbhagidariPratiyogitaBean> getActivityCompleteListDetails(Integer stcd);

	List<JanbhagidariPratiyogitaBean> getActivityCompleteListPIADetails(Integer stcd, String username);

	String checkdupWorkEntry(Integer villageId, Integer workId);


}
