package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import app.bean.AuditReportBean;
import app.model.UserReg;
@Service("auditService")
public interface AuditService {
	LinkedHashMap<Integer,List<AuditReportBean>> getOnlineUserList();
	


}
