<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/main_header.jsp" flush="false" />
<script>
	$(function() {
		// 읽기모드
		$("#rmode").css({"display":""});
		// 편집모드
		$("#umode").css({"display":"none"});
		
		// 수정버튼 클릭시 편집모드로 변환
		$("#btn2").on("click",function(e){
			$("#rmode").css({"display":"none"});
			$("#umode").css({"display":""});
			$("h3").text("게시글 수정");
			
			$("#btitle, #bcontent").removeAttr("readonly");
		});
		
		// 수정취소버튼 
		$("#btn5").on("click",function(e){
			e.preventDefault();
			location.href="/bbs/view?bnum=${bbsDTO.bnum}&reqPage=${rc.reqPage}";
		});
		
		// 삭제버튼 : 게시글 삭제
		$("#btn3").on("click",function(e){
			e.preventDefault();
			location.href="/bbs/delete?bnum=${bbsDTO.bnum}&reqPage=${rc.reqPage}";
		});
		
		
		// 목록버튼 : 목록으로 이동
		$("#btn4,#btn7").on("click",function(e){
			e.preventDefault();
			location.href="/bbs/list?reqPage=${rc.reqPage}";
		});
		
		// 수정완료버튼
		$("#btn6").on("click",function(e){
			e.preventDefault();
			if(valChk()){
			$("form").submit();
			}
		});
		
		// 답글작성
		$("#btn1").on("click", function() {
			/* var user ="${user.id == null ? null : user.id}";
			if(user == null || user == ""){
				location.href="/login/loginForm";
				return;
			} */
			location.href = "/bbs/replyForm/${bbsDTO.bnum}/${rc.reqPage}";
		});
		
		
		//유효성체크 오류시 에러표시 css적용 
		$("span[id$='.errors']").each(function(idx) {
			if ($(this).text().length > 0) {
					$(this).prev().removeClass("is-valid").addClass("is-invalid");
					$(this).removeClass("valid-feedback").addClass("invalid-feedback");
			}
		});
	});

	function valChk() {

		/*제목입력값이 없을 경우*/
		if ($("#btitle").val().length == 0) {
			alert('제목을 입력하세요.');
			$("#btitle").focus();
			return false;
		}

		/*제목입력값길이 체크*/
		if ($("#btitle").val().length > 30) {
			alert('제목은 30자이내로 입력하세요.');
			$("#btitle").focus();
			return false;
		}

		/*내용입력값이 없을 경우*/
		if ($("#bcontent").val().length == 0) {
			alert('내용을 입력하세요.');
			bcontent.focus();
			return false;
		}

		/*내용입력값길이 체크*/
		if ($("#bcontent").val().length > 100) {
			alert('내용은 100자이내로 입력하세요.');
			$("#bcontent").focus();
			return false;
		}
		return true;
	};
</script>
<div class="container">
	<div class="table-responsive">
		<h3 class="text-center p-3 mb-3 bg-white font-weight-bolder">게시글 보기</h3>
		<form:form modelAttribute="bbsDTO" action="/bbs/modifyOk" method="post">
		<form:hidden path="bid" value="${user.id }"></form:hidden>
		<form:hidden path="bnickname" value="${user.nickName }"></form:hidden>
		<form:hidden path="bnum" value="${bbsDTO.bnum }"></form:hidden>
			<input type="hidden" name="reqPage" value="${rc.reqPage }"/>
			<table class="table table-sm" summary="게시글 보기">
				<colgroup>
					<col width="20%">
					<col width="">
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<form:input class="form-control" type="text"
								path="btitle" readonly="true"
								value="${bbsDTO.btitle }" />
							<form:errors path="btitle" class="valid-feedback"></form:errors>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${bbsDTO.bnickname }(${bbsDTO.bid })</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<form:textarea class="form-control"
								path="bcontent" rows="15" cols="30" placeholder="본문내용을 입력하세요."
								readonly="true" value="${bbsDTO.bcontent }"></form:textarea>
							<form:errors path="bcontent" class="valid-feedback"></form:errors>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="right">
						<div id="rmode">
							<button id="btn1" type="button" class="btn btn-outline-primary btn-sm">답글</button>
							<!-- 작성자만 수정,삭제 기능 시작 -->
							<c:if test="${bbsDTO.bid eq sessionScope.user.id }">
							<button id="btn2" type="button" class="btn btn-outline-primary btn-sm">수정</button>
							<button id="btn3" type="button" class="btn btn-outline-primary btn-sm">삭제</button>
							</c:if>
							<!-- 작성자만 수정,삭제 기능 종료 -->
							<button id="btn4" type="button" class="btn btn-outline-primary btn-sm">목록</button>
						</div>
						<div id="umode">
							<button id="btn5" type="button" class="btn btn-outline-primary btn-sm">수정취소</button>
							<button id="btn6" type="button" class="btn btn-outline-primary btn-sm">수정완료</button>
							<button id="btn7" type="button" class="btn btn-outline-primary btn-sm">목록</button>
						</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/bbs/reReply.jsp" flush="false" />
<jsp:include page="/main_footer.jsp" flush="false" />
