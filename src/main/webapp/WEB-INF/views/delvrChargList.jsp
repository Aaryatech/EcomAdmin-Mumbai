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
							class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span>
						<!--  -->
						<span class="font-size-sm text-uppercase font-weight-semibold"><a class="card-title"
							href="${pageContext.request.contextPath}/addDeliveryCharge"
							style="color: white;"><i class="icon-add-to-list ml-2"
								style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add Delivery Charge</a></span>
					</div>

					<br>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

					<table class="table datatable-header-basic">
						<thead>
							<tr>
								<th width="5%">SR. No.</th>
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
									<td>${count.index+1}</td>
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
</body>
</html>