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
<c:url value="deleteSelMultiCategory" var="deleteSelMultiCategory"></c:url>
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
							${title}</span>
						<!--  -->
						<c:if test="${addAccess==0}">
							<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/newCategory"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add
									Category</a></span>
						</c:if>
					</div>

					<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
				<div class="card-body">
					<table class="table datatable-header-basic" id="printtable">
						<thead>
							<tr>
								<th width="10%">Sr. No.&nbsp; <input type="checkbox" name="selAll" id="selAll"/></th>
								<th>Category</th>
								<th>Prefix</th>
								<th>Image</th>
								<th>Status</th>								
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${catList}" var="catList" varStatus="count">
								<tr>
									<td>${count.index+1}   &nbsp;
									<input type="checkbox" id="${catList.catId}" value="${catList.catId}" name="cityId" class="chkcls"></td>
									<td>${catList.catName}</td>
									<td>${catList.catPrefix}</td>
									<td><img src="${imgPath}${catList.imageName}" alt="${catList.imageName}" height="42" width="60"></td>
									<td>${catList.isActive==1 ? 'Active' : 'In-Active'}</td>
									<td style="display: none;"></td>
									<td class="text-center"><c:if test="${editAccess==0}">
											<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/editCategory?catId=${catList.exVar1}"
													class="list-icons-item" title="Edit"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
										</c:if> <c:if test="${deleteAccess==0}">
											<div class="list-icons">
												<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${catList.exVar1}" data-popup="tooltip" title=""
													data-original-title="Delete"><i class="icon-trash"></i></a>
											</div>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
						<input type="hidden" value="${compId}" id="compId">
						<div class="text-center">
							<div class="form-check form-check-switchery form-check-inline">

								<label class="form-check-label"> <input type="checkbox"
									id="chkPdf" class="form-check-input-switchery" checked
									data-fouc> Click For show or hide header on pdf.
								</label>
							</div>
						</div>
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
												className : 'btn-danger'
											}
										},
										callback : function(result) {
											if (result) {
												location.href = "${pageContext.request.contextPath}/deleteCategory?catId="
														+ uuid;

											}
										}
									});
						});
		
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
					.open("${pageContext.request.contextPath}/pdfReport?url=pdf/getCategoryPdf/"+compId+"/"+showHead);
		}
		
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
										var catIds = [];
										$(".chkcls:checkbox:checked").each(function() {
											catIds.push($(this).val());
										});
										
										alert(catIds)
																
								$
								.getJSON(
										'${deleteSelMultiCategory}',
										{
											catIds : JSON.stringify(catIds),
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
</body>
</html>