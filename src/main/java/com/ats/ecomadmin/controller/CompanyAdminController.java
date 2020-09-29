package com.ats.ecomadmin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.BannerPage;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.Customer;
import com.ats.ecomadmin.model.CustomerAddDetail;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.GetCustomerInfo;
import com.ats.ecomadmin.model.GetRouteList;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.Route;
import com.ats.ecomadmin.model.RouteDelivery;
import com.ats.ecomadmin.model.RouteType;
import com.ats.ecomadmin.model.SubCategory;
import com.ats.ecomadmin.model.Tax;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@Scope("session")
public class CompanyAdminController {

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Add Company

	@RequestMapping(value = "/showAddCompany", method = RequestMethod.GET)
	public String showAddCompany(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddCompany", "showCompanys", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addCompany";
				CompMaster comp = new CompMaster();
				model.addAttribute("comp", comp);
				model.addAttribute("title", "Add Company");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);
			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Edit Company
	@RequestMapping(value = "/showEditCompany", method = RequestMethod.GET)
	public String showEditCompany(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showEditCompany", "showCompanys", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addCompany";

				String base64encodedString = request.getParameter("companyId");
				String companyId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				CompMaster comp = Constants.getRestTemplate().postForObject(Constants.url + "getCompanyByCompanyId",
						map, CompMaster.class);

				model.addAttribute("comp", comp);
				model.addAttribute("title", "Edit Company");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);

			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Save Company
	@RequestMapping(value = "/insertNewCompany", method = RequestMethod.POST)
	public String insertNewCompany(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {

		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String curDateTime = CommonUtility.getCurrentYMDDateTime();
		CompMaster comp = new CompMaster();
		HttpSession session = request.getSession();
		String profileImage = null;
		User userObj = (User) session.getAttribute("userObj");

		String mav = null;

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showAddCompany", "showCompanys", "0", "1", "0", "0", newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {

			mav = "redirect:/showCompanys";
			try {

				if (!doc.getOriginalFilename().equalsIgnoreCase("")) {


					profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

					try {
						new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
					} catch (Exception e) {
					}

				} else {
					profileImage = request.getParameter("editImg");

				}

				int companyId = Integer.parseInt(request.getParameter("companyId"));

				String comp_name = request.getParameter("comp_name");

				String contact_no = request.getParameter("contact_no");
				String email = request.getParameter("email");
				String openDate = request.getParameter("openDate");
				String address = request.getParameter("address");
				int city = Integer.parseInt(request.getParameter("city"));
				String state = request.getParameter("state");
				String companyPrefix = request.getParameter("companyPrefix");
				String website = request.getParameter("website");
				String gstCode = request.getParameter("gstCode");
				String gstNo = request.getParameter("gstNo");
				int compGstType = Integer.parseInt(request.getParameter("compGstType"));
				int paymentGatewayApplicable = Integer.parseInt(request.getParameter("paymentGatewayApplicable"));
				String bankName = request.getParameter("bankName");
				String branchName = request.getParameter("branchName");
				String ifscCode = request.getParameter("ifscCode");
				String accNo = request.getParameter("accNo");
				String cinNo = request.getParameter("cinNo");
				String fdaNo = request.getParameter("fdaNo");
				String fdaDelc = request.getParameter("fdaDelc");
				String panNo = request.getParameter("panNo");

				String paymentGatewayLink = new String();

				if (paymentGatewayApplicable == 1) {
					paymentGatewayLink = request.getParameter("paymentGatewayLink");
				}

				// System.err.println("CommonUtility.convertToYMD(openDate)" +
				// CommonUtility.convertToYMD(openDate));
				// System.err.println("curDateTime" + curDateTime);

				comp.setCompAddress(address);
				comp.setCompanyLogo(profileImage);
				comp.setCompanyName(comp_name);
				comp.setCompanyPrefix(companyPrefix);
				comp.setCompBankAccNo(accNo);
				comp.setCompBankBranchName(branchName);
				comp.setCompBankIfsc(ifscCode);
				comp.setCompBankName(bankName);
				comp.setCompCinNo(cinNo);
				comp.setCompCity(city);
				comp.setCompContactNo(contact_no);
				comp.setCompEmailAddress(email);
				comp.setCompFdaDeclarText(fdaDelc);
				comp.setCompFdaNo(fdaNo);
				comp.setCompGstNo(gstNo);
				comp.setCompGstType(compGstType);
				comp.setCompOpeningDate(CommonUtility.convertToYMD(openDate));
				comp.setCompPanNo(panNo);
				comp.setCompState(state);
				comp.setCompStateGstCode(gstCode);
				comp.setCompWebsite(website);
				comp.setCompanyId(companyId);
				comp.setDelStatus(1);
				comp.setExInt1(0);
				comp.setExInt2(0);
				comp.setExInt3(0);
				comp.setExVar1("NA");
				comp.setExVar2("NA");
				comp.setExVar3("NA");
				comp.setExVar4("NA");
				comp.setExFloat1(0);
				comp.setExFloat2(0);
				comp.setExFloat3(0);
				comp.setCompanyType(0);
				comp.setParentCompId(0);

				if (companyId > 0) {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("compId", companyId);

					CompMaster comp1 = Constants.getRestTemplate()
							.postForObject(Constants.url + "getCompanyByCompanyId", map, CompMaster.class);

					comp.setUpdtDttime(curDateTime);
					comp.setInsertDttime(comp1.getInsertDttime());

				} else {
					comp.setUpdtDttime(curDateTime);
					comp.setInsertDttime(curDateTime);

				}

				comp.setPaymentGatewayApplicable(paymentGatewayApplicable);
				comp.setPaymentGatewayLink(paymentGatewayLink);
				comp.setMakerUserId(userObj.getUserId());

				// System.err.println(comp.toString());
				CompMaster res = Constants.getRestTemplate().postForObject(Constants.url + "saveCompany", comp,
						CompMaster.class);

				// System.err.println(res.toString());
				if (res.getCompanyId() > 0) {
					if (companyId == 0)
						session.setAttribute("successMsg", "Company Saved Sucessfully");
					else
						session.setAttribute("successMsg", "Company  Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Company");
				}

			} catch (Exception e) {
				System.out.println("Execption in /insertUom : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return mav;

	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- CompanyList
	@RequestMapping(value = "/showCompanys", method = RequestMethod.GET)
	public String showCompanys(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showCompanys", "showCompanys", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/companyList";

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				CompMaster[] userArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCompany",
						CompMaster[].class);
				List<CompMaster> userList = new ArrayList<CompMaster>(Arrays.asList(userArr));

				for (int i = 0; i < userList.size(); i++) {

					userList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getCompanyId())));
				}
				model.addAttribute("compList", userList);
				model.addAttribute("title", "Company List");

				Info add = AccessControll.checkAccess("showCompanys", "showCompanys", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showCompanys", "showCompanys", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showCompanys", "showCompanys", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) { // System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) { // System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) { //
					System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}

		} catch (Exception e) {
			System.out.println("Execption in /showUsers : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Delete Company

	@RequestMapping(value = "/deleteCompany", method = RequestMethod.GET)
	public String deleteCompany(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteUser", "showCompanys", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "redirect:/showCompanys";
				String base64encodedString = request.getParameter("companyId");
				String companyId = FormValidation.DecodeKey(base64encodedString);
				User userObj = (User) session.getAttribute("userObj");
				String dateTime = CommonUtility.getCurrentYMDDateTime();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", Integer.parseInt(companyId));
				map.add("userId", userObj.getUserId());
				map.add("dateTime", dateTime);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteCompById", map, Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Add Customer

	@RequestMapping(value = "/showAddCustomer", method = RequestMethod.GET)
	public String showAddCustomer(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddCustomer", "showCustomers", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addCustomer";
				Customer cust = new Customer();
				model.addAttribute("cust", cust);
				model.addAttribute("title", "Add Customer");

				model.addAttribute("imgPath", Constants.showDocSaveUrl);

				/*
				 * CompMaster[] userArr = Constants.getRestTemplate().getForObject(Constants.url
				 * + "getAllCompany", CompMaster[].class); List<CompMaster> userList = new
				 * ArrayList<CompMaster>(Arrays.asList(userArr));
				 * 
				 * model.addAttribute("compList", userList);
				 */
			}
		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :-Edit Customer

	@RequestMapping(value = "/showEditCustomer", method = RequestMethod.GET)
	public String showEditCustomer(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showEditCustomer", "showCustomers", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addCustomer";
				String base64encodedString = request.getParameter("custId");
				String custId = FormValidation.DecodeKey(base64encodedString);

				model.addAttribute("title", "Edit Customer");

				model.addAttribute("imgPath", Constants.showDocSaveUrl);

				/*
				 * CompMaster[] userArr = Constants.getRestTemplate().getForObject(Constants.url
				 * + "getAllCompany", CompMaster[].class); List<CompMaster> userList = new
				 * ArrayList<CompMaster>(Arrays.asList(userArr));
				 * 
				 * model.addAttribute("compList", userList);
				 */

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custId", custId);
				Customer cust = Constants.getRestTemplate().postForObject(Constants.url + "getCustById", map,
						Customer.class);

				model.addAttribute("cust", cust);
			}
		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :-Customer List

	@RequestMapping(value = "/showCustomers", method = RequestMethod.GET)
	public String showCustomers(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showCustomers", "showCustomers", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/customerList";

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				GetCustomerInfo[] userArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllCustomerDetailInfo", GetCustomerInfo[].class);
				List<GetCustomerInfo> userList = new ArrayList<GetCustomerInfo>(Arrays.asList(userArr));

				for (int i = 0; i < userList.size(); i++) {

					userList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getCustId())));
				}
				model.addAttribute("custList", userList);
				model.addAttribute("title", "Customer List");

				Info add = AccessControll.checkAccess("showCustomers", "showCustomers", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showCustomers", "showCustomers", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showCustomers", "showCustomers", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) { // System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) { // System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) { //
					System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}

		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :-delete Customer
	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.GET)
	public String deleteCustomer(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteCustomer", "showCustomers", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "redirect:/showCustomers";
				String base64encodedString = request.getParameter("custId");
				String custId = FormValidation.DecodeKey(base64encodedString);
				User userObj = (User) session.getAttribute("userObj");
				String dateTime = CommonUtility.getCurrentYMDDateTime();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custId", Integer.parseInt(custId));
				map.add("userId", userObj.getUserId());
				map.add("dateTime", dateTime);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteCustById", map, Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Save Customer

	@RequestMapping(value = "/insertNewCustomer", method = RequestMethod.POST)
	public String insertNewCustomer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {

		try {

			Date date = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = CommonUtility.getCurrentYMDDateTime();
			HttpSession session = request.getSession();
			String profileImage = null;
			User userObj = (User) session.getAttribute("userObj");
			int companyId = (int) session.getAttribute("companyId");

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {


				profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				profileImage = request.getParameter("editImg");

			}
			Customer cust = new Customer();

			// int companyId = Integer.parseInt(request.getParameter("companyId"));

			String cust_name = request.getParameter("custName");

			String custMobileNo = request.getParameter("custMobileNo");
			String email = request.getParameter("email");
			String dateOfBirth = request.getParameter("dateOfBirth");
			int city = Integer.parseInt(request.getParameter("city"));
			int cust_id = Integer.parseInt(request.getParameter("cust_id"));

			int custGender = Integer.parseInt(request.getParameter("custGender"));
			int ageRange = Integer.parseInt(request.getParameter("ageRange"));

			int languageId = Integer.parseInt(request.getParameter("languageId"));

			// System.err.println("CommonUtility.convertToYMD(openDate)" +
			// CommonUtility.convertToYMD(dateOfBirth));
			// System.err.println("curDateTime" + curDateTime);

			cust.setAgeRange(ageRange);
			cust.setCityId(city);
			cust.setCustAddPlatform(1);
			cust.setCustGender(custGender);
			cust.setCustId(cust_id);
			cust.setCustMobileNo(custMobileNo);
			cust.setCustName(cust_name);
			cust.setDateOfBirth(CommonUtility.convertToYMD(dateOfBirth));
			cust.setEmailId(email);
			cust.setIsPrimiunmCust(0);
			cust.setLanguageId(languageId);
			cust.setProfilePic(profileImage);
			cust.setCompanyId(companyId);
			cust.setIsActive(1);
			cust.setDelStatus(1);
			cust.setExInt1(0);
			cust.setExInt2(0);
			cust.setExInt3(0);
			cust.setExVar1("NA");
			cust.setExVar2("NA");
			cust.setExVar3("NA");
			cust.setMakerUserId(userObj.getUserId());
			if (cust_id > 0) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custId", cust_id);
				Customer cust1 = Constants.getRestTemplate().postForObject(Constants.url + "getCustById", map,
						Customer.class);

				cust.setUpdtDttime(curDateTime);
				cust.setInsertDttime(cust1.getInsertDttime());

			} else {
				cust.setInsertDttime(curDateTime);
				cust.setUpdtDttime(curDateTime);

			}

			System.err.println(cust.toString());
			Customer res = Constants.getRestTemplate().postForObject(Constants.url + "saveCustomer", cust,
					Customer.class);

			// System.err.println(res.toString());
			if (res.getCustId() > 0) {
				if (cust_id == 0)
					session.setAttribute("successMsg", "Customer Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Customer  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Customer");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showCustomers";

	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Show Add CustomerAddress
	@RequestMapping(value = "/showAddCustomerAddress", method = RequestMethod.GET)
	public String showAddCustomerAddress(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddCustomer", "showCustomers", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addCustAddress";
				model.addAttribute("title", "Add Customer Address");

				String base64encodedString = request.getParameter("custId");
				String custId = FormValidation.DecodeKey(base64encodedString);

				CustomerAddDetail custDet = new CustomerAddDetail();
				model.addAttribute("custDet", custDet);
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custId", custId);
				Customer cust = Constants.getRestTemplate().postForObject(Constants.url + "getCustById", map,
						Customer.class);
				model.addAttribute("cust", cust);
				
				
				model.addAttribute("custIdVal", FormValidation.Encrypt(custId));

			}
		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Show CustomerAddress list

	@RequestMapping(value = "/showCustomerAddressList", method = RequestMethod.GET)
	public String showCustomerAddressList(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showCustomers", "showCustomers", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/custAddressList";
				model.addAttribute("title", " Customer Address List");

				String base64encodedString = request.getParameter("custId");
				String custId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custId", custId);
				Customer cust = Constants.getRestTemplate().postForObject(Constants.url + "getCustById", map,
						Customer.class);
				model.addAttribute("cust", cust);
				map = new LinkedMultiValueMap<>();
				map.add("custId", custId);

				CustomerAddDetail[] userArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllCustomerDetailByCustId", map, CustomerAddDetail[].class);
				List<CustomerAddDetail> userList = new ArrayList<CustomerAddDetail>(Arrays.asList(userArr));

				for (int i = 0; i < userList.size(); i++) {

					userList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getCustDetailId())));
					userList.get(i).setExVar2(FormValidation.Encrypt(String.valueOf(userList.get(i).getCustId())));
				}
				model.addAttribute("custAddList", userList);

				Info add = AccessControll.checkAccess("showCustomers", "showCustomers", "0", "1",
						"0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showCustomers", "showCustomers", "0", "0",
						"1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showCustomers", "showCustomers", "0", "0",
						"0", "1", newModuleList);

				if (add.isError() == false) { // System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) { // System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) { //
					System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}
		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Save CustomerAddress
	@RequestMapping(value = "/insertNewCustomerAddress", method = RequestMethod.POST)
	public String insertNewCustomerAddress(HttpServletRequest request, HttpServletResponse response) {
		int cust_id = 0;
		try {

			Date date = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = CommonUtility.getCurrentYMDDateTime();
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			String address = request.getParameter("address");
			String landmark = request.getParameter("landmark");
			String caption = request.getParameter("caption");
			int cityId = Integer.parseInt(request.getParameter("cityId"));
			int areaId = Integer.parseInt(request.getParameter("areaId"));

			double latitude;
			try {
				latitude = Double.parseDouble(request.getParameter("latitude"));
			} catch (Exception e) {
				latitude = 0;
			}
			double longitude;
			try {
				longitude = Double.parseDouble(request.getParameter("longitude"));
			} catch (Exception e) {
				longitude = 0;
			}
			cust_id = Integer.parseInt(request.getParameter("cust_id"));
			int custDetailId = Integer.parseInt(request.getParameter("custDetailId"));
			CustomerAddDetail custDet = new CustomerAddDetail();

			custDet.setAddress(address);
			custDet.setAreaId(areaId);
			custDet.setCaption(caption);
			custDet.setCityId(cityId);
			custDet.setCustDetailId(custDetailId);
			custDet.setCustId(cust_id);
			custDet.setLandmark(landmark);
			custDet.setLatitude(latitude);
			custDet.setLongitude(longitude);
			custDet.setIsActive(1);
			custDet.setDelStatus(1);
			custDet.setExInt1(0);
			custDet.setExInt2(0);
			custDet.setExInt3(0);
			custDet.setExVar1("NA");
			custDet.setExVar2("NA");
			custDet.setExVar3("NA");

			custDet.setMakerUserId(userObj.getUserId());

			if (custDetailId > 0) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custDetId", custDetailId);
				CustomerAddDetail custDet1 = Constants.getRestTemplate().postForObject(Constants.url + "getCustDetById",
						map, CustomerAddDetail.class);

				custDet.setInsertDttime(custDet1.getInsertDttime());
				custDet.setUpdtDttime(curDateTime);

			} else {
				custDet.setInsertDttime(curDateTime);
				custDet.setUpdtDttime(curDateTime);
			}

			CustomerAddDetail res = Constants.getRestTemplate().postForObject(Constants.url + "saveCustomerDet",
					custDet, CustomerAddDetail.class);

			if (res.getCustDetailId() > 0) {
				if (custDetailId == 0)
					session.setAttribute("successMsg", "Customer Address Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Customer Address Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Customer Address");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showCustomerAddressList?custId=" + FormValidation.Encrypt(String.valueOf(cust_id));

	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Delete CustomerAddress
	@RequestMapping(value = "/deleteCustomerAddress", method = RequestMethod.GET)
	public String deleteCustomerAddress(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteCustomer", "showCustomers", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {


				String base64encodedString = request.getParameter("custDetailId");
				String custDetailId = FormValidation.DecodeKey(base64encodedString);

				String base64encodedString1 = request.getParameter("custId");
				String custId = FormValidation.DecodeKey(base64encodedString1);
				mav = "redirect:/showCustomerAddressList?custId=" + FormValidation.Encrypt(String.valueOf(custId));

				User userObj = (User) session.getAttribute("userObj");
				String dateTime = CommonUtility.getCurrentYMDDateTime();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custDetId", Integer.parseInt(custDetailId));
				map.add("userId", userObj.getUserId());
				map.add("dateTime", dateTime);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteCustDetById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- EditCustomerAddress
	@RequestMapping(value = "/showEditCustomerAddress", method = RequestMethod.GET)
	public String showEditCustomerAddress(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showEditCustomer", "showCustomers", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addCustAddress";
				model.addAttribute("title", "Edit Customer Address");

				String base64encodedString = request.getParameter("custDetailId");
				String custDetId = FormValidation.DecodeKey(base64encodedString);

				String base64encodedString1 = request.getParameter("custId");
				String custId = FormValidation.DecodeKey(base64encodedString1);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custDetId", custDetId);
				CustomerAddDetail custDet = Constants.getRestTemplate().postForObject(Constants.url + "getCustDetById",
						map, CustomerAddDetail.class);
				model.addAttribute("custDet", custDet);

				model.addAttribute("custDet", custDet);
				map = new LinkedMultiValueMap<>();
				map.add("custId", custId);
				Customer cust = Constants.getRestTemplate().postForObject(Constants.url + "getCustById", map,
						Customer.class);
				model.addAttribute("cust", cust);
				
				
				model.addAttribute("custIdVal", FormValidation.Encrypt(custId));

			}
		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Check unique Cust mobile

	@RequestMapping(value = "/getCustInfo", method = RequestMethod.GET)
	@ResponseBody
	public Info getUserInfo(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			int userId = 0;
			try {
				userId = Integer.parseInt(request.getParameter("userId"));
			} catch (Exception e) {
				userId = 0;
				e.printStackTrace();
			}
			String mobNo = request.getParameter("mobNo");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("mobNo", mobNo);
			map.add("userId", userId);
			Customer res = Constants.getRestTemplate().postForObject(Constants.url + "getCustByMobNo", map,
					Customer.class);
			if (res != null) {
				info.setError(false);
				info.setMsg("Customer Found");
			} else {
				info.setError(true);
				info.setMsg("Customer Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getUserInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Add sub Cat

	@RequestMapping(value = "/showAddSubCat", method = RequestMethod.GET)
	public String showAddSubCat(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		HttpSession session = request.getSession();
		List<Category> catList = new ArrayList<>();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddCompany", "showSubCatList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addSubCat";
				SubCategory subCat = new SubCategory();

				int compId = (int) session.getAttribute("companyId");

				model.addAttribute("subCat", subCat);
				model.addAttribute("title", "Add Sub Category");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);

				CompMaster[] compArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCompany",
						CompMaster[].class);
				List<CompMaster> compList = new ArrayList<CompMaster>(Arrays.asList(compArr));
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				model.addAttribute("catList", catList);
				model.addAttribute("compList", compList);
			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Edit sub Cat
	@RequestMapping(value = "/showEditSubCat", method = RequestMethod.GET)
	public String showEditSubCat(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		HttpSession session = request.getSession();
		List<Category> catList = new ArrayList<>();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showEditSubCat", "showSubCatList", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addSubCat";

				String base64encodedString = request.getParameter("subCatId");
				String subCatId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("subCatId", subCatId);
				SubCategory subCat = Constants.getRestTemplate().postForObject(Constants.url + "getSubCatById", map,
						SubCategory.class);

				int compId = (int) session.getAttribute("companyId");

				model.addAttribute("subCat", subCat);
				model.addAttribute("title", "Edit Sub Category");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);

				CompMaster[] compArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCompany",
						CompMaster[].class);
				List<CompMaster> compList = new ArrayList<CompMaster>(Arrays.asList(compArr));
				map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				model.addAttribute("catList", catList);
				model.addAttribute("compList", compList);
			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- sub Cat List

	@RequestMapping(value = "/showSubCatList", method = RequestMethod.GET)
	public String showSubCatList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<SubCategory> subCatList = new ArrayList<SubCategory>();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showSubCatList", "showSubCatList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/subCatList";
				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				SubCategory[] subCatArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllActiveSubCategories", map, SubCategory[].class);
				subCatList = new ArrayList<SubCategory>(Arrays.asList(subCatArray));

				for (int i = 0; i < subCatList.size(); i++) {
					subCatList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(subCatList.get(i).getSubCatId())));
				}
				model.addAttribute("subCatList", subCatList);
				model.addAttribute("title", "Sub Category List");

				Info add = AccessControll.checkAccess("showSubCatList", "showSubCatList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showSubCatList", "showSubCatList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showSubCatList", "showSubCatList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) { // System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) { // System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) { //
					System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}

		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- insert sub Cat
	@RequestMapping(value = "/insertNewSubCat", method = RequestMethod.POST)
	public String insertNewSubCat(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {
		int cust_id = 0;
		try {
			HttpSession session = request.getSession();
			Date date = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

			String profileImage = new String();
			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {


				profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				profileImage = request.getParameter("editImg");

			}

			String subCatName = request.getParameter("subCatName");
			String subCatDesc = request.getParameter("subCatDesc");
			String subCatPrefix = request.getParameter("subCatPrefix");
			String subCatCode = request.getParameter("subCatCode");

			int catId = Integer.parseInt(request.getParameter("catId"));
			int allowToCopy = Integer.parseInt(request.getParameter("allowToCopy"));
			int subCatId = Integer.parseInt(request.getParameter("subCatId"));
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			int sortNo = Integer.parseInt(request.getParameter("sortNo"));

			SubCategory subcat = new SubCategory();
			subcat.setAllowToCopy(allowToCopy);
			subcat.setCatId(catId);
			subcat.setSubCatId(subCatId);
			subcat.setCompanyId(companyId);
			subcat.setImageName(profileImage);
			subcat.setIsParent(0);
			subcat.setSubCatPrefix(subCatPrefix);
			subcat.setSubCatName(subCatName);
			subcat.setSubCatDesc(subCatDesc);
			subcat.setSubCatCode(subCatCode);
			subcat.setSortNo(sortNo);
			subcat.setIsActive(1);
			subcat.setDelStatus(1);
			subcat.setExInt1(0);
			subcat.setExInt2(0);
			subcat.setExInt3(0);
			subcat.setExVar1("NA");
			subcat.setExVar2("NA");
			subcat.setExVar3("NA");
			subcat.setExFloat1(0);
			subcat.setExFloat2(0);
			subcat.setExFloat3(0);

			SubCategory res = Constants.getRestTemplate().postForObject(Constants.url + "saveSubCat", subcat,
					SubCategory.class);

			if (res.getSubCatId() > 0) {
				if (subCatId == 0)
					session.setAttribute("successMsg", "SubCategory Address Saved Sucessfully");
				else
					session.setAttribute("successMsg", "SubCategory Address Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save SubCategory Address");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showSubCatList";

	}
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Delete sub Cat

	@RequestMapping(value = "/deleteSubCat", method = RequestMethod.GET)
	public String deleteSubCat(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteSubCat", "showSubCatList", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "redirect:/showSubCatList";
				String base64encodedString = request.getParameter("subCatId");
				String subCatId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("subCatId", Integer.parseInt(subCatId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteSubCatById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// *************************Banner****
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Add Banner

	@RequestMapping(value = "/showAddBanner", method = RequestMethod.GET)
	public String showAddBanner(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddBanner", "showBannerList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addBanner";
				List<MFilter> filterList = new ArrayList<MFilter>();

				BannerPage banner = new BannerPage();
				model.addAttribute("banner", banner);
				model.addAttribute("title", "Add Banner");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);

				int companyId = (int) session.getAttribute("companyId");

				List<Franchise> frList = new ArrayList<Franchise>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				frList = new ArrayList<Franchise>(Arrays.asList(frArr));
				model.addAttribute("frList", frList);

				map = new LinkedMultiValueMap<>();
				map.add("filterTypeId", 7);
				map.add("compId", companyId);

				MFilter[] filterArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getFiltersListByTypeId", map, MFilter[].class);
				filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));
				model.addAttribute("filterList", filterList);

			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- insert Banner


	@RequestMapping(value = "/insertNewBanner", method = RequestMethod.POST)
	public String insertNewBanner(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {
		try {

			Date date = new Date();
			HttpSession session = request.getSession();

			int companyId = (int) session.getAttribute("companyId");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = CommonUtility.getCurrentYMDDateTime();
			User userObj = (User) session.getAttribute("userObj");
			String profileImage = new String();
			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {


				profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				profileImage = request.getParameter("editImg");

			}

			String bannerEventName = request.getParameter("bannerEventName");
			String captionOnproductPage = request.getParameter("captionOnproductPage");
			int bannerId = Integer.parseInt(request.getParameter("bannerId"));
			int sortNo = Integer.parseInt(request.getParameter("sortNo"));

			StringBuilder sbEmp = new StringBuilder();
			String frIds[] = request.getParameterValues("frId");

			String daysList = "";
			if (frIds != null) {
				List<String> daysIdList = new ArrayList<>();
				daysIdList = Arrays.asList(frIds);

				daysList = daysIdList.toString().substring(1, daysIdList.toString().length() - 1);
				daysList = daysList.replaceAll("\"", "");
				daysList = daysList.replaceAll(" ", "");
			}

			String tagIds[] = request.getParameterValues("tagId");

			String daysList1 = "";
			if (tagIds != null) {
				List<String> daysIdList1 = new ArrayList<>();
				daysIdList1 = Arrays.asList(tagIds);

				daysList1 = daysIdList1.toString().substring(1, daysIdList1.toString().length() - 1);
				daysList1 = daysList1.replaceAll("\"", "");
				daysList1 = daysList1.replaceAll(" ", "");
			}

			BannerPage banner = new BannerPage();
			banner.setBannerEventName(bannerEventName);
			banner.setBannerId(bannerId);
			banner.setBannerImage(profileImage);
			banner.setCaptionOnproductPage(captionOnproductPage);
			banner.setFrIds(daysList);
			banner.setSortNo(sortNo);
			banner.setTagIds(daysList1);
			banner.setCompId(companyId);
			banner.setSortNo(sortNo);
			banner.setIsActive(1);
			banner.setDelStatus(1);
			banner.setExInt1(0);
			banner.setExInt2(0);
			banner.setExInt3(0);
			banner.setExVar1("NA");
			banner.setExVar2("NA");
			banner.setExVar3("NA");

			if (bannerId > 0) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("bannerId", bannerId);

				BannerPage banner1 = Constants.getRestTemplate().postForObject(Constants.url + "getBannerById", map,
						BannerPage.class);

				banner.setInsertDateTime(banner1.getInsertDateTime());
				banner.setUpdateDateTime(curDateTime);
				banner.setInsertUserId(banner1.getInsertUserId());
				banner.setUpdateUserId(userObj.getUserId());

			} else {
				banner.setInsertDateTime(curDateTime);
				banner.setUpdateDateTime(curDateTime);
				banner.setInsertUserId(userObj.getUserId());
				banner.setUpdateUserId(0);

			}
			BannerPage res = Constants.getRestTemplate().postForObject(Constants.url + "saveBanner", banner,
					BannerPage.class);

			if (res.getBannerId() > 0) {
				if (bannerId == 0)
					session.setAttribute("successMsg", "BannerPage   Saved Sucessfully");
				else
					session.setAttribute("successMsg", "BannerPage   Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save BannerPage Address");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showBannerList";

	}
	
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Edit Banner


	@RequestMapping(value = "/showEditBanner", method = RequestMethod.GET)
	public String showEditBanner(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showEditBanner", "showBannerList", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addBanner";

				String base64encodedString = request.getParameter("bannerId");
				String bannerId = FormValidation.DecodeKey(base64encodedString);
				List<MFilter> filterList = new ArrayList<MFilter>();
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("bannerId", bannerId);

				BannerPage banner = Constants.getRestTemplate().postForObject(Constants.url + "getBannerById", map,
						BannerPage.class);

				model.addAttribute("banner", banner);
				model.addAttribute("title", "Edit Banner");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);

				int companyId = (int) session.getAttribute("companyId");

				List<Franchise> frList = new ArrayList<Franchise>();

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				frList = new ArrayList<Franchise>(Arrays.asList(frArr));
				model.addAttribute("frList", frList);

				List<Integer> frIds = Stream.of(banner.getFrIds().split(",")).map(Integer::parseInt)
						.collect(Collectors.toList());
				model.addAttribute("frIds", frIds);

				List<Integer> tagIds = Stream.of(banner.getTagIds().split(",")).map(Integer::parseInt)
						.collect(Collectors.toList());
				model.addAttribute("tagIds", tagIds);

				map = new LinkedMultiValueMap<>();
				map.add("filterTypeId", 7);
				map.add("compId", companyId);

				MFilter[] filterArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getFiltersListByTypeId", map, MFilter[].class);
				filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));
				model.addAttribute("filterList", filterList);

			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Delete Banner


	@RequestMapping(value = "/deleteBanner", method = RequestMethod.GET)
	public String deleteBanner(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteBanner", "showBannerList", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "redirect:/showBannerList";
				String base64encodedString = request.getParameter("bannerId");
				String bannerId = FormValidation.DecodeKey(base64encodedString);
				User userObj = (User) session.getAttribute("userObj");
				String dateTime = CommonUtility.getCurrentYMDDateTime();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("bannerId", Integer.parseInt(bannerId));
				map.add("userId", userObj.getUserId());
				map.add("dateTime", dateTime);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteBannerById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteBannerById : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :-   Banner List


	@RequestMapping(value = "/showBannerList", method = RequestMethod.GET)
	public String showBannerList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<BannerPage> subCatList = new ArrayList<BannerPage>();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showBannerList", "showBannerList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/bannerList";
				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				BannerPage[] subCatArray = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllBannerByCompId", map, BannerPage[].class);
				subCatList = new ArrayList<BannerPage>(Arrays.asList(subCatArray));

				for (int i = 0; i < subCatList.size(); i++) {
					subCatList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(subCatList.get(i).getBannerId())));
				}
				model.addAttribute("bannerList", subCatList);
				model.addAttribute("title", "Banner List");

				Info add = AccessControll.checkAccess("showBannerList", "showBannerList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showBannerList", "showBannerList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showBannerList", "showBannerList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) { // System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) { // System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) { //
					System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}

		} catch (

		Exception e) {
			System.out.println("Execption in /showBannerList : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 25-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Add Route


	@RequestMapping(value = "/showAddRoute", method = RequestMethod.GET)
	public String showAddRoute(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddRoute", "showRouteList", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addRoute";
				Route route = new Route();
				model.addAttribute("route", route);
				model.addAttribute("title", "Add Route");

				int companyId = (int) session.getAttribute("companyId");

				RouteType[] routeArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllRouteType",
						RouteType[].class);
				List<RouteType> routeList = new ArrayList<RouteType>(Arrays.asList(routeArr));
				model.addAttribute("routeTypeList", routeList);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				RouteDelivery[] routeArrDel = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllRouteDelivery", RouteDelivery[].class);
				List<RouteDelivery> routeDelList = new ArrayList<RouteDelivery>(Arrays.asList(routeArrDel));
				model.addAttribute("routeDelList", routeDelList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));
				model.addAttribute("frList", frList);

			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 25-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Edit Route


	@RequestMapping(value = "/showEditRoute", method = RequestMethod.GET)
	public String showEditRoute(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showEditRoute", "showRouteList", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addRoute";

				model.addAttribute("title", "Edit Route");

				int companyId = (int) session.getAttribute("companyId");

				String base64encodedString = request.getParameter("routeId");
				String routeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("routeId", routeId);

				Route route = Constants.getRestTemplate().postForObject(Constants.url + "getRouteById", map,
						Route.class);

				model.addAttribute("route", route);

				List<Integer> frIds = Stream.of(route.getFrIds().split(",")).map(Integer::parseInt)
						.collect(Collectors.toList());
				model.addAttribute("frIds", frIds);

				RouteType[] routeArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllRouteType",
						RouteType[].class);
				List<RouteType> routeList = new ArrayList<RouteType>(Arrays.asList(routeArr));
				model.addAttribute("routeTypeList", routeList);

				map = new LinkedMultiValueMap<>();

				RouteDelivery[] routeArrDel = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllRouteDelivery", RouteDelivery[].class);
				List<RouteDelivery> routeDelList = new ArrayList<RouteDelivery>(Arrays.asList(routeArrDel));
				model.addAttribute("routeDelList", routeDelList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));
				model.addAttribute("frList", frList);

			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 25-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :-   Route List

	@RequestMapping(value = "/showRouteList", method = RequestMethod.GET)
	public String showRouteList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showRouteList", "showRouteList", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				model.addAttribute("title", "Route List");

				mav = "masters/routeList";
				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				GetRouteList[] userArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllRouteByCompId", map, GetRouteList[].class);
				List<GetRouteList> userList = new ArrayList<GetRouteList>(Arrays.asList(userArr));

				for (int i = 0; i < userList.size(); i++) {

					userList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getRouteId())));
				}
				model.addAttribute("routeList", userList);

				Info add = AccessControll.checkAccess("showRouteList", "showRouteList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showRouteList", "showRouteList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showRouteList", "showRouteList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) { // System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) { // System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) { //
					System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}

		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 25-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Delete Route


	@RequestMapping(value = "/deleteRoute", method = RequestMethod.GET)
	public String deleteRoute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteRoute", "showRouteList", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "redirect:/showRouteList";
				String base64encodedString = request.getParameter("routeId");
				String routeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("routeId", Integer.parseInt(routeId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteRouteById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 25-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Insert Route


	@RequestMapping(value = "/insertNewRoute", method = RequestMethod.POST)
	public String insertNewRoute(HttpServletRequest request, HttpServletResponse response) {

		try {


			HttpSession session = request.getSession();

			int companyId = (int) session.getAttribute("companyId");

			String routeName = request.getParameter("routeName");

			String routeCode = request.getParameter("routeCode");
			float routeKm = Float.parseFloat(request.getParameter("routeKm"));
			float sortNo = Float.parseFloat(request.getParameter("sortNo"));

			int rouidDelveryId = Integer.parseInt(request.getParameter("rouidDelveryId"));
			int routeId = Integer.parseInt(request.getParameter("routeId"));
			int routeTypeName = Integer.parseInt(request.getParameter("routeTypeName"));

			int isPrimeRoute = Integer.parseInt(request.getParameter("isPrimeRoute"));

			StringBuilder sbEmp = new StringBuilder();
			String frIds[] = request.getParameterValues("frId");

			String daysList = "";
			if (frIds != null) {
				List<String> daysIdList = new ArrayList<>();
				daysIdList = Arrays.asList(frIds);

				daysList = daysIdList.toString().substring(1, daysIdList.toString().length() - 1);
				daysList = daysList.replaceAll("\"", "");
				daysList = daysList.replaceAll(" ", "");
			}

			Route route = new Route();

			route.setFrIds(daysList);
			route.setTypeOfRoute(routeTypeName);
			route.setSortNo(sortNo);
			route.setRouteName(routeName);
			route.setRouteId(routeId);
			route.setRouteCode(routeCode);
			route.setIsPrimeRoute(isPrimeRoute);
			route.setIsDeliveryNo(rouidDelveryId);
			route.setRouteKm(routeKm);
			route.setCompanyId(companyId);
			route.setIsActive(1);
			route.setDelStatus(1);
			route.setExInt1(0);
			route.setExInt2(0);
			route.setExInt3(0);
			route.setExVar1("NA");
			route.setExVar2("NA");
			route.setExVar3("NA");

			Route res = Constants.getRestTemplate().postForObject(Constants.url + "saveRoute", route, Route.class);

			if (res.getRouteId() > 0) {
				if (routeId == 0)
					session.setAttribute("successMsg", "Route Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Route  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Route");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showRouteList";

	}
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- showRouteTypeList


	@RequestMapping(value = "/showRouteTypeList", method = RequestMethod.GET)
	public String showRouteTypeList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showRouteTypeList", "showRouteTypeList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				model.addAttribute("title", "Route Type List");

				mav = "masters/routeTypeList";
				int companyId = (int) session.getAttribute("companyId");

				RouteType[] routeArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllRouteType",
						RouteType[].class);
				List<RouteType> routeList = new ArrayList<RouteType>(Arrays.asList(routeArr));

				for (int i = 0; i < routeList.size(); i++) {

					routeList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(routeList.get(i).getRouteTypeId())));
				}
				model.addAttribute("routeTypeList", routeList);
				Info add = AccessControll.checkAccess("showRouteTypeList", "showRouteTypeList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showRouteTypeList", "showRouteTypeList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showRouteTypeList", "showRouteTypeList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) { // System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) { // System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) { //
					System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}

		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Delete Type


	@RequestMapping(value = "/deleteRouteType", method = RequestMethod.GET)
	public String deleteRouteType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteRouteType", "showRouteTypeList", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "redirect:/showRouteTypeList";
				String base64encodedString = request.getParameter("routeTypeId");
				String routeTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("routeId", Integer.parseInt(routeTypeId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteRouteTypeById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteRouteType : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Add route Type

	@RequestMapping(value = "/showAddRouteType", method = RequestMethod.GET)
	public String showAddRouteType(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddRouteType", "showRouteTypeList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addRouteType";
				RouteType route = new RouteType();
				model.addAttribute("route", route);
				model.addAttribute("title", "Add Route Type");

			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Edit route Type

	@RequestMapping(value = "/showEditRouteType", method = RequestMethod.GET)
	public String showEditRouteType(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showEditRouteType", "showRouteTypeList", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addRouteType";

				model.addAttribute("title", "Edit Route Type");

				String base64encodedString = request.getParameter("routeTypeId");
				String routeTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("routeId", routeTypeId);

				RouteType route = Constants.getRestTemplate().postForObject(Constants.url + "getRouteTypeById", map,
						RouteType.class);

				model.addAttribute("route", route);

			}
		} catch (Exception e) {
			System.out.println("Execption in /showEditRouteType : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- insert route Type

	@RequestMapping(value = "/insertNewRouteType", method = RequestMethod.POST)
	public String insertNewRouteType(HttpServletRequest request, HttpServletResponse response) {

		try {


			HttpSession session = request.getSession();

			int companyId = (int) session.getAttribute("companyId");

			String routeTypeName = request.getParameter("routeTypeName");

			String routeRangeInKm = request.getParameter("routeRangeInKm");
			float sortNo = Float.parseFloat(request.getParameter("sortNo"));

			int routeTypeId = Integer.parseInt(request.getParameter("routeTypeId"));

			int isCopy = Integer.parseInt(request.getParameter("isCopy"));

			RouteType route = new RouteType();

			route.setIsCopy(isCopy);
			route.setSortNo(sortNo);
			route.setRouteTypeName(routeTypeName);
			route.setRouteRangeInKm(routeRangeInKm);
			route.setRouteTypeId(routeTypeId);
			route.setCompanyId(companyId);
			route.setIsActive(1);
			route.setDelStatus(1);
			route.setExInt1(0);
			route.setExInt2(0);
			route.setExInt3(0);
			route.setExVar1("NA");
			route.setExVar2("NA");
			route.setExVar3("NA");

			RouteType res = Constants.getRestTemplate().postForObject(Constants.url + "saveRouteType", route,
					RouteType.class);

			if (res.getRouteTypeId() > 0) {
				if (routeTypeId == 0)
					session.setAttribute("successMsg", "Route Type Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Route Type  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Route Type");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showRouteTypeList";

	}

	/**************************** Route del *********************************/
	
	
	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :-   route Delivery list

	@RequestMapping(value = "/showRouteDelList", method = RequestMethod.GET)
	public String showRouteDelList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showRouteDelList", "showRouteDelList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				model.addAttribute("title", "Route Delivery List");

				mav = "masters/RouteDelList";
				int companyId = (int) session.getAttribute("companyId");

				RouteDelivery[] routeArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllRouteDelivery", RouteDelivery[].class);
				List<RouteDelivery> routeList = new ArrayList<RouteDelivery>(Arrays.asList(routeArr));

				for (int i = 0; i < routeList.size(); i++) {

					routeList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(routeList.get(i).getRouidDelveryId())));
				}
				model.addAttribute("routeDelList", routeList);
				Info add = AccessControll.checkAccess("showRouteDelList", "showRouteDelList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showRouteDelList", "showRouteDelList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showRouteDelList", "showRouteDelList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) { // System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) { // System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) { //
					System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}

			}

		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	
	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Delete route Delivery
	@RequestMapping(value = "/deleteRouteDel", method = RequestMethod.GET)
	public String deleteRouteDel(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteRouteDel", "showRouteDelList", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "redirect:/showRouteDelList";
				String base64encodedString = request.getParameter("rouidDelveryId");
				String routeTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("delvId", Integer.parseInt(routeTypeId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteRouteDeliveryById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteRouteType : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	
	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Add route Delivery
	@RequestMapping(value = "/showAddRouteDel", method = RequestMethod.GET)
	public String showAddRouteDel(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showAddRouteDel", "showRouteDelList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addRouteDelv";
				RouteDelivery route = new RouteDelivery();
				model.addAttribute("route", route);
				model.addAttribute("title", "Add Route Type");

			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Edit route Delivery
	@RequestMapping(value = "/showEditRouteDel", method = RequestMethod.GET)
	public String showEditRouteDel(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showEditRouteDel", "showRouteDelList", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addRouteDelv";

				model.addAttribute("title", "Edit Route Delivery");

				String base64encodedString = request.getParameter("rouidDelveryId");
				String routeTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("routeId", routeTypeId);

				RouteDelivery route = Constants.getRestTemplate().postForObject(Constants.url + "getRouteDeliveryById",
						map, RouteDelivery.class);

				model.addAttribute("route", route);

			}
		} catch (Exception e) {
			System.out.println("Execption in /showEditRouteType : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- insert route Delivery

	@RequestMapping(value = "/insertNewRouteDelv", method = RequestMethod.POST)
	public String insertNewRouteDelv(HttpServletRequest request, HttpServletResponse response) {

		try {


			HttpSession session = request.getSession();

			int companyId = (int) session.getAttribute("companyId");

			String deliveryName = request.getParameter("deliveryName");

			String timeSlots = request.getParameter("timeSlots");
			float sortNo = Float.parseFloat(request.getParameter("sortNo"));

			int rouidDelveryId = Integer.parseInt(request.getParameter("rouidDelveryId"));

			int isCopy = Integer.parseInt(request.getParameter("isCopy"));

			RouteDelivery route = new RouteDelivery();

			route.setIsCopy(isCopy);
			route.setSortNo(sortNo);
			route.setDeliveryName(deliveryName);
			route.setTimeSlots(timeSlots);
			route.setRouidDelveryId(rouidDelveryId);

			route.setCompanyId(companyId);
			route.setIsActive(1);
			route.setDelStatus(1);
			route.setExInt1(0);
			route.setExInt2(0);
			route.setExInt3(0);
			route.setExVar1("NA");
			route.setExVar2("NA");
			route.setExVar3("NA");

			RouteDelivery res = Constants.getRestTemplate().postForObject(Constants.url + "saveRouteDelivery", route,
					RouteDelivery.class);

			if (res.getRouidDelveryId() > 0) {
				if (rouidDelveryId == 0)
					session.setAttribute("successMsg", "Route Delivery Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Route Delivery  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Route Delivery");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showRouteDelList";

	}

}
