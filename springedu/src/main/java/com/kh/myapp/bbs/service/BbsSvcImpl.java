package com.kh.myapp.bbs.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kh.myapp.bbs.dao.BbsDAO;
import com.kh.myapp.bbs.dto.BbsDTO;
import com.kh.myapp.util.FindCriteria;
import com.kh.myapp.util.PageCriteria;
import com.kh.myapp.util.RecordCriteria;

@Service
public class BbsSvcImpl implements BbsSvc {

	private static final Logger logger = LoggerFactory.getLogger(BbsSvcImpl.class);

	@Inject
	@Qualifier("bbsDAOImplXML")
	BbsDAO bbsdao;

	// 글쓰기
	@Override
	public int write(BbsDTO bbsDTO) throws Exception {
		int cnt = 0;
		cnt = bbsdao.write(bbsDTO);
		return cnt;
	}

	// 글목록
	@Override
	public List<BbsDTO> list() throws Exception {
		List<BbsDTO> list = null;
		list = bbsdao.list();
		return list;
	}

	// 글목록
	@Override
	public List<BbsDTO> list(int startRec, int endRec) throws Exception {
		List<BbsDTO> list = null;
		list = bbsdao.list(startRec, endRec);
		return list;
	}

	// 글읽기
	@Override
	public BbsDTO view(String bnum) throws Exception {
		BbsDTO bbsdto = null;
		bbsdto = bbsdao.view(bnum);
		return bbsdto;
	}

	// 글수정
	@Override
	public int modify(BbsDTO bbsDTO) throws Exception {
		int cnt = 0;
		cnt = bbsdao.modify(bbsDTO);
		return cnt;
	}

	// 글삭제
	@Override
	public int delete(String bnum) throws Exception {
		int cnt = 0;
		cnt = bbsdao.delete(bnum);
		return cnt;
	}

	// 원글가져오기
	@Override
	public BbsDTO replyView(String bnum) throws Exception {
		BbsDTO bbsdto = null;
		bbsdto = bbsdao.replyView(bnum);
		return bbsdto;
	}

	// 답글쓰기
	@Override
	public int reply(BbsDTO bbsDTO) throws Exception {
		int cnt = 0;
		cnt = bbsdao.reply(bbsDTO);
		return cnt;
	}

	// 게시글 총계
	@Override
	public int totalRec() throws Exception {
		int cnt = 0;
		cnt = bbsdao.totalRec();
		return cnt;
	}

	// 검색목록
	@Override
	public List<BbsDTO> list(int startRecord, int endRecord, String searchType, String keyword) throws Exception {
		List<BbsDTO> list = null;
		list = bbsdao.list(startRecord, endRecord, searchType, keyword);
		return list;
	}

	@Override
	 public void list(HttpServletRequest request, Model model) throws Exception {
		int NUM_PER_PAGE = 10;			// 한페이지에 보여줄 레코드수
		int PAGE_NUM_PER_PAGE = 10;	// 한페이지에 보여줄 페이지수
		int reqPage = 0;						// 요청페이지
		int totalRec = 0;						// 전체레코드수
			
		String searchType = null;		// 검색타입
		String keyword = null;			// 검색어
		
		PageCriteria pc = null;			
		RecordCriteria rc = null;		// 검색조건이 없는 경우의 레코드 시작,종료페이지
		FindCriteria fc = null;			// 검색조건이 있는 경우의 레코드 시작,종료페이지
			
		List<BbsDTO> alist = null;
		// 요청페이지가 없는경우 1페이지로 이동
		if(request.getParameter("reqPage")==null || request.getParameter("reqPage")=="") {
			reqPage = 1;
		}else {
			reqPage = Integer.parseInt(request.getParameter("reqPage"));
		}
		logger.info("reqPage:"+reqPage);
		// 검색 매개값 체크(searchType, keyword)
		searchType = request.getParameter("searchType");
		keyword = request.getParameter("keyword");
		
		logger.info("검색어타입"+searchType);
		logger.info("검색어"+keyword);
		
		if(keyword == null || keyword.trim().isEmpty()) {
			// 검색조건이 없는 경우
			
			rc = new RecordCriteria(reqPage,NUM_PER_PAGE);
			totalRec = bbsdao.totalRec();
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			alist = bbsdao.list(rc.getStartRecord(), rc.getEndRecord());
			
			request.setAttribute("list", alist);
			request.setAttribute("pc", pc);
			
		}else {
			// 검색조건이 있는 경우
			
			rc = new FindCriteria(reqPage,NUM_PER_PAGE,searchType,keyword);

			// 검색목록 총레코드수 반환
			totalRec = bbsdao.searchTotalRec(
					((FindCriteria)rc).getSearchType(),
					((FindCriteria)rc).getKeyword()
					);
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			
			alist = bbsdao.list(
					rc.getStartRecord(), 
					rc.getEndRecord(),
					((FindCriteria)rc).getSearchType(),
					((FindCriteria)rc).getKeyword()
					);
	
		}		
			model.addAttribute("list", alist);
			model.addAttribute("pc", pc);
 
	 }

	// 검색 총계
	@Override
	public int searchTotalRec(String searchType, String keyword) throws Exception {
		int totalRec = 0;
		totalRec = bbsdao.searchTotalRec(searchType, keyword);
		return totalRec;
	}

	@Override
	public void execute(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}