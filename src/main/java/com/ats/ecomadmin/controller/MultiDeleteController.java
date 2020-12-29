package com.ats.ecomadmin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.Info;


//Akhilesh 2020-12-29 For Multi Delete 
@Controller
@Scope("session")
public class MultiDeleteController {
	
	
	
	
	
	// Created By :- Mahendra Singh
		// Created On :- 26-12-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Delete Selected Citys
		@RequestMapping(value = "/deleteSelCitys", method = RequestMethod.GET)
		public @ResponseBody Info deleteSelCitys(HttpServletRequest request, HttpServletResponse response) {
			System.err.println("in deleteSelCitys ");
			Info info = new Info();
			List<Integer> cityIds = new ArrayList<>();
			String a = "";
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			try {

				map.add("tableName", "mn_city");
				map.add("columnName", "del_status");
				map.add("value", 0);
				map.add("id", "city_id");

				String cityIdArr = request.getParameter("cityIds");

				cityIdArr = cityIdArr.substring(1, cityIdArr.length() - 1);
				cityIdArr = cityIdArr.replaceAll("\"", "");

				System.out.println(cityIdArr);
				map.add("cityIdArr", cityIdArr);
				info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
				if (info.isError()) {
					info.setError(true);
					info.setMsg("Unable To Delete Cities");
				} else {
					info.setError(true);
					info.setMsg("Cities Deleted");
				}
				
			} catch (Exception e) {
				info.setError(true);
				info.setMsg("Unable To Delete Cities Exception Occuered");
				System.out.println("Execption in /deleteSelCitys : " + e.getMessage());
				e.printStackTrace();
			}
			return info;

		}

