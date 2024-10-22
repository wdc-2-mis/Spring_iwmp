package app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.bean.GalleryBean;
import app.service.GalleryService;

@Controller("galleryController")
public class GalleryController {

	@Autowired(required = true)
	GalleryService galleryService;
	
	// getFiles will return gallery files to display
	public HashMap<String,List<GalleryBean>> getFiles(int stCode) {
		HashMap<String,List<GalleryBean>> map = new HashMap<String,List<GalleryBean>>();
		List<GalleryBean> list=new  ArrayList<GalleryBean>();
		list=galleryService.getFiles(stCode);
		List<GalleryBean> sublist=new  ArrayList<GalleryBean>();
		System.out.println("Size: "+list.size());
		int j=0;
		for(int i=0; i<list.size();i++) {
			if(map!=null) {
				if(!map.containsKey(list.get(i).getStName())) {
					sublist=new  ArrayList<GalleryBean>();
					sublist.add(list.get(i));
					map.put(list.get(i).getStName(), sublist);
				}else {
					sublist=map.get(list.get(i).getStName());
					sublist.add(list.get(i));
					map.put(list.get(i).getStName(), sublist);
				}
			}
			
		}
		
		return map;
	}
	
	// getIndexImages will return files for index page
	public HashMap<String,List<GalleryBean>> getIndexImages() {
		HashMap<String,List<GalleryBean>> map = new HashMap<String,List<GalleryBean>>();
		List<GalleryBean> list=new  ArrayList<GalleryBean>();
		int size=8;
		list=galleryService.getIndexFiles(size);
		List<GalleryBean> sublist=new  ArrayList<GalleryBean>();
		System.out.println("Size: "+list.size());
		int j=0;
		for(int i=0; i<list.size();i++) {
			if(map!=null) {
				if(!map.containsKey(list.get(i).getStName())) {
					map.put(list.get(i).getStName(), list.subList(i, ++j));
				}else {
					sublist.addAll(map.get(list.get(i).getStName()));
					sublist.addAll(list.subList(i, ++j));
					map.put(list.get(i).getStName(), sublist);
				}
			}
			
		}
		
		return map;
	}
	
	// getAllImages will return all files
	public HashMap<String,List<GalleryBean>> getAllImages() {
		HashMap<String,List<GalleryBean>> map = new HashMap<String,List<GalleryBean>>();
		List<GalleryBean> list=new  ArrayList<GalleryBean>();
		list=galleryService.getAllFiles();
		List<GalleryBean> sublist=new  ArrayList<GalleryBean>();
		System.out.println("Size: "+list.size());
		int j=0;
		for(int i=0; i<list.size();i++) {
			if(map!=null) {
				if(!map.containsKey(list.get(i).getStName())) {
					map.put(list.get(i).getStName(), list.subList(i, ++j));
				}else {
					sublist.addAll(map.get(list.get(i).getStName()));
					sublist.addAll(list.subList(i, ++j));
					map.put(list.get(i).getStName(), sublist);
				}
			}
			
		}
		
		return map;
	}
}
