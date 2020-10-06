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
					
					<input type="hidden" value="${fromDate}" name="fromDate" id="fromDate">
					<input type="hidden" value="${toDate}" name="toDate" id="toDate">
				
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
					<input type="hidden" id="frm_date">
					<input type="hidden" id="to_date">
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

												/* 	var str = '<a href="javascript:void(0)" class="list-icons-item text-primary-600" data-popup="tooltip" title="" data-original-title="Order Detail" onclick="getOrderDetal('
															+ order.orderId
															+ ')"><i class="fa fa-list"></i></a>' */

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

	<script type="text/javascript">
		function getOrderDetal(orderId, para1) {
			var fromDate = delDate;
			var toDate = delDate;
			var statusList = status;

			document.getElementById("orderNoShow").innerHTML = null;
			document.getElementById("custName").innerHTML = null;
			document.getElementById("frName").innerHTML = null;
			document.getElementById("orderStatus").innerHTML = null;
			document.getElementById("pamentStat").innerHTML = null;
			document.getElementById("delDateTime").innerHTML = null;
			document.getElementById("orderTypeShow").innerHTML = null;
			document.getElementById("payMode").innerHTML = null;
			document.getElementById("ttlAmt").innerHTML = null;

			document.getElementById("taxableAmt").innerHTML = null;
			document.getElementById("taxAmt").innerHTML = null;
			document.getElementById("discAmt").innerHTML = null;
			document.getElementById("deliveryCharges").innerHTML = null;
			document.getElementById("totalOrderAmt").innerHTML = null;

			$('#order_dtl_table td').remove();
			$('#order_trail_table td').remove();

			if (orderId > 0) {

				$('#billPopup').popup('show')
				$
						.getJSON(
								'${getOrderDataByOrderId}',
								{
									orderId : orderId,
									ajax : 'true'

								},
								function(data) {

									var orderStats = null;
									var payMode = null;
									var pamentStatus = null;
									var orderType = null;
									var trailStatus = null;

									if (data[0].orderStatus == 0) {
										orderStats = "Park Orde";
									} else if (data[0].orderStatus == 1) {
										orderStats = "Shop Confirmation Pending";
									} else if (data[0].orderStatus == 2) {
										orderStats = "Accept";
									} else if (data[0].orderStatus == 3) {
										orderStats = "Processing";
									} else if (data[0].orderStatus == 4) {
										orderStats = "Delivery Pending";
									} else if (data[0].orderStatus == 5) {
										orderStats = "Delivered";
									} else if (data[0].orderStatus == 6) {
										orderStats = "Rejected by Shop";
									} else if (data[0].orderStatus == 7) {
										orderStats = "Return Order";
									} else if (data[0].orderStatus == 8) {
										orderStats = "Cancelled Order";
									}

									if (data[0].paymentMethod == 1) {
										payMode = "Cash";
									} else if (data[0].paymentMethod == 2) {
										payMode = "Card";
									} else {
										payMode = "E-Pay";
									}

									if (data[0].orderPlatform == 1) {
										orderType = "Executive";
									} else if (data[0].orderPlatform == 2) {
										orderType = "Mobile App";
									} else {
										orderType = "Web Site";
									}

									if (data[0].paidStatus == 0) {
										pamentStatus = "Pending";
									} else {
										pamentStatus = "Paid";
									}

									document.getElementById("orderNoShow").innerHTML = data[0].orderNo;
									document.getElementById("custName").innerHTML = data[0].custName;
									document.getElementById("frName").innerHTML = data[0].frName;
									document.getElementById("orderStatus").innerHTML = orderStats;
									document.getElementById("pamentStat").innerHTML = pamentStatus;
									document.getElementById("delDateTime").innerHTML = data[0].deliveryDateDisplay
											+ " " + data[0].deliveryTimeDisplay;
									document.getElementById("orderTypeShow").innerHTML = orderType;
									document.getElementById("payMode").innerHTML = payMode;
									document.getElementById("ttlAmt").innerHTML = data[0].totalAmt;

									$
											.each(
													data[0].orderDetailList,
													function(key, itm) {

														var tr = $('<tr style="background:##03a9f4;"></tr>');

														tr
																.append($(
																		'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd; text-align: left;"></td>')
																		.html(
																				itm.itemName));
														tr
																.append($(
																		'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd; text-align: left;"></td>')
																		.html(
																				""));

														tr
																.append($(
																		'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd; text-align: right;"></td>')
																		.html(
																				itm.mrp));

														tr
																.append($(
																		'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd; text-align: right;"></td>')
																		.html(
																				itm.qty));

														tr
																.append($(
																		'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd; text-align: right;"></td>')
																		.html(
																				itm.mrp
																						* itm.qty));

														$(
																'#order_dtl_table tbody')
																.append(tr);

													});

									//***************************************Trail Table*****************************************//
									document.getElementById("taxableAmt").innerHTML = data[0].taxableAmt;
									document.getElementById("taxAmt").innerHTML = data[0].igstAmt;
									document.getElementById("discAmt").innerHTML = data[0].discAmt;
									document.getElementById("deliveryCharges").innerHTML = data[0].deliveryCharges;
									document.getElementById("totalOrderAmt").innerHTML = data[0].totalAmt;
									$('#order_trail_table td').remove();

									$
											.each(
													data[0].orderTrailList,
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
														} else if (data.orderStatus == 5) {
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
																		'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd; text-align: left;"></td>')
																		.html(
																				trailStatus));
														tr
																.append($(
																		'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd; text-align: left;"></td>')
																		.html(
																				trail.userName));

														tr
																.append($(
																		'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd; text-align: center;"></td>')
																		.html(
																				trail.trailDate));

														$(
																'#order_trail_table tbody')
																.append(tr);

													});

								});
			}
}
		
