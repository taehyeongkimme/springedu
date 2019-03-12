package com.kh.myapp.bbs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kh.myapp.bbs.dto.RbbsDTO;

@Repository(value="rbbsDAOImplXML")
public class RbbsDAOImplXML implements RbbsDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(RbbsDAOImplXML.class);
	
	@Inject
	SqlSession sqlSession;

	// 댓글 등록
	@Override
	public int write(RbbsDTO rbbsDTO) throws Exception {
		return sqlSession.insert("mappers.rbbs.write", rbbsDTO);
	}

	// 댓글 목록
	@Override
	public List<RbbsDTO> list(String bnum) throws Exception {
		return sqlSession.selectList("mappers.rbbs.listOld", bnum);
	}

	// 댓글 목록
	@Override
	public List<RbbsDTO> list(String bnum, int startRec, int endRec) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("bnum", bnum);
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		return sqlSession.selectList("mappers.rbbs.list", map);
	}

	// 댓글 수정
	@Override
	public int modify(RbbsDTO rbbsDTO) throws Exception {
		return sqlSession.update("mappers.rbbs.modify", rbbsDTO);
	}

	// 댓글 삭제
	@Override
	public int delete(String rnum) throws Exception {
		int cnt = 0;
		// 답글 존재 유무 판단
		if(isReply(rnum)) {
			// 답글 존재
			cnt = sqlSession.update("mappers.rbbs.update_isdel", rnum);
		}else {
			// 답글 미존재
			cnt = sqlSession.delete("mappers.rbbs.delete", rnum);
		}
		return cnt;
	}

	//답글 미존재 판단
	private boolean isReply(String rnum) {
		boolean isYN = false;
		int cnt = sqlSession.selectOne("mappers.rbbs.isReply", rnum);
		if(cnt>0) {isYN = true;}
		return isYN;
	}

	// 호감 비호감
	@Override
	public int goodOrBad(String rnum, String goodOrBad) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("rnum", rnum);
		map.put("goodOrBad", goodOrBad);
		return sqlSession.update("mappers.rbbs.goodOrBad", map);
	}

	// 대댓글 등록
	@Override
	public int reply(RbbsDTO rbbsDTO) throws Exception {
		int cnt1, cnt2 = 0;
		
		// 댓글 대상 정보 읽어오기
		RbbsDTO originDTO = replyView(rbbsDTO.getRrnum());
		
		// 이전댓글 step 업데이트
		cnt1 = updateStep(originDTO.getRgroup(), originDTO.getRstep());
		
		Map<String,Object> map = new HashMap<>();
		map.put("originDTO", originDTO);
		map.put("rbbsDTO", rbbsDTO);
		cnt2 = sqlSession.insert("mappers.rbbs.reply", map);
		return cnt2;
	}

	// 이전댓글 step 업데이트
	private int updateStep(int rgroup, int rstep) {
		Map<String,Object> map = new HashMap<>();
		map.put("rgroup", rgroup);
		map.put("rstep", rstep);
		return sqlSession.update("mappers.rbbs.updateStep", map);
	}

	//댓글 대상 정보 읽어오기
	private RbbsDTO replyView(Integer rrnum) {
		RbbsDTO rdto = null;
		rdto = sqlSession.selectOne("mappers.rbbs.replyView", rrnum);
		return rdto;
	}

	// 댓글 총계
	@Override
	public int replyTotalRec(String bnum) throws Exception {
		return sqlSession.selectOne("mappers.rbbs.replyTotalRec", bnum);
	}

}
