<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<div class="modal" id="modal-message"><div class="modal-dialog"><div class="modal-content">
	<div class="modal-header">
		<h4 class="modal-title">Message</h4>
		<button type="button" class="close" data-dismiss="modal">&times;</button>
	</div>
	<div class="modal-body" id="modal-message-body"></div>
	<div class="modal-footer">
	<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	</div>
</div></div></div>
<script>
const urlParams = new URLSearchParams(window.location.search);
const message = urlParams.get('message');
$(document).ready(function() {
    if(message != null && message.length > 0){
    	$("#modal-message-body").html(message);
    	$("#modal-message").modal('show');
	}
});
</script>