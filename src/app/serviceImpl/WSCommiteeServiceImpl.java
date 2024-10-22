package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.WSCommiteeDao;
import app.model.IwmpMProject;
import app.model.master.IwmpMWc;
import app.service.WSCommiteeService;

@Service("WSCommiteeService")
public class WSCommiteeServiceImpl implements WSCommiteeService{

	@Autowired
	private WSCommiteeDao wsdao;
	
	@Override
	//@Transactional
	public Map<String, String> getWatershedCommitteeList(Integer regid) 
	{
		Map<String, String> wscList=new LinkedHashMap<String, String>();
		for (IwmpMWc temp : wsdao.getWatershedCommitteeList(regid)) 
		{
			wscList.put(temp.getWcId()+"", temp.getWcname());
		}
		return wscList ;
	}

	@Override
	//@Transactional
	public Map<String, String> getUserProjectList(int regid) {
		Map<String, String> uprojList=new LinkedHashMap<String, String>();
		for (IwmpMProject temp : wsdao.getUserProjectList(regid)) 
		{
			uprojList.put(temp.getProjectId()+"", temp.getProjName());
		}
		return uprojList;
	}

	@Override
	public Integer saveWatershedCommittee(Integer ProjId, String wcname, HttpSession sss) {
		return wsdao.saveWatershedCommittee(ProjId, wcname, sss);
	}

	@Override
	public Integer updateWatershedCommittee(IwmpMWc mwc, HttpSession session) {
		return wsdao.updateWatershedCommittee( mwc, session);
	}

	@Override
	public Integer deleteWatershedCommittee(String wc_code) {
		return wsdao.deleteWatershedCommittee( wc_code);
	}

	@Override
	public List<IwmpMWc> getWCLocationAdded(int regid) {
		// TODO Auto-generated method stub
		return wsdao.getWCLocationAdded(regid) ;
	}

	@Override
	public List getlistofApprove() {
		
		return wsdao.getlistofApprove();
	}

	@Override
	public List checkWSCommittee(Integer ProjId) {
		// TODO Auto-generated method stub
		return wsdao.checkWSCommittee( ProjId);
	}

}
