package com.kh.myapp;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.myapp.member.dao.MemberDAOImplXML;
import com.kh.myapp.member.dto.MemberDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class MemberXMLTest {

	private static Logger logger = LoggerFactory.getLogger("MemberXMLTest.class");
	
	@Inject
	MemberDAOImplXML xml;
	
	// 회원 등록
	@Test @Disabled
	void test() {
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("test30@test.com");
		memberDTO.setPw("1234");
		memberDTO.setTel("010-1234-5678");
		memberDTO.setNickName("테스터30");
		memberDTO.setGender("남");
		memberDTO.setRegion("울산");
		memberDTO.setBirth("2019-01-09");
		
		boolean success = xml.insert(memberDTO);
	  logger.info("생성여부 :"+success);
	}

	// 회원 수정
	@Test @Disabled
	void modify() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("test30@test.com");
		memberDTO.setPw("1234");
		memberDTO.setTel("010-9999-9999");
		memberDTO.setNickName("수정테스터30");
		memberDTO.setGender("여");
		memberDTO.setRegion("부산");
		memberDTO.setBirth("2001-01-01");		
		boolean success = xml.modify(memberDTO);
		logger.info("회원수정여부 : " + success);
	}
	// 회원 삭제(회원용)
	@Test
	void delete() {
		boolean success = xml.delete("test5@test.com", "1234");
		logger.info("회원삭제여부 : " + success);
	}
	// 회원 삭제(관리자용)
	@Test @Disabled 
	void adminDelete() {
		boolean success = xml.adminDelete("test30@test.com");
		logger.info("회원삭제여부 : " + success);
	}	
	// 회원 조회
	@Test @Disabled
	void selectOne() {
		MemberDTO mdto = xml.getMember("test1@test.com");
		logger.info(mdto.toString());
	}
	// 회원목록조회
	@Test @Disabled
	void selectList() {
		List<MemberDTO> list = null;
		list = xml.getMemberList();
		list.stream().forEach(m->{
			logger.info(m.toString());
		});
	}
}














