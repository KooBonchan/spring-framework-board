<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<%@include file="../includes/navbar.jsp" %>
<h2>Write your document</h2>

  <form method="post" name="form-write" enctype="multipart/form-data">
  <ul>
    <li><input name="title" placeholder="Write Title" required /></li>
    <li><input name="writer" placeholder="Writer Name"
      autocomplete="username" required /></li>
    <li><textarea name="content"
        placeholder="Write your content" required></textarea></li>
    <li><input type="file" name="files" multiple
          accept="image/jpeg,
                  image/png,
                  image/gif,
                  image/webp,
                  image/svg+xml"></li>
    <li><button type="button" name="submit-button" onclick="verify()">SUBMIT</button></li>
  </ul>
  </form>
  
<%@include file="../includes/footer.jsp" %>
<script src="/resources/js/fileupload.js"></script>
<script>
function verify() {
  if(fileVerified) writeForm.submit();
}
</script>
</body>
</html>