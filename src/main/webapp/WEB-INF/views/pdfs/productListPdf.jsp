<%@page contentType="text/html; charset=ISO8859_1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.lang.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Product List</title>
<style type="text/css">
@media print {
	.no-break {
		page-break-inside: avoid !important;
	}
	
}
body{
	
    background-color: #fff;
	
}
table {
	border-collapse: collapse;
	font-size: 10;
	font-size : 13;
	width: 90%;
	page-break-inside: auto !important;
	margin-left: 5%;
	margin-right: 5%;
}

table th {
  background-color: #ee558f !important;
  color: #fff;
}


.footer_btm{position: fixed; text-align: center; padding: 10px; bottom: 0; left:0; font-size: 12px; 
color:#333; width: 100%; background: #f5f5f5; min-height: 35px;}
</style>
</head>
<body>
					<div class="no-break">
						 
							<div
							style="text-align: center; font-size: 18px; margin-top: 20px; margin-bottom: 5px;">
			
								<h3
									style="color: #000; text-align: center; margin: 0px;">${compName}</h3> 
								<h5
									style="color: #000; text-align: center; margin: 0px;">${compAddress}</h5> 
									<h5
									style="color: #000; text-align: center; margin: 0px;">${compContact}</h5> 
							
						

						<div
							style="text-align: center; font-size: 18px;">

							<h4
								style="color: #000; font-size: 16px; text-align: center; margin: 0px;">Product List</h4>							
								<%-- <p
									style="color: #000; font-size: 15px; text-align: center; font-weight: bold;">
									Date : ${fromDate}&nbsp;&nbsp; to &nbsp;&nbsp;${toDate}</p> --%>
							
						</div>  
						</div>



		<table align="center" border="1" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131;"
			class="table datatable-header-basic">
			<tr>				
				<th width="30">Sr. No.</th>
				<c:forEach items="${proIds}" var="proIds">
					<c:if test="${proIds==1}">
						<th>Product Code</th>
					</c:if>
					
					<c:if test="${proIds==2}">
					<th>Name</th>
					</c:if>
					
					<c:if test="${proIds==3}">
					<th>Short Name</th>
					</c:if>
					
					<c:if test="${proIds==4}">
					<th>Category</th>
					</c:if>
					
					<c:if test="${proIds==5}">
					<th>Category</th>
					</c:if>
					
					<c:if test="${proIds==6}">
					<th>Tax</th>
					</c:if>
					
					<c:if test="${proIds==7}">
					<th>Sort No.</th>
					</c:if>
					
					<c:if test="${proIds==8}">
					<th>Min Qty.</th>
					</c:if>
					
					<c:if test="${proIds==9}">
					<th>Shelf Life</th>
					</c:if>
					
					<c:if test="${proIds==10}">
					<th>Return Allowed</th>
					</c:if>
					
					<c:if test="${proIds==11}">
					<th>Return %</th>
					</c:if>
					
					<c:if test="${proIds==12}">
					<th>UOM</th>
					</c:if>
					
					<c:if test="${proIds==13}">
					<th>Shape</th>
					</c:if>
					
					<c:if test="${proIds==14}">
					<th>Product Type</th>
					</c:if>
					
					<c:if test="${proIds==15}">
					<th>Default Shape</th>
					</c:if>
					
					
					<c:if test="${proIds==16}">
					<th>Flavour</th>
					</c:if>
					
					<c:if test="${proIds==17}">
					<th>Product Status</th>
					</c:if>
					
					<c:if test="${proIds==18}">
					<th>Default Flavour</th>
					</c:if>
					
					<c:if test="${proIds==19}">
					<th>Veg/Non-Veg</th>
					</c:if>
					
					<c:if test="${proIds==20}">
					<th>Book Before Days</th>
					</c:if>
					
					<c:if test="${proIds==21}">
					<th>Default Veg/Non-Veg</th>
					</c:if>
					
					<c:if test="${proIds==22}">
					<th>Alphabats Limit</th>
					</c:if>
					
					<c:if test="${proIds==23}">
					<th>No. Alphabats</th>
					</c:if>
					
					<c:if test="${proIds==24}">
					<th>No. Of Msg Character</th>
					</c:if>
					
					<c:if test="${proIds==25}">
					<th>Bread Type</th>
					</c:if>
					
					<c:if test="${proIds==26}">
					<th>Cream Type</th>
					</c:if>
					
					<c:if test="${proIds==27}">
					<th>Layering Cream</th>
					</c:if>
					
					<c:if test="${proIds==28}">
					<th>Topping Cream</th>
					</c:if>					
					
					<c:if test="${proIds==29}">
					<th>Applicable Tags</th>
					</c:if>
					
					<c:if test="${proIds==30}">
					<th>Product Desc</th>
					</c:if>
					
					<c:if test="${proIds==31}">
					<th>Ingredient</th>
					</c:if>
					
					<c:if test="${proIds==32}">
					<th>Preparation Time</th>
					</c:if>
					
					<c:if test="${proIds==33}">
					<th>Rate Setting Type</th>
					</c:if>
					
					<c:if test="${proIds==34}">
					<th>"Max Weight</th>
					</c:if>
					
					<c:if test="${proIds==35}">
					<th>Available Weights</th>
					</c:if>
					
					<c:if test="${proIds==36}">
					<th>Basic Product MRP</th>
					</c:if>
					
					<c:if test="${proIds==37}">
					<th>Active</th>
					</c:if>
				</c:forEach>
			</tr>


			<c:forEach items="${prodList}" var="prod" varStatus="count">
				<tr>
					<td>${count.index+1}</td>
					<c:forEach items="${proIds}" var="proIds">					
					<c:if test="${proIds==1}">
						<td>${prod.productCode}</td>
					</c:if>
					
					<c:if test="${proIds==2}">
						<td width="20%;">${prod.productName}</td>
					</c:if>
					
					<c:if test="${proIds==3}">
					<td>${prod.catName}</td>
					</c:if>
					
					<c:if test="${proIds==4}">
					<td>${prod.catName}</td>
					</c:if>
					
					<c:if test="${proIds==5}">
					<td>${prod.subCatName}</td>
					</c:if>
					
					<c:if test="${proIds==6}">
					<td>${prod.taxName}</td>
					</c:if>
					
					<c:if test="${proIds==7}">
					<td>${prod.sortId}</td>
					</c:if>
					
					<c:if test="${proIds==8}">
						<td>${prod.minQty}</td>
					</c:if>
					
					<c:if test="${proIds==9}">
						<td>${prod.shelfLife}</td>
					</c:if>
					
					<c:if test="${proIds==10}">
						<td>${prod.isReturnAllow == 1 ? 'Yes' : 'No'}</td>
					</c:if>
					
					<c:if test="${proIds==11}">
						<td>${prod.retPer}</td>
					</c:if>
					
					<c:if test="${proIds==12}">
						<td>${prod.uomShowName}</td>
					</c:if>
					
					<c:if test="${proIds==13}">
						<td>${prod.shapeNames}</td>
					</c:if>
					
					<c:if test="${proIds==14}">
						<td>${prod.prodTypeName}</td>
					</c:if>
					
					<c:if test="${proIds==15}">
						<td>${prod.defaultShapeName}</td>
					</c:if>
					
					<c:if test="${proIds==16}">
						<td>${prod.flavorNames}</td>
					</c:if>
					
					<c:if test="${proIds==17}">
						<td>${prod.prodStatusName}</td>
					</c:if>
					
					<c:if test="${proIds==18}">
						<td>${prod.defaultFlavorName}</td>
					</c:if>
					
					<c:if test="${proIds==19}">
						<td>${prod.vegNonvegName}</td>
					</c:if>
					
					<c:if test="${proIds==20}">
						<td>${prod.bookBefore}</td>
					</c:if>
					
					<c:if test="${proIds==21}">
						<td>${prod.defaultVegNonvegName}</td>
					</c:if>
					
					<c:if test="${proIds==22}">
						<td>${prod.isCharLimit == 1 ? 'Yes' : 'No'}</td>
					</c:if>
					
					<c:if test="${proIds==23}">
						<td>${prod.noOfCharsForAlphaCake}</td>
					</c:if>
					
					<c:if test="${proIds==24}">
						<td>${prod.noOfCharsOnCake}</td>
					</c:if>
					
					<c:if test="${proIds==25}">
						<td>${prod.breadTypeName}</td>
					</c:if>
					
					<c:if test="${proIds==26}">
						<td>${prod.creamTypeName}</td>
					</c:if>
					
					<c:if test="${proIds==27}">
						<td>${prod.layeringCreamNames}</td>
					</c:if>					
					
					<c:if test="${proIds==28}">
						<td>${prod.toppingCreamNames}</td>
					</c:if>
					
					<c:if test="${proIds==29}">
						<td>${prod.appliTagNames}</td>
					</c:if>
					
					<c:if test="${proIds==30}">
						<td>${prod.productDesc}</td>
					</c:if>
					
					<c:if test="${proIds==31}">
						<td>${prod.ingerdiants}</td>
					</c:if>
					
					<c:if test="${proIds==32}">
						<td>${prod.prepTime}</td>
					</c:if>
					
					<c:if test="${proIds==33}">
						<td>${prod.rateSettingType == 0 ? 'Per UOM' : prod.rateSettingType == 1 ? 'Per Kg' : 'As of Filter'}</td>
					</c:if>
					
					<c:if test="${proIds==34}">
						<td>${prod.maxWt}</td>
					</c:if>
					
					<c:if test="${proIds==35}">
						<td>${prod.availInWeights}</td>
					</c:if>
					
					<c:if test="${proIds==36}">
						<td>${prod.actualRate}</td>
					</c:if>
					
					<c:if test="${proIds==37}">
						<td>${prod.isHomePageProd}</td>
					</c:if>
					
					</c:forEach>
				</tr>
			</c:forEach>
		</table>


	</div>
		<div style="page-break-after: auto;"></div>
	
			<div class="footer_btm" style="display: inline-block; width: 100%; text-align: center; position: absolute; bottom:0; margin: 20px 0 0 0;">
	<img alt="" src="${pageContext.request.contextPath}/resources/global_assets/images/mongi.png" height="20px;" style="float: left; vertical-align: middle;">
	<span style="display: inline-block; float:left; text-align: center; width: 70%; vertical-align: middle; font-size: 12px;  ">******</span>
	<img alt="" src="${pageContext.request.contextPath}/resources/global_assets/images/powerd_logo.png" height="10px;" style="float: right; vertical-align: top;">
	</div>
</body>
</html>