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
							Add Product</span>
						<!--  -->
						<c:if test="${addAccess==0}">
							<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/showProdList"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Product List</a></span>
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
									id="submitProdForm" enctype="multipart/form-data" method="post"
									>
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
												<option value="${catList.catId}">${catList.catName}</option>
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
												placeholder="Select Sub Category" id="sub_cat_id" name="sub_cat_id"
												onchange="getSubCatPrefixData()">
											</select> <span class="validation-invalid-label"  id="error_sub_cat_id"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prod_code">
											Product Code <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<input type="text" class="form-control  "
												placeholder="Product Code" id="prod_code" name="prod_code"
												autocomplete="off" readonly> <span
												class="validation-invalid-label" id="error_prod_code"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="prod_name">
											Product Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input  type="text" class="form-control maxlength-badge-position"
												placeholder="Product Name" maxlength="60" id="prod_name" name="prod_name"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_prod_name"
												style="display: none;">This field is required.</span>
										</div>


										<label class="col-form-label col-lg-2" for="short_name">
											Short Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="30" class="form-control maxlength-badge-position"
												placeholder="Product Short Name" id="short_name"
												name="short_name" autocomplete="off"> <span
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
												<option value="${taxList.taxId}">${taxList.taxName}</option>
												</c:forEach>
												
											</select> <span class="validation-invalid-label" id="error_tax_id"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="sort_no">
											Sort No <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="4" class="form-control numbersOnly maxlength-badge-position"
												placeholder="Product Sort No" id="sort_no" name="sort_no"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_sort_no"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="min_qty">
											Min Quantity <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="5" class="form-control numbersOnly maxlength-badge-position"
												placeholder="Minimum Order Quantity" id="min_qty"
												name="min_qty" autocomplete="off"> <span
												class="validation-invalid-label" id="error_min_qty"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="shelf_life">
											Shelf Life <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="3" class="form-control numbersOnly maxlength-badge-position"
												placeholder="Product Shelf Life" id="shelf_life"
												name="shelf_life" autocomplete="off"> <span
												class="validation-invalid-label" id="error_shelf_life"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="is_return_allow">
											Return Allowed <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Return Allowed" id="is_return_allow"
												name="is_return_allow">
												<option selected value="1">Yes</option>
												<option  value="0">No</option>
											</select> <span class="validation-invalid-label"
												id="error_is_return_allow" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="return_per">
											Return % <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="3" class="form-control numbersOnly maxlength-badge-position"
												placeholder="Product Return %" id="return_per"
												name="return_per" autocomplete="off"> <span
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
												data-fouc="" aria-hidden="true" data
												placeholder="Select UOM" id="uom_id" name="uom_id">
											
											<option selected disabled value="">Select UOM</option>
												<c:forEach items="${uomList}" var="uomList"
												varStatus="count">
												<option value="${uomList.uomId}">${uomList.uomShowName}</option>
												</c:forEach>
											</select> <span class="validation-invalid-label"
												id="error_uom_id" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="shape_id">
											Select Shape <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Shape" id="shape_id" name="shape_id">
											</select> <span class="validation-invalid-label" id="error_shape_id"
												style="display: none;">This field is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="is_sameDay_del">
											Same Day Delivery<span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Same Day Delivery" id="is_sameDay_del"
												name="is_sameDay_del">
												<option selected value="1">Yes</option>
												<option value="0">No</option>
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
											</select> <span class="validation-invalid-label" id="error_sameDay_timeSlot"
												style="display: none;">This field is required.</span>
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

										
									</div>


									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="flav_ids">
											Select Flavors <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" multiple aria-hidden="true" data
												placeholder="Select Flavors" id="flav_ids" name="flav_ids">
											
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
										<label class="col-form-label col-lg-2" for="book_b4">
											Book before Days <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="2" class="form-control numbersOnly maxlength-badge-position"
												placeholder="Book before days" id="book_b4" name="book_b4"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_book_b4"
												style="display: none;">This field is required.</span>
										</div>

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
												<option selected value="1">Yes</option>
												<option value="0">No</option>
												
											</select> <span class="validation-invalid-label"
												id="error_char_limit_yn" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="no_of_alpha">
											No of Alphabets <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="2" class="form-control numbersOnly maxlength-badge-position"
												placeholder="No of Alphabets" id="no_of_alpha"
												name="no_of_alpha" autocomplete="off"> <span
												class="validation-invalid-label" id="error_no_of_alpha"
												style="display: none;">This field is required.</span>
										</div>


									</div>
									<div class="form-group row">
										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_cover_ph"
												  name="is_cover_ph" autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_base_ph"
												  name="is_base_ph" autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_sp_inst"
												  name="is_sp_inst" autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control"
												id="is_msg_on_cake" name="is_msg_on_cake"
												autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_slot_used"
												  name="is_slot_used" autocomplete="off">
										</div>
										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_used"
												  name="is_used" autocomplete="off">
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="is_cover_ph">
											Cover Photo Upload <span style="color: red">* </span>:
										</label> <label class="col-form-label col-lg-2" for="is_base_ph">
											Base Photo Upload <span style="color: red">* </span>:
										</label> <label class="col-form-label col-lg-2" for="is_sp_inst">
											Special Instruction <span style="color: red">* </span>:
										</label> <label class="col-form-label col-lg-2" for="is_msg_on_cake">
											Message On Cake <span style="color: red">* </span>:
										</label> <label class="col-form-label col-lg-2" for="is_slot_used">
											    Is Slot Used <span style="color: red">* </span>:
										</label> <label class="col-form-label col-lg-2" for="is_used">
											Is Used <span style="color: red">* </span>:
										</label>
									</div>

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="no_of_msg_char">
											No of Msg Characters <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="4" class="form-control numbersOnly maxlength-badge-position"
												placeholder="No of Message Characters" id="no_of_msg_char"
												name="no_of_msg_char" autocomplete="off"> <span
												class="validation-invalid-label" id="error_no_of_msg_char"
												style="display: none;">This field is required.</span>
										</div>
										
										<label class="col-form-label col-lg-2" for="no_of_msg_char">
											 <span style="color: red"> </span>
										</label>
										<div class="col-lg-4">
										</div>
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
												<option selected value="1">L1</option>
												<option selected value="0">L2</option>
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
												</select> <span
												class="validation-invalid-label" id="error_appl_tags"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prod_desc">
											Product Desc <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="90" class="form-control maxlength-badge-position"
												placeholder="Product Description" id="prod_desc"
												name="prod_desc" autocomplete="off"> <span
												class="validation-invalid-label" id="error_prod_desc"
												style="display: none;">This field is required.</span>
										</div>

									</div>


									<div class="form-group row">

										<label class="col-form-label col-lg-2" for="Ingredients">
											Ingredients<span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" maxlength="100" class="form-control maxlength-badge-position"
												placeholder="Ingredients" id="Ingredients"
												name="Ingredients" autocomplete="off"> <span
												class="validation-invalid-label" id="error_Ingredients"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prep_time" title="Prep Time in minutes">
											Preparation Time <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"  class="form-control numbersOnly maxlength-badge-position"
												placeholder="Preparation Time in Minutes" maxlength="3" id="prep_time" name="prep_time"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_prep_time"
												style="display: none;">This field is required.</span>
										</div>

									</div>


