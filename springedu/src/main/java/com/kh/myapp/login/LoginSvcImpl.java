package com.kh.myapp.login;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kh.myapp.member.dto.MemberDTO;

@Service
public class LoginSvcImpl implements LoginSvc {

	@Inject
	LoginDAO loginDAO;
	
	//회원 존재 유무 체크
	@Override
	public boolean isExist(String id) {
		return loginDAO.isExist(id);
	}
	
	//정상회원 체크
	@Override
	public boolean isMember(String id, String pw) {

		return loginDAO.isMember(id, pw);
	}

	//로그인
	@Override
	public MemberDTO login(String id, String pw) {
	
		return loginDAO.login(id, pw);
	}

	@Override
	public MemberDTO findId(String tel, String birth) {
		return loginDAO.findId(tel, birth);
	}

	@Override
	public MemberDTO findPw(String id, String tel, String birth) {
		return loginDAO.findPw(id, tel, birth);
	}



}
