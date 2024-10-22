package app.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.AlertDao;
import app.model.master.IwmpUserUploadDetails;
import app.service.AlertService;

@Service("alertService")
public class AlertServiceImpl implements AlertService{
	
	@Autowired
	private AlertDao dao ;

	@Override
	//@Transactional
	public List<IwmpUserUploadDetails> getAlert() {
		// TODO Auto-generated method stub
		return dao.getAlert();
	}

	@Override
	public List<IwmpUserUploadDetails> getnewAlert(String lang) {
		return dao.getnewAlert(lang);
	}

}
