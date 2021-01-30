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
<c:url var="getFrDetailById" value="getFrDetailById"/>
<c:url value="deleteSelMultiFranchises" var="deleteSelMultiFranchises"></c:url>
<c:url value="getFranchisePrintIds" var="getFranchisePrintIds"/>
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
								href="${pageContext.request.contextPath}/newFranchise/0"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add Franchise</a></span>
						</c:if>
					</div>

					<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

					<table class="table datatable-header-basic" id="printtable">
						<thead>
							<tr>
								<th width="10%">Sr. No. &nbsp; <input type="checkbox" name="selAll" id="selAll" class="selAllTab"></th>
								<th>Franchise</th>
								<th>Contact No.</th>
								<th>FDA Lic. Expiry Date</th>
								<th>Status</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${frList}" var="frList" varStatus="count">
								<tr>
									<td>${count.index+1}   &nbsp;
									<input type="checkbox" id="${frList.frId}" value="${frList.frId}" name="cityId" class="chkcls"></td>
									<td>${frList.frCode} - ${frList.frName}</td>
									<td>${frList.frContactNo}</td>
									<td>${frList.fdaLicenseDateExp}</td>
									<td>${frList.isActive==1 ? 'Active' : 'In-Active'}</td>									
									<td class="text-center"><c:if test="${editAccess==0}">
									<div class="list-icons">
											<a href="#" onclick="getFrDetail(${frList.frId})"
												class="list-icons-item" title="Add Additional Chages">
												<i class="fas fa-plus"></i>
											</a>&nbsp;
										</div>
											<div class="list-icons">
												<a
													href="${pageContext.request.contextPath}/newFranchise/${frList.frId}"
													class="list-icons-item" title="Edit"> <i
													class="icon-database-edit2"></i>
												</a>
											</div>
										</c:if> <c:if test="${deleteAccess==0}">
											<div class="list-icons">
												<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${frList.frId}" data-popup="tooltip" title=""
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
				<table class="table datatable-header-basic" id="printtable2" style="display: none;">
						<thead>
							<tr>
								<th width="10%">Sr. No</th>
								<th>Code</th>
								<th>Franchise</th>
								<th>Opening Date</th>
								<th>Owner's DOB</th>
								<th>Email</th>
								<th>Contact</th>
								<th>Address</th>
								<th>City</th>
								<th>Pincode</th>
								<th>Status</th>
								<th>Rating</th>
								<th>FDA No.</th>
								<th>FDA Lic.Expiry</th>
								<th>GST Type</th>
								<th>GST No.</th>
								<th>Longitude</th>
								<th>Latitude</th>
								<th>Served Pincodes</th>
								<th>Km. Cover</th>
								<th>Bank</th>
								<th>Branch</th>
								<th>IFSC</th>
								<th>A/C No.</th>
								<th>Payment Gateway</th>
								<th>Parent P/G</th>
								<th>PAN</th>
								<th>Charges B/w Date</th>
								<th>Surcharge Fees</th>
								<th>Packing Chg</th>
								<th>Handling Chg</th>
								<th>Extra Chg</th>
								<th>Round Off Amt</th>					
							</tr>
						</thead>
						<tbody>
						
						</tbody>
					</table>					
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
<script src="${pageContext.request.contextPath}/resources/assets/commanjs/checkAll.js"></script>

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
												className : 'btn-danger'
											}
										},
										callback : function(result) {
											if (result) {
												location.href = "${pageContext.request.contextPath}/deleteFranchise?frId="
														+ uuid;

											}
										}
									});
						});
	</script>
	<!-- Large modal -->
	<div id="modal_large" class="modal fade" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header bg-blue"
					style="padding: 10px 12px 0px 10px;">
					<h5 class="modal-title">Additional Charges</h5>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<form
					action="${pageContext.request.contextPath}/addFrAdditionalChrgs"
					id="submitInsert" method="post">
					<div class="modal-body">
						<!-- <h6 class="font-weight-semibold">Text in a modal</h6> -->

						<div class="form-group row">
							<label class="col-form-label font-weight-bold col-lg-2"
								for="kmeter">Franchise<span class="text-danger"></span>:
							</label>
							<div class="col-lg-4">
								<input type="text" class="form-control" name="fr_name"
									id="fr_name" readonly="readonly">
							</div>

							<label class="col-form-label font-weight-bold col-lg-2"
								for="cust_name"> <span class="text-danger">*
							</span>:
							</label>
							<div class="col-lg-4">
								<input type="hidden" class="form-control" name="fr_id" id="fr_id"
									 readonly="readonly">
							</div>

						</div>

						<hr>

						<h6 class="font-weight-semibold">Additional Charges</h6>
						
						<input type="hidden" class="form-control" name="charge_id" id="charge_id"
							 value="${charges.chargeId}">

						<div class="form-group row">
							<label class="col-form-label font-weight-bold col-lg-2"
								for="dates">Date<span class="text-danger">*</span>:
							</label>
							<div class="col-lg-4">
								<input type="text" class="form-control daterange-basic_new"
									name="dates" id="dates" autocomplete="off">
							</div>

							<label class="col-form-label font-weight-bold col-lg-2"
								for="surcharge">Surcharge Fees<span class="text-danger">*
							</span>:
							</label>
							<div class="col-lg-4">
								<input type="text" class="form-control maxlength-badge-position" maxlength="5" name="surcharge"
									id="surcharge" value="${charges.surchargeFee}" autocomplete="off"> <span
									class="validation-invalid-label text-danger" 
									id="error_surcharge" style="display: none;">This field
									is required.</span>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-form-label font-weight-bold col-lg-2"
								for="packing">Packing Charges<span class="text-danger">*</span>:
							</label>
							<div class="col-lg-4">
								<input type="text" class="form-control maxlength-badge-position" maxlength="5" " name="packing"
									id="packing" value="${charges.packingChg}" autocomplete="off"> <span
									class="validation-invalid-label text-danger" id="error_packing"
									style="display: none;">This field is required.</span>
							</div>

							<label class="col-form-label font-weight-bold col-lg-2"
								for="handling">Handling Charges<span class="text-danger">*
							</span>:
							</label>
							<div class="col-lg-4">
								<input type="text" class="form-control maxlength-badge-position" maxlength="5" " name="handling"
									id="handling" value="${charges.handlingChg}" autocomplete="off"><span
									class="validation-invalid-label text-danger"
									id="error_handling" style="display: none;">This field is
									required.</span>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-form-label font-weight-bold col-lg-2"
								for="extra">Extra Charges<span class="text-danger">*</span>:
							</label>
							<div class="col-lg-4">
								<input type="text" class="form-control maxlength-badge-position" maxlength="5" " name="extra" id="extra"
									value="${charges.extraChg}" autocomplete="off"><span
									class="validation-invalid-label text-danger" id="error_extra"
									style="display: none;">This field is required.</span>
							</div>

							<label class="col-form-label font-weight-bold col-lg-2"
								for="round_off">Round Off Amt.<span class="text-danger">*
							</span>:
							</label>
							<div class="col-lg-4">
								<input type="text" class="form-control maxlength-badge-position" maxlength="5" " name="round_off"
									id="round_off" value="${charges.roundOffAmt}" autocomplete="off"><span
									class="validation-invalid-label text-danger"
									id="error_round_off" style="display: none;">This field
									is required.</span>
							</div>
						</div>
					</div>
					<div class="modal-footer">						
						<button type="submit" class="btn bg-primary">Save<i class="icon-paperplane ml-2"></i></button>
					</div>
				</form>

			</div>
		</div>
	</div>
	<!-- /large modal -->
	<script>
	function getFrDetail(frId) {

		$.getJSON('${getFrDetailById}', {
			frId : frId,
			ajax : 'true',
		}, function(data) {
		//	alert(JSON.stringify(data))
		
			if(data!=null){
				document.getElementById("fr_name").value=data.franchise.frName+"-"+data.franchise.frCode;
				document.getElementById("fr_id").value=data.franchise.frId;
				
				if(data.frCharges!=null){
					document.getElementById("dates").value=data.frCharges.fromDate+" to "+data.frCharges.toDate;
					document.getElementById("charge_id").value=data.frCharges.chargeId;
					document.getElementById("surcharge").value=data.frCharges.surchargeFee;
					document.getElementById("packing").value=data.frCharges.packingChg;
					document.getElementById("handling").value=data.frCharges.handlingChg;
					document.getElementById("extra").value=data.frCharges.extraChg;
					document.getElementById("round_off").value=data.frCharges.roundOffAmt;
				}
				$("#modal_large").modal('show');
			}
			
		});

	}
	
	$('#modal_large').on('hidden.bs.modal', function () {
		var today = new Date();
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		var yyyy = today.getFullYear();

		today = mm + '-' + dd + '-' + yyyy;
		
		document.getElementById("dates").value=today+" to "+today;
		document.getElementById("charge_id").value=0.0;
		document.getElementById("surcharge").value=0.0;
		document.getElementById("packing").value=0.0;
		document.getElementById("handling").value=0.0;
		document.getElementById("extra").value=0.0;
		document.getElementById("round_off").value=0.0;
	}); 
	
	$('.daterange-basic_new').daterangepicker({
		applyClass : 'bg-slate-600',

		cancelClass : 'btn-light',
		locale : {
			format : 'DD-MM-YYYY',
			separator : ' to '
		}
	});

	$(document).ready(function($) {

		$("#submitInsert").submit(function(e) {
			var isError = false;
			var errMsg = "";

			if (!$("#surcharge").val()) {
				isError = true;
				$("#error_surcharge").show()
			} else {
				$("#error_surcharge").hide()
			}

			if ($("#packing").val() == "") {
				isError = true;
				$("#error_packing").show()
			} else {
				$("#error_packing").hide()
			}

			if (!$("#handling").val()) {
				isError = true;
				$("#error_handling").show()
			} else {
				$("#error_handling").hide()
			}

			if (!$("#extra").val()) {
				isError = true;
				$("#error_extra").show()
			} else {
				$("#error_extra").hide()
			}

			if (!$("#round_off").val()) {
				isError = true;
				$("#error_round_off").show()
			} else {
				$("#error_round_off").hide()
			}

			if (!isError) {
				var x = true;
				if (x == true) {
					document.getElementById("submtbtn").disabled = true;
					return true;
				}
			}

			return false;

		});
	});

	
	$('.maxlength-options').maxlength({
		alwaysShow : true,
		threshold : 10,
		warningClass : 'text-success form-text',
		limitReachedClass : 'text-danger form-text',
		separator : ' of ',
		preText : 'You have ',
		postText : ' chars remaining.',
		validate : true
	});

	$('.maxlength-badge-position').maxlength({
		alwaysShow : true,
		placement : 'top'
	});
	

	$('#surcharge').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		})
		
	$('#packing').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#handling').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#extra').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#round_off').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
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
										var frIds = [];
										$(".chkcls:checkbox:checked").each(function() {
											frIds.push($(this).val());
										});
										
										alert(frIds)
																
								$
								.getJSON(
										'${deleteSelMultiFranchises}',
										{
											frIds : JSON.stringify(frIds),
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
	
	
	///////////////////////////////////////////
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
								<input type="hidden" value="${compId}" id="compId">
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
					for (var i = 0; i < thArray.length; i++) {
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
								'${getFranchisePrintIds}',
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
											var showHead = 0;
											if($("#chkPdf").is(":checked")){
												showHead = 1;
											}else{
												showHead = 0;
											}
											 window.open('${pageContext.request.contextPath}/pdfReport?url=pdf/getFranchiseIdsListPdf/'+compId+'/'+elemntIds.join()+'/'+showHead);
										}
									}
								});
						}
					}		
				</script>
</body>
</html>