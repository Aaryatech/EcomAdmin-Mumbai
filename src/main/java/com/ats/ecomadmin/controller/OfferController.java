package com.ats.ecomadmin.controller;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
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
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.offer.Images;
import com.ats.ecomadmin.model.offer.OfferDetail;
import com.ats.ecomadmin.model.offer.OfferHeader;
 

@Controller
@SessionScope
public class OfferController {

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

				System.err.println("RES = " + res);

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
							System.err.println("OFFER DETAIL LIST  = " + offerDetailList);

							if (offerDetailList != null) {
								itemSubTypeOffer = offerDetailList.get(0).getOfferSubType();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				model.addObject("itemSubTypeOffer", itemSubTypeOffer);

			}

			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");
			System.err.println("COMP ID = " + compId);

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

	@RequestMapping(value = "/saveOfferHeader", method = RequestMethod.POST)
	public String saveOfferHeader(HttpServletRequest request, HttpServletResponse response) {

		int offerId = 0;

		try {
			HttpSession session = request.getSession();
			System.err.println("saveOfferHeader--- ");

			offerId = Integer.parseInt(request.getParameter("offerId"));
			String title = request.getParameter("offerTitle");
			String desc = request.getParameter("offerDesc");
			int type = Integer.parseInt(request.getParameter("selectType"));
			String offerDate = request.getParameter("offerDate");
			String fromTime = request.getParameter("fromTime");
			String toTime = request.getParameter("toTime");
			int freqType = Integer.parseInt(request.getParameter("freq_type"));

			int userId = (int) session.getAttribute("userId");
			int compId = (int) session.getAttribute("companyId");
System.err.println(fromTime+"***"+toTime);
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

			System.err.println("DATE = " + offerDate);
			System.err.println("DAYS = " + daysList);
			System.err.println("APPLICABLE = " + applicableList);

			OfferHeader offer = new OfferHeader(offerId, title, desc, type, applicableList, 0, freqType, daysList,
					fromDate, toDate, fromTime, toTime, userId, CommonUtility.getCurrentYMDDateTime(),
					CommonUtility.getCurrentYMDDateTime(), compId, 0, 0, 0, 0, 0, 0, "", "", "", "", 0, 0, 0, 0);

			System.err.println("OFFER = " + offer);

			OfferHeader res = Constants.getRestTemplate().postForObject(Constants.url + "saveOfferHeader", offer,
					OfferHeader.class);

			System.err.println("OFFER SAVED = " + res);

			if (res != null) {
				offerId = res.getOfferId();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addNewOffers/" + offerId;
	}

	
	List<Images> imageList = new ArrayList<>();
	@RequestMapping(value = "/getImagesByDocIdAndDocType", method = RequestMethod.GET)
	public @ResponseBody List<Images> getImagesByDocId(HttpServletRequest request, HttpServletResponse response) {

		int type = Integer.parseInt(request.getParameter("type"));
		System.err.println("TYPE - " + type);
		int selectId = Integer.parseInt(request.getParameter("selectId"));
		System.err.println("selectId - " + selectId);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("docId", selectId);
		map.add("docType", type);

		imageList = new ArrayList<>();

		Images[] imgArr = Constants.getRestTemplate().postForObject(Constants.url + "getImagesByDocIdAndDocType", map,
				Images[].class);
		imageList = new ArrayList<Images>(Arrays.asList(imgArr));

		return imageList;
	}

	// -----------------------SAVE OFFER DETAIL----------------------------
	@RequestMapping(value = "/saveOfferDetail", method = RequestMethod.POST)
	public String saveOfferDetail(HttpServletRequest request, HttpServletResponse response) {

		int offerId = 0;

		try {
			HttpSession session = request.getSession();
			System.err.println("saveOfferDetail--- ");

			offerId = Integer.parseInt(request.getParameter("tab2OfferId"));

			int billWiseOfferDetailId = 0;
			try {
				billWiseOfferDetailId = Integer.parseInt(request.getParameter("billWiseDetailId"));
			} catch (Exception e) {
			}

			int type = Integer.parseInt(request.getParameter("offerTypeWise"));
			String coupon = request.getParameter("billWiseCoupon");

			float disc = 0, limit = 0;
			try {
				disc = Float.parseFloat(request.getParameter("billWiseDisc"));
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
						0, 0, 0, 0, noOfTimes, 0, 0, 0, "", "", "", "", 0, 0, 0, 0);
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
			System.err.println("RESULT = " + info);

			if (info != null) {
				if (!info.isError()) {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("offerId", offerId);
					map.add("type", type);

					Info info1 = Constants.getRestTemplate().postForObject(Constants.url + "updateOfferType", map,
							Info.class);
					System.err.println("UPDATE TYPE RESULT = " + info1);
				}
			}

			if (!deleteUncheckItemDiscIds.isEmpty()) {
				deleteUncheckItemDiscIds = deleteUncheckItemDiscIds.substring(1, deleteUncheckItemDiscIds.length());

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerDetailIds", deleteUncheckItemDiscIds);

				Info infoUpdate = Constants.getRestTemplate().postForObject(Constants.url + "removeOfferDetailIds", map,
						Info.class);
				System.err.println("DELETE RES = " + infoUpdate);
				System.err.println("DELETE IDS =" + deleteUncheckItemDiscIds);
			}

			if (!deleteBuyGetFreeIds.isEmpty()) {
				deleteBuyGetFreeIds = deleteBuyGetFreeIds.substring(1, deleteBuyGetFreeIds.length());

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerDetailIds", deleteBuyGetFreeIds);

				Info infoUpdate = Constants.getRestTemplate().postForObject(Constants.url + "removeOfferDetailIds", map,
						Info.class);
				System.err.println("DELETE RES = " + infoUpdate);
				System.err.println("DELETE IDS =" + deleteBuyGetFreeIds);
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
	@RequestMapping(value = "/saveOfferDetailAjax", method = RequestMethod.GET)
	public @ResponseBody Info saveOfferDetailAjax(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		int offerId = Integer.parseInt(request.getParameter("offerId"));
		int type = Integer.parseInt(request.getParameter("type"));
		String coupon = request.getParameter("coupon");
		float disc = Float.parseFloat(request.getParameter("disc"));
		float limit = Float.parseFloat(request.getParameter("limit"));

		List<OfferDetail> detailList = new ArrayList<>();
		OfferDetail detail = new OfferDetail(0, offerId, 1, 0, 1, disc, limit, coupon, 0, 0, 0, 0, 0, 0, 0, 0, "", "",
				"", "", 0, 0, 0, 0);
		detailList.add(detail);

		info = Constants.getRestTemplate().postForObject(Constants.url + "saveOfferDetailList", detailList, Info.class);
		System.err.println("RESULT = " + info);

		if (info != null) {
			if (!info.isError()) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerId", offerId);
				map.add("type", type);

				Info info1 = Constants.getRestTemplate().postForObject(Constants.url + "updateOfferType", map,
						Info.class);
				System.err.println("UPDATE TYPE RESULT = " + info1);
			}
		}

		return info;
	}

	 

	// IMAGE UPLOAD-------------------
	@ResponseBody
	@RequestMapping(value = "/ajaxImageUploadOffer/{offerId}", method = RequestMethod.POST)
	public String ajaxImageUploadOffer(@PathVariable int offerId, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("files") List<MultipartFile> files) {

		System.err.println("ajaxImageUploadOffer--- " + files.size());

		try {

			if (offerId > 0) {
				List<Images> imageList = new ArrayList<>();

				if (files.size() > 0) {

					for (int i = 0; i < files.size(); i++) {

						String ext = files.get(i).getOriginalFilename().split("\\.")[1];
						String fileName = CommonUtility.getCurrentTimeStamp() + "." + ext;
						// new ImageUploadController().saveUploadedFiles(files.get(i), 1, fileName);

						Info info = new ImageUploadController().saveUploadedImgeWithResize(files.get(i), fileName, 450,
								250);
						if (info != null) {
							if (!info.isError()) {

								Images image = new Images(0, 4, offerId, fileName, (i + 1), 0, 0, 0, 0, 0, "", "", "",
										"", 0, 0, 0);
								Constants.getRestTemplate().postForObject(Constants.url + "saveImage", image, Info.class);

							}
						}

						// Images images = new Images(0, 4, offerId, fileName, (i + 1), 0, 0, 0, 0, 0,
						// "", "", "", "", 0,
						// 0, 0);
						// imageList.add(images);
					}

					// Info info = Constants.getRestTemplate().postForObject(Constants.url +
					// "saveMultipleImage", imageList,
					// Info.class);

				}
			} else {
				return "false";
			}

		} catch (Exception e) {
			return "false";
		}
		return "true";

	}

	@RequestMapping(value = "/showOfferList", method = RequestMethod.GET)
	public ModelAndView showOfferList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("franchisee/offersList");

		HttpSession session = request.getSession();
		int compId = (int) session.getAttribute("companyId");
		System.err.println("COMP ID = " + compId);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("compId", compId);

		OfferHeader[] arr = Constants.getRestTemplate().postForObject(Constants.url + "getAllOfferHeaderListByCompId",
				map, OfferHeader[].class);
		List<OfferHeader> offerList = new ArrayList<OfferHeader>(Arrays.asList(arr));
		System.err.println("offerList = " + offerList);

		model.addObject("offerList", offerList);

		return model;
	}

	// ------DELETE OFFER HEADER------------
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

	 
}