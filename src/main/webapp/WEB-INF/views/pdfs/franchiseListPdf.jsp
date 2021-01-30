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
<!-- <title>Production List</title> -->
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
.footer_lft{position: fixed; left:6px; bottom:6px; width: 20px; height: 20px;}
.footer_lft img{width: 100%;}
.footer_right{position: fixed; right:6px; bottom:6px; }
.footer_right span{display: inline-block; margin: 0 3px 0 0; font-size: 12px; }
.footer_right img{width: 20px; height: 20px;}


</style>
</head>
<body>
					<div class="no-break">
						 
							<div
							style="text-align: center; font-size: 18px; margin-top: 20px; margin-bottom: 5px;">
			
								 <h3
									style="color: #000; text-align: center; margin: 0px;">${company}</h3> 
							
						

						<div
							style="text-align: center; font-size: 18px;">

							<h4
								style="color: #000; font-size: 16px; text-align: center; margin: 0px;">Franchise List Pdf</h4>							
								<%-- <p
									style="color: #000; font-size: 15px; text-align: center; font-weight: bold;">
									Date : ${fromDate}&nbsp;&nbsp; to &nbsp;&nbsp;${toDate}</p> --%>
							
						</div>  
						</div>
<c:set value="" var="chgDate"/>
		<table align="center" border="1" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131;"
			class="table datatable-header-basic">
			<tr>
				<c:forEach items="${printFrIds}" var="printFrIds" varStatus="count">

					<c:if test="${printFrIds==1}">
						<th width="10%">Sr. No.</th>
					</c:if>

					<c:if test="${printFrIds==2}">
						<th>Code</th>
					</c:if>

					<c:if test="${printFrIds==3}">
						<th>Franchise</th>
					</c:if>

					<c:if test="${printFrIds==4}">
						<th>Opening Date</th>
					</c:if>

					<c:if test="${printFrIds==5}">
						<th>Owner's DOB</th>
					</c:if>

					<c:if test="${printFrIds==6}">
						<th>Email</th>
					</c:if>

					<c:if test="${printFrIds==7}">
						<th>Contact</th>
					</c:if>

					<c:if test="${printFrIds==8}">
						<th>Address</th>
					</c:if>

					<c:if test="${printFrIds==9}">
						<th>City</th>
					</c:if>
					
					<c:if test="${printFrIds==10}">
						<th>Pincode</th>
					</c:if>
					
					<c:if test="${printFrIds==11}">
						<th>Status</th>
					</c:if>
					
					<c:if test="${printFrIds==12}">
						<th>Rating</th>
					</c:if>
					
					<c:if test="${printFrIds==13}">
						<th>FDA No.</th>
					</c:if>
					
					<c:if test="${printFrIds==14}">
						<th>FDA Lic.Expiry</th>
					</c:if>
					
					<c:if test="${printFrIds==15}">
						<th>GST Type</th>
					</c:if>
					
					<c:if test="${printFrIds==16}">
						<th>GST No.</th>
					</c:if>
					
					<c:if test="${printFrIds==17}">
						<th>Longitude</th>
					</c:if>
					
					<c:if test="${printFrIds==18}">
						<th>Latitude</th>
					</c:if>
					
					<c:if test="${printFrIds==19}">
						<th>Served Pincodes</th>
					</c:if>
					
					<c:if test="${printFrIds==20}">
						<th>Km. Cover</th>
					</c:if>
					
					<c:if test="${printFrIds==21}">
						<th>Bank</th>
					</c:if>
					
					<c:if test="${printFrIds==22}">
						<th>Branch</th>
					</c:if>
					
					<c:if test="${printFrIds==23}">
						<th>IFSC</th>
					</c:if>
					
					<c:if test="${printFrIds==24}">
						<th>A/C No.</th>
					</c:if>
					
					<c:if test="${printFrIds==25}">
						<th>Payment Gateway</th>
					</c:if>
					
					<c:if test="${printFrIds==26}">
						<th>Parent P/G</th>
					</c:if>
					
					<c:if test="${printFrIds==27}">
						<th>PAN</th>
					</c:if>
					
					<c:if test="${printFrIds==28}">
						<th>Charges B/w Date</th>
					</c:if>
					
					<c:if test="${printFrIds==29}">
						<th>Surcharge Fees</th>
					</c:if>
					
					<c:if test="${printFrIds==30}">
						<th>Packing Chg</th>
					</c:if>
					
					<c:if test="${printFrIds==31}">
						<th>Handling Chg</th>
					</c:if>
					
					<c:if test="${printFrIds==32}">
						<th>Extra Chg</th>
					</c:if>
					
					<c:if test="${printFrIds==33}">
						<th>Round Off Amt</th>	
					</c:if>
					
				</c:forEach>
			</tr>


			<c:forEach items="${printFrList}" var="printFrList" varStatus="count">
				<tr>
					<c:forEach items="${printFrIds}" var="printFrIds">
					
					<c:if test="${printFrIds==1}">
						<td>${count.index+1}</td>
					</c:if>
					
					<c:if test="${printFrIds==2}">						
						<td>${printFrList.frCode}</td>
					</c:if>
					
					<c:if test="${printFrIds==3}">						
					<td>${printFrList.frName}</td>
					</c:if>
					
					<c:if test="${printFrIds==4}">						
						<td>${printFrList.openingDate}</td>
					</c:if>
					
					<c:if test="${printFrIds==5}">						
						<td>${printFrList.ownersBirthDay}</td>
					</c:if>
					
					<c:if test="${printFrIds==6}">						
						<td>${printFrList.frEmailId}</td>
					</c:if>
					
					<c:if test="${printFrIds==7}">						
						<td>${printFrList.frContactNo}</td>
					</c:if>									
					
					<c:if test="${printFrIds==8}">						
						<td>${printFrList.frAddress}</td>
					</c:if>
					
					<c:if test="${printFrIds==9}">						
						<td>${printFrList.city}</td>
					</c:if>
					
					<c:if test="${printFrIds==10}">						
						<td>${printFrList.pincode}</td>
					</c:if>
					
					<c:if test="${printFrIds==11}">						
						<td>${printFrList.isActive==1 ? 'Active' : 'In-Active'}</td>
					</c:if>
					
					<c:if test="${printFrIds==12}">						
						<td>${printFrList.frRating}</td>
					</c:if>
					
					<c:if test="${printFrIds==13}">						
						<td>${printFrList.fdaNumber}</td>
					</c:if>
					
					<c:if test="${printFrIds==14}">						
						<td>${printFrList.fdaLicenseDateExp}</td>
					</c:if>
					
					<c:if test="${printFrIds==15}">						
						<td>${printFrList.gstType eq '1'?'Regular':printFrList.gstType eq '2'?'Composite':'Non-Register'}</td>
					</c:if>
					
					<c:if test="${printFrIds==16}">						
						<td>${printFrList.gstNumber}</td>
					</c:if>
					
					<c:if test="${printFrIds==17}">						
						<td>${printFrList.shopsLogitude}</td>
					</c:if>
					
					<c:if test="${printFrIds==18}">						
						<td>${printFrList.shopsLatitude}</td>
					</c:if>
					
					<c:if test="${printFrIds==19}">						
						<td>${printFrList.pincodeWeServed}</td>
					</c:if>
					
					<c:if test="${printFrIds==20}">						
						<td>${printFrList.noOfKmAreaCover}</td>
					</c:if>
					
					<c:if test="${printFrIds==21}">						
						<td>${printFrList.coBankName}</td>
					</c:if>
					
					<c:if test="${printFrIds==22}">						
						<td>${printFrList.coBankBranchName}</td>
					</c:if>
					
					<c:if test="${printFrIds==23}">						
						<td>${printFrList.coBankIfscCode}</td>
					</c:if>
					
					<c:if test="${printFrIds==24}">						
						<td>${printFrList.coBankAccNo}</td>
					</c:if>
					
					<c:if test="${printFrIds==25}">						
						<td>${printFrList.paymentGetwayLink}</td>
					</c:if>
					
					<c:if test="${printFrIds==26}">						
						<td>${printFrList.paymentGetwayLinkSameAsParent}</td>
					</c:if>
					
					<c:if test="${printFrIds==27}">						
						<td>${printFrList.panNo}</td>
					</c:if>
					
					<c:if test="${printFrIds==28}">
						<c:choose>
							<c:when test="${!empty printFrList.exVar6}">
							<c:set value="${printFrList.exVar6} to ${printFrList.exVar7}" var="chgDate"/>								
							</c:when>
							<c:otherwise>
								<c:set value="" var="chgDate"/>	
							</c:otherwise>
						</c:choose>					
						<td>${chgDate}</td>
					</c:if>
					
					<c:if test="${printFrIds==29}">						
						<td>${printFrList.exFloat1}</td>
					</c:if>
					
					<c:if test="${printFrIds==30}">						
						<td>${printFrList.exFloat2}</td>
					</c:if>
					
					<c:if test="${printFrIds==31}">						
						<td>${printFrList.exFloat3}</td>
					</c:if>
					
					<c:if test="${printFrIds==32}">						
						<td>${printFrList.exFloat4}</td>
					</c:if>
					
					<c:if test="${printFrIds==33}">						
						<td>${printFrList.exFloat5}</td>
					</c:if>
				</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</div>
		<div style="page-break-after: auto;"></div>
	
</body>
</html>