<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="/main_header.jsp" flush="false" />


<style>
	.bd-placeholder-img {
		font-size: 1.125rem;
		text-anchor: middle;
	}

	@media ( min-width : 768px) {
		.bd-placeholder-img-lg {
			font-size: 3.5rem;
		}
	}
</style>
<!-- Custom styles for this template -->
<link href="cover.css" rel="stylesheet">
<body class="text-center">
	<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">

		<main role="main" class="inner cover">
		<h3 class="cover-heading">이용해주셔서 감사합니다.</h3>
		<p class="lead">회원가입 후 다시 사이트를 이용하실 수 있습니다.</p>
		<p class="lead">
			<a href="${pageContext.request.contextPath }/"
				class="btn btn-lg btn-secondary">메인화면으로 이동</a>
		</p>
		</main>
		</div>
<jsp:include page="/main_footer.jsp" flush="false" />