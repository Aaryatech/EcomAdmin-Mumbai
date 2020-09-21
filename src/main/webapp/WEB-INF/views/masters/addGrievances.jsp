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
	<c:url value="/getGrievanceInfo" var="getGrievanceInfo"></c:url>
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
									href="${pageContext.request.contextPath}/showGrievences"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertGrievanceInstruction"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control" name="grievances_id"
										id="grievances_id" value="${grievance.grievanceId}">
									
									<input type="hidden" class="form-control" value="${grievance.isParent}"
										name="isParent" id="isParent">

									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="city_name">Grievance Type<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="griev_type" id="griev_type">
												<option value="">Select Grievances</option>

												<c:forEach items="${grievList}" var="grievList"
													varStatus="count">
													<c:choose>
														<c:when
															test="${grievList.grevTypeId==grievance.grievenceTypeId}">
															<option selected value="${grievList.grevTypeId}">${grievList.caption}</option>
														</c:when>
														<c:otherwise>
															<option value="${grievList.grevTypeId}">${grievList.caption}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_griev_type" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="area_code">Grievance Caption<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="griev_cap" id="griev_cap" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${grievance.caption}"> <span
												class="validation-invalid-label text-danger" id="error_cap"
												style="display: none;">This field is required.</span> <span
												class="validation-invalid-label text-danger" id="unq_cap"
												style="display: none;">Grievance Instruction Caption
												Already Exist.</span>
										</div>
									</div>									
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="area_decp">Description<span class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<textarea class="form-control maxlength-badge-position"
												placeholder="Enter Grievances Type Instruction Description"
												id="griev_decp" name="griev_decp" autocomplete="off"
												maxlength="100" onchange="trim(this)">${grievance.description}</textarea>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="grievance"
													id="grievance_y" ${grievance.isActive==1 ? 'checked' : ''}>
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="grievance"
													id="grievance_n" ${grievance.isActive==0 ? 'checked' : ''}>
													In-Active
												</label>
											</div>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="allowCopy">Allow Copy <span class="text-danger">*
										</span>:
										</label>
										 <div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="allowCopy"
													id="copy_y" ${grievance.allowToCopy==1 ? 'checked' : ''}>
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0" name="allowCopy"
													id="copy_n" ${grievance.allowToCopy==0 ? 'checked' : ''}>
													No
												</label>
											</div>
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
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#griev_cap").val()) {
					isError = true;
					$("#error_cap").show()
				} else {
					$("#error_cap").hide()
				}

				if (!$("#griev_type").val()) {
					isError = true;
					$("#error_griev_type").show()
				} else {
					$("#error_griev_type").hide()
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

		$("#griev_cap").change(function() { // 1st
			var caption = $("#griev_cap").val();
			var grievancesId = $("#grievances_id").val();
			//alert(code)

			$.getJSON('${getGrievanceInfo}', {
				caption : caption,
				grievancesId : grievancesId,
				ajax : 'true',
			}, function(data) {

				if (data.error == false) {
					$("#unq_cap").show();
					$("#griev_cap").val('');
					document.getElementById("griev_cap").focus();
				} else {
					$("#unq_cap").hide();
				}
			});
		});

		$("#data_view_button")
				.click(
						function() {
							//blockThis();

							$
									.getJSON(
											'${getSingleCategory}',
											{
												ajax : 'true',
											},
											function(data) {
												//document.getElementById("g_name").innerHTML=" Category Name:" +data.catName;
												//document.getElementById("ifsc_code").innerHTML=" Bank Name: Bank of India";
												document
														.getElementById("mod_title").innerHTML = "Appending Ajax Return Data ";
												document
														.getElementById("f_name").value = data.catName;
												document
														.getElementById("l_name").value = "Thakur";
												//unBlock();
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
	</script>


</body>
</html>