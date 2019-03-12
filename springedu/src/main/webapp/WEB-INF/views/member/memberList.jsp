<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/webjars/bootstrap/4.2.1/css/bootstrap.min.css" />

<script src="/webjars/jquery/3.3.1/dist/jquery.min.js"></script>
<script src="/webjars/popper.js/1.14.6/dist/umd/popper.min.js"></script>
<script src="/webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script>
	$(function(){
		//수정버튼 클릭시
		$(".modify, .delete, .insert").on("click",function(){
			$(location).attr("href",$(this).attr("data-memurl"));
		});
		//삭제버튼 클릭시
/* 		$(".delete").on("click",function(){
			console.log("del");
			$(location).attr("href",$(this).attr("data-memurl"));
		});
 */	
	});
</script>
</head>
<body>
<h2>[회원목록]</h2>
<table border=1 cellpadding=5 cellspacing=0>
	<tr>
		<td>아이디</td>
		<td>전화번호</td>
		<td>별칭</td>
		<td>성별</td>
		<td>지역</td>
		<td>생년월일</td>
		<td>가입일시</td>
		<td>변경일시</td>
		<td>수정</td>
		<td>삭제</td>
	</tr>
	<c:forEach items="${memberList }" var="mdto">	
	<tr>
		<td>${mdto.id }</td>
		<td>${mdto.tel }</td>
		<td>${mdto.nickName }</td>
		<td>${mdto.gender }</td>
		<td>${mdto.region }</td>
		<td>${mdto.birth }</td>
		<td>${mdto.cdate }</td>
		<td>${mdto.udate }</td>
		<td><button class="modify" data-memurl="/member/memberModifyForm/${mdto.id }">수정</button></td>
		<td><button class="delete" data-memurl="/member/memberDelete/${mdto.id }">삭제</button></td>		
	</tr>
	</c:forEach>
	<tr><td colspan="10" align="center"><button class="insert" data-memurl="/member/memberJoinForm">회원가입</button></td></tr>
</table>
</body>
</html>