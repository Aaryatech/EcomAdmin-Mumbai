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
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">
	<c:url value="/getConfigByCatId" var="getConfigByCatId"></c:url>

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
							
								<%-- <span class="font-size-sm text-uppercase font-weight-semibold"><a
									class="card-title"
									href="${pageContext.request.contextPath}/configFranchiseList"
									style="color: white;"><i class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View
										List</a></span> --%>
							
							</div>
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
							<div class="card-body">
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>
								<form
									action="${pageContext.request.contextPath}/saveMutiFrCharges"
									id="submitInsert" method="post">


									<input type="hidden" class="form-control" name="charge_id"
										id="charge_id" value="${charges.chargeId}">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="dates">Date<span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control daterange-basic_new"
												name="dates" id="dates" autocomplete="off">
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="surcharge">Surcharge Fees<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												name="surcharge" id="surcharge"
												value="${charges.surchargeFee}" autocomplete="off">
											<span class="validation-invalid-label text-danger"
												id="error_surcharge" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="packing">Packing Charges<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												" name="packing" id="packing" value="${charges.packingChg}"
												autocomplete="off"> <span
												class="validation-invalid-label text-danger"
												id="error_packing" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="handling">Handling Charges<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												" name="handling" id="handling"
												value="${charges.handlingChg}" autocomplete="off"><span
												class="validation-invalid-label text-danger"
												id="error_handling" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="extra">Extra Charges<span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												" name="extra" id="extra" value="${charges.extraChg}"
												autocomplete="off"><span
												class="validation-invalid-label text-danger"
												id="error_extra" style="display: none;">This field is
												required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="round_off">Round Off Amt.<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												" name="round_off" id="round_off"
												value="${charges.roundOffAmt}" autocomplete="off"><span
												class="validation-invalid-label text-danger"
												id="error_round_off" style="display: none;">This
												field is required.</span>
										</div>
									</div>





									<!--Table-->
									<table class="table ddatatable-header-basic" id="printtable1">
										<thead>
											<tr>
												<th>Sel All<input type="checkbox" name="selAll"
													id="selAll" /></th>
												<th>Sr.No.</th>
												<th>Franchise Code</th>
												<th>Franchise Name</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${frList}" var="frList" varStatus="count">

												<tr>
													<td><input type="checkbox" id="frId${frList.frId}"
														value="${frList.frId}" name="frId" class="select_all"></td>
													<td>${count.index+1})</td>
													<td>${frList.frCode}</td>
													<td>${frList.frName}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<input type="hidden" id="btnType" name="btnType"> <span
										class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
									<div class="text-center">
										<br>
										<!-- <button type="submit" class="btn btn-primary" id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
										</button> -->
										<button type="submit" class="btn btn-primary" id="submtbtn"
											onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>

										<button type="submit" class="btn btn-primary" id="submtbtn1"
											onclick="pressBtn(1)">
											Save & Next<i class="icon-paperplane ml-2"></i>
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

	<script>
		function pressBtn(btnVal) {
			$("#btnType").val(btnVal)
		}
		$(document).ready(

				function() {

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);

							});
				});

		$("#configId").change(function() {
			if ($("#configId").val() == 0 || !$("#configId").val()) {
			} else {
				document.getElementById("search_button").click();
			}
		});
	</Script>

	<script type="text/javascript">
		$(document).ready(function($) {
			$("#configFranchise").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if ($("#catId").val() == 0 || !$("#catId").val()) {
					isError = true;
					$("#error_category").show()
					$("#catId").focus();
				} else {
					$("#error_category").hide()
				}

				if ($("#configId").val() == 0 || !$("#configId").val()) {
					isError = true;
					$("#error_configId").show()
					$("#configId").focus();
				} else {
					$("#error_configId").hide()
				}

				if (!isError) {
					var x = true;
					if (x == true) {
						//document
						//.getElementById("subBtn").disabled = true;
						return true;
					}
				}

				return false;

			});
		});

		$(document)
				.ready(
						function($) {

							$("#submitInsert")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												var checked = $("#submitInsert input:checked").length > 0;

												var count = $('#printtable1 tr').length;
												//alert(checked);
												if (!checked || count <= 1) {
													$("#error_chks").show()
													isError = true;
												} else {
													$("#error_chks").hide()
													isError = false;
												}

												if (!$("#surcharge").val()) {
													isError = true;
													$("#error_surcharge").show()
												} else {
													$("#error_surcharge").hide()
												}

												if ($("#packing").val() == "") {
													isError = true;
													$("#error_packing").show()
												} else {
													$("#error_packing").hide()
												}

												if (!$("#handling").val()) {
													isError = true;
													$("#error_handling").show()
												} else {
													$("#error_handling").hide()
												}

												if (!$("#extra").val()) {
													isError = true;
													$("#error_extra").show()
												} else {
													$("#error_extra").hide()
												}

												if (!$("#round_off").val()) {
													isError = true;
													$("#error_round_off").show()
												} else {
													$("#error_round_off").hide()
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
	</script>

	<script type="text/javascript">
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
	

	$('#surcharge').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		})
		
	$('#packing').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#handling').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#extra').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#round_off').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	
	$('.daterange-basic_new').daterangepicker({
		applyClass : 'bg-slate-600',

		cancelClass : 'btn-light',
		locale : {
			format : 'DD-MM-YYYY',
			separator : ' to '
		}
	});
	</script>
</body>
</html>