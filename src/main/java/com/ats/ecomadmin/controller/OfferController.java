package com.ats.ecomadmin.controller;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.offer.GetConfigureOfferList;
import com.ats.ecomadmin.model.offer.GetOfferFrConfiguredList;
import com.ats.ecomadmin.model.offer.Images;
import com.ats.ecomadmin.model.offer.OfferConfig;
import com.ats.ecomadmin.model.offer.OfferDetail;
import com.ats.ecomadmin.model.offer.OfferHeader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@SessionScope
public class OfferController {
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 18-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- addNewOffers

	@RequestMapping(value = "/addNewOffers/{offerId}", method = RequestMethod.GET)
	public ModelAndView newOffers(@PathVariable int offerId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("franchisee/offers");

		try {

			int setOfferId = 0;
			int frequencyType = 1;
			int tab = 1;
			int itemSubTypeOffer = 0;

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			String dateStr = sdf.format(cal.getTime()) + " to " + sdf.format(cal.getTime());

			if (offerId > 0) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerId", offerId);

				OfferHeader res = Constants.getRestTemplate().postForObject(Constants.url + "getOfferHeaderById", map,
						OfferHeader.class);


				if (res != null && res.getOfferId() > 0) {
					model.addObject("offer", res);
					setOfferId = res.getOfferId();

					if (res.getOfferType() == 0) {
						tab = 2;
					} else {
						tab = 1;
					}

					if (!res.getFrequency().isEmpty()) {
						List<Integer> dayIds = Stream.of(res.getFrequency().split(",")).map(Integer::parseInt)
								.collect(Collectors.toList());

						model.addObject("dayIds", dayIds);
					} else {
						model.addObject("dayIds", "");
					}

					if (!res.getApplicableFor().isEmpty()) {
						List<Integer> applicableIds = Stream.of(res.getApplicableFor().split(","))
								.map(Integer::parseInt).collect(Collectors.toList());

						model.addObject("applicableIds", applicableIds);
					} else {
						model.addObject("applicableIds", "");
					}

					frequencyType = res.getFrequencyType();

					String fromDate = CommonUtility.convertToDMY(res.getFromDate());
					String toDate = CommonUtility.convertToDMY(res.getToDate());

					dateStr = fromDate + " to " + toDate;

					try {

						if (res.getOfferType() > 0) {

							MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<>();
							map1.add("offerId", offerId);

							OfferDetail[] detailList = Constants.getRestTemplate().postForObject(
									Constants.url + "getOfferDetailListByOfferId", map1, OfferDetail[].class);
							List<OfferDetail> offerDetailList = new ArrayList<OfferDetail>(Arrays.asList(detailList));
							model.addObject("offerDetailList", offerDetailList);

							if (offerDetailList != null) {
								itemSubTypeOffer = offerDetailList.get(0).getOfferSubType();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				model.addObject("itemSubTypeOffer", itemSubTypeOffer);

			} else {

				// added by harsha
				OfferHeader offer = new OfferHeader();

				model.addObject("offer", offer);

			}

			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			/*
			 * ItemListForOfferDetail[] arr = Constants.getRestTemplate().postForObject(
			 * Constants.url + "getItemListForOfferDetailByCompId", map,
			 * ItemListForOfferDetail[].class); List<ItemListForOfferDetail> itemList = new
			 * ArrayList<ItemListForOfferDetail>(Arrays.asList(arr));
			 * System.err.println("ITEM LIST = " + itemList);
			 */

			model.addObject("frequencyType", frequencyType);
			model.addObject("dateStr", dateStr);
			model.addObject("tab", tab);
			/* model.addObject("itemList", itemList); */
			model.addObject("offerId", setOfferId);

			model.addObject("imageUrl", Constants.IMAGE_URL);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 18-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- saveOfferHeader

	@RequestMapping(value = "/saveOfferHeader", method = RequestMethod.POST)
	public String saveOfferHeader(HttpServletRequest request, HttpServletResponse response) {

		int offerId = 0;

		try {
			HttpSession session = request.getSession();

			offerId = Integer.parseInt(request.getParameter("offerId"));
			String title = request.getParameter("offerTitle");
			String desc = request.getParameter("offerDesc");
			int type = Integer.parseInt(request.getParameter("selectType"));
			String offerDate = request.getParameter("offerDate");
			String fromTime = request.getParameter("fromTime");
			String time = request.getParameter("time");

			String toTime = request.getParameter("toTime");
			int freqType = Integer.parseInt(request.getParameter("freq_type"));

			int userId = (int) session.getAttribute("userId");
			int compId = (int) session.getAttribute("companyId");
			String daysIdsArray[] = request.getParameterValues("selectDay");

			String daysList = "";
			if (daysIdsArray != null) {
				List<String> daysIdList = new ArrayList<>();
				daysIdList = Arrays.asList(daysIdsArray);

				daysList = daysIdList.toString().substring(1, daysIdList.toString().length() - 1);
				daysList = daysList.replaceAll("\"", "");
				daysList = daysList.replaceAll(" ", "");
			}

			String applicableArray[] = request.getParameterValues("selectApplicableTo");

			String applicableList = "";
			if (applicableArray != null) {
				List<String> applicableTempList = new ArrayList<>();
				applicableTempList = Arrays.asList(applicableArray);

				applicableList = applicableTempList.toString().substring(1, applicableTempList.toString().length() - 1);
				applicableList = applicableList.replaceAll("\"", "");
				applicableList = applicableList.replaceAll(" ", "");
			}

			String fromDate = "";
			String toDate = "";

			String temp[] = offerDate.split(" to ");
			fromDate = CommonUtility.convertToYMD(temp[0]);
			toDate = CommonUtility.convertToYMD(temp[1]);

			if (freqType == 1) {
				// applicableList = "";
			} else if (freqType == 2) {
				daysList = "";
			}


			OfferHeader offer = new OfferHeader(offerId, title, desc, type, applicableList, 0, freqType, daysList,
					fromDate, toDate, fromTime, toTime, userId, CommonUtility.getCurrentYMDDateTime(),
					CommonUtility.getCurrentYMDDateTime(), compId, 1, 1, 0, 0, 0, 0, "", "", "", "", 0, 0, 0, 0);


			OfferHeader res = Constants.getRestTemplate().postForObject(Constants.url + "saveOfferHeader", offer,
					OfferHeader.class);


			if (res != null) {
				offerId = res.getOfferId();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addNewOffers/" + offerId;
	}

	// -----------------------SAVE OFFER DETAIL----------------------------
	
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 18-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- saveOfferDetail
	@RequestMapping(value = "/saveOfferDetail", method = RequestMethod.POST)
	public String saveOfferDetail(HttpServletRequest request, HttpServletResponse response) {

		int offerId = 0;

		try {
			HttpSession session = request.getSession();

			offerId = Integer.parseInt(request.getParameter("tab2OfferId"));

			int billWiseOfferDetailId = 0;
			try {
				billWiseOfferDetailId = Integer.parseInt(request.getParameter("billWiseDetailId"));
			} catch (Exception e) {
			}

			int type = Integer.parseInt(request.getParameter("offerTypeWise"));
			String coupon = request.getParameter("billWiseCoupon");

			float disc = 0, limit = 0,minDisc=0;
			try {
				disc = Float.parseFloat(request.getParameter("billWiseDisc"));
				minDisc = Float.parseFloat(request.getParameter("billWiseDiscMin"));
				limit = Float.parseFloat(request.getParameter("billWiseLimit"));
			} catch (Exception e) {
			}

			int subType = 0;
			if (type == 1) {
				subType = 1;
			} /*
				 * else { subType =
				 * Integer.parseInt(request.getParameter("itemWiseOfferSelect")); }
				 */
			List<OfferDetail> detailList = new ArrayList<>();

			String deleteUncheckItemDiscIds = "";
			String deleteBuyGetFreeIds = "";

			if (type == 1) { // SAVE BILL WISE OFFER DETAIL

				int noOfTimes = 0;
				try {
					noOfTimes = Integer.parseInt(request.getParameter("noOfTimes"));
				} catch (Exception e) {
				}

				OfferDetail detail = new OfferDetail(billWiseOfferDetailId, offerId, subType, 0, 1, disc, limit, coupon,
						0, 0, 1, 1, noOfTimes, 0, 0, 0, "", "", "", "", minDisc, 0, 0, 0);
				detailList.add(detail);

			} /*
				 * else if (type == 2 && subType == 1) {// SAVE ITEM WISE DISCOUNT OFFER DETAIL
				 * 
				 * if (itemListByOffer != null) {
				 * 
				 * if (itemListByOffer.getItemList() != null) {
				 * 
				 * for (int i = 0; i < itemListByOffer.getItemList().size(); i++) {
				 * 
				 * int pkItemId = itemListByOffer.getItemList().get(i).getItemId(); int
				 * itemCatId = itemListByOffer.getItemList().get(i).getCatId(); int
				 * offerDetailId = itemListByOffer.getItemList().get(i).getOfferDetailId();
				 * 
				 * float itemDiscPer = 0;
				 * 
				 * if (request.getParameter("itemCheck" + pkItemId) != null) {
				 * 
				 * if (request.getParameter("itemDisc#" + pkItemId + "#" + itemCatId) != null) {
				 * itemDiscPer = Float .parseFloat(request.getParameter("itemDisc#" + pkItemId +
				 * "#" + itemCatId)); }
				 * 
				 * OfferDetail detail = new OfferDetail(offerDetailId, offerId, subType,
				 * pkItemId, 1, itemDiscPer, 0, "", 0, 0, 0, 0, 0, 0, 0, 0, "", "", "", "", 0,
				 * 0, 0, 0); detailList.add(detail);
				 * 
				 * } else {
				 * 
				 * if (itemListByOffer.getItemList().get(i).getChecked() == 1) {
				 * deleteUncheckItemDiscIds = deleteUncheckItemDiscIds + "," + offerDetailId; }
				 * 
				 * }
				 * 
				 * }
				 * 
				 * }
				 * 
				 * }
				 * 
				 * } else if (type == 2 && subType == 2) {// SAVE ITEM WISE BUY GET FREE OFFER
				 * DETAIL
				 * 
				 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				 * map.add("offerId", offerId);
				 * 
				 * ItemBuyGetFreeOffer[] arr = Constants.getRestTemplate()
				 * .postForObject(Constants.url + "getBuyGetFreeOfferList", map,
				 * ItemBuyGetFreeOffer[].class); List<ItemBuyGetFreeOffer> offerList = new
				 * ArrayList<ItemBuyGetFreeOffer>(Arrays.asList(arr));
				 * System.err.println("PREV OFFER DETAIL LIST = " + offerList);
				 * 
				 * if (buyGetFreeList != null) {
				 * 
				 * if (buyGetFreeList.getItemList() != null) {
				 * 
				 * for (ItemBuyGetFreeOffer item : buyGetFreeList.getItemList()) {
				 * 
				 * float pQty = 0; if (request.getParameter("selectPQty" +
				 * item.getPrimaryItemId()) != null) { pQty =
				 * Float.parseFloat(request.getParameter("selectPQty" +
				 * item.getPrimaryItemId())); }
				 * 
				 * float sQty = 0; if (request.getParameter( "qty#" + item.getPrimaryItemId() +
				 * "#" + item.getSecondaryItemId()) != null) { sQty =
				 * Float.parseFloat(request.getParameter( "qty#" + item.getPrimaryItemId() + "#"
				 * + item.getSecondaryItemId())); }
				 * 
				 * OfferDetail detail = new OfferDetail(item.getOfferDetailId(), offerId,
				 * subType, item.getPrimaryItemId(), pQty, 0, 0, "", item.getSecondaryItemId(),
				 * sQty, 0, 0, 0, 0, 0, 0, "", "", "", "", 0, 0, 0, 0); detailList.add(detail);
				 * 
				 * }
				 * 
				 * if (offerList != null) {
				 * 
				 * for (int i = 0; i < offerList.size(); i++) {
				 * 
				 * int flag = 0;
				 * 
				 * for (int j = 0; j < buyGetFreeList.getItemList().size(); j++) { if
				 * (offerList.get(i).getOfferDetailId() == buyGetFreeList.getItemList().get(j)
				 * .getOfferDetailId()) { flag = 1; break; } }
				 * 
				 * if (flag == 0) { deleteBuyGetFreeIds = deleteBuyGetFreeIds + "," +
				 * offerList.get(i).getOfferDetailId(); } }
				 * 
				 * }
				 * 
				 * System.err.println("DELETE IDS ============ " + deleteBuyGetFreeIds);
				 * 
				 * }
				 * 
				 * }
				 * 
				 * }
				 */

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "saveOfferDetailList", detailList,
					Info.class);

			if (info != null) {
				if (!info.isError()) {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("offerId", offerId);
					map.add("type", type);

					Info info1 = Constants.getRestTemplate().postForObject(Constants.url + "updateOfferType", map,
							Info.class);
				}
			}

			if (!deleteUncheckItemDiscIds.isEmpty()) {
				deleteUncheckItemDiscIds = deleteUncheckItemDiscIds.substring(1, deleteUncheckItemDiscIds.length());

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerDetailIds", deleteUncheckItemDiscIds);

				Info infoUpdate = Constants.getRestTemplate().postForObject(Constants.url + "removeOfferDetailIds", map,
						Info.class);
			}

			if (!deleteBuyGetFreeIds.isEmpty()) {
				deleteBuyGetFreeIds = deleteBuyGetFreeIds.substring(1, deleteBuyGetFreeIds.length());

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerDetailIds", deleteBuyGetFreeIds);

				Info infoUpdate = Constants.getRestTemplate().postForObject(Constants.url + "removeOfferDetailIds", map,
						Info.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addNewOffers/" + offerId;
	}

//	// -----------------AJAX-ITEM LIST FOR OFFER DETAIL-------------------
//	GetItemListForOfferDetail itemListByOffer = new GetItemListForOfferDetail();
//
//	@RequestMapping(value = "/getItemListForOfferDetail", method = RequestMethod.GET)
//	public @ResponseBody GetItemListForOfferDetail getItemListForOfferDetail(HttpServletRequest request,
//			HttpServletResponse response) {
//
//		int offerId = Integer.parseInt(request.getParameter("offerId"));
//
//		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//		map.add("offerId", offerId);
//
//		ItemListForOfferDetail[] arr = Constants.getRestTemplate()
//				.postForObject(Constant.url + "getItemListForOfferDetail", map, ItemListForOfferDetail[].class);
//		List<ItemListForOfferDetail> itemList = new ArrayList<ItemListForOfferDetail>(Arrays.asList(arr));
//
//		MCategory[] catArr = Constant.getRestTemplate().getForObject(Constant.url + "showAllCategory",
//				MCategory[].class);
//		List<MCategory> mCategoryList = new ArrayList<MCategory>(Arrays.asList(catArr));
//
//		itemListByOffer = new GetItemListForOfferDetail();
//		itemListByOffer.setCatList(mCategoryList);
//		itemListByOffer.setItemList(itemList);
//
//		return itemListByOffer;
//	}

	// -----------------AJAX-SAVE OFFER DETAIL-------------------
	
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 18-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- saveOfferDetailAjax
	@RequestMapping(value = "/saveOfferDetailAjax", method = RequestMethod.GET)
	public @ResponseBody Info saveOfferDetailAjax(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		int offerId = Integer.parseInt(request.getParameter("offerId"));
		int type = Integer.parseInt(request.getParameter("type"));
		String coupon = request.getParameter("coupon");
		float disc = Float.parseFloat(request.getParameter("disc"));
		float limit = Float.parseFloat(request.getParameter("limit"));

		List<OfferDetail> detailList = new ArrayList<>();
		OfferDetail detail = new OfferDetail(0, offerId, 1, 0, 1, disc, limit, coupon, 0, 0, 1, 1, 0, 0, 0, 0, "", "",
				"", "", 0, 0, 0, 0);
		detailList.add(detail);

		info = Constants.getRestTemplate().postForObject(Constants.url + "saveOfferDetailList", detailList, Info.class);

		if (info != null) {
			if (!info.isError()) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerId", offerId);
				map.add("type", type);

				Info info1 = Constants.getRestTemplate().postForObject(Constants.url + "updateOfferType", map,
						Info.class);
			}
		}

		return info;
	}

	// IMAGE UPLOAD-------------------
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- showOfferList

	@RequestMapping(value = "/showOfferList", method = RequestMethod.GET)
	public ModelAndView showOfferList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("franchisee/offersList");

		HttpSession session = request.getSession();
		int compId = (int) session.getAttribute("companyId");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("compId", compId);

		OfferHeader[] arr = Constants.getRestTemplate().postForObject(Constants.url + "getAllOfferHeaderListByCompId",
				map, OfferHeader[].class);
		List<OfferHeader> offerList = new ArrayList<OfferHeader>(Arrays.asList(arr));

		model.addObject("offerList", offerList);

		return model;
	}

	// ------DELETE OFFER HEADER------------
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- deleteOfferHeaderById
	@RequestMapping(value = "/deleteOfferHeaderById", method = RequestMethod.GET)
	public String deleteOfferHeaderById(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			int offerId = Integer.parseInt(request.getParameter("offerId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("offerId", offerId);
			map.add("status", 1);

			Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteOfferHeaderById", map,
					Info.class);

			if (!res.isError()) {
				session.setAttribute("successMsg", res.getMsg());
			} else {
				session.setAttribute("errorMsg", res.getMsg());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showOfferList";
	}

	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- showOfferConfiguration
	/****************************** Offer Config *********************************/
	@RequestMapping(value = "/showOfferConfiguration", method = RequestMethod.GET)
	public ModelAndView showOfferConfiguration(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		try {
			model = new ModelAndView("franchisee/offerConfiguration");
			OfferConfig offer = new OfferConfig();
			model.addObject("offer", offer);

			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			OfferHeader[] offerArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllOfferHeads", map,
					OfferHeader[].class);
			List<OfferHeader> offerList = new ArrayList<OfferHeader>(Arrays.asList(offerArr));


			model.addObject("offerList", offerList);

			model.addObject("title", "Franchise Offer Configuration");

		} catch (Exception e) {
			System.out.println("Execption in /showOfferConfiguration : " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}

	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- getConfigFrList
	List<GetConfigureOfferList> frList = new ArrayList<GetConfigureOfferList>();
	
	

	@RequestMapping(value = "/getConfigFrList", method = RequestMethod.GET)
	@ResponseBody
	public List<GetConfigureOfferList> getConfigFrList(HttpServletRequest request, HttpServletResponse response) {

		frList = new ArrayList<GetConfigureOfferList>();
		try {

			int offerId = Integer.parseInt(request.getParameter("offerId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("offerId", offerId);

			GetConfigureOfferList[] configArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getConfigureOfferList", map, GetConfigureOfferList[].class);
			frList = new ArrayList<GetConfigureOfferList>(Arrays.asList(configArr));

		} catch (Exception e) {
			System.out.println("Execption in /getConfigFrList : " + e.getMessage());
			e.printStackTrace();
		}
		return frList;

	}

	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- saveOfferConfiguration
	@RequestMapping(value = "/saveOfferConfiguration", method = RequestMethod.POST)
	public String saveOfferConfiguration(HttpServletRequest request, HttpServletResponse response) {
		try {

			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String frIdStr = "";

			int offersId = Integer.parseInt(request.getParameter("offers"));

			String[] frIds = request.getParameterValues("frIds");
			if (frIds.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : frIds) {
					sb.append(s).append(",");
				}
				frIdStr = sb.deleteCharAt(sb.length() - 1).toString();
				// System.out.println("frIdStr---"+offersId+" **** "+frIdStr);

			}
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("offerId", offersId);

			OfferConfig resOffer = Constants.getRestTemplate().postForObject(Constants.url + "getOfferInfoByOfferId",
					map, OfferConfig.class);
			System.out.println("resOffer----" + resOffer);

			if (resOffer == null) {

				int offerConfigId = 0;
				try {
					offerConfigId = Integer.parseInt(request.getParameter("offerConfigId"));
				} catch (Exception e) {
					e.printStackTrace();
					offerConfigId = 0;
				}
				OfferConfig offer = new OfferConfig();

				offer.setDelStatus(1);
				offer.setExInt1(0);
				offer.setExInt2(0);
				offer.setExVar1("NA");
				offer.setExVar2("NA");
				offer.setFrId(frIdStr);
				offer.setIsActive(1);
				offer.setMakerDatetime(sf.format(date));
				offer.setMakerUserId(userObj.getUserId());
				offer.setOfferConfigId(offerConfigId);
				offer.setOfferId(offersId);
				offer.setUpdatedDateTime(sf.format(date));

				// System.out.println("Configure Offer---------------"+offer);

				OfferConfig offerRes = Constants.getRestTemplate()
						.postForObject(Constants.url + "addFrOfferConfiguration", offer, OfferConfig.class);

				if (offerRes != null)
					session.setAttribute("successMsg", "Offer Successfully Configure With Franchise");
				else
					session.setAttribute("errorMsg", "Failed to Configure Offer With Franchise");
			} else {
				map = new LinkedMultiValueMap<>();
				map.add("frIdStr", frIdStr);
				map.add("offerId", offersId);
				map.add("updtTime", sf.format(date));
				map.add("userId", userObj.getUserId());

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "updateFrOfferConfig", map,
						Info.class);

				if (!res.isError())
					session.setAttribute("successMsg", res.getMsg());
				else
					session.setAttribute("errorMsg", res.getMsg());
			}

		} catch (Exception e) {
			System.out.println("Execption in /saveOfferConfiguration : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showOfferConfigurationList";
	}

	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- showOfferConfiguration
	@RequestMapping(value = "/showOfferConfigurationList", method = RequestMethod.GET)
	public ModelAndView showOfferConfigurationList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		List<GetOfferFrConfiguredList> list = new ArrayList<GetOfferFrConfiguredList>();
		try {
			model = new ModelAndView("franchisee/gerFrOfferConfigList");

			GetOfferFrConfiguredList[] areaArr = Constants.getRestTemplate()
					.getForObject(Constants.url + "getAllOfferFrConfiguredList", GetOfferFrConfiguredList[].class);
			list = new ArrayList<GetOfferFrConfiguredList>(Arrays.asList(areaArr));

			for (int i = 0; i < list.size(); i++) {

				list.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(list.get(i).getOfferId())));

				list.get(i).setExVar2(FormValidation.Encrypt(String.valueOf(list.get(i).getOfferConfigId())));
			}
			model.addObject("frOfferConfigList", list);

			model.addObject("title", "Franchise Offer Configuration List");

		} catch (Exception e) {
			System.out.println("Execption in /showOfferConfigurationList : " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- editFrOfferConfig

	@RequestMapping(value = "/editFrOfferConfig", method = RequestMethod.GET)
	public ModelAndView editFrOfferConfig(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		try {
			model = new ModelAndView("franchisee/offerConfiguration");

			String base64encodedString = request.getParameter("editOfferId");
			String editOffersId = FormValidation.DecodeKey(base64encodedString);

			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");
			int compId = (int) session.getAttribute("companyId");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			OfferHeader[] offerArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllOfferHeads", map,
					OfferHeader[].class);
			List<OfferHeader> offerList = new ArrayList<OfferHeader>(Arrays.asList(offerArr));
			model.addObject("offerList", offerList);

			model.addObject("editOfferId", editOffersId);

			model.addObject("title", "Franchise Offer Configuration");

		} catch (Exception e) {
			System.out.println("Execption in /showOfferConfiguration : " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}

	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- deleteFrOfferConfig
	@RequestMapping(value = "/deleteFrOfferConfig", method = RequestMethod.GET)
	public String deleteFrOfferConfig(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		try {

			String base64encodedString = request.getParameter("frOfferConfigId");
			String frOfferConfigId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("frOfferConfigId", Integer.parseInt(frOfferConfigId));

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteFrOfferConfigById", map,
					Info.class);

			if (!info.isError()) {
				session.setAttribute("successMsg", info.getMsg());
			} else {
				session.setAttribute("errorMsg", info.getMsg());
			}

		} catch (Exception e) {
			System.out.println("Execption in /deleteFrOfferConfig : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showOfferConfigurationList";
	}

	/*
	 * @RequestMapping(value = "/getItemImagesByDocIdAndDocType", method =
	 * RequestMethod.GET) public @ResponseBody List<Images>
	 * getItemImagesByDocIdAndDocType(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * int type = Integer.parseInt(request.getParameter("type"));
	 * System.err.println("TYPE - " + type); int selectId =
	 * Integer.parseInt(request.getParameter("selectId"));
	 * System.err.println("selectId - " + selectId);
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	 * map.add("docId", selectId); map.add("docType", type);
	 * 
	 * imageList = new ArrayList<>();
	 * 
	 * Images[] imgArr = Constant.getRestTemplate().postForObject(Constant.url +
	 * "getImagesByDocIdAndDocType", map, Images[].class); imageList = new
	 * ArrayList<Images>(Arrays.asList(imgArr));
	 * 
	 * return imageList; }
	 */

	/************************* Image save ******************************/
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :-21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- getItemImagesByDocIdAndDocType

	List<String> list = new ArrayList<>();

	@RequestMapping(value = "/getItemImagesByDocIdAndDocType", method = RequestMethod.GET)
	public @ResponseBody List<String> getItemImagesByDocIdAndDocType(HttpServletRequest request,
			HttpServletResponse response) {

		int selectId = Integer.parseInt(request.getParameter("selectId"));

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("selectId", selectId);

		String[] imgArr = Constants.getRestTemplate().postForObject(Constants.url + "getImagesByDocIdAndDocType", map,
				String[].class);
		list = new ArrayList<String>(Arrays.asList(imgArr));

		return list;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- deleteOfferImageAjax
	@RequestMapping(value = "/deleteOfferImageAjax", method = RequestMethod.GET)
	public @ResponseBody Info deleteOfferImageAjax(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		int offerId = Integer.parseInt(request.getParameter("offerId"));
		String imageName = request.getParameter("imageName");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("offerId", offerId);
		map.add("imageName", imageName);

		info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteByImageOfOffer", map, Info.class);

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
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- ajaxImageUploadOffer

	@ResponseBody
	@RequestMapping(value = "/ajaxImageUploadOffer/{offerId}", method = RequestMethod.POST)
	public String ajaxImageUploadOffer(@PathVariable int offerId, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("files") List<MultipartFile> files) {


		try {
		 

			Info info = new Info();

			String filesList = new String();

			if (offerId > 0) {
				List<Images> imageList = new ArrayList<>();
				
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerId", offerId);

				OfferHeader res = Constants.getRestTemplate().postForObject(Constants.url + "getOfferHeaderById", map,
						OfferHeader.class);
				
				 

			

				if (files.size() > 0) {

					for (int i = 0; i < files.size(); i++) {
						Random random = new Random();
						int randomInt = random.nextInt(100);

						String ext = files.get(i).getOriginalFilename().split("\\.")[1];
						 String fileName = CommonUtility.getCurrentTimeStamp()+ "_" + randomInt + "." + ext;
						 new ImageUploadController().saveUploadedFiles(files.get(i), 1, fileName);

						//String fileName = sf.format(date) + "_" + files.get(i).getOriginalFilename() + "_" + randomInt;

						info = new ImageUploadController().saveUploadedImgeWithResize(files.get(i), fileName, 450, 250);

						if (filesList.isEmpty()) {

							filesList = fileName;
						} else {

							filesList = filesList.concat("," + fileName);

						}

					}
					
					
					if(res.getOfferImages().length()>0) {
						

						filesList=res.getOfferImages().concat(","+filesList);
						
					}
					
				
					if (info != null) {
						if (!info.isError()) {
						 map = new LinkedMultiValueMap<>();
							map.add("filesList", filesList);
							map.add("offerId", offerId);
							Constants.getRestTemplate().postForObject(Constants.url + "updateOfferImg", map,
									Info.class);
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

	 
}
