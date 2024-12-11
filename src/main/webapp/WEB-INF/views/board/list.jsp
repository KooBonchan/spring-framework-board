<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<%@include file="../includes/header.jsp" %>
<body>
<div class="wrapper">
	<%@include file="../includes/navbar.jsp" %>
	<div id="page-wrapper">
		<!-- PAGE-HEADER -->
		<div class="row"><div class="col-lg-12">
	    	<h2 class="page-header">LOGO</h2>
	    </div></div>
	    
	    <!-- PAGE-BODY -->
		<div class="row"><div class="col-lg-12"><div class="panel panel-default">
            <div class="panel-body">
            	<!-- PAGE SIZING -->
            	<c:url var="queryUrl" value="/board">
					<c:param name="category" value="${param.category}" />
				    <c:param name="query" value="${param.query}" />
				</c:url>
            	<div class="d-flex flex-row justify-content-between mb-3">
	            	<form class="form-inline justify-content" role="form" action="${queryUrl}" name="form-page-size">
						<div class="form-group">
							<label for="pageSize" class="mr-3">Page Size:</label>
							<div class="input-group">
							<select class="form-control" name="pageSize" onchange="document['form-page-size'].submit();">
								<option value="10" label="10" ${param.pageSize == "10" ? 'selected' : ''}>
								<option value="20" label="20" ${param.pageSize == "20" ? 'selected' : ''}>
								<option value="50" label="50" ${param.pageSize == "50" ? 'selected' : ''}>
								<option value="100" label="100" ${param.pageSize == "100" ? 'selected' : ''}>
							</select>
							</div>
						</div>
					</form>
					<button class="btn btn-primary" type="button" onclick="location.href='/board/write'">Write new document</button>
				<!-- BUTTONS-->
				
				</div>
				
				<!-- SEARCH QUERY -->
				<div class="mb-3">
					<c:url var="pageSizeUrl" value="/board">
						<c:param name="pageSize" value="${param.pageSize}" />
					</c:url>
					<form role="form" class="form-inline" action="${pageSizeUrl }" name="form-search">
						<div class="form-group">
							<select class="form-control" name="category">
								<option value="title" label="Title">
								<option value="writer" label="Writer" ${param.category == "writer" ? 'selected' : ''}>
								<option value="content" label="Content" ${param.category == "content" ? 'selected' : ''}>
							</select>
						</div>
						<div class="form-group mr-3"  style="flex-grow: 1;" >
							<input style="width:100%;" class="form-control" name="query" value='${param.query}'>
						</div>
						
						<div class="form-group">
							<button class="btn btn-default" type="submit" value="submit">
								<i class="fa fa-search"></i>
							</button>
						</div>
						<div class="form-group">
							<button class="btn btn-default" type="button" onclick='location.href="/board"'>RESET</button>
						</div>
					</form>
				</div>
				<!-- TABLE -->
            	<table class="table table-striped table-bordered table-hover">
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
						<tr onclick="location.href='/board?idx=${board.idx}'">
							<td>${board.idx}</td>
							<td>${board.title}</td>
							<td>${board.writer}</td>
							<td>${board.regDate}</td>
						</tr>
						</c:forEach>
                    </tbody>
                </table>
                
                <!-- table pagination -->
                <c:url var="pagingUrl" value="${queryUrl }">
					<c:param name="pageSize" value="${param.pageSize}" />
				</c:url>
				<div class='float-right'>
					<ul class='pagination'>
						<c:choose>
						<c:when test="${pageInfo.startPage == 1}">
							<li class="page-item disabled">
							<a class="page-link" href="javascript:void(0);" tabindex="-1">PREV</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="page-item">
							<a class="page-link" href="${pagingUrl}&page=${pageInfo.startPage - 1}">PREV</a>
							</li>
						</c:otherwise>
						</c:choose>
						
						<c:forEach var="page" begin="${pageInfo.startPage}" end="${pageInfo.endPage }">
					    	<li class="page-item ${pageInfo.page == page ? 'active' : '' }">
					    		<a class="page-link" href="${pagingUrl}&page=${page}">${page}</a>
				    		</li>
					    </c:forEach>
					    
					    <c:choose>
						<c:when test="${pageInfo.endPage == pageInfo.maxPage}">
							<li class="page-item disabled">
							<a class="page-link" href="javascript:void(0);" tabindex="-1">NEXT</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="page-item">
					    	<a class="page-link" href="${pagingUrl}&page=${pageInfo.endPage + 1}">NEXT</a>
					    	</li>
						</c:otherwise>
						</c:choose>
					</ul>
				</div><!-- pagination end -->
            </div><!-- END PANEL-BODY -->
        </div></div></div><!-- END PAGE -->
	</div><!-- /.page-wrapper -->
</div><!-- /.wrapper -->


<div class="modal" id="modal"><div class="modal-dialog"><div class="modal-content">
	<div class="modal-header">
		<h4 class="modal-title">Modal Heading</h4>
		<button type="button" class="close" data-dismiss="modal">&times;</button>
	</div>
	<div class="modal-body" id="modal-body"></div>
	<div class="modal-footer">
	<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	</div>
</div></div></div>

</body>
<%@include file="../includes/footer.jsp" %>
</html>