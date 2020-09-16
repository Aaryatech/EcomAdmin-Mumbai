<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
<c:url var="getProductListByCat" value="/getProductListByCat" />
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
			<%-- 	<div class="page-header page-header-light">


				<div
					class="breadcrumb-line breadcrumb-line-light header-elements-md-inline">
					<div class="d-flex">
						<div class="breadcrumb">
							<a href="index.html" class="breadcrumb-item"><i
								class="icon-home2 mr-2"></i> Home</a> <span
								class="breadcrumb-item active">Dashboard</span>
						</div>

						<a href="#" class="header-elements-toggle text-default d-md-none"><i
							class="icon-more"></i></a>
					</div>

					<div class="breadcrumb justify-content-center">
						<a href="${pageContext.request.contextPath}/showEmpTypeList"
							class="breadcrumb-elements-item"> Employee Type List</a>

					</div>


				</div>
			</div> --%>
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
								<h6 class="card-title">Configure Related Product</h6>
								<!-- <div class="header-elements">
									<div class="list-icons">
										<a class="list-icons-item" data-action="collapse"></a>
									</div>
								</div> -->
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
									<span class="font-weight-semibold"></span>
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
									<span class="font-weight-semibold"></span>
									<%
										out.println(session.getAttribute("successMsg"));
									%>
								</div>
								<%
									session.removeAttribute("successMsg");
									}
								%>

								<form
									action="${pageContext.request.contextPath}/submitReletedProductCofig"
									id="submitInsertEmpType" method="post">



									<input type="hidden" id="comoffallowed" name="comoffallowed"
										value="1">

									<div class="form-group row">
										<label class="col-form-label col-lg-2" for="uom_id">
											Select Product <span style="color: red">* </span>:
										</label>
										<div class="col-lg-4">
											<select
												class="form-control form-control-select2 select2-hidden-accessible"
												data-fouc="" aria-hidden="true" data placeholder="Select "
												id="product_id" name="product_id">

												<option selected disabled value="">Select</option>
												<c:forEach items="${productList}" var="productList"
													varStatus="count">
													<option value="${productList.productId}">${productList.productName}</option>
												</c:forEach>
											</select> <span class="validation-invalid-label" id="error_productId"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div class="col-lg-12" align="center">
										<table class="table datatable-scroller table-border"
											id="printtable1">
											<thead>
												<tr class="bg-blue">
													<th>Sr. No.</th>
													<th>List</th>

												</tr>
											</thead>
											<tbody>


												<c:forEach items="${catProList}" var="catProList1"
													varStatus="count">

													<tr>

														<td>${count.index+1}&nbsp;&nbsp;<input
															type="checkbox" id="header${catProList1.cat.catId}"
															name="header${catProList1.cat.catId}" class="select_all"
															value="0"
															onclick="checkSubmodule(${catProList1.cat.catId})"></td>
														<td colspan="5"><b>${catProList1.cat.catName}</b></td>

													</tr>

													<c:forEach items="${catProList1.productList}" var="proList">
														<tr>

															<td></td>

															<td><input type="checkbox"
																id="${proList.productId}view${proList.prodCatId}"
																name="${proList.productId}view${proList.prodCatId}"
																onclick="changeValue(${proList.productId},${proList.prodCatId})">
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${proList.productName}</td>

														</tr>

													</c:forEach>


												</c:forEach>


											</tbody>
										</table>
									</div>

									<div class="form-group row">
										<div class="col-lg-10">
											<span class="validation-invalid-label" id="error_checkbox"
												style="display: none;">Check Minimum One Checkbox</span>
										</div>
									</div>
									<br>

									<div class="col-md-12" style="text-align: center;">

										<button type="submit" class="btn bg-blue ml-3 legitRipple"
											id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
										</button>
										<a href="${pageContext.request.contextPath}/showRoleList"><button
												type="button" class="btn btn-primary">Cancel</button></a>

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

	<script>
		$('.datatable-fixed-left_custom').DataTable({
			columnDefs : [ {
				orderable : false,
				targets : [ 1 ]
			} ],
			scrollX : true,
			scrollY : '50vh',
			scrollCollapse : true,
			paging : false,
			fixedColumns : {
				leftColumns : 1,
				rightColumns : 0
			}

		});
		function checkSubmodule(catId) {
			
			//alert(catId);
			
			

			$
					.getJSON(
							'${getProductListByCat}',
							{
								catId : catId,
								ajax : 'true',

							},
							function(data) {
								alert( JSON.stringify(data));
								if (document
										.getElementById("header"+catId).checked == true) {

									for (var i = 0; i < data.length; i++) {
										
										alert(data[i].productId+"view"+catId);

										document.getElementById(data[i].productId
												+ "view"+catId).checked = true;
										 document.getElementById(data[i].productId+"view"+catId).value=1;

										
									}

								} else {
									for (var i = 0; i < data.length; i++) {

										document.getElementById(data[i].productId
												+ "view"+catId).checked = false;
										 document.getElementById(data[i].productId+"view"+catId).value=0;

										 
									}
								}

							});

		}

		function row_level(productId, catId) {
			if (document.getElementById(productId+"view"+catId).checked == true) {
				document.getElementById(productId+"view"+catId).checked = false;

				document.getElementById(productId+"view" + catId).value = 1;

			} else {
				document.getElementById(productId+"view"+catId).checked = true;

				document.getElementById(productId+"view" + catId).value = 0;


			}
 
		}
		function changeValue(productId, catId) {

		 
				if (document.getElementById(productId + "view" + catId).checked == true) {

					document.getElementById(productId + "view" + catId).value = 1;

				} else {

					document.getElementById(productId + "view" + catId).value = 0;
				}

		 
		}

	
	</script>

	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			//checkSame();
			return;
		}

		$(document).ready(function($) {

			$("#submitInsertEmpType").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#product_id").val()) {

					isError = true;

					$("#error_productId").show()
					//return false;
				} else {
					$("#error_productId").hide()
				}

				var checkboxes = $("input[type='checkbox']");

				if (!checkboxes.is(":checked")) {

					isError = true;

					$("#error_checkbox").show()

				} else {
					$("#error_checkbox").hide()
				}

				if (!isError) {

					var x = true;
					if (x == true) {

						document.getElementById("submtbtn").disabled = true;
						return true;
					}
					//end ajax send this to php page
				}
				return false;
			});
		});
		//
	</script>
	<!-- <script type="text/javascript">

function checkSame(){
	//alert("Hii..");
	x=document.getElementById("empTypeName").value;
	y=document.getElementById("empShortName").value;
	//alert(x);
	//alert(y);
	
	if(x!== '' && y!== ''){
	if(x==y){
		  
			$("#error_sameName").show()
			document.getElementById("empShortName").value="";

		} else {

			$("#error_sameName").hide()
		}
		
		
	}
}


</script> -->
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