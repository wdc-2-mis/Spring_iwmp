package app.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import app.model.IwmpMProject;
import app.model.master.IwmpMWc;

public interface WSCommiteeDao {
	
	List<IwmpMProject>  getUserProjectList( int regid);
	List<IwmpMWc> getWatershedCommitteeList(Integer regid);
	Integer saveWatershedCommittee(Integer ProjId, String wcname, HttpSession sss);
	Integer updateWatershedCommittee(IwmpMWc mwc, HttpSession session);
	Integer deleteWatershedCommittee(String wc_code);
	List<IwmpMWc>  getWCLocationAdded(int regid);
	List getlistofApprove();
	List checkWSCommittee(Integer ProjId);
}
