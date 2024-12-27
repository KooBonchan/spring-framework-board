<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags"  prefix="sec"%>
<html>
<head>
	<!-- Metadata -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="/resources/style/theme.css"/>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
	
	<title>Login</title>
</head>

<body>
<div class="container mt-3">
  <form role="form" method="post" action="/login" name="form-login">
    <div class="form-group">
      <label for="username">ID</label>
      <input name="username" id="username"
        class="form-control"
        autocomplete="username"
        autofocus required/>
      <small
        class="invalid-feedback">
        Your personal information is our product.
      </small>
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" name="password" id="password"
        class="form-control"
        required/>
    </div>
    <div>
      <input type="checkbox" name="remember-me" id="remember-me"/>
      <label for="remember-me" class="form-check-label">
        I re member You
       </label>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="button" id="btn-login"
      class="btn btn-primary mt-3">
      Login
    </button>
  </form>
</div>
<script>
window.onload = () => {
	const formLogin = document['form-login'];
	document.getElementById("btn-login").addEventListener("click", (e) => {
		e.preventDefault();
		formLogin.submit();
	})
}
</script>
</body>
</html>
