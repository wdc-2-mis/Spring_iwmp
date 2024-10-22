package app.service;

import java.util.List;
import org.springframework.stereotype.Service;

import app.model.master.IwmpUserUploadDetails;

@Service("storiesService")
public interface StoriesService {
	List<IwmpUserUploadDetails> getStories();
	List<IwmpUserUploadDetails> getAllStories(int pageid,int total);
}
