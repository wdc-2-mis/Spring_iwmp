package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.GalleryBean;

@Service("galleryService")
public interface GalleryService {
	
	List<GalleryBean> getFiles(int stCode);
	List<GalleryBean> getIndexFiles(int size);
	List<GalleryBean> getAllFiles();
}
