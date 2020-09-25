package com.ats.ecomadmin.controller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.annotation.SessionScope;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.FilterTypes;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.GetFrConfigList;
import com.ats.ecomadmin.model.GetFrForConfig;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.ItemConfHeader;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.offer.OfferDetail;

@Controller
@SessionScope
public class FranchiseConfigurationController {

	@RequestMapping(value = "/configFranchise", method = RequestMethod.GET)
	public String configFranchise(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		model.addAttribute("title", "Configure Franchisee");
		String mav = new String();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("configFranchise", "configFranchise", "1", "0", "0", "0", newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
			try {

				mav = "franchisee/franchiseConfig";

				int catId = 0;
				int configId = 0;
				try {
					catId = Integer.parseInt(request.getParameter("catId"));
				} catch (Exception e) {
					catId = 0;
				}
				try {
					configId = Integer.parseInt(request.getParameter("configId"));
				} catch (Exception e) {
					configId = 0;
				}

				model.addAttribute("catId", catId);
				model.addAttribute("configId", configId);
				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				List<Category> catList = new ArrayList<>();
				List<GetFrForConfig> frList = new ArrayList<>();

				map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));
				model.addAttribute("catList", catList);

				if (catId > 0) {

					map = new LinkedMultiValueMap<>();
					map.add("catId", catId);
					map.add("companyId", compId);
					GetFrForConfig[] frArr = Constants.getRestTemplate()
							.postForObject(Constants.url + "getFranchiseForConfig", map, GetFrForConfig[].class);
					frList = new ArrayList<GetFrForConfig>(Arrays.asList(frArr));

				}

				model.addAttribute("frList", frList);

			} catch (Exception e) {
				System.out.println("Execption in /configFranchise : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;
	}

	@RequestMapping(value = "/getConfigByCatId", method = RequestMethod.GET)
	public @ResponseBody List<ItemConfHeader> getConfigByCatId(HttpServletRequest request,
			HttpServletResponse response) {

		List<ItemConfHeader> list = new ArrayList<ItemConfHeader>();
		try {
			int catId = Integer.parseInt(request.getParameter("catId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map = new LinkedMultiValueMap<>();
			map.add("catId", catId);
			ItemConfHeader[] catArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getConfigurationByCatId", map, ItemConfHeader[].class);
			list = new ArrayList<ItemConfHeader>(Arrays.asList(catArr));

			// System.err.println("list" + list.toString());
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}

		return list;
	}

	@RequestMapping(value = "/saveFrConfiguration", method = RequestMethod.POST)
	public String submitCopyTable(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String curDateTime = dateFormat.format(cal.getTime());
		User userObj = (User) session.getAttribute("userObj");

		try {
			int cfgId = Integer.parseInt(request.getParameter("cfgId"));
			StringBuilder sb = new StringBuilder();
			String[] frId = request.getParameterValues("frId");
//System.err.println("cfgId"+cfgId);
			int actualRate = Integer.parseInt(request.getParameter("actualRate"));
			int displayRate = Integer.parseInt(request.getParameter("displayRate"));

			for (int i = 0; i < frId.length; i++) {
				sb = sb.append(frId[i] + ",");
			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("actualRate", actualRate);
			map.add("displayRate", displayRate);
			map.add("frIds", items);
			map.add("cfgId", cfgId);
			map.add("curDateTime", curDateTime);
			map.add("userId", userObj.getUserId());

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/insertFrConfig", map, Info.class);
			if (!info.isError()) {
				session.setAttribute("successMsg", info.getMsg());
			} else {
				session.setAttribute("errorMsg", info.getMsg());
			}
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/configFranchise";

	}

	@RequestMapping(value = "/configFranchiseList", method = RequestMethod.GET)
	public String configFranchiseList(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		model.addAttribute("title", "Configure Franchisee List");
		String mav = new String();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("configFranchiseList", "configFranchiseList", "1", "0", "0", "0",
				newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
			try {

				mav = "franchisee/franchiseConfigList";
				StringBuilder sbAct = new StringBuilder();
				String[] frSelList = null;
				String itemsFr = null;

				int orderBy = 0;
				int companyId = (int) session.getAttribute("companyId");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				List<Franchise> frList = new ArrayList<>();
				List<ItemConfHeader> list = new ArrayList<ItemConfHeader>();
				List<GetFrConfigList> frConfigList = new ArrayList<>();

				try {
					orderBy = Integer.parseInt(request.getParameter("orderBy"));
				} catch (Exception e1) {
					orderBy = 0;
				}

				model.addAttribute("orderBy", orderBy);
				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				frList = new ArrayList<Franchise>(Arrays.asList(frArr));

				for (int i = 0; i < frList.size(); i++) {

					frList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(frList.get(i).getFrId())));
				}

				model.addAttribute("frList", frList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				ItemConfHeader[] catArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getConfigurationByCompId", map, ItemConfHeader[].class);
				list = new ArrayList<ItemConfHeader>(Arrays.asList(catArr));

				model.addAttribute("configList", list);

				try {
					sbAct = new StringBuilder();
					frSelList = request.getParameterValues("frId");
					// System.err.println("emp id are " + locId2);
					for (int j = 0; j < frSelList.length; j++) {
						sbAct = sbAct.append(frSelList[j] + ",");
					}
					itemsFr = sbAct.toString();
					itemsFr = itemsFr.substring(0, itemsFr.length() - 1);
				} catch (Exception e) {

					itemsFr = "0";
				}
				List<Integer> frIds = Stream.of(itemsFr.split(",")).map(Integer::parseInt).collect(Collectors.toList());

				model.addAttribute("frIds", frIds);

				String[] conSelList = null;
				String itemsCon = null;

				try {
					sbAct = new StringBuilder();
					conSelList = request.getParameterValues("configId");
					// System.err.println("emp id are " + locId2);
					for (int j = 0; j < conSelList.length; j++) {
						sbAct = sbAct.append(conSelList[j] + ",");
					}
					itemsCon = sbAct.toString();
					itemsCon = itemsCon.substring(0, itemsCon.length() - 1);
				} catch (Exception e) {

					itemsCon = "0";
				}
				List<Integer> configIds = Stream.of(itemsCon.split(",")).map(Integer::parseInt)
						.collect(Collectors.toList());
				model.addAttribute("configIds", configIds);
				
				
				

				if (itemsCon != "0" && itemsFr != "0" && orderBy != 0) {

					map = new LinkedMultiValueMap<>();
					map.add("frIds", itemsFr);
					map.add("configIds", itemsCon);
					map.add("orderBy", orderBy);
					GetFrConfigList[] confArr = Constants.getRestTemplate()
							.postForObject(Constants.url + "getFranchiseConfigList", map, GetFrConfigList[].class);
					frConfigList = new ArrayList<GetFrConfigList>(Arrays.asList(confArr));

				}

				model.addAttribute("frConfigList", frConfigList);
			} catch (Exception e) {
				System.out.println("Execption in /frConfigList : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;
	}
	
	
	
	@RequestMapping(value = "/deleteFrConfiguration", method = RequestMethod.POST)
	public String deleteFrConfiguration(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String curDateTime = dateFormat.format(cal.getTime());
		User userObj = (User) session.getAttribute("userObj");

		try {
 			StringBuilder sb = new StringBuilder();
			String[] frId = request.getParameterValues("frachaseConfigId");
 		 
			for (int i = 0; i < frId.length; i++) {
				sb = sb.append(frId[i] + ",");
			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
 
			map.add("configIds", items);
 			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteFrConfig", map, Info.class);
			if (!info.isError()) {
				session.setAttribute("successMsg", info.getMsg());
			} else {
				session.setAttribute("errorMsg", info.getMsg());
			}
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/configFranchiseList";

	}


}
