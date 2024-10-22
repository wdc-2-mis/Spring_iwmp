package app.serviceImpl.reports;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.reports.UserFileUploadBean;
import app.bean.reports.UserUploadDetailsBean;
import app.dao.reports.UserUploadDetailDao;
import app.model.master.IwmpUploadCategory;
import app.service.reports.UserUploadDetailServices;


@Service("userUploadDetailsSL")
public class UserUploadDetailServicesImpl implements UserUploadDetailServices{

	@Autowired
	private UserUploadDetailDao udd;
	
	@Override
	public Map<String, String> getUploadCategoryforSLNA() {
		Map<String, String> categoryType=new LinkedHashMap<String, String>();
		for (IwmpUploadCategory temp : udd.getUploadCategoryforSLNA()) {
			categoryType.put(temp.getId()+"", temp.getUploadCategory());
		}
		return categoryType ;
	}

	@Override
	public List<UserUploadDetailsBean> getListofuploadedFileforSLNA(Integer stcode) {
		return udd.getListofuploadedFileforSLNA(stcode);
	}

	@Override
	public Integer getMaxId() 
	{
		return udd.getMaxId();
	}

	@Override
	public boolean userUploadSaveSLNA(UserFileUploadBean userfileup, HttpSession session, String filePath, String fname) 
	{
		return udd.userUploadSaveSLNA(userfileup, session, filePath, fname);
	}

	@Override
	public boolean userUploadDeleteSLNA(Integer id, String filename, String filePath) 
	{
		return udd.userUploadDeleteSLNA(id, filename, filePath);
	}

	@Override
	public boolean userUploadUpdateSLNA(Integer id, String newsub, String caption_id, String publish_id,
			int newcat_id, String newcattxt, String login) 
	{
		return udd.userUploadUpdateSLNA(id, newsub, caption_id, publish_id, newcat_id, newcattxt, login);
	}

	@Override
	public Map<String, String> getUploadCategoryforDL() {
		Map<String, String> categoryType=new LinkedHashMap<String, String>();
		for (IwmpUploadCategory temp : udd.getUploadCategoryforDL()) {
			categoryType.put(temp.getId()+"", temp.getUploadCategory());
		}
		return categoryType ;
	}

	@Override
	public List<UserUploadDetailsBean> getListofuploadedFileforDL() {
		
		return udd.getListofuploadedFileforDL();
	}

	@Override
	public boolean userUploadSaveDL(UserFileUploadBean userfileup, HttpSession session, String filePath, String fname, String lang) {
		
		return udd.userUploadSaveDL(userfileup, session, filePath, fname, lang);
	}

	@Override
	public List<UserUploadDetailsBean> getListofUploadedDetailsSLNAReport(int stCode) {
		// TODO Auto-generated method stub
		return udd.getListofUploadedDetailsSLNAReport(stCode);
	}

	@Override
	public List<UserUploadDetailsBean> getListofuploadedFileforState(Integer stcode) {
		// TODO Auto-generated method stub
		return udd.getListofuploadedFileforState(stcode);
	}

	@Override
	public boolean doLRApprovalUploadingDocument(String[] upid) {
		// TODO Auto-generated method stub
		return udd.doLRApprovalUploadingDocument(upid);
	}

}
