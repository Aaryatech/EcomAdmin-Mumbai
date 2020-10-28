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
									href="${pageContext.request.contextPath}/showSpHomePages"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">
									<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

								<form
									action="${pageContext.request.contextPath}/insertSpHomePages"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${spDay.spDayId}" name="spDayId" id="spDayId">



									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="spdayName">SP Day Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${spDay.spdayName}" name="spdayName" id="spdayName">
											<span class="validation-invalid-label" id="error_spdayName"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="spdayCaption">Home Page Caption<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${spDay.spdayCaptionHomePage}" name="spdayCaption"
												id="spdayCaption"> <span
												class="validation-invalid-label" id="error_spdayCaption"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Franchise<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-10">
											<select class="form-control select-search" data-fouc
															name="frId" id="frId" multiple="multiple">
															<c:forEach items="${frList}" var="frList">
																<c:set value="0" var="flag" />
																<c:forEach items="${frIds}" var="frIds">
																	<c:if test="${frIds == frList.frId}">
																		<c:set value="1" var="flag" />
																	</c:if>
																</c:forEach>
																<c:choose>
																	<c:when test="${flag==1}">
																		<option value="${frList.frId}" selected="selected">
																		${frList.frCode}-${frList.frName}</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${frList.frId}">${frList.frCode}-${frList.frName}</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach> 
															<%-- <c:forEach items="${frList}" var="frList">
																<option value="${frList.frId}">${frList.frCode}-${frList.frName}</option>
															</c:forEach> --%>
														</select>
														<span class="validation-invalid-label"
												id="error_frId" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Tags<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-10">
											<select class="form-control select-search" data-fouc
															name="tag" id="tag" multiple="multiple">
															<c:forEach items="${tagsList}" var="tag">
																<c:set value="0" var="flag" />
																<c:forEach items="${tagIds}" var="tagIds">
																	<c:if test="${tagIds == tag.filterId}">
																		<c:set value="1" var="flag" />
																	</c:if>
																</c:forEach>
																<c:choose>
																	<c:when test="${flag==1}">
																		<option value="${tag.filterId}" selected="selected">${tag.filterName}</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${tag.filterId}">${tag.filterName}</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															
															
															<%-- <c:forEach items="${tagsList}" var="tags">
																<option value="${tags.filterId}">${tags.filterName}</option>
															</c:forEach> --%>
														</select><span class="validation-invalid-label"
												id="error_tag" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-1"
											for="cust_name">Dates<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-3">
											<input type="text" class="form-control daterange-basic_new"
												autocomplete="off" name="dates" id="dates" value="${spDay.fromDate} to ${spDay.toDate}"> <span
												class="validation-invalid-label text-danger" id="error_date"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-1"
											for="fromTime">From  <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-3">											
												<input type="time" class="form-control" value="${spDay.fromTime}"
												autocomplete="off" name="fromTime" id="fromTime"> <span
												class="validation-invalid-label text-danger" id="error_fromTime"
												style="display: none;">This field is required.</span>											
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-1"
											for="toTime">To <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-3">											
												<input type="time" class="form-control" value="${spDay.toTime}"
												autocomplete="off" name="toTime" id="toTime"> <span
												class="validation-invalid-label text-danger" id="error_toTime"
												style="display: none;">This field is required.</span>											
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="captionOnProductPage">Product Page Caption<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${spDay.captionOnProductPage}"
												name="captionOnProductPage" id="captionOnProductPage">
											<span class="validation-invalid-label"
												id="error_captionOnProductPage" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="sortNo">Sort No<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)"
												value="${spDay.sortNo > 0 ? spDay.sortNo : 1}" name="sortNo"
												id="sortNo"> <span
												class="validation-invalid-label" id="error_sortNo"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
									<label class="col-form-label font-weight-bold col-lg-2"
											for="captionImage">Image<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${spDay.spdayCaptionImageHomePage}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc" accept="image/*" accept=".jpg,.png,.jpeg"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${spDay.spdayCaptionImageHomePage}"> <span
												class="validation-invalid-label text-danger" id="error_doc"
												style="display: none;">This field is required.</span>
												<span class="text-danger">*Please upload file having extensions
														 .jpeg/.jpg/.png only.</span>
											</label>
										</div>
									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${spDay.spDayId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="isActive"
													id="active_n" ${spDay.isActive==1 ? 'checked' : ''}>
													Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isActive" id="active_n"
													${spDay.isActive==0 ? 'checked' : ''}>
													In-Active
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
													name="isActive" id="active_n">
													In-Active
												</label>
											</div>
											</c:otherwise>
										</c:choose>
											
										</div>
									</div>
									<input type="hidden" id="btnType" name="btnType">
									
									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										<c:if test="${spDay.spDayId==0}">
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
		var loadFile = function(event) {
			try {
				var image = document.getElementById('output');
				image.src = URL.createObjectURL(event.target.files[0]);
			} catch (err) {
				console.log(err);
			}
		};
		
		function pressBtn(btnVal){
			$("#btnType").val(btnVal)
		}
	</script>
	
	<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#spdayName").val()) {
					isError = true;
					$("#error_spdayName").show()
				} else {
					$("#error_spdayName").hide()
				}

				if (!$("#spdayCaption").val()) {
					isError = true;
					$("#error_spdayCaption").show()
				} else {
					$("#error_spdayCaption").hide()
				}
				
				if ($("#frId").val()=='') {
					isError = true;
					$("#error_frId").show()
				} else {
					$("#error_frId").hide()
				}
				
				if ($("#tag").val()=='') {
					isError = true;
					$("#error_tag").show()
				} else {
					$("#error_tag").hide()
				}
				
				if ($("#fromTime").val()=='') {
					isError = true;
					$("#error_fromTime").show()
				} else {
					$("#error_fromTime").hide()
				}
				
				if ($("#toTime").val()=='') {
					isError = true;
					$("#error_toTime").show()
				} else {
					$("#error_toTime").hide()
				}
				
				if ($("#captionOnProductPage").val()=='') {
					isError = true;
					$("#error_captionOnProductPage").show()
				} else {
					$("#error_captionOnProductPage").hide()
				}
				
				if ($("#sortNo").val()=='') {
					isError = true;
					$("#error_sortNo").show()
				} else {
					$("#error_sortNo").hide()
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

		$('.daterange-basic_new').daterangepicker({
			applyClass : 'bg-slate-600',

			cancelClass : 'btn-light',
			locale : {
				format : 'DD-MM-YYYY',
				separator : ' to '
			}
		});
	</script>


</body>
</html>