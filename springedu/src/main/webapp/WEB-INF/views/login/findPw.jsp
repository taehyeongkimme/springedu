<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/main_header.jsp" flush="false" />

<style>
	.errmsg {
		color: #f00;
	}
	
	.bd-placeholder-img {
		font-size: 1.125rem;
		text-anchor: middle;
	}
	
	@media ( min-width : 768px) {
		.bd-placeholder-img-lg {
			font-size: 3.5rem;
		}
		.find {
			font-size: 13px;
		}
	}
</style>

<script>
	$(function() {
		//유효성체크 오류시 에러표시 css적용 
		$(".errmsg").each(function(idx) {
			if ($(this).text().length > 0) {
				$(this).prev().removeClass("is-valid").addClass("is-invalid");
			}
		});
		
		$("#findPw").on("click",function(e){
			e.preventDefault();
			$("form").submit();
		})
		
		$("#cancel").on("click",function(e){
			e.preventDefault();
			location.href="/";
		})

	});
</script>

<div class="container">
	<div class="row justify-content-center">
		<div class="col-4">
			<form:form class="form-findPw" modelAttribute="findPW" action="${pageContext.request.contextPath}/login/findPw">
				<div class="text-center mb-4">
					<h1 class="h3 mb-3 font-weight-normal">비밀번호 찾기</h1>
				</div>

				<div class="form-label-group mb-3">
					<form:label path="id">아이디</form:label>
					<form:input type="text" path="id" class="form-control" placeholder="ex)aaa@bbb.com" autofocus="true" />
					<form:errors path="id" cssClass="errmsg"></form:errors>
				</div>


				<div class="form-label-group mb-3">
					<form:label path="tel">전화번호</form:label>
					<form:input type="tel" path="tel" class="form-control" placeholder="ex)010-1234-5678" />
					<form:errors path="tel" cssClass="col-sm-2 errmsg"></form:errors>
				</div>

				<div class="form-label-group mb-3">
					<form:label path="birth">생년월일</form:label>
					<form:input type="date" path="birth" class="form-control" placeholder="생년월일을 입력하세요!" />
					<form:errors path="birth" cssClass=" col-sm-2 errmsg"></form:errors>
				</div>

				<div class="form-label-group mb-3">
					<label>찾은 비밀번호</label> <input type="text" class="form-control" readonly="true" value="${mdto.pw }"></input>
				</div>

				<div class="form-label-group mb-3 row">
					 <div class="col-md" >
						<button class="btn btn-md btn-primary btn-block" type="submit" id="findPw">조회</button>
					 </div>
					 <div class="col-md" >
						<button class="btn btn-md btn-primary btn-block" type="submit" id="cancel">취소</button>
					 </div>
				</div>
				<p class="mt-5 mb-3 text-muted text-center">&copy; 2017-2018</p>
			</form:form>

		</div>
	</div>
</div>


<jsp:include page="/main_footer.jsp" flush="false" />




























