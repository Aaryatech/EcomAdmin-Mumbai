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
							Product List</span>
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
					<form action="${pageContext.request.contextPath}/getProdConf"
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
										<option value="${catList.catId}">${catList.catName}</option>
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
						
						
						<div class="form-group row">
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
										</div>
						<div class="table-responsive">
							<table
								class="table datatable-fixed-left_custom table-bordered table-hover table-striped"
								width="100%" id="printtable2">

								<thead>
									<tr>
										<th>Product Name</th>
										<th>Flavor</th>
										<th>Veg Type</th>
										<th>Qty/Weight</th>
										<th>Rate 1</th>
										<th>Rate 2</th>
										<th>Rate 3</th>
										<th>Rate 4</th>
										<th>Rate 5</th>
										<th>Rate 6</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${tempProdConfList}" var="prod" varStatus="count">
										<tr>
											<td>${count.index+1}) ${prod.productName}</td>
											<td>${prod.flavorName}</td>
											<td>${prod.vegType==0 ? 'Veg' :prod.vegType==1 ? 'Non Veg' : 'Veg-Non Veg'}</td>
											<td>${prod.weight}</td>
											<td><input type="text" id="r1${prod.uuid}${prod.productId}" name="r1${prod.uuid}${prod.productId}" value="0" maxlength="7" class="form-control floatOnly"/></td>
											<td><input type="text" id="r2${prod.uuid}${prod.productId}" name="r2${prod.uuid}${prod.productId}" value="0" maxlength="7" class="form-control floatOnly"/></td>
											<td><input type="text" id="r3${prod.uuid}${prod.productId}" name="r3${prod.uuid}${prod.productId}" value="0" maxlength="7" class="form-control floatOnly"/></td>
											<td><input type="text" id="r4${prod.uuid}${prod.productId}" name="r4${prod.uuid}${prod.productId}" value="0" maxlength="7" class="form-control floatOnly"/></td>
											<td><input type="text" id="r5${prod.uuid}${prod.productId}" name="r5${prod.uuid}${prod.productId}" value="0" maxlength="7" class="form-control floatOnly"/></td>
											<td><input type="text" id="r6${prod.uuid}${prod.productId}" name="r6${prod.uuid}${prod.productId}" value="0" maxlength="7" class="form-control floatOnly"/></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="col-lg-3">
								<button type="submit" class="btn btn-primary">Save</button>
							</div>
						
					</form>
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
	</script>
</body>
</html>