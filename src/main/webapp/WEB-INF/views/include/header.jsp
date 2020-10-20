<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>

<link
	href="https://fonts.googleapis.com/css2?family=Oxygen:wght@300;400;700&display=swap"
	rel="stylesheet">
<!-- font-family: 'Oxygen', sans-serif; -->


<style>
.white-txt.select2-selection--single {
	color: #FFF !important;
}
</style>

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
	function imgError(image) {
		image.onerror = "";
		image.src = "${pageContext.request.contextPath}/resources/global_assets/images/default-user.jpg";
		return true;
	}
</script>