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
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">
	<c:url var="chkUniqPrefix" value="chkUniqPrefix" />
	<c:url value="/getUserInfo" var="getUserInfo"></c:url>
	<c:url value="/getUserInfoByEmail" var="getUserInfoByEmail" />
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
									href="${pageContext.request.contextPath}/showFilterTypeList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">
								<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
			

								<form
									action="${pageContext.request.contextPath}/insertFilterType"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${filterType.filterTypeId}" name="filterTypeId"
										id="filterTypeId">



									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="catName">Filter Type Name<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="70" autocomplete="off" onchange="trim(this)"
												value="${filterType.filterTypeName}" name="filterTypeName"
												id="filterTypeName"> <span
												class="validation-invalid-label" id="error_filterTypeName"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${filterType.filterTypeId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="isActive"
													id="active_n" ${filterType.isActive==1 ? 'checked' : ''}>
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isActive" id="active_n"
													${filterType.isActive==0 ? 'checked' : ''}> In-Active
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="isActive"
													id="active_n">
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isActive" id="active_n"> In-Active
												</label>
											</div>
											</c:otherwise>
										</c:choose>											
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="isCostAffect">Is Cost Affect <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${filterType.filterTypeId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="isCostAffect"
													id="costAffect_y" ${filterType.isCostAffect==1 ? 'checked' : ''}>
													Yes
												</label>
											</div>
											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isCostAffect" id="costAffect_n"
													${filterType.isCostAffect==0 ? 'checked' : ''}> No
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="isCostAffect"
													id="costAffect_y">
													Yes
												</label>
											</div>
											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isCostAffect" id="costAffect_n"> No
												</label>
											</div>
											</c:otherwise>
										</c:choose>
											
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="isUsedFilter">Is Used Filter <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${filterType.filterTypeId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="isUsedFilter"
													id="isUsedFilter_y" ${filterType.isUsedFilter==1 ? 'checked' : ''}>
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isUsedFilter" id="isUsedFilter_n"
													${filterType.isUsedFilter==0 ? 'checked' : ''}> No
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="isUsedFilter"
													id="isUsedFilter_y">
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isUsedFilter" id="isUsedFilter_n"> No
												</label>
											</div>
											</c:otherwise>
										</c:choose>											
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Description <span class="text-danger">
										</span>:
										</label>
										<div class="col-lg-10">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${filterType.filterTypeDesc}" name="description" id="description">
											<span class="validation-invalid-label text-danger"
												id="error_description" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									<input type="hidden" id="btnType" name="btnType">
									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										<c:if test="${filterType.filterTypeId==0}">										
											<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn(1)">
												Save & Next<i class="icon-paperplane ml-2"></i>
											</button>
										</c:if>
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
	function pressBtn(btnVal){
		$("#btnType").val(btnVal)
	}
	
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#filterTypeName").val()) {
					isError = true;
					$("#error_filterTypeName").show()
				} else {
					$("#error_filterTypeName").hide()
				}
				
				/* if (!$("#description").val()) {
					isError = true;
					$("#error_description").show()
				} else {
					$("#error_description").hide()
				} */
				
			
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
	</script>


</body>
</html>