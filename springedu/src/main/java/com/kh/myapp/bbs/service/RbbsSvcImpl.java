package com.kh.myapp.bbs.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kh.myapp.bbs.dao.RbbsDAO;
import com.kh.myapp.bbs.dto.RbbsDTO;

@Service
public class RbbsSvcImpl implements RbbsSvc {
	
	private static final Logger logger = LoggerFactory.getLogger(RbbsSvcImpl.class);
	
	@Inject
	@Qualifier("rbbsDAOImplXML")
	RbbsDAO rbbsdao;


	// 댓글등록
	@Override
	public int write(RbbsDTO rbbsDTO) throws Exception {
		int cnt = 0;
		cnt = rbbsdao.write(rbbsDTO);
		return cnt;
	}
	
	// 댓글목록
	@Override
	public List<RbbsDTO> list(String bnum) throws Exception {
		List<RbbsDTO> list = null;
		list = rbbsdao.list(bnum);
		return list;
	}

	// 댓글목록
	@Override
	public List<RbbsDTO> list(String bnum, int startRec, int endRec) throws Exception {
		List<RbbsDTO> list = null;
		list = rbbsdao.list(bnum,startRec,endRec);
		return list;
	}

	// 댓글 수정
	@Override
	public int modify(RbbsDTO rbbsDTO) throws Exception {
		int cnt = 0;
		cnt = rbbsdao.modify(rbbsDTO);
		return cnt;
	}

	// 댓글 삭제
	@Override
	public int delete(String rnum) throws Exception {
		int cnt = 0;
		cnt = rbbsdao.delete(rnum);
		return cnt;
	}

	// 댓글 호감 비호감
	@Override
	public int goodOrBad(String rnum, String goodOrBad) throws Exception {
		int cnt = 0;
		cnt = rbbsdao.goodOrBad(rnum, goodOrBad);
		return cnt;
	}

	// 대댓글 등록
	@Override
	public int reply(RbbsDTO rbbsDTO) throws Exception {
		int cnt = 0;
		cnt = rbbsdao.reply(rbbsDTO);
		return cnt;
	}

	// 댓글 총계
	@Override
	public int replyTotalRec(String bnum) throws Exception {
		int cnt = 0; 
		cnt = rbbsdao.replyTotalRec(bnum);
		return cnt;
	}

}