<div class="form-group row">
										<label class="col-form-label col-lg-2" for="is_veg">
											Veg/Non Veg <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Veg Non Veg" id="is_veg" name="is_veg">
											<option value="0">Veg</option>
												<option value="1">Non Veg</option>
											    <option value="2">Both</option></select> <span class="validation-invalid-label" id="error_is_veg"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2"  title="Product rate setting" for="rate_setting_type">
											Rate Setting Type <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Rate Setting Type" onchange="showWeightDiv()" id="rate_setting_type" name="rate_setting_type">
												
												<option value="0">Per UOM</option>
												<option value="1">Per Kg</option>
											    <option value="2">As of Filter</option>
												
											</select> <span class="validation-invalid-label" id="error_rate_setting_type"
												style="display: none;">This field is required.</span>
										</div>

										
									</div>
									
									<div class="col-lg-12" id="weight_div">
									<div class="form-group row">
									<label class="col-form-label col-lg-2" for="max_wt">
											Max Weights <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
										<input type="text" class="form-control numbersOnly maxlength-badge-position"
												placeholder="Maximum Weight Range" maxlength="3" id="max_wt" name="max_wt"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_max_wt"
												style="display: none;">This field is required.</span>
								
										</div>
										<div class="col-lg-1">
										<button type="button" onclick="show_apply_weight()"
													class="btn btn-primary"
													id="11" >Apply</button>
										</div>
									<label class="col-form-label col-lg-2" for="weight_ids">
											Available In Weights <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Weight" id="weight_ids"
												name="weight_ids" multiple>
											</select> <span class="validation-invalid-label" id="error_weight_ids"
												style="display: none;">This field is required.</span>
										</div>
									
									</div>
									</div>
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="primary_img">
											Primary Image: </label>
										<div class="col-lg-4">
											<div class="input-group-btn  ">
												<img id="output" width="150" src="${prodImgUrl}"/>
												<input type="file" class="btn btn-primary" accept="image/*" name="primary_img" id="primary_img" value="${asset.assetPurImage}" 
												accept=".jpg,.png,.gif,.jpeg,.bmp" onchange="loadFile(event)"><span
													class="form-text text-muted">Only
													.jpg,.png,.gif,.jpeg,.bmp</span>
											</div>


										</div>

										<div class="col-lg-4"></div>
									</div>

									<div class="form-group row mb-0">
	<div style="margin: 0 auto;">											<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
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
	$('.maxlength-badge-position').maxlength({
		alwaysShow : true,
		placement : 'top'
	});
	
	 var loadFile = function(event) {
		 document.getElementById('output').style.display="none";
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
if(parseInt(rateType)==2||parseInt(rateType)==1){
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
		weightHtml += '<option disabled value="'+p+'" selected>' + q
				+ '</option>';
				weightHtml += '</option>';
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
	var p = "";
	var q = "Select Events";
	
	var bread_idhtml;
	var p = "";
	var q = "Select Bread Type";
	bread_idhtml += '<option disabled value="'+p+'" selected>' + q
			+ '</option>';
	bread_idhtml += '</option>';
	
	var cream_idhtml;
	var p = "";
	var q = "Select Cream Type";
	cream_idhtml += '<option disabled value="'+p+'" selected>' + q
			+ '</option>';
	cream_idhtml += '</option>';
	
	var layering_cream_idhtml;
	var p = "";
	var q = "Select Layering Cream";
	layering_cream_idhtml += '<option disabled value="'+p+'" selected>' + q
			+ '</option>';
	layering_cream_idhtml += '</option>';
	
	var topping_cream_idhtml;
	var p = "";
	var q = "Select Topping Cream";
	topping_cream_idhtml += '<option disabled value="'+p+'" selected>' + q
			+ '</option>';
	topping_cream_idhtml += '</option>';
	
	var flav_idshtml;
	var p = "";
	var q = "Select Flavor";
	
	var shape_idhtml;
	var p = "";
	var q = "Select Product Shape";
	shape_idhtml += '<option disabled value="'+p+'" selected>' + q
			+ '</option>';
	shape_idhtml += '</option>';
	
	var sameDay_timeSlothtml;
	var p = "";
	var q = "Select Time Slot";
	
	
	var prod_type_idhtml;
	var p = "";
	var q = "Select Product Type";
	prod_type_idhtml += '<option disabled value="'+p+'" selected>' + q
			+ '</option>';
	prod_type_idhtml += '</option>';
	
	var appl_tagshtml;
	var p = "";
	var q = "Select Applicable Tags";
	
	data=${filterJSON};
	
	var len = data.length;
	for (var i = 0; i < len; i++) {
		if(1==parseInt(data[i].filterTypeId)){
			shape_idhtml += '<option value="' + data[i].filterId + '">'
				+ data[i].filterName + '</option>';
		}//end of if
		else if(2==parseInt(data[i].filterTypeId)){
			sameDay_timeSlothtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
		else if(3==parseInt(data[i].filterTypeId)){
			prod_type_idhtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
		else if(4==parseInt(data[i].filterTypeId)){
			flav_idshtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
		else if(5==parseInt(data[i].filterTypeId)){
			prod_statushtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
		else if(6==parseInt(data[i].filterTypeId)){
			event_idshtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
		else if(7==parseInt(data[i].filterTypeId)){
			appl_tagshtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
		else if(8==parseInt(data[i].filterTypeId)){
			bread_idhtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
	
		else if(9==parseInt(data[i].filterTypeId)){
			cream_idhtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
		else if(10==parseInt(data[i].filterTypeId)){
			layering_cream_idhtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
		else if(11==parseInt(data[i].filterTypeId)){
			topping_cream_idhtml += '<option value="' + data[i].filterId + '">'
			+ data[i].filterName + '</option>';
	}//end of if
	
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
	function setSubCatList(){
		var catId=document.getElementById("cat_id").value;
		
		var html;
		var p = "";
		var q = "Select Sub Category";
		html += '<option disabled value="'+p+'" selected>' + q
				+ '</option>';
		html += '</option>';

		var temp = 0;
		var data=${subCatListJSON};

		var len = data.length;
		for (var i = 0; i < len; i++) {
			if(parseInt(catId)==parseInt(data[i].catId)){
			html += '<option value="' + data[i].subCatId + '">'
					+ data[i].subCatName + '</option>';
			}//end of if
		}//end of for loop

		$('#sub_cat_id').html(html);
		$("#sub_cat_id").trigger("chosen:updated");
		document.getElementById("prod_code").value="";
	}//end of function  
	</script>
	<script>
		function checkAdd() {

			if (document.getElementById("checkSameAdd").checked == true) {

				document.getElementById("permntAdd").value = document
						.getElementById("tempAdd").value;

			} else {

				document.getElementById("permntAdd").value = "";
			}

		}
	</script>

	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}

		function validateEmail(email) {

			var eml = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

			if (eml.test($.trim(email)) == false) {

				return false;

			}

			return true;

		}
		function validateMobile(mobile) {
			var mob = /^[1-9]{1}[0-9]{9}$/;

			if (mob.test($.trim(mobile)) == false) {

				//alert("Please enter a valid email address .");
				return false;

			}
			return true;

		}

		function getSubCatPrefixData(){
			var fd = new FormData();
			subCatId=document.getElementById("sub_cat_id").value;
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
													
													

													if (!$("#min_qty").val()) { 
														$("#error_min_qty").show();
														isError = true;
													} else {
														$("#error_min_qty").hide();
													}	

													if (!$("#shelf_life").val()) { 
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
													if (!$("#return_per").val()) { 
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
												
												if (!$("#shape_id").val()) { 
													$("#error_shape_id").show();
													isError = true;
													} else {
														$("#error_shape_id").hide()
													}


												if (!$("#is_sameDay_del").val()) { 
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
												}

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

												if (!$("#book_b4").val()) { 
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

												if (!$("#prod_desc").val()) {
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
													}


												if (!$("#prep_time").val()) {
														 $("#error_prep_time").show();
														isError = true;
													} else {
														$("#error_prep_time").hide()
			 										}

												if (!$("#is_veg").val()) {
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
												//alert("Is Error "+isError)
												if (!isError) {
													//alert("! Is Error ")
													var x = true;
													if (x == true) {
														document
																.getElementById("submtbtn").disabled = true;
														return true;
													}
													//end ajax send this to php page
												}
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
			/* 
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#image1').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			} */
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

	<script type="text/javascript">
		function checkUnique(inputValue, valueType) {
			//alert("hi");

			document.getElementById("submtbtn").disabled = false;

			var valid = false;
			if (valueType == 1) {
				//alert("Its Mob no");
				if (inputValue.length == 10) {
					valid = true;
					//alert("Len 10")
				} else {
					//alert("Not 10");
				}
			} else if (valueType == 2) {
				//alert("Its Email " );

				var mailformat = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
				if (inputValue.match(mailformat)) {
					valid = true;
					//alert("Valid Email Id");
				} else {
					valid = false;
					//alert("InValid Email Id");
				}
			}
			if (valid == true) {
				$
						.getJSON(
								'${getUserInfo}',
								{
									inputValue : inputValue,
									valueType : valueType,
									ajax : 'true',

								},
								function(data) {

									if (valueType == 2) {

										if (data.empId == 0) {

											$("#error_email").hide();
											document
													.getElementById("emailExist").value = 0;

										} else {
											$("#error_email").show();
											/* document.getElementById("email").value = ""; */
											document
													.getElementById("emailExist").value = 1;
											document
													.getElementById("error_email").innerHTML = "This Email is already exist.";
										}

									} else {

										if (data.empId == 0) {

											$("#error_mobile1").hide();
											document
													.getElementById("mobile1Exist").value = 0;

										} else {
											$("#error_mobile1").show()
											/* document.getElementById("mobile1").value = ""; */
											document
													.getElementById("mobile1Exist").value = 1;
											document
													.getElementById("error_mobile1").innerHTML = "This Mobile No. is already exist.";
										}

									}

								});
				document.getElementById("uname").value = document
						.getElementById("email").value;
			}
		}
	</script>

	<!-- <script type="text/javascript">
	$('#submtbtn').on('click', function() {
        swalInit({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            buttonsStyling: false
        }).then(function(result) {
            if(result.value) {
                swalInit(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                );
            }
            else if(result.dismiss === swal.DismissReason.cancel) {
                swalInit(
                    'Cancelled',
                    'Your imaginary file is safe :)',
                    'error'
                );
            }
        });
    });
	
	</script> -->

</body>
</html>
<%-- <select name="locId2" id="locId2" data-placeholder="Select Location"
												
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" tabindex="-1" aria-hidden="true">

												<option value="">Select Location</option>

												<c:forEach items="${locationList}" var="locationList">
													<option value="${locationList.locId}">${locationList.locName}</option>
												</c:forEach>
											</select> --%>
