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

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.City;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.cms.ContactUs;
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
}
