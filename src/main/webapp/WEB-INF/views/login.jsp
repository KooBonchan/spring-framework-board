<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags"  prefix="sec"%>
<html>
<head>
	<!-- Metadata -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="/board/resources/style/theme.css"/>
	
	<title>Login</title>
</head>

<body>
<div class="container mt-3">
  <form role="form" method="post" action="/board/login"
    name="form-login" novalidate class="needs-validation">
    <div class="form-group">
      <label for="username">ID</label>
      <input name="username" id="username"
        class="form-control"
        autocomplete="username"
        pattern="^[a-zA-Z0-9가-힣_-]{3,}$"
        autofocus/>
      <small class="invalid-feedback">
        
      </small>
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" name="password" id="password"
        class="form-control"
        pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&+#^=~_|:;,])[A-Za-z\d@$!%*?&+#^=~_|:;,]{8,20}$"/>
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
    <button type="button" id="btn-signup"
      class="btn btn-secondary mt-3">
      Sign Up
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
	document.getElementById("btn-signup")?.addEventListener("click", (e) => {
	  e.preventDefault();
	  formLogin.setAttribute("action", "signup");
	  if (formLogin.checkValidity()) {
	    formLogin.submit();
	    return;
	  }
	  formLogin.classList.add('was-validated');
	})
}
</script>
</body>
</html>
