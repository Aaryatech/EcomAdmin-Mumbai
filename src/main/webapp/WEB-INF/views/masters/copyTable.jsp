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
	<c:url value="/getCustInfo" var="getCustInfo"></c:url>
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
									href="${pageContext.request.contextPath}/showBannerList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">

								<form action="${pageContext.request.contextPath}/showCopyTable"
									id="submitInsert">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${banner.bannerId}" name="bannerId" id="bannerId">


									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="frId">Select<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="tableId" id="tableId" onchange=" addOptionText()"
												data-placholder="Select Table To Copy">

												<c:forEach items="${tblList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.tableName eq tblName}">
															<option selected value="${list.tableName}">${list.tableName}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.tableName}">${list.tableName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_frId" style="display: none;">This field is
												required.</span>
										</div>
										<div class="col-lg-4">
											<button type="submit" class="btn btn-primary">
												Search <i class="icon-paperplane ml-2"></i>
											</button>
										</div>
									</div>


									<input type="hidden" name="lstUserText" id="txtUserText">


								</form>


								<form
									action="${pageContext.request.contextPath}/submitCopyTable"
									id="submitCopyTable">



									<table class="table datatable-header-basic" id="printtable1">
										<thead>
											<tr>
												<th width="5%">SR. No.<input type="checkbox" name="selAll" id="selAll" /></th>
												<c:forEach items="${fieldList}" var="fieldList"
													varStatus="count">
													<th>${fieldList}</th>

												</c:forEach>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${datalist}" var="datalist"
												varStatus="count">
												<tr>

													<td>${count.index+1}<input type="checkbox" id="primId${datalist.prim_id}"
										value="${datalist.prim_id}" name="primId" class="select_all"></td>

													<%-- <c:set var="summary" value=".ext_field${n.index+1}" /> --%>



													<c:choose>
														<c:when test="${temp==1}">
															<td>${datalist.ext_field1}</td>
														</c:when>

														<c:when test="${temp==2}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
														</c:when>
														<c:when test="${temp==3}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
														</c:when>

														<c:when test="${temp==3}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
														</c:when>

														<c:when test="${temp==4}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
															<td>${datalist.ext_field4}</td>
														</c:when>


														<c:when test="${temp==5}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
															<td>${datalist.ext_field4}</td>
															<td>${datalist.ext_field5}</td>
														</c:when>

														<c:when test="${temp==6}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
															<td>${datalist.ext_field4}</td>
															<td>${datalist.ext_field5}</td>
															<td>${datalist.ext_field6}</td>
														</c:when>

														<c:when test="${temp==7}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
															<td>${datalist.ext_field4}</td>
															<td>${datalist.ext_field5}</td>
															<td>${datalist.ext_field6}</td>
															<td>${datalist.ext_field7}</td>
														</c:when>

														<c:when test="${temp==8}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
															<td>${datalist.ext_field4}</td>
															<td>${datalist.ext_field5}</td>
															<td>${datalist.ext_field6}</td>
															<td>${datalist.ext_field7}</td>
															<td>${datalist.ext_field8}</td>

														</c:when>
														<c:when test="${temp==9}">
															<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
															<td>${datalist.ext_field4}</td>
															<td>${datalist.ext_field5}</td>
															<td>${datalist.ext_field6}</td>
															<td>${datalist.ext_field7}</td>
															<td>${datalist.ext_field8}</td>
															<td>${datalist.ext_field9}</td>

														</c:when>
														<c:otherwise>
         . 
 																	<td>${datalist.ext_field1}</td>
															<td>${datalist.ext_field2}</td>
															<td>${datalist.ext_field3}</td>
															<td>${datalist.ext_field4}</td>
															<td>${datalist.ext_field5}</td>
															<td>${datalist.ext_field6}</td>
															<td>${datalist.ext_field7}</td>
															<td>${datalist.ext_field8}</td>
															<td>${datalist.ext_field9}</td>
															<td>${datalist.ext_field10}</td>
														</c:otherwise>
													</c:choose>

												</tr>

											</c:forEach>
										</tbody>
									</table>




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
		function addOptionText() {

			var elm = document.getElementById("tableId");
			var text = elm.options[elm.selectedIndex].innerHTML;
			document.getElementById("txtUserText").value = text;

		}
		
		
		
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

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#bannerEventName").val()) {
					isError = true;
					$("#error_bannerName").show()
				} else {
					$("#error_bannerName").hide()
				}

				if ($("#frId").val().length == 0) {
					isError = true;
					$("#error_frId").show()
				} else {
					$("#error_frId").hide()
				}
				if ($("#tagId").val().length == 0) {
					isError = true;
					$("#error_tagId").show()
				} else {
					$("#error_tagId").hide()
				}

				if (!$("#captionOnproductPage").val()) {
					isError = true;
					$("#error_captionOnproductPage").show()
				} else {
					$("#error_captionOnproductPage").hide()
				}

				if (!$("#sortNo").val() || $("#sortNo").val() == 0) {
					isError = true;
					$("#error_sortNo").show()
				} else {
					$("#error_sortNo").hide()
				}

				/* if (!$("#doc").val()) {
					isError = true;
					$("#error_doc").show()
				} else {
					$("#error_doc").hide()
				}  */

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
	</script>
	<script type="text/javascript">
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}

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

		$('.datepickerclass').daterangepicker({
			"autoUpdateInput" : true,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

		$('#mob_no').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9]/g, '').replace(
							/(\..*)\./g, '$1');
				});
	</script>


</body>
</html>