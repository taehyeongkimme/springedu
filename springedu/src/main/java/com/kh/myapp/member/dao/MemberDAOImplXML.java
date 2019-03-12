package com.kh.myapp.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kh.myapp.member.dto.MemberDTO;

@Repository("memberDAOImplXML")
public class MemberDAOImplXML implements MemberDAO {
	
	private static final Logger logger
		= LoggerFactory.getLogger(MemberDAOImplXML.class);
	
	@Inject
	private SqlSession sqlSession;
	
	//회원 등록
	@Override
	public boolean insert(MemberDTO memberDTO) {
		logger.info("MemberDAOImplXML.insert 호출됨!");
		boolean success = false;
		int cnt = sqlSession.insert("memberInsert", memberDTO);
		if (cnt>0) { success = true; }
		return success;
	}
	//회원 수정
	@Override
	public boolean modify(MemberDTO memberDTO) {
		logger.info("MemberDAOImplXML.modify 호출됨!");
		boolean success = false;
		int cnt = sqlSession.update("memberUpdate", memberDTO);
		if (cnt>0) { success = true; }
		return success;
	}
	//회원 삭제(회원용)
	@Override
	public boolean delete(String id, String pw) {
		logger.info("MemberDAOImplXML.delete 호출됨!");
		boolean success = false;
		Map<String,String> map = new HashMap<>();
		map.put("id", id);
		map.put("pw", pw);
		int cnt = sqlSession.delete("memberDelete", map);
		if (cnt>0) { success = true; }
		return success;
	}
	//회원 삭제(관리자용)
	@Override
	public boolean adminDelete(String id) {
		logger.info("MemberDAOImplXML.adminDelete 호출됨!");
	boolean success = false;
	int cnt = sqlSession.delete("adminMemberDelete", id);
	if (cnt>0) { success = true; }
	return success;
	}
	//회원 조회
	@Override
	public MemberDTO getMember(String id) {
		logger.info("MemberDAOImplXML.getMember 호출됨!");
		MemberDTO memberDTO = null;
		memberDTO = sqlSession.selectOne("memberSelectOne", id);
		return memberDTO;
	}
	//회원 목록 조회
	@Override
	public List<MemberDTO> getMemberList() {
		logger.info("MemberDAOImplXML.getMemberList 호출됨!");		
		List<MemberDTO> list = null;
		list = sqlSession.selectList("memberSelectList");
		return list;
	}
}
