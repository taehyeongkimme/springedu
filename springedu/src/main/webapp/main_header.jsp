<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    


<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }"/>

<!doctype html>
<html lang="ko">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="ysh">
    <title>Sring Framwork</title>

    <!-- Bootstrap 시작 -->
		<link rel="stylesheet" href="/resources/bootstrap-4.2.1/dist/css/bootstrap.css" />
		
		<script src="/webjars/jquery/3.3.1/dist/jquery.min.js"></script>
		<script src="/webjars/popper.js/1.14.6/dist/umd/popper.min.js"></script>
		<script src="/resources/bootstrap-4.2.1/dist/js/bootstrap.js"></script>
		<!-- Bootstrap 끝     -->   
        
		<!-- font-awesome -->        
    <link rel="stylesheet" href="/webjars/font-awesome/5.6.3/css/all.css" />
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
      }
      
      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    <!-- Custom styles for this template -->
    <link href="https://fonts.googleapis.com/css?family=Playfair+Display:700,900" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="/resources/css/blog.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:300,400,700|Noto+Serif+KR:300,400,700|Open+Sans:300i,400,400i,600,700,800&amp;subset=korean" rel="stylesheet">    
    <style>
		 .display-4 {
			  font-size: 2rem;
			  font-weight: 400;
			  line-height: 1.2;
		  }
		     
		 html,body,samp,
		 .tooltip, .popover, text-monospace {
			font-family: 'Open Sans', 'Noto Sans KR', sans-serif,'Noto Serif KR', serif;	
		 }   
    </style>
  </head>
  <body>
    <div class="container">
  <header class="blog-header py-3">
    <div class="row flex-nowrap justify-content-between align-items-center">
      <div class="col-3 pt-1">
        <a class="text-muted" href="/">Home</a>
      </div>
      <div class="col-5 text-center">
        <a class="blog-header-logo text-dark" href="/">Spring Framework</a>
      </div>
      <div class="col-4 d-flex justify-content-end align-items-center">
        <a class="text-muted" href="#">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="mx-3" focusable="false" role="img"><title>Search</title><circle cx="10.5" cy="10.5" r="7.5"></circle><line x1="21" y1="21" x2="15.8" y2="15.8"></line></svg>
        </a>
        
        <c:choose>
	        <c:when test="${user eq null }">
		        <a class="btn btn-sm btn-outline-secondary mr-2" href="/login/loginForm">로그인</a>
	  	      <a class="btn btn-sm btn-outline-secondary" href="/member/memberJoinForm">회원가입</a>
	  	    </c:when>
					<c:otherwise>
	  	      <b class="mr-2">${user.nickName}님</b>
	  	      <a class="btn btn-sm btn-outline-secondary mr-2" href="/member/memberModifyForm/${user.id}">내정보</a>						
	  	      <a class="btn btn-sm btn-outline-secondary" href="/login/logout">로그아웃</a>						
					</c:otherwise>
        </c:choose>
      </div>
    </div>
  </header>
  <div class="nav-scroller py-1 mb-2">
    <!-- <nav class="nav d-flex justify-content-between"> -->
    <nav class="nav d-flex justify-content-end">
      <a class="p-2 text-muted" href="#"></a>
      <a class="p-2 text-muted" href="#"></a>
      <a class="p-2 text-muted" href="#"></a>
      <a class="p-2 text-muted" href="#"></a>
      <a class="p-2 text-muted" href="#"></a>
      <a class="p-2 text-muted" href="/bbs/list">게시판</a>
      <a class="p-2 text-muted" href="javascript:alert('공사중')">관리자</a>
    </nav>
  </div>

</div>