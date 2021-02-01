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
<c:url value="deleteSelMultiDelCharges" var="deleteSelMultiDelCharges"></c:url>
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
							class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span>
						<!--  -->
						<span class="font-size-sm text-uppercase font-weight-semibold"><a class="card-title"
							href="${pageContext.request.contextPath}/addDeliveryCharge"
							style="color: white;"><i class="icon-add-to-list ml-2"
								style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add Delivery Charge</a></span>
					</div>

					<br>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

					<table class="table datatable-header-basic" id="printtable">
						<thead>
							<tr>
								<th width="5%">SR. No.&nbsp; <input type="checkbox" name="selAll" id="selAll"/></th>
								<th>Group Name</th>
								<th>MIN Km.</th>
								<th>MAX Km.</th>
								<th>Amt. 1</th>
								<th>Amt. 2</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${chargeList}" var="chargeList" varStatus="count">
								<tr>
									<td>${count.index+1}   &nbsp;
									<input type="checkbox" id="city${chargeList.chId}" value="${chargeList.chId}" name="cityId" class="chkcls"></td>
									<td>${chargeList.groupName}</td>
									<td>${chargeList.minKm}</td>
									<td>${chargeList.maxKm}</td>
									<td>${chargeList.amt1}</td>
									<td>${chargeList.amt2}</td>
									<td class="text-center">
										<div class="list-icons">
											<a
												href="${pageContext.request.contextPath}/editDeliveryCharge?chargeId=${chargeList.exVar1}"
												class="list-icons-item" title="Edit"> <i
												class="icon-database-edit2"></i>
											</a>
										</div>
										<div class="list-icons">
											<a href="javascript:void(0)"
												class="list-icons-item text-danger-600 bootbox_custom"
												data-uuid="${chargeList.exVar1}" data-popup="tooltip" title=""
												data-original-title="Delete"><i class="icon-trash"></i></a>
										</div>
										
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
						<input type="hidden" value="${compId}" id="compId">
						
						<div class="text-center">
							<div class="form-check form-check-switchery form-check-inline">

								<label class="form-check-label"> <input type="checkbox" id="chkPdf"
									class="form-check-input-switchery" checked data-fouc>
									Click For show or hide header on pdf.
								</label>
							</div>
						</div>

<c:choose>
					<c:when test="${chargeListSize<=0}">
					<div style="text-align: center;margin: 0,auto;" >
					<img src="${pageContext.request.contextPath}/resources/global_assets/images/norecordfound.jpg" alt="">
					</div>
					</c:when>
					<c:otherwise>
						<div class="text-center">
							<button type="submit" class="btn btn-primary" id="submtbtn"
								onclick="deletSelctd()">
								Delete <i class="far fa-trash-alt"></i>
							</button>
							
							<button type="button" class="btn btn-primary" id="submtbtn"
								onclick="exportToExcel()">
								Excel <i class="far fa-file-excel"></i>
							</button> 

							<button type="button" class="btn btn-primary" id="submtbtn1"  onclick="genPdf()">
								Pdf<i class="fas fa-file-pdf"></i>
							</button>
							</div>
					</c:otherwise>
					
					</c:choose>


						
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
												location.href = "${pageContext.request.contextPath}/deleteDeliveryCharge?chargeId="
														+ uuid;

											}
										}
									});
						});
		
		function getItemTag(tagId, tagName){
			var status = null;
			document.getElementById("tag_name").innerHTML=tagName;
			
			$('#item_tag_table td').remove();
			if(tagId>0){
			
			$
			.getJSON(
					'${showItemTagList}',
					{
						tagId : tagId,
						ajax : 'true'

					},
					function(data) {

						//alert(JSON.stringify(data));						

						$
								.each(
										data,
										function(key, itm) {
											if(itm.isUsed==0){
												status = 'Active'
											}else{
												status = 'In-Active'
											}
												
											
											var tr = $('<tr style="background:#cbff92;"></tr>');
											tr
											.append($(
													'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
													.html(key+1));
											
											tr
											.append($(
														'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd;"></td>')
														.html(
																itm.itemName));
											tr
											.append($(
													'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
													.html(status));

											$('#item_tag_table tbody').append(tr);

											
										});

						

					});
			}
			
		}
	</script>
	
	<script>
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
										var chIds = [];
										$(".chkcls:checkbox:checked").each(function() {
											chIds.push($(this).val());
										});
										
										alert(chIds)
																
								$
								.getJSON(
										'${deleteSelMultiDelCharges}',
										{
											chIds : JSON.stringify(chIds),
											ajax : 'true'
										},
										function(data) {
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

	function exportToExcel() {
		window.open("${pageContext.request.contextPath}/exportToExcelNew");
		document.getElementById("expExcel").disabled = true;
	}

	function genPdf() {
		var compId = $("#compId").val();

		var showHead = 0;
		if($("#chkPdf").is(":checked")){
			showHead = 1;
		}else{
			showHead = 0;
		}
		window
				.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getDelvrChrgListPdf/"+compId+"/"+showHead);
	}
	</script>
</body>
</html>