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
	<c:url value="/getConfigByCatId" var="getConfigByCatId"></c:url>
	<c:url value="/configFranchiseAJAX" var="configFranchiseAJAX"></c:url>

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
										List</a></span><!-- showOfferConfigurationList -->
							</div>
							<!-- <div class="form-group row"></div> -->
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

							<div class="card-body">														
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>
								<form
									action="${pageContext.request.contextPath}/configFranchise"
									id="configFranchise">


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-1"
											for="catId">Category <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-3">
											<select class="form-control select-search" data-fouc
												name="catId" id="catId"
												onchange="getConfiguration(this.value)">
												<option value="0">Select</option>
												<c:forEach items="${catList}" var="catList"
													varStatus="count">
													<c:choose>
														<c:when test="${catList.catId==catId}">
															<option selected value="${catList.catId}">${catList.catName}</option>
														</c:when>
														<c:otherwise>
															<option value="${catList.catId}">${catList.catName}</option>
														</c:otherwise>
													</c:choose>

												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_category" style="display: none;">This field
												is required.</span>
										</div>

										<div class="col-lg-1">
										</div>
										

										<label class="col-form-label font-weight-bold col-lg-2"
											for="configId">Configuration<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-2">
									<select class="form-control select-search" data-fouc
												name="configId" id="configId">

											</select> <span class="validation-invalid-label text-danger"
												id="error_configId" style="display: none;">This field
												is required.</span>
										</div>
										
										<div class="col-lg-1">
										</div>
										
										<div class="">								
										<button onclick="SearchBtn()" type="button" id="search_button" class="btn btn-primary">
												Search <i class="icon-paperplane ml-2"></i>
											</button>
									</div>
										
									</div>									
									
									
								</form>
								</div>
								
								<div class="card-body">
								<form
									action="${pageContext.request.contextPath}/saveFrConfiguration"
									id="submitInsert" method="post">
									
									
									<input type="hidden" name="cfgId" id="cfgId" value="${configId}">
									<!--Table-->
									<table class="table ddatatable-header-basic" id="printtable1">
										<thead>
											<tr>
												<th>Sel All<input type="checkbox" name="selAll" id="selAll"/></th>
												<th>Sr.No.</th>
												<th>Franchise Name</th>
												<th>Franchise Code</th>
												<th>City</th>
												<th>Route</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${frList}" var="frList" varStatus="count">

												<%-- <tr>
													<td><input type="checkbox" id="frId${frList.frId}"
														value="${frList.frId}" name="frId" class="select_all"></td>
													<td>${count.index+1})</td>
													<td>${frList.frName}</td>
													<td>${frList.frCode}</td>
													<td>${frList.frCity}</td>
													<td>${frList.route}</td>
													
													
											 
												</tr> --%>
											</c:forEach>
										</tbody>
									</table>
									<br>
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="displayRate">Display Rate<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="displayRate" id="displayRate"
												data-placholder="Select ">
												<option value=""></option>
												<option value="1">Rate Amt</option>
												<option value="2">MRP Amt</option>
												<option value="3">Special Rate Amt-1</option>
												<option value="4">Special Rate Amt-2</option>
												<option value="5">Special Rate Amt-3</option>
												<option value="6">Special Rate Amt-4</option>


											</select> <span class="validation-invalid-label text-danger"
												id="error_displayRate" style="display: none;">This field is
												required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="actualRate">Actual Rate<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="actualRate" id="actualRate" data-placholder="Select ">
												<option value=""></option>
												<option value="1">Rate Amt</option>
												<option value="2">MRP Amt</option>
												<option value="3">Special Rate Amt-1</option>
												<option value="4">Special Rate Amt-2</option>
												<option value="5">Special Rate Amt-3</option>
												<option value="6">Special Rate Amt-4</option>


											</select> <span class="validation-invalid-label text-danger"
												id="error_actualRate" style="display: none;">This field is
												required.</span>
										</div>
									</div>
									<input type="hidden" id="btnType" name="btnType">
									
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
									<div class="text-center">
										<br>
										<!-- <button type="submit" class="btn btn-primary" id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
										</button> -->
										<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>								
									
										<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn(1)">
											Save & Next<i class="icon-paperplane ml-2"></i>
										</button>
									
									</div>
								</form>
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
	function SearchBtn() {
		//alert("Asdf");		 
		var catId=document.getElementById("catId").value;
		var configId=document.getElementById("configId").value;
	//	alert(catId+"Asdf"+configId);
		$.getJSON(
				'${configFranchiseAJAX}',
				{
					catId : catId,
					configId : configId,
					ajax : 'true',
				},

				function(data) {
					$('#printtable1 td').remove();
					//alert("Data " + JSON.stringify(data));
					$.each(data,function(key, config) {
						var tr = $('<tr></tr>');
						var catCheckbox="<input type=checkbox id='"+config.frId+"' value='"+config.frId+"' name=frId class=select_all >"
						tr.append($('<td></td>').html(catCheckbox));
						tr.append($('<td></td>').html(key+1));
						tr.append($('<td></td>').html(config.frName));
						tr.append($('<td></td>').html(config.frCode));
						tr.append($('<td></td>').html(config.frCity));
						tr.append($('<td></td>').html(config.route));
						
						$('#printtable1 tbody').append(tr);
					
					});
					
					$("#cfgId").val(configId);
				});
	}
	</script>
	
	

	<script>
	function pressBtn(btnVal){
		$("#btnType").val(btnVal)
	}
		$(document).ready(

				function() {

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);

							});
				});
		
		$("#configId").change(function(){
			if ($("#configId").val() == 0 || !$("#configId").val()) {
			}else{
			 document
				.getElementById("search_button").click();
			}
			});
	</Script>
	
		<script type="text/javascript">
		
		$(document).ready(function($) {
			$("#configFranchise").submit(function(e) {
				var isError = false;
				var errMsg = "";
				
				
				if ($("#catId").val() == 0 || !$("#catId").val()) {
					isError = true;
					$("#error_category").show()
					$("#catId").focus();
				} else {
					$("#error_category").hide()
				}
				
				if ($("#configId").val() == 0 || !$("#configId").val()) {
					isError = true;
					$("#error_configId").show()
					$("#configId").focus();
				} else {
					$("#error_configId").hide()
				}				 
			
				if (!isError) {
					var x = true;
					if (x == true) {
						//document
								//.getElementById("subBtn").disabled = true;
						return true;
					}
				}

				return false;

			});
		});
		
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				
				var checked = $("#submitInsert input:checked").length > 0;

				var count = $('#printtable1 tr').length;
				//alert(checked);
				if (!checked || count <= 1) {
					$("#error_chks").show()
					isError = true;
				} else {
					$("#error_chks").hide()
					isError = false;
				}
				
				if ($("#catId").val() == 0 || !$("#catId").val()) {
					isError = true;
					$("#error_category").show()
				} else {
					$("#error_category").hide()
				}
				if ($("#configId").val() == 0 || !$("#configId").val()) {
					isError = true;
					$("#error_configId").show()
				} else {
					$("#error_configId").hide()
				}
				 
				if (!$("#displayRate").val()) {
					isError = true;
					$("#error_displayRate").show()
				} else {
					$("#error_displayRate").hide()
				}
				
				if (!$("#actualRate").val()) {
					isError = true;
					$("#error_actualRate").show()
				} else {
					$("#error_actualRate").hide()
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
									var x = ${configId};
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



</body>
</html>