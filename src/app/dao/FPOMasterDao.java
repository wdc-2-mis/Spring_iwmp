package app.dao;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import app.bean.FPOReportBean;
import app.model.outcome.FpoDetail;

public interface FPOMasterDao {

	LinkedHashMap<Integer, String> getCoreActivity();

	String savefpodata(Integer projId, String group, Integer no, List<String> fponame, List<Integer> deptorg, List<String> regno,
			List<String> regdt, List<Integer> noofmembers, List<String> fpoactivity, List<BigDecimal> fpoavg, List<Integer> nooffarm, String loginID);

	List<FPOReportBean> getfpodatastatewise(Integer state);
    List<FPOReportBean> getfpodatadistwise(Integer statecode);
    List<FpoDetail> getfpodataprojwise(int dcode, String fpoType);
    List<FpoDetail> getfpodataallprojwise(int stcode, String fpoType);
    List<FPOReportBean> getfpodraftdata(Integer projectId, String group);
    String detFPOdraftdata(Integer fpoid);
    String finalSaveFPOdraftdata(Integer fpoid);
    List<FPOReportBean> fpofinaldata(Integer projectId, String group);
    LinkedHashMap<Integer, String> getdeptorg(int stCode);

}
