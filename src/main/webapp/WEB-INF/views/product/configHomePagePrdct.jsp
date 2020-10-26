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

<body class="sidebar-xs" onload="configHomePrdct();">

	<c:url value="/getConfigProductsByStatusId"
		var="getConfigProductsByStatusId" />

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
							href="${pageContext.request.contextPath}/showHomePagePrdctConfig"
							style="color: white;"><i class="icon-add-to-list ml-2"
								style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Product
								Home Page List</a></span>

					</div>
					<div class="card-body">
						<p class="desc text-danger fontsize11">Note : * Fields are
							mandatory.</p>

						<div class="row">
							<div class="col-md-12">
								<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
								<form
									action="${pageContext.request.contextPath}/configurPrdctHomePage"
									id="submitInsert" method="post">
									<input type="hidden" class="form-control"
										name="homePageStatusId" id="homePageStatusId"
										value="${homePage.homePageStatusId}">

									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="filterId">Status Type<span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<c:choose>
												<c:when test="${isEdit==1}">
													
													<input type="text"
														class="form-control maxlength-badge-position"
														autocomplete="off" onchange="trim(this)"
														value="${homePage.exVar2}" readonly="readonly"> 
												
												<input type="hidden" value="${homePage.statusId}" name="filterStatus" 
												id="filterStatus" class="form-control">
												
												</c:when>
												<c:otherwise>
													<select class="form-control select-search" data-fouc
														data-placeholder="Select Type" name="filterStatus"
														id="filterStatus" ><!-- onchange="configHomePrdct()" -->
														<option value=""></option>
														<c:forEach items="${filterList}" var="filterList">
															<c:set value="0" var="flag" />
															<c:forEach items="${homePageList}" var="list">
																<c:if test="${list.statusId == filterList.filterId}">
																	<c:set value="1" var="flag" />
																</c:if>
															</c:forEach>

															<c:choose>
																<c:when test="${flag==1}">
																	<option value="${filterList.filterId}"
																		disabled="disabled">${filterList.filterName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${filterList.filterId}">${filterList.filterName}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>


														<%-- <c:forEach items="${filterList}" var="filterList">
													<option value="${filterList.filterId}">${filterList.filterName}</option>
												</c:forEach> --%>
													</select>
												</c:otherwise>
											</c:choose>
											<span
												class="validation-invalid-label text-danger"
												id="error_filterStatus" style="display: none;">This
												field is required.</span>
										</div>
										
										<div class="col-lg-2"></div>
										<div class="col-lg-4">
											<button type="button" class="btn btn-primary" id="searchbtn" onclick="configHomePrdct()">
												Search <i class="icon-paperplane ml-2"></i>
											</button>
										</div>										
									</div>


									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Is Active<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${homePage.homePageStatusId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="activeStat" id="radio_y"
													${homePage.isActive==1 ? 'checked' : ''}> Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="activeStat"
													id="radio_n" ${homePage.isActive==0 ? 'checked' : ''}>
													No
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="activeStat" id="radio_y"> Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="activeStat"
													id="radio_n">
													No
												</label>
											</div>
											</c:otherwise>
										</c:choose>											
										</div>

										<div id="sortDiv" class="col-lg-6" style="display: none;">
											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="statusSortNo">Sort No.<span
													class="text-danger"></span>:
												</label>
												<div class="col-lg-8">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="statusSortNo" id="statusSortNo" maxlength="5"
														autocomplete="off" onchange="trim(this)" value="${homePage.sortNo > 0 ? homePage.sortNo : 1}"> <span
														class="validation-invalid-label text-danger"
														id="error_statusSortNo" style="display: none;">This
														field is required.</span>
												</div>
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

									<table class="table" id="config_product">
										<thead>
											<tr>
												<th style="padding: .5rem 0.5rem"></th>
												<th style="padding: .5rem 0.5rem" colspan=2>Category
													Wise Product</th>
												<th>Sort No</th>
													


											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>									
									<input type="hidden" id="isEdit" name="isEdit" value="${isEdit}">
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Product Check Box.</span>
									<div class="text-center">
									<input type="hidden" id="btnType" name="btnType">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										<c:if test="${homePage.homePageStatusId==0}">										
											<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn(1)">
												Save & Next<i class="icon-paperplane ml-2"></i>
											</button>
										</c:if>
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
	function pressBtn(btnVal){
		$("#btnType").val(btnVal)
	}
	
		function configHomePrdct() {
							
							var filterStatus = $("#filterStatus").val();
							
						//	alert(filterStatus)
							
							$('#config_product td').remove();
							$('#loader').show();
							$
									.getJSON(
											'${getConfigProductsByStatusId}',
											{

												filterStatus : filterStatus,
												ajax : 'true',
											},
											function(data) {
												$('#loader').hide();
												if (data == null) {
													alert("No data found.")
												}
												document
														.getElementById("submtbtn").disabled = false;

												//alert(JSON
												//		.stringify(data.categoryList));

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

															if (data.productList[j].isChecked==1) {
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
																					'<input type="text" value="'+data.productList[j].sortNo+'" class="chkVal'+data.categoryList[i].catId+'">'));
															/* '<input type="text" value="'+data.productList[j].checked+'" class="chkVal'+data.categoryList[i].catId+'">' */

															tr1
																	.append($(
																			'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;"></td>')
																			.html(
																					'<input type="text" name="prdctSortNo'
																							+ data.productList[j].productId
																							+ '" id="prdctSortNo'
																							+ data.productList[j].productId
																							+ '" value="'
																							+ data.productList[j].sortNo
																							+ '")"'
																							+ 'class="form-control maxlength-badge-position" maxlength="5">'));
															
															$(
																	'#config_product tbody')
																	.append(tr1);
														}
													}
												}

											});
						}

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

		$("#filterStatus").on('change', function() {

			if (!$("#filterStatus").val()) {
				document.getElementById("sortDiv").style.display = "none";
			} else {
				document.getElementById("sortDiv").style.display = "block";
			}
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
				//alert("Please select product for configuration.");
				return false;
			}

		}
		 $(document).ready(function($) {
			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#filterStatus").val() || $("#filterStatus").val()==0) {
					isError = true;
					$("#error_filterStatus").show()
				} else {
					$("#error_filterStatus").hide()
				}
				
				if (!$("#statusSortNo").val() || $("#filterStatus").val()==0) {
					isError = true;
					$("#error_statusSortNo").show()
				} else {
					$("#error_statusSortNo").hide()
				}
				
				/* if(!validation()){
					isError = true;
					$("#error_chks").show();
				}else{
					$("#error_chks").hide();
				} */

				
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