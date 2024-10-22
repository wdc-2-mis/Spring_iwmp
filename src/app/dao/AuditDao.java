package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import app.bean.AuditReportBean;
import app.model.UserReg;

public interface AuditDao {
	LinkedHashMap<Integer,List<AuditReportBean>> getOnlineUserList();
}
