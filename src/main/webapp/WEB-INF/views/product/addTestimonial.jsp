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
	<c:url value="/getLangInfoByCode" var="getLangInfoByCode"></c:url>
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
									href="${pageContext.request.contextPath}/showHomePageTestimonial"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">
							
							<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

								<form action="${pageContext.request.contextPath}/insertHomePageTestimonial"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control" name="testimonialId"
										id="testimonialId" value="${testimonial.testimonialsId}">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="caption">Caption Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" name="caption"
												id="caption" maxlength="100" autocomplete="off"
												onchange="trim(this)" value="${testimonial.captionName}">
											<span class="validation-invalid-label text-danger"
												id="error_caption" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="testimonial_name">Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="testimonial_name" id="testimonial_name"
												maxlength="100" autocomplete="off" onchange="trim(this)"
												value="${testimonial.name}"> <span
												class="validation-invalid-label text-danger"
												id="error_testimonial_name" style="display: none;">This
												field is required.</span>
										</div>
									</div>


									<div class="form-group row">
									<label class="col-form-label font-weight-bold col-lg-2"
											for="designation">Designation<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="designation" id="designation" data-placeholder="Select Designation">
												<option value=""></option>

												<c:forEach items="${desigList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.designationId==testimonial.designation}">
															<option selected value="${list.designationId}">${list.designation}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.designationId}">${list.designation}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_designation" style="display: none;">This
												field is required.</span>
										</div>
									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="message">Message<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" name="messages"
												id="message" maxlength="100" autocomplete="off"
												onchange="trim(this)" value="${testimonial.messages}">
											<span class="validation-invalid-label text-danger"
												id="error_message" style="display: none;">This field
												is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<c:choose>
												<c:when test="${testimonial.testimonialsId>0}">
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="isActive" id="isActive_y"
															${testimonial.isActive==1 ? 'checked' : ''}>
															Active
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" value="0"
															name="isActive" id="isActive_n"
															${testimonial.isActive==0 ? 'checked' : ''}>
															In-Active
														</label>
													</div>
												</c:when>
												<c:otherwise>
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="isActive" id="isActive_y"> Active
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" value="0"
															name="isActive" id="isActive_n"> In-Active
														</label>
													</div>
												</c:otherwise>
											</c:choose>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="sortNo">Sort No<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" name="sortNo"
												id="sortNo" maxlength="3" autocomplete="off"
												onchange="trim(this)" value="${testimonial.sortNo > 0 ? testimonial.sortNo : 1}">
											<span class="validation-invalid-label text-danger"
												id="error_sortNo" style="display: none;">This field
												is required.</span>
										</div>										
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Franchise<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-10">
											<select class="form-control select-search" data-fouc data-placeholder="Select Franchises"
															name="frIds" id="frIds" multiple="multiple">
															<option value=""></option>
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
														</select>
														<span class="validation-invalid-label"
												id="error_frId" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Profile Image <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${testimonial.images}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${testimonial.images}"> <span
												class="validation-invalid-label text-danger" id="error_doc"
												style="display: none;">This field is required.</span>
												<span class="text-danger">*Please upload file having extensions
														 .jpeg/.jpg/.png only.</span>
											</label>
										</div>
									</div>
										<input type="hidden" id="btnType" name="btnType">
									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										<c:if test="${testimonial.testimonialsId==0}">
											<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn(1)">
												Save & Next<i class="icon-paperplane ml-2"></i>
											</button>
										</c:if>
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
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/views/include/cmn.js"></script> --%>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/scrolltable.js"></script>
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

		
		$('#sortNo').on('input', function() {
			 this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');
			});
	</script>

	<script type="text/javascript">
		 $(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#caption").val()) {
					isError = true;
					$("#error_caption").show()
				} else {
					$("#error_caption").hide()
				}

				if (!$("#testimonial_name").val()) {
					isError = true;
					$("#error_testimonial_name").show()
				} else {
					$("#error_testimonial_name").hide()
				}	
				

				if (!$("#designation").val()) {
					isError = true;
					$("#error_designation").show()
				} else {
					$("#error_designation").hide()
				}	
				

				if (!$("#sortNo").val()) {
					isError = true;
					$("#error_sortNo").show()
				} else {
					$("#error_sortNo").hide()
				}	
				
				if (!$("#message").val()) {
					isError = true;
					$("#error_message").show()
				} else {
					$("#error_message").hide()
				}	
				
				if (!$("#frIds").val() || $("#frIds").val()=="") {
					isError = true;
					$("#error_frId").show()
				} else {
					$("#error_frId").hide()
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
	<script>
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