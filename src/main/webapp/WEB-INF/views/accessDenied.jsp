<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>
</head>

<body>

	<!-- Main navbar -->
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<!-- /main navbar -->


	<!-- Page content -->
	<div class="page-content">



		<!-- Main content -->
		<div class="content-wrapper">
			<div class="card">


				<div class="navbar-collapse1 collapse1 text-center w-100"
					id="navbar-footer1">
					<h1>
						<span class="navbar-text"> Access Denied </span>
					</h1>
					<h5>

						<a href="${pageContext.request.contextPath}/dashboard">Home
							Page </a>

					</h5>

				</div>



			</div>

			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>

</body>
</html>