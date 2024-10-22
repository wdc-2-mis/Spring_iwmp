package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.DisApprovalBean;
import app.dao.Disapprovaldao;
import app.service.Disapprovalservice;

@Service("disApprovalService")
public class DisapprovalServiceImpl implements Disapprovalservice{

	@Autowired
	Disapprovaldao disapprovaldao;

	@Override
	public List<DisApprovalBean> checkstatus(Integer stcode) {
		
		return disapprovaldao.checkstatus(stcode);
	}

	@Override
	//@Transactional
	public LinkedHashMap<Integer, String> getAllState() {
		// TODO Auto-generated method stub
		return disapprovaldao.getAllState();
	}
	
	@Override
	public Boolean updateslnadisapp(String val, Integer stcode) {
		
		return disapprovaldao.updateslnadisapp(val, stcode);
	}
}
