package com.kh.myapp.member.dto;


import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.kh.myapp.authority.dto.AuthorityDTO;

import lombok.Data;

@Entity
@Data
public class MemberDTO implements UserDetails{
  
	private static final long serialVersionUID = -4691108051963240017L;
	
	@Pattern(regexp="^\\w+@\\w+\\.\\w+(\\.\\w+)?$", message="ex)aaa@bbb.com")
	private String id; 				//회원아이디
	@Size(min=4,max=30, message="비밀번호는 4~30자리로 입력바랍니다.")
	private String pw; 				//비밀번호
	@Pattern(regexp="^(02|010)-\\d{3,4}-\\d{4}$",message="ex)010-1234-5678")
	private String tel; 			//전화번호
	@Size(min=4,max=10, message="닉네임은 3~10자리로 입력바랍니다.")
	private String nickName; 	//닉네임
	private String gender;  	//성별
	private String region; 		//지역
	@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="생년월일 ex)xxxx-xx-xx")
	private String birth; 		//생년월일
	private Timestamp cdate;				//가입일
	private Timestamp udate;				//변경일
	
	private List<AuthorityDTO> authorities; 		//권한
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(List<AuthorityDTO> authorities) {
		this.authorities = authorities;
	}
	
	@Override
	public String getPassword() {
		return pw;
	}
	@Override
	public String getUsername() {
		return id;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static MemberDTO current() {
		try {
			return (MemberDTO) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			
		}	catch(Exception e) {
			return null;
		}
	
	}
}
