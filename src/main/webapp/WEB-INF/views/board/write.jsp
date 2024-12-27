<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
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
</head>
<body>
<div class="wrapper">
<%@include file="../includes/navbar.jsp" %>
<div id="page-wrapper">
<div class="row"><div class="col-lg-12"><div class="panel panel-default"><div class="panel-body">
  <h2>Write New Document</h2>
  <form method="post" name="form-write" enctype="multipart/form-data">
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
	    <button type="button" name="submit-button" onclick="verify()"
	      class="btn btn-primary mt-2">
	      SUBMIT
	    </button>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  </form>

</div></div></div></div>
</div><!-- /.page-wrapper -->
</div><!-- /.wrapper -->
  
<%@include file="../includes/footer.jsp" %>
<script src="/resources/js/fileupload.js"></script>
<script>
function verify() {
  if(fileVerified) writeForm.submit();
}
</script>
</body>
</html>