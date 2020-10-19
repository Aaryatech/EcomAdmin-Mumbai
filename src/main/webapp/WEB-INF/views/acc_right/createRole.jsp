<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<c:url var="getSubmoduleList" value="/getSubmoduleList" />
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
							<div class="card-header header-elements-inline">
								<h6 class="card-title">Create Access Role</h6>
								<!-- <div class="header-elements">
									<div class="list-icons">
										<a class="list-icons-item" data-action="collapse"></a>
									</div>
								</div> -->
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
									action="${pageContext.request.contextPath}/submitCreateRole"
									id="submitInsertEmpType" method="post">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="empTypeName">Role Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-6">
											<input type="text" class="form-control maxlength-badge-position" autofocus="autofocus"
												placeholder="Role Name" id="empTypeName" maxlength="70"
												name="roleName" autocomplete="off" onchange="trim(this)">
											<span class="validation-invalid-label"  id="error_empTypeName"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<!-- <div class="form-group row">
										<label class="col-form-label col-lg-2" for="empShortName">Employee
											Type Short Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Employee Type Short Name" id="empShortName"
												name="empShortName" autocomplete="off" onchange="trim(this)"
												maxlength="5"> <span
												class="validation-invalid-label" id="error_empShortName"
												style="display: none;">This field is required.</span>
												<span
												class="validation-invalid-label" id="error_sameName"
												style="display: none;">Employee Type Short Name Can Not be same as Employee Type Name.</span>
												
												
												
										</div>
									</div> -->

									<!-- <div class="form-group row">
										<label class="col-form-label col-lg-2" for="comoffallowed">Comp
											Off Request Allowed <span style="color: red">* </span>:
										</label>
										<div class="form-check form-check-inline">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="comoffallowed"
												id="comoffallowed" checked value="1"> Yes
											</label>
										</div>
										<div class="form-check form-check-inline">
											<label class="form-check-label"> <input type="radio"
												class="form-check-input" name="comoffallowed"
												id="comoffallowed" value="0"> No
											</label>
										</div>
									</div> -->
									<input type="hidden"   id="comoffallowed"
										name="comoffallowed" value="1">
									<!-- <div class="form-group row">
										<label class="col-form-label col-lg-2" for="remark">Remark
											: </label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Any Remark" onchange="trim(this)" id="remark"
												name="remark"></textarea>

										</div>
									</div> -->
