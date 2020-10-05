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
<c:url value="/getOrderDetailById" var="getOrderDetailById"></c:url>
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
						<span class="font-size-sm text-uppercase font-weight-semibold card-title">
						${title}</span> 
						<!--  -->
						
					</div>
					
					<div class="form-group row"></div>
				<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
				<c:set value="0" var="ttlAmt"/>
					<table class="table datatable-header-basic">
						<thead>
							<tr>
								<th width="5%">SR. No.</th>
								<th>Item Name</th>
								<th>Qty.</th>									
								<th>Rate</th>
								<th>CGST%</th>
								<th>CGST Amt</th>
								<th>SGST%</th>
								<th>SGST Amt</th>
								<th>Tax Amt</th>
								<th>Taxable Amt</th>
								<th>Total Amt</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orderDetail}" var="orderList" varStatus="count">
								 <tr>
									<td>${count.index+1}</td>
									<td>${orderList.orderNo}</td>
									<td>${orderList.deliveryDateDisplay} ${orderList.deliveryTimeDisplay}</td>
									<td>${orderList.frName}</td>	
									<td>${orderList.custName} ${orderList.custMobile}</td>
									<!-- ------------------------------------------------------------------------- -->
									<c:set var="ordStatus" value=""/>
									<c:if test="${orderList.orderStatus==0}">
										<c:set var="ordStatus" value="Park Order"/>
									</c:if>
									<c:if test="${orderList.orderStatus==1}">
										<c:set var="ordStatus" value="Shop Confirmation Pending"/>
									</c:if>	
									<c:if test="${orderList.orderStatus==2}">
										<c:set var="ordStatus" value="Accept"/>
									</c:if>
									<c:if test="${orderList.orderStatus==3}">
										<c:set var="ordStatus" value="Processing"/>
									</c:if>
									<c:if test="${orderList.orderStatus==4}">
										<c:set var="ordStatus" value="Delivery Pending"/>
									</c:if>
									<c:if test="${orderList.orderStatus==5}">
										<c:set var="ordStatus" value="Delivered"/>
									</c:if>
									<c:if test="${orderList.orderStatus==6}">
										<c:set var="ordStatus" value="Rejected by Shop"/>
									</c:if>	
									<c:if test="${orderList.orderStatus==7}">
										<c:set var="ordStatus" value="Return Order">
									</c:set>
									</c:if>	
									<c:if test="${orderList.orderStatus==8}">
										<c:set var="ordStatus" value="Cancelled Order" />									
									</c:if>
									
									<td>${ordStatus}</td>
									<!-- ------------------------------------------------------------------------- -->
									<c:set var="payMod" value=""></c:set>
									<c:if test="${orderList.paymentMethod==1}">
										<c:set var="payMod" value="Cash"></c:set>
									</c:if>
									<c:if test="${orderList.paymentMethod==2}">
										<c:set var="payMod" value="Card"></c:set>
									</c:if>		
									<c:if test="${orderList.paymentMethod==3}">
										<c:set var="payMod" value="E-Pay"></c:set>
									</c:if>										
									<td>${payMod}</td>		
									<c:set value="${orderList.totalAmt+ttlAmt}" var="ttlAmt"/>
									<td>${orderList.totalAmt}</td>	
									<td class="text-center">
										<div class="list-icons">
											<a href="#" onclick="getOrderDetal('${orderList.orderNo}')"
												class="list-icons-item" title="Detail" > <i
												class="fa fa-list"></i>
											</a>
										</div>																		
									</td>								
								</tr>
							</c:forEach>	
							<tr></tr>
							<tr>
								<td>Total</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>${ttlAmt}</td>
								<td></td>
							</tr>				
						</tbody>
					</table>
					<br>
					<input type="hidden" value="${typeId}" id="typeId">
					<input type="hidden" value="${fromDate}" id="fromDate">
					<input type="hidden" value="${toDate}" id="toDate">
					<input type="hidden" value="${statusList}" id="statusList">
					<input type="hidden" value="${reportBy}" id="reportBy">
					<input type="hidden" value="${datetype}" id="datetype">
					<input type="hidden" value="${yearVal}" id="yearVal">
					<div class="text-center">
						<button class="btn btn-danger" value="PDF" id="PDFButton"
								onclick="genPdf()">PDF</button>
								
								<input type="button" id="expExcel" class="btn btn-danger"
									value="EXPORT TO Excel" onclick="exportToExcel();">
							</div>
							<br>
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

