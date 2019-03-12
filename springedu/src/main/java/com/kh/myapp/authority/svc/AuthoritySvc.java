package com.kh.myapp.authority.svc;

import java.util.List;

import com.kh.myapp.authority.dto.AuthorityDTO;

public interface AuthoritySvc {

	// 사용자 권한 등록
	boolean insert(AuthorityDTO authorityDTO);
	
	// 사용자 권한 수정
	boolean update(AuthorityDTO authorityDTO);
	
	// 사용자 권한 삭제
	boolean delete(AuthorityDTO authorityDTO);
	
	// 사용자 권한 조회
	List<AuthorityDTO> selectOne(String id);
	
	// 사용자 전체 권한 조회
	List<AuthorityDTO> list();
}
