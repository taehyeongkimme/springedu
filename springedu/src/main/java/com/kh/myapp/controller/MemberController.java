package com.kh.myapp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.myapp.login.LoginCmd;
import com.kh.myapp.login.LoginSvc;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.member.service.MemberSvc;
import com.kh.myapp.util.Code;

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger logger 
		= LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	private MemberSvc memberSvc;
	@Inject
	private LoginSvc loginSvc;
	
	
	//회원등록양식
	@RequestMapping("/memberJoinForm")
	public String memberJoinForm(Model model) {
		logger.info("memberJoinForm() 호출됨!");		
		model.addAttribute("mdto", new MemberDTO());
	
		return "/member/memberJoinForm";
	}
	
	@ModelAttribute
	public void initData(Model model) {
		//지역
		List<Code> rCodes = new ArrayList<>();
		rCodes.add(new Code("서울","서울"));
		rCodes.add(new Code("경기","경기"));
		rCodes.add(new Code("인천","인천"));
		rCodes.add(new Code("대전","대전"));
		rCodes.add(new Code("충북","충북"));
		rCodes.add(new Code("충남","충북"));
		rCodes.add(new Code("전남","전남"));
		rCodes.add(new Code("전북","전북"));
		rCodes.add(new Code("경남","경남"));
		rCodes.add(new Code("경북","경북"));
		rCodes.add(new Code("경북","경북"));
		rCodes.add(new Code("강원","강원"));
		rCodes.add(new Code("대구","대구"));
		rCodes.add(new Code("울산","울산"));
		rCodes.add(new Code("부산","부산"));
		rCodes.add(new Code("제주","제주"));
		
		//성별
		List<Code> gender = new ArrayList<>();
		gender.add(new Code("남","남자"));
		gender.add(new Code("여","여자"));
		
		model.addAttribute("rCodes",rCodes);
		model.addAttribute("gender",gender);
	}

	//회원등록처리
	@RequestMapping(value="/memberJoin", method=RequestMethod.POST)
	public String memberJoin(
			@Valid @ModelAttribute("mdto") MemberDTO mdto, 
			BindingResult result, Model model) {
		logger.info("/member/memberJoin 호출됨!");
		logger.info(mdto.toString());
		boolean success = false;
		
		if(result.hasErrors()) {
			
			logger.info(result.toString());
			logger.info("회원가입시 오류발생!!");
			return "/member/memberJoinForm";
		}
		
		success = memberSvc.insert(mdto);
		model.addAttribute("result", success);
		return "redirect:/login/loginForm";
	}

	//회원목록조회
	@RequestMapping("/memberList")
	public String memberList(Model model) {
		
		List<MemberDTO> list = memberSvc.getMemberList();
		model.addAttribute("memberList", list);
		
		return "/member/memberList";
	}
	
	//회원수정페이지
	@RequestMapping(value="/memberModifyForm/{id:.+}")
	public String memberModifyForm(@PathVariable String id, Model model) {
		logger.info("/memberModifyForm");
		
		MemberDTO mdto = memberSvc.getMember(id);
		model.addAttribute("mdto", mdto);
		return "/member/memberModifyForm";
	}
	
	//회원수정처리
	@RequestMapping(value="/memberModify", method=RequestMethod.POST)
	public String memberModify(
			@Valid @ModelAttribute("mdto") MemberDTO mdto, BindingResult result,
			HttpSession session) {
		logger.info("/memberModify"+mdto);
		boolean success = false;
		 
		if(result.hasErrors()) {
			return "/member/memberModifyForm";
		}
		success = memberSvc.modify(mdto);
		if(success) {
			session.removeAttribute("user");
			mdto = loginSvc.login(mdto.getId(), mdto.getPw());
			session.setAttribute("user", mdto);
		}
		
		logger.info("수정처리 결과:" + success);
//		return "/member/memberList";
		return "redirect:/";
	}
	
	//회원삭제처리
	@RequestMapping("/memberDelete/{id:.+}")
	public String memberDelete(@PathVariable String id, HttpSession session, Model model) {
		logger.info("/memberDelete/{id:.+}");
		boolean success = false;
		
		logger.info(id);
		success = memberSvc.adminDelete(id);
		logger.info("삭제처리 결과:" + success);
		if(success) {
			session.removeAttribute("user");
		}
		model.addAttribute("login", new LoginCmd());
		return "/member/memberDelete";
	}
	
	//이미지 업로드
	//@RequestMapping(value="/upload",method=RequestMethod.POST)
	@PostMapping("/upload")
	@ResponseBody //view를 전달하지않고 데이터만 보겠다는 restfull서비스를 위함.
	public ResponseEntity<String> doUpload(@RequestParam("file") MultipartFile file) {
		
		ResponseEntity<String> resCode = null;
		String randomFileName = null;	// 난수 파일명
		String originFileName = null;	// 초기 파일명
		String fileLocation = "C:\\KTH\\git\\repository\\springedu\\src\\main\\webapp\\resources\\upload";
		
		if(!file.isEmpty()) {
			
			randomFileName = UUID.randomUUID().toString();
			originFileName = file.getOriginalFilename();
			
			// 초기파일명 확장자 추출
			int pos = originFileName.lastIndexOf(".");
			String ext = originFileName.substring(pos+1);
			randomFileName = randomFileName + "." + ext;
			
			File tmpFile = new File(fileLocation, randomFileName);
			try {
				// 파일 시스템에 파일쓰기
				file.transferTo(tmpFile);
				resCode = new ResponseEntity<String>("success",HttpStatus.OK);
			} catch (IOException e) {
				e.printStackTrace();
				resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
				return resCode;
			}
		}
		
		return resCode;
	}
	
	
	
}





