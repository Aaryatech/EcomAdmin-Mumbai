<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>

<c:url var="getConfiguredFilters" value="/getConfiguredFilters" />
</head>

<body>

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
			<%-- 	<div class="page-header page-header-light">


				<div
					class="breadcrumb-line breadcrumb-line-light header-elements-md-inline">
					<div class="d-flex">
						<div class="breadcrumb">
							<a href="index.html" class="breadcrumb-item"><i
								class="icon-home2 mr-2"></i> Home</a> <span
								class="breadcrumb-item active">Dashboard</span>
						</div>

						<a href="#" class="header-elements-toggle text-default d-md-none"><i
							class="icon-more"></i></a>
					</div>

					<div class="breadcrumb justify-content-center">
						<a href="${pageContext.request.contextPath}/showEmpTypeList"
							class="breadcrumb-elements-item"> Employee Type List</a>

					</div>


				</div>
			</div> --%>
			<!-- /page header -->


			<!-- Content area -->
			<div class="content">

				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">
						<!-- Title -->
						<!-- <div class="mb-3">
							<h6 class="mb-0 font-weight-semibold">Hidden labels</h6>
							<span class="text-muted d-block">Inputs with empty values</span>
						</div> -->
						<!-- /title -->


						<div class="card">							
							<div
								class="card-header bg-blue text-white d-flex justify-content-between">
								<span
									class="font-size-sm text-uppercase font-weight-semibold card-title">Configure Category And Filters</span>
							
							</div>

							<div class="card-body">
								<%
									if (session.getAttribute("errorMsg") != null) {
								%>
								<div
									class="alert bg-danger text-white alert-styled-left alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">
										<span>×</span>
									</button>
									<span class="font-weight-semibold"></span>
									<%
										out.println(session.getAttribute("errorMsg"));
									%>
								</div>

								<%
									session.removeAttribute("errorMsg");
									}
								%>
								<%
									if (session.getAttribute("successMsg") != null) {
								%>
								<div
									class="alert bg-success text-white alert-styled-left alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">
										<span>×</span>
									</button>
									<span class="font-weight-semibold"></span>
									<%
										out.println(session.getAttribute("successMsg"));
									%>
								</div>
								<%
									session.removeAttribute("successMsg");
									}
								%>

								<form
									action="${pageContext.request.contextPath}/addCateAndFiltersConfig"
									id="submitInsertEmpType" method="post">



									<input type="hidden" id="filterConfigId" name="filterConfigId"
										value="${config.cateFilterConfigId}">

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="cat_id">
											Select Category <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data placeholder="Select " onchange="getFilters(this.value)"
												id="cat_id" name="cat_id" >

												<option selected disabled value="">Select</option>
												<c:forEach items="${cateList}" var="cateList"
													varStatus="count">
														<option value="${cateList.catId}">${cateList.catName}</option>
													<%-- <c:choose>
														<c:when test="${productList.productId==prodId}">
															<option selected="selected"
																value="${productList.productId}">${productList.productName}</option>
														</c:when>
														<c:otherwise>
															<option value="${productList.productId}">${productList.productName}</option>
														</c:otherwise>
													</c:choose>--%>
												</c:forEach> 
											</select> <span class="validation-invalid-label" id="error_CateId"
												style="display: none;">This field is required.</span>
										</div>
										<div id="loader1" style="display: none;">
											<img
												src='${pageContext.request.contextPath}/resources/assets/images/giphy.gif'
												width="100px" height="100px"
												style="display: block; margin-left: auto; margin-right: auto">
										</div>
										
									
									</div>
									
							 
										 

									<div class="col-lg-12" align="center">
										<table class="table datatable-scroller table-border"
											id="printtable1">
											<thead>
												<tr class="bg-blue">
													<th>Sr. No.</th>
													<th>Filters</th>

												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>

									<div class="form-group row">
										<div class="col-lg-10">
											<span class="validation-invalid-label" id="error_checkbox"
												style="display: none;">Check Minimum One Checkbox</span>
										</div>
									</div>
									<br>

									<div class="col-md-12" style="text-align: center;">

										<button type="submit" class="btn bg-blue ml-3 legitRipple"
											id="submtbtn">
											Submit <i class="icon-paperplane ml-2"> </i>
										</button><%-- <a href="${pageContext.request.contextPath}/showRelProConfgList"><button
												type="button" class="btn btn-primary">Cancel</button></a> --%>
										

									</div>
								</form>
							</div>
						</div>


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
	
	<script>
	
		function getFilters(cateId){
			//alert(cateId);
			
			//$("#loader1").show();
			if(cateId>0){
			$('#printtable1 td').remove();
			
 		 	$
					.getJSON(
							'${getConfiguredFilters}',
							{
								cateId : cateId,
								ajax : 'true',

							},
							function(data) {
								
								var filterIds = 0;
								
								if(data.catFilterConfig!=null){
									filterIds = data.catFilterConfig.filterIds.split(',');
								}
								
								if(data.filterList.length>0){
									
									for (i = 0; i < data.filterList.length; i++) {										 
										var flag = 0;
										var tr1 = $('<tr></tr>');		
										
										for (j = 0; j < filterIds.length; j++) {											
											
												if(filterIds[j]==data.filterList[i].filterTypeId){
													flag=1;
												}						
										} 
										
										if(flag==1){
											tr1
											.append($(
													'<td style="border-top: 1px solid #ddd;""></td>')
													.html(
															'<input type="checkbox" checked name="chk" id="chk" value="'+
															data.filterList[i].filterTypeId
															+'" class="chkcls'+data.filterList[i].filterTypeId+'">'));
										}else{
											tr1
											.append($(
													'<td style="border-top: 1px solid #ddd;""></td>')
													.html(
															'<input type="checkbox" name="chk" id="chk" value="'+
															data.filterList[i].filterTypeId
															+'" class="chkcls'+data.filterList[i].filterTypeId+'">'));
										}
										
										
										tr1
										.append($(
												'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;""></td>')
												.html(
														data.filterList[i].filterTypeName));
										
										$(
										'#printtable1 tbody')
										.append(tr1);
									} 
								}else{
								//	 $("#loader1").hide();
								}
								 

							});  
		}
 			
}

