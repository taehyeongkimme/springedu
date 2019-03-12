package com.kh.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.myapp.login.LoginCmd;
import com.kh.myapp.login.LoginSvc;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.member.service.MemberSvc;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
			
	@Inject
	LoginSvc loginSvc;
	@Inject
	MemberSvc memberSvc;
	
	@RequestMapping("/loginForm")
	public void loginForm(@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		model.addAttribute("login", new LoginCmd());
		
		logger.info("error : "+error);
		logger.info("logout : "+logout);
		
		if(error != null) {
			model.addAttribute("error", "로그인 실패");
		}
		if(logout != null) {
			model.addAttribute("logout", "로그아웃 성공");
		}
	}
	
	//로그인
//	@RequestMapping(value="/loginOk")//,method=RequestMethod.POST)
//	public String login(@Valid @ModelAttribute("login") LoginCmd login, BindingResult result, Model model, HttpSession session) {
//
//		logger.info("String login 호출됨!");
//				
//		MemberDTO mdto = null;
//
//		if(result.hasErrors()) {
//			logger.info(result.toString());			
//			return "/login/loginForm";
//		}
//		
//		//1) 회원존재유무체크
//		if(loginSvc.isExist(login.getId())) {
//		
//		//1-1) 정상회원 체크
//			if (loginSvc.isMember(login.getId(), login.getPw())) {
//				//정상회원
//				mdto = loginSvc.login(login.getId(), login.getPw());
//				session.setAttribute("user", mdto);
//				logger.info("로그인 처리됨:" + login.getId());
//				return "redirect:/";
//			}else {
//				//비밀번호오류
//				model.addAttribute("errmsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
//				logger.info("아이디/비밀번호 오류");
//				return "forward:/login/loginForm";
//			}
//		}else {
//			//회원정보가 없습니다.
//			model.addAttribute("errmsg", "일치하는 회원정보가 없습니다.");
//			logger.info("회원정보가 없습니다.");
//			return "forward:/login/loginForm";
//		}
//		
//		/*//1)회원 유무체크
//		if (loginSvc.isMember(login.getId(), login.getPw())) {
//		//2)로그인 처리
//			mdto = loginSvc.login(login.getId(), login.getPw());
//			session.setAttribute("user", mdto);
//			logger.info("로그인 처리됨:" + login.getId());
//		}else {
//			return "forward:/login/loginForm";
//		}
//		return "redirect:/";*/
//	}
	
	//로그아웃
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		
//		session.invalidate();
//		
//		return "redirect:/login/loginForm";
//	}
	
	//아이디 찾기 페이지
	@RequestMapping("/findIdForm")
	public String findIdForm(Model model) {
		logger.info("String findIdForm 호출됨!");
		model.addAttribute("findID", new MemberDTO());
		return "/login/findId";
	}
	
	//아이디 찾기 처리
	@RequestMapping(value="/findId",method=RequestMethod.POST)
	public String findId(@Valid @ModelAttribute("findID") LoginCmd login, BindingResult result, Model model) {
		logger.info("String findId 호출됨!");
		MemberDTO mdto = null;
		
		if(result.hasErrors()) {
			logger.info("result"+result.toString());
			return "/login/findId";
		}
		
		mdto = loginSvc.findId(login.getTel(), login.getBirth());
		
		model.addAttribute("mdto", mdto);
		logger.info("mdto:"+mdto);
		return "/login/findId";
	}
	
	//비밀번호 찾기 페이지
	@RequestMapping("/findPwForm")
	public String findPwForm(Model model) {
		logger.info("String findPwForm 호출됨!");
		model.addAttribute("findPW", new MemberDTO());
		return "/login/findPw";
	}
	
	//비밀번호 찾기 처리
	@RequestMapping(value="/findPw",method=RequestMethod.POST)
	public String findPw(@Valid @ModelAttribute("findPW") LoginCmd login, BindingResult result, Model model) {
		logger.info("String findPw 호출됨!");
		MemberDTO mdto = null;
		
		if(result.hasErrors()) {
			logger.info(result.toString());
			return "/login/findPw";
		}
		
		mdto = loginSvc.findPw(login.getId(),login.getTel(), login.getBirth());
		
		model.addAttribute("mdto", mdto);
		logger.info("mdto:"+mdto);
		return "/login/findPw";
	}
	
	
}

