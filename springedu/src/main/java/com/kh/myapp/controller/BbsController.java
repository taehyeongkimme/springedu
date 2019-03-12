package com.kh.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.myapp.bbs.dto.BbsDTO;
import com.kh.myapp.bbs.service.BbsSvc;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.util.RecordCriteria;

@Controller
@RequestMapping("/bbs")
public class BbsController {
	private static Logger logger = LoggerFactory.getLogger(BbsController.class);

	@Inject
	BbsSvc bbsSvc;
	
	//게시글 등록양식-write_form.jsp
	@RequestMapping("/write")
	public String write(Model model) {
		logger.info("/write호출");
		
		model.addAttribute("bbsDTO", new BbsDTO());
		return "/bbs/writeForm";
	}
	
	//게시글 등록처리
	@RequestMapping("/writeOk")
	public String writeOk(@Valid @ModelAttribute("bbsDTO") BbsDTO bbsDTO, 
			BindingResult result, Model model, HttpSession session) {
		logger.info("/writeOk호출:"+bbsDTO);
		
		int cnt = 0;
		if(result.hasErrors()) {
			logger.info(result.toString());
			return "/bbs/writeForm";
		}
		
		MemberDTO mdto = (MemberDTO)session.getAttribute("user");
		// 사용자 세션정보가 없는 경우
		if(mdto == null) {
			return "redirect:/login/loginForm";
		}
		
		try {
			bbsDTO.setBid(mdto.getId());
			bbsDTO.setBnickname(mdto.getNickName());
			cnt = bbsSvc.write(bbsDTO);
			logger.info("게시글 처리건수:"+cnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/bbs/list";
	}
	
	//게시글 답글양식-reply_form.jsp
	@RequestMapping("/replyForm/{bnum}/{reqPage}")
	public String reply(
			@PathVariable("bnum") String bnum,
			@PathVariable("reqPage") String reqPage,
			Model model, HttpSession session) {
		
		BbsDTO bbsDTO = null;
		RecordCriteria rc = null;
		
		// 사용자 세션정보가 없는 경우
		MemberDTO mdto = (MemberDTO)session.getAttribute("user");
		if(mdto == null) {
			return "redirect:/login/loginForm";
		}
		
		
		try {
			rc = new RecordCriteria(Integer.parseInt(reqPage));
			bbsDTO = bbsSvc.view(bnum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("bbsDTO", bbsDTO);
		model.addAttribute("rc", rc);
		return "/bbs/replyForm";
	}
	
	//게시글 답글처리
	@RequestMapping("/replyOk")
	public String replyOk(@Valid @ModelAttribute("bbsDTO") BbsDTO bbsDTO,
			BindingResult result, HttpSession session, @RequestParam String reqPage) {
		
		if(result.hasErrors()) {
			logger.info(result.toString());
			return "/bbs/replyForm";
		}
		
		// 사용자 세션정보가 없는 경우
		MemberDTO mdto = (MemberDTO)session.getAttribute("user");
		if(mdto == null) {
			return "redirect:/login/loginForm";
		}
		
		try {
			bbsDTO.setBid(mdto.getId());
			bbsDTO.setBnickname(mdto.getNickName());
			
			bbsSvc.reply(bbsDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/bbs/list?reqPage="+reqPage;
	}
	
	//게시글 보기-read.jsp
	@RequestMapping("/view")
	public String view(@ModelAttribute BbsDTO bbsDTO, Model model, HttpServletRequest request) {
		
		RecordCriteria rc = null;
		try {
			rc = new RecordCriteria(Integer.parseInt(request.getParameter("reqPage")));
			bbsDTO = bbsSvc.view(request.getParameter("bnum"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("bbsDTO", bbsDTO);
		model.addAttribute("rc", rc);
		return "/bbs/read";
	}
	
	//게시글 목록 보기-list.jsp
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		logger.info("/list호출");
		
		try {
			bbsSvc.list(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/bbs/list";
	}
	
	
	//게시글 수정양식
	
	//게시글 수정처리 : 글수정후 수정된글 읽기 상태
	@RequestMapping("/modifyOk")
	public String modify(@Valid @ModelAttribute BbsDTO bbsDTO,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "/bbs/read";
		}
		
		try {
			bbsSvc.modify(bbsDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward:/bbs/view";
	}
	
	//게시글 삭제처리
	@RequestMapping("/delete")
	public String delete(
			@RequestParam("bnum") String bnum,
			@RequestParam("reqPage") String reqPage) { 
		
		try {
			bbsSvc.delete(bnum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/bbs/list?reqPage="+reqPage;
	}
	
	@RequestMapping(value="/rereply",method=RequestMethod.GET)
	public String rereply() {
		return "/bbs/reReply";
	}
	
	
}
































