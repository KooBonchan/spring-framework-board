<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
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
</head>
<body>
<div class="wrapper">
<%@include file="./includes/navbar.jsp" %>
<div id="page-wrapper">
<div class="row"><div class="col-lg-12"><div class="panel panel-default"><div class="panel-body">
  <h2>Write New Document</h2>
  <form action="/board" method="post" name="form-write" enctype="multipart/form-data">
  	<div class="form-group">
	    <label for="title">Title</label>
	    <input name="title" id="title" placeholder="Write Title"
        class="form-control" required />
	  </div>
	  <div class="form-group">
      <label for="writer">Writer</label>
		  <input name="writer" id="writer" tabindex="-1"
		    class="form-control"
		    value='<sec:authentication property="principal.username"/>'
		    readonly />
	  </div>
  
    <div class="form-group">
		  <label for="content">Content</label>
		  <textarea name="content" id="content" 
		    class="form-control" rows="5" required></textarea>
		</div>
    <input type="file" name="files" multiple
          class="form-control-file"
          accept="image/jpeg,
                  image/png,
                  image/gif,
                  image/webp,
                  image/svg+xml">
    <div class="d-flex justify-content-end">
	    <button type="button" name="submit-button" onclick="submitForm()"
	      class="btn btn-primary mt-2">
	      SUBMIT
	    </button>
    </div>
  </form>

</div></div></div></div>
</div><!-- /.page-wrapper -->
</div><!-- /.wrapper -->
  
<%@include file="./includes/footer.jsp" %>
<script src="/board/resources/js/fileupload.js"></script>
<script>
function submitForm() {
  if( ! fileVerified) return false;
  const writeForm = document['form-write'];
  if( writeForm.title.value.trim().length == 0) return false;
  if( writeForm.content.value.trim().length == 0) return false;
  
  
  let formData = new FormData();
  formData.append("title", writeForm.title.value);
  formData.append("content", writeForm.content.value);
  const files = writeForm.files.files;
  for(const file of files){
	  formData.append("files", file)
  }
  
  let csrfToken = document.querySelector('meta[name="csrf-token"]').content;
  let headers = new Headers();
  headers.append('X-CSRF-TOKEN', csrfToken);
  
  fetch('/board/api', {
	    method: 'POST',
	    body: formData,
	    headers: headers
	})
	.then(response => {
		if(!response.ok) throw new Error();
		return response.text();
	})
	.then(page => {location.href='/board?idx='+page})
	.catch(error => {
	    console.error('Error:', error);
	    alert("Unexpected error occurred while uploading your file");
	});
}
</script>
</body>
</html>