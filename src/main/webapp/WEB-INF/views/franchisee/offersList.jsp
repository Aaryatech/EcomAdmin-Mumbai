<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
							class="font-size-sm text-uppercase font-weight-semibold card-title">
							Offer List </span>
						<!--  -->
						<span class="font-size-sm text-uppercase font-weight-semibold"><a
							href="${pageContext.request.contextPath}/addNewOffers/0"
							style="color: white;" class="card-title"><i
								class="icon-plus2 "></i>&nbsp;&nbsp;&nbsp;&nbsp;Add
								New Offer</a></span>
					</div>

					<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>


					<table class="table datatable-header-basic">
						<thead>
							<tr>
								<th width="5%">SR. No.</th>
								<th>Offer Name</th>
								<th>Type</th>
								<th>Day/Date</th>
								<th>Duration</th>
								<th>Offer On</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${offerList}" var="offer" varStatus="count">

								<tr>
									<td><c:out value="${count.index+1}" /></td>
									<td>${offer.offerName}</td>

									<c:choose>
										<c:when test="${offer.type==1}">
											<td>POS</td>
										</c:when>
										<c:otherwise>
											<td>Online</td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${offer.frequencyType==1}">
											<td>Days</td>

											<td>
												<!--  --> <c:set var="freqStr" value="${offer.frequency}" />
												<c:set var="arrayofFreq" value="${fn:split(freqStr,',')}" />
												<c:set var="freq" value="" /> <c:forEach var="i" begin="0"
													end="${fn:length(arrayofFreq)}">
													<%-- arrayofmsg[${i}]: ${arrayofmsg[i]}<br> --%>



													<c:choose>
														<c:when test="${arrayofFreq[i]==2}">
															<div class="badge bg-grey-300 mx-1">Monday</div>
														</c:when>
														<c:when test="${arrayofFreq[i]==3}">
															<div class="badge bg-grey-300 mx-1">Tuesday</div>
														</c:when>
														<c:when test="${arrayofFreq[i]==4}">
															<div class="badge bg-grey-300 mx-1">Wednesday</div>
														</c:when>
														<c:when test="${arrayofFreq[i]==5}">
															<div class="badge bg-grey-300 mx-1">Thursday</div>
														</c:when>
														<c:when test="${arrayofFreq[i]==6}">
															<div class="badge bg-grey-300 mx-1">Friday</div>
														</c:when>
														<c:when test="${arrayofFreq[i]==7}">
															<div class="badge bg-grey-300 mx-1">Saturday</div>
														</c:when>
														<c:when test="${arrayofFreq[i]==1}">
															<div class="badge bg-grey-300 mx-1">Sunday</div>
														</c:when>



													</c:choose>
												</c:forEach>
											</td>


										</c:when>
										<c:otherwise>
											<td>Date</td>

											<%-- <fmt:formatDate pattern="dd-MM-yyyy" value="${parsedfromDate}" /> --%>

											<c:set var="from" value="${offer.fromDate}" />
											<fmt:parseDate value="${from}" var="parsedfromDate"
												pattern="yyyy-MM-dd" />

											<c:set var="to" value="${offer.toDate}" />
											<fmt:parseDate value="${to}" var="parsedToDate"
												pattern="yyyy-MM-dd" />

											<td><fmt:formatDate type="date"
													value="${parsedfromDate}" /> &nbsp;To&nbsp; <fmt:formatDate
													type="date" value="${parsedToDate}" /></td>

										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${offer.offerType==1}">
											<td>Bill</td>
										</c:when>
										<c:otherwise>
											<td>Item</td>
										</c:otherwise>
									</c:choose>

									<td style="display: none;"></td>
									<td class="text-center">
										<div class="list-icons">


											<a
												href="${pageContext.request.contextPath}/addNewOffers/${offer.offerId}"
												class="list-icons-item" title="Edit"> <i
												class="icon-database-edit2"></i>
											</a>
											<!--  -->
											&nbsp;&nbsp;
											<!--  -->
											<a href="javascript:void(0)"
												class="list-icons-item text-danger-600 bootbox_custom"
												data-uuid="${offer.offerId}" data-popup="tooltip" title=""
												data-original-title="Delete"><i class="icon-trash"></i></a>

										</div>
									</td>
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
												location.href = "${pageContext.request.contextPath}/deleteOfferHeaderById?offerId="
														+ uuid;

											}
										}
									});
						});
	</script>

</body>
</html>