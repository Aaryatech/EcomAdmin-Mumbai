<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
.daterangepicker {
	width: 100%;
}

.daterangepicker.show-calendar .calendar {
	display: inline--block;
}

.daterangepicker .calendar, .daterangepicker .ranges {
	float: right;
}

.select2-selection--multiple .select2-selection__rendered {
	border-bottom: 1px solid #ddd;
}
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">
<c:url value="/getLangInfoByCode" var="getLangInfoByCode"></c:url>
	<!-- Main navbar -->
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<!-- /main navbar -->

	<!-- Page content -->
	<div class="page-content">

		<!-- Main sidebar -->
		<jsp:include page="/WEB-INF/views/include/left.jsp"></jsp:include>
		<!-- /main sidebar -->


		<!-- Main content -->
		<div class="content-wrapper">

			<!-- Page header -->
			<div class="page-header page-header-light"></div>
			<!-- /page header -->



			<!-- Content area -->
			<div class="content">


				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">


						<div class="card">

							<div
								class="card-header bg-blue text-white d-flex justify-content-between">
								<span class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span> 
								<!--  -->
								<%-- <span
									class="font-size-sm text-uppercase font-weight-semibold"><a class="card-title"
									href="${pageContext.request.contextPath}/showLanguage"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span> --%>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/renewPassword"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control" name="userId" id="userId" value="${userId}">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="new_password">New Password<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="password" class="form-control maxlength-badge-position" name="new_password"
												id="new_password" maxlength="15" autocomplete="off" onchange="trim(this)"> 
												
										<span
												class="validation-invalid-label text-danger" id="error_new_pass"
												style="display: none;">This field is required.</span>	
												
										<span
												class="validation-invalid-label text-danger" id="error_pass"
												style="display: none;">Passwords not matched.</span>	
																						
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="confirm_pass">Confirm Password<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="password" class="form-control maxlength-badge-position" name="confirm_pass"
												id="confirm_pass" maxlength="15" autocomplete="off" onchange="trim(this)"> 
										<span
												class="validation-invalid-label text-danger" id="error_confirm_pass"
												style="display: none;">This field is required.</span>		
										</div>
									</div>
									
									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="submtbtn">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

			</div>
			<!-- /content area -->


			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/views/include/cmn.js"></script> --%>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/scrolltable.js"></script>
	<!-- /page content -->
	
	<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#new_password").val()) {
					isError = true;
					$("#error_new_pass").show()
				} else {
					$("#error_new_pass").hide()
				}

				if (!$("#confirm_pass").val()) {
					isError = true;
					$("#error_confirm_pass").show()
				} else {
					$("#error_confirm_pass").hide()
				}				
				
				if($("#new_password").val() != $("#confirm_pass").val()){
					isError = true;
					$("#error_pass").show()	
				}else {
					$("#error_pass").hide()
				}

				if (!isError) {
					var x = true;
					if (x == true) {
						document.getElementById("submtbtn").disabled = true;
						return true;
					}
				}

				return false;

			});
		});
		
		$("#language_code").change(function(){   // 1st
			var code = $("#language_code").val();								
			var langId = $("#lang_id").val();	
		//alert(code)
		
			 $.getJSON('${getLangInfoByCode}', {
				code : code,
				langId : langId,
				ajax : 'true',
			}, function(data) {
				
				if(data.error==false){								
					$("#unq_language_code").show();
					$("#language_code").val('');
					document.getElementById("language_code").focus();
				}else{
					$("#unq_language_code").hide();
				}
			});
		});
		
		$("#data_view_button")
		.click(
				function() {
					//blockThis();
					
					$.getJSON('${getSingleCategory}', {
						ajax : 'true',
					}, function(data) {
						//document.getElementById("g_name").innerHTML=" Category Name:" +data.catName;
						//document.getElementById("ifsc_code").innerHTML=" Bank Name: Bank of India";
						document.getElementById("mod_title").innerHTML="Appending Ajax Return Data ";
						document.getElementById("f_name").value=data.catName;
						document.getElementById("l_name").value="Thakur";	
						//unBlock();
					});
				});
	</script>
<script>
function trim(el) {
	el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
	replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
	replace(/\n +/, "\n"); // Removes spaces after newlines

	return;
}

$('.maxlength-options').maxlength({
    alwaysShow: true,
    threshold: 10,
    warningClass: 'text-success form-text',
    limitReachedClass: 'text-danger form-text',
    separator: ' of ',
    preText: 'You have ',
    postText: ' chars remaining.',
    validate: true
});

$('.maxlength-badge-position').maxlength({
    alwaysShow: true,
    placement: 'top'
});
</script>


</body>
</html>