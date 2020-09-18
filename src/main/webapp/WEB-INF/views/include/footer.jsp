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
		  this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	});
	//only one (dot) . is allowed and numbers only
	jQuery('.floatOnly1').keyup(function() {
		  this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	});
	//only one (dot) . is allowed and numbers only and only two digits after dot
	jQuery('.floatOnly').keyup(function() {
		  this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		   var number = ($(this).val().split('.'));
		   if (number[1].length > 2)
		    {
		        var data = parseFloat($(this).val());
		        $(this).val(data.toFixed(2));
		    }
	});
	 $('.floatOnly').bind("cut copy paste",function(e) {
         e.preventDefault();
     });
	$("#salary").keyup(function(){
	    var number = ($(this).val().split('.'));
	    if (number[1].length > 2)
	    {
	        var salary = parseFloat($("#salary").val());
	        $("#salary").val( salary.toFixed(2));
	    }
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