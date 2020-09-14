<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<c:url var="getSubmoduleList" value="/getSubmoduleList" />
<c:url value="/getUserInfo" var="getUserInfo"></c:url>

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
							<div class="card-header header-elements-inline">
								<table width="100%">
									<tr width="100%">
										<td width="60%"><h5 class="card-title">Add Product</h5></td>
										<td width="40%" align="right">
											<%--  <a
									href="${pageContext.request.contextPath}/showEmpList"
									class="breadcrumb-elements-item">
										<button type="button" class="btn btn-primary">Employee List </button>
								</a> --%>
										</td>
									</tr>
								</table>
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
									action="${pageContext.request.contextPath}/submitInsertEmployeeUserInfo"
									id="submitInsertEmp" method="post"
									enctype="multipart/form-data">
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="cat_id">
											Select Category <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Category" id="cat_id" name="cat_id">
												
												<c:forEach items="${catList}" var="cat">
												<c:when test="${1==cat.catId}">
												<option selected value="${cat.catId}"> ${cat.catName}</option>
												</c:when>
												<c:otherwise>
											<option value="${cat.catId}"> ${cat.catName}</option>
												</c:otherwise>
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
												placeholder="Sub Category" id="sub_cat_id" name="sub_cat_id">
											</select> <span class="validation-invalid-label" id="error_sub_cat_id"
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
											<input type="text" class="form-control  "
												placeholder="Product Name" id="prod_name" name="prod_name"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_prod_name"
												style="display: none;">This field is required.</span>
										</div>


										<label class="col-form-label col-lg-2" for="short_name">
											Short Name <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control  "
												placeholder="Product Short Name" id="short_name"
												name="short_name" autocomplete="off"> <span
												class="validation-invalid-label" id="error_prod_name"
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
											</select> <span class="validation-invalid-label" id="error_tax_id"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="sort_no">
											Sort No <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control numbersOnly"
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
											<input type="text" class="form-control numbersOnly"
												placeholder="Minimum Order Quantity" id="min_qty"
												name="min_qty" autocomplete="off"> <span
												class="validation-invalid-label" id="error_min_qty"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="shelf_life">
											Shelf Life <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control numbersOnly"
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
												<option selected value="0">No</option>
											</select> <span class="validation-invalid-label"
												id="error_is_return_allow" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="return_per">
											Return % <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control numbersOnly"
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
												<option selected value="1">KG</option>
												<option selected value="0">Liter</option>
											</select> <span class="validation-invalid-label"
												id="error_is_return_allow" style="display: none;">This
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
												<option selected value="0">No</option>
											</select> <span class="validation-invalid-label"
												id="error_is_return_allow" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="sameDay_timeSlot">
											Same Day TimeSlot <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Same Day Time Slot"
												id="sameDay_timeSlot" name="sameDay_timeSlot">
											</select> <span class="validation-invalid-label" id="error_shape_id"
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
												<option selected value="1">P1</option>
												<option selected value="0">P2</option>
											</select> <span class="validation-invalid-label"
												id="error_prod_type_id" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="weight_ids">
											Available In Weights <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Select Weight" id="weight_ids"
												name="weight_ids">
											</select> <span class="validation-invalid-label" id="error_weight_ids"
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
												data-fouc="" aria-hidden="true" data
												placeholder="Select Flavors" id="flav_ids" name="flav_ids">
												<option selected value="1">P1</option>
												<option selected value="0">P2</option>
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
											<input type="text" class="form-control numbersOnly"
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
												data-fouc="" aria-hidden="true" data
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
											</select> <span class="validation-invalid-label"
												id="error_char_limit_yn" style="display: none;">This
												field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="no_of_char">
											No of Alphabets <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control numbersOnly"
												placeholder="No of Characters" id="no_of_char"
												name="no_of_char" autocomplete="off"> <span
												class="validation-invalid-label" id="error_no_of_char"
												style="display: none;">This field is required.</span>
										</div>


									</div>
									<div class="form-group row">
										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_cover_ph"
												value="1" name="is_cover_ph" autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_base_ph"
												value="1" name="is_base_ph" autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_sp_inst"
												value="1" name="is_sp_inst" autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control"
												id="is_msg_on_cake" value="1" name="is_msg_on_cake"
												autocomplete="off">
										</div>

										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_slot_used"
												value="1" name="is_slot_used" autocomplete="off">
										</div>
										<div class="col-lg-2">
											<input type="checkbox" class="form-control" id="is_used"
												value="1" name="is_used" autocomplete="off">
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
											<input type="text" class="form-control numbersOnly"
												placeholder="No of Message Characters" id="no_of_msg_char"
												name="no_of_msg_char" autocomplete="off"> <span
												class="validation-invalid-label" id="error_no_of_char"
												style="display: none;">This field is required.</span>
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
												<option selected value="1">B1</option>
												<option selected value="0">B2</option>
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
											<input type="text" class="form-control"
												placeholder="Applicable Tags" id="appl_tags"
												name="appl_tags" autocomplete="off"> <span
												class="validation-invalid-label" id="error_appl_tags"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prod_desc">
											Product Desc <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
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
											<input type="text" class="form-control"
												placeholder="Ingredients" id="Ingredients"
												name="Ingredients" autocomplete="off"> <span
												class="validation-invalid-label" id="error_Ingredients"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="copy_item">
											Copy Item <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												placeholder="Copy Item" id="copy_item" name="copy_item"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_copy_item"
												style="display: none;">This field is required.</span>
										</div>

									</div>


