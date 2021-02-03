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

<body onload="changeCostEffect()" class="sidebar-xs">
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
									href="${pageContext.request.contextPath}/showFilter/${filterTypeId}"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>


							<div class="card-body">
								<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

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
											for="filterName">${filterType} Name<span
											class="text-danger">* </span>:
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

									<div style="display: none;"> 
										<label id="filtLable"  class="col-form-label font-weight-bold col-lg-2"
											for="filterType">Filter Type<span class="text-danger">*
										</span>:
										</label>
										<div id="filType" class="col-lg-4">
									<input type="text" class="form-control" value="${filterType}"
												readonly="readonly"> <input type="hidden"
												class="form-control" value="${filterTypeId}"
												name="filterTypeId" id="filterTypeId">
										</div>
										</div>
									
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<c:choose>
												<c:when test="${filter.filterId>0}">
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="isActive" id="active_n"
															${filter.isActive==1 ? 'checked' : ''}> Active
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label "> <input
															type="radio" class="form-check-input" value="0"
															name="isActive" id="active_n"
															${filter.isActive==0 ? 'checked' : ''}> In-Active
														</label>
													</div>
												</c:when>
												<c:otherwise>
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="isActive" id="active_n"> Active
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

										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Allow Copy <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<c:choose>
												<c:when test="${filter.filterId>0}">
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="allowCopy" id="copy_y"
															${filter.allowToCopy==1 ? 'checked' : ''}> Yes
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label "> <input
															type="radio" class="form-check-input" value="0"
															name="allowCopy" id="copy_n"
															${filter.allowToCopy==0 ? 'checked' : ''}> No
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
													onclick="changeCostEffect()"
													${filter.costAffect==1 ? 'checked' : ''}> Yes
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="0"
													name="isCostAffect" id="costAffect_n"
													onclick="changeCostEffect()"
													${filter.costAffect==0 ? 'checked' : ''}> No
												</label>
											</div>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="isUsedFilter">Is Used<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<c:choose>
												<c:when test="${filter.filterId>0}">
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
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
												</c:when>
												<c:otherwise>
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="isUsedFilter" id="isUsedFilter_y"> Yes
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
											for="isUsedDesc">Used For Description<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<c:choose>
												<c:when test="${filter.filterId>0}">
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="isUsedDesc" id="isUsedDesc_y"
															${filter.usedForDescription==1 ? 'checked' : ''}>
															Yes
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label "> <input
															type="radio" class="form-check-input" value="0"
															name="isUsedDesc" id="isUsedDesc_n"
															${filter.usedForDescription==0 ? 'checked' : ''}>
															No
														</label>
													</div>
												</c:when>
												<c:otherwise>
													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" class="form-check-input" checked value="1"
															name="isUsedDesc" id="isUsedDesc_y"> Yes
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label "> <input
															type="radio" class="form-check-input" value="0"
															name="isUsedDesc" id="isUsedDesc_n"> No
														</label>
													</div>
												</c:otherwise>
											</c:choose>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="sortNo">Sort No.<span class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)"
												value="${filter.sortNo > 0 ? filter.sortNo : 1}"
												name="sortNo" id="sortNo"> <span
												class="validation-invalid-label" id="error_sortNo"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row" >
										<label id="descLbl" class="col-form-label font-weight-bold col-lg-2"
											for="address">Description <span class="text-danger">
										</span>:
										</label>
										<div id="descDiv" class="col-lg-10">
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
									
									
									<div class="form-group row"  >
									<label  id="priceRangeLbl" class="col-form-label font-weight-bold col-lg-2"
											for="filterName">Price Range<span
											class="text-danger">* </span>:
										</label>
										<div id="priceRange1" class="col-lg-2">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${fromPrice}" name="fromPrice" 
												id="fromPrice"  > <span
												class="validation-invalid-label" id="error_filterName"
												style="display: none;">This field is required.</span>
										</div>
										<div class="col-lg-2" id="toDiv" style="text-align: center;" ><span >To</span></div>
								
										<div id="priceRange2" class="col-lg-2">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="70"
												autocomplete="off" onchange="trim(this)"
												value="${toPrice}" name="toPrice"
												id="toPrice"> <span
												class="validation-invalid-label" id="error_filterName"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
											<label  id="isTaglabel"  class="col-form-label font-weight-bold col-lg-2"
												for="adtotag_y">Add to Tag: <span
												class="text-danger">* </span>:
											</label>
											<div  id="isTagDiv"  class="col-lg-4">
												<c:choose>
													<c:when test="${filter.filterId>0}">
														<div class="form-check form-check-inline">
															<label class="form-check-label"> <input
																type="radio" class="form-check-input" checked value="1"
																name="addToTag" id="adtotag_y"
																${filter.isTagAdd==1 ? 'checked' : ''}> Yes
															</label>
														</div>

														<div class="form-check form-check-inline">
															<label class="form-check-label "> <input
																type="radio" class="form-check-input" value="0"
																name="addToTag" id="adtotag_n"
																${filter.isTagAdd==0 ? 'checked' : ''}> No
															</label>
														</div>
													</c:when>
													<c:otherwise>
														<div class="form-check form-check-inline">
															<label class="form-check-label"> <input
																type="radio" class="form-check-input" checked value="1"
																name="addToTag" id="adtotag_y"> Yes
															</label>
														</div>

														<div class="form-check form-check-inline">
															<label class="form-check-label "> <input
																type="radio" class="form-check-input" value="0"
																name="addToTag" id="adtotag_n"> No
															</label>
														</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									<div id="cost_div" style="display: none;">

										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="costEffectType">Cost Effect Type: <span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">
												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-check-input" checked value="1"
														name="costEffectType" id="costAffect_type_ot"
														${filter.addOnType==1 ? 'checked' : ''}> One Time
													</label>
												</div>

												<div class="form-check form-check-inline">
													<label class="form-check-label "> <input
														type="radio" class="form-check-input" value="2"
														name="costEffectType" id="costAffect_type_pu"
														${filter.addOnType==2 ? 'checked' : ''}> Per UOM
													</label>
												</div>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2"
												for="add_on_rs">Add On Rs<span class="text-danger"></span>:
											</label>
											<div class="col-lg-4">
												<input type="text"
													class="form-control maxlength-badge-position" maxlength="5"
													autocomplete="off" onchange="trim(this)"
													value="${filter.addOnRs}" name="add_on_rs" id="add_on_rs">
												<span class="validation-invalid-label" id="error_add_on_rs"
													style="display: none;">This field is required.</span>
											</div>


										</div>

										
									</div>
									<input type="hidden" id="btnType" name="btnType"> <br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="submtbtn"
											onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										<c:if test="${filter.filterId==0}">
											<button type="submit" class="btn btn-primary" id="submtbtn1"
												onclick="pressBtn(1)">
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
	$( document ).ready(function() {
	  var filterId=document.getElementById('filterId').value;
	  var filterTypeId=document.getElementById('filterTypeId').value;
	   alert(filterTypeId);
	    if(filterId!=0){
		   document.getElementById('filtLable').style.display = "none";
		   document.getElementById('filType').style.display = "none";
		}else {
			 document.getElementById('filtLable').style.display = "block";
			   document.getElementById('filType').style.display = "block";
		} 
	    
	    if(filterTypeId==16){
			   
			   document.getElementById('descDiv').style.display = "none";
			   document.getElementById('descLbl').style.display = "none";
			   document.getElementById('priceRangeLbl').style.display = "block";
			   document.getElementById('priceRange1').style.display = "block";
			   document.getElementById('priceRange2').style.display = "block";
			   document.getElementById('toDiv').style.display = "block";
		   }else{
			   
			   document.getElementById('descDiv').style.display = "block";
			   document.getElementById('descLbl').style.display = "block";
			   document.getElementById('priceRangeLbl').style.display = "none";
			   document.getElementById('priceRange1').style.display = "none";
			   document.getElementById('priceRange2').style.display = "none";
			   document.getElementById('toDiv').style.display = "none";
		   }
	   
	   if(filterTypeId==7){  
		   document.getElementById('isTagDiv').style.display = "none";
		   document.getElementById('isTaglabel').style.display = "none";
		   document.getElementById('addToTag').value=0;
	   }else{
		   document.getElementById('isTagDiv').style.display = "block"; 
		   document.getElementById('isTaglabel').style.display = "block";
	   }
	      
	 

	});
	</script>
	
	<script type="text/javascript">
		function changeCostEffect() {
			var isCostEff = 0;
			try {
				//isCostEff=${filter.costAffect}
				isCostEff = $('input[name="isCostAffect"]:checked').val();
			} catch (e) {

			}

			if (parseInt(isCostEff) == 0) {
				document.getElementById("cost_div").style.display = "none";
			} else {
				document.getElementById("cost_div").style.display = "block";
			}

		}
	</script>
	<script type="text/javascript">
		function pressBtn(btnVal) {
			$("#btnType").val(btnVal)
		}

		$(document)
				.ready(
						function($) {

							$("#submitInsert")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#filterName").val()) {
													isError = true;
													$("#error_filterName")
															.show()
												} else {
													$("#error_filterName")
															.hide()
												}

												if ($("#filterType").val() == "") {
													isError = true;
													$("#error_filterType")
															.show()
												} else {
													$("#error_filterType")
															.hide()
												}

												/* 
												 if (!$("#description").val()) {
												 isError = true;
												 $("#error_description").show()
												 } else {
												 $("#error_description").hide()
												 } */

												var isCostEffect = $(
														'input[name="isCostAffect"]:checked')
														.val();

												if (parseInt(isCostEffect) == 1) {
													if (!$("#add_on_rs").val()
															|| parseFloat($(
																	"#add_on_rs")
																	.val()) < 1) {
														isError = true;
														$("#error_add_on_rs")
																.show();
													} else {
														$("#error_add_on_rs")
																.hide();
													}
												} else {
													$("#error_add_on_rs")
															.hide();
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
																		$(
																				".btn")
																				.attr(
																						"disabled",
																						true);
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

		$('#add_on_rs').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9.]/g, '').replace(
							/(\..*)\./g, '$1');
				});

		$('#sortNo').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9]/g, '').replace(
							/(\..*)\./g, '$1');
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