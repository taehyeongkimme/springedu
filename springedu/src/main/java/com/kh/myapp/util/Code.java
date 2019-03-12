package com.kh.myapp.util;

import lombok.Data;

@Data
public class Code {
	private String code;
	private String label;
	
	public Code(String code, String label) {
		this.code = code;
		this.label = label;
	}
}
