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
	<c:url value="/getUserInfo" var="getUserInfo"></c:url>
	<c:url value="/getUserInfoByEmail" var="getUserInfoByEmail"/>	
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
								<span
									class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span>
								<!--  -->
								<span class="font-size-sm text-uppercase font-weight-semibold"><a
									class="card-title"
									href="${pageContext.request.contextPath}/showUsers"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">

								<form action="${pageContext.request.contextPath}/insertNewCategory"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${user.userId}" name="user_id" id="user_id">									
									
									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="catName">Category<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="100"
												autocomplete="off" onchange="trim(this)"
												value="${cate.catName}" name="catName" id="catName">
											<span class="validation-invalid-label" id="error_catName"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="prefix">Prefix<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)"
												value="${cat.prefix}" name="prefix" id="prefix">
											<span class="validation-invalid-label text-danger" id="error_prefix"
												style="display: none;">This field is required.</span> <span
												class="validation-invalid-label text-danger" id="unq_prefix"
												style="display: none;">This Prefix is Already
												Exist.</span>


										</div>

									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Description <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="100"
												autocomplete="off" onchange="trim(this)"
												value="${cat.catDesc}" name="address" id="address">
											<span class="validation-invalid-label text-danger"
												id="error_address" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Allow Copy <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="allowCopy"
													id="copy_y" ${cat.allowToCopy==1 ? 'checked' : ''}>
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0" name="allowCopy"
													id="copy_n" ${cat.allowToCopy==0 ? 'checked' : ''}>
													No
												</label>
											</div>
										</div>


										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="user"
													id="user_y" ${user.isActive==1 ? 'checked' : ''}>
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0" name="user"
													id="user_n" ${user.isActive==0 ? 'checked' : ''}>
													In-Active
												</label>
											</div>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Category Image <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${cat.imageName}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${cat.imageName}"> <span
												class="validation-invalid-label text-danger" id="error_doc"
												style="display: none;">This field is required.</span>
											</label>
										</div>
									</div>

									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
									</div>
								</form>
							</div>



						</div>
						<!-- /a legend -->
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
	<!-- /page content -->
	<script type="text/javascript">
		var loadFile = function(event) {
			try {
				var image = document.getElementById('output');
				image.src = URL.createObjectURL(event.target.files[0]);
			} catch (err) {
				console.log(err);
			}
		};
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function($) {

							$("#submitInsert")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#user_name").val()) {
													isError = true;
													$("#error_user_name")
															.show()
												} else {
													$("#error_user_name")
															.hide()
												}

												/* if (!$("#company").val()) {
													isError = true;
													$("#error_company").show()
												} else {
													$("#error_company").hide()
												}
 */
												if (!$("#mob_no").val()
														|| !validateMobile($(
																"#mob_no")
																.val())) {
													isError = true;
													$("#error_mob_no").show()
												} else {
													$("#error_mob_no").hide()
												}

												if (!$("#address").val()) {
													isError = true;
													$("#error_address").show()
												} else {
													$("#error_address").hide()
												}

												if (!$("#pass").val()) {
													isError = true;
													$("#error_pass").show()
												} else {
													$("#error_pass").hide()
												}

												if (!$("#department").val()) {
													isError = true;
													$("#error_department")
															.show()
												} else {
													$("#error_department")
															.hide()
												}

												if (!$("#reg_date").val()) {
													isError = true;
													$("#error_reg_date").show()
												} else {
													$("#error_reg_date").hide()
												}
												
												if (!$("#dob").val()) {
													isError = true;
													$("#error_dob").show()
												} else {
													$("#error_dob").hide()
												}

												if (!$("#user_type").val()) {
													isError = true;
													$("#error_user_type")
															.show()
												} else {
													$("#error_user_type")
															.hide()
												}

												if (!$("#email").val()
														|| !validateEmail($(
																"#email").val())) {
													isError = true;
													$("#error_email").show()
												} else {
													$("#error_email").hide()
												}

												if (!isError) {
													var x = true;
													if (x == true) {
														document
																.getElementById("submtbtn").disabled = true;
														return true;
													}
												}

												return false;

											});
						});

		$("#mob_no").change(function() { // 1st
			var mobNo = $("#mob_no").val();
			var userId = $("#user_id").val();
			//alert(code)

			$.getJSON('${getUserInfo}', {
				mobNo : mobNo,
				userId : userId,
				ajax : 'true',
			}, function(data) {

				if (data.error == false) {
					$("#unq_mob").show();
					$("#mob_no").val('');
					document.getElementById("mob_no").focus();
				} else {
					$("#unq_mob").hide();
				}
			});
		});
		
		$("#email").change(function() { 
			var email = $("#email").val();
			var userId = $("#user_id").val();
			//alert(code)

			$.getJSON('${getUserInfoByEmail}', {
				email : email,
				userId : userId,
				ajax : 'true',
			}, function(data) {

				if (data.error == false) {
					$("#unq_email").show();
					$("#email").val('');
					document.getElementById("email").focus();
				} else {
					$("#unq_email").hide();
				}
			});
		});
	</script>
	<script type="text/javascript">
	function trim(el) {
		el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
		replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
		replace(/\n +/, "\n"); // Removes spaces after newlines
		return;
	}
	
	$('.maxlength-options').maxlength({
		alwaysShow : true,
		threshold : 10,
		warningClass : 'text-success form-text',
		limitReachedClass : 'text-danger form-text',
		separator : ' of ',
		preText : 'You have ',
		postText : ' chars remaining.',
		validate : true
	});

	$('.maxlength-badge-position').maxlength({
		alwaysShow : true,
		placement : 'top'
	});

		function validateEmail(email) {
			var eml = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
			if (eml.test($.trim(email)) == false) {
				return false;
			}
			return true;
		}

		function validateMobile(mobile) {
			var mob = /^[1-9]{1}[0-9]{9}$/;
			if (mob.test($.trim(mobile)) == false) {
				return false;
			}
			return true;
		}

		$('.datepickerclass').daterangepicker({
			"autoUpdateInput" : true,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
		
		$('#mob_no').on('input', function() {
			 this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');
			});
	</script>


</body>
</html>