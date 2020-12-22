<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link
	href="https://fonts.googleapis.com/css2?family=Oxygen:wght@300;400;700&display=swap"
	rel="stylesheet">
<!-- font-family: 'Oxygen', sans-serif; -->


<style>
.white-txt.select2-selection--single {
	color: #FFF !important;
}
</style>
<c:url value="/setCompanyInSess" var="setCompanyInSess"/>	
<!-- Main navbar -->
<div class="navbar navbar-expand-md navbar-dark bg-indigo navbar-static"
	style="padding: 0px; background: #2a3042;">
	<div class="navbar-brand" style="padding: 10px; width: 10%;">
		<a href="${pageContext.request.contextPath}/home"
			class="d-inline-block"> <img
			src="${pageContext.request.contextPath}/resources/images/logo.png"
			alt="" style="width: 70%; height: 100%; padding-left: 10px;">
		</a>
	</div>

	<div class="d-md-none">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbar-mobile">
			<i class="icon-tree5"></i>
		</button>
		<button class="navbar-toggler sidebar-mobile-main-toggle"
			type="button">
			<i class="icon-paragraph-justify3"></i>
		</button>
	</div>
	<div class="collapse navbar-collapse" id="navbar-mobile">
		<ul class="navbar-nav">
			<li class="nav-item"><a href="#"
				class="navbar-nav-link sidebar-control sidebar-main-toggle d-none d-md-block">
					<i class="icon-paragraph-justify3" style="color: #32b0de;"></i>
			</a></li>			
		
			<li class="nav-item dropdown dropdown-user"><a href="javascript:void(0)"
				class="navbar-nav-link d-flex align-items-center dropdown-toggle"
				data-toggle="dropdown" aria-expanded="false"> <i
					class="far fa-building "></i><span style="margin: 0 0 0 5px;">	
					<c:forEach items="${sessionScope.sessCompList}" var="compList">
							<c:if test="${compList.companyId==sessionScope.companyId}">
					${compList.companyName}
					</c:if>
						</c:forEach>
				</span>
			</a>
			<c:if test="${compType==1}">
				<div class="dropdown-menu dropdown-menu-right">
					<c:forEach items="${sessionScope.sessCompList}" var="compList">

						<c:choose>
							<c:when test="${compList.companyId==sessionScope.companyId}">
								<a href="javascript:void(0)" class="dropdown-item"
									onclick="setCompany(${compList.companyId})"
									style="background: #f5f5f5;"><i class="far fa-building "></i>
									${compList.companyName}</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:void(0)" class="dropdown-item"
									onclick="setCompany(${compList.companyId})"><i
									class="far fa-building "></i> ${compList.companyName}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>

				</div>
				</c:if>
				</li>
		</ul>


		<ul class="navbar-nav ml-md-auto">
		
		
			<li class="nav-item"><a href="#" class="navbar-nav-link"> <i
					class="icon-user-lock" style="color: #ffffff;"></i>&nbsp;&nbsp; <span>${sessionScope.loginUser}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</a></li>
			
			<li class="nav-item"><a href="${pageContext.request.contextPath}/changePassword" class="navbar-nav-link"
				title="Change Password"> <i class="icon-unlocked" style="color: #32b0de;"></i>
					<span class="d-md-none ml-2">Change Password</span>
			</a></li>

			<li class="nav-item"><a href="${pageContext.request.contextPath}/logout" class="navbar-nav-link"
				title="Logout"> <i class="icon-switch2" style="color: #32b0de;"></i>
					<span class="d-md-none ml-2">Logout</span>
			</a></li>
		</ul>
	</div>
</div>
<!-- /main navbar -->
<script>
function setCompany(companyId) {
	 
	var fd = new FormData();
	fd.append('companyId', companyId); 
	$.ajax({
		url : '${pageContext.request.contextPath}/setCompanyInSess',
		type : 'post',
		dataType : 'json',
		data : fd,
		contentType : false,
		processData : false,
		success : function(response) {			
			location.reload(true);
		},
	});
	  
}
</script>


<script>
$(document).ready(function() {
	$("body").removeClass("sidebar-xs");
});

	function imgError(image) {
		image.onerror = "";
		image.src = "${pageContext.request.contextPath}/resources/global_assets/images/default-user.jpg";
		return true;
	}
</script>