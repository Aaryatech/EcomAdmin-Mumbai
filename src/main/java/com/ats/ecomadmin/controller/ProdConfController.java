package com.ats.ecomadmin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.GetItemConfHead;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.ItemConfDetail;
import com.ats.ecomadmin.model.ItemConfHeader;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.TempProdConfig;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.offer.GetConfigureOfferList;

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

	@RequestMapping(value = { "/getNewItemInConfOld" }, method = RequestMethod.POST)
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

				/* 24-10
				 * if (prodList.get(i).getIsVeg() == 2) {
				 * 
				 * vegNonVegList.add(0); vegNonVegList.add(1);
				 * 
				 * } else {
				 * 
				 * }
				 */
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

										//24-10config.setVegType(prodList.get(i).getIsVeg());

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

								//24-10	config.setVegType(prodList.get(i).getIsVeg());

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
	
	
	//Sachin 26-10-2020
	List<MFilter> vegNonVegList = new ArrayList<MFilter>();// Sachin 22-10-2020
	List<MFilter> shapeList = new ArrayList<MFilter>();// Sachin 22-10-2020

	public  float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	@RequestMapping(value = { "/getNewItemInConf" }, method = RequestMethod.POST)
	public ModelAndView getProdConfNew(HttpServletRequest request, HttpServletResponse response) {
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

			
			map = new LinkedMultiValueMap<>();
			map.add("filterTypeId", 12);
			map.add("compId", companyId);

			MFilter[] vegNonVegTypeArray = Constants.getRestTemplate().postForObject(Constants.url + "getFiltersListByTypeId",
					map, MFilter[].class);
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
				ProductMaster prod=prodList.get(i);
				
				float basicMrp=0;//prod.getBasicMrp();
				
				//	System.err.println(" Work For  ONLY prod Id " +prod.getProductId());
				List<String> prodFlavIdList = Arrays.asList(prodList.get(i).getFlavourIds().split(",", -1));
				List<MFilter> flavList = getFlavList(prodFlavIdList);

				List<String> vegTypeStrList = Arrays.asList(prodList.get(i).getIsVeg().split(",", -1));
				List<MFilter> vegTypeList = getSortedVegNonVegList(vegTypeStrList);
				
				List<String> shapeIdsStr = Arrays.asList(prodList.get(i).getShapeId().split(",", -1));
				List<MFilter> shapeIdList = getSortedShapeList(shapeIdsStr);
				
				List<String> wtList=new ArrayList<String>();
				if (prod.getRateSettingType() == 2) {
					wtList = Arrays.asList(prodList.get(i).getAvailInWeights().split(",", -1));
				}else {
					wtList.add("1");
				}
				
				for (int w = 0; w < wtList.size(); w++) {
					float weight=Float.parseFloat(wtList.get(w));
					basicMrp=prod.getBasicMrp();

					basicMrp=basicMrp*weight;
							
					for(int v=0;v<vegTypeList.size();v++) {
						
						float vegPrice=vegTypeList.get(v).getAddOnRs();
						
						if(vegTypeList.get(v).getAddOnType()==2) {
							vegPrice=vegTypeList.get(v).getAddOnRs()*weight;
						}
						String shapeName="Shape NA";
						
						float shapePrice=0;
						float flavorPrice=0;
							if(!shapeIdList.isEmpty()) {
								for(int s=0;s<shapeIdList.size();s++) {

									shapePrice=shapeIdList.get(s).getAddOnRs();
									
									if(shapeIdList.get(s).getAddOnType()==2) {
										shapePrice=shapeIdList.get(s).getAddOnRs()*weight;
									}
									String flvName="Flavor NA";
									 flavorPrice=0;
										if(!flavList.isEmpty()) {
											for(int f=0;f<flavList.size();f++) {
												
												flavorPrice=flavList.get(f).getAddOnRs();
												
												if(flavList.get(f).getAddOnType()==2) {
													flavorPrice=flavList.get(f).getAddOnRs()*weight;
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
												config.setMrpAmt(roundUp(basicMrp+vegPrice+shapePrice+flavorPrice));
												tempProdConfList.add(config);
											}//end of flavList for F
										}//end of if flavList not empty
									else {
										//flavor Empty
										flvName="NA Flavor";
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
										
										config.setMrpAmt(roundUp(basicMrp+vegPrice+shapePrice+flavorPrice));

										tempProdConfList.add(config);
									}
								}//end of shapeIdList for S
							}// end of if !shapeIdList.isEmpty()
						else {
							shapeName="Na";
							 flavorPrice=0;
							 
								if(!flavList.isEmpty()) {
									for(int f=0;f<flavList.size();f++) {
										
										flavorPrice=flavList.get(f).getAddOnRs();
										
										if(flavList.get(f).getAddOnType()==2) {
											flavorPrice=flavList.get(f).getAddOnRs()*weight;
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
										config.setMrpAmt(roundUp(basicMrp+vegPrice+shapePrice+flavorPrice));

										tempProdConfList.add(config);
										
									}//end of flavList for F
									
								}//end of if flavList not empty
								
							else {
								//Flavor List Empty
								TempProdConfig config = new TempProdConfig();
								UUID uuid = UUID.randomUUID();
								config.setUuid(uuid.toString());
								config.setCatId(prod.getProdCatId());
								config.setCurTimeStamp(CommonUtility.getCurrentYMDDateTime());
								config.setFlavorId(0);
								config.setFlavorName("NA F");
								config.setProductId(prod.getProductId());
								config.setProductName(prod.getProductName());
								config.setRateSetingType(prod.getRateSettingType());
								config.setVegType(vegTypeList.get(v).getFilterId());
								config.setWeight(Float.parseFloat(wtList.get(w)));
								config.setVegNonVegName(vegTypeList.get(v).getFilterName());
								config.setShapeId(0);
								config.setShapeName("NA S");
								config.setMrpAmt(roundUp(basicMrp+vegPrice+shapePrice+flavorPrice));

								tempProdConfList.add(config);
							}
						}//end of shape "Na"
					}//end of vegTypeList For V
									
				}//end of wtList For W
				
			}
			model.addObject("tempProdConfList", tempProdConfList);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}
	
	
	//Sachin 22-10-2020
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
		
		//Sachin 22-10-2020
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
	
	
	//Sachin 26-10-2020
	// Created By :- Mahendra Singh
		// Created On :- 15-09-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Show Franchise
		@RequestMapping(value = "/showFrListToSyncData", method = RequestMethod.GET)
		public String showFrListToSyncData(HttpServletRequest request, HttpServletResponse response, Model model) {

			String mav = new String();

			try {

				HttpSession session = request.getSession();
				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("showFrListToSyncData", "showFranchises", "1", "0", "0", "0",
						newModuleList);

				if (view.isError() == true) {

					mav = "accessDenied";

				} else {
					int companyId = (int) session.getAttribute("companyId");

					mav = "product/fr_data_sync";

					List<Franchise> frList = new ArrayList<Franchise>();

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

					map = new LinkedMultiValueMap<>();
					map.add("compId", companyId);

					Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
							Franchise[].class);
					frList = new ArrayList<Franchise>(Arrays.asList(frArr));

					for (int i = 0; i < frList.size(); i++) {

						frList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(frList.get(i).getFrId())));
					}

					model.addAttribute("frList", frList);

					model.addAttribute("title", "Franchise Data Sync");

					
				}
			} catch (Exception e) {
				System.out.println("Execption in /showFranchises : " + e.getMessage());
				e.printStackTrace();
			}
			return mav;
		}

		@RequestMapping(value = "/generateFrDataJSON", method = RequestMethod.POST)
		public String generateFrDataJSON(HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();
				String[] frIdArray=request.getParameterValues("dataSyncFrId");

				String frIdString=getCommaSepStringFromStrArray(frIdArray);
				int compId = (int) session.getAttribute("companyId");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				map.add("frIdList",frIdString);

			Object jsonSaveRes = Constants.getRestTemplate().postForObject(Constants.url + "generateFrDataJSON", map, Object.class);
		if(jsonSaveRes!=null) 
			session.setAttribute("successMsg", "Franchise Data Sync Successful");
			else
			session.setAttribute("errorMsg", "Failed to Sync Data");
	
			}catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/showFrListToSyncData";
			
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
}
