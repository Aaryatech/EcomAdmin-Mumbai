package com.ats.ecomadmin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.Customer;
import com.ats.ecomadmin.model.CustomerAddDetail;
import com.ats.ecomadmin.model.GetCustomerInfo;
import com.ats.ecomadmin.model.Info;
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
	public String newUom(HttpServletRequest request, HttpServletResponse response, Model model) {
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

				CompMaster comp = Constants.getRestTemplate().postForObject(Constants.url + "getCompById", map,
						CompMaster.class);

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

		try {

			System.err.println("hii");
			Date date = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = dateFormat.format(cal.getTime());
			CompMaster comp = new CompMaster();
			HttpSession session = request.getSession();
			String profileImage = null;
			User userObj = (User) session.getAttribute("userObj");

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				System.err.println("In If ");

				profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				System.err.println("In else ");
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

			System.err.println("CommonUtility.convertToYMD(openDate)" + CommonUtility.convertToYMD(openDate));
			System.err.println("curDateTime" + curDateTime);

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
			comp.setInsertDttime(curDateTime);
			comp.setUpdtDttime(curDateTime);
			comp.setPaymentGatewayApplicable(paymentGatewayApplicable);
			comp.setPaymentGatewayLink(paymentGatewayLink);
			comp.setMakerUserId(userObj.getCompanyId());

			System.err.println(comp.toString());
			CompMaster res = Constants.getRestTemplate().postForObject(Constants.url + "saveCompany", comp,
					CompMaster.class);

			System.err.println(res.toString());
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
		return "redirect:/showCompanys";

	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- CompanyList
	@RequestMapping(value = "/showCompanys", method = RequestMethod.GET)
	public String showMnUsers(HttpServletRequest request, HttpServletResponse response, Model model) {

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
	public String deleteUser(HttpServletRequest request, HttpServletResponse response) {

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

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", Integer.parseInt(companyId));

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

				CompMaster[] userArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCompany",
						CompMaster[].class);
				List<CompMaster> userList = new ArrayList<CompMaster>(Arrays.asList(userArr));

				model.addAttribute("compList", userList);
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

				CompMaster[] userArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCompany",
						CompMaster[].class);
				List<CompMaster> userList = new ArrayList<CompMaster>(Arrays.asList(userArr));

				model.addAttribute("compList", userList);

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

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custId", Integer.parseInt(custId));

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

			System.err.println("hii");
			Date date = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = dateFormat.format(cal.getTime());
			HttpSession session = request.getSession();
			String profileImage = null;
			User userObj = (User) session.getAttribute("userObj");

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				System.err.println("In If ");

				profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				System.err.println("In else ");
				profileImage = request.getParameter("editImg");

			}
			Customer cust = new Customer();

			int companyId = Integer.parseInt(request.getParameter("companyId"));

			String cust_name = request.getParameter("custName");

			String custMobileNo = request.getParameter("custMobileNo");
			String email = request.getParameter("email");
			String dateOfBirth = request.getParameter("dateOfBirth");
			int city = Integer.parseInt(request.getParameter("city"));
			int cust_id = Integer.parseInt(request.getParameter("cust_id"));

			int custGender = Integer.parseInt(request.getParameter("custGender"));
			int ageRange = Integer.parseInt(request.getParameter("ageRange"));

			int languageId = Integer.parseInt(request.getParameter("languageId"));

			System.err.println("CommonUtility.convertToYMD(openDate)" + CommonUtility.convertToYMD(dateOfBirth));
			System.err.println("curDateTime" + curDateTime);

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
			cust.setInsertDttime(curDateTime);
			cust.setUpdtDttime(curDateTime);
			cust.setMakerUserId(userObj.getCompanyId());

			System.err.println(cust.toString());
			Customer res = Constants.getRestTemplate().postForObject(Constants.url + "saveCustomer", cust,
					Customer.class);

			System.err.println(res.toString());
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
	// Descriprion :- Show  Add CustomerAddress 
	@RequestMapping(value = "/showAddCustomerAddress", method = RequestMethod.GET)
	public String showAddCustomerAddress(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			/*
			 * List<ModuleJson> newModuleList = (List<ModuleJson>)
			 * session.getAttribute("newModuleList"); Info view =
			 * AccessControll.checkAccess("showAddCustomer", "showCustomers", "0", "1", "0",
			 * "0", newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * mav = "accessDenied";
			 * 
			 * } else {
			 */

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

			// }
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

		/*	List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showCustomerAddressList", "showCustomerAddressList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
*/
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

			//}
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

			System.err.println("hii");
			Date date = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = dateFormat.format(cal.getTime());
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
			custDet.setInsertDttime(curDateTime);
			custDet.setUpdtDttime(curDateTime);
			custDet.setMakerUserId(userObj.getCompanyId());

			CustomerAddDetail res = Constants.getRestTemplate().postForObject(Constants.url + "saveCustomerDet",
					custDet, CustomerAddDetail.class);

			System.err.println(res.toString());
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

		/*	List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteCustomer", "showCustomers", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {*/

				String base64encodedString = request.getParameter("custDetailId");
				String custDetailId = FormValidation.DecodeKey(base64encodedString);

				String base64encodedString1 = request.getParameter("custId");
				String custId = FormValidation.DecodeKey(base64encodedString1);
				mav = "redirect:/showCustomerAddressList?custId=" + FormValidation.Encrypt(String.valueOf(custId));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custDetId", Integer.parseInt(custDetailId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteCustDetById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			//}
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
			/*
			 * List<ModuleJson> newModuleList = (List<ModuleJson>)
			 * session.getAttribute("newModuleList"); Info view =
			 * AccessControll.checkAccess("showAddCustomer", "showCustomers", "0", "1", "0",
			 * "0", newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * mav = "accessDenied";
			 * 
			 * } else {
			 */

			mav = "masters/addCustAddress";
			model.addAttribute("title", "Edit Customer Address");

			String base64encodedString = request.getParameter("custDetailId");
			String custDetId = FormValidation.DecodeKey(base64encodedString);
			
			
			String base64encodedString1 = request.getParameter("custId");
			String custId = FormValidation.DecodeKey(base64encodedString1);

 			
			
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("custDetId", custDetId);
			CustomerAddDetail custDet = Constants.getRestTemplate().postForObject(Constants.url + "getCustDetById", map,
					CustomerAddDetail.class);
			model.addAttribute("custDet", custDet);
			
			
			model.addAttribute("custDet", custDet);
			 map = new LinkedMultiValueMap<>();
			map.add("custId", custId);
			Customer cust = Constants.getRestTemplate().postForObject(Constants.url + "getCustById", map,
					Customer.class);
			model.addAttribute("cust", cust);

			// }
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
	// Descriprion :-  Check unique Cust mobile
	
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
 			Customer res = Constants.getRestTemplate().postForObject(Constants.url + "getCustByMobNo", map, Customer.class);
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

}
