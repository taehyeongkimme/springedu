package com.kh.myapp.board;

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

import com.kh.myapp.bbs.dto.BbsDTO;
import com.kh.myapp.bbs.service.BbsSvc;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class BoardTest {

	private static Logger logger = LoggerFactory.getLogger(BoardTest.class);
	@Inject
	BbsSvc bbsSvc;
	BbsDTO bbsdto;
	int cnt;
	List<BbsDTO>list;
	
	@BeforeEach
	void beforeEach() {
		
	 bbsdto =new BbsDTO();
	}
	//글쓰기
	@Test  //@Disabled
	void write() {
		
			for(int i=1; i<101; i++) {
			bbsdto.setBtitle("게시글 작성 테스트_"+i);
			bbsdto.setBid("test_"+i+"@test.com");
			bbsdto.setBnickname("테스터_"+i);
			bbsdto.setBcontent("게시글 작성 테스트 입니다 ^^");
			
				try {
					bbsSvc.write(bbsdto);
					logger.info("처리 건수:"+cnt);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
	
	//글목록
	@Test  @Disabled
	void list() {
		try {
			list = bbsSvc.list();
			logger.info("목록건수:"+list.size());
			for(BbsDTO bbsdto: list) {
			logger.info("목록건수:"+bbsdto.toString());
			}
		} catch (Exception e) {
	  e.printStackTrace();
		}
	}
	
	//글목록2
		@Test  @Disabled
		void list2() {
			try {
				list = bbsSvc.list(1,10);
				logger.info("목록건수:"+list.size());
				for(BbsDTO bbsdto: list) {
				logger.info("목록건수:"+bbsdto.toString());
				}
			} catch (Exception e) {
		  e.printStackTrace();
			}
		}
		
		//글읽기
		@Test @Disabled
		void view() {
			String bnum = "10044";
			try {
				bbsdto = bbsSvc.view(bnum);
				logger.info(bnum+"번글내용:"+bbsdto.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//글수정
		@Test @Disabled
		void modify() {
			int bnum = 10044;
			bbsdto.setBtitle("[수정제발5]서비스레이어 테스트!");
			bbsdto.setBcontent("서비스 레이어 테스트 내용 수정");
			bbsdto.setBnum(bnum);
			
			try {
				cnt = bbsSvc.modify(bbsdto);
				logger.info("수정건수:"+ cnt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//글삭제
		@Test @Disabled
		void delete() {
			String bnum ="10043";
			try {
				cnt = bbsSvc.delete(bnum);
				logger.info("삭제건수:"+cnt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//원글가져오기
		@Test @Disabled
		void replyView() {
			String bnum="10000";
			try {
				bbsdto = bbsSvc.replyView(bnum);
				logger.info("원글:"+bbsdto);
			} catch (Exception e) {
		
				e.printStackTrace();
			}
		}
		//답글쓰기
		@Test @Disabled
		void reply() {
			int bnum = 10046;
			bbsdto.setBtitle("[답글]서비스레이어 테스트!");
			bbsdto.setBid("test11@test.com");
			bbsdto.setBnickname("테스터11");
			bbsdto.setBcontent("서비스 레이어 테스트");
			bbsdto.setBgroup(bnum);
			
			
			try {
				cnt = bbsSvc.reply(bbsdto);
				logger.info("답글생성건수:"+cnt);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			
		}
		//게시글 총계
		@Test @Disabled
		void totalRec() {
			try {
				cnt = bbsSvc.totalRec();
				logger.info("게시글총계:"+cnt);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//검색목록
		@Test @Disabled
		void search() {
			int startRecord = 1;
			int endRecord = 20;
			String searchType = "TC";
			String keyword = "테스트";
			try {
				list = bbsSvc.list(startRecord, endRecord, searchType, keyword);
				for(BbsDTO bbsdto : list) {
				logger.info("검색목록:"+bbsdto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}