<div class="col-lg-12" align="center">
										<table
											class="table datatable-scroller table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
											id="printtable1">
											<thead>
												<tr class="bg-blue">
													<th>Sr. No.</th>
													<th>Module Name</th>
													<th>View</th>
													<th>Add</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${moduleList}" var="moduleList"
													varStatus="count">


													<c:choose>
														<c:when
															test="${empty moduleList.accessRightSubModuleList}"></c:when>
														<c:otherwise>
															<tr>

																<td>${count.index+1}&nbsp;&nbsp;<input
																	type="checkbox" id="header${moduleList.moduleId}"
																	name="header${moduleList.moduleId}" class="select_all"
																	onclick="checkSubmodule(${moduleList.moduleId})"
																	value="0"></td>
																<td colspan="5">${moduleList.iconDiv}&nbsp;<b>${moduleList.moduleName}</b></td>


															</tr>

															<c:forEach items="${moduleList.accessRightSubModuleList}"
																var="subModuleList">
																<tr>

																	<td></td>
																	<td><input type="checkbox"
																		onclick="row_level(1,${subModuleList.subModuleId},${subModuleList.moduleId})">
																		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${subModuleList.subModulName}</td>
																	<td style="text-align: center;"><input
																		type="checkbox"
																		id="${subModuleList.subModuleId}view${subModuleList.moduleId}"
																		class="check${subModuleList.moduleId}"
																		name="${subModuleList.subModuleId}view${subModuleList.moduleId}"
																		value="0"
																		onclick="changeValue(1,${subModuleList.subModuleId},${subModuleList.moduleId})"></td>
																	<td style="text-align: center;"><input
																		type="checkbox"
																		id="${subModuleList.subModuleId}add${subModuleList.moduleId}"
																		class="check${allModuleList.moduleId}"
																		name="${subModuleList.subModuleId}add${subModuleList.moduleId}"
																		value="0"
																		onclick="changeValue(2,${subModuleList.subModuleId},${subModuleList.moduleId})"></td>
																	<td><input type="checkbox"
																		class="check${allModuleList.moduleId}"
																		id="${subModuleList.subModuleId}edit${subModuleList.moduleId}"
																		name="${subModuleList.subModuleId}edit${subModuleList.moduleId}"
																		value="0"
																		onclick="changeValue(3,${subModuleList.subModuleId},${subModuleList.moduleId})"></td>
																	<td><input type="checkbox"
																		class="check${allModuleList.moduleId}"
																		id="${subModuleList.subModuleId}delete${subModuleList.moduleId}"
																		name="${subModuleList.subModuleId}delete${subModuleList.moduleId}"
																		value="0"
																		onclick="changeValue(4,${subModuleList.subModuleId},${subModuleList.moduleId})"></td>
																</tr>

															</c:forEach>
														</c:otherwise>
													</c:choose>
												</c:forEach>

											</tbody>
										</table>
									</div>
									<span class="form-text text-muted">* If Want To Access
										Add, Edit,Delete Then View Access is Compulsory</span>
									<div class="form-group row">
										<div class="col-lg-10">
											<span class="validation-invalid-label" id="error_checkbox"
												style="display: none;">Check Minimum One Checkbox</span>
										</div>
									</div>
									<br>

									<div class="col-md-12" style="text-align: center;">

										<!-- <input type="submit" class="btn bg-blue ml-1 legitRipple"
											id="submtbtnsave" name="saveBtn" value="Save"/> -->
											
									
										<button type="submit" class="btn bg-blue ml-3 legitRipple"
											id="submtbtn" name="saveNext" value="savenxt">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										<a href="${pageContext.request.contextPath}/showRoleList"><button
												type="button" class="btn btn-primary">Cancel</button></a>

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
			function checkSubmodule(moduleId) {
				
				$.getJSON('${getSubmoduleList}', {
					moduleId : moduleId,
					ajax : 'true',

				}, function(data) { 
					 
					
					if(document.getElementById("header"+moduleId).checked == true){
						
						for(var i=0 ; i<data.length; i++){
							 
							document.getElementById(data[i]+"view"+moduleId).checked=true;
							 document.getElementById(data[i]+"add"+moduleId).checked=true;
							 document.getElementById(data[i]+"edit"+moduleId).checked=true;
							 document.getElementById(data[i]+"delete"+moduleId).checked=true;
							 document.getElementById(data[i]+"view"+moduleId).value=1;
							 document.getElementById(data[i]+"add"+moduleId).value=1;
							 document.getElementById(data[i]+"edit"+moduleId).value=1;
							 document.getElementById(data[i]+"delete"+moduleId).value=1;
						}
						 
					 }else{
						 for(var i=0 ; i<data.length; i++){
								
								document.getElementById(data[i]+"view"+moduleId).checked=false;
								 document.getElementById(data[i]+"add"+moduleId).checked=false;
								 document.getElementById(data[i]+"edit"+moduleId).checked=false;
								 document.getElementById(data[i]+"delete"+moduleId).checked=false;
								 document.getElementById(data[i]+"view"+moduleId).value=0;
								 document.getElementById(data[i]+"add"+moduleId).value=0;
								 document.getElementById(data[i]+"edit"+moduleId).value=0;
								 document.getElementById(data[i]+"delete"+moduleId).value=0;
							}
					 }
				
				});
 
				 
			}
			
			function changeValue(type,subModuleId,moduleId) {
				 
				 
							 if(type==1){
								 if(document.getElementById(subModuleId+"view"+moduleId).checked == true){
									 
									 document.getElementById(subModuleId+"view"+moduleId).value=1;
									 
								 }else{
									 
									 document.getElementById(subModuleId+"view"+moduleId).value=0;
								 }
								
							 }else if(type==2){
								 if(document.getElementById(subModuleId+"add"+moduleId).checked == true){
									 
								 	document.getElementById(subModuleId+"add"+moduleId).value=1;
								 }else{
									 document.getElementById(subModuleId+"add"+moduleId).value=0;
								 }
							 }else if(type==3){
								 if(document.getElementById(subModuleId+"edit"+moduleId).checked == true){
									 
									 document.getElementById(subModuleId+"edit"+moduleId).value=1;
									 
								 }else{
									 
									 document.getElementById(subModuleId+"edit"+moduleId).value=0;
									 
								 }
								 
							 }else if(type==4){
								 
								 if(document.getElementById(subModuleId+"delete"+moduleId).checked == true){
									 
									 document.getElementById(subModuleId+"delete"+moduleId).value=1;
									 
								 }else{
									 
									 document.getElementById(subModuleId+"delete"+moduleId).value=0;
									 
								 }
								 
							 }
							  
			}
			
			function row_level(type1,subModuleId,moduleId){
					 if(document.getElementById(subModuleId+"view"+moduleId).checked == true){
						 document.getElementById(subModuleId+"view"+moduleId).checked = false;

						 document.getElementById(subModuleId+"view"+moduleId).value=1;
						 
					 }else{
						 document.getElementById(subModuleId+"view"+moduleId).checked = true;
						 document.getElementById(subModuleId+"view"+moduleId).value=0;

					 }
					
					 if(document.getElementById(subModuleId+"add"+moduleId).checked == true){
					 	document.getElementById(subModuleId+"add"+moduleId).value=1;
					 	document.getElementById(subModuleId+"add"+moduleId).checked = false;
					 }else{
						 document.getElementById(subModuleId+"add"+moduleId).value=0;
						 	document.getElementById(subModuleId+"add"+moduleId).checked = true;
					 }
					 if(document.getElementById(subModuleId+"edit"+moduleId).checked == true){
						 document.getElementById(subModuleId+"edit"+moduleId).value=1;
						 document.getElementById(subModuleId+"edit"+moduleId).checked = false;
					 }else{
						 
						 document.getElementById(subModuleId+"edit"+moduleId).value=0;
						 document.getElementById(subModuleId+"edit"+moduleId).checked = true;
					 }
					 
					 
					 if(document.getElementById(subModuleId+"delete"+moduleId).checked == true){
						 
						 document.getElementById(subModuleId+"delete"+moduleId).value=1;
						 document.getElementById(subModuleId+"delete"+moduleId).checked = false;
						 
					 }else{
						 
						 document.getElementById(subModuleId+"delete"+moduleId).value=0;
						 document.getElementById(subModuleId+"delete"+moduleId).checked = true;

				 } 
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

				if (!$("#empTypeName").val()) {

					isError = true;

					$("#error_empTypeName").show()
					//return false;
				} else {
					$("#error_empTypeName").hide()
				}

				
				var checkboxes = $("input[type='checkbox']");
				
				if (!checkboxes.is(":checked")) {

					isError = true;

					$("#error_checkbox").show()

				} else {
					$("#error_checkbox").hide()
				}
				

				if (!isError) {
		
					
					bootbox.confirm({
		                title: 'Create Role',
		                message: 'Are you sure you want to submit this form!.',
		                buttons: {
		                    confirm: {
		                        label: 'Yes',
		                        className: 'btn-primary'
		                    },
		                    cancel: {
		                        label: 'Cancel',
		                        className: 'btn-link'
		                    }
		                },
		                callback: function (result) {
		                   if(result){		                	   
		                	  // document.getElementById("submtbtn").disabled = true;
		                	  // document.getElementById("submtbtnsave").disabled = true;
		                	   document.getElementById("submitInsertEmpType").submit();
		                   }
		                }
		            });
					e.preventDefault();

					/* var x = true;
					if (x == true) {

						document.getElementById("submtbtn").disabled = true;
						return true;
					} */
					//end ajax send this to php page
				}
				return false;
			});
		});
		//
	</script>
	<script>

