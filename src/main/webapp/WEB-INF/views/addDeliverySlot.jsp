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
									href="${pageContext.request.contextPath}/showDelSlotList"
									style="color: white;"><i class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View
										Delivery Slot List</a></span>
							</div>



							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/submitAddDelSlot"
									id="submitAddDelSlot" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>


									<input type="hidden" class="form-control" name="deliSlotId"
										id="deliSlotId" value="${delSlot.deliSlotId}">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="delslotName">Delivery Slot Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-10">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="100"
												autocomplete="off" onchange="trim(this)" name="delslotName"
												id="delslotName" value="${delSlot.deliverySlotName}" placeholder="Enter Delivery Slot Name"> <span
												class="validation-invalid-label text-danger"
												id="error_group_name" style="display: none;">This field
												is required.</span>
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="fromTime">From Time<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control pickatime"  
												autocomplete="off" onchange="trim(this)" name="fromTime"
												id="fromTime" value="${delSlot.fromTime}"> <span
												class="validation-invalid-label text-danger"
												id="error_min_km" style="display: none;">This field
												is required.</span>	
												<span
												class="validation-invalid-label text-danger"
												id="from_time_error" style="display: none;">To Time Cant Be Less Than From Time!!!</span>											
										</div>										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="toTime">To Time<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control pickatime"  
												autocomplete="off" onchange="trim(this);Compare()"  name="toTime"
												id="toTime" value="${delSlot.toTime}"> <span
												class="validation-invalid-label text-danger"
												id="error_max_km" style="display: none;">This field
												is required.</span>
												 
										</div>
									</div>
									<div class="form-group row">
									<label class="form-check-label font-weight-bold col-lg-2"
											for="activeInactive">Active / Inactive Status<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
										<c:choose>
										<c:when test="${delSlot.isActive==1}">
										<div class="form-check form-check-inline">
												<label class="form-check-label">
												
												 <input type="radio"
													class="form-check-input" checked value="1"
													name="radioConfig" id="add_radio"> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="0" name="radioConfig"
													id="remove_radio"> Inactive
												</label>
											</div>
										
										
										</c:when>
										<c:otherwise>
										<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input"  value="1"
													name="radioConfig" id="add_radio"> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="0" name="radioConfig"
													id="remove_radio"> Inactive
												</label>
											</div>
										
										
										</c:otherwise>
										
										
										</c:choose>
											
										</div>
									</div>
									
									
									
									
									
								<%-- 	<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="amt1">Delivery Charges <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)" name="amt1"
												id="amt1" value="${delCharge.amt1}"> <span
												class="validation-invalid-label text-danger"
												id="error_amt1" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="amt2">Above Free Delivery Min Order Amt<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												autocomplete="off" onchange="trim(this)" name="amt2"
												id="amt2" value="${delCharge.amt2}"> <span
												class="validation-invalid-label text-danger"
												id="error_amt2" style="display: none;">This field
												is required.</span>
										</div>
									</div> --%>

									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-danger" id="submtbtn">
											Save <i class="icon-paperplane ml-2"></i>
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

	<script type="text/javascript">
	$(document).ready(function($) {

			$("#submitAddDelSlot").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#delslotName").val()) {
					isError = true;
					$("#error_group_name").show()
				} else {
					$("#error_group_name").hide()
				}
				 
				if ($("#fromTime").val() == "") {
					isError = true;
					$("#error_min_km").show()
				} else {
					$("#error_min_km").hide()
				}
				
				
				if ($("#toTime").val() == "") {
					isError = true;
					$("#error_max_km").show()
				} else {
					$("#error_max_km").hide()
				}
				

				/* if ($("#amt1").val() == "") {
					isError = true;
					$("#error_amt1").show()
				} else {
					$("#error_amt1").hide()
				}
				
				if ($("#amt2").val() == "") {
					isError = true;
					$("#error_amt2").show()
				} else {
					$("#error_amt2").hide()
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

		
		
	/* 	$('#min_km').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		
		$('#max_km').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		
		$('#amt1').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		
		$('#amt2').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
			});
		 */
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
		
		
		/* function validDate(val){
			var fromTm=document.getElementById("fromTime").value;
			var toTm=val;
			var hrFtm=fromTm.split(':');
			var hrTtm=toTm.split(':');
			alert(hrFtm[0]+hrTtm[0])
			if(hrFtm[0]==hrTtm[0]){
					alert("same Hour")
			}
			
		} */
		
		function Compare() {
		    var strStartTime = document.getElementById("fromTime").value;
		    var strEndTime = document.getElementById("toTime").value;
			var hrStTm=strStartTime.split(":");
		    var startTime = new Date().setHours(GetHours(strStartTime), GetMinutes(strStartTime), 0);
		    var endTime = new Date(startTime)
		    endTime = endTime.setHours(GetHours(strEndTime), GetMinutes(strEndTime), 0);
		    if (startTime > endTime && hrStTm[0]!=12 ) {
		    	//alert(startTime);
		        //alert("Start Time is greater than end time");
		        $("#from_time_error").show()
		    }
		    if (startTime == endTime) {
		       // alert("Start Time equals end time");
		        $("#from_time_error").hide()
		    }
		    if (startTime < endTime) {
		       // alert("Start Time is less than end time");
		        
		        $("#from_time_error").hide()
		    }
		}
		function GetHours(d) {
		    var h = parseInt(d.split(':')[0]);
		    if (d.split(':')[1].split(' ')[1] == "PM") {
		        h = h + 12;
		    }
		    return h;
		}
		function GetMinutes(d) {
		    return parseInt(d.split(':')[1].split(' ')[0]);
		}
		
		
	</script>

</body>
</html>