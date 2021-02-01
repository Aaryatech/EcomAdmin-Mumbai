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
<c:url value="/getSpHomePagePrint" var="getSpHomePagePrint"/>
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
						<span class="font-size-sm text-uppercase font-weight-semibold card-title" >
						<h6 class="card-title">${title}</h6></span> 
						<!--  -->
						<c:if test="${addAccess==0}">
						<span
							class="font-size-sm text-uppercase font-weight-semibold"><a class="card-title"
							href="${pageContext.request.contextPath}/newSpDayHomePage"
							style="color: white;"><i
								class="icon-add-to-list ml-2" style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add Sp Day
								Home Page</a></span>
						</c:if>
					</div>
					
					<div class="form-group row"></div>
				<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
			<div class="card-body">
					<table class="table datatable-header-basic" id="printtable2">
						<thead>
							<tr>
								<th width="10%">Sr. No.</th>
								<th>SP Day Name</th>
								<th>SP Day Caption</th>									
								<th style="display: none;">Franchise</th>
								<th style="display: none;">Tags</th>	
								<th style="display: none;">Dates</th>
								<th style="display: none;">Time</th>
								<th style="display: none;">Sort No.</th>
								<th>Status</th>	
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${spDayList}" var="spDayList" varStatus="count">
								 <tr>
									<td>${count.index+1}</td>
									<td>${spDayList.spdayName}</td>
									<td>${spDayList.spdayCaptionHomePage}</td>									
									<td>${spDayList.isActive==1 ? 'Active' : 'In-Active'}</td>										
									<td style="display: none;"></td>
									<td style="display: none;"></td>	
																	
															
									<td class="text-center">
									<c:if test="${editAccess==0}">
										<div class="list-icons">
											<a href="${pageContext.request.contextPath}/editSpday?spDayId=${spDayList.exVar1}"
												class="list-icons-item" title="Edit"> <i
												class="icon-database-edit2"></i>
											</a>
										</div>
										</c:if> <c:if test="${deleteAccess==0}">
										<div class="list-icons">
										<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${spDayList.exVar1}" data-popup="tooltip"
													title="" data-original-title="Delete"><i
													class="icon-trash"></i></a>
										</div>
										</c:if>										
									</td>								
								</tr>
							</c:forEach>					
						</tbody>
					</table>
					<div class="text-center">
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
//Custom bootbox dialog
$('.bootbox_custom')
		.on(
				'click',
				function() {
					var uuid = $(this).data("uuid") // will return the number 123
								bootbox.confirm({
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
										location.href = "${pageContext.request.contextPath}/deleteSpDayHomePage?spDayId="
												+ uuid;

									}
								}
							});
				});
</script>

 <!-- Primary modal -->
				<div id="modal_theme_primary" class="modal fade" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header bg-primary">
								<h6 class="modal-title">SP Day Home Page List</h6>
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
							<input type="hidden" value="${compId}" id="compId">

<div class="text-center">
							<div class="form-check form-check-switchery form-check-inline">

								<label class="form-check-label"> <input type="checkbox" id="chkPdf"
									class="form-check-input-switchery" checked data-fouc>
									Click For show or hide header on pdf.
								</label>
							</div>
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
	
				$('#printtable2 > thead > tr > th').each(function(){
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
								'${getSpHomePagePrint}',
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
											var showHead = 0;
											if($("#chkPdf").is(":checked")){
												showHead = 1;
											}else{
												showHead = 0;
											}
											var compId = $("#compId").val();
											 window.open('${pageContext.request.contextPath}/pdfReport?url=pdf/getSpHomePageListPdf/'+compId+'/'+elemntIds.join()+'/'+showHead);
											 $('#selAllChk').prop('checked', false);

										}
									}
								});
						}
					}		
				</script>
</body>
</html>