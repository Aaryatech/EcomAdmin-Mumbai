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
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">
	<c:url value="/getConfigFrList" var="getConfigFrList"></c:url>

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
							href="${pageContext.request.contextPath}/showOfferConfigurationList"
							style="color: white;"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View List</a></span>
							</div>
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

							<div class="card-body">
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>
								<form
									action="${pageContext.request.contextPath}/saveOfferConfiguration"
									id="submitInsert" method="post">

								<input type="hidden" value="${offer.offerConfigId}" id="offerConfigId" name="offerConfigId">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="tags">Offers <span class="text-danger">* </span>:
										</label>
										<div class="col-lg-3">
											<select class="form-control select-search" data-fouc
												name="offers" id="offers">
												<option value="0">Select Offer</option>
												<c:forEach items="${offerList}" var="offerList"
													varStatus="count">
													<c:choose>
														<c:when test="${offerList.offerId==editOfferId}">
															<option selected value="${offerList.offerId}">${offerList.offerName}</option>
														</c:when>
														<c:otherwise>
															<option value="${offerList.offerId}">${offerList.offerName}</option>
														</c:otherwise>
													</c:choose>
													
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_offers"
												style="display: none;">This field is required.</span>
										</div>
										 <div class="col-lg-2" style="text-align: right;">
											<!-- <button type="button" class="btn btn-danger"
												onclick="getFrConfigList()">
												Search <i class="icon-paperplane ml-2"></i>
											</button> -->
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

									<!--Table-->
									<table class="table ddatatable-header-basic" id="item_tag_table">
										<thead>
											<tr>
												<th style="padding: .5rem 0.5rem"><input type="checkbox" id="allChk1" name="allChk1"
													onclick="selAllItems()"></th>
												<th style="padding: .5rem 0.5rem">Franchise</th>

											</tr>
										</thead>
										<tbody>
										

										</tbody>
									</table>
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Item Check Box.</span>
									<div class="text-center">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
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

	<script>
		
	$("#offers").on('change', function(){    // 2nd (A)
		var offerId = $("#offers").val();
	//	alert("Offer----"+offerId)
			if (offerId > 0) {
				$("#error_tags").hide()
				
				$('#item_tag_table td').remove();
				$('#loader').show();
				$
						.getJSON(
								'${getConfigFrList}',
								{
									offerId : offerId,
									ajax : 'true'

								},
								function(data) {
									$('#loader').hide();
									$
									.each(
											data,
											function(key, value) {

												var tr1 = $('<tr style="background:#cbff92;"></tr>');
												
												if(value.checked==1){
												tr1
												.append($(
														'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;"></td>')
														.html(
																'<input type="checkbox" checked class="chk" name="frIds" id="allChk'
																+ value.frId
																+ '" value="'
																+ value.frId
																+ '">'));
												}else{
													tr1
													.append($(
															'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;"></td>')
															.html(
																	'<input type="checkbox" class="chk" name="frIds" id="allChk'
																			+ value.frId
																			+ '" value="'
																			+ value.frId
																			+ '">'));
												}
												
												tr1
												.append($(
														'<td style="display:none;"></td>')
														.html(
																'<input type="text" class="chkVal" value="'+value.checked+'">'));
												
												tr1
														.append($(
																'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;" colspan=2"></td>')
																.html(
																		value.frName));
													$(
														'#item_tag_table tbody')
														.append(
																tr1);
											});
								});
			} else {
				$("#error_tags").show()
			}
	});

	
function selAllItems() {
	
	var checkBox = document.getElementById("allChk1");
	//alert(checkBox.checked);

	if (checkBox.checked == true) {

		$(".chk")
				.each(
						function(counter) {

							document.getElementsByClassName("chk")[counter].checked = true;

						});

	} else {

		$(".chk")
				.each(
						function(counter) {

							if (document
									.getElementsByClassName("chkVal")[counter].value == 0) {
								document.getElementsByClassName("chk")[counter].checked = false;
								//document.getElementsByClassName("chkVal" + id)[counter].checked = false;

							}
						});

	}

}

	</script>
	<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if ($("#offers").val() == 0) {
					isError = true;
					$("#error_offers").show()
				} else {
					$("#error_offers").hide()
				}

				/* if($(".chk").is(":checked")==false){
					isError = true;
					$("#error_chks").show()
				} else {
					$("#error_chks").hide()
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

</body>
</html>