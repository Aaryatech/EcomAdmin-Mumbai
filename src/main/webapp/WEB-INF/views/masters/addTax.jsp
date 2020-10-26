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
								<span class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span> 
								<!--  -->
								<span
									class="font-size-sm text-uppercase font-weight-semibold"><a class="card-title"
									href="${pageContext.request.contextPath}/showTaxList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;Tax List</a></span>
							</div>


							<div class="card-body">
								<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

								<form
									action="${pageContext.request.contextPath}/insertTax"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control" name="taxId" id="taxId" value="${tax.taxId}">
									 <input type="hidden" class="form-control" value="${tax.isParent}"
										name="isParent" id="isParent">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="taxName">Tax Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" name="taxName"
												id="taxName" maxlength="70" autocomplete="off"
												onchange="trim(this)" value="${tax.taxName}"> <span
												class="validation-invalid-label text-danger"
												id="error_taxName" style="display: none;">This field
												is required.</span> 
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="hsnCode">HSN Code<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" name="hsnCode"
												id="hsnCode" maxlength="10" autocomplete="off"
												onchange="trim(this)" value="${tax.hsnCode}"> <span
												class="validation-invalid-label text-danger"
												id="error_hsnCode" style="display: none;">This
												field is required.</span>
												
												<span
												class="validation-invalid-label text-danger"
												id="error_hsnTax" style="display: none;">Invalide HSN Code.</span>
												
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="totalTaxPer">Total Tax%<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" oninput="calTaxes(this.value)"
												class="form-control maxlength-badge-position float-num" name="totalTaxPer"
												id="totalTaxPer" maxlength="5" autocomplete="off"
												onchange="trim(this)" value="${tax.totalTaxPer}"> <span
												class="validation-invalid-label text-danger"
												id="error_totalTaxPer" style="display: none;">This field
												is required.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cessPer">CESS%<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position float-num" name="cessPer"
												id="cessPer" maxlength="5" autocomplete="off" oninput="calCess(this.value)"
												onchange="trim(this)" value="${tax.cessPer}"> <span
												class="validation-invalid-label text-danger"
												id="error_cessPer" style="display: none;">This
												field is required.</span>
										</div>
									</div>		
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="sgstPer">SGST%<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position float-num" name="sgstPer"
												id="sgstPer" autocomplete="off" readonly="readonly"
												onchange="trim(this)" value="${tax.sgstPer}"> <span
												class="validation-invalid-label text-danger"
												id="error_sgstPer" style="display: none;">This field
												is required.</span> 
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cgstPer">CGST%<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position float-num" name="cgstPer"
												id="cgstPer" autocomplete="off"  readonly="readonly"
												onchange="trim(this)" value="${tax.cgstPer}"> <span
												class="validation-invalid-label text-danger"
												id="error_cgstPer" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="igstPer">IGST%<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position float-num" name="igstPer"
												id="igstPer" autocomplete="off"  readonly="readonly"
												onchange="trim(this)" value="${tax.igstPer}"> <span
												class="validation-invalid-label text-danger"
												id="error_igstPer" style="display: none;">This field
												is required.</span> 
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${tax.taxId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="active_tax" id="tax_y"
													${tax.isActive==1 ? 'checked' : ''}> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="active_tax"
													id="tax_n" ${tax.isActive==0 ? 'checked' : ''}>
													In-Active
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="active_tax" id="tax_y"> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="active_tax"
													id="tax_n">
													In-Active
												</label>
											</div>											
											</c:otherwise>
										</c:choose>
											
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="language_name">Description<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="text"
												class="form-control maxlength-badge-position"
												name="description" id="description" maxlength="200"
												autocomplete="off" onchange="trim(this)"
												value="${tax.taxDesc}"> <span
												class="validation-invalid-label text-danger"
												id="error_taxDesc" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="allowCopy">Allow Copy <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
											<c:when test="${tax.taxId>0}">
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="allowCopy"
													id="copy_y" ${tax.allowToCopy==1 ? 'checked' : ''}>
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0" name="allowCopy"
													id="copy_n" ${tax.allowToCopy==0 ? 'checked' : ''}>
													No
												</label>
											</div>
											</c:when>
											<c:otherwise>
												<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1" name="allowCopy"
													id="copy_y">
													Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0" name="allowCopy"
													id="copy_n">
													No
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
										<c:if test="${tax.taxId==0}">										
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
	function pressBtn(btnVal){
		$("#btnType").val(btnVal)
	}
	
		var loadFile = function(event) {
			try {
				var image = document.getElementById('output');
				image.src = URL.createObjectURL(event.target.files[0]);
			} catch (err) {
				console.log(err);
			}
		};
		
		function calTaxes(ttlTax){
			if(ttlTax > 0){				
				var cessPer = $("#cessPer").val();
				var calTax = ttlTax-cessPer;
				$("#cgstPer").val(calTax/2);
				$("#sgstPer").val(calTax/2);
				$("#igstPer").val(calTax);
			}
		}
		
		function calCess(cessPer){
			var ttlTax = $("#totalTaxPer").val();
			if(cessPer > 0 && ttlTax > 0){				
				var calTax = ttlTax-cessPer;
				$("#cgstPer").val(calTax/2);
				$("#sgstPer").val(calTax/2);
				$("#igstPer").val(calTax);
			}
		}
		
		$('#hsnCode').on('input', function() {
			 this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');
		});		
		
	</script>

	<script type="text/javascript">
		 $(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#taxName").val()) {
					isError = true;
					$("#error_taxName").show()
				} else {
					$("#error_taxName").hide()
				}

				if (!$("#hsnCode").val()) {
					isError = true;
					$("#error_hsnCode").show()
				} else {
					$("#error_hsnCode").hide()
				}	
				
				if (!$("#cgstPer").val()) {
					isError = true;
					$("#error_cgstPer").show()
				} else {
					$("#error_cgstPer").hide()
				}	
				
				if (!$("#sgstPer").val()) {
					isError = true;
					$("#error_sgstPer").show()
				} else {
					$("#error_sgstPer").hide()
				}	
				
				if (!$("#igstPer").val()) {
					isError = true;
					$("#error_igstPer").show()
				} else {
					$("#error_igstPer").hide()
				}	
				
				if (!$("#cessPer").val()) {
					isError = true;
					$("#error_cessPer").show()
				} else {
					$("#error_cessPer").hide()
				}	
				
				if (!$("#totalTaxPer").val()) {
					isError = true;
					$("#error_totalTaxPer").show()
				} else {
					$("#error_totalTaxPer").hide()
				}	
				
				
				if ($("#hsnCode").val()==$("#totalTaxPer").val()) {
					isError = true;
					$("#error_hsnTax").show()
				} else {
					$("#error_hsnTax").hide()
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
		
		 $('.float-num').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});		 
		 
		/* $("#language_code").change(function(){   // 1st
			var code = $("#language_code").val();								
			var langId = $("#lang_id").val();	
		//alert(code)
		
			 $.getJSON('${getLangInfoByCode}', {
				code : code,
				langId : langId,
				ajax : 'true',
			}, function(data) {
				
				if(data.error==false){								
					$("#unq_language_code").show();
					$("#language_code").val('');
					document.getElementById("language_code").focus();
				}else{
					$("#unq_language_code").hide();
				}
			});
		}); */
		
	</script>
<script>
function trim(el) {
	el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
	replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
	replace(/\n +/, "\n"); // Removes spaces after newlines

	return;
}

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


</body>
</html>