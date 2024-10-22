package app.controllers;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import app.service.IndexCircleDataService;

@Controller("indexCircleDataController")
public class IndexCircleDataController {

	@Autowired(required = true)
	IndexCircleDataService indexCircleDataService;
	
	// getData will return index page circle data 
	public List<String> getData() {
		return indexCircleDataService.getCircleData();
	}
}
