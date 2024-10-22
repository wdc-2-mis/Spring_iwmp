package app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import app.model.master.IwmpUserUploadDetails;
import app.service.AlertService;


@Controller("alertController")
public class AlertController {
	
	@Autowired(required = true)
	AlertService alertService;
	
	// getAlert() will return all alert
	@SuppressWarnings("null")
	public HashMap<String,List<IwmpUserUploadDetails>> getAlert(Locale locale) throws ParseException {
		
		   String lang = locale.getDisplayLanguage();
		 
		List<IwmpUserUploadDetails> alert=new  ArrayList<IwmpUserUploadDetails>();
		List<IwmpUserUploadDetails> circular=new  ArrayList<IwmpUserUploadDetails>();
		List<IwmpUserUploadDetails> message=new  ArrayList<IwmpUserUploadDetails>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		List<IwmpUserUploadDetails> list=new  ArrayList<IwmpUserUploadDetails>();
		if(lang == "Hindi") {
			lang = "hi";
			list=alertService.getnewAlert(lang);
		}
		else {
			lang = "en";
		list=alertService.getnewAlert(lang);
		}
		for(IwmpUserUploadDetails l : list) {
			if(l.getIwmpUploadCategory().getUploadCategory().equalsIgnoreCase("Alert")) {
	            
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
				alert.add(l);
				
				
			}
			if(l.getIwmpUploadCategory().getUploadCategory().equalsIgnoreCase("Circular")) {
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
				circular.add(l);
			}
			
			if(l.getIwmpUploadCategory().getUploadCategory().equalsIgnoreCase("Message")) {
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
				message.add(l);
			}
		}
		
		HashMap<String,List<IwmpUserUploadDetails>> map = new HashMap<String,List<IwmpUserUploadDetails>>();
		map.put("alert", alert);
		map.put("circular",circular);
		map.put("message",message);
		return map;
	}
	
	
	// getWhatsNew() will return whats new data
	public List<IwmpUserUploadDetails> getWhatsNew() {
		
		List<IwmpUserUploadDetails> listWhatsNew=new  ArrayList<IwmpUserUploadDetails>();
		listWhatsNew=alertService.getAlert();
		return listWhatsNew;
	}

}
