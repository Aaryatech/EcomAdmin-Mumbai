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
									class="font-size-sm text-uppercase font-weight-semibold card-title">${title}-${cust.custName}</span>
								<!--  -->
								<span class="font-size-sm text-uppercase font-weight-semibold"><a
									class="card-title"
									href="${pageContext.request.contextPath}/showCustomerAddressList?custId=${custIdVal}"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>
	 

							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertNewCustomerAddress"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${cust.custId}" name="cust_id" id="cust_id"> <input
										type="hidden" class="form-control" value="${custDet.custDetailId}"
										name="custDetailId" id="custDetailId">



									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Address <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-9">
											<textarea class="form-control maxlength-badge-position"
											maxlength="500"	autocomplete="off" onchange="trim(this)" name="address"
												id="address">${custDet.address}</textarea>
											<span class="validation-invalid-label" id="error_address"
												style="display: none;">This field is required.</span>
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
											for="caption">Caption <span class="text-danger">*
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
											for="cityId">City<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="cityId" id="cityId" data-placholder="Select City">
												<option value="0"></option>
												<option value="1" ${custDet.cityId==1 ? 'selected' : '' }>Nasik</option>
												<option value="2" ${custDet.cityId==2 ? 'selected' : '' }>Mumbai</option>
												<option value="3" ${custDet.cityId==3 ? 'selected' : '' }>Pune</option>
												<%-- <c:forEach items="${desigList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.designationId==custDet.designationId}">
															<option selected value="${list.designationId}">${list.designation}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.designationId}">${list.designation}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach> --%>
											</select> <span class="validation-invalid-label text-danger"
												id="error_cityId" style="display: none;">This field
												is required.</span>
										</div>
								 
										<label class="col-form-label font-weight-bold col-lg-2"
											for="areaId">Area<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="areaId" id="areaId" data-placholder="Select Gender">
												<option value="0"></option>
												<option value="1" ${custDet.areaId==1 ? 'selected' : '' }>Area1</option>
												<option value="2" ${custDet.areaId==2 ? 'selected' : '' }>Area2</option>
												<option value="3" ${custDet.areaId==3 ? 'selected' : '' }>Area3</option>
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
												id="error_areaId" style="display: none;">This field
												is required.</span>
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
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#address").val()) {
					isError = true;
					$("#error_address").show()
				} else {
					$("#error_address").hide()
				}

				if (!$("#caption").val()) {
					isError = true;
					$("#error_caption").show()
				} else {
					$("#error_caption").hide()
				}

				if (!$("#landmark").val()) {
					isError = true;
					$("#error_landmark").show()
				} else {
					$("#error_landmark").hide()
				}

				if (!$("#cityId").val() || $("#cityId").val() == 0) {
					isError = true;
					$("#error_cityId").show()
				} else {
					$("#error_cityId").hide()
				}

				if (!$("#areaId").val() || $("#areaId").val() == 0) {
					isError = true;
					$("#error_areaId").show()
				} else {
					$("#error_areaId").hide()
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
	</script>


</body>
</html>