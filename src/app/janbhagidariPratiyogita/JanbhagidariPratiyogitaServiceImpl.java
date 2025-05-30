package app.janbhagidariPratiyogita;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("JanbhagidariPratiyogitaService")
public class JanbhagidariPratiyogitaServiceImpl implements JanbhagidariPratiyogitaService{
	
	
	@Autowired
	JanbhagidariPratiyogitaDao dao;
	
	
	

	@Override
	public LinkedHashMap<String, Integer> getJanbhagidariPratiyogitaProject(Integer distcd) {
		
		return dao.getJanbhagidariPratiyogitaProject(distcd);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getJanbhagidariPratiyogitaProjectData(Integer project) {
		// TODO Auto-generated method stub
		return dao.getJanbhagidariPratiyogitaProjectData(project);
	}




	@Override
	public String saveJanbhagidariPratiyogita(List<String> vill, List<String> ngoname, int dcode, int proj, int nogp, 
			int novillage, String projarea, String projoutlay, String funoutlay, String projexp, String expper, String swckgp, HttpSession session) {
		// TODO Auto-generated method stub
		return dao.saveJanbhagidariPratiyogita(vill, ngoname, dcode, proj,  nogp, 
				novillage, projarea, projoutlay, funoutlay, projexp, expper, swckgp, session);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getDraftListDetails(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getDraftListDetails(stcd);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getCompleteListDetails(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getCompleteListDetails(stcd);
	}




	@Override
	public String deleteJanbhagidariPratiyogita(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.deleteJanbhagidariPratiyogita(assetid, userid);
	}




	@Override
	public String completeJanbhagidariPratiyogita(List<Integer> assetid, String userid) {
		// TODO Auto-generated method stub
		return dao.completeJanbhagidariPratiyogita(assetid, userid);
	}




	@Override
	public LinkedHashMap<Integer, String> getGPofJanbhagidariPratiyogita(Integer project) {
		// TODO Auto-generated method stub
		return dao.getGPofJanbhagidariPratiyogita(project);
	}




	@Override
	public LinkedHashMap<Integer, String> getVILLofJanbhagidariPratiyogita(List<Integer> gcode, Integer projectid) {
		// TODO Auto-generated method stub
		return dao.getVILLofJanbhagidariPratiyogita(gcode, projectid);
	}




	@Override
	public String getExistingProjectCodes(Integer pCode) {
		// TODO Auto-generated method stub
		return dao.getExistingProjectCodes(pCode);
	}




	@Override
	public Integer getTotalNoofGP(Integer dCode) {
		// TODO Auto-generated method stub
		return dao.getTotalNoofGP(dCode);
	}




	@Override
	public Integer getTotalNoofVill(Integer dCode) {
		// TODO Auto-generated method stub
		return dao.getTotalNoofVill(dCode);
	}




	@Override
	public boolean isNGONameExists(String ngoName, int projectId) {
		// TODO Auto-generated method stub
		return dao.isNGONameExists(ngoName, projectId);
	}
	
	@Override
	public List<JanbhagidariPratiyogitaBean> getListJanbhagidariPratiyogitaDetails() {
		// TODO Auto-generated method stub
		return dao.getListJanbhagidariPratiyogitaDetails();
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> janbhagidariPratiyogitaALLReport(String State, String district,
			String project) {
		// TODO Auto-generated method stub
		return dao.janbhagidariPratiyogitaALLReport(State, district, project);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getListofNGONameWithGPandVillage(Integer project) {
		// TODO Auto-generated method stub
		return dao.getListofNGONameWithGPandVillage(project);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getListofswckGPand(Integer projid) {
		// TODO Auto-generated method stub
		return dao.getListofswckGPand(projid);
	}

	@Override
	public List<JanbhagidariPratiyogitaBean> getDraftListPIADetails(Integer stcd, String username) {
		// TODO Auto-generated method stub
		return dao.getDraftListPIADetails(stcd, username);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getCompleteListPIADetails(Integer stcd, String username) {
		// TODO Auto-generated method stub
		return dao.getCompleteListPIADetails(stcd, username);
	}
	
	
	@Override
	public List<JanbhagidariPratiyogitaBean> getjanbhagidariPratiyogitaStatusReport() {
		// TODO Auto-generated method stub
		return dao.getjanbhagidariPratiyogitaStatusReport();
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getjanbhagidariPratiyogitaDistStatusReport(int id) {
		// TODO Auto-generated method stub
		return dao.getjanbhagidariPratiyogitaDistStatusReport(id);
	}




	@Override
	public String saveJanbhagidariPratiyogitaActivity(int dcode, int proj, String vill, String workList,
			String estValueList, String villagersList, String ngosList, String corporateList,
			String compWorkList,String completedDateList, HttpSession session, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.saveJanbhagidariPratiyogitaActivity(dcode, proj, vill, workList, estValueList, villagersList, ngosList, corporateList, compWorkList, completedDateList, session, request);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getActivityDraftListDetails(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getActivityDraftListDetails(stcd);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getActivityDraftListPIADetails(Integer stcd, String username) {
		// TODO Auto-generated method stub
		return dao.getActivityDraftListPIADetails(stcd, username);
	}




	@Override
	public String deleteJanbhagidariActivity(List<Integer> assetid) {
		// TODO Auto-generated method stub
		return dao.deleteJanbhagidariActivity(assetid);
	}




	@Override
	public String completeJanbhagidariActivity(List<Integer> assetid, String createdBy) {
		// TODO Auto-generated method stub
		return dao.completeJanbhagidariActivity(assetid, createdBy);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getActivityCompleteListDetails(Integer stcd) {
		// TODO Auto-generated method stub
		return dao.getActivityCompleteListDetails(stcd);
	}


	@Override
	public List<JanbhagidariPratiyogitaBean> getActivityCompleteListPIADetails(Integer stcd, String username) {
		// TODO Auto-generated method stub
		return dao.getActivityCompleteListPIADetails(stcd, username);
	}




	@Override
	public String checkdupWorkEntry(Integer villageId, Integer workId) {
		// TODO Auto-generated method stub
		return dao.checkdupWorkEntry(villageId, workId);
	}
	
	@Override
	public List<JanbhagidariPratiyogitaBean> getjanbhagidariPratiyogitaActivitiesStatus() {
		// TODO Auto-generated method stub
		return dao.getjanbhagidariPratiyogitaActivitiesStatus();
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getjanbhagidariNoStatus(Integer stcd, Integer district, Integer projid) {
		// TODO Auto-generated method stub
		return dao.getjanbhagidariNoStatus(stcd, district, projid);
	}




	@Override
	public String updateJanbhagidariCompDate(List<Integer> assetid, List<String> compworkval, List<String> completedDate, String createdBy) {
		return dao.updateJanbhagidariCompDate(assetid, compworkval, completedDate, createdBy);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getjanbhagidariPIANoStatusWithProj(String projid) {
		// TODO Auto-generated method stub
		return dao.getjanbhagidariPIANoStatusWithProj(projid);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getjanbhagidariPIANoStatus(Integer regId) {
		// TODO Auto-generated method stub
		return dao.getjanbhagidariPIANoStatus(regId);
	}
	
	@Override
	public List<JanbhagidariPratiyogitaBean> getjanbhagidariPratiyogitaActivitiesDistrict(int id) {
		// TODO Auto-generated method stub
		return dao.getjanbhagidariPratiyogitaActivitiesDistrict(id);
	}




	@Override
	public List<JanbhagidariPratiyogitaBean> getJanbhagidariPratiyogitaProjectExist(Integer project) {
		// TODO Auto-generated method stub
		return dao.getJanbhagidariPratiyogitaProjectExist(project);
	}


	
}
