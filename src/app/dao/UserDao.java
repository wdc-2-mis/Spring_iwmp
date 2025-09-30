package app.dao;

import java.util.List;
import java.util.Map;

import app.bean.StateToVillageBean;
import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.UserReg;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;

public interface UserDao {

	List<IwmpState> getStateList(); 
	List<IwmpDistrict> getDistrictList(int stateCode);
	List<IwmpMProject>  getProjectList(int stateCode, int distCode);
	List<IwmpMProject> getUserProjectList(String stateCode, String dist);
	List<UserReg> getUseridList(String stateCode, String dist);
	void saveUserReg(UserReg userReg);
	Integer regId();
	List<IwmpMFinYear> getCurrentFinYear();
	List<IwmpMMonth> getnotcompletedmonth();
	List<IwmpMProject> getMidTermProjList(int district);
	
	
}
