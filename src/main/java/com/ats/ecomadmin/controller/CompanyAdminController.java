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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.HomeController;
import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.BannerPage;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.City;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.CompanyContactInfo;
import com.ats.ecomadmin.model.Customer;
import com.ats.ecomadmin.model.CustomerAddDetail;
import com.ats.ecomadmin.model.CustomerDetailInfo;
import com.ats.ecomadmin.model.ExportToExcel;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.GetCustomerInfo;
import com.ats.ecomadmin.model.GetRouteList;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.Language;
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

				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				City[] cityArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCities", map,
						City[].class);
				List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));
				model.addAttribute("cityList", cityList);
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
				int sessCompId = (int) session.getAttribute("companyId");

				String base64encodedString = request.getParameter("companyId");
				String companyId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				CompMaster comp = Constants.getRestTemplate().postForObject(Constants.url + "getCompanyByCompanyId",
						map, CompMaster.class);

				model.addAttribute("comp", comp);
				model.addAttribute("title", "Edit Company");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);

				map = new LinkedMultiValueMap<>();
				map.add("compId", sessCompId);
				City[] cityArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCities", map,
						City[].class);
				List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));
				model.addAttribute("cityList", cityList);

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
		Info info = new Info();
		String mav = null;

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showAddCompany", "showCompanys", "0", "1", "0", "0", newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {

			mav = "redirect:/showCompanys";
			try {
				int companyId = Integer.parseInt(request.getParameter("companyId"));

				if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

					profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

					try {
						info = ImageUploadController.saveImgFiles(doc, Constants.imageFileExtensions, profileImage);
					} catch (Exception e) {
					}

				} else {
					profileImage = request.getParameter("editImg");

				}

				if (info.isError()) {
					session.setAttribute("errorMsg", "Invalid Image Formate");

					if (companyId > 0)
						mav = "redirect:/showEditCompany?companyId="
								+ FormValidation.Encrypt(String.valueOf(companyId));
					else
						mav = "redirect:/showAddCompany";

				} else {

					String comp_name = request.getParameter("comp_name");

					String contact_no = request.getParameter("contact_no");
					String email = request.getParameter("email");
					String openDate = request.getParameter("openDate");
					String address = request.getParameter("address");
					int city = Integer.parseInt(request.getParameter("city"));
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
					comp.setCompanyPrefix(companyPrefix.toUpperCase());
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
					comp.setCompOpeningDate(openDate);
					comp.setCompPanNo(panNo);
					comp.setCompState("NA");
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
					comp.setCompanyType(Integer.parseInt(request.getParameter("isParent")));
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
							session.setAttribute("successMsg", "Company Saved Successfully");
						else
							session.setAttribute("successMsg", "Company  Update Successfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Save Company");
					}
				}

			} catch (Exception e) {
				System.out.println("Execption in /insertUom : " + e.getMessage());
				e.printStackTrace();
			}
		}
		int btnVal = Integer.parseInt(request.getParameter("btnType"));

		if (btnVal == 0)
			mav = "redirect:/showCompanys";
		else
			mav = "redirect:/showAddCompany";

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
				User userObj = (User) session.getAttribute("userObj");

				model.addAttribute("compId", userObj.getCompanyId());

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

	List<CompMaster> printCompList = new ArrayList<CompMaster>();
	List<Long> compIds = new ArrayList<Long>();
	@RequestMapping(value = "/getCompanyPrintIds", method = RequestMethod.GET)
	public @ResponseBody List<CompMaster> getCompanyPrintIds(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		try {
			HttpSession session = request.getSession();
			
			int val = Integer.parseInt(request.getParameter("val"));			
			String selctId = request.getParameter("elemntIds");

			selctId = selctId.substring(1, selctId.length() - 1);
			selctId = selctId.replaceAll("\"", "");
			
			int compId = (int) session.getAttribute("companyId");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			CompMaster[] userArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCompany",
					CompMaster[].class);
			printCompList = new ArrayList<CompMaster>(Arrays.asList(userArr));

			compIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			
			for (int i = 0; i < compIds.size(); i++) {
				
				if(compIds.get(i)==1)
				rowData.add("Sr No.");
				
				if(compIds.get(i)==2)
				rowData.add("Company Name");
				
				if(compIds.get(i)==3)
				rowData.add("Prefix");
				
				if(compIds.get(i)==4)
				rowData.add("Opening Date");
				
				if(compIds.get(i)==5)
				rowData.add("Contact");
				
				if(compIds.get(i)==6)
				rowData.add("Email");
				
				if(compIds.get(i)==7)
				rowData.add("Address");
				
				if(compIds.get(i)==8)
				rowData.add("City");
				
				if(compIds.get(i)==9)
					rowData.add("Website");
				
				if(compIds.get(i)==10)
					rowData.add("Is Parent");
				
				if(compIds.get(i)==11)
					rowData.add("GST Type");
				
				if(compIds.get(i)==12)
					rowData.add("GST No.");
				
				if(compIds.get(i)==13)
					rowData.add("GST Code");
				
				if(compIds.get(i)==14)
					rowData.add("Bank");
				
				if(compIds.get(i)==15)
					rowData.add("Branch");
				
				if(compIds.get(i)==16)
					rowData.add("IFSC Code");
				
				if(compIds.get(i)==17)
					rowData.add("A/c No.");
				
				if(compIds.get(i)==18)
					rowData.add("CIN No.");
				
				if(compIds.get(i)==19)
					rowData.add("FDA No.");
				
				if(compIds.get(i)==20)
					rowData.add("FDA Declaration");
				
				if(compIds.get(i)==21)
					rowData.add("PAN");
				
				if(compIds.get(i)==22)
					rowData.add("Payment Gateway Applicable");				
				
				if(compIds.get(i)==23)
					rowData.add("Payment Link");
			}
			expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int srno = 1;
			for (int i = 0; i < printCompList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				for (int j = 0; j < compIds.size(); j++) {
				
					if(compIds.get(j)==1)
						rowData.add(" "+srno);
					
					if(compIds.get(j)==2)
					rowData.add(" " + printCompList.get(i).getCompanyName());
					
					if(compIds.get(j)==3)
					rowData.add(" " + printCompList.get(i).getCompanyPrefix());
					
					if(compIds.get(j)==4)
					rowData.add(" " + printCompList.get(i).getCompOpeningDate());
					
					if(compIds.get(j)==5)
					rowData.add(" " + printCompList.get(i).getCompContactNo());
					
					if(compIds.get(j)==6)
					rowData.add(" " + printCompList.get(i).getCompEmailAddress());
					
					if(compIds.get(j)==7)
					rowData.add(" " + printCompList.get(i).getCompAddress());
				
					if(compIds.get(j)==8)
					rowData.add(" " + printCompList.get(i).getExVar4());
					
					if(compIds.get(j)==9)
					rowData.add(printCompList.get(i).getCompWebsite());
					
					if(compIds.get(j)==10)
						rowData.add(printCompList.get(i).getCompanyType() == 1 ? "Yes" : "No");
					
					if(compIds.get(j)==11)
						rowData.add(printCompList.get(i).getCompGstType() == 1 ? "Regular" : printCompList.get(i).getCompGstType() == 2 ? "Composite" : "Non-Register");
					
					if(compIds.get(j)==12)
						rowData.add(printCompList.get(i).getCompGstNo());
					
					if(compIds.get(j)==13)
						rowData.add(printCompList.get(i).getCompStateGstCode());
					
					if(compIds.get(j)==14)
						rowData.add(printCompList.get(i).getCompBankName());
					
					if(compIds.get(j)==15)
						rowData.add(printCompList.get(i).getCompBankBranchName());
					
					if(compIds.get(j)==16)
						rowData.add(printCompList.get(i).getCompBankIfsc());
					
					if(compIds.get(j)==17)
						rowData.add(printCompList.get(i).getCompBankAccNo());
					
					if(compIds.get(j)==18)
						rowData.add(printCompList.get(i).getCompCinNo());
					
					if(compIds.get(j)==19)
						rowData.add(printCompList.get(i).getCompFdaNo());
					
					if(compIds.get(j)==20)
						rowData.add(printCompList.get(i).getCompFdaDeclarText());
					
					if(compIds.get(j)==21)
						rowData.add(printCompList.get(i).getCompPanNo());
					
					if(compIds.get(j)==22)
						rowData.add(printCompList.get(i).getPaymentGatewayApplicable()==1 ? "Yes" : "No");
					
					if(compIds.get(j)==23)
						rowData.add(printCompList.get(i).getPaymentGatewayLink());
				}
				srno = srno + 1;
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "Company List");
			session.setAttribute("reportNameNew", "Company List");
			session.setAttribute("searchByNew", "All Company");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "Company Excel");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return printCompList;
	}
	
