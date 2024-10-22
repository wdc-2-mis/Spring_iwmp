package app.dao.master;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import app.bean.PfmsTransactionBean;

public interface PfmsDao {
	
	List<PfmsTransactionBean> getPfmsTransaction(Integer mstrLvl, Integer projId);
	String saveAsDraftPfmsTransaction(String eatmisdataId[], Integer projId);
	String deletePfmsTransaction(Integer eatmisdataId);
	String completePfmsTransaction(String eatmisdataId[]);
	LinkedHashMap<Integer, String> getProjectByStCode(Integer stCode);
	List<PfmsTransactionBean> getPfmsWorkTransaction(Integer regId, Integer projId, Integer finyear);
	LinkedHashMap<Integer, String> getworkiddtl(int projId);
	String saveAsDraftPfmsWorkId(List<String> workid, List<String> eatmisdataId, List<String> expenditure, List<Integer> totalworks, String createdby, HttpServletRequest request);
	LinkedHashMap<Integer, String> getworkiddraftdtl(List<Integer> wicode);
	String deletePfmsworkTransaction(Integer eatmisdataId);
	String completePfmsWorkIdTransaction(String[] eatmisdataId);
	List<PfmsTransactionBean> getworkidexpdtl(Integer id);
	

}
