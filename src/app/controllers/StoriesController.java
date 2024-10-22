package app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.model.master.IwmpUserUploadDetails;
import app.service.StoriesService;

@Controller("storiesController")
public class StoriesController {
	
	@Autowired(required = true)
	StoriesService storiesService;
	
	// getStories is used to get all stories from database
	public List<IwmpUserUploadDetails> getStories() throws ParseException {
		
		List<IwmpUserUploadDetails> list=new  ArrayList<IwmpUserUploadDetails>();
		List<IwmpUserUploadDetails> story=new  ArrayList<IwmpUserUploadDetails>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		list=storiesService.getStories();
		for(IwmpUserUploadDetails l : list) {
			if(l.getUpdatedDate() !=null) {
				Date d1 = sdf.parse(sdf.format(new Date()).toString());
	            Date d2 = sdf.parse(sdf.format(l.getUpdatedDate()).toString());
	         // Calucalte time difference in milliseconds
	            long difference_In_Time = d1.getTime() - d2.getTime();
	            long difference_In_Days = (difference_In_Time/ (1000 * 60 * 60 * 24))% 365;
	            
				if(difference_In_Days<=15) 
				l.setIsNew(true);
				else
				l.setIsNew(false);
			
			}
			story.add(l);
		}
		return story;
	}
	
	// getAllStories is use to get stories page wise(pagination)
public List<IwmpUserUploadDetails> getAllStories(int pageid,int total) {
		
		List<IwmpUserUploadDetails> list=new  ArrayList<IwmpUserUploadDetails>();
		list=storiesService.getAllStories(pageid,total);
		return list;
	}

}
