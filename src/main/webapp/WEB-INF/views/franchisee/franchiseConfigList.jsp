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
								<!--  -->
								<span class="font-size-sm text-uppercase font-weight-semibold"><a
									class="card-title"
									href="${pageContext.request.contextPath}/showOfferConfigurationList"
									style="color: white;"><i class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View
										List</a></span>
							</div>
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

							<div class="card-body">
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>
								<form
									action="${pageContext.request.contextPath}/configFranchiseList"
									id="configFranchiseList">





									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="frId">Franchisee<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search"
												multiple="multiple" data-fouc name="frId" id="frId"
												data-placholder="Select ">
												<option value="0" ${fn:contains(frIds, 0) ? 'selected' : ''}>All</option>
												<c:forEach items="${frList}" var="list" varStatus="count">

													<option value="${list.frId}"
														${fn:contains(frIds, list.frId) ? 'selected' : ''}>${list.frName}</option>

												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_frId" style="display: none;">This field is
												required.</span>
										</div>


										<label class="col-form-label font-weight-bold col-lg-2"
											for="configId">Configuration<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select" multiple="multiple"
												data-fouc name="configId" id="configId"
												data-placholder="Select ">


												<option value="0"
													${fn:contains(configIds, 0) ? 'selected' : ''}>All</option>


												<c:forEach items="${configList}" var="list"
													varStatus="count">

													<option value="${list.configHeaderId}"
														${fn:contains(configIds, list.configHeaderId) ? 'selected' : ''}>${list.configName}</option>

												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_configId" style="display: none;">This field
												is required.</span>
										</div>
									</div>
									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="frId">Order By<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="orderBy" id="orderBy" data-placholder="Select ">
												<option value=""></option>
												<option value="1" ${orderBy==1 ? 'selected' : '' }>Configuration</option>
												<option value="2" ${orderBy==2 ? 'selected' : '' }>Franchise</option>
											</select> <span class="validation-invalid-label text-danger"
												id="error_orderBy" style="display: none;">This field
												is required.</span>
										</div>


										<div class="col-lg-2">
											<button type="submit" class="btn btn-primary">
												Search <i class="icon-paperplane ml-2"></i>
											</button>
										</div>
									</div>

								</form>

								<form
									action="${pageContext.request.contextPath}/deleteFrConfiguration"
									id="deleteFrConfiguration" method="post">




									<table class="table ddatatable-header-basic" id="printtable1">
										<thead>
											<tr>
												<th>Sel All<input type="checkbox" name="selAll"
													id="selAll" /></th>
												<th>Sr.No.</th>
												<th>Franchise Name</th>
												<th>Franchise Code</th>
												<th>City</th>
												<th>Route</th>
												<th>Configuration Name</th>
												<th>Action</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${frConfigList}" var="frConfigList"
												varStatus="count">

												<tr>
													<td><input type="checkbox"
														id="frachaseConfigId${frConfigList.frachaseConfigId}"
														value="${frConfigList.frachaseConfigId}"
														name="frachaseConfigId" class="select_all"></td>
													<td>${count.index+1})</td>
													<td>${frConfigList.frName}</td>
													<td>${frConfigList.frCode}</td>
													<td>${frConfigList.frCity}</td>
													<td>${frConfigList.route}</td>
													<td>${frConfigList.configName}</td>
													<td class="text-center"><c:if test="${deleteAccess==0}">
														<div class="list-icons"> 
															<a href="javascript:void(0)"
																class="list-icons-item text-danger-600 bootbox_custom"
																data-uuid="${frConfigList.frachaseConfigId}" data-popup="tooltip"
																title="" data-original-title="Delete"><i
																class="icon-trash"></i></a>

														</div>
													</c:if></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
									<div class="text-center">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
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
		//Custom bootbox dialog
		$('.bootbox_custom')
				.on(
						'click',
						function() {
							var uuid = $(this).data("uuid") // will return the number 123
							bootbox
									.confirm({
										title : 'Confirm ',
										message : 'Are you sure you want to delete selected records ?',
										buttons : {
											confirm : {
												label : 'Yes',
												className : 'btn-success'
											},
											cancel : {
												label : 'Cancel',
												className : 'btn-link'
											}
										},
										callback : function(result) {
											if (result) {
												location.href = "${pageContext.request.contextPath}/deleteFrConfig?configId="
														+ uuid;

											}
										}
									});
						});
	</script>

	<script>
		$(document).ready(

				function() {

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);

							});
				});
	</Script>

	<script type="text/javascript">
		$(document)
				.ready(
						function($) {

							$("#deleteFrConfiguration")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												var checked = $("#deleteFrConfiguration input:checked").length > 0;

												var count = $('#printtable1 tr').length;
												//alert(checked);
												if (!checked || count <= 1) {
													$("#error_chks").show()
													isError = true;
												} else {
													$("#error_chks").hide()
													isError = false;
												}

												if (!$("#frId").val()) {
													isError = true;
													$("#error_frId").show()
												} else {
													$("#error_frId").hide()
												}
												if (!$("#configId").val()) {
													isError = true;
													$("#error_configId").show()
												} else {
													$("#error_configId").hide()
												}

												if (!$("#orderBy").val()) {
													isError = true;
													$("#error_orderBy").show()
												} else {
													$("#error_orderBy").hide()
												}

												if (!isError) {
													var x = true;
													if (x == true) {
														document
																.getElementById("submtbtn").disabled = true;
														return true;
													}
												}

												return false;

											});
						});
	</script>

	<script type="text/javascript">
		function getConfiguration(catId) {

			if (catId > 0) {

				$
						.getJSON(
								'${getConfigByCatId}',
								{
									catId : catId,
									ajax : 'true',
								},

								function(data) {

									//alert("Data " + JSON.stringify(data));
									var x = $
									{
										configId
									}
									;
									var html;
									var p = 0;
									var q = "Please Select";
									html += '<option  value="'+p+'" >' + q
											+ '</option>';
									html += '</option>';

									var len = data.length;
									for (var i = 0; i < len; i++) {

										if (x == data[i].configHeaderId) {
											html += '<option  Selected value="' + data[i].configHeaderId + '">'
													+ data[i].configName
													+ '</option>';
										} else {
											html += '<option value="' + data[i].configHeaderId + '">'
													+ data[i].configName
													+ '</option>';
										}

									}

									$('#configId').html(html);
									$("#configId").trigger("chosen:updated");

								});

			}
		}
	</script>



</body>
</html>