</script>
	<script>
		$('.datatable-fixed-left_custom').DataTable({
			columnDefs : [ {
				orderable : false,
				targets : [ 1 ]
			} ],
			scrollX : true,
			scrollY : '50vh',
			scrollCollapse : true,
			paging : false,
			fixedColumns : {
				leftColumns : 1,
				rightColumns : 0
			}

		});
		function checkSubmodule(catId) {
			
			//alert(catId);
			
			

			$
					.getJSON(
							'${getProductListByCat}',
							{
								catId : catId,
								ajax : 'true',

							},
							function(data) {
								//alert( JSON.stringify(data));
								if (document
										.getElementById("header"+catId).checked == true) {

									for (var i = 0; i < data.length; i++) {
										
										//alert(data[i].productId+"view"+catId);

										document.getElementById(data[i]
												+ "view"+catId).checked = true;
										 document.getElementById(data[i]+"view"+catId).value=1;

										
									}

								} else {
									for (var i = 0; i < data.length; i++) {

										document.getElementById(data[i]
												+ "view"+catId).checked = false;
										 document.getElementById(data[i]+"view"+catId).value=0;

										 
									}
								}

							});

		}
	</script>

	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			//checkSame();
			return;
		}

		 $(document).ready(function($) {

			$("#submitInsertEmpType").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#cat_id").val()) {

					isError = true;

					$("#error_CateId").show()
					//return false;
				} else {
					$("#error_CateId").hide()
				}

				var checkboxes = $("input[type='checkbox']");

				if (!checkboxes.is(":checked")) {

					isError = true;

					$("#error_checkbox").show()

				} else {
					$("#error_checkbox").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

						document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//end ajax send this to php page
				}
				return false;
			});
		});
		//
	</script>


</body>
</html>