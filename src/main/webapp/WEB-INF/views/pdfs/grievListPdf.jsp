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
								style="color: #000; font-size: 16px; text-align: center; margin: 0px;">Grievances Instruction List Pdf</h4>							
								<%-- <p
									style="color: #000; font-size: 15px; text-align: center; font-weight: bold;">
									Date : ${fromDate}&nbsp;&nbsp; to &nbsp;&nbsp;${toDate}</p> --%>
							
						</div>  
						</div>



		<table align="center" border="1" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131;"
			class="table datatable-header-basic">
			<tr>
				<th width="10%">Sr. No.</th>
				<th>Grievance Caption</th>
				<th>Grievance Type</th>
				<th>Status</th>
			</tr>
			<c:forEach items="${grievPrintList}" var="grievList" varStatus="count">
				<tr>
					<td>${count.index+1}</td>
					<td>${grievList.caption}</td>	
					<td>${grievList.exVar3}</td>									
					<td>${grievList.isActive==1 ? 'Active' : 'In-Active'}</td>	
				</tr>
			</c:forEach>
		</table>
	</div>
		<div style="page-break-after: auto;"></div>
	
</body>
</html>