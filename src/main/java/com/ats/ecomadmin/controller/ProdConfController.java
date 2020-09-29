package com.ats.ecomadmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import com.ats.ecomadmin.model.GetItemConfHead;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.ItemConfDetail;
import com.ats.ecomadmin.model.ItemConfHeader;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.TempProdConfig;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;

//Sachin 
//24-09-2020
@Controller
@Scope("session")
public class ProdConfController {

	/*****************************
	 * //Created Date: 24-09-2020 //UpdateDate:24-09-2020 //Description: to show Add
	 * Existing Product Config Header Based on CatId //Devloped By : Sachin //Updated By : Sachin
	 ******************************/

	@RequestMapping(value = "/showAddNewItemsinExProdConf", method = RequestMethod.GET)
	public ModelAndView showAddNewItemsinExProdConf(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("product/new_item_in_ExProdConf");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddNewItemsinExProdConf", "showAddNewItemsinExProdConf", "1", "0",
					"0", "0", newModuleList);

			if (view.isError() == true) {

				model = new ModelAndView("accessDenied");

			} else {
				int compId = (int) session.getAttribute("companyId");

				List<Category> catList = new ArrayList<>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));
				String catIdList=new String();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < catList.size(); i++) {
					sb = sb.append(catList.get(i).getCatId() + ",");
					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				catIdList = sb.toString();
				catIdList = catIdList.substring(0, catIdList.length() - 1);

				model.addObject("catList", catList);
				model.addObject("catId", 0);
				model.addObject("configId", 0);
				
				map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				map.add("catIdList", catIdList);

				GetItemConfHead[] confHeadArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "getProdConfList", map, GetItemConfHead[].class);
				List<GetItemConfHead> confHeadList = new ArrayList<GetItemConfHead>(Arrays.asList(confHeadArray));
				model.addObject("confHeadList", confHeadList);
				
				model.addObject("confHeadJSON", CommonUtility.toJSONString(confHeadList));
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return model;
	}

	//Sachin 24-09-2020
	List<MFilter> filterList = new ArrayList<MFilter>();

	List<ProductMaster> prodList = null;
	List<TempProdConfig> tempProdConfList = new ArrayList<>();

	@RequestMapping(value = { "/getNewItemInConf" }, method = RequestMethod.POST)
	public ModelAndView getProdConf(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("product/new_item_in_ExProdConf");

		int catId = 0;
		try {
			catId = Integer.parseInt(request.getParameter("cat_id"));
		} catch (Exception e) {
			catId = 0;
		}
		
		int configId = 0;
		try {
			configId = Integer.parseInt(request.getParameter("conf_id"));
		} catch (Exception e) {
			configId = 0;
		}

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		HttpSession session = request.getSession();
		int companyId = (int) session.getAttribute("companyId");


		map = new LinkedMultiValueMap<>();
		map.add("compId", companyId);
		map.add("catId", catId);
		map.add("configId", configId);
		ProductMaster[] prodArray = Constants.getRestTemplate().postForObject(Constants.url + "getProdListForAddingNewItemInExConf",
				map, ProductMaster[].class);

		prodList = new ArrayList<ProductMaster>(Arrays.asList(prodArray));
		model.addObject("prodList", prodList);

		map = new LinkedMultiValueMap<>();
		map.add("compId", companyId);
		List<Category> catList = new ArrayList<>();

		Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
				Category[].class);
		catList = new ArrayList<Category>(Arrays.asList(catArr));
		String catIdList=new String();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < catList.size(); i++) {
			sb = sb.append(catList.get(i).getCatId() + ",");
			catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
		}

		catIdList = sb.toString();
		catIdList = catIdList.substring(0, catIdList.length() - 1);

		model.addObject("catList", catList);

		map = new LinkedMultiValueMap<>();
		map.add("companyId", companyId);
		map.add("catIdList", catIdList);

		GetItemConfHead[] confHeadArray = Constants.getRestTemplate()
				.postForObject(Constants.url + "getProdConfList", map, GetItemConfHead[].class);
		List<GetItemConfHead> confHeadList = new ArrayList<GetItemConfHead>(Arrays.asList(confHeadArray));
		model.addObject("confHeadList", confHeadList);
		
		model.addObject("confHeadJSON", CommonUtility.toJSONString(confHeadList));
		
		model.addObject("configId", configId);
		model.addObject("catId", catId);
		try {

			// Get Filter By Comp Id and Filter type ie 4 for Flavor
			// filterList = filterRepo.getFiltersByFilterId(compId, 4);

			map.add("filterTypeId", 4);
			map.add("compId", companyId);
			filterList = new ArrayList<MFilter>();

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

				if (prodList.get(i).getIsVeg() == 2) {

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

										config.setVegType(prodList.get(i).getIsVeg());

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

									config.setVegType(prodList.get(i).getIsVeg());

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


		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}
	//Sachin 24-09-2020
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
	
	//Sachin 24-09-2020
	//saveInsertProdConfEx
	
	@RequestMapping(value = "/saveInsertProdConfEx", method = RequestMethod.POST)
	public String saveInsertProdConfEx(HttpServletRequest request, HttpServletResponse response) {
String mav=new String();
		try {
			
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info add = AccessControll.checkAccess("showAddNewItemsinExProdConf", "showAddNewItemsinExProdConf", "0", "1", "0", "0", newModuleList);

			if (add.isError() == true) {

				mav = "redirect:/accessDenied";

			} else {
				mav="redirect:/showViewProdConfigHeader";
			
			List<ItemConfDetail> confDetailList = new ArrayList<ItemConfDetail>();

			
			User userObj = (User) session.getAttribute("userObj");
			int configId = 0;
			try {
				configId = Integer.parseInt(request.getParameter("configId"));
			} catch (Exception e) {
				configId = 0;
			}

			if(configId>0)
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

						confDetail.setConfigHeaderId(configId);
						
						confDetail.setExDate1("2020-09-23");
						confDetail.setExDate2("2020-09-24");

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
						
						confDetailList.add(confDetail);

					} // End of Else

				} catch (Exception e) {
					e.printStackTrace();
				}
			} // End Of tempProdConfList For Loop I

			ItemConfDetail[] res = Constants.getRestTemplate().postForObject(Constants.url + "saveNewItemToProdConf", confDetailList,
					ItemConfDetail[].class);
			
			if (res.length> 0) 
				session.setAttribute("successMsg", "New Products Added in Configuration");
				else
				session.setAttribute("errorMsg", "Failed to Add New Products in Configuration");
		
			}//end of else
		
		//end of try
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		return mav;

	}

}
