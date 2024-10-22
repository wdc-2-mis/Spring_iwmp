package app.dao;

import java.util.List;

import app.model.master.IwmpUserUploadDetails;

public interface StoriesDao {
	List<IwmpUserUploadDetails> getStories();
	List<IwmpUserUploadDetails> getAllStories(int pageid,int total);

}
