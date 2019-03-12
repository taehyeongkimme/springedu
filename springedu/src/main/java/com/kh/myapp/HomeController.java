package com.kh.myapp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
		
		return "/index";
	}
	
	@RequestMapping(value="/test")
	public String test123(Model model) {
		
		model.addAttribute("name","서버에서 Model객체를 통해서 전달한 문자열입니다!");
		return "abc";
	}
	
	@RequestMapping(value="/test2")
	public ModelAndView test2() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("abc");
		mv.addObject("name","서버에서 ModelAndView객체를 통해서 전달한 문자열입니다!");
		return mv;
	}
	
}
