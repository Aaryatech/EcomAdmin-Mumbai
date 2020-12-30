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
<c:url value="deleteSelMultiOffer" var="deleteSelMultiOffer"></c:url>
<c:url value="/getOfferPrintIds" var="getOfferPrintIds"/>
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


					<table class="table datatable-header-basic" id="printtable">
						<thead>
							<tr>
								<th width="5%">Sr. No. &nbsp; <input type="checkbox" name="selAll" id="selAll"/></th>
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
									<td>${count.index+1}   &nbsp;
									<input type="checkbox" id="${offer.offerId}" value="${offer.offerId}" name="cityId" class="chkcls"></td>
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
					<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
										<div class="text-center">
							<button type="submit" class="btn btn-primary" id="submtbtn"
								onclick="deletSelctd()">
								Delete <i class="far fa-trash-alt"></i>
							</button>
							
							
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
	<!-- /page content -->

	<script type="text/javascript">
	$(document).ready(

			function() {

				$("#selAll").click(
						function() {
							$('#printtable tbody input[type="checkbox"]')
									.prop('checked', this.checked);

						});
			});
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
		<script >
	function deletSelctd(){	
		var isError = false;
		var checked = $("#printtable input:checked").length > 0;
		var count = $('#printtable tr').length;
		
		if (!checked || count <= 1) {
			$("#error_chks").show()
			isError = true;
		} else {
			$("#error_chks").hide()
			isError = false;
		}
		
		if(!isError){
			

			var x = false;
			bootbox
					.confirm({
						title : 'Confirm ',
						message : 'Are you sure you want to Submit ?',
						buttons : {
							confirm : {
								label : 'Yes',
								className : 'btn-success'
							},
							cancel : {
								label : 'Cancel',
								className : 'btn-danger'
							}
						},
						callback : function(
								result) {
							if (result) {
								$(
										".btn")
										.attr(
												"disabled",
												true);								
										var offerIds = [];
										$(".chkcls:checkbox:checked").each(function() {
											offerIds.push($(this).val());
										});
										
										alert(offerIds)
																
								$
								.getJSON(
										'${deleteSelMultiOffer}',
										{
											offerIds : JSON.stringify(offerIds),
											ajax : 'true'
										},
										function(data) {
											//alert(data.error);
									 if(!data.error){
												window.location.reload();
											}else{
												window.location.reload();
											} 
											
										});
							}
						}
					});
			//end ajax send this to php page
			return false;
		}//end of if !isError
	}
	
	
	</script>
	
	
	 <!-- Primary modal -->
				<div id="modal_theme_primary" class="modal fade" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header bg-primary">
								<h6 class="modal-title">Select Header</h6>
								<button type="button" class="close" data-dismiss="modal">&times;</button>
							</div>
				
							<div class="modal-body">
								<table class="table table-bordered table-hover table-striped"
										width="100%" id="modelTable">
									<thead>
										<tr>
											<th width="15">Sr.No.
											<input type="checkbox" name="selAll" id="selAllChk"/>
											</th>
											<th>Headers</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								<span class="validation-invalid-label" id="error_modelchks"
										style="display: none;">Select Check Box.</span>
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
	
				$('#printtable > thead > tr > th').each(function(){
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

							$("#selAllChk").click(
									function() {
										$('#modelTable tbody input[type="checkbox"]')
												.prop('checked', this.checked);

									});
						});
				
				  function getIdsReport(val) {
					  var isError = false;
						var checked = $("#modal_theme_primary input:checked").length > 0;
					
						if (!checked) {
							$("#error_modelchks").show()
							isError = true;
						} else {
							$("#error_modelchks").hide()
							isError = false;
						}

						if(!isError){
					  var elemntIds = [];										
								
								$(".chkcls:checkbox:checked").each(function() {
									elemntIds.push($(this).val());
								}); 
														
						$
						.getJSON(
								'${getOfferPrintIds}',
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
											 window.open('${pageContext.request.contextPath}/pdfReport?url=pdf/getOfferListPdf');
										}
									}
								});
						}
					}		
				</script>

</body>
</html>