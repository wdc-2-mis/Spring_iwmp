package app.watershedyatra.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.watershedyatra.bean.InaugurationBean;

public interface InaugurationService {
	
	public String saveInauguration(InaugurationBean userfileup, HttpSession session);
	public List<InaugurationBean> getInaugurationDetails(Integer stcd);
	public List<String> getImagesByInaugurationId(int inaugurationId);
	
	String getExistingBlockInaguraCodes(Integer bCode);
	
	String deleteInaugurationDetails(List<Integer>  assetid, String userid);
	
	
	
	
}
