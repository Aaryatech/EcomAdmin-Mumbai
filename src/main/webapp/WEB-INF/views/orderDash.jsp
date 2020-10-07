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
.select2-selection--multiple .select2-selection__rendered{
	border-bottom: 1px solid #ddd;
}
</style>



<link
	href="${pageContext.request.contextPath}/resources/login/assets/css/custom.css"
	rel="stylesheet" type="text/css">

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>

</head>

<body class="sidebar-xs">
	
	<c:url value="/getOrderListByStatus" var="getOrderListByStatus"/>
	<c:url value="/getOrderDashDetailByBillId" var="getOrderDashDetailByBillId"/>
	
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
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
			<div class="page-header page-header-light">

				<div
					class="breadcrumb-line breadcrumb-line-light header-elements-md-inline">
					<div class="d-flex">
						<div class="breadcrumb">
							<a href="${pageContext.request.contextPath}/home"
								class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Home</a>
						</div>

					</div>

				</div>
			</div>
			<!-- /page header -->




			<!-- Content area -->
			<div class="content">

				<!-- radio button and search bar -->
				<div class="card signle_row" style="padding: 15px 0px 10px 15px;">
				<form action="${pageContext.request.contextPath}/getDashCountData" id="submitInsert" method="get">
					<div class="row">	
						<div class="col-lg-6">
							<div class="radio_row_l">
								<label class="radoi_lab">Select : </label>								
								<div class="form-check form-check-inline">
										<label class="form-check-label">
											<input type="radio" class="form-check-input" name="calRadio"
												id="dayRadio" checked="checked" value="1" onclick="showDiv(1)"  ${radVal==1 ? 'checked' : ''}>
												Day
										</label>
									</div>
									<div class="form-check form-check-inline">
										<label class="form-check-label">
											<input type="radio" class="form-check-input" name="calRadio"
												id="weekRadio" value="2" onclick="showDiv(2)"  ${radVal==2 ? 'checked' : ''}>
												Week
										</label>
									</div>
									<div class="form-check form-check-inline">
										<label class="form-check-label">
											<input type="radio" class="form-check-input" name="calRadio"
												id="monthRadio" value="3" onclick="showDiv(3)" ${radVal==3 ? 'checked' : ''}>
												Month
										</label>
									</div>
									<div class="form-check form-check-inline">
										<label class="form-check-label">
											<input type="radio" class="form-check-input" name="calRadio"
												id="custRadio" value="4" onclick="showDiv(4)" ${radVal==4 ? 'checked' : ''}>
											Customize
										</label>
									</div>									
							</div>
						</div>

						<!-- search here -->
						<div class="col-lg-3">
						<c:choose>
							<c:when test="${showDate==1}">								
								<div class="input-group">
									<span class="input-group-prepend"> <span
										class="input-group-text"><i class="icon-calendar22"></i></span>
									</span> <input type="text" class="form-control daterange-basic_new"
										id="dates" name="dates" value="${dateVal}">
								</div>							
							</c:when>
							<c:otherwise>
								<div id="dateDiv" style="display: none;">
								<div class="input-group">
									<span class="input-group-prepend"> <span
										class="input-group-text"><i class="icon-calendar22"></i></span>
									</span> <input type="text" class="form-control daterange-basic_new"
										id="dates" name="dates" value="${dateVal}">
								</div>
							</div>
							</c:otherwise>	
						</c:choose>							
						</div>

						<div class="col-lg-2">
							<button type="submit" class="btn bg-teal legitRipple"
								>Search</button>
						</div>

						<!-- <div class="col-lg-2">
							<div class="form-group-feedback-left">
								<select class="form-control select-search" data-fouc
									data-placeholder="" name="showDataBy" id="showDataBy">
									<option value="1">Order</option>
									<option value="2">Grievances</option>
								</select>
							</div>
						</div> -->						
					</div>
					</form>
				</div>
				
				<div class="row">
				<c:forEach items="${statusCnt}" var="statusCnt" varStatus="count">
					<c:if test="${!empty statusCnt.statusName}">
					<div class="col-sm-6 col-xl-3" onclick="getOrderDetailData(${statusCnt.orderStatus})">
						<div class="card card-body card card-body bg-danger">
							<div class="media">
								<div class="media-body">
								<h3 class="font-weight-semibold mb-0">${statusCnt.statusName}</h3>
									<h3 class="font-weight-semibold mb-0">${statusCnt.orderStatusCnt}</h3>
									<span class="text-uppercase font-size-sm text-muted"></span>
								</div>

								<div class="ml-3 align-self-center">
									<i class="icon-bag icon-3x text-white-100"></i>
								</div>
							</div>
						</div>
					</div>
					</c:if>
					</c:forEach>
					</div>
					<div id="orderTab_div" style="display: none;">
						<div class="row">
							<div class="col-lg-12">
								<div class="card">
									<table class="table datatable-header-basic" width="100%"
										id="order_table">
										<thead>
											<tr>
												<th width="5%">SR. No.</th>
												<th>Order No.</th>
												<th>Delivery Date</th>
												<th>Customer</th>
												<th>Franchise</th>
												<th>Order Status</th>
												<th>Payment Mode</th>
												<th>Total</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
		
				
				<div class="row align_cen">
					<input type="hidden" value="${imagePath}" id="imgPath">
					<input type="hidden" value="${fromDate}" name="fromDate" id="fromDate">
					<input type="hidden" value="${toDate}" name="toDate" id="toDate">
				</div>

				<!-- 4 button row -->
				<!-- <div class="row align_cen">
					
					<div class="col-lg-12">
						<a href="#" class="bg-teal-400 four_btn"><i
							class="fab fa-android"></i> Android</a> <a href="#"
							class="bg-pink-400 four_btn"><i class="fab fa-apple"></i> IOS</a>
						<a href="#" class="bg-blue-400 four_btn"><i
							class="fas fa-desktop"></i> Web</a> <a href="#"
							class="bg-indigo-400 four_btn"><i class="fab fa-whatsapp"></i>
							WhatsApp</a>
					</div>
				</div> -->
			</div>
			<!-- /content area -->



			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->
	</div>
	<!-- -------------------------------------------------------------------------------------------------------------------- -->
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


	<script>	
		function getOrderDetailData(statusId){ 
		$('#order_table td').remove();
		
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			
					$
					.getJSON(
							'${getOrderListByStatus}',
							{
								fromDate : fromDate,
								toDate : toDate,
								statusId : statusId,
								ajax : 'true'
							},
							function(data) {
							//	$('#loader').hide();
								if (data == null) {
									alert("No Data Found!");									
								}
								document.getElementById("orderTab_div").style.display = "block";
								
								var orderTtlAmt = 0;
								$
										.each(
												data,
												function(key, order) {

													var orderStatus = null;
													var paymentMode = null;

													var acStr = '<a href="javascript:void(0)" class="list-icons-item text-primary-600" data-popup="tooltip" title="" data-original-title="Order Detail" onclick="getOrderDetail('
														+ order.orderId
														+ ')"><i class="fa fa-list"></i></a>'

													if (order.paymentMethod == 1) {
														paymentMode = "Cash";
													} else if (order.paymentMethod == 2) {
														paymentMode = "Card";
													} else {
														paymentMode = "E-Pay";
													}

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

													var tr = $('<tr style="background:##03a9f4;"></tr>');

													tr
															.append($(
																	'<td  style=""></td>')
																	.html(
																			key + 1));

													tr
															.append($(
																	'<td style=""></td>')
																	.html(
																			order.orderNo));

													tr
															.append($(
																	'<td style=""></td>')
																	.html(
																			order.deliveryDateDisplay));

													tr
															.append($(
																	'<td style=""></td>')
																	.html(
																			order.custName
																					+ " - "
																					+ order.custMobile));

													tr
															.append($(
																	'<td style=""></td>')
																	.html(
																			order.frName));

													tr
															.append($(
																	'<td style=""></td>')
																	.html(
																			orderStatus));

													tr
															.append($(
																	'<td style=""></td>')
																	.html(
																			paymentMode));

													orderTtlAmt = orderTtlAmt+order.totalAmt;
													tr
															.append($(
																	'<td style=""></td>')
																	.html(
																			order.totalAmt));
													
													tr
													.append($(
															'<td style=""></td>')
															.html(acStr));
													

													$('#order_table tbody')
															.append(tr);

												});
								
								var tr = "<tr>";
                                var td1 = "<td colspan='7'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
                                var td2 = "<td><b><b>"
                                        + addCommas((orderTtlAmt).toFixed(2));
                                +"</b></td>";   
                                var trclosed = "</tr>";
                                
                                $('#order_table tbody').append(tr);
								$('#order_table tbody').append(td1);
								$('#order_table tbody').append(td2);
								$('#order_table tbody').append(trclosed);  
							
							});
		}

		 function showDiv(radioValue) {
			//var radioValue = $("input[name='calRadio']:checked").val();
			
			if (radioValue == 4) {
				document.getElementById("dateDiv").style.display = "block";
			} else {
				document.getElementById("dateDiv").style.display = "none";
			}
		} 
</script>

<script>

function getOrderDetail(orderId) {
	$('#order_dtl_table td').remove();	
	
	var imgPath = $("#imgPath").val();
	if (orderId > 0) {

		$
				.getJSON(
						'${getOrderDashDetailByBillId}',
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
														
														var itemPic = '<img src="'+imgPath+itm.itemPic+'"  width="50" height="50" alt="Product Image">';
																
																
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
</script>

<script type="text/javascript">
function addCommas(x) {

	x = String(x).toString();
	var afterPoint = '';
	if (x.indexOf('.') > 0)
		afterPoint = x.substring(x.indexOf('.'), x.length);
	x = Math.floor(x);
	x = x.toString();
	var lastThree = x.substring(x.length - 3);
	var otherNumbers = x.substring(0, x.length - 3);
	if (otherNumbers != '')
		lastThree = ',' + lastThree;
	return otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",")
			+ lastThree + afterPoint;
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