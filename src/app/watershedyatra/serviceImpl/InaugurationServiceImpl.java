package app.watershedyatra.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.daoImpl.InaugurationDaoImpl;
import app.watershedyatra.service.InaugurationService;

@Service("InaugurationService")
public class InaugurationServiceImpl implements InaugurationService {
	
	@Autowired
	InaugurationDaoImpl dao;
	
	public String saveInauguration(InaugurationBean userfileup, HttpSession session) {
		return dao.saveInauguration(userfileup, session);
	}

	public List<InaugurationBean> getInaugurationDetails(Integer stcd) {
		return dao.getInaugurationDetails(stcd);
	}

	@Override
	public List<String> getImagesByInaugurationId(int inaugurationId) {
		return dao.getImagesByInaugurationId(inaugurationId);
	}

	@Override
	public String getExistingBlockInaguraCodes(Integer bCode) {
		return dao.getExistingBlockInaguraCodes(bCode);
	}

	@Override
	public String deleteInaugurationDetails(List<Integer> assetid, String userid) {
		return dao.deleteInaugurationDetails(assetid, userid);
	}

	@Override
	public String completeInaugurationDetails(List<Integer> assetid, String userid) {
		return dao.completeInaugurationDetails(assetid, userid);
	}

	@Override
	public List<InaugurationBean> getInaugurationDetailsComp(Integer stcd) {
		return dao.getInaugurationDetailsComp(stcd);
	}
	

}
