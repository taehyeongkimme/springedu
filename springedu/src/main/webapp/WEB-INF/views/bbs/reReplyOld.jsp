<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>

	#reply_body {
		margin: 0 auto;
		padding: 0;
		
	}
	
	/* 댓글  */
	
	#modifyDiv {
		/*position: absolute;
		top: 20%;
		left: 50%; */
		margin: 0 auto;
		width: 400px;
		height: 200px;
		padding: 20px;
		background: #666;
		/* z-index: 10; */
	}
	
	#rcontents div {
		border: 1px solid #666;
	}
	
	#rcontents label {
		display: inline-block;
		width: 10%;
	}
	
	#rcontents input,
	#rcontents textarea {
		width: 80%;
		border: 1px solid #ccc;
		padding: 2px;
	}
	
	#rcontents input[type=button] {
		width: 100px;
	}

	/* 페이징  */
	#pageNumList li {
		display: inline-block;
		background: #000;
	}
	
	#pageNumList li a{
		text-decoration: none;
		color: #fff;
	}
	
</style>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	var bnum = 10103;		// 원글 번호
	var rereqPage = 1;	// 요청페이지
	
	// document가 전부 로딩됐을때 실행
	$(function(){
		
		//댓글목록 보내기
		replyList(rereqPage);
		
		// 댓글 작성
		$('#replyBtn').on('click',function(){		// $('#replyBtn').click(function(){})
			var rid = $('#rid').val();		// 작성자
			var rcontent = $('#rcontent').val();		// 댓글 본문
			
			console.log(rid);
			console.log(rcontent);
			
			// ajax 비동기 처리기술
			$.ajax({
				type: "POST",		// post, get, put, delete
				url: "/rbbs/posts",
				dataType: "text",
				data:{
					bnum: bnum,
					rcontent: rcontent
				},
				success: function(result){
					// 댓글 목록 호출
					//console.log(result);
					replyList(rereqPage);
					
				},
				error: function(xhr,status,err){
					//console.log("실패"+e);
					console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
				}
				
			});
		});
		
		// 댓글 목록의 수정버튼 클릭시
		$("#modifyDiv").hide();
		$("#replyList").on("click","button#b1",function(){
			//alert("b1클릭됨");
			var li = $(this).parent();
			//console.log(li);
			var rnum = li.attr("data-rnum");
			var rcontent = li.attr("data-rcontent");
			//var rcontent = li.text();

			
			$(".title-dialog").html(rnum);
			$("#rrcontent").val(rcontent);
			$("#modifyDiv").show("slow");
		});
		
		// 대댓글 등록
		$("#reReplyBtn").on("click",function(){
			var rnum = $(".title-dialog").text();
			var rrcontent = $("#rrcontent").val();
			console.log(rnum);
			console.log(rrcontent);
			
			$.ajax({
				type: "POST",	
				url: "/rbbs/rposts",
				dataType: "text",
				data:{
					rnum: rnum,
					rcontent: rrcontent
				},
				success: function(result){
					replyList(rereqPage);
				},
				error: function(xhr,status,err){
					console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
				}
			});
		});
		
		// 댓글 수정
		$("#reModifyBtn").on("click",function(){
			var rnum = $(".title-dialog").text();
			var rrcontent = $("#rrcontent").val();
			//console.log(rnum);
			//console.log(rrcontent);
			
			$.ajax({
				type: "PUT",
				url: "/rbbs/posts",
				dataType: "text",
				data: {
					rnum: rnum,
					rcontent: rrcontent
				},
				success: function(data){
					replyList(rereqPage);
				},
				error: function(xhr,status,err){
					console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
				}
			});
			
		});
		
		// 댓글 삭제
		$("#reDeleteBtn").on("click",function(){
			var rnum = $(".title-dialog").text();
			
			$.ajax({
				type: "DELETE",
				url: "/rbbs/posts/"+rnum,
				dataType: "text",
				/* data: {
					rnum: rnum
				},*/
				success: function(data){
					replyList(rereqPage);
				},
				error: function(xhr,status,err){
					console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
				}
			});
		});
		
		// 호감
		$("#goodBtn").on("click",function(){
			var rnum = $(".title-dialog").text();
			
			$.ajax({
				type: "PUT",
				url: "/rbbs/posts/good"+rnum,
				dataType: "text",
				/* data: {
					rnum: rnum,
					goodOrBad: "good"
				}, */
				success: function(data){
					replyList(rereqPage);
				},
				error: function(xhr,status,err){
					console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
				}
			});
		});
		
		// 비호감
		$("#badBtn").on("click",function(){
			var rnum = $(".title-dialog").text();
			
			$.ajax({
				type: "POST",
				url: "/rbbs/posts/bad"+rnum,
				dataType: "text",
				/* data: {
					rnum: rnum,
					goodOrBad: "bad"
				}, */
				success: function(data){
					replyList(rereqPage);
				},
				error: function(xhr,status,err){
					console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
				}
			});
		});
		
		// 닫기
		$("#closeBtn").on("click",function(){
			$("#modifyDiv").hide();
		});
		
		
		// 페이지번호 클릭시 이벤트 처리 pageNumList
		$('#pageNumList').on("click","a",function(evt){		// div(pageNumList)클릭시 a태그에 이벤트위임
			
			// 이벤트 본래의 행위를 방지
			evt.preventDefault();
			rereqPage = $(this).attr("href");
			replyList(rereqPage);
		});
		
	});

	// 요청페이지에 대한 댓글목록 가져오기
	function replyList(rereqPage){
		var str="";
		$.ajax({
			type: "GET",
			url: "/rbbs/posts/map/"+bnum+"/"+rereqPage,
					dataType:"json",
					success: function(data,status,xhr){
 						console.log("data"+data);
						console.log("data.item"+data.item);
						console.log("data.pagecriteria"+data.pagecriteria);
						
 						$.each(data.item,function(idx,rec){
 							console.log("idx"+idx);
							console.log("rec"+rec);
							
							str += "<span>";
							str += "<li data-rnum='"+rec.rnum+"'data-rcontent='"+rec.rcontent+"' class='reList'>";
							
							// 들여쓰기
							for(i=0; i<rec.rindent; i++){
								str += "&nbsp;&nbsp;";
							}
							str += rec.rid+"|"
									+  rec.rnickname+"|"
									+  rec.rcdate+"|"
									+  rec.rcontent+"|"
									+  rec.rgood+"|"
									+  rec.rbad+"|"
									+  "<button id='b1'>수정</button>"
									+  "</li></span>";
							
									console.log(str);
							
							// 댓글 목록 삽입
							$("#replyList").html(str);
							
						});
						
						// 페이지 리스트 호출
						showPageList(data.pagecriteria);
						
					},
					error: function(xhr,status,err){
						console.log("xhr"+xhr);
						console.log("status"+status);
						console.log("err"+err);
					}
		});
		
	}
	
	// 페이징 구현
	function showPageList(pagecriteria){
		
		var str= "";
		console.log(pagecriteria);
		
		// 이전페이지 여부
		if(pagecriteria.prev){
			// 처음
			str += "<li><a href='1'>◀</a></li>";
			// 이전페이지
			str += "<li><a href='"+(pagecriteria.startPage-1)+"'>◁</a></li>";
		}
		
		// 페이지 1~10
		for(i=pagecriteria.startPage, end=pagecriteria.endPage; i<=end; i++){
			str += "<li><a href='"+i+"'>"+i+"</a></li>";
		}
		
		// 다음페이지 여부
		if(pagecriteria.next){
			// 이전페이지
			str += "<li><a href='"+(pagecriteria.endPage-1)+"'>▷</a></li>";
			// 마지막
			str += "<li><a href='"+(pagecriteria.finalEndPage)+"'>▶</a></li>";
		}
		
		// 페이징 삽입
		$('#pageNumList').html(str);
		
	}
	
</script>
</head>
	<div id="modifyDiv">
		<span class="title-dialog"></span>번 댓글<div>
			<textarea id="rrcontent" cols="50" rows="3"></textarea>
		</div>
		<div>
			<button id="reReplyBtn">댓글</button>
			<button id="reModifyBtn">수정</button>
			<button id="reDeleteBtn">삭제</button>
			<button id="goodBtn">호감</button>
			<button id="badBtn">비호감</button>
			<button id="closeBtn">닫기</button>
		</div>
	</div>

	<!-- 댓글 작성   -->
	<div id="rcontents">
		<div>
			<label for="rid">작성자</label><input type="text" name="" id="rid" />
		</div>
		<div>
			<label for="rcontent">댓글</label><textarea name="" id="rcontent" cols="50" rows="8"></textarea>
		</div>
		<div>
			<label for=""></label><input type="button" value="댓글작성" id="replyBtn" />
		</div>
	</div>

	<!-- 댓글 목록   -->
	<div id="replyList"></div>

	<!-- 댓글 페이징   -->
	<div id="pageNumList"></div>
