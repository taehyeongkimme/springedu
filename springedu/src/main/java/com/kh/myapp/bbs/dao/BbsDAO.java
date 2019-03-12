package com.kh.myapp.bbs.dao;

import java.util.List;

import com.kh.myapp.bbs.dto.BbsDTO;


public interface BbsDAO {

	// 글쓰기
	int write(BbsDTO bbsDTO) throws Exception;

	// 글목록
	List<BbsDTO> list() throws Exception;
	List<BbsDTO> list(int startRec, int endRec) throws Exception;
	
	// 글읽기
	BbsDTO view(String bnum) throws Exception;
	
	// 글수정
	int modify(BbsDTO bbsDTO) throws Exception;
	
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
	
	// 검색 총계
	int searchTotalRec(String searchType, String keyword) throws Exception;	
}
