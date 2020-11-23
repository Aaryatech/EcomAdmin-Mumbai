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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.CategoryProduct;
import com.ats.ecomadmin.model.ConfigHomePageProduct;
import com.ats.ecomadmin.model.Designation;
import com.ats.ecomadmin.model.FilterTypes;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.GetCatProduct;
import com.ats.ecomadmin.model.GetFilterIds;
import com.ats.ecomadmin.model.GetPrdctHomePageCat;
import com.ats.ecomadmin.model.GetTaxCakeShapeList;
import com.ats.ecomadmin.model.GrievencesInstruction;
import com.ats.ecomadmin.model.HomePageTestimonial;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.ProductHomPage;
import com.ats.ecomadmin.model.ProductHomePageDetail;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.SpDayHomePage;
import com.ats.ecomadmin.model.Tax;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.offer.CateFilterConfig;
import com.ats.ecomadmin.model.offer.FestiveEvent;
import com.ats.ecomadmin.model.offer.GetConfiguredCatAndFilter;

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

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("configProductAndFilter", "configProductAndFilter", "1", "0", "0",
					"0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "product/configPrdctFltr";

				List<FilterTypes> filterTypeList = new ArrayList<FilterTypes>();

				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				FilterTypes[] filterTypeArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllFilterTypes", map, FilterTypes[].class);
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

				Info add = AccessControll.checkAccess("configProductAndFilter", "configProductAndFilter", "0", "1", "0",
						"0", newModuleList);
				Info edit = AccessControll.checkAccess("configProductAndFilter", "configProductAndFilter", "0", "0",
						"1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("configProductAndFilter", "configProductAndFilter", "0", "0",
						"0", "1", newModuleList);

				if (add.isError() == false) {
					model.addAttribute("addAccess", 0);
				}
				if (edit.isError() == false) {
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					model.addAttribute("deleteAccess", 0);
				}
			}

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
		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("configProductAndFilter", "configProductAndFilter", "0", "1", "0",
					"0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

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
				}

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
				mav = "redirect:/configProductAndFilter";
			}
		} catch (Exception e) {
			System.out.println("Execption in /saveProductConfiguration : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;

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

		List<GetTaxCakeShapeList> taxCakeShplist = new ArrayList<GetTaxCakeShapeList>();

		try {
			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");

			int typeConfigId = Integer.parseInt(request.getParameter("typeConfigId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			if (typeConfigId == 1) {

				map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				Tax[] taxArr = Constants.getRestTemplate().postForObject(Constants.url + "getTaxes", map, Tax[].class);
				List<Tax> taxList = new ArrayList<Tax>(Arrays.asList(taxArr));

				for (int i = 0; i < taxList.size(); i++) {
					GetTaxCakeShapeList taxCake = new GetTaxCakeShapeList();

					taxCake.setValueId(taxList.get(i).getTaxId());
					taxCake.setValueName(taxList.get(i).getTaxName());

					taxCakeShplist.add(taxCake);
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

					taxCakeShplist.add(taxCake);
				}
			}

		} catch (Exception e) {
			System.out.println("Execption in /getProductsByFilterIds : " + e.getMessage());
			e.printStackTrace();
		}
		return taxCakeShplist;

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

			ProductMaster[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getProductsByType",
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
			}

			Info res = new Info();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

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

			model.addAttribute("title", "Home Page Product Configure List");

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
			int filterStatus = Integer.parseInt(request.getParameter("filterStatus"));

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

			int filterStatus = Integer.parseInt(request.getParameter("filterStatus"));
			int sortNo = Integer.parseInt(request.getParameter("statusSortNo"));
			int isActve = Integer.parseInt(request.getParameter("activeStat"));
			int homePageSatusId = Integer.parseInt(request.getParameter("homePageStatusId"));

			String[] productIds = request.getParameterValues("chk");

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

					map = new LinkedMultiValueMap<>();
					map.add("configStatusId", homePageSatusId);
					map.add("sortNo", sortNo);
					map.add("isActve", isActve);

					Info val = Constants.getRestTemplate().postForObject(Constants.url + "updatePrdctHomePageSortNo",
							map, Info.class);

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
		
		int btnVal = Integer.parseInt(request.getParameter("btnType"));
		
		if(btnVal==0)
			return "redirect:/showHomePagePrdctConfig";
		else
			return "redirect:/configHomePageProduct";
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
			map.add("homePageStatusId", homePageStatusId);

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

	/*-------------------------------------------------------------------------------*/

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show UOM List
	@RequestMapping(value = "/showHomePageTestimonial", method = RequestMethod.GET)
	public String showUomList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		List<HomePageTestimonial> testimonialList = new ArrayList<HomePageTestimonial>();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showHomePageTestimonial", "showHomePageTestimonial", "1", "0", "0",
					"0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				session.setAttribute("compId", 1);

				int compId = (int) session.getAttribute("companyId");
				mav = "product/testimonialList";

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				HomePageTestimonial[] hmPgTestmonlArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getTestimonials", map, HomePageTestimonial[].class);
				testimonialList = new ArrayList<HomePageTestimonial>(Arrays.asList(hmPgTestmonlArr));

				for (int i = 0; i < testimonialList.size(); i++) {

					testimonialList.get(i).setExVar1(
							FormValidation.Encrypt(String.valueOf(testimonialList.get(i).getTestimonialsId())));
				}
				model.addAttribute("testimonialList", testimonialList);

				model.addAttribute("title", "Home Page Testimonial List");

				Info add = AccessControll.checkAccess("showHomePageTestimonial", "showUomList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showHomePageTestimonial", "showHomePageTestimonial", "0", "0",
						"1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showHomePageTestimonial", "showHomePageTestimonial", "0", "0",
						"0", "1", newModuleList);

				if (add.isError() == false) {
					model.addAttribute("addAccess", 0);
				}
				if (edit.isError() == false) {
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					model.addAttribute("deleteAccess", 0);
				}
			}

		} catch (Exception e) {
			System.out.println("Execption in /showHomePageTestimonial : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 22-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add Home Page Testimonial JSP Page
	@RequestMapping(value = "/newTestimonial", method = RequestMethod.GET)
	public String newUom(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newTestimonial", "showHomePageTestimonial", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "product/addTestimonial";

				HomePageTestimonial testimonial = new HomePageTestimonial();
				model.addAttribute("testimonial", testimonial);

				int compId = (int) session.getAttribute("companyId");

				List<Franchise> frList = new ArrayList<Franchise>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				frList = new ArrayList<Franchise>(Arrays.asList(frArr));
				model.addAttribute("frList", frList);

				map.add("compId", compId);

				Designation[] desigArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getDesignationsByCompId", map, Designation[].class);
				List<Designation> desigList = new ArrayList<Designation>(Arrays.asList(desigArr));

				model.addAttribute("desigList", desigList);

				model.addAttribute("title", "Add Home Page Testimonial");
			}
		} catch (Exception e) {
			System.out.println("Execption in /newTestimonial : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 23-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Add Home Page Testimonial
	@RequestMapping(value = "/insertHomePageTestimonial", method = RequestMethod.POST)
	public String insertSpHomePages(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String profileImage = null;
			
			Info info = new Info();

			int companyId = (int) session.getAttribute("companyId");

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				System.err.println("In If ");

				profileImage = sf.format(date) + "_" + doc.getOriginalFilename();

				try {
				//	new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
					info = ImageUploadController.saveImgFiles(doc, Constants.imageFileExtensions, profileImage);
				} catch (Exception e) {
				}

			} else {
				System.err.println("In else ");
				profileImage = request.getParameter("editImg");

			}
			
			int testimonialId = Integer.parseInt(request.getParameter("testimonialId"));
			
			if (info.isError()) {
				session.setAttribute("errorMsg", "Invalid Image Formate");
				if(testimonialId>0) {
					mav = "redirect:/editTestimonial?testimonialId=" + FormValidation.Encrypt(String.valueOf(testimonialId)); 
				}else{
					mav = "redirect:/newTestimonial";
				}
				 
			} else {

			HomePageTestimonial testimonial = new HomePageTestimonial();

			

			if (testimonialId > 0) {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("testimonialId", testimonialId);
				HomePageTestimonial res = Constants.getRestTemplate()
						.postForObject(Constants.url + "getTestimonialsById", map, HomePageTestimonial.class);

				testimonial.setInsertDateTime(res.getInsertDateTime());
				testimonial.setInsertUserId(res.getInsertUserId());
			}

			String frIdsStr = "";
			String[] frIds = request.getParameterValues("frIds");
			if (frIds.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : frIds) {
					sb.append(s).append(",");
				}
				frIdsStr = sb.deleteCharAt(sb.length() - 1).toString();

			}

			testimonial.setTestimonialsId(testimonialId);
			testimonial.setCaptionName(request.getParameter("caption"));
			testimonial.setName(request.getParameter("testimonial_name"));
			testimonial.setMessages(request.getParameter("messages"));
			testimonial.setDesignation(Integer.parseInt(request.getParameter("designation")));
			testimonial.setFrIds(frIdsStr);
			testimonial.setImages(profileImage);

			if (testimonialId == 0) {
				testimonial.setInsertDateTime(sf.format(date));
				testimonial.setInsertUserId(userObj.getUserId());
			} else {
				testimonial.setUpdateDateTime(sf.format(date));
				testimonial.setUpdateUserId(userObj.getUserId());
			}

			testimonial.setSortNo(Integer.parseInt(request.getParameter("sortNo")));
			testimonial.setIsActive(Integer.parseInt(request.getParameter("isActive")));
			testimonial.setCompanyId(companyId);
			testimonial.setDelStatus(1);
			testimonial.setExInt1(0);
			testimonial.setExInt2(0);
			testimonial.setExVar1("NA");
			testimonial.setExVar2("NA");

			HomePageTestimonial res = Constants.getRestTemplate().postForObject(Constants.url + "saveTestimonial",
					testimonial, HomePageTestimonial.class);

			if (res.getTestimonialsId() > 0) {
				if (testimonialId == 0)
					session.setAttribute("successMsg", "Home Page Testimonial Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Home Page Testimonial Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Home Page Testimonial");
			}		
			
				int btnVal = Integer.parseInt(request.getParameter("btnType"));

				if (btnVal == 0)
					return "redirect:/showHomePageTestimonial";
				else
					return "redirect:/newTestimonial";
			}
		} catch (Exception e) {
			System.out.println("Execption in /insertHomePageTestimonial : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 23-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Edit Home Page Testimonial
	@RequestMapping(value = "/editTestimonial", method = RequestMethod.GET)
	public String editSpday(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editTestimonial", "showHomePageTestimonial", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "product/addTestimonial";

				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));

				model.addAttribute("frList", frList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				map.add("filterTypeId", 7);

				MFilter[] filterArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getFiltersListByTypeId", map, MFilter[].class);
				List<MFilter> tagsList = new ArrayList<MFilter>(Arrays.asList(filterArr));

				model.addAttribute("tagsList", tagsList);

				String base64encodedString = request.getParameter("testimonialId");
				String testimonialId = FormValidation.DecodeKey(base64encodedString);

				map = new LinkedMultiValueMap<>();
				map.add("testimonialId", testimonialId);

				HomePageTestimonial res = Constants.getRestTemplate()
						.postForObject(Constants.url + "getTestimonialsById", map, HomePageTestimonial.class);

				List<Integer> frIds = new ArrayList<>();
				try {
					if (!res.getFrIds().isEmpty()) {
						frIds = Stream.of(res.getFrIds().split(",")).map(Integer::parseInt)
								.collect(Collectors.toList());
					}
				} catch (Exception e) {
				}

				model.addAttribute("frIds", frIds);

				model.addAttribute("testimonial", res);

				map.add("compId", companyId);

				Designation[] desigArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getDesignationsByCompId", map, Designation[].class);
				List<Designation> desigList = new ArrayList<Designation>(Arrays.asList(desigArr));

				model.addAttribute("desigList", desigList);

				model.addAttribute("imgPath", Constants.showDocSaveUrl);
				model.addAttribute("title", "Edit Home Page Testimonial");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editTestimonial : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 23-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Home Page Testimonial
	@RequestMapping(value = "/deleteTestimonial", method = RequestMethod.GET)
	public String deleteSpDay(HttpServletRequest request, HttpServletResponse response) {

		String mav = new String();
		HttpSession session = request.getSession();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteTestimonial", "showHomePageTestimonial", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				String base64encodedString = request.getParameter("testimonialId");
				String testimonialId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("testimonialId", Integer.parseInt(testimonialId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteTestimonialsById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}

				mav = "redirect:/showHomePageTestimonial";
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteTestimonial : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	
	/*-------------------------------------------------------------------------------------------*/
		// Created By :- Mahendra Singh
		// Created On :- 23-11-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Redirect to Configure Product And Festive Events
		@RequestMapping(value = "/showConfigProductAndEvents", method = RequestMethod.GET)
		public String showConfigProductAndEvents(HttpServletRequest request, HttpServletResponse response, Model model) {

			String mav = new String();
			try {

				HttpSession session = request.getSession();
				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("showConfigProductAndEvents", "showConfigProductAndEvents", "1", "0", "0",
						"0", newModuleList);

				if (view.isError() == true) {

					mav = "accessDenied";

				} else {
					mav = "product/prdctEventList";

					List<FestiveEvent> festiveEventList = new ArrayList<FestiveEvent>();

					int compId = (int) session.getAttribute("companyId");

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("compId", compId);

					FestiveEvent[] festiveEventArr = Constants.getRestTemplate()
							.postForObject(Constants.url + "getFestiveEventAndProductsList", map, FestiveEvent[].class);
					festiveEventList = new ArrayList<FestiveEvent>(Arrays.asList(festiveEventArr));
					
					for (int i = 0; i < festiveEventList.size(); i++) {

						festiveEventList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(festiveEventList.get(i).getEventId())));
					}

					model.addAttribute("eventList", festiveEventList);
					
					model.addAttribute("title", "Configure Products And Festive Events");

					Info add = AccessControll.checkAccess("showConfigProductAndEvents", "showConfigProductAndEvents", "0", "1", "0",
							"0", newModuleList);
					Info edit = AccessControll.checkAccess("showConfigProductAndEvents", "showConfigProductAndEvents", "0", "0",
							"1", "0", newModuleList);
					Info delete = AccessControll.checkAccess("showConfigProductAndEvents", "showConfigProductAndEvents", "0", "0",
							"0", "1", newModuleList);

					if (add.isError() == false) {
						model.addAttribute("addAccess", 0);
					}
					if (edit.isError() == false) {
						model.addAttribute("editAccess", 0);
					}
					if (delete.isError() == false) {
						model.addAttribute("deleteAccess", 0);
					}
				}

			} catch (Exception e) {
				System.out.println("Execption in /showConfigProductAndEvents : " + e.getMessage());
				e.printStackTrace();
			}
			return mav;
		}
		
		// Created By :- Mahendra Singh
		// Created On :- 17-09-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Configure Products And Filter
		@RequestMapping(value = "/prdctEventConfig", method = RequestMethod.GET)
		public String prdctEventConfig(HttpServletRequest request, HttpServletResponse response, Model model) {
			String mav = new String();
			try {
				HttpSession session = request.getSession();
				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("prdctEventConfig", "showConfigProductAndEvents", "0", "1", "0",
						"0", newModuleList);

				if (view.isError() == true) {

					mav = "accessDenied";

				} else {
					FestiveEvent festiveEvent = new FestiveEvent();
					model.addAttribute("festiveEvent", festiveEvent);
					
					int companyId = (int) session.getAttribute("companyId");

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("compId", companyId);

					ProductMaster[] filterArr = Constants.getRestTemplate()
							.postForObject(Constants.url + "getAllProducts", map, ProductMaster[].class);
					productList = new ArrayList<ProductMaster>(Arrays.asList(filterArr));
					
					Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
							Category[].class);
					List<Category> catList = new ArrayList<Category>(Arrays.asList(catArr));
					
					model.addAttribute("productList", productList);
					model.addAttribute("catList", catList);
					
					mav = "product/configPrdctEvent";
					
					model.addAttribute("title", "Add Products And Festive Event Configuration");
				}
			} catch (Exception e) {
				System.out.println("Execption in /prdctEventConfig : " + e.getMessage());
				e.printStackTrace();
			}
			return mav;

		}
		
		// Created By :- Mahendra Singh
		// Created On :- 17-09-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Configure Products And Filter
		@RequestMapping(value = "/saveEventConfiguration", method = RequestMethod.POST)
		public String saveEventConfiguration(HttpServletRequest request, HttpServletResponse response) {
			String mav = new String();
			try {
				HttpSession session = request.getSession();
				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("saveEventConfiguration", "showConfigProductAndEvents", "0", "1", "0",
						"0", newModuleList);

				if (view.isError() == true) {

					mav = "accessDenied";

				} else {
					Date date = new Date();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String productIdsStr = "";

					int compId = (int) session.getAttribute("companyId");

					String[] productIds = request.getParameterValues("productId");

					if (productIds.length > 0) {
						StringBuilder sb = new StringBuilder();
						for (String s : productIds) {
							sb.append(s).append(",");
						}
						productIdsStr = sb.deleteCharAt(sb.length() - 1).toString();
					}
					int eventId = Integer.parseInt(request.getParameter("eventId"));

					FestiveEvent festiveEvent = new FestiveEvent();
					
					festiveEvent.setCompId(compId);
					festiveEvent.setDelStatus(1);
					festiveEvent.setDescription(request.getParameter("description"));
					festiveEvent.setEventId(eventId);
					festiveEvent.setEventName(request.getParameter("eventName"));
					festiveEvent.setExInt1(0);
					festiveEvent.setExInt2(0);
					festiveEvent.setExInt3(0);
					festiveEvent.setExVar1("NA");
					festiveEvent.setExVar2("NA");
					festiveEvent.setExVar3("NA");
					festiveEvent.setFromDate(request.getParameter("fromDate"));
					festiveEvent.setFromTime(request.getParameter("fromTime"));
					festiveEvent.setIsActive(Integer.parseInt(request.getParameter("active_event")));
					festiveEvent.setMakeTime(request.getParameter("eventName"));
					festiveEvent.setProductIds(productIdsStr);
					festiveEvent.setToTime(request.getParameter("toTime"));
					festiveEvent.setToDate(request.getParameter("toDate"));
					festiveEvent.setMakeTime(sf.format(date));

					FestiveEvent res = Constants.getRestTemplate().postForObject(Constants.url + "saveFestiveEventAndProducts", festiveEvent,
							FestiveEvent.class);

					if (res.getEventId()>0) {
						session.setAttribute("successMsg", "Product And Event Configure Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Configure Product And Event");
					}
					mav = "redirect:/showConfigProductAndEvents";
				}
			} catch (Exception e) {
				System.out.println("Execption in /saveProductConfiguration : " + e.getMessage());
				e.printStackTrace();
			}
			return mav;

		}
		
		
		// Created By :- Mahendra Singh
		// Created On :- 23-11-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Update Event
		@RequestMapping(value = "/editEvent", method = RequestMethod.GET)
		public String editEvent(HttpServletRequest request, HttpServletResponse response, Model model) {

			String mav = new String();
			try {
				HttpSession session = request.getSession();
				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("editEvent", "showConfigProductAndEvents", "0", "0", "1", "0", newModuleList);

				if (view.isError() == true) {

					mav = "accessDenied";

				} else {
					mav = "product/configPrdctEvent";

					String base64encodedString = request.getParameter("eventId");
					String eventId = FormValidation.DecodeKey(base64encodedString);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("eventId", eventId);

					FestiveEvent festiveEvent = Constants.getRestTemplate().postForObject(Constants.url + "getFestiveEventConfigById", map, FestiveEvent.class);
					model.addAttribute("festiveEvent", festiveEvent);
					
					List<Integer> prodctIdsIds = Stream.of(festiveEvent.getProductIds().split(",")).map(Integer::parseInt)
							.collect(Collectors.toList());
					
					model.addAttribute("prodctIdsIds", prodctIdsIds);
					
					int companyId = (int) session.getAttribute("companyId");

					map = new LinkedMultiValueMap<>();
					map.add("compId", companyId);

					ProductMaster[] filterArr = Constants.getRestTemplate()
							.postForObject(Constants.url + "getAllProducts", map, ProductMaster[].class);
					productList = new ArrayList<ProductMaster>(Arrays.asList(filterArr));
					
					Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
							Category[].class);
					List<Category> catList = new ArrayList<Category>(Arrays.asList(catArr));
					
					model.addAttribute("productList", productList);
					model.addAttribute("catList", catList);
					
					model.addAttribute("title", "Edit Products And Festive Event Configuration");
				}
			} catch (Exception e) {
				System.out.println("Execption in /editEvent : " + e.getMessage());
				e.printStackTrace();
			}

			return mav;
		}
		
		// Created By :- Mahendra Singh
		// Created On :- 23-11-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Delete Event
		@RequestMapping(value = "/deleteEvent", method = RequestMethod.GET)
		public String deleteEvent(HttpServletRequest request, HttpServletResponse response) {

			String mav = new String();
			HttpSession session = request.getSession();
			try {
				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("deleteEvent", "showConfigProductAndEvents", "0", "0", "0", "1",
						newModuleList);
				if (view.isError() == true) {

					mav = "accessDenied";

				} else {
					String base64encodedString = request.getParameter("eventId");
					String eventId = FormValidation.DecodeKey(base64encodedString);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("eventId", Integer.parseInt(eventId));

					Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteFestiveEventById", map,
							Info.class);

					if (!res.isError()) {
						session.setAttribute("successMsg", res.getMsg());
					} else {
						session.setAttribute("errorMsg", res.getMsg());
					}

					mav = "redirect:/showConfigProductAndEvents";
				}
			} catch (Exception e) {
				System.out.println("Execption in /deleteEvent : " + e.getMessage());
				e.printStackTrace();
			}
			return mav;
		}
		
		/*------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 23-11-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect To Configure Category And Filter
	@RequestMapping(value = "/configCateAndFilters", method = RequestMethod.GET)
	public String showAddRelProConfg(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		HttpSession session = request.getSession();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("configCateAndFilters", "configCateAndFilters", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "product/addCateFltrConfig";
				int compId = (int) session.getAttribute("companyId");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				
				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				List<Category> catList = new ArrayList<Category>(Arrays.asList(catArr));

//				map = new LinkedMultiValueMap<>();
//				map.add("compId", compId);				
				
				model.addAttribute("cateList", catList);	
				
				CateFilterConfig config = new CateFilterConfig();
				
				model.addAttribute("config", config);	
				
				model.addAttribute("title", "Add Category And Filter Configuration");

			}
		} catch (Exception e) {
			System.out.println("Execption in /configCateAndFilters : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	
	@RequestMapping(value = "/getConfiguredFilters", method = RequestMethod.GET)
	public @ResponseBody GetConfiguredCatAndFilter getConfiguredFiltersList(HttpServletRequest request, HttpServletResponse response){
		GetConfiguredCatAndFilter config = new GetConfiguredCatAndFilter();
		try {
			
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");			

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);

			FilterTypes[] filterTypeArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllFilterTypes", map, FilterTypes[].class);
			List<FilterTypes> filterTypeList = new ArrayList<FilterTypes>(Arrays.asList(filterTypeArr));
			
			config.setFilterList(filterTypeList);
				
			map = new LinkedMultiValueMap<>();
			map.add("cateId", Integer.parseInt(request.getParameter("cateId")));
			map.add("compId", companyId);
			
			CateFilterConfig getFiltersIds = Constants.getRestTemplate()
					.postForObject(Constants.url + "getConfigureCateAndFilters", map, CateFilterConfig.class);			
			config.setCatFilterConfig(getFiltersIds);
			
		}catch (Exception e) {
			System.out.println("Execption in /getConfiguredFilters : " + e.getMessage());
			e.printStackTrace();
		}
		
		return config;
		
	}
	
	
	
	
	@RequestMapping(value = "/addCateAndFiltersConfig", method = RequestMethod.POST)
	public String addCateAndFiltersConfig(HttpServletRequest request, HttpServletResponse response) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();			
				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String filterIds = "";

				int compId = (int) session.getAttribute("companyId");
				
				int cateId = Integer.parseInt(request.getParameter("cat_id"));
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("cateId", cateId);
				map.add("compId", compId);
				
				CateFilterConfig getConfigDetails = Constants.getRestTemplate().postForObject(Constants.url + "getConfigureCateAndFilters", map,
						CateFilterConfig.class);
				
				System.err.println("Details----"+getConfigDetails);
				
				String[] productIds = request.getParameterValues("chk");

				if (productIds.length > 0) {
					StringBuilder sb = new StringBuilder();
					for (String s : productIds) {
						sb.append(s).append(",");
					}
					filterIds = sb.deleteCharAt(sb.length() - 1).toString();
				}
				
				
				if(getConfigDetails!=null) {
					 map = new LinkedMultiValueMap<>();
					 map.add("cateId", cateId);
					 map.add("filterIds", filterIds);
					 map.add("upDateTime", sf.format(date));
					 map.add("compId", compId);
					 Info res = Constants.getRestTemplate().postForObject(Constants.url + "updateFilterAndCatConfig", map,
								Info.class);

						if (!res.isError()) {
							session.setAttribute("successMsg", res.getMsg());
						} else {
							session.setAttribute("errorMsg", res.getMsg());
						}

					 
				}else {
					CateFilterConfig config = new CateFilterConfig();
					config.setCateFilterConfigId(Integer.parseInt(request.getParameter("filterConfigId")));
					config.setCateId(cateId);
					config.setCompId(compId);
					config.setDelStatus(1);
					config.setExInt1(0);
					config.setExInt2(0);
					config.setExVar1("NA");
					config.setExVar2("NA");
					config.setFilterIds(filterIds);
					config.setIsActive(1);
					config.setMakerDateTime(sf.format(date));
					
					CateFilterConfig res = Constants.getRestTemplate().postForObject(Constants.url + "saveCatAndFilter", config,
							CateFilterConfig.class);

					if (res.getCateFilterConfigId()>0) {
						session.setAttribute("successMsg", "Category And Filters Configure Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Configure Category And Filters");
					}
				}
				
				mav = "redirect:/configCateAndFilters";
			
		} catch (Exception e) {
			System.out.println("Execption in /addCateAndFiltersConfig : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;

	}
	
}
