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
<body class="sidebar-xs" onload="init();">

	<c:url value="/validateUnqFrMobNo" var="validateUnqFrMobNo"/>
	<c:url value="/getFrInfoByEmail" var="getFrInfoByEmail"/>	
	
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
							
								<%-- <span class="font-size-sm text-uppercase font-weight-semibold"><a
									class="card-title"
									href="${pageContext.request.contextPath}/showFranchises"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;Franchise
										List</a></span> --%>
							</div>


							<div class="card-body">		

								<ul class="nav nav-tabs nav-tabs-highlight mb-0">
									<li class="nav-item"><a href="#bordered-tab1" id="tab1"
										name="tab1" class="nav-link active" data-toggle="tab">Companies
											Information</a></li>
									<c:if test="${cus.pageHead ne null}">
										<li class="nav-item"><a href="#bordered-tab2" id="tab2"
											name="tab2" class="nav-link" data-toggle="tab">Social
												Links</a></li>
										<li class="nav-item"><a href="#bordered-tab3" id="tab3"
											name="tab3" class="nav-link" data-toggle="tab">Footer 
												Details</a></li>
									</c:if>
								</ul>

								<div class="form-group row"></div>
								<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>


								<div
									class="tab-content card card-body border border-top-0 rounded-top-0 shadow-0 mb-0">
									<!--------------------- TAB 1 ---------------------------->
									<div class="tab-pane fade show active" id="bordered-tab1">

										<form
											action="${pageContext.request.contextPath}/addContactCompDtl"
											id="submitInsert" method="post" enctype="multipart/form-data">
											<p class="desc text-danger fontsize11">Note : * Fields
												are mandatory.</p>
											
											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="pageHead">Page Heading<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" name="pageHead"
														id="pageHead" value="${cus.pageHead}">
												</div><span
														class="validation-invalid-label text-danger"
														id="error_pageHead" style="display: none;">This field
														is required.</span>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="subHeading">Sub Heading<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="subHeading" id="subHeading" maxlength="80"
														autocomplete="off" onchange="trim(this)"
														value="${cus.subHeading}"> <span
														class="validation-invalid-label text-danger"
														id="error_subHeading" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="phoneText">Phone Text<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="phoneText" id="phoneText" maxlength="50"
														autocomplete="off" onchange="trim(this)"
														value="${cus.phoneText}"> <span
														class="validation-invalid-label text-danger"
														id="error_phoneText" style="display: none;">This
														field is required.</span>
												</div>
											</div>
											
											<div class="form-group row">

												<label class="col-form-label font-weight-bold col-lg-2"
													for="phone1">Phone No.1 <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position" name="phone1"
														id="phone1" maxlength="10" autocomplete="off"
														onchange="trim(this)" value="${cus.phone1}">
													<span class="validation-invalid-label text-danger"
														id="error_phone1" style="display: none;">This field
														is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="phone2">Phone No.2 <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position" name="phone2"
														id="phone2" maxlength="10" autocomplete="off"
														onchange="trim(this)" value="${cus.phone2}">
													<span class="validation-invalid-label text-danger"
														id="error_phone2" style="display: none;">This field
														is required.</span>
												</div>
											</div>
											
											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="emailText">Email Text<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="emailText" id="emailText" maxlength="50"
														autocomplete="off" onchange="trim(this)"
														value="${cus.emailText}"> <span
														class="validation-invalid-label text-danger"
														id="error_emailText" style="display: none;">This
														field is required.</span>
												</div>
											</div>


											<div class="form-group row">

												<label class="col-form-label font-weight-bold col-lg-2"
													for="email1">Email 1<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="email" placeholder="@gmail.com"
														class="form-control maxlength-badge-position" name="email1"
														id="email1" maxlength="80" autocomplete="off"
														onchange="trim(this)" value="${cus.email1}">
													<span class="validation-invalid-label text-danger"
														id="error_email1" style="display: none;">This field
														is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="email2">Email 2<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="email" placeholder="@gmail.com"
														class="form-control maxlength-badge-position" name="email2"
														id="email2" maxlength="80" autocomplete="off"
														onchange="trim(this)" value="${cus.email2}">
													<span class="validation-invalid-label text-danger"
														id="error_email2" style="display: none;">This field
														is required.</span>
												</div>
											</div>


											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="officeText">Corporate Office Text<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="officeText" id="officeText" maxlength="50"
														autocomplete="off" onchange="trim(this)"
														value="${cus.officeText}"> <span
														class="validation-invalid-label text-danger"
														id="error_officeText" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="officeAddress">Office Address<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-10">
													<textarea rows="1"  name="officeAddress"
														id="officeAddress" class="form-control" onchange="trim(this)">${cus.officeAddress}</textarea>
													<span class="validation-invalid-label text-danger"
														id="error_officeAddress" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="manufacAddressTxt">Manufacturer Address Text<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="manufacAddressTxt" id="manufacAddressTxt" maxlength="50"
														autocomplete="off" onchange="trim(this)"
														value="${cus.manufacAddressTxt}"> <span
														class="validation-invalid-label text-danger"
														id="error_manufacAddressTxt" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="manufacAddress">Manufacturer Address<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-10">
													<textarea rows="1"  name="manufacAddress"
														id="manufacAddress" class="form-control" onchange="trim(this)">${cus.manufacAddress}</textarea>
													<span class="validation-invalid-label text-danger"
														id="error_manufacAddress" style="display: none;">This
														field is required.</span>
												</div>
											</div>
											<input type="hidden" id="btnType" name="btnType">
											<br>
											<div class="text-center">
												<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										
										<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn(1)">
											Save & Next<i class="icon-paperplane ml-2"></i>
										</button>
											</div>
										</form>

									</div>


									<!---------------------End TAB 1 ---------------------------->

									<!---------------------- TAB 2---------------------------->
									<div class="tab-pane fade" id="bordered-tab2">
										<form
											action="${pageContext.request.contextPath}/insertSocialLinks"
											  id="submitSocialLinks" method="post">

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="facebookLink">Face Book Link<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-10">
													<input type="text"
														class="form-control maxlength-badge-position" name="facebookLink"
														id="facebookLink" maxlength="80" autocomplete="off"
														onchange="trim(this)" value="${cus.facebookLink}">
													<span class="validation-invalid-label text-danger"
														id="error_facebookLink" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="twitterLink">Twitter Link<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-10">
													<input type="text"
														class="form-control maxlength-badge-position" name="twitterLink"
														id="twitterLink" maxlength="80" autocomplete="off"
														onchange="trim(this)" value="${cus.twitterLink}">
													<span class="validation-invalid-label text-danger"
														id="error_twitterLink" style="display: none;">This field
														is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="linkedInLink">Linked In Link<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-10">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="linkedInLink" id="linkedInLink" maxlength="80"
														autocomplete="off" onchange="trim(this)"
														value="${cus.linkedInLink}"> <span
														class="validation-invalid-label text-danger"
														id="error_linkedInLink" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="googleAcLink">Google Account<span
													class="text-danger"> </span>:
												</label>
												<div class="col-lg-10">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="googleAcLink" id="googleAcLink" maxlength="100"
														autocomplete="off" onchange="trim(this)"
														value="${cus.googleAcLink}"> <span
														class="validation-invalid-label text-danger"
														id="error_googleAcLink" style="display: none;">This
														field is required.</span>
												</div>
											</div>
											<input type="hidden" id="btnType2" name="btnType">
											<br>
											<div class="text-center">
												<button type="submit" class="btn btn-primary" id="submtbtn" onclick="pressBtn2(0)">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
										
										<button type="submit" class="btn btn-primary" id="submtbtn1" onclick="pressBtn2(1)">
											Save & Next<i class="icon-paperplane ml-2"></i>
										</button>
											</div>
										</form>
									</div>
									<!----------------------End TAB 2---------------------------->
									<!----------------------TAB 3---------------------------->
									<div class="tab-pane fade" id="bordered-tab3">
										<form
											action="${pageContext.request.contextPath}/submitFooterDtl"
											id="submitFooterDtl" method="post" enctype="multipart/form-data">
											
											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="footerAddress">Fotter Address<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10">
													<textarea rows="1"  name="footerAddress"
														id="footerAddress" class="form-control" onchange="trim(this)">${cus.footerAddress}</textarea>
													 <span
														class="validation-invalid-label text-danger"
														id="error_footerAddress" style="display: none;">This
														field is required.</span>
												</div>

												
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="footerPhone1">Footer Phone1<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="footerPhone1" id="footerPhone1" maxlength="10"
														autocomplete="off" onchange="trim(this)"
														value="${cus.footerPhone1}"> <span
														class="validation-invalid-label text-danger"
														id="error_footerPhone1" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="footerPhone2">Footer Phone2<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text"
														class="form-control maxlength-badge-position"
														name="footerPhone2" id="footerPhone2" maxlength="10"
														autocomplete="off" onchange="trim(this)"
														value="${cus.footerPhone2}"> <span
														class="validation-invalid-label text-danger"
														id="error_footerPhone2" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="footerEmail1">Footer Email1<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="email"
														class="form-control maxlength-badge-position"
														name="footerEmail1" id="footerEmail1" maxlength="100"
														autocomplete="off" onchange="trim(this)"
														value="${cus.footerEmail1}"> <span
														class="validation-invalid-label text-danger"
														id="error_footerEmail1" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="footerEmail2">Footer Email2<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="email"
														class="form-control maxlength-badge-position"
														name="footerEmail2" id="footerEmail2" maxlength="100"
														autocomplete="off" onchange="trim(this)"
														value="${cus.footerEmail2}"> <span
														class="validation-invalid-label text-danger"
														id="error_footerEmail2" style="display: none;">This
														field is required.</span>
												</div>
											</div>
											
											<div class="form-group row">
										<label class="col-form-label col-lg-2" for="primary_img">
											Primary Image: </label>
										<div class="col-lg-4">
											<div class="input-group-btn  ">
												<img id="output" width="150" src="${imgPath}${cus.footerImage}"/>
												<%-- <input type="file" class="btn btn-primary" accept="image/*"  name="doc" id="doc" value="${cus.footerImage}" 
												accept=".jpg,.png,.gif,.jpeg,.bmp" onchange="loadFile(event)"> --%>
												
												<input
												type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc" accept="image/*" accept=".jpg,.png,.jpeg"><span
													class="form-text text-muted">Only
													.jpg,.png,.gif,.jpeg,.bmp</span>
												<input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${cus.footerImage}">
											</div>
										</div>										
										</div>
											<br>
											<div class="text-center">
												<button type="submit" class="btn btn-primary"
													id="submtbankbtn">
													Save <i class="icon-paperplane ml-2"></i>
												</button>
											</div>
										</form>
									</div>
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
			$("#btnType").val(btnVal);
			
		}
		function pressBtn2(btnVal){
			$("#btnType2").val(btnVal);
		}
	</script>

	<script type="text/javascript">
		//Basic Info Form
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				
					if (!$("#pageHead").val()) {
						isError = true;
						$("#error_pageHead").show()
						$("#pageHead").focus();
					} else {
						isError = false;
						$("#error_pageHead").hide()
					}
				
				
				if (!$("#subHeading").val()) {
					isError = true;
					$("#error_subHeading").show()
					$("#subHeading").focus();
				} else {
					$("#error_subHeading").hide()
				}
				
				if (!$("#phoneText").val()) {
					isError = true;
					$("#error_phoneText").show()
					$("#phoneText").focus();
				} else {
					$("#error_phoneText").hide()
				}
				
				if (!$("#phone1").val()) {
					isError = true;
					$("#error_phone1").show()
					$("#phone1").focus();
				} else {
					$("#error_phone1").hide()
				}

				if (!$("#phone2").val()) {
					isError = true;
					$("#error_phone2").show()
					$("#phone2").focus();
				} else {
					$("#error_phone2").hide()
				}

				if (!$("#emailText").val()) {
					isError = true;
					$("#error_emailText").show()
					$("#emailText").focus();
				} else {
					$("#error_emailText").hide()
				}

				if (!$("#email1").val()) {
					isError = true;
					$("#error_email1").show()
					$("#email1").focus();
				} else {
					$("#error_email1").hide()
				}

				if (!$("#email2").val()) {
					isError = true;
					$("#error_email2").show()
					$("#email2").focus();
				} else {
					$("#error_email2").hide()
				}
				
				if (!$("#officeText").val()) {
					isError = true;
					$("#error_officeText").show()
					$("#officeText").focus();
				} else {
					$("#error_officeText").hide()
				}
				
				if (!$("#officeAddress").val()) {
					isError = true;
					$("#error_officeAddress").show()
					$("#officeAddress").focus();
				} else {
					$("#error_officeAddress").hide()
				}
				
				if (!$("#manufacAddressTxt").val()) {
					isError = true;
					$("#error_manufacAddressTxt").show()
					$("#manufacAddressTxt").focus();
				} else {
					$("#error_manufacAddressTxt").hide()
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

		//FDA & GST Info Form
		$(document)
				.ready(
						function($) {
							$("#submitSocialLinks")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#facebookLink").val()) {
													isError = true;
													$("#error_facebookLink").show()
													$("#facebookLink").focus();
												} else {
													$("#error_facebookLink").hide()
												}

												if (!$("#twitterLink").val()) {
													isError = true;
													$("#error_twitterLink")
															.show()
													$("#twitterLink").focus();
												} else {
													$("#error_twitterLink")
															.hide()
												}

												if (!$("#linkedInLink").val()) {
													isError = true;
													$("#error_linkedInLink").show()
													$("#linkedInLink").focus();
												} else {
													$("#error_linkedInLink").hide()
												}
												
												if (!$("#googleAcLink").val()) {
													isError = true;
													$("#error_googleAcLink").show()
													$("#googleAcLink").focus();
												} else {
													$("#error_googleAcLink").hide()
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
																				.getElementById("submitSocialLinks")
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

		//Bank Detail Form
		$(document)
				.ready(
						function($) {
							$("#submitFooterDtl")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												if (!$("#footerAddress").val()) {
													isError = true;
													$("#error_footerAddress")
															.show()
													$("#footerAddress").focus();
												} else {
													$("#error_footerAddress")
															.hide()
												}

												if (!$("#footerPhone1").val()) {
													isError = true;
													$("#error_footerPhone1")
															.show()
													$("#footerPhone1").focus();
												} else {
													$("#error_footerPhone1")
															.hide()
												}

												if (!$("#footerPhone2").val()) {
													isError = true;
													$("#error_footerPhone2").show()
													$("#footerPhone2").focus();
												} else {
													$("#error_footerPhone2").hide()
												}

												if (!$("#footerEmail1").val()) {
													isError = true;
													$("#error_footerEmail1").show()
													$("#footerEmail1").focus();
												} else {
													$("#error_footerEmail1").hide()
												}

												if (!$("#footerEmail2").val()) {
													isError = true;
													$("#error_footerEmail2")
															.show()
													$("#footerEmail2").focus();
												} else {
													$("#error_footerEmail2")
															.hide()
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
																				.getElementById("submitFooterDtl")
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
				return false;
			}
			return true;
		}

		$('.non-char').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9]/g, '').replace(
							/(\..*)\./g, '$1');
				});

		$('.non-char-float').on(
				'input',
				function() {
					this.value = this.value.replace(/[^0-9.]/g, '').replace(
							/(\..*)\./g, '$1');
				});

		/* Auto Resize Text Area for address */
		var observe;
		if (window.attachEvent) {
			observe = function(element, event, handler) {
				element.attachEvent('on' + event, handler);
			};
		} else {
			observe = function(element, event, handler) {
				element.addEventListener(event, handler, false);
			};
		}
		function init() {
			var text = document.getElementById('address');
			function resize() {
				text.style.height = 'auto';
				text.style.height = text.scrollHeight + 'px';
			}
			/* 0-timeout to get the already changed text */
			function delayedResize() {
				window.setTimeout(resize, 0);
			}
			observe(text, 'change', resize);
			observe(text, 'cut', delayedResize);
			observe(text, 'paste', delayedResize);
			observe(text, 'drop', delayedResize);
			observe(text, 'keydown', delayedResize);

			text.focus();
			text.select();
			resize();
		}
	</script>
	<script>
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
	</script>
<script>
$("#mobNo").change(function() { 
	
	var mobNo = $("#mobNo").val();
	var frId = $("#frId").val();
	

	$.getJSON('${validateUnqFrMobNo}', {
		mobNo : mobNo,
		frId : frId,
		ajax : 'true',
	}, function(data) {

		if (data.error == false) {
			$("#unq_mob").show();
			$("#mobNo").val('');
			document.getElementById("mobNo").focus();
		} else {
			$("#unq_mob").hide();
		}
	});
});

$("#email").change(function() { 
	var email = $("#email").val();
	var frId = $("#frId").val();
	//alert(code)

	$.getJSON('${getFrInfoByEmail}', {
		email : email,
		frId : frId,
		ajax : 'true',
	}, function(data) {

		if (data.error == false) {
			$("#unq_email").show();
			$("#email").val('');
			document.getElementById("email").focus();
		} else {
			$("#unq_email").hide();
		}
	});
});

</script>

</body>
</html>