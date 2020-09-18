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

						<%-- <span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/addNewCity"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add City</a></span> --%>

					</div>
					<div class="card-body">
						<p class="desc text-danger fontsize11">Note : * Fields are
							mandatory.</p>

						<div class="row">
							<div class="col-md-12">

								<form
									action="${pageContext.request.contextPath}/saveProductConfiguration"
									id="submitInsert" method="post"><!-- onsubmit="return validation()" -->
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="filterTypeId">Type <span class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												data-placeholder="Select Type" name="filterTypeId"
												id="filterTypeId">
												<option value="2">Same Day Time Slot</option>
												<option value="4">Flavour Type</option>
												<option value="6">Event Type</option>
												<option value="7">Tags Type</option>
											</select>


											<%-- <select class="form-control select-search" data-fouc
										data-placeholder="Select Type" name="filterTypeId"
										id="filterTypeId">
										<c:forEach items="${filterType}" var="filterType">
											<option value="${filterType.filterTypeId}">${filterType.filterTypeName}</option>
										</c:forEach>
									</select> --%>
											<span class="validation-invalid-label text-danger"
												id="error_city" style="display: none;">This field is
												required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="filterId">Filter Type<span class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												data-placeholder="Select Type" name="filterId" id="filterId">
											</select>
										</div>
									</div>


									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="filterTypeId">Option <span class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="radioConfig" id="add_radio"> Add
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="radioConfig"
													id="remove_radio"> Remove
												</label>
											</div>
										</div>


										<div class="col-lg-2"></div>
										<div class="col-lg-4">
											<button type="button" class="btn btn-primary" id="searchbtn">
												Search <i class="icon-paperplane ml-2"></i>
											</button>
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

									<table class="table" id="config_product">
										<thead>
											<tr>
												<th style="padding: .5rem 0.5rem"></th>
												<th style="padding: .5rem 0.5rem" colspan=2>Category
													Wise Product</th>

											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Product Check Box.</span>
									<div class="text-center">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn" disabled="disabled">
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
		$("#searchbtn")
				.click(
						function() {
							var optionVal = $(
									"input[name='radioConfig']:checked").val();
							var filterId = $("#filterId").val();
							var filterTypeId = $("#filterTypeId").val();

							$('#config_product td').remove();
							$('#loader').show();
							$
									.getJSON(
											'${getProductsByFilterIds}',
											{
												optionVal : optionVal,
												filterId : filterId,
												filterTypeId : filterTypeId,
												ajax : 'true',
											},
											function(data) {
												$('#loader').hide();
												if(data==null){
													alert("No data found.")
												}
												document.getElementById("submtbtn").disabled = false;
												/* alert(JSON
														.stringify(data.categoryList)); */
												for (i = 0; i < data.categoryList.length; i++) {

													var tr = $('<tr style="background:#03a9f4;"></tr>');
													tr
															.append($(
																	'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;"></td>')
																	.html(
																			'<input type="checkbox" name="catCheck'
																					+ data.categoryList[i].catId
																					+ '" id="catCheck'
																					+ data.categoryList[i].catId
																					+ '" value="'
																					+ data.categoryList[i].catId
																					+ '" onclick="selAllItems('
																					+ data.categoryList[i].catId
																					+ ')">'));

													tr
															.append($(
																	'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																	.html(""));

													tr
															.append($(
																	'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd; color:#fff; font-weight: bold;" colspan=2></td>')
																	.html(
																			data.categoryList[i].catName));

													$('#config_product tbody')
															.append(tr);

													for (j = 0; j < data.productList.length; j++) {

														var tr1 = $('<tr></tr>');
														tr1
																.append($(
																		'<td style="padding: 7px; line-height:0; border-top:0px;"></td>')
																		.html(
																				""));

														if (data.categoryList[i].catId == data.productList[j].prodCatId) {

															if (data.productList[j].checked) {
																tr1
																		.append($(
																				'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;""></td>')
																				.html(
																						'<input type="checkbox" checked name="chk" id="chk" value="'+
												data.productList[j].productId
												+'" class="chkcls'+data.categoryList[i].catId+'">'));
															} else {
																tr1
																		.append($(
																				'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;""></td>')
																				.html(
																						'<input type="checkbox" name="chk" id="chk" value="'+
											data.productList[j].productId
											+'" class="chkcls'+data.categoryList[i].catId+'">'));
															}

															tr1
																	.append($(
																			'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;""></td>')
																			.html(
																					data.productList[j].productName));

															tr1
																	.append($(
																			'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd; display: none;"></td>')
																			.html(
																					'<input type="text" value="'+data.productList[j].checked+'" class="chkVal'+data.categoryList[i].catId+'">'));

															$(
																	'#config_product tbody')
																	.append(tr1);
														}
													}
												}

											});
						});

		/* --------------------------------------------------------------------------- */
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
		/* ---------------------------------------------------------------------------- */

		$("#filterTypeId")
				.on(
						'change',
						function() {

							var filterTypeId = $("#filterTypeId").val();
							$('#loader').show();
							$
									.getJSON(
											'${getFilterByFilterType}',
											{
												filterTypeId : filterTypeId,
												ajax : 'true',
											},
											function(data) {
												$('#loader').hide();
												//	alert(JSON.stringify(data.filterIds))

												$('#filterId').find('option')
														.remove().end()
												$("#filterId")
														.append(
																$("<option value=''>Select</option>"));

												for (var i = 0; i < data.filterList.length; i++) {

													var flag = 0;
													for (var j = 0; j <= data.filterIds.length; j++) {

														if (data.filterList[i] == data.filterIds[j]) {
															flag = 1;
														}
													}
													if (flag == 1) {
														$("#filterId")
																.append(
																		$(
																				"<option selected></option>")
																				.attr(
																						"value",
																						data.filterList[i].filterId)
																				.text(
																						data.filterList[i].filterName));
													} else {
														$("#filterId")
																.append(
																		$(
																				"<option></option>")
																				.attr(
																						"value",
																						data.filterList[i].filterId)
																				.text(
																						data.filterList[i].filterName));
													}

												}
												$("#filterId").trigger(
														"chosen:updated");
											});
						});
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
		/* $(document).ready(function($) {
			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";
				alert("HI=");
				
				if(!$('input[type=checkbox]').attr('checked')) {
					isError = true;
   					 alert("UnChecked");
				}else{
					alert("Checked");
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
		}); */
	</script>
</body>
</html>