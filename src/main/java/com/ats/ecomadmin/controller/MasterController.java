package com.ats.ecomadmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Session;
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
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.Tax;
import com.ats.ecomadmin.model.Uom;

@Controller
@Scope("session")
public class MasterController {

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Show UOM List
	@RequestMapping(value = "/showUomList", method = RequestMethod.GET)
	public String showUomList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		List<Uom> uomList = new ArrayList<Uom>();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("compId", 1);

			int compId = (int) session.getAttribute("compId");
			mav = "masters/uomList";

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			Uom[] tagArr = Constants.getRestTemplate().postForObject(Constants.url + "getUoms", map, Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(tagArr));

			for (int i = 0; i < uomList.size(); i++) {

				uomList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(uomList.get(i).getUomId())));
			}
			model.addAttribute("uomList", uomList);

			model.addAttribute("title", "UOM List");

		} catch (Exception e) {
			System.out.println("Execption in /showUomList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Redirect to Add UOM JSP Page
	@RequestMapping(value = "/newUom", method = RequestMethod.GET)
	public String newUom(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "masters/addUom";
			Uom uom = new Uom();
			model.addAttribute("uom", uom);
			model.addAttribute("title", "Add UOM");

		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Insert UOM in database
	@RequestMapping(value = "/insertUom", method = RequestMethod.POST)
	public String insertUom(HttpServletRequest request, HttpServletResponse response) {

		try {
			Uom uom = new Uom();
			HttpSession session = request.getSession();

			int uomId = Integer.parseInt(request.getParameter("uomId"));
			int compId = (int) session.getAttribute("compId");

			uom.setAllowToCopy(0);
			uom.setCompanyId(compId);
			uom.setDelStatus(1);
			uom.setExInt1(0);
			uom.setExInt2(0);
			uom.setExInt3(0);
			uom.setExVar1("NA");
			uom.setExVar2("NA");
			uom.setExVar3("NA");
			uom.setExVar4("NA");
			uom.setIsActive(Integer.parseInt(request.getParameter("active_uom")));
			uom.setIsParent(0);
			uom.setSortNo(0);
			uom.setUomDesc(request.getParameter("description"));
			uom.setUomId(uomId);
			uom.setUomName(request.getParameter("uom_name"));
			uom.setUomShowName(request.getParameter("show_name"));

			Uom res = Constants.getRestTemplate().postForObject(Constants.url + "saveUom", uom, Uom.class);

			if (res.getUomId() > 0) {
				if (uomId == 0)
					session.setAttribute("successMsg", "UOM Saved Sucessfully");
				else
					session.setAttribute("successMsg", "UOM  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save UOM");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showUomList";

	}

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Update UOM
	@RequestMapping(value = "/editUom", method = RequestMethod.GET)
	public String editUom(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "masters/addUom";

			String base64encodedString = request.getParameter("uomId");
			String uomId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("uomId", uomId);

			Uom uom = Constants.getRestTemplate().postForObject(Constants.url + "getUomById", map, Uom.class);
			model.addAttribute("uom", uom);
			model.addAttribute("title", "Edit UOM");

		} catch (Exception e) {
			System.out.println("Execption in /editUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Delete UOM
	@RequestMapping(value = "/deleteUom", method = RequestMethod.GET)
	public String deleteUom(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		try {

			String base64encodedString = request.getParameter("uomId");
			String uomId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("uomId", Integer.parseInt(uomId));

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteUomById", map, Info.class);

			if (!info.isError()) {
				session.setAttribute("successMsg", info.getMsg());
			} else {
				session.setAttribute("errorMsg", info.getMsg());
			}

		} catch (Exception e) {
			System.out.println("Execption in /deleteFrOfferConfig : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showUomList";
	}

	/*-------------------------------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Show Tax List
	@RequestMapping(value = "/showTaxList", method = RequestMethod.GET)
	public String showTaxList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		List<Tax> taxList = new ArrayList<Tax>();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("compId", 1);

			int compId = (int) session.getAttribute("compId");
			mav = "masters/taxList";

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			Tax[] tagArr = Constants.getRestTemplate().postForObject(Constants.url + "getTaxes", map, Tax[].class);
			taxList = new ArrayList<Tax>(Arrays.asList(tagArr));

			for (int i = 0; i < taxList.size(); i++) {

				taxList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(taxList.get(i).getTaxId())));
			}
			model.addAttribute("taxList", taxList);

			model.addAttribute("title", "Tax List");

		} catch (Exception e) {
			System.out.println("Execption in /showTaxList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Redirect to Add Tax JSP Page
	@RequestMapping(value = "/newTax", method = RequestMethod.GET)
	public String newTax(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "masters/addTax";
			Tax tax = new Tax();
			model.addAttribute("tax", tax);
			model.addAttribute("title", "Add Tax");

		} catch (Exception e) {
			System.out.println("Execption in /newTax : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Insert Tax
	@RequestMapping(value="/insertTax",method=RequestMethod.POST)
	public String insertTax(HttpServletRequest request, HttpServletResponse response) {

		try {
			Tax tax = new Tax();
			HttpSession session = request.getSession();

			int taxId = Integer.parseInt(request.getParameter("taxId"));
			int compId = (int) session.getAttribute("compId");

			tax.setTaxId(taxId);
			tax.setTaxName(request.getParameter("taxName"));
			tax.setTaxDesc(request.getParameter("description"));
			tax.setHsnCode(request.getParameter("hsnCode"));
			System.out.println("---------------------"+request.getParameter("sgstPer"));
			tax.setSgstPer(Float.parseFloat(request.getParameter("sgstPer")));
			tax.setCgstPer(Float.parseFloat(request.getParameter("cgstPer")));
			tax.setIgstPer(Float.parseFloat(request.getParameter("igstPer")));
			tax.setCessPer(Float.parseFloat(request.getParameter("cessPer")));
			tax.setTotalTaxPer(Float.parseFloat(request.getParameter("totalTaxPer")));
			tax.setAllowToCopy(0);
			tax.setCompanyId(compId);
			tax.setDelStatus(1);
			tax.setExInt1(0);
			tax.setExInt2(0);
			tax.setExInt3(0);
			tax.setExVar1("NA");
			tax.setExVar2("NA");
			tax.setExVar3("NA");
			tax.setIsActive(Integer.parseInt(request.getParameter("active_tax")));
			tax.setIsParent(0);
			tax.setSortNo(0);
			

			Tax res = Constants.getRestTemplate().postForObject(Constants.url + "saveTax", tax, Tax.class);

			if (res.getTaxId() > 0) {
				if (taxId == 0)
					session.setAttribute("successMsg", "Tax Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Tax  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Tax");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertTax : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showTaxList";

	}
	

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- Update Tax
	@RequestMapping(value = "/editTax", method = RequestMethod.GET)
	public String editTax(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "masters/addTax";

			String base64encodedString = request.getParameter("taxId");
			String taxId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("taxId", taxId);

			Tax tax = Constants.getRestTemplate().postForObject(Constants.url + "getTaxById", map, Tax.class);
			model.addAttribute("tax", tax);
			model.addAttribute("title", "Edit Tax");

		} catch (Exception e) {
			System.out.println("Execption in /editTax : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	// Created By :- Mahendra Singh
		// Created On :- 12-09-2020
		// Modified By :- NA
		// Modified On :- NA
		// Descriprion :- Delete Tax
		@RequestMapping(value = "/deleteTax", method = RequestMethod.GET)
		public String deleteTax(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("taxId");
				String taxId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("taxId", Integer.parseInt(taxId));

				Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteTaxById", map, Info.class);

				if (!info.isError()) {
					session.setAttribute("successMsg", info.getMsg());
				} else {
					session.setAttribute("errorMsg", info.getMsg());
				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteTax : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showTaxList";
		}

}
