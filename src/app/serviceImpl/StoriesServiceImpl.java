package app.serviceImpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.dao.StoriesDao;
import app.model.master.IwmpUserUploadDetails;
import app.service.StoriesService;

@Service("storiesService")
public class StoriesServiceImpl implements StoriesService{
	@Autowired
	private StoriesDao dao ;

	@Override
	//@Transactional
	public List<IwmpUserUploadDetails> getStories() {
		return dao.getStories();
	}
	
	@Override
	//@Transactional
	public List<IwmpUserUploadDetails> getAllStories(int pageid,int total) {
		return dao.getAllStories(pageid,total);
	}
}
