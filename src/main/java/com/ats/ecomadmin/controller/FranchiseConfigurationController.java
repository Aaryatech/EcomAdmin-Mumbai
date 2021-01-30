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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.HomeController;
import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.City;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.CompanyContactInfo;
import com.ats.ecomadmin.model.ExportToExcel;
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
import com.ats.ecomadmin.model.offer.FrCharges;
import com.ats.ecomadmin.model.offer.FrChargesBean;
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
	List<GetFrConfigList> frConfigPrintList = new ArrayList<GetFrConfigList>();
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
				model.addAttribute("compId", companyId);
				
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
				
				frConfigPrintList = frConfigList;
				
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
	
	
	
	List<Long> frConfigIds = new ArrayList<Long>();
	@RequestMapping(value = "/getConfigFranchisePrintIds", method = RequestMethod.GET)
	public @ResponseBody List<GetFrConfigList> getTaxIds(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		try {
			HttpSession session = request.getSession();
			
			int val = Integer.parseInt(request.getParameter("val"));			
			String selctId = request.getParameter("elemntIds");

			selctId = selctId.substring(1, selctId.length() - 1);
			selctId = selctId.replaceAll("\"", "");
		
			
			int compId = (int) session.getAttribute("companyId");

			frConfigIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
			
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();
			
			for (int i = 0; i <frConfigIds.size(); i++) {
				if(frConfigIds.get(i)==2)
					rowData.add("Sr No");
				
				if(frConfigIds.get(i)==3)
				rowData.add("Code");
				
				if(frConfigIds.get(i)==4)
				rowData.add("Franchise");
				
				if(frConfigIds.get(i)==5)
				rowData.add("City");
				
				if(frConfigIds.get(i)==6)
				rowData.add("Route");
				
				if(frConfigIds.get(i)==7)
				rowData.add("Configuration Name");				
			}
			expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int srno = 1;
			for (int i = 0; i < frConfigPrintList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				for (int j = 0; j < frConfigIds.size(); j++) {
					if(frConfigIds.get(j)==2)
						rowData.add(" "+srno);
					
					if(frConfigIds.get(j)==3)
					rowData.add(" " + frConfigPrintList.get(i).getFrCode());
					
					if(frConfigIds.get(j)==4)
					rowData.add(" " + frConfigPrintList.get(i).getFrName());
					
					if(frConfigIds.get(j)==5)
					rowData.add(" " + frConfigPrintList.get(i).getFrCity());
					
					if(frConfigIds.get(j)==6)
					rowData.add(" " + frConfigPrintList.get(i).getRoute());
					
					if(frConfigIds.get(j)==7)
					rowData.add(" " + frConfigPrintList.get(i).getConfigName());
				
				}
				srno = srno + 1;
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "ConfigurationFranchise");
			session.setAttribute("reportNameNew", "Configuration Franchise List");
			session.setAttribute("searchByNew", " NA");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "Configuration Franchise Excel");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return frConfigPrintList;
	}
	
