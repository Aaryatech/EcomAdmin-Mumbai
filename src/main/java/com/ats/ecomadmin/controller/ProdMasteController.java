package com.ats.ecomadmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.SubCategory;
import com.ats.ecomadmin.model.Tax;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@Scope("session")
/*****************************
//Created Date: 14-09-2020
//UpdateDate:14-09-2020
//Description: Product Related Function Controller
//Devloped By(Devloper Name): Sachin
//Updated By(Devloper Name): Sachin
 ******************************/
public class ProdMasteController {

	
	/*****************************
	//Created Date: 14-09-2020
	//UpdateDate:14-09-2020
	//Description: to show Add Product page
	//Devloped By(Devloper Name): Sachin
	//Updated By(Devloper Name): Sachin
	 ******************************/
	@RequestMapping(value = "/showAddProduct", method = RequestMethod.GET)
	public ModelAndView showAddProduct(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("product/addProd");

		try {
			model.addObject("isEdit", 1);
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showUomList", "showUomList", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				
				List<Uom> uomList = new ArrayList<Uom>();
				List<Tax> taxList = new ArrayList<Tax>();
				List<Category> catList = new ArrayList<>();
				List<MFilter> filterList = new ArrayList<MFilter>();
				
				List<SubCategory> subCatList = new ArrayList<SubCategory>();
				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				Uom[] tagArr = Constants.getRestTemplate().postForObject(Constants.url + "getUoms", map, Uom[].class);
				uomList = new ArrayList<Uom>(Arrays.asList(tagArr));

				for (int i = 0; i < uomList.size(); i++) {

					uomList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(uomList.get(i).getUomId())));
				}
				model.addObject("uomList", uomList);

				Tax[] taxArr = Constants.getRestTemplate().postForObject(Constants.url + "getTaxes", map, Tax[].class);
				taxList = new ArrayList<Tax>(Arrays.asList(taxArr));

				for (int i = 0; i < taxList.size(); i++) {

					taxList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(taxList.get(i).getTaxId())));
				}
				model.addObject("taxList", taxList);

				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				for (int i = 0; i < catList.size(); i++) {

					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				model.addObject("catList", catList);
				model.addObject("catId", 0);
				
				MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFilter", map,
						MFilter[].class);
				filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

				for (int i = 0; i < filterList.size(); i++) {

					filterList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(filterList.get(i).getFilterId())));
				}
				System.err.println("filterList "+filterList.toString());
				model.addObject("filterList", filterList);
				
				model.addObject("filterJSON", CommonUtility.toJSONString(filterList));
				
				SubCategory[] subCatArray = Constants.getRestTemplate().postForObject(Constants.url + "getAllActiveSubCategories", map,
						SubCategory[].class);
				subCatList = new ArrayList<SubCategory>(Arrays.asList(subCatArray));

				for (int i = 0; i < subCatList.size(); i++) {
					subCatList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(subCatList.get(i).getSubCatId())));
				}
				model.addObject("subCatList", subCatList);
				model.addObject("subCatListJSON", CommonUtility.toJSONString(subCatList));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;

	}

}
