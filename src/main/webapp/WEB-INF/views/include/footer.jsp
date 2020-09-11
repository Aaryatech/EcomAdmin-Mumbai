<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="navbar navbar-expand-lg navbar-light">
	

	<div class="navbar-collapse1 collapse1 text-center w-100"
		id="navbar-footer1">
		<span class="navbar-text"> &copy; 2019 - 2022. Madhvi</span>

	
	</div>
</div>

<script>
	jQuery('.numbersOnly').keyup(function() {
		this.value = this.value.replace(/[^0-9\.]/g, '');
	});
	jQuery('.alphaonly').keyup(function() {
		this.value = this.value.replace(/[^a-zA-Z\s]+$/, '');
	});
	jQuery('.alhanumeric').keyup(function() {
		this.value = this.value.replace(/[^a-zA-Z0-9\-\s]+$/, '');
	});
	jQuery('.dob').keyup(function() {
		this.value = this.value.replace(/[^a-zA-Z0-9\-\s]+$/, '');
	});
</script>