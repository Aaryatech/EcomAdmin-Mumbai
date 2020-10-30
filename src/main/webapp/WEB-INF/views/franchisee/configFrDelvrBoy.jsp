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

.select2-selection--multiple .select2-selection__rendered {
	border-bottom: 1px solid #ddd;
}
.table caption+thead tr:first-child td, .table caption+thead tr:first-child th, .table colgroup+thead tr:first-child td, .table colgroup+thead tr:first-child th, .table thead:first-child tr:first-child td, .table thead:first-child tr:first-child th {
      border-top-width: 1px!important;  
}
</style>
<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">
	<c:url value="/getFranchiseListFrDlvrBoyConfig"
		var="getFranchiseListFrDlvrBoyConfig"></c:url>

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
									href="${pageContext.request.contextPath}/configFranchiseList"
									style="color: white;"><i class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View
										List</a></span>
								<!-- showOfferConfigurationList -->
							</div>
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

							<form
								action="${pageContext.request.contextPath}/saveFrDelvrBoyConfig"
								id="submitInsert" method="post">

								<div class="card-body">
									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" value="${config.delBoyAssignId}"
										name="assignId">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="delBoyId">Delivery Boy <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="delBoyId" id="delBoyId">
												<option value="0">Select</option>
												<c:forEach items="${delvrBoyList}" var="list"
													varStatus="count">

													<%-- <c:set value="0" var="flag"></c:set>
													<c:forEach items="${asignDelBoyId}" var="list">
													
													
													
													</c:forEach>
													 <c:choose>
														<c:when test="${catList.catId==catId}">
															<option value="${list.delBoyId}">${list.firstName} ${list.lastName}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.delBoyId}">${list.firstName} ${list.lastName}</option>
														</c:otherwise>
													</c:choose>  --%>
													<option value="${list.delBoyId}">${list.firstName}
														${list.lastName}</option>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_delBoyId" style="display: none;">This field
												is required.</span>
										</div>



										<label class="col-form-label font-weight-bold col-lg-2"
											for="configId">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													id="active_y" class="form-check-input" checked value="1"
													name="activeStat"> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="activeStat"
													id="active_n"> In-Active
												</label>
											</div>
											<%-- <c:choose>
											<c:when test="${delvrBoy.delBoyId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="activeStat"
													id="active_y" ${delvrBoy.isActive==1 ? 'checked' : ''}>
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="activeStat" id="active_n"
													${delvrBoy.isActive==0 ? 'checked' : ''}> In-Active
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio" id="active_y"
													class="form-check-input" checked value="1" name="activeStat">
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="activeStat" id="active_n"> In-Active
												</label>
											</div>
											</c:otherwise>
										</c:choose> --%>
										</div>
									</div>

									<div class="text-center">
										<button type="button" id="search_button"
											class="btn btn-primary">
											Search <i class="icon-paperplane ml-2"></i>
										</button>
									</div>

								</div>

								<div class="card-body">
									<!--Table-->
									<table class="table datatable-fixed-left_custom table-bordered table-hover table-striped"
									 id="printtable1" width="100%">
										<thead>
											<tr>
												<th>Sel All<input type="checkbox" name="seleAll"
													id="seleAll1"  onclick="selAllItems()" /></th>
												<th>Sr.No.</th>
												<th>Franchise Name</th>
												<th>Franchise Code</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
									<input type="hidden" id="btnType" name="btnType"> <span
										class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
									<div class="text-center">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn"
											onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>

										<button type="submit" class="btn btn-primary" id="submtbtn1"
											onclick="pressBtn(1)">
											Save & Next<i class="icon-paperplane ml-2"></i>
										</button>

									</div>

								</div>
							</form>
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
		
