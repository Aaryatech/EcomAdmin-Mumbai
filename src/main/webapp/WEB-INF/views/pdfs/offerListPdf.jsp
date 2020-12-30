<%@page contentType="text/html; charset=ISO8859_1"%>
<%@ page import="java.lang.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
								style="color: #000; font-size: 16px; text-align: center; margin: 0px;">Offer List Pdf</h4>							
								<%-- <p
									style="color: #000; font-size: 15px; text-align: center; font-weight: bold;">
									Date : ${fromDate}&nbsp;&nbsp; to &nbsp;&nbsp;${toDate}</p> --%>
							
						</div>  
						</div>
	<c:set value="" var="daysFnd"/>
		<table align="center" border="1" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131;"
			class="table datatable-header-basic">
			<tr>
				<c:forEach items="${offerIds}" var="offerIds" varStatus="count">

					<c:if test="${offerIds==1}">
						<th width="10%">Sr. No.</th>
					</c:if>

					<c:if test="${offerIds==2}">
						<th>Offer Name</th>
					</c:if>

					<c:if test="${offerIds==3}">
						<th>Type</th>
					</c:if>

					<c:if test="${offerIds==4}">
						<th>Day/Date</th>
					</c:if>

					<c:if test="${offerIds==5}">
						<th>Duration</th>
					</c:if>

					<c:if test="${offerIds==6}">
						<th>Offer On</th>
					</c:if>
				</c:forEach>
			</tr>


			<c:forEach items="${offerPrintList}" var="offer" varStatus="count">
				<tr>
					<c:forEach items="${offerIds}" var="offerIds">

						<c:if test="${offerIds==1}">
							<td>${count.index+1}</td>
						</c:if>

						<c:if test="${offerIds==2}">
							<td>${offer.offerName}</td>
						</c:if>

						<c:if test="${offerIds==3}">
							<td>${offer.type==1 ? 'POS' : 'Online'}</td>
						</c:if>

						<c:if test="${offerIds==4}">
							<td>${offer.frequencyType==1 ? 'Day' : 'Date'}</td>
						</c:if>

						<c:if test="${offerIds==5}">
							<c:choose>
								<c:when test="${offer.frequencyType==1}">


									<td><c:set var="freqStr" value="${offer.frequency}" /> <c:set
											var="arrayofFreq" value="${fn:split(freqStr,',')}" /> <c:set
											var="freq" value="" /> <c:forEach var="i" begin="0"
											end="${fn:length(arrayofFreq)}">
											<c:choose>
												<c:when test="${arrayofFreq[i]==2}">
													<div class="badge">Monday</div>
												</c:when>
												<c:when test="${arrayofFreq[i]==3}">
													<div class="badge">Tuesday</div>
												</c:when>
												<c:when test="${arrayofFreq[i]==4}">
													<div class="badge">Wednesday</div>
												</c:when>
												<c:when test="${arrayofFreq[i]==5}">
													<div class="badge">Thursday</div>
												</c:when>
												<c:when test="${arrayofFreq[i]==6}">
													<div class="badge">Friday</div>
												</c:when>
												<c:when test="${arrayofFreq[i]==7}">
													<div class="badge">Saturday</div>
												</c:when>
												<c:when test="${arrayofFreq[i]==1}">
													<div class="badge">Sunday</div>
												</c:when>



											</c:choose>
										</c:forEach></td>


								</c:when>
								<c:otherwise>



									<c:set var="from" value="${offer.fromDate}" />
									<fmt:parseDate value="${from}" var="parsedfromDate"
										pattern="yyyy-MM-dd" />

									<c:set var="to" value="${offer.toDate}" />
									<fmt:parseDate value="${to}" var="parsedToDate"
										pattern="yyyy-MM-dd" />

									<td><fmt:formatDate type="date" value="${parsedfromDate}" />
										&nbsp;To&nbsp; <fmt:formatDate type="date"
											value="${parsedToDate}" /></td>

								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${offerIds==6}">
							<td>${offer.offerType==1? 'Bill' : 'Item'}</td>
						</c:if>


					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</div>
		<div style="page-break-after: auto;"></div>
	
</body>
</html>