package com.kh.myapp.authority.svc;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kh.myapp.authority.dao.AuthorityDAO;
import com.kh.myapp.authority.dto.AuthorityDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthoritySvcImpl implements AuthoritySvc{

	@Inject
	AuthorityDAO authorityDAO;
	
	// 사용자 권한 등록
	@Override
	public boolean insert(AuthorityDTO authorityDTO) {
		boolean success = false;
		success = authorityDAO.insert(authorityDTO);
		return success;
	}

	// 사용자 권한 수정
	@Override
	public boolean update(AuthorityDTO authorityDTO) {
		boolean success = false;
		success = authorityDAO.update(authorityDTO);
		return success;
	}
	
	// 사용자 권한 삭제
	@Override
	public boolean delete(AuthorityDTO authorityDTO) {
		boolean success = false;
		success = authorityDAO.delete(authorityDTO);
		return success;
	}
	
	// 사용자 권한 조회
	@Override
	public List<AuthorityDTO> selectOne(String id) {
		return authorityDAO.selectOne(id);
	}
	
	// 사용자 전체 권한 조회
	@Override
	public List<AuthorityDTO> list() {
		return authorityDAO.list();
	}

}
