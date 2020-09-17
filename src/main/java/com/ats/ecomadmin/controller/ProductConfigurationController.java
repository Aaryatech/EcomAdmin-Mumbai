package com.ats.ecomadmin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.CategoryProduct;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.GetRelatedProductConfig;
import com.ats.ecomadmin.model.GetRequreProduct;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.RelatedProductConfig;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.AccessRightModule;
import com.ats.ecomadmin.model.acrights.AssignRoleDetailList;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.acrights.SubModuleJson;

@Controller
@SessionScope
public class ProductConfigurationController {

	List<CategoryProduct> catProList = new ArrayList<>();

	@RequestMapping(value = "/showAddRelProConfg", method = RequestMethod.GET)
	public String showAddRelProConfg(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		HttpSession session = request.getSession();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddRelProConfg", "showRelProConfgList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "product/addRelProConfig";
				int compId = (int) session.getAttribute("companyId");
				System.err.println("compId**********" + compId);
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				CategoryProduct[] userArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllProductAndCategory", map, CategoryProduct[].class);
				catProList = new ArrayList<CategoryProduct>(Arrays.asList(userArr));
				model.addAttribute("catProList", catProList);

				System.err.println("**********" + catProList.toString());

				map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				ProductMaster[] userArr1 = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllProductByCompId", map, ProductMaster[].class);
				List<ProductMaster> productList = new ArrayList<ProductMaster>(Arrays.asList(userArr1));
				model.addAttribute("productList", productList);

				String base64encodedString = new String();
				int productId = 0;
				String base64encodedString1 = new String();
				int configId = 0;

				base64encodedString = request.getParameter("prodId");

				base64encodedString1 = request.getParameter("configId");

				if (base64encodedString != null) {
					productId = Integer.parseInt(FormValidation.DecodeKey(base64encodedString));

				}

				if (base64encodedString1 != null) {
					configId = Integer.parseInt(FormValidation.DecodeKey(base64encodedString1));

				}

				model.addAttribute("prodId", productId);
				model.addAttribute("configId", configId);
				model.addAttribute("title", "Add Related Product Configuration");

			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getProductListByCat", method = RequestMethod.GET)
	public @ResponseBody List<Integer> getSubmoduleList(HttpServletRequest request, HttpServletResponse response) {

		List<ProductMaster> productList = new ArrayList<>();
		List<Integer> list = new ArrayList<>();
		try {

			int catId = Integer.parseInt(request.getParameter("catId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("catId", catId);
			ProductMaster[] userArr1 = Constants.getRestTemplate().postForObject(Constants.url + "getAllProductByCatId",
					map, ProductMaster[].class);
			productList = new ArrayList<ProductMaster>(Arrays.asList(userArr1));

			for (int i = 0; i < productList.size(); i++) {
				list.add(productList.get(i).getProductId());

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;

	}

	@RequestMapping(value = "/getProductConfig", method = RequestMethod.GET)
	public @ResponseBody List<GetRequreProduct> getProductConfig(HttpServletRequest request,
			HttpServletResponse response) {

		List<GetRequreProduct> productList = new ArrayList<>();
		try {

			int primaryItemId = Integer.parseInt(request.getParameter("product_id"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("primaryItemId", primaryItemId);
			GetRequreProduct[] userArr1 = Constants.getRestTemplate()
					.postForObject(Constants.url + "getRelProConfigByPrimaryItemId", map, GetRequreProduct[].class);
			productList = new ArrayList<GetRequreProduct>(Arrays.asList(userArr1));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return productList;

	}

	@RequestMapping(value = "/submitReletedProductCofig", method = RequestMethod.POST)
	public String submitReletedProductCofig(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		try {
			Date date = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = dateFormat.format(cal.getTime());
			String profileImage = null;
			User userObj = (User) session.getAttribute("userObj");

			int relItem = Integer.parseInt(request.getParameter("product_id"));
			int configId = Integer.parseInt(request.getParameter("configId"));
			RelatedProductConfig config = new RelatedProductConfig();

			String productIds = new String();

			for (int i = 0; i < catProList.size(); i++) {

				int isPresent = 0;

				List<ProductMaster> productList = catProList.get(i).getProductList();

				for (int j = 0; j < productList.size(); j++) {

					String view = request.getParameter(
							productList.get(j).getProductId() + "view" + productList.get(j).getProdCatId());

					System.out.println("view " + view);
					try {
						if (view.equals("1")) {
							isPresent = 1;

							if (productIds.length() == 0) {
								productIds = String.valueOf(productList.get(j).getProductId());

							} else {
								productIds = productIds + "," + productList.get(j).getProductId();

							}
						}
					} catch (Exception e) {

					}

				}
			}
			config.setRelatedProductId(configId);
			config.setPrimaryItemId(relItem);
			config.setSecondaryItemId(productIds);
			config.setIsActive(1);
			config.setDelStatus(1);
			config.setExInt1(0);
			config.setExInt2(0);
			config.setExInt3(0);
			config.setExVar1("NA");
			config.setExVar2("NA");
			config.setExVar3("NA");
			config.setInsertDttime(curDateTime);
			config.setUpdtDttime(curDateTime);
			config.setMakerUserId(userObj.getUserId());

			RelatedProductConfig info = Constants.getRestTemplate()
					.postForObject(Constants.url + "saveRelatedProductConfig", config, RelatedProductConfig.class);

			if (info.getRelatedProductId() > 0) {
				session.setAttribute("successMsg", "Saved Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showRelProConfgList";
	}

	@RequestMapping(value = "/showRelProConfgList", method = RequestMethod.GET)
	public String showRelProConfgList(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		HttpSession session = request.getSession();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showRelProConfgList", "showRelProConfgList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "product/relProConfigList";
				model.addAttribute("title", "Related Product Configuration");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", session.getAttribute("companyId"));
				GetRelatedProductConfig[] userArr1 = Constants.getRestTemplate()
						.postForObject(Constants.url + "getRelProConfigByCompId", map, GetRelatedProductConfig[].class);
				ArrayList<GetRelatedProductConfig> catProList = new ArrayList<GetRelatedProductConfig>(
						Arrays.asList(userArr1));

				for (int i = 0; i < catProList.size(); i++) {

					catProList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(catProList.get(i).getRelatedProductId())));

					catProList.get(i)
							.setExVar2(FormValidation.Encrypt(String.valueOf(catProList.get(i).getPrimaryItemId())));
				}
				model.addAttribute("configList", catProList);

				System.err.println("catProList" + catProList.toString());

				Info add = AccessControll.checkAccess("showRelProConfgList", "showRelProConfgList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showRelProConfgList", "showRelProConfgList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showRelProConfgList", "showRelProConfgList", "0", "0", "0",
						"1", newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/deleteProductConfig", method = RequestMethod.GET)
	public String deleteProductConfig(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteProductConfig", "showRelProConfgList", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "redirect:/showRelProConfgList";
				String base64encodedString = request.getParameter("configId");
				String configId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("relatedProductId", Integer.parseInt(configId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteProdConfig", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

}
