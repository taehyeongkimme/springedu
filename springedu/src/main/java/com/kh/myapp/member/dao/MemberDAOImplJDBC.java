package com.kh.myapp.member.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.myapp.member.dto.MemberDTO;

@Repository("memberDAOImplJDBC")
public class MemberDAOImplJDBC implements MemberDAO {
	private static final Logger logger 
	= LoggerFactory.getLogger(MemberDAOImplJDBC.class);

	@Inject //같은 타입의 인스턴스를 참조
	private JdbcTemplate jdbcTemplate;
	
	//회원 등록
	@Override
	public boolean insert(MemberDTO memberDTO) {
		logger.info(memberDTO.toString());
		logger.info("JdbcTemplate"+jdbcTemplate);
		int cnt = 0;
		boolean success = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into member (id,pw,tel,nickname,gender,region,birth) ");
		sql.append("values(?,?,?,?,?,?,?) ");
		
		cnt = jdbcTemplate.update(
				sql.toString(), 		memberDTO.getId(), 			memberDTO.getPw(),
				memberDTO.getTel(),	memberDTO.getNickName(),memberDTO.getGender(),
				memberDTO.getRegion(),memberDTO.getBirth()
		);
		
		if(cnt > 0) { success = true; }
		return success;
	}

	//회원 수정
	@Override
	public boolean modify(MemberDTO memberDTO) {
		int cnt = 0;
		boolean success = false;
		
		StringBuffer sql = new StringBuffer();		
		sql.append("update member set "
				+ "pw=?,"
				+ "tel=?,"
				+ "nickname=?,"
				+ "gender=?,"
				+ "region=?,"
				+ "birth=? ");
		sql.append("where id=? and pw=?");
		
		cnt = jdbcTemplate.update(
				sql.toString(),
				memberDTO.getPw(),		memberDTO.getTel(),		memberDTO.getNickName(),
				memberDTO.getGender(),memberDTO.getRegion(),memberDTO.getBirth(),
				memberDTO.getId(),		memberDTO.getPw()
		);		
		
		if(cnt > 0) { success = true; }
		return success;
	}

	//회원 삭제(회원용)
	@Override
	public boolean delete(String id, String pw) {
		int cnt = 0;
		boolean success = false;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("delete from member where id=? and pw=?");		
		cnt = jdbcTemplate.update(sql.toString(), id, pw);
		
		if(cnt > 0) { success = true; }
		return success;		
	}
	
	//회원 삭제(관리자용)
	@Override
	public boolean adminDelete(String id) {
		int cnt = 0;
		boolean success = false;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("delete from member where id=?");		
		cnt = jdbcTemplate.update(sql.toString(), id);
		
		if(cnt > 0) { success = true; }
		return success;		
	}
	//회원 조회
	@Override
	public MemberDTO getMember(String id) {
		MemberDTO mdto = new MemberDTO();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,pw,nickname,gender,region,birth,tel ");
		sql.append("from member where id=? ");
		
		mdto = jdbcTemplate.queryForObject(
				sql.toString(), 
				new Object[]{id}, 
				new BeanPropertyRowMapper<>(MemberDTO.class));
		
		return mdto;
	}

	//회원 목록 조회
	@Override
	public List<MemberDTO> getMemberList() {
		List<MemberDTO> list = new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,pw,nickname,gender,region,birth,tel,cdate,udate ");
		sql.append("  from member ");
		
		list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(MemberDTO.class));
		
		return list;
	}



}
