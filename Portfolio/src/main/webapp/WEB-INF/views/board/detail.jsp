<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<section class="content">
	<div class="box">
		<div class="box-header">
			<h3 class="box-title">상세보기</h3>
		</div>
		<div class="box-body">
			<div class="form-group">
				<label>제목</label> <input type="text" name="title"
					class="form-control" value="${vo.title}" readonly="readonly" />
			</div>
			<div class="form-group">
				<label>내용</label>
				<textarea name="content" rows="5" readonly="readonly"
					class="form-control">${vo.content}</textarea>
			</div>
			<div class="form-group">
				<label>작성자</label> <input type="text" class="form-control"
					value="${vo.nickname}" readonly="readonly" />
			</div>
		</div>
		<div class="box-footer">
			<button class="btn btn-success" id="mainbtn">메인</button>
			<c:if test="${user.email == vo.email}">
				<!-- 로그인 한 사람과 글 쓴 사람이 같을 때만 수정, 삭제가 된다 -->
				<button class="btn btn-warning" id="updatebtn">수정</button>
				<button class="btn btn-danger" id="deletebtn">삭제</button>
			</c:if>
			<button class="btn btn-primary" id="listbtn">목록</button>
			<button class="btn btn-default" id="replyregister">댓글 작성</button>
			<button class ="btn btn-default" id="replylist">댓글 읽기</button>
			<!-- 댓글이 있을 때만 버튼 만들기 -->
			<!--<c:if test="${vo.replycnt >  0}">-->			
			</c:if>
        
		</div>
	</div>
	<div id="replydisp" style="width:100%; height:200px; overflow-y:scroll"></div>
</section>
<%@ include file="../include/footer.jsp"%>

<!-- jquery ui를 사용하기 위한 링크 설정 -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<script>

	//현재 로그인 한 유저의 email을 가져오기
	//세션에 있는 요소를 자바스크립트에 저장하기
	//댓글작성한 유저와 비교해서 수정과 삭제버튼을 생성하기 위해서
	//로그인 한 유저의 email을 저장
	email = '${user.email}'
	//데이터를 가져오는 함수
	function getReply() {
		$.ajax({
			url : '/reply/list',
			data : {
				bno : '${vo.bno}'
			},
			dataType : "json",
			success : function(data) {
				//출력하는 메소드 호출
				display(data);
			}
		});
	};
	//데이터를 출력하는 함수
	function display(data) {
		//출력할 내용을 저장할 변수
		var disp = '';
		$(data).each(function(idx, item) {
			disp += "<div style='width:80%; height:50px'><label>"
			disp += item.nickname + ":" + item.replytext
			disp += "</label>"
			//로그인한 유저와 작성자가 같으면 수정과 삭제버튼을 추가
			if (email == item.email) {
				disp += "&nbsp;&nbsp;"
				disp += "<button type='submit' class='btn btn-danger' "
				disp += "id='del" + item.rno + "'"
				//disp += " style='float:right;'"
				disp += " onclick='del(this)'>댓글삭제</button>"
				disp += "&nbsp;&nbsp;"
				disp += "<button type='submit' class='btn btn-warning' "
				disp += "id='mod" + item.rno + "'"
				//disp += " style='float:right;'"
				disp += " onclick='mod(this)'>댓글수정</button>"
			}
		});
		document.getElementById("replydisp").innerHTML = disp
	}
	
	//댓글 수정 버튼을 눌렀을 떄 호출되는 함수
	function mod(btn){
		//댓글 번호찾아오기
		var  btnid = btn.id
		var rno = btnid.substr(3)
		
		//화면에 텍스트를 입력할 대화상자 출력
		$('#replyform').dialog({
			resizable:false,
			height:"auto",
			width:400, 
			modal:true,
			buttons:{
				"수정":function(){
				replytext = document.getElementById("replytext").value;	
				$.ajax({
					url:'/reply/update', 
					data:{'rno':rno, 'replytext': replytext},
					dateType:'json',
					success:function(data){
						getReply();
					}
				});
				$(this).dialog("close")
				},
				"취소":function(){
					$(this).dialog("close")
				}
			}
		});
	}
	
	function del(btn){
		//이벤트가 발생한 버튼의 id 찾아오기
		var btnid = btn.id;
		//댓글번호 가져오기
		var rno = btnid.substr(3);
		
		//삭제요청을하고 삭제에 성공하면 댓글만 다시 가져오기
		$.ajax({
			url:'/reply/delete',
		    data:{'rno': rno},
		    dataType:'json',
		    success:function(e){
		    	getReply();
		    }
		});
		
	};
	
	document.getElementById("replylist").addEventListener(
			"click", function(e){
				getReply();
			});
	

	//자바스크립트는 줄단위로 실행
	document.getElementById("mainbtn").addEventListener("click", function(e) {
		location.href = "/";
	});

	document.getElementById("listbtn").addEventListener("click", function(e) {
		location.href = "/board/list";
	});

	<c:if test="${user.email == vo.email}">
	document.getElementById("updatebtn").addEventListener("click", function(e) {
		location.href = "/board/update/${vo.bno}";
	});

	document.getElementById("replyregister").addEventListener(
			"click",
			function(e) {
				$('#replyform').dialog(
						{
							resizable : false,
							height : 'auto',
							width : 400,
							modal : true,
							buttons : {
								"저장" : function() {
									$(this).dialog("close")
									replyText = document
											.getElementById("replytext").value
									$.ajax({
										url : "/reply/register",
										data : {
											bno : "${vo.bno}",
											replyText : replyText
										},
										dataType : "json",
										success : function(data) {
											//alert(data);
											getReply();
										}
									});
								},
								"취소" : function() {
									$(this).dialog("close")
								}
							}
						});
			});

	</c:if>
</script>

<div class="box-body" style="display: none;" id="replyform" title="댓글작성">
	<label for="nickname">작성자</label> <input class="form-control"
		type="text" id="nickname" value="${user.nickname}" readonly="readonly">
	<label for="replytext">댓글 내용</label> 
	<input class="form-control"	type="text" placeholder="댓글을 입력하세요" id="replytext">
</div>
