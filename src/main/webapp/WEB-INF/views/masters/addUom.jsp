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
									href="${pageContext.request.contextPath}/showUomList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;UOM List</a></span>
							</div>


							<div class="card-body">
								
								<form
									action="${pageContext.request.contextPath}/insertUom"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control" name="uomId" id="uomId" value="${uom.uomId}">
									 <input type="hidden" class="form-control" value="${uom.isParent}"
										name="isParent" id="isParent">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="language_code">UOM Name<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="uom_name" style="text-transform: uppercase;"
												id="uom_name" maxlength="6" autocomplete="off" onchange="trim(this)" value="${uom.uomName}"> 
										<span
												class="validation-invalid-label text-danger" id="error_uom_name"
												style="display: none;">This field is required.</span>	
										<span
												class="validation-invalid-label text-danger" id="unq_uom_name"
												style="display: none;">UOM Already Exist.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="language_name">UOM Show Name<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="show_name"
												id="show_name" maxlength="30" autocomplete="off" onchange="trim(this)" value="${uom.uomShowName}"> 
										<span
												class="validation-invalid-label text-danger" id="error_show_name"
												style="display: none;">This field is required.</span>		
										</div>
									</div>
									

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="language_name">UOM Description<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-10">
											<input type="text" class="form-control maxlength-badge-position" name="description"
												id="description" maxlength="200" autocomplete="off" onchange="trim(this)" value="${uom.uomDesc}"> 
										<span
												class="validation-invalid-label text-danger" id="error_description"
												style="display: none;">This field is required.</span>		
										</div>
									</div>
									
									
									<div class="form-group row">									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<c:choose>
												<c:when test="${uom.uomId>0}">
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="active_uom" id="uom_y"
															${uom.isActive==1 ? 'checked' : ''}> Active
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" value="0"
															name="active_uom" id="uom_n"
															${uom.isActive==0 ? 'checked' : ''}> In-Active
														</label>
													</div>
												</c:when>
												<c:otherwise>
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="active_uom" id="uom_y"> Active
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" value="0"
															name="active_uom" id="uom_n"> In-Active
														</label>
													</div>
												</c:otherwise>
											</c:choose>

										</div>
										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Allow Copy <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<c:choose>
												<c:when test="${uom.uomId>0}">
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="allowCopy" id="copy_y"
															${uom.allowToCopy==1 ? 'checked' : ''}> Yes
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label "> <input
															type="radio" class="form-check-input" value="0"
															name="allowCopy" id="copy_n"
															${uom.allowToCopy==0 ? 'checked' : ''}> No
														</label>
													</div>
												</c:when>
												<c:otherwise>
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="allowCopy" id="copy_y"> Yes
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label "> <input
															type="radio" class="form-check-input" value="0"
															name="allowCopy" id="copy_n"> No
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
										<c:if test="${uom.uomId==0}">
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
	</script>

	<script type="text/javascript">
	$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#show_name").val()) {
					isError = true;
					$("#error_show_name").show()
				} else {
					$("#error_show_name").hide()
				}

				if (!$("#uom_name").val()) {
					isError = true;
					$("#error_uom_name").show()
				} else {
					$("#error_uom_name").hide()
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