<div class="form-group row">
										<label class="col-form-label col-lg-2" for="is_veg">
											Veg/Non Veg <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
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
										<div class="col-lg-2">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data
												placeholder="Rate Setting Type" id="rate_setting_type" name="rate_setting_type">
												
												<option value="0">Per UOM</option>
												<option value="1">Per Kg</option>
											    <option value="2">As of Filter</option>
												
											</select> <span class="validation-invalid-label" id="error_rate_setting_type"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label col-lg-2" for="prep_time" title="Prep Time in minutes">
											Preparation Time <span style="color: red">* </span>:
										</label>
										<div class="col-lg-2">
											<input type="text" class="form-control numbersOnly"
												placeholder="Preparation Time in Minutes" maxlength="" id="prep_time" name="prep_time"
												autocomplete="off"> <span
												class="validation-invalid-label" id="error_prep_time"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="profilePic">
											Primary Image: </label>
										<div class="col-lg-4">
											<div class="input-group-btn  ">

												<span class="filename" style="user-select: none1;"><img
													id="temppreviewimageki1" name="image1"
													class="temppreviewimageki1" alt="l"
													style="width: 200px; height: auto; display: none"> </span>
												<!-- image-preview-clear button -->
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
									</div>






									<div class="form-group row mb-0">
										<div class="col-lg-10 ml-lg-auto">
											<!-- <button type="reset" class="btn btn-light legitRipple">Reset</button> -->
											<button type="submit" class="btn bg-blue ml-3 legitRipple"
												id="submtbtn">
												Submit <i class="icon-paperplane ml-2"></i>
											</button>
											<a href="${pageContext.request.contextPath}/showEmployeeList"><button
													type="button" class="btn btn-primary">
													<i class="${sessionScope.cancelIcon}" aria-hidden="true"></i>&nbsp;&nbsp;
													Cancel
												</button></a> <input type="hidden" id="mobile1Exist" name="mobile1Exist"><input
												type="hidden" id="emailExist" name="emailExist">
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
		function setDate(value) {
			///alert("Value " +value)
			if (value == 1) {
				//alert(value)
				//document.getElementById("relDate").removeAttribute("required");
				document.getElementById("abc").style.display = "none";
				document.getElementById("xyz").style.display = "none";

				//alert(value)
			} else {
				//alert(value)
				//document.getElementById("relDate").setAttribute("required","true");
				document.getElementById("abc").style.display = "block";
				document.getElementById("xyz").style.display = "block";

				//alert(value)

			}

		}
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

		$(document)
				.ready(
						function($) {

							$("#submitInsertEmp")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#empCode").val()) {

													isError = true;

													$("#error_empCode").show()
													//return false;
												} else {
													$("#error_empCode").hide()
												}

												if (!$("#fname").val()) {

													isError = true;

													$("#error_fname").show()

												} else {
													$("#error_fname").hide()
												}

												if (!$("#mname").val()) {

													isError = true;

													$("#error_mname").show()

												} else {
													$("#error_mname").hide()
												}

												if (!$("#sname").val()) {

													isError = true;

													$("#error_sname").show()

												} else {
													$("#error_sname").hide()
												}

												if ($("#locId2").val() == "") {

													isError = true;

													$("#error_locId2").show()

												} else {
													$("#error_locId2").hide()
												}

												if (!$("#locId").val()) {

													isError = true;

													$("#error_locId").show()

												} else {
													$("#error_locId").hide()
												}

												if (!$("#catId").val()) {

													isError = true;

													$("#error_catId").show()

												} else {
													$("#error_catId").hide()
												}
												if (!$("#typeId").val()) {

													isError = true;

													$("#error_typeId").show()

												} else {
													$("#error_typeId").hide()
												}
												if (!$("#deptId").val()) {

													isError = true;

													$("#error_deptId").show()

												} else {
													$("#error_deptId").hide()
												}
												if (!$("#permntAdd").val()) {

													isError = true;

													$("#error_permntAdd")
															.show()

												} else {
													$("#error_permntAdd")
															.hide()
												}

												if (!$("#bloodGrp").val()) {

													isError = true;

													$("#error_bloodGrp").show()

												} else {
													$("#error_bloodGrp").hide()
												}

												if (!$("#tempAdd").val()) {

													isError = true;

													$("#error_tempAdd").show()

												} else {
													$("#error_tempAdd").hide()
												}

												if (!$("#emgContPrsn1").val()) {

													isError = true;

													$("#error_emgContPrsn1")
															.show()

												} else {
													$("#error_emgContPrsn1")
															.hide()
												}

												/* if (!$("#emgContPrsn2").val()) {

													isError = true;

													$("#error_emgContPrsn2")
															.show()

												} else {
													$("#error_emgContPrsn2")
															.hide()
												} */

												if (!$("#ratePerHr").val()) {

													isError = true;

													$("#error_ratePerHr")
															.show()

												} else {
													$("#error_ratePerHr")
															.hide()
												}

												if (!$("#prevsExpYr").val()) {

													isError = true;

													$("#error_prevsExpYr")
															.show()

												} else {
													$("#error_prevsExpYr")
															.hide()
												}

												if (!$("#prevsExpMn").val()) {

													isError = true;

													$("#error_prevsExpMn")
															.show()

												} else {
													$("#error_prevsExpMn")
															.hide()
												}

												if (!$("#mobile1").val()
														|| !validateMobile($(
																"#mobile1")
																.val())) {

													isError = true;

													if (!$("#mobile1").val()) {
														document
																.getElementById("error_mobile1").innerHTML = "This field is required.";
													} else {
														document
																.getElementById("error_mobile1").innerHTML = "Enter valid Mobile No.";
													}

													$("#error_mobile1").show()

												} else {

													if ($("#mobile1Exist")
															.val() == 1) {

														$("#error_mobile1")
																.show()
													} else {
														$("#error_mobile1")
																.hide()
													}

												}

												if (!$("#emgContNo1").val()
														|| !validateMobile($(
																"#emgContNo1")
																.val())) {

													isError = true;

													if (!$("#emgContNo1").val()) {
														document
																.getElementById("error_emgContNo1").innerHTML = "This field is required.";
													} else {
														document
																.getElementById("error_emgContNo1").innerHTML = "Enter valid Mobile No.";
													}

													$("#error_emgContNo1")
															.show()

												} else {
													$("#error_emgContNo1")
															.hide()
												}

												if ($("#emgContNo2").val() != ""
														&& !validateMobile($(
																"#emgContNo2")
																.val())) {
													isError = true;
													document
															.getElementById("error_emgContNo2").innerHTML = "Enter valid Mobile No.";
													$("#error_emgContNo2")
															.show()
												} else {
													$("#error_emgContNo2")
															.hide()
												}
												
										//for alternate contact no.
										
										
											if ($("#mobile2").val() != ""
														&& !validateMobile($(
																"#mobile2")
																.val())) {
													isError = true;
													document
															.getElementById("error_emgContNo2_alt").innerHTML = "Enter valid Mobile No.";
													$("#error_emgContNo2_alt")
															.show()
												} else {
													$("#error_emgContNo2_alt")
															.hide()
												}
													
												 
												if (!$("#email").val()
														|| !validateEmail($(
																"#email").val())) {

													isError = true;

													if (!$("#email").val()) {
														document
																.getElementById("error_email").innerHTML = "This field is required.";
													} else {
														document
																.getElementById("error_email").innerHTML = "Enter valid email.";
													}

													$("#error_email").show()

												} else {

													if ($("#emailExist").val() == 1) {

														$("#error_email")
																.show()
													} else {
														$("#error_email")
																.hide()
													}

												}

												if (!$("#uname").val()) {

													isError = true;

													$("#error_uname").show()

												} else {
													$("#error_uname").hide()
												}
												if (!$("#upass").val()) {

													isError = true;

													$("#error_upass").show()

												} else {
													$("#error_upass").hide()
												}
												if (!isError) {

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
