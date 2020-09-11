<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="sidebar sidebar-light sidebar-main sidebar-expand-md">

	<!-- Sidebar content -->
	<div class="sidebar-content">


		<!-- Main navigation -->
		<div class="card card-sidebar-mobile">
			<ul class="nav nav-sidebar" data-nav-type="accordion">

				<!-- Main -->
				<!-- <li class="nav-item-header"><div class="text-uppercase font-size-xs line-height-xs">Main</div> <i class="icon-menu" title="Main"></i></li> -->
				<li class="nav-item"><a
					href="${pageContext.request.contextPath}/home"
					class="nav-link active"> <i class="icon-home4"></i> <span>
							Home </span>
				</a></li>




				<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Item Masters</span></a>
					<ul class="nav nav-group-sub" data-submenu-title="Item Masters">

						<%-- <li class="nav-item"><a
							href="${pageContext.request.contextPath}/showAddCategoryDetail"
							class="nav-link">Add Category Details</a></li>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showCategoryDetailList"
							class="nav-link">Category Detail List</a></li> --%>

						<%-- <li class="nav-item"><a
							href="${pageContext.request.contextPath}/showItemDetailList"
							class="nav-link">Add Sub Category Details</a></li>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showItemDetailList"
							class="nav-link">Sub Category Detail List</a></li> --%>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showCategoryList"
							class="nav-link">Category / Sub-Category List</a></li>
						
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showTasteCategory"
							class="nav-link">Taste Category List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/addTasteCategory"
							class="nav-link">Add Taste Category</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showTasteTypeList"
							class="nav-link">Taste List</a></li>
						
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/addTasteType"
							class="nav-link">Add Taste</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showTagList"
							class="nav-link">Tag List</a></li>
						
						<%-- <li class="nav-item"><a
							href="${pageContext.request.contextPath}/addTag"
							class="nav-link">Add Tag</a></li> --%>


						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showAddItemDetail"
							class="nav-link">Add Item Details</a></li>
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showItemDetailList"
							class="nav-link">Item Detail List</a></li>
						
						<%-- <li class="nav-item"><a
							href="${pageContext.request.contextPath}/configTagItems"
							class="nav-link">Configure Items And Tag</a></li> --%>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showRelatedProductsList"
							class="nav-link">Related Product Configuration</a></li>


					</ul></li>
					
					<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Basic Masters</span></a>
					<ul class="nav nav-group-sub" data-submenu-title="Basic Masters">
					
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showDesignationList"
							class="nav-link">Designation List</a></li>
					
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showMnUsers"
							class="nav-link">User List</a></li>
							
						<%-- <li class="nav-item"><a
							href="${pageContext.request.contextPath}/showCompanies"
							class="nav-link">Company List</a></li> --%>
							
							

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showLanguage"
							class="nav-link">Language List</a></li>
						
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showDeliveryInstructn"
							class="nav-link">Delivery Instruction List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showGrievencesTypeIntructn"
							class="nav-link">Grievance Type Instruction List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showGrievences"
							class="nav-link">Grievance List</a></li>	

					</ul></li>
					
					
					<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Franchise</span></a>
					<ul class="nav nav-group-sub" data-submenu-title="Franchise">
					
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showCities"
							class="nav-link">City List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showArea"
							class="nav-link">Area List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showFranchiseeConfiguration"
							class="nav-link">Franchise Configuration List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showConfiguredItemList"
							class="nav-link">Item Configuration List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/newItemsConfiguration/0/0"
							class="nav-link">Item Configuration</a></li>
						
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showOfferConfigurationList"
							class="nav-link">Franchise Offer Configuration</a></li>	
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showAgentsList"
							class="nav-link">Agents List</a></li>

					</ul></li>

					<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Offer Masters</span></a>
					<ul class="nav nav-group-sub" data-submenu-title="Offer Masters">
					
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/addNewOffers/0"
							class="nav-link">Add Offer</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showOfferList"
							class="nav-link">Offer List</a></li>	
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showRemarkList"
							class="nav-link">Remark List</a></li>	
						
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showDeliveryChargesList"
							class="nav-link">Delivery Charges List</a></li>	

					</ul></li>
					
					 <li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Reports</span></a>
					
					<ul class="nav nav-group-sub" data-submenu-title="Reports">
					
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showReports"
							class="nav-link">Admin Reports</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/getWalletReportByCustAndDate"
							class="nav-link">Wallet Reports</a></li>
						
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/getFrDateWiseOrderReport"
							class="nav-link">Franchise Wise Order Date Report</a></li>

					</ul>
					
					</li>
					
					
					<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Grievances</span></a>
					
					<ul class="nav nav-group-sub" data-submenu-title="Grievance Information">
					
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showGrievanceList"
							class="nav-link">View Grievances</a></li>
							
							<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showGrievReportPage"
							class="nav-link">Grievance Reports</a></li>
							

					</ul>
					
					</li>

			</ul>
		</div>
		<!-- /main navigation -->

	</div>
	<!-- /sidebar content -->

</div>

