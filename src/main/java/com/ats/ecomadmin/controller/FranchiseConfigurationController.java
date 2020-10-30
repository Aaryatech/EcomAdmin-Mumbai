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
import com.ats.ecomadmin.model.City;
import com.ats.ecomadmin.model.FilterTypes;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.GetFrConfigList;
import com.ats.ecomadmin.model.GetFrForConfig;
import com.ats.ecomadmin.model.GetFranchiesAndFrIds;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.ItemConfHeader;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.offer.DeliveryBoy;
import com.ats.ecomadmin.model.offer.FrDelvrBoyConfig;
import com.ats.ecomadmin.model.offer.OfferDetail;

@Controller
@SessionScope
public class FranchiseConfigurationController {

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 24-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- get all fr configs

	@RequestMapping(value = "/configFranchise", method = RequestMethod.GET)
	public String configFranchise(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		model.addAttribute("title", "Configure Franchise");
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

					if (frList.isEmpty()) {
						session.setAttribute("errorMsg", "No Record Found.");
					} else {
						session.removeAttribute("errorMsg");
					}
				}
				model.addAttribute("frList", frList);

			} catch (Exception e) {
				System.out.println("Execption in /configFranchise : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 24-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- getConfigByCatId

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

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 24-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- saveFrConfiguration
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

		int btnVal = Integer.parseInt(request.getParameter("btnType"));

		if (btnVal == 0)
			return "redirect:/configFranchiseList";
		else
			return "redirect:/configFranchise";

	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 24-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- configFranchiseList

	@RequestMapping(value = "/configFranchiseList", method = RequestMethod.GET)
	public String configFranchiseList(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		model.addAttribute("title", "Configure Franchise List");
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

				Info delete = AccessControll.checkAccess("configFranchiseList", "configFranchiseList", "0", "0", "0",
						"1", newModuleList);

				if (delete.isError() == false) {
					model.addAttribute("deleteAccess", 0);
				}

			} catch (Exception e) {
				System.out.println("Execption in /frConfigList : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 25-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- deleteFrConfiguration multiple

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

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 25-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- deleteFrConfiguration single

	@RequestMapping(value = "/deleteFrConfig", method = RequestMethod.GET)
	public String deleteOfferHeaderById(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("deleteFrConfig", "configFranchiseList", "1", "0", "0", "0",
				newModuleList);
		String mav = null;
		if (view.isError() == true) {

			mav = "redirect:/accessDenied";

		} else {
			try {

				mav = "redirect:/configFranchiseList";
				int configId = Integer.parseInt(request.getParameter("configId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("configIds", configId);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteFrConfig", map, Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}

	/*--------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 20-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Delivery Boy List
	@RequestMapping(value = "/showDeliveryList", method = RequestMethod.GET)
	public String showUomList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		List<DeliveryBoy> delBoyList = new ArrayList<DeliveryBoy>();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showDeliveryList", "showDeliveryList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/delvrBoyList";

				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				DeliveryBoy[] uomArr = Constants.getRestTemplate().postForObject(Constants.url + "getDeliveryBoysList",
						map, DeliveryBoy[].class);
				delBoyList = new ArrayList<DeliveryBoy>(Arrays.asList(uomArr));

				for (int i = 0; i < delBoyList.size(); i++) {

					delBoyList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(delBoyList.get(i).getDelBoyId())));
				}
				model.addAttribute("delBoyList", delBoyList);

				model.addAttribute("title", "Delivery Boy List");

				Info add = AccessControll.checkAccess("showDeliveryList", "showDeliveryList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showDeliveryList", "showDeliveryList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showDeliveryList", "showDeliveryList", "0", "0", "0", "1",
						newModuleList);

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
			System.out.println("Execption in /showDeliveryList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 20-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add UOM JSP Page
	@RequestMapping(value = "/newDeliveryBoy", method = RequestMethod.GET)
	public String newUom(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newDeliveryBoy", "showDeliveryList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addDeliveryBoy";

				DeliveryBoy delvrBoy = new DeliveryBoy();

				model.addAttribute("delvrBoy", delvrBoy);
				model.addAttribute("title", "Add DeliveryBoy");
			}
		} catch (Exception e) {
			System.out.println("Execption in /newDeliveryBoy : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 20-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert Delivery Boy in database
	@RequestMapping(value = "/insertDeliveryBoy", method = RequestMethod.POST)
	public String insertDeliveryBoy(HttpServletRequest request, HttpServletResponse response) {

		try {
			DeliveryBoy delBoy = new DeliveryBoy();

			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");

			int delBoyId = Integer.parseInt(request.getParameter("delBoyId"));
			String empCode = new String();
			try {
				empCode = request.getParameter("empCode");
			} catch (Exception e) {
				empCode = null;
			}

			if (empCode.isEmpty() || empCode == null) {
				delBoy.setEmpCode("NA");
			} else {
				delBoy.setEmpCode(empCode);
			}

			delBoy.setDelBoyId(delBoyId);
			delBoy.setAddress(request.getParameter("address"));
			delBoy.setCompId(compId);
			delBoy.setDateOfBirth(request.getParameter("dob"));
			delBoy.setDelStatus(1);
			delBoy.setEmailId("NA");
			delBoy.setExInt1(0);
			delBoy.setExInt2(0);
			delBoy.setExVar1("NA");
			delBoy.setExVar2("NA");
			delBoy.setFirstName(request.getParameter("firstName"));
			delBoy.setJoiningDate(request.getParameter("joiningDate"));
			delBoy.setLastName(request.getParameter("lastName"));
			delBoy.setMobileNo(request.getParameter("mobNo"));
			delBoy.setVehicleNo("NA");
			delBoy.setIsActive(Integer.parseInt(request.getParameter("activeStat")));

			DeliveryBoy res = Constants.getRestTemplate().postForObject(Constants.url + "saveDeliveryBoy", delBoy,
					DeliveryBoy.class);

			if (res.getDelBoyId() > 0) {
				if (delBoyId == 0)
					session.setAttribute("successMsg", "Delivery Boy Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Delivery Boy Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Delivery Boy");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertDeliveryBoy : " + e.getMessage());
			e.printStackTrace();
		}

		int btnVal = Integer.parseInt(request.getParameter("btnType"));
		if (btnVal == 0)
			return "redirect:/showDeliveryList";
		else
			return "redirect:/newDeliveryBoy";

	}

	// Created By :- Mahendra Singh
	// Created On :- 20-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Update Delivery Boy
	@RequestMapping(value = "/editDeliveryBoy", method = RequestMethod.GET)
	public String editDeliveryBoy(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editDeliveryBoy", "showDeliveryList", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addDeliveryBoy";

				String base64encodedString = request.getParameter("delBoyId");
				String delBoyId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("delBoyId", delBoyId);

				DeliveryBoy delvrBoy = Constants.getRestTemplate().postForObject(Constants.url + "getDeliveryBoyById",
						map, DeliveryBoy.class);
				model.addAttribute("delvrBoy", delvrBoy);
				model.addAttribute("title", "Edit Delivery Boy");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editDeliveryBoy : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 20-20-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Delivery Boy
	@RequestMapping(value = "/deleteDeliveryBoy", method = RequestMethod.GET)
	public String deleteUom(HttpServletRequest request, HttpServletResponse response) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteDeliveryBoy", "showDeliveryList", "0", "0", "0", "1",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "redirect:/showDeliveryList";

				String base64encodedString = request.getParameter("delBoyId");
				String delBoyId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map.add("delBoyId", Integer.parseInt(delBoyId));
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteDeliveryBoyById", map,
						Info.class);

				if (!info.isError()) {
					session.setAttribute("successMsg", info.getMsg());
				} else {
					session.setAttribute("errorMsg", info.getMsg());
				}

			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteDeliveryBoy : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getDelvrBoyInfoByMobNo", method = RequestMethod.GET)
	@ResponseBody
	public Info getCityInfoByCode(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			String delBoyId = request.getParameter("delBoyId");
			String mobNo = request.getParameter("mobNo");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("delBoyId", delBoyId);
			map.add("mobNo", mobNo);

			DeliveryBoy res = Constants.getRestTemplate().postForObject(Constants.url + "getDeliveryBoyByMobNo", map,
					DeliveryBoy.class);

			if (res != null) {
				info.setError(false);
				info.setMsg("Delivery Boy Found");
			} else {
				info.setError(true);
				info.setMsg("Delivery Boy Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getDelvrBoyInfoByMobNo : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}
	/*---------------------------------------------------------------------------------------*/

	// Created By :- Mahendra Singh
	// Created On :- 29-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Franchise Cpnfigure with Delivery Boy
	@RequestMapping(value = "/frDelveryBoyConfig", method = RequestMethod.GET)
	public String frDelveryBoyConfig(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		model.addAttribute("title", "Configure Franchise And Delivery Boy");
		String mav = new String();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("frDelveryBoyConfig", "frDelveryBoyConfig", "1", "0", "0", "0",
				newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
			try {

				mav = "franchisee/configFrDelvrBoy";

				FrDelvrBoyConfig config = new FrDelvrBoyConfig();

				model.addAttribute("config", config);
				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				DeliveryBoy[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getDeliveryBoysList",
						map, DeliveryBoy[].class);
				List<DeliveryBoy> delvrBoyList = new ArrayList<DeliveryBoy>(Arrays.asList(catArr));
				model.addAttribute("delvrBoyList", delvrBoyList);

			} catch (Exception e) {
				System.out.println("Execption in /configFranchise : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;
	}

	@RequestMapping(value = "/getFranchiseListFrDlvrBoyConfig", method = RequestMethod.GET)
	@ResponseBody
	public GetFranchiesAndFrIds getFranchiseListFrDlvrBoyConfig(HttpServletRequest request,
			HttpServletResponse response) {
		List<Franchise> frList = new ArrayList<Franchise>();

		GetFranchiesAndFrIds frAndIds = new GetFranchiesAndFrIds();
		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");

			int delBoyId = Integer.parseInt(request.getParameter("delBoyId"));
			System.out.println(delBoyId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);
			map.add("delBoyId", delBoyId);
			String frStrIds = Constants.getRestTemplate().postForObject(Constants.url + "getFrDelBoyIds", map,
					String.class);			
			frAndIds.setFrStrIds(frStrIds);

			map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);

			Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
					Franchise[].class);
			frList = new ArrayList<Franchise>(Arrays.asList(frArr));

			frAndIds.setFrList(frList);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return frAndIds;

	}

	// Created By :- Mahendra Singh
	// Created On :- 20-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert Delivery Boy in database
	@RequestMapping(value = "/saveFrDelvrBoyConfig", method = RequestMethod.POST)
	public String saveFrDelvrBoyConfig(HttpServletRequest request, HttpServletResponse response) {

		try {
			FrDelvrBoyConfig config = new FrDelvrBoyConfig();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = dateFormat.format(cal.getTime());

			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");
			int compId = (int) session.getAttribute("companyId");

			int delBoyId = Integer.parseInt(request.getParameter("delBoyId"));

			int delBoyAssignId = Integer.parseInt(request.getParameter("assignId"));

			String frIdsStr = new String();
			String[] frIds = request.getParameterValues("chk");
			if (frIds.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : frIds) {
					sb.append(s).append(",");
				}
				frIdsStr = sb.deleteCharAt(sb.length() - 1).toString();
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map = new LinkedMultiValueMap<String, Object>();
			map.add("delBoyId", delBoyId);
			map.add("compId", compId);
			FrDelvrBoyConfig frConfig = Constants.getRestTemplate().postForObject(Constants.url + "getFrDelvryConfig",
					map, FrDelvrBoyConfig.class);

			if (frConfig != null) {
				String savedFrIds = frConfig.getFrIds();				

				map = new LinkedMultiValueMap<String, Object>();
				map.add("delBoyId", delBoyId);
				map.add("frIdsStr", savedFrIds + "," + frIdsStr);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "editConfigFranchises", map,
						Info.class);

				if (!info.isError()) {
					session.setAttribute("successMsg", "Franchise And Delivery Boy Configuration Update Successfully");
				}

			} else {

				if (delBoyAssignId > 0)
					config.setEditDttime(curDateTime);
				else
					config.setAddDttime(curDateTime);

				config.setCompanyId(compId);
				config.setDelBoyAssignId(delBoyAssignId);
				config.setDelBoyId(delBoyId);
				config.setDelStatus(1);

				config.setExInt1(0);
				config.setExInt2(0);
				config.setExVar1("NA");
				config.setExVar2("NA");
				config.setFrIds(frIdsStr);
				config.setIsActive(Integer.parseInt(request.getParameter("activeStat")));
				config.setMakerUsrId(userObj.getUserId());
				config.setIsAvailable(0);

				FrDelvrBoyConfig newConfig = Constants.getRestTemplate()
						.postForObject(Constants.url + "configFrDeliveryBoy", config, FrDelvrBoyConfig.class);

				if (newConfig.getDelBoyAssignId() > 0) {
					session.setAttribute("successMsg", "Franchise And Delivery Boy Configuration Saved Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Configure Franchise And Delivery Boy");
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		int btnVal = Integer.parseInt(request.getParameter("btnType"));

		if (btnVal == 0)
			return "redirect:/frDelveryBoyConfig";
		else
			return "redirect:/frDelveryBoyConfig";
	}

}
