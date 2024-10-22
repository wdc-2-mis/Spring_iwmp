package app.dao.master;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.model.MDepartmentScheme;

public interface DepartmentSchemeMasterDao {
	
	List<MDepartmentScheme> getDeptSchemeMaster();
	String saveDeptSchemeMasterData(String desc, BigDecimal seqno, String type, HttpSession session);
	Boolean deleteDeptSchemeData(int id, String schemetype);
	List<MDepartmentScheme> deptschememasterfind(Integer id);
	String updateDeptSchemeData(int id, String desc, BigDecimal seqno, String status, HttpSession session);
}
