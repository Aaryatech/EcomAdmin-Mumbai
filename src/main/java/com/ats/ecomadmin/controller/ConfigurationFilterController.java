// Created By :- Mahendra Singh
// Created On :- 17-09-2020
// Modified By :- NA
// Modified On :- NA
package com.ats.ecomadmin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.FilterTypes;
import com.ats.ecomadmin.model.GetCatProduct;
import com.ats.ecomadmin.model.GetFilterIds;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@Scope("session")
public class ConfigurationFilterController {

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show UOM List
	@RequestMapping(value = "/configProductAndFilter", method = RequestMethod.GET)
	public String showUomList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "product/configPrdctFltr";

			List<FilterTypes> filterTypeList = new ArrayList<FilterTypes>();

			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			FilterTypes[] filterTypeArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFilterTypes",
					map, FilterTypes[].class);
			filterTypeList = new ArrayList<FilterTypes>(Arrays.asList(filterTypeArr));

			model.addAttribute("filterType", filterTypeList);
			
			List<Category> catList = new ArrayList<>();

			map = new LinkedMultiValueMap<>();
			map.add("compId", compId);
			Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
					Category[].class);
			catList = new ArrayList<Category>(Arrays.asList(catArr));
			model.addAttribute("catList", catList);

			model.addAttribute("title", "Configure Product");

		} catch (Exception e) {
			System.out.println("Execption in /configProcuctAndFilter : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*---------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/getFilterByFilterType", method = RequestMethod.GET)
	@ResponseBody
	public GetFilterIds getFilterByFilterType(HttpServletRequest request, HttpServletResponse response) {
		GetFilterIds beanVal = new GetFilterIds();
		List<MFilter> filterList = new ArrayList<MFilter>();
		try {
			HttpSession session = request.getSession();

			int filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));
			int companyId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("filterTypeId", filterTypeId);
			map.add("compId", companyId);

			MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getFiltersListByTypeId",
					map, MFilter[].class);
			filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

			beanVal.setFilterList(filterList);

			map.add("filterTypeId", filterTypeId);
			String valIds = Constants.getRestTemplate().postForObject(Constants.url + "getFilterIds", map,
					String.class);

			HashSet<String> strIds = new HashSet<String>(Arrays.asList(valIds.split(",")));
			// Remove duplicate Ids

			beanVal.setFilterIds(strIds.toString());

		} catch (Exception e) {
			System.out.println("Execption in /getFilterByFilterType : " + e.getMessage());
			e.printStackTrace();
		}
		return beanVal;
	}
	
	List<ProductMaster> productList = new ArrayList<ProductMaster>();
	@RequestMapping(value = "/getProductsByFilterIds", method = RequestMethod.GET)
	@ResponseBody
	public GetCatProduct getProductsByFilterIds(HttpServletRequest request, HttpServletResponse response) {
		GetCatProduct catPrdct = new GetCatProduct();
		
		try {
			HttpSession session = request.getSession();
			
			int filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));
			int filterIds = Integer.parseInt(request.getParameter("filterId"));
			int optionVal = Integer.parseInt(request.getParameter("optionVal"));
			
			System.out.println("Param--------------------"+filterTypeId+" / "+filterIds+" / "+optionVal);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("filterTypeId", filterTypeId);
			map.add("filterId", filterIds);
			map.add("optionVal", optionVal);
			
			ProductMaster[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getProductsNotConfigure",
					map, ProductMaster[].class);
			productList = new ArrayList<ProductMaster>(Arrays.asList(filterArr));
			
			
			catPrdct.setProductList(productList);
			
			int companyId = (int) session.getAttribute("companyId");
			
			map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);
			
			Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
					Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(catArr));
			catPrdct.setCategoryList(catList);
			
		}catch (Exception e) {
			System.out.println("Execption in /getProductsByFilterIds : " + e.getMessage());
			e.printStackTrace();
		}
		return catPrdct;
		
	}
	
	
	@RequestMapping(value = "/saveProductConfiguration", method = RequestMethod.POST)
	public String saveProductConfiguration(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");	

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String productIdsStr = "";

			String filterId = request.getParameter("filterId");
			int filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));
			int optnValue = Integer.parseInt(request.getParameter("radioConfig"));
			
			String[] productIds = request.getParameterValues("chk");
			if (productIds.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : productIds) {
					sb.append(s).append(",");
				}
				productIdsStr = sb.deleteCharAt(sb.length() - 1).toString();
				System.out.println("Product---" + filterTypeId +"*****"+ filterId +"*****" + productIdsStr);

			}
			
			//System.out.println("List--------------"+productList);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("filterId", filterId);
			map.add("filterTypeId", filterTypeId);
			map.add("prdctIdsStr", productIdsStr);
			
			Info res = Constants.getRestTemplate().postForObject(Constants.url + "configProductWithFilter", map,
					Info.class);

			if (!res.isError()) {
				session.setAttribute("successMsg", res.getMsg());
			} else {
				session.setAttribute("errorMsg", res.getMsg());
			}
			
		}catch (Exception e) {
			System.out.println("Execption in /saveProductConfiguration : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/configProductAndFilter";
		
	}
}
