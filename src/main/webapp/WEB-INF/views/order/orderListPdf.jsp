<%@page contentType="text/html; charset=ISO8859_1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.lang.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <title>Production List</title> -->
<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<style type="text/css">
@media print {
	.no-break {
		page-break-inside: avoid !important;
	}
	
}
body{
	
    background-color: #fff;
	
}
table {
	border-collapse: collapse;
	font-size: 10;
	font-size : 13;
	width: 90%;
	page-break-inside: auto !important;
	margin-left: 5%;
	margin-right: 5%;
}

table th {
  background-color: #f44336 !important;
  color: black;
}


.footer_btm{position: fixed; text-align: center; padding: 10px; bottom: 0; left:0; font-size: 12px; 
color:#333; width: 100%; background: #f5f5f5; min-height: 35px;}
.footer_lft{position: fixed; left:6px; bottom:6px; width: 20px; height: 20px;}
.footer_lft img{width: 100%;}
.footer_right{position: fixed; right:6px; bottom:6px; }
.footer_right span{display: inline-block; margin: 0 3px 0 0; font-size: 12px; }
.footer_right img{width: 20px; height: 20px;}


</style>
</head>
<body>
					<div class="no-break">
						<c:if test="${allowCompName==1}">
							<div
							style="text-align: center; font-size: 18px; margin-top: 20px; margin-bottom: 5px;">
			
								<h3
									style="color: #000; text-align: center; margin: 0px;">${company}</h3>
							</div>
						</c:if>

						<div
							style="text-align: center; font-size: 18px;">

							<h4
								style="color: #000; font-size: 16px; text-align: center; margin: 0px;">${reportName}</h4>
							<c:if test="${allowFilter==1}">
								<p
									style="color: #000; font-size: 15px; text-align: center; font-weight: bold;">
									Date : ${fromDate}&nbsp;&nbsp; to &nbsp;&nbsp;${toDate}</p>
							</c:if>
						</div> 
				
					

				<table align="center" border="1" cellpadding="0" cellspacing="0"
				style="border-top: 1px solid #313131;" class="table datatable-header-basic">
					<tr>				
					
						<th width="5%">Sr.No.</th>
						<th width="10">Order No.</th>
						<th width="10">Order Date</th>
						<th width="10%">Delivery Date</th>
						<th width="20%">Customer</th>		
						<th width="20%">Franchise</th>		
						<th width="20%">Order Status</th>		
						<th width="15%">Payment Mode</th>	
						<th width="20%">Total Amt.</th>		
					</tr>


					<c:set value="1" var="srno"></c:set>
					<c:set value="0" var="total"></c:set>

					<c:forEach items="${orderList}" var="order" varStatus="count">
							<tr>
								<td width="5%">${srno}</td>
								<td width="10%" style="text-align: center;">${order.orderNo}</td>
								<td width="10%" style="text-align: center;">${order.orderDateDisplay}</td>
								<td width="10%" style="text-align: center;">${order.deliveryDateDisplay}</td>
								<td width="20%" style="text-align: left;">${order.custName}-${order.custMobile}</td>
								<td width="20%" style="text-align: left;">${order.frName}</td>	
								
								<!-- ---------------------------------------------------- -->
								<c:set value="" var="orderStatus"/>
								<c:choose>
									<c:when test="${order.orderStatus==0}">
										<c:set value="Park Order" var="orderStatus"/>
									</c:when>
									<c:when test="${order.orderStatus==1}">
										<c:set value="Shop Confirmation Pending" var="orderStatus"/>
									</c:when>
									<c:when test="${order.orderStatus==2}">
										<c:set value="Accept" var="orderStatus"/>
									</c:when>
									<c:when test="${order.orderStatus==3}">
										<c:set value="Processing" var="orderStatus"/>
									</c:when>
									<c:when test="${order.orderStatus==4}">
										<c:set value="Delivery Pending" var="orderStatus"/>
									</c:when>
									<c:when test="${order.orderStatus==5}">
										<c:set value="Delivered" var="orderStatus"/>
									</c:when>
									<c:when test="${order.orderStatus==6}">
										<c:set value="Rejected by Shop" var="orderStatus"/>
									</c:when>
									<c:when test="${order.orderStatus==7}">
										<c:set value="Return Order" var="orderStatus"/>
									</c:when>
									<c:when test="${order.orderStatus==8}">
										<c:set value="Cancelled Order" var="orderStatus"/>
									</c:when>
									<c:otherwise>
										<c:set value="/" var="orderStatus"/>
									</c:otherwise>
								</c:choose>
								<td width="20%" style="text-align: left;">${orderStatus}</td>	
								
								<!-- ------------------------------------------------ -->
								<c:set value="" var="payMode"/>	
								<c:choose>
									<c:when test="${order.paymentMethod==1}">
										<c:set value="Cash" var="payMode"/>
									</c:when>
									<c:when test="${order.paymentMethod==2}">
										<c:set value="Card" var="payMode"/>
									</c:when>
									<c:otherwise>
										<c:set value="E-Pay" var="payMode"/>
									</c:otherwise>
								</c:choose>							
								<td width="15%" style="text-align: center;">${payMode}</td>	
								<!-- ---------------------------------------------------- -->
								<td width="20%" style="text-align: right;">${order.totalAmt}</td>									
							</tr>

							<c:set value="#{srno+1}" var="srno"></c:set>
							<c:set value="${total+order.totalAmt}" var="total"></c:set>
						

					</c:forEach>

					<tr>
						<td colspan="8"
							style="font-weight: bold; border-bottom: 1px solid #313131; border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 15px;">Total</td>
						<td width="20%"
							style="font-weight: bold; border-bottom: 1px solid #313131; border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 15px; text-align: right;">${total}</td>
					</tr>
				</table> 
				
						
		</div>
		<div style="page-break-after: auto;"></div>
	
</body>
</html>













<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
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
							class="font-size-sm text-uppercase font-weight-semibold card-title">Order
							List</span>
					</div>

					<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

					<table class="table datatable-header-basic">
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
									</tr>
						</thead>
						<tbody>
							<c:forEach items="${orderList}" var="orderList" varStatus="count">
								<tr>
									<td>${count.index+1}</td>
									<td>${orderList.orderNo}</td>
									<td>${orderList.orderDateDisplay}</td>
									<td>${orderList.deliveryDateDisplay}</td>
									<td>${orderList.custName}</td>
									<td>${orderList.frName}</td>
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
									<td>${orderList.totalAmt}</td>		
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
												location.href = "${pageContext.request.contextPath}/deleteUom?uomId="
														+ uuid;

											}
										}
									});
						});
	</script>
</body>
</html> --%>