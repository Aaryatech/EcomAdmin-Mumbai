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
									href="${pageContext.request.contextPath}/showDeliveryChargesList"
									style="color: white;"><i class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View
										List</a></span>
							</div>



							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/saveDeliveryCharge"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>


									<input type="hidden" class="form-control" name="charge_id"
										id="charge_id" value="${delCharge.chId}">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="group_name">Group Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-10">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="100"
												autocomplete="off" onchange="trim(this)" name="group_name"
												id="group_name" value="${delCharge.groupName}"> <span
												class="validation-invalid-label text-danger"
												id="error_group_name" style="display: none;">This field
												is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="min_km">MIN Km.<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)" name="min_km"
												id="min_km" value="${delCharge.minKm}"> <span
												class="validation-invalid-label text-danger"
												id="error_min_km" style="display: none;">This field
												is required.</span>												
										</div>										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="max_km">MAX Km.<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)" name="max_km"
												id="max_km" value="${delCharge.maxKm}"> <span
												class="validation-invalid-label text-danger"
												id="error_max_km" style="display: none;">This field
												is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="amt1">Delivery Charges <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)" name="amt1"
												id="amt1" value="${delCharge.amt1}"> <span
												class="validation-invalid-label text-danger"
												id="error_amt1" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="amt2">Above Free Delivery Min Order Amt<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)" name="amt2"
												id="amt2" value="${delCharge.amt2}"> <span
												class="validation-invalid-label text-danger"
												id="error_amt2" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-danger" id="submtbtn">
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

				if (!$("#group_name").val()) {
					isError = true;
					$("#error_group_name").show()
				} else {
					$("#error_group_name").hide()
				}

				if ($("#min_km").val() == "") {
					isError = true;
					$("#error_min_km").show()
				} else {
					$("#error_min_km").hide()
				}
				
				
				if ($("#max_km").val() == "") {
					isError = true;
					$("#error_max_km").show()
				} else {
					$("#error_max_km").hide()
				}
				

				if ($("#amt1").val() == "") {
					isError = true;
					$("#error_amt1").show()
				} else {
					$("#error_amt1").hide()
				}
				
				if ($("#amt2").val() == "") {
					isError = true;
					$("#error_amt2").show()
				} else {
					$("#error_amt2").hide()
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

		
		
		$('#min_km').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		
		$('#max_km').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		
		$('#amt1').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		
		$('#amt2').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		
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
	</script>

</body>
</html>