package app.dao.reports;

import java.util.List;

import javax.servlet.http.HttpSession;

import app.bean.reports.UserFileUploadBean;
import app.bean.reports.UserUploadDetailsBean;
import app.model.master.IwmpUploadCategory;

public interface UserUploadDetailDao {

	List<IwmpUploadCategory> getUploadCategoryforSLNA();
	List<UserUploadDetailsBean> getListofuploadedFileforSLNA(Integer stcode);
	Integer getMaxId();
	boolean userUploadSaveSLNA(UserFileUploadBean userfileup, HttpSession session, String filePath, String fname);
	boolean userUploadDeleteSLNA(Integer id, String filename, String filePath);
	boolean userUploadUpdateSLNA(Integer id, String newsub, String caption_id, String publish_id, int newcat_id, String newcattxt, String login);
	
	List<IwmpUploadCategory> getUploadCategoryforDL();
	List<UserUploadDetailsBean> getListofuploadedFileforDL();
	boolean userUploadSaveDL(UserFileUploadBean userfileup, HttpSession session, String filePath, String fname, String lang);
	List<UserUploadDetailsBean> getListofUploadedDetailsSLNAReport(int stCode);
	List<UserUploadDetailsBean> getListofuploadedFileforState(Integer stcode);
	boolean doLRApprovalUploadingDocument(String upid[]);
}
