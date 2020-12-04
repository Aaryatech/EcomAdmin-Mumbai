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
.table caption+thead tr:first-child td, .table caption+thead tr:first-child th, .table colgroup+thead tr:first-child td, .table colgroup+thead tr:first-child th, .table thead:first-child tr:first-child td, .table thead:first-child tr:first-child th {
      border-top-width: 1px!important;  
}
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">


	<c:url value="/getFranchiseListForRep" var="getFranchiseListForRep" />
	<c:url value="/getDateWiseillsReport" var="getDateWiseillsReport" />
	<c:url value="/getDateWiseCustDtlReport" var="getDateWiseCustDtlReport" />
	
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
									class="font-size-sm text-uppercase font-weight-semibold card-title">Order Date Wise Bill Report</span>
							</div>



							<div class="card-body">
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>

								<div class="form-group row">
									<label class="col-form-label font-weight-bold col-lg-2"
										for="cust_name">Date<span class="text-danger">*
									</span>:
									</label>
									<div class="col-lg-4">
										<input type="text" class="form-control daterange-basic_new"
											autocomplete="off" name="dates" id="dates"> <span
											class="validation-invalid-label text-danger" id="error_date"
											style="display: none;">This field is required.</span>
									</div>

								</div>
								<div class="form-group row">
									<label class="col-form-label font-weight-bold col-lg-2"
										for="cust_name">Franchise<span class="text-danger">*
									</span>:
									</label>
									<div class="col-lg-10">
										<select class="form-control select-search" data-fouc
											name="frId" id="frId" multiple="multiple"
											onchange="getFrnch(this.value)">
											<option value="-1">All</option>
											<c:forEach items="${frList}" var="list"
												varStatus="count">
												<option value="${list.frId}">${list.frName}</option>
											</c:forEach>
										</select> <span class="validation-invalid-label text-danger"
											id="error_status" style="display: none;">This
											field is required.</span>
									</div>
								</div>
								<br>
								<div class="text-center">
									<button type="submit" class="btn btn-primary" id="submtbtn">
										Search <i class="icon-paperplane ml-2"></i>
									</button>
								</div>
								<br>
								<div align="center" id="loader" style="display: none;">
									<span>
										<h4>
											<font color="#343690">Loading</font>
										</h4>
									</span> <span class="l-1"></span> <span class="l-2"></span> <span
										class="l-3"></span> <span class="l-4"></span> <span
										class="l-5"></span> <span class="l-6"></span>
								</div>
							</div>
						</div>
						<!-- /a legend -->

					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="card">
							<table class="table datatable-fixed-left_custom table-bordered table-hover table-striped"
								id="order_table">
								<thead>
									<tr>
										<th width="5%">SR. No.</th>
										<th>Order Date.</th>
										<th>No.Of Bills</th>										
										<th>Total Amt</th>
										<th>COD</th>
										<th>Card</th>
										<th>E-Pay</th>
										<th class="text-center">Actions</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>

							<br>

							<div class="text-center">
								<!-- <button class="btn btn-primary" value="PDF" id="PDFButton"
									onclick="genPdf()" disabled="disabled">PDF</button> -->

								<input type="button" id="expExcel" class="btn btn-primary"
									value="EXPORT TO Excel" onclick="exportToExcel();"
									disabled="disabled">
							</div>
							<br>

							<table class="table" id="item_tag_table2"></table>
						</div>
					</div>
				</div>

			</div>		

			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->
	<!-- Modal -->
	<!-- Large modal -->
	<div id="modal_large" class="modal fade" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Order Details</h5>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body">

					<!-- <div class="form-group row">
						<label class="col-form-label font-weight-bold col-lg-10"
							for="cust_name"><h4>Tag Name:&nbsp; &nbsp; <span id="tag_name"></span></h4> </label>
						<div class="col-lg-4">
											<h3>Tag1</h3>
										</div>
					</div> -->

					

					<table class="table" id="order_dtl_table">
						<thead>
							<tr>
								<th>Sr. No.</th>
								<th>Bill No.</th>
								<th>Bill Date</th>
								<th>Bill Amt.</th>
								<th>Payment Mode</th>
								<th>Delivery Boy</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

				<div class="modal-footer">
				<input type="button" id="dtlExpExcel" class="btn btn-primary"
									value="EXPORT TO Excel" onclick="exportToExcelDtl();"
									disabled="disabled">
									
					<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					<!-- <button type="button" class="btn bg-primary">Save changes</button> -->
				</div>
			</div>
		</div>
	</div>
	<!-- /large modal -->
	
	<!-- Images Model -->
	<!-- Large modal -->
	<div id="modal_large2" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Product Images</h5>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body">
					<div class="row" id="imgDiv">
					  <!-- <div class="col-sm-6 col-lg-3">
						<div class="card" >
							 <div class="card-img-actions m-1" >
								<img class="card-img img-fluid" src="../../../../global_assets/images/demo/flat/1.png" alt=""> 						
							</div> 
						</div> -->
					</div>  
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>					
				</div>
			</div>
		</div>
	</div>
	</div>
	<!-- /large modal -->
	<script>
	function getFrnch(opt) {

		if (opt == -1) {
			$.getJSON('${getFranchiseListForRep}', {
				
				ajax : 'true',
				
			}, function(data) {

				if(data!=null){
					$("#error_status").hide();
				}
				$('#frId').find('option').remove().end()
				$("#frId")
				/* 	.append(
							$("<option value=''>Select</option>")); */

				for (var i = 0; i < data.length; i++) {
					$("#frId").append(
							$("<option selected></option>").attr("value",
									data[i].frId).text(
									data[i].frName));
				}
				$("#frId").trigger("chosen:updated");
			});

		}
	}
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
	
		$("#submtbtn")
				.click(
						function() {
							var dates = $("#dates").val();
							var frId = $("#frId").val();
							
								if (frId=="") {									
									$("#error_status").show()
									return false;
								} else {
								
							$('#loader').show();
							$('#order_table td').remove();
							var dataTable = $('#order_table').DataTable();
							dataTable.clear().draw();
							$
									.getJSON(
											'${getDateWiseillsReport}',
											{
												dates : dates,
												frId : JSON.stringify(frId),
												ajax : 'true'
											},
											function(data) {

												$('#loader').hide();
												if (data == "") {
													alert("No records found !!");
													document.getElementById("expExcel").disabled = true;
												//	document.getElementById("PDFButton").disabled = true;
												}else{
													
													document.getElementById("expExcel").disabled = false;
												//	document.getElementById("PDFButton").disabled = false;
												}	
												//alert(JSON.stringify(data));
												$
														.each(
																data,
																function(key,
																		order) {

																	var acStr = '<a href="javascript:void(0)" class="list-icons-item text-primary-600" data-popup="tooltip" title="" data-original-title="Order Detail" onclick="getOrderDetail(\''
																			+ order.billDate
																			+ '\')"><i class="fa fa-list"></i></a>'

																	var tr1 = $('<tr></tr>');

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							key + 1));

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.billDate));

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.totalBills));
																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.totalAmt));
																	
																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.cod));
																	tr1
																	.append($(
																			'<td></td>')
																			.html(
																					order.card));
																	

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.epay));

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							acStr));

																	dataTable.row.add(
																			[key + 1,order.billDate, order.totalBills, order.totalAmt.toFixed(2), 
																			order.cod, order.card, order.epay,
																			acStr]).draw();
																});
											});
						}
						});
		

		function getOrderDetail(orderDate) {			
			var frId = $("#frId").val();
						
			$('#order_dtl_table td').remove();
			
			
			if (orderDate != null) {

				$
						.getJSON(
								'${getDateWiseCustDtlReport}',
								{
									fromDate : orderDate,
									toDate : orderDate,
									frId : JSON.stringify(frId),
									ajax : 'true'

								},
								function(data) {
									document.getElementById("dtlExpExcel").disabled = false;
									$('#modal_large').modal('toggle');
									$
									.each(
											data,
											function(key, value) {												
												var tr = $('<tr style="background:##03a9f4;"></tr>');
												tr
												.append($(
														'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd;"></td>')
														.html(key + 1));

												tr
														.append($(
																'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd;"></td>')
																.html(
																		value.invoiceNo));
												tr
														.append($(
																'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																.html(
																		value.billDate));

												tr
														.append($(
																'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																.html(
																		value.grandTotal));
												tr
												.append($(
														'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
														.html(value.paymentMode == 1 ? 'Cash' : value.paymentMode == 2 ? 'Card' : 'E-Pay' ));
												
												tr
												.append($(
														'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
														.html( value.delvrBoyName));

												$(
														'#order_dtl_table tbody')
														.append(
																tr);

											});

								});
			}
		}
		
	function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcelNew");
			document.getElementById("expExcel").disabled = true;
	}
 	function exportToExcelDtl() {
				window.open("${pageContext.request.contextPath}/exportToExcelDummy");
				document.getElementById("expExcel").disabled = true;
	}
		  
	function genPdf(){		
		window.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getOrderListPdf");		
	} 
		
	</script>
	<script>
		$('.daterange-basic_new').daterangepicker({
			applyClass : 'bg-slate-600',

			cancelClass : 'btn-light',
			locale : {
				format : 'DD-MM-YYYY',
				separator : ' to '
			}
		});
	</script>

</body>
</html>