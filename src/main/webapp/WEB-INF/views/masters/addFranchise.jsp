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
<body class="sidebar-xs" onload="init();">

	<c:url value="/validateUnqFrMobNo" var="validateUnqFrMobNo"/>
	<c:url value="/getFrInfoByEmail" var="getFrInfoByEmail"/>	
	
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
									href="${pageContext.request.contextPath}/showFranchises"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;Franchise
										List</a></span>
							</div>


							<div class="card-body">

								<ul class="nav nav-tabs nav-tabs-highlight mb-0">
									<li class="nav-item"><a href="#bordered-tab1" id="tab1"
										name="tab1" class="nav-link active" data-toggle="tab">Basic
											Information</a></li>
									<c:if test="${frId>0}">
										<li class="nav-item"><a href="#bordered-tab2" id="tab2"
											name="tab2" class="nav-link" data-toggle="tab">FDA & GST
												Details</a></li>
										<li class="nav-item"><a href="#bordered-tab3" id="tab3"
											name="tab3" class="nav-link" data-toggle="tab">Bank
												Details</a></li>
									</c:if>
								</ul>




								<div
									class="tab-content card card-body border border-top-0 rounded-top-0 shadow-0 mb-0">
									<!--------------------- TAB 1 ---------------------------->
									<div class="tab-pane fade show active" id="bordered-tab1">

										<form
											action="${pageContext.request.contextPath}/insertFranchise"
											id="submitInsert" method="post" enctype="multipart/form-data">
											<p class="desc text-danger fontsize11">Note : * Fields
												are mandatory.</p>
											<div class="form-group row">
												<div class="col-lg-4">
													<input type="hidden" class="form-control" name="frId"
														id="frId" value="${frId}">
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="frCode">Franchise Code<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" name="frCode"
														id="frCode" value="${franchise.frCode}"
														readonly="readonly">
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="frName">Franchise Name<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="frName" id="frName" maxlength="80"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.frName}"> <span
														class="validation-invalid-label text-danger"
														id="error_frName" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="openDate">Opening Date<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control datepickerclass maxlength-badge-position"
														name="openDate" id="openDate" maxlength="10"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.openingDate}"> <span
														class="validation-invalid-label text-danger"
														id="error_openDate" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="ownerDob">Owners Date oF Birth<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control datepickerclass maxlength-badge-position"
														name="ownerDob" id="ownerDob" maxlength="10"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.ownersBirthDay}"> <span
														class="validation-invalid-label text-danger"
														id="error_ownerDob" style="display: none;">This
														field is required.</span>
												</div>
											</div>


											<div class="form-group row">

												<label class="col-form-label font-weight-bold col-lg-2"
													for="email">Email <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="email" placeholder="@gmail.com"
														class="form-control maxlength-badge-position" name="email"
														id="email" maxlength="80" autocomplete="off"
														onchange="trim(this)" value="${franchise.frEmailId}">
													<span class="validation-invalid-label text-danger"
														id="error_email" style="display: none;">This field
														is required.</span><span
												class="validation-invalid-label text-danger" id="unq_email"
												style="display: none;">This Email is Already
												Exist.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="mobNo">Contact<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position non-char"
														name="mobNo" id="mobNo" maxlength="10"
														placeholder="Contact No." autocomplete="off"
														onchange="trim(this)" value="${franchise.frContactNo}">
													<span class="validation-invalid-label text-danger"
														id="error_mobNo" style="display: none;">This field
														is required.</span><span
												class="validation-invalid-label text-danger" id="unq_mob"
												style="display: none;">This Mobile No. is Already
												Exist.</span>
												</div>
											</div>

											<div class="form-group row">

												<label class="col-form-label font-weight-bold col-lg-2"
													for="address">Address<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<textarea rows="1" style="height: 1em;" name="address"
														id="address" class="form-control" onchange="trim(this)">${franchise.frAddress}</textarea>
													<span class="validation-invalid-label text-danger"
														id="error_address" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-1"
													for="city">City <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-2">
													<%-- <input type="text"
														class="form-control maxlength-badge-position" name="city"
														id="city" maxlength="30" autocomplete="off"
														onchange="trim(this)" value="${franchise.frCity}"> --%>

													<select class="form-control select-search" data-fouc
														data-placeholder="Select City" name="city" id="city">
														<c:forEach items="${cityList}" var="cityList">
															<c:choose>
																<c:when test="${cityList.cityId==franchise.frCity}">
																	<option value="${cityList.cityId}" selected="selected">${cityList.cityName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${cityList.cityId}">${cityList.cityName}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select> <span class="validation-invalid-label text-danger"
														id="error_city" style="display: none;">This field
														is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-1"
													for="state">State<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-2">
													<input type="text"
														class="form-control maxlength-badge-position" name="state"
														id="state" maxlength="30" autocomplete="off"
														onchange="trim(this)" value="${franchise.state}">
													<span class="validation-invalid-label text-danger"
														id="error_state" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="Pincode ">Pin Code <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position non-char"
														name="pincode" id="pincode" maxlength="6"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.pincode}"> <span
														class="validation-invalid-label text-danger"
														id="error_pincode" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="password">Password<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="password"
														class="form-control maxlength-badge-position" name="pass"
														id="pass" maxlength="30" autocomplete="off"
														onchange="trim(this)"> <span
														class="validation-invalid-label text-danger"
														id="error_password" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Image <span class="text-danger"></span>:
												</label>
												<div class="col-lg-4">
													<label class="form-check-label"> <img id="output"
														width="150" src="${imgPath}${franchise.frImage}" /> <input
														type="file" class="form-control-uniform" data-fouc
														onchange="loadFile(event)" name="doc" id="doc"> <input
														type="hidden" class="form-control-uniform" name="editImg"
														id="editImg" value="${franchise.frImage}"> <span
														class="validation-invalid-label text-danger"
														id="error_doc" style="display: none;">This field is
															required.</span>
													</label>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Rating <span class="text-danger"></span>:
												</label>
												<div class="col-lg-4">
													<select class="form-control select" data-fouc
														name="frRating" id="frRating"
														data-placholder="Give Rating">
														<option value="1"
															${franchise.frRating==1 ? 'selected' : '' }>1</option>
														<option value="2"
															${franchise.frRating==2 ? 'selected' : '' }>2</option>
														<option value="3"
															${franchise.frRating==3 ? 'selected' : '' }>3</option>
														<option value="4"
															${franchise.frRating==4 ? 'selected' : '' }>4</option>
														<option value="5"
															${franchise.frRating==5 ? 'selected' : '' }>5</option>
													</select>
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


									<!---------------------End TAB 1 ---------------------------->

									<!---------------------- TAB 2---------------------------->
									<div class="tab-pane fade" id="bordered-tab2">
										<form
											action="${pageContext.request.contextPath}/insertFrFdaAndGst"
											id="submitFdaGstDtl" method="post">
											<div class="form-group row">
												<div class="col-lg-4">
													<input type="hidden" class="form-control" name="frId"
														id="frId" value="${frId}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="fdaNo">FDA No.<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position" name="fdaNo"
														id="fdaNo" maxlength="14" autocomplete="off"
														onchange="trim(this)" value="${franchise.fdaNumber}">
													<span class="validation-invalid-label text-danger"
														id="error_fdaNo" style="display: none;">This field
														is required.</span>
												</div>



												<label class="col-form-label font-weight-bold col-lg-2"
													for="fdaExpDate">FDA Lic. Expiry Date<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control datepickerclass maxlength-badge-position"
														name="fdaExpDate" id="fdaExpDate" maxlength="10"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.fdaLicenseDateExp}"> <span
														class="validation-invalid-label text-danger"
														id="error_fdaExpDate" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="gstType">GST Type<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<select class="form-control select" data-fouc
														name="gstType" id="gstType"
														data-placholder="Select GST Type">
														<option value="1"
															${franchise.gstType==1 ? 'selected' : '' }>Regular</option>
														<option value="2"
															${franchise.gstType==2 ? 'selected' : '' }>Composite</option>
														<option value="3"
															${franchise.gstType==3 ? 'selected' : '' }>Non-Register</option>
													</select>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="gstNo">GST No.<span class="text-danger"
													id="mandat"> </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position" name="gstNo"
														id="gstNo" maxlength="15" autocomplete="off"
														onchange="trim(this)" value="${franchise.gstNumber}">
													<span class="validation-invalid-label text-danger"
														id="error_gstNo" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="longitude">Longitude<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="longitude" id="longitude" maxlength="12"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.shopsLogitude}"> <span
														class="validation-invalid-label text-danger"
														id="error_longitude" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="latitude">Latitude<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="latitude" id="latitude" maxlength="12"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.shopsLatitude}"> <span
														class="validation-invalid-label text-danger"
														id="error_latitude" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="servePincode">Pin Code we Served <span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="servePincode" id="servePincode" maxlength="100"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.pincodeWeServed}"> <span
														class="validation-invalid-label text-danger"
														id="error_servePincode" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="kmCover">Area Km. Cover <span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position non-char-float"
														name="kmCover" id="kmCover" maxlength="30"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.noOfKmAreaCover}"> <span
														class="validation-invalid-label text-danger"
														id="error_kmCover" style="display: none;">This
														field is required.</span>
												</div>
											</div>
											<br>
											<div class="text-center">
												<button type="submit" class="btn btn-primary"
													id="submtFdabtn">
													Save <i class="icon-paperplane ml-2"></i>
												</button>
											</div>
										</form>
									</div>
									<!----------------------End TAB 2---------------------------->
									<!----------------------TAB 3---------------------------->
									<div class="tab-pane fade" id="bordered-tab3">
										<form
											action="${pageContext.request.contextPath}/submitBankDtl"
											id="submitBankDtl" method="post">

											<div class="form-group row">
												<div class="col-lg-4">
													<input type="hidden" class="form-control" name="frId"
														id="frId" value="${frId}">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="coBankName">Company Bank Name<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="coBankName" id="coBankName" maxlength="100"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.coBankName}"> <span
														class="validation-invalid-label text-danger"
														id="error_coBankName" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="branchName">Branch Name<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="branchName" id="branchName" maxlength="50"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.coBankBranchName}"> <span
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
														name="ifscCode" id="ifscCode" maxlength="11"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.coBankIfscCode}"> <span
														class="validation-invalid-label text-danger"
														id="error_ifscCode" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="accNo">Account No.<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position non-char"
														name="accNo" id="accNo" maxlength="17" autocomplete="off"
														onchange="trim(this)" value="${franchise.coBankAccNo}">
													<span class="validation-invalid-label text-danger"
														id="error_accNo" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="paymentGateWay">Payment Gate Way<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="paymentGateWay" id="paymentGateWay" maxlength="100"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.paymentGetwayLink}"> <span
														class="validation-invalid-label text-danger"
														id="error_paymentGateWay" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="samePayGateWay">Payment Gate Way Same as
													Parent<span class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="samePayGateWay" id="samePayGateWay" maxlength="100"
														autocomplete="off" onchange="trim(this)"
														value="${franchise.paymentGetwayLinkSameAsParent}">
													<span class="validation-invalid-label text-danger"
														id="error_samePayGateWay" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="panNo">Pan No<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position" name="panNo"
														id="panNo" maxlength="10" autocomplete="off"
														onchange="trim(this)" value="${franchise.panNo}">
													<span class="validation-invalid-label text-danger"
														id="error_panNo" style="display: none;">This field
														is required.</span>
												</div>
											</div>
											<br>
											<div class="text-center">
												<button type="submit" class="btn btn-primary"
													id="submtbankbtn">
													Save <i class="icon-paperplane ml-2"></i>
												</button>
											</div>
										</form>
									</div>
								</div>
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
		//Basic Info Form
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#frName").val()) {
					isError = true;
					$("#error_frName").show()
				} else {
					$("#error_frName").hide()
				}

				if (!$("#ownerDob").val()) {
					isError = true;
					$("#error_ownerDob").show()
				} else {
					$("#error_ownerDob").hide()
				}

				if (!$("#openDate").val()) {
					isError = true;
					$("#error_openDate").show()
				} else {
					$("#error_openDate").hide()
				}

				if (!$("#email").val() || !validateEmail($("#email").val())) {
					isError = true;
					$("#error_email").show()
				} else {
					$("#error_email").hide()
				}

				if (!$("#mobNo").val() || !validateMobile($("#mobNo").val())) {
					isError = true;
					$("#error_mobNo").show()
				} else {
					$("#error_mobNo").hide()
				}

				if (!$("#address").val()) {
					isError = true;
					$("#error_address").show()
				} else {
					$("#error_address").hide()
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

				if (!$("#pincode").val()) {
					isError = true;
					$("#error_pincode").show()
				} else {
					$("#error_pincode").hide()
				}

				if (!$("#pass").val()) {
					isError = true;
					$("#error_password").show()
				} else {
					$("#error_password").hide()
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

		//FDA & GST Info Form
		$(document)
				.ready(
						function($) {
							$("#submitFdaGstDtl")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#fdaNo").val()) {
													isError = true;
													$("#error_fdaNo").show()
												} else {
													$("#error_fdaNo").hide()
												}

												if (!$("#fdaExpDate").val()) {
													isError = true;
													$("#error_fdaExpDate")
															.show()
												} else {
													$("#error_fdaExpDate")
															.hide()
												}

												if (parseInt($("#gstType")
														.val()) == 1
														|| parseInt($(
																"#gstType")
																.val()) == 2) {
													if (!$("#gstNo").val()) {
														isError = true;
														document
																.getElementById("mandat").innerHTML = "*";
														$("#error_gstNo")
																.show()
													} else {
														$("#error_gstNo")
																.hide()
													}
												} else {
													document
															.getElementById("mandat").innerHTML = "";
													$("#error_gstNo").hide()
												}

												if (!$("#kmCover").val()) {
													isError = true;
													$("#error_kmCover").show()
												} else {
													$("#error_kmCover").hide()
												}

												if (!$("#servePincode").val()) {
													isError = true;
													$("#error_servePincode")
															.show()
												} else {
													$("#error_servePincode")
															.hide()
												}

												if (!$("#longitude").val()) {
													isError = true;
													$("#error_longitude")
															.show()
												} else {
													$("#error_longitude")
															.hide()
												}

												if (!$("#latitude").val()) {
													isError = true;
													$("#error_latitude").show()
												} else {
													$("#error_latitude").hide()
												}

												if (!isError) {
													var x = true;
													if (x == true) {
														document
																.getElementById("submtFdabtn").disabled = true;
														return true;
													}
												}

												return false;

											});
						});

		//Bank Detail Form
		$(document)
				.ready(
						function($) {
							$("#submitBankDtl")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#coBankName").val()) {
													isError = true;
													$("#error_coBankName")
															.show()
												} else {
													$("#error_coBankName")
															.hide()
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

												if (!$("#paymentGateWay").val()) {
													isError = true;
													$("#error_paymentGateWay")
															.show()
												} else {
													$("#error_paymentGateWay")
															.hide()
												}

												if (!$("#samePayGateWay").val()) {
													isError = true;
													$("#error_samePayGateWay")
															.show()
												} else {
													$("#error_samePayGateWay")
															.hide()
												}

												if (!$("#panNo").val()) {
													isError = true;
													$("#error_panNo").show()
												} else {
													$("#error_panNo").hide()
												}

												if (!isError) {
													var x = true;
													if (x == true) {
														document
																.getElementById("submtbankbtn").disabled = true;
														return true;
													}
												}

												return false;

											});
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

		$('.non-char').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9]/g, '').replace(
							/(\..*)\./g, '$1');
				});

		$('.non-char-float').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9.]/g, '').replace(
							/(\..*)\./g, '$1');
				});

		/* Auto Resize Text Area for address */
		var observe;
		if (window.attachEvent) {
			observe = function(element, event, handler) {
				element.attachEvent('on' + event, handler);
			};
		} else {
			observe = function(element, event, handler) {
				element.addEventListener(event, handler, false);
			};
		}
		function init() {
			var text = document.getElementById('address');
			function resize() {
				text.style.height = 'auto';
				text.style.height = text.scrollHeight + 'px';
			}
			/* 0-timeout to get the already changed text */
			function delayedResize() {
				window.setTimeout(resize, 0);
			}
			observe(text, 'change', resize);
			observe(text, 'cut', delayedResize);
			observe(text, 'paste', delayedResize);
			observe(text, 'drop', delayedResize);
			observe(text, 'keydown', delayedResize);

			text.focus();
			text.select();
			resize();
		}
	</script>
	<script>
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

		$('.datepickerclass').daterangepicker({
			"autoUpdateInput" : true,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
	</script>
<script>
$("#mobNo").change(function() { 
	
	var mobNo = $("#mobNo").val();
	var frId = $("#frId").val();
	

	$.getJSON('${validateUnqFrMobNo}', {
		mobNo : mobNo,
		frId : frId,
		ajax : 'true',
	}, function(data) {

		if (data.error == false) {
			$("#unq_mob").show();
			$("#mobNo").val('');
			document.getElementById("mobNo").focus();
		} else {
			$("#unq_mob").hide();
		}
	});
});

$("#email").change(function() { 
	var email = $("#email").val();
	var frId = $("#frId").val();
	//alert(code)

	$.getJSON('${getFrInfoByEmail}', {
		email : email,
		frId : frId,
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

</body>
</html>