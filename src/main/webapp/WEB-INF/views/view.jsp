<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags"  prefix="sec"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Board View</title>
<%@include file="./includes/header.jsp" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/view.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/imageview.css" />">
</head>
<body>
<div class="wrapper">
<%@include file="./includes/navbar.jsp" %>
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
	
	<sec:authorize access="hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')">
	<sec:authentication property="principal.username" var="username"/>
	<c:if test="${username eq board.writer }">
		<div class="modify-links d-flex justify-left">
	    <form action="/board/update" method="get">
        <input type="hidden" name="idx" value="<c:out value="${board.idx}"/>" />
        <button class="btn btn-default btn-writer-only">Update</button>
      </form>
	    <form action="/board/delete" method="post">
	      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	      <input type="hidden" name="idx" value="<c:out value="${board.idx}"/>" />
	      <input type="hidden" name="writer" value="${username}" />
	      <button class="btn btn-default btn-writer-only">Delete</button>
	    </form>
	  </div>
  </c:if>
	</sec:authorize>
	
	<p class="content"><c:out value='${board.content}' /></p>
	
	<div>
	<c:forEach var="image" items="${board.images}">
		<c:url var="pathUrl" value="/api/image/thumbnail/${image.filePath}/${image.realFileName}" />
		<div class="image-wrapper">
		 	 <img class="thumbnail" alt="attached images" src="${pathUrl}">
		</div>
	</c:forEach>
	</div>
</section>

<section class="container">
	
	<sec:authorize access="hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')">
    <form name="form-reply" id="form-reply" onsubmit="return false;" class="row">
      <div class="col-12 mb-1 mt-3">
        <span id="writer">Leave your comments, <sec:authentication property="principal.username"/></span>
      </div>
      <div class="col-9">
          <textarea name="content" id="content" class="form-control" rows="4" placeholder="Comment" required></textarea>
      </div>
      <div class="col-3">
          <button type="button" class="btn btn-primary w-100" onclick="submitReply(<c:out value="${board.idx}"/>)">Reply</button>
      </div>
      <input type="hidden" id="csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
	</sec:authorize>
	<sec:authorize access="isAnonymous()">
    <div class="row">
      <div class="col-9">
        <span id="writer"> You need to log in to write reply. </span>
      </div>
      <div class="col-3">
          <button id="btn-login" type="button" class="btn btn-primary w-100"
            onClick="location.href='/login'">
            Login
          </button>
      </div>
    </div>
	</sec:authorize>
	
	<div id="reply-header">
		<span onclick="loadReplies(<c:out value="${board.idx}"/>)">&#x21bb;</span>
	</div>
	<table id="reply-container">
		<!-- Table content will be dynamically populated here -->
	</table>
</section>
</div><!-- Page-Wrapper -->
</div><!-- Wrapper -->

<div id="big-image-background">
  <img id="image-fullscreen" alt="FULLSCREEN IMAGE" src="">
</div>
<script>
window.onload = () => {
	loadReplies(<c:out value="${board.idx}"/>);
}
const csrfHeaderName = "${_csrf.headerName}";
const csrfTokenValue = "${_csrf.token}";
const myname = "${username}";
</script>
<script src="<c:url value="/resources/js/imageview.js" />"></script>
<script src="<c:url value="/resources/js/view.js" />"></script>
<%@include file="./includes/footer.jsp" %>
</body>
</html>