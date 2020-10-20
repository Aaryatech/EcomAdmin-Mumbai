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
<body>
<c:url value="/getItemImagesByProductId" var="getItemImagesByProductId"></c:url>
<c:url value="/deleteProductImageAjax" var="deleteProductImageAjax"></c:url>

	<!-- <body class="sidebar-xs">
 -->



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
				<!-- ColReorder integration -->
				<div class="card">

					<div
						class="card-header bg-blue text-white d-flex justify-content-between">
						<span
							class="font-size-sm text-uppercase font-weight-semibold card-title">
							Product Images</span>
						<!--  -->
						<c:if test="${addAccess==0}">
							<span class="font-size-sm text-uppercase font-weight-semibold"><a
								class="card-title"
								href="${pageContext.request.contextPath}/showAddProduct"
								style="color: white;"><i class="icon-add-to-list ml-2"
									style="font-size: 23px;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add
									Product</a></span>
						</c:if>
					</div>

					<div class="form-group row"></div>
					<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>


					<form action="#" id="submitForm3" method="post"
						enctype="multipart/form-data">

						<input type="hidden" id="prodId" name="prodId"
							value="${productIdStr}">

						<div class="form-group" id="imageDiv">

							<label class="col-form-label font-weight-bold col-lg-2"
								for="cust_name">Images List : </label>

							<div class="row" style="text-align: center;" id="dispImageDiv"></div>

							<br> <br>

							<div align="center" id="loader3" style="display: none;">

								<span>
									<h4>
										<font color="#343690">Loading</font>
									</h4>
								</span> <span class="l-1"></span> <span class="l-2"></span> <span
									class="l-3"></span> <span class="l-4"></span> <span class="l-5"></span>
								<span class="l-6"></span>
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

					</form>


					<!-- /colReorder integration -->

				</div>
				<!-- /content area -->


				<!-- Footer -->
				<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
				<!-- /footer -->

			</div>
			<!-- /main content -->
		</div>
	</div>
	<!-- /page content -->
	<script>
	$('.datatable-fixed-left_custom').DataTable({

		columnDefs : [ {
			orderable : false,
			targets : [ 0 ]
		} ],
		//scrollX : true,
		scrollX : true,
		scrollY : '50vh',
		scrollCollapse : true,
		order:[],
		paging : false,
		fixedColumns : {
			leftColumns : 1,
			rightColumns : 0
		}

	});
	</script>
	<script type="text/javascript">
		function getImageData(prodId) {
			alert("In ");
			$('#loader3').show();
			$
					.getJSON(
							'${getItemImagesByProductId}',
							{
								productId : prodId,
								ajax : 'true',
							},
								
							function(data){
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
													var divCard = "<div class=card style='width: 12%; height: 8%; margin: 10px;'>";
													var divCardImg = "<div class='card-img-actions mx-1 mt-1'>"
															+ "<img class='card-img img-fluid'"+
						"src='${imageUrl}"+image+"' alt=''>"
															+ "<a target='_blank'  href='${imageUrl}"+image+"'><div class='card-img-actions-overlay card-img'></div></a></div>";

													var divCardImg2 = "<div class=card-body style='padding: 5px;'>"
															+ "<div class='d-flex align-items-start flex-nowrap'><div style='text-align: center;'><input style='display:none;' type=text   value='"+image+"' class=imgId> "
															+ "</div><div class='list-icons list-icons-extended ml-auto' style='text-align: center;'>"
															+ "<a href=# onclick=deleteImage('"
															+ image+ "') class='list-icons-item'><i class='icon-bin top-0'></i></a></div></div></div></div>";
																
													img = img + divCard + ""
															+ divCardImg + ""
															+ divCardImg2;
												});
								document.getElementById("dispImageDiv").innerHTML = img;
							)}
		}
	</script>
	<script type="text/javascript">
	function appendImageList(data){
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
	}
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
		 
		var data=${imageJSON};
		appendImageList(data);
		
		  var prodId=document.getElementById("prodId").value; 
		$('.file-input-ajax1')
				.fileinput(
						{
							browseLabel : 'Browse',
							uploadUrl : "http://97.74.228.55:8080/ecomAdmin/ajaxImageUploadProduct/"+prodId, // server upload action
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
							//alert(extra.bdInteli + " " + response);
							//getImageData(prodId);
								showImgList(prodId);
							$('#files').fileinput('reset');
							//start
							//end
						});
		
		function showImgList(prodId){
			$('#loader3').show();
			$.getJSON(
							'${getItemImagesByProductId}',
							{
								productId : prodId,
								ajax : 'true',
							},
							function(data2){
								appendImageList(data2);
								$('#loader3').hide();
							}
							)
			$('#loader3').hide();
		}
	</script>

	<script type="text/javascript">
		function deleteImage(name) {
			var prodId=document.getElementById("prodId").value;
			$('#loader3').show();
			$.getJSON('${deleteProductImageAjax}', 
			{
				productId : prodId,
				imageName : name,
				ajax : 'true',
			}, function(data) {
				//alert(JSON.stringify(data));
				if (data.error) {
					//alert(data.message);
				} else {
					//getImageData(prodId);
					showImgList(prodId);
				}
			});
		}
	</script>

</body>
</html>