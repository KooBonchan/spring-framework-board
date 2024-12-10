<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Board</title>
<style>
*{
	margin: 0 auto;
	padding: 0;
}
a {
	text-decoration: none;
	color:inherit;
}
html, body{
	width: 400px;
}

.title{
	font-size:31px;
	font-weight:bold;
}
.metadata{
	display:flex;
}
.metadata span{
	margin-left: 0;
	margin-right: 10px;
	color: #999;
	font-size: 12px;
}
.content{
	margin-top:10px;
}
</style>

</head>
<body>
<header>
	<h2 style="width:fit-content;margin:0"><a href="/board">LOGO</a></h2>
</header>
<section>
	<div class="title">${board.title }</div>
	<div class="metadata">
		<span>Writer: ${board.writer}</span>
		<span>Registered : <fmt:formatDate value="${board.regDate}" pattern="yy.MM.dd."/></span>
		<c:if test="${board.updateDate != board.regDate }">
		<span>Updated: <fmt:formatDate value="${board.regDate}" pattern="yy.MM.dd."/></span>
		</c:if>
	</div>
	<p class="content">
	${board.content}
	</p>
</section>

<section>
	<form name="form-reply" onsubmit="submitReply(${board.idx});return false;">
		<input name="writer">
		<textarea name="content">
		</textarea>
		<input type="submit"> 
	</form>
	<div id="reply-header">
		<span onclick="loadReplies(${board.idx})">&#x21bb;</span>
	</div>
	<div id="reply-container">
		
	</div>
</section>

<footer>
	<div class="btn-bar">
	<button type="button" onclick="location.href='/board/update?idx=${board.idx}'">Update</button>
	<button type="button" onclick="location.href='/board/delete?idx=${board.idx}'">Delete</button>
	</div>
</footer>
</body>

<script>
window.onload = () => {
	loadReplies(${board.idx});
}

const replyContainer = document.getElementById("reply-container");
function loadReplies(idx){
	fetch("/api/board/" + idx)
	.then(response => response.json())
	.then(jsons => {
		replyContainer.innerHTML = "";
		for(let json of jsons){
			
		}
	});
}
function submitReply(idx){
	fetch()	
}
</script>
</html>