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
						<%-- <c:if test="${addAccess==0}">
							<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/newFranchise/0"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add Franchise</a></span>
						</c:if> --%>
					</div>
<div class="card-body">
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
	<form
									action="${pageContext.request.contextPath}/generateFrDataJSON"
									id="dataSyncForm" method="post"
									>
					<table id="printtable1" class="table datatable-fixed-left_custom table-bordered table-hover table-striped">
						<thead>
							<tr>
								<th width="10%">Sr. No.</th>
								<th>All &nbsp; <input type="checkbox" name="selAll"
													id="selAll" /></th>
								<th>Franchise</th>
								<th>Contact No.</th>
								<th>Status</th>
								<th style="display: none"></th>
								<th style="display: none"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${frList}" var="frList" varStatus="count">
								<tr>
									<td>${count.index+1}</td>
									<td><input type="checkbox"
														id="data_sync_frId${frList.frId}"
														value="${frList.frId}"
														name="dataSyncFrId" class="select_all"></td>
									<td>${frList.frCode} - ${frList.frName}</td>
									<td>${frList.frContactNo}</td>
									<td>${frList.isActive==1 ? 'Active' : 'In-Active'}</td>
									<td style="display: none"></td>
								<td style="display: none"></td>									
									<%-- <td class="text-center"><c:if test="${editAccess==0}">
											<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/newFranchise/${frList.frId}"
													class="list-icons-item" title="Edit"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
										</c:if> <c:if test="${deleteAccess==0}">
											<div class="list-icons">
												<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${frList.frId}" data-popup="tooltip" title=""
													data-original-title="Delete"><i class="icon-trash"></i></a>
											</div>
										</c:if></td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<span class="validation-invalid-label" id="error_fr_check"
												style="display: none;">Please select some franchise</span>
				
						<div class="form-group row mb-0">
	<div style="margin: 0 auto;">		
								<button type="submit"  id="submtbtn" class="btn bg-blue ml-3 legitRipple">Sync Data</button>
							</div>
						</div>
					</form>
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

	<script type="text/javascript">
		$(document)
	.ready(
			function($) {
				$("#dataSyncForm")
						.submit(
								function(e) {
									var table = $('#printtable1').DataTable();
									table.search("").draw();
									var isError = false;
									var errMsg = "";
									 if (parseInt($('input[name=dataSyncFrId]:checked').length) <1) {
									        isError=true;
									        $("#error_fr_check").show();
									    }else{
									    	 $("#error_fr_check").hide();
									    }
									
									if (!isError) {
									bootbox
									.confirm({
										title : 'Confirm ',
										message : 'Are you sure you want sync data',
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
												document
												.getElementById("submtbtn").disabled = true;
											var form = document.getElementById("dataSyncForm")
										    form.submit();
											}
										}
									});
									}
									return false;
								})
			})
			
		$(document).ready(

				function() {

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);

							});
				});
		$('.datatable-fixed-left_custom').DataTable({

		columnDefs : [ {
			orderable : false,
			targets : [ 1 ]
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
												location.href = "${pageContext.request.contextPath}/deleteFranchise?frId="
														+ uuid;

											}
										}
									});
						});
	</script>
</body>
</html>