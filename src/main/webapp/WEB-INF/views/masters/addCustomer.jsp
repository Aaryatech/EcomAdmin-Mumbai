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
	<c:url value="/getCustInfo" var="getCustInfo"></c:url>
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
									href="${pageContext.request.contextPath}/showCustomers"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertNewCustomer"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${cust.custId}" name="cust_id" id="cust_id">


									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="custName">Customer Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${cust.custName}" name="custName" id="custName">
											<span class="validation-invalid-label" id="error_custName"
												style="display: none;">This field is required.</span>
										</div>




										<label class="col-form-label font-weight-bold col-lg-2"
											for="doc">Profile Image <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${cust.profilePic}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${cust.profilePic}"> <span
												class="validation-invalid-label text-danger" id="error_doc"
												style="display: none;">This field is required.</span>
											</label>
										</div>
									</div>



									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="custMobileNo">Mobile No.<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${cust.custMobileNo}" name="custMobileNo" id="custMobileNo">
											<span class="validation-invalid-label text-danger"
												id="error_custMobileNo" style="display: none;">This field
												is required.</span> <span
												class="validation-invalid-label text-danger" id="unq_mob"
												style="display: none;">This Mobile No. is Already
												Exist.</span>


										</div>



										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Email <span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${cust.emailId}" name="email" id="email"> <span
												class="validation-invalid-label text-danger"
												id="error_email" style="display: none;">This field is
												required.</span>
										</div>

									</div>



									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="dateOfBirth">Date Of Birth<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass"
												autocomplete="off" value="${cust.dateOfBirth}" name="dateOfBirth"
												id="dateOfBirth"> <span
												class="validation-invalid-label text-danger" id="error_dateOfBirth"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="city">City<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="city" id="city" data-placholder="Select City">
												<option value="0"></option>
												<option value="1" ${cust.cityId==1 ? 'selected' : '' }>Nasik</option>
												<option value="2" ${cust.cityId==2 ? 'selected' : '' }>Mumbai</option>
												<option value="3" ${cust.cityId==3 ? 'selected' : '' }>Pune</option>
												<%-- <c:forEach items="${desigList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.designationId==cust.designationId}">
															<option selected value="${list.designationId}">${list.designation}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.designationId}">${list.designation}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach> --%>
											</select> <span class="validation-invalid-label text-danger"
												id="error_city" style="display: none;">This field is
												required.</span>
										</div>
									</div>

								<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="custGender">Gender<span class="text-danger"> </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="custGender" id="custGender" data-placholder="Select Gender">
												<option value="0"></option>
												<option value="1" ${cust.custGender==1 ? 'selected' : '' }>Male</option>
												<option value="2" ${cust.custGender==2 ? 'selected' : '' }>Female</option>
												<option value="3" ${cust.custGender==3 ? 'selected' : '' }>Transgender</option>
												<%-- <c:forEach items="${desigList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.designationId==cust.designationId}">
															<option selected value="${list.designationId}">${list.designation}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.designationId}">${list.designation}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach> --%>
											</select> <span class="validation-invalid-label text-danger"
												id="error_custGender" style="display: none;">This field is
												required.</span>
										</div>
										<label class="col-form-label font-weight-bold col-lg-2"
											for="ageRange">Age Range<span class="text-danger"> </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="ageRange" id="ageRange" data-placholder="Select Age Range">
												<option value="0"></option>
												<option value="1" ${cust.ageRange==1 ? 'selected' : '' }>20-30</option>
												<option value="2" ${cust.ageRange==2 ? 'selected' : '' }>30-40</option>
												<option value="3" ${cust.ageRange==3 ? 'selected' : '' }>40-50</option>
												<%-- <c:forEach items="${desigList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.designationId==cust.designationId}">
															<option selected value="${list.designationId}">${list.designation}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.designationId}">${list.designation}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach> --%>
											</select> <span class="validation-invalid-label text-danger"
												id="error_ageRange" style="display: none;">This field is
												required.</span>
										</div>
									</div>

								<div class="form-group row">
									<%-- 	<label class="col-form-label font-weight-bold col-lg-2"
											for="companyId">Company<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="companyId" id="companyId" data-placholder="Select Company">
												 
												<c:forEach items="${compList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.companyId==cust.companyId}">
															<option selected value="${list.companyId}">${list.companyName}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.companyId}">${list.companyName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_companyId" style="display: none;">This field is
												required.</span>
										</div> --%>
										<label class="col-form-label font-weight-bold col-lg-2"
											for="languageId">Language<span class="text-danger"> </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="languageId" id="languageId" data-placholder="Select Age Range">
												<option value="0"></option>
												<option value="1" ${cust.languageId==1 ? 'selected' : '' }>Marathi</option>
												<option value="2" ${cust.languageId==2 ? 'selected' : '' }>English</option>
												<option value="3" ${cust.languageId==3 ? 'selected' : '' }>Hindi</option>
												<%-- <c:forEach items="${desigList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.designationId==cust.designationId}">
															<option selected value="${list.designationId}">${list.designation}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.designationId}">${list.designation}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach> --%>
											</select> <span class="validation-invalid-label text-danger"
												id="error_languageId" style="display: none;">This field is
												required.</span>
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
												
 
												if (!$("#custName").val()) {
													isError = true;
													$("#error_custName")
															.show()
												} else {
													$("#error_custName")
															.hide()
												}
 
												if (!$("#custMobileNo").val()
														|| !validateMobile($(
																"#custMobileNo")
																.val())) {
													isError = true;
													$("#error_custMobileNo").show()
												} else {
													$("#error_custMobileNo").hide()
												}

												if (!$("#dateOfBirth").val()) {
													isError = true;
													$("#error_dateOfBirth").show()
												} else {
													$("#error_dateOfBirth").hide()
												}

											  

												if (!$("#city").val() || $("#city").val()==0) {
													isError = true;
													$("#error_city").show()
												} else {
													$("#error_city").hide()
												}
 
/* 
												if (!$("#companyId").val()) {
													isError = true;
													$("#error_companyId").show()
												} else {
													$("#error_companyId").hide()
												}
  */
 
												if ( $("#email").val().length != 0) {

												if (!$("#email").val()
														|| !validateEmail($(
																"#email").val())) {
													isError = true;
													$("#error_email").show()
												} else {
													$("#error_email").hide()
												}
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

		$("#custMobileNo").change(function() {
			var mobNo = $("#custMobileNo").val();
			var userId = $("#cust_id").val();
			
		 
			//alert(mobNo)

			$.getJSON('${getCustInfo}', {
				mobNo : mobNo,
				userId : userId,
				ajax : 'true',
			}, function(data) {

				if (data.error == false) {
					$("#unq_mob").show();
					$("#custMobileNo").val('');
					document.getElementById("custMobileNo").focus();
				} else {
					$("#unq_mob").hide();
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

		$('#mob_no').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9]/g, '').replace(
							/(\..*)\./g, '$1');
				});
	</script>


</body>
</html>