<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="/main_header.jsp" flush="false"/>
 <!-- Custom styles for this template -->
    <link href="/resources/ccs/floating-labels.css" rel="stylesheet">
		<style>
			.errmsg {color: #f00;}
			
		.bd-placeholder-img {
	    font-size: 1.125rem;
	    text-anchor: middle;
	  }
	
		@media (min-width: 768px) {
	    .bd-placeholder-img-lg {
	      font-size: 3.5rem;
	  	}
	  	.find {
    		font-size: 13px;
    	}
	  }
	  
	  .find {
	  	margin-top:20px;
	  	border-top:1px solid #ccc;
	  	line-height: 14px;
    	padding-top: 20px;
   	 	text-align: center;
    	color: #8e8e8e;
	  }
	  
	  #findId, #findPw {
	  	color:#ccc;
	  }   
	  
	  .bar {
		  display: inline-block;
		  width: 1px;
		  height: 13px;
		  margin: 0 4px;
		  text-indent: -999em;
		  background: #e4e4e5;
    }
	</style>
	
          
	<div class="container">
      <div class="row justify-content-center">
    <div class="col-4">
<form:form class="form-signin" modelAttribute="login" action="/login">
  <div class="text-center mb-4">
    <img class="mb-4" src="/resources/images/login.png" alt="" width="150" height="150">
        <h1 class="h3 mb-3 font-weight-normal">Log in</h1>
    <!-- <p>Build form controls with floating labels via the <code>:placeholder-shown</code> pseudo-element. <a href="https://caniuse.com/#feat=css-placeholder-shown">Works in latest Chrome, Safari, and Firefox.</a></p> -->
  </div>

  <div class="form-label-group">
    <form:label path="id">아이디</form:label>
    <form:input type="text" path="id" class="form-control" placeholder="ex)aaa@bbb.com" autofocus="true" />
    <form:errors path="id" cssClass="errmsg"></form:errors>
  </div>

  <div class="form-label-group">
    <form:label path="pw">비밀번호</form:label>
    <form:input type="password" path="pw" class="form-control" placeholder="비밀번호를 입력하세요!"/>
    <form:errors path="pw" cssClass="errmsg"></form:errors>    
  </div>

  <div class="checkbox mb-3">
    <label>
      <input type="checkbox" name="remember-me"> Remember me
    </label>
  </div>
  <div class="form-label-group">
  	<span class="errmsg">${errmsg }</span>
  </div>

  <button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
  <div class="find">
  	<p>
  		<a id="findId" href="/login/findIdForm">아이디 찾기</a>
  		<span class="bar">|</span>
  		<a id="findPw" href="/login/findPwForm">비밀번호 찾기</a>
  	</p>
  </div>
  <p class="mt-5 mb-3 text-muted text-center">&copy; 2019-2022</p>
</form:form>
</div></div></div>
<jsp:include page="/main_footer.jsp" flush="false" />
