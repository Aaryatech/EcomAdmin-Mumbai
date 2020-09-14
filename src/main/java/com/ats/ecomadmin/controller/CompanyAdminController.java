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
import org.springframework.web.multipart.MultipartFile;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.CompMaster;
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

			
			System.err.println("CommonUtility.convertToYMD(openDate)"+CommonUtility.convertToYMD(openDate));
			System.err.println("curDateTime"+curDateTime);

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
	// Descriprion :-  CompanyList
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

}
