package com.kh.myapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kh.myapp.member.dto.MemberDTO;

@RestController // 리소스(데이터) 자원을 반환하는데 사용(json,xml,문자열)
@RequestMapping("/rest")
public class RestfullController {
	
	private static final Logger logger = LoggerFactory.getLogger(RestfullController.class);
	

	@RequestMapping(value="/json/member", method=RequestMethod.GET, produces="application/json")
	public MemberDTO read() {
		MemberDTO mdto = new MemberDTO();
		mdto.setId("test1@test.com");
		mdto.setNickName("test1");
		mdto.setTel("010-1111-1111");
		mdto.setGender("남");
		mdto.setBirth("2019-01-01");
		mdto.setRegion("울산");
		
		return mdto;
	}
	
	@RequestMapping(value="/xml/member", method=RequestMethod.GET, produces="application/xml")
	public MemberDTO read2() {
		return read();
	}
	
	@RequestMapping(value="/json/members", method=RequestMethod.GET, produces="application/json")
	public List<MemberDTO> list() {
		List<MemberDTO> list = new ArrayList<>();
		
		MemberDTO mdto = null;
		mdto = new MemberDTO();
		mdto.setId("test1@test.com");
		mdto.setNickName("test1");
		mdto.setTel("010-1111-1111");
		mdto.setGender("남");
		mdto.setBirth("2019-01-01");
		mdto.setRegion("울산");
		list.add(mdto);
		
		mdto = new MemberDTO();
		mdto.setId("test2@test.com");
		mdto.setNickName("test2");
		mdto.setTel("010-2222-2222");
		mdto.setGender("남");
		mdto.setBirth("2019-01-01");
		mdto.setRegion("울산");
		list.add(mdto);

		mdto = new MemberDTO();
		mdto.setId("test3@test.com");
		mdto.setNickName("test3");
		mdto.setTel("010-3333-3333");
		mdto.setGender("여");
		mdto.setBirth("2019-01-01");
		mdto.setRegion("울산");
		list.add(mdto);
		
		return list;
	}
	
	@RequestMapping(value="/xml/members", method=RequestMethod.GET, produces="application/xml")
	public List<MemberDTO> list2() {
		return list();
	}
	
	@RequestMapping(value="/json/map/members", method=RequestMethod.GET, produces="application/json")
	public Map<String,MemberDTO> list3(){
		Map<String,MemberDTO> map = new HashMap<>();
		
		MemberDTO mdto = null;
		mdto = new MemberDTO();
		mdto.setId("test1@test.com");
		mdto.setNickName("test1");
		mdto.setTel("010-1111-1111");
		mdto.setGender("남");
		mdto.setBirth("2019-01-01");
		mdto.setRegion("울산");
		map.put("test1", mdto);
		
		mdto = new MemberDTO();
		mdto.setId("test2@test.com");
		mdto.setNickName("test2");
		mdto.setTel("010-2222-2222");
		mdto.setGender("남");
		mdto.setBirth("2019-01-01");
		mdto.setRegion("울산");
		map.put("test2", mdto);

		mdto = new MemberDTO();
		mdto.setId("test3@test.com");
		mdto.setNickName("test3");
		mdto.setTel("010-3333-3333");
		mdto.setGender("여");
		mdto.setBirth("2019-01-01");
		mdto.setRegion("울산");
		map.put("test3", mdto);
		
		return map;
	}
	
	@RequestMapping(value="/xml/map/members", method=RequestMethod.GET, produces="application/xml")
	public Map<String,MemberDTO> list4(){
		return list3();
	}
	
	@RequestMapping(value="/json/array/members", method=RequestMethod.GET, produces="application/json")
	public String[] array(){
		String[] str = new String[] {"홍길동","홍길서","홍길남","홍길북"};
		return str;
	}
	
	@RequestMapping(value="/xml/array/members", method=RequestMethod.GET, produces="application/xml")
	public String[] array2(){
		return array();
	}
	
	@RequestMapping(value="/member", method=RequestMethod.POST)
	public ResponseEntity<String> write(@RequestBody MemberDTO mdto){
		
		ResponseEntity<String> resCode = null;
		
		logger.info("등록"+mdto.toString());
		resCode = new ResponseEntity<String>("success",HttpStatus.OK);
		return resCode;
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.PUT)
	public ResponseEntity<String> modify(@RequestBody MemberDTO mdto,@PathVariable("id") String id){
		
		ResponseEntity<String> resCode = null;
		
		logger.info("수정id:"+id);
		logger.info("수정:"+mdto.toString());
		resCode = new ResponseEntity<String>("success",HttpStatus.OK);
		return resCode;
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") String id){
		
		ResponseEntity<String> resCode = null;
		
		logger.info("삭제id:"+id);
		resCode = new ResponseEntity<String>("success",HttpStatus.OK);
		return resCode;
	}
	
	
	
	
}