function getFrOrderDetail(frId, statusVal, fromDate, toDate){
			
		$('#fr_dtl_table td').remove();	
			
			if (frId > 0) {

				
				$
						.getJSON(
								'${getOrderDataByFrId}',
								{
									frId : frId,
									statusVal : statusVal,
									fromDate: fromDate, 
									toDate : toDate,
									ajax : 'true'

								},
								function(data) {
									var orderStat = null;
									var payMode = null;
									var pamentStatus = null;
									var orderType = null;
									var ttlAmt = 0;
									
						
								document.getElementById("frdtl").innerHTML = data[0].frName;
								document.getElementById("frTab_div").style.display = "block";

									$
											.each(
													data,
													function(key, itm) {
														
														if (itm.orderStatus == 0) {
															orderStat = "Park Orde";
														} else if (itm.orderStatus == 1) {
															orderStat = "Shop Confirmation Pending";
														} else if (itm.orderStatus == 2) {
															orderStat = "Accept";
														} else if (itm.orderStatus == 3) {
															orderStat = "Processing";
														} else if (itm.orderStatus == 4) {
															orderStat = "Delivery Pending";
														} else if (itm.orderStatus == 5) {
															orderStat = "Delivered";
														} else if (itm.orderStatus == 6) {
															orderStat = "Rejected by Shop";
														} else if (itm.orderStatus == 7) {
															orderStat = "Return Order";
														} else if (itm.orderStatus == 8) {
															orderStat = "Cancelled Order";
														}

														if (itm.paymentMethod == 1) {
															payMode = "Cash";
														} else if (itm.paymentMethod == 2) {
															payMode = "Card";
														} else {
															payMode = "E-Pay";
														}

														if (itm.orderPlatform == 1) {
															orderType = "Executive";
														} else if (itm.orderPlatform == 2) {
															orderType = "Mobile App";
														} else {
															orderType = "Web Site";
														}

														if (itm.paidStatus == 0) {
															pamentStatus = "Pending";
														} else {
															pamentStatus = "Paid";
														}

														var tr = $('<tr style="background:##03a9f4;"></tr>');

														tr
																.append($(
																		'<td></td>')
																		.html(
																				key+1));
														tr
																.append($(
																		'<td></td>')
																		.html(
																				itm.orderNo));
														tr
																.append($(
																		'<td></td>')
																		.html(
																		itm.deliveryDateDisplay+" "+itm.deliveryTimeDisplay));
														

														tr
																.append($(
																		'<td></td>')
																		.html(
																				itm.custName));

														tr
																.append($(
																		'<td></td>')
																		.html(orderStat));
														
														 tr
																.append($(
																		'<td></td>')
																		.html(orderType));
														
														tr
																.append($(
																		'<td></td>')
																		.html(payMode));
														
															ttlAmt = ttlAmt+itm.totalAmt;
														tr
																.append($(
																		'<td></td>')
																		.html(
																		itm.totalAmt)); 
														
														$( '#fr_dtl_table tbody')
																.append(tr);

													});
									
									
										var tr = "<tr>";
	                                    var td1 = "<td colspan='7'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
	                                    var td2 = "<td><b><b>"
	                                            + addCommas((ttlAmt).toFixed(2));
	                                    +"</b></td>";   
	                                    var trclosed = "</tr>";
	                                    
	                                    $('#fr_dtl_table tbody').append(tr);
										$('#fr_dtl_table tbody').append(td1);
										$('#fr_dtl_table tbody').append(td2);
										$('#fr_dtl_table tbody').append(trclosed);  
								});
				}
	}
		
