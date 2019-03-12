package com.kh.myapp.bbs.dao;

import java.util.List;

import com.kh.myapp.bbs.dto.RbbsDTO;


public interface RbbsDAO {

	//댓글 등록
	int write(RbbsDTO rbbsDTO) throws Exception;
	
	// 댓글 목록
	List<RbbsDTO> list(String bnum) throws Exception;
	List<RbbsDTO> list(String bnum, int startRec, int endRec) throws Exception;
	
	// 댓글 수정
	int modify(RbbsDTO rbbsDTO) throws Exception;
	
	// 댓글 삭제
	int delete(String rnum) throws Exception;
	
	// 댓글 호감 비호감
	int goodOrBad(String rnum, String goodOrBad) throws Exception;
	
	// 대댓글 등록
	int reply(RbbsDTO rbbsDTO) throws Exception;
	
	// 댓글 총계
	int replyTotalRec(String bnum) throws Exception;
	

}
