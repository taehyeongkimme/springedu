package com.kh.myapp.bbs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kh.myapp.bbs.dto.BbsDTO;

@Repository(value="bbsDAOImplXML")
public class BbsDAOImplXML implements BbsDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(BbsDAOImpl.class);

	@Inject
	SqlSession sqlSession;
	
	// 글쓰기
	@Override
	public int write(BbsDTO bbsDTO) throws Exception {
		return sqlSession.insert("mappers.bbs.write", bbsDTO);
	}

	

	// 글읽기
	@Override
	public BbsDTO view(String bnum) throws Exception {
		BbsDTO bbsDTO = null;
		bbsDTO = sqlSession.selectOne("mappers.bbs.view", bnum);
		
		// 조회수 증가
		udpateHit(bbsDTO.getBnum());
		return bbsDTO;
	}

	// 조회수 증가
	private void udpateHit(int bnum) {
		sqlSession.update("mappers.bbs.updateHit", bnum);
		
	}

	// 글수정
	@Override
	public int modify(BbsDTO bbsDTO) throws Exception {
		return sqlSession.update("mappers.bbs.modify", bbsDTO);
	}

	// 글삭제
	@Override
	public int delete(String bnum) throws Exception {
		int cnt = 0;
		// 답글 존재 유무 판단
		if(isReply(bnum)) {
			// 답글 존재
			cnt = sqlSession.update("mappers.bbs.update_isdel", bnum);
		}else {
			// 답글 미존재
			cnt = sqlSession.delete("mappers.bbs.delete", bnum);
		}
		return cnt;
	}

	private boolean isReply(String bnum) {
		boolean isYN = false;
		
		int cnt = sqlSession.selectOne("mappers.bbs.isReply", bnum);
		
		if(cnt>0) {
			isYN = true;
		}
		return isYN;
	}

	// 원글가져오기
	@Override
	public BbsDTO replyView(String bnum) throws Exception {
		return sqlSession.selectOne("mappers.bbs.replyView", bnum);
	}

	// 답글쓰기
	@Override
	public int reply(BbsDTO bbsDTO) throws Exception {
		int cnt1 = 0 ,cnt2 = 0;
		
		// 이전 답글 step 업데이트(원글 그룹에 대한 세로정렬 재정의)
		cnt1 = updateStep(bbsDTO.getBgroup(),bbsDTO.getBstep());
		
		cnt2 = sqlSession.insert("mappers.bbs.reply", bbsDTO);
		return cnt2;
	}

	private int updateStep(int bgroup, int bstep) {
		Map<String,Object> map = new HashMap<>();
		map.put("bgroup", bgroup);
		map.put("bstep", bstep);
		return sqlSession.update("mappers.bbs.updateStep", map);
	}

	// 게시글 총계
	@Override
	public int totalRec() throws Exception {
		return sqlSession.selectOne("mappers.bbs.totalRec");
	}
	
	// 글목록 전체
	@Override
	public List<BbsDTO> list() throws Exception {
		return sqlSession.selectList("mappers.bbs.listOld");
	}

	// 글목록 요청 페이지
	@Override
	public List<BbsDTO> list(int startRec, int endRec) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		
		return sqlSession.selectList("mappers.bbs.list", map);
	}

	// 검색목록
	@Override
	public List<BbsDTO> list(int startRecord, int endRecord, String searchType, String keyword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sqlSession.selectList("mappers.bbs.flist", map);
	}

	// 검색 총계
	@Override
	public int searchTotalRec(String searchType, String keyword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne("mappers.bbs.searchTotalRec", map);
	}

}
