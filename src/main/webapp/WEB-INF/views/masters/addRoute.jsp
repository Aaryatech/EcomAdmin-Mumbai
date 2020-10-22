<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
	<c:url value="/chkUnqRouteCode" var="chkUnqRouteCode"></c:url>
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
									href="${pageContext.request.contextPath}/showRouteList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
							
								<form action="${pageContext.request.contextPath}/insertNewRoute"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${route.routeId}" name="routeId" id="routeId">


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="routeName">Route Name <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="50"
												autocomplete="off" onchange="trim(this)"
												value="${route.routeName}" name="routeName"
												id="routeName"> <span
												class="validation-invalid-label" id="error_routeName"
												style="display: none;">This field is required.</span>
										</div>


										<label class="col-form-label font-weight-bold col-lg-2"
											for="routeCode">Route Code <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="6"
												autocomplete="off" onchange="trim(this)"
												value="${route.routeCode}" name="routeCode"
												id="routeCode"> <span
												class="validation-invalid-label" id="error_routeCode"
												style="display: none;">This field is required.</span>
												<span
												class="validation-invalid-label" id="unq_route_code"
												style="display: none;">Route code already exist.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="routeKm">Route Km. <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="6"
												autocomplete="off" onchange="trim(this)"
												value="${route.routeKm > 0 ? route.routeKm : 1}" name="routeKm"
												id="routeKm"> <span
												class="validation-invalid-label" id="error_routeKm"
												style="display: none;">This field is required.</span>
										</div>
										
											<label class="col-form-label font-weight-bold col-lg-2"
											for="sortNo">Sort No.<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="6"
												autocomplete="off" onchange="trim(this)"
												value="${route.sortNo > 0 ? route.sortNo : 1}" name="sortNo" id="sortNo"> <span
												class="validation-invalid-label text-danger"
												id="error_sortNo" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="frId">Franchise<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" multiple="multiple"
												data-fouc name="frId" id="frId" data-placholder="Select">

												<c:forEach items="${frList}" var="list" varStatus="count">
													<c:set value="0" var="flag" />
													<c:forEach items="${frIds}" var="frIds">
														<c:if test="${frIds == list.frId}">
															<c:set value="1" var="flag" />
														</c:if>
													</c:forEach>
													
													<c:choose>
															<c:when test="${flag==1}">
																<option value="${list.frId}" selected="selected">${list.frName}</option>
															</c:when>
															<c:otherwise>
																<option value="${list.frId}">${list.frName}</option>
															</c:otherwise>
													</c:choose>
													<%-- <option value="${list.frId}"
														${fn:contains(frIds, list.frId) ? 'selected' : ''}>${list.frName}</option> --%>

												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_frId" style="display: none;">This field is
												required.</span>
										</div>


										<label class="col-form-label font-weight-bold col-lg-2"
											for="routeTypeName">Type of Route <span
											class="text-danger"></span>:
										</label>
										 
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="routeTypeName" id="routeTypeName" data-placholder="Select Category">

												<c:forEach items="${routeTypeList}" var="routeTypeList" varStatus="count">
													<c:choose>
														<c:when test="${routeTypeList.routeTypeId==route.typeOfRoute}">
															<option selected value="${routeTypeList.routeTypeId}">${routeTypeList.routeTypeName}</option>
														</c:when>
														<c:otherwise>
															<option value="${routeTypeList.routeTypeId}">${routeTypeList.routeTypeName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_routeTypeName" style="display: none;">This field is
												required.</span>
										</div>
									</div>

									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="rouidDelveryId">Route Delivery <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="rouidDelveryId" id="rouidDelveryId" data-placholder="Select Category">

												<c:forEach items="${routeDelList}" var="routeDelList" varStatus="count">
													<c:choose>
														<c:when test="${routeDelList.rouidDelveryId==route.isDeliveryNo}">
															<option selected value="${routeDelList.rouidDelveryId}">${routeDelList.deliveryName}</option>
														</c:when>
														<c:otherwise>
															<option value="${routeDelList.rouidDelveryId}">${routeDelList.deliveryName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_rouidDelveryId" style="display: none;">This field is
												required.</span>
										</div>

								 
									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="isPrimeRoute">Is Prime Route <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="0" name="isPrimeRoute"
													id="type_y" ${route.isPrimeRoute==0 ? 'checked' : ''}>
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="1" name="isPrimeRoute" id="type_n"
													${route.isPrimeRoute==1 ? 'checked' : ''}> No
												</label>
											</div>
										</div>
									</div>

										<input type="hidden" id="btnType" name="btnType">

									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
									
										<c:if test="${isEdit==0}">
											<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn(1)">
												Save & Next<i class="icon-paperplane ml-2"></i>
											</button>
										</c:if>
									</div>
								</form>
							</div>
							<input type="hidden" value="${isEdit}" id="isEdit">
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
		
		function pressBtn(btnVal){
			$("#btnType").val(btnVal)
		}
		
		$('#routeKm').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		
		$('#sortNo').on('input', function() {
			 this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');
			});
		
		$("#routeCode").change(function() { // 1st
			var code = $("#routeCode").val();
			var routeId = $("#routeId").val();
			//alert(code)

			$.getJSON('${chkUnqRouteCode}', {
				code : code,
				routeId : routeId,
				ajax : 'true',
			}, function(data) {

				if (data.error == false) {
					$("#unq_route_code").show();
					$("#routeCode").val('');
					document.getElementById("city_code").focus();
				} else {
					$("#unq_route_code").hide();
				}
			});
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";
				
				if (!$("#routeName").val()) {
					isError = true;
					$("#error_routeName").show()
				} else {
					$("#error_routeName").hide()
				}

				if ($("#frId").val().length == 0) {
					isError = true;
					$("#error_frId").show()
				} else {
					$("#error_frId").hide()
				}
			 

				if (!$("#routeCode").val()) {
					isError = true;
					$("#error_routeCode").show()
				} else {
					$("#error_routeCode").hide()
				}

				if (!$("#routeKm").val() || $("#routeKm").val() == 0.0) {
					isError = true;
					$("#error_routeKm").show()
				} else {
					$("#error_routeKm").hide()
				}
 
				
				if (!$("#sortNo").val() || $("#sortNo").val() == 0.0) {
					isError = true;
					$("#error_sortNo").show()
				} else {
					$("#error_sortNo").hide()
				}
 
				if (!$("#routeTypeName").val()) {
					isError = true;
					$("#error_routeTypeName").show()
				} else {
					$("#error_routeTypeName").hide()
				}
				
				if (!$("#routeTypeName").val()) {
					isError = true;
					$("#error_rouidDelveryId").show()
				} else {
					$("#error_rouidDelveryId").hide()
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
										/* document
												.getElementById("submtbtn").disabled = true;
										if(isEdit==0){
										document
												.getElementById("submtbtn1").disabled = true;
										} */
										
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