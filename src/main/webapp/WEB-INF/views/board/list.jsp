<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Board List</title>
</head>
<body>
<header>
<h2>LOGO</h2>
<div>
	<form name="form-search">
	<select name="category">
		<option value="title" label="Title">
		<option value="writer" label="Writer" ${param.category == "writer" ? 'selected' : ''}>
		<option value="content" label="Content" ${param.category == "content" ? 'selected' : ''}>
	</select>
	<input name="query" value='${param.query}'>
	<input type="submit" value="submit">
	</form>
	<button type="button" onclick='location.href="/board"'>reset</button>
</div>
</header>
<table>
	<tr>
		<th>Index</th>
		<th>Title</th>
		<th>Writer</th>
		<th>Reg Date</th>
	</tr>
	<c:if test="${empty boards}">
	<tr><td class="no-content" colspan=4>No Contents</td></tr>
	</c:if>
	<c:forEach var="board" items="${boards}">
	<tr>
		<td>${board.idx}</td>
		
		<td><a href="/board?idx=${board.idx}">${board.title}</a></td>
		
		<td>${board.writer}</td>
		<td>${board.regDate}</td>
	</tr>
	</c:forEach>
</table>
<footer>
<div>
<c:url var="queryUrl" value="/board">
	<c:param name="category" value="${param.category}" />
    <c:param name="query" value="${param.query}" />
</c:url>
<c:choose>
    <c:when test="${pageInfo.startPage == 1}">
        <span class="">Start</span>
        <span class="">PREV</span>
    </c:when>
    <c:otherwise>
        <a href="${queryUrl}">Start</a>
        <a href="${queryUrl}&page=${pageInfo.startPage - 1}">PREV</a>
    </c:otherwise>
</c:choose>
<span>
	<c:forEach var="page" begin="${pageInfo.startPage}" end="${pageInfo.endPage }">
	<c:choose>
	    <c:when test="${pageInfo.page == page}">
	        <span class="disabled">${page}</span>
	    </c:when>
	    <c:otherwise>
	    	<a href="${queryUrl}&page=${page}">${page}</a>
	    </c:otherwise>
	</c:choose>
	</c:forEach>
</span>

<c:choose>
    <c:when test="${pageInfo.endPage == pageInfo.maxPage}">
        <span class="disabled">NEXT</span>
        <span class="disabled">End</span>
    </c:when>
    <c:otherwise>
        <a href="${queryUrl}&page=${pageInfo.endPage + 1}">NEXT</a>
        <a href="${queryUrl}&page=${pageInfo.maxPage}">End</a>
    </c:otherwise>
</c:choose>

</div>
<div class="btn-bar">
<button type="button" onclick="location.href='/board/write'">Write</button>
</div>
</footer>
</body>
<script>
const urlParams = new URLSearchParams(window.location.search);
const message = urlParams.get('message');
window.onload = () => {
	if(message != null && message.length > 0){
		alert(message);
	}	
}
</script>
</html>