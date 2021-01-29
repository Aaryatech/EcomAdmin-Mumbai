package com.ats.ecomadmin.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.City;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.ExportToExcel;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.cms.ContactUs;
import com.ats.ecomadmin.model.cms.TermsAndCondtn;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Scope("session")
public class CMSController {

	ContactUs[] cusArr = null;
	List<ContactUs> cusList = null;
	ObjectMapper mapper = null;
	
	@RequestMapping(value = "/configContactUs", method = RequestMethod.GET)
	public String showAddCompany(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("configContactUs", "configContactUs", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "cms/addContactUsInfo";
				CompMaster comp = new CompMaster();
				model.addAttribute("comp", comp);
				model.addAttribute("title", "Add Contact US Details");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);

				int compId = (int) session.getAttribute("companyId");

				ContactUs cus = new ContactUs();
				
				mapper = new ObjectMapper();
				
				
				cusArr = mapper.readValue(new File(Constants.JSON_FILES_PATH + "1_ContactUS_.json"),
							ContactUs[].class);
					
				 cusList = new ArrayList<ContactUs>(Arrays.asList(cusArr));				
				for (int i = 0; i < cusList.size(); i++) {
					if(cusList.get(i).getCompanyId()==compId) {
						
						cus.setCompanyId(compId);
						
						cus.setEmailText(cusList.get(i).getEmailText());
						cus.setEmail1(cusList.get(i).getEmail2());
						cus.setEmail2(cusList.get(i).getEmail1());			

						cus.setManufacAddressTxt(cusList.get(i).getManufacAddressTxt());
						cus.setManufacAddress(cusList.get(i).getManufacAddress());
						
						cus.setOfficeText(cusList.get(i).getManufacAddressTxt());
						cus.setOfficeAddress(cusList.get(i).getManufacAddress());			
						
						cus.setPageHead(cusList.get(i).getPageHead());
						cus.setSubHeading(cusList.get(i).getSubHeading());
						
						cus.setPhoneText(cusList.get(i).getPhoneText());
						cus.setPhone1(cusList.get(i).getPhone1());
						cus.setPhone2(cusList.get(i).getPhone2());
						
						cus.setFacebookLink(cusList.get(i).getFacebookLink());
						cus.setTwitterLink(cusList.get(i).getTwitterLink());
						cus.setLinkedInLink(cusList.get(i).getLinkedInLink());
						cus.setGoogleAcLink(cusList.get(i).getGoogleAcLink());
						
						cus.setFooterAddress(cusList.get(i).getFooterAddress());
						cus.setFooterEmail1(cusList.get(i).getFooterEmail1());			
						cus.setFooterEmail2(cusList.get(i).getFooterEmail2());
						cus.setFooterPhone1(cusList.get(i).getFooterPhone1());
						cus.setFooterPhone2(cusList.get(i).getFooterPhone2());
					}
				}
				
				model.addAttribute("cus", cus);		
			}
		} catch (Exception e) {
			System.out.println("Execption in /configContactUs : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "/addContactCompDtl", method = RequestMethod.POST)
	public String addContactUsDtl(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		try {
			HttpSession session = request.getSession();
			
			ArrayList<ContactUs> cntctUsList = new ArrayList<ContactUs>();
			ContactUs cus = new ContactUs();
			
			int compId = (int) session.getAttribute("companyId");
			
			
			for (int i = 0; i < cusList.size(); i++) {
				if(cusList.get(i).getCompanyId()==compId) {					
										
					cus.setFacebookLink(cusList.get(i).getFacebookLink());
					cus.setTwitterLink(cusList.get(i).getTwitterLink());
					cus.setLinkedInLink(cusList.get(i).getLinkedInLink());
					cus.setGoogleAcLink(cusList.get(i).getGoogleAcLink());				
					
					cus.setFooterAddress(cusList.get(i).getFooterAddress());
					cus.setFooterEmail1(cusList.get(i).getFooterEmail1());			
					cus.setFooterEmail2(cusList.get(i).getFooterEmail2());
					cus.setFooterPhone1(cusList.get(i).getFooterPhone1());
					cus.setFooterPhone2(cusList.get(i).getFooterPhone2());
					
				}
			}
			cus.setCompanyId(compId);
			
			cus.setEmailText(request.getParameter("emailText"));
			cus.setEmail1(request.getParameter("email1"));
			cus.setEmail2(request.getParameter("email2"));			

			cus.setManufacAddressTxt(request.getParameter("manufacAddressTxt"));
			cus.setManufacAddress(request.getParameter("manufacAddress"));
			
			cus.setOfficeText(request.getParameter("officeText"));
			cus.setOfficeAddress(request.getParameter("officeAddress"));			
			
			cus.setPageHead(request.getParameter("pageHead"));
			cus.setSubHeading(request.getParameter("subHeading"));
			
			cus.setPhoneText(request.getParameter("phoneText"));
			cus.setPhone1(request.getParameter("phone1"));
			cus.setPhone2(request.getParameter("phone2"));
			
			cntctUsList.add(cus);
			
			System.out.println("List Gen-----------"+cntctUsList);
			
			
			ObjectMapper obj = new ObjectMapper();
			String jsonStr = obj.writeValueAsString(cntctUsList);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
			String JSON_SAVE_URL = "/home/maddy/ats-11/";
			//Constants.getRestTemplate()
			//		.getForObject(Constants.url + "getJsonPath", String.class);
			
			Writer output = null;
			File file = null;
			
			file = new File(JSON_SAVE_URL + compId + "_ContactUS_" + ".json");
			output = new BufferedWriter(new FileWriter(file));
			output.write(jsonStr.toString());
			output.close();

			
		}catch (Exception e) {
			System.out.println("Excep in /addContactCompDtl : "+e.getMessage());
		}
		
		return "redirect:/configContactUs";
		
	}
	
	@RequestMapping(value = "/insertSocialLinks", method = RequestMethod.POST)
	public String insertSocialLinks(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		try {
			HttpSession session = request.getSession();
			
			ArrayList<ContactUs> cntctUsList = new ArrayList<ContactUs>();
			ContactUs cus = new ContactUs();
			
			int compId = (int) session.getAttribute("companyId");
			cus.setCompanyId(compId);
			
			for (int i = 0; i < cusList.size(); i++) {
				if(cusList.get(i).getCompanyId()==compId) {
					
					cus.setEmailText(cusList.get(i).getEmailText());
					cus.setEmail1(cusList.get(i).getEmail2());
					cus.setEmail2(cusList.get(i).getEmail1());			

					cus.setManufacAddressTxt(cusList.get(i).getManufacAddressTxt());
					cus.setManufacAddress(cusList.get(i).getManufacAddress());
					
					cus.setOfficeText(cusList.get(i).getManufacAddressTxt());
					cus.setOfficeAddress(cusList.get(i).getManufacAddress());			
					
					cus.setPageHead(cusList.get(i).getPageHead());
					cus.setSubHeading(cusList.get(i).getSubHeading());
					
					cus.setPhoneText(cusList.get(i).getPhoneText());
					cus.setPhone1(cusList.get(i).getPhone1());
					cus.setPhone2(cusList.get(i).getPhone2());
					
					cus.setFooterAddress(cusList.get(i).getFooterAddress());
					cus.setFooterEmail1(cusList.get(i).getFooterEmail1());			
					cus.setFooterEmail2(cusList.get(i).getFooterEmail2());
					cus.setFooterPhone1(cusList.get(i).getFooterPhone1());
					cus.setFooterPhone2(cusList.get(i).getFooterPhone2());
					
					
				}
			}
			
			cus.setFacebookLink(request.getParameter("facebookLink"));
			cus.setTwitterLink(request.getParameter("twitterLink"));
			cus.setLinkedInLink(request.getParameter("linkedInLink"));
			cus.setGoogleAcLink(request.getParameter("googleAcLink"));
			
			
			cntctUsList.add(cus);
			
			System.out.println("List Gen-----------"+cntctUsList);
			
			
			ObjectMapper obj = new ObjectMapper();
			String jsonStr = obj.writeValueAsString(cntctUsList);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
			String JSON_SAVE_URL = "/home/maddy/ats-11/";
			//Constants.getRestTemplate()
			//		.getForObject(Constants.url + "getJsonPath", String.class);
			
			Writer output = null;
			File file = null;
			
			file = new File(JSON_SAVE_URL + compId + "_ContactUS_" + ".json");
			output = new BufferedWriter(new FileWriter(file));
			output.write(jsonStr.toString());
			output.close();

			
		}catch (Exception e) {
			System.out.println("Excep in /insertSocialLinks : "+e.getMessage());
		}
		
		return "redirect:/configContactUs";
		
	}
	
	@RequestMapping(value = "/submitFooterDtl", method = RequestMethod.POST)
	public String submitFooterDtl(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		try {
			HttpSession session = request.getSession();
			
			ArrayList<ContactUs> cntctUsList = new ArrayList<ContactUs>();
			ContactUs cus = new ContactUs();
			
			int compId = (int) session.getAttribute("companyId");
			cus.setCompanyId(compId);
			
			for (int i = 0; i < cusList.size(); i++) {
				if(cusList.get(i).getCompanyId()==compId) {
					
					cus.setCompanyId(compId);
					
					cus.setEmailText(cusList.get(i).getEmailText());
					cus.setEmail1(cusList.get(i).getEmail2());
					cus.setEmail2(cusList.get(i).getEmail1());			

					cus.setManufacAddressTxt(cusList.get(i).getManufacAddressTxt());
					cus.setManufacAddress(cusList.get(i).getManufacAddress());
					
					cus.setOfficeText(cusList.get(i).getManufacAddressTxt());
					cus.setOfficeAddress(cusList.get(i).getManufacAddress());			
					
					cus.setPageHead(cusList.get(i).getPageHead());
					cus.setSubHeading(cusList.get(i).getSubHeading());
					
					cus.setPhoneText(cusList.get(i).getPhoneText());
					cus.setPhone1(cusList.get(i).getPhone1());
					cus.setPhone2(cusList.get(i).getPhone2());
					
					cus.setFacebookLink(cusList.get(i).getFacebookLink());
					cus.setTwitterLink(cusList.get(i).getTwitterLink());
					cus.setLinkedInLink(cusList.get(i).getLinkedInLink());
					cus.setGoogleAcLink(cusList.get(i).getGoogleAcLink());
				}
			}
			
			
			cus.setFooterAddress(request.getParameter("footerAddress"));
			cus.setFooterEmail1(request.getParameter("footerEmail1"));		
			cus.setFooterEmail2(request.getParameter("footerEmail2"));
			cus.setFooterPhone1(request.getParameter("footerPhone1"));
			cus.setFooterPhone2(request.getParameter("footerPhone2"));
			
			
			cntctUsList.add(cus);
			
			System.out.println("List Gen-----------"+cntctUsList);
			
			
			ObjectMapper obj = new ObjectMapper();
			String jsonStr = obj.writeValueAsString(cntctUsList);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
			String JSON_SAVE_URL = "/home/maddy/ats-11/";
			//Constants.getRestTemplate()
			//		.getForObject(Constants.url + "getJsonPath", String.class);
			
			Writer output = null;
			File file = null;
			
			file = new File(JSON_SAVE_URL + compId + "_ContactUS_" + ".json");
			output = new BufferedWriter(new FileWriter(file));
			output.write(jsonStr.toString());
			output.close();

			
		}catch (Exception e) {
			System.out.println("Excep in /submitFooterDtl : "+e.getMessage());
		}
		
		return "redirect:/configContactUs";
		
	}
	
	
	/******************************************************************************************/
	@RequestMapping(value = "/TAndCList", method = RequestMethod.GET)
	public String showUomList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();		
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("TAndCList", "TAndCList", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "cms/t&cList";

				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("companyId", compId);
				
				TermsAndCondtn[] tncArr = Constants.getRestTemplate().postForObject(Constants.url + "getTermsAndCondtns", map,
						TermsAndCondtn[].class);
				List<TermsAndCondtn> tncList = new ArrayList<TermsAndCondtn>(Arrays.asList(tncArr));
				for (int i = 0; i < tncList.size(); i++) {

					tncList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(tncList.get(i).getTermsId())));
				}
					
				
				model.addAttribute("tncList", tncList);	
				model.addAttribute("title", "Terms & Conditions List");

				Info add = AccessControll.checkAccess("TAndCList", "TAndCList", "0", "1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("TAndCList", "TAndCList", "0", "0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("TAndCList", "TAndCList", "0", "0", "0", "1",
						newModuleList);
				
				// export To Excel
//				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
//
//				ExportToExcel expoExcel = new ExportToExcel();
//				List<String> rowData = new ArrayList<String>();

//				rowData.add("Sr No");
//				rowData.add("UOM");
//				rowData.add("Status");
//
//				expoExcel.setRowData(rowData);
//				int srno = 1;
//				exportToExcelList.add(expoExcel);
//				for (int i = 0; i < uomList.size(); i++) {
//					expoExcel = new ExportToExcel();
//					rowData = new ArrayList<String>();
//
//					rowData.add(" " + srno);
//					rowData.add(" " + uomList.get(i).getUomName());
//					rowData.add(uomList.get(i).getIsActive() == 1 ? "Active" : "In-Active");
//					
//					srno=srno+1;
//					expoExcel.setRowData(rowData);
//					exportToExcelList.add(expoExcel);
//
//				}
//				session.setAttribute("exportExcelListNew", exportToExcelList);
//				session.setAttribute("excelNameNew", "UOM");
//				session.setAttribute("reportNameNew", "UOM List");
//				 session.setAttribute("searchByNew", "NA");
//				session.setAttribute("mergeUpto1", "$A$1:$L$1");
//				session.setAttribute("mergeUpto2", "$A$2:$L$2");
//
//				session.setAttribute("exportExcelList", exportToExcelList);
//				session.setAttribute("excelName", "UOM Excel");

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
			System.out.println("Execption in /TAndCList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/termsAndCondtn", method = RequestMethod.GET)
	public String termsAndCondtn(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("termsAndCondtn", "TAndCList", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "cms/addT&C";
				TermsAndCondtn tnc = new TermsAndCondtn();
				
				model.addAttribute("tnc", tnc);
				model.addAttribute("title", "Add Terms And Conditions");

			}
		} catch (Exception e) {
			System.out.println("Execption in /termsAndCondtn : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	@RequestMapping(value = "/addTermsAndCondtnDtl", method = RequestMethod.POST)
	public @ResponseBody String addTermsAndCondtnDtl(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		try {
			System.out.println("Here");
			HttpSession session = request.getSession();
			
			int compId = (int) session.getAttribute("companyId");
			int termsId = Integer.parseInt(request.getParameter("termsId"));
			
			TermsAndCondtn tnc = new TermsAndCondtn();
			
			tnc.setCompanyId(compId);			
			tnc.setDelStatus(1);
			tnc.setExInt1(0);
			tnc.setExInt2(0);
			tnc.setExVar1("NA");
			tnc.setExVar2("NA");
			tnc.setSectionTxt("NA");
			tnc.setTermsId(termsId);
			tnc.setTermsTxt(request.getParameter("termsCond"));
			
			TermsAndCondtn newTnc = Constants.getRestTemplate().postForObject(Constants.url + "addTermsAndConditions", tnc,
					TermsAndCondtn.class);
			
			if(newTnc.getTermsId()>0) {
					if (termsId == 0)
						session.setAttribute("successMsg", "T&C Saved Sucessfully");
					else
						session.setAttribute("successMsg", "T&C  Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save T&C");
				}
			
		}catch (Exception e) {
			System.out.println("Excep in /addTermsAndCondtnDtl : "+e.getMessage());
			e.printStackTrace();
		}
		
			return "redirect:/termsAndCondtn";
				
	}
	
	
	@RequestMapping(value = "/editTerms", method = RequestMethod.GET)
	public String editUom(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editTerms", "TAndCList", "0", "0", "1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "cms/addT&C";

				String base64encodedString = request.getParameter("termsId");
				String termsId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("termId",Integer.parseInt(termsId));

				TermsAndCondtn tnc = Constants.getRestTemplate().postForObject(Constants.url + "getTermsAndCondtnById", map,
						TermsAndCondtn.class);
				model.addAttribute("tnc", tnc);
				
				model.addAttribute("title", "Edit Terms And Conditions");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editTerms : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	
	
	@RequestMapping(value = "/deleteTerms", method = RequestMethod.GET)
	public String deleteUom(HttpServletRequest request, HttpServletResponse response) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteTerms", "TAndCList", "0", "0", "0", "1", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				String base64encodedString = request.getParameter("termsId");
				String termsId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
					map.add("termsId", Integer.parseInt(termsId));
					Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteTermsAndCondtnId", map,
							Info.class);

					if (!info.isError()) {
						session.setAttribute("successMsg", info.getMsg());
					} else {
						session.setAttribute("errorMsg", info.getMsg());
					}				
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/TAndCList";
	}
}
