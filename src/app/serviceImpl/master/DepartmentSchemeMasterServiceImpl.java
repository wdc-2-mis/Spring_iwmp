package app.serviceImpl.master;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.master.DepartmentSchemeMasterDao;
import app.model.MDepartmentScheme;
import app.service.master.DepartmentSchemeMasterService;

@Service("DepartmentSchemeMasterService")
public class DepartmentSchemeMasterServiceImpl implements DepartmentSchemeMasterService{

	@Autowired
	DepartmentSchemeMasterDao dsmd;
	
	@Override
	public List<MDepartmentScheme> getDeptSchemeMaster() {
		// TODO Auto-generated method stub
		return dsmd.getDeptSchemeMaster();
	}

	@Override
	public String saveDeptSchemeMasterData(String desc, BigDecimal seqno, String type, HttpSession session) {
		// TODO Auto-generated method stub
		return dsmd.saveDeptSchemeMasterData(desc, seqno, type, session);
	}

	@Override
	public Boolean deleteDeptSchemeData(int id, String schemetype) {
		// TODO Auto-generated method stub
		return dsmd.deleteDeptSchemeData( id, schemetype);
	}

	@Override
	public List<MDepartmentScheme> deptschememasterfind(Integer id) {
		// TODO Auto-generated method stub
		return dsmd.deptschememasterfind( id);
	}

	@Override
	public String updateDeptSchemeData(int id, String desc, BigDecimal seqno, String status, HttpSession session) {
		// TODO Auto-generated method stub
		return dsmd.updateDeptSchemeData(id, desc, seqno, status, session);
	}

}
