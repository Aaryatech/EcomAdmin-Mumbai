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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.ats.ecomadmin.model.ConfigHomePageProduct;
import com.ats.ecomadmin.model.FilterTypes;
import com.ats.ecomadmin.model.GetCatProduct;
import com.ats.ecomadmin.model.GetFilterIds;
import com.ats.ecomadmin.model.GetPrdctHomePageCat;
import com.ats.ecomadmin.model.GetTaxCakeShapeList;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.ProductHomPage;
import com.ats.ecomadmin.model.ProductHomePageDetail;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.Tax;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@Scope("session")
public class ConfigurationFilterController {

	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Product And Filter Configure Page
	@RequestMapping(value = "/configProductAndFilter", method = RequestMethod.GET)
	public String configProductAndFilter(HttpServletRequest request, HttpServletResponse response, Model model) {

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
	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Get Filter By Filter Type Id
	@RequestMapping(value = "/getFilterByFilterType", method = RequestMethod.GET)
	@ResponseBody
	public List<MFilter> getFilterByFilterType(HttpServletRequest request, HttpServletResponse response) {

		List<MFilter> filterList = new ArrayList<MFilter>();
		try {
			HttpSession session = request.getSession();

			int filterTypeId = 0;
			try {
				filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			int companyId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("filterTypeId", filterTypeId);
			map.add("compId", companyId);

			MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getFiltersListByTypeId",
					map, MFilter[].class);
			filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

		} catch (Exception e) {
			System.out.println("Execption in /getFilterByFilterType : " + e.getMessage());
			e.printStackTrace();
		}
		return filterList;
	}

	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Get Products By Filter Id
	List<ProductMaster> productList = new ArrayList<ProductMaster>();

	@RequestMapping(value = "/getProductsByFilterIds", method = RequestMethod.GET)
	@ResponseBody
	public GetCatProduct getProductsByFilterIds(HttpServletRequest request, HttpServletResponse response) {
		GetCatProduct catPrdct = new GetCatProduct();

		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");

			int filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));
			int filterIds = Integer.parseInt(request.getParameter("filterId"));
			int optionVal = Integer.parseInt(request.getParameter("optionVal"));

			System.out.println("Param--------------------" + filterTypeId + " / " + filterIds + " / " + optionVal);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("filterTypeId", filterTypeId);
			map.add("filterId", filterIds);
			map.add("optionVal", optionVal);
			map.add("compId", companyId);
			ProductMaster[] filterArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getProductsNotConfigure", map, ProductMaster[].class);
			productList = new ArrayList<ProductMaster>(Arrays.asList(filterArr));

			catPrdct.setProductList(productList);

