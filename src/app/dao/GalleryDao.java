package app.dao;

import java.util.List;

import app.bean.GalleryBean;

public interface GalleryDao {
	List<GalleryBean> getFiles(int stCode);
	List<GalleryBean> getIndexFiles(int size);
	List<GalleryBean> getAllFiles();
}
