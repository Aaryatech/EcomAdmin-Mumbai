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
.table caption+thead tr:first-child td, .table caption+thead tr:first-child th, .table colgroup+thead tr:first-child td, .table colgroup+thead tr:first-child th, .table thead:first-child tr:first-child td, .table thead:first-child tr:first-child th {
      border-top-width: 1px!important;  
}
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>
 <body>
<!-- <body class="sidebar-xs">
 -->



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
<div class="card-body">

					<div
						class="card-header bg-blue text-white d-flex justify-content-between">
						<span
							class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span>
						<!--  -->
						<c:if test="${addAccess==0}">
							<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/prdctEventConfig"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Configuration</a></span>
						</c:if>
					</div>

					<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
					<div class="table-responsive">
						<table class="table datatable-fixed-left_custom  table-bordered table-hover table-striped"
										width="100%" id="printtable2">
										
						<thead>
							<tr>
								<th>Sr. No.</th>
								<th>Event Name</th>
								<th>Period</th>
								<th>Time</th>
								<th>Active</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${eventList}" var="event" varStatus="count">
								<tr>
					<td>${count.index+1}</td>
					<td width="20%;">${event.eventName}</td>
					<td>${event.fromDate} to ${event.toDate}</td>		
					<td>${event.fromTime} - ${event.toTime}</td>				
					<td>${event.isActive==1 ? 'Yes' :event.isActive==0 ? 'No' : 'No'}</td>
					<td><c:if test="${editAccess==0}">
					<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/editEvent?eventId=${event.exVar1}"
													class="list-icons-item" title="Edit Product"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
											</c:if>
											
											&nbsp;
											&nbsp;
											&nbsp;
											<c:if test="${deleteAccess==0}">
											<div class="list-icons">
												<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${event.exVar1}"  title="" ><i class="icon-trash"></i></a>
											</div>
										</c:if>
											</td>		
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
	</div>
	<!-- /page content -->
<script>
	$('.datatable-fixed-left_custom').DataTable({

		columnDefs : [ {
			orderable : false,
			targets : [ 0 ]
		} ],
		//scrollX : true,
		scrollX : true,
		scrollY : '65vh',
		scrollCollapse : true,
		order:[],
		paging : false,
		fixedColumns : {
			leftColumns : 1,
			rightColumns : 0
		}

	});
	</script>

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
												location.href = "${pageContext.request.contextPath}/deleteEvent?eventId="
														+ uuid;

											}
										}
									});
						});
	</script>
</body>
</html>