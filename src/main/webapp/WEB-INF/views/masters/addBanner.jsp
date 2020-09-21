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

								<form
									action="${pageContext.request.contextPath}/insertNewBanner"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${banner.bannerId}" name="bannerId" id="bannerId">


									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="bannerEventName">Event Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${banner.bannerEventName}" name="bannerEventName"
												id="bannerEventName"> <span
												class="validation-invalid-label" id="error_bannerName"
												style="display: none;">This field is required.</span>
										</div>

									</div>

									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="frId">Franchisee<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select" multiple="multiple"
												data-fouc name="frId" id="frId"
												data-placholder="Select Category">

												<c:forEach items="${frList}" var="list" varStatus="count">

													<option value="${list.frId}"
														${fn:contains(frIds, list.frId) ? 'selected' : ''}>${list.frName}</option>

												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_frId" style="display: none;">This field is
												required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="tagId">Applicable Tags<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select" multiple="multiple"
												data-fouc name="tagId" id="tagId" data-placholder="Select ">

												<c:forEach items="${filterList}" var="list"
													varStatus="count">

													<option value="${list.filterId}"
														${fn:contains(tagIds, list.filterId) ? 'selected' : ''}>${list.filterName}</option>

												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_tagId" style="display: none;">This field is
												required.</span>
										</div>




									</div>


									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="sortNo">Sort No.<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="10"
												autocomplete="off" onchange="trim(this)"
												value="${banner.sortNo}" name="sortNo" id="sortNo">
											<span class="validation-invalid-label text-danger"
												id="error_sortNo" style="display: none;">This field
												is required.</span>
										</div>
									</div>



									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="captionOnproductPage">Caption on Product Page<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												autocomplete="off" onchange="trim(this)"
												value="${banner.captionOnproductPage}"
												name="captionOnproductPage" id="captionOnproductPage">
											<span class="validation-invalid-label text-danger"
												id="error_captionOnproductPage" style="display: none;">This
												field is required.</span>
										</div>


									</div>



									<div class="form-group row">



										<label class="col-form-label font-weight-bold col-lg-2"
											for="doc">Profile Image <span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${banner.bannerImage}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${banner.bannerImage}"> <span
												class="validation-invalid-label text-danger" id="error_doc"
												style="display: none;">This field is required.</span>
											</label>
										</div>




									</div>

									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary">
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
		var loadFile = function(event) {
			try {
				var image = document.getElementById('output');
				image.src = URL.createObjectURL(event.target.files[0]);
			} catch (err) {
				console.log(err);
			}
		};
	</script>

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