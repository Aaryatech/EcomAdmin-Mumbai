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
	<c:url value="/getUserInfoByEmail" var="getUserInfoByEmail" />
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
									href="${pageContext.request.contextPath}/showCompanys"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertNewCompany"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${comp.companyId}" name="companyId" id="companyId">

									<label class="col-form-label font-weight-bold" for="comp_name">Company
										Basic Info: </label>

									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="comp_name">Company Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.companyName}" name="comp_name" id="comp_name">
											<span class="validation-invalid-label" id="error_comp_name"
												style="display: none;">This field is required.</span>
										</div>


										<label class="col-form-label font-weight-bold col-lg-2"
											for="doc">Profile Image <span class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${comp.companyLogo}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${comp.companyLogo}"> <span
												class="validation-invalid-label text-danger" id="error_doc"
												style="display: none;">This field is required.</span>
											</label>
										</div>

									</div>


									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="contact_no">Contact No.<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${comp.compContactNo}" name="contact_no"
												id="contact_no"> <span
												class="validation-invalid-label text-danger"
												id="error_contact_no" style="display: none;">This
												field is required.</span>


										</div>



										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Email <span class="text-danger"> </span>:
										</label>
										<div class="col-lg-4">
											<input type="email"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.compEmailAddress}" name="email" id="email">
											<span class="validation-invalid-label text-danger"
												id="error_email" style="display: none;">This field is
												required.</span>
										</div>

									</div>

									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="openDate">Opening Date<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass"
												autocomplete="off" value="${comp.compOpeningDate}"
												name="openDate" id="openDate"> <span
												class="validation-invalid-label text-danger"
												id="error_openDate" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Address <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<textarea class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												name="address" id="address">${comp.compAddress}</textarea>
											<span class="validation-invalid-label text-danger"
												id="error_address" style="display: none;">This field
												is required.</span>
										</div>


									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="city">City<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="city" id="city" data-placholder="Select City">
												<option value=""></option>
												<option value="1" ${comp.compCity==1 ? 'selected' : '' }>Nasik</option>
												<option value="2" ${comp.compCity==2 ? 'selected' : '' }>Mumbai</option>
												<option value="3" ${comp.compCity==3 ? 'selected' : '' }>Pune</option>
												<%-- <c:forEach items="${desigList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.designationId==comp.designationId}">
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


										<label class="col-form-label font-weight-bold col-lg-2"
											for="state">State <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.compState}" name="state" id="state"> <span
												class="validation-invalid-label" id="error_state"
												style="display: none;">This field is required.</span>
										</div>


									</div>


									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="website">Website <span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${comp.compWebsite}" name="website" id="website">
											<span class="validation-invalid-label text-danger"
												id="error_website" style="display: none;">This field
												is required.</span>
										</div>


										<label class="col-form-label font-weight-bold col-lg-2"
											for="companyPrefix">Company Prefix<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${comp.companyPrefix}" name="companyPrefix"
												id="companyPrefix"> <span
												class="validation-invalid-label text-danger"
												id="error_companyPrefix" style="display: none;">This
												field is required.</span>
										</div>

									</div>
									<!--  ************************************-->

									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="compGstType">Company GST Type<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="compGstType" id="compGstType"
												data-placholder="Select Department">
												<option value=""></option>
												<option value="1" ${comp.compGstType==1 ? 'selected' : '' }>CGST</option>
												<option value="2" ${comp.compGstType==2 ? 'selected' : '' }>IGST</option>
												<option value="3" ${comp.compGstType==3 ? 'selected' : '' }>SGSt</option>
												<%-- <c:forEach items="${desigList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.designationId==comp.designationId}">
															<option selected value="${list.designationId}">${list.designation}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.designationId}">${list.designation}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach> --%>
											</select> <span class="validation-invalid-label text-danger"
												id="error_compGstType" style="display: none;">This
												field is required.</span>
										</div>
										<label class="col-form-label font-weight-bold col-lg-2"
											for="gstNo">GST No. <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${comp.compGstNo}" name="gstNo" id="gstNo">
											<!-- <span
												class="validation-invalid-label text-danger"
												id="error_gstNo" style="display: none;">This field is
												required.</span> -->
										</div>



									</div>


									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="gstCode">GST Code <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.compStateGstCode}" name="gstCode" id="gstCode">
											<span class="validation-invalid-label text-danger"
												id="error_gstCode" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									<label class="col-form-label font-weight-bold" for="comp_name">Company
										Bank Details: </label>

									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="bankName">Bank Name <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.compBankName}" name="bankName" id="bankName">
											<span class="validation-invalid-label text-danger"
												id="error_bankName" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="branchName">Bank Branch Name <span
											class="text-danger"> </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${comp.compBankBranchName}" name="branchName"
												id="branchName"> <span
												class="validation-invalid-label text-danger"
												id="error_branchName" style="display: none;">This
												field is required.</span>
										</div>


									</div>



									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="ifscCode">IFSC Code<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${comp.compBankIfsc}" name="ifscCode" id="ifscCode">
											<span class="validation-invalid-label text-danger"
												id="error_ifscCode" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="accNo">Bank Account Number <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.compBankAccNo}" name="accNo" id="accNo">
											<span class="validation-invalid-label text-danger"
												id="error_accNo" style="display: none;">This field is
												required.</span>
										</div>


									</div>
									<label class="col-form-label font-weight-bold" for="comp_name">Company
										Other Details: </label>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cinNo">CIN No.<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${comp.compCinNo}" name="cinNo" id="cinNo"> <span
												class="validation-invalid-label text-danger"
												id="error_cinNo" style="display: none;">This field is
												required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="fdaNo">FDA No. <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.compFdaNo}" name="fdaNo" id="fdaNo"> <span
												class="validation-invalid-label text-danger"
												id="error_fdaNo" style="display: none;">This field is
												required.</span>
										</div>

									</div>

									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="fdaDelc">FDA Declaration<span
											class="text-danger"> </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${comp.compFdaDeclarText}" name="fdaDelc"
												id="fdaDelc"> <span
												class="validation-invalid-label text-danger"
												id="error_fdaDelc" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="panNo">PAN No. <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.compPanNo}" name="panNo" id="panNo"> <span
												class="validation-invalid-label text-danger"
												id="error_panNo" style="display: none;">This field is
												required.</span>
										</div>


									</div>



									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="paymentGatewayApplicable">Payment Gateway
											Applicable <span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="paymentGatewayApplicable" id="app_y"
													${comp.paymentGatewayApplicable ==1 ? 'checked' : ''}>
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="paymentGatewayApplicable" id="app_n"
													${comp.paymentGatewayApplicable==0 ? 'checked' : ''}>
													No
												</label>
											</div>
										</div>
										<label class="col-form-label font-weight-bold col-lg-2"
											for="paymentGatewayLink">Payment Gateway Link <span
											class="text-danger"> </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${comp.paymentGatewayLink}" name="paymentGatewayLink"
												id="paymentGatewayLink"> <span
												class="validation-invalid-label text-danger"
												id="error_paymentGatewayLink" style="display: none;">This
												field is required.</span>
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
	<!-- <script type="text/javascript">
		var loadFile = function(event) {
			try {
				var image = document.getElementById('output');
				image.src = URL.createObjectURL(event.target.files[0]);
			} catch (err) {
				console.log(err);
			}
		};
	</script>
 -->
	<script type="text/javascript">
		$(document)
				.ready(
						function($) {

							$("#submitInsert")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#comp_name").val()) {
													isError = true;
													$("#error_comp_name")
															.show()
												} else {
													$("#error_comp_name")
															.hide()
												}

												 
												if ( $("#contact_no").val().length != 0) {

													if (!$("#contact_no").val()
															|| !validateMobile($(
																	"#contact_no")
																	.val())) {
														isError = true;
														$("#error_contact_no")
																.show()
													} else {
														$("#error_contact_no")
																.hide()
													}
												}
												if ( $("#email").val().length != 0) {

													if (!$("#email").val()
															|| !validateEmail($(
																	"#email")
																	.val())) {
														isError = true;
														$("#error_email")
																.show()
													} else {
														$("#error_email")
																.hide()
													}

												}

												if (!$("#address").val()) {
													isError = true;
													$("#error_address").show()
												} else {
													$("#error_address").hide()
												}

												if (!$("#openDate").val()) {
													isError = true;
													$("#error_openDate").show()
												} else {
													$("#error_openDate").hide()
												}
												if (!$("#city").val()) {
													isError = true;
													$("#error_city").show()
												} else {
													$("#error_city").hide()
												}

												if (!$("#state").val()) {
													isError = true;
													$("#error_state").show()
												} else {
													$("#error_state").hide()
												}

												if (!$("#website").val()) {
													isError = true;
													$("#error_website").show()
												} else {
													$("#error_website").hide()
												}

												if (!$("#companyPrefix").val()) {
													isError = true;
													$("#error_companyPrefix")
															.show()
												} else {
													$("#error_companyPrefix")
															.hide()
												}

												if (!$("#compGstType").val()) {
													isError = true;
													$("#error_compGstType")
															.show()
												} else {
													$("#error_compGstType")
															.hide()
												}

												if (!$("#gstCode").val()) {
													isError = true;
													$("#error_gstCode").show()
												} else {
													$("#error_gstCode").hide()
												}

												if (!$("#bankName").val()) {
													isError = true;
													$("#error_bankName").show()
												} else {
													$("#error_bankName").hide()
												}

												if (!$("#branchName").val()) {
													isError = true;
													$("#error_branchName")
															.show()
												} else {
													$("#error_branchName")
															.hide()
												}

												if (!$("#ifscCode").val()) {
													isError = true;
													$("#error_ifscCode").show()
												} else {
													$("#error_ifscCode").hide()
												}

												if (!$("#accNo").val()) {
													isError = true;
													$("#error_accNo").show()
												} else {
													$("#error_accNo").hide()
												}

												if (!$("#cinNo").val()) {
													isError = true;
													$("#error_cinNo").show()
												} else {
													$("#error_cinNo").hide()
												}

												if (!$("#fdaNo").val()) {
													isError = true;
													$("#error_fdaNo").show()
												} else {
													$("#error_fdaNo").hide()
												}

												if (!$("#panNo").val()) {
													isError = true;
													$("#error_panNo").show()
												} else {
													$("#error_panNo").hide()
												}

												/* var temp = document.getElementsByName('paymentGatewayApplicable').value;
												alert(temp);
												if (temp=1) {
												

												if (!$("#paymentGatewayLink").val()) {
													isError = true;
													$("#error_paymentGatewayLink")
															.show()
												} else {
													$("#error_paymentGatewayLink")
															.hide()
												}
												
												
												}
												 */

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