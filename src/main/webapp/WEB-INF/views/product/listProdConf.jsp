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

					<div
						class="card-header bg-blue text-white d-flex justify-content-between">
						<span
							class="font-size-sm text-uppercase font-weight-semibold card-title">
							Product Config List</span>
						<!--  -->
						<c:if test="${addAccess==0}">
							<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/showAddProduct"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add
									Product</a></span>
						</c:if>
					</div>
					<div class="card-body">
					<form action="${pageContext.request.contextPath}/getViewProdConfigHeader"
						id="submitProdForm1" method="post">
						<div class="form-group row"></div>
						<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

						<div class="form-group row">
							<label class="col-form-label col-lg-2" for="cat_id">
								Select Category <span style="color: red">* </span>:
							</label>
							<div class="col-lg-2">
								<select
									class="form-control form-control-select2 select2-hidden-accessible"
									data-fouc="" aria-hidden="true" data
									placeholder="Select Category" id="cat_id" name="cat_id"
									>
									<option selected disabled value="">Select Category</option>
									<c:forEach items="${catList}" var="catList" varStatus="count">
									<c:choose>
									<c:when test="${catId==catList.catId}">
									<option selected value="${catList.catId}">${catList.catName}</option>
									</c:when>
									<c:otherwise>
										<option value="${catList.catId}">${catList.catName}</option>
									</c:otherwise>
									</c:choose>
									</c:forEach>
								</select> <span class="validation-invalid-label" id="error_cat_id"
									style="display: none;">This field is required.</span>
							</div>

							<div class="col-lg-1">
								<button type="submit" class="btn btn-primary">Search</button>
							</div>
						</div>

</form>
<form action="${pageContext.request.contextPath}/saveInsertProdConf"
						id="submitProdForm1" method="post">
						
						
						<!-- <div class="form-group row">
						<label class="col-form-label col-lg-2" for="conf_name">
											Configuration Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<input type="text" class="form-control" required maxlength="50"
												placeholder="Configuration Name" id="conf_name" name="conf_name"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_conf_name"
												style="display: none;">This field is required.</span>
										</div>
										</div> -->
						<div class="table-responsive">
							<table
								class="table datatable-fixed-left_custom table-bordered table-hover table-striped"
								width="100%" id="printtable1">

								<thead>
									<tr>
										<th>Configuration Name</th>
										<th>Category</th>
										<th>Active/InActive</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${confHeadList}" var="confHead" varStatus="count">
										<tr>
											<td>${count.index+1}) ${confHead.configName}</td>
											<td>${confHead.catName}</td>
											<td>${confHead.isActive==0 ? 'In Active' :confHead.isActive==1 ? 'Active' : 'Active'}</td>
											<td align="center"><%-- <a href="${pageContext.request.contextPath}/getProdConfDetailByConfHeader/?configHeaderId=${confHead.configHeaderId}">Edit Details</a> --%>
											<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/getProdConfDetailByConfHeader/?configHeaderId=${confHead.configHeaderId}"
													class="list-icons-item" title="Edit Details"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
											
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="text-center">
							<button type="button" class="btn btn-primary" id="submtbtn"
								onclick="exportToExcel()">
								Excel <i class="far fa-file-excel"></i>
							</button> 

							<button type="button" class="btn btn-primary" id="submtbtn1"  onclick="genPdf()">
								Pdf<i class="fas fa-file-pdf"></i>
							</button>
						
						</div>	
						</div>
						
						<div class="form-group row mb-0" style="display: none;">
					<div style="margin: 0 auto;">
								<button type="submit" class="btn blue_btn ml-3 legitRipple">Save</button>
							</div>
							</div>
						
					</form>
					</div>
					
					<!-- /colReorder integration -->

			
				<!-- /content area -->

			</div>
			<!-- /main content -->
		</div>
		<!-- Footer -->
				<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
				<!-- /footer -->
	</div>
	</div>
	
	<!-- /page content -->
	<script>
		$('.datatable-fixed-left_custom').DataTable({
			columnDefs : [ {
				orderable : true,
				targets : [ 0 ]
			} ],
			//scrollX : true,
			scrollX : true,
			scrollY : '50vh',
			scrollCollapse : true,
			paging : false,
			order:[],
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
												location.href = "${pageContext.request.contextPath}/deleteCustomer?custId="
														+ uuid;
											}
										}
									});
						});
		
		
		function exportToExcel() {
			window.open("${pageContext.request.contextPath}/exportToExcelNew");
			document.getElementById("expExcel").disabled = true;
		}

		function genPdf() {
			window
					.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getProductConfigPdf");
		}
	</script>
</body>
</html>