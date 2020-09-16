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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.GetSubCatPrefix;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.SubCategory;
import com.ats.ecomadmin.model.Tax;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@Scope("session")
/*****************************
 * //Created Date: 14-09-2020 //UpdateDate:14-09-2020 //Description: Product
 * Related Function Controller //Devloped By(Devloper Name): Sachin //Updated
 * By(Devloper Name): Sachin
 ******************************/
public class ProdMasteController {

	/*****************************
	 * //Created Date: 14-09-2020 //UpdateDate:14-09-2020 //Description: to show Add
	 * Product page //Devloped By(Devloper Name): Sachin //Updated By(Devloper
	 * Name): Sachin
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
				System.err.println("filterList " + filterList.toString());
				model.addObject("filterList", filterList);

				model.addObject("filterJSON", CommonUtility.toJSONString(filterList));

				SubCategory[] subCatArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllActiveSubCategories", map, SubCategory[].class);
				subCatList = new ArrayList<SubCategory>(Arrays.asList(subCatArray));

				for (int i = 0; i < subCatList.size(); i++) {
					subCatList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(subCatList.get(i).getSubCatId())));
				}
				model.addObject("subCatList", subCatList);
				model.addObject("subCatListJSON", CommonUtility.toJSONString(subCatList));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;

	}

	// submitProductSave
	/*****************************
	 * //Created Date: 16-09-2020 //UpdateDate:16-09-2020 //Description: to submit Product (Save) 
	 * //Devloped By(Devloper Name): Sachin //Updated By(Devloper
	 * Name): Sachin
	 ******************************/
	@RequestMapping(value = "/submitProductSave", method = RequestMethod.POST)
	public String submitProductSave(HttpServletRequest request, HttpServletResponse response) {

		try {
			ProductMaster prod = new ProductMaster();

			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");

			String catId = request.getParameter("cat_id");
			String sub_cat_id = request.getParameter("sub_cat_id");
			String prod_code = request.getParameter("prod_code");
			String prod_name = request.getParameter("prod_name");
			String short_name = request.getParameter("short_name");
			String tax_id = request.getParameter("tax_id");
			String sort_no = request.getParameter("sort_no");
			String min_qty = request.getParameter("min_qty");
			String shelf_life = request.getParameter("shelf_life");
			String is_return_allow = request.getParameter("is_return_allow");
			String return_per = request.getParameter("return_per");
			String uom_id = request.getParameter("uom_id");
			String shape_id = request.getParameter("shape_id");

			String is_sameDay_del = request.getParameter("is_sameDay_del");
			String prod_type_id = request.getParameter("prod_type_id");

			String prod_status = request.getParameter("prod_status");
			String book_b4 = request.getParameter("book_b4");
			String char_limit_yn = request.getParameter("char_limit_yn");// For Alphabetic Cakes
			String no_of_alpha = request.getParameter("no_of_alpha");
			String is_cover_ph = request.getParameter("is_cover_ph");
			String is_base_ph = request.getParameter("is_base_ph");
			String is_sp_inst = request.getParameter("is_sp_inst");
			String is_msg_on_cake = request.getParameter("is_msg_on_cake");
			String is_slot_used = request.getParameter("is_slot_used");
			String is_used = request.getParameter("is_used");
			String no_of_msg_char = request.getParameter("no_of_msg_char"); // If is_msg_on_cake==1 then only
			String bread_id = request.getParameter("bread_id");
			String cream_id = request.getParameter("cream_id");

			String layering_cream_id = request.getParameter("layering_cream_id");
			String topping_cream_id = request.getParameter("topping_cream_id");
			
			
			String prod_desc = request.getParameter("prod_desc");
			
			String Ingredients = request.getParameter("Ingredients");
			
			String prep_time = request.getParameter("prep_time"); // time in Integer minutes
			
			String is_veg = request.getParameter("is_veg"); // 0 veg 1 non veg 2 both
			
			String rate_setting_type = request.getParameter("rate_setting_type");
			
			String max_wt = request.getParameter("max_wt");
			
			String[] weight_ids = request.getParameterValues("weight_ids");
			String[] appl_tags = request.getParameterValues("appl_tags");
			String[] event_ids = request.getParameterValues("event_ids");
			String[] flav_ids = request.getParameterValues("flav_ids");
			String[] sameDay_timeSlot = request.getParameterValues("sameDay_timeSlot");

			//getCommaSepStringFromStrArray
			System.err.println("rate_setting_type "+rate_setting_type);
			
			
			System.err.println("is_cover_ph "+is_cover_ph);
			System.err.println("is_base_ph "+is_base_ph);
			
			String applicableTags=getCommaSepStringFromStrArray(appl_tags);
			prod.setApplicableTags(applicableTags);
			
			prod.setBookBefore(Integer.parseInt(book_b4));
			
			prod.setCompanyId(compId);
			
			String eventIds=getCommaSepStringFromStrArray(event_ids);
			prod.setEventsIds(eventIds);

			prod.setCopyItemId(0);
			prod.setDelStatus(1);
			prod.setExDate1("2020-09-16");
			prod.setExDate2("2020-09-17");
			prod.setExFloat1(1);
			prod.setExFloat2(1);
			prod.setExFloat3(1);
			prod.setExInt1(1);
			prod.setExInt2(1);
			prod.setExInt3(1);
			
			prod.setExVar1("v1");
			prod.setExVar2("v2");
			prod.setExVar3("v3");
			prod.setExVar4("v4");
			
			String flavIds=getCommaSepStringFromStrArray(flav_ids);
			prod.setFlavourIds(flavIds);
			
			prod.setIngerdiants(Ingredients.trim());
			prod.setInsertDttime(CommonUtility.getCurrentYMDDateTime());
			prod.setIsActive(1);
			
			
			
			prod.setIsVeg(Integer.parseInt(is_veg));
			prod.setLayeringCream(Integer.parseInt(layering_cream_id));
			prod.setMakerUserId(1);
			prod.setMaxWt(Integer.parseInt(max_wt));
			prod.setMinQty(Integer.parseInt(min_qty));
			
			
			prod.setPrepTime(Integer.parseInt(prep_time));
			prod.setProdCatId(Integer.parseInt(catId));
			prod.setProdImagePrimary("na");
			prod.setProdStatusId(Integer.parseInt(prod_status));
			prod.setProdSubCatId(Integer.parseInt(sub_cat_id));
			prod.setProdTypeId(Integer.parseInt(prod_type_id));
			prod.setProductCode(prod_code.trim());
			
			prod.setProductDesc(prod_desc.trim());
			
			if(char_limit_yn.equals("1")) {
			prod.setIsCharLimit(Integer.parseInt(char_limit_yn));
			prod.setNoOfCharsForAlphaCake(Integer.parseInt(no_of_alpha));
			}else {
				prod.setIsCharLimit(0);
				prod.setNoOfCharsForAlphaCake(0);
			}
			try {
			if(is_msg_on_cake.equalsIgnoreCase("on")) {
				prod.setAllowMsgOnCake(1);
				prod.setNoOfCharsOnCake(Integer.parseInt(no_of_msg_char));
			}
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			if(is_sp_inst.equalsIgnoreCase("on")) {
				prod.setAllowSpecialInstruction(1);
			}
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			if(is_slot_used.equalsIgnoreCase("on")) {
				prod.setIsSlotUsed(1);
			}
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			if(is_used.equalsIgnoreCase("on")) {
				prod.setIsUsed(1);
			}
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			
		//**
			prod.setProductId(0);
			prod.setProductImages("na");
			prod.setProductName(prod_name.trim());
			
			if(is_return_allow.equals("1")) {
				prod.setIsReturnAllow(1);
				prod.setRetPer(Float.parseFloat(return_per));
			}else {
				prod.setIsReturnAllow(0);
				prod.setRetPer(0);
			}
			
			if(rate_setting_type.equals("2")) {
				System.err.println("Rate Setting 2 weight_ids= "+weight_ids);
				String weightString=getCommaSepStringFromStrArray(weight_ids);
				prod.setAvailInWeights(weightString);
				prod.setRateSettingType(2);
			}else {
				prod.setAvailInWeights("0");
				prod.setRateSettingType(Integer.parseInt(rate_setting_type));
			}
			
			if(is_sameDay_del.equals("1")) {
				prod.setAllowSameDayDelivery(1);
				String sameDayTimeSlot=getCommaSepStringFromStrArray(sameDay_timeSlot);
				prod.setSameDayTimeAllowedSlot(sameDayTimeSlot);
			}
			try {
			if(is_cover_ph.equalsIgnoreCase("on")) {
				//set 1
				prod.setAllowCoverPhotoUpload(1);
			
			}else {
				//set 0
			}
			}catch (Exception e) {
				// TODO: handle exception
			}	
			try {
			if(is_base_ph.equalsIgnoreCase("on")) {
				//set 1
				prod.setAllowBasePhotoUpload(1);
			}else {
				//set 0
			}
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			prod.setShapeId(Integer.parseInt(shape_id));
			prod.setShelfLife(Integer.parseInt(shelf_life));
			prod.setShortName(short_name.trim());
			prod.setSortId(Integer.parseInt(sort_no));
			prod.setTaxId(Integer.parseInt(tax_id));
			prod.setToppingCream(Integer.parseInt(topping_cream_id));
			prod.setTypeOfBread(Integer.parseInt(bread_id));
			
			prod.setTypeOfCream(Integer.parseInt(cream_id));
			prod.setUomId(Integer.parseInt(uom_id));
			prod.setUpdtDttime(CommonUtility.getCurrentYMDDateTime());
			
			System.err.println("prod  Input " +prod.toString());
			ProductMaster res = Constants.getRestTemplate().postForObject(Constants.url + "saveProduct", prod, ProductMaster.class);
			
			System.err.println("prod  res " +res.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/showAddProduct";
	}
	
	
	
	@RequestMapping(value = "/getSubCatPrefix", method = RequestMethod.POST)
	public @ResponseBody Object getSubCatPrefix(HttpServletRequest request, HttpServletResponse response) {
		GetSubCatPrefix subCatPref=new GetSubCatPrefix();
		try {
			
			
			
		}catch (Exception e) {
			
		}
		return response;
	}
	
	public String getCommaSepStringFromStrArray(String[] strArray) {
		
		String commaSepString = new String();
		
		try {
			
		StringBuilder sb = new StringBuilder();
		
		for (int j = 0; j < strArray.length; j++) {
			sb  = sb .append(strArray[j] + ",");
		}
		
		 commaSepString = sb.toString();
		 commaSepString = commaSepString.substring(0, commaSepString.length() - 1);
		 
		}catch (Exception e) {
			
			commaSepString = new String();
		}
		
		return commaSepString ;
		
	}
}
