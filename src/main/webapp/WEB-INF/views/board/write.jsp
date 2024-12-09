<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
	<h2>Write your document</h2>

	<form method="post" name="form-write">
	<ul>
		<li><input name="title" placeholder="Write Title" required /></li>
		<li><input name="writer" placeholder="Writer Name"
			autocomplete="username" required /></li>
		<li><textarea name="content"
				placeholder="Write your content" required></textarea></li>
		<li><input type="submit" value="submit"></li>
	</ul>
	</form>
</body>
</html>