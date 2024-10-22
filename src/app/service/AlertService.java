package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.model.master.IwmpUserUploadDetails;


@Service("alertService")
public interface AlertService {

	List<IwmpUserUploadDetails> getAlert();

	List<IwmpUserUploadDetails> getnewAlert(String lang);
}
