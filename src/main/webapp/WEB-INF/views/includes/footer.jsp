<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Bootstrap insert -->
<!-- <script src="/resources/vendor/jquery/jquery.min.js"></script> -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/metismenu/3.0.7/metisMenu.min.js"></script>
<script src="/resources/dist/js/sb-admin-2.js"></script>
<script>
const urlParams = new URLSearchParams(window.location.search);
const message = urlParams.get('message');
$(document).ready(function() {
    $('.sidebar-nav')
    	.attr("class", "sidebar-nav navbar-collapse collapse")
    	.attr("area-expanded", 'false')
    	.attr("style", 'height:1px;');
    
    if(message != null && message.length > 0){
    	$("#modal-body").html(message);
    	$("#modal").modal('show');
	}
});
</script>
<!-- ------------- -->