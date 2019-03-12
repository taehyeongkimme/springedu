package com.kh.myapp.bbs.dto;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class BbsCMD {
	@Size(min=4, max=30, message="제목은 4~30자내로 입력가능합니다.")
	private String btitle;		// 제목
	@Size(min=4, max=100, message="내용은 4~100자내로 입력가능합니다.")
	private String bcontent;	// 내용
}
