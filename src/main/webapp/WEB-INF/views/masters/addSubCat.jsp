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
								<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

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
												name="catId" id="catId" data-placholder="Select Category" disabled="true">

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
												value="${subCat.sortNo > 0 ? subCat.sortNo : 1}" name="sortNo" id="sortNo">
											<span class="validation-invalid-label text-danger"
												id="error_sortNo" style="display: none;">This field
												is required.</span>
										</div>
									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="allowToCopy">Allow to Copy Applicable <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${subCat.subCatId>0}">
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
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="allowToCopy" id="app_y"> Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="allowToCopy" id="app_n"> No
												</label>
											</div>
											</c:otherwise>
										</c:choose>
											
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
											for="doc">Image <span class="text-danger">*</span>:
										</label>
										<div class="col-lg-10">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imgPath}${subCat.imageName}" /> <input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc" accept="image/*" accept=".jpg,.png,.jpeg"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${subCat.imageName}"> <span
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
										<c:if test="${subCat.subCatId==0}">
											<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn(1)">
												Save & Next<i class="icon-paperplane ml-2"></i>
											</button>
										</c:if>
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
	// A $( document ).ready() block.
	$( document ).ready(function() {
	   var subCatid=document.getElementById('subCatId').value;
	   if(subCatid>0){  	
		   $('#catId').prop('disabled', true); 
		   $('#subCatCode').prop('disabled', true); 
	}else{
		 $('#catId').prop('disabled', false); 
		   $('#subCatCode').prop('disabled', false); 
	}
	});
	</script>
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
					$("#subCatPrefix").val('');
				}else{
					$("#error_unqPrefix").hide()
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