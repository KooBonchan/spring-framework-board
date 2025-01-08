<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="./includes/header.jsp" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/list.css" />">
<title>Board List</title>
</head>
<body>
<div class="wrapper">
<%@include file="./includes/navbar.jsp" %>
<div id="page-wrapper">
<div class="row"><div class="col-lg-12"><div class="panel panel-default"><div class="panel-body">
	<!-- PAGE SIZING -->
	<c:url var="queryUrl" value="/">
		<c:param name="category" value="${param.category}" />
		<c:param name="query" value="${param.query}" />
	</c:url>
	<div class="d-flex flex-row justify-content-between mb-3 mt-3">
		<form class="form-inline justify-content" role="form"
			action="${queryUrl}" name="form-page-size">
			<div class="form-group">
				<label for="pageSize" class="mr-3">Page Size:</label>
				<div class="input-group">
					<select class="form-control" name="pageSize"
						onchange="document['form-page-size'].submit();">
						<option value="10" label="10"
							${param.pageSize == "10" ? 'selected' : ''}>
						<option value="20" label="20"
							${param.pageSize == "20" ? 'selected' : ''}>
						<option value="50" label="50"
							${param.pageSize == "50" ? 'selected' : ''}>
						<option value="100" label="100"
							${param.pageSize == "100" ? 'selected' : ''}>
					</select>
				</div>
			</div>
		</form>
		<sec:authorize access="isAuthenticated()">
			<button class="btn btn-primary" type="button"
	      onclick="location.href='/board/write'">Write new
	      document
	    </button>
		</sec:authorize>
		<sec:authorize access="isAnonymous()">
		  <div>
		    <span>You need to login<br>to write new document</span>
		  </div>
		</sec:authorize>
		
	</div>

	<!-- SEARCH QUERY -->
	<div class="mb-3">
		<c:url var="pageSizeUrl" value="/">
			<c:param name="pageSize" value="${param.pageSize}" />
		</c:url>
		<form role="form" class="form-inline" action="${pageSizeUrl }" name="form-search">
			<div class="form-group mr-1">
				<select class="form-control" name="category">
					<option value="title" label="Title">
					<option value="writer" label="Writer" ${param.category == "writer" ? 'selected' : ''}>
					<option value="content" label="Content" ${param.category == "content" ? 'selected' : ''}>
				</select>
			</div>
			<div class="form-group mr-3"  style="flex-grow: 1;" >
				<input style="width:100%;" class="form-control" name="query" value='<c:out value="${param.query}" />'>
			</div>
			
			<div class="form-group">
				<button class="btn btn-default" type="submit" value="submit">
					<i class="fa fa-search"></i>
				</button>
			</div>
			<div class="form-group">
				<button class="btn btn-default" type="button" onclick='location.href="/board"' tabindex="0">RESET</button>
			</div>
		</form>
	</div>
				
  <!-- TABLE -->
	<table class="table table-striped table-bordered table-hover" id="board-list">
		<thead>
			<tr>
				<th>Index</th>
				<th>Title</th>
				<th>Writer</th>
				<th>Reg Date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="board" items="${boards}">
				<tr
					onclick="location.href='/board?idx=<c:out value="${board.idx}" />'">
					<td><c:out value="${board.idx}" /></td>
					<td><c:out value="${board.title}" />
            <c:if
              test="${board.imageCount > 0}">
              <i class="far fa-file-image"></i>
            </c:if>
            <c:if
							test="${board.replyCount > 0 }">
							<span class="reply-count">[<c:out
									value="${board.replyCount}" />]
							</span>
						</c:if>
					</td>
					<td><c:out value="${board.writer}" /></td>
					<td><c:out value="${board.regDate}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- table pagination -->
	<c:url var="pagingUrl" value="/">
    <c:param name="category" value="${param.category}" />
    <c:param name="query" value="${param.query}" />
		<c:param name="pageSize" value="${param.pageSize}" />
	</c:url>
	<div class='float-right'>
		<ul class='pagination'>
			<c:choose>
				<c:when test="${pageInfo.startPage == 1}">
					<li class="page-item disabled"><a class="page-link"
						href="javascript:void(0);" tabindex="-1">PREV</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link"
						href="${pagingUrl}&page=${pageInfo.startPage - 1}">PREV</a>
					</li>
				</c:otherwise>
			</c:choose>
	
			<c:forEach var="page" begin="${pageInfo.startPage}"
				end="${pageInfo.endPage }">
				<li class="page-item ${pageInfo.page == page ? 'active' : '' }">
					<a class="page-link"
					 href="${pagingUrl}&page=${page}">${page}</a>
				</li>
			</c:forEach>
	
			<c:choose>
				<c:when test="${pageInfo.endPage == pageInfo.maxPage}">
					<li class="page-item disabled"><a class="page-link"
						href="javascript:void(0);" tabindex="-1">NEXT</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link"
						href="${pagingUrl}&page=${pageInfo.endPage + 1}">NEXT</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	<!-- pagination end -->
</div></div></div></div><!-- END PAGE -->
</div><!-- /.page-wrapper -->
</div><!-- /.wrapper -->

<%@include file="./includes/footer.jsp" %>
</body>

</html>