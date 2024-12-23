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
  
<script>
// TODO: Filetype Restriction, File count restriction, File size restriction
// if restricted not submit, no alert - error message below input
const form = document['form-write'];
const files = form.files;
const allowedTypes = 'image/jpeg,image/png,image/gif,image/webp,image/svg+xml'.split(',')
let fileVerified = true;
form.files.addEventListener('change',function (e) {
  if(files.length > 5){
    alert('You can upload up to 5 files');
    files.value = '';
    fileVerified = false;
    return;
  }
  for(const file of files){
    if(file.size > 10 * 1024 * 1024) {
      alert('File "' + file.name + '" is too large. You can upload file with size up to 10MB');
      files.value = '';
      fileVerified = false;
      return;
    }
    else if ( ! file.type in allowedTypes){
      alert('File "' + file.name + '" has not allowed format. Only JPG, PNG, GIF, WEBP, SVG are allowed');
      files.value = '';
      fileVerified = false;
      return;
    }
  }
  console.log(files);
});
function verify() {
	if(fileVerified) form.submit();
}
</script>
<%@include file="../includes/footer.jsp" %>
</body>
</html>