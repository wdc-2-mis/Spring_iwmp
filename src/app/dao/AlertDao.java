package app.dao;

import java.util.List;

import app.model.master.IwmpUserUploadDetails;


public interface AlertDao {
	
	List<IwmpUserUploadDetails> getAlert();

	List<IwmpUserUploadDetails> getnewAlert(String lang);

}
