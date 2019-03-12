package com.kh.myapp.util;

/*
 *	input		: 요청페이지번호
 *	output	: 시작레코드, 종료레코드 
 */
public class RecordCriteria {
	private int reqPage;		// 요청페이지
	private int numPerPage;	// 한페이지에 보여줄 레코드수
	
	
	public RecordCriteria() { }

	public RecordCriteria(int reqPage) {
		this.reqPage = reqPage;
	}
	
	public RecordCriteria(int reqPage, int numPerPage) {
		this.reqPage = reqPage;
		this.numPerPage = numPerPage;
	}
	
	public int getReqPage() {
		return reqPage;
	}
	
	public void setReqPage(int reqPage) {
		this.reqPage = reqPage;
	}
	
	public int getNumPerPage() {
		return numPerPage;
	}
	
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	
	// 시작 레코드 계산(요청페이지-1) * (한페이지에 보여줄 레코드수)+1
	public int getStartRecord() {
		return ((this.reqPage-1) * this.numPerPage)+1;
	}
	// 종료 레코드 계산(요청페이지 * 한페이지에 보여줄 레코드수)
	public int getEndRecord() {
		return (this.reqPage * this.numPerPage);
	}
	
	@Override
	public String toString() {
		return "RecordCriteria [reqPage=" + reqPage + ", numPerPage=" + numPerPage + "]";
	}
	
	
}
