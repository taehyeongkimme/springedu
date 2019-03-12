<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<b>member page</b>
	<hr>
	<!--1) SPRING_SECURITY_CONTEXT 내장객체 -->
		<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }"/>
		<b>사용자아이디${user.id }</b><hr>
		<b>사용자닉네임${user.nickName }</b><hr>
		<b>사용자생일${user.birth }</b>
		<hr>
		<p>권한 ${user.authorities }</p>
		<c:forEach var="auth" items="${user.authorities }">
			<h3> ${auth.roleId }</h3>
		</c:forEach>
		
	<!--2) 권한  authorize 값은 true/false -->
		<sec:authorize var="member" access="hasRole('ROLE_MEMBER')"/>
		<sec:authorize var="admin" access="hasRole('ROLE_ADMIN')"/>
			
			<c:if test="${member }">
				ROLE_MEMBER권한만 보냄
			</c:if>
			
			<c:if test="${admin }">
				ROLE_MEMBER권한만 보냄
			</c:if>
		
		<%-- <sec:authorize access="hasRole('ROLE_ADMIN')">
			ROLE_ADMIN권한만 보냄
		</sec:authorize> --%>
		<hr>
		
	<!--3) 인증 authentication -->		
		<sec:authentication property="principal"/>
		사용자명 : ${user.username }
</body>
</html>