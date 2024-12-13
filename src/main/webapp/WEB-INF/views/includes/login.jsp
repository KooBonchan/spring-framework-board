<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<div class="modal" id="modal-login"><div class="modal-dialog"><div class="modal-content">
	<div class="modal-header">
		<h4 class="modal-title">Modal Heading</h4>
		<button type="button" class="close" data-dismiss="modal-login">&times;</button>
	</div>
	<div class="modal-body" id="modal-login-body">
	
	
	</div>
	<div class="modal-footer">
	<button type="button" class="btn btn-danger" data-dismiss="modal-login">Close</button>
	</div>
</div></div></div>
<script>
$(document).ready(function() {
	$("#modal-login").modal('show');
});
</script>