package app.watershedyatra.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.watershedyatra.bean.InaugurationBean;

@Service("InaugurationService")
public interface InaugurationService {
	
	public String saveInauguration(InaugurationBean userfileup, HttpSession session);
	public List<InaugurationBean> getInaugurationDetails(Integer stcd);
	public List<String> getImagesByInaugurationId(int inaugurationId);
	
	String getExistingBlockInaguraCodes(Integer bCode);
	
	String deleteInaugurationDetails(List<Integer> assetid, String userid);
	
	String completeInaugurationDetails(List<Integer> assetid, String userid);
	
	public List<InaugurationBean> editInaugurationDetails(Integer inagur);
	
	public String updateInaugurationDetails(InaugurationBean userfileup, HttpSession session);
	
	public List<InaugurationBean> getInaugurationDetailsComp(Integer stcd);
	
}
