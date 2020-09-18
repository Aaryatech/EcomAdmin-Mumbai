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
	<c:url value="/getFilterConfigList" var="getFilterConfigList" />
	<c:url value="/getProductsByConfigTypeId"
		var="getProductsByConfigTypeId" />

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
									action="${pageContext.request.contextPath}/saveProductTaxCakeConfig"
									id="submitInsert" method="post">
									<!-- onsubmit="return validation()" -->
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="typeConfigId">Type <span class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												data-placeholder="Select Type" name="typeConfigId"
												id="typeConfigId">
												<option value="1">Tax</option>
												<option value="2">Return %</option>
												<option value="3">Cake Shape</option>
												<option value="4">Active</option>
												<option value="5">In-Active</option>
											</select>
										</div>

										<div id="filterDiv" class="col-lg-6">
											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-4"
													for="filterId">Filter Type<span class="text-danger">*</span>:
												</label>
												<div class="col-lg-8">
													<select class="form-control select-search" data-fouc
														data-placeholder="Select Type" name="filterId"
														id="filterId">
													</select> <span class="validation-invalid-label text-danger"
														id="error_filterId" style="display: none;">This
														field is required.</span>
												</div>
											</div>
										</div>

										<div id="returnDiv" style="display: none;" class="col-lg-6">
											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-4"
													for="filterId">Return Allowed<span
													class="text-danger">*</span>:
												</label>
												<div class="col-lg-8">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="returnPer" id="returnPer" maxlength="5"
														autocomplete="off" onchange="trim(this)"
														placeholder="Enter Return %"> <span
														class="validation-invalid-label text-danger"
														id="error_returnPer" style="display: none;">This
														field is required.</span>
												</div>
											</div>
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
													id="remove_radio"> Replace
												</label>
											</div>
										</div>


										<div class="col-lg-2"></div>
										<div class="col-lg-4">
											<button type="button" class="btn btn-primary" id="searchbtn">
												 <i class="fas fa-search"></i>Search
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
												<th style="padding: .5rem 0.5rem" id="returnPerVal"></th>	

											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Product Check Box.</span>
									<div class="text-center">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn"
											disabled="disabled">
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

							var radioConfig = radioConfig = $(
									"input[name='radioConfig']:checked").val();
							var typeConfigId = $("#typeConfigId").val();
							var filterId = 0;

							if (typeConfigId == 2) {
								document.getElementById("returnPerVal").innerHTML = "Return %";
								filterId = 0;

							} else {
								document.getElementById("returnPerVal").innerHTML = "";
								filterId = $("#filterId").val();
							}

							$('#config_product td').remove();
							$('#loader').show();
							$
									.getJSON(
											'${getProductsByConfigTypeId}',
											{

												typeConfigId : typeConfigId,
												filterId : filterId,
												radioConfig : radioConfig,
												ajax : 'true',
											},
											function(data) {
												$('#loader').hide();
												if (data == null) {
													alert("No data found.")
												}

												document
														.getElementById("submtbtn").disabled = false;
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
															if (typeConfigId==2) {
															tr1
															.append($(
																	'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;""></td>')
																	.html(
																			data.productList[j].retPer));
															}

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

		$("#typeConfigId")
				.on(
						'change',
						function() {

							var typeConfigId = $("#typeConfigId").val();

							if (typeConfigId == 2) {
								document.getElementById("returnDiv").style.display = "block";
								document.getElementById("filterDiv").style.display = "none";

							} else {
								document.getElementById("returnDiv").style.display = "none";
								document.getElementById("filterDiv").style.display = "block";

							}

							$('#loader').show();
							$
									.getJSON(
											'${getFilterConfigList}',
											{
												typeConfigId : typeConfigId,
												ajax : 'true',
											},
											function(data) {
												$('#loader').hide();
												///alert(JSON.stringify(data))

												$('#filterId').find('option')
														.remove().end()
												$("#filterId")
														.append(
																$("<option value=''>Select</option>"));

												for (var i = 0; i < data.length; i++) {
													$("#filterId")
															.append(
																	$(
																			"<option></option>")
																			.attr(
																					"value",
																					data[i].valueId)
																			.text(
																					data[i].valueName));

												}
												$("#filterId").trigger(
														"chosen:updated");

												/* --------------------------------------------------- */
												//	Replace With
												$('#replaceFilterId').find(
														'option').remove()
														.end()
												$("#replaceFilterId")
														.append(
																$("<option value=''>Select</option>"));

												for (var i = 0; i < data.length; i++) {
													$("#replaceFilterId")
															.append(
																	$(
																			"<option></option>")
																			.attr(
																					"value",
																					data[i].valueId)
																			.text(
																					data[i].valueName));

												}
												$("#replaceFilterId").trigger(
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
		$(document).ready(function($) {
			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if ($("#typeConfigId").val() == 2) {
					if (!$("#returnPer").val()) {
						isError = true;
						$("#error_returnPer").show()
					} else {
						$("#error_returnPer").hide()
					}
				} else {
					if (!$("#filterId").val()) {
						isError = true;
						$("#error_filterId").show()
					} else {
						$("#error_filterId").hide()
					}
				}

				/* if(!$('input[type=checkbox]').attr('checked')) {
					isError = true;
					 alert("UnChecked");
				}else{
					alert("Checked");
				} */
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

		$('#returnPer').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9.]/g, '').replace(
							/(\..*)\./g, '$1');
				});
	</script>
</body>
</html>