<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<style type="text/css">
tr.highlight {
		    background-color: grey !important;
		}
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

<body class="sidebar-xs" >
	<c:url value="/getConfigByCatId" var="getConfigByCatId"></c:url>

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
									href="${pageContext.request.contextPath}/configFranchiseList"
									style="color: white;"><i class="icon-list2 ml-2"></i>&nbsp;&nbsp;&nbsp;&nbsp;View
										List</a></span> --%>
							
							</div>
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>
							<div class="card-body">
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>
								<form
									action="${pageContext.request.contextPath}/saveMutiFrCharges"
									id="submitInsert" method="post">


									<input type="hidden" class="form-control" name="charge_id"
										id="charge_id" value="${charges.chargeId}">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="dates">Date<span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control daterange-basic_new"
												name="dates" id="dates" autocomplete="off">
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="surcharge">Surcharge Fees<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												name="surcharge" id="surcharge"
												value="${charges.surchargeFee}" autocomplete="off">
											<span class="validation-invalid-label text-danger"
												id="error_surcharge" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="packing">Packing Charges<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												" name="packing" id="packing" value="${charges.packingChg}"
												autocomplete="off"> <span
												class="validation-invalid-label text-danger"
												id="error_packing" style="display: none;">This field
												is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="handling">Handling Charges<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												" name="handling" id="handling"
												value="${charges.handlingChg}" autocomplete="off"><span
												class="validation-invalid-label text-danger"
												id="error_handling" style="display: none;">This field
												is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="extra">Extra Charges<span class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" 
												class="form-control maxlength-badge-position" maxlength="5"
												" name="extra" id="extra" value="${charges.extraChg}"
												autocomplete="off"><span
												class="validation-invalid-label text-danger"
												id="error_extra" style="display: none;">This field is
												required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="round_off">Round Off Amt.<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"
												class="form-control maxlength-badge-position" maxlength="5"
												" name="round_off" id="round_off"
												value="${charges.roundOffAmt}" autocomplete="off"><span
												class="validation-invalid-label text-danger"
												id="error_round_off" style="display: none;">This
												field is required.</span>
										</div>
									</div>





									<!--Table-->
									<table class="table ddatatable-header-basic" id="printtable1">
										<thead>
											<tr >
												<th>Sel All<input type="checkbox" name="selAll"
													id="selAll" /></th>
												<th>Sr.No.</th>
												<th>Franchise Name</th>
												<th>Surcharge_Fee</th>
												<th>Packing_ch.</th>
												<th>Handling ch.</th>
												<th>Extra ch.</th>
												<th>Round Off Amt</th>
												
							

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${frList}" var="frList" varStatus="count">

												<tr id="tr_tb${frList.frId}" >
												
													<td><input type="checkbox" id="frId${frList.frId}" value="${frList.frId}" name="frId" class="select_all">
													<input type="hidden" id="exVar2${frList.frId}" name="exVar2${frList.frId}" value="${frList.exVar2}"></td>
													<td>${count.index+1})</td>
													<td  id="name${frList.frId}" >${frList.frName}</td>
													<td ><input  type="text" id="exVar3${frList.frId}" name="exVar3${frList.frId}" value="${frList.exVar3}" ></td>
													<td ><input  type="text" id="exVar4${frList.frId}" name="exVar4${frList.frId}" value="${frList.exVar4}" ></td>
													<td ><input  type="text" id="exVar5${frList.frId}" name="exVar5${frList.frId}" value="${frList.exVar5}" ></td>
													<td ><input  type="text" id="exVar6${frList.frId}" name="exVar6${frList.frId}" value="${frList.exVar6}" ></td>
													<td ><input  type="text" id="exVar7${frList.frId}" name="exVar7${frList.frId}" value="${frList.exVar7}" ></td>
													
													
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<input type="hidden" id="btnType" name="btnType"> <span
										class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Check Box.</span>
									<div class="text-center">
										<br>
										<!-- <button type="submit" class="btn btn-primary" id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
										</button> -->
										<button type="button" class="btn btn-primary" onclick="Apply()" >
											Apply <i class="icon-paperplane ml-2"></i>
									 </button> 
										<button type="button" style="display: none;" id="popUpbtn"  data-toggle="modal" data-target="#modal_full"></button>
										<button type="button" class="btn btn-primary"
											onclick="pressBtn()" >
											Save & Next<i class="icon-paperplane ml-2"></i>
										</button>

									</div>
								</form>
							</div>
						</div>
						<!-- /a legend -->
					</div>
				</div>
			
					<div id="modal_full" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-full">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">Full width modal</h5>
								<button type="button" class="close" data-dismiss="modal">&times;</button>
							</div>

							<div class="modal-body">
							<form
									action="${pageContext.request.contextPath}/saveMutiFrCharges"
									id="submitInsert1" method="post">
									      
									<input  type="hidden" id="surchargeH" name="surchargeH">
									<input  type="hidden" id="packingH" name="packingH">
									<input  type="hidden" id="handlingH" name="handlingH">
									<input  type="hidden" id="extraH" name="extraH">
									<input  type="hidden" id="round_offH" name="round_offH">
									<input type="hidden" id="datesH" name="datesH">
									
									
									
							<table class="table ddatatable-header-basic" id="printtable2">
							<thead>
								<tr >
												<th>Sel All<input type="checkbox" name="selAll2"
													id="selAll2" /></th>
												<th>Sr.No.</th>
												<th>Franchise Name</th>
												<th>Surcharge_Fee</th>
												<th>Packing_ch.</th>
												<th>Handling ch.</th>
												<th>Extra ch.</th>
												<th>Round Off Amt</th>
												
							

								</tr>
							
							</thead>
							<tbody>
							
							
							</tbody>
							
							</table>
						</form>
							
							
							
							
							
							</div>

							<div class="modal-footer">
								<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								<button  type="submit" class="btn btn-primary" id="submtbtn1" onclick="submitForm()" >Save changes</button>
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

	<script>
		function pressBtn() {
			//alert("press");
			var a=document.getElementById("surcharge").value;
			var b=document.getElementById("packing").value;
			var c=document.getElementById("handling").value;
			var d=document.getElementById("extra").value;
			var e=document.getElementById("round_off").value;
			var f=document.getElementById("dates").value;
			//alert(f);
			      
		document.getElementById("surchargeH").value=a;
		document.getElementById("packingH").value=b;
		document.getElementById("handlingH").value=c;
		document.getElementById("extraH").value=d;
		document.getElementById("round_offH").value=e;
		document.getElementById("datesH").value=f;
			
			 var dataTable = $('#printtable2').DataTable();
			 dataTable.clear().draw();
			
			 var unique = array.filter(onlyUnique);
			 //alert(unique);
			for(var fl=0;fl<unique.length;fl++){
				//alert(unique[fl]);
				var name="name"+unique[fl];
			   	var exvar3="exVar3"+unique[fl];
		    	var exvar4="exVar4"+unique[fl];
		    	var exvar5="exVar5"+unique[fl];
		    	var exvar6="exVar6"+unique[fl];
		    	var exvar7="exVar7"+unique[fl];
		    	var tr_tb="tr_tb"+unique[fl];
		    	var exvar2 ="exVar2"+unique[fl];
		    	//alert(exvar2);
		     //document.body.append(checkbox.value + ' ');
		     var name=  document.getElementById(""+name).innerHTML;
		 var sur=  document.getElementById(""+exvar3).value;
		   var pack =document.getElementById(""+exvar4).value;
		   var hand =document.getElementById(""+exvar5).value;
		  var extra = document.getElementById(""+exvar6).value;
		  var round=  document.getElementById(""+exvar7).value;
		  var exvar2=document.getElementById(""+exvar2).value;
		 // alert(name);
		
		  var sel ='<td><input type="checkbox" id="frId'+unique[fl]+'" value="'+unique[fl]+'" name="frId" class="select_all"><input type="hidden" id="exVar2'+unique[fl]+'" name="exVar2'+unique[fl]+'" value="'+exvar2+'" ></td>';
		  var sr = ''+ (fl+1) +'';
		  var fName= '<td ><input type="text" id="name'+unique[fl]+'" name="name'+unique[fl]+'" value="'+name+'" ></td>';
		  var sCharge= '<td><input type="text" id="exVar3'+unique[fl]+'" name="exVar3'+unique[fl]+'" value="'+sur+'"></td>';
		  var packCh= '<td ><input type="text" id="exVar4'+unique[fl]+'" name="exVar4'+unique[fl]+'" value="'+pack+'"></td>';
		  var handCh= '<td  ><input type="text" id="exVar5'+unique[fl]+'" name="exVar5'+unique[fl]+'" value="'+hand+'"></td>';
		  var extraCh= '<td  ><input type="text" id="exVar6'+unique[fl]+'" name="exVar6'+unique[fl]+'" value="'+extra+'"></td>';
		  var roundCh= '<td  ><input type="text" id="exVar7'+unique[fl]+'" name="exVar7'+unique[fl]+'" value="'+round+'"></td>';
		 /*  var str = '<a href="javascript:void(0)" class="list-icons-item text-primary-600" data-popup="tooltip" title="" data-original-title="Edit" onclick="callEdit('
		  + v.totalAmt
		  + ','
		  + i
		  + ')"><i class="icon-pencil7"></i></a>&nbsp;&nbsp;<a href="javascript:void(0)"   class="list-icons-item text-danger-600 bootbox_custom" data-uuid="5" data-original-title="Delete" onclick="callDelete('
		  + v.totalAmt
		  + ','
		  + i
		  + ')"><i class="icon-trash"></i></a>' */
		  dataTable.row.add(
		  [sel,sr,fName,sCharge,packCh,handCh,extraCh,roundCh]).draw();
	
				
				
				
				
			}
			
			
			
			
			document.getElementById("popUpbtn").click();
			
		}
		$(document).ready(

				function() {

					$("#selAll").click(
							function() {
								$('#printtable1 tbody input[type="checkbox"]')
										.prop('checked', this.checked);

							});
				}
				
		
		
		);
		$(document).ready(

				function() {

					$("#selAll2").click(
							function() {
								$('#printtable2 tbody input[type="checkbox"]')
										.prop('checked', this.checked);

							});
				}
				
		
		
		);

		$("#configId").change(function() {
			if ($("#configId").val() == 0 || !$("#configId").val()) {
			} else {
				document.getElementById("search_button").click();
			}
		});
	</Script>

	<script type="text/javascript">
		$(document).ready(function($) {
			$("#configFranchise").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if ($("#catId").val() == 0 || !$("#catId").val()) {
					isError = true;
					$("#error_category").show()
					$("#catId").focus();
				} else {
					$("#error_category").hide()
				}

				if ($("#configId").val() == 0 || !$("#configId").val()) {
					isError = true;
					$("#error_configId").show()
					$("#configId").focus();
				} else {
					$("#error_configId").hide()
				}

				if (!isError) {
					var x = true;
					if (x == true) {
						//document
						//.getElementById("subBtn").disabled = true;
						return true;
					}
				}

				return false;

			});
		});

		$(document)
				.ready(
						function($) {

							$("#submitInsert")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												var checked = $("#submitInsert1 input:checked").length > 0;

												var count = $('#printtable1 tr').length;
												//alert(checked);
												if (!checked || count <= 1) {
													$("#error_chks").show()
													isError = true;
												} else {
													$("#error_chks").hide()
													isError = false;
												}

											/* 	if (!$("#surcharge").val()) {
													isError = true;
													$("#error_surcharge").show()
												} else {
													$("#error_surcharge").hide()
												}

												if ($("#packing").val() == "") {
													isError = true;
													$("#error_packing").show()
												} else {
													$("#error_packing").hide()
												}

												if (!$("#handling").val()) {
													isError = true;
													$("#error_handling").show()
												} else {
													$("#error_handling").hide()
												} 

												if (!$("#extra").val()) {
													isError = true;
													$("#error_extra").show()
												} else {
													$("#error_extra").hide()
												}

												if (!$("#round_off").val()) {
													isError = true;
													$("#error_round_off").show()
												} else {
													$("#error_round_off").hide()
												} */

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
	</script>

	<script type="text/javascript">
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
	

	$('#surcharge').on('input', function() {
			 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		})
		
	$('#packing').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#handling').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#extra').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	$('#round_off').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	})
	
	
	$('.daterange-basic_new').daterangepicker({
		applyClass : 'bg-slate-600',

		cancelClass : 'btn-light',
		locale : {
			format : 'DD-MM-YYYY',
			separator : ' to '
		}
	});
	
	 var i = 0;
	 var arr = [];	
	 
	 
	 
	 var i = 0;
	 var array = Array();

	 function add_element_to_array(val){
		 array[i]=val;
		 i++;
	 }
	 
	 function onlyUnique(value, index, self) {
		  return self.indexOf(value) === index;
		}
	 
	 
	 
	 function display_array()
	 {
		 var unique = array.filter(onlyUnique);
	    var e = "<hr/>";   
	     
	    for (var y=0; y<unique.length; y++)
	    {
	      e += "Element "+unique[y]+",";
	    }
	   alert(e);
	 }
	
	function Apply(){
		//alert("In Apply");
	
		
		var a = document.getElementById("surcharge").value;
		var b = document.getElementById("packing").value;
		var c =document.getElementById("handling").value;
		var d =document.getElementById("extra").value;
		var e =document.getElementById("round_off").value;
		var x = document.getElementsByName("frId");
		//alert(a);
		for (var checkbox of x) {
			
		    if (checkbox.checked){
		    	add_element_to_array(checkbox.value);
		    	//alert(checkbox.value);
		    	var exvar2="exVar2"+checkbox.value;
		    	var exvar3="exVar3"+checkbox.value;
		    	var exvar4="exVar4"+checkbox.value;
		    	var exvar5="exVar5"+checkbox.value;
		    	var exvar6="exVar6"+checkbox.value;
		    	var exvar7="exVar7"+checkbox.value;
		    	var tr_tb="tr_tb"+checkbox.value;
		    	
		    	//alert(exvar3);
		     //document.body.append(checkbox.value + ' ');
		   document.getElementById(""+exvar3).value=a;
		   document.getElementById(""+exvar4).value=b;
		   document.getElementById(""+exvar5).value=c;
		   document.getElementById(""+exvar6).value=d;
		   document.getElementById(""+exvar7).value=e;
		  
	  	 var element = document.getElementById(""+tr_tb);
		  element.classList.add("highlight");
		 
		
		    }
		  }
		
	}
	
	function submitForm(){
		      
	$("#submitInsert1").submit();
	}

	
	
	</script>
</body>
</html>