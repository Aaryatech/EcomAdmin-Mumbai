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
	<c:url value="/getFilterByFilterType" var="getFilterByFilterType" />
	<c:url value="/getProductsByFilterIds" var="getProductsByFilterIds" />

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
				<!-- ColReorder integration -->


				<div class="card">
					<div
						class="card-header bg-blue text-white d-flex justify-content-between">
						<span
							class="font-size-sm text-uppercase font-weight-semibold card-title">
							${title}</span>
						<!--  -->

						<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/showConfigProductAndEvents"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp; Show Configured Products And Event</a></span> 

					</div>
					<div class="card-body">
						<p class="desc text-danger fontsize11">Note : * Fields are
							mandatory.</p>
							
							<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

						<div class="row">
							<div class="col-md-12">

								<form
									action="${pageContext.request.contextPath}/saveEventConfiguration"
									id="submitInsert" method="post" enctype="multipart/form-data">
									
									<input type="hidden" value="${festiveEvent.eventId}" name="eventId" id="eventId">
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="filterTypeId">Event Name <span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" name="eventName"
												id="eventName" maxlength="150" autocomplete="off"
												onchange="trim(this)" value="${festiveEvent.eventName}"> 
											<span class="validation-invalid-label text-danger"
												id="error_eventName" style="display: none;">This field is
												required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="filterId">Status<span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${festiveEvent.eventId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="active_event" id="tax_y"
													${festiveEvent.isActive==1 ? 'checked' : ''}> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="active_event"
													id="tax_n" ${festiveEvent.isActive==0 ? 'checked' : ''}>
													In-Active
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="active_event" id="tax_y"> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="active_event"
													id="tax_n">
													In-Active
												</label>
											</div>											
											</c:otherwise>
										</c:choose>
											
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="eventDates">Date<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${festiveEvent.eventId>0}">
												<input type="text" class="form-control daterange-basic_new"
												autocomplete="off" value="${festiveEvent.fromDate} to ${festiveEvent.toDate}"
												name="eventDates" id="eventDates">
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control daterange-basic_new"
												autocomplete="off" 
												name="eventDates" id="eventDates">
											</c:otherwise>
										</c:choose>
											 <span
												class="validation-invalid-label text-danger"
												id="error_eventDates" style="display: none;">This
												field is required.</span>
										</div>
									
												<label class="col-form-label font-weight-bold col-lg-2"
													for="fromTime">Time<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-1">
													<input class="form-control" type="time" name="fromTime"
														id="fromTime" value="${festiveEvent.fromTime}" style="width: 117%;">
														
													<span
												class="validation-invalid-label text-danger"
												id="error_fromTime" style="display: none; width: 136%;">required</span>	
												</div>

												<label class="col-form-label font-weight-bold col-lg-1"
													for="toTime" style="margin-left: 2%; margin-right: -3%;">To<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-1">
													<input class="form-control" type="time" name="toTime"
														id="toTime" value="${festiveEvent.toTime}" style="width: 117%;"><span
												class="validation-invalid-label text-danger"
												id="error_toTime" style="display: none; width: 136%;">required</span>
												</div>

											</div>
											
										<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="language_name">Description<span
											class="text-danger"> </span>:
										</label>
										<div class="col-lg-10">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="description" id="description" maxlength="250"
												autocomplete="off" onchange="trim(this)"
												value="${festiveEvent.description}"> <span
												class="validation-invalid-label text-danger"
												id="error_taxDesc" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="primary_img">
											Image: </label>
										<div class="col-lg-4">
											<div class="input-group-btn  ">
												<img id="output" width="150" src="${imgPath}${festiveEvent.exVar2}"/>
												<input type="file" class="btn btn-primary" accept="image/*" name="doc" id="doc" 
												accept=".jpg,.png,.gif,.jpeg,.bmp" onchange="loadFile(event)"><span
													class="form-text text-muted">Only
													.jpg,.png,.gif,.jpeg</span>
													
													 <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${festiveEvent.exVar2}">
											</div>
										</div>
										
										</div>


									<div align="center" id="loader" style="display: none;">

										<span>
											<h4>
												<font color="#343690">Loading</font>
											</h4>
										</span> <span class="l-1"></span> <span class="l-2"></span> <span
											class="l-3"></span> <span class="l-4"></span> <span
											class="l-5"></span> <span class="l-6"></span>
									</div>
									<!-- -------------------------------------------------------------- -->

									<table class="table" id="config_event">
										<thead>
											<tr>
												<th style="padding: .5rem 0.5rem"></th>
												<th style="padding: .5rem 0.5rem" colspan=2>Category
													Wise Product</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${catList}" var="cat" varStatus="count">
												<tr style="background: #03a9f4;">
													<td
														style="padding: 7px; line-height: 0; border-top: 1px solid #ddd;">
														<input type="checkbox" name="catCheck${cat.catId}" id="catCheck${cat.catId}"
														 value="${cat.catId}" onclick="selAllItems(${cat.catId})">
													</td>
													<td
														style="padding: 12px; line-height: 0; border-top: 1px solid #ddd;"></td>
													<td
														style="padding: 12px; line-height: 0; border-top: 1px solid #ddd; color: #fff; font-weight: bold;"
														colspan=2>${cat.catName}</td>
												</tr>
													<c:forEach items="${productList}" var="prod" varStatus="cnt">
														<tr>
															
															<c:if test="${cat.catId==prod.prodCatId}">
															<td
																style="padding: 7px; line-height: 0; border-top: 0px;"></td>
																
																<c:set value="0" var="flag" />
																<c:forEach items="${prodctIdsIds}" var="prodctIdsIds">
																	<c:if test="${prodctIdsIds == prod.productId}">
																		<c:set value="1" var="flag" />
																	</c:if>
																</c:forEach>

															<c:choose>
																<c:when test="${flag==1}">
																	<td
																		style="padding: 7px; line-height: 0; border-top: 1px solid #ddd;">
																		<input type="checkbox" name="productId" id="chk"
																		checked="checked" value="${prod.productId}"
																		class="chkcls${cat.catId}">
																	</td>
																</c:when>
																<c:otherwise>
																	<td
																		style="padding: 7px; line-height: 0; border-top: 1px solid #ddd;">
																		<input type="checkbox" name="productId" id="chk"
																		value="${prod.productId}" class="chkcls${cat.catId}">
																	</td>
																</c:otherwise>
															</c:choose>



															<td
																	style="padding: 7px; line-height: 0; border-top: 1px solid #ddd;">
																	<!-- <input type="text" readonly="readonly"
																	value="" > -->${prod.productName}
																</td>
																
																<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd; display: none;">
																	<input type="text" value="${flag}" class="chkVal${cat.catId}">
																</td>
															</c:if> 
														</tr>
													</c:forEach>
												
											</c:forEach>
										</tbody>
									</table>
									
									<div class="form-group row">
										<div class="col-lg-10">
											<span class="validation-invalid-label" id="error_checkbox"
												style="display: none;">Check Minimum One Checkbox</span>
										</div>
									</div>
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Product Check Box.</span>
									<div class="text-center">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
										</button>
									</div>
								</form>
								<!-- -------------------------------------------------------------- -->

							</div>
						</div>
					</div>
				</div>
				<!-- /colReorder integration -->
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
	function selAllItems(id) {

		var checkBox = document.getElementById("catCheck" + id);
		//alert(checkBox.checked);

		if (checkBox.checked == true) {

			$(".chkcls" + id)
					.each(
							function(counter) {

								document.getElementsByClassName("chkcls"
										+ id)[counter].checked = true;

							});

		} else {

			$(".chkcls" + id)
					.each(
							function(counter) {

								if (document
										.getElementsByClassName("chkVal"
												+ id)[counter].value == 0) {
									document
											.getElementsByClassName("chkcls"
													+ id)[counter].checked = false;

								}
							});

		}

	}
	
	
	
	 var loadFile = function(event) {
		 document.getElementById('output').style.display="none";
		 try {
			var image = document.getElementById('output');
			image.src = URL.createObjectURL(event.target.files[0]);
			document.getElementById('output').style="display:block"
		 } catch(err) {
			 console.log(err);
			}
		};

		/* --------------------------------------------------------------------------- */
		
	</script>
	<script type="text/javascript">
		function validation() {

			var flag = 0;

			$(".chkcls")
					.each(
							function(counter) {
								if (document.getElementsByClassName("chkcls")[counter].checked) {
									flag = 1;
								}
							});

			if (flag == 0) {
				alert("Please select product for configuration.");
				return false;
			}

		}
		
		$(document).ready(function($) {
			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#eventName").val()) {
					isError = true;
					$("#error_eventName").show()
				} else {
					$("#error_eventName").hide()
				}
				
				
				if (!$("#fromTime").val()) {
					isError = true;
					$("#error_fromTime").show()
				} else {
					$("#error_fromTime").hide()
				}
				
				if (!$("#toTime").val()) {
					isError = true;
					$("#error_toTime").show()
				} else {
					$("#error_toTime").hide()
				}
				
				var checkboxes = $("input[type='checkbox']");

				if (!checkboxes.is(":checked")) {

					isError = true;

					$("#error_checkbox").show()

				} else {
					$("#error_checkbox").hide()
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
		
		$('.datepickerclass').daterangepicker({
			"autoUpdateInput" : true,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
		
		$('.daterange-basic_new').daterangepicker({
			applyClass : 'bg-slate-600',

			cancelClass : 'btn-light',
			locale : {
				format : 'DD-MM-YYYY',
				separator : ' to '
			}
		});
		
		$('.maxlength-badge-position').maxlength({
		    alwaysShow: true,
		    placement: 'top'
		});
	</script>
</body>
</html>