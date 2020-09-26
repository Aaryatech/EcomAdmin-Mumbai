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

/* .nav-tabs-highlight .nav-link.active:before {
	background-color: #f50000 !important;
}
 */
.ajax-table-td {
	padding: 7px;
	line-height: 0;
	border-top: 0px;
}
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs"
	onload="onLoadSetBillDisc();showDiv(${frequencyType});selectTab(${tab});showItemWiseOfferDiv(${offer.offerType});setItemWiseOfferDiv(${offer.offerType},${itemSubTypeOffer});getImageData(${offerId});">

	<!-- setItemWiseOfferDiv(${itemSubTypeOffer}) -->

	<c:url var="getBuyAndGetFreeOfferData"
		value="/getBuyAndGetFreeOfferData"></c:url>

	<c:url var="saveOfferDetailAjax" value="/saveOfferDetailAjax"></c:url>

	<c:url var="getItemListForOfferDetail"
		value="/getItemListForOfferDetail"></c:url>

	<c:url var="addBuyGetFreeItemToListAjax"
		value="/addBuyGetFreeItemToListAjax"></c:url>

	<c:url var="deleteBuyGetOfferBySecId" value="/deleteBuyGetOfferBySecId"></c:url>

	<c:url var="deleteBuyGetOfferByPrimaryId"
		value="/deleteBuyGetOfferByPrimaryId"></c:url>


	<c:url var="getItemImagesByDocIdAndDocType"
		value="/getItemImagesByDocIdAndDocType"></c:url>

	<c:url var="updateItemImageSequenceOrderAjax"
		value="/updateItemImageSequenceOrderAjax"></c:url>

	<c:url var="deleteOfferImageAjax" value="/deleteOfferImageAjax"></c:url>



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


						<!-- ##########  Tab ######### -->

						<div class="card">


							<div
								class="card-header bg-blue text-white d-flex justify-content-between">
								<c:choose>
									<c:when test="${offerId>0}">
										<span
											class="font-size-sm text-uppercase font-weight-semibold card-title">Offer</span>
									</c:when>
									<c:otherwise>
										<span
											class="font-size-sm text-uppercase font-weight-semibold card-title">Add
											New Offer</span>
									</c:otherwise>
								</c:choose>

								<!--  -->
								<span class="font-size-sm text-uppercase font-weight-semibold"><a
									href="${pageContext.request.contextPath}/showOfferList"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;Offer List</a></span>
							</div>

							<div class="card-body">
								<ul class="nav nav-tabs nav-tabs-highlight mb-0">
									<li class="nav-item"><a href="#bordered-tab1" id="tab1"
										name="tab1" class="nav-link active" data-toggle="tab">Basic
											Information</a></li>
									<li class="nav-item"><a href="#bordered-tab2" id="tab2"
										name="tab2" class="nav-link" data-toggle="tab">Offer
											Details</a></li>
									<li class="nav-item"><a href="#bordered-tab3" id="tab3"
										name="tab3" class="nav-link" data-toggle="tab">Offer
											Images</a></li>

								</ul>

								<div
									class="tab-content card card-body border border-top-0 rounded-top-0 shadow-0 mb-0">

									<!--------------------- TAB 1 ---------------------------->

									<div class="tab-pane fade show active" id="bordered-tab1">

										<form
											action="${pageContext.request.contextPath}/saveOfferHeader"
											id="submitForm1" method="post"
											onsubmit="return validation();">


											<p class="desc text-danger fontsize11">Note : * Fields
												are mandatory.</p>



											<input type="hidden" id="offerId" name="offerId"
												value="${offerId}">

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Offer Title <span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" id="offerTitle"
														name="offerTitle" value="${offer.offerName}">
												</div>

											</div>


											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Offer Description <span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10">
													<textarea rows="3" cols="3" class="form-control"
														placeholder="Description..." id="offerDesc"
														name="offerDesc">${offer.offerDesc}</textarea>
												</div>

											</div>


											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Type <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<select class="form-control select-search" data-fouc
														id="selectType" name="selectType">
														<option value="">Select Type</option>
														<option value="1"
															${1 == offer.type ? 'selected="selected"' : ''}>POS</option>
														<option value="2"
															${2 == offer.type ? 'selected="selected"' : ''}>Online</option>

													</select>
												</div>


												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name"
													style="line-height: normal; text-align: center;">Show
													Offer<span class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">

													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" id="day_radio" class="form-check-input"
															name="freq_type" checked value="1"
															onclick="showDiv(this.value)"
															${offer.frequencyType=='1'?'checked':''}> Day
														</label>
													</div>

													<div class="form-check form-check-inline">
														<label class="form-check-label"> <input
															type="radio" id="date_radio" class="form-check-input"
															name="freq_type" value="2" onclick="showDiv(this.value)"
															${offer.frequencyType=='2'?'checked':''}> Date
														</label>
													</div>
												</div>


												<%-- 		 
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"   
													name="freq_type" id="day_radio"  	onclick="showDiv(this.value)"
														${offer.frequencyType=='1'?'checked':''}>
													Day
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label "> <input
													type="radio" class="form-check-input" value="2"   
													name="freq_type" id="date_radio" 	onclick="showDiv(this.value)"
														${offer.frequencyType=='2'?'checked':''}>
													Date
												</label>
											</div>
										</div> --%>

												<!--  -->

											</div>


											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Offer By <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-10" id="day_div">
													<select class="form-control select" multiple="multiple"
														data-fouc data-placeholder="Select Day" id="selectDay"
														name="selectDay">
														<option value="1"
															${fn:contains(dayIds, 2) ? 'selected' : ''}>Monday</option>
														<option value="2"
															${fn:contains(dayIds, 3) ? 'selected' : ''}>Tuesday</option>
														<option value="3"
															${fn:contains(dayIds, 4) ? 'selected' : ''}>Wednesday</option>
														<option value="4"
															${fn:contains(dayIds, 5) ? 'selected' : ''}>Thursday</option>
														<option value="5"
															${fn:contains(dayIds, 6) ? 'selected' : ''}>Friday</option>
														<option value="6"
															${fn:contains(dayIds, 7) ? 'selected' : ''}>Saturday</option>
														<option value="7"
															${fn:contains(dayIds, 1) ? 'selected' : ''}>Sunday</option>
													</select>
												</div>


												<!-- <label class="col-form-label font-weight-bold col-lg-2"
												for="cust_name">Date <span class="text-danger">*
											</span>:
											</label> -->
												<div class="col-lg-2" id="date_div" style="display: none;">
													<input type="text" class="form-control daterange-basic_new"
														placeholder="Enter Offer Date" id="offerDate"
														name="offerDate" autocomplete="off">
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Time From<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-2">
													<input class="form-control" type="time" name="fromTime"
														id="fromTime" value="${offer.fromTime}">
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">To<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-2">
													<input class="form-control" type="time" name="toTime"
														id="toTime" value="${offer.toTime}">
												</div>

											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Applicable To<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10">
													<select class="form-control select" multiple="multiple"
														data-fouc data-placeholder="Select Options"
														id="selectApplicableTo" name="selectApplicableTo">
														<option value="1"
															${fn:contains(applicableIds, 1) ? 'selected' : ''}>Executive</option>
														<option value="2"
															${fn:contains(applicableIds, 2) ? 'selected' : ''}>Mobile</option>
														<option value="3"
															${fn:contains(applicableIds, 3) ? 'selected' : ''}>Web
															Site</option>
														<option value="4"
															${fn:contains(applicableIds, 4) ? 'selected' : ''}>WhatsApp</option>
													</select>
												</div>
											</div>




											<br>
											<div class="text-center">

												<c:choose>

													<c:when test="${offerId>0}">
														<button type="submit" class="btn btn-primary">
															Update <i class="icon-paperplane ml-2"></i>
														</button>
													</c:when>

													<c:otherwise>
														<button type="submit" class="btn btn-primary">
															Save <i class="icon-paperplane ml-2"></i>
														</button>
													</c:otherwise>

												</c:choose>


											</div>
										</form>

									</div>


									<!------------- TAB 1 END --------------->


									<!---------------TAB 2-----------------  -->

									<div class="tab-pane fade" id="bordered-tab2">


										<form
											action="${pageContext.request.contextPath}/saveOfferDetail"
											id="submitForm2" method="post"
											onsubmit="return validationTab2();">

											<c:choose>
												<c:when test="${offer.offerType==1}">
													<input type="hidden" id="billWiseDetailId"
														name="billWiseDetailId"
														value="${offerDetailList[0].offerDetailId}">
												</c:when>
												<c:otherwise>
													<input type="hidden" id="billWiseDetailId"
														name="billWiseDetailId" value="0">
												</c:otherwise>

											</c:choose>


											<!----------  -->
											<input type="hidden" id="tab2OfferId" name="tab2OfferId"
												value="${offerId}">


											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2">Type
													<span class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">


													<c:choose>

														<c:when test="${offer.offerType>0}">
															<div class="form-check form-check-inline">
																<label class="form-check-label"> <input
																	type="radio" id="billwise_radio"
																	class="form-check-input" name="offerTypeWise" checked
																	value="1" onclick="showItemWiseOfferDiv(this.value)"
																	${offer.offerType=='1'?'checked':''}
																	disabled="disabled"> Bill Wise
																</label>
															</div>

															<div class="form-check form-check-inline">
																<label class="form-check-label"> <input
																	type="radio" id="itemwise_radio"
																	class="form-check-input" name="offerTypeWise" value="2"
																	onclick="showItemWiseOfferDiv(this.value)"
																	${offer.offerType=='2'?'checked':''}
																	disabled="disabled"> Item Wise
																</label>
															</div>

															<input type="hidden" id="offerTypeWise"
																name="offerTypeWise" value="${offer.offerType}">

														</c:when>

														<c:otherwise>
															<div class="form-check form-check-inline">
																<label class="form-check-label"> <input
																	type="radio" id="billwise_radio"
																	class="form-check-input" name="offerTypeWise" checked
																	value="1" onclick="showItemWiseOfferDiv(this.value)"
																	${offer.offerType=='1'?'checked':''}> Bill Wise
																</label>
															</div>

															<div class="form-check form-check-inline">
																<label class="form-check-label"> <input
																	type="radio" id="itemwise_radio"
																	class="form-check-input" name="offerTypeWise" value="2"
																	onclick="showItemWiseOfferDiv(this.value)"
																	${offer.offerType=='2'?'checked':''}> Item Wise
																</label>
															</div>
														</c:otherwise>

													</c:choose>





												</div>

												<div id="itemWiseOfferDiv" class="col-lg-6"
													style="display: none;">

													<div class="form-group row">
														<label class="col-form-label font-weight-bold col-lg-4">Item
															Wise Offer<span class="text-danger">* </span>:
														</label>
														<div class="col-lg-8">

															<c:choose>

																<c:when test="${offer.offerType>0}">

																	<select class="form-control select-search" data-fouc
																		id="itemWiseOfferSelect" name="itemWiseOfferSelect"
																		disabled="disabled">
																		<option value="0"
																			${itemSubTypeOffer == '0' ? 'selected="selected"' : ''}>Select
																			Offer</option>
																		<option value="1"
																			${itemSubTypeOffer == '1' ? 'selected="selected"' : ''}>Discount</option>
																		<option value="2"
																			${itemSubTypeOffer == '2' ? 'selected="selected"' : ''}>Buy
																			1 get 1 free</option>
																	</select>

																	<input type="hidden" id="itemWiseOfferSelect"
																		name="itemWiseOfferSelect" value="${itemSubTypeOffer}">

																</c:when>

																<c:otherwise>

																	<select class="form-control select-search" data-fouc
																		id="itemWiseOfferSelect" name="itemWiseOfferSelect"
																		onchange="setItemWiseOfferDiv(0,this.value)">
																		<option value="0"
																			${itemSubTypeOffer == '0' ? 'selected="selected"' : ''}>Select
																			Offer</option>
																		<option value="1"
																			${itemSubTypeOffer == '1' ? 'selected="selected"' : ''}>Discount</option>
																		<option value="2"
																			${itemSubTypeOffer == '2' ? 'selected="selected"' : ''}>Buy
																			1 get 1 free</option>
																	</select>

																</c:otherwise>

															</c:choose>

														</div>
													</div>
												</div>



											</div>

											<div align="center" id="loader" style="display: none;">

												<span>
													<h4>
														<font color="#343690">Loading</font>
													</h4>
												</span> <span class="l-1"></span> <span class="l-2"></span> <span
													class="l-3"></span> <span class="l-4"></span> <span
													class="l-5"></span> <span class="l-6"></span>
											</div>

											<div class="form-group row" id="billDiscDiv"
												style="display: none;">
												<div class="form-group row">

													<label class="col-form-label font-weight-bold col-lg-2">Discount
														% <span class="text-danger">* </span>:
													</label>
													<div class="col-lg-2">

														<c:choose>

															<c:when test="${offer.offerType==1}">
																<input type="text" class="form-control"
																	style="width: 50%" id="billWiseDisc"
																	name="billWiseDisc"
																	value="${offerDetailList[0].discPer}">
															</c:when>

															<c:otherwise>
																<input type="text" class="form-control"
																	style="width: 50%" id="billWiseDisc"
																	name="billWiseDisc">
															</c:otherwise>

														</c:choose>



													</div>

													<label class="col-form-label font-weight-bold col-lg-2">Offer
														Limit (Rs.) <span class="text-danger">* </span>:
													</label>
													<div class="col-lg-2">

														<c:choose>

															<c:when test="${offer.offerType==1}">
																<input type="text" class="form-control"
																	style="width: 50%" id="billWiseLimit"
																	name="billWiseLimit"
																	value="${offerDetailList[0].offerLimit}">
															</c:when>

															<c:otherwise>
																<input type="text" class="form-control"
																	style="width: 50%" id="billWiseLimit"
																	name="billWiseLimit">
															</c:otherwise>

														</c:choose>


													</div>


													<label class="col-form-label font-weight-bold col-lg-2">No
														of Times Applicable <span class="text-danger">* </span>:
													</label>
													<div class="col-lg-2">

														<c:choose>

															<c:when test="${offer.offerType==1}">
																<input type="text" class="form-control"
																	style="width: 50%" id="noOfTimes" name="noOfTimes"
																	value="${offerDetailList[0].exInt1}">
															</c:when>

															<c:otherwise>
																<input type="text" class="form-control"
																	style="width: 50%" id="noOfTimes" name="noOfTimes">
															</c:otherwise>

														</c:choose>


													</div>


												</div>


												<div class="form-group row">

													<label class="col-form-label font-weight-bold col-lg-2">Coupon
														Code : </label>
													<div class="col-lg-10">

														<c:choose>

															<c:when test="${offer.offerType==1}">
																<input type="text" class="form-control"
																	id="billWiseCoupon" name="billWiseCoupon"
																	value="${offerDetailList[0].couponCode}">
															</c:when>

															<c:otherwise>
																<input type="text" class="form-control"
																	id="billWiseCoupon" name="billWiseCoupon">
															</c:otherwise>

														</c:choose>


													</div>

												</div>

											</div>

											<div class="form-group row" id="itemDiscDiv"
												style="display: none;">

												<table class="table " id="itemDiscTable">
													<thead>
														<tr>
															<th style="padding: .5rem 0.5rem">Check</th>
															<th style="padding: .5rem 0.5rem" colspan="2">Category
																Wise Items</th>
															<th style="padding: .5rem 0.5rem">Discount %</th>
														</tr>
													</thead>
													<tbody>


													</tbody>
												</table>

											</div>



											<div class="form-group row" id="itemBuyGetFreeDiv"
												style="display: none;">

												<div class="form-group row">

													<label class="col-form-label font-weight-bold col-lg-2">Primary
														Item<span class="text-danger">* </span>:
													</label>
													<div class="col-lg-4">

														<select class="form-control select-search" data-fouc
															id="primary" name="primary">
															<option value="0">Select Item</option>
															<c:forEach items="${itemList}" var="item">
																<option value="${item.itemId}">${item.itemName}</option>
															</c:forEach>

														</select>

													</div>


													<label class="col-form-label font-weight-bold col-lg-2">Applicable
														Offer<span class="text-danger">* </span>:
													</label>
													<div class="col-lg-4">

														<select class="form-control select-search" data-fouc
															id="pkApplicable" name="pkApplicable">
															<option value="1">1</option>
															<option value="2">2</option>
															<option value="3">3</option>
															<option value="4">4</option>
															<option value="5">5</option>


														</select>

													</div>
												</div>


												<div class="form-group row">

													<label class="col-form-label font-weight-bold col-lg-2">Secondary
														Item<span class="text-danger">* </span>:
													</label>
													<div class="col-lg-8">

														<select class="form-control select-search" data-fouc
															multiple="multiple" id="secondary" name="secondary">
															<option value="0">Select Item</option>
															<c:forEach items="${itemList}" var="item">
																<option value="${item.itemId}">${item.itemName}</option>
															</c:forEach>

														</select>

													</div>



													<div class="col-lg-2">

														<button type="button" class="btn btn-primary"
															onclick="addBuyGetFreeOffer()">
															Add <i class="icon-paperplane ml-2"></i>
														</button>

													</div>
												</div>


												<div class="form-group row">

													<table class="table table-hover" id="getForFreeTable">
														<thead>
															<tr>
																<th style="padding: .5rem 0.5rem">Sr</th>
																<th style="padding: .5rem 0.5rem" colspan="2">Items</th>
																<th style="padding: .5rem 0.5rem">Quantity</th>
																<th style="padding: .5rem 0.5rem">Action</th>


															</tr>
														</thead>
														<tbody>


														</tbody>
													</table>

												</div>



											</div>



											<div class="text-center">
												<input type="button" class="btn btn-danger" id="tab2Btn"
													onclick="saveTab2Data()" value="Save"
													style="display: none;"> </input>




												<c:choose>

													<c:when test="${offer.offerType>0}">
														<button type="submit" class="btn btn-primary">
															Update <i class="icon-paperplane ml-2"></i>
														</button>
													</c:when>

													<c:otherwise>
														<button type="submit" class="btn btn-primary">
															Save <i class="icon-paperplane ml-2"></i>
														</button>
													</c:otherwise>

												</c:choose>

											</div>

										</form>
									</div>
									<!-- TAB 2 End -->



									<!---------------TAB 3-----------------  -->

									<div class="tab-pane fade" id="bordered-tab3">

										<form action="#" id="submitForm3" method="post"
											enctype="multipart/form-data">

											<div class="form-group" id="imageDiv">

												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Images List : </label>

												<div class="row" style="text-align: center;"
													id="dispImageDiv"></div>

												<%-- <div class="text-center">
													<button type="button" class="btn btn-primary"
														onclick="updateSeqNo(${offerId})">Update Image
														Sequence</button>
												</div> --%>
												<br> <br>

												<div align="center" id="loader3" style="display: none;">

													<span>
														<h4>
															<font color="#343690">Loading</font>
														</h4>
													</span> <span class="l-1"></span> <span class="l-2"></span> <span
														class="l-3"></span> <span class="l-4"></span> <span
														class="l-5"></span> <span class="l-6"></span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Offer Image Upload <span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10">
													<input type="file" class="file-input-ajax1" name="files"
														id="files" multiple="multiple" style="background: red"
														data-fouc>
												</div>

											</div>



											<div class="text-center" style="display: none;">
												<button type="submit" class="btn btn-danger">
													Save <i class="icon-paperplane ml-2"></i>
												</button>
											</div>

										</form>

									</div>
									<!-- TAB 3 End -->

								</div>
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
		function onLoadSetBillDisc() {

			document.getElementById("billDiscDiv").style.display = "block";

		}
	</script>

	<script type="text/javascript">
	
	function selectTab(tab){
		
		//alert(tab);
		
		
		document.getElementById("tab"+tab).click(); 
		
			
	}
	
	</script>

	<script type="text/javascript">
	
		function showItemWiseOfferDiv(id) {
			
		 

			if (id == 1) {
				document.getElementById("itemWiseOfferDiv").style.display = "none";
				document.getElementById("billDiscDiv").style.display = "block";

				document.getElementById("itemDiscDiv").style.display = "none";
				document.getElementById("itemBuyGetFreeDiv").style.display = "none";

				$("#itemWiseOfferSelect").val("0").change();

			} else {
				document.getElementById("itemWiseOfferDiv").style.display = "block";
				document.getElementById("billDiscDiv").style.display = "none";

				document.getElementById("itemDiscDiv").style.display = "none";
				document.getElementById("itemBuyGetFreeDiv").style.display = "none";
				

			}
		}

		</script>

	<script type="text/javascript">
		function showDiv(id) {
			//alert(id);
			if (id == 1) {
				document.getElementById("day_div").style.display = "block";
				document.getElementById("date_div").style.display = "none";
			} else {
				document.getElementById("day_div").style.display = "none";
				document.getElementById("date_div").style.display = "block";
			}
		}

		function showOfferOn(id) {
			if (id == 1) {
				document.getElementById("disc_div").style.display = "block";
				document.getElementById("disc_item_div").style.display = "none";
			} else {
				document.getElementById("disc_div").style.display = "none";
				document.getElementById("disc_item_div").style.display = "block";
			}
		}

	

		

	
	</script>





	<script type="text/javascript">
		function setItemWiseOfferDiv(headerType,id) {
			//alert(id);
			
			if(headerType==1){
				document.getElementById("itemDiscDiv").style.display = "none";
				document.getElementById("itemBuyGetFreeDiv").style.display = "none";
				
				document.getElementById("itemWiseOfferDiv").style.display = "none";
				document.getElementById("billDiscDiv").style.display = "block";
			}else{
				
				if (id == 1) {
					document.getElementById("itemDiscDiv").style.display = "block";
					document.getElementById("itemBuyGetFreeDiv").style.display = "none";
					
					var offerId=document.getElementById("offerId").value;
					//alert(offerId);
					
					getItemListForDiscount(offerId);

					
				} else if (id == 2) {
					document.getElementById("itemDiscDiv").style.display = "none";
					document.getElementById("itemBuyGetFreeDiv").style.display = "block";

					var offerId=document.getElementById("offerId").value;
					//alert("Offer Id = "+offerId);
					getBuyAndGetFreeOfferData(offerId);
					
				} else {
					document.getElementById("itemDiscDiv").style.display = "none";
					document.getElementById("itemBuyGetFreeDiv").style.display = "none";
					
					document.getElementById("itemWiseOfferDiv").style.display = "none";
					document.getElementById("billDiscDiv").style.display = "block";
					
					
				}
				
			}
			

		}
	</script>












	<script type="text/javascript">
	 

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
		// Modal template
	var modalTemplate = '<div class="modal-dialog modal-lg" role="document">\n'
				+ '  <div class="modal-content">\n'
				+ '    <div class="modal-header align-items-center">\n'
				+ '      <h6 class="modal-title">{heading} <small><span class="kv-zoom-title"></span></small></h6>\n'
				+ '      <div class="kv-zoom-actions btn-group">{toggleheader}{fullscreen}{borderless}{close}</div>\n'
				+ '    </div>\n'
				+ '    <div class="modal-body">\n'
				+ '      <div class="floating-buttons btn-group"></div>\n'
				+ '      <div class="kv-zoom-body file-zoom-content"></div>\n'
				+ '{prev} {next}\n'
				+ '    </div>\n'
				+ '  </div>\n'
				+ '</div>\n';

		// Buttons inside zoom modal
		var previewZoomButtonClasses = {
			toggleheader : 'btn btn-light btn-icon btn-header-toggle btn-sm',
			fullscreen : 'btn btn-light btn-icon btn-sm',
			borderless : 'btn btn-light btn-icon btn-sm',
			close : 'btn btn-light btn-icon btn-sm'
		};

		// Icons inside zoom modal classes
		var previewZoomButtonIcons = {
			prev : '<i class="icon-arrow-left32"></i>',
			next : '<i class="icon-arrow-right32"></i>',
			toggleheader : '<i class="icon-menu-open"></i>',
			fullscreen : '<i class="icon-screen-full"></i>',
			borderless : '<i class="icon-alignment-unalign"></i>',
			close : '<i class="icon-cross2 font-size-base"></i>'
		};

		// File actions
		var fileActionSettings = {
			zoomClass : '',
			zoomIcon : '<i class="icon-zoomin3"></i>',
			dragClass : 'p-2',
			dragIcon : '<i class="icon-three-bars"></i>',
			removeClass : '',
			removeErrorClass : 'text-danger',
			removeIcon : '<i class="icon-bin"></i>',
			indicatorNew : '<i class="icon-file-plus text-success"></i>',
			indicatorSuccess : '<i class="icon-checkmark3 file-icon-large text-success"></i>',
			indicatorError : '<i class="icon-cross2 text-danger"></i>',
			indicatorLoading : '<i class="icon-spinner2 spinner text-muted"></i>'
		};
		 
		 var offerId=document.getElementById("offerId").value; 
		 console.log("offerId"+offerId);
		
		$('.file-input-ajax1')
		
				.fileinput(
						{
							browseLabel : 'Browse',
							uploadUrl : "http://localhost:8989/ecomadmin/ajaxImageUploadOffer/"+offerId, // server upload action
 							uploadAsync : false,
							maxFileCount : 100,
							initialPreview : [],
							browseIcon : '<i class="icon-file-plus mr-2"></i>',
							uploadIcon : '<i class="icon-file-upload2 mr-2"></i>',
							removeIcon : '<i class="icon-cross2 font-size-base mr-2"></i>',
							fileActionSettings : {
								removeIcon : '<i class="icon-bin"></i>',
								uploadIcon : '<i class="icon-upload"></i>',
								uploadClass : '',
								zoomIcon : '<i class="icon-zoomin3"></i>',
								zoomClass : '',
								indicatorNew : '<i class="icon-file-plus text-success"></i>',
								indicatorSuccess : '<i class="icon-checkmark3 file-icon-large text-success"></i>',
								indicatorError : '<i class="icon-cross2 text-danger"></i>',
								indicatorLoading : '<i class="icon-spinner2 spinner text-muted"></i>',
							},
							layoutTemplates : {
								icon : '<i class="icon-file-check"></i>',
								modal : modalTemplate
							},
							initialCaption : 'No file selected',
							previewZoomButtonClasses : previewZoomButtonClasses,
							previewZoomButtonIcons : previewZoomButtonIcons
						});
		
		
		
		//CATCH RESPONSE
		$('#files')
				.on(
						'filebatchuploaderror',
						function(event, data, previewId, index) {
							alert("Error!");
							var form = data.form, files = data.files, extra = data.extra, response = data.response, reader = data.reader;
						});

		$('#files')
				.on(
						'filebatchuploadsuccess',
						function(event, data, previewId, index) {
							var form = data.form, files = data.files, extra = data.extra, response = data.response, reader = data.reader;
							//alert(extra.bdInteli + " " + response.uploaded);
							getImageData(offerId);
							$('#files').fileinput('reset');
						});
		
	</script>


	<script type="text/javascript">
		function validation() {

			var title = document.getElementById("offerTitle").value;
			var desc = document.getElementById("offerDesc").value;
			var type = document.getElementById("selectType").value;
			var selectDay = document.getElementById("selectDay").value;
			var offerDate = document.getElementById("offerDate").value;
			var fromTime = document.getElementById("fromTime").value;
			var toTime = document.getElementById("toTime").value;
			var selectApplicableTo = document
					.getElementById("selectApplicableTo").value;

			var offerType = 0;

			if (document.getElementById('day_radio').checked) {
				offerType = document.getElementById("day_radio").value;
			} else {
				offerType = document.getElementById("date_radio").value;
			}

			//alert(toTime);

			if (title == '' || title == null) {
				alert("Please Enter Offer Title");
				return false;
			} else if (desc == '' || desc == null) {
				alert("Please Enter Offer Description");
				return false;
			} else if (type == 0) {
				alert("Please Select Type");
				return false;
			} else if (offerType == 1) {

				if (selectDay == '' || selectDay == null) {
					alert("Please Select Days");
					return false;
				}

			} else if (offerType == 2) {

				if (offerDate == '' || offerDate == null) {
					alert("Please Select Date");
					return false;
				}

			} else if (fromTime == '' || fromTime == null) {
				alert("Please Select From Time");
				return false;
			} else if (toTime == '' || toTime == null) {
				alert("Please Select To Time");
				return false;
			} else if (selectApplicableTo == "" || selectApplicableTo == null) {
				alert("Please Select Offer Applicable To Options");
				return false;
			} else {
				return true;
			}
		}
	</script>


	<script type="text/javascript">
		function validationTab2() {
			
			var offerId=document.getElementById("offerId").value;

			var offerType = 0;

			if (document.getElementById('billwise_radio').checked) {
				offerType = document.getElementById("billwise_radio").value;
			} else {
				offerType = document.getElementById("itemwise_radio").value;
			}
			
			var disc = document.getElementById("billWiseDisc").value;
			var limit = document.getElementById("billWiseLimit").value;
			var coupon = document.getElementById("billWiseCoupon").value;
			
			
			if(offerType==1){
				
				if(offerId==0){
					alert("Please Enter Basic Information First");
					document.getElementById("tab"+1).click(); 
					return false;
				} else if(disc=='' || disc == null){
					alert("Please Enter Discount %");
					document.getElementById("billWiseDisc").focus();
					return false;
				} else if(limit=='' || limit == null){
					alert("Please Enter Offer Limit");
					document.getElementById("billWiseLimit").focus();
					return false;
				} else if(coupon=='' || coupon == null){
					alert("Please Enter Coupon");
					document.getElementById("billWiseCoupon").focus();
					return false;
				} else{
					return true;
				}
				
			}
			
			var select = document.getElementById("itemWiseOfferSelect").value;
			
			if(offerType==2){
				
				if(offerId==0){
					alert("Please Enter Basic Information First");
					document.getElementById("tab"+1).click(); 
					return false;
				} else if(select==0){
					alert("Please Select Item Wise Offer Type");
					document.getElementById("itemWiseOfferSelect").focus();
					return false;
				}else{
					
					if(select==1){
						
						var flag=0;
						$(".commonChk").each(function(counter) {
									
							if(document.getElementsByClassName("commonChk")[counter].checked == true){
								flag=1;
							}

						});
						
						if(flag==0){
							alert("Please Select Item To Submit");
							return false;
						}
						
					}else if(select==2){
						
						var tableSize = $('#getForFreeTable tbody tr').length;
						//alert(tableSize);
						
						if(tableSize==0){
							alert("Please Enter Offer Details");
							return false;
						}
						
					}
					
				
				}
				
			}
			
			
		}
	</script>


	<script type="text/javascript">

		function saveTab2Data() {
	
			var offerType = 0;
			var saveType=0;
			
			var offerId=document.getElementById("offerId").value;

			if (document.getElementById('billwise_radio').checked) {
				offerType = document.getElementById("billwise_radio").value;
			} else {
				offerType = document.getElementById("itemwise_radio").value;
			}
			
			var disc=document.getElementById("billWiseDisc").value;
			var limit=document.getElementById("billWiseLimit").value;
			var coupon=document.getElementById("billWiseCoupon").value;
			
			var offerSelectDropDown=document.getElementById("itemWiseOfferSelect").value;
			
			if(offerType==1){
				
				if(disc == ''){
					alert("Please Enter Discount %");
					document.getElementById("billWiseDisc").focus();
				}else if(limit == ''){
					alert("Please Enter Offer Limit");
					document.getElementById("billWiseLimit").focus();
				}else if(coupon == ''){
					alert("Please Enter Coupon Codes");
					document.getElementById("billWiseCoupon").focus();
				}else{
					
					saveType=1;
				}
				
			}else if(offerType==2){
				
				if(offerSelectDropDown == 0 ){
					alert("Please Select Offer Type");
					document.getElementById("itemWiseOfferSelect").focus();
				}else{
					saveType=2;
				}
				
			}
			
			
			if(saveType==1){
				
				$('#loader').show();
				
				$.getJSON('${saveOfferDetailAjax}',
						{
							offerId : offerId,
							type : offerType,
							disc : disc,
							limit : limit,
							coupon : coupon,
							ajax : 'true'
						},
						function(data) {

							$('#loader').hide();

							alert(JSON.stringify(data));
							
							if(data.error==false){
								document.getElementById("tab2Btn").value="Update";
							}else{
								document.getElementById("tab2Btn").value="Save";
							}

						});
			} else if(saveType==2){
				
				//$('#loader').show();
				
			}
			
			
		}
	
	</script>

	<script type="text/javascript">
	
		function getItemListForDiscount(offerId){
			
			$('#loader').show();
			
			//document.getElementById("itemDiscDiv").style.display = "block";
		
			$.getJSON('${getItemListForOfferDetail}',
					{
						offerId : offerId,
						ajax : 'true'
					},
					function(data) {

						$('#loader').hide();

						//alert(JSON.stringify(data));
						//alert(data.catList);
						
						$('#itemDiscTable td').remove();
						
						
						$.each(data.catList,function(key, category) {

									var tr = $('<tr style="background:#03a9f4;"></tr>');
									
									var catCheckbox="<input type=checkbox value='"+category.catId+"' name='catCheck"+category.catId+"' id='catCheck"+category.catId+"' onclick=selAllItems(this.value) >"
									
									tr.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(catCheckbox));
									
									tr.append($('<td  style="padding: 7px; line-height:0; color:#fff; border-top:0px; font-weight:700;" colspan=2></td>').html(category.catName));
									
									
									var input="<input type=text class='form-control qty' style='width:20%;  line-height:0; height:0' "
										+ " placeholder=0 id='catDisc"
										+ category.catId
										+ "' name='catDisc"+category.catId+"' oninput=insertCommonDisc('"+category.catId+"',this.value)>";
									
									tr.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(input));

									$('#itemDiscTable tbody').append(tr);

									
									$.each(data.itemList,function(key, item) {
										
										if(category.catId==item.catId){
										
											var tr1 = $('<tr></tr>');
										
											tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(""));
											
											var itemCheckbox="";
										
											if(item.checked==1){
												itemCheckbox="<input checked type=checkbox class='itemChk"+item.catId+" commonChk' value='"+item.itemId+"' name='itemCheck"+item.itemId+"' id='itemCheck"+item.itemId+"'>";
											}else{
												itemCheckbox="<input type=checkbox class='itemChk"+item.catId+" commonChk' value='"+item.itemId+"' name='itemCheck"+item.itemId+"' id='itemCheck"+item.itemId+"'>";
											}
											
											tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(itemCheckbox));
											
											tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(item.itemName));
										
											var input="<input type=text class='form-control onluNumber itemDiscClass"+item.catId+"' style='width:20%;  line-height:0; height:0' "
												+ " placeholder=0 id='itemDisc#"
												+ item.itemId
												+ "#"
												+item.catId+"' name='itemDisc#"+item.itemId+"#"+item.catId+"' value='"+item.disc+"'>";
										
											tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(input));
										
											var hiddenChkInput="<input type=text value="+item.checked+" class='hiddenChk"+item.catId+"'>"
											tr1.append($('<td style="display:none;"></td>').html(hiddenChkInput));
											
											var hiddenOldDiscInput="<input type=text value="+item.disc+" class='oldDisc"+item.catId+"'>"
											tr1.append($('<td style="display:none;"></td>').html(hiddenOldDiscInput));
											
											$('#itemDiscTable tbody').append(tr1);
										
										}
									});
									
									
									
						});

					//	alert("hi");

					});
		
			
		}
	
	</script>

	<script type="text/javascript">
	
	function selAllItems(id) {
		//alert(id);

		var checkBox = document.getElementById("catCheck" + id);
		//alert(checkBox.checked);

		 if (checkBox.checked == true) {

			document.getElementById("catDisc" + id).readOnly = false;

			$(".itemChk" + id)
					.each(
							function(counter) {
								
								document.getElementsByClassName("itemChk" + id)[counter].checked = true;

								/* document
										.getElementsByClassName("itemDiscClass" + id)[counter].readOnly = false; */

							});

		}  else {

			//document.getElementById("catDisc" + id).readOnly = true;

			document.getElementById("catDisc" + id).value = "";

			$(".itemChk" + id)
					.each(
							function(counter) {
								
								var hiddenChk=document.getElementsByClassName("hiddenChk" + id)[counter].value;
								var oldDisc=document.getElementsByClassName("oldDisc" + id)[counter].value;
								//alert(hiddenChk)

								if(hiddenChk==1){
									document.getElementsByClassName("itemChk" + id)[counter].checked = true;
									
									document
									.getElementsByClassName("itemDiscClass" + id)[counter].value = oldDisc;
									
								}else{
									document.getElementsByClassName("itemChk" + id)[counter].checked = false;
									
									document
									.getElementsByClassName("itemDiscClass" + id)[counter].value = "";
								}
								
								

								/* document
										.getElementsByClassName("itemDiscClass" + id)[counter].readOnly = true; */

								

							});

		}
 
	}
	
	
	function insertCommonDisc(id, value) {

		//var disc = document.getElementById("allChk" + id);

		$(".itemDiscClass" + id).each(function(counter) {
							
			if(value==0 || value == '' || value == null){
				var oldDisc=document.getElementsByClassName("oldDisc" + id)[counter].value;
				document.getElementsByClassName("itemDiscClass" + id)[counter].value = oldDisc;
			}else{
				document.getElementsByClassName("itemDiscClass" + id)[counter].value = value;
			}

		});
	}
	
	</script>


	<script type="text/javascript">
		function addBuyGetFreeOffer() {

			var primaryIndex = document.getElementById("primary").selectedIndex;
			var primaryName = document.getElementById("primary").options[primaryIndex].text;
			var primaryId = document.getElementById("primary").value;
			//alert(primaryId);

			var secIds = $('#secondary').val();
			//alert(JSON.stringify(secIds));

			var pQty = document.getElementById("pkApplicable").value;

			if (parseInt(primaryId) == 0) {
				alert("Please Select Primary Item For Offer");
			} else if (secIds == null || secIds == '' || secIds == 0) {
				alert("Please Select Secondary Item For Offer");
			} else {

				$('#getForFreeTable td').remove();
				
				$('#loader').show();

				$.getJSON('${addBuyGetFreeItemToListAjax}',
					{
						primaryId : primaryId,
						secIds : JSON.stringify(secIds),
						pQty : pQty,
						ajax : 'true'
					},function(data) {
						
						$('#loader').hide();

						//alert(JSON.stringify(data));

						setBuyGetFreeTableData(data);

						$("#primary").val("0").change();
						$("#secondary").val("-1").change();
						$("#pkApplicable").val("1").change();

					});
				
			}

		}
	</script>

	<script type="text/javascript">
	
		function getBuyAndGetFreeOfferData(offerId){
			
			$('#loader').show();
			
			$.getJSON('${getBuyAndGetFreeOfferData}',
					{
						offerId : offerId,
						ajax : 'true'
					},
					function(data) {

						$('#loader').hide();
						//alert(JSON.stringify(data));
						//alert(data.catList);
					
						setBuyGetFreeTableData(data);
					});
		
			 
		}
	
	</script>


	<script type="text/javascript">
	
		function setBuyGetFreeTableData(data){
		
			$('#getForFreeTable td').remove();
			
		
			$.each(data.idList,function(key, primary) {

					
					var tr = $('<tr style="background:#cbff92;"></tr>');
					
					tr.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(key+1));

					var pItemName="";
					$.each(data.itemList,function(key, item) {
					
						if(primary==item.primaryItemId){
							pItemName=item.primaryItemName;
						}
					
					});
					tr.append($('<td  style="padding: 7px; line-height:0; border-top:0px; font-weight:700;" colspan=2></td>').html(pItemName));
					
					
					/* var input="<input type=text class='form-control qty' style='width:20%;  line-height:0; height:0' "
						+ " placeholder=0 id='qty"
						+ primary.primaryItemId
						+ "' name='qty"+primary.primaryItemId+"' >";
					
					tr.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(input)); */
					
					var select="";
					
					
					var pQty="";
					$.each(data.itemList,function(key, item) {
					
						if(primary==item.primaryItemId){
							pQty=item.primaryQty;
						}
					
					});
					
					if(pQty==1){
						select="<select style='width:20%; opacity:1; border-bottom:0px;' name='selectPQty"+primary+"' class='form-control' data-fouc><option value=1 selected>1</option><option value=2>2</option><option value=3>3</option><option value=4>4</option><option value=5>5</option></select>";
					}else if(pQty==2){
						select="<select style='width:20%; opacity:1; border-bottom:0px;' name='selectPQty"+primary+"' class='form-control' data-fouc><option value=1 >1</option><option value=2 selected >2</option><option value=3>3</option><option value=4>4</option><option value=5>5</option></select>";
					}else if(pQty==3){
						select="<select style='width:20%; opacity:1; border-bottom:0px;' name='selectPQty"+primary+"' class='form-control' data-fouc><option value=1 >1</option><option value=2  >2</option><option value=3 selected>3</option><option value=4>4</option><option value=5>5</option></select>";
					}else if(pQty==4){
						select="<select style='width:20%; opacity:1; border-bottom:0px;' name='selectPQty"+primary+"' class='form-control' data-fouc><option value=1 >1</option><option value=2  >2</option><option value=3 >3</option><option value=4 selected>4</option><option value=5>5</option></select>";
					}else if(pQty==5){
						select="<select style='width:20%; opacity:1; border-bottom:0px;' name='selectPQty"+primary+"' class='form-control' data-fouc><option value=1 >1</option><option value=2  >2</option><option value=3 >3</option><option value=4 >4</option><option value=5 selected>5</option></select>";
					}
					
					
					tr.append($('<td style="padding: 0px; line-height:0; border-top:0px;"></td>').html(select));
					
					var del="<a href=# title=Delete onclick=deleteBuyGetOfferByPrimaryId('"+primary+"')><i class=icon-cross2 style='color:#f44336;'></i></a>"

					tr.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(del));

				
					$('#getForFreeTable tbody').append(tr);
					
					
					$.each(data.itemList,function(key, item) {
					
						if(primary==item.primaryItemId){
							
							var tr1 = $('<tr></tr>');
							
							tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(" "));
							
							tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(" - "));
							
							tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(item.secondaryItemName));
							
							var input="<input type=text class='form-control qty' style='width:20%;  line-height:0; height:0' "
							+ " placeholder=0 id='qty#"
							+ item.primaryItemId
							+ "#"+item.secondaryItemId+"' name='qty#"+item.primaryItemId+"#"+item.secondaryItemId+"' value='"+item.secondaryQty+"'>";
						
							tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(input)); 
							
							
							var del="<a href=# title=Delete onclick=deleteBuyGetOfferBySecId('"+item.primaryItemId+"','"+item.secondaryItemId+"') ><i class=icon-cross2 style='color:#f44336;'></i></a>"
							
							tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(del));

							$('#getForFreeTable tbody').append(tr1);
						}
						
					});
					
			});
		
		}
	
	</script>

	<script type="text/javascript">
	
		function deleteBuyGetOfferBySecId(pId,sId){
			
			$('#loader').show();
		
			$.getJSON('${deleteBuyGetOfferBySecId}',
					{
						pId : pId,
						sId : sId,
						ajax : 'true'
					},
					function(data) {
						

						$('#loader').hide();
						//alert(JSON.stringify(data));
						//alert(data.catList);
					
						setBuyGetFreeTableData(data);
					});
			
		}
		
		
		function deleteBuyGetOfferByPrimaryId(pId){
			
			$('#loader').show();
		
			
			$.getJSON('${deleteBuyGetOfferByPrimaryId}',
					{
						pId : pId,
						ajax : 'true'
					},
					function(data) {

						$('#loader').hide();
						//alert(JSON.stringify(data));
						//alert(data.catList);
					
						setBuyGetFreeTableData(data);
					});
			
		}
		
	</script>


	<!--  Images -->

	<script type="text/javascript">
		function getImageData(offerId) {
			
		 

			//var type = 4;

			$('#loader3').show();

			$
					.getJSON(
							'${getItemImagesByDocIdAndDocType}',
							{
							 
								selectId : offerId,
								ajax : 'true'

							},
							function(data) {

								//alert(JSON.stringify(data));

								$('#loader3').hide();

								if (data == "") {
									document.getElementById("dispImageDiv").innerHTML = "";
									document.getElementById("imageDiv").style.display = "none";
								} else {
									document.getElementById("imageDiv").style.display = "block";
								}

								var img = "";

								$
										.each(
												data,
												function(key, image) {
													
													
												//	alert(image);

													var divCard = "<div class=card style='width: 12%; height: 8%; margin: 10px;'>";

													var divCardImg = "<div class='card-img-actions mx-1 mt-1'>"
															+ "<img class='card-img img-fluid'"+
						"src='${imageUrl}"+image+"' alt=''>"
															+ "<a target='_blank'  href='${imageUrl}"+image+"'><div class='card-img-actions-overlay card-img'></div></a></div>";

													var divCardImg2 = "<div class=card-body style='padding: 5px;'>"
															+ "<div class='d-flex align-items-start flex-nowrap'><div style='text-align: center;'><input style='display:none;' type=text   value='"+image+"' class=imgId> "
															+ "</div><div class='list-icons list-icons-extended ml-auto' style='text-align: center;'>"
															+ "<a href=# onclick=deleteImage('"
															+ image
															+ "') class='list-icons-item'><i class='icon-bin top-0'></i></a></div></div></div></div>";
												 
																
													img = img + divCard + ""
															+ divCardImg + ""
															+ divCardImg2;
												});

								document.getElementById("dispImageDiv").innerHTML = img;

							});

		}
	</script>


	<!-- <script type="text/javascript">
		function updateSeqNo(offerId) {
			
			
			alert("updateSeqNo");

			$('#loader3').show();

			var imgList = [];

			$(".imgId")
					.each(
							function(counter) {
								
							 
								var id = document
										.getElementsByClassName("imgId")[counter].value;
								
								
								
							 
								var seq = document
										.getElementsByClassName("seq")[counter].value;
								
 
								var img = {
									id : id,
									seq : seq
								}

								imgList.push(img);

							});

			//alert(JSON.stringify(imgList));

			$.getJSON('${updateItemImageSequenceOrderAjax}', {
				json : JSON.stringify(imgList),
				ajax : 'true'
			}, function(data) {

				$('#loader3').hide();

				//alert(JSON.stringify(data));

				if (data.error) {
					alert(data.message);
				} else {
					getImageData(offerId);
				}

			});

		}
	</script>
 -->

	<script type="text/javascript">
		function deleteImage(name) {

		//	 alert("hii"+name);
			
			var offerId=document.getElementById("offerId").value;

			$('#loader3').show();

			$.getJSON('${deleteOfferImageAjax}', {
				offerId : offerId,
				imageName : name,
				ajax : 'true'
			}, function(data) {

				$('#loader3').hide();

				//alert(JSON.stringify(data));

				if (data.error) {
					alert(data.message);
				} else {
					getImageData(offerId);
				}

			});

		}
	</script>


</body>
</html>