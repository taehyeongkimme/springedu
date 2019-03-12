package com.kh.myapp.bbs.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RbbsDTO {
	
	private int rnum; 					// 댓글번호
	private int bnum;						// 원글번호
	private String rid;					// 작성자ID
	private String rnickname; 	// 작성자이름(별칭)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Timestamp rcdate;		// 작성일
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Timestamp rudate;		// 수정일
	private String rcontent;		// 댓글내용
	private int rgood;					// 호감
	private int rbad;						// 비호감
	private int rgroup;					// 댓글그룹
	private int rstep;					// 댓글 단계
	private int rindent;				// 댓글 들여쓰기
	private Integer rrnum; 			// 대댓글번호
	private String isdel;				// 댓글 존재유무
//	private String rrnickname;		// 대댓글닉네임
	
	private RbbsDTO rrdto;			// 대댓글정보
}
