package com.kh.myapp.board;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.myapp.bbs.dto.RbbsDTO;
import com.kh.myapp.bbs.service.RbbsSvc;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class RbbsTest {

	private static Logger logger = LoggerFactory.getLogger(BoardTest.class);
	
	@Inject
	RbbsSvc rbbsSvc;
	
	RbbsDTO rbbsDTO;
	int cnt;
	
	@BeforeEach
	void beforeEach() {
		
		rbbsDTO =new RbbsDTO();
	}
	
	
	//@Test
	void write() {
		rbbsDTO = new RbbsDTO();
		
		rbbsDTO.setBnum(10241);
		rbbsDTO.setRid("2@2.2");
		rbbsDTO.setRnickname("22222");
		rbbsDTO.setRcontent("댓글테스트123");
		
		try {
			cnt = rbbsSvc.write(rbbsDTO);
			logger.info("등록건수:"+cnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test	 @Disabled
	void list() {
		List<RbbsDTO> list = null;
		
		try {
			list = rbbsSvc.list("10222");
			logger.info("댓글:"+list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test @Disabled
	void list2() {
	List<RbbsDTO> list = null;
	
	try {
		list = rbbsSvc.list("10103", 1, 10);
		logger.info("댓글:"+list);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	//@Test 
	void modify() {
		rbbsDTO.setRcontent("댓글수정테스트합니다");
		rbbsDTO.setRnum(10241);
		
		try {
			cnt = rbbsSvc.modify(rbbsDTO);
		} catch (Exception e) {
			logger.info("댓글수정건수:"+cnt);
			e.printStackTrace();
		}
	}
	
	//@Test	
	void delete() {
		try {
			cnt = rbbsSvc.delete("10241");
		} catch (Exception e) {
			logger.info("댓글삭제건수:"+cnt);
			e.printStackTrace();
		}
	}
	
	//@Test
	void goodOrBad() {
		try {
			cnt = rbbsSvc.goodOrBad("10241", "bad");
		} catch (Exception e) {
			logger.info("댓글 호감 비호감반영:"+cnt);
			e.printStackTrace();
		}
	}
	
	//대댓글 등록
	//@Test	
	void reply() {
		rbbsDTO = new RbbsDTO();
		rbbsDTO.setRnum(10243);
		rbbsDTO.setBnum(10241);
		rbbsDTO.setRid("2@2.2");
		rbbsDTO.setRnickname("22222");
		rbbsDTO.setRcontent("대댓글작성123");
		rbbsDTO.setRgroup(10243);
		rbbsDTO.setRstep(1);
		rbbsDTO.setRindent(1);
		try {
			cnt = rbbsSvc.reply(rbbsDTO);
		} catch (Exception e) {
			logger.info("대댓글 등록건수:"+cnt);
			e.printStackTrace();
		}
	}
	
	//댓글 총계
	//@Test
	void replyTotalRec() {
		try {
			cnt = rbbsSvc.replyTotalRec("10103");
			logger.info("댓글 총계:"+cnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test	
	void listTest() {
		List<RbbsDTO> list = new ArrayList<>();
		
		try {
			list = rbbsSvc.list("10221", 1, 10);
		} catch (Exception e) {
			logger.info("댓글 목록:"+list);
			e.printStackTrace();
		}
		
	}
	
	
}


























