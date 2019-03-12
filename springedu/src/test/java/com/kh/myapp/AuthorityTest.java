package com.kh.myapp;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.myapp.authority.svc.AuthoritySvc;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.member.service.MemberSvc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class AuthorityTest {

	@Inject
	MemberSvc msvc;
	@Inject
	AuthoritySvc authoritySvc; 
	
	@Test	//@Disabled
	void insert() {
		boolean success = false;
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setId("test2@test.com");
		memberDTO.setPw("test");
		memberDTO.setTel("010-0000-0000");
		memberDTO.setBirth("2000-04-01");
		memberDTO.setGender("여");
		memberDTO.setNickName("테스터");
		memberDTO.setRegion("울산");
		success = msvc.insert(memberDTO);
		log.info("성공:"+success);
	
	}
	
	@Test @Disabled
	void insert2() {
		
	}

}
