package app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.service.DolrSupportService;


@Controller("dolrsupports")
public class DolrsupportController {
	HttpSession session;
	
	@Autowired(required = true)
	DolrSupportService dolrsupportservice;
	
	
	@RequestMapping(value = "/dolrSupport") 
	public ModelAndView dolrSupport(HttpServletRequest request, HttpServletResponse
	  response) {
		ModelAndView mav;
		session = request.getSession(true);
	    mav = new ModelAndView("dolrsupport");
	    mav.addObject("dolrsprtdata",dolrsupportservice.getsupportdata());
	    return mav;
		
	}
	
}