/* $(document).ready(
		function() {
			$("#seleAll").click(
					
					function() {
						$('#printtable1 tr input[type="checkbox"]')
								.prop('checked', this.checked);

					});
		}); */
		
		function selAllItems() {
			
			var checkBox = document.getElementById("seleAll1");
	//		alert(checkBox.checked);

			if (checkBox.checked == true) {

				$(".chk")
						.each(
								function(counter) {

									document.getElementsByClassName("chkcls")[counter].checked = true;

								});

			} else {

				$(".chk")
						.each(
								function(counter) {

									if (document
											.getElementsByClassName("chkVal")[counter].value == 0) {
										document.getElementsByClassName("chkcls")[counter].checked = false;

									}
								});

			}

		}

</script>
	<script>
		$("#search_button")
				.click(
						function() {
							var delBoyId = $("#delBoyId").val();
							//$('#loader').show();
							$('#printtable1 td').remove();
							var dataTable = $('#printtable1').DataTable();
							dataTable.clear().draw();
							$
									.getJSON(
											'${getFranchiseListFrDlvrBoyConfig}',
											{
												delBoyId : delBoyId,
												ajax : 'true'
											},
											function(data) {
												/* alert(JSON
														.stringify(data.frStrIds)); */

												if (data.frStrIds != null) {
													
													var frIds = data.frStrIds
															.split(',');
													var tempFrList = [];

													if (frIds.length > 0) {
														for (var j = 0; j < frIds.length; j++) {
															var found = 0;

															for (var i = 0; i < data.frList.length; i++) {
																
																if (frIds[j] == data.frList[i].frId) {
																	data.frList[i].tempVar=1;

																}
															}
														}

														
															var count = 1;
														for (var i = 0; i < data.frList.length; i++) {
															if(data.frList[i].tempVar==0){
																var acStr = '<input type="checkbox"  name="chk" id="chk'+data.frList[i].frId+'" value="'+
																data.frList[i].frId
												+'" class="chkcls">'

																dataTable.row
																		.add(
																				[
																						acStr,
																						count,
																						data.frList[i].frName,
																						data.frList[i].frCode])
																		.draw();
																count++;
															}
														
														}
													}

												} else {
													
													for (var i = 0; i < data.frList.length; i++) {
														franchise = data.frList[i];

														var acStr = '<input type="checkbox"  name="chk" id="chk'+data.frList[i].frId+'" value="'+
												franchise.frId
												+'" class="chkcls">'

														dataTable.row
																.add(
																		[
																				acStr,
																				i + 1,
																				franchise.frName,
																				franchise.frCode ])
																.draw();
													}
												}
												
											});
						});

		function pressBtn(btnVal) {
			$("#btnType").val(btnVal)
		}
		
	

					
				

		$("#configId").change(function() {
			if ($("#configId").val() == 0 || !$("#configId").val()) {
			} else {
				document.getElementById("search_button").click();
			}
		});
	</Script>

	<script type="text/javascript">
		 	$(document).ready(function($) {

				$("#submitInsert").submit(function(e) {
					var isError = false;
					var errMsg = "";
					
					
					if ($("#delBoyId").val()==0) {
						isError = true;
						$("#error_delBoyId").show()
					} else {
						$("#error_delBoyId").hide()
					}
					
					var checkboxes = $("input[type='checkbox']");

					if (!checkboxes.is(":checked")) {

						isError = true;

						$("#error_chks").show()

					} else {
						$("#error_chks").hide()
					}
					
					
					if (!isError) {
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
											$(".btn").attr("disabled", true);
											var form = document
													.getElementById("submitInsert")
											form
													.submit();
										}
									}
								});
						//end ajax send this to php page
						return false;
					}//end of if !isError

					return false;

				});
			}); 
	</script>
	<script type="text/javascript">
	$('.datatable-fixed-left_custom').DataTable({

		columnDefs : [ {
			orderable : false,
			targets : [ 0 ]
		} ],
		//scrollX : true,
		scrollX : true,
		scrollY : '65vh',
		scrollCollapse : true,
		order:[],
		paging : false,
		fixedColumns : {
			leftColumns : 1,
			rightColumns : 0
		}

	});
	</script>

</body>
</html>