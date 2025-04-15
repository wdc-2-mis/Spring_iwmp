package app.janbhagidariPratiyogita;

import java.util.LinkedHashMap;
import java.util.List;

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
			int novillage, String projarea, String projoutlay, int funoutlay, String projexp, String expper, String swckgp, HttpSession session) {
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

}
