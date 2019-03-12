package com.kh.myapp.bbs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.kh.myapp.bbs.dto.BbsDTO;

public interface BbsSvc {
	
	void execute(HttpServletRequest request);
	
	// 글쓰기
	int write(BbsDTO bbsDTO) throws Exception;

	// 글목록
	List<BbsDTO> list() throws Exception;
	List<BbsDTO> list(int starRec, int endRec) throws Exception;
	
	// 글읽기
	BbsDTO view(String bnum) throws Exception;
	
	// 글수정
	int modify(BbsDTO boardDTO) throws Exception;
	
	// 글삭제
	int delete(String bnum) throws Exception;

	// 원글가져오기
	BbsDTO replyView(String bnum) throws Exception;
	
	// 답글쓰기
	int reply(BbsDTO bbsDTO) throws Exception;
	
	// 게시글 총계
	int totalRec() throws Exception;

	// 검색목록
	List<BbsDTO> list(int startRecord, int endRecord, String searchType, String keyword) throws Exception;
	void list(HttpServletRequest request, Model model) throws Exception;
	
	// 검색 총계
	int searchTotalRec(String searchType, String keyword) throws Exception;
}
