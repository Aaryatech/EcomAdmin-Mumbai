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
									href="${pageContext.request.contextPath}/showFilterList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">

								<form action="${pageContext.request.contextPath}/insertFilter"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${filter.filterId}" name="filterId" id="filterId">

									<input type="hidden" class="form-control"
										value="${filter.isParent}" name="isParent" id="isParent">

									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="filterName">Filter Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${filter.filterName}" name="filterName"
												id="filterName"> <span
												class="validation-invalid-label" id="error_filterName"
												style="display: none;">This field is required.</span>
										</div>

										
											<label class="col-form-label font-weight-bold col-lg-2"
												for="filterType">Filter Type <span
												class="text-danger">* </span>:
											</label> 
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
												name="filterType" id="filterType" data-placeholder="Select User Type">
												<option value=""></option>

												<c:forEach items="${filterType}" var="list"
													varStatus="count">
													<c:choose>
														<c:when test="${list.filterTypeId==filter.filterTypeId}">
															<option selected value="${list.filterTypeId}">${list.filterTypeName}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.filterTypeId}">${list.filterTypeName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_filterType" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="isActive"
													id="active_n" ${filter.isActive==1 ? 'checked' : ''}>
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isActive" id="active_n"
													${filter.isActive==0 ? 'checked' : ''}> In-Active
												</label>
											</div>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Allow Copy <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="allowCopy"
													id="copy_y" ${filter.allowToCopy==1 ? 'checked' : ''}>
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="allowCopy" id="copy_n"
													${filter.allowToCopy==0 ? 'checked' : ''}> No
												</label>
											</div>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="isContAffect">Is Cost Affect <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="isCostAffect" id="costAffect_y"
													${filter.costAffect==1 ? 'checked' : ''}> Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isCostAffect" id="costAffect_n"
													${filter.costAffect==0 ? 'checked' : ''}> No
												</label>
											</div>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="isUsedFilter">Is Used Filter <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="isUsedFilter" id="isUsedFilter_y"
													${filter.usedForFilter==1 ? 'checked' : ''}> Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isUsedFilter" id="isUsedFilter_n"
													${filter.usedForFilter==0 ? 'checked' : ''}> No
												</label>
											</div>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="isUsedDesc">Used For Description<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="isUsedDesc" id="isUsedDesc_y"
													${filter.usedForDescription==1 ? 'checked' : ''}> Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isUsedDesc" id="isUsedDesc_n"
													${filter.usedForDescription==0 ? 'checked' : ''}> No
												</label>
											</div>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="sortNo">Sort No.<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)"
												value="${filter.sortNo}" name="sortNo"
												id="sortNo"> <span
												class="validation-invalid-label" id="error_sortNo"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Description <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-10">
											<input type="text"
												class="form-control maxlength-badge-position"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${filter.filterDesc}" name="description"
												id="description"> <span
												class="validation-invalid-label text-danger"
												id="error_description" style="display: none;">This
												field is required.</span>
										</div>
									</div>



									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="subBtn">
											Save <i class="icon-paperplane ml-2"></i>
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
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#filterName").val()) {
					isError = true;
					$("#error_filterName").show()
				} else {
					$("#error_filterName").hide()
				}
				
				if ($("#filterType").val()=="") {
					isError = true;
					$("#error_filterType").show()
				} else {
					$("#error_filterType").hide()
				}
				
				

				if (!$("#description").val()) {
					isError = true;
					$("#error_description").show()
				} else {
					$("#error_description").hide()
				}

				if (!isError) {
					var x = true;
					if (x == true) {
						document.getElementById("subBtn").disabled = true;
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
	</script>


</body>
</html>