function getGrievanceDetail(grivStatus){
	
	var fromDate = $("#frm_date").val();
	var toDate = $("#to_date").val();
	var radioValue = $("input[name='calRadio']:checked").val();	
	
	if(grivStatus>0){
		$('#griev_table td').remove();	
		$
		.getJSON(
				'${getGrievanceData}',
				{
				
					grivStatus : grivStatus,
					fromDate: fromDate, 
					toDate : toDate,
					radioValue : radioValue,
					ajax : 'true'

				},
				function(data) {
					
					var ttlAmt = 0;
					var curStatus = null;
					var isSet = null;
					
				document.getElementById("grievTab_div").style.display = "block";
				
					$
							.each(
									data,
									function(key, itm) {
										
										if(itm.currentStatus==0){
											curStatus = 'Pending';
										}else if(itm.currentStatus==1){
											curStatus = 'Resolved';
										}else{
											curStatus = 'Damaged';
										}
										
										if(itm.isSet==1){
											isSet='Yes';
										}else{
											isSet='No';											
										}

										var tr = $('<tr style="background:##03a9f4;"></tr>');

										tr
												.append($(
														'<td></td>')
														.html(key+1));
										tr
												.append($(
														'<td></td>')
														.html(itm.grievencceNo));
										tr
												.append($(
														'<td></td>')
														.html(itm.date));
										

										tr
												.append($(
														'<td></td>')
														.html(itm.custName));

										tr
												.append($(
														'<td></td>')
														.html(itm.orderNo));										
										 
										
										tr
												.append($(
														'<td></td>')
														.html(isSet));
										
											
										tr
												.append($(
														'<td></td>')
														.html(curStatus)); 
										
									 ttlAmt = ttlAmt+itm.orderAmt; 
											
										tr
										.append($(
												'<td></td>')
												.html(itm.orderAmt));
										
										$( '#griev_table tbody')
												.append(tr);

									});
					
					
						 var tr = "<tr>";
                        var td1 = "<td colspan='7'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
                        var td2 = "<td><b><b>"
                                + addCommas((ttlAmt).toFixed(2));
                        +"</b></td>";   
                        var trclosed = "</tr>";
                        
                        $('#griev_table tbody').append(tr);
						$('#griev_table tbody').append(td1);
						$('#griev_table tbody').append(td2);
						$('#griev_table tbody').append(trclosed);   
				});
	}
}		
		
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
function getGrievBarDate(){
	var  grievTypeId= $("#grivancId").val();
	var radioValue = $("input[name='calRadio']:checked").val();	
	var fromDate = $("#frm_date").val();
	var toDate = $("#to_date").val();
	
	$
	.getJSON(
			'${getGrievanceAjaxData}',
			{				
				grievTypeId : JSON.stringify(grievTypeId),
				fromDate : fromDate,
				toDate : toDate,
				radioValue : radioValue,
				ajax : 'true',
			},
			function(data) {
				if(data==null){
					alert("!No Data Found");
				}
				
				//Bar Chart
				
				var chartDiv = document.getElementById('griev_chart');
				var dataTable = new google.visualization.DataTable();
				
				dataTable.addColumn('string', 'Franchise'); // Implicit data column.
				dataTable.addColumn('number', 'Grievance'); // Implicit domain column.
				//dataTable.addColumn({type: 'string', role: 'tooltip'});
				
				var dataSale = [];
				var Header =  ['Franchise', 'Grievance', { role: 'annotation' }];
				dataSale.push(Header);

				google.charts.load('current', {
					'packages' : [ 'corechart', 'bar' ]
				});
				google.charts.setOnLoadCallback(drawStuff);

				function drawStuff() {
					
					document.getElementById("grievBar_div").style.display = "block";
					
					$.each(data, function(key, chartsBardata) {
						
						var temp = [];
						temp.push(chartsBardata.frName,
								parseInt(chartsBardata.grievCnt), chartsBardata.caption);
						dataSale.push(temp);

					});
				
					var data1 =google.visualization.arrayToDataTable(dataSale);

					var materialOptions = {
						width : 1000,
						height : 450,
						chart : {
							title : 'Franchise Grievance Count',
							subtitle : ' '
						},
						series : {
							0 : {
								axis : 'distance'
							}, // Bind series 0 to an axis named 'distance'.
							1 : {
								axis : 'brightness'
							}
						// Bind series 1 to an axis named 'brightness'.
						},
						axes : {
							y : {
								distance : {
									 label : 'Grievance Count' 
								}, // Left y-axis.
								brightness : {
									side : 'right',
									 label : 'Amount'
								}
							// Right y-axis.
							},
							x : {
								distance : {
									label : 'Franchise'
								}, // Left y-axis.
								brightness : {
									side : 'right',
									label : ''
								}
							// Right y-axis.
							}
						}
					};
					
					var materialChart = new google.charts.Bar(
							chartDiv);
					

					function drawMaterialChart() {		
						google.visualization.events.addListener(materialChart, 'select', selectQtyHandler);   
						materialChart
								.draw(
										data1,
										google.charts.Bar
												.convertOptions(materialOptions));

					}
					
					
					function selectQtyHandler(e) {
						var selectedItem = materialChart.getSelection()[0];
					
						if (selectedItem) {										
							i = selectedItem.row, 0;
							//alert("Hi---------->"+data.frStatusCnt[i].exInt1)
							getFrGrievDetail(data[i].grevTypeId, data[i].frId);
						} 
					}
					
					drawMaterialChart();
				}
				
			});
}


