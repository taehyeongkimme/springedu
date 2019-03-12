<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>    
<style>
    textarea.autosize {
      outline:none;
      width:100%;
      margin-top: 10px;
      padding:5px 0 5px 0;
      border:none;
      border-bottom:2px solid #ccc;
      overflow: hidden;
      resize: none;
    }
</style>
<script>
		var bnum = ${bbsDTO.bnum};		// 원글 번호
		var rereqPage = 1;	// 요청페이지
		
		var l_id = "${sessionScope.user.id}";	// 로긴아이디
		var l_nickname = "${sessionScope.user.nickName}";	//로긴 닉네임
		
		//대댓글 작성 html코드 시작
	    var reply_str = '';
	    reply_str += '<li data-rrnum="" data-form="write" class="list-group-item m-0 p-0">';
	    reply_str += '  <div>';
	    reply_str += '    <div class="row m-0 p-0">';
	    reply_str += '     <div class="col-1"></div>';
	    reply_str += '     <div class="col-11">';
	    reply_str += '        <textarea class="autosize" rows="1"></textarea>';
	    reply_str += '     </div>';
	    reply_str += '     <div class="col-12 text-right">';
	    reply_str += '       <a href="javascript:void(0)" class="rrclose badge badge-pill badge-dark mx-0 px-2">취소</a>';
	    reply_str += '       <a href="javascript:void(0)" class="rrreply badge badge-pill badge-dark mx-0 px-2">댓글</a>';
	    reply_str += '     </div>';
	    reply_str += '   </div>';
	    reply_str += ' </div>';
	    reply_str += '</li>';
	    //대댓글 작성 html코드 끝

	    //대댓글 수정 html코드 시작
	    var modify_str = '';
	    modify_str += '<li data-rrnum="" data-rnum="" data-form="modify" class="list-group-item m-0 p-0">';
	    modify_str += '  <div>';
	    modify_str += '    <div class="row m-0 p-0">';
	    modify_str += '     <div class="col-1"></div>';
	    modify_str += '     <div class="col-11">';
	    modify_str += '        <textarea class="autosize" rows="1"></textarea>';
	    modify_str += '     </div>';
	    modify_str += '     <div class="col-12 text-right">';
	    modify_str += '       <a href="javascript:void(0)" class="rrclose badge badge-pill badge-dark mx-0 px-2">취소</a>';
	    modify_str += '       <a href="javascript:void(0)" class="rrmodify badge badge-pill badge-dark mx-0 px-2">수정</a>';
	    modify_str += '     </div>';
	    modify_str += '   </div>';
	    modify_str += ' </div>';
	    modify_str += '</li>';
	    //대댓글 수정 html코드 끝
	
		// document가 전부 로딩됐을때 실행
		$(function(){
		
		//댓글목록 보내기
		replyList(rereqPage);
		
		// 댓글 글자수 제한 시작--------------------------------------------------------------
		var $rcontent = $("#rcontent"); 
		$rcontent.on("keyup",function(){
			var rcontent = $('#rcontent').val();
			var cnt = rcontent.length;

			$("#count").html(cnt+'/100');
			
			if(cnt>100){
				
				$rcontent.addClass("is-invalid");	
				// 100자 초과시 잘라내기				
				rcontent = rcontent.substring(rcontent.length-100);
				$rcontent.val(rcontent);
				
				$("#count").html(rcontent.length+'/100');
				return false;
			}
			$rcontent.removeClass("is-invalid");
		});
		$rcontent.keyup();
		// 댓글 글자수 제한 끝---------------------------------------------------------------
		
		// 댓글 작성
		$('#replyBtn').on('click',function(){		// $('#replyBtn').click(function(){})
			var rid = $('#rid').text();		// 작성자
			var rnickname = $('#rnickname').text();		// 작성자
			var rcontent = $('#rcontent').val();		// 댓글 본문
			var cnt = rcontent.trim().length;
			
			if(cnt == 0){
				$("#rcontent").attr("placeholder","댓글을 작성하세요.").addClass("is-invalid");
				$("#rcontent").focus();
				return false;
			}else if(cnt>100){
				alert("100자를 초과하였습니다.")
				$("#rcontent").focus();
				return false;
			}
			
			/* console.log(rid);
			console.log(rcontent); */			
			
			// ajax 비동기 처리기술
			$.ajax({
				type: "POST",		// post, get, put, delete
				url: "/rbbs/posts",
				headers:{
					"Content-Type":"application/json",
					"X-HTTP-Method-Override":"POST"
				},
				dataType: "text",
				data:JSON.stringify({
					bnum: bnum,
					rid:rid,
					rnickname:rnickname,
					rcontent: rcontent
				}),
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
		
	});
		
	// 요청페이지에 대한 댓글목록 가져오기
	function replyList(rereqPage){
		var str="";
		var rrnickname = "";	// 부모댓글의 별칭
		var rrid ="";					// 부모댓글의 아이디
		
		$.ajax({
			type: "GET",
			url: "/rbbs/posts/map/"+bnum+"/"+rereqPage,
					dataType:"json",
					success: function(data,status,xhr){
 						console.log("data"+data);
						console.log("data.item"+data.item);
						console.log("data.pagecriteria"+data.pagecriteria);
						
						str += "<ul class='list-group list-group-flush'>";
						
 						$.each(data.item,function(idx,rec){
 							console.log("rec.isdel"+rec.isdel);
 							
							// rrdto null체크 : 부모글이 있는지 체크
							if(rec.rrdto != null){
								rrnickname = '@'+rec.rrdto.rnickname;	//부모댓글의 별칭
								rrid = rec.rrdto.rid;							//부모댓글의 아이디
							}
 							
 							// 삭제유무 판단
 							if(rec.isdel == 'Y'){
 								// 들여쓰기
								if(rec.rindent == 0){
									//1step
								  str += "<li data-rnum='"+rec.rnum+"' class='reList list-group-item'>";
								  str += "  <div>";
								  str += "  <div class='row m-0 p-0'>";
								  str += "    <div class='col-1'>";
								  str += "    </div>";
								  str += "     <div class='col-11'>";
								  str += "       <p class='replybody'><span class='mr-1 text-warning'>삭제된 게시물입니다.</span></p>";
								  str += "     </div>";
								  str += "  </div>";
												
								}else{
									//2step
								  str += "<li data-rnum='"+rec.rnum+"'data-rrnum='"+rec.rrnum+"' class='reList list-group-item'>";
								  str += "  <div>";
								  str += "  <div class='row m-0 p-0'>";
								  str += "    <div class='col-1'></div>";
								  str += "    <div class='col-1'>";
								  str += "    </div>";
								  str += "     <div class='col-10'>";
								  str += "       <p class='replybody'><span class='mr-1 text-warning'>삭제된 게시물입니다.</span></p>";
								  str += "     </div>";
								  str += "  </div>";
								
								}
								  str += "    </div>";
								  str += "</li>";
 							}else{
							
								// 들여쓰기
								if(rec.rindent == 0){
									//1step
								  str += "<li data-rnum='"+rec.rnum+"' class='reList list-group-item'>";
								  str += "  <div>";
								  str += "  <div class='row m-0 p-0'>";
								  str += "    <div class='col-1'>";
								  str += "      <img src='/resources/images/img_avatar3.png' class='rounded-circle'";
								  str += "           style='width:50px;'>";
								  str += "    </div>";
								  str += "     <div class='col-11'>";
								  str += "       <h6>"+rec.rnickname+"("+rec.rid+")<small><i>"+rec.rcdate+"</i></small></h6>";
								  str += "       <p class='replybody'>"+rec.rcontent+"</p>";
								  str += "     </div>";
								  str += "  </div>";
												
								}else{
									//2step
								  str += "<li data-rnum='"+rec.rnum+"'data-rrnum='"+rec.rrnum+"' class='reList list-group-item'>";
								  str += "  <div>";
								  str += "  <div class='row m-0 p-0'>";
								  str += "    <div class='col-1'></div>";
								  str += "    <div class='col-1'>";
								  str += "      <img src='/resources/images/img_avatar2.png' class='rounded-circle'";
								  str += "           style='width:40px;'>";
								  str += "    </div>";
								  str += "     <div class='col-10'>";
								  str += "       <h6>"+rec.rnickname+"("+rec.rid+")<small><i>"+rec.rcdate+"</i></small><small class='ml-2'>";
								  str += "         </small></h6>";
								  str += "       <p class='replybody'><span class='mr-1 text-primary'>"+rrnickname+"</span>"+rec.rcontent+"</p>";
								  str += "     </div>";
								  str += "  </div>";
								
								}
								  str += "    <div class='row m-0 p-0'>";
								  str += "      <div class='col-2'></div>";
								  str += "      <div class='col-8 pl-3'>";
								  str += "        <a href='javascript:void(0)' class='goodBtn badge badge-pill badge-light' ";
								  str += "        data-html='true' title='<small>호감</small>'>";
								  str += "          <i class='far fa-thumbs-up mr-2'>"+rec.rgood+"</i>";
								  str += "        </a>";
								  str += "        <a href='javascript:void(0)' class='badBtn badge badge-pill badge-light' ";
								  str += "        data-html='true' title='<small>비호감</small>'>";
								  str += "          <i class='far fa-thumbs-down mr-2'>"+rec.rbad+"</i>";
								  str += "        </a>";
								  str += "        <a href='javascript:void(0)' class='reReplyBtn badge badge-pill badge-dark px-2'>답글</a>";
								  str += "      </div>";
								  
								  if(l_id == rec.rid){
									  str += "      <div class='col-2 text-right'>";
									  str += "        <a href='javascript:void(0)'  data-active='off' class='reModifyBtn badge badge-pill badge-dark mx-0 px-2'>수정</a>";
									  str += "        <a href='javascript:void(0)'  data-active='off' class='reDelBtn badge badge-pill badge-dark mx-0 px-2'>삭제</a>";
									  str += "      </div>";
								  }
								  str += "    </div>";
								  str += "   </div>";
								  str += "</li>";
	 							}
							  
						});
 						str += "</ul>";
 						
						// 댓글 목록 삽입
						$("#replyList").html(str);
						
						// 페이지 리스트 호출
						showPageList(data.pagecriteria);
						
						// 댓글목록 이벤트 처리
						doActionEvent();
					},
					error: function(xhr,status,err){
						console.log("xhr"+xhr);
						console.log("status"+status);
						console.log("err"+err);
					}
		});
		
		// 페이지번호 클릭시 이벤트 처리
		$("#pageNumList").on("click","li a",function(e){
			e.preventDefault();							// 현재 이벤트의 기본동작을 중단.
			e.stopImmediatePropagation();		// 현재 이벤트가 상위 및 현재레벨에 걸린 다른 이벤트도 동작않도록 중단.
			//e.stopPropagation();						// 현재 이벤트가 상위로 전파되지 않도록 중단.
			reqPage = $(this).attr("href");
			replyList(reqPage);
		});
		
	}
		
	// 페이징구현 
	function showPageList(pagecriteria){
		//console.log(pagecriteria);
		
		var str="";
		
		//이전페이지여부
		if(pagecriteria.prev){		
		 //처음	
		 str += "<li class='page-item'>";
	   str += "<a class='page-link' href='1' tabindex='-1' aria-disabled='true'>◀</a></li>";
	   
	   //이전페이지
	   str += "<li class='page-item'><a class='page-link href='";
	   str +=  (pagecriteria.startPage-1);
	   str += "' tabindex='-1' aria-disabled='true'>◁</a></li>";
		}
		
		//페이지 1~10
		for( i=pagecriteria.startPage, end=pagecriteria.endPage; i<=end; i++){
		
	    <!-- 현재페이지와 요청페이지가 다르면 -->
	   	if(pagecriteria.recordCriteria.reqPage != i) {           
	   		str += "<li class='page-item'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
	   	}else{
	    	<!-- 현재페이지와 요청페이지가 같으면 -->
	    	str += "<li class='page-item active'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
	   	}
		}
		
		//다음페이지여부
		if(pagecriteria.next){
			//다음페이지
	    str += "<li class='page-item'><a class='page-link' href='";
	    str += (pagecriteria.endPage+1);
	    str += "'>▷</a></li>";		
	    
			//마지막
	    str += "<li class='page-item'><a class='page-link' href='";
	    str += (pagecriteria.finalEndPage);
	    str += "'>▶</a></li>";			
		}
	   
		//페이징 삽입
		$("#pageNumList").html(str);
	}	
	
	// 댓글목록 이벤트 처리
	function doActionEvent(){

    //대댓글 작성 클릭시
    $(".reReplyBtn").on("click",function(e){
      //console.log(e.target.innerText);

      var $liEle = $(this).parents("li");
      var $data_rnum = $liEle.attr('data-rnum');  //댓글번호
      var $data_rrnum = $liEle.next().attr('data-rrnum');  //대댓글번호
      var $data_form = $liEle.next().attr('data-form');  //

      //대댓글 등록양식이 없을 경우만 추가
      if($data_rnum != $data_rrnum) {
        $liEle.after(reply_str);

     // 현재글의 대댓글은 있으나 댓글 등록중이 아닐경우
      }else if($data_rnum == $data_rrnum && $data_form == null) {
    	  $liEle.after(reply_str);
      
      }else if($data_rnum == $data_rrnum && $data_form == 'write') {
        var $textarea = $liEle.next().find("textarea");
        var $tmp = $textarea.val();

        $liEle.next().replaceWith(reply_str);
        $liEle.next().find("textarea").val($tmp);
        
      }else if($data_rnum == $data_rrnum && $data_form == 'modify') {
        $liEle.next().replaceWith(reply_str);
        $liEle.next().find("textarea").val($tmp);

      }
      $liEle.next().attr('data-rrnum',$liEle.attr('data-rnum'));

      //대댓글 양식 높이 자동조절
      var $textareaEle = $("textarea.autosize");
      $textareaEle.on("keyup focus",function(){
        $(this)[0].style.height = 'auto';
        $(this).css('height',$(this).prop('scrollHeight'));
      });
      $textareaEle.trigger('focus');

      //취소버튼 클릭시 대댓글 양식 닫기
      $(".rrclose").on("click",function(e){
        e.stopImmediatePropagation();
        $liEle.next().remove();
      });
      
      //리댓글버튼 클릭시 리댓글 등록 - bnum,rrnum,rid,rnickname,rcontent
      $(".rrreply").on("click",function(e){
    	  console.log("리댓글");
    	  var $li = $(this).parents('li');
    	  var rrnum = $li.attr('data-rrnum');
    	  var rcontent = $li.find('textarea').val();
    	  
    	  
    	  console.log(rcontent);
    	  
    	  $.ajax({
    		  type:"POST",
    		  url:"/rbbs/rposts",
    		  headers:{
    			  "Content-Type":"application/json"
    		  },
    		  dataType:"text",
    		  data:JSON.stringify({
    			  bnum : bnum,
    			  rrnum : rrnum,
    			  rid : l_id,
    			  rnickname : l_nickname,
    			  rcontent : rcontent
    		  }),
    		  success:function(result){
        		replyList(rereqPage);
        	},
        	error:function(){
   	    		console.log("xhr"+xhr);
   					console.log("status"+status);
   					console.log("err"+err);
        	}
    		  
    	  });
    	  
    	  
      });
      
    })

    //대댓글 수정 클릭시
    $(".reModifyBtn").on("click",function(e){
      //console.log(e.target.innerText);
      var $liEle = $(this).parents("li");
      var $data_rnum = $liEle.attr('data-rnum');  //댓글번호
      var $data_rrnum = $liEle.next().attr('data-rrnum');  //대댓글번호
      var $data_form = $liEle.next().attr('data-form'); 

      //대댓글 수정양식이 없을 경우만 추가
      if( $data_rnum != $data_rrnum) {
          var tmp = $liEle.find('p').text();
          $liEle.after(modify_str);
          $liEle.next().find('textarea').val(tmp);
          
          // 현재글의 대댓글은 있으나 댓글 수정중이 아닐경우
      	}else if($data_rnum == $data_rrnum && $data_form == null) {
      		var tmp = $liEle.find('p').text();
          $liEle.after(modify_str);
          $liEle.next().find('textarea').val(tmp);

        }else if($data_rnum == $data_rrnum && $data_form == 'write') {
          $tmp = $liEle.find('p').text();
          $liEle.next().replaceWith(modify_str);
          $liEle.next().find('textarea').val($tmp);

        }else if($data_rnum == $data_rrnum && $data_form == 'modify') {

          var $textarea = $liEle.next().find('textarea')
          var $tmp = $textarea.val();
          if($tmp.trim().length == 0 ){
            $tmp = $liEle.find('p').text();
          }
          console.log($tmp);
          $liEle.next().replaceWith(modify_str);
          $liEle.next().find('textarea').val($tmp);
        }
        $liEle.next().attr('data-rrnum',$liEle.attr('data-rnum'));

      //대댓글 양식 높이 자동조절
      var $textareaEle = $("textarea.autosize");
      $textareaEle.on("keyup focus",function(){
        $(this)[0].style.height = 'auto';
        $(this).css('height',$(this).prop('scrollHeight'));
      });
      $textareaEle.trigger('focus');

      //취소버튼 클릭시 대댓글 양식 닫기
      $(".rrclose").on("click",function(e){
        e.stopImmediatePropagation();
        $liEle.next().remove();
      });
      
      //수정버튼 클릭시  rnum,rcontent
      $(".rrmodify").on("click",function(e){
    	  console.log("수정");
				var $li = $(this).parents('li');
				var rnum = $li.attr('data-rrnum');
				var rcontent = $li.find('textarea').val();
				
				$.ajax({
					type:"PUT",
					url:"/rbbs/posts",
					headers:{
    			  "Content-Type":"application/json"
    		  },
    		  dataType:"text",
    		  data:JSON.stringify({
    			  rnum:rnum,
    			  rcontent:rcontent
    		  }),
    		  success:function(result){
        		replyList(rereqPage);
        	},
        	error:function(){
   	    		console.log("xhr"+xhr);
   					console.log("status"+status);
   					console.log("err"+err);
        	}
				});
      
      });
      
    });

    // 댓글 삭제 클릭시
    $(".reDelBtn").on("click",function(){
      // 삭제클릭시 이벤트가 일어나는 조상태그 찾음
    	var $li = $(this).parents('li');
      var rnum = $li.attr('data-rnum');
      console.log('rnum'+rnum);
      
      // ajax호출 {}-객체
      $.ajax({
    	  type:"DELETE",
    	  url:"/rbbs/posts/"+rnum,
/*     	  headers:{
    		  "Content-Type":"application/json"	//전송내용이 json포맷
    		  "X-HTTP-Method-Override":"POST"		//낮은버전 브라우저에서 POST방식으로 전송
    	  }, */
    	  dataType:"text",
    	  success:function(){
    		  replyList(rereqPage);
    	  },
    	  error:function(){
	    		console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
    	  }
      });
      
    	
    	//삭제 toast띄우기
      $("#t-del-msg").toast("show");
    });
    
		
		// 호감
		$(".goodBtn").on("click",function(){
			console.log("호감");
			var $li = $(this).parents('li');
		  var rnum = $li.attr('data-rnum');
		  
			$.ajax({
    	  type:"PUT",
    	  url:"/rbbs/posts/good/"+rnum,
    	  dataType:"text",
    	  success:function(){
    		  replyList(rereqPage);
    	  },
    	  error:function(){
	    		console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
    	  }
      });
			
		});
    
		// 비호감
		$(".badBtn").on("click",function(){
			console.log("비호감");
			var $li = $(this).parents('li');
		  var rnum = $li.attr('data-rnum');
			$.ajax({
    	  type:"PUT",
    	  url:"/rbbs/posts/bad/"+rnum,
    	  dataType:"text",
    	  success:function(){
    		  replyList(rereqPage);
    	  },
    	  error:function(){
	    		console.log("xhr"+xhr);
					console.log("status"+status);
					console.log("err"+err);
    	  }
      });
			
			

		});
		
	   if (${sessionScope.user.id == null}) {
			   $('.badBtn, .goodBtn, .reReplyBtn').off('click');
			   $('.badBtn, .goodBtn, .reReplyBtn').on('click',function(){
				   alert('로그인이 필요합니다.')
			   }) ;
      }
    
	}
	
</script>
<div class="container">
	<table class="table table-sm table-borderless">
		<colgroup>
			<col width="10%"/>
			<col width="80%"/>
			<col width="10%"/>
		</colgroup>
		<tr class="table-dark">
			<td>작성자</td>
			<td>
				<span id="rnickname">${sessionScope.user.nickName}</span>/
				<span id="rid">${sessionScope.user.id}</span>(
				<span>2019-01-23</span>)</td>
			<td></td>
		</tr>
		<tr class="table-dark">
			<td>댓글</td>
			<td>
				<textarea class="form-control" name="" id="rcontent" rows="2"></textarea>
				<p class="text-right" id="count"></p>
			</td>
			<td><button class="btn btn-dark btn-block btn-md mt-2" id="replyBtn">등록</button></td>
		</tr>
	</table>
	
	<!-- 댓글 목록   -->
	<div id="replyList"></div>
	
	<!-- 댓글 페이징   -->
	<div>
		<nav aria-label="Page navigation example">
			<ul id="pageNumList" class="pagination justify-content-center  pagination-sm"></ul>
		</nav>
	</div>
	</div>

  <!-- toasts -->
  <div role='alert' aria-live='assertive' aria-atomic='true' class='toast' id='t-del-msg'
  style="position:fixed; width:200px; right:50px; bottom:50px;" data-autohide='true' data-delay='1500'>
  <div class='toast-header'>
    <!-- <img src='' class='rounded mr-2' alt=''> -->
    <i class='fas fa-trash mr-1' style='color:red;'></i>
    <strong class='mr-auto'>삭제</strong>
    <!-- <small class='invisible'>11 mins ago</small> -->
    <a type='a' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>
      <span aria-hidden='true'></span>
    </a>
  </div>
  <div class='toast-body'>
    	댓글이 삭제됨.
  </div>
	</div>

















