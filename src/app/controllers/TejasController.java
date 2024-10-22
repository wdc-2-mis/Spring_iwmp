package app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("tejasController")
public class TejasController {

	@GetMapping("tejasBharatMap")
	public ModelAndView tejasBharatMap(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("tejasbharatmap");
		return mav;
	}
}