$('.maxlength-options').maxlength({
    alwaysShow: true,
    threshold: 10,
    warningClass: 'text-success form-text',
    limitReachedClass: 'text-danger form-text',
    separator: ' of ',
    preText: 'You have ',
    postText: ' chars remaining.',
    validate: true
});

$('.maxlength-badge-position').maxlength({
    alwaysShow: true,
    placement: 'top'
});
</script>
<!-- <script type="text/javascript">

function checkSame(){
	//alert("Hii..");
	x=document.getElementById("empTypeName").value;
	y=document.getElementById("empShortName").value;
	//alert(x);
	//alert(y);
	
	if(x!== '' && y!== ''){
	if(x==y){
		  
			$("#error_sameName").show()
			document.getElementById("empShortName").value="";

		} else {

			$("#error_sameName").hide()
		}
		
		
	}
}


</script> -->
	<!-- <script type="text/javascript">
	$('#submtbtn').on('click', function() {
        swalInit({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            buttonsStyling: false
        }).then(function(result) {
            if(result.value) {
                swalInit(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                );
            }
            else if(result.dismiss === swal.DismissReason.cancel) {
                swalInit(
                    'Cancelled',
                    'Your imaginary file is safe :)',
                    'error'
                );
            }
        });
    });
	
	</script> -->

</body>
</html>