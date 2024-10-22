package app.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.model.master.IwmpMWc;

@Service("WSCommiteeService")
public interface WSCommiteeService {
	
	Map<String, String>  getUserProjectList( int regid);
	Map<String, String>  getWatershedCommitteeList( Integer regid);
	Integer saveWatershedCommittee(Integer ProjId, String wcname, HttpSession ss);
	Integer updateWatershedCommittee(IwmpMWc mwc, HttpSession session);
	Integer deleteWatershedCommittee(String wc_code);
	List checkWSCommittee(Integer ProjId);
	List<IwmpMWc>  getWCLocationAdded(int regid);
	List getlistofApprove();
}
