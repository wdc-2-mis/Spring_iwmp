package app.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.GalleryBean;
import app.dao.GalleryDao;
import app.service.GalleryService;

@Service("galleryService")
public class GalleryServiceImpl implements GalleryService {
	
	@Autowired
	private GalleryDao dao ;

	@Override
	//@Transactional
	public List<GalleryBean> getFiles(int stCode) {
		// TODO Auto-generated method stub
		return dao.getFiles(stCode);
	}
	
	@Override
	//@Transactional
	public List<GalleryBean> getIndexFiles(int size) {
		// TODO Auto-generated method stub
		return dao.getIndexFiles(size);
	}
	
	@Override
	//@Transactional
	public List<GalleryBean> getAllFiles() {
		// TODO Auto-generated method stub
		return dao.getAllFiles();
	}

}
