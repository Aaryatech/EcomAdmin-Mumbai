<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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

.select2-selection--multiple .select2-selection__rendered {
	border-bottom: 1px solid #ddd;
}
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">
	<c:url value="/getConfigByCatId" var="getConfigByCatId"></c:url>
<c:url value="/getConfigFranchisePrintIds" var="getConfigFranchisePrintIds"/>
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
				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">


						<div class="card">

							<div
								class="card-header bg-blue text-white d-flex justify-content-between">
								<span
									class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span>
								<!--  -->
								<span class="font-size-sm text-uppercase font-weight-semibold"><a
									class="card-title" 
									href="${pageContext.request.contextPath}/configFranchise"
									style="color: white;"><i class="icon-add-to-list"></i>&nbsp;&nbsp;&nbsp;&nbsp;Configure
										Franchise</a></span><!-- showOfferConfigurationList -->
							</div>
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

							<div class="card-body">
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>
								<form
									action="${pageContext.request.contextPath}/configFranchiseList"
									id="configFranchiseList">





									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="frId">Franchise<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search"
												multiple="multiple" data-fouc name="frId" id="frId"
												data-placholder="Select ">
												<option value="0" ${fn:contains(frIds, 0) ? 'selected' : ''}>All</option>
												<c:forEach items="${frList}" var="list" varStatus="count">


		<c:set value="0" var="flag" />
																<c:forEach items="${frIds}" var="frIds">
																	<c:if test="${frIds == list.frId}">
																		<c:set value="1" var="flag" />
																	</c:if>
																</c:forEach>
																
																<c:choose>
																	<c:when test="${flag==1}">
																		<option value="${list.frId}" selected="selected">${list.frName}</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${list.frId}">${list.frName}</option>
																	</c:otherwise>
																</c:choose>
													<%-- <option value="${list.frId}"
														${fn:contains(frIds, list.frId) ? 'selected' : ''}>${list.frName}</option> --%>

												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_frId" style="display: none;">This field is
												required.</span>
										</div>


										<label class="col-form-label font-weight-bold col-lg-2"
											for="configId">Configuration<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select" multiple="multiple"
												data-fouc name="configId" id="configId"
												data-placholder="Select ">


												<option value="0"
													${fn:contains(configIds, 0) ? 'selected' : ''}>All</option>


												<c:forEach items="${configList}" var="list"
													varStatus="count">
													
														<c:set value="0" var="flag" />
																<c:forEach items="${configIds}" var="configIds">
																	<c:if test="${configIds == list.configHeaderId}">
																		<c:set value="1" var="flag" />
																	</c:if>
																</c:forEach>
																
																<c:choose>
																	<c:when test="${flag==1}">
																		<option value="${list.configHeaderId}" selected="selected">${list.configName}</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${list.configHeaderId}">${list.configName}</option>
																	</c:otherwise>
																</c:choose>

													<%-- <option value="${list.configHeaderId}"
														${fn:contains(configIds, list.configHeaderId) ? 'selected' : ''}>${list.configName}</option> --%>

												</c:forEach>
											</select><span class="validation-invalid-label text-danger"
												id="error_configId" style="display: none;">This field
												is required.</span>
										</div>
									</div>
									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="frId">Order By<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="orderBy" id="orderBy" data-placholder="Select ">
												<option value=""></option>
												<option value="1" ${orderBy==1 ? 'selected' : '' }>Configuration</option>
												<option value="2" ${orderBy==2 ? 'selected' : '' }>Franchise</option>
											</select> <span class="validation-invalid-label text-danger"
												id="error_orderBy" style="display: none;">This field
												is required.</span>
										</div>


										<div class="col-lg-2">
											<button type="submit" class="btn btn-primary">
												Search <i class="icon-paperplane ml-2"></i>
											</button>
										</div>
									</div>

								</form>

								<form
									action="${pageContext.request.contextPath}/deleteFrConfiguration"
									id="deleteFrConfiguration" method="post">




									<table class="table ddatatable-header-basic" id="printtable1">
										<thead>
											<tr>
												<th>Sel All<input type="checkbox" name="selAll"
													id="selAll" /></th>
												<th>Sr.No.</th>
												<th>Franchise Name</th>
												<th>Franchise Code</th>
												<th>City</th>
												<th>Route</th>
												<th>Configuration Name</th>
												<th>Action</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${frConfigList}" var="frConfigList"
												varStatus="count">

												<tr>
													<td><input type="checkbox"
														id="frachaseConfigId${frConfigList.frachaseConfigId}"
														value="${frConfigList.frachaseConfigId}"
														name="frachaseConfigId" class="select_all"></td>
													<td>${count.index+1})</td>
													<td>${frConfigList.frName}</td>
													<td>${frConfigList.frCode}</td>
													<td>${frConfigList.frCity}</td>
													<td>${frConfigList.route}</td>
													<td>${frConfigList.configName}</td>
													<td class="text-center"><c:if
															test="${deleteAccess==0}">
															<div class="list-icons">
																<a href="javascript:void(0)"
																	class="list-icons-item text-danger-600 bootbox_custom"
																	data-uuid="${frConfigList.frachaseConfigId}"
																	data-popup="tooltip" title=""
																	data-original-title="Delete"><i class="icon-trash"></i></a>

															</div>
														</c:if></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
									<div class="text-center">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn">
											Delete <i class="icon-paperplane ml-2"></i>
										</button>
										<button type="button" class="btn btn-primary" id="submtbtn1"
											data-toggle="modal" data-target="#modal_theme_primary"
											onclick="getHeaders()">
											Pdf/Excel <i class="fas fa-file-pdf"></i>
										</button>
									</div>
								</form>
								<input type="hidden" value="${compId}" id="compId">
							</div>
						</div>
						<!-- /a legend -->
					</div>
				</div>

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
										message : 'Are you sure you want to delete selected records?',
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
												location.href = "${pageContext.request.contextPath}/deleteFrConfig?configId="
														+ uuid;

											}
										}
									});
						});
	</script>

	<script>
		$(document).ready(

				function() {

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);

							});
				});
	</Script>

	<script type="text/javascript">
	
	$(document).ready(function($) {
		$("#configFranchiseList").submit(function(e) {
			var isError = false;
			var errMsg = "";
			
			if (!$("#orderBy").val()) {
				isError = true;
				$("#error_orderBy").show()
			} else {
				$("#error_orderBy").hide()
			}		 
		
			if (!isError) {
				var x = true;
				if (x == true) {
					
					return true;
				}
			}

			return false;

		});
	});
	
		$(document)
				.ready(
						function($) {

							$("#deleteFrConfiguration")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												var checked = $("#deleteFrConfiguration input:checked").length > 0;

												var count = $('#printtable1 tr').length;
												//alert(checked);
												if (!checked || count <= 1) {
													$("#error_chks").show()
													isError = true;
												} else {
													$("#error_chks").hide()
													isError = false;
												}

												if (!$("#frId").val()) {
													isError = true;
													$("#error_frId").show()
												} else {
													$("#error_frId").hide()
												}
												if (!$("#configId").val()) {
													isError = true;
													$("#error_configId").show()
												} else {
													$("#error_configId").hide()
												}

												if (!$("#orderBy").val()) {
													isError = true;
													$("#error_orderBy").show()
												} else {
													$("#error_orderBy").hide()
												}
												
												if (!isError) {
													var x = false;
													bootbox
															.confirm({
																title : 'Confirm ',
																message : 'Are you sure you want to delete selected records?',
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
																		$(".btn").attr("disabled", true);
																		var form = document
																				.getElementById("deleteFrConfiguration")
																		form
																				.submit();
																	}
																}
															});
													//end ajax send this to php page
													return false;
												}//end of if !isErro

												/* if (!isError) {
													var x = true;
													if (x == true) {
														document
																.getElementById("submtbtn").disabled = true;
														return true;
													}
												} */

												return false;

											});
						});
	</script>

	<script type="text/javascript">
		function getConfiguration(catId) {

			if (catId > 0) {

				$
						.getJSON(
								'${getConfigByCatId}',
								{
									catId : catId,
									ajax : 'true',
								},

								function(data) {

									//alert("Data " + JSON.stringify(data));
									var x = $
									{
										configId
									}
									;
									var html;
									var p = 0;
									var q = "Please Select";
									html += '<option  value="'+p+'" >' + q
											+ '</option>';
									html += '</option>';

									var len = data.length;
									for (var i = 0; i < len; i++) {

										if (x == data[i].configHeaderId) {
											html += '<option  Selected value="' + data[i].configHeaderId + '">'
													+ data[i].configName
													+ '</option>';
										} else {
											html += '<option value="' + data[i].configHeaderId + '">'
													+ data[i].configName
													+ '</option>';
										}

									}

									$('#configId').html(html);
									$("#configId").trigger("chosen:updated");

								});

			}
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
	
				$('#printtable1 > thead > tr > th').each(function(){
				    thArray.push($(this).text())
				})
				//console.log(thArray[0]);
					
				var seq = 0;
					for (var i = 1; i < thArray.length-1; i++) {
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
						var checked = $("#modelTable input:checked").length > 0;
					
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
								'${getConfigFranchisePrintIds}',
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
											var compId = $("#compId").val();
											var frIds = $("#frId").val();
											var configIds = $("#configId").val();
											var orderBy = $("#orderBy").val();
											
											var showHead = 0;
											if($("#chkPdf").is(":checked")){
												showHead = 1;
											}else{
												showHead = 0;
											}
						window.open('${pageContext.request.contextPath}/pdfReport?url=pdf/getConfigFrListPdf/'+compId+'/'+elemntIds.join()+'/'+frIds+'/'+configIds+'/'+orderBy+'/'+showHead);
										}
									}
								});
						}
					}		
				</script>

</body>
</html>