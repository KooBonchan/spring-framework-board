<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!-- Bootstrap insert -->
<!-- Navigation -->
<nav class="navbar navbar-light bg-light" role="navigation" style="margin-bottom: 0">
	<a class="navbar-brand" href="/board"><strong>LOGO</strong></a>
	<sec:authorize access="isAnonymous()">
		<button type="button" class="btn btn-primary mt-2"
		  onclick="location.href='/board/login'">
      Log In
		</button>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
	<form action="/board/logout" method="post">
    <button class="btn mt-2">
      Logout
    </button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
  </sec:authorize>
</nav>
<!-- ------------- -->