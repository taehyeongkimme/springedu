package com.kh.myapp.login;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.myapp.member.dto.MemberDTO;

@Repository
public class LoginDAOImplJDBC implements LoginDAO {

	private static Logger logger = LoggerFactory.getLogger("LoginImpleJDBC.class");
	
	@Inject //같은 타입의 인스턴스를 참조
	private JdbcTemplate jdbcTemplate;
	
	
	//회원 존재 유무
	@Override
	public boolean isExist(String id) {
		boolean isExist = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) from member where id=? ");
		
		int cnt = jdbcTemplate.queryForObject(
				sql.toString(), new String[]{id}, Integer.class);
		if(cnt > 0) {
			isExist = true;
		}
		return isExist;
	}
	
	//정상회원 체크
	@Override
	public boolean isMember(String id,String pw) {
		boolean isMember = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) from member where id=? and pw=?");
		
		int cnt = jdbcTemplate.queryForObject(
				sql.toString(), new String[]{id,pw}, Integer.class);
		if(cnt > 0) {
			isMember = true;
		}
		return isMember;
	}

	//로그인
	@Override
	public MemberDTO login(String id, String pw) {
		MemberDTO mdto = new MemberDTO();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,pw,tel,nickname,gender,region,birth,cdate,udate ");
		sql.append("  from member where id=? and pw=?");		
		
		mdto = jdbcTemplate.queryForObject(sql.toString(), new String[] {id,pw}, 
				new BeanPropertyRowMapper<>(MemberDTO.class));
		return mdto;
	}

	@Override
	public MemberDTO findId(String tel, String birth){
		MemberDTO mdto = new MemberDTO();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from member where tel=? and birth=?");
		
		mdto = jdbcTemplate.queryForObject(sql.toString(), new Object[] {tel,birth },
				new BeanPropertyRowMapper<>(MemberDTO.class));
		
		return mdto;
	}

	@Override
	public MemberDTO findPw(String id, String tel, String birth){
		MemberDTO mdto = new MemberDTO();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from member where id=? and tel=? and birth=?");
		
		mdto = jdbcTemplate.queryForObject(sql.toString(), new Object[] {id,tel,birth }, 
				new BeanPropertyRowMapper<>(MemberDTO.class));
		
		return mdto;
	}



}
