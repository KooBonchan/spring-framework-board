<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="modal" id="modal-login"><div class="modal-dialog"><div class="modal-content">
	<div class="modal-header">
		<h4 class="modal-title">Modal Heading</h4>
		<button type="button" class="close" data-dismiss="modal">&times;</button>
	</div>
  <form name="form-login">
	  <div class="modal-body" id="modal-login-body">
      <div class="form-group">
        <label for="name" class="col-form-label">Username:</label>
        <input type="text" class="form-control" name="username" autocomplete="username" required/>
      </div>
      <div class="form-group">
        <label for="message-text" class="col-form-label">Password:</label>
        <input type="password" class="form-control" name="password" required/>
      </div>
      <div class="alert alert-danger d-none" id="login-error-message"></div>
      <div class="alert alert-danger d-none" id="signup-error-message"></div>
    </div>
    <div class="modal-footer">
      <button type="submit" class="btn btn-primary">Log In</button>
      <button type="button" class="btn btn-secondary" id="modal-signup">Sign Up</button>
      <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    </div>
  </form>
</div></div></div>
<script>
const formLogin = document['form-login'];
const loginErrorMessage = document.getElementById("login-error-message");
const signupErrorMessage = document.getElementById("signup-error-message");
$(document).ready(function() {
  $("#btn-logout").click(()=>{
	  fetch("/api/auth/logout")
	  .then(response=>location.reload())
  });
  $("#btn-login").click(()=>{
    $("#modal-login").modal('show');
  })

  /* Login Handler */
  formLogin.addEventListener("submit", (e)=>{
    e.preventDefault();
    const data = {
      username: formLogin.username.value,
      password: formLogin.password.value,
    };
    formLogin.reset();
    fetch("/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "Application/json"
      },
      body: JSON.stringify(data),
    })
    .then(response => {
      if(response.ok){
    	  loginErrorMessage.innerText = "";
    	  loginErrorMessage.classList.add("d-none");
    	  location.reload();
      } else {
    	  loginErrorMessage.innerText = "Invalid username or password. You may sign up if you haven't registered.";
        loginErrorMessage.classList.remove("d-none");
      }
    })
  })
  
  /* Signup Handler */
  $("#modal-signup").click(()=>{
	  const data = {
      username: formLogin.username.value,
      password: formLogin.password.value,
    };
    formLogin.reset();
    if( ! validate(data)) return false;
    fetch("/api/auth", {
      method: "POST",
      headers: {
        "Content-Type": "Application/json"
      },
      body: JSON.stringify(data),
    })
    .then(response => {
      console.log(response);
      if(response.ok){
        signupErrorMessage.innerText = "";
        loginErrorMessage.innerText = "";
        location.reload();
      } else if(response.status == 409) {
        signupErrorMessage.innerText = "ID already exists.";
      } else {
        signupErrorMessage.innerText = "Bad Signup Request";
      }
    })
  })
});

const ID_REGEX = /^[a-zA-Z0-9가-힣_-]+$/
const PW_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+[\]{};':"\\|,.<>/?]).{8,20}$/
function validate(data){
	  return ID_REGEX.test(data.username) &&
	   	     PW_REGEX.test(data.password);
}
</script>