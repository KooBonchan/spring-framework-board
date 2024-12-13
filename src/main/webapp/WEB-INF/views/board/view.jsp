<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Board View</title>
<%@include file="../includes/header.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/style/view.css">
</head>
<body>
<div class="wrapper">
<%@include file="../includes/navbar.jsp" %>
<div id="page-wrapper">
<section class="container">
	<div class="title"><c:out value='${board.title }' /></div>
	<div class="metadata">
		<span>Writer: <c:out value='${board.writer }' /></span>
		<span>Registered : <fmt:formatDate value="${board.regDate}" pattern="yy.MM.dd."/></span>
		<c:if test="${board.updateDate != board.regDate }">
			<span>Updated: <fmt:formatDate value="${board.regDate}" pattern="yy.MM.dd."/></span>
		</c:if>
	</div>
	<div class="modify-links">
		<a href="/board/update?idx=<c:out value="${board.idx}"/>">Update</a>
		<a href="/board/delete?idx=<c:out value="${board.idx}"/>">Delete</a>
	</div>
	<p class="content">
	${board.content}
	</p>
</section>

<section class="container">
	
	<c:choose>
	<c:when test="${not empty writer }">
		<form name="form-reply" id="form-reply" onsubmit="return false;" class="row">
			<div class="col-12 mb-1">
				<span id="writer"> <c:out value="${writer}" /> </span>
		        <input name="writer" id="writer" class="form-control" type="text" placeholder="Writer" required>
		    </div>
		    <div class="col-9">
		        <textarea name="content" id="content" class="form-control" rows="4" placeholder="Content" required></textarea>
		    </div>
		    <div class="col-3">
		        <button type="button" class="btn btn-primary w-100" onclick="submitReply(<c:out value="${board.idx}"/>)">Reply</button>
		    </div>
		</form>
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-9">
				<span id="writer"> You need to log in to write reply. </span>
			</div>
			<div class="col-3">
          <button id="btn-login" type="button" class="btn btn-primary w-100" onclick="login()">
            Login
          </button>
      </div>
		</div>
		
	</c:otherwise>
	</c:choose>
	<div id="reply-header">
		<span onclick="loadReplies(<c:out value="${board.idx}"/>)">&#x21bb;</span>
	</div>
	<table id="reply-container">
		<!-- Table content will be dynamically populated here -->
	</table>
</section>
</div><!-- Page-Wrapper -->
</div><!-- Wrapper -->

<script>
window.onload = () => {
	loadReplies(${board.idx});
}
</script>
<script src="/resources/js/view.js"></script>
<%@include file="../includes/footer.jsp" %>
</body>
</html>