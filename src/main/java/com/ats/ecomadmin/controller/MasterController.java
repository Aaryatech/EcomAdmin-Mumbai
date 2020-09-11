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
import com.ats.ecomadmin.model.Uom;

@Controller
@Scope("session")
public class MasterController {
	
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
			
			Uom[] tagArr = Constants.getRestTemplate().postForObject(Constants.url + "getUoms", map,
					Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(tagArr));

			for (int i = 0; i < uomList.size(); i++) {

				uomList.get(i)
						.setExVar1(FormValidation.Encrypt(String.valueOf(uomList.get(i).getUomId())));
			}
			model.addAttribute("uomList", uomList);

			model.addAttribute("title", "UOM List");
			
			
		} catch (Exception e) {
			System.out.println("Execption in /showUomList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/newUom", method = RequestMethod.GET)
	public String newFranchiseConfiguration(HttpServletRequest request, HttpServletResponse response, Model model) {

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
	
	
	
	@RequestMapping(value = "/insertUom", method = RequestMethod.POST)
	public String insertUom(HttpServletRequest request, HttpServletResponse response) {
		
		try {
				Uom uom = new Uom();
				HttpSession session = request.getSession();
				
				int uomId = Integer.parseInt(request.getParameter("uomId"));
				int compId = (int) session.getAttribute("compId");
				 
				uom.setAllowToCopy(0);
				uom.setCompanyId(compId);
				uom.setDelStatus(0);
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
				
				Uom res = Constants.getRestTemplate()
						.postForObject(Constants.url + "saveUom", uom, Uom.class);
				
				if(res.getUomId()>0) {
					if(uomId==0) 
							session.setAttribute("successMsg", "UOM Saved Sucessfully");
						else
							session.setAttribute("successMsg", "UOM  Update Sucessfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Save UOM");
					}
				
			
		}catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showUomList";
		
	}
	
	
	

	@RequestMapping(value = "/editUom", method = RequestMethod.GET)
	public String editUom(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			mav = "masters/addUom";
			
			String base64encodedString = request.getParameter("uomId");
			String uomId = FormValidation.DecodeKey(base64encodedString);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("uomId", uomId);
			
			Uom uom = Constants.getRestTemplate()
					.postForObject(Constants.url + "getUomById", map, Uom.class);
			model.addAttribute("uom", uom);
			model.addAttribute("title", "Edit UOM");
			
		} catch (Exception e) {
			System.out.println("Execption in /editUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "/deleteUom", method = RequestMethod.GET)
	public String deleteIngrediantCategory(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		try {

			String base64encodedString = request.getParameter("uomId");
			String uomId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("uomId", Integer.parseInt(uomId));

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteUomById", map,
					Info.class);

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

}
