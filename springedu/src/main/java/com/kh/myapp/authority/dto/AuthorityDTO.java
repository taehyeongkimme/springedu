package com.kh.myapp.authority.dto;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityDTO implements GrantedAuthority{

	private static final long serialVersionUID = 9117047437536262470L;

	private int seq;
	private String id;			// 회원아이디
	private String roleId;	// 권한영문명
	
	
	public AuthorityDTO() {}
	
	public AuthorityDTO(String id, String roleId) {
		this.id = id;
		this.roleId = roleId;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	@Override
	public String getAuthority() {
		return roleId;
	}

	@Override
	public String toString() {
		return "AuthorityDTO [seq=" + seq + ", id=" + id + ", roleId=" + roleId + "]";
	}

	
	
}
