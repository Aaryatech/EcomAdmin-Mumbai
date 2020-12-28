package com.ats.ecomadmin.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.GetItemConfHead;
import com.ats.ecomadmin.model.GetProdList;
import com.ats.ecomadmin.model.GetSubCatPrefix;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.ItemConfDetail;
import com.ats.ecomadmin.model.ItemConfHeader;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.SubCategory;
import com.ats.ecomadmin.model.Tax;
import com.ats.ecomadmin.model.TempConfTraveller;
import com.ats.ecomadmin.model.TempProdConfig;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.offer.Images;
import com.ats.ecomadmin.model.offer.OfferHeader;

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
			model.addObject("isEdit", 0);
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info add = AccessControll.checkAccess("showProdList", "showProdList", "0", "1", "0", "0", newModuleList);

			if (add.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				Info view = AccessControll.checkAccess("showProdList", "showProdList", "1", "0", "0", "0",
						newModuleList);
				if (view.isError() == false) {
					model.addObject("viewAccess", 1);
				}
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

				
				model.addObject("catId", 0);

				MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFilter", map,
						MFilter[].class);
				filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

				for (int i = 0; i < filterList.size(); i++) {

					filterList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(filterList.get(i).getFilterId())));
				}
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

				map = new LinkedMultiValueMap<>();
				map.add("compId", 1);
				
				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				for (int i = 0; i < catList.size(); i++) {

					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				model.addObject("catList", catList);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;

	}

	// submitProductSave
	/*****************************
	 * //Created Date: 16-09-2020 //UpdateDate:16-09-2020 //Description: to submit
	 * Product (Save) //Devloped By(Devloper Name): Sachin //Updated By(Devloper
	 * Name): Sachin
	 ******************************/
	@RequestMapping(value = "/submitProductSave", method = RequestMethod.POST)
	public String submitProductSave(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("primary_img") MultipartFile prodImgFile) {
		String returnPage = "redirect:/showProdList";;
		int savenext = 0;
		try {
			// System.err.println("prodImgFile " + prodImgFile.getOriginalFilename());
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info add = AccessControll.checkAccess("showProdList", "showProdList", "0", "1", "0", "0", newModuleList);

			if (add.isError() == true) {
				returnPage = "accessDenied";
			} else {
				returnPage = "redirect:/showProdList";

				ProductMaster prod = new ProductMaster();

				User userObj = (User) session.getAttribute("userObj");

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
				// String shape_id = request.getParameter("shape_id");
				String shape_id[] = request.getParameterValues("shape_id");

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

				// String is_veg = request.getParameter("is_veg"); // 0 veg 1 non veg 2 both

				String rate_setting_type = request.getParameter("rate_setting_type");

				String max_wt = request.getParameter("max_wt");

				String[] weight_ids = request.getParameterValues("weight_ids");
				String[] appl_tags = request.getParameterValues("appl_tags");
				String[] event_ids = request.getParameterValues("event_ids");
				String[] flav_ids = request.getParameterValues("flav_ids");
				String[] sameDay_timeSlot = request.getParameterValues("sameDay_timeSlot");
				String[] is_veg = request.getParameterValues("is_veg"); // 0 veg 1 non veg 2 both

				// getCommaSepStringFromStrArray

				String applicableTags = getCommaSepStringFromStrArray(appl_tags);
				prod.setApplicableTags(applicableTags);

				prod.setBookBefore(Integer.parseInt(book_b4));

				prod.setCompanyId(compId);

				String eventIds = getCommaSepStringFromStrArray(event_ids);
				prod.setEventsIds(eventIds);
				prod.setShapeId(getCommaSepStringFromStrArray(shape_id));

				prod.setCopyItemId(0);
				prod.setDelStatus(1);
				prod.setExDate1("16-09-2020");
				prod.setExDate2("17-09-2020");
				prod.setExFloat1(1);
				prod.setExFloat2(1);
				prod.setExFloat3(1);
				try {
					// Sachin 30-10-2020 to add default values of flavor,shape and veg nonveg
					// filter's
					prod.setExInt1(Integer.parseInt(request.getParameter("def_shape")));
					prod.setExInt2(Integer.parseInt(request.getParameter("def_vnv")));
					prod.setExInt3(Integer.parseInt(request.getParameter("def_flav")));
				} catch (Exception e) {
					prod.setExInt1(0);
					prod.setExInt2(Integer.parseInt(request.getParameter("def_vnv")));
					prod.setExInt3(Integer.parseInt(request.getParameter("def_flav")));
				}

				prod.setExVar1("v1");
				prod.setExVar2("v2");
				prod.setExVar3("v3");
				prod.setExVar4("v4");
				String basicMrp = request.getParameter("basic_mrp");
				try {
					prod.setBasicMrp(Float.parseFloat(basicMrp));
				} catch (Exception e) {
					prod.setBasicMrp(0);
				}
				String flavIds = getCommaSepStringFromStrArray(flav_ids);
				prod.setFlavourIds(flavIds);

				prod.setIngerdiants(Ingredients.trim());

				try {
					if (request.getParameter("idtime") == null) {
						prod.setInsertDttime(CommonUtility.getCurrentYMDDateTime());
					} else {
						prod.setInsertDttime(request.getParameter("idtime"));
					}
				} catch (Exception e) {
					prod.setInsertDttime(CommonUtility.getCurrentYMDDateTime());
				}

				prod.setIsActive(1);

				prod.setIsVeg(getCommaSepStringFromStrArray(is_veg));// change on 21-10-2020
				prod.setLayeringCream(Integer.parseInt(layering_cream_id));
				prod.setMakerUserId(userObj.getUserId());
				try {
					prod.setMaxWt(Integer.parseInt(max_wt));
				} catch (Exception e) {
					prod.setMaxWt(0);
				}
				int minQ = (int) Float.parseFloat(min_qty);
				prod.setMinQty(minQ);

				prod.setPrepTime(Integer.parseInt(prep_time));
				prod.setProdCatId(Integer.parseInt(catId));
				prod.setProdStatusId(Integer.parseInt(prod_status));
				prod.setProdSubCatId(Integer.parseInt(sub_cat_id));
				prod.setProdTypeId(Integer.parseInt(prod_type_id));
				prod.setProductCode(prod_code.trim());

				prod.setProductDesc(prod_desc.trim());

				if (char_limit_yn.equals("1")) {
					prod.setIsCharLimit(Integer.parseInt(char_limit_yn));
					prod.setNoOfCharsForAlphaCake(Integer.parseInt(no_of_alpha));
				} else {
					prod.setIsCharLimit(0);
					prod.setNoOfCharsForAlphaCake(0);
				}
				try {
					if (is_msg_on_cake.equalsIgnoreCase("on")) {
						prod.setAllowMsgOnCake(1);
						prod.setNoOfCharsOnCake(Integer.parseInt(no_of_msg_char));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if (is_sp_inst.equalsIgnoreCase("on")) {
						prod.setAllowSpecialInstruction(1);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if (is_slot_used.equalsIgnoreCase("on")) {
						prod.setIsSlotUsed(1);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if (is_used.equalsIgnoreCase("on")) {
						prod.setIsUsed(1);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				// **
				try {
					int prodId = 0;
					String prodIdStr = request.getParameter("prod_id");
					System.err.println("prodIdStr is " + prodIdStr);
					try {

						if (prodIdStr != null) {
							prodId = Integer.parseInt(FormValidation.DecodeKey(prodIdStr));
						} else {
							System.err.println("prodIdStr null");
						}

					} catch (Exception e) {
						prodId = 0;
					}
					prod.setProductId(prodId);
				} catch (Exception e) {
					prod.setProductId(0);
				}

				prod.setProductImages("");
				prod.setProductName(prod_name.trim());

				if (is_return_allow.equals("1")) {
					prod.setIsReturnAllow(1);
					prod.setRetPer(Float.parseFloat(return_per));
				} else {
					prod.setIsReturnAllow(0);
					prod.setRetPer(0);
				}

				if (rate_setting_type.equals("2")) {
					String weightString = getCommaSepStringFromStrArray(weight_ids);
					prod.setAvailInWeights(weightString);
					prod.setRateSettingType(2);
				} else {
					//prod.setAvailInWeights("0");
					try {
					String weightString = getCommaSepStringFromStrArray(weight_ids);
					prod.setAvailInWeights(weightString);
					}catch (Exception e) {
						prod.setAvailInWeights("1");
					}
					prod.setRateSettingType(Integer.parseInt(rate_setting_type));
				}

				if (is_sameDay_del.equals("1")) {
					prod.setAllowSameDayDelivery(1);
					String sameDayTimeSlot = getCommaSepStringFromStrArray(sameDay_timeSlot);
					prod.setSameDayTimeAllowedSlot(sameDayTimeSlot);
				}
				try {
					if (is_cover_ph.equalsIgnoreCase("on")) {
						// set 1
						prod.setAllowCoverPhotoUpload(1);

					} else {
						// set 0
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if (is_base_ph.equalsIgnoreCase("on")) {
						// set 1
						prod.setAllowBasePhotoUpload(1);
					} else {
						// set 0
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				prod.setShelfLife(Integer.parseInt(shelf_life));
				prod.setShortName(short_name.trim());
				prod.setSortId(Integer.parseInt(sort_no));
				prod.setTaxId(Integer.parseInt(tax_id));
				prod.setToppingCream(Integer.parseInt(topping_cream_id));
				prod.setTypeOfBread(Integer.parseInt(bread_id));

				prod.setTypeOfCream(Integer.parseInt(cream_id));
				prod.setUomId(Integer.parseInt(uom_id));
				prod.setUpdtDttime(CommonUtility.getCurrentYMDDateTime());

				Random random = new Random();
				int randomInt = random.nextInt(100);

				String prime_image = request.getParameter("prime_image");
				String fileName = prime_image;
				if (!prodImgFile.getOriginalFilename().equalsIgnoreCase("")) {
					String ext = prodImgFile.getOriginalFilename().split("\\.")[1];
					fileName = CommonUtility.getCurrentTimeStamp() + "_" + randomInt + "." + ext;
				}
// new ImageUploadController().saveUploadedFiles(files.get(i), 1, fileName);
				prod.setProdImagePrimary(fileName);
				try {//saveImgFiles //saveProdImgeWithResize
					Info info = new ImageUploadController().saveImgFilesProdImg(prodImgFile,Constants.imageFileExtensions, fileName);
				} catch (Exception e) {
					// TODO: handle exception
				}

				ProductMaster res = Constants.getRestTemplate().postForObject(Constants.url + "saveProduct", prod,
						ProductMaster.class);

				if (res.getProductId() > 0)
					session.setAttribute("successMsg", "Product Saved Sucessfully");
				else
					session.setAttribute("errorMsg", "Failed to Save Product");

			}
			try {
				String saveNextStr = request.getParameter("savenext");
				if (saveNextStr.equalsIgnoreCase("1")) {
					savenext = 1;
				}
			} catch (Exception e) {
				savenext = 0;
			}
			if (savenext != 0) {
				returnPage = "redirect:/showAddProduct";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnPage;
	}

	/*****************************
	 * //Created Date: 16-09-2020 //UpdateDate:17-09-2020 //Description: to get
	 * Product code from subcat prefix //Devloped By(Devloper Name): Sachin
	 * //Updated By(Devloper Name): Sachin
	 ******************************/
	@RequestMapping(value = "/getSubCatPrefix", method = RequestMethod.POST)
	public @ResponseBody Object getSubCatPrefix(HttpServletRequest request, HttpServletResponse response) {

		GetSubCatPrefix subCatPref = new GetSubCatPrefix();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("subCatId", Integer.parseInt(request.getParameter("subCatId")));

			subCatPref = Constants.getRestTemplate().postForObject(Constants.url + "getSubCatPrefix", map,
					GetSubCatPrefix.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return subCatPref;
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

	/*****************************
	 * //Created Date: 18-09-2020 //UpdateDate:18-09-2020 //Description: to show
	 * Product Config Based on CatId //Devloped By(Devloper Name): Sachin //Updated
	 * By(Devloper Name): Sachin
	 ******************************/

	@RequestMapping(value = "/showAddProdConfig", method = RequestMethod.GET)
	public ModelAndView showAddProdConfig(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("product/addProdConf");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info add = AccessControll.checkAccess("showViewProdConfigHeader", "showViewProdConfigHeader", "0", "1", "0",
					"0", newModuleList);

			if (add.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				Info view = AccessControll.checkAccess("showViewProdConfigHeader", "showViewProdConfigHeader", "1", "0",
						"0", "0", newModuleList);
				if (view.isError() == false) {

					model.addObject("viewAccess", 1);
				}

				int compId = (int) session.getAttribute("companyId");

				List<Category> catList = new ArrayList<>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", 1);

				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				for (int i = 0; i < catList.size(); i++) {

					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				model.addObject("catList", catList);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return model;
	}

	/*****************************
	 * //Created Date: 17-09-2020 //UpdateDate:17-09-2020 //Description: to show
	 * Product List page //Devloped By(Devloper Name): Sachin //Updated By(Devloper
	 * Name): Sachin
	 ******************************/

	@RequestMapping(value = "/showProdList", method = RequestMethod.GET)
	public ModelAndView showProdList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("product/prodList");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showProdList", "showProdList", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {

				Info add = AccessControll.checkAccess("showProdList", "showProdList", "0", "1", "0", "0",
						newModuleList);

				if (add.isError() == false) {
					model.addObject("addAccess", 1);
				}
				Info edit = AccessControll.checkAccess("showProdList", "showProdList", "0", "0", "1", "0",
						newModuleList);

				if (edit.isError() == false) {
					model.addObject("editAccess", 1);
				}
				List<GetProdList> prodList = new ArrayList<GetProdList>();
				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				GetProdList[] prodArr = Constants.getRestTemplate().postForObject(Constants.url + "getProdList", map,
						GetProdList[].class);
				prodList = new ArrayList<GetProdList>(Arrays.asList(prodArr));

				for (int i = 0; i < prodList.size(); i++) {

					prodList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(prodList.get(i).getProductId())));
				}

				model.addObject("prodList", prodList);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return model;
	}

	// TempProdConfig
	// Sachin 18-09-2020
	List<MFilter> filterList = new ArrayList<MFilter>();

	List<MFilter> vegNonVegList = new ArrayList<MFilter>();// Sachin 22-10-2020
	List<MFilter> shapeList = new ArrayList<MFilter>();// Sachin 22-10-2020
	List<ProductMaster> prodList = null;
	List<TempProdConfig> tempProdConfList = new ArrayList<>();

	@RequestMapping(value = { "/getProdConfOld" }, method = RequestMethod.POST)
	public ModelAndView getProdConf(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("product/addProdConf");

		int catId = 0;

		try {

			catId = Integer.parseInt(request.getParameter("cat_id"));

		} catch (Exception e) {
			catId = 0;
		}

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		HttpSession session = request.getSession();
		int companyId = (int) session.getAttribute("companyId");

		filterList = new ArrayList<MFilter>();

		map = new LinkedMultiValueMap<>();
		map.add("compId", companyId);
		map.add("catId", catId);
		ProductMaster[] prodArray = Constants.getRestTemplate()
				.postForObject(Constants.url + "getProdListByCatIdCompId", map, ProductMaster[].class);

		prodList = new ArrayList<ProductMaster>(Arrays.asList(prodArray));
		model.addObject("prodList", prodList);

		map = new LinkedMultiValueMap<>();
		map.add("compId", 1);
		List<Category> catList = new ArrayList<>();

		Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
				Category[].class);
		catList = new ArrayList<Category>(Arrays.asList(catArr));

		for (int i = 0; i < catList.size(); i++) {

			catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
		}

		model.addObject("catList", catList);
		map = new LinkedMultiValueMap<>();
		map.add("compId", companyId);
		try {

			// Get Filter By Comp Id and Filter type ie 4 for Flavor
			// filterList = filterRepo.getFiltersByFilterId(compId, 4);

			map.add("filterTypeId", 4);
			map.add("compId", companyId);

			MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getFiltersListByTypeId",
					map, MFilter[].class);
			filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			// model.addAttribute("productList", productList);

			// List<ProductMaster> prodList =
			// productMasterRepo.findByProdCatIdAndDelStatusAndCompanyId(catId, 1,compId);
			tempProdConfList = new ArrayList<>();

			for (int i = 0; i < prodList.size(); i++) {
				List<Integer> vegNonVegList = new ArrayList<>();

				if (Integer.parseInt(prodList.get(i).getIsVeg()) == 2) {

					vegNonVegList.add(0);
					vegNonVegList.add(1);

				} else {

				}

				List<String> prodFlavIdList = Arrays.asList(prodList.get(i).getFlavourIds().split(",", -1));

				List<MFilter> flavList = getFlavList(prodFlavIdList);

				if (!flavList.isEmpty()) {

					if (flavList.size() > 0) {

						if (prodList.get(i).getRateSettingType() == 2) {

							// By weight ids;

							List<String> wtList = Arrays.asList(prodList.get(i).getAvailInWeights().split(",", -1));

							for (int p = 0; p < flavList.size(); p++) {

								for (int x = 0; x < wtList.size(); x++) {

									if (vegNonVegList.size() == 2) {
										// Create Bean and Assign Values
										for (int y = 0; y < vegNonVegList.size(); y++) {
											TempProdConfig config = new TempProdConfig();

											UUID uuid = UUID.randomUUID();
											config.setUuid(uuid.toString());

											config.setCatId(prodList.get(i).getProdCatId());
											config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
											config.setFlavorId(flavList.get(p).getFilterId());
											config.setFlavorName(flavList.get(p).getFilterName());
											config.setProductId(prodList.get(i).getProductId());
											config.setProductName(prodList.get(i).getProductName());
											config.setRateSetingType(prodList.get(i).getRateSettingType());

											config.setVegType(vegNonVegList.get(y));

											config.setWeight(Float.parseFloat(wtList.get(x)));
											tempProdConfList.add(config);
										} // end of vegNonVegList Y for

									} // end of if vegNonVegList.size()==2
									else {
										TempProdConfig config = new TempProdConfig();

										UUID uuid = UUID.randomUUID();
										config.setUuid(uuid.toString());

										config.setCatId(prodList.get(i).getProdCatId());
										config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
										config.setFlavorId(flavList.get(p).getFilterId());
										config.setFlavorName(flavList.get(p).getFilterName());
										config.setProductId(prodList.get(i).getProductId());
										config.setProductName(prodList.get(i).getProductName());
										config.setRateSetingType(prodList.get(i).getRateSettingType());

										// config.setVegType(InprodList.get(i).getIsVeg());

										config.setWeight(Float.parseFloat(wtList.get(x)));
										tempProdConfList.add(config);

									} // else part of if vegNonVegList.size()==2
								} // end of wtList x for Loop

							} // end of flavList P for Loop

						} // End of If getRateSettingType==2

						else {
							// by UOM or Kg ie constant 1 Qty

							for (int p = 0; p < flavList.size(); p++) {
								// Create Bean and Assign Values

								if (vegNonVegList.size() == 2) {
									// Create Bean and Assign Values
									for (int y = 0; y < vegNonVegList.size(); y++) {
										TempProdConfig config = new TempProdConfig();

										UUID uuid = UUID.randomUUID();
										config.setUuid(uuid.toString());

										config.setCatId(prodList.get(i).getProdCatId());
										config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
										config.setFlavorId(flavList.get(p).getFilterId());
										config.setFlavorName(flavList.get(p).getFilterName());
										config.setProductId(prodList.get(i).getProductId());
										config.setProductName(prodList.get(i).getProductName());
										config.setRateSetingType(prodList.get(i).getRateSettingType());

										config.setVegType(vegNonVegList.get(y));

										config.setWeight(1);
										tempProdConfList.add(config);
									} // end of vegNonVegList Y for
								} // end of vegNonVegList.size()==2
								else {

									TempProdConfig config = new TempProdConfig();

									UUID uuid = UUID.randomUUID();
									config.setUuid(uuid.toString());

									config.setCatId(prodList.get(i).getProdCatId());
									config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
									config.setFlavorId(flavList.get(p).getFilterId());
									config.setFlavorName(flavList.get(p).getFilterName());
									config.setProductId(prodList.get(i).getProductId());
									config.setProductName(prodList.get(i).getProductName());
									config.setRateSetingType(prodList.get(i).getRateSettingType());

									// config.setVegType(prodList.get(i).getIsVeg());

									config.setWeight(1);
									tempProdConfList.add(config);
								}
							} // end of flavList P for Loop

						} // End of else

					} // end of If flavList size >0

				} // end of if !flavList.isEmpty
				prodList.get(i).setTempProdConfList(tempProdConfList);
			}
			model.addObject("tempProdConfList", tempProdConfList);
			model.addObject("catId", catId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	// Sachin 22-10-2020 Prod confi by wt,veg type, shape,flavor
	public float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	@RequestMapping(value = { "/getProdConf" }, method = RequestMethod.POST)
	public ModelAndView getProdConfNew(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("product/addProdConf");

		int catId = 0;

		try {

			catId = Integer.parseInt(request.getParameter("cat_id"));

		} catch (Exception e) {
			catId = 0;
		}

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		HttpSession session = request.getSession();
		int companyId = (int) session.getAttribute("companyId");
		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

		Info view = AccessControll.checkAccess("showViewProdConfigHeader", "showViewProdConfigHeader", "1", "0", "0",
		 		"0", newModuleList);
		if (view.isError() == false) {

			model.addObject("viewAccess", 1);
		}

		filterList = new ArrayList<MFilter>();

		map = new LinkedMultiValueMap<>();
		map.add("compId", companyId);
		map.add("catId", catId);
		ProductMaster[] prodArray = Constants.getRestTemplate()
				.postForObject(Constants.url + "getProdListByCatIdCompId", map, ProductMaster[].class);

		prodList = new ArrayList<ProductMaster>(Arrays.asList(prodArray));
		model.addObject("prodList", prodList);

		map = new LinkedMultiValueMap<>();
		map.add("compId", 1);
		List<Category> catList = new ArrayList<>();

		Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
				Category[].class);
		catList = new ArrayList<Category>(Arrays.asList(catArr));

		for (int i = 0; i < catList.size(); i++) {

			catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
		}

		model.addObject("catList", catList);

		try {

			// Get Filter By Comp Id and Filter type ie 4 for Flavor
			// filterList = filterRepo.getFiltersByFilterId(compId, 4);
			map = new LinkedMultiValueMap<>();
			map.add("filterTypeId", 4);
			map.add("compId", companyId);

			MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getFiltersListByTypeId",
					map, MFilter[].class);
			filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

			map = new LinkedMultiValueMap<>();
			map.add("filterTypeId", 12);
			map.add("compId", companyId);

			MFilter[] vegNonVegTypeArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getFiltersListByTypeId", map, MFilter[].class);
			vegNonVegList = new ArrayList<MFilter>(Arrays.asList(vegNonVegTypeArray));

			map = new LinkedMultiValueMap<>();
			map.add("filterTypeId", 1);
			map.add("compId", companyId);

			MFilter[] shapeArray = Constants.getRestTemplate().postForObject(Constants.url + "getFiltersListByTypeId",
					map, MFilter[].class);
			shapeList = new ArrayList<MFilter>(Arrays.asList(shapeArray));

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			// model.addAttribute("productList", productList);

			// List<ProductMaster> prodList =
			// productMasterRepo.findByProdCatIdAndDelStatusAndCompanyId(catId, 1,compId);
			tempProdConfList = new ArrayList<>();

			for (int i = 0; i < prodList.size(); i++) {
				ProductMaster prod = prodList.get(i);

				float basicMrp = 0;// prod.getBasicMrp();

				// System.err.println(" Work For ONLY prod Id " +prod.getProductId());
				List<String> prodFlavIdList = Arrays.asList(prodList.get(i).getFlavourIds().split(",", -1));
				List<MFilter> flavList = getFlavList(prodFlavIdList);

				List<String> vegTypeStrList = Arrays.asList(prodList.get(i).getIsVeg().split(",", -1));
				List<MFilter> vegTypeList = getSortedVegNonVegList(vegTypeStrList);

				List<String> shapeIdsStr = Arrays.asList(prodList.get(i).getShapeId().split(",", -1));
				List<MFilter> shapeIdList = getSortedShapeList(shapeIdsStr);

				List<String> wtList = new ArrayList<String>();
				if (prod.getRateSettingType() == 2) {
					wtList = Arrays.asList(prodList.get(i).getAvailInWeights().split(",", -1));
				} else {
					wtList.add("1");
				}

				for (int w = 0; w < wtList.size(); w++) {
					float weight = Float.parseFloat(wtList.get(w));
					basicMrp = prod.getBasicMrp();

					basicMrp = basicMrp * weight;

					for (int v = 0; v < vegTypeList.size(); v++) {

						float vegPrice = vegTypeList.get(v).getAddOnRs();

						if (vegTypeList.get(v).getAddOnType() == 2) {
							vegPrice = vegTypeList.get(v).getAddOnRs() * weight;
						}
						//String shapeName = "Shape NA";
						String shapeName = "";

						float shapePrice = 0;
						float flavorPrice = 0;
						if (!shapeIdList.isEmpty()) {
							for (int s = 0; s < shapeIdList.size(); s++) {

								shapePrice = shapeIdList.get(s).getAddOnRs();

								if (shapeIdList.get(s).getAddOnType() == 2) {
									shapePrice = shapeIdList.get(s).getAddOnRs() * weight;
								}
								//String flvName = "Flavor NA";
								String flvName = "";
								flavorPrice = 0;
								if (!flavList.isEmpty()) {
									for (int f = 0; f < flavList.size(); f++) {

										flavorPrice = flavList.get(f).getAddOnRs();

										if (flavList.get(f).getAddOnType() == 2) {
											flavorPrice = flavList.get(f).getAddOnRs() * weight;
										}

										TempProdConfig config = new TempProdConfig();
										UUID uuid = UUID.randomUUID();
										config.setUuid(uuid.toString());
										config.setCatId(prod.getProdCatId());
										config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
										config.setFlavorId(flavList.get(f).getFilterId());
										config.setFlavorName(flavList.get(f).getFilterName());
										config.setProductId(prod.getProductId());
										config.setProductName(prod.getProductName());
										config.setRateSetingType(prod.getRateSettingType());
										config.setVegType(vegTypeList.get(v).getFilterId());
										config.setWeight(Float.parseFloat(wtList.get(w)));
										config.setVegNonVegName(vegTypeList.get(v).getFilterName());
										config.setShapeId(shapeIdList.get(s).getFilterId());
										config.setShapeName(shapeIdList.get(s).getFilterName());
										config.setMrpAmt(roundUp(basicMrp + vegPrice + shapePrice + flavorPrice));
										tempProdConfList.add(config);
									} // end of flavList for F
								} // end of if flavList not empty
								else {
									// flavor Empty
									//flvName = "NA Flavor";
									flvName = "";
									TempProdConfig config = new TempProdConfig();
									UUID uuid = UUID.randomUUID();
									config.setUuid(uuid.toString());
									config.setCatId(prod.getProdCatId());
									config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
									config.setFlavorId(0);
									config.setFlavorName(flvName);
									config.setProductId(prod.getProductId());
									config.setProductName(prod.getProductName());
									config.setRateSetingType(prod.getRateSettingType());
									config.setVegType(vegTypeList.get(v).getFilterId());
									config.setWeight(Float.parseFloat(wtList.get(w)));
									config.setVegNonVegName(vegTypeList.get(v).getFilterName());
									config.setShapeId(shapeIdList.get(s).getFilterId());
									config.setShapeName(shapeIdList.get(s).getFilterName());

									config.setMrpAmt(roundUp(basicMrp + vegPrice + shapePrice + flavorPrice));

									tempProdConfList.add(config);
								}
							} // end of shapeIdList for S
						} // end of if !shapeIdList.isEmpty()
						else {
							//shapeName = "Na";
							shapeName = "";
							flavorPrice = 0;

							if (!flavList.isEmpty()) {
								for (int f = 0; f < flavList.size(); f++) {

									flavorPrice = flavList.get(f).getAddOnRs();

									if (flavList.get(f).getAddOnType() == 2) {
										flavorPrice = flavList.get(f).getAddOnRs() * weight;
									}

									TempProdConfig config = new TempProdConfig();
									UUID uuid = UUID.randomUUID();
									config.setUuid(uuid.toString());
									config.setCatId(prod.getProdCatId());
									config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
									config.setFlavorId(flavList.get(f).getFilterId());
									config.setFlavorName(flavList.get(f).getFilterName());
									config.setProductId(prod.getProductId());
									config.setProductName(prod.getProductName());
									config.setRateSetingType(prod.getRateSettingType());
									config.setVegType(vegTypeList.get(v).getFilterId());
									config.setWeight(Float.parseFloat(wtList.get(w)));
									config.setVegNonVegName(vegTypeList.get(v).getFilterName());
									config.setShapeId(0);
									config.setShapeName(shapeName);
									config.setMrpAmt(roundUp(basicMrp + vegPrice + shapePrice + flavorPrice));

									tempProdConfList.add(config);

								} // end of flavList for F

							} // end of if flavList not empty

							else {
								// Flavor List Empty
								TempProdConfig config = new TempProdConfig();
								UUID uuid = UUID.randomUUID();
								config.setUuid(uuid.toString());
								config.setCatId(prod.getProdCatId());
								config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
								config.setFlavorId(0);
								config.setFlavorName("");
								config.setProductId(prod.getProductId());
								config.setProductName(prod.getProductName());
								config.setRateSetingType(prod.getRateSettingType());
								config.setVegType(vegTypeList.get(v).getFilterId());
								config.setWeight(Float.parseFloat(wtList.get(w)));
								config.setVegNonVegName(vegTypeList.get(v).getFilterName());
								config.setShapeId(0);
								config.setShapeName(" ");
								config.setMrpAmt(roundUp(basicMrp + vegPrice + shapePrice + flavorPrice));

								tempProdConfList.add(config);
							}
						} // end of shape "Na"
					} // end of vegTypeList For V

				} // end of wtList For W

			}
			// upto
			System.err.println("tempProdConfList " + tempProdConfList.toString());
			model.addObject("tempProdConfList", tempProdConfList);
			model.addObject("catId", catId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	// Sachin 22-10-2020
	public List<MFilter> getSortedVegNonVegList(List<String> vegNonVegIds) {
		List<MFilter> sortedVegNonVegList = new ArrayList<MFilter>();

		try {
			for (int a = 0; a < vegNonVegIds.size(); a++) {
				for (int i = 0; i < vegNonVegList.size(); i++) {
					Integer isSame = null;
					isSame = Integer.compare(Integer.parseInt(vegNonVegIds.get(a)), vegNonVegList.get(i).getFilterId());
					if (isSame.equals(0)) {
						sortedVegNonVegList.add(vegNonVegList.get(i));
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sortedVegNonVegList;
	}

	// Sachin 22-10-2020
	public List<MFilter> getSortedShapeList(List<String> shapeIds) {
		List<MFilter> sortedShapeList = new ArrayList<MFilter>();

		try {
			for (int a = 0; a < shapeIds.size(); a++) {
				for (int i = 0; i < shapeList.size(); i++) {
					Integer isSame = null;
					isSame = Integer.compare(Integer.parseInt(shapeIds.get(a)), shapeList.get(i).getFilterId());
					if (isSame.equals(0)) {
						sortedShapeList.add(shapeList.get(i));
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sortedShapeList;
	}

	public List<MFilter> getFlavList(List<String> flavIds) {
		List<MFilter> flavList = new ArrayList<MFilter>();

		try {
			for (int a = 0; a < flavIds.size(); a++) {
				for (int i = 0; i < filterList.size(); i++) {
					Integer isSame = null;
					isSame = Integer.compare(Integer.parseInt(flavIds.get(a)), filterList.get(i).getFilterId());
					if (isSame.equals(0)) {
						flavList.add(filterList.get(i));
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flavList;
	}

	/*****************************
	 * //Created Date: 18-09-2020 //UpdateDate:18-09-2020 //Description: to save
	 * Product Config Based on CatId //Devloped By(Devloper Name): Sachin //Updated
	 * By(Devloper Name): Sachin
	 ******************************/

	// saveInsertProdConf
	@RequestMapping(value = "/saveInsertProdConf", method = RequestMethod.POST)
	public String saveInsertProdConf(HttpServletRequest request, HttpServletResponse response) {

		try {

			ProductMaster prod = new ProductMaster();

			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");
			List<ItemConfDetail> confDetailList = new ArrayList<ItemConfDetail>();

			ItemConfHeader confHeader = new ItemConfHeader();
			confHeader.setApplicableFor("NA");
			confHeader.setCatId(tempProdConfList.get(0).getCatId());
			confHeader.setCompanyId(compId);
			confHeader.setConfigDesc("na");
			confHeader.setConfigHeaderId(0);
			confHeader.setConfigName(request.getParameter("conf_name"));
			confHeader.setDelStatus(1);

			confHeader.setExDate1("2020-09-18");
			confHeader.setExDate2("2020-09-18");

			confHeader.setExFloat1(1);
			confHeader.setExFloat2(1);
			confHeader.setExFloat3(1);

			confHeader.setExInt1(0);
			confHeader.setExInt2(0);
			confHeader.setExInt3(0);

			confHeader.setExVar1("na");
			confHeader.setExVar2("na");
			confHeader.setExVar3("na");
			confHeader.setExVar4("na");

			confHeader.setIsActive(1);
			confHeader.setDelStatus(1);
			confHeader.setInsertDttime(CommonUtility.getCurrentYMDDateTime());
			confHeader.setIsAllowToCopy(1);
			User userObj = (User) session.getAttribute("userObj");

			confHeader.setMakerUserId(userObj.getUserId());
			confHeader.setUpdtDttime(CommonUtility.getCurrentYMDDateTime());

			for (int i = 0; i < tempProdConfList.size(); i++) {

				float mrpAmt;

				TempProdConfig tempConf = tempProdConfList.get(i);

				String r1 = "0";
				try {
					r1 = request.getParameter("r1" + tempConf.getUuid() + "" + tempConf.getProductId());

					try {
						mrpAmt = Float.parseFloat(r1);
					} catch (Exception e) {
						mrpAmt = 0;
					}
					try {
						if (r1 == "" || r1.equals(null)) {
							continue;
						} else if (mrpAmt == 0) {
							continue;
						} else {
							// Create New Object And Save.
							float rateAmt;
							float spRateAmt1;
							float spRateAmt2;
							float spRateAmt3;
							float spRateAmt4;

							String r2 = request.getParameter("r2" + tempConf.getUuid() + "" + tempConf.getProductId());
							String r3 = request.getParameter("r3" + tempConf.getUuid() + "" + tempConf.getProductId());
							String r4 = request.getParameter("r4" + tempConf.getUuid() + "" + tempConf.getProductId());
							String r5 = request.getParameter("r5" + tempConf.getUuid() + "" + tempConf.getProductId());
							String r6 = request.getParameter("r6" + tempConf.getUuid() + "" + tempConf.getProductId());

							try {
								spRateAmt1 = Float.parseFloat(r3);
							} catch (Exception e) {
								spRateAmt1 = 0;
							}
							try {
								spRateAmt2 = Float.parseFloat(r4);
							} catch (Exception e) {
								spRateAmt2 = 0;
							}
							try {
								spRateAmt3 = Float.parseFloat(r5);
							} catch (Exception e) {
								spRateAmt3 = 0;
							}
							try {
								spRateAmt4 = Float.parseFloat(r6);
							} catch (Exception e) {
								spRateAmt4 = 0;
							}
							try {
								rateAmt = Float.parseFloat(r2);
							} catch (Exception e) {
								rateAmt = 0;
							}

							ItemConfDetail confDetail = new ItemConfDetail();

							confDetail.setExDate1("2020-09-18");
							confDetail.setExDate2("2020-09-19");

							confDetail.setExFloat1(0);
							confDetail.setExFloat2(0);
							confDetail.setExFloat3(0);

							confDetail.setExInt1(0);
							confDetail.setExInt2(0);
							confDetail.setExInt3(0);

							confDetail.setExVar1("na");
							confDetail.setExVar2("na");
							confDetail.setExVar3("na");
							confDetail.setExVar4("na");

							confDetail.setIsActive(1);
							confDetail.setDelStatus(1);

							confDetail.setFlavorId(tempConf.getFlavorId());
							confDetail.setInsertDttime(CommonUtility.getCurrentYMDDateTime());

							confDetail.setIsVeg(tempConf.getVegType());

							confDetail.setMakerUserId(userObj.getUserId());

							confDetail.setMrpAmt(mrpAmt);
							confDetail.setProductId(tempConf.getProductId());
							confDetail.setQty(tempConf.getWeight());

							confDetail.setRateAmt(rateAmt);
							confDetail.setRateSettingType(tempConf.getRateSetingType());

							confDetail.setSpRateAmt1(spRateAmt1);

							confDetail.setSpRateAmt2(spRateAmt2);
							confDetail.setSpRateAmt3(spRateAmt3);
							confDetail.setSpRateAmt4(spRateAmt4);
							confDetail.setUpdtDttime(CommonUtility.getCurrentYMDDateTime());

							confDetail.setExInt1(tempConf.getShapeId()); // Set Ex Int1 as shape Id 23-10-2020

							confDetailList.add(confDetail);

						} // End of Else

					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} // End Of tempProdConfList For Loop I
			confHeader.setItemConfDetList(confDetailList);
			if (!confDetailList.isEmpty()) {
				ItemConfHeader res = Constants.getRestTemplate().postForObject(Constants.url + "saveProdConfHD",
						confHeader, ItemConfHeader.class);

				if (res.getConfigHeaderId() > 0)
					session.setAttribute("successMsg", "Product Configuration Saved Sucessfully");
				else
					session.setAttribute("errorMsg", "Failed to Save Product Configuration");

			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return "redirect:/showViewProdConfigHeader";

	}

	/*****************************
	 * //Created Date: 21-09-2020 //UpdateDate:21-09-2020 //Description: to Show
	 * Product Config Header Based on CatId //Devloped By(Devloper Name): Sachin
	 * //Updated By(Devloper Name): Sachin
	 ******************************/

	@RequestMapping(value = "/showViewProdConfigHeader", method = RequestMethod.GET)
	public ModelAndView showViewProdConfigHeader(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("product/listProdConf");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showViewProdConfigHeader", "showViewProdConfigHeader", "1", "0",
					"0", "0", newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				int compId = (int) session.getAttribute("companyId");

				List<Category> catList = new ArrayList<>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", 1);

				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				for (int i = 0; i < catList.size(); i++) {

					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				model.addObject("catList", catList);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return model;
	}

	/*****************************
	 * //Created Date: 21-09-2020 //UpdateDate:21-09-2020 //Description: to Get
	 * Product Config Header Based on CatId //Devloped By(Devloper Name): Sachin
	 * //Updated By(Devloper Name): Sachin
	 ******************************/
	@RequestMapping(value = "/getViewProdConfigHeader", method = RequestMethod.POST)
	public ModelAndView getViewProdConfigHeader(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("product/listProdConf");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showViewProdConfigHeader", "showViewProdConfigHeader", "1", "0",
					"0", "0", newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				int compId = (int) session.getAttribute("companyId");

				List<Category> catList = new ArrayList<>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", 1);

				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				for (int i = 0; i < catList.size(); i++) {

					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				model.addObject("catList", catList);

				int catId = 0;

				try {

					catId = Integer.parseInt(request.getParameter("cat_id"));

				} catch (Exception e) {
					catId = 0;
				}
				model.addObject("catId", catId);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				map.add("catIdList", catId);

				GetItemConfHead[] confHeadArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "getProdConfList", map, GetItemConfHead[].class);
				List<GetItemConfHead> confHeadList = new ArrayList<GetItemConfHead>(Arrays.asList(confHeadArray));

				model.addObject("confHeadList", confHeadList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/*****************************
	 * //Created Date: 22-09-2020 //UpdateDate:22-09-2020 //Description: to Get
	 * Product Config Detail Based on Conf Header Id //Devloped By(Devloper Name):
	 * Sachin //Updated By(Devloper Name): Sachin
	 ******************************/

	// for Update operation these array list used
	// New Product Conf Detail
	List<TempProdConfig> tempProdUpdateConfList = new ArrayList<>();

	// Existing Product Conf Detail
	List<TempProdConfig> tempProdUpdateConfDetail = new ArrayList<>();

	@RequestMapping(value = "/getProdConfDetailByConfHeader", method = RequestMethod.GET)
	public ModelAndView getProdConfDetailByConfHeader(HttpServletRequest request, HttpServletResponse response) {

		System.err.println("In Method getProdConfDetailByConfHeader");

		ModelAndView model = new ModelAndView("product/editProdConfDetail");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info edit = AccessControll.checkAccess("showViewProdConfigHeader", "showViewProdConfigHeader", "0", "0",
					"1", "0", newModuleList);

			if (edit.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				int compId = (int) session.getAttribute("companyId");

				List<Category> catList = new ArrayList<>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", 1);

				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				for (int i = 0; i < catList.size(); i++) {

					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				model.addObject("catList", catList);

				int configHeaderId = 0;

				try {

					configHeaderId = Integer.parseInt(request.getParameter("configHeaderId"));

				} catch (Exception e) {
					configHeaderId = 0;
				}
				model.addObject("configHeaderId", configHeaderId);

				map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				map.add("configHeaderId", configHeaderId);

				TempConfTraveller infoRes = Constants.getRestTemplate()
						.postForObject(Constants.url + "getProdConfDetailByConfHeader", map, TempConfTraveller.class);
				// List<TempProdConfig> tempProdConfList = new ArrayList<TempProdConfig>();
				tempProdUpdateConfList = new ArrayList<TempProdConfig>();

				tempProdUpdateConfList = infoRes.getTempProdConfList();

				model.addObject("tempProdConfList", tempProdUpdateConfList);

				// List<TempProdConfig> prodConfDetList = new ArrayList<TempProdConfig>();
				tempProdUpdateConfDetail = new ArrayList<TempProdConfig>();

				tempProdUpdateConfDetail = infoRes.getProdConfDetailList();

				model.addObject("prodConfDetList", tempProdUpdateConfDetail);

				GetItemConfHead prodConfHead = infoRes.getConfHead();
				model.addObject("prodConfHead", prodConfHead);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	//

	/*****************************
	 * //Created Date: 23-09-2020 //UpdateDate:23-09-2020 //Description: To Save
	 * Prod Conf Header Detail Edit //Developed By(Developer Name): Sachin //Updated
	 * By(Developer Name): Sachin
	 ******************************/
	@RequestMapping(value = "/saveUpdateProdConf", method = RequestMethod.POST)
	public String saveUpdateProdConf(HttpServletRequest request, HttpServletResponse response) {
		// List<TempProdConfig> tempProdUpdateConfList = new ArrayList<>();
		// List<TempProdConfig> tempProdUpdateConfDetail = new ArrayList<>();

		try {
			ProductMaster prod = new ProductMaster();

			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");
			List<ItemConfDetail> confDetailList = new ArrayList<ItemConfDetail>();

			List<TempProdConfig> confDetailUpdateList = new ArrayList<TempProdConfig>();
			ItemConfHeader confHeader = new ItemConfHeader();
			confHeader.setApplicableFor("NA");
			confHeader.setCatId(tempProdUpdateConfDetail.get(0).getCatId());
			confHeader.setCompanyId(compId);
			confHeader.setConfigDesc("na");
			confHeader.setConfigHeaderId(Integer.parseInt(request.getParameter("conf_id")));
			confHeader.setConfigName(request.getParameter("conf_name"));
			confHeader.setDelStatus(1);

			confHeader.setExDate1("2020-09-18");
			confHeader.setExDate2("2020-09-18");

			confHeader.setExFloat1(1);
			confHeader.setExFloat2(1);
			confHeader.setExFloat3(1);

			confHeader.setExInt1(0);
			confHeader.setExInt2(0);
			confHeader.setExInt3(0);

			confHeader.setExVar1("na");
			confHeader.setExVar2("na");
			confHeader.setExVar3("na");
			confHeader.setExVar4("na");

			confHeader.setIsActive(1);
			confHeader.setDelStatus(1);
			confHeader.setInsertDttime(CommonUtility.getCurrentYMDDateTime());
			confHeader.setIsAllowToCopy(1);
			User userObj = (User) session.getAttribute("userObj");

			confHeader.setMakerUserId(userObj.getUserId());
			confHeader.setUpdtDttime(CommonUtility.getCurrentYMDDateTime());

			/*****************************************
			 * for Adding New Product detail
			 *****************************************/
			for (int i = 0; i < tempProdUpdateConfList.size(); i++) {

				float mrpAmt;

				TempProdConfig tempConf = tempProdUpdateConfList.get(i);

				String r1 = "0";
				try {
					r1 = request.getParameter("r1" + tempConf.getUuid() + "" + tempConf.getProductId());

					try {
						mrpAmt = Float.parseFloat(r1);
					} catch (Exception e) {
						mrpAmt = 0;
					}

					if (r1 == "" || r1.equals(null)) {
						continue;
					} else if (mrpAmt == 0) {
						continue;
					} else {
						// Create New Object And Save.
						float rateAmt;
						float spRateAmt1;
						float spRateAmt2;
						float spRateAmt3;
						float spRateAmt4;

						String r2 = request.getParameter("r2" + tempConf.getUuid() + "" + tempConf.getProductId());
						String r3 = request.getParameter("r3" + tempConf.getUuid() + "" + tempConf.getProductId());
						String r4 = request.getParameter("r4" + tempConf.getUuid() + "" + tempConf.getProductId());
						String r5 = request.getParameter("r5" + tempConf.getUuid() + "" + tempConf.getProductId());
						String r6 = request.getParameter("r6" + tempConf.getUuid() + "" + tempConf.getProductId());

						try {
							spRateAmt1 = Float.parseFloat(r3);
						} catch (Exception e) {
							spRateAmt1 = 0;
						}
						try {
							spRateAmt2 = Float.parseFloat(r4);
						} catch (Exception e) {
							spRateAmt2 = 0;
						}
						try {
							spRateAmt3 = Float.parseFloat(r5);
						} catch (Exception e) {
							spRateAmt3 = 0;
						}
						try {
							spRateAmt4 = Float.parseFloat(r6);
						} catch (Exception e) {
							spRateAmt4 = 0;
						}
						try {
							rateAmt = Float.parseFloat(r2);
						} catch (Exception e) {
							rateAmt = 0;
						}

						ItemConfDetail confDetail = new ItemConfDetail();

						confDetail.setConfigHeaderId(confHeader.getConfigHeaderId());

						confDetail.setExDate1("2020-09-18");
						confDetail.setExDate2("2020-09-19");

						confDetail.setExFloat1(0);
						confDetail.setExFloat2(0);
						confDetail.setExFloat3(0);

						confDetail.setExInt1(tempConf.getShapeId());
						confDetail.setExInt2(0);
						confDetail.setExInt3(0);

						confDetail.setExVar1("na");
						confDetail.setExVar2("na");
						confDetail.setExVar3("na");
						confDetail.setExVar4("na");

						confDetail.setIsActive(1);
						confDetail.setDelStatus(1);

						confDetail.setFlavorId(tempConf.getFlavorId());
						confDetail.setInsertDttime(CommonUtility.getCurrentYMDDateTime());

						confDetail.setIsVeg(tempConf.getVegType());
						confDetail.setMakerUserId(userObj.getUserId());

						confDetail.setMrpAmt(mrpAmt);
						confDetail.setProductId(tempConf.getProductId());
						confDetail.setQty(tempConf.getWeight());

						confDetail.setRateAmt(rateAmt);
						confDetail.setRateSettingType(tempConf.getRateSetingType());

						confDetail.setSpRateAmt1(spRateAmt1);

						confDetail.setSpRateAmt2(spRateAmt2);
						confDetail.setSpRateAmt3(spRateAmt3);
						confDetail.setSpRateAmt4(spRateAmt4);
						confDetail.setUpdtDttime(CommonUtility.getCurrentYMDDateTime());

						confDetailList.add(confDetail);

					} // End of Else

				} catch (Exception e) {
					e.printStackTrace();
				}
			} // End Of tempProdConfList For Loop I

			/*****************************************
			 * for Existing Product detail Update
			 *****************************************/
			for (int p = 0; p < tempProdUpdateConfDetail.size(); p++) {

				float mrpAmt;

				TempProdConfig tempConf = tempProdUpdateConfDetail.get(p);

				int isChange = 0;
				try {

					isChange = Integer.parseInt(
							request.getParameter("is_change" + tempConf.getUuid() + "" + tempConf.getProductId()));

				} catch (Exception e) {
					isChange = 0;
				}

				if (isChange == 0) {
					continue;
				}

				String r1 = "0";
				try {
					r1 = request.getParameter("r1" + tempConf.getUuid() + "" + tempConf.getProductId());
					try {
						mrpAmt = Float.parseFloat(r1);
					} catch (Exception e) {
						mrpAmt = 0;
					}
					// Create New Object And Save.
					float rateAmt;
					float spRateAmt1;
					float spRateAmt2;
					float spRateAmt3;
					float spRateAmt4;

					String r2 = request.getParameter("r2" + tempConf.getUuid() + "" + tempConf.getProductId());
					String r3 = request.getParameter("r3" + tempConf.getUuid() + "" + tempConf.getProductId());
					String r4 = request.getParameter("r4" + tempConf.getUuid() + "" + tempConf.getProductId());
					String r5 = request.getParameter("r5" + tempConf.getUuid() + "" + tempConf.getProductId());
					String r6 = request.getParameter("r6" + tempConf.getUuid() + "" + tempConf.getProductId());

					try {
						spRateAmt1 = Float.parseFloat(r3);
					} catch (Exception e) {
						spRateAmt1 = 0;
					}
					try {
						spRateAmt2 = Float.parseFloat(r4);
					} catch (Exception e) {
						spRateAmt2 = 0;
					}
					try {
						spRateAmt3 = Float.parseFloat(r5);
					} catch (Exception e) {
						spRateAmt3 = 0;
					}
					try {
						spRateAmt4 = Float.parseFloat(r6);
					} catch (Exception e) {
						spRateAmt4 = 0;
					}
					try {
						rateAmt = Float.parseFloat(r2);
					} catch (Exception e) {
						rateAmt = 0;
					}

					// ItemConfDetail confDetail = new ItemConfDetail();
					TempProdConfig confUpdt = new TempProdConfig();

					/*
					 * confDetail.setConfigHeaderId(confHeader.getConfigHeaderId());
					 * confDetail.setConfigDetailId(tempConf.getConfigDetailId());
					 * confDetail.setMakerUserId(userObj.getUserId());
					 * 
					 * confDetail.setMrpAmt(mrpAmt); confDetail.setRateAmt(rateAmt);
					 * 
					 * confDetail.setSpRateAmt1(spRateAmt1); confDetail.setSpRateAmt2(spRateAmt2);
					 * confDetail.setSpRateAmt3(spRateAmt3); confDetail.setSpRateAmt4(spRateAmt4);
					 * 
					 * confDetail.setUpdtDttime(CommonUtility.getCurrentYMDDateTime());
					 */

					confUpdt.setConfigHeaderId(confHeader.getConfigHeaderId());
					confUpdt.setConfigDetailId(tempConf.getConfigDetailId());
					confUpdt.setMakerUserId(userObj.getUserId());

					confUpdt.setMrpAmt(mrpAmt);
					confUpdt.setRateAmt(rateAmt);

					confUpdt.setSpRateAmt1(spRateAmt1);
					confUpdt.setSpRateAmt2(spRateAmt2);
					confUpdt.setSpRateAmt3(spRateAmt3);
					confUpdt.setSpRateAmt4(spRateAmt4);

					confUpdt.setUpdtDttime(CommonUtility.getCurrentYMDDateTime());

					confDetailUpdateList.add(confUpdt);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			confHeader.setItemConfDetList(confDetailList);
			TempConfTraveller traveller = new TempConfTraveller();

			traveller.setProdConfDetailList(confDetailUpdateList);
			traveller.setConfDetailList(confDetailList);

			GetItemConfHead head = new GetItemConfHead();

			head.setConfigHeaderId(confHeader.getConfigHeaderId());
			head.setConfigName(confHeader.getConfigName());
			head.setCatId(confHeader.getMakerUserId());
			head.setCatName(confHeader.getUpdtDttime());

			traveller.setConfHead(head);

			ItemConfHeader res = Constants.getRestTemplate().postForObject(Constants.url + "saveUpdateProdConfHD",
					traveller, ItemConfHeader.class);
			System.err.println("saveUpdateProdConf " + res.toString());
			if (res.getConfigHeaderId() > 0)
				session.setAttribute("successMsg", "Product Configuration Saved Sucessfully");
			else
				session.setAttribute("errorMsg", "Failed to Save Product Configuration");

		} catch (Exception e) {
			e.printStackTrace();

		}

		return "redirect:/showViewProdConfigHeader";

	}

	/*****************************
	 * //Created Date: 26-09-2020 //UpdateDate:26-09-2020 //Description: To Show
	 * Product Edit page //Developed By(Developer Name): Sachin //Updated
	 * By(Developer Name): Sachin
	 ******************************/
	@RequestMapping(value = "/showEditProd/{productIdStr}", method = RequestMethod.GET)
	public ModelAndView showEditProd(@PathVariable String productIdStr, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("product/editProd");

		try {
			model.addObject("isEdit", 1);
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showProdList", "showProdList", "0", "0", "1", "0", newModuleList);

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

				
				model.addObject("catId", 0);

				MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFilter", map,
						MFilter[].class);
				filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

				for (int i = 0; i < filterList.size(); i++) {

					filterList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(filterList.get(i).getFilterId())));
				}
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

				map = new LinkedMultiValueMap<>();
				model.addObject("productId", productIdStr);
				int productId = Integer.parseInt(FormValidation.DecodeKey(productIdStr));
				map.add("productId", productId);

				ProductMaster editProd = Constants.getRestTemplate()
						.postForObject(Constants.url + "getProductByProductId", map, ProductMaster.class);
				model.addObject("editProd", editProd);
				model.addObject("prodImgUrl", Constants.PROD_IMG_VIEW_URL);

				 map = new LinkedMultiValueMap<>();
					map.add("compId", 1);
				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				for (int i = 0; i < catList.size(); i++) {

					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				model.addObject("catList", catList);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;

	}

	/*****************************
	 * //Created Date: 26-09-2020 //UpdateDate:26-09-2020 //Description: To Show
	 * manage Product Image List //Developed By(Developer Name): Sachin //Updated
	 * By(Developer Name): Sachin
	 ******************************/
	@RequestMapping(value = "/manageProdImages/{productIdStr}", method = RequestMethod.GET)
	public ModelAndView manageProdImages(@PathVariable String productIdStr, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = null;

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info edit = AccessControll.checkAccess("showProdList", "showProdList", "0", "0", "1", "0", newModuleList);

			if (edit.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				model = new ModelAndView("product/prod_images");
				model.addObject("isEdit", 1);
				model.addObject("productIdStr", productIdStr);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				int productId = Integer.parseInt(FormValidation.DecodeKey(productIdStr));

				map.add("productId", productId);

				String[] imageArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "getProdImagesByProductId", map, String[].class);

				List<String> imageList = new ArrayList<String>(Arrays.asList(imageArray));

				model.addObject("imageList", imageList);
				model.addObject("imageJSON", CommonUtility.toJSONString(imageList));

				model.addObject("imageUrl", Constants.PROD_IMG_VIEW_URL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	/*****************************
	 * //Created Date: 28-09-2020 //UpdateDate:28-09-2020 //Description: To
	 * add/update Product Image List //Developed By(Developer Name): Sachin
	 * //Updated By(Developer Name): Sachin
	 ******************************/

	@ResponseBody
	@RequestMapping(value = "/ajaxImageUploadProduct/{productIdStr}", method = RequestMethod.POST)
	public String ajaxImageUploadOffer(@PathVariable String productIdStr, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("files") List<MultipartFile> files) {

		int productId = Integer.parseInt(FormValidation.DecodeKey(productIdStr));
		try {

			Info info = new Info();

			String filesList = new String();

			if (productId > 0) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("productId", productId);

				ProductMaster prodMaster = Constants.getRestTemplate()
						.postForObject(Constants.url + "getProductByProductId", map, ProductMaster.class);

				if (files.size() > 0) {

					for (int i = 0; i < files.size(); i++) {
						Random random = new Random();
						int randomInt = random.nextInt(100);

						String ext = files.get(i).getOriginalFilename().split("\\.")[1];
						String fileName = CommonUtility.getCurrentTimeStamp() + "_" + randomInt + "." + ext;
						// new ImageUploadController().saveUploadedFiles(files.get(i), 1, fileName);
//dsds
						info=new ImageUploadController().saveImgFilesProdImg(files.get(i),Constants.imageFileExtensions, fileName);
						//info = new ImageUploadController().saveProdImgeWithResize(files.get(i), fileName, 450, 250);

						if (filesList.isEmpty()) {

							filesList = fileName;
						} else {

							filesList = filesList.concat("," + fileName);

						}

					}

					if (prodMaster.getProductImages().length() > 0) {

						filesList = prodMaster.getProductImages().concat("," + filesList);

					}

					if (info != null) {
						if (!info.isError()) {
							map = new LinkedMultiValueMap<>();
							map.add("filesList", filesList);
							map.add("productId", productId);
							Constants.getRestTemplate().postForObject(Constants.url + "updateProdImg", map, Info.class);
						}
					}
				}
			} else {
				return "false";
			}

		} catch (Exception e) {

			e.printStackTrace();

			return "false";

		}
		return "true";

	}

	/*****************************
	 * //Created Date: 28-09-2020 //UpdateDate:28-09-2020 //Description: To delete
	 * Product Image from image List //Developed By(Developer Name): Sachin
	 * //Updated By(Developer Name): Sachin
	 ******************************/
	@RequestMapping(value = "/deleteProductImageAjax", method = RequestMethod.GET)
	public @ResponseBody Info deleteOfferImageAjax(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		String pIdStr = request.getParameter("productId");
		int productId = Integer.parseInt(FormValidation.DecodeKey(pIdStr));

		String imageName = request.getParameter("imageName");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("productId", productId);
		map.add("imageName", imageName);

		info = Constants.getRestTemplate().postForObject(Constants.url + "/removeImageFromProduct", map, Info.class);

		try {

			if (!info.isError()) {
				File f = new File(Constants.UPLOAD_URL + imageName);
				f.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}

	@RequestMapping(value = "/getItemImagesByProductId", method = RequestMethod.GET)
	public @ResponseBody List<String> getItemImagesByDocIdAndDocType(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> imgList = new ArrayList<String>();
		try {
			// int productId = Integer.parseInt(request.getParameter("productId"));

			String pIdStr = request.getParameter("productId");
			int productId = Integer.parseInt(FormValidation.DecodeKey(pIdStr));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("productId", productId);

			String[] imgArr = Constants.getRestTemplate().postForObject(Constants.url + "getProdImagesByProductId", map,
					String[].class);
			imgList = new ArrayList<String>(Arrays.asList(imgArr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgList;
	}

}
