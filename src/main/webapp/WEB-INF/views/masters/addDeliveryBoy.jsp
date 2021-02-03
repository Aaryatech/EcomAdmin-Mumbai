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
	<c:url value="/getDelvrBoyInfoByMobNo" var="getDelvrBoyInfoByMobNo"></c:url>
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
									href="${pageContext.request.contextPath}/showDeliveryList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

								<form action="${pageContext.request.contextPath}/insertDeliveryBoy"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control" name="delBoyId"
										id="delBoyId" value="${delvrBoy.delBoyId}">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="city_code">First Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="firstName"  id="firstName" maxlength="50" 
												autocomplete="off" onchange="trim(this)" value="${delvrBoy.firstName}"> <span
												class="validation-invalid-label text-danger"
												id="error_firstName" style="display: none;">This
												field is required.</span> 
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="city_name">Last Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="lastName" id="lastName" maxlength="50"
												autocomplete="off" onchange="trim(this)"
												value="${delvrBoy.lastName}"> <span
												class="validation-invalid-label text-danger"
												id="error_lastName" style="display: none;">This
												field is required.</span>
										</div>

									</div>	

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="mobNo">Mobile No.<span
											class="text-danger">*</span>:
										</label>

										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="mobNo" id="mobNo" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${delvrBoy.mobileNo}"> <span
												class="validation-invalid-label text-danger"
												id="error_mobNo" style="display: none;">This
												field is required.</span>												
												 <span
												class="validation-invalid-label text-danger"
												id="unq_mobNo" style="display: none;">Mobile No. Already Exist</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="empCode">Employee Code<span
											class="text-danger"></span>:
										</label>

										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="empCode" id="empCode" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${delvrBoy.empCode}"> <span
												class="validation-invalid-label text-danger"
												id="error_empCode" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="mobNo">Date Of Birth<span
											class="text-danger">*</span>:
										</label>

										<div class="col-lg-4">
											<input type="text"
												class="form-control datepickerclass maxlength-badge-position"
												name="dob" id="dob" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${delvrBoy.dateOfBirth}"> <span
												class="validation-invalid-label text-danger"
												id="error_dob" style="display: none;">This
												field is required.</span>												
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="joiningDate">Joining Date<span
											class="text-danger">*</span>:
										</label>

										<div class="col-lg-4">
											<input type="text"
												class="form-control datepickerclass maxlength-badge-position"
												name="joiningDate" id="joiningDate" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${delvrBoy.joiningDate}"> <span
												class="validation-invalid-label text-danger"
												id="error_joiningDate" style="display: none;">This
												field is required.</span>
										</div>
										</div>
										
										 <div class="form-group row">
										   <label class="col-form-label font-weight-bold col-lg-2"
											for="Delivery Boy Y/N Licen No">Delivery Boy License No<span
											class="text-danger">*</span>:
										   </label>
										   
										  <div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="deliveryBoyY/NLicenNo" id="licenseNo" maxlength="20"
												autocomplete="off" onchange="trim(this)" value="${delvrBoy.deliveryBoyLicenseNo} ">
												 <span class="validation-invalid-label text-danger"
												id="error_licenseNo" style="display: none;">This
												field is required.</span>
												
												 <span
												class="validation-invalid-label text-danger"
												id="unq_licenseNo" style="display: none;">License No. Already Exist</span>
										  </div>
																								
																										
											
										   <label class="col-form-label font-weight-bold col-lg-2">Own Vehicle</label>
																						
																								
										 <div class="form-check form-check-inline"> 
										  <input type="radio"	name="ownVehicle" id="yes"  class="form-check-input" checked value="1"
										   ${delvrBoy.ownVehicle==1 ? 'checked' : ''} >
										   <label class="form-check-label">Yes</label>
											</div>	
											
											
										 <div class="form-check form-check-inline"> 										
										   <input type="radio" name="ownVehicle" id="no"  class="form-check-input" value="0" 
										   ${delvrBoy.ownVehicle==0 ? 'checked' : ''}>
										   <label class="form-check-label">No	</label>
										   </div>
										
										</div>	
									
										
										 <div class="form-group row">
										  <label class="col-form-label font-weight-bold col-lg-2"
											for="Vahocal  No">Vehicle  No.<span
											class="text-danger">*</span>:
										  </label>

										  <div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="vehicalNo" id="vehicalNo" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${delvrBoy.vehicleNo}"> <span
												class="validation-invalid-label text-danger"
												id="error_vehicalNo" style="display: none;">This
												field is required.</span>												
												 <span
												class="validation-invalid-label text-danger"
												id="unq_mobNo" style="display: none;">Vehicle  No Already Exist</span>
										  </div>
										
										
										  <label class="col-form-label font-weight-bold col-lg-2"
											for="Licen Expiry Date">License Expiry Date<span
											class="text-danger">*</span>:
										  </label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control datepickerclass maxlength-badge-position"
												name="licenExpiryDate" id="Licen Expiry Date" maxlength="10"
												autocomplete="off" onchange="trim(this)" value="${delvrBoy.licenseExpiryDate}"> <span
												class="validation-invalid-label text-danger"
												id="error_joiningDate" style="display: none;">This
												field is required.</span>
										</div>	
										</div>	
										
										
										<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="Licen Expiry Date">Insurance Expiry Date<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control datepickerclass maxlength-badge-position"
												name="insuranceExpiryDate" id="Insurance Expiry Date" maxlength="10"
												autocomplete="off" onchange="trim(this)" value="${delvrBoy.insuranceExpiryDate}"> <span
												class="validation-invalid-label text-danger"
												id="error_Insurance Expiry Date" style="display: none;">This
												field is required.</span>
										</div>	
										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="owner Of Vehical">Owner Of Vehical<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="ownerOfVehical" id="ownerOfVehical" maxlength="50"
												autocomplete="off" onchange="trim(this)"
												value="${delvrBoy.ownerOfVehicle}"> <span
												class="validation-invalid-label text-danger"
												id="error_ownerOfVehical" style="display: none;">This
												field is required.</span>
										</div>
										</div>
										
										<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="PUC Expiry Date">PUC Expiry Date<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control datepickerclass maxlength-badge-position"
												name="pucExpiryDate" id="PUC Expiry Date" maxlength="10"
												autocomplete="off" onchange="trim(this)" value="${delvrBoy.pucExpiryDate}"> <span
												class="validation-invalid-label text-danger"
												id="error_PUC Expiry Date" style="display: none;">This
												field is required.</span>
										</div>	
									
								
								
										<label class="col-form-label font-weight-bold col-lg-2"
											for="mobNo">Address<span
											class="text-danger">*</span>:
										</label>

										<div class="col-lg-4">
											<textarea type="text"
												class="form-control maxlength-badge-position"
												name="address" id="address" maxlength="250"
												autocomplete="off" onchange="trim(this)"> ${delvrBoy.address}</textarea>
												 <span class="validation-invalid-label text-danger"
												id="error_address" style="display: none;">This
												field is required.</span>
										</div>										
									</div>
									
									
																																															
									
																							                                     
																																																						

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${delvrBoy.delBoyId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="activeStat"
													id="active_y" ${delvrBoy.isActive==1 ? 'checked' : ''}>
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="activeStat" id="active_n"
													${delvrBoy.isActive==0 ? 'checked' : ''}> In-Active
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio" id="active_y"
													class="form-check-input" checked value="1" name="activeStat">
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="activeStat" id="active_n"> In-Active
												</label>
											</div>
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<input type="hidden" id="btnType" name="btnType">
									<br>
									<div class="text-center">								
										<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
									
									<c:if test="${delvrBoy.delBoyId==0}">
										<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn(1)">
											Save & Next<i class="icon-paperplane ml-2"></i>
										</button>
									</c:if>
									</div>
								</form>
								<input type="hidden" value="${isEdit}" id="isEdit">
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
		
		function pressBtn(btnVal){
			$("#btnType").val(btnVal)
		}
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

												if (!$("#firstName").val()) {
													isError = true;
													$("#error_firstName")
															.show()
												} else {
													$("#error_firstName")
															.hide()
												}

												if (!$("#lastName").val()) {
													isError = true;
													$("#error_lastName")
															.show()
												} else {
													$("#error_lastName")
															.hide()
												}
												
												if (!$("#mobNo").val()
														|| !validateMobile($(
																"#mobNo")
																.val())) {
													isError = true;
													$("#mobNo").focus();
													$("#error_mobNo")
															.show()
												} else {
													$("#error_mobNo")
															.hide()
												}
												
												if (!$("#licenseNo").val()) {
													isError = true;
													$("#licenseNo").focus();
													$("#error_licenseNo")
															.show()
												} else {
													$("#error_licenseNo")
															.hide()
												}
												
												if (!$("#joiningDate").val()) {
													isError = true;
													$("#error_joiningDate")
															.show()
												} else {
													$("#error_joiningDate")
															.hide()
												}
												
												if (!$("#vehicalNo").val()) {
													isError = true;
													$("#error_vehicalNo")
															.show()
												} else {
													$("#error_vehicalNo")
															.hide()
												}
												
												if (!$("#ownerOfVehical").val()) {
													isError = true;
													$("#error_ownerOfVehical")
															.show()
												} else {
													$("#error_ownerOfVehical")
															.hide()
												}
												
												
												if (!$("#address").val()) {
													isError = true;
													$("#error_address")
															.show()
												} else {
													$("#error_address")
															.hide()
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
																		$(".btn").attr("disabled", true);
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

		$("#mobNo").change(function() { // 1st
			var mobNo = $("#mobNo").val();
			var delBoyId = $("#delBoyId").val();
			//alert(code)

			$.getJSON('${getDelvrBoyInfoByMobNo}', {
				mobNo : mobNo,
				delBoyId : delBoyId,
				ajax : 'true',
			}, function(data) {

				if (data.error == false) {
					$("#unq_mobNo").show();
					$("#mobNo").val('');
					$("#mobNo").focus();
				} else {
					$("#unq_mobNo").hide();
				}
			});
		});

		$("#Delivery Boy Y/N Licen No").change(function() { // 1st
			var DeliveryBoyYNLicenNo = $("#Delivery Boy Y/N Licen No").val();
			var delBoyId = $("#delBoyId").val();
			//alert(code)

			$.getJSON('${getDelvrBoyInfoBydeliveryBoyLicenseNo}', {
				DeliveryBoyYNLicenNo : DeliveryBoyYNLicenNo,
				delBoyId : delBoyId,
				ajax : 'true',
			}, function(data) {

				if (data.error == false) {
					$("#unq_licenseNo").show();
					$("#licenseNo").val('');
					$("#licenseNo").focus();
				} else {
					$("#unq_licenseNo").hide();
				}
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

		$('#mobNo').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9]/g, '').replace(
							/(\..*)\./g, '$1');
				});
	</script>


</body>
</html>