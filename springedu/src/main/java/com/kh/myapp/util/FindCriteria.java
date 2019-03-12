package com.kh.myapp.util;

public class FindCriteria extends RecordCriteria{
	private String searchType;		// 검색어타입
	private String keyword;				// 검색어
	
	public FindCriteria(int reqPage, int numPerPage, String searchType, String keyword) {
		super(reqPage, numPerPage);
		this.searchType = searchType;
		this.keyword = keyword;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
}
