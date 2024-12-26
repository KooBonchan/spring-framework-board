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
</head>
<body>
<%@include file="../includes/navbar.jsp" %>
	<h2>Write your document</h2>

	<form action="/board/update" method="post" name="form-write"
	 enctype="multipart/form-data">
	<input type="hidden" name="idx" value="${board.idx }" />
	<ul>
		<li>
		  <input name="title" placeholder="Write Title"
		    value=<c:out value="${board.title }"/>
		    required />
    </li>
		<li>
		  <input
		    value=<c:out value="${board.writer}" />
		    disabled />
    </li>
		<li><textarea name="content"
				placeholder="Write your content"
				required><c:out value="${board.content }"/></textarea>
		</li>
		<li>Click images to delete</li>		
		<li>
			<c:url var="baseUrl" value="/api/image/" />
			<c:forEach items="${board.images }" var="image">
			  <c:url var="pathUrl" value="${baseUrl}thumbnail/${image.filePath}/${image.realFileName}" />			  
			  <div class="image-container" style="display:inline-block;"
          data-image-idx=<c:out value="${image.idx}" />
        >
			    <img class="thumbnail" alt="attached images" src="${pathUrl}" width="100px">
			  </div>
			</c:forEach>
		</li>
		<li><input type="file" name="files" multiple
          accept="image/jpeg,
                  image/png,
                  image/gif,
                  image/webp,
                  image/svg+xml"></li>
		<li><input type="button" value="submit" onclick="verify()"></li>
	</ul>
	</form>
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