		public String getCommaSepStringFromStrArray(String[] strArray) {

			String commaSepString = new String();

			try {

				StringBuilder sb = new StringBuilder();

				for (int j = 0; j < strArray.length; j++) {
					sb = sb.append(strArray[j] + ",");
				}

				commaSepString = sb.toString();
				commaSepString = commaSepString.substring(0, commaSepString.length() - 1);

			} catch (Exception e) {

				commaSepString = new String();
			}

			return commaSepString;

		}
		
		
		// Created By :- Akhilesh
				// Created On :- 28-12-2020
				// Modified By :- NA
				// Modified On :- NA
				// Description :- Delete Selected Multiple Routes Type
				@RequestMapping(value = "/deleteSelRoutesType", method = RequestMethod.GET)
				public @ResponseBody Info deleteSelRoutesType(HttpServletRequest request, HttpServletResponse response) {
					System.err.println("in deleteSelRoutes Types ");
					Info info = new Info();
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					try {

						map.add("tableName", "m_route_type");
						map.add("columnName", "del_status");
						map.add("value", 0);
						map.add("id", "route_type_id");

						String routeTypedArr = request.getParameter("routrTypeIds");

						routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
						routeTypedArr = routeTypedArr.replaceAll("\"", "");

						System.out.println(routeTypedArr);
						map.add("cityIdArr", routeTypedArr);
						info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
						if (info.isError()) {
							info.setError(true);
							info.setMsg("Unable To Delete Routes");
						} else {
							info.setError(true);
							info.setMsg("Cities Routes");
						}
						
					} catch (Exception e) {
						info.setError(true);
						info.setMsg("Unable To Delete Routes Exception Occuered");
						System.out.println("Execption in /deleteSelRoutesType : " + e.getMessage());
						e.printStackTrace();
					}
					return info;

				}
		
				
				
				
				// Created By :- Akhilesh
							// Created On :- 28-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Routes Delivery
							@RequestMapping(value = "/deleteSelRouteDelivery", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelRouteDelivery(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelRouteDelivery ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_route_delivery");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "rouid_delvery_id");

									String routeTypedArr = request.getParameter("routeDelivryIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Routes");
									} else {
										info.setError(true);
										info.setMsg("Cities Routes");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Routes Exception Occuered");
									System.out.println("Execption in /deleteSelRouteDelivery : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
					
							
							
							
							// Created By :- Akhilesh
							// Created On :- 28-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Routes
							@RequestMapping(value = "/deleteSelRoutes", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelRoutes(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelRoutes ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_route");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "route_id");

									String routeTypedArr = request.getParameter("routrTypeIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Routes");
									} else {
										info.setError(true);
										info.setMsg("Cities Routes");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Routes Exception Occuered");
									System.out.println("Execption in /deleteSelRoutes : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							
							
							// Created By :- Akhilesh
							// Created On :- 28-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Franchises
							@RequestMapping(value = "/deleteSelMultiFranchises", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiFranchises(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiFranchises ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_franchise");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "fr_id");

									String routeTypedArr = request.getParameter("frIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Routes");
									} else {
										info.setError(true);
										info.setMsg("Cities Routes");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Routes Exception Occuered");
									System.out.println("Execption in /deleteSelMultiFranchises : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							
							
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Filter  type
							@RequestMapping(value = "/deleteSelMultiFiltertype", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiFiltertype(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiFiltertype ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_filter_type");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "filter_type_id");

									String routeTypedArr = request.getParameter("fTypeIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Routes");
									} else {
										info.setError(true);
										info.setMsg("Cities Routes");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Routes Exception Occuered");
									System.out.println("Execption in /deleteSelMultiFiltertype : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
					
							
							
							
							
							// Created By :- Akhilesh
							// Created On :- 28-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Delivery boy
							@RequestMapping(value = "/deleteSelMultiDeliveryBoy", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiDeliveryBoy(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiFranchises ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_delivery_boy");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "del_boy_id");

									String routeTypedArr = request.getParameter("dBoyIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Routes");
									} else {
										info.setError(true);
										info.setMsg("Cities Routes");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Routes Exception Occuered");
									System.out.println("Execption in /deleteSelMultiDeliveryBoy : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Category
							@RequestMapping(value = "/deleteSelMultiCategory", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiCategory(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiCategory ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_category");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "cat_id");

									String routeTypedArr = request.getParameter("catIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Routes");
									} else {
										info.setError(true);
										info.setMsg("Cities Routes");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Category Exception Occuered");
									System.out.println("Execption in /deleteSelMultiCategory : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
		
		
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Sub-Category
							@RequestMapping(value = "/deleteSelMultiSubCategory", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiSubCategory(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiSubCategory ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_sub_category");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "sub_cat_id");

									String routeTypedArr = request.getParameter("subCatIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Sub-Category");
									} else {
										info.setError(true);
										info.setMsg("Sub-Categories Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Sub-Category Exception Occuered");
									System.out.println("Execption in /deleteSelMultiSubCategory : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							
							
							
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Tax
							@RequestMapping(value = "/deleteSelMultiTax", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiTax(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiTax ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_tax");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "tax_id");

									String routeTypedArr = request.getParameter("taxIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Taxes");
									} else {
										info.setError(true);
										info.setMsg("Taxes Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Tax Exception Occuered");
									System.out.println("Execption in /deleteSelMultiTax : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple UOM
							@RequestMapping(value = "/deleteSelMultiUOM", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiUOM(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiUOM ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_uom");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "uom_id");

									String routeTypedArr = request.getParameter("uomIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete UOM");
									} else {
										info.setError(true);
										info.setMsg("UOM Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete UOM Exception Occuered");
									System.out.println("Execption in /deleteSelMultiUOM : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Multiple Filter
							@RequestMapping(value = "/deleteSelMultiFilter1", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiFilter(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiUOM ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								/*try {*/
								//	System.err.println(request.getParameter("filterIds"));
									//System.err.println(request.getParameter("filterTypeId"));
								
								//int filterTypeId=Integer.parseInt(request.getParameter("filterTypeId"));
								//System.err.println(filterTypeId+filterIds);
									//System.err.println("Filter Type==>"+filterTypeId);
									//map.add("tableName", "m_filter");
									//map.add("columnName", "del_status");
									//map.add("value", 0);
									//map.add("id", "filter_id");
									//map.add("filterTypeId", filterTypeId);

									//String routeTypedArr = request.getParameter("filterIds");

									//routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									//routeTypedArr = routeTypedArr.replaceAll("\"", "");

									//System.out.println(routeTypedArr);
									//map.add("cityIdArr", routeTypedArr);
								//	info = Constants.getRestTemplate().postForObject(Constants.url + "multiDeleteForFilter", map, Info.class);
								/*	if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Filter");
									} else {
										info.setError(true);
										info.setMsg("Filter Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Filter Exception Occuered");
									System.out.println("Execption in /deleteSelMultiFilter : " + e.getMessage());
									e.printStackTrace();
								}*/
								return info;

							}
		
		
							@RequestMapping(value = "/filterDelete", method = RequestMethod.GET)
							public void filterDelete(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("In Filter Delete");
							}
							
		
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Related Product Config
							@RequestMapping(value = "/deleteSelMultiRelatedProd", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiRelatedProd(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiRelatedProd ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_product");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "product_id");

									String routeTypedArr = request.getParameter("prodIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Product");
									} else {
										info.setError(true);
										info.setMsg("Product Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Product Exception Occuered");
									System.out.println("Execption in /deleteSelMultiRelatedProd : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
	
							
							
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Related Product And Festive Event
							@RequestMapping(value = "/deleteSelMultiFestiveEvents", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiFestiveEvents(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiFestiveEvents ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "m_festive_events");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "event_id");

									String routeTypedArr = request.getParameter("prodIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Events");
									} else {
										info.setError(true);
										info.setMsg("Events Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Events Exception Occuered");
									System.out.println("Execption in /deleteSelMultiFestiveEvents : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Related Offer
							@RequestMapping(value = "/deleteSelMultiOffer", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiOffer(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiOffer ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "mn_offer_header");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "offer_id");

									String routeTypedArr = request.getParameter("offerIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Offer");
									} else {
										info.setError(true);
										info.setMsg("Offer Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Offer Exception Occuered");
									System.out.println("Execption in /deleteSelMultiOffer : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							
							
							
							
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Grievances Type
							@RequestMapping(value = "/deleteSelMultiGrievances", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiGrievances(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiGrievances ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "mn_grievences_type_instructn");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "grev_type_id");

									String routeTypedArr = request.getParameter("griTypeIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Grievances");
									} else {
										info.setError(true);
										info.setMsg("Grievances Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Grievances Exception Occuered");
									System.out.println("Execption in /deleteSelMultiGrievances : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
							// Created By :- Akhilesh
							// Created On :- 29-12-2020
							// Modified By :- NA
							// Modified On :- NA
							// Description :- Delete Selected Grievances Instruction
							@RequestMapping(value = "/deleteSelMultiGrievancesIns", method = RequestMethod.GET)
							public @ResponseBody Info deleteSelMultiGrievancesIns(HttpServletRequest request, HttpServletResponse response) {
								System.err.println("in deleteSelMultiGrievancesIns ");
								Info info = new Info();
								MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
								try {

									map.add("tableName", "mn_grievences_type_instructn");
									map.add("columnName", "del_status");
									map.add("value", 0);
									map.add("id", "grev_type_id");

									String routeTypedArr = request.getParameter("griTypeIds");

									routeTypedArr = routeTypedArr.substring(1, routeTypedArr.length() - 1);
									routeTypedArr = routeTypedArr.replaceAll("\"", "");

									System.out.println(routeTypedArr);
									map.add("cityIdArr", routeTypedArr);
									info = Constants.getRestTemplate().postForObject(Constants.url + "multiDelete", map, Info.class);
									if (info.isError()) {
										info.setError(true);
										info.setMsg("Unable To Delete Grievances Instruction");
									} else {
										info.setError(true);
										info.setMsg("Grievances Instruction Deleted");
									}
									
								} catch (Exception e) {
									info.setError(true);
									info.setMsg("Unable To Delete Grievances Instruction Exception Occuered");
									System.out.println("Execption in /deleteSelMultiGrievancesIns : " + e.getMessage());
									e.printStackTrace();
								}
								return info;

							}
	
	

}
