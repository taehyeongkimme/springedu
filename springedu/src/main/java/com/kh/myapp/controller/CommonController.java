package com.kh.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kh.myapp.member.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CommonController {
	
	@GetMapping("/customLogin")
	public void customLogin() {
		log.info("login!");
	}
	
	@GetMapping("/accessDeny")
	public String accessDeny(Authentication auth, Model model) {
		log.info("accessDeny"+auth);
		model.addAttribute("msg", "접근 제한구역입돠~!");
		return "/common/forbidden";
	}
	
	@GetMapping("/ajax")
	public void ajax() {
		
	}
	
	@GetMapping("/sec1")
	public void aa(@AuthenticationPrincipal MemberDTO mdto) {
	//	MemberDTO mdto = (MemberDTO)user;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		UserDetails userDetails = (UserDetails)principal;
		log.info("g1"+mdto);
		log.info("g2"+MemberDTO.current());
		log.info("g3"+userDetails);
	}
	
	@GetMapping("/sec2")
	public void aa(HttpServletRequest req) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		MemberDTO mdto = (MemberDTO)auth.getPrincipal();
		log.info("gg"+mdto);
	}
	
	@PostMapping("/test")
	public ResponseEntity<String> test(@RequestBody MemberDTO mdto) {
		ResponseEntity<String> result = null;
		
        HttpHeaders responseHeaders = new HttpHeaders();   
        responseHeaders.add("Content-Type", "text/html; charset=utf-8");   
        
//		result = new ResponseEntity<String>("hi",HttpStatus.OK);
		result = new ResponseEntity<String>("오류발생했뿟다",responseHeaders,HttpStatus.BAD_REQUEST);
		return result;
		
	}
}
