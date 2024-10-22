package app.service.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import app.bean.reports.UserFileUploadBean;
import app.bean.reports.UserUploadDetailsBean;
@Service("userUploadDetailsSL")
public interface UserUploadDetailServices {
	
	Map<String, String> getUploadCategoryforSLNA();
	List<UserUploadDetailsBean> getListofuploadedFileforSLNA(Integer stcode);
	Integer getMaxId();
	boolean userUploadSaveSLNA(UserFileUploadBean userfileup, HttpSession session, String filePath, String fname);
	boolean userUploadDeleteSLNA(Integer id, String filename, String filePath);
	boolean userUploadUpdateSLNA(Integer id, String newsub, String caption_id, String publish_id, int newcat_id, String newcattxt, String login);
	
	Map<String, String> getUploadCategoryforDL();
	List<UserUploadDetailsBean> getListofuploadedFileforDL();
	boolean userUploadSaveDL(UserFileUploadBean userfileup, HttpSession session, String filePath, String fname, String lang);
	List<UserUploadDetailsBean> getListofUploadedDetailsSLNAReport(int stCode);
	
	List<UserUploadDetailsBean> getListofuploadedFileforState(Integer stcode);
	boolean doLRApprovalUploadingDocument(String upid[]);
}
