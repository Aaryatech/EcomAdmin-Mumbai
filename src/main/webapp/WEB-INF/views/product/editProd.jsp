<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<c:url var="getSubmoduleList" value="/getSubmoduleList" />
<c:url value="/getUserInfo" var="getUserInfo"></c:url>
<c:url value="/getSubCatPrefix" var="getSubCatPrefixData"></c:url>

<style type="text/css">
.select2-selection--multiple .select2-selection__rendered {
	border-bottom: 1px solid #ddd;
}
</style>
</head>

<body>

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
						<!-- Title -->
						<!-- <div class="mb-3">
							<h6 class="mb-0 font-weight-semibold">Hidden labels</h6>
							<span class="text-muted d-block">Inputs with empty values</span>
						</div> -->
						<!-- /title -->
						<div class="card">
							<div
								class="card-header bg-blue text-white d-flex justify-content-between">
								<span
									class="font-size-sm text-uppercase font-weight-semibold card-title">
									Edit Product ${editProd.productName}-(${editProd.productCode})</span>
								<!--  -->
								<c:if test="${addAccess==0}">
									<span class="font-size-sm text-uppercase font-weight-semibold"><a
										class="card-title"
										href="${pageContext.request.contextPath}/showProdList"
										style="color: white;"><i class="icon-add-to-list ml-2"
											style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Product
											List</a></span>
								</c:if>
							</div>
							<div class="card-header header-elements-inline">
								<%-- <table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Add Product</h5></td>
										<td width="40%" align="right">
											 <a
									href="${pageContext.request.contextPath}/showEmpList"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">Employee List </button>
								</a>
										</td>
									</tr>
								</table> --%>
							</div>

							<div class="card-body">

								<%
									if (session.getAttribute("errorMsg") != null) {
								%>
								<div
									class="alert bg-danger text-white alert-styled-left alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">
										<span>×</span>
									</button>
									<span class="font-weight-semibold">Oh snap!</span>
									<%
										out.println(session.getAttribute("errorMsg"));
									%>
								</div>

								<%
									session.removeAttribute("errorMsg");
									}
								%>
								<%
									if (session.getAttribute("successMsg") != null) {
								%>
								<div
									class="alert bg-success text-white alert-styled-left alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">
										<span>×</span>
									</button>
									<span class="font-weight-semibold">Well done!</span>
									<%
										out.println(session.getAttribute("successMsg"));
									%>
								</div>
								<%
									session.removeAttribute("successMsg");
									}
								%>

								<form
									action="${pageContext.request.contextPath}/submitProductSave"
									id="submitProdForm" enctype="multipart/form-data" method="post">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="cat_id">
											Select Category <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Category" id="cat_id" name="cat_id"
												onchange="setSubCatList()">
												<option selected disabled value="">Select Category</option>
												<c:forEach items="${catList}" var="catList"
													varStatus="count">
													<c:choose>
														<c:when test="${editProd.prodCatId==catList.catId}">
															<option selected value="${catList.catId}">${catList.catName}</option>
														</c:when>
														<c:otherwise>
															<option value="${catList.catId}">${catList.catName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label" id="error_cat_id"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="sub_cat_id">
											Sub Category <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Sub Category" id="sub_cat_id"
												name="sub_cat_id" onchange="getSubCatPrefixData()">
											</select> <span class="validation-invalid-label" id="error_sub_cat_id"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prod_code">
											Product Code <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<input type="text" class="form-control  "
												placeholder="Product Code" id="prod_code" name="prod_code"
												autocomplete="off" value="${editProd.productCode}" readonly>
											<span class="validation-invalid-label" id="error_prod_code"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="prod_name">
											Product Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input maxlength="60" type="text"
												class="form-control maxlength-badge-position"
												placeholder="Product Name" id="prod_name" name="prod_name"
												autocomplete="off" value="${editProd.productName}">
											<span class="validation-invalid-label" id="error_prod_name"
												style="display: none;">This field is required.</span>
										</div>


										<label class="col-form-label col-lg-2" for="short_name">
											Short Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="30"
												class="form-control maxlength-badge-position"
												placeholder="Product Short Name" id="short_name"
												name="short_name" autocomplete="off"
												value="${editProd.shortName}"> <span
												class="validation-invalid-label" id="error_short_name"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="tax_id">
											Select Tax <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Tax" id="tax_id" name="tax_id">

												<option selected disabled value="">Select Tax</option>
												<c:forEach items="${taxList}" var="taxList"
													varStatus="count">
													<c:choose>
														<c:when test="${editProd.taxId==taxList.taxId}">
															<option selected value="${taxList.taxId}">${taxList.taxName}</option>
														</c:when>
														<c:otherwise>
															<option value="${taxList.taxId}">${taxList.taxName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>

											</select> <span class="validation-invalid-label" id="error_tax_id"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="sort_no">
											Sort No <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="4"
												class="form-control numbersOnly"
												placeholder="Product Sort No" id="sort_no" name="sort_no"
												autocomplete="off" value="${editProd.sortId}"> <span
												class="validation-invalid-label" id="error_sort_no"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="min_qty">
											Min Quantity <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="5"
												class="form-control numbersOnly maxlength-badge-position"
												placeholder="Minimum Order Quantity" id="min_qty"
												name="min_qty" autocomplete="off" value="${editProd.minQty}">
											<span class="validation-invalid-label" id="error_min_qty"
												style="display: none;">This field is required. (min
												value 1)</span>
										</div>

										<label class="col-form-label col-lg-2" for="shelf_life">
											Shelf Life <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="3"
												class="form-control numbersOnly maxlength-badge-position"
												placeholder="Product Shelf Life" id="shelf_life"
												name="shelf_life" autocomplete="off"
												value="${editProd.shelfLife}"> <span
												class="validation-invalid-label" id="error_shelf_life"
												style="display: none;">This field is required. </span>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="is_return_allow">
											Return Allowed <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true"
												data-placeholder="Return Allowed" id="is_return_allow"
												name="is_return_allow">
												<c:choose>
													<c:when test="${editProd.isReturnAllow==1}">
														<option selected value="1">Yes</option>
														<option value="0">No</option>
													</c:when>
													<c:otherwise>
														<option selected value="0">No</option>
														<option value="1">Yes</option>
													</c:otherwise>
												</c:choose>
											</select> <span class="validation-invalid-label"
												id="error_is_return_allow" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="return_per">
											Return % <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="5"
												class="form-control numbersOnly maxlength-badge-position"
												placeholder="Product Return %" id="return_per"
												name="return_per" autocomplete="off"
												value="${editProd.retPer}"> <span
												class="validation-invalid-label" id="error_return_per"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="uom_id">
											Select UOM <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true"
												data-placeholder="Select UOM" id="uom_id" name="uom_id">

												<option selected disabled value="">Select UOM</option>
												<c:forEach items="${uomList}" var="uomList"
													varStatus="count">
													<c:choose>
														<c:when test="${editProd.uomId==uomList.uomId}">
															<option selected value="${uomList.uomId}">${uomList.uomShowName}</option>
														</c:when>
														<c:otherwise>
															<option value="${uomList.uomId}">${uomList.uomShowName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> <span class="validation-invalid-label" id="error_uom_id"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="shape_id">
											Select Shape <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" onchange="getDefaultShape()"
												multiple data placeholder="Select Shape" id="shape_id"
												name="shape_id">
											</select> <span class="validation-invalid-label" id="error_shape_id"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row" style="display: none;">
										<label class="col-form-label col-lg-2" for="is_sameDay_del">
											Same Day Delivery<span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Same Day Delivery" id="is_sameDay_del"
												name="is_sameDay_del">
												<c:choose>
													<c:when test="${editProd.allowSameDayDelivery==1}">
														<option selected value="1">Yes</option>
														<option value="0">No</option>
													</c:when>
													<c:otherwise>
														<option selected value="0">No</option>
														<option value="1">Yes</option>
													</c:otherwise>
												</c:choose>
											</select> <span class="validation-invalid-label"
												id="error_is_sameDay_del" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="sameDay_timeSlot">
											Same Day TimeSlot <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" multiple data
												placeholder="Select Same Day Time Slot"
												id="sameDay_timeSlot" name="sameDay_timeSlot">
											</select> <span class="validation-invalid-label"
												id="error_sameDay_timeSlot" style="display: none;">This
												field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="prod_type_id">
											Product Type <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Product Type" id="prod_type_id"
												name="prod_type_id">

											</select> <span class="validation-invalid-label"
												id="error_prod_type_id" style="display: none;">This
												field is required.</span>
										</div>
										<label class="col-form-label col-lg-2" for="def_shape">
											Default Shape <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Default Shape" id="def_shape" name="def_shape">
											</select> <span class="validation-invalid-label" id="error_def_shape"
												style="display: none;">This field is required.</span>
										</div>

									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="flav_ids">
											Select Flavors <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" multiple aria-hidden="true" data
												placeholder="Select Flavors" onchange="getFlav()"
												id="flav_ids" name="flav_ids">

											</select> <span class="validation-invalid-label" id="error_flav_ids"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prod_status">
											Product Status <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Status" id="prod_status"
												name="prod_status">
											</select> <span class="validation-invalid-label"
												id="error_prod_status" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="def_flav">
											Default Flavor <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Default Flavor" id="def_flav" name="def_flav">

											</select> <span class="validation-invalid-label" id="error_def_flav"
												style="display: none;">This field is required.</span>
										</div>
										<label class="col-form-label col-lg-2" for="is_veg">
											Veg/Non Veg <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" onchange="getDefaultvegNonVeg()"
												aria-hidden="true" multiple
												data-placeholder="Select Veg Non Veg" id="is_veg"
												name="is_veg">
												<!-- <option value="0">Veg</option>
												<option value="1">Non Veg</option>
											    <option value="2">Both</option> -->
											</select> <span class="validation-invalid-label" id="error_is_veg"
												style="display: none;">This field is required.</span>
										</div>


									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="book_b4">
											Book before Days <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="2"
												class="form-control numbersOnly maxlength-badge-position"
												placeholder="Book before days" id="book_b4" name="book_b4"
												autocomplete="off" value="${editProd.bookBefore}"> <span
												class="validation-invalid-label" id="error_book_b4"
												style="display: none;">This field is required. (min
												value 1)</span>
										</div>

										<label class="col-form-label col-lg-2" for="def_vnv">
											Default Veg NonVeg <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Default Veg NonVeg" id="def_vnv" name="def_vnv">

											</select> <span class="validation-invalid-label" id="error_def_vnv"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="char_limit_yn">
											Alphabet Limit Y/N <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Limit Yes No" id="char_limit_yn"
												name="char_limit_yn">
												<c:choose>
													<c:when test="${editProd.isCharLimit==1}">
														<option selected value="1">Yes</option>
														<option value="0">No</option>
													</c:when>
													<c:otherwise>
														<option value="1">Yes</option>
														<option selected value="0">No</option>
													</c:otherwise>
												</c:choose>
											</select> <span class="validation-invalid-label"
												id="error_char_limit_yn" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="no_of_alpha">
											No of Alphabets <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="2"
												class="form-control numbersOnly maxlength-badge-position"
												placeholder="No of Alphabets" id="no_of_alpha"
												name="no_of_alpha" autocomplete="off"
												value="${editProd.noOfCharsForAlphaCake}"> <span
												class="validation-invalid-label" id="error_no_of_alpha"
												style="display: none;">This field is required.</span>
										</div>


									</div>
									<div class="form-group row">
										<div class="col-lg-2">
											<input type="checkbox"
												${editProd.allowCoverPhotoUpload==1 ? 'checked' : ''}
												class="form-control" id="is_cover_ph" name="is_cover_ph"
												autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox"
												${editProd.allowBasePhotoUpload==1 ? 'checked' : ''}
												class="form-control" id="is_base_ph" name="is_base_ph"
												autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_sp_inst"
												name="is_sp_inst"
												${editProd.allowSpecialInstruction==1 ? 'checked' : ''}
												autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control"
												id="is_msg_on_cake"
												${editProd.allowMsgOnCake==1 ? 'checked' : ''}
												name="is_msg_on_cake" autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_slot_used"
												name="is_slot_used"
												${editProd.isSlotUsed==1 ? 'checked' : ''}
												autocomplete="off">
										</div>
										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_used"
												name="is_used" ${editProd.isUsed==1 ? 'checked' : ''}
												autocomplete="off">
										</div>
									</div>

									<div class="form-group row">
										<label style="text-align: center;"
											class="col-form-label col-lg-2" for="is_cover_ph">
											Cover Photo Upload <span style="color: red">* </span>:
										</label> <label style="text-align: center;"
											class="col-form-label col-lg-2" for="is_base_ph">
											Base Photo Upload <span style="color: red">* </span>:
										</label> <label style="text-align: center;"
											class="col-form-label col-lg-2" for="is_sp_inst">
											Special Instruction <span style="color: red">* </span>:
										</label> <label style="text-align: center;"
											class="col-form-label col-lg-2" for="is_msg_on_cake">
											Message On Cake <span style="color: red">* </span>:
										</label> <label style="text-align: center;"
											class="col-form-label col-lg-2" for="is_slot_used">
											Is Slot Used <span style="color: red">* </span>:
										</label> <label style="text-align: center;"
											class="col-form-label col-lg-2" for="is_used"> Is
											Active <span style="color: red">* </span>:
										</label>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="no_of_msg_char">
											No of Msg Characters <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="4"
												class="form-control numbersOnly maxlength-badge-position"
												placeholder="No of Message Characters" id="no_of_msg_char"
												name="no_of_msg_char" autocomplete="off"
												value="${editProd.noOfCharsOnCake}"> <span
												class="validation-invalid-label" id="error_no_of_msg_char"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="no_of_msg_char">
											<span style="color: red"> </span>
										</label>
										<div class="col-lg-4"></div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="bread_id">
											Bread Type <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Bread" id="bread_id" name="bread_id">

											</select> <span class="validation-invalid-label" id="error_bread_id"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="cream_id">
											Cream Type <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Cream" id="cream_id" name="cream_id">
											</select> <span class="validation-invalid-label" id="error_cream_id"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="layering_cream_id">
											Layering Cream <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Layering Cream" id="layering_cream_id"
												name="layering_cream_id">

											</select> <span class="validation-invalid-label"
												id="error_layering_cream_id" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="topping_cream_id">
											Topping Cream <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Topping Cream" id="topping_cream_id"
												name="topping_cream_id">
											</select> <span class="validation-invalid-label"
												id="error_topping_cream_id" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="appl_tags">
											Applicable Tags<span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" multiple aria-hidden="true" data
												placeholder="Select Applicable Tags" id="appl_tags"
												name="appl_tags">
											</select> <span class="validation-invalid-label" id="error_appl_tags"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prod_desc">
											Product Desc <span style="color: red;"> </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="90"
												class="form-control maxlength-badge-position"
												placeholder="Product Description" id="prod_desc"
												name="prod_desc" autocomplete="off"
												value="${editProd.productDesc}"> <span
												class="validation-invalid-label" id="error_prod_desc"
												style="display: none;">This field is required.</span>
										</div>

									</div>


									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="Ingredients">
											Ingredients<span style="color: red"> </span>:
										</label>
										<div class="col-lg-4">
											<textarea maxlength="150"
												class="form-control maxlength-badge-position"
												placeholder="Ingredients" id="Ingredients"
												name="Ingredients" autocomplete="off">${editProd.ingerdiants}</textarea>
											<span class="validation-invalid-label" id="error_Ingredients"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prep_time"
											title="Prep Time in minutes"> Preparation Time <span
											style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control numbersOnly maxlength-badge-position"
												placeholder="Preparation Time in Minutes" maxlength="3"
												id="prep_time" name="prep_time" autocomplete="off"
												value="${editProd.prepTime}"> <span
												class="validation-invalid-label" id="error_prep_time"
												style="display: none;">This field is required.</span>
										</div>

									</div>


									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="event_ids">
											Select Events <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" multiple aria-hidden="true" data
												placeholder="Select Events" id="event_ids" name="event_ids">
											</select> <span class="validation-invalid-label" id="error_event_ids"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2"
											title="Product rate setting" for="rate_setting_type">
											Rate Setting Type <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Rate Setting Type" onchange="showWeightDiv()"
												id="rate_setting_type" name="rate_setting_type">

												<option ${editProd.rateSettingType==0 ? 'selected' : ''}
													value="0">Per UOM</option>
												<option ${editProd.rateSettingType==1 ? 'selected' : ''}
													value="1">Per Kg</option>
												<option ${editProd.rateSettingType==2 ? 'selected' : ''}
													value="2">As of Filter</option>

											</select> <span class="validation-invalid-label"
												id="error_rate_setting_type" style="display: none;">This
												field is required.</span>
										</div>


									</div>

									<div class="col-lg-12" id="weight_div">
										<div class="form-group row">
											<label class="col-form-label col-lg-2" for="max_wt">
												Max Weights <span style="color: red">* </span>:
											</label>
											<div class="col-lg-2">
												<input type="text"
													class="form-control numbersOnly maxlength-badge-position"
													value="${editProd.maxWt}"
													placeholder="Maximum Weight Range" maxlength="3"
													id="max_wt" name="max_wt" autocomplete="off"> <span
													class="validation-invalid-label" id="error_max_wt"
													style="display: none;">This field is required.</span>

											</div>
											<div class="col-lg-1">
												<button type="button" onclick="show_apply_weight()"
													class="btn btn-primary" id="11">Apply</button>
											</div>
											<div class="col-lg-1"></div>
											<label class="col-form-label col-lg-2" for="weight_ids">
												Available In Weights <span style="color: red">* </span>:
											</label>
											<div class="col-lg-4">
												<select
													class="form-control form-control-select2 select2-hidden-accessible"
													data-fouc="" aria-hidden="true" data
													placeholder="Select Weight" id="weight_ids"
													name="weight_ids" multiple>

													<c:forEach items="${editProd.availInWeights}" var="wt">
														<option selected value="${wt}">${wt}</option>
													</c:forEach>
												</select> <span class="validation-invalid-label"
													id="error_weight_ids" style="display: none;">This
													field is required.</span>
											</div>

										</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="primary_img">
											Primary Image:</label>
										<div class="col-lg-4">
											<div class="input-group-btn  ">
												<img id="output" width="150"
													src="${prodImgUrl}${editProd.prodImagePrimary}" /> <input
													type="file" class="btn btn-primary" accept="image/*"
													name="primary_img" id="primary_img"
													value="${editProd.prodImagePrimary}"
													accept=".jpg,.png,.gif,.jpeg,.bmp"
													onchange="loadFile(event)"><span
													class="form-text text-muted">Only
													.jpg,.png,.gif,.jpeg,.bmp</span>
											</div>


										</div>
										<label class="col-form-label col-lg-2" for="prep_time"
											title="Product Basic MRP"> Basic MRP <span
											style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control floatOnly maxlength-badge-position"
												placeholder="Basic Product MRP" maxlength="5"
												value="${editProd.basicMrp}" id="basic_mrp" name="basic_mrp"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_basic_mrp"
												style="display: none;">This field is required. (min
												value 1)</span>
										</div>
									</div>
									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="similarProduct">
											Select Similar Product : </label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" multiple="multiple"
												data-Placeholder="Select Similar Product"
												id="similarProduct" name="similarProduct">
											</select> <span class="validation-invalid-label"
												id="error_similarProduct" style="display: none;">you
												can select only 3 product.</span>
										</div>
									</div>
									<!-- <div class="form-group row">
										<label class="col-form-label col-lg-2" for="profilePic">
											Primary Image: </label>
										<div class="col-lg-4">
											<div class="input-group-btn  ">

												<span class="filename" style="user-select: none1;"><img
													id="temppreviewimageki1" name="image1"
													class="temppreviewimageki1" alt="l"
													style="width: 200px; height: auto; display: none"> </span>
												image-preview-clear button
												<button type="button" title="Clear selected files"
													class="btn btn-default btn-secondary fileinput-remove fileinput-remove-button legitRipple image-preview-clear image-preview-clear1"
													id="1" style="display: none;">
													<i class="icon-cross2 font-size-base mr-2"></i> Clear
												</button>

												<div class="btn btn-primary btn-file legitRipple">
													<i class="icon-file-plus"></i> <span class="hidden-xs">Browse</span><input
														type="file" class="file-input browseimage browseimage1"
														data-fouc="" id="1" name="profilePic"
														accept=".jpg,.png,.gif">
												</div>
											</div>


										</div>

										<div class="col-lg-4"></div>
									</div> -->

									<div class="form-group row mb-0">
										<div style="margin: 0 auto;">
											<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/showProdList"><button
													type="button" class="btn btn-primary">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
													Cancel
												</button></a>
										</div>
									</div>
									<input type="hidden" value="${editProd.sameDayTimeAllowedSlot}"
										id="slots"> <input type="hidden"
										value="${editProd.flavourIds}" id="flavIds"> <input
										type="hidden" value="${editProd.applicableTags}" id="appTags">

									<input type="hidden" value="${editProd.eventsIds}"
										id="eventIdss"> <input type="hidden"
										value="${editProd.productCode}" id="prodCod"> <input
										type="hidden" name="prod_id" value="${productId}" id="prod_id">
									<input type="hidden" name="idtime"
										value="${editProd.insertDttime}"> <input type="hidden"
										value="${editProd.shapeId}" id="shapeids"> <input
										type="hidden" value="${editProd.isVeg}" id="vegids"> <input
										type="hidden" value="${editProd.prodImagePrimary}"
										name="prime_image">



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
	<!-- /page content -->
	<script type="text/javascript">
	//document.getElementById('output').style="display:block"
	$('.maxlength-badge-position').maxlength({
		alwaysShow : true,
		placement : 'top'
	});
	
	//Sachin 30-10-2020 - to allow selection of default flavor,shape,veg non veg
	function getFlav(){
		var options = $('#flav_ids option:selected');
		var txt = $("#flav_ids option:selected");

		// Next, translate that into an array of just the values
		var values = $.map(options, e => $(e).val());
		var textName= $.map(txt, e => $(e).text());
		
		var len=values.length;
		
		var flavHtml="";
		
		if(parseInt(len)==1 && parseInt(values[0])<1){
			//alert("a")
			flavHtml += '<option  selected value="'+values[0]+'">'
			+ textName[0] +  '</option>';
		}
		else if(parseInt(len)==1&& parseInt(values[0])>0){
			//alert("b")
			flavHtml += '<option  selected value="'+values[0]+'">'
			+ textName[0] +  '</option>';
		}
		else{
			flavHtml += '<option disabled selected value="">Select Default Flavor</option>';
		for(var x=0;x<len;x++){
			if(parseInt(values[x])>0){
				var defFlavId=${editProd.exInt3};
			if(parseInt(values[x])==parseInt(defFlavId)){
				flavHtml += '<option selected value="'+values[x]+'">'
			+ textName[x] +  '</option>';
			}else{
				flavHtml += '<option value="'+values[x]+'">'
				+ textName[x] +  '</option>';
			}
						
			}//End of If
		}//End of Loop
		}//End of else
		$('#def_flav').html(flavHtml);
		$("#def_flav").trigger("chosen:updated");
	}//End of Function getFlav()
	
	function getDefaultShape(){
		var options = $('#shape_id option:selected');
		var txt = $("#shape_id option:selected");

		// Next, translate that into an array of just the values
		var values = $.map(options, e => $(e).val());
		var textName= $.map(txt, e => $(e).text());
		
		var len=values.length;
		
		var shapeHtml="";
		
		if(parseInt(len)==1 && parseInt(values[0])<1){
			//alert("a")
			shapeHtml += '<option  selected value="'+values[0]+'">'
			+ textName[0] +  '</option>';
		}
		else if(parseInt(len)==1&& parseInt(values[0])>0){
			//alert("b")
			shapeHtml += '<option  selected value="'+values[0]+'">'
			+ textName[0] +  '</option>';
		}
		else{
			shapeHtml += '<option disabled selected value="">Select Default Shape</option>';
		for(var x=0;x<len;x++){
			if(parseInt(values[x])>0){
				var defShapeId=${editProd.exInt1};
				if(parseInt(values[x])==parseInt(defShapeId)){
				shapeHtml += '<option selected value="'+values[x]+'">'
			+ textName[x] +  '</option>';
				}else{
					shapeHtml += '<option value="'+values[x]+'">'
					+ textName[x] +  '</option>';
				}
			}//End of If
		}//End of Loop
		}//End of else
		$('#def_shape').html(shapeHtml);
		$("#def_shape").trigger("chosen:updated");
	}//End of Function getDefaultShape()
	
	function getDefaultvegNonVeg(){
		var options = $('#is_veg option:selected');
		var txt = $("#is_veg option:selected");

		// Next, translate that into an array of just the values
		var values = $.map(options, e => $(e).val());
		var textName= $.map(txt, e => $(e).text());
		
		var len=values.length;
		
		var vegHtml="";
		
		if(parseInt(len)==1 && parseInt(values[0])<1){
			//alert("a")
			vegHtml += '<option  selected value="'+values[0]+'">'
			+ textName[0] +  '</option>';
		}
		else if(parseInt(len)==1&& parseInt(values[0])>0){
			//alert("b")
			vegHtml += '<option  selected value="'+values[0]+'">'
			+ textName[0] +  '</option>';
		}
		else{
			vegHtml += '<option disabled selected value="">Select Default Veg Nonveg</option>';
		for(var x=0;x<len;x++){
			if(parseInt(values[x])>0){
				var defVegId=${editProd.exInt2};
				if(parseInt(values[x])==parseInt(defVegId)){
				vegHtml += '<option selected value="'+values[x]+'">'
			+ textName[x] +  '</option>';
				}else{
					vegHtml += '<option value="'+values[x]+'">'
					+ textName[x] +  '</option>';
				}
			}//End of If
		}//End of Loop
		}//End of else
		$('#def_vnv').html(vegHtml);
		$("#def_vnv").trigger("chosen:updated");
	}//End of Function getDefaultvegNonVeg()
	
	
	 var loadFile = function(event) {
		
		// document.getElementById('output').style.display="none";
		 try {
			var image = document.getElementById('output');
			image.src = URL.createObjectURL(event.target.files[0]);
			document.getElementById('output').style="display:block"
		 } catch(err) {
			 console.log(err);
			}
		 
		};
	function showWeightDiv(){
		var rateType=document.getElementById("rate_setting_type").value;
if(parseInt(rateType)==2 || parseInt(rateType)==1){
		document.getElementById("weight_div").style="display:block";
}else{
	document.getElementById("weight_div").style.display="none";
}
	}//end of function showWeightDiv
	
	function show_apply_weight(){
		var maxWt=document.getElementById("max_wt").value;
		var weightHtml;
		var p = "";
		var q = "Select Weight";
		/* weightHtml += '<option disabled value="'+p+'" selected>' + q
				+ '</option>';
				weightHtml += '</option>'; */
		for(var i=0.5;i<=parseFloat(maxWt);i=i+0.5){
			weightHtml += '<option value="'+i+'">'
			+ i +  ' Kg</option>';
		}
		$('#weight_ids').html(weightHtml);
		$("#weight_ids").trigger("chosen:updated");
	}//end of function show_apply_weight
	</script>
	<script type="text/javascript">
	var prod_statushtml;
	var p = "";
	var q = "Select Product Status";
	prod_statushtml += '<option disabled value="'+p+'" selected>' + q
			+ '</option>';
			prod_statushtml += '</option>';
	
	var event_idshtml;
	var p = "0";
	var q = "NA";
	event_idshtml += '<option  value="'+p+'">' + q
	+ '</option>';
	event_idshtml += '</option>';
	
	var bread_idhtml;
	var p = "0";
	var q = "NA";
	bread_idhtml += '<option value="'+p+'" selected>' + q
			+ '</option>';
	bread_idhtml += '</option>';
	
	var cream_idhtml;
	var p = "0";
	var q = "NA";
	cream_idhtml += '<option  value="'+p+'">' + q
			+ '</option>';
	cream_idhtml += '</option>';
	
	var layering_cream_idhtml;
	var p = "0";
	var q = "NA";
	layering_cream_idhtml += '<option value="'+p+'">' + q
			+ '</option>';
	layering_cream_idhtml += '</option>';
	
	var topping_cream_idhtml;
	var p = "0";
	var q = "NA";
	topping_cream_idhtml += '<option  value="'+p+'">' + q
			+ '</option>';
	topping_cream_idhtml += '</option>';
	
	var flav_idshtml;
	var p = "0";
	var q = "NA";
	
	var shape_idhtml;
	var p = "0";
	var q = "NA";
	shape_idhtml += '<option disabled value="'+p+'">' + q
			+ '</option>';
	shape_idhtml += '</option>';
	
	var sameDay_timeSlothtml;
	var p = "0";
	var q = "NA";
	
	
	var prod_type_idhtml;
	var p = "";
	var q = "Select Product Type";
	prod_type_idhtml += '<option disabled value="'+p+'" selected>' + q
			+ '</option>';
	prod_type_idhtml += '</option>';
	
	var appl_tagshtml;
	var p = "";
	var q = "Select Applicable Tags";
	
	var is_veg_html; //2020-10-21
	
	data=${filterJSON};
	//var shapeId=${editProd.shapeId};
	
  	var shp =$("#shapeids").val();//2020-10-21
	var shapeids=shp.split(",");
	
  	var vng =$("#vegids").val();//2020-10-21
	var vegids=vng.split(",");
  	
	var ts =$("#slots").val();
	var sdts=ts.split(",");
	
	var prodType=${editProd.prodTypeId};
	
	var flavIdStr =$("#flavIds").val();
	var flavArr=flavIdStr.split(",");
	
	var prodStatus=${editProd.prodStatusId};
	
	var eventStr =$("#eventIdss").val();
	var evenArr=eventStr.split(",");
	
	var appTagStr =$("#appTags").val();
	var tagArr=appTagStr.split(",");
	
	var breadTypeId=${editProd.typeOfBread};
	var  creamTypeId=${editProd.typeOfCream};
	
	var layCreamId=${editProd.layeringCream};
	var toppCreamId=${editProd.toppingCream};
	var len = data.length;
	
	if(shapeids.includes('0')){
			shape_idhtml += '<option selected value="0">NA</option>';
		}
	if(flavArr.includes('0')){
		flav_idshtml += '<option selected value="0">NA</option>';
	}
	if(evenArr.includes('0')){
		event_idshtml += '<option selected value="0">NA</option>';
	}
	for (var i = 0; i < len; i++) {
		if(1==parseInt(data[i].filterTypeId)){
		 	//if(parseInt(shapeId)==parseInt(data[i].filterId)){
		 		
		 		if(shapeids.includes(''+data[i].filterId)){ //2020-10-21
			shape_idhtml += '<option selected value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
			else{
				shape_idhtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
		}//end of if
		else if(2==parseInt(data[i].filterTypeId)){
			if(sdts.includes(''+data[i].filterId)){
			sameDay_timeSlothtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				sameDay_timeSlothtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(3==parseInt(data[i].filterTypeId)){
			if(parseInt(prodType)==parseInt(data[i].filterId)){
			prod_type_idhtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				prod_type_idhtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(4==parseInt(data[i].filterTypeId)){
			if(flavArr.includes(''+data[i].filterId)){
			flav_idshtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				flav_idshtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(5==parseInt(data[i].filterTypeId)){
			if(parseInt(prodStatus)==parseInt(data[i].filterId)){
			prod_statushtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				prod_statushtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(6==parseInt(data[i].filterTypeId)){
			if(evenArr.includes(''+data[i].filterId)){
				event_idshtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				event_idshtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(7==parseInt(data[i].filterTypeId)){
			
			if(tagArr.includes(''+data[i].filterId)){
			appl_tagshtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				appl_tagshtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(8==parseInt(data[i].filterTypeId)){
			if(parseInt(breadTypeId)==parseInt(data[i].filterId)){
			bread_idhtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				bread_idhtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
	
		else if(9==parseInt(data[i].filterTypeId)){
			if(parseInt(creamTypeId)==parseInt(data[i].filterId)){
			cream_idhtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				cream_idhtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(10==parseInt(data[i].filterTypeId)){
			if(parseInt(layCreamId)==parseInt(data[i].filterId)){
			layering_cream_idhtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				layering_cream_idhtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(11==parseInt(data[i].filterTypeId)){
			
			if(parseInt(toppCreamId)==parseInt(data[i].filterId)){
			topping_cream_idhtml += '<option selected value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
			}else{
				topping_cream_idhtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
			}
	}//end of if
		else if(12==parseInt(data[i].filterTypeId)){
			if(vegids.includes(''+data[i].filterId)){
				is_veg_html += '<option selected value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
				}else{
					is_veg_html += '<option value="' + data[i].filterId + '">'
					+ data[i].filterName + '</option>';
				}
			
		}//end of if //2020-10-21
	
	}//end of for loop
	
	$('#flav_ids').html(flav_idshtml);
	$("#flav_ids").trigger("chosen:updated");
	
	$('#prod_type_id').html(prod_type_idhtml);
	$("#prod_type_id").trigger("chosen:updated");
	
	$('#sameDay_timeSlot').html(sameDay_timeSlothtml);
	$("#sameDay_timeSlot").trigger("chosen:updated");
	
	$('#shape_id').html(shape_idhtml);
	$("#shape_id").trigger("chosen:updated");
	
	$('#topping_cream_id').html(topping_cream_idhtml);
	$("#topping_cream_id").trigger("chosen:updated");
	
	$('#layering_cream_id').html(layering_cream_idhtml);
	$("#layering_cream_id").trigger("chosen:updated");
	
	$('#cream_id').html(cream_idhtml);
	$("#cream_id").trigger("chosen:updated");
	
	$('#bread_id').html(bread_idhtml);
	$("#bread_id").trigger("chosen:updated");
	
	$('#event_ids').html(event_idshtml);
	$("#event_ids").trigger("chosen:updated");
	
	$('#prod_status').html(prod_statushtml);
	$("#prod_status").trigger("chosen:updated");
	
	$('#appl_tags').html(appl_tagshtml);
	$("#appl_tags").trigger("chosen:updated");
	
	$('#is_veg').html(is_veg_html);
	$("#is_veg").trigger("chosen:updated");
	
	
	
	function setSubCatList(){
		var catId=document.getElementById("cat_id").value;
		prodCod=document.getElementById("prodCod").value;
		document.getElementById("prod_code").value=prodCod;
		var html;
		var p = "";
		var q = "Select Sub Category";
		html += '<option disabled value="'+p+'" selected>' + q
				+ '</option>';
		html += '</option>';

		var temp = 0;
		var data=${subCatListJSON};
	var editSubCatId=0;
	
	try{
		editSubCatId=${editProd.prodSubCatId};
	}catch (e) {
		editSubCatId=${editProd.prodSubCatId};
	}
		var len = data.length;
		for (var i = 0; i < len; i++) {
			if(parseInt(catId)==parseInt(data[i].catId)){
				if(parseInt(editSubCatId)==parseInt(data[i].subCatId)){
				html += '<option selected value="' + data[i].subCatId + '">'
					+ data[i].subCatName + '</option>';
				}else{
					html += '<option value="' + data[i].subCatId + '">'
					+ data[i].subCatName + '</option>';
				}
			}//end of if
		}//end of for loop
		$('#sub_cat_id').html(html);
		$("#sub_cat_id").trigger("chosen:updated");
		getSimilarProductList(catId);
		//document.getElementById("prod_code").value="";
	}//end of function  
	</script>

	<script>
	function getSimilarProductList(catId){
		//alert("sdf")
		
		var smiprd='${editProd.exVar4}';
		 
		var spltsmi = smiprd.split(",");
		
		var fd = new FormData();
		fd.append('catId', catId);
		$
		.ajax({
		url : '${pageContext.request.contextPath}/getProductListByCatId',
		type : 'post',
		dataType : 'json',
		data : fd,
		contentType : false,
		processData : false,
		success : function(data) 
		{
			 
			var html = '<option disabled value="" > Select Similar Product </option>';
 
			var len = data.length;
			for (var i = 0; i < len; i++) {
				 var find = 0;
				 
				for (var j = 0; j < spltsmi.length; j++) {
					
					if(spltsmi[j]==data[i].productId ){
						
						html += '<option value="' + data[i].productId + '" selected>'
						+ data[i].productName + '</option>';
						find=1;
						break; 
					}
					
				}
				if(find==0){
					html += '<option value="' + data[i].productId + '">'
					+ data[i].productName + '</option>';
				}
				
				 
			}//end of for loop

			$('#similarProduct').html(html);
			$("#similarProduct").trigger("chosen:updated");
								
		}
		
		});
	}
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}
		
		function getSubCatPrefixData(){
			var fd = new FormData();
			subCatId=document.getElementById("sub_cat_id").value;
			prodCod=document.getElementById("prodCod").value;
			document.getElementById("prod_code").value=prodCod;
			var editSubCatId=${editProd.prodSubCatId};
			
			if(parseInt(editSubCatId)!=parseInt(subCatId)){
			
				if(parseInt(subCatId)>0){
				fd.append('subCatId', subCatId);
				$
				.ajax({
				url : '${pageContext.request.contextPath}/getSubCatPrefix',
				type : 'post',
				dataType : 'json',
				data : fd,
				contentType : false,
				processData : false,
				success : function(data) {
										document.getElementById("prod_code").value=""+data.subCatPrefix+"-"+(parseInt(data.prodCount)+1);
										}
				
				})
				
			}//end of if
			}
			
		}//end of function
		
		$(document)
				.ready(
						function($) {
							$("#submitProdForm")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";
												if (!$("#cat_id").val()) {
													isError = true;
													$("#error_cat_id").show();
												} else {
													$("#error_cat_id").hide();
												}

												if (!$("#sub_cat_id").val()) {
													isError = true;
													$("#error_sub_cat_id").show();
												} else {
													$("#error_sub_cat_id").hide();
												}
												
												if (!$("#prod_name").val()) {
													isError = true;
													$("#error_prod_name").show();
												} else {
													$("#error_prod_name").hide();
												}
												if (!$("#short_name").val()) { 
													$("#error_short_name").show();
													isError = true;
												} else {
													$("#error_short_name").hide();
												}	
												
												if (!$("#tax_id").val()) {
													isError = true;
													$("#error_tax_id").show();
													} else {
														$("#error_tax_id").hide();
													}	

													if (!$("#sort_no").val()) { 
														$("#error_sort_no").show();
														isError = true;
													} else {
														$("#error_sort_no").hide();
													}	
													
													

													if (!$("#min_qty").val()||parseFloat($("#min_qty").val())<1) { 
														$("#error_min_qty").show();
														isError = true;
													} else {
														$("#error_min_qty").hide();
													}	

													if (!$("#shelf_life").val()||parseFloat($("#shelf_life").val())<1) { 
														$("#error_shelf_life").show();
														isError = true;
													} else {
														$("#error_shelf_life").hide();
													}
													if (!$("#is_return_allow").val()) { 
														$("#error_is_return_allow").show();
														isError = true;
													} else {
														$("#error_is_return_allow").hide()
													}


												if (parseInt($("#is_return_allow").val())==1){
													if (!$("#return_per").val() || parseFloat($("#return_per").val())>100 || parseFloat($("#return_per").val())<1) { 
													$("#error_return_per").show();
													isError = true;
													} else {
														$("#error_return_per").hide()
													}
												}else{
												//$("#return_per").val()=0;
												$("#error_return_per").hide();
												}

												if (!$("#uom_id").val()) { 
													$("#error_uom_id").show();
													isError = true;
													} else {
														$("#error_uom_id").hide()
													}
												var shapecnt = $('#shape_id > option:selected');
										         if(shapecnt.length == 0){
											//	if (!$("#shape_id").val()) { 
													$("#error_shape_id").show();
													isError = true;
													} else {
														$("#error_shape_id").hide()
													}


												/* if (!$("#is_sameDay_del").val()) { 
													$("#error_is_sameDay_del").show();
													isError = true;
													} else {
														$("#error_is_sameDay_del").hide()
													}

												if (parseInt($("#is_sameDay_del").val())==1){
													var timeSlotCount = $('#sameDay_timeSlot > option:selected');
											         if(timeSlotCount.length == 0){
														$("#error_sameDay_timeSlot").show();
														isError = true;
													} else {
														$("#error_sameDay_timeSlot").hide();
													}
												}else{
													//$("#sameDay_timeSlot").val()=0;
														$("#error_sameDay_timeSlot").hide();
												} */

												if (!$("#prod_type_id").val()) { 
													$("#error_prod_type_id").show();
													isError = true;
													} else {
														$("#error_prod_type_id").hide();
													}

												var flavCount = $('#flav_ids > option:selected');
										         if(flavCount.length == 0){
													$("#error_flav_ids").show();
												isError = true;
													} else {
														$("#error_flav_ids").hide();
													}

												if (!$("#prod_status").val()) { 
													$("#error_prod_status").show();
													isError = true;
													} else {
														$("#error_prod_status").hide();
													}

												if (!$("#book_b4").val()|| parseInt($("#book_b4").val())<1) { 
													$("#error_book_b4").show();
													isError = true;
													} else {
														$("#error_book_b4").hide();
													}
												var eventCount = $('#event_ids > option:selected');
										         if(eventCount.length == 0){
													$("#error_event_ids").show();
													isError = true;
													} else {
														$("#error_event_ids").hide();
													}

												if (!$("#char_limit_yn").val()) { 
													$("#error_char_limit_yn").show();
													isError = true;
													} else {
														$("#error_char_limit_yn").hide();
													}

												
												if (parseInt($("#char_limit_yn").val())==1) { 
												if (!$("#no_of_alpha").val()) {
														 $("#error_no_of_alpha").show();
														isError = true;
													} else {
														$("#error_no_of_alpha").hide()
													}
												}else{
													$("#error_no_of_alpha").hide()
												}

												//if (parseInt($("#is_msg_on_cake").val())==1) {
													if($("#is_msg_on_cake").prop('checked')){
														//alert("Checked True");
														//if (parseInt($("#no_of_msg_char").val())<1)
															if (!$("#no_of_msg_char").val()) {
															//alert("Checked True in if Length <1");
														 $("#error_no_of_msg_char").show();
														isError = true;
													}
													 else {
														// alert("Checked true value proper");
														$("#error_no_of_msg_char").hide();
													}
												}
												else{
													//alert("Checked false");
													$("#error_no_of_msg_char").hide();
												}

												if (!$("#bread_id").val()) {
														 $("#error_bread_id").show();
														isError = true;
													} else {
														$("#error_bread_id").hide()
													}
												
												if (!$("#cream_id").val()) {
														 $("#error_cream_id").show();
														isError = true;
													} else {
														$("#error_cream_id").hide()
													}

												if (!$("#layering_cream_id").val()) {
														 $("#error_layering_cream_id").show();
														isError = true;
													} else {
														$("#error_layering_cream_id").hide()
													}
												
												if (!$("#topping_cream_id").val()) {
														 $("#error_topping_cream_id").show();
														isError = true;
													} else {
														$("#error_topping_cream_id").hide()
													}

												var tagCount = $('#appl_tags > option:selected');
										         if(tagCount.length == 0){
														 $("#error_appl_tags").show();
														isError = true;
													} else {
														$("#error_appl_tags").hide()
													}

												/* if (!$("#prod_desc").val()) {
														 $("#error_prod_desc").show();
														isError = true;
													} else {
														$("#error_prod_desc").hide()
													}

												if (!$("#Ingredients").val()) {
														 $("#error_Ingredients").show();
														isError = true;
													} else {
														$("#error_Ingredients").hide()
													} */


												if (!$("#prep_time").val()|| parseInt($("#prep_time").val())<0) {
														 $("#error_prep_time").show();
														isError = true;
													} else {
														$("#error_prep_time").hide()
			 										}
												var vegcnt = $('#is_veg > option:selected');
										         if(vegcnt.length == 0){
											//	if (!$("#is_veg").val()) {
														 $("#error_is_veg").show();
														isError = true;
													} else {
														$("#error_is_veg").hide()
													}

												if (!$("#rate_setting_type").val()) {
														 $("#error_rate_setting_type").show();
														isError = true;
													} else {
														$("#error_rate_setting_type").hide()
													}

												if (parseInt($("#rate_setting_type").val())==2||parseInt($("#rate_setting_type").val())==1)
												{
												if (!$("#max_wt").val()) {		

												 $("#error_max_wt").show();
														isError = true;
												}
													 else {
														$("#error_max_wt").hide()
													}
												}
												else{
												//$("#error_max_wt").val()=0;
												}

												if (parseInt($("#rate_setting_type").val())==2||parseInt($("#rate_setting_type").val())==1)
												{
												var wtIds = $('#weight_ids > option:selected');
										         if(wtIds.length == 0){
												 $("#error_weight_ids").show();
														isError = true;
												}
													 else {
														$("#error_weight_ids").hide();
													}
												}
												
												if (!$("#basic_mrp").val()|| parseInt($("#basic_mrp").val())<1) { 
													$("#error_basic_mrp").show();
													isError = true;
													} else {
														$("#error_basic_mrp").hide();
													}
												
												
												
												//Sachin 30-10-2020
												if (!$("#def_flav").val()) {
														isError = true;	
														$("#error_def_flav").show();														
														$("#def_flav").focus();
													} else {
														$("#error_def_flav").hide()
													}
												
												if (!$("#def_shape").val()) {
													isError = true;	
													$("#error_def_shape").show();														
													$("#def_shape").focus();
												} else {
													$("#error_def_shape").hide()
												}
												
												if (!$("#def_vnv").val()) {
													isError = true;	
													$("#error_def_vnv").show();														
													$("#def_vnv").focus();
												} else {
													$("#error_def_vnv").hide()
												}
												
												//alert("Is Error "+isError)
												if (!isError) {
													var x = false;
													bootbox.confirm({
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
														callback : function(result) {
															if(result){
																document
																	.getElementById("submtbtn").disabled = true;
																var form = document.getElementById("submitProdForm")
															    form.submit();
															}
														}
													});
													//end ajax send this to php page
													return false;
												}//end of if !isError
												return false;
											});
						});
		//
	</script>
	<script type="text/javascript">
		// Single picker
		$('.datepickerclass').daterangepicker({
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

		//daterange-basic_new
		// Basic initialization
		$('.daterange-basic_new').daterangepicker({
			applyClass : 'bg-slate-600',

			cancelClass : 'btn-light',
			locale : {
				format : 'DD-MM-YYYY',
				separator : ' to '
			}
		});
	</script>


	<script type="text/javascript">
		function readURL(input) {
		}

		$("#profilePic").change(function() {

			//readURL(this);
		});

		$(function() {

			//image 1
			// Create the close button

			// Clear event
			$('.image-preview-clear').click(function() {
				var imgid = $(this).attr('id');

				$('.browseimage' + imgid).val("");
				$('.image-preview-clear' + imgid).hide();

				//$('.image-preview-input-title'+imgid).text("Browse"); 
				$('.temppreviewimageki' + imgid).attr("src", '');
				$('.temppreviewimageki' + imgid).hide();
			});
			// Create the preview image
			$(".browseimage").change(
					function() {
						var img = $('<img/>', {
							id : 'dynamic',
							width : 250,
							height : 200,
						});
						var imgid = $(this).attr('id');
						var file = this.files[0];
						var reader = new FileReader();
						// Set preview image into the popover data-content
						reader.onload = function(e) {

							//	$('.image-preview-input-title'+imgid).text("Change");
							$('.image-preview-clear' + imgid).show();
							//	$('.image-preview-filename'+imgid).val(file.name);   
							img.attr('src', e.target.result);

							$(".temppreviewimageki" + imgid).attr("src",
									$(img)[0].src);
							$(".temppreviewimageki" + imgid).show();

						}
						reader.readAsDataURL(file);
					});
			//end  
		});
	</script>


</body>
</html>