<script>
function genPdf() {	

	var from_date = $("#fromDate").val();
	var to_date = $("#toDate").val();
	var statusList = $("#statusList").val();
	var id = $("#typeId").val(); // Franchise, Customer, etc.
	var reportBy = $("#reportBy").val();
	var datetype = $("#datetype").val();
	var yearVal = $("#yearVal").val();
	
	if(from_date!=null && to_date!=null){
		
		if(reportBy==1){
			window.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getOrderReportBtwnDatePdf/"
					+from_date+"/"+to_date+"/"+statusList);
			
		}else if(reportBy==2){
			window.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getFrReportDtlPdf/"
					+from_date+"/"+statusList+"/"+datetype+"/"+yearVal+"/"+id+"/"+reportBy);
		}else if(reportBy==3){
			window.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getCustReportDtlPdf/"
					+from_date+"/"+statusList+"/"+datetype+"/"+yearVal+"/"+id+"/"+reportBy);
		}else if(reportBy==4){
			window.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getOrderComeFromDtlPdf/"
					+from_date+"/"+statusList+"/"+datetype+"/"+yearVal+"/"+id+"/"+reportBy);
		}else if(reportBy==5){
			window.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getPaymentModeDtlPdf/"
					+from_date+"/"+statusList+"/"+datetype+"/"+yearVal+"/"+id+"/"+reportBy);
		}
	}
}
function exportToExcel() {

	window.open("${pageContext.request.contextPath}/exportToExcelDtl");
	document.getElementById("expExcel").disabled = true;
}
</script>

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
											<input type="text" class="form-control" readonly="readonly" id="orderNo">
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Customer Name <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="custName">
										</div>
								</div>
								
								<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Shop Name<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="frName">
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Order Status<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="orderStatus">
										</div>
								</div>
								
								<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Date & Time<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="dateTime">
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Payment Status<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="pamentStat">
										</div>
								</div>
								
								<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Order Type<span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="orderType">
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Payment Method<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="payMode">
										</div>
								</div>
								<div class="form-group row">
										<div style="display: none;">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="cust_name">Order Type<span class="text-danger">
											</span>:
												</label>
											<div class="col-lg-4">
												<input type="text" class="form-control" readonly="readonly" id="frName">
											</div>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Total<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="ttlAmt">
										</div>
								</div>
						</div>
					</div>	

					<table class="table" id="order_dtl_table">
						<thead>
							<tr>
								<th>Items Name</th>
								<th>Special Note</th>
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
											for="cust_name">Item Total <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="taxableAmt" style="text-align: right;">
										</div>
								</div>
								
								<div class="form-group row">										
									<div class="col-lg-6"></div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Tax <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="taxAmt" style="text-align: right;">
										</div>
								</div>
								
								<div class="form-group row">										
									<div class="col-lg-6"></div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Offer Discount <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="discAmt" style="text-align: right;">
										</div>
								</div>
								
								<div class="form-group row">										
									<div class="col-lg-6"></div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Delivery Charges <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="deliveryCharges" 
											style="text-align: right; border-bottom: solid;">
										</div>
								</div>
								
								<div class="form-group row">										
									<div class="col-lg-6"></div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Total <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control" readonly="readonly" id="totalOrderAmt" 
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
<script type="text/javascript">
function getOrderDetal(orderNo){
	
	$('#order_dtl_table td').remove();
	
	var statusList = $("#statusList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	
	if(orderNo!=null){
	
	$
	.getJSON(
			'${getOrderDetailById}',
			{
				orderNo : orderNo,
				statusList : statusList,
				toDate : toDate,
				fromDate : fromDate,
				ajax : 'true'

			},
			function(data) {
				
				$('#modal_large').modal('toggle');
				var status = null;
				var paymentMode = null;
				var pamentStatus=null;
				var orderType = null;
				var trailStatus=null;
				
				if(data[0].orderStatus==0){
					status = "Park Orde";
				}
				else if(data[0].orderStatus==1){
					status = "Shop Confirmation Pending";
				}
				else if(data[0].orderStatus==2){
					status = "Accept";							
				}
				else if(data[0].orderStatus==3){
					status = "Processing";
				}
				else if(data[0].orderStatus==4){
					status = "Delivery Pending";
				}
				else if(data[0].orderStatus==5){
					status = "Delivered";
				}
				else if(data[0].orderStatus==6){
					status = "Rejected by Shop";
				}
				else if(data[0].orderStatus==7){
					status = "Return Order";
				}
				else if(data[0].orderStatus==8){
					status = "Cancelled Order";
				}
								
				if(data[0].paymentMethod==1){
					paymentMode="Cash";
				}else if(data[0].paymentMethod==2){
					paymentMode="Card";
				}else{
					paymentMode="E-Pay";
				}
				
				if(data[0].orderPlatform==1){
					orderType="Executive";
				}else if(data[0].orderPlatform==2){
					orderType="Mobile App";
				}else{
					orderType="Web Site";
				}
				
				
				if(data[0].paidStatus==0){
					pamentStatus="Pending";
				}else{
					pamentStatus="Paid";
				}
				
				document.getElementById("orderNo").value = data[0].orderNo;
				document.getElementById("custName").value = data[0].custName;
				document.getElementById("frName").value = data[0].frName;
				document.getElementById("orderStatus").value = status;
				document.getElementById("pamentStat").value = pamentStatus;
				document.getElementById("dateTime").value = data[0].deliveryDateDisplay+" "+data[0].deliveryTimeDisplay;
				document.getElementById("orderType").value = orderType;
				document.getElementById("payMode").value = paymentMode;
				document.getElementById("ttlAmt").value = data[0].totalAmt;
				
				
				document.getElementById("taxableAmt").value = data[0].taxableAmt;
				document.getElementById("taxAmt").value = data[0].igstAmt;
				document.getElementById("discAmt").value = data[0].discAmt;
				document.getElementById("deliveryCharges").value = data[0].deliveryCharges;
				document.getElementById("totalOrderAmt").value = data[0].totalAmt;
				
				
				//alert("order dtl : "+JSON.stringify(data[0].orderDetailList))
				$
						.each(
								data[0].orderDetailList,
								function(key, itm) {
									
										
									
									var tr = $('<tr style="background:##03a9f4;"></tr>');									
									
									tr
									.append($(
												'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd;"></td>')
												.html(
														itm.itemName));
									tr
									.append($(
											'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
											.html(""));
									
									tr
									.append($(
											'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
											.html(itm.mrp));
									
									tr
									.append($(
											'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
											.html(itm.qty));
									
									tr
									.append($(
											'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
											.html(itm.mrp * itm.qty));

									$('#order_dtl_table tbody').append(tr);

									
								});

				//***************************************Trail Table*****************************************//
				$('#order_trail_table td').remove();
				
				$
				.each(
						data[0].orderTrailList,
						function(key, trail) {
							
							if(trail.status==0){
								trailStatus = "Park Orde";
							}
							else if(trail.status==1){
								trailStatus = "Shop Confirmation Pending";
							}
							else if(trail.status==2){
								trailStatus = "Accept";							
							}
							else if(trail.status==3){
								trailStatus = "Processing";
							}
							else if(trail.status==4){
								trailStatus = "Delivery Pending";
							}
							else if(trail.status==5){
								trailStatus = "Delivered";
							}
							else if(trail.status==6){
								trailStatus = "Rejected by Shop";
							}
							else if(trail.status==7){
								trailStatus = "Return Order";
							}
							else if(trail.status==8){
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
									.html(trail.userName));
							
							tr
							.append($(
									'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
									.html(trail.trailDate));
							
						

							$('#order_trail_table tbody').append(tr);

							
						});

			});
	}
}

</script>
</body>
</html>