function getFrGrievDetail(grievId, frId){
	var fromDate = $("#frm_date").val();
	var toDate = $("#to_date").val();

	var radioValue = $("input[name='calRadio']:checked").val();	
	
	if(grievId>0){
		$('#griev_fr_table td').remove();	
		$
		.getJSON(
				'${getGrievanceFrData}',
				{
				
					grievId : grievId,
					fromDate: fromDate, 
					toDate : toDate,
					frId : frId,
					radioValue : radioValue,
					ajax : 'true'
				},
				function(data) {
					
					var ttlAmt = 0;
					var curStatus = null;
					var isSet = null;
					
				document.getElementById("grievFR_div").style.display = "block";
				
					$
							.each(
									data,
									function(key, itm) {
										
										if(itm.currentStatus==0){
											curStatus = 'Pending';
										}else if(itm.currentStatus==1){
											curStatus = 'Resolved';
										}else{
											curStatus = 'Damaged';
										}
										
										if(itm.isSet==1){
											isSet='Yes';
										}else{
											isSet='No';											
										}

										var tr = $('<tr style="background:##03a9f4;"></tr>');

										tr
												.append($(
														'<td></td>')
														.html(key+1));
										tr
										.append($(
												'<td></td>')
												.html(itm.caption));
										
										tr
										.append($(
												'<td></td>')
												.html(itm.grievencceNo));
										
										
										tr
												.append($(
														'<td></td>')
														.html(itm.date));
										

										tr
												.append($(
														'<td></td>')
														.html(itm.custName));

										tr
												.append($(
														'<td></td>')
														.html(itm.orderNo));										
										 
										
										tr
												.append($(
														'<td></td>')
														.html(isSet));
										
											
										tr
												.append($(
														'<td></td>')
														.html(curStatus)); 
										
									 ttlAmt = ttlAmt+itm.orderAmt; 
											
										tr
										.append($(
												'<td></td>')
												.html(itm.orderAmt));
										
										$( '#griev_fr_table tbody')
												.append(tr);

									});
					
					
						 var tr = "<tr>";
                        var td1 = "<td colspan='8'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
                        var td2 = "<td><b><b>"
                                + addCommas((ttlAmt).toFixed(2));
                        +"</b></td>";   
                        var trclosed = "</tr>";
                        
                        $('#griev_fr_table tbody').append(tr);
						$('#griev_fr_table tbody').append(td1);
						$('#griev_fr_table tbody').append(td2);
						$('#griev_fr_table tbody').append(trclosed);   
				});
	}
}



function selectGrievance(grivancId){
	if(grivancId==-1){
		$
						.getJSON(
								'${getGrievanceAjaxList}',
								{
									
									ajax : 'true',
								},
								function(data) {
								//	alert(JSON.stringify(data));
									$('#grivancId').find(
											'option').remove()
											.end()
									$("#grivancId")
										/* 	.append(
													$("<option value=''>Select</option>")); */

									for (var i = 0; i < data.length; i++) {										
											$("#grivancId")
													.append(
															$(
																	"<option selected style='text-align: left;'></option>")
																	.attr(
																			"value",
																			data[i].grevTypeId)
																	.text(
																			data[i].caption));										 
									}
									$("#grivancId").trigger(
											"chosen:updated");
								});
		
	}
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