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
				<!-- ColReorder integration -->
				<div class="card">

					<div
						class="card-header bg-blue text-white d-flex justify-content-between">
						<span
							class="font-size-sm text-uppercase font-weight-semibold card-title">
							${title}</span>
						<!--  -->
						<c:if test="${addAccess==0}">
							<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/showAddRoute"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add Route
							</a></span>
						</c:if>
					</div>

					<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

					<table class="table datatable-header-basic">
						<thead>
							<tr>
								<th width="10%">Sr. No.</th>
								<th>Name</th>
								<th>Code</th>
								<th>Type</th>
								<th>Delivery</th>
								<th>Km.</th>
								<th>Franchise</th>
								<th>Sort No.</th>

								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${routeList}" var="routeList" varStatus="count">
								<tr>
									<td>${count.index+1}</td>
									<td>${routeList.routeName}</td>
									<td>${routeList.routeCode}</td>
									<td>${routeList.routeTypeName}</td>
									<td>${routeList.deliveryName}</td>
									<td>${routeList.routeKm}</td>
									<td>${routeList.frName}</td>
									<td>${routeList.sortNo}</td>



									<td class="text-center"><c:if test="${editAccess==0}">
											<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/showEditRoute?routeId=${routeList.exVar1}"
													class="list-icons-item" title="Edit"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
										</c:if> <c:if test="${deleteAccess==0}">
											<div class="list-icons">
												<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${routeList.exVar1}" data-popup="tooltip"
													title="" data-original-title="Delete"><i
													class="icon-trash"></i></a>
											</div>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
												className : 'btn-danger'
											}
										},
										callback : function(result) {
											if (result) {
												location.href = "${pageContext.request.contextPath}/deleteRoute?routeId="
														+ uuid;

											}
										}
									});
						});
	</script>
</body>
</html>