@RequestMapping(value = "pdf/getCompanyListPdf/{compId}/{selctId}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getCompanyListPdf(HttpServletRequest request,
			HttpServletResponse response,@PathVariable int compId, @PathVariable String selctId, @PathVariable int showHead) {
		ModelAndView model = new ModelAndView("pdfs/companyListPdf");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			CompMaster[] userArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCompany",
					CompMaster[].class);
			printCompList = new ArrayList<CompMaster>(Arrays.asList(userArr));

			compIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
				model.addObject("printCompList", printCompList);
				model.addObject("compIds", compIds);
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

	// Created By :- Mahendra Singh
	// Created On :- 24-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Check Company Unique Prefix
	@RequestMapping(value = "/chkUniqCompPrefix", method = RequestMethod.GET)
	@ResponseBody
	public Info chkUniqPrefix(HttpServletRequest request, HttpServletResponse response, Model model) {

		Info res = new Info();

		try {
			String prefix = request.getParameter("prefix");
			int companyId = Integer.parseInt(request.getParameter("companyId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("prefix", prefix);
			map.add("compId", companyId);

			CompMaster cmp = Constants.getRestTemplate().postForObject(Constants.url + "getCompByPrefix", map,
					CompMaster.class);
			if (cmp != null) {
				res.setError(false);
				res.setMsg("Prefix Found");
			} else {
				res.setError(true);
				res.setMsg("Prefix Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /chkUniqPrefix : " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	// Created By :- Mahendra Singh
	// Created On :- 26-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Check Company Unique Contact No
	@RequestMapping(value = "/getCompanyInfo", method = RequestMethod.GET)
	@ResponseBody
	public Info getCompanyInfo(HttpServletRequest request, HttpServletResponse response, Model model) {

		Info res = new Info();

		try {
			String mobNo = request.getParameter("mobNo");
			int compId = Integer.parseInt(request.getParameter("compId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("mobNo", mobNo);
			map.add("compId", compId);

			CompMaster cmp = Constants.getRestTemplate().postForObject(Constants.url + "getCompByMobileNo", map,
					CompMaster.class);
			if (cmp != null) {
				res.setError(false);
				res.setMsg("Contact No. Found");
			} else {
				res.setError(true);
				res.setMsg("Contact No. Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /chkUniqPrefix : " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	// Created By :- Mahendra Singh
	// Created On :- 26-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Check Company Unique Email Id
	@RequestMapping(value = "/getCompInfoByEmail", method = RequestMethod.GET)
	@ResponseBody
	public Info getCompInfoByEmail(HttpServletRequest request, HttpServletResponse response, Model model) {

		Info res = new Info();

		try {
			String email = request.getParameter("email");
			int compId = Integer.parseInt(request.getParameter("compId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("email", email);
			map.add("compId", compId);

			CompMaster cmp = Constants.getRestTemplate().postForObject(Constants.url + "getCompByEmailId", map,
					CompMaster.class);
			if (cmp != null) {
				res.setError(false);
				res.setMsg("Email Found");
			} else {
				res.setError(true);
				res.setMsg("Email Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /chkUniqPrefix : " + e.getMessage());
			e.printStackTrace();
		}
		return res;
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
				model.addAttribute("isEdit", 0);

				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				Language[] langArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllLanguages", map,
						Language[].class);
				List<Language> langList = new ArrayList<Language>(Arrays.asList(langArr));
				model.addAttribute("langList", langList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				City[] cityArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCities", map,
						City[].class);
				List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));

				model.addAttribute("cityList", cityList);

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
	List<City> cityList;
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

				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				Language[] langArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllLanguages", map,
						Language[].class);
				List<Language> langList = new ArrayList<Language>(Arrays.asList(langArr));
				model.addAttribute("langList", langList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				City[] cityArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCities", map,
						City[].class);
				cityList = new ArrayList<City>(Arrays.asList(cityArr));

				model.addAttribute("cityList", cityList);

				map = new LinkedMultiValueMap<>();
				map.add("custId", custId);
				Customer cust = Constants.getRestTemplate().postForObject(Constants.url + "getCustById", map,
						Customer.class);

				model.addAttribute("cust", cust);

				CustomerAddDetail custDet = new CustomerAddDetail();
				model.addAttribute("custDet", custDet);

				// Customer Address List
				// map = new LinkedMultiValueMap<>();
				// map.add("custId", custId);
				CustomerAddDetail[] userArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllCustomerDetailByCustId", map, CustomerAddDetail[].class);
				List<CustomerAddDetail> userList = new ArrayList<CustomerAddDetail>(Arrays.asList(userArr));

//				for (int i = 0; i < userList.size(); i++) {
//
//					userList.get(i)
//							.setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getCustDetailId())));
//					userList.get(i).setExVar2(FormValidation.Encrypt(String.valueOf(userList.get(i).getCustId())));
//				}
				model.addAttribute("custAddList", userList);
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

				model.addAttribute("isEdit", 1);
			}
		} catch (Exception e) {
			System.out.println("Execption in /Customer : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getCityListAjax", method = RequestMethod.GET)
	@ResponseBody
	public List<City> getCityListAjax(HttpServletRequest request, HttpServletResponse response, Model model) {
		return cityList;
	}
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :-Customer List
	List<GetCustomerInfo> custPrintList = new ArrayList<GetCustomerInfo>();
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

				int compId = (int) session.getAttribute("companyId");
				model.addAttribute("compId", compId);
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();				

				GetCustomerInfo[] userArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllCustomerDetailInfo", GetCustomerInfo[].class);
				List<GetCustomerInfo> userList = new ArrayList<GetCustomerInfo>(Arrays.asList(userArr));
					
				
				for (int i = 0; i < userList.size(); i++) {

					userList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getCustId())));
				}
				model.addAttribute("custList", userList);
				model.addAttribute("title", "Customer List");

				custPrintList = userList; 
				
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
	
	
	List<Long> custIds = new ArrayList<Long>();
	@RequestMapping(value = "/getCustomerPrintIds", method = RequestMethod.GET)
	public @ResponseBody List<GetCustomerInfo> getElementIds(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		try {
			HttpSession session = request.getSession();
			
			int val = Integer.parseInt(request.getParameter("val"));			
			String selctId = request.getParameter("elemntIds");

			selctId = selctId.substring(1, selctId.length() - 1);
			selctId = selctId.replaceAll("\"", "");
		
			
			int compId = (int) session.getAttribute("companyId");
			
			custIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
		
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			
			for (int i = 0; i < custIds.size(); i++) {
				
				if(custIds.get(i)==1)
					rowData.add("Sr No");
				
				if(custIds.get(i)==2)
				rowData.add("Customer Name");
				
				if(custIds.get(i)==3)
				rowData.add("Moile No.");
				
				if(custIds.get(i)==4)
				rowData.add("Company");
				
				if(custIds.get(i)==5)
				rowData.add("City");
				
				if(custIds.get(i)==6)
				rowData.add("Date Of Birth");
				
			}
			expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int srno = 1;
			for (int i = 0; i < custPrintList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				for (int j = 0; j < custIds.size(); j++) {
				
					if(custIds.get(j)==1)
						rowData.add(" "+srno);
					
					if(custIds.get(j)==2)
					rowData.add(" " + custPrintList.get(i).getCustName());
					
					if(custIds.get(j)==3)
					rowData.add(" " + custPrintList.get(i).getCustMobileNo());
					
					if(custIds.get(j)==4)
					rowData.add(" " + custPrintList.get(i).getCompanyName());
					
					if(custIds.get(j)==5)
					rowData.add(" " + custPrintList.get(i).getCityName());
					
					if(custIds.get(j)==6)
					rowData.add(" " + custPrintList.get(i).getDateOfBirth());
				
				}
				srno = srno + 1;
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "CustomerList");
			session.setAttribute("reportNameNew", "Customer List");
			session.setAttribute("searchByNew", " NA");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "Customer List Excel");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return custPrintList;
	}
	
	@RequestMapping(value = "pdf/getCustomerListPdf/{compId}/{selctId}/{showHead}", method = RequestMethod.GET)
	public String getCustomerListPdf(HttpServletRequest request,
			HttpServletResponse response, Model model,@PathVariable int compId, @PathVariable String selctId, @PathVariable int showHead) {
		try {
			GetCustomerInfo[] userArr = Constants.getRestTemplate()
					.getForObject(Constants.url + "getAllCustomerDetailInfo", GetCustomerInfo[].class);
			List<GetCustomerInfo> custPrintList = new ArrayList<GetCustomerInfo>(Arrays.asList(userArr));
				
			custIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
				model.addAttribute("custPrintList", custPrintList);
				CompanyContactInfo dtl = HomeController.getCompName(compId);
				if(showHead==1) {
					model.addAttribute("compName", dtl.getCompanyName());
					model.addAttribute("compAddress", dtl.getCompAddress());
					model.addAttribute("compContact", dtl.getCompContactNo());	
				}
				
				model.addAttribute("custIds", custIds);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "pdfs/custListPdf";
		
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
		String mav = new String();
		try {

			Date date = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = CommonUtility.getCurrentYMDDateTime();
			HttpSession session = request.getSession();
			String profileImage = null;
			User userObj = (User) session.getAttribute("userObj");
			int companyId = (int) session.getAttribute("companyId");
			Info info = new Info();

			int cust_id = Integer.parseInt(request.getParameter("cust_id"));

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

				try {
					// new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
					info = ImageUploadController.saveImgFiles(doc, Constants.imageFileExtensions, profileImage);
				} catch (Exception e) {
				}

			} else {
				profileImage = request.getParameter("editImg");

			}
			if (info.isError()) {
				session.setAttribute("errorMsg", "Invalid Image Formate");
				if (cust_id > 0) {
					mav = "redirect:/showEditCustomer?custId=" + FormValidation.Encrypt(String.valueOf(cust_id));
				} else {
					mav = "redirect:/showAddCustomer";
				}

			} else {
				Customer cust = new Customer();

				// int companyId = Integer.parseInt(request.getParameter("companyId"));

				String cust_name = request.getParameter("custName");

				String custMobileNo = request.getParameter("custMobileNo");
				String email = request.getParameter("email");
				String dateOfBirth = request.getParameter("dateOfBirth");
				int city = Integer.parseInt(request.getParameter("city"));

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
				cust.setDateOfBirth(dateOfBirth);
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
						session.setAttribute("successMsg", "Customer Saved Successfully");
					else
						session.setAttribute("successMsg", "Customer  Update Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Customer");
				}
				int btnVal = Integer.parseInt(request.getParameter("btnType"));

				if (btnVal == 0)
					mav = "redirect:/showCustomers";
				else
					mav = "redirect:/showAddCustomer";
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;

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

			cust_id = Integer.parseInt(request.getParameter("cust_id"));
			int custDetailId = Integer.parseInt(request.getParameter("custDetailId"));

			CustomerAddDetail custDet = new CustomerAddDetail();

			custDet.setAddress(request.getParameter("address"));
			custDet.setAreaId(0);
			custDet.setCaption(request.getParameter("caption"));
			custDet.setCityId(Integer.parseInt(request.getParameter("cityId")));
			custDet.setCustDetailId(custDetailId);
			custDet.setCustId(cust_id);
			custDet.setLandmark(request.getParameter("landmark"));
			custDet.setLatitude(request.getParameter("latitude"));
			custDet.setLongitude(request.getParameter("longitude"));
			custDet.setIsActive(1);
			custDet.setDelStatus(1);
			custDet.setExInt1(0);
			custDet.setExInt2(0);
			custDet.setExInt3(0);
			custDet.setExVar1(request.getParameter("address2"));
			custDet.setExVar2(request.getParameter("address3"));
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
					session.setAttribute("successMsg", "Customer Address Saved Successfully");
				else
					session.setAttribute("successMsg", "Customer Address Update Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Customer Address");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showEditCustomer?custId=" + FormValidation.Encrypt(String.valueOf(cust_id));
		// return "redirect:/showCustomerAddressList?custId=" +
		// FormValidation.Encrypt(String.valueOf(cust_id));

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

				int custDetailId = Integer.parseInt(request.getParameter("custDetailId"));

				int custId = Integer.parseInt(request.getParameter("custId"));

				// mav = "redirect:/showCustomerAddressList?custId=" +
				// FormValidation.Encrypt(String.valueOf(custId));
				mav = "redirect:/showEditCustomer?custId=" + FormValidation.Encrypt(String.valueOf(custId));

				User userObj = (User) session.getAttribute("userObj");
				String dateTime = CommonUtility.getCurrentYMDDateTime();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("custDetId", custDetailId);
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

	// Created By :- Mahendra Singh
	// Created On :- 23-010-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Get Customer And Address Details
	@RequestMapping(value = "/getCustAdrsDtl", method = RequestMethod.GET)
	@ResponseBody
	public CustomerDetailInfo getCustAdrsDtl(HttpServletRequest request, HttpServletResponse response) {

		CustomerDetailInfo custDtl = new CustomerDetailInfo();
		try {

			int custDetId = Integer.parseInt(request.getParameter("custDetailId"));
			int custId = Integer.parseInt(request.getParameter("custId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("custDetId", custDetId);
			CustomerAddDetail custDet = Constants.getRestTemplate().postForObject(Constants.url + "getCustDetById", map,
					CustomerAddDetail.class);
			custDtl.setCustAdd(custDet);
			// model.addAttribute("custDet", custDet);

			map = new LinkedMultiValueMap<>();
			map.add("custId", custId);
			Customer cust = Constants.getRestTemplate().postForObject(Constants.url + "getCustById", map,
					Customer.class);
			custDtl.setCust(cust);

		} catch (Exception e) {
			System.out.println("Execption in /validateUnqFrMobNo : " + e.getMessage());
			e.printStackTrace();
		}
		return custDtl;
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

	// Created By :- Mahendra Singh
	// Created On :- 26-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Check unique Cust Email

	@RequestMapping(value = "/getCustInfoByEmail", method = RequestMethod.GET)
	@ResponseBody
	public Info getCustInfoByEmail(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			int custId = 0;
			try {
				custId = Integer.parseInt(request.getParameter("custId"));
			} catch (Exception e) {
				custId = 0;
				e.printStackTrace();
			}
			String email = request.getParameter("email");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("email", email);
			map.add("custId", custId);
			Customer res = Constants.getRestTemplate().postForObject(Constants.url + "getCustByEmailId", map,
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

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);
				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				model.addAttribute("catList", catList);
			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getSubCatCodeByCatId", method = RequestMethod.GET)
	@ResponseBody
	public Info getSubCatCodeByCatId(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			HttpSession session = request.getSession();

			int companyId = (int) session.getAttribute("companyId");
			int cateId = Integer.parseInt(request.getParameter("catId"));

			map = new LinkedMultiValueMap<>();
			map.add("cateId", cateId);

			String catePrefix = Constants.getRestTemplate().postForObject(Constants.url + "getCatePrefixByCateId", map,
					String.class);

			map = new LinkedMultiValueMap<>();
			map.add("cateId", cateId);
			map.add("compId", companyId);

			int subCatCount = Constants.getRestTemplate().postForObject(Constants.url + "getSubCateIdCnt", map,
					Integer.class);

			String subCatCode = catePrefix + "0" + (subCatCount + 1);

			info.setMsg(subCatCode);

		} catch (Exception e) {
			System.out.println("Execption in /getSubCatCodeByCatId : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = "/chkUnqSubCatPrfx", method = RequestMethod.GET)
	@ResponseBody
	public Info chkUnqSubCatPrfx(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			HttpSession session = request.getSession();

			int companyId = (int) session.getAttribute("companyId");
			String prefix = request.getParameter("prefix");
			int subCateId = Integer.parseInt(request.getParameter("subCatId"));

			map = new LinkedMultiValueMap<>();
			map.add("prefix", prefix);
			map.add("compId", companyId);
			map.add("subCateId", subCateId);

			info = Constants.getRestTemplate().postForObject(Constants.url + "unqSubCatePrefix", map, Info.class);

		} catch (Exception e) {
			System.out.println("Execption in /chkUnqSubCatPrfx : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
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
	List<SubCategory> subCatList = new ArrayList<SubCategory>();
	@RequestMapping(value = "/showSubCatList", method = RequestMethod.GET)
	public String showSubCatList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showSubCatList", "showSubCatList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/subCatList";
				int compId = (int) session.getAttribute("companyId");
				model.addAttribute("compId", compId);

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
				model.addAttribute("imgPath", Constants.showDocSaveUrl);
				
				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

					rowData.add("Sr No.");				
					rowData.add("Sub Category");
					rowData.add("Category");
					rowData.add("Code");
					rowData.add("Prefix");
				
				expoExcel.setRowData(rowData);
				
				exportToExcelList.add(expoExcel);
				int srno = 1;
				for (int i = 0; i < subCatList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					
					rowData.add(" "+srno);					
					rowData.add(" " + subCatList.get(i).getSubCatName());
					rowData.add(" " + subCatList.get(i).getExVar4());		
					rowData.add(" " + subCatList.get(i).getSubCatCode());			
					rowData.add(" " + subCatList.get(i).getSubCatPrefix());	
					
					srno = srno + 1;
					
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}
				session.setAttribute("exportExcelListNew", exportToExcelList);
				session.setAttribute("excelNameNew", "SubCategory");
				session.setAttribute("reportNameNew", "Sub Category List");
				session.setAttribute("searchByNew", " NA");
				session.setAttribute("mergeUpto1", "$A$1:$L$1");
				session.setAttribute("mergeUpto2", "$A$2:$L$2");

				session.setAttribute("exportExcelList", exportToExcelList);
				session.setAttribute("excelName", "Sub Category Excel");
				
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
			System.out.println("Execption in /showSubCatList : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "pdf/getSubCategoryPdf/{compId}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getSubCategoryPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable int compId, @PathVariable int showHead) {
		ModelAndView model = new ModelAndView("pdfs/subCategoryPdf");
		try {
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			SubCategory[] subCatArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllActiveSubCategories", map, SubCategory[].class);
			subCatList = new ArrayList<SubCategory>(Arrays.asList(subCatArray));
			
			model.addObject("subCatList", subCatList);
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
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- insert sub Cat
	@RequestMapping(value = "/insertNewSubCat", method = RequestMethod.POST)
	public String insertNewSubCat(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();
			Date date = new Date();
			int companyId = (int) session.getAttribute("companyId");
			Info info = new Info();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

			String profileImage = new String();
			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

				try {
					// new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
					info = ImageUploadController.saveImgFiles(doc, Constants.imageFileExtensions, profileImage);
				} catch (Exception e) {
				}

			} else {
				profileImage = request.getParameter("editImg");
			}

			int subCatId = Integer.parseInt(request.getParameter("subCatId"));
			if (info.isError()) {
				session.setAttribute("errorMsg", "Invalid Image Formate");
				if (subCatId > 0)
					mav = "redirect:/showEditSubCat?subCatId=" + FormValidation.Encrypt(String.valueOf(subCatId));
				else
					mav = "redirect:/showAddSubCat";
			} else {

				String subCatName = request.getParameter("subCatName");
				String subCatDesc = request.getParameter("subCatDesc");
				String subCatPrefix = request.getParameter("subCatPrefix");
				String subCatCode = request.getParameter("subCatCode");

				int catId = Integer.parseInt(request.getParameter("catId"));
				int allowToCopy = Integer.parseInt(request.getParameter("allowToCopy"));

				int sortNo = Integer.parseInt(request.getParameter("sortNo"));

				SubCategory subcat = new SubCategory();
				subcat.setAllowToCopy(allowToCopy);
				subcat.setCatId(catId);
				subcat.setSubCatId(subCatId);
				subcat.setCompanyId(companyId);
				subcat.setImageName(profileImage);
				subcat.setIsParent(0);
				subcat.setSubCatPrefix(subCatPrefix.toUpperCase());
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
						session.setAttribute("successMsg", "Sub Category Saved Successfully");
					else
						session.setAttribute("successMsg", "Sub Category Update Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save SubCategory");
				}

				int btnVal = Integer.parseInt(request.getParameter("btnType"));
				if (btnVal == 0)
					mav = "redirect:/showSubCatList";
				else
					mav = "redirect:/showAddSubCat";
			}
		} catch (Exception e) {
			System.out.println("Execption in /insertNewSubCat : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
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

				String base64encodedString = request.getParameter("subCatId");
				String subCatId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("subCatId", Integer.parseInt(subCatId));

				int result = Constants.getRestTemplate().postForObject(Constants.url + "getProdIdCntBySubCatId", map,
						Integer.class);
				if (result > 0) {
					session.setAttribute("errorMsg",
							"Failed to Delete SubCategory, Products are assigned to this SubCategory");
					mav = "redirect:/showSubCatList";
				} else {
					mav = "redirect:/showSubCatList";

					map.add("subCatId", Integer.parseInt(subCatId));
					Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteSubCatById", map,
							Info.class);

					if (!res.isError()) {
						session.setAttribute("successMsg", res.getMsg());
					} else {
						session.setAttribute("errorMsg", res.getMsg());
					}
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

		String mav = new String();
		try {

			Date date = new Date();
			HttpSession session = request.getSession();

			Info info = new Info();

			int companyId = (int) session.getAttribute("companyId");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String curDateTime = CommonUtility.getCurrentYMDDateTime();
			User userObj = (User) session.getAttribute("userObj");
			String profileImage = new String();

			int bannerId = Integer.parseInt(request.getParameter("bannerId"));

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				profileImage = dateFormat.format(date) + "_" + doc.getOriginalFilename();

				try {
					info = ImageUploadController.saveImgFiles(doc, Constants.imageFileExtensions, profileImage);
				} catch (Exception e) {
				}

			} else {
				profileImage = request.getParameter("editImg");

			}

			if (info.isError()) {
				session.setAttribute("errorMsg", "Invalid Image Formate");
				if (bannerId > 0)
					mav = "redirect:/showEditBanner?bannerId=" + FormValidation.Encrypt(String.valueOf(bannerId));
				else
					mav = "redirect:/showAddBanner";
			} else {

				String bannerEventName = request.getParameter("bannerEventName");
				String captionOnproductPage = request.getParameter("captionOnproductPage");

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
				banner.setIsActive(Integer.parseInt(request.getParameter("isActive")));
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
						session.setAttribute("successMsg", "Banner Saved Successfully");
					else
						session.setAttribute("successMsg", "Banner Update Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Banner");
				}

				int btnVal = Integer.parseInt(request.getParameter("btnType"));
				if (btnVal == 0)
					mav = "redirect:/showBannerList";
				else
					mav = "redirect:/showAddBanner";
			}
		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
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
	// Descriprion :- Banner List
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
				model.addAttribute("compId", compId);
				
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
	
	List<BannerPage> bannerPrintList = new ArrayList<BannerPage>();
	List<Long> bannerIds = new ArrayList<Long>();
	@RequestMapping(value = "/getBannerPrintIds", method = RequestMethod.GET)
	public @ResponseBody List<BannerPage> getBannerPrintIds(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		try {
			HttpSession session = request.getSession();
			
			int val = Integer.parseInt(request.getParameter("val"));			
			String selctId = request.getParameter("elemntIds");

			selctId = selctId.substring(1, selctId.length() - 1);
			selctId = selctId.replaceAll("\"", "");
		
			
			int compId = (int) session.getAttribute("companyId");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			BannerPage[] bnrArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllBannerDtl", map, BannerPage[].class);
			bannerPrintList = new ArrayList<BannerPage>(Arrays.asList(bnrArray));
			
			
			bannerIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			
			for (int i = 0; i < bannerIds.size(); i++) {
				
				if(bannerIds.get(i)==1)
					rowData.add("Sr No");
				
				if(bannerIds.get(i)==2)
				rowData.add("Event Name");
				
				if(bannerIds.get(i)==3)
				rowData.add("Sort No.");
				
				if(bannerIds.get(i)==4)
				rowData.add("Caption on Product Page");
				
				if(bannerIds.get(i)==5)
				rowData.add("Status");
				
				if(bannerIds.get(i)==6)
				rowData.add("Franchise");
				
				if(bannerIds.get(i)==7)
				rowData.add("Tags");
			}
			expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int srno = 1;
			for (int i = 0; i < bannerPrintList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				for (int j = 0; j < bannerIds.size(); j++) {
				
					if(bannerIds.get(j)==1)
						rowData.add(" "+srno);
					
					if(bannerIds.get(j)==2)
					rowData.add(" " + bannerPrintList.get(i).getBannerEventName());
					
					if(bannerIds.get(j)==3)
					rowData.add(" " + bannerPrintList.get(i).getSortNo());
					
					if(bannerIds.get(j)==4)
					rowData.add(" " + bannerPrintList.get(i).getCaptionOnproductPage());
					
					if(bannerIds.get(j)==5)
						rowData.add(bannerPrintList.get(i).getIsActive() == 1 ? "Active"  : "In-Active");
					
					if(bannerIds.get(j)==6)
					rowData.add(" " + bannerPrintList.get(i).getExVar2());
				
					if(bannerIds.get(j)==7)
					rowData.add(" " + bannerPrintList.get(i).getExVar3());
					
				}
				srno = srno + 1;
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
			}
			
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "BannerList");
			session.setAttribute("reportNameNew", "Banner List");
			session.setAttribute("searchByNew", "All");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "Banner List Excel");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bannerPrintList;
	}
	
	@RequestMapping(value = "pdf/getBannerListPdf/{compId}/{selctId}/{showHead}", method = RequestMethod.GET)
	public String getProductListPdf(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable int compId, @PathVariable String selctId ,@PathVariable int showHead) {
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			BannerPage[] bnrArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllBannerDtl", map, BannerPage[].class);
			bannerPrintList = new ArrayList<BannerPage>(Arrays.asList(bnrArray));
			
			
			bannerIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
				model.addAttribute("bannerPrintList", bannerPrintList);

				CompanyContactInfo dtl = HomeController.getCompName(compId);
			if(showHead==1) {
				model.addAttribute("compName", dtl.getCompanyName());
				model.addAttribute("compAddress", dtl.getCompAddress());
				model.addAttribute("compContact", dtl.getCompContactNo());	
			}
				model.addAttribute("bannerIds", bannerIds);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "pdfs/bannerListPdf";
		
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
				model.addAttribute("isEdit", 0);

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

				/*
				 * Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url +
				 * "getAllFranchises", map, Franchise[].class);
				 */
				map.add("frIds", 0);
				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getFrListToAddInRoute", map,
						Franchise[].class);
				
				//
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
				map.add("frIds", route.getFrIds());
				/*
				 * Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url +
				 * "getAllFranchises", map, Franchise[].class);
				 */
				
				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getFrListToAddInRoute", map,
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
	// Descriprion :- Route List

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

				List<GetRouteList> routeList = new ArrayList<GetRouteList>();

				int companyId = (int) session.getAttribute("companyId");
				model.addAttribute("compId", companyId);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				GetRouteList[] routeArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllRouteByCompId", map, GetRouteList[].class);
				routeList = new ArrayList<GetRouteList>(Arrays.asList(routeArr));

				for (int i = 0; i < routeList.size(); i++) {

					routeList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(routeList.get(i).getRouteId())));
				}
				model.addAttribute("routeList", routeList);
			}

		} catch (Exception e) {
			System.out.println("Execption in /showRouteList : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	
	

@RequestMapping(value="/getRoutListAjax",method=RequestMethod.GET)
	public @ResponseBody List<GetRouteList> getRoutListAjax(HttpServletRequest request,HttpServletResponse response){
		System.err.println("In /getRoutListAjax");
		List<GetRouteList> routeList=new ArrayList<>();
		HttpSession session=request.getSession();
		try {
			int companyId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);
			GetRouteList[] routeArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllRouteByCompId", map, GetRouteList[].class);
			routeList = new ArrayList<GetRouteList>(Arrays.asList(routeArr));

			for (int i = 0; i < routeList.size(); i++) {

				routeList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(routeList.get(i).getRouteId())));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered In /getRoutListAjax");
			e.printStackTrace();
		}
		return routeList;
	}
	
	List<GetRouteList> routeListPrint = new ArrayList<GetRouteList>();
	List<Long> routeIds = new ArrayList<Long>();
	@RequestMapping(value = "/getRouteIds", method = RequestMethod.GET)
	public @ResponseBody List<GetRouteList> getRouteIds(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		try {
			HttpSession session = request.getSession();
			
			int val = Integer.parseInt(request.getParameter("val"));			
			String selctId = request.getParameter("elemntIds");

			selctId = selctId.substring(1, selctId.length() - 1);
			selctId = selctId.replaceAll("\"", "");
		
			
			int compId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			GetRouteList[] routeArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllRouteByCompId", map, GetRouteList[].class);
			routeListPrint = new ArrayList<GetRouteList>(Arrays.asList(routeArr));
			
			routeIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			
			for (int i = 0; i < routeIds.size(); i++) {		
				
				if(routeIds.get(i)==1)
				rowData.add("Sr No.");
				
				if(routeIds.get(i)==2)
				rowData.add("Name");
				
				if(routeIds.get(i)==3)
				rowData.add("Code");
				
				if(routeIds.get(i)==4)
				rowData.add("Type");
				
				if(routeIds.get(i)==5)
				rowData.add("Delivery");
				
				if(routeIds.get(i)==6)
				rowData.add("Km.");
				
				if(routeIds.get(i)==7)
				rowData.add("Franchise");
				
				if(routeIds.get(i)==8)
				rowData.add("Sort No.");
				
			}
			expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int srno = 1;
			for (int i = 0; i < routeListPrint.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				for (int j = 0; j < routeIds.size(); j++) {
				
					if(routeIds.get(j)==1)
						rowData.add(" "+srno);
					
					if(routeIds.get(j)==2)
						rowData.add(" " + routeListPrint.get(i).getRouteName());
					
					if(routeIds.get(j)==3)
					rowData.add(" " + routeListPrint.get(i).getRouteCode());
					
					if(routeIds.get(j)==4)
					rowData.add(" " + routeListPrint.get(i).getRouteTypeName());
					
					if(routeIds.get(j)==5)
					rowData.add(" " + routeListPrint.get(i).getDeliveryName());
					
					if(routeIds.get(j)==6)
					rowData.add(" " + routeListPrint.get(i).getRouteKm());
				
					if(routeIds.get(j)==7)
					rowData.add(" " + routeListPrint.get(i).getFrName());
					
					if(routeIds.get(j)==8)
					rowData.add(" " + routeListPrint.get(i).getSortNo());
				}
				srno = srno + 1;
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "RouteList");
			session.setAttribute("reportNameNew", "Route List");
			session.setAttribute("searchByNew", " NA");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "Route List Excel");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return routeListPrint;
	}
	
	@RequestMapping(value = "pdf/getRouteListPdf/{compId}/{selctId}/{showHead}", method = RequestMethod.GET)
	public String getRouteListPdf(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable int compId,@PathVariable String selctId,
			@PathVariable int showHead) {
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			GetRouteList[] routeArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllRouteByCompId", map, GetRouteList[].class);
			routeListPrint = new ArrayList<GetRouteList>(Arrays.asList(routeArr));
			
			routeIds = new ArrayList<Long>();
			routeIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());			
		
				model.addAttribute("routeListPrint", routeListPrint);
				model.addAttribute("routeIds", routeIds);
				
				CompanyContactInfo dtl = HomeController.getCompName(compId);
				if(showHead==1) {
					model.addAttribute("compName", dtl.getCompanyName());
					model.addAttribute("compAddress", dtl.getCompAddress());
					model.addAttribute("compContact", dtl.getCompContactNo());	
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "pdfs/routeListPdf";
		
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
					session.setAttribute("successMsg", "Route Saved Successfully");
				else
					session.setAttribute("successMsg", "Route  Update Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Route");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}

		int btnVal = Integer.parseInt(request.getParameter("btnType"));

		if (btnVal == 0)
			return "redirect:/showRouteList";
		else
			return "redirect:/showAddRoute";

	}

	@RequestMapping(value = "/chkUnqRouteCode", method = RequestMethod.GET)
	@ResponseBody
	public Info chkUnqRouteCode(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			String code = request.getParameter("code");
			int routeId = Integer.parseInt(request.getParameter("routeId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("code", code);
			map.add("routeId", routeId);

			Route routeRes = Constants.getRestTemplate().postForObject(Constants.url + "getRouteDtlByCode", map,
					Route.class);

			if (routeRes != null) {
				info.setError(false);
				info.setMsg("Route Found");
			} else {
				info.setError(true);
				info.setMsg("Route Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /chkUnqRouteCode : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- showRouteTypeList
	List<RouteType> routeList = new ArrayList<RouteType>();
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
				
				model.addAttribute("compId", companyId);

				RouteType[] routeArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllRouteType",
						RouteType[].class);
				routeList = new ArrayList<RouteType>(Arrays.asList(routeArr));

				for (int i = 0; i < routeList.size(); i++) {

					routeList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(routeList.get(i).getRouteTypeId())));
				}
				model.addAttribute("routeTypeList", routeList);
				
				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr No.");													
				rowData.add("Name");		
				rowData.add("Km Range");	
				rowData.add("Sort No.");
				expoExcel.setRowData(rowData);
				
				exportToExcelList.add(expoExcel);
				int srno = 1;
				for (int i = 0; i < routeList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					
					rowData.add(" "+srno);
					rowData.add(" " + routeList.get(i).getRouteTypeName());	
					rowData.add(" " + routeList.get(i).getRouteRangeInKm());						
					rowData.add(" " + routeList.get(i).getSortNo());					
					srno = srno + 1;
					
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}
				session.setAttribute("exportExcelListNew", exportToExcelList);
				session.setAttribute("excelNameNew", "RouteTypeList");
				session.setAttribute("reportNameNew", "Route Type List");
				session.setAttribute("searchByNew", " NA");
				session.setAttribute("mergeUpto1", "$A$1:$L$1");
				session.setAttribute("mergeUpto2", "$A$2:$L$2");

				session.setAttribute("exportExcelList", exportToExcelList);
				session.setAttribute("excelName", "Route Type Excel");
				
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
	
	@RequestMapping(value = "pdf/getRouteTpyePdf/{compId}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getRouteTpyePdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable int compId, @PathVariable int showHead) {
		ModelAndView model = new ModelAndView("pdfs/routeTypePdf");
		try {
			
			RouteType[] routeArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllRouteType",
					RouteType[].class);
			List<RouteType> routeList = new ArrayList<RouteType>(Arrays.asList(routeArr));
			
			model.addObject("routeList", routeList);
			
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
				model.addAttribute("isEdit", 0);
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
				model.addAttribute("isEdit", 1);
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
					session.setAttribute("successMsg", "Route Type Saved successfully");
				else
					session.setAttribute("successMsg", "Route Type  Update successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Route Type");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}

		int btnVal = Integer.parseInt(request.getParameter("btnType"));
		if (btnVal == 0)
			return "redirect:/showRouteTypeList";
		else
			return "redirect:/showAddRouteType";
	}

	/**************************** Route del *********************************/

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 26-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- route Delivery list
	List<RouteDelivery> routeDelList = new ArrayList<RouteDelivery>();
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
				model.addAttribute("compId", companyId);

				RouteDelivery[] routeArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllRouteDelivery", RouteDelivery[].class);
				List<RouteDelivery> routeList = new ArrayList<RouteDelivery>(Arrays.asList(routeArr));
				routeDelList = routeList;
				for (int i = 0; i < routeList.size(); i++) {

					routeList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(routeList.get(i).getRouidDelveryId())));
				}
				model.addAttribute("routeDelList", routeList);
				
				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

					rowData.add("Sr No");				
					rowData.add("Delivery Name");
					rowData.add("Time Slot");
					rowData.add("Sort No.");
				
				expoExcel.setRowData(rowData);
				
				exportToExcelList.add(expoExcel);
				int srno = 1;
				for (int i = 0; i < routeList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					rowData.add(" "+srno);
					
						rowData.add(" " + routeList.get(i).getDeliveryName());
						rowData.add(" " + routeList.get(i).getTimeSlots());				
						rowData.add(" " + routeList.get(i).getSortNo());					
						srno = srno + 1;
					
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}
				session.setAttribute("exportExcelListNew", exportToExcelList);
				session.setAttribute("excelNameNew", "RouteDeliveryList");
				session.setAttribute("reportNameNew", "Route Delivery List");
				session.setAttribute("searchByNew", " NA");
				session.setAttribute("mergeUpto1", "$A$1:$L$1");
				session.setAttribute("mergeUpto2", "$A$2:$L$2");

				session.setAttribute("exportExcelList", exportToExcelList);
				session.setAttribute("excelName", "Route Delivery List Excel");
				
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
	
	@RequestMapping(value = "pdf/getRouteDelvrPdf/{compId}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getRouteDelvrPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable int compId, @PathVariable int showHead) {
		ModelAndView model = new ModelAndView("pdfs/routeDelvrPdf");
		try {			
			RouteDelivery[] routeArr = Constants.getRestTemplate()
					.getForObject(Constants.url + "getAllRouteDelivery", RouteDelivery[].class);
			List<RouteDelivery> routeDelList = new ArrayList<RouteDelivery>(Arrays.asList(routeArr));
			
			model.addObject("routeDelList", routeDelList);
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
				model.addAttribute("title", "Add Route Delivery");

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
					session.setAttribute("successMsg", "Route Delivery Saved Successfully");
				else
					session.setAttribute("successMsg", "Route Delivery  Update Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Route Delivery");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		int btnVal = Integer.parseInt(request.getParameter("btnType"));
		if (btnVal == 0)
			return "redirect:/showRouteDelList";
		else
			return "redirect:/showAddRouteDel";
	}

}
