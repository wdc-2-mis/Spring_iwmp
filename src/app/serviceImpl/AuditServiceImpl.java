package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AuditReportBean;
import app.dao.AuditDao;
import app.model.UserReg;
import app.service.AuditService;

@Service("auditService")
public class AuditServiceImpl implements AuditService {
	
	@Autowired
	AuditDao auditDao;

	@Override
	//@Transactional
	public LinkedHashMap<Integer, List<AuditReportBean>> getOnlineUserList() {
		// TODO Auto-generated method stub
		return auditDao.getOnlineUserList();
		
		
	}

	
}
