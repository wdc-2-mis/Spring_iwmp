package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.DisApprovalBean;


@Service("disApprovalService")
public interface Disapprovalservice {

	
	public List<DisApprovalBean> checkstatus(Integer stcode);

	public Boolean updateslnadisapp(String val, Integer stcode);

	LinkedHashMap<Integer,String> getAllState();
}
