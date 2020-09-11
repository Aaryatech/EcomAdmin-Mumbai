<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ATS Login Page</title>

	<!-- Global stylesheets -->
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/global_assets/css/icons/icomoon/styles.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/assets/css/bootstrap_limitless.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/assets/css/layout.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/assets/css/components.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/assets/css/colors.min.css" rel="stylesheet" type="text/css">

	<link href="${pageContext.request.contextPath}/resources/assets/css/custom.css" rel="stylesheet" type="text/css">
	<!-- /global stylesheets -->

	<!-- Core JS files -->
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/main/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/main/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/loaders/blockui.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/ui/ripple.min.js"></script>
	<!-- /core JS files -->

	<!-- Theme JS files -->
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/forms/styling/uniform.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/assets/js/app.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/login.js"></script>
	<!-- /theme JS files -->

</head>

<body>

	<!-- Page content -->
	<div class="page-content">

		<!-- Main content -->
		<div class="content-wrapper">

			<!-- Content area -->
			<div class="content d-flex justify-content-center align-items-center login_bg">
				<div class="login_bx">
					<div class="card mb-0">
						<div class="card-body">
							<div class="lgn_cont">
								<div class="lgn_cont_l">
									<div class="lgn_logo_cont">
										<img src="${pageContext.request.contextPath}/resources/global_assets/images/powerdBy.png" alt="">
										<p><strong>Welcome to India’s</strong> one of most preferred Ut enim ad minim veniam, <strong>quis nostrud exercitation ullamco</strong> laboris nisi ut aliquip ex ea commodo!!</p>
										<p>Lorem ipsum dolor sit amet, <strong>consectetur adipiscing elit</strong>, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
									</div>
								</div>
								<div class="lgn_cont_r">
									<h2 class="lgn-title">
										<i class="icon-reading icon-2x blue-bor border-3 rounded-round p-3 mt-1"></i>
										<span>Sign into your account </span></h2>

										<div class="form-group form-group-feedback form-group-feedback-left">
											<input type="text" class="form-control" placeholder="Username">
											<div class="form-control-feedback">
												<i class="icon-user text-muted"></i>
											</div>
										</div>

										<div class="form-group form-group-feedback form-group-feedback-left">
											<input id="password-field" type="password" class="form-control" name="password" placeholder="Password" value="">
				              <span toggle="#password-field" class="icon-eye field-icon text-muted toggle-password"></span>
											<div class="form-control-feedback">
												<i class="icon-lock2 text-muted"></i>
											</div>

										</div>

										<div class="form-group d-flex align-items-center">
											<div class="form-check mb-0">
												<label class="form-check-label">
													<input type="checkbox" name="remember" class="form-input-styled" checked data-fouc>
													Remember
												</label>
											</div>

											<a href="login_password_recover.html" class="ml-auto ">Forgot password?</a>
										</div>

										<div class="form-group">
											<button type="submit" class="btn bg-primary btn-block">Sign in <i class="icon-circle-right2 ml-2"></i></button>
										</div>


								</div>
								<div class="clr"></div>
							</div>
						</div>
					</div>

					<!--password-recovery-->
					<div class="card mb-0" style="margin:20px 0 0 0;">
						<div class="card-body">
							<div class="lgn_cont">
								<div class="lgn_cont_l reset">
									<div class="lgn_logo_cont">
									<img src="${pageContext.request.contextPath}/resources/global_assets/images/powerdBy.png" alt="">

										<p><strong>Welcome to the World of Technology
</strong> We are a digital company having expertise in innovating, <strong>designing and producing digital experiences</strong> that clients and employees love to use.</p>
										<p>We encourage our clients to<strong>discover, design and implement opportunities</strong>to use digital technologies to enhance their business.</p>
									</div>
								</div>
								<div class="lgn_cont_r">
									<h2 class="lgn-title">
										<i class="icon-spinner11 icon-2x blue-bor border-3 rounded-round p-3 mt-1"></i>
										<span>Password Recovery </span></h2>

										<div class="form-group form-group-feedback form-group-feedback-right">
											<input type="email" class="form-control" placeholder="Your email">
											<div class="form-control-feedback">
												<i class="icon-mail5 text-muted"></i>
											</div>
										</div>


										<div class="form-group">
											<button type="submit" class="btn bg-primary btn-block"><i class="icon-spinner11 mr-2"></i> Reset Password </button>
										</div>

								</div>
								<div class="clr"></div>
							</div>
						</div>
					</div>

				</div>

				<div class="clr"></div>
				<div class="clr"></div>

			</div>
			<!-- /content area -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->

<!--password eye toggle-->
<script type="text/javascript">
$(".toggle-password").hover(function() {

$(this).toggleClass("icon-eye-blocked");
var input = $($(this).attr("toggle"));
if (input.attr("type") == "password") {
	input.attr("type", "text");
} else {
	input.attr("type", "password");
}
});
</script>

</body>
</html>