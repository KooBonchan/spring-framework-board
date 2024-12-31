<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="../includes/header.jsp" %>
<title>Write your Document</title>
<style>
input, textarea{
	width:200px;
	border: 1px solid #ccc;
}
textarea{
	height:150px;
	resize: none;
	font-family: inherit;
	margin-top:3px;
}
</style>
<link rel="stylesheet" type="text/css" href="/resources/style/update.css">
<link rel="stylesheet" type="text/css" href="/resources/style/write.css">
</head>
<body>
<div class="wrapper">
<%@include file="../includes/navbar.jsp" %>
<div id="page-wrapper">
<div class="row"><div class="col-lg-12"><div class="panel panel-default"><div class="panel-body">

	<h2>Document Edit</h2>
	<form action="/board/update" method="post" name="form-write"
    enctype="multipart/form-data">
    <input type="hidden" name="idx" value="${board.idx }" />
    <div class="form-group">
      <label for="title">Title</label>
      <input name="title" placeholder="Write Title"
        value=<c:out value="${board.title }"/>
        class="form-control" required />
    </div>
    <div class="form-group">
      <label>Writer</label>
      <input
        value=<c:out value="${board.writer}" />
        disabled />
      <input name="writer" type="hidden" value=<c:out value="${board.writer}" /> />
    </div>
    <div class="form-group">
      <label for="content">Content</label>
      <textarea name="content" id="content"
        class="form-control" rows="5" 
        required><c:out value="${board.content }"/></textarea>
    </div>
    
  	<div>Click images to delete</div>
  	<div class="image-container">
			<c:url var="baseUrl" value="/api/image/" />
			<c:forEach items="${board.images }" var="image">
			  <c:url var="pathUrl" value="${baseUrl}thumbnail/${image.filePath}/${image.realFileName}" />			  
			  <div class="image-wrapper" style="display:inline-block;"
	         data-image-idx=<c:out value="${image.idx}" />
	       >
			    <img class="thumbnail" alt="attached images" src="${pathUrl}" width="100px">
			  </div>
			</c:forEach>
		</div>
		
		<input type="file" name="files" multiple
         class="form-control-file mt-2"
         accept="image/jpeg,
                 image/png,
                 image/gif,
                 image/webp,
                 image/svg+xml">
    <div class="d-flex justify-content-end">
      <button type="button" onclick="verify()"
        class="btn btn-primary mt-2">
        SUBMIT
      </button>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</div></div></div></div><!-- END PAGE -->
</div><!-- /.page-wrapper -->
</div><!-- /.wrapper -->
	
<%@include file="../includes/footer.jsp" %>
<script src="/resources/js/fileupload.js"></script>
<script src="/resources/js/update.js"></script>
<script>
function verify() {
  if(fileVerified && verifyUpdateFileCounts()){
	  appendImageUpdates();
	  writeForm.submit();
  }
}
</script>
</body>
</html>