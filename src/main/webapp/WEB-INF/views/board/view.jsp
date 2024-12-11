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
	box-sizing: border-box;
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
.delete{
	font-size: 15px;
	color: #f66;
}

#reply-container {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  overflow: hidden;
  border: 1px solid #ccc;
}
#reply-container .writer{
	width: 100px;
	font-weight: bold;
}
#reply-container .content{
	width: 400px;
}
#reply-container td {
  padding: 10px;
  text-align: left;
  vertical-align: middle;
  font-size: 14px;
  line-height: 1.6;
}
button.delete {
  padding: 6px 12px;
  background-color: #dc3545;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
button.delete:hover {
  background-color: #c82333;
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
	<form name="form-reply" onsubmit="return false;">
		<input name="writer">
		<textarea name="content"></textarea>
		<button type="button" onclick="submitReply(${board.idx})">Reply</button> 
	</form>
	<div id="reply-header">
		<span onclick="loadReplies(${board.idx})">&#x21bb;</span>
	</div>
	<table id="reply-container">
		<!-- Table content will be dynamically populated here -->
	</table>
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
const formReply = document['form-reply'];
function loadReplies(idx){
	fetch("/api/board/" + idx)
	.then(response => response.json())
	.then(jsons => {
		let inner = "";
		if(jsons.length == 0){
			inner = "<tr><td colspan=3>No Replies Yet</td></tr>"
		}
		else {
			for(let json of jsons){
				inner = inner +
					"<tr><td class='writer'>" +json['writer'] + "</td>" +
					"<td class='content'>" +json['content'] + "</td>" +
					"<td><button type='button' class='delete' onclick='deleteReply("+idx+','+json['idx']+")'>X</button></td>" +
					"</tr>";
			}	
		}
		
		replyContainer.innerHTML = inner;
	});
}
function submitReply(idx){
	const data = {
		writer: formReply.writer.value,
		content: formReply.content.value,
	}
	fetch("/api/board/" + idx, {
		method: "POST",
		headers: {
	    	'Content-Type': 'application/json',
	  	},
	  	body: JSON.stringify(data),
	})
	.then(response => {
		loadReplies(idx);
	})
	.catch(console.log)
}
function deleteReply(boardIdx, idx){
	fetch("/api/board/" + boardIdx + "/" + idx, {
		method: "DELETE"
	})
	.then(response => {
		loadReplies(boardIdx);
	})
	.catch(console.log)
}
</script>
</html>