package com.kh.myapp.bbs.dto;

import java.sql.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class BbsDTO {
	private int bnum;					//게시글번호
	@Size(min=4, max=30, message="제목은 4~30자내로 입력가능합니다.")
	private String btitle;		//제목
	@Pattern(regexp="^\\w+@\\w+\\.\\w+(\\.\\w+)?$", message="ex)aaa@bbb.com")
	private String bid;				//작성자ID
	@Size(min=4,max=10, message="닉네임은 3~10자리로 입력바랍니다.")
	private String bnickname;	//작성자이름(별칭)
	private Date	bcdate;			//작성일
	private Date	budate;			//수정일
	private int	bhit;					//조회수
	@Size(min=4, max=100, message="제목은 4~100자내로 입력가능합니다.")
	private String bcontent;	//본문내용
	private int bgroup;				//답글그룹
	private int bstep;				//답변글의 단계
	private int bindent;			//답변글의 들여쓰기
	private String isdel;			//답글 존재유무
}