			map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);

			Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
					Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(catArr));
			catPrdct.setCategoryList(catList);

		} catch (Exception e) {
			System.out.println("Execption in /getProductsByFilterIds : " + e.getMessage());
			e.printStackTrace();
		}
		return catPrdct;

	}

	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Configure Products And Filter
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
				// System.out.println("Product---" + filterTypeId + "*****" + filterId + "*****"
				// + productIdsStr);

			}

			// System.out.println("List--------------"+productList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("filterId", filterId);
			map.add("filterTypeId", filterTypeId);
			map.add("prdctIdsStr", productIdsStr);
			map.add("optnValue", optnValue);

			Info res = Constants.getRestTemplate().postForObject(Constants.url + "configProductWithFilter", map,
					Info.class);

			if (!res.isError()) {
				session.setAttribute("successMsg", res.getMsg());
			} else {
				session.setAttribute("errorMsg", res.getMsg());
			}

		} catch (Exception e) {
			System.out.println("Execption in /saveProductConfiguration : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/configProductAndFilter";

	}

	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Product And Filter Configure Page
	@RequestMapping(value = "/configProductTaxAndCakeShape", method = RequestMethod.GET)
	public String configProductTaxAndCakeShape(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "product/configPrdctTaxCakShp";
			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");

			model.addAttribute("title", "Configure Product, Tax And Cake Shape");

		} catch (Exception e) {
			System.out.println("Execption in /configProductTaxAndCakeShape : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Get Products By Filter Id
	@RequestMapping(value = "/getFilterConfigList", method = RequestMethod.GET)
	@ResponseBody
	public List<GetTaxCakeShapeList> getFilterConfigList(HttpServletRequest request, HttpServletResponse response) {

		List<GetTaxCakeShapeList> list = new ArrayList<GetTaxCakeShapeList>();

		try {
			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");

			int typeConfigId = Integer.parseInt(request.getParameter("typeConfigId"));
			// int filterIds = Integer.parseInt(request.getParameter("filterId"));
			// int optionVal = Integer.parseInt(request.getParameter("optionVal"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			if (typeConfigId == 1) {

				map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				Tax[] tagArr = Constants.getRestTemplate().postForObject(Constants.url + "getTaxes", map, Tax[].class);
				List<Tax> taxList = new ArrayList<Tax>(Arrays.asList(tagArr));
				System.err.println("List Of---------" + taxList);
				for (int i = 0; i < taxList.size(); i++) {
					GetTaxCakeShapeList taxCake = new GetTaxCakeShapeList();

					taxCake.setValueId(taxList.get(i).getTaxId());
					taxCake.setValueName(taxList.get(i).getTaxName());

					list.add(taxCake);
				}

			} else if (typeConfigId == 3) {
				map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				map.add("filterTypeId", 1);

				MFilter[] filterArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getFiltersListByTypeId", map, MFilter[].class);
				List<MFilter> filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

				for (int i = 0; i < filterList.size(); i++) {
					GetTaxCakeShapeList taxCake = new GetTaxCakeShapeList();

					taxCake.setValueId(filterList.get(i).getFilterId());
					taxCake.setValueName(filterList.get(i).getFilterName());

					list.add(taxCake);
				}
			}

		} catch (Exception e) {
			System.out.println("Execption in /getProductsByFilterIds : " + e.getMessage());
			e.printStackTrace();
		}
		return list;

	}

	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Get Products By Config Filter Id
	@RequestMapping(value = "/getProductsByConfigTypeId", method = RequestMethod.GET)
	@ResponseBody
	public GetCatProduct getProductsConfigTypeId(HttpServletRequest request, HttpServletResponse response) {
		GetCatProduct catPrdct = new GetCatProduct();

		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");

			int typeConfigId = Integer.parseInt(request.getParameter("typeConfigId"));
			int filterId = Integer.parseInt(request.getParameter("filterId"));
			int optionVal = Integer.parseInt(request.getParameter("radioConfig"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map = new LinkedMultiValueMap<>();
			map.add("filterId", filterId);
			map.add("typeConfigId", typeConfigId);
			map.add("compId", companyId);
			map.add("optionVal", optionVal);

			ProductMaster[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getProductsByTaxId",
					map, ProductMaster[].class);
			productList = new ArrayList<ProductMaster>(Arrays.asList(filterArr));

			catPrdct.setProductList(productList);

			map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);

			Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
					Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(catArr));
			catPrdct.setCategoryList(catList);

		} catch (Exception e) {
			System.out.println("Execption in /getProductsByFilterIds : " + e.getMessage());
			e.printStackTrace();
		}
		return catPrdct;

	}

	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Configure Products with Cake Shape, Tax, Return % etc.
	@RequestMapping(value = "/saveProductTaxCakeConfig", method = RequestMethod.POST)
	public String saveProductTaxCakeConfig(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String productIdsStr = "";
			float returnVal = 0;

			String filterId = request.getParameter("filterId");
			int typeConfigId = Integer.parseInt(request.getParameter("typeConfigId"));
			int optnValue = Integer.parseInt(request.getParameter("radioConfig"));

			try {
				returnVal = Float.parseFloat(request.getParameter("returnPer"));
			} catch (Exception e) {
				returnVal = 0;
			}

			String[] productIds = request.getParameterValues("chk");
			if (productIds.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : productIds) {
					sb.append(s).append(",");
				}
				productIdsStr = sb.deleteCharAt(sb.length() - 1).toString();
				System.out.println("Product---" + typeConfigId + "*****" + filterId + "*****" + productIdsStr);

			}

			Info res = new Info();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			// map.add("optnValue", optnValue);
			if (typeConfigId == 2) {

				map.add("prdctIdsStr", productIdsStr);
				map.add("returnVal", returnVal);
				res = Constants.getRestTemplate().postForObject(Constants.url + "configProductReturnPer", map,
						Info.class);
			} else if (typeConfigId == 4 || typeConfigId == 5) {

				map.add("prdctIdsStr", productIdsStr);
				map.add("typeConfigId", typeConfigId);
				res = Constants.getRestTemplate().postForObject(Constants.url + "configProductStatus", map, Info.class);
			} else {
				map.add("filterId", filterId);
				map.add("typeConfigId", typeConfigId);
				map.add("prdctIdsStr", productIdsStr);
				map.add("optnValue", optnValue);
				res = Constants.getRestTemplate().postForObject(Constants.url + "configProductOtherFilter", map,
						Info.class);
			}

			if (!res.isError()) {
				session.setAttribute("successMsg", res.getMsg());
			} else {
				session.setAttribute("errorMsg", res.getMsg());
			}

		} catch (Exception e) {
			System.out.println("Execption in /saveProductConfiguration : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/configProductTaxAndCakeShape";

	}

	/*----------------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :-Show Home Page Product Config List
	@RequestMapping(value = "/showHomePagePrdctConfig", method = RequestMethod.GET)
	public String showHomePagePrdctConfig(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "product/configHomPagPrdctList";

			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("compId", compId);
			ProductHomPage[] homePageArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getHomePageConfigProductList", map, ProductHomPage[].class);
			List<ProductHomPage> homePageList = new ArrayList<ProductHomPage>(Arrays.asList(homePageArr));

			for (int i = 0; i < homePageList.size(); i++) {

				homePageList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(homePageList.get(i).getHomePageStatusId())));
			}
			model.addAttribute("homePageList", homePageList);

			model.addAttribute("title", "Home Page Product Config List");

		} catch (Exception e) {
			System.out.println("Execption in /showHomePagePrdctConfig : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :-Configure Product with home page
	@RequestMapping(value = "/configHomePageProduct", method = RequestMethod.GET)
	public String configHomePagePrdct(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "product/configHomePagePrdct";

			List<FilterTypes> filterTypeList = new ArrayList<FilterTypes>();

			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");

			ProductHomPage homePage = new ProductHomPage();
			model.addAttribute("homePage", homePage);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			ProductHomPage[] homePageArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getHomePageSatusList", map, ProductHomPage[].class);
			List<ProductHomPage> homePageList = new ArrayList<ProductHomPage>(Arrays.asList(homePageArr));

			model.addAttribute("homePageList", homePageList);

			map = new LinkedMultiValueMap<>();
			map.add("compId", compId);
			map.add("filterTypeId", 5);

			MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFilterForConfig",
					map, MFilter[].class);
			List<MFilter> filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

			model.addAttribute("filterList", filterList);

			model.addAttribute("isEdit", 0);

			model.addAttribute("title", "Product Home Page Configuration");

		} catch (Exception e) {
			System.out.println("Execption in /configHomePageProduct : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :-Get Product For Home Page Status Configuration
	@RequestMapping(value = "/getConfigProductsByStatusId", method = RequestMethod.GET)
	@ResponseBody
	public GetPrdctHomePageCat getConfigProductsByStatusId(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		GetPrdctHomePageCat catPrdct = new GetPrdctHomePageCat();
		try {

			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");
			String filterStatus = request.getParameter("filterStatus");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map = new LinkedMultiValueMap<>();
			map.add("compId", compId);
			map.add("statusType", filterStatus);

			ConfigHomePageProduct[] filterArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getProductStatusConfigList", map, ConfigHomePageProduct[].class);
			List<ConfigHomePageProduct> list = new ArrayList<ConfigHomePageProduct>(Arrays.asList(filterArr));
			catPrdct.setProductList(list);

			List<Category> catList = new ArrayList<>();

			map = new LinkedMultiValueMap<>();
			map.add("compId", compId);
			Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
					Category[].class);
			catList = new ArrayList<Category>(Arrays.asList(catArr));
			catPrdct.setCategoryList(catList);

		} catch (Exception e) {
			System.out.println("Execption in /getConfigProductsByStatusId : " + e.getMessage());
			e.printStackTrace();
		}
		return catPrdct;
	}

	// Created By :- Mahendra Singh
	// Created On :- 17-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Configure Products Home Page.
	@RequestMapping(value = "/configurPrdctHomePage", method = RequestMethod.POST)
	public String configurPrdctHomePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String productIdsStr = "";

			int filterStatus = Integer.parseInt(request.getParameter("filterStatus"));
			int sortNo = Integer.parseInt(request.getParameter("statusSortNo"));
			int isActve = Integer.parseInt(request.getParameter("activeStat"));
			int homePageSatusId = Integer.parseInt(request.getParameter("homePageStatusId"));

			String[] productIds = request.getParameterValues("chk");
			if (productIds.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : productIds) {
					sb.append(s).append(",");
				}
				productIdsStr = sb.deleteCharAt(sb.length() - 1).toString();
				System.out.println(
						"Product---" + sortNo + "*****" + filterStatus + "*****[" + productIdsStr + "]*****" + isActve+ "*****" + homePageSatusId);

			}
			int isEdit = Integer.parseInt(request.getParameter("isEdit"));
			if (isEdit == 0) {
				ProductHomPage prdctHomeHead = new ProductHomPage();

				prdctHomeHead.setHomePageStatusId(homePageSatusId);
				prdctHomeHead.setSortNo(sortNo);
				prdctHomeHead.setStatusId(filterStatus);
				prdctHomeHead.setIsActive(isActve);
				prdctHomeHead.setProductId(0);
				prdctHomeHead.setCompanyId(userObj.getCompanyId());
				prdctHomeHead.setDelStatus(1);
				prdctHomeHead.setExInt1(0);
				prdctHomeHead.setExInt2(0);
				prdctHomeHead.setExVar1("NA");
				prdctHomeHead.setExVar2("NA");

				List<ProductHomePageDetail> homePageDtl = new ArrayList<ProductHomePageDetail>();
				if (productIds.length > 0) {
					for (int i = 0; i < productIds.length; i++) {

						ProductHomePageDetail prdctHomeDtl = new ProductHomePageDetail();
						prdctHomeDtl.setExInt1(0);
						prdctHomeDtl.setExInt2(0);
						prdctHomeDtl.setExVar1("NA");
						prdctHomeDtl.setExVar2("NA");
						prdctHomeDtl.setHomePageStatusId(0);
						prdctHomeDtl.setHpStatusDetailId(0);
						prdctHomeDtl.setProductId(Integer.parseInt(productIds[i]));
						prdctHomeDtl.setSortNo(Integer.parseInt(request.getParameter("prdctSortNo" + productIds[i])));
						homePageDtl.add(prdctHomeDtl);
					}
				}
				prdctHomeHead.setPrdctHomeList(homePageDtl);
				System.out.println("Detail-----------" + prdctHomeHead);

				ProductHomPage res = Constants.getRestTemplate()
						.postForObject(Constants.url + "savePrdctHomePageConfige", prdctHomeHead, ProductHomPage.class);

				if (res.getHomePageStatusId() > 0) {
					session.setAttribute("successMsg", "Configuration Saved Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Saved Configuration");
				}
			} else {
				System.err.println("In Else");
				try {
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

					map = new LinkedMultiValueMap<>();
					map.add("configStatusId", homePageSatusId);

					Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteHomePageStatusDtl", map,
							Info.class);
					System.err.println("res----"+res);
					
					
						map = new LinkedMultiValueMap<>();
						map.add("configStatusId", homePageSatusId);
						map.add("sortNo", sortNo);
						map.add("isActve", isActve);

						Info val = Constants.getRestTemplate()
								.postForObject(Constants.url + "updatePrdctHomePageSortNo", map, Info.class);

						if (!val.isError()) {
							List<ProductHomePageDetail> homePageDtlList = new ArrayList<ProductHomePageDetail>();
							if (productIds.length > 0) {
								for (int i = 0; i < productIds.length; i++) {

									ProductHomePageDetail prdctHomeDtl = new ProductHomePageDetail();
									prdctHomeDtl.setExInt1(0);
									prdctHomeDtl.setExInt2(0);
									prdctHomeDtl.setExVar1("NA");
									prdctHomeDtl.setExVar2("NA");
									prdctHomeDtl.setHomePageStatusId(homePageSatusId);
									prdctHomeDtl.setHpStatusDetailId(0);
									prdctHomeDtl.setProductId(Integer.parseInt(productIds[i]));
									prdctHomeDtl.setSortNo(
											Integer.parseInt(request.getParameter("prdctSortNo" + productIds[i])));
									homePageDtlList.add(prdctHomeDtl);
								}

							}
							List<ProductHomePageDetail> saveDtl = Constants.getRestTemplate().postForObject(
									Constants.url + "saveHomePagePrdctConfigDtl", homePageDtlList, List.class);

							if (saveDtl.get(0).getHpStatusDetailId() > 0) {
								session.setAttribute("successMsg", "Configuration Update Successfully");
							} else {
								session.setAttribute("errorMsg", "Failed to Update Configuration");
							}
						}
					
				} catch (Exception e) {
					e.getMessage();
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /saveProductConfiguration : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showHomePagePrdctConfig";

	}

	@RequestMapping(value = "/editHomePagePrdctConfig", method = RequestMethod.GET)
	public String editHomePagePrdctConfig(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			mav = "product/configHomePagePrdct";

			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			String base64encodedString = request.getParameter("homePageStatusId");
			String homePageStatusId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("id", homePageStatusId);

			ProductHomPage configHomePage = Constants.getRestTemplate()
					.postForObject(Constants.url + "getPrdctHomePageById", map, ProductHomPage.class);
			model.addAttribute("homePage", configHomePage);

			map = new LinkedMultiValueMap<>();
			map.add("compId", userObj.getCompanyId());
			map.add("filterTypeId", 5);

			MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFilterForConfig",
					map, MFilter[].class);
			List<MFilter> filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

			model.addAttribute("filterList", filterList);

			model.addAttribute("isEdit", 1);
		} catch (Exception e) {
			System.out.println("Execption in /editHomePagePrdctConfig : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;

	}

	// Created By :- Mahendra Singh
	// Created On :- 22-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Home Page Product Config
	@RequestMapping(value = "/deleteHomePagePrdctConfig", method = RequestMethod.GET)
	public String deleteHomePagePrdctConfig(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		try {
			String base64encodedString = request.getParameter("homePageStatusId");
			String homePageStatusId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("id", homePageStatusId);

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteHomePagePrdct", map,
					Info.class);

			if (!info.isError()) {
				session.setAttribute("successMsg", info.getMsg());
			} else {
				session.setAttribute("errorMsg", info.getMsg());
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteHomePagePrdctConfig : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showHomePagePrdctConfig";
	}
}
