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
	<c:url value="/getCustInfo" var="getCustInfo"></c:url>
	<c:url value="/getSubCatCodeByCatId" var="getSubCatCodeByCatId"></c:url>
	<c:url value="/chkUnqSubCatPrfx" var="chkUnqSubCatPrfx"></c:url>
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
									href="${pageContext.request.contextPath}/showSubCatList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertNewSubCat"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${subCat.subCatId}" name="subCatId" id="subCatId">

									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="catId">Category<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc onchange="getSubCatCode(this.value)"
												name="catId" id="catId" data-placholder="Select Category">

												<c:forEach items="${catList}" var="list" varStatus="count">
													<c:choose>
														<c:when test="${list.catId==subCat.catId}">
															<option selected value="${list.catId}">${list.catName}</option>
														</c:when>
														<c:otherwise>
															<option value="${list.catId}">${list.catName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger"
												id="error_catId" style="display: none;">This field is
												required.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="subCatCode">Sub Category Code<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position"
												autocomplete="off" onchange="trim(this)" readonly="readonly"
												value="${subCat.subCatCode}" name="subCatCode" id="subCatCode">
											<span class="validation-invalid-label text-danger"
												id="error_subCatCode" style="display: none;">This field
												is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="custName">Sub Category Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${subCat.subCatName}" name="subCatName"
												id="subCatName"> <span
												class="validation-invalid-label" id="error_subCatName"
												style="display: none;">This field is required.</span>
										</div>
 
 										<label class="col-form-label font-weight-bold col-lg-2"
											for="subCatPrefix">Sub Category Prefix <span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="10"
												autocomplete="off" onchange="trim(this)" style="text-transform: uppercase;"
												value="${subCat.subCatPrefix}" name="subCatPrefix"
												id="subCatPrefix"> <span
												class="validation-invalid-label text-danger"
												id="error_subCatPrefix" style="display: none;">This
												field is required.</span>
												 <span
												class="validation-invalid-label text-danger"
												id="error_unqPrefix" style="display: none;">Prefix already exist.</span>
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
												value="${subCat.sortNo}" name="sortNo" id="sortNo">
											<span class="validation-invalid-label text-danger"
												id="error_sortNo" style="display: none;">This field
												is required.</span>
										</div>
									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="allowToCopy">Allow to Copy Applicable <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="allowToCopy" id="app_y"
													${subCat.allowToCopy ==1 ? 'checked' : ''}> Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="allowToCopy" id="app_n"
													${subCat.allowToCopy==0 ? 'checked' : ''}> No
												</label>
											</div>
										</div>
									</div>

									<div class="form-group row">


										<label class="col-form-label font-weight-bold col-lg-2"
											for="subCatDesc">Description <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<textarea class="form-control maxlength-badge-position"
												maxlength="200" autocomplete="off" onchange="trim(this)"
												name="subCatDesc" id="subCatDesc">${subCat.subCatDesc}</textarea>
											<span class="validation-invalid-label" id="error_subCatDesc"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="doc">Profile Image <span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${subCat.imageName}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${subCat.imageName}"> <span
												class="validation-invalid-label text-danger" id="error_doc"
												style="display: none;">This field is required.</span>
											</label>
										</div>
									</div>

									<br>
									<div class="text-center">
										<input type="submit" class="btn btn-primary" value="Save" name="submbtn" id="btn1">
										<input type="submit" class="btn btn-primary" value="Save & Next" name="submbtn" id="btn2">
									</div>
								</form>
								<input type="hidden" value="${subCat.catId}" id="cat_id">
								<input type="hidden" value="${subCat.subCatCode}" id="cate_code">
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
		
		$('#sortNo').on('input', function() {
			 this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');
			});
	</script>
	<script type="text/javascript">

	function getSubCatCode(val){
		var prevId =$("#cat_id").val();	
		var catId = $("#catId").val();
		
		if(val!=prevId){
		 $.getJSON('${getSubCatCodeByCatId}', {
			 catId : catId,
			 ajax : 'true',
			}, function(data) {							
				$("#subCatCode").val(data.msg);
			});
		}else{			
			var code = $("#cate_code").val();
			$("#subCatCode").val(code);
		}
			
	}
	
	$('#subCatPrefix').on('change', function() {
		var prefix = $("#subCatPrefix").val();
		var subCatId = $("#subCatId").val();
		
		 $.getJSON('${chkUnqSubCatPrfx}', {
			 prefix : prefix,
			 subCatId : subCatId,
			 ajax : 'true',
			}, function(data) {							
				if(data.error){
					$("#error_unqPrefix").show()
					document.getElementById("btn1").disabled = true;
					document.getElementById("btn2").disabled = true;
				}else{
					$("#error_unqPrefix").hide()
					document.getElementById("btn1").disabled = false;
					document.getElementById("btn2").disabled = false;
				}
			});
	});
	
	</script>

	<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#catId").val()) {
					isError = true;
					$("#error_catId").show()
				} else {
					$("#error_catId").hide()
				}

				if (!$("#subCatName").val()) {
					isError = true;
					$("#error_subCatName").show()
				} else {
					$("#error_subCatName").hide()
				}
				
				if (!$("#sortNo").val() || $("#sortNo").val()==0) {
					isError = true;
					$("#error_sortNo").show()
				} else {
					$("#error_sortNo").hide()
				}

				if (!$("#subCatPrefix").val()) {
					isError = true;
					$("#error_subCatPrefix").show()
				} else {
					$("#error_subCatPrefix").hide()
				}
				/* if (!$("#doc").val()) {
					isError = true;
					$("#error_doc").show()
				} else {
					$("#error_doc").hide()
				}  */

				/* if (!$("#subCatCode").val()) {
					isError = true;
					$("#error_subCatCode").show()
				} else {
					$("#error_subCatCode").hide()
				} */
					
 
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