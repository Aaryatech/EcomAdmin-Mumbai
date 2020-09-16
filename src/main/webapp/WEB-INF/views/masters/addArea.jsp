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
<c:url value="/getAreaInfoByCode" var="getAreaInfoByCode"></c:url>
<c:url value="/getCityBycityId" var="getCityBycityId"></c:url>

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
									href="${pageContext.request.contextPath}/showArea"
									style="color: white;" class="card-title"><i
										class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;Area List</a></span>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertArea"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control" name="area_id" id="area_id" value="${area.areaId}">

									<div class="form-group row">
									<label class="col-form-label font-weight-bold col-lg-2"
											for="city_name">City Name<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc name="city_name" id="city_name">
												<option value="">Select City</option>
												
												<c:forEach items="${cityList}" var="cityList" varStatus="count">	
													<c:choose>
													 <c:when test="${cityList.cityId==area.cityId}">								
														<option selected value="${cityList.cityId}">${cityList.cityName}</option>																								
													</c:when>
													<c:otherwise>
														<option value="${cityList.cityId}">${cityList.cityName}</option>		
													</c:otherwise>
													</c:choose>		
												</c:forEach>
											</select> 
											<input type="hidden" id="city" name="city" value="${area.cityId}">										
										<span
												class="validation-invalid-label text-danger" id="error_city_name"
												style="display: none;">This field is required.</span>
										</div>
									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="area_code">Area Code<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="area_code" style="text-transform: uppercase;"
												id="area_code" maxlength="7" autocomplete="off" onchange="trim(this)" value="${area.areaCode}"> 
										<input type="hidden" id="code" name="code" value="${area.areaCode}">
										<span
												class="validation-invalid-label text-danger" id="error_area_code"
												style="display: none;">This field is required.</span>
												
											<span
												class="validation-invalid-label text-danger" id="unq_area_code"
												style="display: none;">Area Code Already Exist.</span>
										</div>									
									</div>									
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="city_name">Area Name<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="area_name"
												id="area_name" maxlength="20" autocomplete="off" onchange="trim(this)" value="${area.areaName}"> 
										
										<span
												class="validation-invalid-label text-danger" id="error_area_name"
												style="display: none;">This field is required.</span>
										</div>
										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="area_decp">Pin Code<span
											class="text-danger"></span>:
										</label>	
										<div class="col-lg-4">
										<input type="text" class="form-control maxlength-badge-position" name="area_pincode"
												id="area_pincode" maxlength="6" autocomplete="off" onchange="trim(this)" value="${area.pincode}"> 
											<span
												class="validation-invalid-label text-danger" id="error_area_pincode"
												style="display: none;">This field is required.</span>										
										</div>	

									</div>
									
									<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
											for="latitude">Latitude<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="latitude"
												id="latitude" maxlength="20" autocomplete="off" onchange="trim(this)" value="${area.latitude}">		
												<span
												class="validation-invalid-label text-danger" id="error_latitude"
												style="display: none;">This field is required.</span>								
										</div>	
										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="longitude">Longitude<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="longitude"
												id="longitude" maxlength="20" autocomplete="off" onchange="trim(this)" value="${area.longitude}"> 
											<span
												class="validation-invalid-label text-danger" id="error_longitude"
												style="display: none;">This field is required.</span>	
										</div>	
									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="1"
													name="area" id="area_y"
													${area.isActive==1 ? 'checked' : ''}> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="area"
													id="area_n"
													${area.isActive==0 ? 'checked' : ''}> In-Active
												</label>
											</div>
										</div>
										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="area_decp">Area Description<span
											class="text-danger"></span>:
										</label>	
										<div class="col-lg-4">
										<textarea class="form-control maxlength-badge-position"
													placeholder="Enter Area Description" id="area_decp"
													 name="area_decp" autocomplete="off" maxlength="100"
													onchange="trim(this)">${area.description}</textarea>											
										</div>	
									</div>
									<input type="hidden" name="is_edit"
												id="is_edit" value="${isEdit}"> 
									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary" id="submtbtn">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
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
	</script>

	<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#area_code").val()) {
					isError = true;
					$("#error_area_code").show()
				} else {
					$("#error_area_code").hide()
				}

				if (!$("#area_name").val()) {
					isError = true;
					$("#error_area_name").show()
				} else {
					$("#error_area_name").hide()
				}	
				
				if (!$("#area_pincode").val()) {
					isError = true;
					$("#error_area_pincode").show()
				} else {
					$("#error_area_pincode").hide()
				}	
				
				if (!$("#city_name").val()) {
					isError = true;
					$("#error_city_name").show()
				} else {
					$("#error_city_name").hide()
				}	
				
				if (!$("#latitude").val()) {
					isError = true;
					$("#error_latitude").show()
				} else {
					$("#error_latitude").hide()
				}	
				
				if (!$("#longitude").val()) {
					isError = true;
					$("#error_longitude").show()
				} else {
					$("#error_longitude").hide()
				}	
				
				

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
		
		$("#city_name").on('change', function(){  
			var cityId = $("#city_name").val();
			
			var isEdit = $("#is_edit").val();
			var city = $("#city").val();
			var code = $("#code").val();
			
		//alert(cityId)
		if(isEdit==1 && cityId==city){
			$("#area_code").val(code)
		}else{
		
				 $.getJSON('${getCityBycityId}', {
					 cityId : cityId,
					 ajax : 'true',
				}, function(data) {
				//	alert(JSON.stringify(data));
					$("#area_code").val(data.cityCode)
				});
			}
		});
		$("#area_code").change(function(){   // 1st
			var code = $("#area_code").val();								
			var areaId = $("#area_id").val();	
		//alert(code)
		
			 $.getJSON('${getAreaInfoByCode}', {
				code : code,
				areaId : areaId,
				ajax : 'true',
			}, function(data) {
				
				if(data.error==false){								
					$("#unq_area_code").show();
					$("#area_code").val('');
					document.getElementById("area_code").focus();
				}else{
					$("#unq_area_code").hide();
				}
			});
		});
		
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