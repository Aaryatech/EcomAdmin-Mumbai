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
	<c:url value="/getCustAdrsDtl" var="getCustAdrsDtl"></c:url>
	<c:url value="/getCityListAjax" var="getCityListAjax"></c:url>
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
							<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

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
											for="languageId">Language<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="languageId" id="languageId"
												data-placholder="Select Language">
												<option value="0">Select Language</option>
												<c:forEach items="${langList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.langId==cust.languageId}">
															<option selected value="${list.langId}">${list.langName}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.langId}">${list.langName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_languageId" style="display: none;">This
												field is required.</span>
										</div>
									</div>



									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="custMobileNo">Mobile No.<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${cust.custMobileNo}" name="custMobileNo"
												id="custMobileNo"> <span
												class="validation-invalid-label text-danger"
												id="error_custMobileNo" style="display: none;">This
												field is required.</span> <span
												class="validation-invalid-label text-danger" id="unq_mob"
												style="display: none;">This Mobile No. is Already
												Exist.</span>


										</div>



										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Email <span class="text-danger"></span>:
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
											for="dateOfBirth">Date Of Birth<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass"
												autocomplete="off" value="${cust.dateOfBirth}"
												name="dateOfBirth" id="dateOfBirth"> <span
												class="validation-invalid-label text-danger"
												id="error_dateOfBirth" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="city">City<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="city" id="city" data-placholder="Select City">
												<option value="0">Select City</option>												
												 <c:forEach items="${cityList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.cityId==cust.cityId}">
															<option selected value="${list.cityId}">${list.cityName}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.cityId}">${list.cityName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_city" style="display: none;">This field is
												required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="custGender">Gender<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="custGender" id="custGender"
												data-placholder="Select Gender">												
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
												id="error_custGender" style="display: none;">This
												field is required.</span>
										</div>
										<label class="col-form-label font-weight-bold col-lg-2"
											for="ageRange">Age Range<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="ageRange" id="ageRange"
												data-placholder="Select Age Range">												
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
												id="error_ageRange" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="doc">Profile Image <span class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${cust.profilePic}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${cust.profilePic}"> <span
												class="validation-invalid-label text-danger" id="error_doc"
												style="display: none;">This field is required.</span>
												<span class="text-danger">*Please upload file having extensions
														 .jpeg/.jpg/.png only.</span>
											</label>
										</div>

									</div>
									<input type="hidden" id="btnType" name="btnType"> <br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="submtbtn"
											onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										<c:if test="${isEdit==0}">
											<button type="submit" class="btn btn-primary" id="submtbtn1"
												onclick="pressBtn(1)">
												Save & Next<i class="icon-paperplane ml-2"></i>
											</button>
										</c:if>	
										
										<c:if test="${cust.custId>0}">
											<button type="button" class="btn btn-primary rounded-round legitRipple" style="float: right;"
											title="Add Address" data-toggle="modal" data-target="#modal_large">
											<i class="fas fa-plus"></i></button>
										</c:if>
									</div>
									
									
								</form>
							</div>
						</div>
						<!-- /a legend -->
					</div>
				</div>
				
				<!-- -------------------------------------------------------------------- -->
				<!-- Default lists -->
				<div class="row">
					<div class="col-md-12">
						<div class="card">
							<div class="card-header bg-blue text-white d-flex justify-content-between">
								<h6 class="card-title">Customer Address List</h6>
								<div class="header-elements">
									<div class="list-icons">
				                		<a class="list-icons-item" data-action="collapse"></a>
				                		<a class="list-icons-item" data-action="reload"></a>
				                	</div>
			                	</div>
							</div>

							<div class="card-body">
								<table class="table datatable-header-basic">
						<thead>
							<tr>
								<th width="10%">Sr. No.</th>
								<th>Address 1</th>
								<th>Address 2</th>
								<th>Address 3</th>
								<th>Landmark</th>
								<th>City</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${custAddList}" var="custAddList"
								varStatus="count">
								<tr>
									<td>${count.index+1}</td>
									<td>${custAddList.address}</td>
									<td>${custAddList.exVar1}</td>
									<td>${custAddList.exVar2}</td>
									<td>${custAddList.landmark}</td>
									<td>${custAddList.exVar3}</td>
									<td class="text-center"><c:if test="${editAccess==0}">
											<div class="list-icons">
												<a
													href="#" onclick="getCustAdd(${custAddList.custDetailId}, ${custAddList.custId})"
													class="list-icons-item" title="Edit"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
										</c:if> <c:if test="${deleteAccess==0}">
											<div class="list-icons">
												<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${custAddList.custDetailId}"   	data-uuid1="${custAddList.custId}" data-popup="tooltip"
													title="" data-original-title="Delete"><i
													class="icon-trash"></i></a>
											</div>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
							</div>
						</div>
					</div>
					</div>
				<!-- -------------------------------------------------------------------- -->
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

		function pressBtn(btnVal) {
			$("#btnType").val(btnVal)
		}
		
		$('#custMobileNo').on('input', function() {
			 this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');
			});
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
													$("#error_custName").show()
												} else {
													$("#error_custName").hide()
												}

												if (!$("#custMobileNo").val()
														|| !validateMobile($(
																"#custMobileNo")
																.val())) {
													isError = true;
													$("#error_custMobileNo")
															.show()
												} else {
													$("#error_custMobileNo")
															.hide()
												}

												if (!$("#dateOfBirth").val()) {
													isError = true;
													$("#error_dateOfBirth")
															.show()
												} else {
													$("#error_dateOfBirth")
															.hide()
												}

												if (!$("#city").val()
														|| $("#city").val() == 0) {
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

												if ($("#email").val().length != 0) {

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
												if (!isError) {
													var x = false;
													bootbox
															.confirm({
																title : 'Confirm ',
																message : 'Are you sure you want to Submit ?',
																buttons : {
																	confirm : {
																		label : 'Yes',
																		className : 'btn-success'
																	},
																	cancel : {
																		label : 'Cancel',
																		className : 'btn-danger'
																	}
																},
																callback : function(
																		result) {
																	if (result) {
																		$(
																				".btn")
																				.attr(
																						"disabled",
																						true);
																		var form = document
																				.getElementById("submitInsert")
																		form
																				.submit();
																	}
																}
															});
													//end ajax send this to php page
													return false;
												}//end of if !isError

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
<!-- ----------------------------------Model------------------------------- -->
 <!-- Large modal -->
				<div id="modal_large" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="card-header bg-blue text-white d-flex justify-content-between">
								<h5 class="modal-title">Customer-${cust.custName}</h5>
								<button type="button" class="close" data-dismiss="modal" onclick="clearFields()">&times;</button>
							</div>

							<div class="modal-body">
								<form
									action="${pageContext.request.contextPath}/insertNewCustomerAddress"
									id="submitInsert2" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${cust.custId}" name="cust_id" id="cust_id"> <input
										type="hidden" class="form-control" value="${custDet.custDetailId}"
										name="custDetailId" id="custDetailId">



									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address1">Address 1 <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position"
											maxlength="80"	autocomplete="off" onchange="trim(this)" name="address"
												id="address" ${custDet.address}>
											<span class="validation-invalid-label" id="error_address"
												style="display: none;">This field is required.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Address 2 <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position"
											maxlength="80"	autocomplete="off" onchange="trim(this)" name="address2"
												id="address2" ${custDet.exVar1}>
											<span class="validation-invalid-label" id="error_address2"
												style="display: none;">This field is required.</span>
										</div>
									</div>	
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address3">Address 3 <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position"
											maxlength="80"	autocomplete="off" onchange="trim(this)" name="address3"
												id="address3" ${custDet.exVar2}>
											<span class="validation-invalid-label" id="error_address3"
												style="display: none;">This field is required.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cityId">City<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="cityId" id="cityId1" data-placholder="Select City">
												<c:forEach items="${cityList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.cityId==cust.cityId}">
															<option selected value="${list.cityId}">${list.cityName}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.cityId}">${list.cityName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_cityId" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="landmark">Landmark <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${custDet.landmark}" name="landmark" id="landmark">
											<span class="validation-invalid-label" id="error_landmark"
												style="display: none;">This field is required.</span>
										</div>
								 

										<label class="col-form-label font-weight-bold col-lg-2"
											for="caption">Caption <span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${custDet.caption}" name="caption" id="caption">
											<span class="validation-invalid-label" id="error_caption"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="latitude">Latitude <span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${custDet.latitude}" name="latitude" id="latitude">

										</div>


										<label class="col-form-label font-weight-bold col-lg-2"
											for="longitude">Longitude <span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${custDet.longitude}" name="longitude" id="longitude">

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
					</div>
				</div>
				<!-- /large modal -->
<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert2").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#address").val()) {
					isError = true;
					$("#error_address").show()
				} else {
					$("#error_address").hide()
				}
				
				if (!$("#address2").val()) {
					isError = true;
					$("#error_address2").show()
				} else {
					$("#error_address2").hide()
				}
				
				if (!$("#address3").val()) {
					isError = true;
					$("#error_address3").show()
				} else {
					$("#error_address3").hide()
				}

				if (!$("#landmark").val()) {
					isError = true;
					$("#error_landmark").show()
				} else {
					$("#error_landmark").hide()
				}

				if (!$("#cityId1").val() || $("#cityId1").val() == 0) {
					isError = true;
					$("#error_cityId1").show()
				} else {
					$("#error_cityId1").hide()
				}

				if (!isError) {
					var x = false;
					bootbox
							.confirm({
								title : 'Confirm ',
								message : 'Are you sure you want to Submit ?',
								buttons : {
									confirm : {
										label : 'Yes',
										className : 'btn-success'
									},
									cancel : {
										label : 'Cancel',
										className : 'btn-danger'
									}
								},
								callback : function(
										result) {
									if (result) {
										$(
												".btn")
												.attr(
														"disabled",
														true);
										var form = document
												.getElementById("submitInsert2")
										form
												.submit();
									}
								}
							});
					//end ajax send this to php page
					return false;
				}//end of if !isError

				return false;

			});
		});
	</script>
	<script>
	function getCustAdd(custDetailId, custId){
		
		$.getJSON('${getCustAdrsDtl}', {
			custDetailId : custDetailId,
			custId : custId,
			ajax : 'true',
		}, function(data) {
			if (data!=null) {
				var custAdd = data.custAdd;				
				$('#address').val(custAdd.address);				
				$('#address2').val(custAdd.exVar1);				
				$('#address3').val(custAdd.exVar2);
				$('#cityId').val(custAdd.cityId);
				$('#landmark').val(custAdd.landmark);
				$('#caption').val(custAdd.caption);
				$('#latitude').val(custAdd.latitude);
				$('#longitude').val(custAdd.longitude);
				
				$('#cust_id').val(custAdd.custId);
				$('#custDetailId').val(custAdd.custDetailId);	
				
				$.getJSON('${getCityListAjax}', {

					ajax : 'true',
				}, function(data) {

					if(data!=null){
						$("#error_status").hide();
					}
					$('#cityId1').find('option').remove().end()					

					for (var i = 0; i < data.length; i++) {
						if(custAdd.cityId==data[i].cityId){
							$("#cityId1").append(
									$("<option selected></option>").attr("value",
											data[i].cityId).text(
											data[i].cityName));
						}else{
							$("#cityId1").append(
									$("<option></option>").attr("value",
											data[i].cityId).text(
											data[i].cityName));
						}						
					}
					$("#cityId1").trigger("chosen:updated");
				});				
				
				$('#modal_large').modal('show'); 
			}
		});
	}
	
	function clearFields(){
		
		$('#address').val('');				
		$('#address2').val('');				
		$('#address3').val('');
		$('#cityId').val('');
		$('#landmark').val('');
		$('#caption').val('');
		$('#latitude').val('');
		$('#longitude').val('');
		
		$('#custDetailId').val(0);	
	}
	</script>
	<script type="text/javascript">
		//Custom bootbox dialog
		$('.bootbox_custom')
				.on(
						'click',
						function() {
							var uuid = $(this).data("uuid") // will return the number 123
							var uuid1 = $(this).data("uuid1") // will return the number 123
							bootbox
									.confirm({
										title : 'Confirm ',
										message : 'Are you sure you want to delete selected records ?',
										buttons : {
											confirm : {
												label : 'Yes',
												className : 'btn-success'
											},
											cancel : {
												label : 'Cancel',
												className : 'btn-danger'
											}
										},
										callback : function(result) {
											if (result) {
												location.href = "${pageContext.request.contextPath}/deleteCustomerAddress?custDetailId="
														+ uuid+"&custId="+uuid1;

											}
										}
									});
						});
	</script>
</body>
</html>