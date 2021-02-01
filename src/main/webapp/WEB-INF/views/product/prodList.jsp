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

<c:url value="/getElementIds" var="getElementIds"/>

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
							class="font-size-sm text-uppercase font-weight-semibold card-title">
							Product List</span>
						<!--  -->
						<c:if test="${addAccess==1}">
							<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/showAddProduct"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add
									Product</a></span>
						</c:if>
					</div>

					<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
					<div class="table-responsive">
						<table class="table datatable-fixed-left_custom  table-bordered table-hover table-striped"
										width="100%" id="printtable2">
										
						<thead>
							<tr>
								<th>Product Code</th>
								<th>Name</th>
								<th>Category</th>
								<th>Sub Category</th>
								<th>UOM</th>
								<!-- <th>Same Day Delivery</th> -->
								<th>Book Before</th>
								<!-- <th>Is Veg</th> --><th>Status</th>
								<th>Active</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${prodList}" var="prod" varStatus="count">
								<tr>
					<td>${count.index+1}) ${prod.productCode}</td>
					<td width="20%;">${prod.productName}</td>
					<td>${prod.catName}</td>
					<td>${prod.subCatName}</td>
					<td>${prod.uomShowName}</td>
<%-- 					<td>${prod.allowSameDayDelivery==1 ? 'Yes' :prod.allowSameDayDelivery==0 ? 'No' : 'No'}</td>
 --%>					<td>${prod.bookBefore}</td>
<%-- 					<td>${prod.isVeg==0 ? 'Veg' :prod.isVeg==1 ? 'Non Veg' : 'Veg-Non Veg'}</td>
 --%>					<td>${prod.prodStatus}</td>
					<td>${prod.isActive==1 ? 'Yes' :prod.isActive==0 ? 'No' : 'No'}</td>
					<td><div class="list-icons"><c:if test="${editAccess==1}">
												<a
													href="${pageContext.request.contextPath}/showEditProd/${prod.exVar1}"
													class="list-icons-item" title="Edit Product"> <i
													class="icon-database-edit2"></i>
												</a></c:if>
											</div>
											
											<div class="list-icons">
											<c:if test="${editAccess==1}">
												<a
													href="${pageContext.request.contextPath}/manageProdImages/${prod.exVar1}"
													class="list-icons-item" title="Manage Product Images"> <i
													class="icon-images3
"></i>
												</a>
												</c:if>
											</div>
											</td>			
									<%-- <td class="text-center"><c:if test="${editAccess==0}">
											<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/showEditCustomer?custId=${custList.exVar1}"
													class="list-icons-item" title="Edit"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
										</c:if> <c:if test="${deleteAccess==0}">
											<div class="list-icons">
												<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${custList.exVar1}" data-popup="tooltip"
													title="" data-original-title="Delete"><i
													class="icon-trash"></i></a>
											</div>
										</c:if>
										
										
										<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/showAddCustomerAddress?custId=${custList.exVar1}"
													class="list-icons-item" title="Add Address"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
											
												<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/showCustomerAddressList?custId=${custList.exVar1}"
													class="list-icons-item" title="Address List"> <i
													class="icon-database-edit2"></i>
												</a>
											</div></td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="text-center">
							<!-- <button type="submit" class="btn btn-primary" id="submtbtn"
								onclick="deletSelctd()">
								Delete <i class="far fa-trash-alt"></i>
							</button> -->
							<input type="hidden" value="${compId}" id="compId">
							<button type="button" class="btn btn-primary" id="submtbtn1"
							data-toggle="modal" data-target="#modal_theme_primary" onclick="getHeaders()">
								Pdf/Excel <i class="fas fa-file-pdf"></i>
							</button>
						
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
	</div>
	<!-- /page content -->
	
	 <!-- Primary modal -->
				<div id="modal_theme_primary" class="modal fade" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header bg-primary">
								<h6 class="modal-title">Product List</h6>
								<button type="button" class="close" data-dismiss="modal">&times;</button>
							</div>
				
							<div class="modal-body">
								<table class="table table-bordered table-hover table-striped"
										width="100%" id="modelTable">
									<thead>
										<tr>
											<th width="15">Sr.No.
											<input type="checkbox" name="selAll" id="selAll"/>
											</th>
											<th>Headers</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
							</div>
							<div class="text-center">
							<div class="form-check form-check-switchery form-check-inline">

								<label class="form-check-label"> <input type="checkbox" id="chkPdf"
									class="form-check-input-switchery" checked data-fouc>
									Click For show or hide header on pdf.
								</label>
							</div>
						</div>
							<div class="modal-footer">
								<button type="button" class="btn bg-primary" id="expExcel" onclick="getIdsReport(1)">Excel</button>
								<button type="button" class="btn bg-primary" onclick="getIdsReport(2)">Pdf</button>
							</div>
						</div>
					</div>
				</div>
				<script>
				function getHeaders(){
					$('#modelTable td').remove();
				var thArray = [];
	
				$('#printtable2 > thead > tr > th').each(function(){
				    thArray.push($(this).text())
				})
				//console.log(thArray[0]);
					
				var seq = 0;
					for (var i = 0; i < thArray.length-1; i++) {
						seq=i+1;					
						var tr1 = $('<tr></tr>');
						tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html('<input type="checkbox" class="chkcls" name="chkcls'
								+ seq
								+ '" id="catCheck'
								+ seq
								+ '" value="'
								+ seq
								+ '">') );
						tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(innerHTML=thArray[i]));
						$('#modelTable tbody').append(tr1);
					}
				}
				
				$(document).ready(

						function() {

							$("#selAll").click(
									function() {
										$('#modelTable tbody input[type="checkbox"]')
												.prop('checked', this.checked);

									});
						});
				
				  function getIdsReport(val) {
					  var elemntIds = [];										
								
								$(".chkcls:checkbox:checked").each(function() {
									elemntIds.push($(this).val());
								}); 
														
						$
						.getJSON(
								'${getElementIds}',
								{
									elemntIds : JSON.stringify(elemntIds),
									val : val,
									ajax : 'true'
								},
								function(data) {
									if(data!=null){
										$("#modal_theme_primary").modal('hide');
										if(val==1){
											window.open("${pageContext.request.contextPath}/exportToExcelNew");
											document.getElementById("expExcel").disabled = true;
										}else{
											var compId = $("#compId").val();
											var showHead = 0;
											if($("#chkPdf").is(":checked")){
												showHead = 1;
											}else{
												showHead = 0;
											}
											 window.open('${pageContext.request.contextPath}/pdfReport?url=pdf/getProductListPdf/'+compId+"/"+elemntIds.join()+'/'+showHead);
											 $('#selAll').prop('checked', false);
										}
									}
								});
					}		
				</script>
				<!-- /primary modal -->
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
												location.href = "${pageContext.request.contextPath}/deleteCustomer?custId="
														+ uuid;

											}
										}
									});
						});
	</script>
</body>
</html>