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
			<%-- <div class="page-header page-header-light">


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
								<h6 class="card-title">Assign Role to Employees</h6>
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
									<span class="font-weight-semibold"> </span>
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
									action="${pageContext.request.contextPath}/submitAssignedRole"
									id="assignTask" method="post">

									<div class="form-group row">
									
										<label class="col-form-label col-lg-2" for="roleId">Select
											Access Role <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<select name="roleId" data-placeholder="Select Role"
												id="roleId" class="form-control form-control-sm select"
												aria-hidden="true" data-container-css-class="select-sm"
												data-fouc>
												<option value="">Select Role</option>
												<c:forEach items="${createdRoleList}" var="createdRoleList">
													<option value="${createdRoleList.roleId}">${createdRoleList.roleName}</option>
												</c:forEach>

											</select> <span class="validation-invalid-label" id="error_roleId"
												style="display: none;">select access role.</span>
										</div>
										
										<div class="col-lg-4" style="text-align: center;">
										<input type="hidden" id="roleId" name="roleId" value="0">
										<button type="submit" class="btn bg-blue ml-3 legitRipple"
											id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
										</button>
										<a href="${pageContext.request.contextPath}/showCreateRole"><button
												type="button" class="btn btn-primary">Cancel</button></a>

									</div>

									</div>

									<!-- <div class="form-group row">
										<label class="col-form-label col-lg-2" for="roleName">Role
											to be Assigned <span style="color: red">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control"
												placeholder="Role Name" readonly id="roleName"
												maxlength="15" name="roleName" autocomplete="off"
												onchange="trim(this)"> <span
												class="validation-invalid-label" id="error_empTypeName"
												style="display: none;">This field is required.</span>
										</div>
									</div> -->


									<span class="validation-invalid-label" id="error_table2"
										style="display: none;">Please select some employees</span>
	<div class="col-lg-12" align="center">
									<table
										class="table table-bordered table-hover datatable-highlight1 datatable-button-html5-basic  datatable-button-print-columns1"
										id="printtable1">
										<thead>
											<tr class="bg-blue">
												<th width="7%">Sr. No.</th>
												<th>Employee Name</th>
												<th>Employee Type</th>
												<th width="10%">Current Role</th>

											</tr>
										</thead>
										<tbody>


											<c:forEach items="${empList}" var="empList" varStatus="count">
												<tr>
													<td style="text-align: center;"><c:out
															value="${count.index+1}" /></td>
													<td><input type="checkbox"
														name="empIds" id="empIds${empList.empId}" class="chk" value="${empList.empId}"> &nbsp;<c:out value="${empList.empName}" />&nbsp;</td>
																					<td>${empList.empType==1 ? 'Admin': empList.empType==2 ? 'Partner' : empList.empType==3 ? 'Manager' : empList.empType==4 ? 'Team Leader' : empList.empType==5 ? 'Employee' : ''}</td> 
													
													<td><c:out value="${empList.exVar2}" /></td>
												</tr>


											</c:forEach>


										</tbody>
									</table>
									</div>
									<span class="validation-invalid-label" id="error_table1"
										style="display: none;">Please select some employees</span>
								</form>
								<%-- <span class="form-text text-muted">* If Want To Access
										Add, Edit,Delete Then View Access is Compulsory</span>
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
											Submit <i class="icon-paperplane ml-2"></i>
										</button>
										<a href="${pageContext.request.contextPath}/showEmpTypeList"><button
												type="button" class="btn btn-primary">Cancel</button></a>

									</div> --%>

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
		$(document).ready(function($) {

			$("#assignTask").submit(function(e) {
				var isError = true;
				var errMsg = "";
				$("#error_table1").hide();
				$("#error_table2").hide();

				$("#error_roleId").hide();

				var checkedVals = $('.chk:checkbox:checked').map(function() {
					return this.value;
				}).get();
				checkedVals = checkedVals.join(','); 

				if ($("#roleId").val() == '') {
					$("#error_roleId").show();
					return false;
				}
				else if (checkedVals == '') {
					$("#error_table1").show();
					$("#error_table2").show();

					return false;
				}else{
					return true;
				}
 
				return false;
			});
		});

		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			checkSame();
			return;
		}
		function seleRole(roleId, roleName) {
			document.getElementById("roleId").value = roleId;
			document.getElementById("roleName").value = roleName;
		}
	</script>



</body>
</html>