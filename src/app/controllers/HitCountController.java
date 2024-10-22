package app.controllers;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.service.HitCountService;

@Controller("HitCountController")
public class HitCountController {
	
	@Autowired
	HitCountService service;
	
	HttpSession httpSession;
	
	@RequestMapping(value="/hitCount", method = RequestMethod.GET)
	public BigInteger getHitCount(HttpServletRequest request) {
		BigInteger count=BigInteger.ZERO;
		httpSession=request.getSession(true);
		String sessionId=httpSession.getId();
		count=service.getHitCount(sessionId);
		return count;
	}

}
