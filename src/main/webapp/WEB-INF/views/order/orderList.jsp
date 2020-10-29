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


	<c:url value="/getStatusList" var="getStatusList" />
	<c:url value="/getOrderListByDate" var="getOrderListByDate" />
	<c:url value="/getOrderDetailByBillId" var="getOrderDetailByBillId" />
	<c:url value="/getItemImagesByItemId" var="getItemImagesByItemId" />
	
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
							</div>



							<div class="card-body">
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>

								<div class="form-group row">
									<label class="col-form-label font-weight-bold col-lg-2"
										for="cust_name">Date Type<span class="text-danger">*
									</span>:
									</label>
									<div class="col-lg-4">
										<select class="form-control select-search" data-fouc
											data-placeholder="Select" name="datetype" id="datetype">
											<option value="1">Delivery Date</option>
											<option value="2">Production Date</option>
										</select> <span class="validation-invalid-label text-danger"
											id="error_ingrednt_cat" style="display: none;">This
											field is required.</span>
									</div>


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
										for="cust_name">Status<span class="text-danger">*
									</span>:
									</label>
									<div class="col-lg-10">
										<select class="form-control select-search" data-fouc
											name="status" id="status" multiple="multiple"
											onchange="selectOpt(this.value)">
											<option value="-1">All</option>
											<c:forEach items="${statusList}" var="statusList"
												varStatus="count">
												<option value="${statusList.statusId}">${statusList.statusValue}</option>
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
										<th>Order No.</th>
										<th>Order Date</th>
										<th>Delivery Date</th>
										<!-- <th>Time Slot</th> -->
										<th>Customer</th>
										<th>Franchise</th>
										<th>Order Status</th>
										<th>Payment Mode</th>
										<th>Total</th>
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
			<input type="hidden" value="${imagePath}" id="imgPath">
			<!-- /content area -->


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

					<div class="row">
						<div class="col-md-12">
							<div class="form-group row">
								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Order No<span class="text-danger">
								</span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="orderNo">
								</div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Customer Name <span class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="custName">
								</div>
							</div>

							<div class="form-group row">
								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Shop Name<span class="text-danger">
								</span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="frName">
								</div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Order Status<span class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="orderStatus">
								</div>
							</div>

							<div class="form-group row">
								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Date & Time<span class="text-danger">
								</span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="dateTime">
								</div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Payment Status<span class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="pamentStat">
								</div>
							</div>

							<div class="form-group row">
								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Order Type<span class="text-danger">
								</span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="orderType">
								</div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Payment Method<span class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="payMode">
								</div>
							</div>
							<div class="form-group row">
								<div style="display: none;">
									<label class="col-form-label font-weight-bold col-lg-2"
										for="cust_name">Order Type<span class="text-danger">
									</span>:
									</label>
									<div class="col-lg-4">
										<input type="text" class="form-control" readonly="readonly"
											id="frName">
									</div>
								</div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Total<span class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="ttlAmt">
								</div>
							</div>
						</div>
					</div>

					<table class="table" id="order_dtl_table">
						<thead>
							<tr>
								<th>Items Name</th>
								<th>Product Image</th>
								<th>Rate</th>
								<th>Quantity</th>
								<th>Total</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>

					<div class="row">
						<div class="col-md-12">

							<div class="form-group row">
								<div class="col-lg-6"></div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Item Total <span class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="taxableAmt" style="text-align: right;">
								</div>
							</div>

							<div class="form-group row">
								<div class="col-lg-6"></div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Tax <span class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="taxAmt" style="text-align: right;">
								</div>
							</div>

							<div class="form-group row">
								<div class="col-lg-6"></div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Offer Discount <span
									class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="discAmt" style="text-align: right;">
								</div>
							</div>

							<div class="form-group row">
								<div class="col-lg-6"></div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Delivery Charges <span
									class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="deliveryCharges"
										style="text-align: right; border-bottom: solid;">
								</div>
							</div>

							<div class="form-group row">
								<div class="col-lg-6"></div>

								<label class="col-form-label font-weight-bold col-lg-2"
									for="cust_name">Total <span class="text-danger"></span>:
								</label>
								<div class="col-lg-4">
									<input type="text" class="form-control" readonly="readonly"
										id="totalOrderAmt"
										style="text-align: right; border-bottom: none;">
								</div>
							</div>
						</div>
					</div>
					<table class="table" id="order_trail_table">
						<thead>
							<tr>
								<th>Status</th>
								<th>Action By</th>
								<th>Date Time</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

				<div class="modal-footer">
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
							var status = $("#status").val();
							var datetype = $("#datetype").val();
							
						
								if (status=="") {									
									$("#error_status").show()
									return false;
								} else {
								
							$('#loader').show();
							$('#order_table td').remove();
							var dataTable = $('#order_table').DataTable();
							dataTable.clear().draw();
							$
									.getJSON(
											'${getOrderListByDate}',
											{
												dates : dates,
												datetype : datetype,
												status : JSON.stringify(status),
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
																			+ order.orderId
																			+ '\')"><i class="fa fa-list"></i></a>'

																	var orderStatus = null;
																	var paymentMode = null;

																	if (order.orderStatus == 0) {
																		orderStatus = "Park Order";
																	} else if (order.orderStatus == 1) {
																		orderStatus = "Shop Confirmation Pending";
																	} else if (order.orderStatus == 2) {
																		orderStatus = "Accept";
																	} else if (order.orderStatus == 3) {
																		orderStatus = "Processing";
																	} else if (order.orderStatus == 4) {
																		orderStatus = "Delivery Pending";
																	} else if (order.orderStatus == 5) {
																		orderStatus = "Delivered";
																	} else if (order.orderStatus == 6) {
																		orderStatus = "Rejected by Shop";
																	} else if (order.orderStatus == 7) {
																		orderStatus = "Return Order";
																	} else if (order.orderStatus == 8) {
																		orderStatus = "Cancelled Order";
																	}

																	if (order.paymentMethod == 1) {
																		paymentMode = "Cash";
																	} else if ((order.paymentMethod == 2)) {
																		paymentMode = "Card";
																	} else {
																		paymentMode = "E-Pay";
																	}

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
																							order.orderNo));

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.orderDateDisplay));
																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.deliveryDateDisplay));

																	/* tr1
																			.append($(
																					'<td></td>')
																					.html(
																							"")); */

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.custName+"-"+order.custMobile));

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							order.frName));

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							orderStatus));
																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							paymentMode));

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							(order.totalAmt)
																									.toFixed(2)));

																	tr1
																			.append($(
																					'<td></td>')
																					.html(
																							acStr));

																/* 	$(
																			'#order_table tbody')
																			.append(
																					tr1); */

																					
																	dataTable.row.add(
																			[key + 1,order.orderNo,order.orderDateDisplay,order.deliveryDateDisplay,order.custName+"-"+order.custMobile,order.frName,orderStatus,paymentMode,order.totalAmt.toFixed(2),
																			acStr]).draw();
																});
											});
						}
						});
		

		function getOrderDetail(orderId) {
			$('#order_dtl_table td').remove();
			
			var imgPath = $("#imgPath").val();
			if (orderId > 0) {

				$
						.getJSON(
								'${getOrderDetailByBillId}',
								{

									ajax : 'true'

								},
								function(data) {

									$('#modal_large').modal('toggle');
									for (var i = 0; i < data.length; i++) {

										if (data[i].orderId == orderId) {
											var status = null;
											var paymentMode = null;
											var pamentStatus = null;
											var orderType = null;
											var trailStatus = null;

											if (data[i].orderStatus == 0) {
												status = "Park Order";
											} else if (data[i].orderStatus == 1) {
												status = "Shop Confirmation Pending";
											} else if (data[i].orderStatus == 2) {
												status = "Accept";
											} else if (data[i].orderStatus == 3) {
												status = "Processing";
											} else if (data[i].orderStatus == 4) {
												status = "Delivery Pending";
											} else if (data[i].orderStatus == 5) {
												status = "Delivered";
											} else if (data[i].orderStatus == 6) {
												status = "Rejected by Shop";
											} else if (data[i].orderStatus == 7) {
												status = "Return Order";
											} else if (data[i].orderStatus == 8) {
												status = "Cancelled Order";
											}

											if (data[i].paymentMethod == 1) {
												paymentMode = "Cash";
											} else if (data[i].paymentMethod == 2) {
												paymentMode = "Card";
											} else if (data[i].paymentMethod == 3) {
												paymentMode = "E-Pay";
											} else {
												paymentMode = "";
											}

											if (data[i].orderPlatform == 1) {
												orderType = "Executive";
											} else if (data[i].orderPlatform == 2) {
												orderType = "Mobile App";
											} else {
												orderType = "Web Site";
											}

											if (data[i].paidStatus == 0) {
												pamentStatus = "Pending";
											} else {
												pamentStatus = "Paid";
											}

											document.getElementById("orderNo").value = data[i].orderNo;
											document.getElementById("custName").value = data[i].custName+"-"+data[i].custMobile;
											document.getElementById("frName").value = data[i].frName;
											document
													.getElementById("orderStatus").value = status;
											document
													.getElementById("pamentStat").value = pamentStatus;
											document.getElementById("dateTime").value = data[i].deliveryDateDisplay
													+ " "
													+ data[i].deliveryTimeDisplay;
											document
													.getElementById("orderType").value = orderType;
											document.getElementById("payMode").value = paymentMode;
											document.getElementById("ttlAmt").value = data[i].totalAmt;

											document
													.getElementById("taxableAmt").value = data[i].taxableAmt;
											document.getElementById("taxAmt").value = data[i].igstAmt;
											document.getElementById("discAmt").value = data[i].discAmt;
											document
													.getElementById("deliveryCharges").value = data[i].deliveryCharges;
											document
													.getElementById("totalOrderAmt").value = data[i].totalAmt;

											$
													.each(
															data[i].orderDetailList,
															function(key, itm) {			
																
																var itemPic = '<a href="javascript:void(0)" class="list-icons-item text-primary-600" data-popup="tooltip" title="" data-original-title="Show Images" onclick="getItemImages(\''
																	+ itm.itemId
																	+ '\')"><img src="'+imgPath+itm.itemPic+'"  width="50" height="50" alt="Product Image">'
																	+ '</a>';
																		
																		
																var tr = $('<tr style="background:##03a9f4;"></tr>');

																tr
																		.append($(
																				'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd;"></td>')
																				.html(
																						itm.itemName));
																tr
																		.append($(
																				'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																				.html(itemPic));

																tr
																		.append($(
																				'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																				.html(
																						itm.mrp));

																tr
																		.append($(
																				'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																				.html(
																						itm.qty));

																tr
																		.append($(
																				'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																				.html(
																						itm.mrp
																								* itm.qty));

																$(
																		'#order_dtl_table tbody')
																		.append(
																				tr);

															});

											//***************************************Trail Table*****************************************//

											$('#order_trail_table td').remove();

											$
													.each(
															data[i].orderTrailList,
															function(key, trail) {

																if (trail.status == 0) {
																	trailStatus = "Park Orde";
																} else if (trail.status == 1) {
																	trailStatus = "Shop Confirmation Pending";
																} else if (trail.status == 2) {
																	trailStatus = "Accept";
																} else if (trail.status == 3) {
																	trailStatus = "Processing";
																} else if (trail.status == 4) {
																	trailStatus = "Delivery Pending";
																} else if (trail.status == 5) {
																	trailStatus = "Delivered";
																} else if (trail.status == 6) {
																	trailStatus = "Rejected by Shop";
																} else if (trail.status == 7) {
																	trailStatus = "Return Order";
																} else if (trail.status == 8) {
																	trailStatus = "Cancelled Order";
																}

																var tr = $('<tr style="background:##03a9f4;"></tr>');

																tr
																		.append($(
																				'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd;"></td>')
																				.html(
																						trailStatus));
																tr
																		.append($(
																				'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																				.html(
																						trail.userName));

																tr
																		.append($(
																				'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
																				.html(
																						trail.trailDate));

																$(
																		'#order_trail_table tbody')
																		.append(
																				tr);

															});

											break;

										}
									}

								});
			}
		}

		function getItemImages(itemId) {	
			var imgPath = $("#imgPath").val();
			$('#imgDiv').append('');
			if (itemId > 0) {
				$.getJSON('${getItemImagesByItemId}', {
					itemId : itemId,
					ajax : 'true'

				}, function(data) {
					if (!data.error) {
					//	alert("Images---" + JSON.stringify(data.msg));
					//'+imgPath+itemImage+'
						$('#modal_large2').modal('toggle');
						var imgTag = "";
						
						var prodImg = data.msg;
						var match = prodImg.split(',');
						
					    for (var a in match)
					    {
					        var itemImage = match[a]
					        
							imgTag += '<div class="col-sm-6 col-lg-3"> <div class="card" >'+
							'<div class="card-img-actions m-1" ><img class="card-img img-fluid" src="'+imgPath+itemImage+'" alt=""></div>'+
							'</div></div>';

											// alert(itemImage)
										}
										$('#imgDiv').append(imgTag);
									} else {
										alert("No Data Found!")
									}
								});
			}
		}

		function selectOpt(opt) {

			if (opt == -1) {
				$.getJSON('${getStatusList}', {

					ajax : 'true',
				}, function(data) {

					if(data!=null){
						$("#error_status").hide();
					}
					$('#status').find('option').remove().end()
					$("#status")
					/* 	.append(
								$("<option value=''>Select</option>")); */

					for (var i = 0; i < data.length; i++) {
						$("#status").append(
								$("<option selected></option>").attr("value",
										data[i].statusId).text(
										data[i].statusValue));
					}
					$("#status").trigger("chosen:updated");
				});

			}
		}
		
		  $('#modal_large2').on('hidden.bs.modal', function () {
			
			  $("#imgDiv").empty();	
		}) 
		
	function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcelNew");
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