@RequestMapping(value = "pdf/getConfigFrListPdf/{compId}/{selctId}/{frIds}/{configIds}/{orderBy}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getSubCategoryPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable int compId,@PathVariable String selctId, 
			@PathVariable String frIds, @PathVariable String configIds, @PathVariable int orderBy, @PathVariable int showHead) {
		ModelAndView model = new ModelAndView("pdfs/frConfigPrintPdf");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("frIds", frIds);
			map.add("configIds", configIds);
			map.add("orderBy", orderBy);
		
			GetFrConfigList[] confArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getFranchiseConfigList", map, GetFrConfigList[].class);
			List<GetFrConfigList> frConfigList = new ArrayList<GetFrConfigList>(Arrays.asList(confArr));
			
			frConfigIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
				model.addObject("frConfigPrintList", frConfigList);
				model.addObject("frConfigIds", frConfigIds);
				
				CompanyContactInfo dtl = HomeController.getCompName(compId);
				if(showHead==1) {
					model.addObject("compName", dtl.getCompanyName());
					model.addObject("compAddress", dtl.getCompAddress());
					model.addObject("compContact", dtl.getCompContactNo());	
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
		
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
	List<DeliveryBoy> delBoyList = new ArrayList<DeliveryBoy>();
	@RequestMapping(value = "/showDeliveryList", method = RequestMethod.GET)
	public String showUomList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
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
				model.addAttribute("compId", compId);
				
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
				
				
				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

					rowData.add("Sr No.");
					rowData.add("Name");
					rowData.add("Moile No.");
					rowData.add("Joining Date");
					rowData.add("Status");

				expoExcel.setRowData(rowData);
				
				exportToExcelList.add(expoExcel);
				int srno = 1;
				for (int i = 0; i < delBoyList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					rowData.add(" "+srno);					
					rowData.add(" " + delBoyList.get(i).getFirstName()+" "+delBoyList.get(i).getLastName());
					rowData.add(" " + delBoyList.get(i).getMobileNo());
					rowData.add(" " + delBoyList.get(i).getJoiningDate());
					rowData.add(delBoyList.get(i).getIsActive() == 1 ? "Active" : "In-Active");						
					
					srno = srno + 1;
					
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}
				session.setAttribute("exportExcelListNew", exportToExcelList);
				session.setAttribute("excelNameNew", "DeliverBoy");
				session.setAttribute("reportNameNew", "Deliver Boy List");
				session.setAttribute("searchByNew", " NA");
				session.setAttribute("mergeUpto1", "$A$1:$L$1");
				session.setAttribute("mergeUpto2", "$A$2:$L$2");

				session.setAttribute("exportExcelList", exportToExcelList);
				session.setAttribute("excelName", "Deliver Boy Excel");

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
	
	@RequestMapping(value = "pdf/getDeliveBoyPdf/{compId}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getDeliveBoyPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable int compId, @PathVariable int showHead) {
		ModelAndView model = new ModelAndView("pdfs/delvrBoyPdf");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			DeliveryBoy[] uomArr = Constants.getRestTemplate().postForObject(Constants.url + "getDeliveryBoysList",
					map, DeliveryBoy[].class);
			ArrayList<DeliveryBoy> delBoyList = new ArrayList<DeliveryBoy>(Arrays.asList(uomArr));
			
				model.addObject("delBoyList", delBoyList);
				CompanyContactInfo dtl = HomeController.getCompName(compId);
				if(showHead==1) {
					model.addObject("compName", dtl.getCompanyName());
					model.addObject("compAddress", dtl.getCompAddress());
					model.addObject("compContact", dtl.getCompContactNo());	
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
		
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
	public String getFranchiseListFrDlvrBoyConfig(HttpServletRequest request, HttpServletResponse response,
			Model model) {
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

			Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
					Franchise[].class);
			frList = new ArrayList<Franchise>(Arrays.asList(frArr));
			System.out.println("frList------" + frList);
			// frAndIds.setFrList(frList);

			map.add("compId", companyId);
			DeliveryBoy[] delvrBoyArr = Constants.getRestTemplate().postForObject(Constants.url + "getDeliveryBoysList",
					map, DeliveryBoy[].class);
			List<DeliveryBoy> delvrBoyList = new ArrayList<DeliveryBoy>(Arrays.asList(delvrBoyArr));
			System.out.println("delvrBoyList------" + delvrBoyList);

			model.addAttribute("delvrBoyList", delvrBoyList);
			model.addAttribute("frList", frList);
			model.addAttribute("delvrBoyId", delBoyId);

			map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);
			map.add("delBoyId", delBoyId);
			FrDelvrBoyConfig frStrIds = Constants.getRestTemplate().postForObject(Constants.url + "getFrDelvryConfig",
					map, FrDelvrBoyConfig.class);

			// frAndIds.setFrStrIds(frStrIds);
			if (frStrIds != null) {
				model.addAttribute("frStrIds", frStrIds.getFrIds());
				model.addAttribute("assignId", frStrIds.getDelBoyAssignId());
			} else {
				// model.addAttribute("frStrIds", 0);
				model.addAttribute("assignId", 0);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return "franchisee/configFrDelvrBoy";

	}

	// Created By :- Mahendra Singh
	// Created On :- 20-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert Delivery Boy in database
	@RequestMapping(value = "/saveFrDelvrBoyConfig", method = RequestMethod.GET)
	@ResponseBody
	public Info saveFrDelvrBoyConfig(HttpServletRequest request, HttpServletResponse response) {
		Info res = new Info();
		try {
			System.out.println("In Save Maping");

			FrDelvrBoyConfig config = new FrDelvrBoyConfig();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = dateFormat.format(cal.getTime());

			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");
			int compId = (int) session.getAttribute("companyId");

			int delBoyId = Integer.parseInt(request.getParameter("delBoyId"));

			int delBoyAssignId = Integer.parseInt(request.getParameter("assignId"));

			String frIds = request.getParameter("frIds");
			frIds = frIds.substring(1, (frIds.length() - 1));
			frIds = frIds.replace("\"", "");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map = new LinkedMultiValueMap<String, Object>();
			map.add("delBoyId", delBoyId);
			map.add("compId", compId);
			FrDelvrBoyConfig frConfig = Constants.getRestTemplate().postForObject(Constants.url + "getFrDelvryConfig",
					map, FrDelvrBoyConfig.class);

			if (frConfig != null) {

				map = new LinkedMultiValueMap<String, Object>();

				map.add("delBoyId", delBoyId);
				map.add("frIdsStr", frIds);

				Info info = Constants.getRestTemplate().postForObject(Constants.url + "editConfigFranchises", map,
						Info.class);

				if (!info.isError()) {
					res.setError(false);
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
				config.setFrIds(frIds);
				config.setIsActive(1);
				config.setMakerUsrId(userObj.getUserId());
				config.setIsAvailable(0);

				FrDelvrBoyConfig newConfig = Constants.getRestTemplate()
						.postForObject(Constants.url + "configFrDeliveryBoy", config, FrDelvrBoyConfig.class);

				if (newConfig.getDelBoyAssignId() > 0) {
					res.setError(false);
					session.setAttribute("successMsg", "Franchise And Delivery Boy Configuration Saved Successfully");

				} else {
					res.setError(true);
					session.setAttribute("errorMsg", "Failed to Configure Franchise And Delivery Boy");
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}

	// Created By :- Mahendra Singh
	// Created On :- 22-12-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert Franchise Charges
	@RequestMapping(value = "/addFrAdditionalChrgs", method = RequestMethod.POST)
	public String addFrAdditionalChrgs(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			
			int compId = (int) session.getAttribute("companyId");
				
			int chargeId = Integer.parseInt(request.getParameter("charge_id"));
			String dates = request.getParameter("dates");
			String string = dates;
			String[] parts = string.split("to");
			String part1 = parts[0];
			String part2 = parts[1];

			FrCharges charges = new FrCharges();

			charges.setChargeId(chargeId);
			charges.setExtraChg(Float.parseFloat(request.getParameter("extra")));
			charges.setFrId(Integer.parseInt(request.getParameter("fr_id")));
			charges.setFromDate(part1);
			charges.setHandlingChg(Float.parseFloat(request.getParameter("handling")));
			charges.setPackingChg(Float.parseFloat(request.getParameter("packing")));
			charges.setRoundOffAmt(Float.parseFloat(request.getParameter("round_off")));
			charges.setSurchargeFee(Float.parseFloat(request.getParameter("surcharge")));
			charges.setToDate(part2);

			charges.setExFloat1(0);
			charges.setExFloat2(0);
			charges.setExFloat3(0);

			charges.setExInt1(compId);
			charges.setExInt2(0);
			charges.setExInt3(0);

			charges.setExVar1("NA");
			charges.setExVar2("NA");
			charges.setExVar3("NA");

			FrCharges res = Constants.getRestTemplate().postForObject(Constants.url + "saveFrCharges", charges,
					FrCharges.class);

			if (res.getChargeId() > 0) {
				if (chargeId == 0)
					session.setAttribute("successMsg", "Additional Charges Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Additional Charges  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Additional Charges ");
			}

		} catch (Exception e) {
			System.out.println("Execption in /addFrAdditionalChrgs : " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showFranchises";

	}

	@RequestMapping(value = "/getFrDetailById", method = RequestMethod.GET)
	@ResponseBody
	public FrChargesBean getFrDetailById(HttpServletRequest request, HttpServletResponse response) {
		FrChargesBean frCharges = new FrChargesBean();
		try {
			String frId = request.getParameter("frId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("frId", Integer.parseInt(frId));

			Franchise franchise = Constants.getRestTemplate().postForObject(Constants.url + "getFranchiseById", map,
					Franchise.class);
			frCharges.setFranchise(franchise);

			map.add("frId", Integer.parseInt(frId));
			FrCharges charges = Constants.getRestTemplate().postForObject(Constants.url + "getFrChargesByFrId", map,
					FrCharges.class);
			frCharges.setFrCharges(charges);

		} catch (Exception e) {
			System.out.println("Execption in /getFrDetailById : " + e.getMessage());
			e.printStackTrace();
		}

		return frCharges;
	}

	@RequestMapping(value = "/addMultiFranchiseCharges", method = RequestMethod.GET)
	public String addMultiFranchiseCharges(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		model.addAttribute("title", "Add Franchise Charges");
		String mav = new String();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("addMultiFranchiseCharges", "addMultiFranchiseCharges", "1", "0", "0",
				"0", newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
			try {

				mav = "franchisee/addMultiFrChrgs";

				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map.add("compId", compId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchiseByCompIdWithCharges", map,
						Franchise[].class);
				List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));
				model.addAttribute("frList", frList);
				
				FrCharges charges = new FrCharges();
				model.addAttribute("charges", charges);
				
			} catch (Exception e) {
				System.out.println("Execption in /addMultiFranchiseCharges : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 22-12-2020
	// Modified By :- Akhilesh
	// Modified On :- 23-12-2020
	// Descriprion :- saveMutiFrCharges
	@RequestMapping(value = "/saveMutiFrCharges", method = RequestMethod.POST)
	public String saveMutiFrCharges(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String curDateTime = dateFormat.format(cal.getTime());
		User userObj = (User) session.getAttribute("userObj");
		List<FrCharges> frChargesList=new ArrayList<>();
		
		try {
			
			String dates = request.getParameter("datesH");
			String string = dates;
			String[] parts = string.split("to");
			
			String part1 = parts[0];
			String part2 = parts[1];
			System.err.println(parts[0]+parts[1]);
			int compId = (int) session.getAttribute("companyId");
			
			StringBuilder sb = new StringBuilder();
			String[] frId = request.getParameterValues("frId");
			//System.err.println("Before For");
			for (int i = 0; i < frId.length; i++) {
				
				//System.err.println(frId[0]+","+frId[1]+","+frId[2]);
			
					FrCharges frCharge=new FrCharges();
					frCharge.setFrId(Integer.parseInt(frId[i]));
					frCharge.setExInt1(compId);
					frCharge.setFromDate(part1);
					frCharge.setToDate(part2);
					frCharge.setChargeId(Integer.parseInt(request.getParameter("exVar2"+frId[i])));
					//System.err.println("0000000000000"+frCharge.getChargeId());
					frCharge.setSurchargeFee(Float.parseFloat(request.getParameter("exVar3"+frId[i])));
					frCharge.setPackingChg(Float.parseFloat(request.getParameter("exVar4"+frId[i])));
					frCharge.setHandlingChg(Float.parseFloat(request.getParameter("exVar5"+frId[i])));
					frCharge.setExtraChg(Float.parseFloat(request.getParameter("exVar6"+frId[i])));
					frCharge.setRoundOffAmt(Float.parseFloat(request.getParameter("exVar7"+frId[i])));
					
					frChargesList.add(frCharge);
					
				
				
				//System.err.println("frids-->"+frId[i]);
				//System.err.println("exVar3"+frId[i]);
				//System.err.println(request.getParameter("exVar3"+frId[i]));
				//sb = sb.append(frId[i] + ",");
			}

			System.err.println("Frcharge List--->"+frChargesList);
			
			
			FrCharges[] rs=Constants.getRestTemplate().postForObject(Constants.url+"saveMultipleFrCharges", frChargesList, FrCharges[].class);
			List<FrCharges> resp=new ArrayList<>(Arrays.asList(rs));
			
			if (resp.size() > 0) {
			
					session.setAttribute("successMsg", "Additional Charges Saved Sucessfully");
			}else {
					session.setAttribute("error", "Additional Charges  Unable To save");
			} 
			
			
			/*String frIds = sb.toString();

			frIds = frIds.substring(0, frIds.length() - 1);
			System.out.println("Fr Ids-----"+frIds);

			int chargeId = Integer.parseInt(request.getParameter("charge_id"));
			String dates = request.getParameter("dates");
			String string = dates;
			String[] parts = string.split("to");
			String part1 = parts[0];
			String part2 = parts[1];

			FrCharges charges = new FrCharges();

			charges.setChargeId(chargeId);
			charges.setExtraChg(Float.parseFloat(request.getParameter("extra")));
			charges.setFrId(0);
			charges.setFromDate(part1);
			charges.setHandlingChg(Float.parseFloat(request.getParameter("handling")));
			charges.setPackingChg(Float.parseFloat(request.getParameter("packing")));
			charges.setRoundOffAmt(Float.parseFloat(request.getParameter("round_off")));
			charges.setSurchargeFee(Float.parseFloat(request.getParameter("surcharge")));
			charges.setToDate(part2);

			charges.setExFloat1(0);
			charges.setExFloat2(0);
			charges.setExFloat3(0);

			charges.setExInt1(compId);
			charges.setExInt2(0);
			charges.setExInt3(0);

			charges.setExVar1(frIds);
			charges.setExVar2("NA");
			charges.setExVar3("NA");
			System.out.println(charges);
			FrCharges res = Constants.getRestTemplate().postForObject(Constants.url + "saveMultiFrCharges", charges,
					FrCharges.class);

			if (res.getChargeId() > 0) {
				if (chargeId == 0)
					session.setAttribute("successMsg", "Additional Charges Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Additional Charges  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Additional Charges ");
			}*/

		} catch (Exception e) {
			System.out.println("Execption in /saveMultiFrCharges : " + e.getMessage());
			e.printStackTrace();
		}
		
		//int btnVal = Integer.parseInt(request.getParameter("btnType"));

		//if (btnVal == 0)
			return "redirect:/addMultiFranchiseCharges";
		//else
			//return "redirect:/addMultiFranchiseCharges";

	}

}
