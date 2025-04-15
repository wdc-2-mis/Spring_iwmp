package app.janbhagidariPratiyogita;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.watershedyatra.bean.InaugurationBean;

@Service("JanbhagidariPratiyogitaService")
public interface JanbhagidariPratiyogitaService {
	
	
	LinkedHashMap<String, Integer> getJanbhagidariPratiyogitaProject(Integer distcd);
	
	List<JanbhagidariPratiyogitaBean> getJanbhagidariPratiyogitaProjectData(Integer project);
	
	public String saveJanbhagidariPratiyogita(List<String> vill, List<String> ngoname, int dcode, int proj,  int nogp, 
			int novillage, String projarea, String projoutlay, int funoutlay, String projexp, String expper, String swckgp, HttpSession session);
	
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

	
	
	
	

}
