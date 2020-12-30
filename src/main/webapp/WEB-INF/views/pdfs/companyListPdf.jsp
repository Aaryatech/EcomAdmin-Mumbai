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
<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
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
  background-color: #f44336 !important;
  color: black;
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
								style="color: #000; font-size: 16px; text-align: center; margin: 0px;">Company List Pdf</h4>							
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
				<c:forEach items="${compIds}" var="compIds" varStatus="count">

					<c:if test="${compIds==1}">
						<th width="10%">Sr. No.</th>
					</c:if>

					<c:if test="${compIds==2}">
						<th>Company Name</th>
					</c:if>

					<c:if test="${compIds==3}">
						<th>Prefix</th>
					</c:if>

					<c:if test="${compIds==4}">
						<th>Opening Date</th>	
					</c:if>

					<c:if test="${compIds==5}">
						<th>Contact</th>
					</c:if>

					<c:if test="${compIds==6}">
						<th>Email</th>
					</c:if>

					<c:if test="${compIds==7}">
						<th>Address</th>
					</c:if>

					<c:if test="${compIds==8}">
						<th>City</th>
					</c:if>
					
					<c:if test="${compIds==9}">
						<th>Website</th>
					</c:if>
					
					<c:if test="${compIds==10}">
						<th>Is Parent</th>
					</c:if>
					
					<c:if test="${compIds==11}">
						<th>GST Type</th>
					</c:if>
					
					<c:if test="${compIds==12}">
						<th>GST No.</th>
					</c:if>
					
					<c:if test="${compIds==13}">
						<th>GST Code.</th>
					</c:if>
					
					<c:if test="${compIds==14}">
						<th>Bank</th>
					</c:if>
					
					<c:if test="${compIds==15}">
						<th>Branch</th>
					</c:if>
					
					<c:if test="${compIds==16}">
						<th>IFSC</th>
					</c:if>
					
					<c:if test="${compIds==17}">
						<th>A/c No.</th>
					</c:if>
				
					<c:if test="${compIds==18}">
						<th>CIN No.</th>
					</c:if>
					
					<c:if test="${compIds==19}">
						<th>FDA No.</th>
					</c:if>
					
					<c:if test="${compIds==20}">
						<th>FDA Declaration</th>
					</c:if>	
					
					<c:if test="${compIds==21}">
						<th>PAN</th>
					</c:if>	
					
					<c:if test="${compIds==22}">
						<th>Payment Gateway Applicable</th>
					</c:if>	
					
					<c:if test="${compIds==23}">
						<th>Payment Link</th>
					</c:if>					
				</c:forEach>
			</tr>


			<c:forEach items="${printCompList}" var="printCompList" varStatus="count">
				<tr>
					<c:forEach items="${compIds}" var="compIds">
					
					<c:if test="${compIds==1}">
						<td>${count.index+1}</td>
					</c:if>
					
					<c:if test="${compIds==2}">						
						<td>${printCompList.companyName}</td>
					</c:if>
					
					<c:if test="${compIds==3}">						
					<td>${printCompList.companyPrefix}</td>
					</c:if>
					
					<c:if test="${compIds==4}">						
						<td>${printCompList.compOpeningDate}</td>
					</c:if>
					
					<c:if test="${compIds==5}">						
						<td>${printCompList.compContactNo}</td>
					</c:if>
					
					<c:if test="${compIds==6}">						
						<td>${printCompList.compEmailAddress}</td>
					</c:if>
					
					<c:if test="${compIds==7}">						
						<td>${printCompList.compAddress}</td>
					</c:if>									
					
					<c:if test="${compIds==8}">						
						<td>${printCompList.exVar4}</td>
					</c:if>
					
					<c:if test="${compIds==9}">						
						<td>${printCompList.compWebsite}</td>
					</c:if>
					
					<c:if test="${compIds==10}">						
						<td>${printCompList.companyType==1 ? 'Yes' : 'NO'}</td>
					</c:if>
					
					<c:if test="${compIds==11}">						
						<td>${printCompList.compGstType== 1 ? "Regular" : printCompList.compGstType == 2 ? "Composite" : "Non-Register"}</td>
					</c:if>
					
					<c:if test="${compIds==12}">						
						<td>${printCompList.compGstNo}</td>
					</c:if>
					
					<c:if test="${compIds==13}">						
						<td>${printCompList.compStateGstCode}</td>
					</c:if>
					
					<c:if test="${compIds==14}">						
						<td>${printCompList.compBankName}</td>
					</c:if>
					
					<c:if test="${compIds==15}">						
						<td>${printCompList.compBankBranchName}</td>
					</c:if>
					
					<c:if test="${compIds==16}">						
						<td>${printCompList.compBankIfsc}</td>
					</c:if>
					
					<c:if test="${compIds==17}">						
						<td>${printCompList.compBankAccNo}</td>
					</c:if>
					
					<c:if test="${compIds==18}">						
						<td>${printCompList.compCinNo}</td>
					</c:if>
					
					<c:if test="${compIds==19}">						
						<td>${printCompList.compFdaNo}</td>
					</c:if>
					
					<c:if test="${compIds==20}">						
						<td>${printCompList.compFdaDeclarText}</td>
					</c:if>
					
					<c:if test="${compIds==21}">						
						<td>${printCompList.compPanNo}</td>
					</c:if>
					
					<c:if test="${compIds==22}">						
						<td>${printCompList.paymentGatewayApplicable ==1 ? 'Yes' : 'No'}</td>
					</c:if>
					
					<c:if test="${compIds==23}">						
						<td>${printCompList.paymentGatewayLink}</td>
					</c:if>
					
					
				</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</div>
		<div style="page-break-after: auto;"></div>
	
</body>
</html>