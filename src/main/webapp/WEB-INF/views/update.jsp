<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="./includes/header.jsp" %>
<title>Write your Document</title>
<meta name="csrf-token" content="${_csrf.token}" />
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
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/update.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/write.css" />">
</head>
<body>
<div class="wrapper">
<%@include file="./includes/navbar.jsp" %>
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
			<c:url var="baseUrl" value="/board/api/image/" />
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
      <button type="button" onclick="submitForm()"
        class="btn btn-primary mt-2">
        SUBMIT
      </button>
    </div>
	</form>
</div></div></div></div><!-- END PAGE -->
</div><!-- /.page-wrapper -->
</div><!-- /.wrapper -->
	
<%@include file="./includes/footer.jsp" %>
<script src="<c:url value="/resources/js/fileupload.js" />"></script>
<script src="<c:url value="/resources/js/update.js" />"></script>
<script>
function submitForm() {
  if( ! (fileVerified && verifyUpdateFileCounts())){ return false; }
  const writeForm = document['form-write'];
  if( writeForm.title.value.trim().length == 0) return false;
  if( writeForm.content.value.trim().length == 0) return false;
  
  let formData = new FormData();
  formData.append("title", writeForm.title.value);
  formData.append("writer", writeForm.writer.value);
  formData.append("content", writeForm.content.value);
  const files = writeForm.files.files;
  for(const file of files){
	  formData.append("files", file)
  }
  document.querySelectorAll(".image-wrapper").forEach((element) => {
    if(element.classList.contains("deleted")){
      formData.append("deletedFiles", element.dataset.imageIdx || '');
    }
  })
  
  let csrfToken = document.querySelector('meta[name="csrf-token"]').content;
  let headers = new Headers();
  headers.append('X-CSRF-TOKEN', csrfToken);
  const urlParams = new URLSearchParams(window.location.search);
  const idx = urlParams.get("idx");
  
  fetch('/board/api/'+idx, {
      method: 'PUT',
      body: formData,
      headers: headers
  })
  .then(response => {
    if(!response.ok) throw new Error();
    if(idx){
    	location.href='/board?idx='+idx;	
    } else{
    	location.href="/board"
    }
  })
  .catch(error => {
      console.error('Error:', error);
      alert("Unexpected error occurred while uploading your file");
  });
  
}
</script>
</body>
</html>